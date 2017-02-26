package com.example.lenovo.discountgali.activity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.model.DealUrlModel;
import com.example.lenovo.discountgali.utility.AlertUtils;
import com.example.lenovo.discountgali.utility.Syso;
import com.example.lenovo.discountgali.utility.Utils;
import com.example.lenovo.discountgali.utils.Constants;
import com.example.lenovo.discountgali.utils.DialogUtils;

public class DealUrlActivity extends BaseActivity implements View.OnLongClickListener, View.OnClickListener {

    private View progress_bar_layout;
    private View coupon_layout;
    private TextView tvCouponCode;
//    private WebView webView;
    private TextView tvVisistStore;
    private String url;
    private String coupon;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_url);

        parseArguments();
        initUi();
        setUpToolbar(R.drawable.signup_back_icon);
        seListener();
        setData();

    }

    private void setData() {

        setCouponConatiner();
//        setWebView();
    }

//    private void setWebView() {
//
//        WebSettings settings = webView.getSettings();
//        settings.setDefaultTextEncodingName("utf-8");
//        settings.setUserAgentString("Android");
//        settings.setJavaScriptEnabled(true);
//        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        webView.loadUrl(url);
//        webView.setWebViewClient(new MyWebViewClient());
//        webView.setWebChromeClient(new WebChromeClient());
//    }
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

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_visit_store:
                Utils.openUrl(this, url);
                break;
        }
    }

    private class MyWebViewClient extends WebViewClient {
//        final ProgressDialog progressDialog = DialogUtils.getProgressDialog(DealUrlActivity.this);


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progress_bar_layout.setVisibility(View.GONE);
//            DialogUtils.hideProgressDialog(progressDialog);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            progress_bar_layout.setVisibility(View.VISIBLE);
//            progressDialog.show();
        }
    }
    private void setCouponConatiner() {
        if(!TextUtils.isEmpty(coupon)){
            tvCouponCode.setText(coupon);
        }
        else {
            coupon_layout.setVisibility(View.GONE);
        }
    }


    private void seListener() {
        coupon_layout.setOnLongClickListener(this);
        tvVisistStore.setOnClickListener(this);
    }

    private void initUi() {
        progress_bar_layout = findViewById(R.id.progress_bar_container);
        coupon_layout = findViewById(R.id.coupon_conatiner);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        tvVisistStore = (TextView) findViewById(R.id.tv_visit_store);

        tvCouponCode = (TextView) findViewById(R.id.tv_coupon);
//        webView = (WebView) findViewById(R.id.web_view);
    }

    private void parseArguments() {
        try{
            url = getIntent().getStringExtra(Constants.REDIRECT_URL);
            Syso.print("DEALURL " + url);
            coupon = getIntent().getStringExtra(Constants.COUPON_CODE);
            Syso.print("DEALURL COUPON" + coupon);

        }
        catch (Exception e){
            e.printStackTrace();
            url = "";
            coupon = "";
        }
    }

    @Override
    public boolean onLongClick(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(coupon, coupon);
        clipboard.setPrimaryClip(clip);
        AlertUtils.showToast(DealUrlActivity.this, R.string.dealCopied);
        return false;
    }
}
