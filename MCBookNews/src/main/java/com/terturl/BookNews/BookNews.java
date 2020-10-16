package com.terturl.BookNews;

import org.bukkit.plugin.java.JavaPlugin;

import com.terturl.MeleeCraftEssentials.MCEssentials;
import com.terturl.MeleeCraftEssentials.Utils.ConfigHandler;

public class BookNews extends JavaPlugin {

	private static BookNews instance;
	private ConfigHandler config;
	private CreateBooks bm;
	
	public void onEnable() {
		instance = this;
		config = new ConfigHandler(this);
		config.firstLoad();
		MCEssentials.getInstance().registerListener(new PlayJoin());
		CreateBooks.createBooksFromFiles(getConfiguration().getMainDirectory().listFiles());
	}
	
	public static BookNews getInstance() {
		return instance;
	}
	
	public ConfigHandler getConfiguration() {
		return config;
	}
	
	public CreateBooks getBookManager() {
		return bm;
	}
	
}