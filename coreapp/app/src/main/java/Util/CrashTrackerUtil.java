package Util;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by alancosta on 9/7/17.
 */

public class CrashTrackerUtil {


    public static void startTraker(Application application) {

        Fabric.with(application, new Crashlytics());
    }


    public static void trackException(Exception ex) {

        Crashlytics.logException(ex);
    }



}
