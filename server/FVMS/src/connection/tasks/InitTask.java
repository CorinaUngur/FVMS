package connection.tasks;

import java.util.HashMap;

import com.rabbitmq.client.QueueingConsumer;

import connection.Connector;
import db.ProjectsDB;

public class InitTask extends RequestTask {
	ProjectsDB pdb = ProjectsDB.getInstance();

	public InitTask(QueueingConsumer initQueue) {
		super(initQueue);
	}

	@Override
	protected HashMap<String, Object> getResponse(
			HashMap<String, Object> request) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		String session = "expired";
		int uid = Integer.valueOf(request.get("uid").toString());
		if (Connector.getLoggedusers().contains(uid)) {
			response.put("projects", pdb.getProjects(uid));
			session = "available";
		}
		response.put("session", session);
		return response;
	}

}
