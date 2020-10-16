package com.terturl.MeleeCraftQuests.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.terturl.MeleeCraftQuests.Main;
import com.terturl.MeleeCraftQuests.MeleeQuestPlayer;
import com.terturl.MeleeCraftQuests.Quests.MeleeQuest;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestMine;

public class MineListener implements Listener {

	@EventHandler
	public void onMine(BlockBreakEvent e) {
		MeleeQuestPlayer p = Main.getInstance().getPlayerManager().getPlayer(e.getPlayer().getUniqueId());
		List<MeleeQuest> activeQuests = new ArrayList<MeleeQuest>();
		for(MeleeQuest q : p.getQuests()) {
			if(!(q instanceof QuestMine)) continue;
			activeQuests.add(q);
		}
		if(activeQuests.size() <= 0) return;
		for(MeleeQuest q : activeQuests) {
			QuestMine qb = (QuestMine)q;
			if(!qb.containsMaterial(e.getBlock().getType())) continue;
			qb.updateEntity(e.getBlock().getType(), p);
		}
	}
	
}