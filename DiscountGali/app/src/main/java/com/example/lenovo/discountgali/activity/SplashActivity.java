package com.example.lenovo.discountgali.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.lenovo.discountgali.R;

/**
 * Created by Gurmail Singh on 1/21/2017.
 */

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000; /**< Splash screen timer*/
    private ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        ivLogo = (ImageView) findViewById(R.id.iv_logo);

        animateCategoriesStrip();
            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_TIME_OUT);

    }
    public void animateCategoriesStrip(){
        if(ivLogo!=null)
        {
            YoYo.with(Techniques.FlipInX)
                    .duration(SPLASH_TIME_OUT)
                    .playOn(ivLogo);
        }
    }
}
