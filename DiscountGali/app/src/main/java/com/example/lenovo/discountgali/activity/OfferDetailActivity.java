package com.example.lenovo.discountgali.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.model.TopOffers;
import com.example.lenovo.discountgali.utils.Constants;
import com.example.lenovo.discountgali.utils.ImageLoaderUtils;

public class OfferDetailActivity extends Activity implements View.OnClickListener {

    Toolbar mToolBar;

    private ImageView ivClose;
    TextView tv_title, tv_description, tv_date, tv_brandName, tv_couponCode, tv_grab_coupon, tv_long_press;
    private ImageView iv_logo;
    private TopOffers topOffer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_detail);

        parseArguments();
        initUi();
//        setUpToolbar(topOffer.BrandName, R.drawable.signup_back_icon);
        setData();
        setListener();
    }

    private void setListener() {
        tv_grab_coupon.setOnClickListener(this);
        ivClose.setOnClickListener(this);
    }

    private void setData() {
        ImageLoaderUtils.loadImage(topOffer.OnlineDeal_Logo, iv_logo);
        tv_brandName.setText(topOffer.BrandName);
        tv_title.setText(topOffer.OnlineDeal_Offer);
        tv_description.setText(topOffer.OnlineDeal_OfferDescription);
        tv_date.setText(topOffer.OnlineDeal_EndDate);
        tv_couponCode.setText(topOffer.OnlineDeal_CouponCode);
        if(!TextUtils.isEmpty(topOffer.OnlineDeal_CouponCode)){
            tv_grab_coupon.setVisibility(View.GONE);
            tv_long_press.setVisibility(View.GONE);
        }
        tv_couponCode.setVisibility(View.GONE);
        tv_long_press.setVisibility(View.GONE);
    }

    private void parseArguments() {
        topOffer = (TopOffers) getIntent().getSerializableExtra("offer");
    }

    private void initUi() {
        tv_brandName = (TextView) findViewById(R.id.tv_brandName);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_description = (TextView) findViewById(R.id.tv_description);
        tv_long_press = (TextView) findViewById(R.id.tv_long_press);
        tv_date = (TextView) findViewById(R.id.tv_validity);
        tv_couponCode = (TextView) findViewById(R.id.tv_coupon_code);
        tv_grab_coupon = (TextView) findViewById(R.id.tv_grab_coupon);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        iv_logo = (ImageView) findViewById(R.id.iv_offerLogo);
        ivClose = (ImageView) findViewById(R.id.iv_close);
    }
//    private void setUpToolbar(String title, int navId) {
////        TextView tv = (TextView) mToolBar.findViewById(R.id.toolbar_title);
////        tv.setText(title);
//        mToolBar.setNavigationIcon(navId);
//        setSupportActionBar(mToolBar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_grab_coupon:
                tv_couponCode.setVisibility(View.VISIBLE);
                tv_long_press.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_close:
                setResult(Constants.ACTIVITYFORRESULT.REQUESTOFFERDETAIL);
                finish();
                break;
        }
    }
}
