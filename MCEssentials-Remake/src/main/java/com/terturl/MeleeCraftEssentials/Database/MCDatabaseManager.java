package com.terturl.MeleeCraftEssentials.Database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MCDatabaseManager {

	private List<MCDatabase> dbs;
	private String userN;
	private String passW;
	private String host;
	private String db;
	
	public MCDatabaseManager(String user, String pass, String server, String dbName) {
		dbs = new ArrayList<MCDatabase>();
		userN = user;
		passW = pass;
		host = server;
		db = dbName;
	}
	
	public void registerDatabase(MCDatabase mc) {
		mc.setUsername(getUsername());
		mc.setPassword(getPassword());
		mc.setHost(getHost());
		mc.setDatabaase(getDB());
		mc.init();
		dbs.add(mc);
	}
	
	public void closeAll() {
		for(MCDatabase mc : dbs) {
			try {
				mc.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public MCDatabase getDatabase(String table) {
		for(MCDatabase mc : dbs) {
			if(mc.getTable().equalsIgnoreCase(table)) return mc;
		}
		return null;
	}
	
	public String getUsername() {
		return userN;
	}
	
	public String getPassword() {
		return passW;
	}
	
	public String getHost() {
		return host;
	}
	
	public String getDB() {
		return db;
	}
	
}