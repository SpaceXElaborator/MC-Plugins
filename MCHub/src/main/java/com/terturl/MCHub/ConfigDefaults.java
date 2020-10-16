package com.terturl.MCHub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigDefaults {
  public ConfigDefaults() {
    Map<Object, Object> mainConf = new HashMap<Object, Object>();
    mainConf.put("JumpPad", "STONE");
    mainConf.put("ServerSelectorName", "&6&lSelect Your Server");
    mainConf.put("ServerSelectorIcon", "COMPASS");
    mainConf.put("ServerSelectorSlot", 0);
    mainConf.put("SelectorInvName", "&6Server Selector");
    mainConf.put("SelectorInvSize", Integer.valueOf(9));
    List<String> lore = new ArrayList<String>();
    lore.add("Servers: ");
    lore.add(">> Obsidian");
    mainConf.put("ServerSelectorLore", lore);
    MCHub.getInstance().getConfiguration().createConfigFromMap("config.yml", mainConf);
   
    Map<Object, Object> ss = new HashMap<Object, Object>();
    List<String> lore2 = new ArrayList<String>();
    lore2.add("The main land!");
    ss.put("Hub.Name", "&4The Hub");
    ss.put("Hub.Lore", lore2);
    ss.put("Hub.Icon", "NETHER_STAR");
    ss.put("Hub.Slot", Integer.valueOf(0));
    List<String> commands = new ArrayList<String>();
    commands.add("server hub");
    ss.put("Hub.Commands", commands);
    MCHub.getInstance().getConfiguration().createConfigFromMap("serverselector.yml", ss);
   
    Map<Object, Object> messages = new HashMap<Object, Object>();
    messages.put("ChatLocked", "The chat has been locked");
    messages.put("YouLocked", "You have locked the chat");
    messages.put("ChatUnlocked", "That chat has been unlocked");
    messages.put("YouUnlocked", "You have unlocked the chat");
    messages.put("ChatCleared", "Chat has been cleared!");
    MCHub.getInstance().getConfiguration().createConfigFromMap("messages.yml", messages);
  }
}
