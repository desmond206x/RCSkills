package com.silthus.rcskills.listeners;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import com.silthus.rcskills.RCEconomy;
import com.silthus.rcskills.RCPermissions;
import com.silthus.rcskills.RCSkills;

/**
 * @description Handles enabling plugins
 * @author Tagette
 */
public class RCPluginListener extends ServerListener {

    private final RCSkills plugin;

    public RCPluginListener(RCSkills instance) {
        plugin = instance;
        RCEconomy.initialize();
    }

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
    	// Try to load again!
    	RCPermissions.onEnable(event.getPlugin());
    	RCEconomy.onEnable(event.getPlugin());
    }

    @Override
    public void onPluginDisable(PluginDisableEvent event) {
    }
}
