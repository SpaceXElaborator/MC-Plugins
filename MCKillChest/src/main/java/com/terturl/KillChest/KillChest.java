package com.terturl.KillChest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.terturl.KillChest.Arena.Types.KillChestArenaKill;

public class KillChest extends JavaPlugin {

	private static KillChest instance;
	
	World w = Bukkit.getWorlds().get(0);
	Block first = w.getBlockAt(153, 62, -162);
	Block second = w.getBlockAt(163, 62, -172);
	
	KillChestArenaKill kca = new KillChestArenaKill(UUID.randomUUID(), Bukkit.getWorlds().get(0), first.getLocation(), second.getLocation());
	
	public void onEnable() {
		instance = this;
		//TODO: POC
		List<ItemStack> items = new ArrayList<ItemStack>();
		items.add(new ItemStack(Material.BONE_BLOCK));
		items.add(new ItemStack(Material.DIAMOND_SWORD));
		
		List<String> cmds = new ArrayList<String>();
		cmds.add("say het");
		
		kca.setKills(5);
		kca.setItems(items);
		kca.setCommands(cmds);
		kca.spawnChest();
	}
	
	public static KillChest getInstance() {
		return instance;
	}

	/*@Override
	public void load() {
		try {
			config.createKillChestFolder();
			config.createKillChestConf();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

	public void onDisable() {
		kca.despawn();
	}
	
}