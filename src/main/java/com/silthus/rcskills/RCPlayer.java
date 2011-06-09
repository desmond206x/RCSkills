package com.silthus.rcskills;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.nijikokun.register.payment.Method.MethodAccount;
import com.nijikokun.register.payment.methods.iCo5;
import com.silthus.rcskills.config.Language;
import com.silthus.rcskills.config.RCConfig;
import com.silthus.rcskills.config.SingleSkill;
import com.silthus.rcskills.config.SkillsConfig;
import com.silthus.rcskills.database.DBLevelup;
import com.silthus.rcskills.database.DBSkills;
import com.silthus.rcskills.extras.ExtraFunctions;
import com.silthus.rcskills.extras.Messaging;

public class RCPlayer {

	private static RCSkills plugin;

	private Player player = null;
	private String playerName = null;
	private World world = null;
	private Server server = null;
	private int level = 0;
	private int exp = 0;
	private int expToNextLevel = 0;
	private int skillPoints = 0;
	private int spendSkillpoints = 0;
	private int skillCount = 0;
	private int skillResetCount = 0;
	private String lastJoinDate = "01-01-2011";
	private MethodAccount account;
	private int converted = 0;

	private DBLevelup lvldb = null;
	private List<DBSkills> skills = null;

	public static void initialize(RCSkills instance) {
		RCPlayer.plugin = instance;
	}

	public RCPlayer(Player player) {
		this.player = player;
		this.world = this.player.getWorld();
		this.server = this.player.getServer();
		setPlayerName();
		// Load the databases
		loadSkillsDatabase();
		loadLevelDatabase();
		// Load the stats of player
		loadStats();
		// Load iConomy
		loadAccount();
	}
	
	public int getConverted() {
		return this.converted;
	}
	
	public void setConverted() {
		this.converted = 1;
	}
	
	/**
	 * Loads all stats of this player from the database
	 */
	private void loadStats() {
		this.player = lvldb.getPlayer();
		this.playerName = lvldb.getPlayerName();
		this.level = lvldb.getLevel();
		this.exp = lvldb.getExp();
		this.expToNextLevel = lvldb.getExpToNextLevel();
		this.lastJoinDate = lvldb.getJoined();
		this.skillPoints = lvldb.getSkillpoints();
		this.skillResetCount = lvldb.getSkillResetCount();
		this.skillCount = lvldb.getSkillCount();
		this.spendSkillpoints = lvldb.getSpendSkillpoints();
		this.converted = lvldb.getConverted();
	}
	
	/**
	 * loads the database entry of player
	 */
	private void loadLevelDatabase() {
		// searches for the playername in the databse (can only exist once)
		lvldb = RCPlayer.plugin.getDatabase().find(DBLevelup.class).where()
				.ieq("playerName", this.player.getName()).findUnique();
		// if player joined first time create new colum with standard variables
		if (lvldb == null) {
			lvldb = new DBLevelup();
			lvldb.setPlayer(player);
			lvldb.setLevel(0);
			lvldb.setExpToNextLevel(getExpToLevel(getLevel() + 1));
			lvldb.setExp(0);
			lvldb.setJoined(this.lastJoinDate);
			lvldb.setSkillpoints(0);
			lvldb.setSkillResetCount(0);
			lvldb.setSkillCount(skills.size());
			lvldb.setSpendSkillpoints(getSpendSkillpoints());
			lvldb.setConverted(getConverted());
		}
	}
	
	/**
	 * loads all skills for the player from the database
	 */
	private void loadSkillsDatabase() {
		skills = RCPlayer.plugin.getDatabase().find(DBSkills.class).where()
				.ieq("playerName", this.player.getName()).findList();
	}
	
	/**
	 * gets the iConomy Account for the player
	 * can be interacted with getAccount().*
	 */
	private void loadAccount() {
		iCo5 economy = new iCo5();
		account = economy.getAccount(player.getName());
	}
	
	/**
	 * saves all changes made into the database
	 * If any variables are added please write them in here too
	 */
	public void writeDatabase() {
		lvldb.setLevel(getLevel());
		lvldb.setExpToNextLevel(getExpToNextLevel());
		lvldb.setExp(getExp());
		lvldb.setJoined(getLastJoinDate());
		lvldb.setSkillpoints(getSkillPoints());
		lvldb.setSkillCount(getSkillCount());
		lvldb.setSkillResetCount(getSkillResetCount());
		lvldb.setSpendSkillpoints(getSpendSkillpoints());
		lvldb.setConverted(getConverted());
		RCPlayer.plugin.getDatabase().save(lvldb);
	}

	public int getLevel() {
		return this.level;
	}
	
