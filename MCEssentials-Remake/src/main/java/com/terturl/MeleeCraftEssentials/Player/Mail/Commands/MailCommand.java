package com.terturl.MeleeCraftEssentials.Player.Mail.Commands;

import org.bukkit.entity.Player;

import com.terturl.MeleeCraftEssentials.Commands.CraftCommand;
import com.terturl.MeleeCraftEssentials.Player.Mail.Commands.SubCommands.MailCheck;
import com.terturl.MeleeCraftEssentials.Player.Mail.Commands.SubCommands.MailSend;

public class MailCommand extends CraftCommand {

	public MailCommand() {
		super("mail");
		addSubCommand(new MailCheck(), new MailSend());
	}
	
	@Override
	public void handleCommand(Player p, String[] args) {
		//TODO: Help Menu
	}
	
}