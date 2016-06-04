package db;

import java.sql.SQLException;

import utils.Logger;
import versioning.tools.Messages;
import db.tools.Columns;
import db.tools.Permissions;
import db.tools.Tables;

public class PermissionsDB {
	DBConnection db = null;
	public static PermissionsDB instance = null;

	private PermissionsDB() {
		db = DBConnection.getInstance();
	}

	public static PermissionsDB getInstance() {
		if (instance == null) {
			instance = new PermissionsDB();
		}
		return instance;
	}

	public String setPermission_onProject(int uid, int pid,
			Permissions permission) {
		if (hasPermission_onProject(uid, pid, Permissions.READ)) {
			return updatePermission(uid, pid, permission,
					Columns.PPermissions_user, Columns.PPermissions_project,
					Columns.PPermissions_rights, Tables.PROJECT_PERMISSIONS,
					true);
		} else {
			if (!hasPermission_onProject(uid, pid, permission)) {
				String values = uid + "," + pid + ",\"" + permission + "\"";
				db.insertRowIntoTable(values, Tables.PROJECT_PERMISSIONS);
				return Messages.Permission_set.toString();
			} else {
				return Messages.Permission_alreadySet.toString();
			}
		}
	}

	public String unsetPermission_onProject(int uid, int fid,
			Permissions permission) {
		return updatePermission(uid, fid, permission,
				Columns.PPermissions_user, Columns.PPermissions_project,
				Columns.PPermissions_rights, Tables.PROJECT_PERMISSIONS, false);
	}

	public boolean hasPermission_onProject(int uid, int pid,
			Permissions permission) {
		String permissions = getPermission(uid, pid, Columns.PPermissions_user,
				Columns.PPermissions_project, Columns.PPermissions_rights,
				Tables.PROJECT_PERMISSIONS);
		if (permissions.isEmpty()) {
			return false;
		} else {
			return permissions.contains(permissions.toString());
		}
	}

	private String getPermission(int uid, int fid, Columns uidCol,
			Columns fidCol, Columns rightCol, Tables table) {
		String permission = "";
		String statement = "SELECT " + rightCol + " FROM " + table + " WHERE "
				+ uidCol + "=" + uid + " AND " + fidCol + "=" + fid;
		db.executeStatement(statement);
		try {
			if (db.getResultSet().first()) {
				permission = db.getResultSet().getString(1);
				db.closeStatementsAndResultSets();
			}
		} catch (SQLException e) {
			Logger.logERROR(e);
		}
		return permission;
	}

	private String updatePermission(int uid, int fid, Permissions permission,
			Columns uidCol, Columns fidCol, Columns rightCol, Tables table,
			boolean flag) {
		String result = "";
		String permissions = getPermission(uid, fid, uidCol, fidCol, rightCol,
				table);
		String newPermission = getNewPermission(permissions, permission, flag);
		String statement = "UPDATE " + table + " SET " + rightCol + "=\""
				+ newPermission + "\" WHERE " + uidCol + "=" + uid + " AND "
				+ fidCol + "=" + fid;

		int rows_affected = db.executeUpdate(statement);
		if (rows_affected > 0) {
			result = Messages.Permission_set.toString();
		} else {
			result = Messages.Permission_settingFailed.toString();
		}
		return result;
	}

	private String getNewPermission(String oldPermission, Permissions toAdd,
			boolean flag) {
		String perm = "";
		if (flag) {
			perm = oldPermission.concat(toAdd.toString());
		} else {
			perm = oldPermission.replace(toAdd.toString(), "");
		}
		return perm;
	}
}
