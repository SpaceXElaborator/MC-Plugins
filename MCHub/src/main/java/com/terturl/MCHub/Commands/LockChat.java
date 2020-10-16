package com.terturl.MCHub.Commands;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.terturl.MCHub.MCHub;
import com.terturl.MeleeCraftEssentials.Commands.CraftCommand;

import net.md_5.bungee.api.ChatColor;

public class LockChat extends CraftCommand {

	public LockChat() {
		super("lc");
	}
	
	@Override
	public void handleCommand(Player p, String[] args) {
		if(p.isOp()) {
			FileConfiguration config = MCHub.getInstance().getConfiguration().loadConfig("messages.yml");
			
			if(MCHub.getInstance().isChatLocked) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("YouUnlocked")));
				MCHub.getInstance().isChatLocked = false;
				for(Player p2 : Bukkit.getOnlinePlayers()) {
					p2.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("ChatUnlocked")));
				}
			} else {
				MCHub.getInstance().isChatLocked = true;
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("YouLocked")));
				for(Player p2 : Bukkit.getOnlinePlayers()) {
					p2.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("ChatLocked")));
				}
			}
		}
	}
	
}