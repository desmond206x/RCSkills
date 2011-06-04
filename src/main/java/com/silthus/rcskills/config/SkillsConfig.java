package com.silthus.rcskills.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.util.config.Configuration;

import com.silthus.rcskills.RCLogger;
import com.silthus.rcskills.RCSkills;

public class SkillsConfig {

	protected static String configFileName = "skills.yml";
	private static Configuration config = null;
	private static File configFile;
	private static RCSkills plugin;
	
	public static String[] skills;

	public static void initialize(RCSkills instance) {
		SkillsConfig.plugin = instance;
		load();
	}

	public static void load() {

		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdirs();
		}

		configFile = new File(plugin.getDataFolder().getAbsolutePath()
				+ File.separator + configFileName);

		if (!configFile.exists()) {
			createNew(configFile);
			RCLogger.warning("No config found! Creating new one...");
		}
		config = new Configuration(configFile);
		config.load();
		if (config.getInt("configversion", 1) < 1) {
			File renameFile = new File(plugin.getDataFolder().getAbsolutePath()
					+ File.separator + configFileName + "_old");
			if (renameFile.exists())
				renameFile.delete();
			configFile.renameTo(renameFile);
			createNew(configFile);
			RCLogger.warning("Old config found! Renaming and creating new one...");
			config = new Configuration(configFile);
			config.load();
		}
		setup(config);
	}

	private static boolean createNew(File configFile) {

		try {
			InputStream stream = RCSkills.class.getResourceAsStream("/"
					+ configFileName);
			OutputStream out = new FileOutputStream(configFile);

			byte[] buf = new byte[1024];
			int len;
			while ((len = stream.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			stream.close();
			out.close();
			return true;
		} catch (IOException iex) {
			RCLogger.warning("Could not create config file! Aborting...");
			return false;
		}
	}

	private static void setup(Configuration file) {
		Object[] obl = file.getList("skills").toArray();
		int len = obl.length;
		String[] skills = {};
		for (int i = 0; i < len; i++) {
			skills[i] = (String) obl[i];
		}
		SkillsConfig.skills = skills;
	}
	
	public static SingleSkill getSingleSkill(String skillName) {
		for (int i = 0; i < SkillsConfig.skills.length; i++) {
			if (skillName.equalsIgnoreCase(SkillsConfig.skills[i]))
				return new SingleSkill(SkillsConfig.skills[i], config);
		}
		return null;
	}
}
