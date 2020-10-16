package com.terturl.MeleeCraftQuests.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.terturl.MeleeCraftQuests.Main;
import com.terturl.MeleeCraftQuests.MeleeQuestPlayer;
import com.terturl.MeleeCraftQuests.Quests.MeleeQuest;
import com.terturl.MeleeCraftQuests.Quests.Subs.QuestKill;

public class KillListener implements Listener {
	
	@EventHandler
	public void onKill(EntityDeathEvent e) {
		if(!(e.getEntity().getKiller() instanceof Player)) return;
		MeleeQuestPlayer p = Main.getInstance().getPlayerManager().getPlayer(e.getEntity().getKiller().getUniqueId());
		List<MeleeQuest> activeQuests = new ArrayList<MeleeQuest>();
		for(MeleeQuest q : p.getQuests()) {
			if(!(q instanceof QuestKill)) continue;
			activeQuests.add(q);
		}
		if(activeQuests.size() <= 0) return;
		for(MeleeQuest q : activeQuests) {
			QuestKill qk = (QuestKill)q;
			if(!qk.containsEntity(e.getEntity().getType())) continue;
			qk.updateEntity(e.getEntity().getType(), p);
		}
	}
	
}