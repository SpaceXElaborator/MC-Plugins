package com.terturl.MeleeCraftQuests.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;

import com.terturl.MeleeCraftQuests.Main;
import com.terturl.MeleeCraftQuests.MeleeQuestPlayer;
import com.terturl.MeleeCraftQuests.Quests.MeleeQuest;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestTame;

public class TameListener implements Listener {

	@EventHandler
	public void onTame(EntityTameEvent e) {
		if(!(e.getOwner() instanceof Player)) return;
		MeleeQuestPlayer p = Main.getInstance().getPlayerManager().getPlayer(e.getOwner().getUniqueId());
		List<MeleeQuest> activeQuests = new ArrayList<MeleeQuest>();
		for(MeleeQuest q : p.getQuests()) {
			if(!(q instanceof QuestTame)) continue;
			activeQuests.add(q);
		}
		if(activeQuests.size() <= 0) return;
		for(MeleeQuest q : activeQuests) {
			QuestTame qt = (QuestTame)q;
			if(!qt.containsEntity(e.getEntity().getType())) continue;
			qt.updateEntity(e.getEntity().getType(), p);
		}
	}
	
}
