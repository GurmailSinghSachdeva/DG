package com.example.lenovo.discountgali.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.model.DealUrlModel;
import com.example.lenovo.discountgali.model.ServerResponse;
import com.example.lenovo.discountgali.model.TopOffers;
import com.example.lenovo.discountgali.network.Code;
import com.example.lenovo.discountgali.network.HttpRequestHandler;
import com.example.lenovo.discountgali.network.api.ApiCall;
import com.example.lenovo.discountgali.network.apicall.GetDealUrlApiCall;
import com.example.lenovo.discountgali.network.apicall.GetLocalDeals;
import com.example.lenovo.discountgali.utility.AlertUtils;
import com.example.lenovo.discountgali.utility.Utils;
import com.example.lenovo.discountgali.utils.Constants;
import com.example.lenovo.discountgali.utils.DialogUtils;
import com.example.lenovo.discountgali.utils.ImageLoaderUtils;

import java.util.ArrayList;

public class OfferDetailActivity extends Activity implements View.OnClickListener {

    Toolbar mToolBar;

    private ImageView ivClose;
    private TextView tv_title, tv_description, tv_date, tv_brandName, tv_couponCode, tv_grab_coupon, tv_long_press, tv_description_fixed;
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
//        if(TextUtils.isEmpty(topOffer.OnlineDeal_CouponCode)){
//            tv_grab_coupon.setVisibility(View.GONE);
//            tv_long_press.setVisibility(View.GONE);
//        }
        tv_couponCode.setVisibility(View.GONE);
        tv_long_press.setVisibility(View.GONE);

        tv_description_fixed.setText(topOffer.OnlineDeal_Type == Constants.typeOnline?"DESCRIPTION":"ADDRESS");
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
        tv_description_fixed = (TextView) findViewById(R.id.tv_description_fixed);
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
//                tv_couponCode.setVisibility(View.VISIBLE);
//                tv_long_press.setVisibility(View.VISIBLE);
                showPhoneNumberDialog();
                break;
            case R.id.iv_close:
                setResult(Constants.ACTIVITYFORRESULT.REQUESTOFFERDETAIL);
                finish();
                break;
        }
    }

    private void showPhoneNumberDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.enterPhoneNumbet);
        builder.setCancelable(true);
        View view = getLayoutInflater().inflate(R.layout.dialog_edit_text,null);
        final EditText editText = (EditText) view.findViewById(R.id.et_input);
        editText.setHint("Ex - 1234567890");
        builder.setView(view);
        builder.setPositiveButton(getString(R.string.tv_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utils.hideSoftKeyBoard(OfferDetailActivity.this);

                String editTextValue = editText.getText().toString();
                if(!TextUtils.isEmpty(editTextValue) && editTextValue.length() == 10){

                    getDealUrlAPiCAll(editTextValue.toString().trim());

                    dialog.cancel();
                }
                else {
                    AlertUtils.showToast(OfferDetailActivity.this, R.string.alertWrongMobileNo);

                    showPhoneNumberDialog();
                }
                }
        });
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utils.hideSoftKeyBoard(OfferDetailActivity.this);
                dialog.cancel();
            }
        });
        builder.create().show();

    }

    private void getDealUrlAPiCAll(String mobileNo) {
        try {

            final ProgressDialog progressDialog = DialogUtils.getProgressDialog(OfferDetailActivity.this);

            progressDialog.show();
            final GetDealUrlApiCall apiCall = new GetDealUrlApiCall(mobileNo, topOffer.OnlineDealId, topOffer.OnlineDeal_Type);

            HttpRequestHandler.getInstance(this).executeRequest(apiCall, new ApiCall.OnApiCallCompleteListener() {
                @Override
                public void onComplete(Exception e) {
                    DialogUtils.hideProgressDialog(progressDialog);
                    if (e == null) {
                        ServerResponse<DealUrlModel> serverResponse = (ServerResponse<DealUrlModel>) apiCall.getResult();
                        if (serverResponse != null) {
                            switch (serverResponse.baseModel.MessageCode) {
                                case Code.SUCCESS_MESSAGE_CODE:
                                   ArrayList<DealUrlModel> dealUtlModels = new ArrayList<DealUrlModel>();
                                    dealUtlModels = serverResponse.data;
                                    if(topOffer.OnlineDeal_Type == Constants.typeOffline){
                                        AlertUtils.showToast(OfferDetailActivity.this, R.string.succes_send_message);
                                        finish();
                                    }
                                    if(!TextUtils.isEmpty(dealUtlModels.get(0).url))
                                    {
                                        Utils.showDealUrlActivity(OfferDetailActivity.this, topOffer.OnlineDeal_CouponCode,
                                                dealUtlModels.get(0).url);
                                    }
                                    else {
                                        AlertUtils.showToast(OfferDetailActivity.this, R.string.alertNoUrl);
                                    }
                                    break;
                            }
                        }

                    } else {
                        Utils.handleError(e, OfferDetailActivity.this);
                    }
                }
            }, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
