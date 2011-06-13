package com.silthus.rcskills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.PersistenceException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.silthus.rcskills.commands.CMDrcs;
import com.silthus.rcskills.config.Language;
import com.silthus.rcskills.config.RCConfig;
import com.silthus.rcskills.config.SkillsConfig;
import com.silthus.rcskills.database.DBLevelup;
import com.silthus.rcskills.database.DBSkills;
import com.silthus.rcskills.extras.CommandManager;
import com.silthus.rcskills.listeners.RCPlayerListener;
import com.silthus.rcskills.listeners.RCPluginListener;

public class RCSkills extends JavaPlugin {

	private final CommandManager commandManager = new CommandManager(this);
	private final RCPluginListener pluginListener = new RCPluginListener(this);
	private final RCPlayerListener playerListener = new RCPlayerListener(this);
	private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();

	public static String name;
	public static String version;
	public static boolean debugging;
	
    public static final String tag = "RCSkills";

	public void onEnable() {

		name = this.getDescription().getName();
		version = this.getDescription().getVersion();
		PluginManager pm = getServer().getPluginManager();

		// Logger
		RCLogger.initialize(Logger.getLogger("Minecraft"));
		// Settings
		RCConfig.initialize(this);
		SkillsConfig.initialize(this);
		Language.initialize(this);
		// Database
		setupDatabase();
		RCPlayer.initialize(this);
		// Supported plugins
		RCEconomy.initialize();
		RCPermissions.initialize(this);
		// Commands
		setupCommands();
		// Makes sure all plugins are correctly loaded.
		pm.registerEvent(Event.Type.PLUGIN_ENABLE, pluginListener,Priority.Monitor, this);
		// Register our events
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener,Priority.Low, this);

		RCLogger.info(name + " version " + version + " is enabled!");

	}

	/*
	 * Sets up the core commands of the plugin.
	 */
	private void setupCommands() {
		// Add command labels here.
		addCommand("rcs", new CMDrcs(this));
	}

	/*
	 * Executes a command when a command event is received.
	 * 
	 * @param sender The thing that sent the command.
	 * 
	 * @param cmd The complete command object.
	 * 
	 * @param label The label of the command.
	 * 
	 * @param args The arguments of the command.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		return commandManager.dispatch(sender, cmd, label, args);
	}

	/*
	 * Adds the specified command to the command manager and server.
	 * 
	 * @param command The label of the command.
	 * 
	 * @param executor The command class that excecutes the command.
	 */
	private void addCommand(String command, CommandExecutor executor) {
		getCommand(command).setExecutor(executor);
		commandManager.addCommand(command, executor);
	}
	
	public CommandManager getCommandManager() {
		return this.commandManager;
	}

	public void onDisable() {

		RCLogger.info(name + " disabled.");
	}

	/*
	 * Checks if a player is in debug mode.
	 * 
	 * @param player The player to check.
	 */
	public boolean isDebugging(final Player player) {
		if (debugees.containsKey(player)) {
			return debugees.get(player);
		} else {
			return false;
		}
	}

	/*
	 * Sets a players debug mode.
	 * 
	 * @param player The player to set the debug mode of.
	 * 
	 * @param value The boolean value to set the players debug mode to.
	 */
	public void setDebugging(final Player player, final boolean value) {
		debugees.put(player, value);
	}
	
	private void setupDatabase() {
        try {
            getDatabase().find(DBLevelup.class).findRowCount();
            getDatabase().find(DBSkills.class).findRowCount();
        } catch (PersistenceException ex) {
            RCLogger.info("Installing database for " + getDescription().getName() + " due to first time usage");
            installDDL();
        }
    }
    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(DBLevelup.class);
        list.add(DBSkills.class);
        return list;
    }

}
