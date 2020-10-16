package com.terturl.MeleeCraftQuests.Quests.QuestHoldingTypes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import com.terturl.MeleeCraftQuests.Quests.MeleeQuest;

public class QuestCategory {

	private String name;
	private String color;
	private Material mat;
	private Boolean def;
	private Integer slot;
	private List<String> lore = new ArrayList<String>();
	
	private List<MeleeQuest> singleQuests = new ArrayList<MeleeQuest>();
	private List<QuestSets> setQuests = new ArrayList<QuestSets>();
	
	/**
	 * Creates a QuestCategory with the given name, color code, and material. It will place the QuestCategory
	 * at the given integer slot and mark it as a default or not. Can be given lore if desired.
	 * @param s				Name of the QuestCategory
	 * @param colors		Color code to set the name
	 * @param itemMat		The material the QuestCategory will show up as
	 * @param slotN			The slot to put the QuestCategory
	 * @param defaultC		Whether or not it is a default category
	 * @param itemLore		List of Strings to add to the Category Lore.
	 */
	public QuestCategory(String s, String colors, Material itemMat, Integer slotN, Boolean defaultC, List<String> itemLore) {
		name = s;
		color = colors;
		def = defaultC;
		slot = slotN;
		lore = itemLore;
		mat = itemMat;
	}
	
	public String getName() {
		return name;
	}
	
	public String getColor() {
		return color;
	}
	
	public Material getMaterial() {
		return mat;
	}
	
	public Integer getSlot() {
		return slot;
	}
	
	public Boolean isDefault() {
		return def;
	}
	
	public List<String> getLore() {
		return lore;
	}
	
	public List<MeleeQuest> getSingleQuests() {
		return singleQuests;
	}
	
	public List<QuestSets> getQuestSets() {
		return setQuests;
	}
	
}