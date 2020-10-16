package com.terturl.ZombieMinigame.Arena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitScheduler;

import com.terturl.ZombieMinigame.ZombieMinigame;
import com.terturl.ZombieMinigame.Arena.Inventory.KitInventory;
import com.terturl.ZombieMinigame.Arena.Kits.Kit;

public class Arena {

	private String name;
	private List<Location> spawnPoints;
	private Location start;
	private Location end;
	private Location lobby;
	private boolean Started;
	
	private Map<UUID, Kit> playerKit = new HashMap<UUID, Kit>();
	private List<UUID> players = new ArrayList<UUID>();
	
	private int zombies = 0;
	
	public Arena(String s) {
		name = s;
		spawnPoints = new ArrayList<Location>();
		Started = false;
	}
	
	public void addPlayer(Player p) {
		if(players.size() >= spawnPoints.size()) {
			p.sendMessage("Arena is full!");
			return;
		}
		players.add(p.getUniqueId());
		p.teleport(lobby.add(0, 1, 0));
		KitInventory ki = new KitInventory(this);
		ki.open(p);
		return;
	}
	
	public void setKit(Player p, Kit k) {
		playerKit.put(p.getUniqueId(), k);
	}
	
	public void startCountdown() {
		
	}
	
	public void start() {
		Started = true;
		for(int x = 0; x < players.size(); x++) {
			Bukkit.getPlayer(players.get(x)).sendMessage("Starting Arena!");
			Bukkit.getPlayer(players.get(x)).teleport(spawnPoints.get(x).add(0, 1, 0));
		}
		
		beginZombieSpawning();
	}
	
	private void beginZombieSpawning() {
		final Random r = new Random();
		zombies = r.nextInt((10 - 6) + 1) + 6;
		final int timer = 20 * 60 + (20 * zombies);
		for(int zombie = 0; zombie < zombies; zombie++) {
			int xMin = Math.min((int) start.getX(), (int) end.getX());
			int xMax = Math.max((int) start.getX(), (int) end.getX());
			int zMin = Math.min((int) start.getZ(), (int) end.getZ());
			int zMax = Math.max((int) start.getZ(), (int) end.getZ());
			int x = r.nextInt(xMax - xMin) + xMin;
			int z = r.nextInt(zMax - zMin) + zMin;
			int y = start.getWorld().getHighestBlockYAt(x, z);
			spawnZombie(new Location(start.getWorld(), x, y + 1, z));
		}
		Bukkit.getServer().getScheduler().runTaskLater(ZombieMinigame.getInstance(), new Runnable() {
			public void run() {
				for(int zombie = 0; zombie < zombies; zombie++) {
					int xMin = Math.min((int) start.getX(), (int) end.getX());
					int xMax = Math.max((int) start.getX(), (int) end.getX());
					int zMin = Math.min((int) start.getZ(), (int) end.getZ());
					int zMax = Math.max((int) start.getZ(), (int) end.getZ());
					int x = r.nextInt(xMax - xMin) + xMin;
					int z = r.nextInt(zMax - zMin) + zMin;
					int y = start.getWorld().getHighestBlockYAt(x, z);
					spawnZombie(new Location(start.getWorld(), x, y + 1, z));
				}
				double twoPerc = zombies * 0.2;
				zombies = (int) Math.ceil(zombies + twoPerc);
				Bukkit.getServer().getScheduler().runTaskLater(ZombieMinigame.getInstance(), this, timer);
			}
		}, timer);
	}
	
	public void spawnZombie(Location loc) {
		final Zombie z = (Zombie)loc.getWorld().spawnEntity(loc.subtract(0, 2, 0), EntityType.ZOMBIE);
		z.setGravity(false);
		final BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		final int i = scheduler.scheduleSyncRepeatingTask(ZombieMinigame.getInstance(), new Runnable() {
			int j = 0;
			public void run() {
				if(j < 22) {
					j++;
					Block b = z.getLocation().getBlock();
					Block tmp = Bukkit.getWorld(b.getWorld().getName()).getHighestBlockAt(b.getLocation());
					tmp.getWorld().playEffect(tmp.getLocation(), Effect.STEP_SOUND, tmp.getType());
					z.teleport(z.getLocation().add(0, 0.1, 0));
				} else {
					z.setGravity(true);
				}
			}
		}, 0L, 1L);
		scheduler.scheduleSyncDelayedTask(ZombieMinigame.getInstance(), new Runnable() {
			public void run() {
				scheduler.cancelTask(i);
			}
		}, 500L);
	}
	
	public boolean hasKit(Player p, String s) {
		if(!playerKit.containsKey(p.getUniqueId())) return false;
		if(playerKit.get(p.getUniqueId()).getName().equalsIgnoreCase(s)) return true;
		return false;
	}
	
	public String getName() {
		return name;
	}
	
	public Location getStart() {
		return start;
	}
	
	public Location getEnd() {
		return end;
	}
	
	public Location getLobby() {
		return lobby;
	}
	
	public List<UUID> getPlayers() {
		return players;
	}
	
	public List<Location> getLocations() {
		return spawnPoints;
	}
	
	public boolean isStarted() {
		return Started;
	}
	
	public void addSpawnPoint(Location l) {
		spawnPoints.add(l);
	}
	
	public void removeSpawnPoint(Location loc) {
		spawnPoints.remove(loc);
	}
	
	public void setStart(Location loc) {
		start = loc;
	}
	
	public void setEnd(Location loc) {
		end = loc;
	}
	
	public void setLobby(Location loc) {
		lobby = loc;
	}
	
}