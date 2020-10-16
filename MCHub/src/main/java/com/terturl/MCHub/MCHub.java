package com.terturl.MCHub;

import org.bukkit.plugin.java.JavaPlugin;

import com.terturl.MCHub.Commands.ClearChat;
import com.terturl.MCHub.Commands.LockChat;
import com.terturl.MCHub.HeadHunter.HeadHuntUtil;
import com.terturl.MCHub.HeadHunter.HeadHunterListeners;
import com.terturl.MCHub.Listeners.BlockListeners;
import com.terturl.MCHub.Listeners.PlayerListeners;
import com.terturl.MCHub.Listeners.WorldListeners;
import com.terturl.MeleeCraftEssentials.MCEssentials;
import com.terturl.MeleeCraftEssentials.Utils.ConfigHandler;

public class MCHub extends JavaPlugin {

	private static MCHub instance;
	
	private ConfigHandler conf = new ConfigHandler(this);
	private HeadHuntUtil hhu = new HeadHuntUtil();
	
	public Boolean isChatLocked = false;
	
	public void onEnable() {
		instance = this;
		
		hhu.load();
		
		if(conf.firstLoad()) {
			new ConfigDefaults();
		}
		
		registerListeners();
		registerCommands();
	}
	
	public void onDisable() {
		hhu.save();
	}
	
	public static MCHub getInstance() {
		return instance;
	}
	
	public ConfigHandler getConfiguration() {
		return conf;
	}
	
	public HeadHuntUtil getHHU() {
		return hhu;
	}
	
	private void registerCommands() {
		MCEssentials.getInstance().registerCommand(new ClearChat());
		MCEssentials.getInstance().registerCommand(new LockChat());
	}
	
	private void registerListeners() {
		MCEssentials.getInstance().registerListener(new BlockListeners());
		MCEssentials.getInstance().registerListener(new PlayerListeners());
		MCEssentials.getInstance().registerListener(new WorldListeners());
		MCEssentials.getInstance().registerListener(new HeadHunterListeners());
	}
	
}