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

public class QuestCollect extends MeleeQuest {

	protected Map<Material, Integer> collRequire;
	protected Map<Material, Integer> collGot = new HashMap<Material, Integer>();
	
	public QuestCollect(String name, Material mat, Map<Material, Integer> required, QuestTheme qt) {
		super(name, QuestType.COLLECT, mat, qt);
		collRequire = required;
		for(Material e : collRequire.keySet()) {
			collGot.put(e, Integer.valueOf(0));
		}
	}
	
	public boolean containsMaterial(Material e) {
		if(collRequire.containsKey(e)) return true;
		return false;
	}
	
	public void setMaterialValue(Material m, Integer i) {
		collGot.put(m, i);
	}
	
	public void updateMaterial(Material e, MeleeQuestPlayer p) {
		if(collGot.get(e) >= collRequire.get(e)) return;
		collGot.put(e, collGot.get(e) + 1);
		boolean eql = isEqual(collRequire, collGot);
		if(eql) {
			onComplete(p);
		}
	}
	
	@Override
	public List<String> getLore() {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.translateAlternateColorCodes('&', theme.getMenuItems()) + "Objectives:");
		List<Material> types = new ArrayList<Material>();
		List<Integer> numbers = new ArrayList<Integer>();
		for(Material t : collRequire.keySet()) {
			types.add(t);
		}
		for(Integer i : collRequire.values()) {
			numbers.add(i);
		}
		for(int x = 0; x < types.size(); x++) {
			lore.add(ChatColor.translateAlternateColorCodes('&', theme.getTabs()) + ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', format(theme.getCollectFormat(), types.get(x), numbers.get(x))));
		}
		return lore;
	}
	
	@Override
	public List<String> getProgress() {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.translateAlternateColorCodes('&', theme.getMenuItems()) + "Progress:");
		List<Material> types = new ArrayList<Material>();
		List<Integer> numbers = new ArrayList<Integer>();
		for(Material t : collGot.keySet()) {
			types.add(t);
		}
		for(Integer i : collGot.values()) {
			numbers.add(i);
		}
		for(int x = 0; x < types.size(); x++) {
			lore.add(ChatColor.translateAlternateColorCodes('&', theme.getTabs()) + ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', format(theme.getCollectProgress(), types.get(x), numbers.get(x))));
		}
		return lore;
	}
	
	public String format(String s, Material m, Integer i) {
		String tmp = m.toString().toLowerCase().replaceAll("_", " ");
		int total = collRequire.get(m);
		String first = tmp.substring(0, 1).toUpperCase();
		String rest = tmp.substring(1);
		s = s.replaceAll("%material%", first + rest);
		s = s.replaceAll("%number%", String.valueOf(i));
		s = s.replaceAll("%final%", String.valueOf(total));
		return s;
	}
	
	public Map<Material, Integer> getGot() {
		return collRequire;
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