package com.discount.coupons.discountgali.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.discount.coupons.discountgali.R;
import com.discount.coupons.discountgali.adapter.FeaturedAdapter;
import com.discount.coupons.discountgali.adapter.NavDrawerAdapter;
import com.discount.coupons.discountgali.adapter.ViewPagerAdapter;
import com.discount.coupons.discountgali.fragment.CategoryFragment;
import com.discount.coupons.discountgali.fragment.FeaturedFragment;
import com.discount.coupons.discountgali.fragment.HomeFragment;
import com.discount.coupons.discountgali.fragment.LocalDealCategoryFragment;
import com.discount.coupons.discountgali.fragment.TopStoresFragment;
import com.discount.coupons.discountgali.model.BaseModel;
import com.discount.coupons.discountgali.model.BottomTab;
import com.discount.coupons.discountgali.model.FeaturedModel;
import com.discount.coupons.discountgali.model.ServerResponse;
import com.discount.coupons.discountgali.network.Code;
import com.discount.coupons.discountgali.network.HttpRequestHandler;
import com.discount.coupons.discountgali.network.api.ApiCall;
import com.discount.coupons.discountgali.network.apicall.GetBannerApiCall;
import com.discount.coupons.discountgali.network.apicall.InsertCampaignUrlApiCAll;
import com.discount.coupons.discountgali.utility.Syso;
import com.discount.coupons.discountgali.utility.Utils;
import com.discount.coupons.discountgali.utils.Constants;
import com.discount.coupons.discountgali.utils.DialogUtils;
import com.discount.coupons.discountgali.utils.ImageLoaderUtils;
import com.discount.coupons.discountgali.utils.SharedPreference;
import com.discount.coupons.discountgali.widget.pageindicator.CirclePageIndicator;
import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.analytics.HitBuilders;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import static com.discount.coupons.discountgali.R.id.DrawerLayout;


