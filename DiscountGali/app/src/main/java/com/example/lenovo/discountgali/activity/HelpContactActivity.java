package com.example.lenovo.discountgali.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.lenovo.discountgali.R;

public class HelpContactActivity extends BaseActivity {

    private WebView mWebView;
    private Toolbar mToolbar;
    private String title = "";

    void inItUi() {
        mWebView = (WebView) findViewById(R.id.wv_help_contact);
        mToolbar = (Toolbar) findViewById(R.id.tb_favourites);
    }

    void setListener() {

    }

    void setData() {
        String screenName = getIntent().getStringExtra("screenName");
        switch (screenName){
            case "contact_us":
                title = "Contact us";
                mWebView.loadUrl("https://securedcommunications.com/contactus.asp");
                break;
            case "help":
                title = "Help/FAQ";
                mWebView.loadUrl("https://securedcommunications.com/faqs.asp");
                break;
        }
        /*if (getIntent().getBooleanExtra("isHelp", false)) {
            title = "Contact us";
            mWebView.loadUrl("https://securedcommunications.com/contactus.asp");
        } else {
            title = "Help/FAQ";
            mWebView.loadUrl("https://securedcommunications.com/faqs.asp");
        }*/
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setSavePassword(true);
        mWebView.getSettings().setSaveFormData(true);
        mWebView.setWebViewClient(new MyWebViewClient());
        setUpToolbar(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return true;
        //return super.onOptionsItemSelected(item);
    }

    private void setUpToolbar(String Title) {
        ((TextView) mToolbar.findViewById(R.id.toolbar_title)).setText(Title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_contact);

    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            dismissProgressDialog();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
//            showProgressDialog();
        }
    }
}
