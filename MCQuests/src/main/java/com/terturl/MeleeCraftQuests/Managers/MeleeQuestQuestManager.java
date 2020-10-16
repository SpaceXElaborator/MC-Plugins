package com.terturl.MeleeCraftQuests.Managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import com.terturl.MeleeCraftQuests.Main;
import com.terturl.MeleeCraftQuests.Quests.MeleeQuest;
import com.terturl.MeleeCraftQuests.Quests.QuestType;
import com.terturl.MeleeCraftQuests.Quests.QuestHoldingTypes.QuestCategory;
import com.terturl.MeleeCraftQuests.Quests.QuestHoldingTypes.QuestSets;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestBreed;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestCollect;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestKill;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestMine;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestTame;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestTravel;
import com.terturl.MeleeCraftQuests.Quests.Utils.QuestTheme;

public class MeleeQuestQuestManager {

	protected Map<String, MeleeQuest> quests = new HashMap<String, MeleeQuest>();
	protected List<QuestSets> sets = new ArrayList<QuestSets>();
	
	protected final String questTitle;
	protected final String questComplete;
	protected final String questAccept;
	protected final String questCancel;
	protected final String xpFormat;
	protected final String vMoneyFormat;
	protected final String pMoneyFormat;
	protected final String mMoneyFormat;
	protected final String QuestInventory;
	
	protected final String nextButton;
	protected final String nextButtonName;
	protected final String backButton;
	protected final String backButtonName;
	protected final String invFiller;
	
