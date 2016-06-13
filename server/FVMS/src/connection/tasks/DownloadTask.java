package connection.tasks;

import java.util.HashMap;
import java.util.List;

import utils.JSONManipulator;

import com.rabbitmq.client.QueueingConsumer;

import config.Settings;
import connection.Connector;
import connection.tools.SendFileDetails;
import db.FileSystemDB;
import db.PermissionsDB;
import db.ProjectsDB;
import db.tools.Permissions;

public class DownloadTask extends NoticeTask {

	public DownloadTask(QueueingConsumer queue) {
		super(queue);
	}

	@Override
	protected void executeRequest(HashMap<String, Object> request) {
		int uid = Integer.valueOf(request.get("uid").toString());
		int pid = Integer.valueOf(request.get("pid").toString());
		if (PermissionsDB.getInstance().hasPermission_onProject(uid, pid,
				Permissions.READ)) {
			List<Integer> fileIDs = JSONManipulator.deserializeList(request
					.get("fids").toString());
			for (int id : fileIDs) {
				HashMap<String, Object> response = new DownloadOnThreadsTask().execute(uid, id, pid);
				Connector.getInstance().sentMessage(
						Settings.Conn_QFILEDOWNLOAD, response);
				int cid = ProjectsDB.getInstance().getChangeID(id, pid);
				String path = FileSystemDB.getInstance().getFilePath(cid);
				Connector.getInstance().startSedingFileTask(
						new SendFileDetails(path, response.get("queue").toString()));
			}
			
		}

	}

}
