package com.terturl.MeleeCraftEssentials.Player.Mail.Commands.SubCommands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.terturl.MeleeCraftEssentials.MCEssentials;
import com.terturl.MeleeCraftEssentials.Commands.CraftCommand;
import com.terturl.MeleeCraftEssentials.Player.Mail.MailDB;

public class MailSend extends CraftCommand {

	public MailSend() {
		super("send");
	}
	
	@Override
	public void handleCommand(Player p, String[] args) {
		FileConfiguration config = MCEssentials.getInstance().getConfiguration().loadConfig("messages.yml");
		String player = args[0];
		if(player.equals(p.getName())) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Mail-CantSendToSelf")));
			return;
		}
		String s = StringUtils.join(args, ' ', 1, args.length);
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', sanatize(config.getString("Mail-MessageSent"), p, s, player)));
		FileConfiguration conf = MCEssentials.getInstance().getConfiguration().loadConfig("config.yml");
		MailDB db = (MailDB)MCEssentials.getInstance().getDBManager().getDatabase("Mail");
		db.addMessage(player, p.getName(), conf.getString("Server-Name"), s);
	}
	
	public String sanatize(String s, Player p, String message, String to) {
		s = s.replaceAll("%player%", to);
		s = s.replaceAll("%message%", message);
		s = s.replaceAll("%sender%", p.getName());
		return s;
	}
	
}