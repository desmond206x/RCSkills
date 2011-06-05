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
import com.silthus.rcskills.config.SingleSkill;
import com.silthus.rcskills.config.SkillsConfig;
import com.silthus.rcskills.extras.CommandManager;
import com.silthus.rcskills.extras.ExtraFunctions;
import com.silthus.rcskills.extras.Messaging;

public class CMDrcs implements CommandExecutor {

	private static RCSkills plugin;
	private Player player = null;
	private CommandManager cmd = null;

	public CMDrcs(RCSkills instance) {
		CMDrcs.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		boolean handled = false;
		cmd = plugin.getCommandManager();
		if (cmd.is(label, "rcs")) {
			if (args == null || args.length == 0) {
				handled = true;
				this.player = cmd.getPlayerOfSender(sender);
				if (player == sender
						&& RCPermissions.permission(player, "rcs.player.stats")) {
					RCPlayer p = new RCPlayer(player);
					Messaging.sendNoTag(
							sender,
							"##### "
									+ Messaging.colorizeText("RaidCraft Stats",
											ChatColor.YELLOW) + " #####");
					// send level
					Messaging.sendMessage("Level", sender, "Level "
							+ ChatColor.YELLOW + p.getLevel());
					// send exp
					Messaging.sendMessage(
							"EXP",
							sender,
							Messaging.colorizeText("" + p.getExp(),
									ChatColor.YELLOW)
									+ "/"
									+ Messaging.colorizeText(
											"" + p.getExpToNextLevel(),
											ChatColor.YELLOW) + " Erfahrung");
					// send money
					Messaging.sendMessage(
							"Money",
							sender,
							Messaging.colorizeText(""
									+ p.getAccount().balance(),
									ChatColor.YELLOW)
									+ " Coins");
					// send skillpoints
					Messaging.sendMessage(
							"Skillpoints",
							sender,
							Messaging.colorizeText("" + p.getSkillPoints(),
									ChatColor.YELLOW) + " Skillpoints");
					// Send all bought skills
					String s = "";
					for (int i = 0; i < p.getSkills().size(); i++) {
						if (i == p.getSkills().size() - 1)
							s += p.getSkill(i).getSkillName() + "";
						else
							s += p.getSkill(i).getSkillName() + ", ";
					}
					Messaging.sendMessage("Skills", sender, s);
				} else {
					Messaging.sendMessage(sender,
							"Du hast nicht die nötigen Rechte dafür!");
				}
			} else if (cmd.is(args[0], "player")) {
				if (args.length == 2
						&& RCPermissions.permission(
								cmd.getPlayerOfSender(sender),
								"rcs.admin.info")) {
					RCPlayer p = new RCPlayer(cmd.getPlayer(sender, args, 1));
					Messaging.sendNoTag(
							sender,
							"##### "
									+ Messaging.colorizeText("RaidCraft Stats",
											ChatColor.YELLOW) + " #####");
					// send level
					Messaging.sendMessage("Level", sender, "Level "
							+ ChatColor.YELLOW + p.getLevel());
					// send exp
					Messaging.sendMessage(
							"EXP",
							sender,
							Messaging.colorizeText("" + p.getExp(),
									ChatColor.YELLOW)
									+ "/"
									+ Messaging.colorizeText(
											"" + p.getExpToNextLevel(),
											ChatColor.YELLOW) + " Erfahrung");
					// send money
					Messaging.sendMessage(
							"Money",
							sender,
							Messaging.colorizeText(""
									+ p.getAccount().balance(),
									ChatColor.YELLOW)
									+ " Coins");
					// send skillpoints
					Messaging.sendMessage(
							"Skillpoints",
							sender,
							Messaging.colorizeText("" + p.getSkillPoints(),
									ChatColor.YELLOW) + " Skillpoints");
					// Send all bought skills
					String s = "";
					for (int i = 0; i < p.getSkills().size(); i++) {
						if (i == p.getSkills().size() - 1)
							s += p.getSkill(i).getSkillName() + "";
						else
							s += p.getSkill(i).getSkillName() + ", ";
					}
					Messaging.sendMessage("Skills", sender, s);
				} else {
					Messaging.sendMessage(sender,
							"Du hast nicht die nötigen Rechte dafür!");
				}
			} else {
				// Put your commands in here
				if (cmd.is(args[0], "skills")
						&& RCPermissions.permission(
								cmd.getPlayerOfSender(sender),
								"rcs.player.skills")) {
					handled = true;
					this.player = cmd.getPlayerOfSender(sender);
					RCPlayer p = new RCPlayer(player);
					if (args.length == 1) {
						Messaging
								.sendMessage(
										"Kaufbare Skills",
										sender,
										"Seite "
												+ Messaging.colorizeText("1",
														ChatColor.YELLOW)
												+ " von "
												+ Messaging.colorizeText(
														""
																+ ExtraFunctions
																		.getPages(p
																				.getBuyableSkills()),
														ChatColor.YELLOW));
						if (p.getBuyableSkills() != null
								&& !p.getBuyableSkills().equals(null))
							ExtraFunctions.listPage(p.getBuyableSkills(),
									player, 1);
						else
							Messaging.sendNoTag(sender, ChatColor.RED
									+ "Du kannst noch keine Skills kaufen.");
					} else if (args.length == 2) {
						Messaging
								.sendMessage(
										"Skills",
										sender,
										"Seite "
												+ Messaging.colorizeText(
														args[1],
														ChatColor.YELLOW)
												+ " von "
												+ Messaging.colorizeText(
														""
																+ ExtraFunctions
																		.getPages(p
																				.getBuyableSkills()),
														ChatColor.YELLOW));
						if (p.getBuyableSkills() != null
								&& !p.getBuyableSkills().equals(null))
							ExtraFunctions.listPage(p.getBuyableSkills(),
									player, Integer.parseInt(args[1]));
						else
							Messaging.sendNoTag(sender, ChatColor.RED
									+ "Du kannst noch keine Skills kaufen.");
					}
				}
				if (cmd.is(args[0], "myskills")
						&& RCPermissions.permission(
								cmd.getPlayerOfSender(sender),
								"rcs.player.skills")) {
					handled = true;
					this.player = cmd.getPlayerOfSender(sender);
					RCPlayer p = new RCPlayer(player);
					if (args.length == 1) {
						Messaging
								.sendMessage(
										"Skills",
										sender,
										"Seite "
												+ Messaging.colorizeText("1",
														ChatColor.YELLOW)
												+ " von "
												+ Messaging.colorizeText(
														""
																+ ExtraFunctions
																		.getPages(p
																				.getSkills()),
														ChatColor.YELLOW));
						if (p.getSkills() != null
								&& !p.getSkills().equals(null))
							ExtraFunctions.listPage(p.getSkills(), player, 1);
						else
							Messaging.sendNoTag(sender, ChatColor.RED
									+ "Du hast noch keine Skills gekauft.");
					} else if (args.length == 2) {
						Messaging
								.sendMessage(
										"Skills",
										sender,
										"Seite "
												+ Messaging.colorizeText(
														args[1],
														ChatColor.YELLOW)
												+ " von "
												+ Messaging.colorizeText(
														""
																+ ExtraFunctions
																		.getPages(p
																				.getSkills()),
														ChatColor.YELLOW));
						if (p.getSkills() != null
								&& !p.getSkills().equals(null))
							ExtraFunctions.listPage(p.getSkills(), player,
									Integer.parseInt(args[1]));
						else
							Messaging.sendNoTag(sender, ChatColor.RED
									+ "Du hast noch keine Skills gekauft.");
					}
				}
				if (cmd.is(args[0], "reset")
						&& RCPermissions.permission(
								cmd.getPlayerOfSender(sender),
								"rcs.player.skills.reset")) {
					handled = true;
					this.player = cmd.getPlayerOfSender(sender);
					RCPlayer p = new RCPlayer(player);
					if (args.length == 1) {
						if (p.getSkillCount() == 0) {
							Messaging
									.sendMessage(sender,
											"Du hast keine Skills die du zurücksetzen kannst.");
							return handled;
						}
						Messaging
								.sendMessage(sender,
										"Bist du dir sicher, dass du deine Skills zurücksetzen möchtest?");
						Messaging.sendMessage(
								sender,
								"Du bekommst "
										+ Messaging.colorizeText(
												"" + p.calcSpendSkillpoints(),
												ChatColor.YELLOW)
										+ " Skillpunkte zurück.");
						Messaging
								.sendMessage(
										sender,
										"Dies ist dein "
												+ Messaging.colorizeText(
														(p.getSkillResetCount() + 1)
																+ ".",
														ChatColor.YELLOW)
												+ " Reset und kostet dich: "
												+ Messaging.colorizeText(
														"" + p.getResetCost(),
														ChatColor.YELLOW)
												+ " Coins");
						Messaging
								.sendMessage(sender,
										"Gebe '/rcs reset confirm' ein um alle deine Skills zurückzusetzen.");
					} else if (args.length == 2 && cmd.is(args[1], "confirm")) {
						if (p.getSkillCount() == 0) {
							Messaging
									.sendMessage(sender,
											"Du hast keine Skills die du zurücksetzen kannst.");
							return handled;
						} else if (!p.getAccount().hasEnough(p.getResetCost())) {
							Messaging.sendMessage(sender,
									"Du hast nicht genug Coins.");
						} else {
							p.resetSkills();
							Messaging.sendMessage(
									sender,
									"Alle deine Skills wurden zurückgesetzt und dir wurden "
											+ Messaging.colorizeText(
													"" + p.getResetCost(),
													ChatColor.YELLOW)
											+ " Coins abgezogen.");
							Messaging.sendMessage(
									sender,
									"Du hast nun "
											+ Messaging.colorizeText(
													"" + p.getSkillPoints(),
													ChatColor.YELLOW)
											+ " Skillpunkte.");
							p.increaseSkillResetCount();
							p.writeDatabase();
						}
					}
				}
				if (cmd.is(args[0], "info")
						&& args.length == 2
						&& RCPermissions.permission(
								cmd.getPlayerOfSender(sender),
								"rcs.player.skills.info")) {
					handled = true;
					this.player = cmd.getPlayerOfSender(sender);
					if (!args[1].isEmpty()
							&& SkillsConfig.skillsList.contains(args[1])) {
						SingleSkill skill = SkillsConfig
								.getSingleSkill(args[1]);
						Messaging.sendNoTag(
								sender,
								"##### "
										+ Messaging.colorizeText(args[1],
												ChatColor.YELLOW) + " #####");
						Messaging.sendMessage("Name", sender, skill.getName());
						Messaging.sendMessage("Beschreibung", sender,
								skill.getDescription());
						Messaging.sendMessage("Kosten", sender, "Kostet "
								+ skill.getCosts() + " Coins");
						Messaging.sendMessage("Level", sender,
								"Benötigt Level " + skill.getLevel());
						Messaging.sendMessage("Skillpoints", sender,
								"Benötigt " + skill.getSkillpoints()
										+ " Skillpunkte");
					}
				}
				if (cmd.is(args[0], "buy")
						&& args.length == 2
						&& RCPermissions.permission(
								cmd.getPlayerOfSender(sender),
								"rcs.player.skills.buy")) {
					handled = true;
					this.player = cmd.getPlayerOfSender(sender);
					RCPlayer p = new RCPlayer(player);
					if (!args[1].isEmpty()
							&& SkillsConfig.skillsList.contains(args[1])) {
						SingleSkill skill = SkillsConfig
								.getSingleSkill(args[1]);
						if (p.hasSkill(skill)) {
							Messaging.sendMessage(sender,
									"Du hast diesen Skill bereits gekauft.");
						} else if (!p.hasEnoughSkillpoints(skill
								.getSkillpoints())) {
							Messaging.sendMessage(sender,
									"Du hast nicht genügend Skillpunkte!");
						} else if (!p.getAccount().hasEnough(skill.getCosts())) {
							Messaging.sendMessage(sender,
									"Du hast nicht genügend Coins!");
						} else if (!(p.getLevel() >= skill.getLevel())) {
							Messaging.sendMessage(sender, "Du benötigst Level "
									+ skill.getLevel() + " für diesen Skill.");
						} else {
							p.addSkill(skill.getSkillName());
							p.getAccount().subtract(skill.getCosts());
							Messaging.sendMessage(sender, "Du hast soeben "
									+ skill.getName() + " gekauft");
							Messaging
									.sendMessage(
											sender,
											"Dir wurden "
													+ Messaging.colorizeText(""
															+ skill.getCosts(),
															ChatColor.YELLOW)
													+ " Coins und "
													+ Messaging.colorizeText(
															""
																	+ skill.getSkillpoints(),
															ChatColor.YELLOW)
													+ " Skillpunkte abgezogen");
						}
					}
					p.writeDatabase();
				}
				if (cmd.is(args[0], "lvl") && args.length == 1) {
					handled = true;
					this.player = cmd.getPlayerOfSender(sender);
					if (player == sender
							&& RCPermissions.permission(player, "rcs.player.level")) {
						RCPlayer p = new RCPlayer(player);
						p.checkForItems();
						if (p.lvlup(false)) {
							Messaging.sendMessage(sender, "Du bist nun Level "
									+ p.getLevel());
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
				} else if (cmd.is(args[0], "lvl")) {
					// Put your commands in here
					if (cmd.is(args[1], "player") && args.length == 3) {
						handled = true;
						if (RCPermissions
								.isAdmin(cmd.getPlayerOfSender(sender))
								|| RCPermissions.permission(
										cmd.getPlayerOfSender(sender),
										"rcs.admin.level")) {
							this.player = cmd.getPlayer(sender, args, 2);
							RCPlayer p = new RCPlayer(player);
							if (p.lvlup(true)) {
								Messaging.sendMessage(
										p.getServer(),
										player.getName() + " ist nun Level "
												+ ChatColor.YELLOW
												+ p.getLevel());
								RCLogger.info(player.getName()
										+ " ist nun Level " + p.getLevel());
								// Save Changes
								p.writeDatabase();
							}
						} else {
							Messaging.sendMessage(sender,
									"Du hast nicht die nötigen Rechte dafür!");
						}
					}
				}
				if (cmd.is(args[0], "top")) {
					handled = true;
					if (cmd.isPlayer(sender)
							&& RCPermissions.permission(
									cmd.getPlayerOfSender(sender),
									"rcs.player.level.top")) {
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
				}
				if (cmd.is(args[0], "xp") || cmd.is(args[0], "exp")) {
					handled = true;
					if (cmd.isPlayer(sender)) {
						if (args.length == 1
								&& RCPermissions.permission(
										cmd.getPlayerOfSender(sender),
										"rcs.player.level.exp")) {
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
										"rcs.admin.info")) {
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

				}
				if (cmd.is(args[0], "debug")) {
					handled = true;
					if (cmd.isPlayer(sender)
							&& RCPermissions.isAdmin(cmd
									.getPlayerOfSender(sender))) {
						RCSkills.debugging = !RCSkills.debugging;
						if (RCSkills.debugging)
							Messaging.sendMessage(sender,
									"Plugin has begun debugging.");
						else
							Messaging.sendMessage(sender,
									"Plugin has stopped debugging.");
					}
				}
				if (cmd.is(args[0], "reload")) {
					handled = true;
					if (cmd.isPlayer(sender)
							&& RCPermissions.permission(cmd.getPlayerOfSender(sender), "rcs.reload")) {
						RCConfig.load();
						SkillsConfig.load();
						Messaging.sendMessage(
								sender,
								Messaging.colorizeText(RCSkills.name,
										ChatColor.GREEN)
										+ " has been reloaded.");
					}
				}
				if (cmd.is(args[0], "setlvl") && args.length > 1) {
					handled = true;
					if (cmd.isPlayer(sender)
							&& RCPermissions.permission(
									cmd.getPlayerOfSender(sender),
									"rcs.admin.setlvl")) {
						if (cmd.is(args[1], "player") && args.length == 4) {
							this.player = cmd.getPlayer(sender, args, 2);
							RCPlayer p = new RCPlayer(player);
							if (!(p.getLevel() != -1)) {
								p.setLevel(Integer.parseInt(args[3]));
								Messaging.sendMessage(
										p.getServer(),
										player.getName() + " ist nun Level "
												+ ChatColor.YELLOW
												+ p.getLevel());
								RCLogger.info(player.getName()
										+ " ist nun Level " + p.getLevel());
								// Save Changes
								p.writeDatabase();
							} else {
								Messaging
										.sendMessage(sender,
												"Spieler in dieser Gruppe können nicht leveln.");
							}

						}
					} else {
						Messaging.sendMessage(sender,
								"Du hast nicht die nötigen Rechte dafür!");
					}
				}
			}
		}
		return handled;
	}
}
