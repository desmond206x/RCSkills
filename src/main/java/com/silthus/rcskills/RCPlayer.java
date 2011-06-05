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
	}

	private void loadLevelDatabase() {
		lvldb = RCPlayer.plugin.getDatabase().find(DBLevelup.class).where()
				.ieq("playerName", this.player.getName()).findUnique();
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
			RCPlayer.plugin.getDatabase().save(lvldb);
		}
	}

	private void loadSkillsDatabase() {
		skills = RCPlayer.plugin.getDatabase().find(DBSkills.class).where()
				.ieq("playerName", this.player.getName()).findList();
	}

	private void loadAccount() {

		iCo5 economy = new iCo5();
		account = economy.getAccount(player.getName());
	}

	public void writeDatabase() {
		lvldb.setLevel(getLevel());
		lvldb.setExpToNextLevel(getExpToNextLevel());
		lvldb.setExp(getExp());
		lvldb.setJoined(getLastJoinDate());
		lvldb.setSkillpoints(getSkillPoints());
		lvldb.setSkillCount(getSkillCount());
		lvldb.setSkillResetCount(getSkillResetCount());
		lvldb.setSpendSkillpoints(getSpendSkillpoints());
		RCPlayer.plugin.getDatabase().save(lvldb);
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
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
		Messaging.sendMessage(
				player,
				"Du hast soeben "
						+ Messaging.colorizeText("" + points, ChatColor.YELLOW)
						+ " Skillpunkte dazubekommen.");
	}

	public void removeSkillPoints(int points) {
		this.skillPoints -= points;
	}

	public boolean grantSkillPoints() {
		if (getLevel() != 0 && getLevel() % RCConfig.skillpointsInterval == 0)
			return true;
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

	public MethodAccount getAccount() {
		return this.account;
	}

	public void addSkill(String skillName) {
		DBSkills skillsdb = new DBSkills();
		SingleSkill skill = SkillsConfig.getSingleSkill(skillName);
		skillsdb.setPlayer(player);
		skillsdb.setSkillName(skillName);
		skillsdb.setGroupName(skill.getGroup());
		skillsdb.setCosts(skill.getCosts());
		skillsdb.setSkillPoints(skill.getSkillpoints());
		skillsdb.setSkillLevel(skill.getLevel());
		increaseSkillCount();
		addSpendSkillpoints(skill.getSkillpoints());
		removeSkillPoints(skill.getSkillpoints());
		RCPlayer.plugin.getDatabase().save(skillsdb);
	}

	public void removeSkill(String skillName) {
		DBSkills skillsdb = RCPlayer.plugin.getDatabase().find(DBSkills.class)
				.where().ieq("skillName", skillName).where()
				.ieq("playerName", player.getName()).findUnique();
		decreaseSkillCount();
		addSkillPoints(skillsdb.getSkillPoints());
		RCPlayer.plugin.getDatabase().delete(skillsdb);
		RCPlayer.plugin.getDatabase().save(skillsdb);
	}

	public boolean resetSkills() {
		if (skills == null)
			return false;
		for (DBSkills s : skills) {
			removeSkill(s.getSkillName());
		}
		this.account.subtract(getResetCost());
		return true;
	}

	public boolean hasSkill(SingleSkill skill) {
		DBSkills skillsdb = RCPlayer.plugin.getDatabase().find(DBSkills.class)
				.where().ieq("skillName", skill.getSkillName()).where()
				.ieq("playerName", player.getName()).findUnique();
		if (skillsdb == null)
			return false;
		return true;
	}

	public String[] getBuyableSkills() {
		int i = 0;
		for (String s : SkillsConfig.skills) {
			if (SkillsConfig.getSingleSkill(s).getLevel() <= getLevel())
				i++;
		}
		String[] buyableSkills = new String[i];
		i = 0;
		for (String s : SkillsConfig.skills) {
			if (SkillsConfig.getSingleSkill(s).getLevel() <= getLevel()) {
				if (!skills.contains(s)) {
					buyableSkills[i] = s;
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

	public int getResetCost() {
		int count = this.skillResetCount + 1;
		if (this.skillResetCount > 5) {
			count = 5;
			return (int) (Math.pow(10, count));
		} else if (this.skillResetCount == 0) {
			count = 1;
			// TODO: make config
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
		return ((((level) * (level)) - ((level) * 5) + 20));
	}

	/**
	 * Removes all Items of item_id then returns the diffrence of amount
	 * 
	 * @param item_id
	 * @param amount
	 */
	public void removeItems(int item_id, int amount) {

		ItemStack items = new ItemStack(item_id, amount);

		HashMap<Integer, ItemStack> difference = player.getInventory()
				.removeItem(items);
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
				removeExp(getExpToNextLevel()); // set new exp
			}
			setLevel((getLevel() + 1)); // set new level
			setExpToNextLevel(); // set new needed exp
			if (grantSkillPoints()) {
				addSkillPoints(RCConfig.skillPoints);
			}
			String group = "Level" + getLevel();
			RCPermissions.promote(player, RCConfig.track, group); // sets the
																	// new group
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

		ItemStack items = new ItemStack(RCConfig.itemID, 2304);

		if (player.getInventory().contains(RCConfig.itemID)) {
			HashMap<Integer, ItemStack> difference = player.getInventory()
					.removeItem(items);
			int exp = 0;
			// change the items for exp
			for (ItemStack s : difference.values())
				exp += s.getAmount();
			exp = 2304 - exp;
			addExp(exp);
			Messaging.sendMessage(player, "Deine " + ChatColor.YELLOW + exp
					+ " " + ChatColor.WHITE + RCConfig.itemName
					+ " wurden gegen EXP eingetauscht.");
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
		// TODO: make configurable
		checkForItems();
		// Without item check
		if (ignoreItems == true) {
			if (getLevel() == -1 && getExp() != 0)
				setExp(0);
			if (getLevel() == -1) {
				Messaging.sendMessage(player,
						"Spieler in der Gruppe " + ChatColor.YELLOW
								+ RCPermissions.getPrimaryGroup(player)
								+ ChatColor.WHITE + " können nicht leveln.");
				return false;
				// ingore item --> true
			} else if (nextLevel(true)) {
				// TODO: Add Skillpoints
				return true;
			}
			Messaging
					.sendMessage(player,
							"Du hast bereits das maximal Level von "
									+ ChatColor.YELLOW + RCConfig.maxLevel
									+ ChatColor.WHITE + " ereicht.");
			return true;
		} else {
			// with item check
			if (hasExpForLevel(getLevel() + 1)) {
				if (getLevel() == -1) {
					Messaging
							.sendMessage(
									player,
									"Spieler in der Gruppe "
											+ ChatColor.YELLOW
											+ RCPermissions
													.getPrimaryGroup(player)
											+ ChatColor.WHITE
											+ " können nicht leveln.");
					return false;
				}
				if (!nextLevel(false)) {
					Messaging.sendMessage(player,
							"Du hast bereits das maximal Level von "
									+ ChatColor.YELLOW + RCConfig.maxLevel
									+ ChatColor.WHITE + " ereicht.");
					return false;
				}
				// TODO: Skillpoints
				return true;
			} else {
				Messaging.sendMessage(player, ChatColor.RED
						+ "Du hast nicht genügend EXP!");
				Messaging.sendMessage(player, "Du hast erst "
						+ ChatColor.YELLOW + getExp() + ChatColor.WHITE + "/"
						+ ChatColor.YELLOW + getExpToNextLevel()
						+ ChatColor.WHITE + " EXP für das nächste Level");
				return false;
			}
		}
	}

	/**
	 * LevelUP Check for the PlayerJoin Event
	 */
	public void checkLevelUP() {
		if (lvlup(false)) {
			Messaging.sendMessage(player, "Du bist nun Level "
					+ ChatColor.YELLOW + getLevel());
			Messaging.sendMessage(player, "Dir wurden " + ChatColor.YELLOW
					+ getExpToNextLevel() + " EXP abgezogen.");
			Messaging.sendMessage(this.server, player.getName()
					+ " ist nun Level " + ChatColor.YELLOW + getLevel());
			RCLogger.info("[LevelUP] " + player.getName() + " ist nun Level "
					+ getLevel());
		}
	}

}
