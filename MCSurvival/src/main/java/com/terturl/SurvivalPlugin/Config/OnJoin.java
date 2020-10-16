package com.terturl.SurvivalPlugin.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import com.terturl.SurvivalPlugin.Main;

public class OnJoin implements Listener {

	protected Map<UUID, BossBar> boss = new HashMap<UUID, BossBar>();
	protected List<UUID> wait = new ArrayList<UUID>();
	
	public void onJoin() {
		ConfigurationSection cs = Main.getInstance().getCM().getConfig("players.yml").getConfigurationSection("P-UUIDs");
		for(String css : cs.getKeys(false)) {
			BossBar bb = Bukkit.createBossBar("Water Level", BarColor.BLUE, BarStyle.SEGMENTED_10);
			Double d = cs.getDouble(css + ".thirst");
			bb.setProgress(d);
			UUID uu = UUID.fromString(css);
			boss.put(uu, bb);
		}
	}
	
	@EventHandler
	public void onJoinServ(PlayerJoinEvent e) {
		if(!boss.containsKey(e.getPlayer().getUniqueId())) {
			BossBar bb = Bukkit.createBossBar("Water Level", BarColor.BLUE, BarStyle.SEGMENTED_10);
			bb.addPlayer(e.getPlayer());
			bb.setProgress(1.0D);
			boss.put(e.getPlayer().getUniqueId(), bb);
		} else {
			BossBar bb = boss.get(e.getPlayer().getUniqueId());
			bb.addPlayer(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		 BossBar bb = boss.get(e.getPlayer().getUniqueId());
		 FileConfiguration config = Main.getInstance().getCM().getConfig("players.yml");
		 config.set(String.valueOf("P-UUIDs." + e.getPlayer().getUniqueId()) + ".thirst", bb.getProgress());
		 try {
			config.save("plugins/SMPSurvival/players.yml");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		double baseRemoval = 0.002D;
		BossBar bb = boss.get(e.getPlayer().getUniqueId());
		final Player p = e.getPlayer();
		if(p.getGameMode() == GameMode.CREATIVE || wait.contains(p.getUniqueId())) return;
		if(p.isSneaking()) baseRemoval = 0.001D;
		if(p.isSprinting()) baseRemoval = 0.005D;
		bb.setProgress(bb.getProgress() - baseRemoval);
		wait.add(p.getUniqueId());
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			public void run() {
				wait.remove(p.getUniqueId());
			}
		}, 20L);
	}
	
	@EventHandler
	public void onHandle(PlayerInteractEvent e) {
		BossBar bb = boss.get(e.getPlayer().getUniqueId());
		if(e.getHand().equals(EquipmentSlot.HAND) && e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			if(e.getItem().getType().equals(Material.WATER_BUCKET)) {
				bb.setProgress(1.0D);
				e.getItem().setType(Material.BUCKET);
			}
		}
	}
	
	@EventHandler
	public void onConsume(PlayerItemConsumeEvent e) {
		BossBar bb = boss.get(e.getPlayer().getUniqueId());
		if(e.getItem().getType().equals(Material.POTION)) {
			PotionMeta pm = (PotionMeta) e.getItem().getItemMeta();
			if(pm.getBasePotionData().getType().equals(PotionType.WATER)) {
				if(bb.getProgress() + 0.5D > 1.0D) bb.setProgress(1.0D);
				else bb.setProgress(bb.getProgress() + 0.5D);
			}
		}
	}
	
}