package com.terturl.MeleeCraftEssentials.Minigames.Utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.terturl.MeleeCraftEssentials.Inventory.ClickAction;
import com.terturl.MeleeCraftEssentials.Inventory.InventoryButton;
import com.terturl.MeleeCraftEssentials.Inventory.InventoryUI;
import com.terturl.MeleeCraftEssentials.Minigames.MinigameArena;
import com.terturl.MeleeCraftEssentials.Minigames.Utils.Kits.MinigameKit;

public class KitSelection extends InventoryUI {
	
	protected MinigameArena<?> ma;
	private List<MinigameKit> kits = new ArrayList<MinigameKit>();
	
	public KitSelection(String selectionName, final List<MinigameKit> kits) {
		super(9, selectionName);
		for(MinigameKit k : kits) {
			this.kits.add(k);
			ItemStack i = k.getShowItem();
			InventoryButton ib = new InventoryButton(i) {
				@Override
				public void onPlayerClick(Player p, ClickAction a) {
					for(MinigameKit kit : kits) {
						if(kit.getName().equalsIgnoreCase(ChatColor.stripColor(getItem().getItemMeta().getDisplayName()))) {
							ma.addPlayer(p, kit);
							p.closeInventory();
							p.sendMessage("You've selected the survivor: " + kit.getName());
						}
					}
				}
			};
			addButton(ib);
		}
		updateInventory();
	}
	
	public void setMinigame(MinigameArena<?> ma) {
		this.ma = ma;
	}
	
	public List<MinigameKit> getKits() {
		return kits;
	}
	
}