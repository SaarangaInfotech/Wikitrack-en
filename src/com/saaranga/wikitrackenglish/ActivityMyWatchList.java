package com.saaranga.wikitrackenglish;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.saaranga.wikitrack.utilities.Constants;

/**
 * activity class - <br>
 * PURPOSE: If user name and passwords are not stored, displays the form to
 * enter the username and password <br>
 * 
 * It actually calls the class {@link LoginHandler} to login to the user account
 * and post request to get my watchlist
 * 
 * TODO option for advanced query settings can be provided in future releases
 * 
 * @author supreeth
 * @version 1.0 30-05-2012
 * 
 *          Copyright Saaranga Infotech
 */
public class ActivityMyWatchList extends Activity implements OnClickListener {

	private static final String tag = "My watchlist activity";

	private String loginUsername, loginPassword, tokenUsername, tokenID;

	private SharedPreferences settings;

	private String DEBUG_TAG = "Activity my Watchlist";
	private ImageView home;

	private TextView tokenUsernameLabel;

	private EditText tokenUsernameBox;

	private TextView tokenTokenLabel;

	private EditText tokenTokenBox;

	private Button tokenDescriptionButton;

	private Button tokenGetFeedButton;

	private Button tokenShowTokenFieldsButton;

	private TextView loginUsernameLabel;

	private TextView loginPasswordLabel;

	private EditText loginUsernameBox;

	private EditText loginTokenBox;

	private CheckBox loginSavePswd;

	private Button loginGetFeedButton;

	private Button loginShowLoginFieldsButton;

	private EditText loginPswdBox;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(tag, "here 0");
		setContentView(R.layout.activity_my_watchlist);

