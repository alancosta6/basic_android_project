package appreview;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateUtils;
import android.util.Log;

/**
 * Created by alancosta on 8/24/15.
 */
public class AppReviewManager {

	private static final String TAG = "AppReviewManager";
	private static final String OPEN_RATING_DIALOG = "Rating Dialog";
	private static final String PLAY_STORE_URI = "market://details?id=";

	private static final int ONE = 1;
	private static final int ZERO = 0;

	public static final String ZOOM_RATING_SHARED_PREFS_NAME = "zoom.rating.app";
	public static final String PREF_DATE_FIRST_LAUNCH = "zoom.date.first.launch";
	public static final String PREF_LAUNCH_COUNT = "zoom.launch.count";
	public static final String PREF_INTERACTIONS_COUNT = "zoom.interactions.count";
	public static final String PREF_DO_NOT_SHOW_AGAIN = "zoom.rating.do.not.show.again";
	public static final String PREF_RETRY_AFTER_USER_NOT_SURE = "zoom.rating.retry.after.user.not.sure";

	private static int minDaysUntilPrompt = 0;
	private static int minInteractionsUntilPrompt = 0;
	private static int minLaunchesUntilPrompt = 0;
	
	private static int ZOOM_RATING_LAUNCHES_RULE = 0;
	private static int ZOOM_RATING_INTERACTIONS_RULE = 0;

	private static final AppReviewManager ratingManagerSingleton = new AppReviewManager();
	private static SharedPreferences mPreferences;
	private static Context mContext;

	public static AppReviewManager getInstance(Context context) {
		mContext = context;
		mPreferences = mContext.getSharedPreferences(ZOOM_RATING_SHARED_PREFS_NAME, ZERO);
		minDaysUntilPrompt = mPreferences.getInt(PREF_RETRY_AFTER_USER_NOT_SURE, ZERO);
		minLaunchesUntilPrompt = ZOOM_RATING_LAUNCHES_RULE;
		minInteractionsUntilPrompt = ZOOM_RATING_INTERACTIONS_RULE;

		return ratingManagerSingleton;
	}


	public AppReviewManager setMinDaysAfterUserNotSure(int minDaysAfterUserNotSure) {

		if (mPreferences != null) {
			SharedPreferences.Editor sharedPrefsEditor = mPreferences.edit();
			sharedPrefsEditor.putInt(PREF_RETRY_AFTER_USER_NOT_SURE, minDaysAfterUserNotSure);
			sharedPrefsEditor.apply();
		}
		return this;
	}

	public void onDontAskAgain() {
		if (mPreferences != null) {
			SharedPreferences.Editor sharedPrefsEditor = mPreferences.edit();
			sharedPrefsEditor.putBoolean(PREF_DO_NOT_SHOW_AGAIN, true);
			sharedPrefsEditor.apply();
		}
	}

	public void onDontKnowYet(int minDaysAfterUserNotSure) {

		// Business rule if the user choses this option show the dialog in 30
		// days from current timestamp
		if (mPreferences != null) 
		{
			SharedPreferences.Editor sharedPrefsEditor = mPreferences.edit();
			sharedPrefsEditor.putInt(PREF_RETRY_AFTER_USER_NOT_SURE, minDaysAfterUserNotSure);
			sharedPrefsEditor.apply();

			sharedPrefsEditor.putLong(PREF_DATE_FIRST_LAUNCH, System.currentTimeMillis());
			sharedPrefsEditor.apply();
		}
	}

	public void onOpenPlayStore() {
		try {
			Uri playStoreUri = Uri.parse(PLAY_STORE_URI + mContext.getPackageName());
			Intent playStore = new Intent(Intent.ACTION_VIEW, playStoreUri);
			mContext.startActivity(playStore);
		} catch (ActivityNotFoundException e) {
			Log.e(TAG, "Play Store is not installed on the device");
		}
		onDontAskAgain();

	}

