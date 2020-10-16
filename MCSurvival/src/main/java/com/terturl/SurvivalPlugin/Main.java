package com.terturl.SurvivalPlugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.terturl.SurvivalPlugin.Config.ConfigManager;
import com.terturl.SurvivalPlugin.Config.OnJoin;

public class Main extends JavaPlugin {

	private static Main main;
	private ConfigManager cm;
	
	public void onEnable() {
		main = this;
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new OnJoin(), this);
		cm = new ConfigManager();
	}
	
	public ConfigManager getCM() {
		return cm;
	}
	
	public static Main getInstance() {
		return main;
	}
	
}