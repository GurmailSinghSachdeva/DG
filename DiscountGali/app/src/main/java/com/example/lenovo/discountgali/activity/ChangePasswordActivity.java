package com.example.lenovo.discountgali.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.network.Code;
import com.example.lenovo.discountgali.network.HttpRequestHandler;
import com.example.lenovo.discountgali.network.api.ApiCall;
import com.example.lenovo.discountgali.network.apicall.GetRecentMessageApiCall;
import com.example.lenovo.discountgali.utility.AlertUtils;
import com.example.lenovo.discountgali.utility.Syso;
import com.example.lenovo.discountgali.utility.Utils;

/**
 * Created by AND-02 on 09-08-2016.
 */
public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEtCurrentPassword; /**<Used for current password */
    private EditText mEtNewPassword; /**<Used for New password */
    private EditText mEtConfirmPassword; /**<Used for Confirm password */
    private TextView mTvPasswordStrength; /**<Used for Password strength*/
    private View mViewProgressBar,mViewParentProgressBar; /**<View for Progress bar */
    private Button mBtnSubmit; /**< Used fro submit button*/
    private String mCurrentPassword,mNewPassword,mConfirmPassword; /**<Used to hold the edit text data in string variables  */
    private int parentProgressBarWidth;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        callChangePasswordApi("kjbjk");
    }


    void inItUi() {
//        mEtCurrentPassword = (EditText) findViewById(R.id.et_current_password);
//        mEtNewPassword = (EditText) findViewById(R.id.et_new_password);
//        mEtConfirmPassword = (EditText) findViewById(R.id.et_confirm_password);
//        mTvPasswordStrength = (TextView) findViewById(R.id.tv_password_strength);
//        mViewProgressBar = (View) findViewById(R.id.view_progress_bar);
//        mViewParentProgressBar = (View) findViewById(R.id.view_progress_bar_base);
//        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        if(toolbar!=null) {
//            setSupportActionBar(toolbar);
//            toolbar.setNavigationIcon(R.drawable.ic_back);
//        }
//        ((TextView) findViewById(R.id.header_title)).setText(getString(R.string.tv_change_password));
    }


    void setListener() {
//        mBtnSubmit.setOnClickListener(ChangePasswordActivity.this);
//        mEtNewPassword.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                setProgressBarStrength(Utils.checkPasswordStrength(s.toString()));
//                if(s.length() == 0){
//                    mViewProgressBar.setBackgroundColor(ContextCompat.getColor(ChangePasswordActivity.this,R.color.black));
//
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
//                    mViewProgressBar.setLayoutParams(params);
//
//                    mTvPasswordStrength.setText(getString(R.string.title_password_strength)+" "+"Weak");
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {}
//        });
    }


    void setData() {
//        mTvPasswordStrength.setText(getString(R.string.title_password_strength)+" "+"Weak");
//        //GET progress bar parent width
//        parentProgressBarWidth = mViewParentProgressBar.getWidth();
//        Syso.print("parentProgressBarWidth  "+ parentProgressBarWidth);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                Utils.hideSoftKeyBoard(this);
//                finish();
//                break;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        mCurrentPassword = mEtCurrentPassword.getText().toString().trim();
        mNewPassword = mEtNewPassword.getText().toString().trim();
        mConfirmPassword = mEtConfirmPassword.getText().toString().trim();
        switch (view.getId()){
            case R.id.btn_submit :
//                if(validatePasswordFields() && checkInternet())
//                    AesUtils.encrypt(ChangePasswordActivity.this, mCurrentPassword, UserPreference.getInstance(this).getStringField(UserPreference.FIELD_EMAIL), new AesInterface() {
//                        @Override
//                        public void onResult(String result) {
//                            callChangePasswordApi(result);
//                        }
//                    });
                break;
        }
    }

    private boolean validatePasswordFields(){
        if((mCurrentPassword.length() == 0) && (mNewPassword.length() == 0) && (mConfirmPassword.length() == 0)) {
            AlertUtils.showToast(ChangePasswordActivity.this, R.string.alert_password_fields_are_empty);
            return false;
        }

        if (mCurrentPassword.length() > 1) {
            if(mNewPassword.length() == 0){
                AlertUtils.showToast(ChangePasswordActivity.this, R.string.alert_new_password_fields_is_empty);
                return false;
            }
            if (mNewPassword.length() > 6) {
                if(mConfirmPassword.length() == 0){
                    AlertUtils.showToast(ChangePasswordActivity.this, R.string.alert_confirm_password_fields_is_empty);
                    return false;
                }
                if (mConfirmPassword.length() > 6) {
                    if(mCurrentPassword.equals(mNewPassword)){
                        AlertUtils.showToast(ChangePasswordActivity.this, R.string.alert_old_and_new_password_match);
                        return false;
                    }else if(!mNewPassword.equals(mConfirmPassword)){
                        AlertUtils.showToast(ChangePasswordActivity.this, R.string.alert_password_not_match_re_enter);
                        return false;
                    }else{
                        return true;
                    }
                } else {
                    AlertUtils.showToast(ChangePasswordActivity.this, R.string.alert_password_length);
                    return false;
                }
            } else {
                AlertUtils.showToast(ChangePasswordActivity.this, R.string.alert_password_length);
                return false;
            }
        } else {
            AlertUtils.showToast(ChangePasswordActivity.this, R.string.alert_password_length);
            return false;
        }
    }

    private void showDialogForPasswordLength() {
        new AlertDialog.Builder(ChangePasswordActivity.this)
                .setTitle("")
                .setMessage(getString(R.string.alert_password_length))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.tv_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create().show();
    }

    private void callChangePasswordApi(String aesPassword){
//        showProgressDialog();
        //TODO: change parameters
        final GetRecentMessageApiCall changePasswordApiCall = new GetRecentMessageApiCall();
        HttpRequestHandler.getInstance(ChangePasswordActivity.this).executeRequest(changePasswordApiCall, new ApiCall.OnApiCallCompleteListener() {
            @Override
            public void onComplete(Exception e) {
//                dismissProgressDialog();
                if (e == null) {

//                    Syso.print("RESPONSE " + changePasswordApiCall.getResult());
//                    ServerResponse serverResponse = (ServerResponse) changePasswordApiCall.getResult();
//                    switch (serverResponse.getCode()){
//                        case Code.CHANGED_PASSWORD_SUCCESS:
//                            finish();
//                            AlertUtils.showToast(ChangePasswordActivity.this, serverResponse.getMessage());
//                            break;
//                        default:
//                            AlertUtils.showToast(ChangePasswordActivity.this, !TextUtils.isEmpty(serverResponse.getMessage()) ? serverResponse.getMessage() : getString(R.string.alert_invalid_response));
//                            break;
//                    }
                } else {
                    AlertUtils.showToast(ChangePasswordActivity.this, R.string.alert_invalid_response);
                }
            }
        }, false);
    }

    private void setProgressBarStrength(String str){
        int strengthValue = 0;
        int color = R.color.black;
        String strengthName = "Weak";
        switch(str){
            case "too_short":
                strengthValue = 110;
                //strengthValue = parentProgressBarWidth/3;
                color = R.color.red;
                strengthName = "Weak";
                break;
            case "no_num":
                strengthValue = 250;
                //strengthValue = (parentProgressBarWidth*2)/3;
                color = R.color.colorPrimaryDark;
                strengthName = "Good";
                break;
            case "no_lowercase":
                strengthValue = 250;
                //strengthValue = parentProgressBarWidth*(2/3);
                color = R.color.colorPrimaryDark;
                strengthName = "Good";
                break;
            case "no_uppercase":
                strengthValue = 250;
                //strengthValue = parentProgressBarWidth*(2/3);
                color = R.color.colorPrimaryDark;
                strengthName = "Good";
                break;
            case "bad_char":
                strengthValue = ViewGroup.LayoutParams.MATCH_PARENT;
                color = R.color.color_great;
                strengthName = "Strong";
                break;
            case "too_long":
                strengthValue = ViewGroup.LayoutParams.MATCH_PARENT;
                color = R.color.color_great;
                strengthName = "Strong";
                break;
            case "very_strong":
                strengthValue = ViewGroup.LayoutParams.MATCH_PARENT;
                color = R.color.color_great;
                strengthName = "Strong";
                break;
            default:
                strengthValue = ViewGroup.LayoutParams.MATCH_PARENT;
                color = R.color.color_great;
                strengthName = "Strong";
                break;
        }
        Syso.print("strengthValue  "+ strengthValue);
        mViewProgressBar.setBackgroundColor(ContextCompat.getColor(ChangePasswordActivity.this,color));

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(strengthValue, ViewGroup.LayoutParams.MATCH_PARENT);
        mViewProgressBar.setLayoutParams(params);

        mTvPasswordStrength.setText(getString(R.string.title_password_strength)+" "+strengthName);
        mTvPasswordStrength.setTextColor(ContextCompat.getColor(ChangePasswordActivity.this,color));
    }

}
