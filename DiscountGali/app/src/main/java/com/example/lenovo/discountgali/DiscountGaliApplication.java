package com.example.lenovo.discountgali;


import android.app.Application;

import com.example.lenovo.discountgali.utility.Utils;
import com.example.lenovo.discountgali.utils.ImageLoaderUtils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by lenovo on 10-01-2017.
 */

public class DiscountGaliApplication extends Application {
    Tracker mTracker;

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoaderUtils.initImageLoader(getApplicationContext());
        Utils.setApplicationContext(getApplicationContext());
        //SharedPreference.saveIsAppUpdateApiCalled(false);
    }

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.app_tracker);
        }
        return mTracker;
    }
}
