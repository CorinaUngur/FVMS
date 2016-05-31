package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.Logger;
import db.beans.File;
import db.beans.Project;
import db.tools.Columns;
import db.tools.Messages;
import db.tools.Permissions;
import db.tools.Tables;

public class ProjectsDB {
	private static ProjectsDB instance = null;
	DBConnection db = null;
	FileSystemDB fsdb = null;
	PermissionsDB perdb = null;

	private ProjectsDB() {
		db = DBConnection.getInstance();
		fsdb = FileSystemDB.getInstance();
		perdb = PermissionsDB.getInstance();
	}

	public static ProjectsDB getInstance() {
		if (instance == null) {
			instance = new ProjectsDB();
		}
		return instance;
	}

	public String addNewProject(String name, String author) {
		String result = "";
		String values = "default,\"" + name + "\",\"" + author + "\"";
		int insertedRows = db.insertRowIntoTable(values, Tables.PROJECTS);
		if (insertedRows > 0) {
			result = Messages.Project_added.toString();
			int pid = db.getID(name, Columns.Projects_Name,
					Columns.Projects_ID, Tables.PROJECTS);
			int uid = UsersDB.getInstance().getUID_byUsername(author);
			perdb.setPermission_onProject(uid, pid, Permissions.ALL);
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

	public int getProjectID(String project_name) {
		return db.getID(project_name, Columns.Projects_Name,
				Columns.Projects_ID, Tables.PROJECTS);
	}

	public int removeAllFilesAndProjects() {
		String statement = "DELETE FROM " + Tables.PROJECT_FILES;
		int removedFiles = db.executeUpdate(statement);
		statement = "DELETE FROM " + Tables.PROJECTS;
		removedFiles += db.executeUpdate(statement);
		return removedFiles;
	}

	public List<Project> getProjects(int uid) {
		List<Project> projects = new ArrayList<Project>();
		String statement = "SELECT " + Columns.PPermissions_project + " FROM "
				+ Tables.PROJECT_PERMISSIONS + " WHERE "
				+ Columns.PPermissions_user + "=" + uid + " AND "
				+ Columns.PPermissions_rights + " LIKE '%" + Permissions.READ
				+ "%'";
		ResultSet rs = db.executeStatement(statement);

		try {
			while (rs.next()) {
				int pid = rs.getInt(1);
				statement = "SELECT " + Columns.Projects_Name + " FROM "
						+ Tables.PROJECTS + " WHERE " + Columns.Projects_ID
						+ "=" + pid;
				ResultSet nrs = db.executeStatement(statement);
				nrs.first();
				String name = nrs.getString(Columns.Projects_Name.toString());
				statement = "SELECT " + Columns.ProjectFiles_RPath + ","
						+ Columns.ProjectFiles_CID + " FROM "
						+ Tables.PROJECT_FILES + " WHERE "
						+ Columns.ProjectFiles_PID + "=" + pid;
				ResultSet prs = db.executeStatement(statement);
				ArrayList<File> files = new ArrayList<File>();
				while (prs.next()) {
					String path = prs.getString(Columns.ProjectFiles_RPath
							.toString());
					int currentFile_id = prs.getInt(Columns.ProjectFiles_CID
							.toString());
					statement = "SELECT " + Columns.Changes_date + ","
							+ Columns.Changes_message + ","
							+ Columns.Changes_owner + " FROM " + Tables.CHANGES
							+ " WHERE " + Columns.Changes_ID + "="
							+ currentFile_id;
					ResultSet frs = db.executeStatement(statement);
					frs.first();
					String lastModified = frs.getString(Columns.Changes_date
							.toString());
					String comment = frs.getString(Columns.Changes_message
							.toString());
					String ownerName = frs.getString(Columns.Changes_owner.toString());
					
					files.add(new File(currentFile_id, path, lastModified,
							comment, ownerName));
				}

				projects.add(new Project(name, files));

			}

		} catch (SQLException e) {
			Logger.logERROR(e);
		}
		db.closeStatementsAndResultSets();
		return projects;
	}

	public void clearProjects() {
		db.removeAllFromTable(Tables.PROJECT_FILES);
		db.removeAllFromTable(Tables.PROJECT_PERMISSIONS);
		db.removeAllFromTable(Tables.PROJECTS);
	}

}
