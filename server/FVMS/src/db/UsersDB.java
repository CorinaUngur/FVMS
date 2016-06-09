package db;

import java.sql.ResultSet;
import java.sql.SQLException;

import utils.Logger;
import utils.Tools;
import config.Settings;
import db.tools.Columns;
import db.tools.Messages;
import db.tools.StatementExecutor;
import db.tools.Tables;

public class UsersDB {
	private static UsersDB instance = null;
	private DBConnection db = null;

	private UsersDB() {
		db = DBConnection.getInstance();
	}

	public static UsersDB getInstance() {
		if (instance == null)
			instance = new UsersDB();
		return instance;
	}

	public void printAllUsers() {
		String statement = "SELECT * FROM " + Tables.USERS;
		db.executeStatement(statement);
		try {
			while (db.getResultSet().next()) {
				System.out.println("user: "
						+ db.getResultSet().getString("user_name").toString()
						+ "email: "
						+ db.getResultSet().getString("email").toString());
			}
		} catch (SQLException e) {
			db.logSQLException("printAllUsers", e);
		}

		db.closeStatementsAndResultSets();
	}

	public boolean doesEmailExist(String email) {
		return db.isValuePresentInTable(email, Columns.USERS_email,
				Tables.USERS);
	}

	public boolean doesUsernameExist(String username) {
		return db.isValuePresentInTable(username, Columns.USERS_username,
				Tables.USERS);
	}

	public String insertUser(String username, String password, String email) {
		String result_message = "";
		boolean username_ok = !db.isValuePresentInTable(username,
				Columns.USERS_username, Tables.USERS);
		boolean email_ok = db.isEmailValid(email);
		if (username_ok && email_ok) {
			password = Tools.hashString(password);
			String statement = "insert into users values(default,\"" + email
					+ "\",\"" + username + "\",\"" + password + "\");";
			db.executeUpdate(statement);
			result_message = Messages.User_inserted.toString();
		} else {
			if (!username_ok) {
				result_message += Messages.Username_used.toString();
			}
			if (!email_ok) {
				result_message += Messages.Email_invalid.toString();
			}
		}
		return result_message;
	}

	public String removeUser_byUsername(String user_name) {
		return removeUser(user_name, Columns.USERS_username);
	}

	public String removeUser_byEmail(String email) {
		return removeUser(email, Columns.USERS_email);
	}

	public String checkUser_byUsername(String user_name, String password) {
		return checkUser(user_name, password, Columns.USERS_username);
	}

	public String checkUser_byEmail(String email, String password) {
		return checkUser(email, password, Columns.USERS_email);
	}

	public String insertTeam(String team) {
		String result = "";
		if (team.trim().isEmpty()) {
			result = Messages.Value_Empty.toString();
		} else {
			if (db.isValuePresentInTable(team, Columns.TEAMS_name, Tables.TEAMS)) {
				result = Messages.Team_alreadyExists.toString();
			} else {
				String statement = "INSERT INTO TEAMS VALUES(default, \""
						+ team + "\");";
				int rows_affected = db.executeUpdate(statement);
				if (rows_affected > 0) {
					result = Messages.Team_inserted.toString();
				} else {
					result = Messages.Team_insertionFailed.toString();
				}
				db.closeStatementsAndResultSets();
			}
		}
		return result;
	}

	public String removeTeam(String team) {
		String result = "";
		if (team.trim().isEmpty()) {
			result = Messages.Value_Empty.toString();
		} else {
			if (!db.isValuePresentInTable(team, Columns.TEAMS_name,
					Tables.TEAMS)) {
				result = Messages.Team_notExists.toString();
			} else {
				int rows_affected = db.removeRow_byValue(team,
						Columns.TEAMS_name, Tables.TEAMS);
				if (rows_affected > 0) {
					result = Messages.Team_removed.toString();
				} else {
					result = Messages.Team_removalFailed.toString();
				}
			}
		}
		return result;
	}

	public String addUserToTeam_byUsername(String user, String team) {
		return addUser_toTeam(user, Columns.USERS_username, team);
	}

	public String addUserToTeam_byEmail(String email, String team) {
		return addUser_toTeam(email, Columns.USERS_email, team);
	}

	public String addTeamLider_byUsername(String user, String team) {
		return addTeamLider(user, team, Columns.USERS_username);
	}

