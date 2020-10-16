package com.terturl.MeleeCraftQuests.Inventories;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.terturl.MeleeCraftEssentials.Inventory.ClickAction;
import com.terturl.MeleeCraftEssentials.Inventory.InventoryButton;
import com.terturl.MeleeCraftEssentials.Inventory.InventoryUI;
import com.terturl.MeleeCraftQuests.Main;
import com.terturl.MeleeCraftQuests.Quests.MeleeQuest;
import com.terturl.MeleeCraftQuests.Utils.StringReplacement;

import net.md_5.bungee.api.ChatColor;


public class CancelQuestInv extends InventoryUI {

	/**
	 * Creates a cancel quest inventory. Takes a MeleeQuest and InventoryButton that will be presented
	 * to the player to see progress and name/item of quest canceling. Will create the yes and no button.
	 * @param q		MeleeQuest
	 * @param ib	InventoryButton
	 * @see com.terturl.MeleeCraftQuests.Quests.MeleeQuest
	 * 
	 */
	public CancelQuestInv(MeleeQuest q, InventoryButton ib) {
		super(9, ChatColor.translateAlternateColorCodes('&', StringReplacement.formatQuestName(Main.getInstance().getQuestManager().getCancelRemark(), q)));
		
		ItemStack yes = new ItemStack(Material.GREEN_WOOL);
		ItemMeta yesM = yes.getItemMeta();
		yesM.setDisplayName(ChatColor.GREEN + "Yes");
		yes.setItemMeta(yesM);
		addButton(new InventoryButton(yes) {
			@Override
			public void onPlayerClick(Player p, ClickAction ca) {
				Main.getInstance().getPlayerManager().getPlayer(p.getUniqueId()).removeQuest(q.getComputerName());
				p.closeInventory();
				CategoryInvList cil = new CategoryInvList();
				cil.open(p);
			}
		}, 2);
		
		addButton(ib, 4);
		
		ItemStack no = new ItemStack(Material.RED_WOOL);
		ItemMeta noM = yes.getItemMeta();
		noM.setDisplayName(ChatColor.RED + "No");
		no.setItemMeta(noM);
		addButton(new InventoryButton(no) {
			@Override
			public void onPlayerClick(Player p, ClickAction ca) {
				p.closeInventory();
				CategoryInvList cil = new CategoryInvList();
				cil.open(p);
			}
		}, 6);
		
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