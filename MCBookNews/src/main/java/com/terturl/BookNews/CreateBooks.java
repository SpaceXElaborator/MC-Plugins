package com.terturl.BookNews;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.terturl.MeleeCraftEssentials.Utils.StringUtil;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class CreateBooks {
	
	public static List<ItemStack> books = new ArrayList<ItemStack>();
	
	@SuppressWarnings("deprecation")
	public static void createBooksFromFiles(File[] files) {
		for(File f : files) {
			//Create the ItemStack and BookMeta for the file with correct configuration
			ItemStack i = new ItemStack(Material.WRITTEN_BOOK);
			BookMeta bm = (BookMeta) i.getItemMeta();
			FileConfiguration config = YamlConfiguration.loadConfiguration(f);
			bm.setAuthor(config.getString("Author"));
			bm.setTitle(config.getString("Title"));
			//Get every string within the file for the different pages
			List<TextComponent> words = new ArrayList<TextComponent>();
			for(String s : config.getConfigurationSection("pages").getKeys(false)) {
				words.clear();
				List<String> pageCont = config.getStringList("pages." + s);
				for(String cont : pageCont) {
					StringUtil us = new StringUtil();
					HoverEvent he = null;
					ClickEvent ce = null;
					if(cont.contains("{")) {
						int Start = cont.indexOf("{") + 1;
						int Final = cont.indexOf("}");
						
						//Get just that part of the string
						String cmd = cont.substring(Start, Final);
						String toRemove = cont.substring(Start-1, Final+1);
						
						//Remove it from the original string
						cont = cont.replace(toRemove, "");

						String[] splits = cmd.split(":");
						switch(splits[0]) {
						case "CU":
							ce = new ClickEvent(ClickEvent.Action.OPEN_URL, splits[1]);
							break;
						case "CS":
							ce = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, splits[1]);
							break;
						case "CC":
							ce = new ClickEvent(ClickEvent.Action.RUN_COMMAND, splits[1]);
							break;
						}
					}
					if(cont.contains("[")) {
						//Get the start and final for the command
						int Start = cont.indexOf("[") + 1;
						int Final = cont.indexOf("]");
						
						//Get just that part of the string
						String cmd = cont.substring(Start, Final);
						String toRemove = cont.substring(Start-1, Final+1);
						
						//Remove it from the original string
						cont = cont.replace(toRemove, "");
						String[] splits = cmd.split(":");
						he = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', splits[1])).create());
					}
					TextComponent tc = new TextComponent(ChatColor.translateAlternateColorCodes('&', cont + "\n"));
					us.setMain(tc);
					if(he != null) {
						us.addHover(he);
					}
					if(ce != null) {
						us.addClick(ce);
					}
					words.add(tc);
				}
				//Create a new BaseComponent array every page and add all of 'words' to it
				BaseComponent[] bc = new BaseComponent[words.size()];
				words.toArray(bc);
				//Add the page and continue on
				bm.spigot().addPage(bc);
			}
			BookNews.getInstance().getLogger().log(Level.INFO, "[*] Added book " + bm.getTitle());
			i.setItemMeta(bm);
			books.add(i);
		}
	}
	
}