package com.terturl.MeleeCraftEssentials.Utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class StringUtil {

	protected TextComponent main;
	
	public StringUtil() {}
	
	public StringUtil(String s) {
		main = new TextComponent(s);
	}
	
	@Deprecated
	public StringUtil setMain(TextComponent tc) {
		main = tc;
		return this;
	}
	
	public StringUtil setColor(ChatColor c) {
		main.setColor(c);
		return this;
	}
	
	public StringUtil setBold(Boolean b) {
		main.setBold(b);
		return this;
	}
	
	public StringUtil setItalic(Boolean b) {
		main.setItalic(true);
		return this;
	}
	
	public StringUtil addHover(HoverEvent e) {
		main.setHoverEvent(e);
		return this;
	}
	
	public StringUtil addClick(ClickEvent e) {
		main.setClickEvent(e);
		return this;
	}
	
	public TextComponent getTextComp() {
		return main;
	}

}