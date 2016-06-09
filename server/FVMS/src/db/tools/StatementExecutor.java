package db.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utils.Logger;
import config.Settings;

public class StatementExecutor {
	private String statement = null;

	public StatementExecutor() {
		statement = "";
	}

	public StatementExecutor select(Columns col) {
		statement += " SELECT " + col;
		return this;
	}

	public StatementExecutor select(Columns... cols) {
		statement += " SELECT " + cols[0];
		for (int i = 1; i < cols.length; i++) {
			statement += ", " + cols[i];
		}
		return this;
	}

	public StatementExecutor from(Tables... table) {
		statement +=" FROM " + table[0];
		for (int i = 1; i < table.length; i++) {
			statement += ", " + table[i];
		}
		return this;
	}

	public StatementExecutor where() {
		statement += " WHERE ";
		return this;
	}

	public StatementExecutor equals(Columns col, String value) {
		statement += col + "=\"" + value + "\" ";
		return this;
	}

	public StatementExecutor set(Columns col, String set) {
		statement += " SET " + col + "=\"" + set + "\" ";
		return this;
	}

	public StatementExecutor insertInto(Tables table) {
		statement += " INSERT INTO " + table;
		return this;
	}

	public StatementExecutor values(Object... objects) {
		statement = " VALUES(";
		for (int i = 0; i < objects.length; i++) {
			statement += ", " + toString(objects[i]);
		}
		statement += ") ";
		return this;
	}
	public String getStringStatement(){
		return statement;
	}
	public ResultSet executeStatement() {
		ResultSet rs = null;
		Statement stmt = getStatement();
		try {
			stmt.execute(statement);
			rs = stmt.getResultSet();
		} catch (SQLException e) {
			Logger.logERROR(e);
		}
		
		return rs;
	}
	public int executeUpdate(){
		int rows_affected = 0;
		Statement stmt = getStatement();
		try {
			rows_affected = stmt.executeUpdate(statement);
		} catch (SQLException e) {
			Logger.logERROR(e);
		}
		
		return rows_affected;
	}
	private String toString(Object object) {
		if (object instanceof String) {
			return "\"" + object + "\"";
		} else {
			return String.valueOf(object);
		}
	}
	private Statement getStatement(){
		Statement stmt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://"
					+ Settings.DB_URL + "/fvms?" + "user=" + Settings.DB_USER
					+ "&password=" + Settings.DB_PASSWORD);
			stmt = conn.createStatement();
		} catch (SQLException ex) {
			Logger.logERROR(ex);
		} catch (Exception ex) {

		}
		return stmt;
	}

	public StatementExecutor equals(Columns col, int value) {
		statement += col + "=" + value + " ";
		return this;
		
	}

}
