package com.terturl.MeleeCraftQuests.Quests.Subs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;

import com.terturl.MeleeCraftQuests.MeleeQuestPlayer;
import com.terturl.MeleeCraftQuests.Quests.MeleeQuest;
import com.terturl.MeleeCraftQuests.Quests.QuestType;
import com.terturl.MeleeCraftQuests.Quests.Utils.QuestTheme;

import net.md_5.bungee.api.ChatColor;

public class QuestMine extends MeleeQuest {

	protected Map<Material, Integer> blocks;
	protected Map<Material, Integer> blocksGot = new HashMap<Material, Integer>();
	
	public QuestMine(String name, Material mat, Map<Material, Integer> require, QuestTheme qt) {
		super(name, QuestType.MINE, mat, qt);
		blocks = require;
		for(Material e : blocks.keySet()) {
			blocksGot.put(e, Integer.valueOf(0));
		}
	}
	
	public boolean containsMaterial(Material e) {
		if(blocks.containsKey(e)) return true;
		return false;
	}
	
	public void updateEntity(Material e, MeleeQuestPlayer p) {
		if(blocksGot.get(e) >= blocks.get(e)) return;
		blocksGot.put(e, blocksGot.get(e) + 1);
		boolean eql = isEqual(blocks, blocksGot);
		if(eql) {
			onComplete(p);
		} else return;
	}
	
	@Override
	public List<String> getLore() {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.translateAlternateColorCodes('&', theme.getMenuItems()) + "Objectives:");
		List<Material> types = new ArrayList<Material>();
		List<Integer> numbers = new ArrayList<Integer>();
		for(Material t : blocks.keySet()) {
			types.add(t);
		}
		for(Integer i : blocks.values()) {
			numbers.add(i);
		}
		for(int x = 0; x < types.size(); x++) {
			lore.add(ChatColor.translateAlternateColorCodes('&', theme.getTabs()) + ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', format(theme.getMineFormat(), types.get(x), numbers.get(x))));
		}
		return lore;
	}
	
	@Override
	public List<String> getProgress() {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.translateAlternateColorCodes('&', theme.getMenuItems()) + "Progress:");
		List<Material> types = new ArrayList<Material>();
		List<Integer> numbers = new ArrayList<Integer>();
		for(Material t : blocksGot.keySet()) {
			types.add(t);
		}
		for(Integer i : blocksGot.values()) {
			numbers.add(i);
		}
		for(int x = 0; x < types.size(); x++) {
			lore.add(ChatColor.translateAlternateColorCodes('&', theme.getTabs()) + ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', format(theme.getMineProgress(), types.get(x), numbers.get(x))));
		}
		return lore;
	}
	
	public void setBlockValue(Material et, Integer i) {
		blocksGot.put(et, i);
	}
	
	public String format(String s, Material et, Integer num) {
		String tmp = et.toString().toLowerCase() + "s";
		int total = blocks.get(et);
		String first = tmp.substring(0, 1).toUpperCase();
		String rest = tmp.substring(1);
		s = s.replaceAll("%block%", first + rest);
		s = s.replaceAll("%number%", String.valueOf(num));
		s = s.replaceAll("%final%", String.valueOf(total));
		return s;
	}
	
	public Map<Material, Integer> getGot() {
		return blocksGot;
	}
	
	@Override
	public void onComplete(MeleeQuestPlayer p) {
		super.onComplete(p);
	}
	
	private Boolean isEqual(Map<Material, Integer> first, Map<Material, Integer> second) {
		if(first.size() != second.size()) return false;
		return first.entrySet().stream().allMatch(e -> e.getValue().equals(second.get(e.getKey())));
	}
	
}