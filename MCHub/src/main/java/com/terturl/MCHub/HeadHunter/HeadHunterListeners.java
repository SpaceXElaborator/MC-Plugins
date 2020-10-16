package com.terturl.MCHub.HeadHunter;

import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.terturl.MCHub.MCHub;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class HeadHunterListeners implements Listener {

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getHand().equals(EquipmentSlot.HAND)) {
			if(e.getClickedBlock().getType() != Material.PLAYER_HEAD) return;
			Skull b = (Skull)e.getClickedBlock().getState();
			String name = b.getOwningPlayer().getName();
			HeadHunt hh = MCHub.getInstance().getHHU().getByName(p.getName());
			if(hh == null) return;
			if(hh.collect(name)) {
				p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("Test").color(ChatColor.GREEN).create());
			}
		}
	}
	
}