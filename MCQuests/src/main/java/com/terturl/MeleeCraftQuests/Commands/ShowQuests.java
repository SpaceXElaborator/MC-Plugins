package com.terturl.MeleeCraftQuests.Commands;

import org.bukkit.entity.Player;

import com.terturl.MeleeCraftEssentials.Commands.CraftCommand;
import com.terturl.MeleeCraftQuests.Inventories.CategoryInvList;

public class ShowQuests extends CraftCommand {
	
	public ShowQuests() {
		super("quests");
	}
	
	@Override
	public void handleCommand(Player p, String[] args) {
		//Create a new inventory with all the categories and open it
		CategoryInvList cil = new CategoryInvList();
		cil.open(p);
	}
	
}