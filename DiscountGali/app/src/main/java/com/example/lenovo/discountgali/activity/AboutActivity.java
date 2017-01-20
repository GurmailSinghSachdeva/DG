package com.example.lenovo.discountgali.activity;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.utility.Utils;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    LinearLayout help_layout,contact_layout;

    TextView tv_app_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        return intent;
    }


    void inItUi() {
        help_layout = (LinearLayout) findViewById(R.id.help_layout);
        contact_layout = (LinearLayout) findViewById(R.id.contact_layout);
        tv_app_version = (TextView) findViewById(R.id.tv_app_version);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar!=null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_back);
        }
        ((TextView) findViewById(R.id.header_title)).setText(getString(R.string.tv_about));
    }


    void setListener() {
        help_layout.setOnClickListener(this);
        contact_layout.setOnClickListener(this);
    }


    void setData() {
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(pInfo !=null){
            String version = pInfo.versionName;
            tv_app_version.setText("version "+version);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Utils.hideSoftKeyBoard(this);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, HelpContactActivity.class);
        switch (view.getId()) {
            case R.id.contact_layout:
                intent.putExtra("screenName", "contact_us");
                break;
            case R.id.help_layout:
                intent.putExtra("screenName", "help");
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
