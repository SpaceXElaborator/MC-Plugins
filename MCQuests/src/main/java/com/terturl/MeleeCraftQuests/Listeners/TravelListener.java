package com.terturl.MeleeCraftQuests.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.terturl.MeleeCraftQuests.Main;
import com.terturl.MeleeCraftQuests.MeleeQuestPlayer;
import com.terturl.MeleeCraftQuests.Quests.MeleeQuest;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestTravel;

public class TravelListener implements Listener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		MeleeQuestPlayer p = Main.getInstance().getPlayerManager().getPlayer(e.getPlayer().getUniqueId());
		List<MeleeQuest> activeQuests = new ArrayList<MeleeQuest>();
		if(p.getQuests().size() <= 0 || p.getQuests().isEmpty()) return;
		for(MeleeQuest q : p.getQuests()) {
			if(!(q instanceof QuestTravel)) continue;
			activeQuests.add(q);
		}
		if(activeQuests.size() <= 0 || activeQuests.isEmpty()) return;
		for(MeleeQuest q : activeQuests) {
			QuestTravel qu = (QuestTravel)q;
			Location loc = qu.getLoc();
			if(withinLoc(e.getPlayer().getLocation(), loc)) {
				q.onComplete(p);
			}
		}
	}
	
	private boolean withinLoc(Location loc1, Location loc2) {
		if(loc1.distanceSquared(loc2) <= 50.0) return true;
		return false;
	}
	
}