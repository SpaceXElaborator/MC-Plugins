package com.terturl.MeleeCraftEssentials.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.terturl.MeleeCraftEssentials.MCEssentials;

public class FirstSpawn implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(e.getPlayer().hasPlayedBefore()) return;
		
		FileConfiguration config = MCEssentials.getInstance().getConfiguration().loadConfig("config.yml");
		String[] cords = config.getString("FirstSpawn").split(":");
		World w = Bukkit.getWorld(cords[0]);
		Double x = Double.valueOf(cords[1]);
		Double y = Double.valueOf(cords[2]);
		Double z = Double.valueOf(cords[3]);
		Location loc = new Location(w, x, y, z);
		e.getPlayer().teleport(loc);
	}
	
}