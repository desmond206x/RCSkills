package com.silthus.rcskills.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.silthus.rcskills.RCLogger;
import com.silthus.rcskills.RCPermissions;
import com.silthus.rcskills.RCPlayer;
import com.silthus.rcskills.RCSkills;
import com.silthus.rcskills.config.RCConfig;
import com.silthus.rcskills.extras.CommandManager;
import com.silthus.rcskills.extras.Messaging;

/**
 * @description Handles a command.
 * @author Tagette
 */
public class CMDLelvelup implements CommandExecutor {

	private static RCSkills plugin;
	private Player player = null;
	private CommandManager cmd = null;

	public CMDLelvelup(RCSkills instance) {
		CMDLelvelup.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		boolean handled = false;
		cmd = plugin.getCommandManager();
		if (cmd.is(label, "lvlup")) {
			if (args == null || args.length == 0) {
				handled = true;
				this.player = cmd.getPlayerOfSender(sender);
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
					}
				} else {
					Messaging.sendMessage(sender,
							"Du hast nicht die nötigen Rechte dafür!");
				}
			} else {
				// Put your commands in here
				if (cmd.is(args[0], "player") && args.length == 2) {
					handled = true;
					if (RCPermissions.isAdmin(cmd.getPlayerOfSender(sender))
							|| RCPermissions.permission(
									cmd.getPlayerOfSender(sender),
									"lvlup.admin.use")) {
						this.player = cmd.getPlayer(sender, args, 1);
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
				} else if (cmd.is(args[0], "top")) {
					handled = true;
					if (cmd.isPlayer(sender)
							&& RCPermissions.permission(
									cmd.getPlayerOfSender(sender), "lvlup.cmd.top")) {
						this.player = cmd.getPlayerOfSender(sender);
						if (args.length == 1) {
							cmd.getTopList(5, sender);
						} else if (args.length == 2) {
							cmd.getTopList(Integer.parseInt(args[1]), sender);
						}
					} else {
						Messaging.sendMessage(sender,
								"Du hast nicht die nötigen Rechte dafür!");
					}
				} else if (cmd.is(args[0], "xp") || cmd.is(args[0], "exp")) {
					handled = true;
					if (cmd.isPlayer(sender)) {
						if (args.length == 1
								&& RCPermissions.permission(
										cmd.getPlayerOfSender(sender),
										"lvlup.cmd.exp")) {
							this.player = cmd.getPlayerOfSender(sender);
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
										cmd.getPlayerOfSender(sender),
										"lvlup.admin.exp")) {
							this.player = cmd.getPlayer(sender, args, 1);
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

				} else if (cmd.is(args[0], "debug")) {
					handled = true;
					if (cmd.isPlayer(sender)
							&& RCPermissions.isAdmin(cmd.getPlayerOfSender(sender))) {
						RCSkills.debugging = !RCSkills.debugging;
						if (RCSkills.debugging)
							Messaging.sendMessage(sender,
									"Plugin has begun debugging.");
						else
							Messaging.sendMessage(sender,
									"Plugin has stopped debugging.");
					}
				} else if (cmd.is(args[0], "reload")) {
					handled = true;
					if (cmd.isPlayer(sender)
							&& RCPermissions.isAdmin(cmd.getPlayerOfSender(sender))) {
						RCConfig.load();
						Messaging.sendMessage(
								sender,
								Messaging.colorizeText(RCSkills.name,
										ChatColor.GREEN)
										+ " has been reloaded.");
					}
				} else if (cmd.is(args[0], "help") || cmd.is(args[0], "?")) {
					handled = false;
				} else {
					handled = true;
					Messaging.sendMessage(sender, "Unknown " + RCSkills.name
							+ " command: /" + label + " " + cmd.join(args, " "));
				}
			}
		}
		return handled;
	}
}
