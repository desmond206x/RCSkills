package com.silthus.rcskills.listeners;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

import com.silthus.rcskills.RCSkills;

public class RCPlayerListener extends PlayerListener {
	@SuppressWarnings("unused")
	private final RCSkills plugin;

	public RCPlayerListener(RCSkills instance) {
		plugin = instance;
	}
	
	public void onPlayerJoin(PlayerJoinEvent event) {
		
	}

}
