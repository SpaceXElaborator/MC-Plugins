package com.terturl.MeleeCraftEssentials.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.terturl.MeleeCraftEssentials.MCEssentials;

public class CreateDefaultConfig {

	public CreateDefaultConfig() {
		Map<Object, Object> dbMap = new HashMap<Object, Object>();
		dbMap.put("User", "root");
		dbMap.put("Pass", "");
		dbMap.put("Host", "localhost");
		dbMap.put("DBName", "MeleeCraft");
		MCEssentials.getInstance().getConfiguration().createConfigFromMap("database.yml", dbMap);
		
		Map<Object, Object> msgMap = new HashMap<Object, Object>();
		msgMap.put("Mail-CantSendToSelf", "&4Can't send mail to yourself!");
		msgMap.put("Mail-MessageSent", "&6You sent &3%player% &6a message!");
		MCEssentials.getInstance().getConfiguration().createConfigFromMap("messages.yml", msgMap);
		
		Map<Object, Object> itemMap = new HashMap<Object, Object>();
		itemMap.put("Inv-Name", "&6Messages");
		itemMap.put("Inv-NamePlayer", "&6Messages from: %player%");
		itemMap.put("Skull-Name", "&4%player%");
		itemMap.put("Item-Type", "paper");
		itemMap.put("Item-Name", "&4%date%");
		List<String> lore = new ArrayList<String>();
		lore.add("From: %player%");
		lore.add("-------------------------");
		lore.add("%message%");
		lore.add("-------------------------");
		itemMap.put("Item-Lore", lore);
		MCEssentials.getInstance().getConfiguration().createConfigFromMap("itemstructure.yml", itemMap);
		
		Map<Object, Object> mainConfig = new HashMap<Object, Object>();
		mainConfig.put("Server-Name", "Hub");
		mainConfig.put("Inv-NextButton", "FEATHER");
		mainConfig.put("Inv-BackButton", "FEATHER");
		mainConfig.put("Inv-NextButtonName", "&6Next");
		mainConfig.put("Inv-BackButtonName", "&6Back");
		mainConfig.put("FirstSpawn", "world:10:10:10");
		MCEssentials.getInstance().getConfiguration().createConfigFromMap("config.yml", mainConfig);
	}
	
}