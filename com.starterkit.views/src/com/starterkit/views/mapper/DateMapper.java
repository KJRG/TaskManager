package com.starterkit.views.mapper;

import org.eclipse.swt.widgets.DateTime;

public class DateMapper {
	public static String getDateString(DateTime date) {
		/*
		 * The month is a number between 0 and 11, it must be incremented.
		 */
		return date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDay();
	}
}
