package connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

import config.Settings;
import connection.tools.Config;
import db.UsersDB;
import db.tools.Messages;

public class Connector {
	private static Connector instance = null;
	private Channel channel = null;
	private QueueingConsumer login = null;
	private UsersDB db = UsersDB.getInstance();

	private Connector() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(Settings.CONN_HOST);
		try {
			Connection connection = factory.newConnection();
			channel = connection.createChannel();
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("x-message-ttl", Config.MessageExpirationTime.getInt());
			channel.queueDeclare(Settings.CONN_QLOGIN, false, false, false,
					args);
			create_consumers();
			channel.basicConsume(Settings.CONN_QLOGIN, login);
			startMainLoop();
		} catch (IOException e) {
			Logger.getGlobal().log(Level.FINE,e.getMessage());
		} catch (TimeoutException e) {
			Logger.getGlobal().log(Level.FINE,e.getMessage());
		}

	}

	public static Connector getInstance() {
		if (instance == null) {
			instance = new Connector();
		}
		return instance;
	}

	private void startMainLoop() {
		HashMap<String, String> credentials = null;
		
		while (true) {
			QueueingConsumer.Delivery delivery;
			try {
				delivery = login.nextDelivery();
				credentials = getMessage(delivery.getBody());
				sendLogInResponse(delivery.getProperties(), credentials);

			} catch (ShutdownSignalException e) {
				Logger.getGlobal().log(Level.FINE,e.getMessage());
				startMainLoop();
			} catch (ConsumerCancelledException e) {
				Logger.getGlobal().log(Level.FINE,e.getMessage());
				startMainLoop();
			} catch (IOException e) {
				Logger.getGlobal().log(Level.FINE,e.getMessage());
				startMainLoop();
			} catch (InterruptedException e) {
				Logger.getGlobal().log(Level.FINE,e.getMessage());
				startMainLoop();
			}
		}

	}

	private HashMap<String, String> getMessage(byte[] deliveryBody)
			throws JsonParseException, JsonMappingException, IOException {
		HashMap<String, String> credentials = null;

		String message = new String(deliveryBody);
		System.out.println(message);
		//String body = message.substring(1, message.length() - 1);
		ObjectMapper mapper = new ObjectMapper();

		credentials = mapper.readValue(message,
				new TypeReference<HashMap<String, String>>() {
				});

		return credentials;
	}

	private void sendLogInResponse(BasicProperties props,
			HashMap<String, String> credentials) throws JsonProcessingException {
		String authorization = "";
		String result = "";
		if (credentials != null) {
			String username = credentials.get("username");
			String password = credentials.get("password");
			result = db.checkUser_byUsername(username, password);

			if (result.equals(Messages.Login_succesfull.toString())) {
				authorization = "1";
			} else {
				authorization = "0";
			}
		} else {
			authorization="0";
			result=Messages.MessageInWrongFormat.toString();
		}
		HashMap<String, String> responseMap = new HashMap<>();
		responseMap.put("authorized", authorization);
		responseMap.put("message", result);
		ObjectMapper resultMapper = new ObjectMapper();
		byte[] response = resultMapper.writeValueAsBytes(responseMap);
		BasicProperties replyToProps = new BasicProperties.Builder()
				.correlationId(props.getCorrelationId()).build();
		sendMessage(props, replyToProps, response);
	}

	private void create_consumers() {
		login = new QueueingConsumer(channel);
	}

	private void sendMessage(BasicProperties props,
			BasicProperties replyToProps, byte[] message) {
		try {
			channel.basicPublish("", props.getReplyTo(), replyToProps, message);
		} catch (IOException e) {
			Logger.getGlobal().log(Level.FINE,e.getMessage());
		}

	}
}