public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    private EditText etTextSearch;
    private ArrayList<BottomTab> bottomtab = new ArrayList<>();
    private View layout_featured;
    private AutoScrollViewPager vp_featured;
    private FeaturedAdapter pagerAdapter;
    private ArrayList<String> home_tabs_name = new ArrayList<>();
    private CirclePageIndicator vp_indicator;
    private RelativeLayout rl_invite, rl_category;
    private ArrayList<FeaturedModel> featuredModellist = new ArrayList<>();
    private Toolbar mToolBar;
    private ImageView ivNavDrawer;
    private ImageView ivSearch;
    private Handler handler = new Handler();
    private static AppBarLayout appBarLayout;
    private android.support.v4.widget.DrawerLayout mDrawer;
    private int count = 0;

    public static void appBarOpenClose()
    {
        appBarLayout.setExpanded(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fixed);

        initUi();
        setUpToolbar();
        getHomeTabs();
        getFeaturedList();
        addTabs();
        setupViewPager(viewPager);
        setListener();
        setUpDrawer();


    }

    private void getFeaturedList() {

        getBannerApiCall();
        //TODO: get featured list here api call
//        FeaturedModel featuredModel = new FeaturedModel("Coupons by 500+ stores","Plus extra cashback","");
//        featuredModellist.add(featuredModel);
//        featuredModellist.add(featuredModel);
//        featuredModellist.add(featuredModel);

    }

    private void getBannerApiCall() {
        try {


            final ProgressDialog progressDialog = DialogUtils.getProgressDialog(this);
            progressDialog.show();
            final GetBannerApiCall apiCall;
            apiCall = new GetBannerApiCall();
            HttpRequestHandler.getInstance(this.getApplicationContext()).executeRequest(apiCall, new ApiCall.OnApiCallCompleteListener() {

                @Override
                public void onComplete(Exception e) {
                    DialogUtils.hideProgressDialog(progressDialog);

                    if (e == null) { // Success
                        try {
                            ServerResponse<FeaturedModel> serverResponse = (ServerResponse<FeaturedModel>) apiCall.getResult();
                            if(serverResponse!=null && serverResponse.data!=null && serverResponse.data.size()>0){
                                vp_featured.setVisibility(View.VISIBLE);
                                featuredModellist.clear();
                                featuredModellist.addAll(serverResponse.data);
                                Syso.print("Wait started");
//                                loadImages();

//                                handler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        DialogUtils.hideProgressDialog(progressDialog);
//                                    }
//                                },1000);
//                                try{
//                                    wait(1000);
//
//                                }catch (InterruptedException i)
//                                {
//                                }
//                                count = 0;
                                Syso.print("Wait Notified");
                                addFeatutedPager();
                            }
                            else {
//                                DialogUtils.hideProgressDialog(progressDialog);

                                vp_featured.setVisibility(View.GONE);
                            }
                        } catch (Exception e1) {

//                            DialogUtils.hideProgressDialog(progressDialog);

                            Utils.handleError(e1, HomeActivity.this);
                        }
                    } else { // Failure
//                        DialogUtils.hideProgressDialog(progressDialog);

                        Utils.handleError(e, HomeActivity.this);
                    }

                }
            }, false);
        } catch (Exception e) {

            Utils.handleError(e, HomeActivity.this);
        }


    }

    private void loadImages() {
        for(int i=featuredModellist.size()-1;i>=0;i--)
            ImageLoaderUtils.loadImageOnly(featuredModellist.get(i).icon, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                    count++;

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
    }


    private void setListener()
    {
        ivSearch.setOnClickListener(this);
        ivNavDrawer.setOnClickListener(this);
        rl_category.setOnClickListener(this);
        rl_invite.setOnClickListener(this);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (viewPager != null && adapter != null) {
                    viewPager.setCurrentItem(tab.getPosition());

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }


            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (viewPager == null || adapter == null)
                    return;

                int tabPosition = tab.getPosition();
//                    final Fragment fragment = ((ViewPagerAdapter) viewPager.getAdapter()).getFragmentAtPosition(tabPosition);
//                final HomeFragment fragment = (HomeFragment) viewPager.getAdapter().instantiateItem(viewPager, tabPosition);
//                if (fragment != null) {
//
//                   //TODO: scroll to top
//                }
            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }


    private void addFeatutedPager()
    {
        pagerAdapter = new FeaturedAdapter(getSupportFragmentManager(), featuredModellist);
        for(int i=0; i<featuredModellist.size();i++)
            pagerAdapter.addFragment(FeaturedFragment.newInstance(featuredModellist.get(i)));
        vp_featured.setAdapter(pagerAdapter);

        vp_featured.setCurrentItem(0);

        vp_featured.setOffscreenPageLimit(featuredModellist.size());
        vp_indicator.setViewPager(vp_featured);
        vp_featured.setInterval(3000);

        vp_featured.startAutoScroll();
        vp_indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                vp_featured.setCurrentItem((vp_featured.getCurrentItem()));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addTabs() {
        if(home_tabs_name!=null)
        {
            for (int i = 0; i < home_tabs_name.size(); i++) {
                TabLayout.Tab tab = tabLayout.newTab();
                tab.setText(home_tabs_name.get(i));
//                View view = getLayoutInflater().inflate(R.layout.layout_home_tab,null);
//                TextView tv = (TextView) view.findViewById(R.id.text_home_tab);
//                tv.setText(home_tabs_name.get(i));
//                ((TextView)(view.findViewById(R.id.text_home_tab))).setText(home_tabs_name.get(i));
//                tab.setCustomView(view);
//                tab.setCustomView(getLayoutInflater().inflate(R.layout.layout_home_tab, tabLayout, false));
//                tab.setText(home_tabs_name.get(i));
                tabLayout.addTab(tab);
            }
        }
    }
    private void getHomeTabs() {
        //Todo: api call here
        if(home_tabs_name!=null)
        {
            home_tabs_name.add("OFFERS");
            home_tabs_name.add("STORES");
            home_tabs_name.add("CATEGORIES");
            home_tabs_name.add("CITY DEALS");
//            home_tabs_name.add("PRODUCTS");
        }
    }


    public void initUi()
    {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        layout_featured = findViewById(R.id.layout_featured_deals);
        vp_featured = (AutoScrollViewPager) findViewById(R.id.vp_tutorial);
        vp_indicator = (CirclePageIndicator) findViewById(R.id.vp_indicator);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        mDrawer = (DrawerLayout) findViewById(DrawerLayout);
        rl_invite = (RelativeLayout) findViewById(R.id.rl_invite);
        rl_category = (RelativeLayout) findViewById(R.id.rl_category);
        ivNavDrawer = (ImageView) mToolBar.findViewById(R.id.navDrawerIcon);

        ivSearch = (ImageView) mToolBar.findViewById(R.id.search);
    }


    private void setUpToolbar() {

        mToolBar.setNavigationIcon(R.drawable.view_sequential);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);

//        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mDrawer.isDrawerOpen(GravityCompat.START)) {
//                    mDrawer.closeDrawer(GravityCompat.START);
//                } else {
//                    mDrawer.openDrawer(GravityCompat.START);
//                }
//
////                Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_SHORT).show();
//            }
//        });

    }
    private void setupViewPager(final ViewPager viewPager) {
        try {

            adapter = new ViewPagerAdapter(getSupportFragmentManager());

//            if(home_tabs_name!=null)
//            {
//                for(int i=0; i<home_tabs_name.size();i++)
//                    adapter.addFragment(HomeFragment.newInstance(home_tabs_name.get(i)), home_tabs_name.get(i));
//            }
            adapter.addFragment(HomeFragment.newInstance("Offers"),"Offers");
            adapter.addFragment(new TopStoresFragment(), "Stores");
            adapter.addFragment(CategoryFragment.newInstance("Categories"), "categories");
            adapter.addFragment(new LocalDealCategoryFragment(), "LocalDeals");

            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    final Fragment fragment1 = (Fragment) viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem());


                    if (viewPager != null && adapter != null) {
                        Fragment fragment = adapter.getFragmentAtPosition(position);
                        if (fragment != null) {
                            fragment.onResume();
//                            if (fragment instanceof BaseFragment) {
//                                ((BaseFragment) fragment).isShown = true;
//                            }
//                            if (fragment1 instanceof NewsFragment) {
//                                L.e("selected"," news");
//                                ((NewsFragment) fragment1).scrollToTop();
//                            }
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            viewPager.setAdapter(adapter);

            viewPager.setCurrentItem(0);

            viewPager.setOffscreenPageLimit(home_tabs_name.size());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Intent i = new Intent("com.android.vending.INSTALL_REFERRER");
//        i.setPackage("com.discount.coupons.discountgali");
////referrer is a composition of the parameter of the campaing
//        i.putExtra("referrer", "https://market.android.com/details?id=com.giago.referraltester&feature=search_result");
//        sendBroadcast(i);

    }

    private void setUpDrawer() {
        //  Setup Profile
//        setupProfile();


        // Setup List
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true); // Letting the system know that the list objects are of fixed size
        // TODO: Move all strings to strings.xml file
        final ArrayList<NavDrawerAdapter.NavMenuItem> navMenuItems = new ArrayList<NavDrawerAdapter.NavMenuItem>();
        navMenuItems.add(new NavDrawerAdapter.NavMenuItem("Home", R.drawable.home));
        navMenuItems.add(new NavDrawerAdapter.NavMenuItem("All Stores", R.drawable.stores_icon));
        navMenuItems.add(new NavDrawerAdapter.NavMenuItem("Contest", R.drawable.contest));
//        navMenuItems.add(new NavDrawerAdapter.NavMenuItem("Notifications", R.drawable.notifications));
        navMenuItems.add(new NavDrawerAdapter.NavMenuItem("About Us", R.drawable.about_us));
        navMenuItems.add(new NavDrawerAdapter.NavMenuItem("Rate us", R.drawable.rate_us));
        navMenuItems.add(new NavDrawerAdapter.NavMenuItem("Refer a Friend", R.drawable.invitation));
        navMenuItems.add(new NavDrawerAdapter.NavMenuItem("Deal of the day", R.drawable.deal_day));
//        navMenuItems.add(new NavDrawerAdapter.NavMenuItem("Help us Improve", R.drawable.help_us));
        navMenuItems.add(new NavDrawerAdapter.NavMenuItem("Merchant", R.drawable.shop));
        navMenuItems.add(new NavDrawerAdapter.NavMenuItem("Contact us", R.drawable.contact_us));

        NavDrawerAdapter mAdapter = new NavDrawerAdapter(navMenuItems, this);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter.setOnItemClickListener(new NavDrawerAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int index) {
                String navItem = navMenuItems.get(index).title;
                if (navItem.equalsIgnoreCase("Home")) {
                    viewPager.setCurrentItem(0);
//                    startActivity(new Intent(HomeActivity.this, LikedStories.class));
                } else if (navItem.equalsIgnoreCase("All Stores")) {
                    viewPager.setCurrentItem(1);

                } else if (navItem.equalsIgnoreCase("Contest")) {
                    startActivity(new Intent(HomeActivity.this, ContestActivity.class));
                } else if (navItem.equalsIgnoreCase("Notifications")) {
//                    startActivity(new Intent(HomeActivity.this, PushNotificationActivity.class));
                } else if (navItem.equalsIgnoreCase("About Us")) {
                    startActivity(new Intent(HomeActivity.this, AboutUsActivity.class));
                } else if (navItem.equalsIgnoreCase("Rate us")) {
                    Utils.navigatePlayStore();
                }else if (navItem.equalsIgnoreCase("Refer a Friend")) {
                    invite();
//                    clearSearchHistoryDialog(HomeActivity.this);
                }else if (navItem.equalsIgnoreCase("Deal of the day")) {
                    startActivity(new Intent(HomeActivity.this, DealOfTheDayActivity.class));
//                    clearSearchHistoryDialog(HomeActivity.this);
                }else if (navItem.equalsIgnoreCase("Help us Improve")) {
                    startActivity(new Intent(HomeActivity.this, HelpUsImproveActivity.class));
                }else if (navItem.equalsIgnoreCase("Merchant")) {
                    startActivity(new Intent(HomeActivity.this, ContactUsActivity.class).putExtra("url", Constants.urlMerchant));
                }else if (navItem.equalsIgnoreCase("Contact us")) {
                    startActivity(new Intent(HomeActivity.this, ContactUsActivity.class).putExtra("url", Constants.urlContactUs));
                }

                mDrawer.closeDrawer(GravityCompat.START);
            }
        });

        mDrawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mDrawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
//                onLoginDetailsChange();
            }
        });

        View header = findViewById(R.id.header);
        header.setOnClickListener(this);
    }
    public void invite() {

        Utils.showCustomSharingFragment(this, "Hey check out my app at: " + Utils.getAppStoreURL(), "Invite Friends Using");

    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar:
                if (mDrawer.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawer.closeDrawer(Gravity.LEFT);
                } else {
                    mDrawer.openDrawer(Gravity.RIGHT);
                }
                break;

            case R.id.header:
                mDrawer.closeDrawer(GravityCompat.START);
//                Intent intent = new Intent(HomeActivity.this, ProfileViewActivity.class);
//                intent.putExtra("user", SharedPreference.getMyProfile());
//                intent.putExtra("user_model_opened", UserModelsUpdateReceiver.UserModelUpdateType.ROAST.name());
//                startActivity(intent);
                break;
            case R.id.rl_invite:
                invite();
                break;
            case R.id.rl_category:
                viewPager.setCurrentItem(2);
                break;
            case R.id.navDrawerIcon:
                if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                    mDrawer.closeDrawer(GravityCompat.START);
                } else {
                    mDrawer.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.search:
                Utils.showSearchActivity(HomeActivity.this);
                break;
        }
    }


    public static class CampaignTrackingReceiverCustom extends BroadcastReceiver {
        private String TAG = "COMPAIGN";


        @Override
        public void onReceive(Context context, Intent intent) {
            Syso.print("===onReceive====" + "====CampaignTrackingReceiver===");
//      "utm_source=testSource&utm_medium=testMedium&utm_term=testTerm&utm_content=testContent&utm_campaign=testCampaign"


            if (intent.hasExtra("referrer")) {
                Syso.print(TAG + "-----referrer is found-----");
                Bundle extras = intent.getExtras();
                String referrerString = null;
                try {
                    referrerString = URLDecoder.decode(extras.getString("referrer"),"UTF-8");
                    Syso.print(TAG + extras.getString("referrer"));

                mTracker.setScreenName("Home");
                mTracker.send(new HitBuilders.ScreenViewBuilder()
                        .setCampaignParamsFromUrl(referrerString)
                        .build());

                    SharedPreference.saveCompaignTrackingUrl(context,referrerString);
                    if(SharedPreference.getCampaignStatus() != 1){

                        saveReferrerOnline(context, referrerString, Utils.getDeviceID());

                    }
                    new CampaignTrackingReceiver().onReceive(context, intent);

                } catch (UnsupportedEncodingException e) {
                    referrerString = extras.getString("referrer");
                    e.printStackTrace();
                }
                Log.v(TAG,referrerString);
//            SharedPreference.saveCompaignTrackingUrl(context,referrerString);
//             Next line uses my helper function to parse a query (eg "a=b&c=d") into key-value pairs
//            HashMap<String, String> getParams = null;
//            try {
//                getParams = (HashMap<String, String>) getHashMapFromQuery(referrerString);
//
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
            } else {
                Log.v(TAG, "-----referrer is not found-----");
//            SharedPreference.saveCompaignTrackingUrl(context,"");
            }

        }

        private void saveReferrerOnline(Context context, String referrerString, String deviceID) {

            try{
                final InsertCampaignUrlApiCAll apiCall;
                apiCall = new InsertCampaignUrlApiCAll(referrerString, deviceID);
                HttpRequestHandler.getInstance(context).executeRequest(apiCall, new ApiCall.OnApiCallCompleteListener() {
                    @Override
                    public void onComplete(Exception e) {

                        if (e == null) {
                            BaseModel baseModel = (BaseModel) apiCall.getResult();
                            if(baseModel!=null)
                            {
                                switch (baseModel.MessageCode)
                                {
                                    case Code.SUCCESS_MESSAGE_CODE:

                                        if(apiCall.getSuccessStatus() == Constants.otp_sent_success){
                                            SharedPreference.saveMyCampaignStatus(Constants.otp_sent_success);
                                        }
                                        break;

                                }
                            }

                        } else {

                        }
                    }
                }, false);
            }catch (Exception e)
            {
            }

        }

        public Map<String, String> getHashMapFromQuery(String query)
                throws UnsupportedEncodingException {

            Map<String, String> query_pairs = new LinkedHashMap<String, String>();

            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
                        URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
                Log.v("==Key=" + pair.substring(0, idx), "" + pair.substring(idx + 1));
            }
            return query_pairs;
        }

        public void printData(Bundle bundle) {
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                Log.d("-----Data------", String.format("%s %s (%s)", key,
                        value.toString(), value.getClass().getName()));
            }
        }

    }
}
