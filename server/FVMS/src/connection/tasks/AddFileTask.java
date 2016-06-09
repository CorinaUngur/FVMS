package connection.tasks;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import versioning.FileSystem;

import com.rabbitmq.client.QueueingConsumer;

import db.PermissionsDB;
import db.UsersDB;
import db.tools.Permissions;

public class AddFileTask extends RequestTask {

	public AddFileTask(QueueingConsumer loginQ) {
		super(loginQ);
	}

	@Override
	protected HashMap<String, Object> getResponse(
			HashMap<String, Object> request) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		int uid = Integer.valueOf(request.get("uid").toString());
		int pid = Integer.valueOf(request.get("pid").toString());
		if (PermissionsDB.getInstance().hasPermission_onProject(uid, pid,
				Permissions.EDIT)) {
			response.put("authorized", 1);
			@SuppressWarnings("unchecked")
			List<LinkedHashMap<String, Object>> files = (List<LinkedHashMap<String, Object>>) request
					.get("files");
			String owner = UsersDB.getInstance().getUsername(uid);
			String message = request.get("message").toString();
			for (LinkedHashMap<String, Object> f : files) {
				byte[] file_content = f.get("file_content").toString()
						.getBytes();
				String file_rpath = f.get("path").toString();
				int fileStatus = Integer.valueOf(f.get("fileStatus").toString());
				FileSystem fs = new FileSystem();
				if (fileStatus== 2) {
					fs.add_newFile(file_content, file_rpath, owner, pid);
				} else {
					int fid = Integer.valueOf(f.get("id").toString());
					fs.addChange(fid, pid, owner, file_content, file_rpath, message);
				}
			}

		} else {
			response.put("authorized", 0);
		}
		return response;
	}

}
