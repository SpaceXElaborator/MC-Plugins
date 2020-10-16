package com.terturl.MeleeCraftQuests.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

import com.terturl.MeleeCraftQuests.Main;
import com.terturl.MeleeCraftQuests.MeleeQuestPlayer;
import com.terturl.MeleeCraftQuests.Quests.MeleeQuest;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestBreed;

public class BreedListener implements Listener {
	
	@EventHandler
	public void onBreed(EntityBreedEvent e) {
		if(!(e.getBreeder() instanceof Player)) return;
		MeleeQuestPlayer p = Main.getInstance().getPlayerManager().getPlayer(e.getBreeder().getUniqueId());
		List<MeleeQuest> activeQuests = new ArrayList<MeleeQuest>();
		for(MeleeQuest q : p.getQuests()) {
			if(!(q instanceof QuestBreed)) continue;
			activeQuests.add(q);
		}
		if(activeQuests.size() <= 0) return;
		for(MeleeQuest q : activeQuests) {
			QuestBreed qb = (QuestBreed)q;
			if(!qb.containsEntity(e.getEntity().getType())) continue;
			qb.updateEntity(e.getEntity().getType(), p);
		}
	}
	
}