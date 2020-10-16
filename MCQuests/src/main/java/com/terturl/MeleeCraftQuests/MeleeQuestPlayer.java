package com.terturl.MeleeCraftQuests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import com.terturl.MeleeCraftQuests.Quests.MeleeQuest;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestBreed;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestCollect;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestKill;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestMine;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestTravel;

public class MeleeQuestPlayer {

	protected UUID playerId;
	protected List<MeleeQuest> quests = new ArrayList<MeleeQuest>();
	protected List<MeleeQuest> completed = new ArrayList<MeleeQuest>();
	
	/**
	 * Creates a new MeleeQuestPlayer
	 * @param u		UUID of the player
	 */
	public MeleeQuestPlayer(UUID u) {
		playerId = u;
	}
	
	public UUID getPlayer() {
		return playerId;
	}
	
	public List<MeleeQuest> getQuests() {
		return quests;
	}
	
	/**
	 * Add the given MeleeQuest to the players quests list
	 * @param mq	MeleeQuest
	 * @see com.terturl.MeleeCraftQuests.Quests.MeleeQuest
	 */
	public void addQuest(MeleeQuest mq) {
		quests.add(mq);
	}
	
	/**
	 * Add the given MeleeQuest to the players completed list
	 * @param mq	MeleeQuest
	 * @see com.terturl.MeleeCraftQuests.Quests.MeleeQuest
	 */
	public void completeQuests(MeleeQuest mq) {
		completed.add(mq);
	}
	
	/**
	 * Removes the given MeleeQuest with their computername
	 * @param s		MeleeQuest ComputerName
	 */
	public void removeQuest(String s) {
		for(MeleeQuest mq : quests) {
			if(mq.getComputerName().equalsIgnoreCase(s)) {
				quests.remove(mq);
				break;
			}
		}
	}
	
	/**
	 * Checks if the player has the given MeleeQuest.
	 * @param mq	MeleeQuest
	 * @return boolean
	 * @see com.terturl.MeleeCraftQuests.Quests.MeleeQuest
	 */
	public boolean hasQuest(MeleeQuest mq) {
		if(quests.isEmpty() || quests.size() <= 0) return false;
		for(MeleeQuest mqcheck : quests) {
			if(mqcheck.getComputerName().equals(mq.getComputerName())) return true;
		}
		return false;
	}
	
	/**
	 * Checks if the player has completed the given MeleeQuest
	 * @param mq	MeleeQuest
	 * @return boolean
	 * @see com.terturl.MeleeCraftQuests.Quests.MeleeQuest
	 */
	public boolean hasCompleted(MeleeQuest mq) {
		if(completed.isEmpty() || completed.size() <= 0) return false;
		for(MeleeQuest mqcheck : completed) {
			if(mqcheck.getComputerName().equals(mq.getComputerName())) return true;
		}
		return false;
	}
	
