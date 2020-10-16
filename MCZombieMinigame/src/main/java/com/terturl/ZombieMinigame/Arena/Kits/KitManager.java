package com.terturl.ZombieMinigame.Arena.Kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;

public class KitManager {

	public List<Kit> kits = new ArrayList<Kit>();
	
	public KitManager() {
		registerKits();
	}
	
	private void registerKits() {
		Kit slayer = new Kit("Slayer", Material.IRON_SWORD);
		slayer.addLore(ChatColor.GREEN + "Slayer");
		slayer.addLore(ChatColor.WHITE + "-------------------------");
		slayer.addLore("The villagers paid him to protected their town. He");
		slayer.addLore("said he'd do anything for a prismarine shard.");
		slayer.addLore("");
		slayer.addLore("Basic all around survivor. Has the ability to sharpen");
		slayer.addLore("his sword, providing he has time to");
		
		Kit engineer = new Kit("Engineer", Material.DISPENSER);
		engineer.addLore(ChatColor.GREEN + "Stats");
		engineer.addLore(ChatColor.WHITE + "-------------------------");
		engineer.addLore("Health: 25 Hearts");
		engineer.addLore("Damage: 3 Hearts (Pistol)");
		engineer.addLore("Damage: 15 Hearts (Shotgun-Close) 5 Hearts (Shotgun-Far)");
		engineer.addLore("Turret Health: 15 Hearts");
		engineer.addLore("Turret Firing Speed: 1 Shot per 3 seconds");
		engineer.addLore("Turret Damage: 10 Hearts");
		engineer.addLore("Movement: Normal");
		engineer.addLore("");
		engineer.addLore(ChatColor.GREEN + "Abilities");
		engineer.addLore(ChatColor.WHITE + "-------------------------");
		engineer.addLore("- Level 1: 1 Turret, Egg Shooter");
		engineer.addLore("- Level 2: Egg Shooter 100% more effective");
		engineer.addLore("- Level 3: Arrow Shooter");
		engineer.addLore("- Level 4: 2 Turrets, Arrow Shooter 100% more effective");
		engineer.addLore("- Level 5: Double Shot");
		engineer.addLore("- Level 6: Firing Speed Upgrade");
		engineer.addLore("- Level 7: 3 Turrets");
		engineer.addLore("- Level 8: Fireball Shooter");
		engineer.addLore("- Level 9: Fireball Shooter 50% more effective");
		engineer.addLore("- Level 10: Firing Speed Upgrade, Fireball Shooter 50% more effective");
		
		kits.add(slayer);
		kits.add(engineer);
	}
	
	public List<Kit> getKits() {
		return kits;
	}
	
}