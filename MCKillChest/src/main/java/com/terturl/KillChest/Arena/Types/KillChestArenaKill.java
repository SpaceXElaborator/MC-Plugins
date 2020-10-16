package com.terturl.KillChest.Arena.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.terturl.KillChest.Arena.KillChestArena;
import com.terturl.KillChest.Arena.Hologram.ArmorStandCreator;

import net.md_5.bungee.api.ChatColor;

public class KillChestArenaKill extends KillChestArena {

	protected Map<UUID, Integer> kills = new HashMap<UUID, Integer>();
	protected Integer killNum;
	
	public KillChestArenaKill(UUID uuid, World w, Location min, Location max) {
		super(uuid, w, min, max);
	}

	@Override
	public void unlock() {
		
	}

	@Override
	public void announce() {
		
	}

	@Override
	public void setHologram() {
		Location loc = block.add(0.5, -1, 0.5);
		List<String> lines = new ArrayList<String>();
		lines.add(ChatColor.BOLD + "" + ChatColor.GOLD + "Death Chest");
		lines.add(ChatColor.DARK_RED + "%kill% kills need to open!");
		
		Collections.reverse(lines);
		
		for(String s : lines) {
			ArmorStand ar = (ArmorStand)killWorld.spawnEntity(loc, EntityType.ARMOR_STAND);
			ArmorStandCreator asc = new ArmorStandCreator(s, ar);
			loc = loc.add(0, 0.25, 0);
			stands.add(asc);
		}
	}

	public void setKills(Integer i) {
		killNum = i;
	}
	
	protected Integer getKills(Integer i, Integer kills) {
		return i-kills;
	}
	
	@Override
	public void updateHologram() {
		
	}
	@EventHandler
	public void handleBlock(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getClickedBlock() == null) return;
		if(!e.getHand().equals(EquipmentSlot.HAND)) return;
		if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if(!e.getClickedBlock().getType().equals(Material.CHEST)) return;
		if(!e.getClickedBlock().getLocation().equals(block.getBlock().getLocation().add(0, 1, 0))) return;
		if(!(kills.containsKey(p.getUniqueId())) || kills.get(p.getUniqueId()) < killNum) {
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
	
	@EventHandler
	public void entityDeath(EntityDeathEvent e) {
		for(Entity ent : stands.get(0).getArmorStand().getNearbyEntities(20, 20, 20)) {
			if(ent instanceof ArmorStand) return;
			if(ent.equals(e.getEntity())) {
				UUID u = e.getEntity().getKiller().getUniqueId();
				if(kills.containsKey(u)) {
					if(kills.get(u) == killNum) {
						e.getEntity().getKiller().sendMessage("You have enough kills go get the chest!");
						return;
					}
					kills.put(u, kills.get(u) + 1);
				} else {
					kills.put(u, Integer.valueOf(1));
				}
				e.getEntity().getKiller().sendMessage("You need " + String.valueOf(getKills(killNum, kills.get(u))) + " more kills!");
			}
		}
		return;
	}
	
}