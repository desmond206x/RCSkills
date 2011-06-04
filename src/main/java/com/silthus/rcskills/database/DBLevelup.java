package com.silthus.rcskills.database;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.avaje.ebean.validation.Length;
import com.avaje.ebean.validation.NotNull;

/**
 * 
 * @author Silthus
 * 
 */
@Entity()
@Table(name = "rcs_level")
public class DBLevelup {

	@Id
	private int id;
	@NotNull
	@Length(max = 32)
	private String playerName;
	private int level;
	private int exp;
	private int expToNextLevel;
	private String joined;
	private int skillpoints;
	private int skillCount;
	private int skillResetCount;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String player) {
		this.playerName = player;
	}

	public Player getPlayer() {
		return Bukkit.getServer().getPlayer(playerName);
	}

	public void setPlayer(Player player) {
		this.playerName = player.getName();
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getExp() {
		return exp;
	}

	public void setExpToNextLevel(int expToNextLevelUP) {
		this.expToNextLevel = expToNextLevelUP;
	}

	public int getExpToNextLevel() {
		return expToNextLevel;
	}

	public void setJoined(String date) {
		this.joined = date;
	}

	public String getJoined() {
		return joined;
	}

	public void setSkillpoints(int skillpoints) {
		this.skillpoints = skillpoints;
	}

	public int getSkillpoints() {
		return skillpoints;
	}

	public void setSkillCount(int skillCount) {
		this.skillCount = skillCount;
	}

	public int getSkillCount() {
		return skillCount;
	}

	public void setSkillResetCount(int skillResetCount) {
		this.skillResetCount = skillResetCount;
	}

	public int getSkillResetCount() {
		return skillResetCount;
	}
}