package com.silthus.rcskills.extras;

import java.util.Hashtable;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.silthus.rcskills.RCSkills;

/**
 * 
 * @author brenda
 */
public class CommandManager {

	@SuppressWarnings("unused")
	private RCSkills plugin;
	private Map<String, CommandExecutor> commands = new Hashtable<String, CommandExecutor>();

	public CommandManager(RCSkills instance) {
		this.plugin = instance;
	}

	public void addCommand(String label, CommandExecutor executor) {
		commands.put(label, executor);
	}

	public boolean dispatch(CommandSender sender, Command command,
			String label, String[] args) {
		if (!commands.containsKey(label)) {
			return false;
		}

		boolean handled = true;

		CommandExecutor ce = commands.get(label);
		handled = ce.onCommand(sender, command, label, args);

		return handled;
	}
}