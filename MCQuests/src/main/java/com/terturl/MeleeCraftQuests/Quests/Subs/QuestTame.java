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

public class QuestTame extends MeleeQuest {

	protected Map<EntityType, Integer> tameRequir;
	protected Map<EntityType, Integer> tameGot = new HashMap<EntityType, Integer>();
	
	public QuestTame(String name, Material mat, Map<EntityType, Integer> required, QuestTheme qt) {
		super(name, QuestType.TAME, mat, qt);
		tameRequir = required;
		for(EntityType e : tameRequir.keySet()) {
			tameGot.put(e, Integer.valueOf(0));
		}
	}
	
	public void setEntity(EntityType et, Integer i) {
		tameGot.put(et, i);
	}
	
	public boolean containsEntity(EntityType e) {
		if(tameRequir.containsKey(e)) return true;
		return false;
	}
	
	public void updateEntity(EntityType e, MeleeQuestPlayer p) {
		if(tameGot.get(e) >= tameRequir.get(e)) return;
		tameGot.put(e, tameGot.get(e) + 1);
		boolean eql = isEqual(tameRequir, tameGot);
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
		for(EntityType t : tameRequir.keySet()) {
			types.add(t);
		}
		for(Integer i : tameRequir.values()) {
			numbers.add(i);
		}
		for(int x = 0; x < types.size(); x++) {
			lore.add(ChatColor.translateAlternateColorCodes('&', theme.getTabs()) + ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', format(theme.getTameFormat(), types.get(x), numbers.get(x))));
		}
		return lore;
	}
	
	@Override
	public List<String> getProgress() {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.translateAlternateColorCodes('&', theme.getMenuItems()) + "Progress:");
		List<EntityType> types = new ArrayList<EntityType>();
		List<Integer> numbers = new ArrayList<Integer>();
		for(EntityType t : tameGot.keySet()) {
			types.add(t);
		}
		for(Integer i : tameGot.values()) {
			numbers.add(i);
		}
		for(int x = 0; x < types.size(); x++) {
			lore.add(ChatColor.translateAlternateColorCodes('&', theme.getTabs()) + ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', format(theme.getTameProgress(), types.get(x), numbers.get(x))));
		}
		return lore;
	}
	
	public String format(String s, EntityType m, Integer i) {
		String tmp = m.toString().toLowerCase().replaceAll("_", " ");
		int total = tameRequir.get(m);
		String first = tmp.substring(0, 1).toUpperCase();
		String rest = tmp.substring(1);
		s = s.replaceAll("%entity%", first + rest);
		s = s.replaceAll("%number%", String.valueOf(i));
		s = s.replaceAll("%final%", String.valueOf(total));
		return s;
	}
	
	public Map<EntityType, Integer> getGot() {
		return tameGot;
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
