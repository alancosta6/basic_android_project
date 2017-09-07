package corebase;

import android.app.Application;

import Util.CrashTrackerUtil;
import Util.TrackerUtil;

/**
 * Created by alancosta on 9/7/17.
 */

public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();


        CrashTrackerUtil.startTraker(this);
        TrackerUtil.getInstance().startTrackers(this);


    }
}
