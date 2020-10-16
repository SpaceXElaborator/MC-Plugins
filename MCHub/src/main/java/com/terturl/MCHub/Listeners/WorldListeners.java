package com.terturl.MCHub.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WorldListeners implements Listener {

	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		if(!e.toWeatherState()) return;
		e.setCancelled(true);
		e.getWorld().setWeatherDuration(0);
		e.getWorld().setThundering(false);
	}
	
	@EventHandler
	public void onSpawn(EntitySpawnEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void FIRE(BlockIgniteEvent e) {
		if(e.getCause().equals(IgniteCause.SPREAD)) e.setCancelled(true);
		if(e.getCause().equals(IgniteCause.FLINT_AND_STEEL)) e.setCancelled(true);
		if(e.getCause().equals(IgniteCause.LAVA)) e.setCancelled(true);
	}
	
	@EventHandler
	public void leafDecay(LeavesDecayEvent e) {
		e.setCancelled(true);
	}
	
}