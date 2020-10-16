package com.terturl.MeleeCraftQuests.Quests.QuestHoldingTypes;

import java.util.ArrayList;
import java.util.List;

import com.terturl.MeleeCraftQuests.Quests.MeleeQuest;

public class QuestSets {

	private String name;
	private List<MeleeQuest> quests = new ArrayList<MeleeQuest>();
	
	/**
	 * Creates a QuestSet with the given name
	 * @param s		Name of the QuestSet
	 */
	public QuestSets(String s) {
		name = s;
	}
	
	public String getName() {
		return name;
	}
	
	public List<MeleeQuest> getQuests() {
		return quests;
	}
	
	public void addQuest(MeleeQuest q) {
		quests.add(q);
	}
	
}