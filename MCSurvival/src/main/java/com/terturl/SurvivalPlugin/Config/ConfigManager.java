package com.terturl.SurvivalPlugin.Config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {

	private File Messages;
	private File Players;
	
	public ConfigManager() {
		File f = new File("plugins/SMPSurvival");
		if(!f.exists()) f.mkdir();
		Messages = new File(f, "messages.yml");
		Players = new File(f, "players.yml");
		if(!Players.exists()) {
			FileConfiguration config = YamlConfiguration.loadConfiguration(Players);
			config.createSection("P-UUIDs");
			
			try {
				config.save(Players);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!Messages.exists()) {
			FileConfiguration config = YamlConfiguration.loadConfiguration(Messages);
			
			
			
			try {
				config.save(Messages);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public FileConfiguration getConfig(String s) {
		File f = new File("plugins/SMPSurvival", s);
		FileConfiguration config = YamlConfiguration.loadConfiguration(f);
		return config;
	}
	
}