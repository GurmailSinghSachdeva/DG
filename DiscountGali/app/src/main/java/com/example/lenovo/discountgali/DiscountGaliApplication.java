package com.example.lenovo.discountgali;


import android.app.Application;

import com.example.lenovo.discountgali.utility.Utils;
import com.example.lenovo.discountgali.utils.ImageLoaderUtils;

/**
 * Created by lenovo on 10-01-2017.
 */

public class DiscountGaliApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoaderUtils.initImageLoader(getApplicationContext());
        Utils.setApplicationContext(getApplicationContext());
        //SharedPreference.saveIsAppUpdateApiCalled(false);
    }
}
