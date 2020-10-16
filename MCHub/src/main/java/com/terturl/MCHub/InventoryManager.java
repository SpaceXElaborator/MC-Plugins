package com.terturl.MCHub;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.terturl.MeleeCraftEssentials.MCEssentials;
import com.terturl.MeleeCraftEssentials.Inventory.ClickAction;
import com.terturl.MeleeCraftEssentials.Inventory.InventoryButton;
import com.terturl.MeleeCraftEssentials.Inventory.InventoryUI;

import net.md_5.bungee.api.ChatColor;

public class InventoryManager extends InventoryUI {
	
	public InventoryManager(Integer slots, String name) {
		super(slots, name);
		
		final YamlConfiguration config = MCHub.getInstance().getConfiguration().loadConfig("serverselector.yml");
		for(final String s : config.getKeys(false)) {
			MCEssentials.getInstance().getLogger().log(Level.INFO, s);
			ItemStack i = new ItemStack(Material.valueOf(config.getString(s + ".Icon")));
			ItemMeta meta = i.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString(s + ".Name")));
			List<String> lore = new ArrayList<String>();
			for(String s2 : config.getStringList(s + ".Lore")) {
				lore.add(ChatColor.translateAlternateColorCodes('&', s2));
			}
			meta.setLore(lore);
			i.setItemMeta(meta);
			
			addButton(new InventoryButton(i) {
				@Override
				public void onPlayerClick(final Player p, ClickAction a) {
					for(String s : config.getStringList(s + ".Commands")) {
						p.performCommand(s);
					}
				}
			}, config.getInt(s + ".Slot"));
		}
		updateInventory();
	}
	
}