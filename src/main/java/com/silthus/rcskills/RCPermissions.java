package com.silthus.rcskills;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijiko.permissions.Group;
import com.nijiko.permissions.PermissionHandler;
import com.nijiko.permissions.User;
import com.nijikokun.bukkit.Permissions.Permissions;

/**
 * @description Handles all plugin permissions
 * @author Tagette
 */
public class RCPermissions {

	public static Plugin permissionPlugin;
	private static PermissionHandler permissionsHandler;
	private static RCSkills plugin;

	public static void initialize(RCSkills instance) {
		RCPermissions.plugin = instance;
		Plugin Permissions = plugin.getServer().getPluginManager()
				.getPlugin("Permissions");

		if (Permissions != null) {
			permissionPlugin = Permissions;
			String version = permissionPlugin.getDescription().getVersion();
			RCLogger.info("Permissions version " + version + " loaded.");
			RCPermissions.permissionsHandler = ((Permissions) RCPermissions.permissionPlugin).getHandler();
		}
	}

	public static void onEnable(Plugin plugin) {
		if (permissionPlugin == null) {
			String pluginName = plugin.getDescription().getName();

			if (pluginName.equals("Permissions")) {
				permissionPlugin = plugin;
				String version = plugin.getDescription().getVersion();
				RCLogger.info("Permissions version " + version + " loaded.");
			}
		}
	}

	public static boolean permission(Player player, String permission) {
		return RCPermissions.permissionsHandler.has(player, permission);
	}

	public static boolean isAdmin(Player player) {
		return permission(player, "basic.admin");
	}

	public static String getPrimaryGroup(Player player) {
		return permissionsHandler.getPrimaryGroup(player.getWorld().getName(), player.getName());
	}

	public static boolean addParent(Player player, String group) {
		User userToBe = permissionsHandler.getUserObject(player.getWorld().getName(),
				player.getName());
		if (userToBe == null)
			return false;
		Group groupToBe = permissionsHandler.getGroupObject(player.getWorld().getName(), group);
		if (groupToBe == null)
			return false;
		userToBe.addParent(groupToBe);
		return true;
	}

	public static boolean removeParent(Player player, String group) {
		User userToBe = permissionsHandler.getUserObject(player.getWorld().getName(),player.getName());
		if (userToBe == null)
			return false;
		Group groupToBe = permissionsHandler.getGroupObject(player.getWorld().getName(), group);
		if (groupToBe == null)
			return false;
		userToBe.removeParent(groupToBe);
		return true;
	}
	
	public static boolean promote(Player player, String track, String group) {
		User userToBe = permissionsHandler.getUserObject(player.getWorld().getName(),player.getName());
		if (userToBe == null)
			return false;
		Group groupToBe = permissionsHandler.getGroupObject(player.getWorld().getName(), group);
		if (groupToBe == null)
			return false;
		userToBe.promote(groupToBe.toGroupWorld(), track);
		return true;
	}
	
	public static boolean demote(Player player, String track, String group) {
		User userToBe = permissionsHandler.getUserObject(player.getWorld().getName(),player.getName());
		if (userToBe == null)
			return false;
		Group groupToBe = permissionsHandler.getGroupObject(player.getWorld().getName(), group);
		if (groupToBe == null)
			return false;
		userToBe.demote(groupToBe.toGroupWorld(), track);
		return true;
	}
	
	public static void saveAll() {
		permissionsHandler.saveAll();
	}
}
