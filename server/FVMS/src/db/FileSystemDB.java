package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.Logger;
import db.beans.File;
import db.tools.Columns;
import db.tools.Config;
import db.tools.Messages;
import db.tools.StatementExecutor;
import db.tools.Tables;

public class FileSystemDB {
	private DBConnection db = null;
	private static FileSystemDB instance = null;
	private static int nextAvailableFileID = 0;

	private FileSystemDB() {
		db = DBConnection.getInstance();
	}

	public static FileSystemDB getInstance() {
		if (instance == null) {
			instance = new FileSystemDB();
		}
		return instance;
	}

	public String addNewFile(int fid, String userName, String path,
			String hash, String datetime) {
		String result = insertChange(fid, fid, datetime, hash, userName,
				Config.NEWFILE_DEFAULTMESSAGE.toString(), path);
		return result;
	}

	public int getNextAvailableID() {
		if (nextAvailableFileID <= 0) {
			String statement = "SELECT MAX(" + Columns.Changes_ID + ") FROM "
					+ Tables.CHANGES;
			ResultSet rs = db.executeStatement(statement);
			if (rs != null) {
				try {
					if (db.getResultSet().first()) {
						nextAvailableFileID = db.getResultSet().getInt(1) + 1;
					}
				} catch (SQLException e) {
					db.logSQLException("getNextAvailableID", e);
				}
				db.closeStatementsAndResultSets();
			} else {
				Logger.logWARNING("Last CID could not be read.");
			}
		} else {
			nextAvailableFileID += 1;
		}

		return nextAvailableFileID;
	}

	public boolean isFileInDB(String hash) {
		boolean result = false;
		result = db.isValuePresentInTable(hash, Columns.Changes_Hash,
				Tables.CHANGES);
		return result;
	}

	public String addChange(int originalFileID, String datetime, String hash,
			String owner, String message, String path) {
		int cid = getNextAvailableID();
		return insertChange(cid, originalFileID, datetime, hash, owner,
				message, path);
	}

	public int removeFileByHash(String hash) {
		return db.removeRow_byValue(hash, Columns.Changes_Hash, Tables.CHANGES);
	}

	public String getLastChangePath(int fid) {
		String result = null;
		if (db.isValuePresentInTable(fid, Columns.Changes_FID, Tables.CHANGES)) {
			String statement = "SELECT " + Columns.Changes_Path
					+ " FROM " + Tables.CHANGES +" WHERE " + Columns.Changes_FID + "=" + fid + " ORDER BY " + Columns.Changes_ID + " DESC LIMIT 1"; 
			result = getFirstStringResult(statement);
		} else {
			result = Messages.File_doesNotExist.toString();
		}
		return result;
	}

	public int getChangeID(String hash) {
		return db.getID(hash, Columns.Changes_Hash, Columns.Changes_ID,
				Tables.CHANGES);
	}

	public int getOriginalFileID(String hash) {
		return db.getID(hash, Columns.Changes_Hash, Columns.Changes_FID,
				Tables.CHANGES);
	}

	public String getFilePath(String hash) {
		String result = null;
		boolean fileExists = db.isValuePresentInTable(hash,
				Columns.Changes_Hash, Tables.CHANGES);
		if (fileExists) {
			String statement = "SELECT " + Columns.Changes_Path + " FROM "
					+ Tables.CHANGES + " WHERE " + Columns.Changes_Hash + "=\""
					+ hash + "\";";
			result = getFirstStringResult(statement);
		}
		return result;
	}

	public int removeAllFiles() {
		int rows_deleted = 0;

		String statement = "Delete from " + Tables.CHANGES;
		rows_deleted = db.executeUpdate(statement);
		return rows_deleted;
	}

	public int removeFiles_ExceptTrash() {
		int rows_deleted = 0;
		String statement = "DELETE FROM " + Tables.CHANGES + " WHERE (SELECT "
				+ Columns.FileStatus_status + " FROM " + Tables.FILE_STATUS
				+ " WHERE " + Columns.FileStatus_FID + "=" + Tables.CHANGES
				+ "." + Columns.Changes_FID + ") !="
				+ Config.STATUS_MOVEDTOTRASH;
		rows_deleted = db.executeUpdate(statement);
		return rows_deleted;
	}

	public int moveFileToTrash(int id) {
		int filesUpdated = 0;
		String statement = "";
		if (db.isValuePresentInTable(id, Columns.FileStatus_FID,
				Tables.FILE_STATUS)) {
			statement = "UPDATE " + Tables.FILE_STATUS + " SET "
					+ Columns.FileStatus_status + "="
					+ Config.STATUS_MOVEDTOTRASH + " WHERE "
					+ Columns.FileStatus_FID + "=" + id;

			filesUpdated = db.executeUpdate(statement);
		} else {
			String values = id + "," + Config.STATUS_MOVEDTOTRASH;
			db.insertRowIntoTable(values, Tables.FILE_STATUS);
		}
		return filesUpdated;
	}

	public int removeTrashFiles() {
		int removedFiles = 0;
		String statement = "DELETE FROM " + Tables.CHANGES + " WHERE (SELECT "
				+ Columns.FileStatus_status + " FROM " + Tables.FILE_STATUS
				+ " WHERE " + Columns.FileStatus_FID + "=" + Tables.CHANGES
				+ "." + Columns.Changes_FID + ") ="
				+ Config.STATUS_MOVEDTOTRASH;
		removedFiles = db.executeUpdate(statement);
		return removedFiles;
	}

