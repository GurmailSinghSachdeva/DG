package com.discount.coupons.discountgali.utility;

/**
 * Created by AND-02 on 02-02-2016.
 */
public class Syso {
    private static boolean isDebug = false;

    public static void print(String message){
        if(isDebug){
            System.out.println(message);
        }
    }
}
