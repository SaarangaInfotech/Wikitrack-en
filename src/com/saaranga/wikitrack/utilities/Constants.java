package com.saaranga.wikitrack.utilities;

import java.util.Calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;

/**
 * Utitlity class - application constants are stored here
 * 
 * @author supreeth
 * @version 1.0 30-05-2012
 * 
 *          Copyright Saaranga Infotech
 */
public class Constants {

	public static final String FEEDTYPE = "FEEDTYPE";

	/**
	 * Language specific Constants Change them approriately to create an
	 * application specific to other language wikis
	 * 
	 */

	public static final String RECENT_CHANGES_FEEDURL = "http://en.wikipedia.org/w/index.php?title=Special:RecentChanges&feed=atom";
	public static final String MYWATCHLIST_LOGIN_URL = "http://en.wikipedia.org/w/api.php?action=login&lgname=";
	public static final String MYWATCHLIST_WITH_TOKEN_FEED_URL = "http://en.wikipedia.org/w/api.php?action=feedwatchlist&wlowner=";
	public static final String MYWATCHLIST_WITH_TOKEN_FEED_URL_PART_2 = "&wltoken=";
	public static final String MYWATCHLIST_WITH_TOKEN_FEED_URL_PART_3 = "&feedformat=atom";
	public static final String MYWATCHLIST_FEED_URL = "http://en.wikipedia.org/w/api.php?action=feedwatchlist&feedformat=atom";
	public static final String MY_CONTRIBUTUINS_URL = "http://en.wikipedia.org/w/api.php?action=feedcontributions&user=";
	public static final String MY_CONTRIBUTUINS_URL_FEED_FORMAT = "&feedformat=atom";

	public static final String ABOUT_APP_TEXT_LANGUAGE = "English";
	public static final String ABOUT_APP_TEXT_URL = "http://en.wikipedia.org";
	public static final String ABOUT_APP_VERSION = "1.0";

	// public static final String ABOUT_APP_TEXT = String
	// .format("<p>Wikitrack %s is an android application developed by <a href=\"http://saaranga.com/\">Saaranga Infotech LLP</a>. This application helps you to track the updates in <a href=\"%s\">%s Wikipedia.</a><br>&nbsp;</p><p>Version %s supports tracking of %s Wikipedia’s recent changes, User’s contributions and User’s watchlist. <br>You can login to get 'my contributions', and your watchlist notifications.<br><br>Preferences for the application can be changed by clicking on the \"Settings\" button, on the home screen of the app.<br><br>If you like this app kindly encourage the effort by giving a rating on Google play and by adding your comments.<br><br>The copyright of the application rests with Saaranga Infotech LLP. Please send in your suggestions, feedback or comments to apps@saaranga.com.<br><br>Text is available under the <a href=\"http://en.wikipedia.org/wiki/Wikipedia:Text_of_Creative_Commons_Attribution-ShareAlike_3.0_Unported_License\">Creative Commons Attribution-ShareAlike License</a>; additional terms may apply. See <a href=\"http://wikimediafoundation.org/wiki/Terms_of_use\">Terms of use</a> for details.<br><br>Wikipedia® is a registered trademark of the <a href=\"http://www.wikimediafoundation.org/\">Wikimedia Foundation, Inc.</a>, a non-profit organization.</p>",
	// ABOUT_APP_TEXT_LANGUAGE, ABOUT_APP_TEXT_URL,
	// ABOUT_APP_TEXT_LANGUAGE, ABOUT_APP_VERSION,
	// ABOUT_APP_TEXT_LANGUAGE);

	public static final String ABOUT_APP_TEXT = String
			.format("<p>Wikitrack %s is an android application developed by Saaranga Infotech LLP (http://saaranga.com). This application helps you to track the updates in %s Wikipedia(%s).<br>&nbsp;</p><p>Version %s supports tracking of %s Wikipedia’s recent changes, User’s contributions and User’s watchlist. <br>You can login to get 'my contributions', and your watchlist notifications.<br><br>Preferences for the application can be changed by clicking on the \"Settings\" button, on the home screen of the app.<br><br>If you like this app kindly encourage the effort by giving a rating on Google play and by adding your comments.<br><br>The copyright of the application rests with Saaranga Infotech LLP. Please send in your suggestions, feedback or comments to apps@saaranga.com.<br><br>Text is available under the Creative Commons Attribution-ShareAlike License (http://en.wikipedia.org/wiki/Wikipedia:Text_of_Creative_Commons_Attribution-ShareAlike_3.0_Unported_License); additional terms may apply. See Wikpedia Terms of use for details.<br><br>Wikipedia® is a registered trademark of the Wikimedia Foundation, Inc.</a>, a non-profit organization.</p>",
					ABOUT_APP_TEXT_LANGUAGE, ABOUT_APP_TEXT_LANGUAGE,
					ABOUT_APP_TEXT_URL, ABOUT_APP_VERSION,
					ABOUT_APP_TEXT_LANGUAGE);

