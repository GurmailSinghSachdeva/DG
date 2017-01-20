package com.example.lenovo.discountgali.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.utility.AlertUtils;
import com.example.lenovo.discountgali.utility.Syso;
import com.example.lenovo.discountgali.utility.Utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by administrator on 29/04/16.
 */


public class BaseActivity extends AppCompatActivity {
    private final String TAG = ">>>>>BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (!SharedPreference.isAppUpdateApiCalled())
//            AppUpdateImplementation.executeTask(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setCurContext(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
