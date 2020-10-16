package com.terturl.BookNews;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayJoin implements Listener {

	protected List<UUID> canOpen = new ArrayList<UUID>();
	
	@EventHandler
	public void onJoin(final PlayerJoinEvent e) {
		BookNews.getInstance().getBookManager();
		//if(canOpen.contains(e.getPlayer().getUniqueId())) return;
		Collections.shuffle(CreateBooks.books);
		ItemStack book = CreateBooks.books.get(0);
		e.getPlayer().openBook(book);
		//canOpen.add(e.getPlayer().getUniqueId());
		
//		Bukkit.getScheduler().runTaskLater(BookNews.getInstance(), new Runnable() {
//			@Override
//			public void run() {
//				canOpen.remove(e.getPlayer().getUniqueId());
//			}
//		}, 30*60*20L);
	}
	
}