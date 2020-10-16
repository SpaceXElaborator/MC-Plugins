package com.terturl.MeleeCraftQuests.Inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.terturl.MeleeCraftEssentials.Inventory.ClickAction;
import com.terturl.MeleeCraftEssentials.Inventory.InventoryButton;
import com.terturl.MeleeCraftQuests.Main;
import com.terturl.MeleeCraftQuests.MeleeQuestPlayer;
import com.terturl.MeleeCraftQuests.Quests.MeleeQuest;
import com.terturl.MeleeCraftQuests.Quests.QuestHoldingTypes.QuestCategory;
import com.terturl.MeleeCraftQuests.Quests.QuestHoldingTypes.QuestSets;
import com.terturl.MeleeCraftQuests.Quests.Utils.Glow;
import com.terturl.MeleeCraftQuests.Utils.StringReplacement;

public class CategoryQuestInv {
	
	/**
	 * Will open a multi-dimension inventory for the player to view quests of that category.
	 * The next and back button are created and will move the player around the inventories accordingly
	 * @param p		Player
	 * @param qc	QuestCategory
	 * @see com.terturl.MeleeCraftQuests.Quests.QuestHoldingTypes.QuestCategory
	 */
	public static void open(Player p, QuestCategory qc) {
		// Gets the players MeleeQuestPlayer instance
		MeleeQuestPlayer mqp = Main.getInstance().getPlayerManager().getPlayer(p.getUniqueId());
		
		//Loads all the quests into the inventory button to be used to create a PageableInventoryManager
		List<InventoryButton> quests = new ArrayList<InventoryButton>();
		if(!(qc.getQuestSets().isEmpty()) || qc.getQuestSets().size() > 0) {
			
			/*
			 * For all of the QuestSets, loop through each of their quests. Check if the player has completed, may see
			 * or cannot see the quest. Create the inventory button of that quest accordingly to allow for a unique
			 * feel for quests inside the inventory.
			 */
			for(QuestSets qs : qc.getQuestSets()) {
				boolean hasAccess = true;
				int leftToAdd = 9 - qs.getQuests().size();
				for(int x = 0; x < qs.getQuests().size(); x++) {
					MeleeQuest mq = qs.getQuests().get(x);
					if(hasAccess) {
						if(mqp.hasCompleted(mq)) {
							InventoryButton ib = completed(mq);
							quests.add(ib);
						} else {
							InventoryButton ib = maySee(mq, mqp);
							quests.add(ib);
						}
					} else {
						InventoryButton ib = cannotSee(mq);
						quests.add(ib);
					}
					if(mqp.hasCompleted(mq)) continue;
					hasAccess = false;
				}
				for(int x = 0; x < leftToAdd; x++) {
					InventoryButton ib = doesNotExist();
					quests.add(ib);
				}
			}
		}
		
		/*
		 * Loop through all of the Singleton quests and create a button if they are started/not started or completed.
		 */
		if(!(qc.getSingleQuests().isEmpty()) || qc.getSingleQuests().size() > 0) {
			for(MeleeQuest mq : qc.getSingleQuests()) {
				if(mqp.hasCompleted(mq)) {
					InventoryButton ib = completed(mq);
					quests.add(ib);
				} else {
					InventoryButton ib = maySee(mq, mqp);
					quests.add(ib);
				}
			}
		}
		
		// Create the instance of the PageableInventoryManager using the 'quests' list made from above
		PageableInventoryManager pim = new PageableInventoryManager(54, ChatColor.translateAlternateColorCodes('&', StringReplacement.formatCategoryName(Main.getInstance().getQuestManager().getQuestInv(), qc)), quests);
		pim.open(p);
	}
	
	private static InventoryButton completed(MeleeQuest mq) {
		ItemStack i = new ItemStack(mq.getTheme().getCompletedItem());
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', mq.getTheme().getCompleted()) + mq.getName());
		i.setItemMeta(meta);
		InventoryButton ib = new InventoryButton(i) {
			@Override
			public void onPlayerClick(Player p, ClickAction a) {}
		};
		return ib;
	}
	
