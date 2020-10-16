package com.terturl.ZombieMinigame;

import java.io.File;

import com.terturl.MeleeCraftEssentials.MCEssentials;
import com.terturl.ZombieMinigame.Arena.ArenaHandler;
import com.terturl.ZombieMinigame.Arena.Kits.KitManager;
import com.terturl.ZombieMinigame.Arena.Listeners.EditListeners;
import com.terturl.ZombieMinigame.Arena.Listeners.Kits.SlayerListener;
import com.terturl.ZombieMinigame.Commands.ZombieArena;
import com.terturl.ZombieMinigame.Utils.PlayerInventoryManager;

public class ZombieMinigame extends MCEssentials {

	private static ZombieMinigame instance;
	private ArenaHandler ah;
	private PlayerInventoryManager pim;
	
	private KitManager km = new KitManager();
	
	public void onEnable() {
		instance = this;
		File f = getDataFolder();
		if(!f.exists()) f.mkdir();
		
		ah = new ArenaHandler();
		pim = new PlayerInventoryManager();
		
		ah.load();
		registerCommands();
		registerListeners();
	}
	
	public void onDisable() {
		ah.save();
	}
	
	public static ZombieMinigame getInstance() {
		return instance;
	}
	
	public ArenaHandler getArenaHandler() {
		return ah;
	}
	
	public PlayerInventoryManager getPlayerInventoryManager() {
		return pim;
	}
	
	public KitManager getKitManager() {
		return km;
	}
	
	public void registerCommands() {
		registerCommand(new ZombieArena());
	}
	
	public void registerListeners() {
		registerListener(new EditListeners());
		
		registerListener(new SlayerListener());
	}
	
}