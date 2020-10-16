package com.terturl.ZombieMinigame.Commands.ArenaCommands;

import org.bukkit.entity.Player;

import com.terturl.MeleeCraftEssentials.Commands.CraftCommand;
import com.terturl.ZombieMinigame.ZombieMinigame;

public class CreateArena extends CraftCommand {

	public CreateArena() {
		super("create");
	}
	
	@Override
	public void handleCommand(Player p, String[] args) {
		ZombieMinigame.getInstance().getArenaHandler().addArena(args[0]);
		p.sendMessage("Arena Added");
	}
	
}