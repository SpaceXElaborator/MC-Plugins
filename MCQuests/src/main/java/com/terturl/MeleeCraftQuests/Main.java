package com.terturl.MeleeCraftQuests;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.terturl.MeleeCraftEssentials.MCEssentials;
import com.terturl.MeleeCraftQuests.Commands.ShowQuests;
import com.terturl.MeleeCraftQuests.Listeners.BreedListener;
import com.terturl.MeleeCraftQuests.Listeners.CollectListener;
import com.terturl.MeleeCraftQuests.Listeners.KillListener;
import com.terturl.MeleeCraftQuests.Listeners.MineListener;
import com.terturl.MeleeCraftQuests.Listeners.TameListener;
import com.terturl.MeleeCraftQuests.Listeners.TravelListener;
import com.terturl.MeleeCraftQuests.Listeners.Player.onJoin;
import com.terturl.MeleeCraftQuests.Listeners.Player.onLeave;
import com.terturl.MeleeCraftQuests.Managers.MeleeQuestCategoryManager;
import com.terturl.MeleeCraftQuests.Managers.MeleeQuestPlayerManager;
import com.terturl.MeleeCraftQuests.Managers.MeleeQuestQuestManager;
import com.terturl.MeleeCraftQuests.Managers.MeleeQuestThemeManager;
import com.terturl.MeleeCraftQuests.Quests.Utils.Glow;

public class Main extends JavaPlugin {

	protected MeleeQuestPlayerManager playerManager;
	protected MeleeQuestQuestManager questManager;
	protected MeleeQuestThemeManager themeManager;
	protected MeleeQuestCategoryManager categoryManager;
	protected static Main instance;
	
	public void onEnable() {
		instance = this;
		File mainDir = new File("plugins/MeleeQuest");
		if(!mainDir.exists()) mainDir.mkdir();
		
		registerListeners();
		registerCommands();
		
		NamespacedKey key = new NamespacedKey(this, getDescription().getName());
		Glow g = new Glow(key);
		if(!Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(g)) {
			try {
				Field f = Enchantment.class.getDeclaredField("acceptingNew");
				f.setAccessible(true);
				f.set(null, true);
				Enchantment.registerEnchantment(g);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		categoryManager = new MeleeQuestCategoryManager();
		themeManager = new MeleeQuestThemeManager();
		questManager = new MeleeQuestQuestManager();
		playerManager = new MeleeQuestPlayerManager();
	}
	
	protected void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new onJoin(), this);
		pm.registerEvents(new onLeave(), this);
		
		pm.registerEvents(new BreedListener(), this);
		pm.registerEvents(new KillListener(), this);
		pm.registerEvents(new TravelListener(), this);
		pm.registerEvents(new TameListener(), this);
		pm.registerEvents(new MineListener(), this);
		pm.registerEvents(new CollectListener(), this);
		//pm.registerEvents(new CraftListener(), this);
	}
	
	protected void registerCommands() {
		MCEssentials.getInstance().registerCommand(new ShowQuests());
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	public MeleeQuestPlayerManager getPlayerManager() {
		return playerManager;
	}
	
	public MeleeQuestQuestManager getQuestManager() {
		return questManager;
	}
	
	public MeleeQuestThemeManager getThemeManager() {
		return themeManager;
	}
	
	public MeleeQuestCategoryManager getCategoryManager() {
		return categoryManager;
	}
	
}