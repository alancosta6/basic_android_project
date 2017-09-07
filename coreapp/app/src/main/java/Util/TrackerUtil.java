package Util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;


/**
 * Created by alancosta on 9/7/17.
 */

public class TrackerUtil {


    private enum contentType {

        SCREEN,
        EVENT,

    }


    private static TrackerUtil instance = null;

    private static FirebaseAnalytics mFirebaseAnalytics;


    public static TrackerUtil getInstance() {
        if(instance == null) {
            instance = new TrackerUtil();
        }
        return instance;
    }

    public void startTrackers(Context context) {

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

    }


    public void trackScreen(Activity activity, String screenName) {

        trackScreenFirebase(activity, screenName);

    }



    public void trackEvent(Context context, String screenName, String eventLabel) {

        trackEventFirebase(context, screenName, eventLabel);

    }


    private void trackScreenFirebase(Activity activity, String screenName) {

        if(mFirebaseAnalytics == null) {

            if(activity != null) {
                startTrackers(activity);
            } else {
                CrashTrackerUtil.trackException(new NullPointerException("FIREBASE COULD NOT TRACK SCREEN: " + screenName));
                return;
            }
        }

        mFirebaseAnalytics.setCurrentScreen(activity, screenName, null /* class override */);
    }

    private void trackEventFirebase(Context context, String screenName, String event) {

        if(mFirebaseAnalytics == null) {

            if(context != null) {
                startTrackers(context);
            } else {
                CrashTrackerUtil.trackException(new NullPointerException("FIREBASE COULD NOT TRACK SCREEN: " + screenName));
            }
        }

        Bundle bundle = new Bundle();
        bundle.putString("screen", screenName);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, event);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, contentType.EVENT.name());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

    }



}
