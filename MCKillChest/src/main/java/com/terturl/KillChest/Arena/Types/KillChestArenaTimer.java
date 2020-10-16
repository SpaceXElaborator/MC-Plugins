package com.terturl.KillChest.Arena.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.terturl.KillChest.KillChest;
import com.terturl.KillChest.Arena.KillChestArena;
import com.terturl.KillChest.Arena.Hologram.ArmorStandCreator;

import net.md_5.bungee.api.ChatColor;

public class KillChestArenaTimer extends KillChestArena {

	protected int time;
	protected int RunnableId;
	
	public KillChestArenaTimer(UUID uuid, World w, Location min, Location max) {
		super(uuid, w, min, max);
	}

	@Override
	public void unlock() {
		for(Entity e : stands.get(0).getArmorStand().getNearbyEntities(10, 10, 10)) {
			if(e instanceof Player) {
				((Player)e).sendMessage("Chest is unlocked!");
			}
		}
	}

	@Override
	public void announce() {
		String x = String.valueOf(block.getBlockX());
		String y = String.valueOf(block.getBlockY());
		String z = String.valueOf(block.getBlockZ());
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.sendTitle(ChatColor.GOLD + "Chest Has Spawned!", ChatColor.GREEN + "go to (" + x + "," + y + "," + z +")!", 10, 30, 10);
		}
	}

	@Override
	public void setHologram() {
		Location loc = block.add(0.5, -1, 0.5);
		List<String> lines = new ArrayList<String>();
		lines.add(ChatColor.BOLD + "" + ChatColor.GOLD + "Timer Chest");
		lines.add(ChatColor.DARK_RED + "%time% seconds open!");
		
		Collections.reverse(lines);
		
		for(String s : lines) {
			ArmorStand ar = (ArmorStand)killWorld.spawnEntity(loc, EntityType.ARMOR_STAND);
			ArmorStandCreator asc = new ArmorStandCreator(s, ar);
			loc = loc.add(0, 0.25, 0);
			stands.add(asc);
		}
		updateHologram();
	}

	@Override
	public void updateHologram() {
		RunnableId = Bukkit.getScheduler().scheduleSyncRepeatingTask(KillChest.getInstance(), new Runnable() {
			int timS = time;
			public void run() {
				for(ArmorStandCreator asc : stands) {
					asc.SetAsName(replaceString(asc.getName(), timS));
				}
				if(timS == 0) {
					unlock();
					stopTimer();
				}
				if(timS != 0) {
					timS -= 1;
				}
			}
		}, 0, 20L);
	}
	
	protected void stopTimer() {
		time = 0;
		Bukkit.getScheduler().cancelTask(RunnableId);
	}
	
	protected String replaceString(String s, int times) {
		s = s.replaceAll("%time%", String.valueOf(times));
		return s;
	}
	
	public void setTime(int x) {
		time = x;
	}
	
	@EventHandler
	public void handleBlock(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getClickedBlock() == null) return;
		if(!e.getHand().equals(EquipmentSlot.HAND)) return;
		if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if(!e.getClickedBlock().getType().equals(Material.CHEST)) return;
		if(!e.getClickedBlock().getLocation().equals(block.getBlock().getLocation().add(0, 1, 0))) return;
		if(time != 0) {
			p.sendMessage("May not open it yet");
			e.setUseInteractedBlock(Result.DENY);
			e.setCancelled(true);
			return;
		} else {
			e.getClickedBlock().setType(Material.AIR);
			giveItems(p);
			sendCommands(p);
			despawn();
			return;
		}
	}
	
}