	public String addTeamLider_byEmail(String email, String team) {
		return addTeamLider(email, team, Columns.USERS_email);
	}

	public String removeUserFromTeam_byUsername(String user, String team) {
		return removeUserOrLiderFromTeam(user, team, Columns.USERS_username,
				Tables.TEAM_USERS);
	}

	public String removeUserFromTeam_byEmail(String email, String team) {
		return removeUserOrLiderFromTeam(email, team, Columns.USERS_email,
				Tables.TEAM_USERS);
	}

	public String removeLiderFromTeam_byUsername(String user, String team) {
		return removeUserOrLiderFromTeam(user, team, Columns.USERS_username,
				Tables.TEAM_LEADERS);
	}

	public String removeLiderFromTeam_byEmail(String email, String team) {
		return removeUserOrLiderFromTeam(email, team, Columns.USERS_email,
				Tables.TEAM_LEADERS);
	}

	public String isUserInTeam_byUsername(String user, String team) {
		return isUserInORLiderOfTeam(user, team, Columns.USERS_username,
				Tables.TEAM_USERS).toString();
	}

	public String isUserInTeam_byEmail(String email, String team) {
		return isUserInORLiderOfTeam(email, team, Columns.USERS_email,
				Tables.TEAM_USERS).toString();
	}

	public String isUserLiderOfTeam_byUsername(String user, String team) {
		return isUserInORLiderOfTeam(user, team, Columns.USERS_username,
				Tables.TEAM_LEADERS).toString();
	}

	public String isUserLiderOfTeam_byEmail(String email, String team) {
		return isUserInORLiderOfTeam(email, team, Columns.USERS_email,
				Tables.TEAM_LEADERS).toString();
	}

	public int getUID_byEmail(String userEmail) {
		return db.getID(userEmail, Columns.USERS_email, Columns.USERS_Id,
				Tables.USERS);
	}

	public int getUID_byUsername(String username) {
		return db.getID(username, Columns.USERS_username, Columns.USERS_Id,
				Tables.USERS);
	}

	private String removeUserOrLiderFromTeam(String value, String team,
			Columns userColumn, Tables teamTable) {
		Messages result = null;
		result = teamAndUserExist(value, team, userColumn);
		if (result.equals(Messages.OK)) {
			int uid = db.getID(value, userColumn, Columns.USERS_Id,
					Tables.USERS);
			int tid = db.getID(team, Columns.TEAMS_name, Columns.TEAMS_Id,
					Tables.TEAMS);
			String statement = "DELETE FROM " + teamTable + " WHERE "
					+ Columns.USERS_Id + "=" + uid + " AND " + Columns.TEAMS_Id
					+ "=" + tid + ";";
			int rows_affected = db.executeUpdate(statement);
			if (rows_affected > 0) {
				result = Messages.UserRemovalFromTeam_Succeeded;
			} else {
				result = Messages.UserRemovalFromTeam_Failed;
			}
			db.closeStatementsAndResultSets();
		}

		return result.toString();
	}

	private Messages teamAndUserExist(String user, String team,
			Columns userColumn) {
		Messages result = Messages.OK;
		if (!db.isValuePresentInTable(user, userColumn, Tables.USERS)) {
			result = Messages.User_inexistend;
		} else {
			if (!db.isValuePresentInTable(team, Columns.TEAMS_name,
					Tables.TEAMS)) {
				result = Messages.Team_notExists;
			}
		}
		return result;
	}

	private String addUser_toTeam(String user, Columns user_column, String team) {
		Messages result = null;
		result = teamAndUserExist(user, team, user_column);
		if (result.equals(Messages.OK)) {
			int uid = db.getID(user, user_column, Columns.USERS_Id,
					Tables.USERS);
			int tid = db.getID(team, Columns.TEAMS_name, Columns.TEAMS_Id,
					Tables.TEAMS);
			result = isUserInORLiderOfTeam(user, team, user_column,
					Tables.TEAM_USERS);
			if (result.equals(Messages.User_notMemberOFTheTeam)) {
				String statement = "INSERT INTO " + Tables.TEAM_USERS
						+ " VALUES(" + uid + "," + tid + ");";
				int rows_affected = db.executeUpdate(statement);
				if (rows_affected > 0) {
					result = Messages.User_addedToTeam;
				} else {
					result = Messages.AddingToTeam_Failed;
				}
			} else {
				result = Messages.User_alreadyInTeam;
			}
			db.closeStatementsAndResultSets();
		}
		return result.toString();
	}

