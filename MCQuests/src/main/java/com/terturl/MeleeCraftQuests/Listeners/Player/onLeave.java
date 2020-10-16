package com.terturl.MeleeCraftQuests.Listeners.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.terturl.MeleeCraftQuests.Main;

public class onLeave implements Listener {

	//Will save the player when they quit the game
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		Main.getInstance().getPlayerManager().getPlayer(e.getPlayer().getUniqueId()).save();
	}
	
}