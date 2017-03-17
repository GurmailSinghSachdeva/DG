package com.example.lenovo.discountgali.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.model.BaseModel;
import com.example.lenovo.discountgali.model.ServerResponse;
import com.example.lenovo.discountgali.model.TopOffers;
import com.example.lenovo.discountgali.network.Code;
import com.example.lenovo.discountgali.network.HttpRequestHandler;
import com.example.lenovo.discountgali.network.api.ApiCall;
import com.example.lenovo.discountgali.network.apicall.GetRecentMessageApiCall;
import com.example.lenovo.discountgali.network.apicall.LoginApiCall;
import com.example.lenovo.discountgali.utility.AlertUtils;
import com.example.lenovo.discountgali.utility.Syso;
import com.example.lenovo.discountgali.utility.Utils;
import com.example.lenovo.discountgali.utils.Constants;
import com.example.lenovo.discountgali.utils.DialogUtils;
import com.example.lenovo.discountgali.utils.SharedPreference;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private View view_upper_phone, view_upper_otp;
    private EditText et_phone_number;
    private static EditText et_otp;
    private ImageView iv_edit, iv_keypad_delete, iv_keypad_ok;
    private TextView tv_resend, tv_number_1, tv_number_2, tv_number_3, tv_number_4, tv_number_5,
            tv_number_6, tv_number_7, tv_number_8, tv_number_9, tv_number_0;
    private boolean isOtpScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUi();
        setListener();

    }
    private void initUi() {
        view_upper_phone = findViewById(R.id.view_upper_phone);
        view_upper_otp = findViewById(R.id.view_upper_otp);

        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        et_otp = (EditText) findViewById(R.id.et_otp);

        iv_edit = (ImageView) findViewById(R.id.iv_edit);
        iv_keypad_delete = (ImageView) findViewById(R.id.iv_keypad_delete);
        iv_keypad_ok = (ImageView) findViewById(R.id.iv_keypad_ok);

        tv_number_0 = (TextView) findViewById(R.id.tv_number_0);
        tv_number_1 = (TextView) findViewById(R.id.tv_number_1);
        tv_number_2 = (TextView) findViewById(R.id.tv_number_2);
        tv_number_3 = (TextView) findViewById(R.id.tv_number_3);
        tv_number_4 = (TextView) findViewById(R.id.tv_number_4);
        tv_number_5 = (TextView) findViewById(R.id.tv_number_5);
        tv_number_6 = (TextView) findViewById(R.id.tv_number_6);
        tv_number_7 = (TextView) findViewById(R.id.tv_number_7);
        tv_number_8 = (TextView) findViewById(R.id.tv_number_8);
        tv_number_9 = (TextView) findViewById(R.id.tv_number_9);
        tv_resend = (TextView) findViewById(R.id.tv_resend);
    }
    private void setListener() {

        iv_edit.setOnClickListener(this);
        iv_keypad_delete.setOnClickListener(this);
        iv_keypad_ok.setOnClickListener(this);

        tv_number_0.setOnClickListener(this);
        tv_number_1.setOnClickListener(this);
        tv_number_2.setOnClickListener(this);
        tv_number_3.setOnClickListener(this);
        tv_number_4.setOnClickListener(this);
        tv_number_5.setOnClickListener(this);
        tv_number_6.setOnClickListener(this);
        tv_number_7.setOnClickListener(this);
        tv_number_8.setOnClickListener(this);
        tv_number_9.setOnClickListener(this);
        tv_resend.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.tv_number_0:
            case R.id.tv_number_1:
            case R.id.tv_number_2:
            case R.id.tv_number_3:
            case R.id.tv_number_4:
            case R.id.tv_number_5:
            case R.id.tv_number_6:
            case R.id.tv_number_7:
            case R.id.tv_number_8:
            case R.id.tv_number_9:
                TextView tv = (TextView) view;
                et_phone_number.setText(et_phone_number.getText().toString() + tv.getText().toString());
                break;
            case R.id.iv_keypad_delete:
                et_phone_number.setText(et_phone_number.getText().toString().substring(0,et_phone_number.getText().toString().length()-1));
                break;
            case R.id.iv_keypad_ok:
                if(isOtpScreen){

                    if(!TextUtils.isEmpty(et_otp.getText().toString()) &&
                            et_phone_number.getText().toString().trim().length() > 0){
                        verificationApiCall();
                    }
                    else {
                        AlertUtils.showToast(LoginActivity.this, R.string.alert_wait_otp);
                    }

                }else {
                    if(!TextUtils.isEmpty(et_phone_number.getText().toString()) &&
                            et_phone_number.getText().toString().trim().length() == Constants.phone_number_max_digits){
                        loginApiCAll();
                    }
                    else {
                        AlertUtils.showToast(LoginActivity.this, R.string.alert_enter_10_digit_mobile_number);
                    }
                }
                break;
        }
    }

    private void verificationApiCall() {

    }

    private void loginApiCAll() {

        try{
            final LoginApiCall apiCall;
            final ProgressDialog progressDialog = DialogUtils.getProgressDialog(LoginActivity.this, "sending otp to this number");
            progressDialog.show();
            apiCall = new LoginApiCall(et_phone_number.getText().toString().trim(), "123" );
            HttpRequestHandler.getInstance(this.getApplicationContext()).executeRequest(apiCall, new ApiCall.OnApiCallCompleteListener() {
                @Override
                public void onComplete(Exception e) {

                    DialogUtils.hideProgressDialog(progressDialog);
                    if (e == null) {
                        BaseModel baseModel = (BaseModel) apiCall.getResult();
                        if(baseModel!=null)
                        {
                            switch (baseModel.MessageCode)
                            {
                                case Code.SUCCESS_MESSAGE_CODE:

                                    if(apiCall.getSuccessStatus() == Constants.otp_sent_success){
                                        SharedPreference.saveMyMobileNumber(et_phone_number.getText().toString().trim());
                                        view_upper_phone.setVisibility(View.GONE);
                                        view_upper_otp.setVisibility(View.VISIBLE);
//                                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//                                        finish();
                                    }else {
                                        AlertUtils.showToast(LoginActivity.this, baseModel.Message);
                                    }
                                    break;
                                default:
                                    DialogUtils.showAlert(LoginActivity.this, "Login Failure");

                                    break;
                            }
                        }

                    } else {

                        Utils.handleError(e,LoginActivity.this);
                    }
                }
            }, false);
        }catch (Exception e)
        {
            Utils.handleError(e, LoginActivity.this);
        }

    }

    public static class SmsReceiver extends BroadcastReceiver {
        private final String TAG = SmsReceiver.class.getSimpleName();


        @Override
        public void onReceive(Context context, Intent intent) {

            Syso.print("LOGIN " + "sms came");
            final Bundle bundle = intent.getExtras();
            try {
                if (bundle != null) {
                    Object[] pdusObj = (Object[]) bundle.get("pdus");
                    for (Object aPdusObj : pdusObj) {
                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                        String senderAddress = currentMessage.getDisplayOriginatingAddress();
                        String message = currentMessage.getDisplayMessageBody();

                        Syso.print("LOGIN " + "SMS " + " message " + message + " senderr " + senderAddress);
//                        Log.e(TAG, "Received SMS: " + message + ", Sender: " + senderAddress);

                        // if the SMS is not from our gateway, ignore the message
                       /* if (!senderAddress.toLowerCase().contains(Constants.SMS_ORIGIN.toLowerCase())) {
                            return;

                        }*/
                        String verificationCode = null;
                        if (senderAddress.equals("HP-DGCLUB")) {
                            verificationCode = getVerificationCode(message);

                            et_otp.setText(verificationCode);
                        }

                        // verification c ode from sms
                        //verificationCode = getVerificationCode(message);

                        //otp_inputCode.setText(verificationCode);


                        Log.e(TAG, "OTP received: " + verificationCode);

                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }

        /**
         * Getting the OTP from sms message body
         * ':' is the separator of OTP from the message
         *
         * @param message
         * @return
         */
        private String getVerificationCode(String message) {
            String code = null;
            int index = message.indexOf(Constants.OTP_DELIMITER);

            if (index != -1) {
                int start = index + 4;
                int length = 5;
                code = message.substring(start, start + length);
                return code;
            }

            return code;
        }
    }

}