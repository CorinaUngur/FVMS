package connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import utils.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import config.Settings;
import connection.tasks.InitTask;
import connection.tasks.LogOutTask;
import connection.tasks.LoginTask;
import connection.tools.Config;

public class Connector {
	private static Connector instance = null;
	private Channel channel = null;
	private QueueingConsumer loginQ = null;
	private QueueingConsumer initQ = null;
	private QueueingConsumer logoutQ = null;
	private static final ExecutorService threadpool = Executors.newFixedThreadPool(3);
	
	private static final ConcurrentLinkedQueue<Integer> loggedUsers = new ConcurrentLinkedQueue<Integer>();


	public static ConcurrentLinkedQueue<Integer> getLoggedusers() {
		return loggedUsers;
	}

	public static ExecutorService getThreadpool() {
		return threadpool;
	}

	private Connector() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(Settings.CONN_HOST);
		try {
			Connection connection = factory.newConnection();
			channel = connection.createChannel();
			prepareQueues();
			startMainLoop();
		} catch (IOException e) {
			Logger.logERROR(e);
		} catch (TimeoutException e) {
			Logger.logERROR(e);
		}

	}

	private void prepareQueues() throws IOException {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-message-ttl", Config.MessageExpirationTime.getInt());
		
		loginQ = startConsuming(Settings.CONN_QLOGIN, args);
		initQ = startConsuming(Settings.CONN_QINIT, args);
		logoutQ = startConsuming(Settings.CONN_QLOGOUT, args);
	
	}

	private QueueingConsumer startConsuming(String queue_name, Map<String, Object> args) throws IOException {
		channel.queueDeclare(queue_name, false, false, false,
				args);
		QueueingConsumer queue = new QueueingConsumer(channel);
		channel.basicConsume(queue_name, queue);
		return queue;
	}

	public static Connector getInstance() {
		if (instance == null) {
			instance = new Connector();
		}
		return instance;
	}

	private void startMainLoop() {
		threadpool.submit(new LoginTask(loginQ, this));
		threadpool.submit(new InitTask(initQ, this));
		threadpool.submit(new LogOutTask(logoutQ, this));
	}

	public HashMap<String, Object> getMessage(byte[] deliveryBody)
			throws JsonParseException, JsonMappingException, IOException {
		HashMap<String, Object> credentials = null;

		String message = new String(deliveryBody);
		System.out.println(message);
		// String body = message.substring(1, message.length() - 1);
		ObjectMapper mapper = new ObjectMapper();

		credentials = mapper.readValue(message,
				new TypeReference<HashMap<String, String>>() {
				});

		return credentials;
	}

	public void sendResponse(BasicProperties props,
			HashMap<String, Object> responseMap) throws JsonProcessingException {

		ObjectMapper resultMapper = new ObjectMapper();
		byte[] response = resultMapper.writeValueAsBytes(responseMap);
		BasicProperties replyToProps = new BasicProperties.Builder()
				.correlationId(props.getCorrelationId()).build();
		Logger.logINFO("Sending... " + String.valueOf(response));
		sendMessage(props, replyToProps, response);
	}


	private void sendMessage(BasicProperties props,
			BasicProperties replyToProps, byte[] message) {
		try {
			channel.basicPublish("", props.getReplyTo(), replyToProps, message);
		} catch (IOException e) {
			Logger.logERROR(e);
		}

	}
}