	private static InventoryButton maySee(final MeleeQuest mq, final MeleeQuestPlayer mqp) {
		ItemStack i = new ItemStack(mq.getMaterial());
		ItemMeta me = i.getItemMeta();
		me.setLore(mq.getLore());
		List<String> lore = me.getLore();
		if(mqp.hasQuest(mq)) {
			lore.add("");
			lore.addAll(mq.getProgress());
			if(mq.getXp() != 0 || mq.getPhysicalMoney() != 0 || mq.getVirtualMoney() != 0 || mq.getMeleeCoins() != 0) {
				lore.add("");
				lore.add(ChatColor.translateAlternateColorCodes('&', mq.getTheme().getMenuItems()) + "Rewards:");
				if(mq.getPhysicalMoney() != 0) {
					lore.add(ChatColor.translateAlternateColorCodes('&', mq.getTheme().getTabs()) + ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', StringReplacement.formatInt(Main.getInstance().getQuestManager().getPMFormat(), mq.getPhysicalMoney())));
				}
				if(mq.getVirtualMoney() != 0) {
					lore.add(ChatColor.translateAlternateColorCodes('&', mq.getTheme().getTabs()) + ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', StringReplacement.formatInt(Main.getInstance().getQuestManager().getVMFormat(), mq.getVirtualMoney())));
				}
				if(mq.getMeleeCoins() != 0) {
					lore.add(ChatColor.translateAlternateColorCodes('&', mq.getTheme().getTabs()) + ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', StringReplacement.formatInt(Main.getInstance().getQuestManager().getMMFormat(), mq.getMeleeCoins())));
				}
				if(mq.getXp() != 0) {
					lore.add(ChatColor.translateAlternateColorCodes('&', mq.getTheme().getTabs()) + ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', StringReplacement.formatInt(Main.getInstance().getQuestManager().getXPFormat(),  mq.getXp())));
				}
			}
			me.setDisplayName(ChatColor.translateAlternateColorCodes('&', mq.getTheme().getInProgress()) + mq.getName());
			NamespacedKey key = new NamespacedKey(Main.getInstance(), Main.getInstance().getDescription().getName());
			Glow g = new Glow(key);
			me.addEnchant(g, 1, true);
			lore.add("");
			lore.add(ChatColor.translateAlternateColorCodes('&', mq.getTheme().getMenuItems()) + "Click To Cancel Quest");
		} else {
			me.setDisplayName(ChatColor.translateAlternateColorCodes('&', mq.getTheme().getNotAccepted()) + mq.getName());
			if(mq.getXp() != 0 || mq.getPhysicalMoney() != 0 || mq.getVirtualMoney() != 0 || mq.getMeleeCoins() != 0) {
				lore.add("");
				lore.add(ChatColor.translateAlternateColorCodes('&', mq.getTheme().getMenuItems()) + "Rewards:");
				if(mq.getPhysicalMoney() != 0) {
					lore.add(ChatColor.translateAlternateColorCodes('&', StringReplacement.formatInt(Main.getInstance().getQuestManager().getPMFormat(), mq.getPhysicalMoney())));
				}
				if(mq.getVirtualMoney() != 0) {
					lore.add(ChatColor.translateAlternateColorCodes('&', StringReplacement.formatInt(Main.getInstance().getQuestManager().getVMFormat(), mq.getVirtualMoney())));
				}
				if(mq.getMeleeCoins() != 0) {
					lore.add(ChatColor.translateAlternateColorCodes('&', StringReplacement.formatInt(Main.getInstance().getQuestManager().getMMFormat(), mq.getMeleeCoins())));
				}
				if(mq.getXp() != 0) {
					lore.add(ChatColor.translateAlternateColorCodes('&', StringReplacement.formatInt(Main.getInstance().getQuestManager().getXPFormat(),  mq.getXp())));
				}
			}
			lore.add("");
			lore.add(ChatColor.translateAlternateColorCodes('&', mq.getTheme().getAccept()) + "Click to Accept!");
		}
		me.setLore(lore);
		i.setItemMeta(me);
		InventoryButton ib = new InventoryButton(i) {
			@Override
			public void onPlayerClick(Player p, ClickAction ca) {
				MeleeQuest mq2 = Main.getInstance().getQuestManager().getQuest(mq.getComputerName());
				if(!mqp.hasQuest(mq2)) {
					p.closeInventory();
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', StringReplacement.formatQuestName(Main.getInstance().getQuestManager().getQuestAccepted(), mq2)));
					mqp.addQuest(mq2);
				} else {
					p.closeInventory();
					CancelQuestInv cqi = new CancelQuestInv(mq, this);
					cqi.open(p);
				}
			}
		};
		return ib;
	}
	
	private static InventoryButton cannotSee(MeleeQuest mq) {
		ItemStack i = new ItemStack(mq.getTheme().getLockedItem());
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', mq.getTheme().getLocked()) + mq.getName());
		i.setItemMeta(im);
		InventoryButton ib = new InventoryButton(i) {
			@Override
			public void onPlayerClick(Player p, ClickAction ca) {}
		};
		return ib;
	}
	
	private static InventoryButton doesNotExist() {
		ItemStack i = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.BLACK + " ");
		i.setItemMeta(im);
		InventoryButton ib = new InventoryButton(i) {
			@Override
			public void onPlayerClick(Player p, ClickAction ca) {
				
			}
		};
		return ib;
	}
	
}