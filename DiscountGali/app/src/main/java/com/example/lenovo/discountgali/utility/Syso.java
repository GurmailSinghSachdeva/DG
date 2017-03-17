package com.example.lenovo.discountgali.utility;

/**
 * Created by AND-02 on 02-02-2016.
 */
public class Syso {
    private static boolean isDebug = true;

    public static void print(String message){
        if(isDebug){
            System.out.println(message);
        }
    }
}