	/**
	 * sets the new Level and Group
	 * @param level
	 */
	public void setLevel(int level) {
		String group = "Level" + getLevel();
		if (RCPermissions.removeParent(player, group)) {
			this.level = level;
			group = "Level" + level;
			RCPermissions.addParent(player, group);
			RCPermissions.saveAll();
		}

	}
	
	public void removePermissions() {
		RCPermissions.removeAllPermissions(player);
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getExp() {
		return this.exp;
	}

	public int getExpToNextLevel() {
		setExpToNextLevel();
		return this.expToNextLevel;
	}

	public void setExpToNextLevel() {
		this.expToNextLevel = getExpToLevel(getLevel() + 1);
	}

	public String getPlayerName() {
		return this.playerName;
	}

	public void setPlayerName() {
		this.playerName = player.getName();
	}

	public String getLastJoinDate() {
		return this.lastJoinDate;
	}

	public void setLastJointDate(String date) {
		this.lastJoinDate = date;
	}

	public World getWorld() {
		return this.world;
	}

	public Server getServer() {
		return this.server;
	}

	public int getSkillPoints() {
		return this.skillPoints;
	}

	public void setSkillPoints(int skillPoints) {
		this.skillPoints = skillPoints;
	}

	public void addSkillPoints(int points) {
		this.skillPoints += points;
	}

	public void removeSkillPoints(int points) {
		this.skillPoints -= points;
	}
	
	/**
	 * Grants the player skillpoints based on the set interval in the config
	 * @return
	 */
	public boolean grantSkillPoints() {
		if (getLevel() != 0 && getLevel() % RCConfig.skillpointsInterval == 0) {
			Messaging.sendMessage(player, "You just got "
					+ RCConfig.skillPoints + " Skillpoints!");
			return true;
		}
		return false;
	}

	/**
	 * @param skillCount
	 *            the skillCount to set
	 */
	public void setSkillCount(int skillCount) {
		this.skillCount = skillCount;
	}

	/**
	 * @return the skillCount
	 */
	public int getSkillCount() {
		return skillCount;
	}

	public void increaseSkillCount() {
		this.skillCount += 1;
	}

	public void decreaseSkillCount() {
		this.skillCount -= 1;
	}

	/**
	 * @param skillResetCount
	 *            the skillResetCount to set
	 */
	public void setSkillResetCount(int skillResetCount) {
		this.skillResetCount = skillResetCount;
	}

	/**
	 * @return the skillResetCount
	 */
	public int getSkillResetCount() {
		return skillResetCount;
	}

	public void increaseSkillResetCount() {
		this.skillResetCount += 1;
	}

	public void decreaseSkillResetCount() {
		this.skillResetCount -= 1;
	}

	public List<DBSkills> getSkills() {
		return this.skills;
	}

	public DBSkills getSkill(int index) {
		return this.skills.get(index);
	}

	public boolean hasEnoughSkillpoints(int points) {
		if (getSkillPoints() >= points)
			return true;
		return false;
	}
	
	/**
	 * call this method to interact with the iConomy
	 * account of this player
	 * @return
	 */
	public MethodAccount getAccount() {
		return this.account;
	}
	
	/**
	 * Adds a skill and removes skillpoints if boolean true
	 * @param skillName
	 * @param removeSkillpoints
	 * @return false if group does not exist
	 */
	public boolean addSkill(String skillName, boolean removeSkillpoints) {
		// connects to db
		DBSkills skillsdb = new DBSkills();
		// creates single skill from skillname
		SingleSkill skill = SkillsConfig.getSingleSkill(skillName);
		// adds the Permission group to the player
		if (RCPermissions.addParent(player, skill.getGroup())) {
			skillsdb.setPlayer(player);
			skillsdb.setSkillName(skillName);
			skillsdb.setGroupName(skill.getGroup());
			skillsdb.setCosts(skill.getCosts());
			skillsdb.setSkillPoints(skill.getSkillpoints());
			skillsdb.setSkillLevel(skill.getLevel());
			increaseSkillCount();
			// removes skillpoints if bool true
			if (removeSkillpoints) {
				addSpendSkillpoints(skill.getSkillpoints());
				removeSkillPoints(skill.getSkillpoints());
			}
			// saves all
			RCPlayer.plugin.getDatabase().save(skillsdb);
			RCPermissions.saveAll();
			return true;
		}
		return false;
	}
	
	/**
	 * removes a skill from player
	 * @param skillName
	 * @return
	 */
	public boolean removeSkill(String skillName) {
		// connects to DB and gets unique skil from unique player
		DBSkills skillsdb = RCPlayer.plugin.getDatabase().find(DBSkills.class)
				.where().ieq("skillName", skillName).where()
				.ieq("playerName", player.getName()).findUnique();
		// removes group for skill
		if (RCPermissions.removeParent(player, skillsdb.getGroupName())) {
		decreaseSkillCount();
		// gives back skillpoints
		addSkillPoints(skillsdb.getSkillPoints());
		// saves all
		RCPlayer.plugin.getDatabase().delete(skillsdb);
		RCPlayer.plugin.getDatabase().save(skillsdb);
		RCPermissions.saveAll();
		return true;
		}
		return false;
	}
	
	/*
	 * Resets all skills
	 * Cycles through removeSkill()
	 */
	public boolean resetSkills(boolean removeMoney) {
		if (skills == null)
			return false;
		for (DBSkills s : skills) {
			removeSkill(s.getSkillName());
		}
		if (removeMoney == true)
			this.account.subtract(getResetCost());
		return true;
	}
	
	/**
	 * Checks if player has skill
	 * @param skill
	 * @return hasSkill
	 */
	public boolean hasSkill(SingleSkill skill) {
		// loads the skill from db
		DBSkills skillsdb = RCPlayer.plugin.getDatabase().find(DBSkills.class)
				.where().ieq("skillName", skill.getSkillName()).where()
				.ieq("playerName", player.getName()).findUnique();
		if (skillsdb == null)
			return false;
		return true;
	}
	
	/**
	 * Gets all skills this player can buy
	 * @return buyableSkills
	 */
	public String[] getBuyableSkills() {
		int i = 0;
		// first get all skills and filter the ones with a too high level
		for (String s : SkillsConfig.skills) {
			if (SkillsConfig.getSingleSkill(s).getLevel() <= getLevel())
				i++;
		}
		String[] buyableSkills = new String[i];
		i = 0;
		// organize the filtered skills and
		// save them into a String array
		for (String s : SkillsConfig.skills) {
			if (SkillsConfig.getSingleSkill(s).getLevel() <= getLevel()) {
				if (!skills.contains(s)) {
					buyableSkills[i] = s + "[" + Messaging.colorizeText("" + SkillsConfig.getSingleSkill(s).getId(), ChatColor.YELLOW) + "]";
				}
				i++;
			}
		}
		return buyableSkills;
	}

	public int getSpendSkillpoints() {
		return this.spendSkillpoints;
	}

	public void setSpendSkillpoints(int points) {
		this.spendSkillpoints = points;
	}

	public void addSpendSkillpoints(int points) {
		this.spendSkillpoints += points;
	}

	public int calcSpendSkillpoints() {
		int skillpoints = 0;
		for (DBSkills s : this.skills) {
			skillpoints += s.getSkillPoints();
		}
		return skillpoints;
	}

	public int getTotalSkillpoints() {
		return this.spendSkillpoints + this.skillPoints;
	}
	
	/**
	 * gets the reset cost
	 * @return
	 */
	public int getResetCost() {
		// increase reset count by one because we already resetted this many times
		int count = this.skillResetCount + 1;
		if (this.skillResetCount > 5) {
			count = 5;
			// TODO: make configurable in config
			// use same method as for the level formula
			return (int) (Math.pow(10, count));
		} else if (this.skillResetCount == 0) {
			count = 1;
			// TODO: make configurable
			return 10;
		}
		return (int) (Math.pow(10, count));
	}

	/**
	 * @param exp
	 *            the exp to add
	 */
	public void addExp(int exp) {
		setExp((getExp() + exp));
	}

	/**
	 * @param exp
	 *            the exp to remove
	 */
	public void removeExp(int exp) {
		setExp((getExp() - exp));
	}

	/**
	 * calculates how much exp is needed to the given level
	 * 
	 * @param level
	 *            the exp to calculate for
	 * @return exp
	 */
	public int getExpToLevel(int level) {
		// TODO: replace formula with config
		/*JEP jep = new JEP();
		jep.addVariable("lvl", level);
		jep.parseExpression(RCConfig.expCalc);
		Object result = jep.getValueAsObject();
		return (Integer) result;*/
		return ((((level) * (level)) - ((level) * 5) + 20));
	}

	/**
	 * Removes all Items of item_id then returns the diffrence of amount
	 * 
	 * @param item_id
	 * @param amount
	 */
	public void removeItems(int item_id, int amount) {
		
		// get ItemStack for all items which should be removes
		ItemStack items = new ItemStack(item_id, amount);
		
		// remove all items and save if too many where removed
		HashMap<Integer, ItemStack> difference = player.getInventory().removeItem(items);
		// give back the diffrence
		for (ItemStack s : difference.values())
			player.getInventory().addItem(s);
	}

	/**
	 * increases the level by 1 if max level no reached
	 * 
	 * @param ignoreEXP
	 * @return false if max level reached
	 */
	private boolean nextLevel(boolean b) {
		if (getLevel() + 1 <= RCConfig.maxLevel) {
			if (b == false) {
				// set new exp
				removeExp(getExpToNextLevel()); 
			}
			// set new level
			setLevel((getLevel() + 1));
			// set new needed exp
			setExpToNextLevel(); 
			if (grantSkillPoints()) {
				addSkillPoints(RCConfig.skillPoints);
			}
			return true;
		}
		return false;
	}

	/**
	 * checks if the player already joined today
	 * 
	 * @return joinedToday?
	 * @throws ParseException
	 */
	public boolean hasJoinedToday() {
		return lastJoinDate.equals(ExtraFunctions.getDate());
	}

	public void setJoinedToday() {
		this.lastJoinDate = ExtraFunctions.getDate();
	}

	/**
	 * Checks if the player has enough exp for the given level
	 * 
	 * @param level
	 *            exp for that level
	 * @return
	 */
	public boolean hasExpForLevel(int level) {
		if (getExp() >= getExpToLevel(level)) {
			return true;
		}
		return false;
	}

	/**
	 * Remove itemID and turns it into EXP
	 * 
	 * @return hasItems
	 */
	public boolean checkForItems() {
		// get all items of itemID
		// 2304 because thats the max inventory space
		ItemStack items = new ItemStack(RCConfig.itemID, 2304);
		
		if (player.getInventory().contains(RCConfig.itemID)) {
			// remove all items and save how many where not removed
			// saves 2304 - how many items the player had
			HashMap<Integer, ItemStack> difference = player.getInventory().removeItem(items);
			int exp = 0;
			// change the items for exp
			// check every stack and how many items this stack had
			for (ItemStack s : difference.values())
				// save in exp how many items the stack contained
				exp += s.getAmount();
			// take the negative since it doesnt really count how many items where taken
			exp = 2304 - exp;
			// finally add the exp
			addExp(exp);
			Messaging.sendMessage(player, Language.your + " "
					+ ChatColor.YELLOW + exp + " " + ChatColor.WHITE
					+ RCConfig.itemName + " "
					+ Language.haveBeenExchangedForExp + ".");
			return true;
		}
		return false;
	}

	/**
	 * main function for the levelup event
	 * 
	 * @param ignoreItems
	 * @return lvlup successfull or failed
	 */
	public boolean lvlup(boolean ignoreItems) {

		// exchanges all items for exp
		checkForItems();
		// Without exp check
		if (ignoreItems == true) {
			if (getLevel() == -1 && getExp() != 0)
				setExp(0);
			if (getLevel() == -1) {
				Messaging.sendMessage(player, Language.cantLevel);
				return false;
				// ingore exp --> true
			} else if (nextLevel(true)) {
				return true;
			}
			Messaging.sendMessage(player, Language.reachedMaxLevel);
			return true;
		} else {
			// with exp check
			if (hasExpForLevel(getLevel() + 1)) {
				if (getLevel() == -1) {
					Messaging.sendMessage(player, Language.cantLevel);
					return false;
				}
				// this is where the player actually levels
				if (!nextLevel(false)) {
					Messaging.sendMessage(player, Language.reachedMaxLevel);
					return false;
				}
				// TODO: Skillpoints
				return true;
			} else {
				Messaging.sendMessage(player, ChatColor.RED
						+ Language.youDontHaveEnough + " EXP!");
				Messaging.sendMessage(player, Language.youOnlyHave + " "
						+ ChatColor.YELLOW + getExp() + ChatColor.WHITE + "/"
						+ ChatColor.YELLOW + getExpToNextLevel()
						+ ChatColor.WHITE + " EXP " + Language.forTheNextLevel);
				return false;
			}
		}
	}

	/**
	 * LevelUP Check for the PlayerJoin Event
	 */
	public void checkLevelUP() {
		// call the main lvlup method
		if (lvlup(false)) {
			Messaging.sendMessage(player, Language.youAreNowLevel + " "
					+ ChatColor.YELLOW + getLevel());
			Messaging.sendMessage(player, Language.youGot + " "
					+ ChatColor.YELLOW + getExpToNextLevel() + " EXP "
					+ Language.deducted + ".");
			Messaging
					.sendMessage(this.server, player.getName() + " "
							+ Language.isNowLevel + " " + ChatColor.YELLOW
							+ getLevel());
			RCLogger.info("[LevelUP] " + player.getName() + " "
					+ Language.isNowLevel + " " + getLevel());
		}
	}

}
