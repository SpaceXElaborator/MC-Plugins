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

public class QuestBreed extends MeleeQuest{

	protected Map<EntityType, Integer> breedRequir;
	protected Map<EntityType, Integer> breedGot = new HashMap<EntityType, Integer>();
	
	public QuestBreed(String name, Material mat, Map<EntityType, Integer> required, QuestTheme qt) {
		super(name, QuestType.BREED, mat, qt);
		breedRequir = required;
		for(EntityType e : breedRequir.keySet()) {
			breedGot.put(e, Integer.valueOf(0));
		}
	}
	
	public void setEntity(EntityType et, Integer i) {
		breedGot.put(et, i);
	}
	
	public boolean containsEntity(EntityType e) {
		if(breedRequir.containsKey(e)) return true;
		return false;
	}
	
	public void updateEntity(EntityType e, MeleeQuestPlayer p) {
		if(breedGot.get(e) >= breedRequir.get(e)) return;
		breedGot.put(e, breedGot.get(e) + 1);
		boolean eql = isEqual(breedRequir, breedGot);
		if(eql) {
			onComplete(p);
		} else return;
	}
	
	@Override
	public void onComplete(MeleeQuestPlayer p) {
		super.onComplete(p);
	}
	
	@Override
	public List<String> getLore() {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.translateAlternateColorCodes('&', theme.getMenuItems()) + "Objectives:");
		List<EntityType> types = new ArrayList<EntityType>();
		List<Integer> numbers = new ArrayList<Integer>();
		for(EntityType t : breedRequir.keySet()) {
			types.add(t);
		}
		for(Integer i : breedRequir.values()) {
			numbers.add(i);
		}
		for(int x = 0; x < types.size(); x++) {
			lore.add(ChatColor.translateAlternateColorCodes('&', theme.getTabs()) + ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', format(theme.getBreedFormat(), types.get(x), numbers.get(x))));
		}
		return lore;
	}
	
	@Override
	public List<String> getProgress() {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.translateAlternateColorCodes('&', theme.getMenuItems()) + "Progress:");
		List<EntityType> types = new ArrayList<EntityType>();
		List<Integer> numbers = new ArrayList<Integer>();
		for(EntityType t : breedGot.keySet()) {
			types.add(t);
		}
		for(Integer i : breedGot.values()) {
			numbers.add(i);
		}
		for(int x = 0; x < types.size(); x++) {
			lore.add(ChatColor.translateAlternateColorCodes('&', theme.getTabs()) + ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', format(theme.getBreedProgress(), types.get(x), numbers.get(x))));
		}
		return lore;
	}
	
	public String format(String s, EntityType et, Integer num) {
		String tmp = et.toString().toLowerCase() + "s";
		int total = breedRequir.get(et);
		String first = tmp.substring(0, 1).toUpperCase();
		String rest = tmp.substring(1);
		s = s.replaceAll("%animal%", first + rest);
		s = s.replaceAll("%number%", String.valueOf(num));
		s = s.replaceAll("%final%", String.valueOf(total));
		return s;
	}
	
	public Map<EntityType, Integer> getGot() {
		return breedGot;
	}
	
	private Boolean isEqual(Map<EntityType, Integer> first, Map<EntityType, Integer> second) {
		if(first.size() != second.size()) return false;
		return first.entrySet().stream().allMatch(e -> e.getValue().equals(second.get(e.getKey())));
	}
	
}