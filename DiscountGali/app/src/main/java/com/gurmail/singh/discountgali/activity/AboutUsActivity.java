package com.gurmail.singh.discountgali.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.gurmail.singh.discountgali.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class AboutUsActivity extends BaseActivity {
    private Toolbar mToolBar;

    private View progressConatiner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        progressConatiner = findViewById(R.id.progress_bar_container);
        progressConatiner.setVisibility(View.GONE);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setUpToolbar(R.drawable.signup_back_icon);

        WebView webView = (WebView) findViewById(R.id.web_view);
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        settings.setJavaScriptEnabled(true);




        webView.loadUrl("file:///android_asset/aboutus.html");
        //webView.loadDataWithBaseURL("file:///android_asset/mobile_privacy_policy.html", null, "text/html", "utf-8", null);
        //webView.loadData(loadAssetTextAsString(this, "mobile_privacy_policy.html"), "text/html; charset=utf-8", "utf-8");
        //webView.loadDataWithBaseURL(null, loadAssetTextAsString(this, "mobile_privacy_policy.html"), "text/html", "UTF-8", null);
    }

    public static String loadAssetTextAsString(Context context, String name) {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = context.getAssets().open(name);
            in = new BufferedReader(new InputStreamReader(is, "utf-8"));

            String str;
            boolean isFirst = true;
            while ( (str = in.readLine()) != null ) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }
            return buf.toString();
        } catch (Exception e) {
            Log.e("", "Error opening asset " + name);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e("", "Error closing asset " + name);
                }
            }
        }

        return "";
    }

    private void setUpToolbar(int navId) {
        mToolBar.setNavigationIcon(navId);
        setSupportActionBar(mToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
