package com.saaranga.wikitrackenglish;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.saaranga.wikitrack.utilities.ClearApplicationCache;
import com.saaranga.wikitrack.utilities.Constants;

/**
 * Preference activity class <br>
 * PURPOSE: provides interface for the default application preferences.
 * 
 * <br>
 * This has the following preferences
 * <ul>
 * <li>
 * Set the feed count to be stored locally in the app database</li>
 * <li>Change the application font</li>
 * <li>Delete the private data</li>
 * <li>Delete database</li>
 * <li>Clear web cache</li>
 * <li>About developer</li>
 * <li>About the application</li>
 * </ul>
 * 
 * @author supreeth
 * @version 1.0
 * 
 *          Copyright Saaranga Infotech
 */
public class ActivityApplicationPreferenceList extends PreferenceActivity
		implements OnPreferenceChangeListener, OnPreferenceClickListener {

	private String DEBUG_TAG = "Activity application preference list";
	private SharedPreferences customPreference;
	private Builder builder;

	private Preference clearCache;
	private Preference deleteAppDatabase;
	private final int dialogIcon = R.drawable.ic_menu_info_details;
	private boolean deleteRC = false, deleteMC = false, deleteWL = false;
	private Preference aboutDev;
	private Preference aboutApp;
	private Preference eraseData;
	// private ListPreference appFont;
	private CheckBoxPreference fixFeedCount;
	private ListPreference feedWl;
	private ListPreference feedMc;
	private ListPreference feedRc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mlog("on create 1");
		addPreferencesFromResource(R.xml.custom_preferences);
		mlog("on create 2");

		// get the preference
		customPreference = PreferenceManager.getDefaultSharedPreferences(this);

		// Fix feed count check box pref
		fixFeedCount = (CheckBoxPreference) getPreferenceScreen()
				.findPreference(Constants.PREF_FIX_FEED_COUNT);
		fixFeedCount.setOnPreferenceChangeListener(this);

		// Categories
		feedRc = (ListPreference) getPreferenceScreen().findPreference(
				Constants.PREF_RC_FEED_COUNT);
		feedMc = (ListPreference) getPreferenceScreen().findPreference(
				Constants.PREF_MC_FEED_COUNT);
		feedWl = (ListPreference) getPreferenceScreen().findPreference(
				Constants.PREF_WL_FEED_COUNT);

		// application font
		// appFont = (ListPreference) getPreferenceScreen().findPreference(
		// Constants.PREF_SELECT_FONT);
		// appFont.setOnPreferenceChangeListener(this);

		eraseData = (Preference) getPreferenceScreen().findPreference(
				Constants.PREF_ERASE_PRIVATE_DATA);
		eraseData.setOnPreferenceClickListener(this);
		// clear cache
		clearCache = (Preference) getPreferenceScreen().findPreference(
				Constants.PREF_CLEAR_CACHE);
		clearCache.setOnPreferenceClickListener(this);

		// Delete database
		deleteAppDatabase = (Preference) getPreferenceScreen().findPreference(
				Constants.PREF_DELETE_DATABASE);
		deleteAppDatabase.setOnPreferenceClickListener(this);

		// about developer
		aboutDev = (Preference) getPreferenceScreen().findPreference(
				Constants.PREF_ABOUT_DEVELOPER);
		aboutDev.setOnPreferenceClickListener(this);

		// about application
		aboutApp = (Preference) getPreferenceScreen().findPreference(
				Constants.PREF_ABOUT_APPLICATION);

		aboutApp.setOnPreferenceClickListener(this);

		setStateOfRadioButtons();

	}

	/**
	 * Set the state of the app version listing items based on the saved
	 * preference - they are not handled by default
	 */
	private void setStateOfRadioButtons() {
		// Set the state of the app version listing items based on the saved
		// preference - they are not handled by default
		// if (customPreference.contains(Constants.PREF_SELECT_FONT)) {
		// applicationFont = customPreference.getString(
		// Constants.PREF_SELECT_FONT, "");
		// // App version preference is already set
		// mlog("app encoding preference is present");
		// if (applicationFont.equals(Constants.FONT_KEDAGE)) {
		// appFont.setValue(Constants.FONT_KEDAGE);
		// } else if (applicationFont.equals(Constants.FONT_LOHIT)) {
		// appFont.setValue(Constants.FONT_LOHIT);
		// } else {
		// appFont.setValue(Constants.FONT_DEFAULT);
		// }
		// }
		if (customPreference.contains(Constants.PREF_FIX_FEED_COUNT)) {

			feedRc.setValue(customPreference.getString(
					Constants.PREF_RC_FEED_COUNT,
					Constants.PREF_DEFUALT_RC_FEED_COUNT));
			feedMc.setValue(customPreference.getString(
					Constants.PREF_MC_FEED_COUNT,
					Constants.PREF_DEFUALT_MC_FEED_COUNT));
			feedWl.setValue(customPreference.getString(
					Constants.PREF_WL_FEED_COUNT,
					Constants.PREF_DEFUALT_WL_FEED_COUNT));
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		mlog("in onresume");

	}

	@Override
	protected void onPause() {
		mlog("in onpause");
		super.onResume();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			setResult(RESULT_OK);
			finish();
			onBackPressed();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void mlog(String msg) {
		// Log.d(DEBUG_TAG, msg);
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		if (preference.getKey().equals(Constants.PREF_FIX_FEED_COUNT)) {

			// If the user disables the feed fix option, set all the feed items
			// to their default values . Here the condition is load defaults
			// when the fixfeed count is checked but it should be otherwise. The
			// problem is fixFeedCount preference is set true in this case
			// when it is unchecked
			if (fixFeedCount.isChecked()) {
				// If the manual preferences are not checked apply the default
				// preferences - handling this manually because the defaults are
				// not applied automatically when the view is diabled. The
				// classic case of view vs value :)
				// check what are the status berfore changing
				SharedPreferences.Editor editor = customPreference.edit();

				editor.putString(Constants.PREF_RC_FEED_COUNT,
						Constants.PREF_DEFUALT_RC_FEED_COUNT);
				editor.putString(Constants.PREF_MC_FEED_COUNT,
						Constants.PREF_DEFUALT_MC_FEED_COUNT);
				editor.putString(Constants.PREF_WL_FEED_COUNT,
						Constants.PREF_DEFUALT_WL_FEED_COUNT);

				// comitt the changes
				editor.commit();

				// Refresh the state of the radio buttobs
				setStateOfRadioButtons();
			}

			if (preference.getKey().equals(Constants.PREF_RC_FEED_COUNT)) {
				mlog("Trimming RC");
				FeedDBAdapter feedDBAdapter = new FeedDBAdapter(
						ActivityApplicationPreferenceList.this);
				feedDBAdapter
						.trimDatabaseToFeedItemLimit(Constants.RECENT_CHANGES);
			}

			if (preference.getKey().equals(Constants.PREF_MC_FEED_COUNT)) {
				mlog("Trimming MC ");
				FeedDBAdapter feedDBAdapter = new FeedDBAdapter(
						ActivityApplicationPreferenceList.this);
				feedDBAdapter
						.trimDatabaseToFeedItemLimit(Constants.MY_CONTRIBUTIONS);
			}

			if (preference.getKey().equals(Constants.PREF_WL_FEED_COUNT)) {
				mlog("Trimming WL");
				FeedDBAdapter feedDBAdapter = new FeedDBAdapter(
						ActivityApplicationPreferenceList.this);
				feedDBAdapter
						.trimDatabaseToFeedItemLimit(Constants.MYWATCHLIST);
			}
		}

		return true;
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		mlog("preferene clicked");
		if (preference.getKey().equals(Constants.PREF_ERASE_PRIVATE_DATA)) {
			mlog("erase data");
			erasePrivateDataDialog();
		}

		if (preference.getKey().equals(Constants.PREF_CLEAR_CACHE)) {
			mlog("erase cache");
			clearChacheDialog();
		}

		if (preference.getKey().equals(Constants.PREF_DELETE_DATABASE)) {
			mlog("delete database");
			deleteAppDatabaseDialog();
		}

		if (preference.getKey().equals(Constants.PREF_ABOUT_DEVELOPER)) {
			mlog("about dev");
			showAboutDevDialog();
		}
		if (preference.getKey().equals(Constants.PREF_ABOUT_APPLICATION)) {
			mlog("about app");
			showAboudAppDialog();
		}

		return false;
	}

	private void showAboudAppDialog() {
		final SpannableString s = new SpannableString(
				Html.fromHtml(Constants.ABOUT_APP_TEXT));
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		llp.setMargins(10, 10, 10, 10);
		// added a TextView
		final TextView message = new TextView(this);
		message.setText(s);
		message.setLayoutParams(llp);
		message.setAutoLinkMask(RESULT_OK);
		message.setTextSize(18);
		message.setMovementMethod(LinkMovementMethod.getInstance());
		Linkify.addLinks(s, Linkify.WEB_URLS);

		builder = new AlertDialog.Builder(this);
		builder.setTitle("About application").setIcon(dialogIcon)
				.setView(message);
		builder.create().show();
	}

	private void showAboutDevDialog() {

		final SpannableString s = new SpannableString(
				Html.fromHtml(getString(R.string.en_about_developer)));
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		llp.setMargins(10, 10, 10, 10);

		// added a TextView
		final TextView message = new TextView(this);
		message.setText(s);
		message.setLayoutParams(llp);
		message.setAutoLinkMask(RESULT_OK);
		message.setTextSize(18);
		message.setMovementMethod(LinkMovementMethod.getInstance());
		Linkify.addLinks(s, Linkify.WEB_URLS);

		builder = new AlertDialog.Builder(this);
		builder.setTitle("About developer").setIcon(dialogIcon)
				.setView(message);
		builder.create().show();
	}

	private void deleteAppDatabaseDialog() {
		builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.en_pref_deletedb_title)
				.setIcon(dialogIcon)
				// .setMessage(
				// "ನಿಮ್ಮ ಅಪ್ಲಿಕೇಶನ್ ಡೇಟಾಬೇಸ್ ಅಳಿಸಲಾಗುವುದು. ನಿಮ್ಮ ನೆಚ್ಚಿನ ಪುಟಗಳು, ಇತರೆ ಆಯ್ಕೆಗಳು ಸಹ ಅಳಿಸಲ್ಪಡುವವು. ಎಚ್ಚರಿಕೆಯಿಂದ ಮುಂದುವರೆಯಿರಿ.")
				.setMultiChoiceItems(
						new CharSequence[] {
								getString(R.string.en_recent_changes),
								getString(R.string.en_my_contributions),
								getString(R.string.en_my_watchlist) },
						new boolean[] { false, false, false },
						new DialogInterface.OnMultiChoiceClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which, boolean isChecked) {
								mlog("item: " + which);
								// set the flags to delete the database tables
								switch (which) {
								case 0:
									deleteRC = isChecked;
									break;
								case 1:
									deleteMC = isChecked;
									break;
								case 2:
									deleteWL = isChecked;
									break;

								}
							}
						})
				.setPositiveButton(R.string.en_delete,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// delete database
								FeedDBAdapter dbAdapter = new FeedDBAdapter(
										ActivityApplicationPreferenceList.this);
								if (deleteRC) {
									dbAdapter
											.updateDatabaseTable(Constants.RECENT_CHANGES);
								}
								if (deleteMC)
									dbAdapter
											.updateDatabaseTable(Constants.MY_CONTRIBUTIONS);
								if (deleteWL)
									dbAdapter
											.updateDatabaseTable(Constants.MYWATCHLIST);

								// disable the preference after deleting the
								// preference
								clearCache.setEnabled(false);

								Toast.makeText(
										ActivityApplicationPreferenceList.this,
										"Database deleted", Toast.LENGTH_SHORT)
										.show();
							}
						})
				.setNegativeButton(R.string.en_return,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// do nothing
								return;
							}
						});

		builder.create().show();
	}

	private void clearChacheDialog() {
		builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.en_clear_cache)
				.setIcon(dialogIcon)
				.setMessage(R.string.en_clear_cache_message)
				.setPositiveButton(R.string.en_clear,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// clear all webview cache
								ClearApplicationCache.clearCache(
										ActivityApplicationPreferenceList.this,
										0); // 0 means deletes all the cache

								// disable the preference after deleting the
								// preference
								clearCache.setEnabled(false);

								Toast.makeText(
										ActivityApplicationPreferenceList.this,
										"Web cache cleared", Toast.LENGTH_SHORT)
										.show();

							}
						})
				.setNegativeButton(R.string.en_return,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// do nothing
								return;
							}
						});

		builder.show();
	}

	private void erasePrivateDataDialog() {
		builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.en_erase_private_data_title)
				.setIcon(dialogIcon)
				.setMessage(R.string.en_erase_private_data_message)
				.setPositiveButton(R.string.en_erase,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// this preference seems tobe different from the
								// application preference
								SharedPreferences settings = getSharedPreferences(
										"MY_PREFS", MODE_PRIVATE);
								SharedPreferences.Editor editor = settings
										.edit();
								editor.clear();
								editor.commit();

								// disable the preference after deleting the
								// preference
								eraseData.setEnabled(false);
							}
						})
				.setNegativeButton(R.string.en_return,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// do nothing
								return;
							}
						});

		builder.show();
	}

}
