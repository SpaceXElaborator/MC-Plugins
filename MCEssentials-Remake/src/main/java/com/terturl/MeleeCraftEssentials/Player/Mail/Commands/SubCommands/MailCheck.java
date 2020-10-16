package com.terturl.MeleeCraftEssentials.Player.Mail.Commands.SubCommands;

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
import org.bukkit.inventory.meta.SkullMeta;

import com.terturl.MeleeCraftEssentials.MCEssentials;
import com.terturl.MeleeCraftEssentials.Commands.CraftCommand;
import com.terturl.MeleeCraftEssentials.Inventory.ClickAction;
import com.terturl.MeleeCraftEssentials.Inventory.InventoryButton;
import com.terturl.MeleeCraftEssentials.Inventory.PageableInventoryManager;
import com.terturl.MeleeCraftEssentials.Player.Mail.MailDB;
import com.terturl.MeleeCraftEssentials.Player.Mail.Utils.MailCarrier;
import com.terturl.MeleeCraftEssentials.Player.Mail.Utils.PlayerMail;

public class MailCheck extends CraftCommand {

	public MailCheck() {
		super("check");
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void handleCommand(Player p, String[] args) {
		String pName = p.getName();
		
		//Get every message that is to the player p
		MailDB db = (MailDB)MCEssentials.getInstance().getDBManager().getDatabase("mail");
		List<PlayerMail> dbObjects = db.getMessages(pName);
		
		//List of every button that is to be added
		List<InventoryButton> buttons = new ArrayList<InventoryButton>();
		
		//Get the configuration structure for the Skull-Name
		final YamlConfiguration config = MCEssentials.getInstance().getConfiguration().loadConfig("itemstructure.yml");
		
		//Placeholder for players
		final Map<String, MailCarrier> mailC = new HashMap<String, MailCarrier>();
		
		//Get every PlayerMail
		for(final PlayerMail pm : dbObjects) {
			if(!mailC.containsKey(pm.getFrom())) {
				mailC.put(pm.getFrom(), new MailCarrier(pm.getFrom()));
			}
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Create message button
			String itemN = sanatize(pm, config.getString("Item-Name"));
			Material m = Material.getMaterial(config.getString("Item-Type").toUpperCase());
			List<String> tempLore = config.getStringList("Item-Lore");
			ItemStack message = new ItemStack(m);
			ItemMeta messageMeta = message.getItemMeta();
			messageMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', itemN));
			List<String> lore = new ArrayList<String>();
			for(String s2 : tempLore) {
				lore.add(ChatColor.translateAlternateColorCodes('&', sanatize(pm, s2)));
			}
			messageMeta.setLore(lore);
			message.setItemMeta(messageMeta);
			
			mailC.get(pm.getFrom()).getMail().add(new InventoryButton(message) {
				@Override
				public void onPlayerClick(Player p, ClickAction a) {
					return;
				}
			});
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		}
		for(String s : mailC.keySet()) {
			//Set the name
			String namePatt = config.getString("Skull-Name").replaceAll("%player%", s);
			ItemStack item = new ItemStack(Material.PLAYER_HEAD);
			
			//Get the SkullMeta
			SkullMeta meta = (SkullMeta)item.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', namePatt));
			meta.setOwningPlayer(Bukkit.getOfflinePlayer(s));
			item.setItemMeta(meta);
			
			//Add skull to inventory of buttons.
			//When clicked, show messages from that player
			buttons.add(new InventoryButton(item) {
				@Override
				public void onPlayerClick(final Player p, ClickAction a) {
					p.closeInventory();
					String s = ChatColor.stripColor(getItem().getItemMeta().getDisplayName());
					List<InventoryButton> messages = mailC.get(s).getMail();
					final PageableInventoryManager pim = new PageableInventoryManager(27, ChatColor.translateAlternateColorCodes('&', config.getString("Inv-NamePlayer").replaceAll("%player%", s)), messages);
					Bukkit.getScheduler().runTaskLater(MCEssentials.getInstance(), new Runnable() {
						public void run() {
							pim.open(p);
						}
						
					}, 2L);
				}
			});
		}
		
		PageableInventoryManager pim = new PageableInventoryManager(18, ChatColor.translateAlternateColorCodes('&', config.getString("Inv-Name")), buttons);
		pim.open(p);
	}
	
	private String sanatize(PlayerMail pm, String s) {
		s = s.replaceAll("%message%", pm.getMessage());
		s = s.replaceAll("%server%", pm.getServer());
		s = s.replaceAll("%date%", pm.getDate());
		s = s.replaceAll("%player%", pm.getFrom());
		return s;
	}
	
}
