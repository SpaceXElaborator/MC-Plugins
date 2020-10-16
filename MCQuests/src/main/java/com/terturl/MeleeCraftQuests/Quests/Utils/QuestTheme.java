package com.terturl.MeleeCraftQuests.Quests.Utils;

import org.bukkit.Material;

public class QuestTheme {

	private String name;
	private String menuItems;
	private String tabs;
	private String InProgress;
	private String Completed;
	private String NotAccepted;
	private String Locked;
	private String Accept;
	private Material completedItem;
	private Material lockedItem;
	private String BreedFormat;
	private String BreedProgress;
	private String CollectFormat;
	private String CollectProgress;
	private String KillFormat;
	private String KillProgress;
	private String MineFormat;
	private String MineProgress;
	private String TameFormat;
	private String TameProgress;
	private String TravelFormat;
	private String TravelProgress;
	private String CraftFormat;
	private String CraftProgress;
	
	public QuestTheme(String s) {
		name = s;
	}
	
	public String getName() {
		return name;
	}

	public String getBreedFormat() {
		return BreedFormat;
	}

	public void setBreedFormat(String breedFormat) {
		BreedFormat = breedFormat;
	}

	public String getBreedProgress() {
		return BreedProgress;
	}

	public void setBreedProgress(String breedProgress) {
		BreedProgress = breedProgress;
	}

	public String getCollectFormat() {
		return CollectFormat;
	}

	public void setCollectFormat(String collectFormat) {
		CollectFormat = collectFormat;
	}

	public String getCollectProgress() {
		return CollectProgress;
	}

	public void setCollectProgress(String collectProgress) {
		CollectProgress = collectProgress;
	}

	public String getKillFormat() {
		return KillFormat;
	}

	public void setKillFormat(String killFormat) {
		KillFormat = killFormat;
	}

	public String getKillProgress() {
		return KillProgress;
	}

	public void setKillProgress(String killProgress) {
		KillProgress = killProgress;
	}

	public String getMineFormat() {
		return MineFormat;
	}

	public void setMineFormat(String mineFormat) {
		MineFormat = mineFormat;
	}

	public String getMineProgress() {
		return MineProgress;
	}

	public void setMineProgress(String mineProgress) {
		MineProgress = mineProgress;
	}

	public String getTameFormat() {
		return TameFormat;
	}

	public void setTameFormat(String tameFormat) {
		TameFormat = tameFormat;
	}

	public String getTameProgress() {
		return TameProgress;
	}

	public void setTameProgress(String tameProgress) {
		TameProgress = tameProgress;
	}

	public String getTravelFormat() {
		return TravelFormat;
	}

	public void setTravelFormat(String travelFormat) {
		TravelFormat = travelFormat;
	}

	public String getTravelProgress() {
		return TravelProgress;
	}

	public void setTravelProgress(String travelProgress) {
		TravelProgress = travelProgress;
	}

	public String getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(String menuItems) {
		this.menuItems = menuItems;
	}

	public String getTabs() {
		return tabs;
	}

	public void setTabs(String tabs) {
		this.tabs = tabs;
	}

	public String getInProgress() {
		return InProgress;
	}

	public void setInProgress(String inProgress) {
		InProgress = inProgress;
	}

	public String getCompleted() {
		return Completed;
	}

	public void setCompleted(String completed) {
		Completed = completed;
	}

	public String getNotAccepted() {
		return NotAccepted;
	}

	public void setNotAccepted(String notAccepted) {
		NotAccepted = notAccepted;
	}

	public String getLocked() {
		return Locked;
	}

	public void setLocked(String locked) {
		Locked = locked;
	}

	public String getAccept() {
		return Accept;
	}

	public void setAccept(String accept) {
		Accept = accept;
	}

	public Material getCompletedItem() {
		return completedItem;
	}

	public void setCompletedItem(Material completedItem) {
		this.completedItem = completedItem;
	}

	public Material getLockedItem() {
		return lockedItem;
	}

	public void setLockedItem(Material lockedItem) {
		this.lockedItem = lockedItem;
	}

	public String getCraftFormat() {
		return CraftFormat;
	}

	public void setCraftFormat(String craftFormat) {
		CraftFormat = craftFormat;
	}

	public String getCraftProgress() {
		return CraftProgress;
	}

	public void setCraftProgress(String craftProgress) {
		CraftProgress = craftProgress;
	}
	
}