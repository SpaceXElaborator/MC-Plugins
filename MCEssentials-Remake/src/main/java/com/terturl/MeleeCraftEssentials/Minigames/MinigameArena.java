package com.terturl.MeleeCraftEssentials.Minigames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.terturl.MeleeCraftEssentials.MCEssentials;
import com.terturl.MeleeCraftEssentials.Minigames.Utils.KitSelection;
import com.terturl.MeleeCraftEssentials.Minigames.Utils.Kits.MinigameKit;

public class MinigameArena<T extends MinigameManager> {

	private T Manager;
	private String gameName;
	private List<MinigameKit> kits = new ArrayList<MinigameKit>();
	private Map<String, MinigameKit> players = new HashMap<String, MinigameKit>();
	private KitSelection kitS;
	private boolean hasStarted;
	
	public MinigameArena(String s, T t, KitSelection selection) {
		gameName = s;
		Manager = t;
		kitS = selection;
		hasStarted = false;
		selection.setMinigame(this);
		
		kits.addAll(selection.getKits());
		
		init();
	}
	
	private void init() {
		getManager().loadMinigame();
	}
	
	public String getGameName() {
		return gameName;
	}
	
	public T getManager() {
		return Manager;
	}
	
	public void onJoin(final Player p) {
		if(p.getOpenInventory() != null) p.closeInventory();
		//p.teleport(getManager().getWorld().getSpawnLocation());
		Bukkit.getScheduler().runTaskLater(MCEssentials.getInstance(), new Runnable() {
			public void run() {
				kitS.open(p);
			}
		}, 2L);
	}
	
	public void start() {
		hasStarted = true;
	}
	
	public MinigameKit getKit(Player p) {
		return players.get(p.getName());
	}
	
	public MinigameKit getKit(String s) {
		for(MinigameKit kit : kits) {
			if(kit.getName().equalsIgnoreCase(s)) return kit;
		}
		return null;
	}
	
	public void addPlayer(Player p, MinigameKit k) {
		players.put(p.getName(), k);
	}
	
	public boolean isGameStarted() {
		return hasStarted;
	}
	
}