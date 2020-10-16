package com.terturl.MeleeCraftEssentials.Player;

import java.util.logging.Level;

import org.bukkit.entity.Player;

import com.terturl.MeleeCraftEssentials.MCEssentials;
import com.terturl.MeleeCraftEssentials.Database.MCDatabase;

import net.md_5.bungee.api.ChatColor;

public class MeleeCoinDB extends MCDatabase {

	public MeleeCoinDB() {
		super("MeleeCoin", "`uuid` CHAR(36) NOT NULL:`coins` INT NOT NULL");
		MCEssentials.getInstance().getLogger().log(Level.INFO, ChatColor.GOLD + "[*] " + ChatColor.GREEN + "Established connection to Coin Database");
	}

	public int getCoins(Player p) {
		Object j = getSingle("coins", "uuid", p.getUniqueId().toString());
		if(j != null) {
			return (Integer)j;
		}
		return 0;
	}
	
	public void addPlayer(Player p) {
		insert(p.getUniqueId(), 0);
	}
	
	public void setCoins(Player p, Integer coins) {
		updateSingle("coins", "uuid", coins, p.getUniqueId().toString());
	}
	
}