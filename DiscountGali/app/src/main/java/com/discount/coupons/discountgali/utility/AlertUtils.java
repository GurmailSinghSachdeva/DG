package com.discount.coupons.discountgali.utility;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by AND-02 on 19-07-2016.
 */
public class AlertUtils {

    public static void showToast(Context context,int res){
        if(context!=null)
            Toast.makeText(context,res,Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context,String res){
        if(context!=null)
          Toast.makeText(context,res,Toast.LENGTH_SHORT).show();
    }
}
