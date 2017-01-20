package com.example.lenovo.discountgali.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.utility.Syso;
import com.example.lenovo.discountgali.utility.Utils;


public class HomeActivityDummy extends BaseActivity implements TabLayout.OnTabSelectedListener {
    private final static String EXTRA_USER_DATA = "EXTRA_USER_DATA"; /**< Used for put email in intent */
    //TODO: write all comment
    private TabLayout tablayout;
    private ViewPager viewPager;
    private Toolbar mToolbar;
    private int mTotalTabs = 5;
    private TextView mProfileStatus;
    private LinearLayout mSearchLayout;
    private EditText mSearchView;
    private Button btnSearch;

    public static Intent getStartIntent(Context context, Bundle bundle) {
        Intent intent = new Intent(context, HomeActivityDummy.class);
        if(bundle!=null)
          intent.putExtra(EXTRA_USER_DATA,bundle);
        return intent;
    }

    private int TabIconsUnSelected[] = {
            R.drawable.menu_contact_off,
            R.drawable.menu_group_off,
            R.drawable.menu_recents_off,
            R.drawable.menu_profile_off,
            R.drawable.menu_setting_off
    };

    private int TabIconsSelected[] = {
            R.drawable.menu_contact,
            R.drawable.menu_group,
            R.drawable.menu_recents,
            R.drawable.menu_profile,
            R.drawable.menu_setting
    };

    private int TabText[] = {
            R.string.tv_favourite,
            R.string.tv_single_group,
            R.string.tv_recent,
            R.string.tv_profile,
            R.string.tv_setting
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_home);
        if(getIntent()!=null)
        {
            if(getIntent().getStringExtra("page")!=null && getIntent().getStringExtra("page").equals("settings"))
            {
                defaultSelectedTab = 4;
            }
        }
        setUpToolbar("Favorites");
        setupTabIcons();
        FragmentManager fragmentManager = getSupportFragmentManager();
//        final PageAdapter pageAdapter = new PageAdapter(fragmentManager, 5);
//        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        viewPager.setOffscreenPageLimit(5);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
//            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, Constants.RequestCode.REQUEST_CODE_READ_CONTACT);
//        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                uploadContact(); // to upload contact to server
            }else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        uploadContact();
                    }
                },1000*60*5);
            }
        }
//        tablayout.getTabAt(defaultSelectedTab).select();

