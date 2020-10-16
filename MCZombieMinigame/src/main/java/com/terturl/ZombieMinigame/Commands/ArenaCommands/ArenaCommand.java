package com.terturl.ZombieMinigame.Commands.ArenaCommands;

import com.terturl.MeleeCraftEssentials.Commands.CraftCommand;

public class ArenaCommand extends CraftCommand {

	public ArenaCommand() {
		super("arena");
		addSubCommand(new CreateArena(), new EditArena(), new StartArena());
	}
	
}