package connection.tasks;

import java.util.HashMap;
import java.util.List;

import com.rabbitmq.client.QueueingConsumer;

import db.FileSystemDB;
import db.beans.File;

public class GetHistoryTask extends RequestTask {

	public GetHistoryTask(QueueingConsumer loginQ) {
		super(loginQ);
	}

	@Override
	protected HashMap<String, Object> getResponse(
			HashMap<String, Object> request) {
		HashMap<String, Object> responseMap = new HashMap<String, Object>();
		int fid = Integer.valueOf(request.get("fid").toString());
		int pid = Integer.valueOf(request.get("pid").toString());
		List<File> files = FileSystemDB.getInstance().getFileHistory(fid, pid);
		responseMap.put("changes", files);
		return responseMap;
	}

}
