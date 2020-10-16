package com.terturl.MCHub.Commands;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.terturl.MCHub.MCHub;
import com.terturl.MeleeCraftEssentials.Commands.CraftCommand;

import net.md_5.bungee.api.ChatColor;

public class ClearChat extends CraftCommand {

	public ClearChat() {
		super("cc");
	}
	
	@Override
	public void handleCommand(Player p, String[] args) {
		if(p.isOp()) {
			FileConfiguration config = MCHub.getInstance().getConfiguration().loadConfig("messages.yml");
			for(Player p2 : Bukkit.getOnlinePlayers()) {
				for(int x = 0; x<=50; x++) {
					p2.sendMessage("");
				}
			}
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("ChatCleared")));
		}
	}
	
}
