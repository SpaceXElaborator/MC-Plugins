package com.terturl.KillChest.Arena;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.terturl.KillChest.Arena.Hologram.ArmorStandCreator;
import com.terturl.MeleeCraftEssentials.MCEssentials;

public abstract class KillChestArena implements Listener {

	protected UUID killchest;
	protected World killWorld;
	protected Location minLoc;
	protected Location maxLoc;
	protected List<ArmorStandCreator> stands = new ArrayList<ArmorStandCreator>();
	protected List<ItemStack> items = new ArrayList<ItemStack>();
	protected List<String> commands = new ArrayList<String>();
	protected Location block;
	
	public KillChestArena(UUID uuid, World w, Location min, Location max) {
		killchest = uuid;
		killWorld = w;
		minLoc = min;
		maxLoc = max;
		register();
	}
	
	public void spawnChest() {
		Random r = new Random();
		int xMin = Math.min((int) minLoc.getX(), (int) maxLoc.getX());
		int xMax = Math.max((int) minLoc.getX(), (int) maxLoc.getX());
		int zMin = Math.min((int) minLoc.getZ(), (int) maxLoc.getZ());
		int zMax = Math.max((int) minLoc.getZ(), (int) maxLoc.getZ());
		int x = r.nextInt(xMax - xMin) + xMin;
		int z = r.nextInt(zMax - zMin) + zMin;
		int y = killWorld.getHighestBlockYAt(x, z);

		Location loc = new Location(killWorld, x, y + 1, z);
		block = loc;
		loc.getBlock().setType(Material.CHEST);

		setHologram();
		announce();
	}
	
	public abstract void unlock();
	
	public abstract void announce();
	
	public abstract void setHologram();
	
	public abstract void updateHologram();
	
	public void setItems(List<ItemStack> item) {
		items = item;
	}
	
	public void setCommands(List<String> cmds) {
		commands = cmds;
	}
	
	protected void giveItems(Player p) {
		for(ItemStack i : items) {
			p.getInventory().addItem(i);
		}
	}
	
	protected void sendCommands(Player p) {
		for(String s : commands) {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s);
		}
	}
	
	public void register() {
		MCEssentials.getInstance().registerListener(this);
	}
	
	public void despawn() {
		for(ArmorStandCreator asc : stands) {
			asc.kill();
		}
	}
	
}