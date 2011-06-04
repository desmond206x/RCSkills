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
	private String skillName;
	private String group;
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
	public void setGroup(String group) {
		this.group = group;
	}
	public String getGroup() {
		return group;
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
	
}