	public String[] getFileVersionsPaths(int id) {
		String statement = "SELECT COUNT(" + Columns.Changes_FID + ") FROM "
				+ Tables.CHANGES;
		db.executeUpdate(statement);
		statement = "SELECT " + Columns.Changes_Path + " FROM "
				+ Tables.CHANGES + " WHERE " + Columns.Changes_FID + "=\"" + id
				+ "\";";
		int filesNo = db.executeUpdate(statement);
		db.closeStatementsAndResultSets();
		String[] result = null;
		if (filesNo > 0) {
			result = new String[filesNo];
			int index = 0;
			try {
				while (db.getResultSet().next()) {
					String path = db.getResultSet().getString(1);
					result[index] = path;
					index++;
				}
			} catch (SQLException e) {
				Logger.logERROR(e);
			}
		}
		return result;
	}

	public boolean isFileInProject(String hash, String file_rpath) {
		boolean fileExists = false;
		if (db.isValuePresentInTable(file_rpath, Columns.ProjectFiles_RPath,
				Tables.PROJECT_FILES)) {
			int idC = db.getID(hash, Columns.Changes_Hash, Columns.Changes_ID,
					Tables.CHANGES);
			int idPF = db.getID(file_rpath, Columns.ProjectFiles_RPath,
					Columns.ProjectFiles_CID, Tables.PROJECT_FILES);
			if (idC == idPF) {
				fileExists = true;
			}
		}
		return fileExists;
	}

	public int getStatus(int id) {
		int status = -1;
		String statement = "SELECT " + Columns.FileStatus_status + " FROM "
				+ Tables.FILE_STATUS + " WHERE " + Columns.FileStatus_FID + "="
				+ id;
		db.executeStatement(statement);
		try {
			db.getResultSet().first();
			status = db.getResultSet().getInt(1);
		} catch (SQLException e) {
			Logger.logERROR(e);
		}
		return status;
	}

	private String getFirstStringResult(String statement) {
		String result = null;
		boolean weHaveResults = false;

		db.executeStatement(statement);
		try {
			weHaveResults = db.getResultSet().first();
			if (weHaveResults) {
				result = db.getResultSet().getString(1);
			} else {
				result = Messages.NoFileFoundWithSpecifiedID.toString();
			}
		} catch (SQLException e) {
			db.logSQLException("getPath", e);
		}

		db.closeStatementsAndResultSets();
		return result;
	}
	private int getFirstIntResult(String statement) {
		int result = -1;
		boolean weHaveResults = false;

		db.executeStatement(statement);
		try {
			weHaveResults = db.getResultSet().first();
			if (weHaveResults) {
				result = db.getResultSet().getInt(1);
			} 
		} catch (SQLException e) {
			db.logSQLException("getPath", e);
		}

		db.closeStatementsAndResultSets();
		return result;
	}

	private String insertChange(int cid, int id, String datetime, String hash,
			String owner, String message, String path) {
		Messages result = null;
		boolean fileIsAlreadySaved = isFileInDB(hash);
		if (fileIsAlreadySaved) {
			result = Messages.File_alreadySaved;
		} else {
			String values = cid + "," + id + ", \"" + datetime + "\",\"" + hash
					+ "\",\"" + owner + "\",\"" + message + "\",\"" + path
					+ "\"";
			int rows_affected = db.insertRowIntoTable(values, Tables.CHANGES);
			if (rows_affected > 0) {
				result = Messages.Change_added;
			} else {
				result = Messages.Change_addingFailed;
			}
		}
		return result.toString();
	}

	public List<File> getFileHistory(int fid, int pid) {
		List<File> files = new ArrayList<File>();
		StatementExecutor statement = new StatementExecutor();
		String statementString = statement
				.select(Columns.Changes_date, Columns.Changes_ID,
						Columns.Changes_message, Columns.Changes_Path,Columns.Changes_owner)
				.from(Tables.CHANGES).where().equals(Columns.Changes_FID, fid)
				.getStringStatement();
		ResultSet rs = db.executeStatement(statementString);
		try {
			while(rs.next()){
				String path = rs.getString(Columns.Changes_Path.toString());
				String date = rs.getString(Columns.Changes_date.toString());
				String message = rs.getString(Columns.Changes_message.toString());
				String owner = rs.getString(Columns.Changes_owner.toString());
				int cid = rs.getInt(Columns.Changes_ID.toString());
				files.add(new File(cid,pid, path, date, message, owner));
			}
		} catch (SQLException e) {
			Logger.logERROR(e);
		}
		return files;

	}

	public int getLastChangeID(int fid) {
		int result = -1;
		if (db.isValuePresentInTable(fid, Columns.Changes_FID, Tables.CHANGES)) {
			String statement = "SELECT " + Columns.Changes_ID
					+ " FROM " + Tables.CHANGES +" WHERE " + Columns.Changes_FID + "=" + fid + " ORDER BY " + Columns.Changes_ID + " DESC LIMIT 1"; 
			result = getFirstIntResult(statement);
		} 
		return result;
	}

	public String getFilePath(int cid) {
		String statement = "SELECT " + Columns.Changes_Path + " FROM " + Tables.CHANGES + " WHERE " + Columns.Changes_ID + "=" + cid;
		ResultSet rs = db.executeStatement(statement);
		try {
			if(rs.first()){
				return rs.getString(Columns.Changes_Path.toString());
			}
		} catch (SQLException e) {
			Logger.logERROR(e, statement);
		}
		return null;
	}

	public String getHash(int cid) {
		String statement = "SELECT " + Columns.Changes_Hash + " FROM " + Tables.CHANGES + " WHERE " + Columns.Changes_ID + "=" + cid;
		ResultSet rs = db.executeStatement(statement);
		try {
			if(rs.first()){
				return rs.getString(Columns.Changes_Hash.toString());
			}
		} catch (SQLException e) {
			Logger.logERROR(e, statement);
		}
		return null;
	}

}
