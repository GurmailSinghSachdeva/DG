package com.example.lenovo.discountgali.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
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
    private EditText et_phone_number, et_ref;
    private static EditText et_otp;
    private ImageView iv_edit, iv_keypad_delete, iv_keypad_ok;
    private TextView tv_resend, tv_number_1, tv_number_2, tv_number_3, tv_number_4, tv_number_5,
            tv_number_6, tv_number_7, tv_number_8, tv_number_9, tv_number_0, tv_phone_no, tv_span, tv_skip, tv_submit;
    private String spannablePath;
    private transient Spannable spannableContent;
    private String[] roots = {"T&C","Privacy Policy"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUi();
        setListener();
        checkSmsPermission();

    }
    private void initUi() {
        view_upper_phone = findViewById(R.id.view_upper_phone);
        view_upper_otp = findViewById(R.id.view_upper_otp);

        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        et_otp = (EditText) findViewById(R.id.et_otp);
        et_ref = (EditText) findViewById(R.id.et_ref);

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
        tv_phone_no = (TextView) findViewById(R.id.tv_phone_no);
        tv_span = (TextView) findViewById(R.id.tv_span);
        tv_skip = (TextView) findViewById(R.id.tv_skip);
        tv_submit = (TextView) findViewById(R.id.tv_submit);

        et_phone_number.requestFocus();

        spannablePath = getString(R.string.tc_and_privacy);
                    spannableContent = Utils.getSpanString(spannablePath, roots, hashUserClickListener);

        tv_span.setMovementMethod(LinkMovementMethod.getInstance());
        tv_span.setText(spannableContent);

    }
    private void setListener() {

        iv_edit.setOnClickListener(this);
        iv_keypad_delete.setOnClickListener(this);
        iv_keypad_ok.setOnClickListener(this);
        et_otp.setOnClickListener(this);
        et_phone_number.setOnClickListener(this);

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
        tv_skip.setOnClickListener(this);
        tv_submit.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loginApiCAll();
            }
        };

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

                if(view_upper_otp.getVisibility() == View.VISIBLE){
                    et_otp.setText(et_otp.getText().toString() + tv.getText().toString());

                }else {
                    et_phone_number.setText(et_phone_number.getText().toString() + tv.getText().toString());
                }
                break;
            case R.id.iv_keypad_delete:
                if(view_upper_otp.getVisibility() == View.VISIBLE){
                    et_otp.setText(et_otp.getText().toString().substring(0,et_otp.getText().toString().length()-1));

                }else {
                    et_phone_number.setText(et_phone_number.getText().toString().substring(0,et_phone_number.getText().toString().length()-1));
                }
                break;
            case R.id.iv_keypad_ok:
//                Utils.hideSoftKeyBoard2(LoginActivity.this);
                Utils.hidKeyBoard(this);
                if(view_upper_otp.getVisibility() == View.VISIBLE){

                    if(et_otp!=null && !TextUtils.isEmpty(et_otp.getText()) &&
                            et_otp.getText().toString().trim().length() > 0){
                        verificationApiCall(et_otp.getText().toString().trim());
                    }
                    else {
                        AlertUtils.showToast(LoginActivity.this, R.string.alert_wait_otp);
                    }

                }else {
                    if(et_phone_number!=null && !TextUtils.isEmpty(et_phone_number.getText()) &&
                            et_phone_number.getText().toString().trim().length() == Constants.phone_number_max_digits){
                        loginApiCAll();
                    }
                    else {
                        AlertUtils.showToast(LoginActivity.this, R.string.alert_enter_10_digit_mobile_number);
                    }
                }
                break;
            case R.id.tv_resend:
                DialogUtils.showAlertOk(LoginActivity.this, getString(R.string.send_another_code), runnable);
                break;
            case R.id.iv_edit:
                et_otp.setText("");
                view_upper_otp.setVisibility(View.GONE);
                view_upper_phone.setVisibility(View.VISIBLE);

                break;
            case R.id.et_otp:
