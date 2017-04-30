package com.discount.coupons.discountgali;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.discount.coupons.discountgali.model.BaseModel;
import com.discount.coupons.discountgali.network.Code;
import com.discount.coupons.discountgali.network.HttpRequestHandler;
import com.discount.coupons.discountgali.network.api.ApiCall;
import com.discount.coupons.discountgali.network.apicall.InsertCampaignUrlApiCAll;
import com.discount.coupons.discountgali.utility.Syso;
import com.discount.coupons.discountgali.utility.Utils;
import com.discount.coupons.discountgali.utils.Constants;
import com.discount.coupons.discountgali.utils.SharedPreference;
import com.google.android.gms.analytics.CampaignTrackingReceiver;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lenovo on 29-04-2017.
 */
public class MyRefReceiver extends BroadcastReceiver {
    private String TAG = "COMPAIGN";


    @Override
    public void onReceive(Context context, Intent intent) {
        Syso.print("===onReceive====" + "====CampaignTrackingReceiver===");
//      "utm_source=testSource&utm_medium=testMedium&utm_term=testTerm&utm_content=testContent&utm_campaign=testCampaign"

        Log.e("onreceive ", "broadcast");

        if (intent.hasExtra("referrer")) {
            Syso.print(TAG + "-----referrer is found-----");
            Bundle extras = intent.getExtras();
            String referrerString = null;
            try {
                referrerString = URLDecoder.decode(extras.getString("referrer"),"UTF-8");
                Syso.print(TAG + extras.getString("referrer"));

                SharedPreference.saveCompaignTrackingUrl(context,referrerString);
                    saveReferrerOnline(context, referrerString, Utils.getDeviceID());
                new CampaignTrackingReceiver().onReceive(context, intent);

            } catch (UnsupportedEncodingException e) {
                referrerString = extras.getString("referrer");
                e.printStackTrace();
            }
            Log.v(TAG,referrerString);
        } else {
            Log.v(TAG, "-----referrer is not found-----");
            SharedPreference.saveCompaignTrackingUrl(context,"");
        }

    }

    private void saveReferrerOnline(Context context, String referrerString, String deviceID) {

        try{
            final InsertCampaignUrlApiCAll apiCall;
            apiCall = new InsertCampaignUrlApiCAll(referrerString, deviceID);
            HttpRequestHandler.getInstance(context).executeRequest(apiCall, new ApiCall.OnApiCallCompleteListener() {
                @Override
                public void onComplete(Exception e) {

                    if (e == null) {
                        BaseModel baseModel = (BaseModel) apiCall.getResult();
                        if(baseModel!=null)
                        {
                            switch (baseModel.MessageCode)
                            {
                                case Code.SUCCESS_MESSAGE_CODE:

//                                        if(apiCall.getSuccessStatus() == Constants.otp_sent_success){
                                    SharedPreference.saveMyCampaignStatus(Constants.otp_sent_success);
//                                        }
                                    break;

                            }
                        }

                    } else {

                    }
                }
            }, false);
        }catch (Exception e)
        {
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
