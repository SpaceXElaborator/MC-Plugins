package com.terturl.ZombieMinigame.Arena.Listeners.Kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import com.terturl.MeleeCraftEssentials.MCEssentials;
import com.terturl.ZombieMinigame.ZombieMinigame;
import com.terturl.ZombieMinigame.Arena.Arena;

public class SlayerListener implements Listener {

	@EventHandler
	public void toggleFlight(PlayerToggleFlightEvent e) {
		Player p = e.getPlayer();
		if(!ZombieMinigame.getInstance().getArenaHandler().playerInArena(p)) return;
		Arena a = ZombieMinigame.getInstance().getArenaHandler().getArenaFromPlayer(p);
		if(!a.hasKit(p, "slayer")) return;
		e.setCancelled(true);
		p.setAllowFlight(false);
		p.setVelocity(p.getVelocity().add(new Vector(0, 0.3, 0)));
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(!ZombieMinigame.getInstance().getArenaHandler().playerInArena(p)) return;
		Arena a = ZombieMinigame.getInstance().getArenaHandler().getArenaFromPlayer(p);
		if(!a.hasKit(p, "slayer")) return;
		if(!p.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.AIR)) return;
		p.setAllowFlight(true);
	}
	
	@EventHandler
	public void playerToggle(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		if(!ZombieMinigame.getInstance().getArenaHandler().playerInArena(p)) return;
		Arena a = ZombieMinigame.getInstance().getArenaHandler().getArenaFromPlayer(p);
		if(!a.hasKit(p, "slayer")) return;
		if (p.isFlying())
			return;
		if (p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR
				&& p.hasPermission("pop.groundpound")) {
			Location loc = p.getLocation();
			loc.add(0.0D, -1.0D, 0.0D);
			p.setVelocity(new Vector(0.0D, -1.5D, 0.0D));
			rg(p, 3, 4);
		}
	}

	public void rg(final Player p, final int start, final int rad) {

		Bukkit.getScheduler().runTaskLater(MCEssentials.getInstance(), new Runnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				if(start != rad) {
					if (p.isOnGround()) {
						for (Block b : circle(p, start)) {
							b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getType());
							//FallingBlock fb = b.getWorld().spawnFallingBlock(b.getLocation().add(0, 1, 0), b.getState().getBlockData());
							//b.setType(Material.AIR);
							//fb.setVelocity(new Vector(0, .10, 0));
						}
						rg(p, start+1, rad);
					}
				}
			}

		}, 5L);
	}
	
	public List<Block> circle(Player p, int radius) {
		List<Block> blocks = new ArrayList<Block>();
		for(int x = (p.getLocation().getBlockX()-radius); x  <= (p.getLocation().getBlockX() + radius); x++) {
			for(int z = (p.getLocation().getBlockZ()-radius); z <= (p.getLocation().getBlockZ()+radius); z++) {
				blocks.add(p.getWorld().getBlockAt(x, p.getLocation().getBlockY()-1, z));
			}
		}
		return blocks;
	}
	
}