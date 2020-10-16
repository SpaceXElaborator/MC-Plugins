package com.terturl.MeleeCraftEssentials.Minigames.Utils.Kits;

import org.bukkit.inventory.ItemStack;

public class MinigameKit {

	private String kitName;
	private ItemStack item;
	
	public MinigameKit(String s, ItemStack showItem) {
		kitName = s;
		item = showItem;
	}
	
	public String getName() {
		return kitName;
	}
	
	public ItemStack getShowItem() {
		return item;
	}
	
}