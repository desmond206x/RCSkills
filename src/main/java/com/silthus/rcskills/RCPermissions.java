package com.silthus.rcskills;

import java.util.LinkedHashSet;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijiko.permissions.Entry;
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
	
	/**
	 * loads the Permissions plugin
	 * @param instance
	 */
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
	
	/**
	 * checks if player has permission
	 * @param player
	 * @param permission
	 * @return hasPermission
	 */
	public static boolean permission(Player player, String permission) {
		return RCPermissions.permissionsHandler.has(player, permission);
	}
	
	/**
	 * checks if the player is admin
	 * @param player
	 * @return isAdmin
	 */
	public static boolean isAdmin(Player player) {
		if (permission(player, "rcs.admin") || player.isOp())
			return true;
		return false;
	}
	
	/**
	 * Gets the main group of the player
	 * @param player
	 * @return
	 */
	public static String[] getGroups(Player player) {
		return permissionsHandler.getGroups(player.getWorld().getName(), player.getName());
	}
	
	/**
	 * Adds a group to the player
	 * @param player
	 * @param group
	 * @return addedGroup?
	 */
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
	
	/**
	 * removes a group from the player
	 * @param player
	 * @param group
	 * @return removedGroup?
	 */
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
	
	/**
	 * promotes a player along the set track
	 * @param player
	 * @param track
	 * @param group
	 * @return
	 */
	@Deprecated
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
	
	/**
	 * demotes the player along the set track
	 * @param player
	 * @param track
	 * @param group
	 * @return
	 */
	@Deprecated
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
	
	/**
	 * removes all Permissions
	 * @param player
	 */
	public static void removeAllPermissions(Player player) {
		User user = permissionsHandler.getUserObject(player.getWorld().getName(), player.getName());
		Set<String> perms = user.getPermissions();
		for (String s : perms) {
			user.removePermission(s);
		}
	}
	
	/**
	 * Gets all groups of the player
	 * @param player
	 * @return parents
	 */
	public static String[] getParents(Player player) {
		User user = permissionsHandler.getUserObject(player.getWorld().getName(), player.getName());
		LinkedHashSet<Entry> parents = user.getParents();
		String[] array = new String[parents.size()];
		int i = 0;
		for (Entry s : parents) {
			array[i] = s.getName();
			i++;
		}
		return array;
	}
	
	/**
	 * Saves all Permissions into the files
	 */
	public static void saveAll() {
		permissionsHandler.saveAll();
	}
}
