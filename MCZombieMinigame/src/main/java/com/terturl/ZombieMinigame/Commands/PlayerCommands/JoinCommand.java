package com.terturl.ZombieMinigame.Commands.PlayerCommands;

import org.bukkit.entity.Player;

import com.terturl.MeleeCraftEssentials.Commands.CraftCommand;
import com.terturl.ZombieMinigame.ZombieMinigame;

public class JoinCommand extends CraftCommand {

	public JoinCommand() {
		super("join");
	}

	@Override
	public void handleCommand(Player p, String[] args) {
		if(ZombieMinigame.getInstance().getArenaHandler().joinArena(p, args[0])) {
			p.sendMessage("Joining Arena: " + args[0]);
			return;
		} else {
			p.sendMessage("Arena not found!");
			return;
		}
	}
	
}