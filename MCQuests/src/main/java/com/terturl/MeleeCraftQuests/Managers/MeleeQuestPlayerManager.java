package com.terturl.MeleeCraftQuests.Managers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import com.terturl.MeleeCraftQuests.Main;
import com.terturl.MeleeCraftQuests.MeleeQuestPlayer;
import com.terturl.MeleeCraftQuests.Quests.MeleeQuest;
import com.terturl.MeleeCraftQuests.Quests.QuestType;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestBreed;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestCollect;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestKill;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestMine;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestTame;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestTravel;

public class MeleeQuestPlayerManager {

	protected List<MeleeQuestPlayer> players = new ArrayList<MeleeQuestPlayer>();
	
	/*
	 * Will create the Players directory if not present and will load all of their quests
	 * based on a list/string format from their file to make sure they have their
	 * quests loaded when they rejoin.
	 */
	public MeleeQuestPlayerManager() {
		File playerDir = new File("plugins/MeleeQuest/Players");
		if(!playerDir.exists()) playerDir.mkdir();
		
		File[] files = playerDir.listFiles();
		if(files.length <= 0) return;
		for(File f : files) {
			FileConfiguration config = YamlConfiguration.loadConfiguration(f);
			
			UUID uuid = UUID.fromString(config.getString("UUID"));
			ConfigurationSection quests = config.getConfigurationSection("Quests");
			
			MeleeQuestPlayer mqp = new MeleeQuestPlayer(uuid);
			for(String s : quests.getKeys(false)) {
				String name = quests.getString(s + ".Name");
				QuestType qt = QuestType.valueOf(quests.getString(s + ".Type"));
				MeleeQuest mq = Main.getInstance().getQuestManager().getQuest(name);
				switch(qt) {
				case BREED:
					QuestBreed qb = (QuestBreed)mq;
					List<String> breedGot = quests.getStringList(s + ".Entities");
					List<Integer> breedNum = quests.getIntegerList(s + ".Numbers");
					for(int x = 0; x < breedGot.size(); x++) {
						qb.setEntity(EntityType.valueOf(breedGot.get(x)), breedNum.get(x));
					}
					mqp.addQuest(qb);
					break;
				case COLLECT:
					QuestCollect qc = (QuestCollect)mq;
					List<String> collGot = quests.getStringList(s + ".Material");
					List<Integer> collNum = quests.getIntegerList(s + ".Numbers");
					for(int x = 0; x < collGot.size(); x++) {
						qc.setMaterialValue(Material.valueOf(collGot.get(x)), collNum.get(x));
					}
					mqp.addQuest(qc);
					break;
//				case CRAFT:
//					QuestCraft qcraft = (QuestCraft)mq;
//					List<String> craftGot = quests.getStringList(s + ".Material");
//					List<Integer> craftNum = quests.getIntegerList(s + ".Numbers");
//					for(int x = 0; x < craftGot.size(); x++) {
//						qcraft.setMaterialValue(Material.valueOf(craftGot.get(x)), craftNum.get(x));
//					}
//					mqp.addQuest(qcraft);
//					break;
				case KILL:
					QuestKill qk = (QuestKill)mq;
					List<String> killGot = quests.getStringList(s + ".Entities");
					List<Integer> killNum = quests.getIntegerList(s + ".Numbers");
					for(int x = 0; x < killGot.size(); x++) {
						qk.setEntity(EntityType.valueOf(killGot.get(x)), killNum.get(x));
					}
					mqp.addQuest(qk);
					break;
				case MINE:
					QuestMine qm = (QuestMine)mq;
					List<String> mineGot = quests.getStringList(s + ".Blocks");
					List<Integer> mineNum = quests.getIntegerList(s + ".Numbers");
					for(int x = 0; x < mineGot.size(); x++) {
						qm.setBlockValue(Material.valueOf(mineGot.get(x)), mineNum.get(x));
					}
					mqp.addQuest(qm);
					break;
				case PLAYER_KILL:
					break;
				case TAME:
					QuestTame qta = (QuestTame)mq;
					List<String> tameGot = quests.getStringList(s + ".Entities");
					List<Integer> tameNum = quests.getIntegerList(s + ".Numbers");
					for(int x = 0; x < tameGot.size(); x++) {
						qta.setEntity(EntityType.valueOf(tameGot.get(x)), tameNum.get(x));
					}
					mqp.addQuest(qta);
					break;
				case TRAVEL:
					QuestTravel trav = (QuestTravel)mq;
					mqp.addQuest(trav);
					break;
				default:
					break;
				}
			}
			
			for(String s : config.getStringList("Completed")) {
				mqp.completeQuests(Main.getInstance().getQuestManager().getQuest(s));
			}
			
			players.add(mqp);
			
		}
	}
	
	/**
	 * Will add a player using their UUID for quest viewing/completing
	 * @param uuid	The players UUID to add
	 */
	public void addPlayer(UUID uuid) {
		MeleeQuestPlayer mqp = new MeleeQuestPlayer(uuid);
		players.add(mqp);
	}
	
	/**
	 * Will return the MeleeQuestPlayer based on UUID or null if not found
	 * @param uuid		The players UUID to get
	 * @return MeleeQuestPlayer
	 */
	public MeleeQuestPlayer getPlayer(UUID uuid) {
		for(MeleeQuestPlayer mqp : players) {
			if(mqp.getPlayer().equals(uuid)) return mqp;
		}
		return null;
	}
}