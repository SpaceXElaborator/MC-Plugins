package com.terturl.MeleeCraftEssentials.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigHandler {

	private String fileLocation;
	private boolean justCreated;
	
	private File mainDir;
	
	public ConfigHandler(JavaPlugin p) {
		fileLocation = p.getDescription().getName();
		createMain();
	}
	
	private void createMain() {
		File f = new File("plugins/" + fileLocation);
		mainDir = f;
		if(!f.exists()) {
			f.mkdir();
			justCreated = true;
			return;
		}
		justCreated = false;
		return;
	}
	
	public boolean firstLoad() {
		return justCreated;
	}
	
	public void createConfigFromMap(String configToCreate, Map<Object, Object> itemMap) {
		File f = new File(mainDir, configToCreate);
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(f);
			Iterator<Entry<Object, Object>> it = itemMap.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry<Object, Object> mapElement = (Map.Entry<Object, Object>)it.next();
				if(mapElement.getValue() instanceof List<?>) {
					config.set(mapElement.getKey().toString(), (List<?>)mapElement.getValue());
				} else {
					config.set(mapElement.getKey().toString(), mapElement.getValue().toString());
				}
			}
			try {
				config.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return;
	}
	
	public File getMainDirectory() {
		return mainDir;
	}
	
	public void createFolder(String folderName) {
		File f = new File(mainDir, folderName);
		if(!f.exists()) {
			f.mkdir();
		}
		return;
	}
	
	public YamlConfiguration loadConfig(String config) {
		File f = new File(mainDir, config);
		if(f.exists()) {
			return YamlConfiguration.loadConfiguration(f);
		}
		return null;
	}
	
}