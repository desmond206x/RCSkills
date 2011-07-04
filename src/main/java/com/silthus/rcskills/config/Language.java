package com.silthus.rcskills.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.ChatColor;
import org.bukkit.util.config.Configuration;

import com.silthus.rcskills.RCLogger;
import com.silthus.rcskills.RCSkills;
import com.silthus.rcskills.extras.Messaging;

public class Language {
	protected static String langFileName = "lang.yml";
	private static Configuration config = null;
	private static File configFile;
	private static RCSkills plugin;
	
	// Help Menu Config
	public static String helpRcs;
	public static String helpRcsLvl;
	public static String helpSkills;
	public static String helpMyskills;
	public static String helpInfo;
	public static String helpBuy;
	public static String helpReset;
	public static String helpTop;
	public static String helpExp;
	// RCS Config
	public static String noPermission;
	public static String experiance;
	public static String currency;
	public static String costs;
	public static String skillpoints;
	public static String page;
	public static String buyableSkills;
	public static String description;
	public static String and;
	public static String from;
	public static String youGot;
	public static String has;
	public static String deducted;
	public static String isNowLevel;
	public static String forTheNextLevel;
	public static String reachedMaxLevel;
	public static String your;
	public static String haveBeenExchangedForExp;
	public static String needs;
	// Skills Config
	public static String noLevel;
	public static String noSkills;
	public static String youAlreadyHaveThisSkill;
	public static String youDontHaveEnough;
	public static String youHaveNow;
	public static String hasNow;
	public static String substracted;
	public static String youNeedLevel;
	public static String forThatSkill;
	public static String youJustBought;
	public static String group;
	public static String notExist;
	// Reset config
	public static String noResetSkills;
	public static String resetWarning;
	public static String youGet;
	public static String skillpointsBack;
	public static String thisIsYour;
	public static String resetAndCostsYou;
	public static String confirm;
	public static String allSkillsReset;
	public static String allSkillsResetFrom;
	public static String reseted;
	public static String cantReset;
	// Admin Config
	public static String alreadyHasThisSkill;
	public static String YouJustGotSkill;
	public static String bekommen;
	public static String youAreNowLevel;
	public static String cantLevel;
	public static String youOnlyHave;
	public static String noSkillName;
	public static String noSkillID;
	
	public static void initialize(RCSkills instance) {
		Language.plugin = instance;
		load();
	}

	public static void load() {

		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdirs();
		}

		configFile = new File(plugin.getDataFolder().getAbsolutePath()
				+ File.separator + langFileName);

		if (!configFile.exists()) {
			createNew(configFile);
			RCLogger.warning("No language file found! Creating new one...");
		}
		
		config = new Configuration(configFile);
		config.load();
		if (config.getInt("configversion", 2) < 3) {
			File renameFile = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + langFileName + "_old");
			if (renameFile.exists())
				renameFile.delete();
			configFile.renameTo(renameFile);
			createNew(configFile);
			RCLogger.warning("Old config found! Renaming and creating new one...");
			config = new Configuration(configFile);
			config.load();
		}
		Language.setup(config);
	}

	private static boolean createNew(File configFile) {

		try {
			InputStream stream = RCSkills.class.getResourceAsStream("/"
					+ langFileName);
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
		
		Language.helpRcs = file.getString("help.rcs");
		Language.helpRcsLvl = file.getString("help.rcs lvl");
		Language.helpSkills = file.getString("help.rcs skills");
		Language.helpMyskills = file.getString("help.rcs myskills");
		Language.helpInfo = file.getString("help.rcs info");
		Language.helpBuy = file.getString("help.rcs buy");
		Language.helpReset = file.getString("help.rcs reset");
		Language.helpTop = file.getString("help.rcs top");
		Language.helpExp = file.getString("help.rcs exp");
		// RCS Config
		Language.noPermission = file.getString("rcs.noPermissions");
		Language.noPermission = Messaging.colorizeText(Language.noPermission, ChatColor.RED);
		Language.experiance = file.getString("rcs.Experiance");
		Language.currency = file.getString("rcs.Currency");
		Language.costs = file.getString("rcs.Costs");
		Language.skillpoints = file.getString("rcs.Skillpoints");
		Language.page = file.getString("rcs.Page");
		Language.buyableSkills = file.getString("rcs.BuyableSkills");
		Language.description = file.getString("rcs.Description");
		Language.and = file.getString("rcs.and");
		Language.from = file.getString("rcs.from");
		Language.youGot = file.getString("rcs.youGot");
		Language.has = file.getString("rcs.has");
		Language.deducted = file.getString("rcs.deducted");
		Language.isNowLevel = file.getString("rcs.isNowLevel");
		Language.forTheNextLevel = file.getString("rcs.forTheNextLevel");
		Language.reachedMaxLevel = file.getString("rcs.reachedMaxLevel");
		Language.your = file.getString("rcs.your");
		Language.haveBeenExchangedForExp = file.getString("rcs.haveBeenExchangedForExp");
		Language.needs = file.getString("rcs.needs");
		// Skills Config
		Language.noLevel = file.getString("skills.noLevel");
		Language.noSkills = file.getString("skills.noSkills");
		Language.youAlreadyHaveThisSkill = file.getString("skills.youAlreadyHaveThisSkill");
		Language.youDontHaveEnough = file.getString("skills.youDontHaveEnough");
		Language.youOnlyHave = file.getString("skills.youOnlyHave");
		Language.youHaveNow = file.getString("skills.youHaveNow");
		Language.hasNow = file.getString("skills.hasNow");
		Language.substracted = file.getString("skills.substracted");
		Language.youNeedLevel = file.getString("skills.youNeedLevel");
		Language.forThatSkill = file.getString("skills.forThatSkill");
		Language.youJustBought = file.getString("skills.youJustBought");
		Language.group = file.getString("skills.group");
		Language.notExist = file.getString("skills.notExist");
		// Reset config
		Language.noResetSkills = file.getString("reset.noResetSkills");
		Language.resetWarning = file.getString("reset.resetWarning");
		Language.youGet = file.getString("reset.youGet");
		Language.skillpointsBack = file.getString("reset.skillpointsBack");
		Language.thisIsYour = file.getString("reset.thisIsYour");
		Language.resetAndCostsYou = file.getString("reset.resetAndCostsYou");
		Language.confirm = file.getString("reset.confirm");
		Language.allSkillsReset = file.getString("reset.allSkillsReset");
		Language.allSkillsResetFrom = file.getString("reset.allSkillsResetFrom");
		Language.reseted = file.getString("reset.reseted");
		Language.cantReset = file.getString("reset.cantReset");
		// Admin Config
		Language.alreadyHasThisSkill = file.getString("admin.alreadyHasThisSkill");
		Language.YouJustGotSkill = file.getString("admin.YouJustGotSkill");
		Language.bekommen = file.getString("admin.bekommen");
		Language.youAreNowLevel = file.getString("admin.youAreNowLevel");
		Language.cantLevel = file.getString("admin.cantLevel");
		Language.noSkillName = file.getString("admin.noSkillName");
		Language.noSkillID = file.getString("admin.noSkillID");
	}
}
