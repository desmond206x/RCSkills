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
import com.silthus.rcskills.config.Language;
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
	// all help infos are loaded here
	private String[] help = { "/rcs " + Language.helpRcs,
			"/rcs lvl " + Language.helpRcsLvl,
			"/rcs skills " + Language.helpSkills,
			"/rcs myskills " + Language.helpMyskills,
			"/rcs info <SkillName> " + Language.helpInfo,
			"/rcs buy <SkillName> " + Language.helpBuy,
			"/rcs reset " + Language.helpReset, "/rcs top " + Language.helpTop,
			"/rcs exp " + Language.helpExp };

	public CMDrcs(RCSkills instance) {
		CMDrcs.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		// handled is returned at the end of onCommand
		boolean handled = false;
		cmd = plugin.getCommandManager();
		// checks if even our plugin is used: /rcs
		// basic /rcs stats output comes here
		if (cmd.is(label, "rcs")) {
			if (args == null || args.length == 0) {
				handled = true;
				// convert sender to player
				this.player = cmd.getPlayerOfSender(sender);
				// TODO: rcs.player.stats
				if (player == sender && RCPermissions.permission(player, "rcs.player.stats")) {
					RCPlayer p = new RCPlayer(player);
					// send header
					Messaging.sendNoTag(sender,
							"##### " + Messaging.colorizeText(RCConfig.title + " Stats",ChatColor.YELLOW) + " #####");
					// send level
					Messaging.sendMessage("Level", sender, 
							"Level " + ChatColor.YELLOW + p.getLevel());
					// send exp
					Messaging.sendMessage("EXP",sender,
							Messaging.colorizeText("" + p.getExp(),ChatColor.YELLOW) + "/" 
							+ Messaging.colorizeText( "" + p.getExpToNextLevel(),ChatColor.YELLOW)
							+ " " + Language.experiance);
					// send money
					Messaging.sendMessage("Money",sender,
							Messaging.colorizeText(""+ p.getAccount().balance(),ChatColor.YELLOW)
									+ " " + Language.currency);
					// send skillpoints
					Messaging.sendMessage(Language.skillpoints,sender,
							Messaging.colorizeText("" + p.getSkillPoints(),ChatColor.YELLOW)
							+ " " + Language.skillpoints);
					// Send all bought skills
					// TODO: create extra method
					if(!p.getSkills().isEmpty()) {
						String s = "";
						// get all skills
						for (int i = 0; i < p.getSkills().size(); i++) {
							if (i == p.getSkills().size() - 1)
								s += p.getSkill(i).getSkillName() + "";
							else
								s += p.getSkill(i).getSkillName() + ", ";
						}
						Messaging.sendMessage("Skills", sender, s);
					}
				} else {
					Messaging.sendMessage(sender, Language.noPermission);
				}
			// output for /rcs player
			} else if (cmd.is(args[0], "player")) {
				// TODO: rcs.admin.info
				if (args.length == 2 && RCPermissions.permission(cmd.getPlayerOfSender(sender),"rcs.admin.info")
						|| RCPermissions.isAdmin(cmd.getPlayerOfSender(sender))) {
					// get the player passed in args[1] from all online players
					this.player = cmd.getPlayer(sender, args, 1);
					RCPlayer p = new RCPlayer(this.player);
					// send header
					Messaging.sendNoTag(sender,
							"##### " + Messaging.colorizeText(player.getName() + "'s Stats", ChatColor.YELLOW) + " #####");
					// send level
					Messaging.sendMessage("Level", sender, "Level "
							+ ChatColor.YELLOW + p.getLevel());
					// send exp
					Messaging.sendMessage("EXP",sender,
							Messaging.colorizeText("" + p.getExp(),ChatColor.YELLOW) + "/"
									+ Messaging.colorizeText("" + p.getExpToNextLevel(),ChatColor.YELLOW)
									+ " " + Language.experiance);
					// send money
					Messaging.sendMessage("Money",sender,
							Messaging.colorizeText(""+ p.getAccount().balance(),ChatColor.YELLOW)
									+ " " + Language.currency);
					// send skillpoints
					Messaging.sendMessage(Language.skillpoints,sender,
							Messaging.colorizeText("" + p.getSkillPoints(),ChatColor.YELLOW)
									+ " " + Language.skillpoints);
					// Send all bought skills
					// TODO: create extra method
					if(!p.getSkills().isEmpty()) {
						// get all skills
						String s = "";
						for (int i = 0; i < p.getSkills().size(); i++) {
							if (i == p.getSkills().size() - 1)
								s += p.getSkill(i).getSkillName() + "";
							else
								s += p.getSkill(i).getSkillName() + ", ";
						}
						Messaging.sendMessage("Skills", sender, s);
					}
				} else {
					Messaging.sendMessage(sender, Language.noPermission);
				}
			// all other commands behind /rcs go here
			} else {
				// TODO: rcs.player.skills
				if (cmd.is(args[0], "skills")&& RCPermissions.permission(cmd.getPlayerOfSender(sender),"rcs.player.skills")) {
					handled = true;
					this.player = cmd.getPlayerOfSender(sender);
					RCPlayer p = new RCPlayer(player);
					// if no page is defined continue
					if (args.length == 1) {
						// gets if the player can buy any skills
						if(p.getBuyableSkills().length > 0){
							Messaging.sendMessage(Language.buyableSkills,sender,
										Language.page+ " "+ Messaging.colorizeText("1",ChatColor.YELLOW)
										+ " " + Language.from + " "
										+ Messaging.colorizeText(""+ ExtraFunctions.getPages(p.getBuyableSkills()),ChatColor.YELLOW));
							ExtraFunctions.listPage(p.getBuyableSkills(),player, 1);
						} else {
							Messaging.sendNoTag(sender, ChatColor.RED + Language.noLevel);
						}
					// lists page two
					} else if (args.length == 2) {
						if(Character.isDigit(args[1].charAt(0)) && Integer.valueOf(args[1]) <= ExtraFunctions.getPages(p.getBuyableSkills())){
							if(p.getBuyableSkills().length > 0){
								Messaging.sendMessage(Language.buyableSkills,sender,
											Language.page + " " + Messaging.colorizeText(args[1],ChatColor.YELLOW)
											+ " " + Language.from + " " + Messaging.colorizeText(
											"" + ExtraFunctions.getPages(p.getBuyableSkills()),ChatColor.YELLOW));
								// checks if the player can buy any skills
								// TODO: remove when page check is given since its redundent then
								ExtraFunctions.listPage(p.getBuyableSkills(),player, Integer.parseInt(args[1]));
							} else {
								Messaging.sendNoTag(sender, ChatColor.RED + Language.noLevel);
							}
						}
					}
				}
				// TODO: rcs.player.skills
				if (cmd.is(args[0], "myskills") && RCPermissions.permission(cmd.getPlayerOfSender(sender),"rcs.player.skills")) {
					handled = true;
					this.player = cmd.getPlayerOfSender(sender);
					RCPlayer p = new RCPlayer(player);
					// lists page 1 again if no number given
					if (args.length == 1) {
						// checks if the player even has skills
						if(!p.getSkills().isEmpty()){
							Messaging.sendMessage("Skills",sender,
									Language.page + " " + Messaging.colorizeText("1",ChatColor.YELLOW)
									+ " " + Language.from + " "+ Messaging.colorizeText(
									"" + ExtraFunctions.getPages(p.getSkills()),ChatColor.YELLOW));
							ExtraFunctions.listPage(p.getSkills(), player, 1);
						} else{
							Messaging.sendNoTag(sender, ChatColor.RED + Language.noSkills);
						}
					// lists page two
					// TODO: check if page two even exists
					} else if (args.length == 2) {
						if(Character.isDigit(args[1].charAt(0)) && Integer.valueOf(args[1]) <= ExtraFunctions.getPages(p.getSkills())){
							if(!p.getSkills().isEmpty()){
								Messaging.sendMessage("Skills",sender,
											Language.page + " " + Messaging.colorizeText(args[1],ChatColor.YELLOW)
											+ " " + Language.from + " " + Messaging.colorizeText(
											"" + ExtraFunctions.getPages(p.getSkills()),ChatColor.YELLOW));
								// checks if the player has any skills
								// TODO: remove when page check is given since its redundent then
								ExtraFunctions.listPage(p.getSkills(), player, 1);
							} else {
								Messaging.sendNoTag(sender, ChatColor.RED + Language.noSkills);
							}
						}
					}
				}
				// TODO: rcs.player.skills.reset
				if (cmd.is(args[0], "reset") && RCPermissions.permission(cmd.getPlayerOfSender(sender),"rcs.player.skills.reset")) {
					handled = true;
					this.player = cmd.getPlayerOfSender(sender);
					RCPlayer p = new RCPlayer(player);
					// shows info if no confirm was typed
					// TODO: maybe add so that this has to be typed before /rcs reset confirm
					if (args.length == 1) {
						if (p.getSkillCount() == 0) {
							Messaging.sendMessage(sender,Language.noResetSkills);
							return handled;
						}
						Messaging.sendMessage(sender, Language.confirm);
						
						Messaging.sendMessage(sender,
								Language.youGet + " " + Messaging.colorizeText(
								"" + p.calcSpendSkillpoints(),ChatColor.YELLOW) 
								+ " " + Language.skillpointsBack);
						
						Messaging.sendMessage(sender,
								Language.thisIsYour + " " + Messaging.colorizeText(
								(p.getSkillResetCount() + 1) + ".",ChatColor.YELLOW)
								+ " " + Language.resetAndCostsYou + " " + Messaging.colorizeText(
								"" + p.getResetCost(),ChatColor.YELLOW) + " " + Language.currency);
						
						Messaging.sendMessage(sender, Language.confirm);
					// confirm command
					} else if (args.length == 2 && cmd.is(args[1], "confirm")) {
						// no skills to reset
						if (p.getSkillCount() == 0) {
							Messaging.sendMessage(sender,Language.noResetSkills);
							return handled;
						// not enough money
						} else if (!p.getAccount().hasEnough(p.getResetCost())) {
							Messaging.sendMessage(sender,Language.youDontHaveEnough + " " + Language.currency);
						// else reset skills
						} else {
							// reset is happening here
							if (p.resetSkills(true)) {
								Messaging.sendMessage(sender,
										Language.allSkillsReset + " " + Messaging.colorizeText(
										"" + p.getResetCost(),ChatColor.YELLOW) 
										+ " " + Language.currency + " " + Language.deducted);
								
								Messaging.sendMessage(sender,
										Language.youHaveNow + " " + Messaging.colorizeText(
										"" + p.getSkillPoints(),ChatColor.YELLOW)
										+ " " + Language.skillpoints);
								p.increaseSkillResetCount();
								// saves everything
								p.writeDatabase();
							} else {
								Messaging.sendMessage(sender,
										ChatColor.RED + "Could not remove Skill! Already removed?!");
							}
						}
					// TODO: rcs.admin.reset
					} else if (args.length == 3 && cmd.is(args[1], "player")) {
						if (RCPermissions.permission(cmd.getPlayerOfSender(sender),"rcs.admin.reset")
								|| RCPermissions.isAdmin(cmd.getPlayerOfSender(sender))) {
							// gets player from list
							this.player = cmd.getPlayer(sender, args, 2);
							p = new RCPlayer(player);
							// no skills to reset
							if (p.getSkillCount() == 0) {
								Messaging.sendMessage(sender,Language.noResetSkills);
								return handled;
							} else {
								// reset is happeing here
								if (p.resetSkills(false)) {
									Messaging.sendMessage(player,
											Language.allSkillsResetFrom + " " + Messaging.colorizeText(
											cmd.getPlayerOfSender(sender).getName(),ChatColor.YELLOW)
											+ " " + Language.reseted);
									
									Messaging.sendMessage(player,
											Language.youHaveNow + " " + Messaging.colorizeText(
											"" + p.getSkillPoints(),ChatColor.YELLOW)
											+ " " + Language.skillpoints);
									p.increaseSkillResetCount();
									// saves everything
									p.writeDatabase();
								} else {
									Messaging.sendMessage(sender,
											// TODO: create option in lang.yml
											ChatColor.RED + "Could not remove Skill! Already removed?!");
								}
							}
						}
					}
				}
				// TODO: rcs.player.skills.info
				if (cmd.is(args[0], "info") && args.length == 2 && RCPermissions.permission(cmd.getPlayerOfSender(sender),"rcs.player.skills.info")) {
					handled = true;
					this.player = cmd.getPlayerOfSender(sender);
					// check if skillname was sent
					if (!args[1].isEmpty()) {
						SingleSkill skill = null;
						// try to get skill ID
						// if input was String continue with skillName
						try {
							int id = Integer.parseInt(args[1]);
							try {
								skill = SkillsConfig.getSingleSkill(id);
							} catch (NullPointerException n) {
								Messaging.sendMessage(sender,
										ChatColor.RED + "There is now Skill with that ID!");
								return handled;
							}
						} catch (Exception e) {
							if (SkillsConfig.skillsList.contains(args[1])) {
								skill = SkillsConfig.getSingleSkill(args[1]);
							} else {
								Messaging.sendMessage(sender,
										ChatColor.RED + "There is no skill with that name!");
								return handled;
							}
						}
						try {
							Messaging.sendNoTag(sender,"##### " + Messaging.colorizeText(skill.getSkillName(),ChatColor.YELLOW) + " #####");
							Messaging.sendMessage("Name",sender,skill.getName() + " " + "[" + Messaging.colorizeText(skill.getId() + "",ChatColor.YELLOW) + "]");
							Messaging.sendMessage(Language.description, sender,skill.getDescription());
							Messaging.sendMessage(Language.costs, sender,"Kostet " + skill.getCosts() + " Coins");
							Messaging.sendMessage("Level",sender,Language.needs + " level " + skill.getLevel());
							Messaging.sendMessage("Skillpoints",sender,Language.needs + " " + skill.getSkillpoints() + " " + Language.skillpoints);
						} catch (NullPointerException e) {
							Messaging.sendMessage(sender, ChatColor.RED
									+ "There is no skill with that name!");
							return handled;
						}
					}
				}
				// TODO: rcs.player.skills.buy
				if (cmd.is(args[0], "buy") && args.length == 2 && RCPermissions.permission(cmd.getPlayerOfSender(sender),"rcs.player.skills.buy")) {
					handled = true;
					this.player = cmd.getPlayerOfSender(sender);
					RCPlayer p = new RCPlayer(player);
					if (!args[1].isEmpty()) {
						SingleSkill skill = null;
						try {
							int id = Integer.valueOf(args[1]).intValue();
							try {
								skill = SkillsConfig.getSingleSkill(id);
							} catch (NullPointerException n) {
								Messaging.sendMessage(sender,ChatColor.RED + "There is no skill with that ID!");
								return handled;
							}
						} catch (NumberFormatException e) {
							if (SkillsConfig.skillsList.contains(args[1])) {
								skill = SkillsConfig.getSingleSkill(args[1]);
							} else {
								Messaging.sendMessage(sender,ChatColor.RED + "There is no skill with that Name!");
								return handled;
							}
						}
						try {
							// see if skill was already bough
							if (p.hasSkill(skill)) {
								Messaging.sendMessage(sender,Language.youAlreadyHaveThisSkill);
							// check for skillpoints
							} else if (!p.hasEnoughSkillpoints(skill.getSkillpoints())) {
								Messaging.sendMessage(sender,Language.youDontHaveEnough + " " + Language.skillpoints);
							// check for money
							} else if (!p.getAccount().hasEnough(skill.getCosts())) {
								Messaging.sendMessage(sender,Language.youDontHaveEnough + " " + Language.currency);
							// check for level
							} else {
								if (p.getCanLevel()) {
									if (!(p.getLevel() >= skill.getLevel())) {
										Messaging.sendMessage(sender,Language.youNeedLevel + " " + skill.getLevel() + " " + Language.forThatSkill);
										return handled;
									}
								}
								// adds the skill and substracts the money
								if (p.addSkill(skill.getSkillName(), true)) {
									p.getAccount().subtract(skill.getCosts());
									
									Messaging.sendMessage(sender,Language.youJustBought + ": " + skill.getName());
									Messaging.sendMessage(sender,Language.youGot+ " " + Messaging.colorizeText(
											"" + skill.getCosts(),ChatColor.YELLOW)
											+ " " + Language.currency + " " + Language.and + " " + Messaging.colorizeText(
											"" + skill.getSkillpoints(),ChatColor.YELLOW) + " " + Language.skillpoints + " " + Language.deducted);
								} else {
									Messaging.sendMessage(sender, ChatColor.RED + "Group " + skill.getGroup() + " does not exist!");
								}
							}
						} catch (NullPointerException e) {
							// TODO: create sentence in lang.yml
							Messaging.sendMessage(sender, ChatColor.RED + "There is no skill with that name!");
							return handled;
						}
					}
					// save everything
					p.writeDatabase();
				}
				// TODO: rcs.admin.addskill
				if (cmd.is(args[0], "addskill") && args.length == 3 ) {
					if (RCPermissions.permission(cmd.getPlayerOfSender(sender),"rcs.admin.addskill") 
							|| RCPermissions.isAdmin(cmd.getPlayerOfSender(sender))) {
					handled = true;
					this.player = cmd.getPlayer(sender, args, 1);
					RCPlayer p = new RCPlayer(player);
					if (!args[2].isEmpty()) {
						SingleSkill skill = null;
						try {
							int id = Integer.valueOf(args[2]).intValue();
							try {
								skill = SkillsConfig.getSingleSkill(id);
							} catch (ArrayIndexOutOfBoundsException oob) {
								Messaging.sendMessage(sender,ChatColor.RED + "There is no skill with that ID!");
								return handled;
							}
						} catch (NumberFormatException e) {
							if (SkillsConfig.skillsList.contains(args[2])) {
								skill = SkillsConfig.getSingleSkill(args[2]);
							} else {
								Messaging.sendMessage(sender,ChatColor.RED + "There is no skill with that name!");
								return handled;
							}
						}
						try {
							if (p.hasSkill(skill)) {
								Messaging.sendMessage(sender, p.getPlayerName()
										+ " " + Language.alreadyHasThisSkill);
							} else if (!(p.getLevel() >= skill.getLevel())) {
								Messaging.sendMessage(sender, p.getPlayerName()
										+ " " + Language.needs + " level "
										+ skill.getLevel() + " "
										+ Language.forThatSkill);
							} else {
								if (p.addSkill(skill.getSkillName(), false)) {
									Messaging.sendMessage(player,
											Language.YouJustGotSkill + " '" + skill.getName() + "' "
											+ Language.from + " " 
											+ cmd.getPlayerOfSender(sender).getName()+ " " + Language.bekommen);

								} else {
									Messaging.sendMessage(sender, ChatColor.RED
											+ "Group " + skill.getGroup()
											+ " does not exist!");
								}
							}
						} catch (NullPointerException e) {
							Messaging.sendMessage(sender, ChatColor.RED
									+ "There is no skill with that name!");
							return handled;
						}
					}

					p.writeDatabase();
					}
				}
				// TODO: rcs.player.level
				if (cmd.is(args[0], "lvl") && args.length == 1) {
					handled = true;
					this.player = cmd.getPlayerOfSender(sender);
					if (player == sender && RCPermissions.permission(player,"rcs.player.level")) {
						RCPlayer p = new RCPlayer(player);
						p.checkForItems();
						if (p.lvlup(false)) {
							Messaging.sendMessage(sender,
									Language.youAreNowLevel + " " + p.getLevel());
							Messaging.sendMessage(sender,
									Language.youGot + " " + ChatColor.YELLOW + p.getExpToLevel(p.getLevel()) + " EXP " + Language.deducted);
							Messaging.sendMessage(p.getServer(),
									player.getName() + " " + Language.isNowLevel + " " + ChatColor.YELLOW + p.getLevel());
							RCLogger.info(player.getName() + " " + Language.isNowLevel + " " + p.getLevel());
							// Save Changes
							p.writeDatabase();
						}
					} else {
						Messaging.sendMessage(sender, Language.noPermission);
					}
				} else if (cmd.is(args[0], "lvl")) {
					// TODO: rcs.admin.level
					if (cmd.is(args[1], "player") && args.length == 3) {
						handled = true;
						if (RCPermissions.permission(cmd.getPlayerOfSender(sender),"rcs.admin.level")
								|| RCPermissions.isAdmin(cmd.getPlayerOfSender(sender))) {
							this.player = cmd.getPlayer(sender, args, 2);
							RCPlayer p = new RCPlayer(player);
							if (p.lvlup(true)) {
								Messaging.sendMessage(p.getServer(),
										player.getName() + " " + Language.isNowLevel + " " + ChatColor.YELLOW + p.getLevel());
								RCLogger.info(player.getName() + " " + Language.isNowLevel + " " + p.getLevel());
								// Save Changes
								p.writeDatabase();
							}
						} else {
							Messaging.sendMessage(sender, Language.noPermission);
						}
					}
				}
				// TODO: rcs.player.level.top
				if (cmd.is(args[0], "top")) {
					handled = true;
					if (cmd.isPlayer(sender) && RCPermissions.permission(cmd.getPlayerOfSender(sender),"rcs.player.level.top")) {
						this.player = cmd.getPlayerOfSender(sender);
						if (args.length == 1) {
							cmd.getTopList(5, sender);
						} else if (args.length == 2) {
							cmd.getTopList(Integer.parseInt(args[1]), sender);
						}
					} else {
						Messaging.sendMessage(sender, Language.noPermission);
					}
				}
				// TODO: rcs.player.level.exp
				if (cmd.is(args[0], "xp") || cmd.is(args[0], "exp")) {
					handled = true;
					if (cmd.isPlayer(sender)) {
						if (args.length == 1 && RCPermissions.permission(cmd.getPlayerOfSender(sender),"rcs.player.level.exp")) {
							this.player = cmd.getPlayerOfSender(sender);
							RCPlayer p = new RCPlayer(player);
							Messaging.sendMessage(sender,
									Language.youGot + " " + Messaging.colorizeText("" + p.getExp(),ChatColor.YELLOW)
									+ " " + Language.from + " " + Messaging.colorizeText("" + p.getExpToNextLevel(),ChatColor.YELLOW)
									+ " EXP " + Language.forTheNextLevel);
						// TODO: rcs.admin.info
						} else if (args.length == 2 && RCPermissions.permission(cmd.getPlayerOfSender(sender),"rcs.admin.info")
								|| RCPermissions.isAdmin(cmd.getPlayerOfSender(sender))) {
							this.player = cmd.getPlayer(sender, args, 1);
							RCPlayer p = new RCPlayer(player);
							Messaging.sendMessage(sender,
									Messaging.colorizeText("" + p.getPlayerName(),ChatColor.YELLOW)
									+ " " + Language.has + " " + Messaging.colorizeText(
									"" + p.getExp(),ChatColor.YELLOW)
									+ " " + Language.from + " " + Messaging.colorizeText(
									"" + p.getExpToNextLevel(),ChatColor.YELLOW)
									+ " " + Language.forTheNextLevel);
						}
					} else {
						Messaging.sendMessage(sender, Language.noPermission);
					}

				}
				if (cmd.is(args[0], "debug")) {
					handled = true;
					if (cmd.isPlayer(sender) && RCPermissions.isAdmin(cmd.getPlayerOfSender(sender))) {
						RCSkills.debugging = !RCSkills.debugging;
						if (RCSkills.debugging)
							Messaging.sendMessage(sender,
									"Plugin has begun debugging.");
						else
							Messaging.sendMessage(sender,
									"Plugin has stopped debugging.");
					}
				}
				// TODO: rcs.reload
				if (cmd.is(args[0], "reload")) {
					handled = true;
					if (cmd.isPlayer(sender) && RCPermissions.permission(cmd.getPlayerOfSender(sender),"rcs.reload")
							|| RCPermissions.isAdmin(cmd.getPlayerOfSender(sender))) {
						RCConfig.load();
						SkillsConfig.load();
						Language.load();
						Messaging.sendMessage(sender,Messaging.colorizeText(RCSkills.name,ChatColor.GREEN) + " has been reloaded.");
					}
				}
				// TODO: rcs.admin.setlvl
				if (cmd.is(args[0], "setlvl") && args.length > 1) {
					handled = true;
					if (cmd.isPlayer(sender) && RCPermissions.permission(cmd.getPlayerOfSender(sender),"rcs.admin.setlvl")
							|| RCPermissions.isAdmin(cmd.getPlayerOfSender(sender))) {
						if (args.length == 3) {
							this.player = cmd.getPlayer(sender, args, 1);
							RCPlayer p = new RCPlayer(player);
							if ((p.getCanLevel())) {
								p.setLevel(Integer.parseInt(args[2]));
								Messaging.sendMessage(p.getServer(),
										player.getName() + " " + Language.isNowLevel + " " + ChatColor.YELLOW + p.getLevel());
								RCLogger.info(player.getName() + " " + Language.isNowLevel + " " + p.getLevel());
								// Save Changes
								p.writeDatabase();
							} else {
								Messaging.sendMessage(sender,Language.cantLevel);
							}
						}
					} else {
						Messaging.sendMessage(sender, Language.noPermission);
					}
				}
				// TODO: rcs.admin.setxp
				if (cmd.is(args[0], "setxp") && args.length > 1) {
					handled = true;
					if (cmd.isPlayer(sender)
							&& RCPermissions.permission(cmd.getPlayerOfSender(sender),"rcs.admin.setxp")
							|| RCPermissions.isAdmin(cmd.getPlayerOfSender(sender))) {
						if (args.length == 3) {
							this.player = cmd.getPlayer(sender, args, 1);
							RCPlayer p = new RCPlayer(player);
							// checks if the player is in the "admin level"
							if ((p.getLevel() != -1)) {
								p.setExp(Integer.parseInt(args[2]));
								Messaging.sendMessage(sender, player.getName()
										+ " " + Language.hasNow + " "
										+ ChatColor.YELLOW + p.getExp() + " "
										+ Language.experiance);
								// Save Changes
								p.writeDatabase();
							} else {
								Messaging.sendMessage(sender,Language.cantLevel);
							}
						}
					} else {
						Messaging.sendMessage(sender, Language.noPermission);
					}
				}
				// TODO: rcs.admin.setsp
				if (cmd.is(args[0], "setsp") || cmd.is(args[0], "setskillpoints") && args.length > 1) {
					handled = true;
					if (cmd.isPlayer(sender) && RCPermissions.permission(cmd.getPlayerOfSender(sender),"rcs.admin.setsp")
							|| RCPermissions.isAdmin(cmd.getPlayerOfSender(sender))) {
						if (args.length == 3) {
							this.player = cmd.getPlayer(sender, args, 1);
							RCPlayer p = new RCPlayer(player);
							// checks if the player is in the "admin level"
							if ((p.getLevel() != -1)) {
								p.setSkillPoints((Integer.parseInt(args[2])));
								Messaging.sendMessage(sender, player.getName()
										+ " " + Language.hasNow + " "
										+ ChatColor.YELLOW + p.getSkillPoints()
										+ " " + Language.skillpoints);
								// Save Changes
								p.writeDatabase();
							} else {
								Messaging.sendMessage(sender,Language.cantLevel);
							}
						}
					} else {
						Messaging.sendMessage(sender, Language.noPermission);
					}
				}
				// everyone can type /rcs help or /rcs ?
				if (cmd.is(args[0], "help") || cmd.is(args[0], "?")) {
					if (args.length == 1) {
						Messaging.sendMessage("RCSkills Help",sender,
								Language.page + " " + Messaging.colorizeText(
								"" + 1,ChatColor.YELLOW) + " " + Language.from + " " + Messaging.colorizeText(
								"" + ExtraFunctions.getPages(help),ChatColor.YELLOW));
						// lists the help array page 1
						ExtraFunctions.listPage(help,cmd.getPlayerOfSender(sender), 1);
					} else if (args.length == 2) {
						Messaging.sendMessage("RCSkills Help",sender,
								Language.page + " " + Messaging.colorizeText("" + args[1],ChatColor.YELLOW)
								+ " " + Language.from + " " + Messaging.colorizeText(
								"" + ExtraFunctions.getPages(help),ChatColor.YELLOW));
						// lists the help array page x
						ExtraFunctions.listPage(help,cmd.getPlayerOfSender(sender),Integer.parseInt(args[1]));
					}
				}
			}
		}
		return handled;
	}
}
