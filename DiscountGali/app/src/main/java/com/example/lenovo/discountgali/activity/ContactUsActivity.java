package com.example.lenovo.discountgali.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.utils.DialogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ContactUsActivity extends BaseActivity {
    private Toolbar mToolBar;
    private View progress_bar_container;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        progress_bar_container = findViewById(R.id.progress_bar_container);
        parseArguments();
        setUpToolbar(R.drawable.signup_back_icon);

        WebView webView = (WebView) findViewById(R.id.web_view);
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
//        webView.setInitialScale(1);
        settings.setUserAgentString("Android");
        settings.setJavaScriptEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setAppCacheEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        settings.setDomStorageEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setUseWideViewPort(true);
        settings.setSaveFormData(true);
        settings.setSavePassword(true);

//        webView.loadUrl("http://discountgali.com/Contactus.aspx");
        webView.loadUrl(url);
        webView.setWebViewClient(new MyWebViewClient());

//        webView.loadUrl("file:///android_asset/Contactus.html");
        //webView.loadDataWithBaseURL("file:///android_asset/mobile_privacy_policy.html", null, "text/html", "utf-8", null);
        //webView.loadData(loadAssetTextAsString(this, "mobile_privacy_policy.html"), "text/html; charset=utf-8", "utf-8");
        //webView.loadDataWithBaseURL(null, loadAssetTextAsString(this, "mobile_privacy_policy.html"), "text/html", "UTF-8", null);
    }

    private void parseArguments() {
        url = getIntent().getStringExtra("url");
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

    private class MyWebViewClient extends WebViewClient {
//        final ProgressDialog progressDialog = DialogUtils.getProgressDialog(ContactUsActivity.this);

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progress_bar_container.setVisibility(View.GONE);
            super.onPageFinished(view, url);

//            DialogUtils.hideProgressDialog(progressDialog);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            progress_bar_container.setVisibility(View.VISIBLE);
//            progressDialog.show();
        }
    }

}
