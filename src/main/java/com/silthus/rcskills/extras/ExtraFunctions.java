package com.silthus.rcskills.extras;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.silthus.rcskills.database.DBSkills;

public class ExtraFunctions {

	/**
	 * formats a Date into a String with dd-MM-yyyy
	 * 
	 * @param date
	 * @return formated Date
	 */
	public static String getDate() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		return df.format(date);
	}
	
	/**
	 * checks how many pages this array can be divided to
	 * @param array
	 * @return total pages
	 */
	public static int getPages(List<DBSkills> array) {
		return (array.size() / 5) + 1;
	}
	
	/**
	 * checks how many pages this array can be divided to
	 * @param array
	 * @return total pages
	 */
	public static int getPages(String[] array) {
		return (array.length / 5) + 1;
	}
	
	/**
	 * Lits a skill page
	 * @param List<DBSkills>
	 * @param player
	 * @param page
	 */
	public static void listPage(List<DBSkills> array, Player player, int page) {
		if (page != 0) {
			if (array != null) {
				// gets page 1
				if (page == 1) {
					for (int i = 0; i < 5; i++) {
						if (!(array.size() <= i))
							Messaging.sendNoTag(player,
									array.get(i).getSkillName()+ "["+ Messaging.colorizeText("" + i,ChatColor.YELLOW) + "]");
					}
				} else {
					// gets all the other pages
					// TODO: fix buggy output since it outputs everything starting at the input page
					page -= 1;
					for (int i = page * 5; i < ((page * 5) + 5); i++) {
						if (!(array.size() <= i))
							Messaging.sendNoTag(player,
									array.get(i).getSkillName() + "[" + Messaging.colorizeText("" + i, ChatColor.YELLOW) + "]");
					}
				}
			}
		}
	}
	
	/**
	 * Lits a page from an array
	 * @param String array
	 * @param player
	 * @param page
	 */
	public static void listPage(String[] array, Player player, int page) {
		if (page != 0) {
			if (array != null) {
				// gets page 1
				if (page == 1) {
					for (int i = 0; i < 5; i++) {
						if (!(array.length <= i))
							Messaging.sendNoTag(player, array[i]);
					}
				} else {
					// gets all the other pages
					// TODO: fix buggy output since it outputs everything starting at the input page
					page -= 1;
					for (int i = page * 5; i < ((page * 5) + 5); i++) {
						if (!(array.length <= i))
							Messaging.sendNoTag(player, array[i]);
					}
				}
			}
		}
	}
}
