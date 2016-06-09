package connection.tasks;

import java.util.HashMap;

import utils.Logger;

import com.rabbitmq.client.QueueingConsumer;

import connection.Connector;
import db.UsersDB;
import db.tools.Messages;

public class LoginTask extends RequestTask {

	private static UsersDB db = UsersDB.getInstance();

	public LoginTask(QueueingConsumer loginQ) {
		super(loginQ);
	}

	@Override
	protected HashMap<String, Object> getResponse(
			HashMap<String, Object> credentials) {
		HashMap<String, Object> responseMap = new HashMap<String, Object>();
		int authorization = 0;
		String result = "";
		int uid = 0;
		if (credentials != null) {
			String username = (String) credentials.get("username");
			String password = (String) credentials.get("password");
			result = db.checkUser_byUsername(username, password);
			uid = db.getUID_byUsername(username);
			if (result.equals(Messages.Login_succesfull.toString())) {
				authorization = 1;
				Connector.getLoggedusers().add(uid);
				responseMap.put("uid", uid);
				responseMap.put("username", username);
			}
		} else {
			result = Messages.MessageInWrongFormat.toString();
		}
		responseMap.put("authorized", authorization);
		responseMap.put("message", result);
		Logger.logINFO(responseMap.toString());
		return responseMap;
	}

}
