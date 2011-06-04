package com.silthus.rcskills;

import java.text.ParseException;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.silthus.rcskills.config.RCConfig;
import com.silthus.rcskills.database.DBLevelup;
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
//	private int skillPoints = 0;
	private String lastJoinDate = "01-01-2011";
	
	private DBLevelup lvldb = null;
//	private Skills skillsdb = null;
	
	public static void initialize(RCSkills instance) {
		RCPlayer.plugin = instance; 
	}
	
	public RCPlayer(Player player) {
		this.player = player;
		this.world = this.player.getWorld();
		this.server = this.player.getServer();
		setPlayerName();
		// Load the database
		loadDatabase();
		// Load the stats of player
		loadStats();
		
	}
	
	private void loadStats() {
		this.player = lvldb.getPlayer();
		this.playerName = lvldb.getPlayerName();
		this.level = lvldb.getLevel();
		this.exp = lvldb.getExp();
		this.expToNextLevel = lvldb.getExpToNextLevel();
		this.lastJoinDate = lvldb.getJoined();
//		this.skillPoints = skillsdb.getSkillPoints();
	}
	
	private void loadDatabase() {
		lvldb = RCPlayer.plugin.getDatabase().find(DBLevelup.class)
		.where().ieq("playerName", this.player.getName()).findUnique();
		if (lvldb == null) {
			lvldb = new DBLevelup();
			lvldb.setPlayer(player);
			lvldb.setPlayerName(player.getName());
			lvldb.setLevel(0);
			lvldb.setExpToNextLevel(getExpToLevel(getLevel() + 1));
			lvldb.setExp(0);
			lvldb.setJoined(this.lastJoinDate);
			RCPlayer.plugin.getDatabase().save(lvldb);
		}
	}
	
	public void writeDatabase() {
		lvldb.setLevel(getLevel());
		lvldb.setExpToNextLevel(getExpToNextLevel());
		lvldb.setExp(getExp());
		lvldb.setJoined(getLastJoinDate());
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
		this.expToNextLevel = getExpToLevel(getLevel() +1);
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
			String group = "Level" + getLevel();
			RCPermissions.promote(player, RCConfig.track, group); // sets the new group
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
		if (ExtraFunctions.getDate().equalsIgnoreCase(getLastJoinDate())) {
			return true;
		} else {	
			return false;
		}
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
			Messaging.sendMessage(player, "Deine "+ ChatColor.YELLOW + exp + " " + ChatColor.WHITE
					+ RCConfig.itemName + " wurden gegen EXP eingetauscht.");
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
				Messaging.sendMessage(player, "Spieler in der Gruppe " + ChatColor.YELLOW
						+ RCPermissions.getPrimaryGroup(player) + ChatColor.WHITE
						+ " können nicht leveln.");
				return false;
				// ingore item --> true
			} else if (nextLevel(true)) {
				// TODO: Add Skillpoints
				return true;
			}
			Messaging.sendMessage(player, "Du hast bereits das maximal Level von "
					+ ChatColor.YELLOW + RCConfig.maxLevel + ChatColor.WHITE
					+ " ereicht.");
			return true;
		} else {
			// with item check
			if (hasExpForLevel(getLevel() + 1)) {
				if (getLevel() == -1) {
					Messaging.sendMessage(player, "Spieler in der Gruppe " + ChatColor.YELLOW
							+ RCPermissions.getPrimaryGroup(player) + ChatColor.WHITE
							+ " können nicht leveln.");
					return false;
				}
				if (!nextLevel(false)) {
					Messaging.sendMessage(player, "Du hast bereits das maximal Level von "
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
					+ getLevel());
			Messaging.sendMessage(player, "Dir wurden "
					+ ChatColor.YELLOW + getExpToNextLevel()
					+ " EXP abgezogen.");
			Messaging.sendMessage(this.server, player.getName() + " ist nun Level " + ChatColor.YELLOW
					+ getLevel());
			RCLogger.info("[LevelUP] " + player.getName() + " ist nun Level "
					+ getLevel());
		}
	}

}