	// fonts
	public static final String FONT_KEDAGE = "Kedage-n.ttf";
	public static final String FONT_LOHIT = "lohit_kn.ttf";
	public static final String FONT_DEFAULT = "default";

	/**
	 * Language specific strings end here
	 * 
	 */

	public static final String LINK_SAARANGA_FACEBOOK_PAGE = "https://www.facebook.com/pages/Saaranga-Infotech/176579575735262";
	public static final String LINK_SAARANGA_TWITTER_PAGE = "http://twitter.com/#%21/SaarangaTech";
	public static final String LINK_SAARANGA_WEBSITE = "http://www.saaranga.com";
	public static final String LINK_APPS_BY_SAARANGA = "market://search?q=pub:Saaranga Infotech";
	public static final String[] EMAIL_TO = { "mobile-dev@saaranga.com" };

	// constants for the feedtype
	public static final String RECENT_CHANGES = "recent_changes";
	public static final String MYWATCHLIST = "my_watchlist";
	public static final String MY_CONTRIBUTIONS = "my_contributions";

	// preferences constants
	public static final String PREF_FIX_FEED_COUNT = "set_database_itemcount";
	public static final String PREF_RC_FEED_COUNT = "feed_count_recent_changes";
	public static final String PREF_WL_FEED_COUNT = "feed_count_watch_list";
	public static final String PREF_MC_FEED_COUNT = "feed_count_mycontributions";
	public static final String PREF_ERASE_PRIVATE_DATA = "erase_private_data";
	public static final String PREF_SELECT_FONT = "select_font";
	public static final String PREF_CLEAR_CACHE = "erase_cache_data";
	public static final String PREF_DELETE_DATABASE = "delete_database";
	public static final String PREF_ABOUT_DEVELOPER = "about_developer";
	public static final String PREF_ABOUT_APPLICATION = "about_application";
	// Default values
	public static final String PREF_DEFUALT_RC_FEED_COUNT = "50";
	public static final String PREF_DEFUALT_MC_FEED_COUNT = "50";
	public static final String PREF_DEFUALT_WL_FEED_COUNT = "50";

	// MY_PREFS preference file
	public static final String USERNAME_PREFS = "MY_PREFS";
	public static final String LOGIN_PASSWORD = "PASSWORD";
	public static final String LOGIN_USERNAME = "USERNAME";
	public static final String MYCONTRIBUTIONS_USERNAME = "MYCONTRIBUTIONS_USERNAME";

	public static final String LINK_WL_TOKEN_DESCRIPTION = " http://en.wikipedia.org/wiki/Wikipedia:Syndication#Watchlist_feed_with_token";

	// time formater
	public static long yearDivisor = 31536000;
	public static long dayDivisor = 86400;
	public static long hourDivisor = 3600;
	public static long minDivisor = 60;
	public static long secDivisor = 1;

	public static Typeface getTypeface(Context c) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(c);
		String font = prefs.getString(PREF_SELECT_FONT, "nothing");
		mlog("font: " + font);

		if (font.equals(FONT_KEDAGE)) {
			return Typeface.createFromAsset(c.getAssets(), FONT_KEDAGE);
		} else if (font.equals(FONT_LOHIT)) {
			return Typeface.createFromAsset(c.getAssets(), FONT_LOHIT);
		} else {
			return null;
		}
	}

	public static String getFontName(Context c) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(c);
		String font = prefs.getString(PREF_SELECT_FONT, "nothing");
		mlog("font: " + font);
		return font;
	}

	public static int getFeedLimit(String feedType, Context c) {

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(c);
		if (feedType.equals(RECENT_CHANGES))
			return Integer.parseInt(prefs.getString(PREF_RC_FEED_COUNT,
					PREF_DEFUALT_RC_FEED_COUNT));
		else if (feedType.equals(MY_CONTRIBUTIONS))
			return Integer.parseInt(prefs.getString(PREF_MC_FEED_COUNT,
					PREF_DEFUALT_MC_FEED_COUNT));
		else if (feedType.equals(MYWATCHLIST))
			return Integer.parseInt(prefs.getString(PREF_WL_FEED_COUNT,
					PREF_DEFUALT_WL_FEED_COUNT));

		else
			return 0;
	}

	public static String getFontFamily(String font) {
		if (font.equals(FONT_KEDAGE)) {
			return "kedage";
		} else if (font.equals(FONT_LOHIT)) {
			return "\"Lohit Kannada\"";
		}
		return "none";
	}

	public static String formatDateStamp(String stampDate) {

		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.set(2007, 01, 10);
		calendar2.set(2007, 07, 01);

		return null;
	}

	private static String DEBUG_TAG = "Constants class";

	private static void mlog(String msg) {
		// Log.d(DEBUG_TAG, msg);
	}
}
