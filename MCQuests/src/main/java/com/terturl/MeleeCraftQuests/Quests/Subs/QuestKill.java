package com.terturl.MeleeCraftQuests.Quests.Subs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import com.terturl.MeleeCraftQuests.MeleeQuestPlayer;
import com.terturl.MeleeCraftQuests.Quests.MeleeQuest;
import com.terturl.MeleeCraftQuests.Quests.QuestType;
import com.terturl.MeleeCraftQuests.Quests.Utils.QuestTheme;

import net.md_5.bungee.api.ChatColor;

public class QuestKill extends MeleeQuest {

	protected Map<EntityType, Integer> killRequir;
	protected Map<EntityType, Integer> killGot = new HashMap<EntityType, Integer>();
	
	public QuestKill(String qName, Material mat, Map<EntityType, Integer> required, QuestTheme qt) {
		super(qName, QuestType.KILL, mat, qt);
		killRequir = required;
		for(EntityType e : killRequir.keySet()) {
			killGot.put(e, Integer.valueOf(0));
		}
	}
	
	public void setEntity(EntityType et, Integer i) {
		killGot.put(et, i);
	}
	
	public boolean containsEntity(EntityType e) {
		if(killRequir.containsKey(e)) return true;
		return false;
	}
	
	public void updateEntity(EntityType e, MeleeQuestPlayer p) {
		if(killGot.get(e) >= killRequir.get(e)) return;
		killGot.put(e, killGot.get(e) + 1);
		boolean eql = isEqual(killRequir, killGot);
		if(eql) {
			onComplete(p);
		} else return;
	}
	
	@Override
	public List<String> getLore() {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.translateAlternateColorCodes('&', theme.getMenuItems()) + "Objectives:");
		List<EntityType> types = new ArrayList<EntityType>();
		List<Integer> numbers = new ArrayList<Integer>();
		for(EntityType t : killRequir.keySet()) {
			types.add(t);
		}
		for(Integer i : killRequir.values()) {
			numbers.add(i);
		}
		for(int x = 0; x < types.size(); x++) {
			lore.add(ChatColor.translateAlternateColorCodes('&', theme.getTabs()) + ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', format(theme.getKillFormat(), types.get(x), numbers.get(x))));
		}
		return lore;
	}
	
	@Override
	public List<String> getProgress() {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.translateAlternateColorCodes('&', theme.getMenuItems()) + "Progress:");
		List<EntityType> types = new ArrayList<EntityType>();
		List<Integer> numbers = new ArrayList<Integer>();
		for(EntityType t : killGot.keySet()) {
			types.add(t);
		}
		for(Integer i : killGot.values()) {
			numbers.add(i);
		}
		for(int x = 0; x < types.size(); x++) {
			lore.add(ChatColor.translateAlternateColorCodes('&', theme.getTabs()) + ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', format(theme.getKillProgress(), types.get(x), numbers.get(x))));
		}
		return lore;
	}
	
	public String format(String s, EntityType m, Integer i) {
		String tmp = m.toString().toLowerCase().replaceAll("_", " ");
		int total = killRequir.get(m);
		String first = tmp.substring(0, 1).toUpperCase();
		String rest = tmp.substring(1);
		s = s.replaceAll("%entity%", first + rest);
		s = s.replaceAll("%number%", String.valueOf(i));
		s = s.replaceAll("%final%", String.valueOf(total));
		return s;
	}
	
	public Map<EntityType, Integer> getGot() {
		return killGot;
	}
	
	@Override
	public void onComplete(MeleeQuestPlayer p) {
		super.onComplete(p);
	}
	
	private Boolean isEqual(Map<EntityType, Integer> first, Map<EntityType, Integer> second) {
		if(first.size() != second.size()) return false;
		return first.entrySet().stream().allMatch(e -> e.getValue().equals(second.get(e.getKey())));
	}
	
}