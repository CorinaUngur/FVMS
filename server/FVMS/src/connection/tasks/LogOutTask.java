package connection.tasks;

import java.util.HashMap;

import com.rabbitmq.client.QueueingConsumer;

import connection.Connector;

public class LogOutTask extends NoticeTask{

	public LogOutTask(QueueingConsumer loginQ, Connector conn) {
		super(loginQ, conn);
	}

	@Override
	protected void executeRequest(HashMap<String, Object> request) {
		int uid = (int) request.get("uid");
		Connector.getLoggedusers().remove(uid);
		
	}

}
