package com.terturl.BookNews;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.terturl.MeleeCraftEssentials.Utils.StringUtil;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class BookManager {

	private List<ItemStack> books = new ArrayList<ItemStack>();
	
	public BookManager() {
		createBooks();
	}
	
	private void createBooks() {
		File main = BookNews.getInstance().getConfiguration().getMainDirectory();
		for(File files : main.listFiles()) {
			if(files.isDirectory()) continue;
			ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
			BookMeta bookM = (BookMeta)item.getItemMeta();
			bookM.setAuthor("MeleeCraft");
			bookM.setTitle(files.getName());
			FileConfiguration config = YamlConfiguration.loadConfiguration(files);
			List<TextComponent> words = new ArrayList<TextComponent>();
			for(String page : config.getConfigurationSection("pages").getKeys(false)) {
				BookNews.getInstance().getLogger().info("Page: " + page);
				for(String lines : config.getStringList("pages." + page)) {
					Pattern ClickPat = Pattern.compile("\\{(.*?)\\}\\[(.*?)\\]");
					Matcher match = ClickPat.matcher(lines);
					BookNews.getInstance().getLogger().info(lines);
					while(match.find()) {
						lines.replaceAll("\\{(.*?)\\}\\[(.*?)\\]", "");
						String Matched = match.group(1);
						String Replace = match.group(2);
						String Command = Matched.substring(0, 3);
						String Extra = Matched.substring(3);
						BookNews.getInstance().getLogger().info("--" + Command + "||" + Extra);
						BookNews.getInstance().getLogger().info("----" + Replace);
						StringUtil su = new StringUtil(Replace);
						if(Command.equalsIgnoreCase("CW:")) {
							su.addClick(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, Command));
						} else if(Command.equalsIgnoreCase("CC:")) {
							su.addClick(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, Command));
						} else if(Command.equalsIgnoreCase("HT:")) {
							su.addHover(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.translateAlternateColorCodes('&', Replace))));
						} else {
							
						}
						words.add(su.getTextComp());
					}
					BookNews.getInstance().getLogger().info(lines);
				}
				BaseComponent[] bc = new BaseComponent[words.size()];
				words.toArray(bc);
				bookM.spigot().addPage(bc);
			}
			BookNews.getInstance().getLogger().log(Level.INFO, "[*] Added book " + bookM.getTitle());
			item.setItemMeta(bookM);
			books.add(item);
		}
	}
	
	public List<ItemStack> getBooks() {
		return books;
	}
	
}