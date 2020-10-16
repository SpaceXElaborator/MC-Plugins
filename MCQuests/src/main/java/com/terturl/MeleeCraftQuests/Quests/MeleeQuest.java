package com.terturl.MeleeCraftQuests.Quests;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import com.terturl.MeleeCraftEssentials.MCEssentials;
import com.terturl.MeleeCraftEssentials.Player.MeleeCoinDB;
import com.terturl.MeleeCraftQuests.Main;
import com.terturl.MeleeCraftQuests.MeleeQuestPlayer;
import com.terturl.MeleeCraftQuests.Quests.Utils.QuestTheme;
import com.terturl.MeleeCraftQuests.Utils.StringReplacement;

import net.md_5.bungee.api.ChatColor;

public class MeleeQuest {

	protected String name;
	protected String computerName;
	protected Material mat;
	protected QuestType type;
	
	protected QuestTheme theme;
	
	protected int XP = 0;
	protected int MeleeCoins = 0;
	protected int VirtualMoney = 0;
	protected int PhysicalMoney = 0;

	/**
	 * Creates a quest with the given name. It will creates a computer name by replacing all _ with ' '.
	 * The quest will appear as the given material and have lore structured with the given QuestTheme
	 * @param qName		QuestName
	 * @param qType		QuestType
	 * @param m			Material the quest will show up as
	 * @param qt		QuestTheme that will be used to display lore
	 * @see com.terturl.MeleeCraftQuests.Quests.QuestType
	 * @see com.terturl.MeleeCraftQuests.Quests.Utils.QuestTheme
	 */
	public MeleeQuest(String qName, QuestType qType, Material m, QuestTheme qt) {
		name = qName;
		computerName = qName.replaceAll(" ", "_");
		type = qType;
		mat = m;
		theme = qt;
	}

	public String getName() {
		return name;
	}

	public String getComputerName() {
		return computerName;
	}

	public QuestType getType() {
		return type;
	}
	
	public QuestTheme getTheme() {
		return theme;
	}
	
	public Material getMaterial() {
		return mat;
	}
	
	public void setXp(int xp) {
		XP = xp;
	}
	
	public int getXp() {
		return XP;
	}
	
	public void setMeleeCoins(int i) {
		MeleeCoins = i;
	}
	
	public int getMeleeCoins() {
		return MeleeCoins;
	}
	
	public void setPhysicalMoney(int i) {
		PhysicalMoney = i;
	}
	
	public int getPhysicalMoney() {
		return PhysicalMoney;
	}
	
	public void setVirtualMoney(int i) {
		VirtualMoney = i;
	}
	
	public int getVirtualMoney() {
		return VirtualMoney;
	}
	
	public List<String> getProgress() {
		return null;
	}
	
	public List<String> getLore() {
		return null;
	}
	
	/**
	 * Called when the player has completed their required tasks for the quest and update their MeleeCoins, XP, and virtual money.
	 * @param p		The MeleeQuestPlayer that will be recieving the rewards
	 * @see com.terturl.MeleeCraftQuests.MeleeQuestPlayer
	 */
	public void onComplete(MeleeQuestPlayer p) {
		p.completeQuests(this);
		p.removeQuest(getComputerName());
		Bukkit.getPlayer(p.getPlayer()).sendMessage(ChatColor.translateAlternateColorCodes('&', StringReplacement.formatQuestName(Main.getInstance().getQuestManager().getQuestComplete(), this)));
		Bukkit.getPlayer(p.getPlayer()).giveExp(getXp());
		if(getMeleeCoins() != 0) {
			MeleeCoinDB db = (MeleeCoinDB)MCEssentials.getInstance().getDBManager().getDatabase("MeleeCoin");
			db.setCoins(Bukkit.getPlayer(p.getPlayer()), getMeleeCoins());
		}
	}

}