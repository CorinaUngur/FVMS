package versioning;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import utils.Logger;
import utils.Tools;
import versioning.tools.Messages;
import config.Settings;
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
				if (file_path != null) {
					hash = Tools.hashContent(file_content);
					String datetime = LocalDateTime.now().toString();
					String dbResult = fsdb.addNewFile(fid, email, file_path,
							hash, datetime);
					Logger.logINFO(dbResult);
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
		String file_path = createFile(fid, file_name, file_content);
		if (file_path != null) {
			String hash = Tools.hashContent(file_content);
			String datetime = LocalDateTime.now().toString();
			String dbResult = fsdb.addChange(fid, datetime, hash, email,
					message, file_path);
			Logger.logINFO(dbResult);
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
			Logger.logERROR(e);
		}
		return file_content;
	}

	public String moveFileToTrash(int id) {
		String result = null;
		String path = fsdb.getFileFolder(id);
		File folder = new File(path);
		int filesMoved = 0;
		if (folder.isDirectory()) {
			File folderInTrash = new File(Settings.TRASH_FOLDER
					+ folder.getName());
			boolean folderCreated = folderInTrash.mkdir();
			if (folderCreated) {
				for (String file_name : folder.list()) {
					String file_path = folder.getPath() + "/" + file_name;
					File file = new File(file_path);
					boolean fileMoved = file.renameTo(new File(folderInTrash
							.getPath() + "/" + file_name));
					filesMoved += fileMoved ? 1 : 0;
				}
				Logger.logINFO(filesMoved + " out of " + folder.list().length
						+ " moved to trash for file id: " + id);

				fsdb.moveFileToTrash(id);
				result = Messages.File_removed.toString();

			} else {
				Logger.logWARNING("Folder was not created in trash. Moving files will not be performed");
				result = Messages.File_removingFailed.toString();
			}
		}
		return result;
	}

	public String removeFile(int id) {
		String result = null;
		String path = fsdb.getFileFolder(id);
		File folder = new File(path);
		int filesMoved = 0;
		int totalFilesNo = folder.list().length;
		if (folder.isDirectory()) {
			filesMoved += Tools.removeAllFiles(folder.getPath());
			folder.delete();
			Logger.logINFO(filesMoved + " out of " + totalFilesNo
					+ " were deleted for file id: " + id);
			result = Messages.File_removed.toString();
		} else {
			result = Messages.File_removingFailed.toString();
		}
		return result;
	}

	public String emptyTrash() {
		Messages result = null;
		Tools.removeAllFiles(Settings.TRASH_FOLDER);
		if ((new File(Settings.TRASH_FOLDER)).list().length == 0) {
			result = Messages.Trash_empty;
		} else {
			result = Messages.Trash_notEmpty;
		}
		return result.toString();
	}

	public String removeFilesFromRoot() {
		String result = null;
		File rootFolder = new File(Settings.FS_ROOTFOLDER);
		File trash_folder = new File(Settings.TRASH_FOLDER);
		int filesRemoved = 0;
		int totalNumberFiles = 0;
		for (File file : rootFolder.listFiles()) {
			if (file.isDirectory()) {
				if (!file.getPath().equals(trash_folder.getPath())) {
					filesRemoved += Tools.removeAllFiles(file.getPath());
					totalNumberFiles += file.list().length;
					file.delete();
				}
			} else {
				if (file.delete()) {
					filesRemoved += 1;
				}
			}
		}
		result = filesRemoved + " out of " + totalNumberFiles
				+ " removed from root folder";
		return result;
	}

	private String createFolder(int fid) {
		String folder_path = null;

		String folder_name = fid + "";
		folder_path = Settings.FS_ROOTFOLDER + folder_name;
		File folder = new File(folder_path);
		if (folder.exists()) {
			Logger.logINFO("Folder '" + folder_path + "' already exists");
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
		String file_path = Settings.FS_ROOTFOLDER + fid + "/" + file_name;
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

				Logger.logINFO(Messages.File_added.toString());
			} else {

				Logger.logINFO(Messages.File_addingFailed.toString());
			}
		} catch (IOException e) {
			Logger.logERROR(e);
		}

		return file_created ? file_path : null;
	}

}
