package com.terturl.MeleeCraftQuests.Inventories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.terturl.MeleeCraftEssentials.Inventory.ClickAction;
import com.terturl.MeleeCraftEssentials.Inventory.InventoryButton;
import com.terturl.MeleeCraftEssentials.Inventory.InventoryUI;
import com.terturl.MeleeCraftQuests.Main;
import com.terturl.MeleeCraftQuests.Utils.StringReplacement;

/**
 * Creates a list of inventories that can be traversed using a forward and back button.
 * @author Sean Rahman
 *
 */
public class PageableInventoryManager {
	
	protected Map<Integer, InventoryUI> pages = new HashMap<Integer, InventoryUI>();
	protected Integer invSize;
	protected List<InventoryButton> items;
	protected String name;
	protected Integer currPage = 0;
	
	/**
	 * Creates a PageableInventoryManager of the given size, title, and what buttons will be filled
	 * into the inventory. The Next and Back buttons are created automatically
	 * @param size		Size of the inventory
	 * @param title		Name of the inventory
	 * @param c			List of InventoryButton's to add
	 */
	public PageableInventoryManager(int size, String title, List<InventoryButton> c) {
		invSize = size;
		items = c;
		name = title;
		
		init();
	}
	
	/**
	 * Will open the given PageableInventory using the first initial page and sets the page number to 0
	 * @param p		Player to have open
	 */
	public void open(Player p) {
		pages.get(0).open(p);
		currPage = 0;
	}
	
	protected void init() {
		int numOfItems = items.size();
		while(numOfItems > 0) {
			int itemsAdded = 0;
			InventoryUI pi = new InventoryUI(invSize, ChatColor.translateAlternateColorCodes('&', StringReplacement.formatPageNum(name, pages.size() + 1)));
			for(int x = 0; x < 7; x++) {
				int y = x+46;
				ItemStack i = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
				ItemMeta im = i.getItemMeta();
				im.setDisplayName(" ");
				i.setItemMeta(im);
				InventoryButton ib = new InventoryButton(i) {};
				pi.addButton(ib, y);
				itemsAdded+=1;
			}
			
			if((numOfItems == invSize) && (pages.isEmpty())) {
				pages.put(pages.size(), pi);
				for(InventoryButton iis : items) {
					pi.addButton(iis);
				}
				pi.updateInventory();
				break;
			}
			if(!(pages.isEmpty())) {
				pi.addButton(new InventoryButton(backButton()) {
					@Override
					public void onPlayerClick(Player p, ClickAction a) {
						p.closeInventory();
						pages.get(currPage - 1).open(p);
						currPage -= 1;
					}
				}, invSize - 9);
				itemsAdded+=1;
			} else {
				InventoryButton ib = new InventoryButton(backButton()) {
					@Override
					public void onPlayerClick(Player p, ClickAction a) {
						p.closeInventory();
						CategoryInvList cil = new CategoryInvList();
						cil.open(p);
					}
				};
				pi.addButton(ib, invSize-9);
				itemsAdded+=1;
			}
			pages.put(pages.size(), pi);
			if(!(numOfItems<=(invSize-1))) {
				pi.addButton(new InventoryButton(nextButton()) {
					@Override
					public void onPlayerClick(Player p, ClickAction a) {
						p.closeInventory();
						pages.get(currPage + 1).open(p);
						currPage += 1;
					}
				}, invSize - 1);
				itemsAdded+=1;
			} else {
				ItemStack i = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
				ItemMeta im = i.getItemMeta();
				im.setDisplayName(" ");
				i.setItemMeta(im);
				InventoryButton ib = new InventoryButton(i) {};
				pi.addButton(ib, invSize - 1);
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
		ItemStack i = new ItemStack(Material.valueOf(Main.getInstance().getQuestManager().getNextButton().toUpperCase()));
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getQuestManager().getNextButtonName()));
		i.setItemMeta(im);
		return i;
	}
	
	private ItemStack backButton() {
		ItemStack i = new ItemStack(Material.valueOf(Main.getInstance().getQuestManager().getBackButton().toUpperCase()));
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getQuestManager().getBackButtonName()));
		i.setItemMeta(im);
		return i;
	}
	
}