//            case R.id.et_phone_number:
//                Utils.hidKeyBoard(this);
//                break;
            case R.id.tv_skip:
                SharedPreference.saveSkipStatus(1);
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
                break;

            case R.id.tv_submit:
                Utils.hidKeyBoard(this);
                if(view_upper_otp.getVisibility() == View.VISIBLE){

                    if(et_otp!=null && !TextUtils.isEmpty(et_otp.getText()) &&
                            et_otp.getText().toString().trim().length() > 4){
                        verificationApiCall(et_otp.getText().toString().trim());
                    }
                    else {
                        AlertUtils.showToast(LoginActivity.this, R.string.alert_wait_otp);
                    }

                }else {
                    if(et_phone_number!=null && !TextUtils.isEmpty(et_phone_number.getText()) &&
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

    private void verificationApiCall(String otp) {
        try{
            final LoginApiCall apiCall;
            final ProgressDialog progressDialog = DialogUtils.getProgressDialog(LoginActivity.this, "verifying....");
            progressDialog.show();
            apiCall = new LoginApiCall(et_phone_number.getText().toString().trim(), "123", 1 , otp);
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
                                        SharedPreference.saveMyLoginStatus(Constants.otp_sent_success);
                                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                        finish();
                                    }else {
                                        AlertUtils.showToast(LoginActivity.this, baseModel.Message);
                                    }
                                    break;
                                default:
                                    DialogUtils.showAlert(LoginActivity.this, !TextUtils.isEmpty(baseModel.Message)?baseModel.Message:"Login error. please try again");

                                    break;
                            }
                        }

                    } else {

                        DialogUtils.showAlert(LoginActivity.this, "Login error. please try again");
                    }
                }
            }, false);
        }catch (Exception e)
        {
            DialogUtils.showAlert(LoginActivity.this, "Login error. please try again");
        }

    }

    private void loginApiCAll() {

        try{
            final LoginApiCall apiCall;
            final ProgressDialog progressDialog = DialogUtils.getProgressDialog(LoginActivity.this, "sending otp to this number");
            progressDialog.show();
            apiCall = new LoginApiCall(et_phone_number.getText().toString().trim(), (et_ref!=null && et_ref.getText()!=null)?
                    et_ref.getText().toString():"", 0, "" );
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
                                        tv_phone_no.setText("+91 " + et_phone_number.getText().toString().trim());
//                                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//                                        finish();
                                    }else {
                                        AlertUtils.showToast(LoginActivity.this, baseModel.Message);
                                    }
                                    break;
                                default:
                                    DialogUtils.showAlert(LoginActivity.this, !TextUtils.isEmpty(baseModel.Message)?baseModel.Message:"Login error. please try again");
                                    break;
                            }
                        }

                    } else {

                        DialogUtils.showAlert(LoginActivity.this, "Login error. please try again");
                    }
                }
            }, false);
        }catch (Exception e)
        {
            DialogUtils.showAlert(LoginActivity.this, "Login error. please try again");
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
            String code = "";
            try {
                String strings[] = message.split(Constants.OTP_DELIMITER);
                code = strings[1];
                return code;

            }catch (Exception e)
            {
                e.printStackTrace();
                return "";
            }
        }
    }
    public Utils.SpanClickListenerI hashUserClickListener = new Utils.SpanClickListenerI() {
        public void onClick(String name, int pos) {


            if(pos == 0)
            {
                startActivity(new Intent(LoginActivity.this, ContactUsActivity.class).putExtra("url",Constants.urlTandC));
            }
            else {
                startActivity(new Intent(LoginActivity.this, ContactUsActivity.class).putExtra("url",Constants.urlPrivacyPolicy));
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.REQUEST_FOR_SMS_PERMISIION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                break;
        }
    }

    private void checkSmsPermission(){
        //Check External storage permission in Android 6
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M ) {
            if (checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_SMS}, Constants.REQUEST_FOR_SMS_PERMISIION);
            }
        }

    }

}
