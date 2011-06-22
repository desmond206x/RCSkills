package com.silthus.rcskills;

import org.bukkit.plugin.Plugin;

/**
 * @description Handles the economics of the plugin
 * @author Tagette
 */
public class RCEconomy {
	
	public static Methods Methods;
	public static Method Economy;
	private static boolean enabled = false;

	/*
	 * Initializes the economy methods
	 */
	public static void initialize() {
		Methods = new Methods("iConomy");
	}

	/*
	 * Loads the economy method.
	 * 
	 * @param plugin The plugin that was enabled.
	 */
	public static void onEnable(Plugin plugin) {
		// Initialize according to what economy plugin is being enabled
		if (!RCEconomy.Methods.hasMethod()) {
			if (RCEconomy.Methods.setMethod(plugin)) {
				RCEconomy.Economy = RCEconomy.Methods.getMethod();
				setEnabled();
				RCLogger.info(RCEconomy.Economy.getName() + " version "
						+ RCEconomy.Economy.getVersion() + " loaded.");
			}
		}
	}
	
	public static boolean isEnabled() {
		return enabled;
	}
	
	public static void setEnabled() {
		enabled = true;
	}
}
