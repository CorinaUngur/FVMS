package connection.tasks;

import java.io.File;
import java.util.HashMap;

import connection.tools.Config;
import db.FileSystemDB;
import db.ProjectsDB;

public class DownloadOnThreadsTask {

	public DownloadOnThreadsTask() {
	}

	protected HashMap<String, Object> execute(int uid, int fid, int pid) {

		int cid = ProjectsDB.getInstance().getChangeID(fid, pid);
		String path = FileSystemDB.getInstance().getFilePath(cid);
		File file = new File(path);
		long file_size = file.length();
		long noOfBlocks = file_size / Config.FileBlockSize.getInt();
		if (file_size % noOfBlocks != 0) {
			noOfBlocks++;
		}
		String fileQueue = uid + "_" + fid + "_" + pid;
		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("totalSize", file_size);
		response.put("noOfBlocks", noOfBlocks);
		response.put("queue", fileQueue);
		response.put("hash", FileSystemDB.getInstance().getHash(cid));
		return response;

	}

}
