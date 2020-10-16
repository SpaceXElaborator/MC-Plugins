package com.terturl.MeleeCraftEssentials.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringJoiner;
import java.util.logging.Level;

import com.terturl.MeleeCraftEssentials.MCEssentials;

public abstract class MCDatabase {
	
	private Connection con = null;
	private String userN;
	private String serverN;
	private String passN;
	private String database;
	
	private String table;
	private String createTString;
	
	private String dataTable;
	
	public MCDatabase(String tableName, String tableCreation) {
		table = tableName;
		createTString = tableCreation;
	}
	
	protected void init() {
		String url = "jdbc:mysql://" + serverN + ":3306/" + getDatabase();
		Properties info = new Properties();
		info.put("user", userN);
		info.put("password", passN);
		try {
			con = DriverManager.getConnection(url, info);
			con.setAutoCommit(false);
			if(con == null) {
				MCEssentials.getInstance().getLogger().log(Level.SEVERE, "[!] Could not connect to " + getHost() + " with database " + getDatabase() + "!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataTable = "`" + getDatabase() + "`.`" + getTable() + "`";
		try {
			createTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void createTable() throws SQLException {
		String[] values = getTableString().split(":");
		StringJoiner sj = new StringJoiner(",");
		for(String s : values) {
			sj.add(s);
		}
		PreparedStatement create = null;
		String temp = "CREATE TABLE IF NOT EXISTS " + dataTable + " (" + sj.toString().trim() + ") ENGINE = InnoDB;";
		create = con.prepareStatement(temp);
		create.executeUpdate();
		con.commit();
	}
	
	protected void updateSingle(String selector, String query, Object setter, Object matcher) {
		String temp = "UPDATE " + dataTable + " SET " + selector + "=? WHERE `" + query + "`=?";
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(temp);
			stmt.setObject(1, setter);
			stmt.setObject(2, matcher);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	protected Object getSingle(String selector, String query, Object matcher) {
		String temp = "SELECT * FROM " + dataTable + " WHERE `" + query + "`=?";
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(temp);
			stmt.setObject(1, matcher);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			Object j = null;
			while(rs.next()) {
				j = rs.getObject(selector);
			}
			stmt.close();
			return j;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected List<Object> getAll(String query, Object matcher, Integer valuesInDB) {
		String temp = "SELECT * FROM  " + dataTable + " WHERE `" + query + "`=?";
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(temp);
			stmt.setObject(1, matcher);
			stmt.execute();
			ResultSet rs = stmt.executeQuery();
			List<Object> objs = new ArrayList<Object>();
			while(rs.next()) {
				for(int i=1; i <= valuesInDB; i++) {
					objs.add(rs.getObject(i));
				}
			}
			return objs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected void insert(Object... objs) {
		List<Object> obj = new ArrayList<Object>();
		for(Object j : objs) {
			obj.add(j);
		}
		int size = obj.size();
		
		StringJoiner sj = new StringJoiner(",");
		for(int x = 0; x < size; x++) {
			sj.add("?");
		}
		
		String temp = "INSERT INTO `" + getDatabase() + "`.`" + getTable() + "` VALUES (" + sj.toString().trim() + ")";
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(temp);
			int x = 0;
			for(Object js : obj) {
				x++;
				stmt.setObject(x, js.toString());
			}
			stmt.executeUpdate();
			con.commit();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return con;
	}
	
	public void setUsername(String s) {
		userN = s;
	}
	
	public String getUsername() {
		return userN;
	}
	
	public void setHost(String s) {
		serverN = s;
	}
	
	public String getHost() {
		return serverN;
	}
	
	public void setPassword(String s) {
		passN = s;
	}
	
	public String getPassword() {
		return passN;
	}
	
	public void setDatabaase(String s) {
		database = s;
	}
	
	public String getDatabase() {
		return database;
	}
	
	public String getTable() {
		return table;
	}
	
	public String getTableString() {
		return createTString;
	}
	
}