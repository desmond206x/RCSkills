package com.silthus.rcskills.config;


import org.bukkit.util.config.Configuration;

public class SingleSkill {
	
	private String skillName;
	private String name;
	private String description;
	private String group;
	private int level;
	private int skillpoints;
	private int costs;

	public SingleSkill(String skillName, Configuration config) {
		config.load();
		setSkillName(skillName);
		setName(config.getString(getPath() + "name", "kaboom"));
		setDescription(config.getString(getPath() + "description", "gogogogo kaboom"));
		setGroup(config.getString(getPath() + "group", "group"));
		setLevel(config.getInt(getPath() + "requires.level", 5));
		setSkillpoints(config.getInt(getPath() + "requires.skillpoints", 1));
		setCosts(config.getInt(getPath() + "requires.costs", 1000));
	}

	/**
	 * @param skillName the skillName to set
	 */
	private void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	/**
	 * @return the skillName
	 */
	public String getSkillName() {
		return skillName;
	}

	/**
	 * @param name the name to set
	 */
	private void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param description the description to set
	 */
	private void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param group the group to set
	 */
	private void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param level the level to set
	 */
	private void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param skillpoints the skillpoints to set
	 */
	private void setSkillpoints(int skillpoints) {
		this.skillpoints = skillpoints;
	}

	/**
	 * @return the skillpoints
	 */
	public int getSkillpoints() {
		return skillpoints;
	}

	/**
	 * @param costs the costs to set
	 */
	private void setCosts(int costs) {
		this.costs = costs;
	}

	/**
	 * @return the costs
	 */
	public int getCosts() {
		return costs;
	}
	
	private String getPath() {
		return "skills." + getSkillName() + ".";
	}

}
