package com.saaranga.wikitrack.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;

/**
 * Utitlity class - format the date string from mm-dd-yy format to ಇಷ್ಟು ದಿನ
 * ಇಷ್ಟು ಗಂಟೆ ಇಷ್ಟು ನಿಮಿಷಗಳ ನಂತರ
 * 
 * Usage: set the pattern for the SimpleDateformatter in constructor
 * 
 * @author supreeth
 * @version 1.0 30-05-2012
 * 
 *          Copyright Saaranga Infotech
 */
public class DateFormatter {

	private static long yearDivisor = 31536000;
	private static long dayDivisor = 86400;
	private static long hourDivisor = 3600;
	private static long minDivisor = 60;

	private static SimpleDateFormat dateFormat;
	private static long diffInsec;
	private Context ctx;

	// These are the strings used to construct the formatted date string. The
	// values for these strings are determined depending on the version of the
	// app
	private String stringHinde;
	private String stringVarshagala;
	private String stringVarshada;
	private String stringDinagala;
	private String stringDinada;
	private String stringGhantegala;
	private String stringGhanteya;
	private String stringNimishagala;
	private String stringNimishada;
	private String stringSecendugala;
	private String stringSecendu;
	private String stringGa;
	private String stringDa;
	private String stringYa;

	public DateFormatter(String pattern) {
		dateFormat = new SimpleDateFormat(pattern);
	}

	/**
	 * Formats the date to the pattern provided TODO check if the parameter
	 * pattern is valid
	 * 
	 * @param unformatted
	 * @return
	 */
	public String getFormattedDate(String unformatted) {

		getStringValues();

		Date myDate = null;
		try {
			myDate = dateFormat.parse(unformatted);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Date now = new Date();

		Calendar mycal = Calendar.getInstance();
		Calendar nowCal = Calendar.getInstance();

		mycal.setTime(myDate);
		nowCal.setTime(now);

		long milliseconds1 = mycal.getTimeInMillis();
		long milliseconds2 = nowCal.getTimeInMillis();
		long diff = milliseconds2 - milliseconds1;
		diffInsec = diff / 1000;

		String year = getYear();
		String day = getDay();
		String hour = getHour();
		String min = getMin();
		String sec = getSec();

		if (!year.equals("")) {
			return year + stringHinde;
		} else {
			if (!day.equals("")) {
				return day + stringHinde;
			} else {
				if (!hour.equals("")) {
					if (!min.equals("")) {
						return trimWord(hour) + "," + min + stringHinde;
					} else {
						return hour + stringHinde;
					}
				} else {
					if (!min.equals("")) {
						return min + stringHinde;
					} else {
						return sec + stringHinde;
					}
				}
			}
		}

	}

	private void getStringValues() {
		

			stringHinde = " ago";
			stringVarshagala = " years";
			stringVarshada = " year";
			stringDinagala = " days";
			stringDinada = " day";
			stringGhantegala = " hours";
			stringGhanteya = " hour";
			stringNimishagala = " minutes";
			stringNimishada = " minute";
			stringSecendugala = " seconds";
			stringSecendu = " second";
			stringGa = "ಗ";
			stringDa = "ದ";
			stringYa = "ಯ";
		
	}


	private String getYear() {
		long diffYear = diffInsec / yearDivisor;
		if (diffYear > 0) {
			if (diffYear > 1)
				return "" + diffYear + stringVarshagala;
			else
				return "" + diffYear + stringVarshada;
		} else
			return "";
	}

	private String getDay() {
		long diffDay = (diffInsec % yearDivisor) / dayDivisor;
		if (diffDay > 0) {
			if (diffDay > 1)
				return "" + diffDay + stringDinagala;
			else
				return "" + diffDay + stringDinada;
		} else
			return "";
	}

	private String getHour() {
		long diffHr = ((diffInsec % yearDivisor) % dayDivisor) / hourDivisor;
		if (diffHr > 0) {
			if (diffHr > 1)
				return "" + diffHr + stringGhantegala;
			else
				return "" + diffHr + stringGhanteya;
		} else
			return "";
	}

	private String getMin() {
		long diffMin = (((diffInsec % yearDivisor) % dayDivisor) % hourDivisor)
				/ minDivisor;
		if (diffMin > 0) {
			if (diffMin > 1)
				return "" + diffMin + stringNimishagala;
			else
				return "" + diffMin + stringNimishada;
		} else
			return "";
	}

	private String getSec() {
		long diffSec = (((diffInsec % yearDivisor) % dayDivisor) % hourDivisor)
				% minDivisor;
		if (diffSec > 0) {
			if (diffSec > 1)
				return "" + diffSec + stringSecendugala;
			else
				return "" + diffSec + stringSecendu;
		} else
			return "";
	}

	private String trimWord(String untrimmed) {
		int index = untrimmed.indexOf(stringGa);
		int index2 = untrimmed.indexOf(stringDa);
		int index3 = untrimmed.indexOf(stringYa);
		if (index != -1) {
			return untrimmed.substring(0, index);
		} else if (index2 != -1) {
			return untrimmed.substring(0, index2);
		} else if (index3 != -1) {
			return untrimmed.substring(0, index3);
		} else {
			return untrimmed;
		}

	}

}