package com.discount.coupons.discountgali.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.discount.coupons.discountgali.DiscountGaliApplication;
import com.discount.coupons.discountgali.utility.Utils;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by administrator on 29/04/16.
 */


public class BaseActivity extends AppCompatActivity {
    private final String TAG = ">>>>>BaseActivity";
    static Tracker mTracker;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTracker = ((DiscountGaliApplication) getApplication()).getDefaultTracker();

//        if (!SharedPreference.isAppUpdateApiCalled())
//            AppUpdateImplementation.executeTask(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendScreenName(this.getClass().getSimpleName());
        Utils.setCurContext(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void sendScreenName(String screenName) {
        if (mTracker != null) {
            mTracker.setScreenName(screenName);
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }
}
