package com.terturl.MeleeCraftEssentials.Player.Mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import com.terturl.MeleeCraftEssentials.MCEssentials;
import com.terturl.MeleeCraftEssentials.Database.MCDatabase;
import com.terturl.MeleeCraftEssentials.Player.Mail.Utils.PlayerMail;

import net.md_5.bungee.api.ChatColor;

public class MailDB extends MCDatabase {

	public MailDB() {
		super("Mail", "`to` VARCHAR(50) NOT NULL:`from` VARCHAR(50) NOT NULL:`server` VARCHAR(50) NOT NULL:`message` VARCHAR(255) NOT NULL:`date` DATE NOT NULL");
		MCEssentials.getInstance().getLogger().log(Level.INFO, ChatColor.GOLD + "[*] " + ChatColor.GREEN + "Established connection to Mail Database");
	}
	
	public void addMessage(String to, String from, String server, String message) {
		Date d = new Date();
		java.sql.Date sqlD = new java.sql.Date(d.getTime());
		insert(to, from, server, message, sqlD.toString());
	}
	
	public List<PlayerMail> getMessages(String to) {
		List<PlayerMail> mail = new ArrayList<PlayerMail>();
		List<Object> objs = getAll("to", to, 5);
		int x = 0;
		List<String> tempN = new ArrayList<String>();
		for(int i = 0; i < objs.size(); i++) {
			tempN.add(objs.get(i).toString());
			x++;
			
			if(x == 5) {
				mail.add(new PlayerMail(tempN.get(1), tempN.get(3), tempN.get(2), tempN.get(4)));
				x = 0;
				tempN.clear();
			}
		}
		if(!mail.isEmpty()) {
			return mail;
		} else {
			return null;
		}
	}
	
}