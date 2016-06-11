package connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import utils.JSONManipulator;
import utils.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import config.Settings;
import connection.tasks.AddFileTask;
import connection.tasks.DownloadTask;
import connection.tasks.GetHistoryTask;
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
	private QueueingConsumer historyQ = null;
	private QueueingConsumer uploadQ = null;
	private QueueingConsumer downloadQ = null;
	private static final ExecutorService threadpool = Executors
			.newFixedThreadPool(10);

	private static final ConcurrentLinkedQueue<Integer> loggedUsers = new ConcurrentLinkedQueue<Integer>();

	private Connector() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(Settings.CONN_HOST);
		try {
			Connection connection = factory.newConnection();
			channel = connection.createChannel();
			prepareQueues();
		} catch (IOException e) {
			Logger.logERROR(e);
		} catch (TimeoutException e) {
			Logger.logERROR(e);
		}

	}

	public static Connector getInstance() {
		if (instance == null) {
			instance = new Connector();
		}
		return instance;
	}

	public static ConcurrentLinkedQueue<Integer> getLoggedusers() {
		return loggedUsers;
	}

	public static ExecutorService getThreadpool() {
		return threadpool;
	}

	public HashMap<String, Object> getMessage(byte[] deliveryBody)
			throws JsonParseException, JsonMappingException, IOException {
		HashMap<String, Object> credentials = new HashMap<String, Object>();

		String message = new String(deliveryBody);
		Logger.logINFO("Received: " + message);
		credentials = JSONManipulator.getMap(message);
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

	public void startMainLoop() {
		threadpool.submit(new LoginTask(loginQ));
		threadpool.submit(new InitTask(initQ));
		threadpool.submit(new LogOutTask(logoutQ));
		threadpool.submit(new GetHistoryTask(historyQ));
		threadpool.submit(new AddFileTask(uploadQ));
		threadpool.submit(new DownloadTask(downloadQ));
	}

	private void prepareQueues() throws IOException {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-message-ttl", Config.MessageExpirationTime.getInt());

		loginQ = startConsuming(Settings.CONN_QLOGIN, args);
		initQ = startConsuming(Settings.CONN_QINIT, args);
		logoutQ = startConsuming(Settings.CONN_QLOGOUT, args);
		historyQ = startConsuming(Settings.CONN_QHISTORY, args);
		uploadQ = startConsuming(Settings.CONN_QUPLOAD, args);
		downloadQ = startConsuming(Settings.CONN_QDOWNLOAD, args);

	}

	private QueueingConsumer startConsuming(String queue_name,
			Map<String, Object> args) throws IOException {
		channel.queueDeclare(queue_name, false, false, false, args);
		QueueingConsumer queue = new QueueingConsumer(channel);
		channel.basicConsume(queue_name, queue);
		return queue;
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
