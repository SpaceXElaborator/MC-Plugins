package com.terturl.MCHub.HeadHunter;

import java.util.ArrayList;
import java.util.List;

public class HeadHunt {

	private String name;
	private List<String> collectedSkulls = new ArrayList<String>();
	private List<String> achievements = new ArrayList<String>();
	
	public HeadHunt(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> collectedSkulls() {
		return collectedSkulls;
	}
	
	public boolean collect(String name) {
		if(collectedSkulls.contains(name)) return false;
		collectedSkulls.add(name);
		return true;
	}
	
	public List<String> getAchievements() {
		return achievements;
	}
	
	public boolean addAchievement(String name) {
		if(achievements.contains(name)) return false;
		achievements.add(name);
		return true;
		
	}
	
}