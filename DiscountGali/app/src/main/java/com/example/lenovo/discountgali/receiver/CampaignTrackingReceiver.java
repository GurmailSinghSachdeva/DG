package com.example.lenovo.discountgali.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.TraceCompat;
import android.util.Log;

import com.example.lenovo.discountgali.DiscountGaliApplication;
import com.example.lenovo.discountgali.utility.Syso;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by amitsaini on 01/07/16.
 */
public class CampaignTrackingReceiver extends BroadcastReceiver {
    private String TAG = "COMPAIGN";

    @Override
    public void onReceive(Context context, Intent intent) {
        Syso.print("===onReceive====" + "====CampaignTrackingReceiver===");
//      "utm_source=testSource&utm_medium=testMedium&utm_term=testTerm&utm_content=testContent&utm_campaign=testCampaign"
//        Tracker mTracker = ((DiscountGaliApplication) ((Activity)context).getApplication()).getDefaultTracker();

        if (intent.hasExtra("referrer")) {
            Syso.print(TAG + "-----referrer is found-----");
            Bundle extras = intent.getExtras();
            String referrerString = null;
            try {
                referrerString = URLDecoder.decode(extras.getString("referrer"),"UTF-8");
                Syso.print(TAG + extras.getString("referrer"));
//                mTracker.setScreenName("Home");
//                mTracker.send(new HitBuilders.ScreenViewBuilder()
//                        .setCampaignParamsFromUrl("abcd")
//                        .build());

            } catch (UnsupportedEncodingException e) {
                referrerString = extras.getString("referrer");
                e.printStackTrace();
            }
            Log.v(TAG,referrerString);
//            SharedPreference.saveCompaignTrackingUrl(context,referrerString);
//             Next line uses my helper function to parse a query (eg "a=b&c=d") into key-value pairs
//            HashMap<String, String> getParams = null;
//            try {
//                getParams = (HashMap<String, String>) getHashMapFromQuery(referrerString);
//
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
        } else {
            Log.v(TAG, "-----referrer is not found-----");
//            SharedPreference.saveCompaignTrackingUrl(context,"");
        }

    }

    public Map<String, String> getHashMapFromQuery(String query)
            throws UnsupportedEncodingException {

        Map<String, String> query_pairs = new LinkedHashMap<String, String>();

        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
                    URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            Log.v("==Key=" + pair.substring(0, idx), "" + pair.substring(idx + 1));
        }
        return query_pairs;
    }

    public void printData(Bundle bundle) {
        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            Log.d("-----Data------", String.format("%s %s (%s)", key,
                    value.toString(), value.getClass().getName()));
        }
    }

}


// Terminal command to broadcast  INSTALL_REFERRER
//./adb shell am broadcast -a com.android.vending.INSTALL_REFERRER -n roastmobileapp.com.roastproject/.receiver.CampaignTrackingReceiver --es referrer "utm_source=testSource\&utm_medium=testMedium\&utm_term=testTerm\&utm_content=444\&utm_campaign=testCampaign"