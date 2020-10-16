package com.terturl.ZombieMinigame.Arena;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.terturl.ZombieMinigame.ZombieMinigame;

public class ArenaHandler {

	private List<Arena> arenas;
	private File zombieFolder;
	private static FileWriter fw;
	
	private Map<UUID, Arena> editingArenas;
	
	public ArenaHandler() {
		arenas = new ArrayList<Arena>();
		editingArenas = new HashMap<UUID, Arena>();
		
		zombieFolder = ZombieMinigame.getInstance().getDataFolder();
		if(!zombieFolder.exists()) zombieFolder.mkdir();
	}
	
	// ################### GETTERS ###################
	public List<Arena> getArenas() {
		return arenas;
	}
	
	public Map<UUID, Arena> getEditing() {
		return editingArenas;
	}
	
	// ################### FUNCTIONS ###################
	public Arena addArena(String s) {
		Arena a = new Arena(s);
		arenas.add(a);
		return a;
	}
	
	public boolean playerInArena(Player p) {
		for(Arena a : arenas) {
			if(a.getPlayers().contains(p.getUniqueId())) return true;
		}
		return false;
	}
	
	public Arena getArenaFromPlayer(Player p) {
		for(Arena a : arenas) {
			if(a.getPlayers().contains(p.getUniqueId())) return a;
		}
		return null;
	}
	
	public boolean editArena(Player p, String s) {
		Arena a = getArena(s);
		if(a == null) return false;
		editingArenas.put(p.getUniqueId(), a);
		return true;
	}
	
	public void stopEditing(Player p) {
		editingArenas.remove(p.getUniqueId());
		ZombieMinigame.getInstance().getPlayerInventoryManager().loadInventory(p);
	}
	
	public boolean isEditing(Player p) {
		if(editingArenas.containsKey(p.getUniqueId())) {
			return true;
		}
		return false;
	}
	
	public boolean joinArena(Player p, String s) {
		Arena a = getArena(s);
		if(a == null) return false;
		a.addPlayer(p);
		return true;
	}
	
	public boolean startArena(String s) {
		Arena a = getArena(s);
		if(a == null) return false;
		a.start();
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public void save() {
		File f = new File(zombieFolder, "Arenas.json");
		f.delete();
		try {
			f.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		JSONArray arenasJSON = new JSONArray();
		for(Arena a : arenas) {
			JSONObject arena = new JSONObject();
			arena.put("Name", a.getName());
			arena.put("StartXZ", serializeLocation(a.getStart()));
			arena.put("EndXZ", serializeLocation(a.getEnd()));
			arena.put("Lobby", serializeLocation(a.getLobby()));
			JSONArray locations = new JSONArray();
			for(Location loc : a.getLocations()) {
				locations.add(serializeLocation(loc));
			}
			arena.put("Spawns", locations);
			arenasJSON.add(arena);
		}
		obj.put("Arenas", arenasJSON);
		
		try {
			fw = new FileWriter(zombieFolder + File.separator + "Arenas.json");
			fw.write(toPrettyFormat(obj));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.flush();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void load() {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(zombieFolder + File.separator + "Arenas.json"));
			JSONObject mainObj = (JSONObject)obj;
			JSONArray arenaList = (JSONArray)mainObj.get("Arenas");
			Iterator<JSONObject> iterator = arenaList.iterator();
			while(iterator.hasNext()) {
				JSONObject innerObj = iterator.next();
				String name = (String)innerObj.get("Name");
				Arena a = addArena(name);
				a.setStart(deserializeLocation((String)innerObj.get("StartXZ")));
				a.setEnd(deserializeLocation((String)innerObj.get("EndXZ")));
				a.setLobby(deserializeLocation((String)innerObj.get("Lobby")));
				List<String> spawns = (List<String>)innerObj.get("Spawns");
				for(String s : spawns) {
					a.addSpawnPoint(deserializeLocation(s));
				}
				ZombieMinigame.getInstance().getLogger().log(Level.INFO, "Loaded Arena: " + a.getName());
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	// ################### PRIVATE FUNCTIONS ###################
	private String toPrettyFormat(JSONObject j) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(j);
		return json;
	}
	
	private String serializeLocation(Location loc) {
		String s = loc.getWorld().getName() + ":" + String.valueOf((int)loc.getX()) + ":" + String.valueOf((int)loc.getY()) + ":" + String.valueOf((int)loc.getZ());
		return s;
	}
	
	private Location deserializeLocation(String s) {
		String[] tmp = s.split(":");
		Location loc = new Location(Bukkit.getWorld(tmp[0]), Integer.valueOf(tmp[1]), Integer.valueOf(tmp[2]), Integer.valueOf(tmp[3]));
		return loc;
	}
	
	private Arena getArena(String s) {
		for(Arena a : arenas) {
			if(a.getName().equalsIgnoreCase(s)) return a;
		}
		return null;
	}
	
}