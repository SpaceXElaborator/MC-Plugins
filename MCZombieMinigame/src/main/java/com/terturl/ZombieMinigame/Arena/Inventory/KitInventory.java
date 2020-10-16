package com.terturl.ZombieMinigame.Arena.Inventory;

import org.bukkit.entity.Player;

import com.terturl.MeleeCraftEssentials.Inventory.ClickAction;
import com.terturl.MeleeCraftEssentials.Inventory.InventoryButton;
import com.terturl.MeleeCraftEssentials.Inventory.InventoryUI;
import com.terturl.ZombieMinigame.ZombieMinigame;
import com.terturl.ZombieMinigame.Arena.Arena;
import com.terturl.ZombieMinigame.Arena.Kits.Kit;

public class KitInventory extends InventoryUI {

	public KitInventory(final Arena arena) {
		super(9, "Kits");
		
		for(final Kit k : ZombieMinigame.getInstance().getKitManager().getKits()) {
			addButton(new InventoryButton(k.build()) {
				@Override
				public void onPlayerClick(Player p, ClickAction a) {
					arena.setKit(p, k);
					p.closeInventory();
					p.sendMessage("Selected kit: " + k.getName());
				}
			});
		}
		
		updateInventory();
	}
	
}