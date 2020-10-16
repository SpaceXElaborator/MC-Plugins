package com.terturl.ZombieMinigame.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerInventoryManager {

	private Map<UUID, SaveableInventory> invs;
	
	public PlayerInventoryManager() {
		invs = new HashMap<UUID, SaveableInventory>();
	}
	
	public void saveInventory(Player p) {
		invs.put(p.getUniqueId(), new SaveableInventory(p.getInventory().getContents()));
	}
	
	public void loadInventory(Player p) {
		p.getInventory().setContents(invs.get(p.getUniqueId()).getItems());
		p.updateInventory();
		invs.remove(p.getUniqueId());
	}
	
	class SaveableInventory {
		
		private ItemStack[] items;
		
		public SaveableInventory(ItemStack[] contents) {
			items = contents;
		}
		
		public ItemStack[] getItems() {
			return items;
		}
		
	}
}