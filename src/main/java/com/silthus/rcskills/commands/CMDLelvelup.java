package com.silthus.rcskills.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.avaje.ebean.PagingList;
import com.silthus.rcskills.RCLogger;
import com.silthus.rcskills.RCPermissions;
import com.silthus.rcskills.RCPlayer;
import com.silthus.rcskills.RCSkills;
import com.silthus.rcskills.config.RCConfig;
import com.silthus.rcskills.database.DBLevelup;
import com.silthus.rcskills.extras.Messaging;

/**
 * @description Handles a command.
 * @author Tagette
 */
public class CMDLelvelup implements CommandExecutor {

	private static RCSkills plugin;
	private Player player = null;

	public CMDLelvelup(RCSkills instance) {
		CMDLelvelup.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		boolean handled = false;
		if (is(label, "lvlup")) {
			if (args == null || args.length == 0) {
				handled = true;
				this.player = getPlayerOfSender(sender);
				if (player == sender
						&& RCPermissions.permission(player, "lvlup.use")) {
					RCPlayer p = new RCPlayer(player);
					p.checkForItems();
					if (p.lvlup(false)) {
						Messaging.sendMessage(sender,
								"Du bist nun Level " + p.getLevel());
						Messaging.sendMessage(
								sender,
								"Dir wurden " + ChatColor.YELLOW
										+ p.getExpToLevel(p.getLevel())
										+ " EXP abgezogen.");
						Messaging.sendMessage(p.getServer(),
								player.getName() + " ist nun Level "
										+ ChatColor.YELLOW + p.getLevel());
						RCLogger.info(player.getName() + " ist nun Level "
								+ p.getLevel());

						// Save Changes
						p.writeDatabase();
						return handled;
					} else {
						return handled;
					}
				} else {
					Messaging.sendMessage(sender,
							"Du hast nicht die nötigen Rechte dafür!");
					return handled;
				}
			} else {
				// Put your commands in here
				if (is(args[0], "player") && args.length == 2) {
					handled = true;
					if (RCPermissions.isAdmin(getPlayerOfSender(sender))
							|| RCPermissions.permission(
									getPlayerOfSender(sender),
									"lvlup.admin.use")) {
						this.player = getPlayer(sender, args, 1);
						RCPlayer p = new RCPlayer(player);
						if (p.lvlup(true)) {
							Messaging.sendMessage(sender, "Du bist nun Level "
									+ p.getLevel());
							Messaging.sendMessage(p.getServer(),
									player.getName() + " ist nun Level "
											+ ChatColor.YELLOW + p.getLevel());
							RCLogger.info(player.getName() + " ist nun Level "
									+ p.getLevel());
							// Save Changes
							p.writeDatabase();
						}
					} else {
						Messaging.sendMessage(sender,
								"Du hast nicht die nötigen Rechte dafür!");
					}
				} else if (is(args[0], "top")) {
					handled = true;
					if (isPlayer(sender)
							&& RCPermissions.permission(
									getPlayerOfSender(sender), "lvlup.cmd.top")) {
						this.player = getPlayerOfSender(sender);
						if (args.length == 1) {
							getTopList(5);
						} else if (args.length == 2) {
							getTopList(Integer.parseInt(args[1]));
						}
					} else {
						Messaging.sendMessage(sender,
								"Du hast nicht die nötigen Rechte dafür!");
					}
				} else if (is(args[0], "xp") || is(args[0], "exp")) {
					handled = true;
					if (isPlayer(sender)) {
						if (args.length == 1
								&& RCPermissions.permission(
										getPlayerOfSender(sender),
										"lvlup.cmd.exp")) {
							this.player = getPlayerOfSender(sender);
							RCPlayer p = new RCPlayer(player);
							Messaging.sendMessage(
									sender,
									"Du hast "
											+ Messaging.colorizeText(
													"" + p.getExp(),
													ChatColor.YELLOW)
											+ " von "
											+ Messaging.colorizeText(
													"" + p.getExpToNextLevel(),
													ChatColor.YELLOW)
											+ " EXP für das nächste Level");
						} else if (args.length == 2
								&& RCPermissions.permission(
										getPlayerOfSender(sender),
										"lvlup.admin.exp")) {
							this.player = getPlayer(sender, args, 1);
							RCPlayer p = new RCPlayer(player);
							Messaging.sendMessage(
									sender,
									Messaging.colorizeText(
											"" + p.getPlayerName(),
											ChatColor.YELLOW)
											+ " hat "
											+ Messaging.colorizeText(
													"" + p.getExp(),
													ChatColor.YELLOW)
											+ " von "
											+ Messaging.colorizeText(
													"" + p.getExpToNextLevel(),
													ChatColor.YELLOW)
											+ " für das nächste Level");
						}
					} else {
						Messaging.sendMessage(sender,
								"You do not have permission.");
					}

				} else if (is(args[0], "debug")) {
					handled = true;
					if (isPlayer(sender)
							&& RCPermissions.isAdmin(getPlayerOfSender(sender))) {
						RCSkills.debugging = !RCSkills.debugging;
						if (RCSkills.debugging)
							Messaging.sendMessage(sender,
									"Plugin has begun debugging.");
						else
							Messaging.sendMessage(sender,
									"Plugin has stopped debugging.");
					}
				} else if (is(args[0], "reload")) {
					handled = true;
					if (isPlayer(sender)
							&& RCPermissions.isAdmin(getPlayerOfSender(sender))) {
						RCConfig.load();
						Messaging.sendMessage(
								sender,
								Messaging.colorizeText(RCSkills.name,
										ChatColor.GREEN)
										+ " has been reloaded.");
					}
				} else if (is(args[0], "help") || is(args[0], "?")) {
					handled = false;
				} else {
					handled = true;
					Messaging.sendMessage(sender, "Unknown " + RCSkills.name
							+ " command: /" + label + " " + join(args, " "));
				}
			}
		}
		return handled;
	}

	// Simplifies and shortens the if statements for commands.
	private boolean is(String entered, String label) {
		return entered.equalsIgnoreCase(label);
	}

	// Checks if the current user is actually a player and returns the name of
	// that player.
	@SuppressWarnings("unused")
	private String getName(CommandSender sender) {
		String name = "";
		if (isPlayer(sender)) {
			Player player = (Player) sender;
			name = player.getName();
		}
		return name;
	}

	// Checks if the current user is actually a player.
	private boolean isPlayer(CommandSender sender) {
		return sender instanceof Player;
	}

	private Player getPlayerOfSender(CommandSender sender) {
		if (isPlayer(sender))
			return (Player) sender;
		Messaging.sendMessage(sender, "Sorry but you can't level up.");
		return null;
	}

	// Gets the player if the current user is actually a player.
	private Player getPlayer(CommandSender sender, String[] args, int index) {
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

	private String join(String[] split, String delimiter) {
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
	private void getTopList(int top) {

		PagingList<DBLevelup> pages = plugin.getDatabase()
				.find(DBLevelup.class).orderBy("level desc")
				.findPagingList(top);

		if (pages.getTotalPageCount() > 0) {
			List<DBLevelup> players = pages.getPage(0).getList(); // top 5
			for (int i = 0; i < players.size(); i++) {
				DBLevelup p = players.get(i);
				Messaging.sendMessage(
						player,
						(i + 1) + ". " + p.getPlayerName() + " - "
								+ p.getLevel());
			}
		}
	}
}
