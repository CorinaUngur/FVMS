package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import utils.Logger;
import config.Settings;
import db.tools.Columns;
import db.tools.Tables;

public class DBConnection {
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private ArrayList<Statement> stmts;
	private ArrayList<ResultSet> rss;

	private static DBConnection instance = null;

	public static DBConnection getInstance() {
		if (instance == null)
			instance = new DBConnection();
		return instance;
	}

	private DBConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://"
					+ Settings.DB_URL + "/fvms?" + "user=" + Settings.DB_USER
					+ "&password=" + Settings.DB_PASSWORD);
			rss = new ArrayList<ResultSet>();
			stmts = new ArrayList<Statement>();
		} catch (SQLException ex) {
			logSQLException("DBConnection", ex);
		} catch (Exception ex) {

		}
	}

	public int removeRow_byValue(String value, Columns column, Tables table) {
		int rows_removed = 0;
		if (isValuePresentInTable(value, column, table)) {
			String statement = "Delete from " + table + " where " + column
					+ "=\"" + value + "\";";
			rows_removed = executeUpdate(statement);
			if (rows_removed > 0) {
				Logger.logINFO(rows_removed + " rows successfully deleted");
			}
			closeStatementsAndResultSets();
		}
		return rows_removed;
	}

	public void logSQLException(String method_name, SQLException ex) {
		Logger.logERROR(ex, "SQLState: " + ex.getSQLState() + ", "
				+ method_name);
	}

	public void closeStatementsAndResultSets() {
		for (ResultSet rs : rss) {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
					logSQLException("closeResultSetAndStatement", sqlEx);
				}

				rs = null;
			}
		}
		rss.clear();
		for (Statement stmt : stmts) {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
					logSQLException("closeResultSetAndStatement", sqlEx);
				}

				stmt = null;
			}
		}
		stmts.clear();
	}

	public boolean isEmailValid(String email) {
		boolean isAlreadyPresent = isValuePresentInTable(email,
				Columns.USERS_email, Tables.USERS);
		boolean inValidFormat = email
				.matches("[A-Za-z_.0-9]+@[A-Za-z_.0-9]+\\.[A-Za-z_.0-9]+");
		if (isAlreadyPresent) {
			Logger.logINFO("Email is already used: " + email);
		}
		if (!inValidFormat) {
			Logger.logINFO("Email is not in a valid format: " + email);
		}
		return !isAlreadyPresent && inValidFormat;
	}

	public boolean isValuePresentInTable(String value, Columns column,
			Tables table) {
		String statement = "SELECT " + column + " FROM " + table + " WHERE "
				+ column + "=\"" + value + "\";";
		return executeValuePresentStatement(statement);
	}

	public boolean isValuePresentInTable(int value, Columns column, Tables table) {
		String statement = "SELECT " + column + " FROM " + table + " WHERE "
				+ column + "=" + value + ";";
		return executeValuePresentStatement(statement);
	}

	public ResultSet executeStatement(String statement) {
		try {
			stmt = conn.createStatement();
			if (stmt.execute(statement)) {
				rs = stmt.getResultSet();
			}
		} catch (SQLException ex) {
			logSQLException("executeStatement: " + statement, ex);
		}

		return rs;
	}

	public int executeUpdate(String statement) {
		rs = null;
		int rowsUpdated = 0;
		try {
			stmt = conn.createStatement();
			rowsUpdated = stmt.executeUpdate(statement);
			if (rowsUpdated > 0) {
				rs = stmt.getResultSet();
			}
			closeStatementsAndResultSets();
		} catch (SQLException ex) {
			logSQLException("executeUpdate: " + statement, ex);
		}
		return rowsUpdated;
	}

	public int getID(String value, Columns valueColumn, Columns idColumn,
			Tables table) {
		String statement = "SELECT " + idColumn.toString() + " FROM " + table
				+ " WHERE " + valueColumn + "=\"" + value + "\";";
		return getID_exStatement(statement);
	}
	public int getID(int value, Columns valueColumn, Columns idColumn,
			Tables table) {
		String statement = "SELECT " + idColumn.toString() + " FROM " + table
				+ " WHERE " + valueColumn + "=" + value + ";";
		return getID_exStatement(statement);
	}
	

	public int insertRowIntoTable(String values, Tables table) {
		String statement = "INSERT INTO " + table + " VALUES(" + values + ");";
		int rows_affected = executeUpdate(statement);
		closeStatementsAndResultSets();
		return rows_affected;
	}

	public ResultSet getResultSet() {
		return rs;
	}

	private boolean executeValuePresentStatement(String statement) {
		executeStatement(statement);
		boolean result = false;
		try {
			result = rs.first();
		} catch (SQLException e) {
			logSQLException("isValuePresentInTable:" + statement, e);
		}
		closeStatementsAndResultSets();
		return result;
	}

	public int removeRow_byValue(int intVal, Columns column, Tables table) {
		int rows_removed = 0;
		if (isValuePresentInTable(intVal, column, table)) {
			String statement = "DELETE FROM " + table + " WHERE " + column
					+ "=" + intVal + ";";
			rows_removed = executeUpdate(statement);
			if (rows_removed > 0) {
				Logger.logINFO(rows_removed + " rows successfully deleted");
			}
			closeStatementsAndResultSets();
		}
		return rows_removed;
	}

	public int setStringValue_byID(String newValue, Columns valColumn, int id,
			Columns idColumn, Tables table) {
		int rowsUpdated = 0;
		String statement = "UPDATE " + table + " SET " + valColumn + "=\""
				+ newValue + "\" WHERE " + idColumn + "=" + id;
		rowsUpdated = executeUpdate(statement);
		return rowsUpdated;
	}

	
	private int getID_exStatement(String statement){
		int id = -1;
		try {
			executeStatement(statement);
			rs.first();
			id = rs.getInt(1);
			closeStatementsAndResultSets();
		} catch (SQLException e) {
			logSQLException("getID", e);
		}
		return id;
	}

	public void removeAllFromTable(Tables table) {
		String statement = "DELETE FROM " + table;
		executeUpdate(statement);
	}
}
