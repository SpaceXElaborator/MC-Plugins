package com.terturl.ZombieMinigame.Arena.Listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.terturl.ZombieMinigame.ZombieMinigame;
import com.terturl.ZombieMinigame.Arena.Arena;

public class EditListeners implements Listener {

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(!ZombieMinigame.getInstance().getArenaHandler().isEditing(p)) return;
		Arena a = ZombieMinigame.getInstance().getArenaHandler().getEditing().get(p.getUniqueId());
		if(!e.getHand().equals(EquipmentSlot.HAND)) return;
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if(p.getInventory().getItemInMainHand().getType().equals(Material.STICK)) {
				Location loc = e.getClickedBlock().getLocation();
				if(a.getLocations().contains(loc)) {
					p.sendMessage("Already added that location as a spawn point");
					return;
				}
				p.sendMessage("Added spawnpoint!");
				a.addSpawnPoint(loc);
				return;
			} else if(p.getInventory().getItemInMainHand().getType().equals(Material.WOODEN_AXE)) {
				a.setEnd(e.getClickedBlock().getLocation());
				p.sendMessage("Set bounding box 1");
				return;
			} else if(p.getInventory().getItemInMainHand().getType().equals(Material.BLAZE_ROD)) {
				a.setLobby(e.getClickedBlock().getLocation());
				p.sendMessage("Set Lobby");
				return;
			}
		}
		
		if(p.getInventory().getItemInMainHand().getType().equals(Material.BARRIER)) {
			ZombieMinigame.getInstance().getArenaHandler().stopEditing(p);
			p.sendMessage("Stopped Editing: " + a.getName());
			return;
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if(!ZombieMinigame.getInstance().getArenaHandler().isEditing(p)) return;
		Arena a = ZombieMinigame.getInstance().getArenaHandler().getEditing().get(p.getUniqueId());
		if(p.getInventory().getItemInMainHand().getType().equals(Material.STICK)) {
			Location loc = e.getBlock().getLocation();
			if(!a.getLocations().contains(loc)) return;
			p.sendMessage("Removed spawnpoint!");
			a.removeSpawnPoint(loc);
			e.setCancelled(true);
			return;
		} else if(p.getInventory().getItemInMainHand().getType().equals(Material.WOODEN_AXE)) {
			a.setStart(e.getBlock().getLocation());
			p.sendMessage("Set bounding box 2");
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(!ZombieMinigame.getInstance().getArenaHandler().isEditing(p)) return;
		Arena a = ZombieMinigame.getInstance().getArenaHandler().getEditing().get(p.getUniqueId());
		if(p.getInventory().getItemInMainHand().getType().equals(Material.STICK)) {
			for(Location loc : a.getLocations()) {
				p.sendBlockChange(loc, Material.GOLD_BLOCK.createBlockData());
			}
		} else {
			for(Location loc : a.getLocations()) {
				p.sendBlockChange(loc, loc.getBlock().getType().createBlockData());
			}
		}
	}
	
}