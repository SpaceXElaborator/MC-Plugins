package com.terturl.MeleeCraftQuests.Quests.Subs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

import com.terturl.MeleeCraftQuests.Quests.MeleeQuest;
import com.terturl.MeleeCraftQuests.Quests.QuestType;
import com.terturl.MeleeCraftQuests.Quests.Utils.QuestTheme;

import net.md_5.bungee.api.ChatColor;

public class QuestTravel extends MeleeQuest {

	protected Location locRequir;
	
	public QuestTravel(String qName, Material mat, Location loc, QuestTheme qt) {
		super(qName, QuestType.TRAVEL, mat, qt);
		locRequir = loc;
	}
	
	@Override
	public List<String> getLore() {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.translateAlternateColorCodes('&', theme.getMenuItems()) + "Objectives:");
		lore.add(ChatColor.translateAlternateColorCodes('&', getTheme().getTabs()) + ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', format(getTheme().getTravelFormat(), locRequir)));
		return lore;
	}
	
	@Override
	public List<String> getProgress() {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.translateAlternateColorCodes('&', theme.getMenuItems()) + "Progress:");
		lore.add(ChatColor.translateAlternateColorCodes('&', getTheme().getTabs()) + ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', format(getTheme().getTravelProgress(), locRequir)));
		return lore;
	}
	
	public String format(String s, Location loc) {
		String x = String.valueOf(loc.getBlockX());
		String y = String.valueOf(loc.getBlockY());
		String z = String.valueOf(loc.getBlockZ());
		s = s.replaceAll("%loc%", "{" + x + ":" + y + ":" + z + "}");
		if(s.contains("%hasmade%")) {
			if(locRequir == null) {
				s = s.replaceAll("%hasmade%", "False");
			}
		}
		return s;
	}

	public Location getLoc() {
		return locRequir;
	}
	
}