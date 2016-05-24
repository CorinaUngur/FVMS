package versioning;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import versioning.tools.Messages;
import config.Tools;
import db.FileSystemDB;

public class FileSystem {
	FileSystemDB fsdb = FileSystemDB.getInstance();

	public String add_newFile(byte[] file_content, String file_name,
			String email) {
		Messages result = null;
		String file_path = null;
		String hash = Tools.hashContent(file_content);
		boolean fileExists = fsdb.isFileInDB(hash);
		if (fileExists) {
			result = Messages.FileAlreadyExists;
		} else {
			int fid = fsdb.getNextAvailableID();
			String folder_path = createFolder(fid);
			if (folder_path != null) {
				file_path = createFile(fid, file_name, file_content);
				if(file_path != null){
					hash = Tools.hashContent(file_content);
					String datetime = LocalDateTime.now().toString();
					String dbResult = fsdb.addNewFile(fid, email, file_path, hash, datetime);
					Logger.getGlobal().log(Level.INFO, dbResult);
					result = Messages.File_added;
				}
			} else {
				result = Messages.Folder_creationFailed;
			}
		}
		return result.toString();
	}

	

	public String addChange(int fid, String email, byte[] file_content,
			String file_name, String message) {
		Messages result = null;
		String file_path= createFile(fid, file_name, file_content);
		if (file_path != null) {
			String hash = Tools.hashContent(file_content);
			String datetime = LocalDateTime.now().toString();
			String dbResult = fsdb.addChange(fid, datetime, hash, email, message, file_path);
			Logger.getGlobal().log(Level.INFO, dbResult);
			result = Messages.File_added;
		}
		return result.toString();
	}

	public byte[] getLastVersion(int fid) {
		byte[] file_content = null;
		String path = fsdb.getLastChangePath(fid);
		try {
			FileInputStream fis = new FileInputStream(path);
			fis.read(file_content);
			fis.close();
		} catch (IOException e) {
			Logger.getGlobal().log(Level.FINE, e.getMessage());
		}
		return file_content;
	}

	private String createFolder(int fid) {
		String folder_path = null;

		String folder_name = fid + "";
		folder_path = Config.ROOT_FOLDER + folder_name;
		File folder = new File(folder_path);
		if (folder.exists()) {
			Logger.getGlobal().log(Level.INFO,
					"Folder '" + folder_path + "' already exists");
		} else {
			boolean folder_created = folder.mkdir();
			if (!folder_created) {
				folder_path = Messages.Folder_creationFailed.toString();
			}

		}
		return folder_path;
	}
	private String createFile(int fid, String file_name, byte[] file_content) {
		file_name = Tools.getDateTimeNow() + " " + file_name;
		String file_path = Config.ROOT_FOLDER.toString() + fid + "/"
				+ file_name;
		FileOutputStream fos;
		boolean file_created = false;
		try {
			File file = new File(file_path);
			file_created = file.createNewFile();
			if (file_created) {
				
				fos = new FileOutputStream(file);
				fos.write(file_content);
				fos.flush();
				fos.close();

				Logger.getGlobal().log(Level.INFO,
						Messages.File_added.toString());
			} else {

				Logger.getGlobal().log(Level.INFO,
						Messages.File_addingFailed.toString());
			}
		} catch (IOException e) {
			Logger.getGlobal().log(Level.FINE, e.getMessage());
		}

		return file_created ? file_path : null;
	}

}