	/**
	 * Saves the players progress
	 */
	public void save() {
		File main = new File("plugins/MeleeQuest/Players");
		File player = new File(main, Bukkit.getOfflinePlayer(playerId).getName() + ".yml");
		if(player.exists()) player.delete();
		try {
			player.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(player);
		config.set("UUID", playerId.toString());
		ConfigurationSection questsConfig = config.createSection("Quests");
		for(MeleeQuest mq : quests) {
			switch(mq.getType()) {
			case BREED:
				QuestBreed qb = (QuestBreed)mq;
				Map<EntityType, Integer> breedGot = qb.getGot();
				List<String> breedTypes = new ArrayList<String>();
				List<Integer> breedNumbers = new ArrayList<Integer>();
				for(EntityType t : breedGot.keySet()) {
					breedTypes.add(t.toString());
				}
				for(Integer i : breedGot.values()) {
					breedNumbers.add(i);
				}
				questsConfig.set(mq.getComputerName() + ".Name", mq.getComputerName());
				questsConfig.set(mq.getComputerName() + ".Type", qb.getType().toString());
				questsConfig.set(mq.getComputerName() + ".Entities", breedTypes);
				questsConfig.set(mq.getComputerName() + ".Numbers", breedNumbers);
				break;
			case COLLECT:
				QuestCollect qc = (QuestCollect)mq;
				Map<Material, Integer> collGot = qc.getGot();
				List<String> collTypes = new ArrayList<String>();
				List<Integer> collNumb = new ArrayList<Integer>();
				for(Material m : collGot.keySet()) {
					collTypes.add(m.toString());
				}
				for(Integer i : collGot.values()) {
					collNumb.add(i);
				}
				questsConfig.set(mq.getComputerName() + ".Name", mq.getComputerName());
				questsConfig.set(mq.getComputerName() + ".Type", qc.getType().toString());
				questsConfig.set(mq.getComputerName() + ".Material", collTypes);
				questsConfig.set(mq.getComputerName() + ".Numbers", collNumb);
				break;
			case CRAFT:
				QuestCollect qcraft = (QuestCollect)mq;
				Map<Material, Integer> craftGot = qcraft.getGot();
				List<String> craftTypes = new ArrayList<String>();
				List<Integer> craftNumb = new ArrayList<Integer>();
				for(Material m : craftGot.keySet()) {
					craftTypes.add(m.toString());
				}
				for(Integer i : craftGot.values()) {
					craftNumb.add(i);
				}
				questsConfig.set(mq.getComputerName() + ".Name", mq.getComputerName());
				questsConfig.set(mq.getComputerName() + ".Type", qcraft.getType().toString());
				questsConfig.set(mq.getComputerName() + ".Material", craftTypes);
				questsConfig.set(mq.getComputerName() + ".Numbers", craftNumb);
				break;
			case KILL:
				QuestKill qk = (QuestKill)mq;
				Map<EntityType, Integer> killGot = qk.getGot();
				List<String> killTypes = new ArrayList<String>();
				List<Integer> killNumbers = new ArrayList<Integer>();
				for(EntityType t : killGot.keySet()) {
					killTypes.add(t.toString());
				}
				for(Integer i : killGot.values()) {
					killNumbers.add(i);
				}
				questsConfig.set(mq.getComputerName() + ".Name", mq.getComputerName());
				questsConfig.set(mq.getComputerName() + ".Type", qk.getType().toString());
				questsConfig.set(mq.getComputerName() + ".Entities", killTypes);
				questsConfig.set(mq.getComputerName() + ".Numbers", killNumbers);
				break;
			case MINE:
				QuestMine qm = (QuestMine)mq;
				Map<Material, Integer> mineGot = qm.getGot();
				List<String> mineTypes = new ArrayList<String>();
				List<Integer> mineNumbers = new ArrayList<Integer>();
				for(Material m : mineGot.keySet()) {
					mineTypes.add(m.toString());
				}
				for(Integer i : mineGot.values()) {
					mineNumbers.add(i);
				}
				questsConfig.set(mq.getComputerName() + ".Name", mq.getComputerName());
				questsConfig.set(mq.getComputerName() + ".Type", qm.getType().toString());
				questsConfig.set(mq.getComputerName() + ".Blocks", mineTypes);
				questsConfig.set(mq.getComputerName() + ".Numbers", mineNumbers);
				break;
			case PLAYER_KILL:
				break;
			case TAME:
				QuestBreed qt = (QuestBreed)mq;
				Map<EntityType, Integer> tameGot = qt.getGot();
				List<String> tameTypes = new ArrayList<String>();
				List<Integer> tameNumbers = new ArrayList<Integer>();
				for(EntityType t : tameGot.keySet()) {
					tameTypes.add(t.toString());
				}
				for(Integer i : tameGot.values()) {
					tameNumbers.add(i);
				}
				questsConfig.set(mq.getComputerName() + ".Name", mq.getComputerName());
				questsConfig.set(mq.getComputerName() + ".Type", qt.getType().toString());
				questsConfig.set(mq.getComputerName() + ".Entities", tameTypes);
				questsConfig.set(mq.getComputerName() + ".Numbers", tameNumbers);
				break;
			case TRAVEL:
				QuestTravel qtr = (QuestTravel)mq;
				Location loc = qtr.getLoc();
				String wor = loc.getWorld().getName();
				String x = String.valueOf(loc.getBlockX());
				String y = String.valueOf(loc.getBlockY());
				String z = String.valueOf(loc.getBlockZ());
				questsConfig.set(mq.getComputerName() + ".Name", mq.getComputerName());
				questsConfig.set(mq.getComputerName() + ".Type", qtr.getType().toString());
				questsConfig.set(mq.getComputerName() + ".Location", wor + ":" + x + ":" + y + ":" + z);
				break;
			default:
				break;
			}
		}
		
		List<String> questsCompleted = new ArrayList<String>();
		for(MeleeQuest mq : completed) {
			questsCompleted.add(mq.getComputerName());
		}
		config.set("Completed", questsCompleted);
		
		try {
			config.save(player);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}