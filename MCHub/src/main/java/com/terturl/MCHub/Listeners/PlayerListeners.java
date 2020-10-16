package com.terturl.MCHub.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.terturl.MCHub.InventoryManager;
import com.terturl.MCHub.MCHub;

import net.md_5.bungee.api.ChatColor;

public class PlayerListeners implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.getPlayer().getInventory().clear();
		YamlConfiguration config = MCHub.getInstance().getConfiguration().loadConfig("config.yml");
		ItemStack item = new ItemStack(Material.valueOf(config.getString("ServerSelectorIcon").toUpperCase()));
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("ServerSelectorName")));
		List<String> lore = new ArrayList<String>();
		for(String s : config.getStringList("ServerSelectorLore")) {
			lore.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		e.getPlayer().getInventory().setItem(Integer.valueOf(config.getString("ServerSelectorSlot")), item);
		e.getPlayer().updateInventory();
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		YamlConfiguration config = MCHub.getInstance().getConfiguration().loadConfig("config.yml");
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if(!e.getHand().equals(EquipmentSlot.HAND)) return;
			if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.valueOf(config.getString("ServerSelectorIcon").toUpperCase()))) {
				InventoryManager im = new InventoryManager(Integer.valueOf(config.getString("SelectorInvSize")), ChatColor.translateAlternateColorCodes('&', config.getString("SelectorInvName")));
				im.open(e.getPlayer());
			}
			return;
		}
		return;
	}
	
	@EventHandler
	public void PlayerHurt(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;
		Player p = (Player) e.getEntity();
		if (e.getCause().equals(DamageCause.FALL))
			e.setCancelled(true);
		if (e.getCause().equals(DamageCause.VOID)) {
			e.setCancelled(true);
			p.teleport(p.getWorld().getSpawnLocation());
			p.setHealth(20D);
		}

	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void invInteract(InventoryClickEvent e) {
		YamlConfiguration config = MCHub.getInstance().getConfiguration().loadConfig("config.yml");
		if(e.getClickedInventory() == null) return;
		if(!(e.getWhoClicked() instanceof Player)) return;
		Inventory view = e.getView().getBottomInventory();
		//if(!view.getTitle().equals(config.getString("ServerSelectorName"))) return;
		ItemStack item = view.getItem(e.getSlot());
		if(item == null || e.getSlot() == -999) return;
		//if(item.getType() != Material.valueOf(config.getString("ServerSelectorIcon").toUpperCase())) return;
		e.setCancelled(true);
	}

	@EventHandler
	public void PlayerDamagePlayer(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player))
			return;
		if (!(e.getEntity() instanceof Player))
			return;

		Player p1 = (Player) e.getEntity();
		Player p2 = (Player) e.getDamager();
		p2.hidePlayer(MCHub.getInstance(), p1);
		p2.playSound(p2.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1.0F, -3.0F);
		p2.spawnParticle(Particle.HEART, p1.getLocation(), 150, 3.0D, 5.0D, 3.0D, 0.0D);
		p2.spawnParticle(Particle.CLOUD, p1.getLocation(), 150, 3.0D, 5.0D, 3.0D, 0.0D);
		p2.spawnParticle(Particle.SMOKE_LARGE, p1.getLocation(), 150, 3.0D, 5.0D, 3.0D, 0.0D);
		p2.spawnParticle(Particle.LAVA, p1.getLocation(), 150, 3.0D, 5.0D, 3.0D, 0.0D);

		e.setCancelled(true);
	}

	@EventHandler
	public void FoodChange(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void PlayerDeath(PlayerDeathEvent e) {
		e.setDeathMessage(null);
	}

	@EventHandler
	public void doubleJump(PlayerToggleFlightEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE)
			return;
		e.setCancelled(true);
		e.getPlayer().setFlying(false);
		e.getPlayer().setAllowFlight(false);
		e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(0.9D).setY(0.9D));
		e.getPlayer().setExp(0.0F);
		e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 1.0F, 15.0F);
		e.getPlayer().spawnParticle(Particle.SMOKE_NORMAL, e.getPlayer().getLocation(), 8);
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if(e.getPlayer().isOp()) return;
		if(MCHub.getInstance().isChatLocked) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE)
			return;
		if (e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR
				&& e.getPlayer().getExp() == 0.0F) {
			e.getPlayer().setAllowFlight(true);
			e.getPlayer().setExp(1.0F);
		}
		
		Location pLoc = e.getPlayer().getLocation();
		Location min = new Location(pLoc.getWorld(), pLoc.getX(), pLoc.getY()-1, pLoc.getZ());
		FileConfiguration config = MCHub.getInstance().getConfiguration().loadConfig("config.yml");
		if(min.getBlock().getType().equals(Material.valueOf(config.getString("JumpPad").toUpperCase()))) {
			e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(1.3D).setY(1.3D));
			e.getPlayer().setExp(0.0F);
			e.getPlayer().setFlying(false);
			e.getPlayer().setAllowFlight(false);
		}
	}
	
}