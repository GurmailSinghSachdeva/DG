package com.gurmail.singh.discountgali.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gurmail.singh.discountgali.R;

public class HelpUsImproveActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvSubmit;
    private EditText etName, etEmail, etMobile, etSuggesst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_us_improve);

        initUi();
        setListener();
    }

    private void setListener() {
        tvSubmit.setOnClickListener(this);
    }

    private void initUi() {
        tvSubmit = (TextView) findViewById(R.id.tv_submit);
        etName = (EditText) findViewById(R.id.et_name);
        etEmail = (EditText) findViewById(R.id.et_email);
        etMobile = (EditText) findViewById(R.id.et_mobile);
        etSuggesst = (EditText) findViewById(R.id.et_suggesst);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_submit:
                if(checkEmptyEditTexts()){

//                    hitApiCall();
                    //on response finish activity
                }
                break;
        }
    }

    private boolean checkEmptyEditTexts() {
        return (!TextUtils.isEmpty(etName.getText().toString().trim()) && !TextUtils.isEmpty(etName.getText().toString().trim())
                && !TextUtils.isEmpty(etName.getText().toString().trim()))?true:false;
    }
}
