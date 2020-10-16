package com.terturl.MeleeCraftQuests.Listeners.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.terturl.MeleeCraftQuests.Main;

public class onJoin implements Listener {

	// Will make sure to add the player for viewing of quests if they aren't already created
	
	@EventHandler
	public void playerJoin(PlayerJoinEvent e) {
		if(Main.getInstance().getPlayerManager().getPlayer(e.getPlayer().getUniqueId()) == null) {
			Main.getInstance().getPlayerManager().addPlayer(e.getPlayer().getUniqueId());
		}
	}
	
}