	public AppReviewManager addInteraction() {

		if (mPreferences != null) {
			SharedPreferences.Editor sharedPrefsEditor = mPreferences.edit();

			// Increment interactions count
			int interactions_count = mPreferences.getInt(PREF_INTERACTIONS_COUNT, ZERO) + ONE;
			sharedPrefsEditor.putInt(PREF_INTERACTIONS_COUNT, interactions_count);

			sharedPrefsEditor.commit();

			Log.d(TAG, "Interactions Incremented");
		}
		return this;

	}
	
	
	public AppReviewManager addLaunch() {

		if (mPreferences != null) {
			SharedPreferences.Editor sharedPrefsEditor = mPreferences.edit();

			// Increment interactions count
			int launch_count = mPreferences.getInt(PREF_LAUNCH_COUNT, ZERO) + ONE;
			sharedPrefsEditor.putInt(PREF_LAUNCH_COUNT, launch_count);

			sharedPrefsEditor.apply();

			Log.d(TAG, "Interactions Incremented");
		}
		return this;

	}

	public boolean start(FragmentManager fragmentManager) {
		Log.d(TAG, "Start AppReviewManager");

		try {
			
			if (mPreferences != null) {
				if (doNotShowAgain(mPreferences)) {
					Log.d(TAG, "App Rating Will not appear due to User settings");
				} else {

					SharedPreferences.Editor sharedPrefsEditor = mPreferences.edit();

					
					int launch_count = mPreferences.getInt(PREF_LAUNCH_COUNT, ZERO);
					
					// Get date of first launch
					Long date_firstLaunch = mPreferences.getLong(PREF_DATE_FIRST_LAUNCH, ZERO);
					// If a date was never set create one
					if (date_firstLaunch == ZERO) {
						date_firstLaunch = System.currentTimeMillis();
						sharedPrefsEditor.putLong(PREF_DATE_FIRST_LAUNCH, date_firstLaunch);
					}

					int interactions_until_prompt = mPreferences.getInt(PREF_INTERACTIONS_COUNT, ZERO);

					sharedPrefsEditor.apply();

					return openRatingDialog(launch_count, interactions_until_prompt, date_firstLaunch, fragmentManager);

				}
			}
			
		} catch (Exception e) {
			Log.d(TAG, "Error fragment was destroyed" + e );
		}

		
		return false;

	}

	public void reset() {
		if (mContext != null) {
			mContext.getSharedPreferences(ZOOM_RATING_SHARED_PREFS_NAME, 0).edit().clear().commit();
			Log.d(TAG, "Cleared App Rating shared preferences.");
		} else {
			Log.e(TAG, "Clear App Rating cant execute, mContext was null.");
		}

	}

	private boolean doNotShowAgain(SharedPreferences mPreferences) {
		return mPreferences.getBoolean(PREF_DO_NOT_SHOW_AGAIN, false);
	}

	private boolean openRatingDialog(int launch_count, int interactions_until_prompt, long date_firstLaunch,
			FragmentManager fragmentManager) {
		Log.d(TAG, "minInteractionsUntilPrompt = " + minInteractionsUntilPrompt);
		Log.d(TAG, "interactions_until_prompt = " + interactions_until_prompt);
		
		
		Log.d(TAG, "launch_count = " + launch_count);
		Log.d(TAG, "minLaunchesUntilPrompt = " + minLaunchesUntilPrompt);

		if (launch_count >= minLaunchesUntilPrompt) {
			long currentDate = System.currentTimeMillis();
			long scheduleLaunchDate = date_firstLaunch + (minDaysUntilPrompt * DateUtils.DAY_IN_MILLIS);
			Log.d(TAG, "currentDate = " + currentDate);
			Log.d(TAG, "scheduleLaunchDate = " + scheduleLaunchDate);
			if (currentDate >= scheduleLaunchDate && interactions_until_prompt >= minInteractionsUntilPrompt) {
				
				
				DialogFragment mFragment = (DialogFragment) fragmentManager.findFragmentByTag(OPEN_RATING_DIALOG);
				if (mFragment == null) {
					
					AppReviewDialog ratingDialog = new AppReviewDialog();
					ratingDialog.setCancelable(true);
					ratingDialog.show(fragmentManager, OPEN_RATING_DIALOG);
					return true;
				}

			}
		}
		
		return false;

	}

}
