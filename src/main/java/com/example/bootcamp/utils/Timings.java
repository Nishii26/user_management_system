package com.example.bootcamp.utils;

import java.util.Calendar;
import java.util.TimeZone;

public class Timings {
	
	private Timings() {}

	public static long currentTime() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		return cal.getTimeInMillis();
	}

}
