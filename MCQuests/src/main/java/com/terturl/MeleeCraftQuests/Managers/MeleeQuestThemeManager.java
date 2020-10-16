package com.terturl.MeleeCraftQuests.Managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.terturl.MeleeCraftQuests.Quests.Utils.QuestTheme;

public class MeleeQuestThemeManager {

	public List<QuestTheme> themes = new ArrayList<QuestTheme>();
	
	/*
	 * Creates the Themes dir and creates a default theme if none is present.
	 * Will loop through every file and create a theme based on the inputs given
	 */
	public MeleeQuestThemeManager() {
		File themeDir = new File("plugins/MeleeQuest/Themes");
		if(!themeDir.exists()) {
			themeDir.mkdir();
			createDefaultTheme(themeDir);
		}
		
		for(File f : themeDir.listFiles()) {
			FileConfiguration config = YamlConfiguration.loadConfiguration(f);
			QuestTheme qt = new QuestTheme(config.getString("Name").toUpperCase());
			qt.setMenuItems(config.getString("MenuItems"));
			qt.setTabs(config.getString("Tabs"));
			qt.setNotAccepted(config.getString("NotAccepted"));
			qt.setInProgress(config.getString("InProgress"));
			qt.setCompleted(config.getString("Completed"));
			qt.setLocked(config.getString("Locked"));
			qt.setAccept(config.getString("Accept"));
			
			qt.setLockedItem(Material.valueOf(config.getString("LockedItem").toUpperCase()));
			qt.setCompletedItem(Material.valueOf(config.getString("CompletedItem").toUpperCase()));
			
			qt.setBreedFormat(config.getString("BreedFormat"));
			qt.setCollectFormat(config.getString("CollectFormat"));
			qt.setKillFormat(config.getString("KillFormat"));
			qt.setMineFormat(config.getString("MineFormat"));
			qt.setTameFormat(config.getString("TameFormat"));
			qt.setTravelFormat(config.getString("TravelFormat"));
			qt.setCraftFormat(config.getString("CraftFormat"));
			
			qt.setBreedProgress(config.getString("BreedProgress"));
			qt.setCollectProgress(config.getString("CollectProgress"));
			qt.setKillProgress(config.getString("KillProgress"));
			qt.setMineProgress(config.getString("MineProgress"));
			qt.setTameProgress(config.getString("TameProgress"));
			qt.setTravelProgress(config.getString("TravelProgress"));
			qt.setCraftProgress(config.getString("CraftProgress"));
			themes.add(qt);
		}
	}
	
	/*
	 * Creates the default QuestTheme that will be used if no other QuestTheme was entered.
	 */
	private void createDefaultTheme(File f) {
		File def = new File(f, "default.yml");
		try {
			def.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(def);
		config.set("Name", "default");
		config.set("MenuItems", "&e&n");
		config.set("Tabs", "&6>>");
		config.set("CompletedItem", Material.GREEN_STAINED_GLASS_PANE.toString());
		config.set("LockedItem", Material.RED_STAINED_GLASS_PANE.toString());
		config.set("NotAccepted", "&4&l");
		config.set("InProgress", "&e&l");
		config.set("Completed", "&a&l");
		config.set("Locked", "&4&l");
		config.set("Accept", "&e&n");
		
		config.set("BreedFormat", "&fBreed %animal% %number% time(s)!");
		config.set("BreedProgress", "&fBred %number%/%final% of %animal% so far!");
		
		config.set("CollectFormat", "&fCollect %number% %material%!");
		config.set("CollectProgress", "&fYou have collected %number%/%final% of %material% so far!");
		
		config.set("KillFormat", "&fSlay %number% %entity%!");
		config.set("KillProgress", "&fYou have killed %number%/%final% of %entity%!");
		
		config.set("MineFormat", "&fMine %number% %block%");
		config.set("MineProgress", "&fYou have mined %number%/%final% %block% thus far");
		
		config.set("TameFormat", "&fTame %entity% %number% of times(1)");
		config.set("TameProgress", "&fTamed %number%/%final% %entity%");
		
		config.set("TravelFormat", "Travel to %loc%");
		config.set("TravelProgress", "Traveled to %loc%: %hasmade%");
		
		config.set("CraftFormat", "Craft %number% %material%s!");
		config.set("CraftProgress", "You've craft %number%/%final% of %material%");
		
		try {
			config.save(def);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Will return the QuestTheme by name or return null if not found
	 * @param s		Name of the QuestTheme
	 * @return QuestTheme
	 * @see com.terturl.MeleeCraftQuests.Quests.Utils.QuestTheme
	 */
	public QuestTheme getTheme(String s) {
		for(QuestTheme qt : themes) {
			if(qt.getName().equals(s)) return qt;
		}
		return null;
	}
	
}