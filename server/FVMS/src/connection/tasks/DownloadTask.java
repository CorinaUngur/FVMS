package connection.tasks;

import java.util.HashMap;
import java.util.List;

import utils.JSONManipulator;
import versioning.FileSystem;

import com.rabbitmq.client.QueueingConsumer;

import db.PermissionsDB;
import db.tools.Permissions;

public class DownloadTask extends RequestTask {

	public DownloadTask(QueueingConsumer queue) {
		super(queue);
	}

	@Override
	protected HashMap<String, Object> getResponse(
			HashMap<String, Object> request) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		int uid = Integer.valueOf(request.get("uid").toString());
		int pid = Integer.valueOf(request.get("pid").toString());
		FileSystem fs = new FileSystem();
		if (PermissionsDB.getInstance().hasPermission_onProject(uid, pid,
				Permissions.READ)){
			List<Integer> fileIDs = JSONManipulator.deserializeList(request.get("fids").toString());
			HashMap<Integer, byte[]> contents = new HashMap<Integer, byte[]>();
			for(int i : fileIDs){
				contents.put(i, fs.getLastVersion(i));
			}
			response.put("contents", contents);
		}
		
		return response;
	}

}
