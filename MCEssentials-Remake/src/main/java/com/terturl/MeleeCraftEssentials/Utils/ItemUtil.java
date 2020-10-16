package com.terturl.MeleeCraftEssentials.Utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {

	private String itemName;
	private Material itemMat = Material.STONE;
	private List<String> itemLore = new ArrayList<String>();
	
	public ItemUtil(String s) {
		itemName = s;
	}
	
	public ItemUtil setMaterial(Material mat) {
		itemMat = mat;
		return this;
	}
	
	public ItemUtil addLore(String s) {
		itemLore.add(s);
		return this;
	}
	
	public ItemUtil addAllLore(List<String> s) {
		for(String s2 : s) {
			itemLore.add(s2);
		}
		return this;
	}
	
	public ItemStack build() {
		ItemStack item = new ItemStack(itemMat);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(itemName);
		meta.setLore(itemLore);
		item.setItemMeta(meta);
		return item;
	}
	
}