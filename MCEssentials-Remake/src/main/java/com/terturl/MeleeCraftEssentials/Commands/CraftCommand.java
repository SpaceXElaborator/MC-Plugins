package com.terturl.MeleeCraftEssentials.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public abstract class CraftCommand implements CommandExecutor, TabCompleter {

	private final Map<String, CraftCommand> subCommands = new HashMap<String, CraftCommand>();

	private final String name;
	private CraftCommand levelUp = null;

	protected CraftCommand(String name) {
		this.name = name;
	}

	protected CraftCommand(final String name, CraftCommand... subCommands) {
		this.name = name;
		addSubCommand(subCommands);
	}

	public final void addSubCommand(CraftCommand... subCommands) {
		for (CraftCommand subCommand : subCommands) {
			if (subCommand.getLevelUp() != null)
				return;
			this.subCommands.put(subCommand.getName(), subCommand);
			subCommand.setLevelUp(this);
		}
	}

	public boolean finalCommand() {
		return false;
	}
	
	public final boolean onCommand(final CommandSender cs, Command cmd, String label, String[] args) {
		try {
			CraftCommand subCommand = null;

			if (subCommand == null && args.length > 0) {
				subCommand = getSubCommand(args[0]);
			}
			if (subCommand != null) {
				if(finalCommand()) {
					try {
						dispatchHandler(cs, args);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				String[] cArgs = (args.length < 2) ? new String[0] : Arrays.copyOfRange(args, 1, args.length);
				subCommand.onCommand(cs, cmd, label, cArgs);
				return true;
			}
			try {
				dispatchHandler(cs, args);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public final List<String> onTabComplete(CommandSender cs, Command cmd, String label, String[] args) {
		if (args.length > 1) {
			CraftCommand possibleHigherLevelSubCommand;
			if ((possibleHigherLevelSubCommand = getSubCommand(args[0])) != null)
				return possibleHigherLevelSubCommand.onTabComplete(cs, cmd, label,
						Arrays.copyOfRange(args, 1, args.length));
		} else if (args.length == 1) {
			List<String> strings = getSubCommandsForPartial(args[0]);
			if (strings.size() != 0) {
				strings.addAll(handleTabComplete(cs, cmd, label, args));
				return strings;
			}
		}
		return handleTabComplete(cs, cmd, label, args);
	}

	private void dispatchHandler(CommandSender cs, String[] args) {
		if (cs instanceof Player)
			handleCommand((Player) cs, args);
		else if (cs instanceof ConsoleCommandSender)
			handleCommand((ConsoleCommandSender) cs, args);
		else if (cs instanceof BlockCommandSender)
			handleCommand((BlockCommandSender) cs, args);
	}

	public final List<String> getSubCommandsForPartial(String s) {
		List<String> commands = new ArrayList<String>();
		CraftCommand subCommand;
		if ((subCommand = getSubCommand(s)) != null) {
			commands.add(subCommand.getName());
			return commands;
		}
		String s2 = s.toUpperCase();
		for (String s1 : subCommands.keySet()) {
			if (s1.toUpperCase().startsWith(s2))
				commands.add(s1);
		}
		return commands;
	}

	protected void handleCommand(Player p, String[] args) {}
	protected void handleCommand(ConsoleCommandSender ccs, String[] args) {}
	protected void handleCommand(BlockCommandSender bcs, String[] args) {}

	protected List<String> handleTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (this.subCommands.size() > 0)
			return Collections.emptyList();
		List<String> ss = new ArrayList<String>();
		String arg = (args.length > 0) ? args[args.length - 1].toLowerCase() : "";
		for (Player player : Bukkit.getOnlinePlayers()) {
			String name1 = player.getName();
			if (name1.toLowerCase().startsWith(arg))
				ss.add(name1);
		}
		return ss;
	}

	public final CraftCommand getSubCommand(String s) {
		if (subCommands.containsKey(s))
			return subCommands.get(s);
		for (String s1 : subCommands.keySet()) {
			if (s1.equalsIgnoreCase(s))
				return subCommands.get(s1);
		}
		return null;
	}

	public final String getName() {
		return name;
	}

	public final CraftCommand getLevelUp() {
		return (levelUp != null) ? levelUp : null;
	}

	private void setLevelUp(CraftCommand cc) {
		this.levelUp = cc;
	}

}