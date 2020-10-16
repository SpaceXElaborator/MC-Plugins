package com.terturl.MeleeCraftEssentials.Player.Mail.Utils;

import com.terturl.MeleeCraftEssentials.Inventory.InventoryButton;

public class PlayerMail extends InventoryButton {

	private String from;
	private String message;
	private String server;
	private String date;
	
	public PlayerMail(String from, String message, String server, String date) {
		this.from = from;
		this.message = message;
		this.server = server;
		this.date = date;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getFrom() {
		return from;
	}
	
	public String getServer() {
		return server;
	}
	
	public String getDate() {
		return date;
	}
	
}