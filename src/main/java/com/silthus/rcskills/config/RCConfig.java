package com.silthus.rcskills.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.util.config.Configuration;

import com.silthus.rcskills.RCLogger;
import com.silthus.rcskills.RCSkills;

public class RCConfig {

	protected static String configFileName = "config.yml";
	private static Configuration config = null;
	private static File configFile;
	private static RCSkills plugin;

	public static int itemID;
	public static String itemName;
	public static int maxLevel;
	public static int skillpointsInterval;
	public static int skillPoints;

	public static String track;

	public static boolean useDailyExp;
	public static int normalExp;
	public static int vipExp;

	public static void initialize(RCSkills instance) {
		RCConfig.plugin = instance;
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
			File renameFile = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + configFileName + "_old");
			if (renameFile.exists())
				renameFile.delete();
			configFile.renameTo(renameFile);
			createNew(configFile);
			RCLogger.warning("Old config found! Renaming and creating new one...");
			config = new Configuration(configFile);
			config.load();
		}
		RCConfig.setup(config);
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
		RCConfig.itemID = file.getInt("Item ID", 19);
		RCConfig.itemName = file.getString("Item Name", "Sponge");
		RCConfig.maxLevel = file.getInt("maxLevel", 25);
		RCConfig.skillpointsInterval = file.getInt("Skillpoints Interval", 5);
		RCConfig.skillPoints = file.getInt("Skillpoints", 1);

		RCConfig.track = file.getString("Permissions.track", "level");

		RCConfig.useDailyExp = file.getBoolean("DailyExp.giveDailyExp", false);
		RCConfig.normalExp = file.getInt("DailyExp.normalExp", 20);
		RCConfig.vipExp = file.getInt("DailyExp.vipExp", 40);
	}

}
