package com.gurmail.singh.discountgali.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.gurmail.singh.discountgali.R;

/**
 * Created by AND-02 on 07-10-2016.
 */
public class ForceUpdateActivity extends Activity{
    private TextView textView;

    public static Intent getStartIntent(Context context){
        Intent intent = new Intent(context,ForceUpdateActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.force_update_layout);
        textView = (TextView) findViewById(R.id.tv_update);
        setListener();
        setFinishOnTouchOutside(false);
    }

    void setListener() {
        textView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            final String appPackageName = getPackageName(); // package name of the app
                                            try {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                            } catch (android.content.ActivityNotFoundException anfe) {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                            }
                                        }
                                    }
        );
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