	/*
	 *  Will create the Quests directory and creates the Quests.yml mail configuration file.
	 *  Presets default values into the configuration file to make sure everything is loaded correctly.
	 */
	public MeleeQuestQuestManager() {
		File questDir = new File("plugins/MeleeQuest/Quests");
		if(!questDir.exists()) questDir.mkdir();
		
		File questConfig = new File("plugins/MeleeQuest/Quests.yml");
		if(!questConfig.exists()) {
			FileConfiguration config = YamlConfiguration.loadConfiguration(questConfig);
			config.set("QuestMenuName", "&6Quests");
			config.set("QuestComplete", "&3Completed Quest - &6&l%quest%");
			config.set("QuestAccepted", "&3Quest Added - &6&l%quest%!");
			config.set("QuestCancel", "&4Cancel - &2%quest%&4?");
			config.set("XPFormat", "&6>> &fXP Amount: %int%");
			config.set("VMFormat", "&6>> &fVirtual Money Amount: %int%");
			config.set("PMFormat", "&6>> &fPhysical Money Amount: %int%");
			config.set("MMFormat", "&6>> &fMeleeCoins Amount: %int%");
			config.set("QuestInv", "&e&lQuests");
			config.set("NextButton", "FEATHER");
			config.set("NextButtonName", "&6Next");
			config.set("BackButton", "FEATHER");
			config.set("BackButtonName", "&6Back");
			config.set("MainInvFiller", "BLACK_STAINED_GLASS_PANE");
			try {
				config.save(questConfig);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(questConfig);
		questTitle = config.getString("QuestMenuName");
		questComplete = config.getString("QuestComplete");
		questAccept = config.getString("QuestAccepted");
		questCancel = config.getString("QuestCancel");
		xpFormat = config.getString("XPFormat");
		vMoneyFormat = config.getString("VMFormat");
		pMoneyFormat = config.getString("PMFormat");
		mMoneyFormat = config.getString("MMFormat");
		QuestInventory = config.getString("QuestInv");
		
		nextButton = config.getString("NextButton");
		nextButtonName = config.getString("NextButtonName");
		backButton = config.getString("BackButton");
		backButtonName = config.getString("BackButtonName");
		invFiller = config.getString("MainInvFiller");
		
		File[] files = questDir.listFiles();
		if(files.length <= 0) return;
		for(File f : files) {
			if(f.isDirectory()) {
				QuestSets qs = new QuestSets(f.getName().replaceAll(" ", "_"));
				boolean hasCategory = false;
				String s = null;
				for(File f2 : f.listFiles()) {
					if(!hasCategory) {
						FileConfiguration config2 = YamlConfiguration.loadConfiguration(f2);
						if(config2.contains("Category")) {
							s = config2.getString("Category");
							hasCategory = true;
						}
					}
					loadFile(f2, qs);
				}
				
				if(hasCategory && s != null) {
					QuestCategory gc = Main.getInstance().getCategoryManager().getCategory(s);
					gc.getQuestSets().add(qs);
				} else {
					QuestCategory gc = Main.getInstance().getCategoryManager().getDefault();
					gc.getQuestSets().add(qs);
				}
				
				sets.add(qs);
				continue;
			}
			loadFile(f, null);
		}
		Main.getInstance().getLogger().log(Level.INFO, "Loaded: " + quests.size() + " Quests");
		Main.getInstance().getLogger().log(Level.INFO, "Loaded: " + sets.size() + " Sets");
	}
	
	// Will load the quest as a QuestSet Quest or a Singleton Quest based on if it was in a folder or not previously
	private void loadFile(File f, QuestSets questS) {
		FileConfiguration config = YamlConfiguration.loadConfiguration(f);
		
		String name = config.getString("Name");
		QuestType type = QuestType.valueOf(config.getString("Type").toUpperCase());
		Material mat = Material.valueOf(config.getString("Material").toUpperCase());
		QuestTheme qt = Main.getInstance().getThemeManager().getTheme(config.getString("Theme").toUpperCase());
		
		switch(type) {
		case BREED:
			List<String> entsBreed = config.getStringList("Entities");
			List<Integer> numbersBreed = config.getIntegerList("Number");
			Map<EntityType, Integer> questReqBreed = new HashMap<EntityType, Integer>();
			for(int x = 0; x < entsBreed.size(); x++) {
				questReqBreed.put(EntityType.valueOf(entsBreed.get(x).toUpperCase()), numbersBreed.get(x));
			}
			QuestBreed qb = new QuestBreed(name, mat, questReqBreed, qt);
			formatQuest(qb, config, questS);
			break;
//		case CRAFT:
//			List<String> entsCraft = config.getStringList("Materials");
//			List<Integer> numbersCraft = config.getIntegerList("Number");
//			Map<Material, Integer> questReqCraft = new HashMap<Material, Integer>();
//			for(int x = 0; x < entsCraft.size(); x++) {
//				questReqCraft.put(Material.valueOf(entsCraft.get(x).toUpperCase()), numbersCraft.get(x));
//			}
//			QuestCraft qcraft = new QuestCraft(name, mat, questReqCraft, qt);
//			
//			if(config.contains("XP")) qcraft.setXp(config.getInt("XP"));
//			if(config.contains("MeleeCoins")) qcraft.setMeleeCoins(config.getInt("MeleeCoins"));
//			if(config.contains("VirtualMoney")) qcraft.setVirtualMoney(config.getInt("VirtualMoney"));
//			if(config.contains("PhysicalMoney")) qcraft.setPhysicalMoney(config.getInt("PhysicalMoney"));
//			
//			if(!config.contains("Category")) {
//				if(questS == null) {
//					QuestCategory qc = Main.getInstance().getCategoryManager().getDefault();
//					qc.getSingleQuests().add(qcraft);
//				}
//				break;
//			}
//			
//			if(questS == null) {
//				QuestCategory cat = Main.getInstance().getCategoryManager().getCategory(config.getString("Category"));
//				cat.getSingleQuests().add(qcraft);
//				quests.put(qcraft.getComputerName(), qcraft);
//			} else {
//				questS.addQuest(qcraft);
//			}
//			break;
		case MINE:
			List<String> entsMine = config.getStringList("Blocks");
			List<Integer> numbersMine = config.getIntegerList("Number");
			Map<Material, Integer> questReqMine = new HashMap<Material, Integer>();
			for(int x = 0; x < entsMine.size(); x++) {
				questReqMine.put(Material.valueOf(entsMine.get(x).toUpperCase()), numbersMine.get(x));
			}
			QuestMine qm = new QuestMine(name, mat, questReqMine, qt);
			formatQuest(qm, config, questS);
			break;
		case TAME:
			List<String> entsTame = config.getStringList("Entities");
			List<Integer> numbersTame = config.getIntegerList("Number");
			Map<EntityType, Integer> questReqTame = new HashMap<EntityType, Integer>();
			for(int x = 0; x < entsTame.size(); x++) {
				questReqTame.put(EntityType.valueOf(entsTame.get(x).toUpperCase()), numbersTame.get(x));
			}
			QuestTame qtame = new QuestTame(name, mat, questReqTame, qt);
			formatQuest(qtame, config, questS);
			break;
		case KILL:
			List<String> ents = config.getStringList("Entities");
			List<Integer> numbers = config.getIntegerList("Number");
			Map<EntityType, Integer> questReq = new HashMap<EntityType, Integer>();
			for(int x = 0; x < ents.size(); x++) {
				questReq.put(EntityType.valueOf(ents.get(x).toUpperCase()), numbers.get(x));
			}
			QuestKill qk = new QuestKill(name, mat, questReq, qt);
			formatQuest(qk, config, questS);
			break;
		case COLLECT:
			List<String> entsColl = config.getStringList("Materials");
			List<Integer> numbersColl = config.getIntegerList("Number");
			Map<Material, Integer> questReqColl = new HashMap<Material, Integer>();
			for(int x = 0; x < entsColl.size(); x++) {
				questReqColl.put(Material.valueOf(entsColl.get(x).toUpperCase()), numbersColl.get(x));
			}
			QuestCollect qc = new QuestCollect(name, mat, questReqColl, qt);
			formatQuest(qc, config, questS);
			break;
		case PLAYER_KILL:
			break;
		case TRAVEL:
			String loc = config.getString("Location");
			String[] locs = loc.split(":");
			Location locTo = new Location(Bukkit.getWorlds().get(0), Integer.valueOf(locs[0]), Integer.valueOf(locs[1]), Integer.valueOf(locs[2]));
			QuestTravel qts = new QuestTravel(name, mat, locTo, qt);
			formatQuest(qts, config, questS);
			break;
		default:
			break;
		}
	}
	
	private void formatQuest(MeleeQuest q, FileConfiguration config, QuestSets questS) {
		if(config.contains("XP")) q.setXp(config.getInt("XP"));
		if(config.contains("MeleeCoins")) q.setMeleeCoins(config.getInt("MeleeCoins"));
		if(config.contains("VirtualMoney")) q.setVirtualMoney(config.getInt("VirtualMoney"));
		if(config.contains("PhysicalMoney")) q.setPhysicalMoney(config.getInt("PhysicalMoney"));
		
		if(!config.contains("Category")) {
			if(questS == null) {
				QuestCategory qc2 = Main.getInstance().getCategoryManager().getDefault();
				qc2.getSingleQuests().add(q);
				quests.put(q.getComputerName(), q);
			}
			return;
		}
		
		if(questS == null) {
			QuestCategory cat = Main.getInstance().getCategoryManager().getCategory(config.getString("Category"));
			cat.getSingleQuests().add(q);
			quests.put(q.getComputerName(), q);
		} else {
			questS.addQuest(q);
		}
		return;
	}
	
	public String getTitle() {
		return questTitle;
	}
	
	public String getQuestAccepted() {
		return questAccept;
	}
	
	public String getQuestComplete() {
		return questComplete;
	}
	
	public String getCancelRemark() {
		return questCancel;
	}
	
	public String getXPFormat() {
		return xpFormat;
	}
	
	public String getVMFormat() {
		return vMoneyFormat;
	}
	
	public String getPMFormat() {
		return pMoneyFormat;
	}
	
	public String getMMFormat() {
		return mMoneyFormat;
	}
	
	public String getQuestInv() {
		return QuestInventory;
	}
	
	public String getNextButton() {
		return nextButton;
	}
	
	public String getNextButtonName() {
		return nextButtonName;
	}
	
	public String getBackButton() {
		return backButton;
	}
	
	public String getBackButtonName() {
		return backButtonName;
	}
	
	public String getInvFiller() {
		return invFiller;
	}
	
	/**
	 * Will get the quest based on ComputerName or return null if not found
	 * @param s		ComputerName of MeleeQuest
	 * @return MeleeQuest
	 */
	public MeleeQuest getQuest(String s) {
		if(quests.get(s) != null) {
			return quests.get(s);
		}
		for(QuestSets qs : sets) {
			for(MeleeQuest mq : qs.getQuests()) {
				if(mq.getComputerName().equals(s)) return mq;
			}
		}
		return null;
	}

	/**
	 * Will return all quests that have been saved
	 * @return lists
	 * @see com.terturl.MeleeCraftQuests.Quests.MeleeQuest
	 */
	public List<MeleeQuest> getQuests() {
		List<MeleeQuest> lists = new ArrayList<MeleeQuest>();
		for(MeleeQuest mq : quests.values()) {
			lists.add(mq);
		}
		return lists;
	}
	
	/**
	 * Will return all QuestSets that have been saved
	 * @return sets
	 * @see com.terturl.MeleeCraftQuests.Quests.QuestHoldingTypes.QuestSets
	 */
	public List<QuestSets> getSets() {
		return sets;
	}
	
}