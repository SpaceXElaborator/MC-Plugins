package com.terturl.MeleeCraftEssentials.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.terturl.MeleeCraftEssentials.MCEssentials;

public class PageableInventoryManager {
	
	protected Map<Integer, InventoryUI> pages = new HashMap<Integer, InventoryUI>();
	protected Integer invSize;
	protected List<InventoryButton> items;
	protected String name;
	protected Integer currPage = 0;
	protected boolean back;
	protected PageableInventoryManager pim;
	
	public PageableInventoryManager(int size, String title, List<InventoryButton> c) {
		invSize = size;
		items = c;
		name = title;
		
		init();
	}
	
	public void open(Player p) {
		pages.get(0).open(p);
		currPage = 0;
	}
	
	protected void init() {
		int numOfItems = items.size();
		while(numOfItems > 0) {
			int itemsAdded = 0;
			InventoryUI pi = new InventoryUI(invSize, ChatColor.translateAlternateColorCodes('&', sanatize(name, pages.size() + 1)));
			if((numOfItems == invSize) && (pages.isEmpty())) {
				pages.put(pages.size(), pi);
				for(InventoryButton iis : items) {
					pi.addButton(iis);
				}
				pi.updateInventory();
				break;
			}
			
			if(!(pages.isEmpty())) { 
				pi.addButton(new InventoryButton(nextButton()) {
					@Override
					public void onPlayerClick(final Player p, ClickAction a) {
						p.closeInventory();
						Bukkit.getScheduler().runTaskLater(MCEssentials.getInstance(), new Runnable() {
							public void run() {
								pages.get(currPage - 1).open(p);
							}
							
						}, 2L);
						currPage -= 1;
					}
				}, invSize - 9);
				itemsAdded+=1;
			}
			pages.put(pages.size(), pi);
			if(!(numOfItems<=(invSize-1))) {
				pi.addButton(new InventoryButton(backButton()) {
					@Override
					public void onPlayerClick(final Player p, ClickAction a) {
						p.closeInventory();
						Bukkit.getScheduler().runTaskLater(MCEssentials.getInstance(), new Runnable() {
							public void run() {
								pages.get(currPage + 1).open(p);
							}
							
						}, 2L);
						currPage += 1;
					}
				}, invSize - 1);
				itemsAdded+=1;
			}
			int temp = invSize-itemsAdded;
			numOfItems -= temp;
			if(temp >= items.size()) temp=items.size();
			List<InventoryButton> ii = new ArrayList<InventoryButton>(items.subList(0, temp));
			items.subList(0, temp).clear();
			for(InventoryButton iis : ii) {
				pi.addButton(iis);
			}
			pi.updateInventory();
		}
	}
	
	private ItemStack nextButton() {
		YamlConfiguration config = MCEssentials.getInstance().getConfiguration().loadConfig("config.yml");
		String name = (config.contains("Inv-NextButtonName")) ? config.getString("Inv-NextButtonName") : "";
		Material type = (config.contains("Inv-NextButton")) ? Material.valueOf(config.getString("Inv-NextButton")) : Material.FEATHER;
		ItemStack item = new ItemStack(type);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		item.setItemMeta(meta);
		return item;
	}
	
	private ItemStack backButton() {
		YamlConfiguration config = MCEssentials.getInstance().getConfiguration().loadConfig("config.yml");
		String name = (config.contains("Inv-BackButtonName")) ? config.getString("Inv-BackButtonName") : "";
		Material type = (config.contains("Inv-BackButton")) ? Material.valueOf(config.getString("Inv-BackButton")) : Material.FEATHER;
		ItemStack item = new ItemStack(type);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		item.setItemMeta(meta);
		return item;
	}
	
	protected String sanatize(String s, int x) {
		s = s.replaceAll("%pagenum%", String.valueOf(x));
		return s;
	}
	
}