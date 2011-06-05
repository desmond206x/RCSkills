package com.silthus.rcskills.extras;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.avaje.ebean.PagingList;
import com.silthus.rcskills.RCSkills;
import com.silthus.rcskills.database.DBLevelup;

/**
 * 
 * @author brenda
 */
public class CommandManager {

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
	
	// Simplifies and shortens the if statements for commands.
	public boolean is(String entered, String label) {
		return entered.equalsIgnoreCase(label);
	}

	// Checks if the current user is actually a player and returns the name of
	// that player.
	public String getName(CommandSender sender) {
		String name = "";
		if (isPlayer(sender)) {
			Player player = (Player) sender;
			name = player.getName();
		}
		return name;
	}

	// Checks if the current user is actually a player.
	public boolean isPlayer(CommandSender sender) {
		return sender instanceof Player;
	}

	public Player getPlayerOfSender(CommandSender sender) {
		if (isPlayer(sender))
			return (Player) sender;
		Messaging.sendMessage(sender, "Sorry but I don't know who you are.");
		return null;
	}

	// Gets the player if the current user is actually a player.
	public Player getPlayer(CommandSender sender, String[] args, int index) {
		if (args.length > index) {
			List<Player> players = sender.getServer().matchPlayer(args[index]);

			if (players.isEmpty()) {
				sender.sendMessage("I don't know who '" + args[index] + "' is!");
				return null;
			} else {
				return players.get(0);
			}
		} else {
			if (isPlayer(sender)) {
				return null;
			} else {
				return (Player) sender;
			}
		}
	}

	public String join(String[] split, String delimiter) {
		String joined = "";
		for (String s : split) {
			joined += s + delimiter;
		}
		joined = joined.substring(0, joined.length() - (delimiter.length()));
		return joined;
	}

	/**
	 * Gets the top 5 leveled users from the database
	 * 
	 * @param top
	 */
	public void getTopList(int top, CommandSender sender) {

		PagingList<DBLevelup> pages = plugin.getDatabase()
				.find(DBLevelup.class).orderBy("level desc")
				.findPagingList(top);

		if (pages.getTotalPageCount() > 0) {
			List<DBLevelup> players = pages.getPage(0).getList(); // top 5
			for (int i = 0; i < players.size(); i++) {
				DBLevelup p = players.get(i);
				Messaging.sendMessage(
						sender,
						(i + 1) + ". " + p.getPlayerName() + " - "
								+ p.getLevel());
			}
		}
	}
}