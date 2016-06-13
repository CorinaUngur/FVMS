package connection.tasks;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import versioning.FileSystem;
import versioning.tools.FileStatus;

import com.rabbitmq.client.QueueingConsumer;

import connection.Connector;
import db.FileSystemDB;
import db.PermissionsDB;
import db.ProjectsDB;
import db.UsersDB;
import db.tools.Permissions;

public class UpdateFilesTask extends RequestTask {

	public UpdateFilesTask(QueueingConsumer queue) {
		super(queue);
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
			HashMap<String, String> queues = new HashMap<String, String>();
			int countNewFiles = 0;
			FileSystem fs = new FileSystem();
			for (LinkedHashMap<String, Object> f : files) {
				int fileStatus = Integer
						.valueOf(f.get("fileStatus").toString());
				if (fileStatus != FileStatus.DELETED.get()) {
					f.put("owner", owner);
					f.put("pid", pid);
					f.put("message", message);
					String file_rpath = f.get("file_rpath").toString();


					String queue;
					if(fileStatus == FileStatus.CHANGED.get()) {
						String hash = f.get("hash").toString();
						if (FileSystemDB.getInstance().isFileInDB(hash)) {
							fs.add_newFile(null, file_rpath, owner, pid);
						} else {
							queue = "new" + countNewFiles + "_" + uid + "_"
									+ pid;
							queues.put(file_rpath, queue);
							Connector.getInstance().startAddFileTask(queue, f);
						}
					}else if(fileStatus == FileStatus.NEW.get()) {
						int fid = Integer.valueOf(f.get("id").toString());
						queue = fid + "_" + uid + "_" + pid;
						queues.put(file_rpath, queue);
						Connector.getInstance().startAddFileTask(queue, f);
						break;
					}
					
				} else {
					int fid = Integer.valueOf(f.get("fid").toString());
					fs.removeFile(fid);
					int cid= FileSystemDB.getInstance().getLastChangeID(fid);
					ProjectsDB.getInstance().removeFileFromProject(pid, cid);
				}
			}
			response.put("queues", queues);
		} else {
			response.put("authorized", 0);
		}
		return response;
	}
}
