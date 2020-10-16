package com.terturl.MeleeCraftQuests.Managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.terturl.MeleeCraftQuests.Main;
import com.terturl.MeleeCraftQuests.Quests.QuestHoldingTypes.QuestCategory;

public class MeleeQuestCategoryManager {

	private List<QuestCategory> categories = new ArrayList<QuestCategory>();
	
	/*
	 * Creates the main Categories directory and fills in a default category if none is made.
	 * Will go through and read all files to create categories of specific values
	 */
	public MeleeQuestCategoryManager() {
		File mainDir = new File("plugins/MeleeQuest/Categories");
		if(!mainDir.exists()) {
			mainDir.mkdir();
			File f = new File(mainDir, "Uncategorized.yml");
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(f);
			config.set("Name", "UnCategorized");
			config.set("Color", "&6&l");
			config.set("Item", Material.CRAFTING_TABLE.toString());
			config.set("Default", true);
			config.set("Slot", 10);
			List<String> lore = new ArrayList<String>();
			lore.add("&fThis is all of the uncategorized");
			lore.add("&fquests in the game. If you dont");
			lore.add("&fset a category, itll try to join");
			lore.add("&fThis one first");
			config.set("Lore", lore);
			try {
				config.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		for(File f : mainDir.listFiles()) {
			FileConfiguration config = YamlConfiguration.loadConfiguration(f);
			String name = config.getString("Name");
			String color = config.getString("Color");
			Material mat = Material.valueOf(config.getString("Item").toUpperCase());
			Boolean isDef = config.getBoolean("Default");
			Integer slot = config.getInt("Slot");
			List<String> lore = config.getStringList("Lore");
			QuestCategory qc = new QuestCategory(name, color, mat, slot, isDef, lore);
			categories.add(qc);
		}
		
		Main.getInstance().getLogger().log(Level.INFO, "Loaded: " + categories.size() + " categories");
	}
	
	/**
	 * Will return the QuestCategory of the given name or null if not found.
	 * @param s		Name of the QuestCategory
	 * @return QuestCategory
	 * @see com.terturl.MeleeCraftQuests.Quests.QuestHoldingTypes.QuestCategory
	 */
	public QuestCategory getCategory(String s) {
		for(QuestCategory qc : categories) {
			if(qc.getName().equalsIgnoreCase(s)) return qc;
		}
		return null;
	}
	
	/**
	 * Will return the default quest category or null if not found
	 * @return QuestCategory
	 */
	public QuestCategory getDefault() {
		for(QuestCategory qc : categories) {
			if(qc.isDefault()) return qc;
		}
		return null;
	}
	
	/**
	 * Will return the list of all currently working Categories
	 * @return categories
	 */
	public List<QuestCategory> getCategory() {
		return categories;
	}
	
}