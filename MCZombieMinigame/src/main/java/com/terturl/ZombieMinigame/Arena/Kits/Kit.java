package com.terturl.ZombieMinigame.Arena.Kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Kit {

	private String name;
	private Material item;
	private List<String> lore = new ArrayList<String>();
	
	public Kit(String kitN, Material mat) {
		name = kitN;
		item = mat;
	}
	
	public String getName() {
		return name;
	}
	
	public Material getItemType() {
		return item;
	}
	
	public List<String> getLore() {
		return lore;
	}
	
	public void addLore(String s) {
		lore.add(s);
	}
	
	public ItemStack build() {
		ItemStack items = new ItemStack(item);
		ItemMeta meta = items.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + name);
		meta.setLore(lore);
		items.setItemMeta(meta);
		return items;
	}
	
}