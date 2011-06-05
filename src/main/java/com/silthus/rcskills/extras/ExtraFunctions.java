package com.silthus.rcskills.extras;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

	public static int getPages(List<DBSkills> array) {
		return (array.size() / 5) + 1;
	}

	public static int getPages(String[] array) {
		return (array.length / 5) + 1;
	}

	public static void listPage(List<DBSkills> array, Player player, int page) {
		if (array != null) {
			if (page == 1) {
				for (int i = 0; i < 5; i++) {
					if (!(array.size() <= i))
						Messaging.sendNoTag(player, array.get(i).toString());
				}
			} else {
				page -= 1;
				for (int i = page * 5; i < i + 5; i++) {
					if (!(array.size() <= i))
						Messaging.sendNoTag(player, array.get(i).toString());
				}
			}
		}
	}

	public static void listPage(String[] array, Player player, int page) {
		if (array != null) {
			if (page == 1) {
				for (int i = 0; i < 5; i++) {
					if (!(array.length <= i))
						Messaging.sendNoTag(player, array[i]);
				}
			} else {
				page -= 1;
				for (int i = page * 5; i < i + 5; i++) {
					if (!(array.length <= i))
						Messaging.sendNoTag(player, array[i]);
				}
			}
		}
	}

}
