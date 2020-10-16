package com.terturl.MeleeCraftQuests.Utils;

import com.terturl.MeleeCraftQuests.Quests.MeleeQuest;
import com.terturl.MeleeCraftQuests.Quests.QuestHoldingTypes.QuestCategory;

public class StringReplacement {

	/**
	 * Given a string, will replace all %quest% with the Quest name.
	 * @param s		String to format
	 * @param q		MeleeQuest for name
	 * @return String
	 * @see com.terturl.MeleeCraftQuests.Quests.MeleeQuest
	 */
	public static String formatQuestName(String s, MeleeQuest q) {
		s = s.replaceAll("%quest%", q.getName());
		return s;
	}
	
	/**
	 * Given a string, will replace all %category% with the QuestCategory name.
	 * @param s		String to format
	 * @param qc	QuestCategory for name
	 * @return String
	 * @see com.terturl.MeleeCraftQuests.Quests.QuestHoldingTypes.QuestCategory
	 */
	public static String formatCategoryName(String s, QuestCategory qc) {
		s = s.replaceAll("%category%", qc.getName());
		return s;
	}
	
	/**
	 * Given a string, will replace all %int% with the Integer string value.
	 * @param s		String to format
	 * @param i		Integer for String
	 * @return String
	 */
	public static String formatInt(String s, Integer i) {
		s = s.replaceAll("%int%", String.valueOf(i));
		return s;
	}
	
	/**
	 * Given a string, will replace all %pagenum% with the PageableInventories page number.
	 * @param s		String to format
	 * @param i		Integer for String
	 * @return String
	 */
	public static String formatPageNum(String s, Integer i) {
		s = s.replaceAll("%pagenum%", String.valueOf(i));
		return s;
	}
	
}