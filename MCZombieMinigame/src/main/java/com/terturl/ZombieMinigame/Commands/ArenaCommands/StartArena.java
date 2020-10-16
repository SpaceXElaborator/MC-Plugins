package com.terturl.ZombieMinigame.Commands.ArenaCommands;

import org.bukkit.entity.Player;

import com.terturl.MeleeCraftEssentials.Commands.CraftCommand;
import com.terturl.ZombieMinigame.ZombieMinigame;

public class StartArena extends CraftCommand {

	public StartArena() {
		super("start");
	}
	
	@Override
	public void handleCommand(Player p, String[] args) {
		if(ZombieMinigame.getInstance().getArenaHandler().startArena(args[0])) {
			p.sendMessage("Starting Arena...");
		} else {
			p.sendMessage("Arena not found");
		}
	}
	
}