package com.starterkit.views.mapper;

import org.eclipse.swt.widgets.DateTime;

public class DateMapper {
	public static String getDateString(DateTime date) {
		return date.getYear() + "-" + date.getMonth() + "-" + date.getDay();
	}
}
