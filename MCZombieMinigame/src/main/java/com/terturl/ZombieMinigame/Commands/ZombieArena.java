package com.terturl.ZombieMinigame.Commands;

import com.terturl.MeleeCraftEssentials.Commands.CommandMeta;
import com.terturl.MeleeCraftEssentials.Commands.CraftCommand;
import com.terturl.ZombieMinigame.Commands.ArenaCommands.ArenaCommand;
import com.terturl.ZombieMinigame.Commands.PlayerCommands.JoinCommand;

@CommandMeta(aliases = "zs")
public class ZombieArena extends CraftCommand {

	public ZombieArena() {
		super("zombiesurvival");
		addSubCommand(new ArenaCommand(), new JoinCommand());
	}
	
}