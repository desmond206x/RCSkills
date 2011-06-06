package com.silthus.rcskills.database;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.avaje.ebean.validation.Length;
import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name = "rcs_skills")
public class DBSkills {
	
	@Id
	private int id;
	@NotNull
	@Length(max = 32)
	private String playerName;
	@NotNull
	private String skillName;
	@NotNull
	private int skillId;
	private String groupName;
	private int costs;
	private int skillPoints;
	private int skillLevel;
	
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
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setGroupName(String group) {
		this.groupName = group;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setCosts(int costs) {
		this.costs = costs;
	}
	public int getCosts() {
		return costs;
	}
	public void setSkillPoints(int skillPoints) {
		this.skillPoints = skillPoints;
	}
	public int getSkillPoints() {
		return skillPoints;
	}
	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
	}
	public int getSkillLevel() {
		return skillLevel;
	}
	/**
	 * @param skillId the skillId to set
	 */
	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
	/**
	 * @return the skillId
	 */
	public int getSkillId() {
		return skillId;
	}
	
}
