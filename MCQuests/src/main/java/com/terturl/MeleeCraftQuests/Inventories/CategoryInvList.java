package com.terturl.MeleeCraftQuests.Inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.terturl.MeleeCraftEssentials.Inventory.ClickAction;
import com.terturl.MeleeCraftEssentials.Inventory.InventoryButton;
import com.terturl.MeleeCraftEssentials.Inventory.InventoryUI;
import com.terturl.MeleeCraftQuests.Main;
import com.terturl.MeleeCraftQuests.Quests.QuestHoldingTypes.QuestCategory;

public class CategoryInvList extends InventoryUI {

	/**
	 * Creates the initial entry Category inventory for the player to view quests.
	 * Will create a new item for every QuestInventory and add it to the inventory.
	 * @see com.terturl.MeleeCraftQuests.Quests.QuestHoldingTypes.QuestCategory
	 */
	public CategoryInvList() {
		super(27, ChatColor.translateAlternateColorCodes('&', Main.getInstance().getQuestManager().getTitle()));
		
		// Loop through all the QuestCategories and create the item that when clicked
		// Will bring the player to that inventory
		for(QuestCategory qc : Main.getInstance().getCategoryManager().getCategory()) {
			ItemStack item = new ItemStack(qc.getMaterial());
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(ChatColor.translateAlternateColorCodes('&', qc.getColor()) + qc.getName().replaceAll("_", " "));
			List<String> lore = qc.getLore();
			List<String> newLore = new ArrayList<String>();
			for(String s : lore) {
				newLore.add(ChatColor.translateAlternateColorCodes('&', s));
			}
			im.setLore(newLore);
			item.setItemMeta(im);
			
			InventoryButton ib = new InventoryButton(item) {
				@Override
				public void onPlayerClick(Player p, ClickAction a) {
					String catName = getItem().getItemMeta().getDisplayName().replaceAll(" ", "_");
					QuestCategory qc = Main.getInstance().getCategoryManager().getCategory(ChatColor.stripColor(catName));
					p.closeInventory();
					CategoryQuestInv.open(p, qc);
				}
			};
			addButton(ib, qc.getSlot());
		}
		
		// For every open slot, create a filler item and place it in the slot with no functionality
		while(getNextOpenSlot() != null) {
			ItemStack filler = new ItemStack(Material.valueOf(Main.getInstance().getQuestManager().getInvFiller()));
			ItemMeta fMeta = filler.getItemMeta();
			fMeta.setDisplayName(" ");
			filler.setItemMeta(fMeta);
			InventoryButton ib2 = new InventoryButton(filler) {
				@Override
				public void onPlayerClick(Player p, ClickAction a) {}
			};
			addButton(ib2);
		}
		
		updateInventory();
	}
	
}