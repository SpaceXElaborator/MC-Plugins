package com.terturl.MeleeCraftEssentials.Minigames;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;

import com.terturl.MeleeCraftEssentials.Minigames.Exceptions.FullWorldException;
import com.terturl.MeleeCraftEssentials.Minigames.Exceptions.NotDirectoryException;

public abstract class MinigameManager {
	
	private String gameName;
	private MinigameType MGType;
	private Minigame game = Minigame.ENABLED;
	
	private File src;
	private World w;
	
	public MinigameManager(String s, MinigameType type) {
		gameName = s;
		MGType = type;
	}
	
	public MinigameType getType() {
		return MGType;
	}
	
	public void setMinigameMap(File folder) throws FullWorldException, NotDirectoryException {
		if(!MGType.equals(MinigameType.FULL_WORLD)) {
			game = Minigame.DISABLED;
			throw new FullWorldException();
		}
		if(!folder.isDirectory()) {
			game = Minigame.DISABLED;
			throw new NotDirectoryException();
		}
		src = folder;
	}
	
	public void loadMinigame() {
		if(MGType.equals(MinigameType.FULL_WORLD)) {
			createWorld();
			onLoad();
		}
	}
	
	public abstract void onLoad();
	
	private void createWorld() {
		File dir = new File(gameName);
		if(!dir.exists()) {
			Thread t = new Thread(new Runnable() {
				public void run() {
					try {
						FileUtils.copyDirectory(src, new File(gameName));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			t.start();
			try {
				t.join();
			} catch (Exception e) {}
			w = Bukkit.createWorld(new WorldCreator(gameName));
		}
	}
	
	public Minigame getLoaded() {
		return game;
	}
	
	public World getWorld() {
		if(MGType == MinigameType.FULL_WORLD) {
			return w;
		}
		return null;
	}
	
	public enum MinigameType {
		FULL_WORLD;
	}
	
	public enum Minigame {
		ENABLED, DISABLED;
	}
	
}