//    }



    private int defaultSelectedTab = 2;

    void inItUi() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        mToolbar = (Toolbar) findViewById(R.id.tb_favourites);

        mProfileStatus = (TextView) mToolbar.findViewById(R.id.profile_status);
        //mProfileStatus.setVisibility(View.GONE);

        for (int i = 0; i < mTotalTabs; i++) {
            tablayout.addTab(tablayout.newTab().setText(TabText[i]));
        }
        mSearchLayout = (LinearLayout) findViewById(R.id.search_layout);
        mSearchView = (EditText) findViewById(R.id.search_view);
        btnSearch = (Button) findViewById(R.id.btn_done);
    }

    void setListener() {
//        tablayout.setOnTabSelectedListener(this);
//        mProfileStatus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ProfileFragment profileFragment = (ProfileFragment) viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem());
//                profileFragment.menuItemClickListener();
//            }
//        });
//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Utils.hidKeyBoard(HomeActivityDummy.this);
//                if(currentSearchCallBack!=null && checkInternet()){
//                    currentSearchCallBack.search(mSearchView.getText().toString().trim());
//                }
//            }
//        });
//        mSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    Utils.hidKeyBoard(HomeActivityDummy.this);
//                    if(currentSearchCallBack!=null && checkInternet()){
//                        currentSearchCallBack.search(mSearchView.getText().toString());
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    void setData() {
//        if(getIntent().getExtras()!=null){
//            Bundle bundle = getIntent().getBundleExtra(EXTRA_USER_DATA);
//            Utils.bundle2string(bundle);
//            if(bundle!=null && !bundle.isEmpty())
//            startActivity(MessageActivity.getStartIntent(this, bundle));
//        }
    }


    @Override
    protected void onResume() {
        Syso.print("inside on resume");
       // getTheme().applyStyle(Utils.getFontSizeId(UserPreference.getInstance(this).getIntField(UserPreference.FIELD_FONT_SIZE)), true);
        //setContentView(R.layout.activity_tab_home);
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Fragment f = (Fragment)viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem());
        f.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode){
//            case Constants.RequestCode.REQUEST_CODE_READ_CONTACT:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    uploadContact();
//                }else {
//                    AlertUtils.showToast(this,R.string.alert_permission_read_contact);
//                }
//                break;
//        }

    }

    public void uploadContact(){
//        checkInternetNew(new PingCallback() {
//            @Override
//            public void success() {
//                UploadContactIntentService.startContactUpload(HomeActivityDummy.this); // to upload contact to server
//            }
//
//            @Override
//            public void fail() {
//
//            }
//        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        tab.setIcon(TabIconsSelected[tab.getPosition()]);
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        //   Toast.makeText(HomeActivityDummy.this, "OnTabUnSelected:-" + tab.getPosition() + "", Toast.LENGTH_SHORT).show();
        tab.setIcon(TabIconsUnSelected[tab.getPosition()]);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    private void setupTabIcons() {
        for (int i = 0; i < mTotalTabs; i++) {
            tablayout.getTabAt(i).setIcon(TabIconsUnSelected[i]);
        }
        tablayout.getTabAt(defaultSelectedTab).setIcon(TabIconsUnSelected[defaultSelectedTab]);
    }

    private void setUpToolbar(String Title) {
        ((TextView) mToolbar.findViewById(R.id.toolbar_title)).setText(Title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Used for creating menu tools
     * @param menu current menu used in base screen
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_home_tab, menu);
        getMenuInflater().inflate(R.menu.menu_home, menu);
        ((TextView) mToolbar.findViewById(R.id.profile_status)).setVisibility(View.GONE);
//        menu.findItem(R.id.menu_first).setVisible(false);
//        menu.findItem(R.id.menu_second).setVisible(false);
        return true;
    }

    /**
     * Used for selection listener of menu toolbar
     * @param item action performed on those menu items
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (viewPager.getCurrentItem()){
//            case 0:
//                ContactsFragment contactsFragment = (ContactsFragment)viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem());
//                contactsFragment.menuItemClickListener(item.getItemId(),item);
//                break;
//            case 1:
//                GroupsFragment groupsFragment = (GroupsFragment)viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem());
//                groupsFragment.menuItemClickListener(item.getItemId());
//                break;
//            case 2:
//                RecentsFragment recentsFragment = (RecentsFragment)viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem());
//                recentsFragment.menuItemClickListener(item.getItemId());
//                break;
//            case 3:
//                ProfileFragment profileFragment = (ProfileFragment)viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem());
//                profileFragment.menuItemClickListener();
//                break;
//        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Syso.print(">>> in Home screen onActivityResult");
        Fragment f = (Fragment)viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem());
        f.onActivityResult(requestCode, resultCode, data);
    }
    public void appyTheme(){
        Intent intent = getStartIntent(this,null);
        intent.putExtra("page","settings");
        //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //finish();
        startActivity(intent);
        overridePendingTransition(0, 0);

//       RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rootLayout);
//       getTheme().applyStyle(Utils.getFontSizeId(UserPreference.getInstance(this).getIntField(UserPreference.FIELD_FONT_SIZE)), true);
//        relativeLayout.invalidate();
    }

    public void showHideSearchBar(boolean isShowSearchLayout){
        mSearchLayout.setVisibility(isShowSearchLayout?View.VISIBLE:View.GONE);
        if(!isShowSearchLayout)
            Utils.hidKeyBoard(this);
        else
            mSearchView.requestFocus();
    }

    public void setTextInSearchView(String text){
        mSearchView.setText(text);
        mSearchView.setSelection(text.length());
    }

//    public void setCurrentSearchCallBack(SearchCallback searchCallBack){
//        currentSearchCallBack = searchCallBack;
//    }

}
