package com.silthus.rcskills.extras;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

}
