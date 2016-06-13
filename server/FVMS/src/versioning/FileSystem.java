package versioning;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.commons.codec.digest.DigestUtils;

import utils.Logger;
import utils.Tools;
import versioning.tools.Messages;
import config.Settings;
import db.FileSystemDB;
import db.ProjectsDB;

public class FileSystem {
	private static final String pathSeparator = "/";
	private FileSystemDB fsdb = FileSystemDB.getInstance();
	private ProjectsDB pdb = ProjectsDB.getInstance();

	public String add_newFile(File file, String file_rpath, String owner,
			int pid) {
		String result = null;
		String file_path = null;
		String hash;
		hash = getFileHash(file);
		boolean fileExistsInFS = fsdb.isFileInDB(hash);
		if (!fileExistsInFS) {
			int fid = fsdb.getNextAvailableID();
			String folder_path = createFolder(fid);
			if (folder_path != null) {
				file_path = createFile(fid, file_rpath, file);
				if (file_path != null) {
					result = addnewFile_toDB(hash, file_rpath, owner, pid,
							file_path, fid);
				}
			} else {
				result = Messages.Folder_creationFailed.toString();
			}
		} else {
			result = addFileToProject(file_rpath, pid, hash);
		}
		return result;
	}

	private String getFileHash(File file) {
		String hash = "";
		try {
			FileInputStream fis = new FileInputStream(file);
			hash = DigestUtils.md5Hex(fis);
			fis.close();
		} catch (IOException e) {
			Logger.logERROR(e);
		}
		return hash;
	}

	private String addFileToProject(String file_rpath, int pid, String hash) {
		String result;
		boolean fileExistsInProjects = fsdb.isFileInProject(hash, file_rpath);
		if (!fileExistsInProjects) {
			int fid = fsdb.getChangeID(hash);
			pdb.addFile_toProject(pid, fid, file_rpath);
			result = Messages.File_boundToProject.toString();
		} else {
			result = Messages.FileAlreadyExists.toString();
		}
		return result;
	}

	private String addnewFile_toDB(String hash, String file_rpath,
			String owner, int pid, String file_path, int fid) {
		String result;
		String datetime = LocalDateTime.now().toString();
		String dbResult = fsdb
				.addNewFile(fid, owner, file_path, hash, datetime);
		Logger.logINFO(dbResult);
		Logger.logINFO(pdb.addFile_toProject(pid, fid, file_rpath));
		result = Messages.File_added.toString();
		Logger.logINFO(result);
		return result;
	}

	public String addChange(int fid, int pid, String owner, File file,
			String file_rpath, String message) {
		Messages result = null;
		String file_name = getNameFromPath(file_rpath);
		String file_path = createFile(fid, file_name, file);
		if (file_path != null) {
			int oldCid = fsdb.getLastChangeID(fid);
			String hash = getFileHash(file);
			String datetime = LocalDateTime.now().toString();
			String dbResult = fsdb.addChange(fid, datetime, hash, owner,
					message, file_path);
			Logger.logINFO(dbResult);
			int cid = fsdb.getChangeID(hash);
			dbResult = pdb.changeVersionToProject(pid, cid, oldCid);
			Logger.logINFO(dbResult);
			result = Messages.File_added;
		}
		return result.toString();
	}

	public byte[] getLastVersion(int fid) {
		String path = fsdb.getLastChangePath(fid);
		byte[] file_content = null;
		try {
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			file_content = new byte[(int) file.length()];
			fis.read(file_content);
			fis.close();
		} catch (IOException e) {
			Logger.logERROR(e);
		}
		return file_content;
	}

	public String moveAllFilesToTrash() {
		return moveAllFiles(new File(Settings.FS_ROOTFOLDER), new File(
				Settings.TRASH_FOLDER));
	}

	public String moveFileToTrash(int id) {
		String result = null;
		File rootFolder = new File(Settings.FS_ROOTFOLDER);
		File trashFolder = new File(Settings.TRASH_FOLDER);
		String path = getFileFolder(rootFolder, id);
		File folder = new File(path);
		result = moveFile(folder, rootFolder, trashFolder);
		fsdb.moveFileToTrash(id);
		return result;
	}

	public String removeFile(int id) {
		String result = null;
		String path = getFileFolder(new File(Settings.FS_ROOTFOLDER), id);
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
			fsdb.removeTrashFiles();
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

	public String retrieveFilesFromTrash() {
		String result = "";
		File rootFolder = new File(Settings.FS_ROOTFOLDER);
		File trashFolder = new File(Settings.TRASH_FOLDER);
		moveAllFiles(trashFolder, rootFolder);
		return result;
	}

	public String getFileFolder(File source, int id) {
		return source.getPath() + pathSeparator + id;
	}

	private String moveAllFiles(File source, File dest) {
		String result = "";
		File trashFolder = new File(Settings.TRASH_FOLDER);
		for (File f : source.listFiles()) {
			if (!f.equals(trashFolder)) {
				moveFile(f, source, dest);
			}
		}
		return result;
	}

	private String moveFile(File folder, File source, File dest) {
		String result = null;
		int filesMoved = 0;
		int toalFilesNo = folder.list().length;
		if (folder.isDirectory()) {
			dest.mkdir();
			if (dest.exists()) {
				filesMoved = moveFolder(folder, source, dest);
				Logger.logINFO(filesMoved + " out of " + toalFilesNo
						+ " moved from " + source.getPath() + " to "
						+ dest.getPath() + " for file id: "
						+ Integer.valueOf(folder.getName()));

				result = Messages.File_removed.toString();
			} else {
				Logger.logWARNING("Folder was not created in trash. Moving files will not be performed");
				result = Messages.File_removingFailed.toString();
			}
			folder.delete();
		}
		return result;
	}

	private int moveFolder(File folder, File source, File dest) {
		int filesMoved = 0;
		File fileFolderInDest = new File(dest.getPath() + pathSeparator
				+ folder.getName());
		fileFolderInDest.mkdir();
		if (fileFolderInDest.exists()) {
			for (String file_name : folder.list()) {
				filesMoved += moveFile(folder, fileFolderInDest, file_name);
			}
		}
		return filesMoved;
	}

	private int moveFile(File folder, File fileFolderInDest, String file_name) {
		String file_path = folder.getPath() + pathSeparator + file_name;
		File file = new File(file_path);
		boolean fileMoved = file.renameTo(new File(fileFolderInDest.getPath()
				+ pathSeparator + file_name));
		return fileMoved ? 1 : 0;
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

	private String createFile(int fid, String file_rPath, File file) {
		String file_name = "";
		file_name = getNameFromPath(file_rPath);

		file_name = Tools.getDateTimeNow() + " " + file_name;
		String file_path = Settings.FS_ROOTFOLDER + fid + pathSeparator
				+ file_name;
		boolean file_created = false;
		file_created = file.renameTo(new File(file_path));
		Logger.logINFO(Messages.File_added.toString());
		return file_created ? file_path : null;
	}

	public String getNameFromPath(String path) {
		String[] names = path.replace('\\', '/').split("/");
		return names[names.length - 1];
	}

}
