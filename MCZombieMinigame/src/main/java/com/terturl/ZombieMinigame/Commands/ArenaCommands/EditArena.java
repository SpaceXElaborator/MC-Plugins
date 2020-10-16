package com.terturl.ZombieMinigame.Commands.ArenaCommands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.terturl.MeleeCraftEssentials.Commands.CraftCommand;
import com.terturl.ZombieMinigame.ZombieMinigame;

import net.md_5.bungee.api.ChatColor;

public class EditArena extends CraftCommand {

	public EditArena() {
		super("edit");
	}
	
	@Override
	public void handleCommand(Player p, String[] args) {
		if(ZombieMinigame.getInstance().getArenaHandler().editArena(p, args[0])) {
			p.sendMessage("Editing Arena: " + args[0]);
			ZombieMinigame.getInstance().getPlayerInventoryManager().saveInventory(p);
			p.getInventory().clear();
			p.updateInventory();
			addEditItems(p);
			return;
		} else {
			p.sendMessage("Arena does not exist!");
			return;
		}
	}
	
	private void addEditItems(Player p) {
		
		ItemStack item = new ItemStack(Material.STICK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Add Spawn Point");
		item.setItemMeta(meta);
		
		ItemStack cancel = new ItemStack(Material.BARRIER);
		ItemMeta cancelM = cancel.getItemMeta();
		cancelM.setDisplayName(ChatColor.RED + "Stop Editing");
		cancel.setItemMeta(cancelM);
		
		ItemStack startEnd = new ItemStack(Material.WOODEN_AXE);
		ItemMeta startEndM = startEnd.getItemMeta();
		startEndM.setDisplayName(ChatColor.GREEN + "Arena Box Setter");
		startEnd.setItemMeta(startEndM);
		
		ItemStack lobby = new ItemStack(Material.BLAZE_ROD);
		ItemMeta lobbyM = lobby.getItemMeta();
		lobbyM.setDisplayName(ChatColor.GOLD + "Set Lobby Point");
		lobby.setItemMeta(lobbyM);
		
		p.getInventory().addItem(item);
		p.getInventory().addItem(startEnd);
		p.getInventory().addItem(lobby);
		p.getInventory().setItem(8, cancel);
		
		p.updateInventory();
	}
	
}