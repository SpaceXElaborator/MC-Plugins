package com.terturl.MeleeCraftQuests.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

import com.terturl.MeleeCraftQuests.Main;
import com.terturl.MeleeCraftQuests.MeleeQuestPlayer;
import com.terturl.MeleeCraftQuests.Quests.MeleeQuest;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestCollect;

public class CollectListener implements Listener {

	@EventHandler
	public void onCollect(EntityPickupItemEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		Player play = (Player)e.getEntity();
		MeleeQuestPlayer p = Main.getInstance().getPlayerManager().getPlayer(play.getUniqueId());
		List<MeleeQuest> activeQuests = new ArrayList<MeleeQuest>();
		for(MeleeQuest q : p.getQuests()) {
			if(!(q instanceof QuestCollect)) continue;
			activeQuests.add(q);
		}
		if(activeQuests.size() <= 0) return;
		for(MeleeQuest q : activeQuests) {
			QuestCollect qk = (QuestCollect)q;
			if(!qk.containsMaterial(e.getItem().getItemStack().getType())) continue;
			qk.updateMaterial(e.getItem().getItemStack().getType(), p);
		}
	}
	
}