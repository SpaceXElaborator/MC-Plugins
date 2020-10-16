package com.terturl.MCHub.HeadHunter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class HeadHuntUtil {

	private Map<String, HeadHunt> players = new HashMap<String, HeadHunt>();
	
	public HeadHunt getByName(String name) {
		if(players.get(name) == null) {
			HeadHunt hh = new HeadHunt(name);
			players.put(name, hh);
			return hh;
		}
		return players.get(name);
	}
	
	public void save() {
		File main = new File("plugins/MCHub/HeadHunt");
		if(main.exists()) main.mkdir();
		for(HeadHunt hh : players.values()) {
			File f = new File("plugins/MCHub/HeadHunt", hh.getName() + ".yml");
			if(f.exists()) f.delete();
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(f);
			config.set("Name", hh.getName());
			config.set("Collected-Skulls", hh.collectedSkulls());
			config.set("Achievements", hh.getAchievements());
			try {
				config.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void load() {
		
	}
	
}