	private Messages isUserInORLiderOfTeam(String user, String team,
			Columns userColumn, Tables tableLookingIn) {
		Messages result = null;
		result = teamAndUserExist(user, team, userColumn);
		if (result.equals(Messages.OK)) {
			int uid = db
					.getID(user, userColumn, Columns.USERS_Id, Tables.USERS);
			int tid = db.getID(team, Columns.TEAMS_name, Columns.TEAMS_Id,
					Tables.TEAMS);
			String statement = "SELECT * FROM " + tableLookingIn + " WHERE "
					+ Columns.TEAMS_Id + "=" + tid + " AND " + Columns.USERS_Id
					+ "=" + uid + ";";
			db.executeStatement(statement);
			try {
				boolean lookingIn_TeamUsers = tableLookingIn
						.equals(Tables.TEAM_USERS);
				if (db.getResultSet().first()) {
					result = lookingIn_TeamUsers ? Messages.User_isMemberOfTheTeam
							: Messages.User_isLiderOfTheTeam;
				} else {
					result = lookingIn_TeamUsers ? Messages.User_notMemberOFTheTeam
							: Messages.User_notLiderOfTheTeam;
				}
			} catch (SQLException e) {
				db.logSQLException("isUserInTeam", e);
				result = Messages.OperationFailed;
			}
			db.closeStatementsAndResultSets();
		}
		return result;
	}

	private String addTeamLider(String value, String team, Columns userColumn) {
		Messages result = null;
		result = teamAndUserExist(value, team, userColumn);
		if (result.equals(Messages.OK)) {
			result = isUserInORLiderOfTeam(value, team, userColumn,
					Tables.TEAM_LEADERS);
			if (result.equals(Messages.User_notLiderOfTheTeam)) {
				int tid = db.getID(team, Columns.TEAMS_name, Columns.TEAMS_Id,
						Tables.TEAMS);
				int uid = db.getID(value, userColumn, Columns.USERS_Id,
						Tables.USERS);
				int affected_rows = db.insertRowIntoTable("" + uid + "," + tid,
						Tables.TEAM_LEADERS);
				if (affected_rows > 0) {
					result = Messages.TeamLider_Added;
				} else {
					result = Messages.TeamLider_AddingFailed;
				}
			} else {
				result = Messages.User_alreadyTeamLider;
			}
		}
		return result.toString();
	}

	private String removeUser(String user, Columns userColumn) {
		String result = "";
		if (user.equals(Settings.DB_ADMIN_USERNMANE)) {
			Logger.logINFO("Admin cannot be removed from database");
		} else {
			int rows = db.removeRow_byValue(user, userColumn, Tables.USERS);
			if (rows > 0) {
				result = Messages.User_deleted.toString();
			} else {
				result = Messages.User_inexistend.toString();
			}
		}
		return result;
	}

	private String checkUser(String value, String password, Columns column) {
		String result = "";
		String statement = "SELECT pass FROM " + Tables.USERS.toString()
				+ " WHERE " + column + "=\"" + value + "\";";
		db.executeStatement(statement);
		try {
			if (db.getResultSet() != null && db.getResultSet().first()) {
				String actual_password = db.getResultSet()
						.getString(Columns.USERS_password.toString())
						.toString();
				password = Tools.hashString(password);
				if (actual_password.equals(password)) {
					result = Messages.Login_succesfull.toString();
				} else {
					result = Messages.Login_failed.toString();
					Logger.logINFO("Login failed: '" + password
							+ "' does not match actual password: "
							+ actual_password);
				}
			} else {
				result = Messages.User_inexistend.toString();
			}
		} catch (SQLException e) {
			db.logSQLException("check_user", e);
		}
		db.closeStatementsAndResultSets();
		return result;
	}

	public String getUsername(int uid) {
		StatementExecutor statement = new StatementExecutor();
		statement.select(Columns.USERS_username).from(Tables.USERS).where().equals(Columns.USERS_Id, uid);
		ResultSet rs = db.executeStatement(statement.getStringStatement());
		String username = null;
		try {
			while(rs.next()){
				username = rs.getString(Columns.USERS_username.toString());
			}
		} catch (SQLException e) {
			Logger.logERROR(e);
		}
		return username;
	}

}
