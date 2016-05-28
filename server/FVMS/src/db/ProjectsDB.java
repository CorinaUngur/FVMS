package db;

import db.tools.Messages;
import db.tools.Columns;
import db.tools.Tables;

public class ProjectsDB {
	private static ProjectsDB instance = null;
	DBConnection db = null;
	FileSystemDB fsdb = null;

	private ProjectsDB() {
		db = DBConnection.getInstance();
		fsdb = FileSystemDB.getInstance();
	}

	public static ProjectsDB getInstance() {
		if (instance == null) {
			instance = new ProjectsDB();
		}
		return instance;
	}

	public String addNewProject(String name, int authorID) {
		String result = "";
		String values = "default,\"" + name + "\"," + authorID;
		int insertedRows = db.insertRowIntoTable(values, Tables.PROJECTS);
		if (insertedRows > 0) {
			result = Messages.Project_added.toString();
		} else {
			result = Messages.Project_addingFailed.toString();
		}
		return result;
	}

	public String removeProject(int id) {
		String result;
		int removedRows = db.removeRow_byValue(id, Columns.Projects_ID,
				Tables.PROJECTS);
		if (removedRows > 0) {
			result = Messages.Project_removed.toString();
		} else {
			result = Messages.Project_removingFailed.toString();
		}
		return result;
	}

	public String changeProjectName(int id, String newName) {
		String result = "";
		int rows_updated = db
				.setStringValue_byID(newName, Columns.Projects_Name, id,
						Columns.Projects_ID, Tables.PROJECTS);
		if (rows_updated > 0) {
			result = Messages.Project_nameUpdated.toString();
		} else {
			result = Messages.Project_renamingFailed.toString();
		}
		return result;
	}

	public String addFile_toProject(int pid, int cid, String relativePath) {
		String result = null;
		if (db.isValuePresentInTable(pid, Columns.ProjectFiles_PID,
				Tables.PROJECTS)) {
			if (db.isValuePresentInTable(cid, Columns.Changes_ID,
					Tables.CHANGES)) {
				String values = pid + "," + cid + ",\"" + relativePath + "\"";
				int filesInserted = db.insertRowIntoTable(values,
						Tables.PROJECT_FILES);
				if (filesInserted > 0) {
					result = Messages.File_addedToProject.toString();
				} else {
					result = Messages.File_addingToPRojectFailed.toString();
				}
			} else {
				result = Messages.File_doesNotExist.toString();
			}
		} else {
			result = Messages.Project_doesNotExist.toString();
		}
		return result;
	}

	public String removeFileFromProject(int pid, int cid) {
		String result = null;
		String statement = "DELETE FROM " + Tables.PROJECT_FILES + " WHERE "
				+ Columns.ProjectFiles_PID + "=" + pid + " AND "
				+ Columns.ProjectFiles_CID + "=" + cid;
		int filesRemoved = db.executeUpdate(statement);
		if (filesRemoved > 0) {
			result = Messages.File_removedFromProject.toString();
		} else {
			result = Messages.File_removingFromProjectFailed.toString();
		}
		return result;
	}

	public String changeRelativePath(int pid, String newPath) {
		int rows_updated = db.setStringValue_byID(newPath,
				Columns.Projects_Path, pid, Columns.Projects_ID,
				Tables.PROJECTS);
		return rows_updated > 0 ? Messages.Project_pathUpdated.toString()
				: Messages.Project_pathUpdatingFailed.toString();
	}

	public int getProjectID(String project_name) {
		return db.getID(project_name, Columns.Projects_Name, Columns.Projects_ID, Tables.PROJECTS);
	}
	
	public int removeAllFilesAndProjects(){
		String statement = "DELETE FROM " + Tables.PROJECT_FILES;
		int removedFiles = db.executeUpdate(statement);
		statement = "DELETE FROM " + Tables.PROJECTS;
		removedFiles += db.executeUpdate(statement);
		return removedFiles;
	}
}
