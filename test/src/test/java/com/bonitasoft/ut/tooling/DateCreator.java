package com.bonitasoft.ut.tooling;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateCreator {

	/**
	 * Create a date for
	 * 
	 * @return
	 */
	public static Date createToday() {
		Calendar calendar = createCalendarUTCTodayAtMidnight();

		Date today = calendar.getTime();

		return today;
	}

	public static Date createTomorrow() {
		return createInXDays(1);
	}
	
	public static Date createInXDays(int numberOfDays) {
		Calendar calendar = createCalendarUTCTodayAtMidnight();

		calendar.add(Calendar.DAY_OF_MONTH, numberOfDays);

		Date dateInXDays = calendar.getTime();

		return dateInXDays;
	}
	
	private static Calendar createCalendarUTCTodayAtMidnight() {
		Calendar calendar = new GregorianCalendar();

		calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

		// reset hour, minutes, seconds and millis
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar;
	}


}