		setUpUI();

	}

	/**
	 * create the views for the activity
	 */
	private void setUpUI() {
		home = (ImageView) findViewById(R.id.wl_home);

		tokenUsernameLabel = (TextView) findViewById(R.id.wl_token_username_label);
		tokenTokenLabel = (TextView) findViewById(R.id.wl_token_token_label);

		tokenUsernameBox = (EditText) findViewById(R.id.wl_token_username_box);
		tokenTokenBox = (EditText) findViewById(R.id.wl_token_token_box);

		tokenDescriptionButton = (Button) findViewById(R.id.wl_token_description_btn);
		tokenGetFeedButton = (Button) findViewById(R.id.wl_token_getfeed_btn);
		tokenShowTokenFieldsButton = (Button) findViewById(R.id.wl_token_show_token_fields_btn);

		loginUsernameLabel = (TextView) findViewById(R.id.wl_login_username_label);
		loginPasswordLabel = (TextView) findViewById(R.id.wl_login_pswd_label);

		loginUsernameBox = (EditText) findViewById(R.id.wl_login_username_box);
		loginPswdBox = (EditText) findViewById(R.id.wl_login_pswd_box);

		loginSavePswd = (CheckBox) findViewById(R.id.wl_login_save_pswd);
		loginGetFeedButton = (Button) findViewById(R.id.wl_login_getfeed_btn);
		loginShowLoginFieldsButton = (Button) findViewById(R.id.wl_login_show_token_fields_btn);

		// set visibility
		hideLoginFields();
		hideTokenFields();

		home.setOnClickListener(this);

		loginShowLoginFieldsButton.setOnClickListener(this);
		loginGetFeedButton.setOnClickListener(this);
		tokenShowTokenFieldsButton.setOnClickListener(this);
		tokenGetFeedButton.setOnClickListener(this);
		tokenDescriptionButton.setOnClickListener(this);

	}

	/**
	 * Username and paswords for login fields are stored, set them to the views
	 * 
	 */
	private void setUsernameAndPasswords() {
		settings = getSharedPreferences(Constants.USERNAME_PREFS, MODE_PRIVATE);

		if (settings.contains(Constants.LOGIN_PASSWORD)) {
			// username passwords are stored - show the username in the username
			// field and mask password
			Log.i(tag, "password present");
			loginUsernameBox.setText(settings.getString(
					Constants.LOGIN_USERNAME, "none"));
			loginPswdBox.setText("******");
			// hide the save password option
			loginSavePswd.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		Log.i(tag, "button clicked");
		// close the keyboard if open
		// closeSoftKeyBoard();

		switch (v.getId()) {

		case R.id.wl_home:
			Intent home = new Intent(ActivityMyWatchList.this,
					ActivityShowCase.class);
			this.startActivity(home);
			finish();
			break;

		case R.id.wl_login_getfeed_btn:

			closeSoftKeyBoard();
			
			// get username and password
			getUsernameAndPasswd();

			// check username and password for validity - basic authentication
			boolean isValid = basicAuthentication(loginUsername, loginPassword);
			mlog("isuname and pswd valid? " + isValid);

			if (!isValid) {
				Toast.makeText(this,
						getString(R.string.en_enter_valid_uname_pswd),
						Toast.LENGTH_LONG).show();
			} else {
				if (isNetworkAvailable()) {
					// if save is set, save username and password
					if (loginSavePswd.isChecked())
						saveUsernameAndPswd();
					// run the background task
					new getMyWatchlistTask(loginUsername, loginPassword)
							.execute("");
				} else {
					Toast.makeText(this,
							getString(R.string.error_msg_internet_unavailable),
							Toast.LENGTH_LONG).show();

				}
			}

			break;

		case R.id.wl_token_show_token_fields_btn:
			// set the visibility of the token area to be visible and the
			// visibility of this button to gone
			showTokenFields();
			hideLoginFields();

			break;

		case R.id.wl_login_show_token_fields_btn:
			// set the visibility of the login area to be visible and the
			// visibility of this button to gone
			showLoginFields();
			setUsernameAndPasswords();
			hideTokenFields();
			break;

		case R.id.wl_token_getfeed_btn:

			//close the keyboard if present
			closeSoftKeyBoard();
			
			// get the feed from the wl token provided and save the username and
			// token and increment the number of times the username used
			tokenUsername = tokenTokenBox.getText().toString();
			tokenID = tokenTokenBox.getText().toString();

			if (basicAuthentication(tokenUsername, tokenID)) {
				if (isNetworkAvailable()) {
					// run the background task
					new getMyWatchlistFromToken(tokenUsername, tokenID)
							.execute("");
				} else {
					Toast.makeText(this,
							getString(R.string.error_msg_internet_unavailable),
							Toast.LENGTH_LONG).show();

				}

			} else {
				Toast.makeText(this,
						getString(R.string.en_enter_valid_uname_token_id),
						Toast.LENGTH_LONG).show();
			}

			break;
			
		case R.id.wl_token_description_btn:
			Intent wltokenDescriptionIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(Constants.LINK_WL_TOKEN_DESCRIPTION));
			wltokenDescriptionIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			this.startActivity(wltokenDescriptionIntent);
			
			break;

		}
	}

	/**
	 * Hide the show login fields button and show other fields
	 * 
	 */
	private void showLoginFields() {
		loginUsernameLabel.setVisibility(View.VISIBLE);
		loginPasswordLabel.setVisibility(View.VISIBLE);
		loginUsernameBox.setVisibility(View.VISIBLE);
		loginPswdBox.setVisibility(View.VISIBLE);
		loginSavePswd.setVisibility(View.VISIBLE);
		loginGetFeedButton.setVisibility(View.VISIBLE);

		loginShowLoginFieldsButton.setVisibility(View.GONE);
	}

	/**
	 * Show the show login fields button and hide other fields
	 * 
	 * 
	 */
	private void hideLoginFields() {
		loginUsernameLabel.setVisibility(View.GONE);
		loginPasswordLabel.setVisibility(View.GONE);
		loginUsernameBox.setVisibility(View.GONE);
		loginPswdBox.setVisibility(View.GONE);
		loginSavePswd.setVisibility(View.GONE);
		loginGetFeedButton.setVisibility(View.GONE);

		loginShowLoginFieldsButton.setVisibility(View.VISIBLE);
	}

	private void showTokenFields() {
		tokenUsernameLabel.setVisibility(View.VISIBLE);
		tokenTokenLabel.setVisibility(View.VISIBLE);
		tokenUsernameBox.setVisibility(View.VISIBLE);
		tokenTokenBox.setVisibility(View.VISIBLE);
		tokenDescriptionButton.setVisibility(View.VISIBLE);
		tokenGetFeedButton.setVisibility(View.VISIBLE);

		tokenShowTokenFieldsButton.setVisibility(View.GONE);
	}

	private void hideTokenFields() {
		tokenUsernameLabel.setVisibility(View.GONE);
		tokenTokenLabel.setVisibility(View.GONE);
		tokenUsernameBox.setVisibility(View.GONE);
		tokenTokenBox.setVisibility(View.GONE);
		tokenDescriptionButton.setVisibility(View.GONE);
		tokenGetFeedButton.setVisibility(View.GONE);

		tokenShowTokenFieldsButton.setVisibility(View.VISIBLE);
	}

	/**
	 * Returns true if internet is available
	 * 
	 * @return
	 */
	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

	/**
	 * Close the softkeyboard if open.
	 */
	private void closeSoftKeyBoard() {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * Save the username and password to the sharedpreferences
	 * 
	 */
	private void saveUsernameAndPswd() {
		try {

			// save password
			Log.i(tag, "password saved");
			SharedPreferences.Editor editor = settings.edit();

			editor.putString(Constants.LOGIN_PASSWORD, loginPassword);
			editor.putString(Constants.LOGIN_USERNAME, loginUsername);
			editor.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Do a basic validation of the text entered. Since we use specialized
	 * editText views, no need of authenticating further
	 * 
	 * 
	 * @param uname
	 * @param pswd
	 * @return
	 */
	private boolean basicAuthentication(String uname, String pswd) {
		if (uname.equals("") && pswd.equals(""))
			return false;

		if ((uname.equals("none") && pswd.equals("*****")))
			return false;
		return true;
	}

	/**
	 * sets the username and passeord to the variables if present in shared
	 * preferences. If not present sets username to "none" and password "****"
	 * 
	 */
	private void getUsernameAndPasswd() {
		// get the username and password from preferences if saved else from the
		// edit boxes
		settings = getSharedPreferences(Constants.USERNAME_PREFS, MODE_PRIVATE);

		if (settings.contains(Constants.LOGIN_PASSWORD)) {
			// password is present in the preferences
			loginUsername = settings
					.getString(Constants.LOGIN_USERNAME, "none");
			loginPassword = settings
					.getString(Constants.LOGIN_PASSWORD, "****");
		} else {
			// not present get from the edit boxes
			loginUsername = loginUsernameBox.getText().toString();
			loginPassword = loginPswdBox.getText().toString();
		}
	}

	/**
	 * 
	 * Asynctask to get the mywatchlist data and store it in database Using the
	 * user credentials, LoginHandler class is used to login to user account,
	 * the String obtained is parsed and returned by Login Handler
	 * 
	 * @author supreeth
	 * 
	 */
	private class getMyWatchlistTask extends AsyncTask<String, Void, Void> {

		ProgressDialog dialog;
		private RSSFeed rssFeed;
		private LoginHandler loginHandler;
		private GetRssFeedClass getRssFeedClass;
		private FeedDBAdapter feedDBAdapter = new FeedDBAdapter(
				ActivityMyWatchList.this);
		private boolean isError = false, isFeedEmpty = false;
		private String usernameString, passwordString;

		List<RSSItem> _itemList;
		private String errorMessage;

		public getMyWatchlistTask(String usernameString, String passwordString) {
			this.usernameString = usernameString;
			this.passwordString = passwordString;
		}

		@Override
		protected void onPreExecute() {

			dialog = new ProgressDialog(ActivityMyWatchList.this);
			dialog.setCancelable(false);
			dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			dialog.setIcon(android.R.drawable.ic_dialog_info);
			dialog.setTitle("Fetching feed");
			dialog.setMessage("Loading");
			dialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {

			getRssFeedClass = new GetRssFeedClass();
			// get the LoginHandler object
			loginHandler = new LoginHandler();

			// feed string can be null if there is error in login
			String feedString = null;
			try {
				feedString = loginHandler.loginToWikiAccount(usernameString,
						passwordString);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (LoginHandler.isError || feedString == null) {
				mlog("isError: " + isError + " feedString : " + feedString);

				// error in login
				isError = true;
				errorMessage = loginHandler.getErrorMessage();
			} else {
				// parse the feed data
				rssFeed = getRssFeedClass.parseFeed(feedString);

				if (rssFeed != null) {
					Log.d(tag, "RssFeed is not null");
					_itemList = rssFeed.getAllItems();
				} else {
					Log.d(tag, "RssFeed is null");
				}

				if (_itemList.size() == 0) {
					// empty feed
					isFeedEmpty = true;
				} else {
					// insert to database
					insertItemsToDatabse();
				}
				// release objects
				rssFeed = null;
				_itemList.clear();
			}

			return null;
		}

		/**
		 * Insert thefeed items to Database
		 */
		private void insertItemsToDatabse() {
			feedDBAdapter.updateDatabaseTable(Constants.MYWATCHLIST);
			feedDBAdapter.open();
			for (int i = 0; i < _itemList.size(); i++) {
				feedDBAdapter.insertFeedItem(Constants.MYWATCHLIST, _itemList
						.get(i).getTitle(), _itemList.get(i).getLink(),
						_itemList.get(i).getSummary(), _itemList.get(i)
								.getUpdated(), _itemList.get(i).getAuthor(), i);
			}
			feedDBAdapter.close();

			// trim to the database length - always be true
			feedDBAdapter.trimDatabaseToFeedItemLimit(Constants.MYWATCHLIST);
		}

		@Override
		protected void onPostExecute(Void result) {
			dialog.dismiss();
			if (isError) {
				handleError();
			} else if (isFeedEmpty) {
				Toast.makeText(ActivityMyWatchList.this,
						"No items in the feed", Toast.LENGTH_LONG).show();

			} else {
				Intent mywatchlistListIntent = new Intent(
						ActivityMyWatchList.this, ActivityLists.class);
				mywatchlistListIntent.putExtra(Constants.FEEDTYPE,
						Constants.MYWATCHLIST);
				ActivityMyWatchList.this
						.startActivity(mywatchlistListIntent);
				ActivityMyWatchList.this.finish();
			}
		}

		/**
		 * Handle error in fetching my watchlist feed
		 */
		private void handleError() {
			if (!isNetworkAvailable()) {

				Toast.makeText(ActivityMyWatchList.this,
						"Unable to connect. Check internet connectivity",
						Toast.LENGTH_LONG).show();

			} else {
				// network is available but the username is not valid - or feed
				// returned is empty (occurs in case of my watchlist - if the
				// number
				// of items is zero
				Toast.makeText(ActivityMyWatchList.this,
						"Error: " + errorMessage, Toast.LENGTH_LONG).show();
			}

		}

		/**
		 * check if network is available
		 * 
		 * @return true is available
		 */
		private boolean isNetworkAvailable() {
			ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetworkInfo = connectivityManager
					.getActiveNetworkInfo();
			return activeNetworkInfo != null;
		}

	}

	/**
	 * Async task that fetches the feed for my contributions in the background
	 * 
	 * @author supreeth
	 * 
	 */
	private class getMyWatchlistFromToken extends AsyncTask<String, Void, Void> {

		private ProgressDialog dialog;
		private RSSFeed rssFeed = new RSSFeed();
		private GetRssFeedClass getRssFeedClass;
		private FeedDBAdapter feedDBAdapter;
		private boolean isError = false;
		private String usernameString, tokenString;

		private List<RSSItem> _itemList;
		private boolean isFeedNull;
		private boolean isEmptyFeed;

		public getMyWatchlistFromToken(String usernameString, String tokenString) {
			this.usernameString = usernameString;
			this.tokenString = tokenString;
		}

		/**
		 * A progress dialog is displayed till the feed is fetched from internet
		 * 
		 */
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(ActivityMyWatchList.this);
			dialog.setCancelable(false);
			dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			dialog.setIcon(android.R.drawable.ic_dialog_info);
			dialog.setTitle("Fetching feed");
			dialog.setMessage("Loading");
			dialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				getRssFeedClass = new GetRssFeedClass();

				// prepare the rss feed url to fetch my contributions feed
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(Constants.MYWATCHLIST_WITH_TOKEN_FEED_URL);
				stringBuilder.append(this.usernameString);
				stringBuilder
						.append(Constants.MYWATCHLIST_WITH_TOKEN_FEED_URL_PART_2);
				stringBuilder.append(this.tokenString);
				stringBuilder
						.append(Constants.MYWATCHLIST_WITH_TOKEN_FEED_URL_PART_3);

				rssFeed = getRssFeedClass.getFeed(stringBuilder.toString());
				_itemList = new ArrayList<RSSItem>();

				if (rssFeed != null) { // get the feed items to a list item
					Log.d(tag, "RssFeed is not null");
					_itemList = rssFeed.getAllItems();

				} else {
					Log.d(tag, "RssFeed is null");
				}

				mlog("count: " + rssFeed.getAllItems().size());
			} catch (Exception e1) {
				e1.printStackTrace();
				isError = true;
			}

			// Handle the errors in fetching the feed here

			if (rssFeed == null) {
				// rss feed is null check if the internet connection is not
				// there or the username is not valid
				mlog("rss feed is null");
				isFeedNull = true;

			} else {

				if (rssFeed.getAllItems().size() == 0) {
					isEmptyFeed = true; // feed is returned but no items present
										// - incase of invalid username
				}

				feedDBAdapter = new FeedDBAdapter(ActivityMyWatchList.this);
				try {

					feedDBAdapter
							.updateDatabaseTable(Constants.MYWATCHLIST);
					feedDBAdapter.open();
					for (int i = 0; i < _itemList.size(); i++) {
						feedDBAdapter.insertFeedItem(
								Constants.MYWATCHLIST, _itemList.get(i)
										.getTitle(),
								_itemList.get(i).getLink(), _itemList.get(i)
										.getSummary(), _itemList.get(i)
										.getUpdated(), _itemList.get(i)
										.getAuthor(), i);
					}
					feedDBAdapter.close();

					// trim to the database length - always be true
					feedDBAdapter
							.trimDatabaseToFeedItemLimit(Constants.MYWATCHLIST);
				} catch (SQLException e) {
					e.printStackTrace();
					isError = true;
				}
			}
			// release rssFeed item
			rssFeed = null;

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (isError) {
				Log.e(tag, "Error in fetching feed");
			}
			dialog.dismiss();

			if (isFeedNull || isEmptyFeed) {
				// if either the feed is null or url returned empty feed, handle
				// them
				handleNullFeed();

			} else {
				Intent mywatchlistListIntent = new Intent(
						ActivityMyWatchList.this, ActivityLists.class);
				mywatchlistListIntent.putExtra(Constants.FEEDTYPE,
						Constants.MYWATCHLIST);
				ActivityMyWatchList.this
.startActivity(mywatchlistListIntent);
				ActivityMyWatchList.this.finish();
			}

		}

		/**
		 * handle the null and empty feed cases
		 * 
		 */
		private void handleNullFeed() {

			if (!isNetworkAvailable() && isFeedNull) {

				Toast.makeText(ActivityMyWatchList.this,
						"Unable to connect. Check internet connectivity",
						Toast.LENGTH_LONG).show();

			} else if (isEmptyFeed) {
				// network is available but the username is not valid - or feed
				// returned is empty (occurs in case of my watchlist - if the
				// number
				// of items is zero
				Toast.makeText(ActivityMyWatchList.this,
						"It seems username doesn't exist or no feed returned",
						Toast.LENGTH_LONG).show();
			}

		}
	}

	private void mlog(String msg) {
		// Log.d(DEBUG_TAG, msg);
	}

}
