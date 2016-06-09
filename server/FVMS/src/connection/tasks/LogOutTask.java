package connection.tasks;

import java.util.HashMap;

import com.rabbitmq.client.QueueingConsumer;

import connection.Connector;

public class LogOutTask extends NoticeTask{

	public LogOutTask(QueueingConsumer logoutQ) {
		super(logoutQ);
	}

	@Override
	protected void executeRequest(HashMap<String, Object> request) {
		int uid = Integer.valueOf(request.get("uid").toString());
		Connector.getLoggedusers().remove(uid);
		
	}

}
