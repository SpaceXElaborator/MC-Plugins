package com.terturl.MeleeCraftEssentials.Player.Mail.Utils;

import java.util.ArrayList;
import java.util.List;

import com.terturl.MeleeCraftEssentials.Inventory.InventoryButton;

public class MailCarrier {

	private String name;
	private List<InventoryButton> mail = new ArrayList<InventoryButton>();
	
	public MailCarrier(String player) {
		this.name = player;
	}
	
	public String getName() {
		return name;
	}
	
	public List<InventoryButton> getMail() {
		return mail;
	}
	
}