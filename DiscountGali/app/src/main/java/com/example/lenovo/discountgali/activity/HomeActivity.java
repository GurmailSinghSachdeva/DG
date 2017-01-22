package com.example.lenovo.discountgali.activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.adapter.FeaturedAdapter;
import com.example.lenovo.discountgali.adapter.NavDrawerAdapter;
import com.example.lenovo.discountgali.adapter.ViewPagerAdapter;
import com.example.lenovo.discountgali.fragment.CategoryFragment;
import com.example.lenovo.discountgali.fragment.FeaturedFragment;
import com.example.lenovo.discountgali.fragment.HomeFragment;
import com.example.lenovo.discountgali.fragment.LocalDealCategoryFragment;
import com.example.lenovo.discountgali.fragment.TopStoresFragment;
import com.example.lenovo.discountgali.model.BottomTab;
import com.example.lenovo.discountgali.model.FeaturedModel;
import com.example.lenovo.discountgali.utility.Utils;
import com.example.lenovo.discountgali.widget.pageindicator.CirclePageIndicator;

import java.util.ArrayList;

import static com.example.lenovo.discountgali.R.id.DrawerLayout;


public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    private EditText etTextSearch;
    private ArrayList<BottomTab> bottomtab = new ArrayList<>();
    private View layout_featured;
    private ViewPager vp_featured;
    private FeaturedAdapter pagerAdapter;
    private ArrayList<String> home_tabs_name = new ArrayList<>();
    private CirclePageIndicator vp_indicator;
    private RelativeLayout rl_invite, rl_category;
    private ArrayList<FeaturedModel> featuredModellist = new ArrayList<>();
    private Toolbar mToolBar;
    private static AppBarLayout appBarLayout;
    private android.support.v4.widget.DrawerLayout mDrawer;

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
        //TODO: get featured list here api call
        FeaturedModel featuredModel = new FeaturedModel("Coupons by 500+ stores","Plus extra cashback","");
        featuredModellist.add(featuredModel);
        featuredModellist.add(featuredModel);
        featuredModellist.add(featuredModel);

    }




    private void setListener()
    {
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

        pagerAdapter = new FeaturedAdapter(getSupportFragmentManager(), featuredModellist);
        for(int i=0; i<featuredModellist.size();i++)
            pagerAdapter.addFragment(FeaturedFragment.newInstance(featuredModellist.get(i)));
        vp_featured.setAdapter(pagerAdapter);

        vp_featured.setCurrentItem(0);

        vp_featured.setOffscreenPageLimit(featuredModellist.size());
        vp_indicator.setViewPager(vp_featured);

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
            home_tabs_name.add("Deals");
//            home_tabs_name.add("PRODUCTS");
        }
    }


    public void initUi()
    {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        layout_featured = findViewById(R.id.layout_featured_deals);
        vp_featured = (ViewPager) findViewById(R.id.vp_tutorial);
        vp_indicator = (CirclePageIndicator) findViewById(R.id.vp_indicator);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        mDrawer = (DrawerLayout) findViewById(DrawerLayout);
        rl_invite = (RelativeLayout) findViewById(R.id.rl_invite);
        rl_category = (RelativeLayout) findViewById(R.id.rl_category);


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
    private void setUpDrawer() {
        //  Setup Profile
//        setupProfile();


        // Setup List
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true); // Letting the system know that the list objects are of fixed size
        // TODO: Move all strings to strings.xml file
        final ArrayList<NavDrawerAdapter.NavMenuItem> navMenuItems = new ArrayList<NavDrawerAdapter.NavMenuItem>();
        navMenuItems.add(new NavDrawerAdapter.NavMenuItem("Hot Offers", R.drawable.file_image));
        navMenuItems.add(new NavDrawerAdapter.NavMenuItem("Invite & Earn", R.drawable.file_image));
        navMenuItems.add(new NavDrawerAdapter.NavMenuItem("All Stores", R.drawable.file_image));
        navMenuItems.add(new NavDrawerAdapter.NavMenuItem("Notifications", R.drawable.file_image));
        navMenuItems.add(new NavDrawerAdapter.NavMenuItem("About Us", R.drawable.file_image));
        navMenuItems.add(new NavDrawerAdapter.NavMenuItem("Rate our app", R.drawable.file_image));

        NavDrawerAdapter mAdapter = new NavDrawerAdapter(navMenuItems, this);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter.setOnItemClickListener(new NavDrawerAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int index) {
                String navItem = navMenuItems.get(index).title;
                if (navItem.equalsIgnoreCase("Hot Offers")) {
//                    startActivity(new Intent(HomeActivity.this, LikedStories.class));
                } else if (navItem.equalsIgnoreCase("Invite & Earn")) {
                    invite();
                } else if (navItem.equalsIgnoreCase("All Stores")) {
//                    startActivity(new Intent(HomeActivity.this, LikedStories.class));
                } else if (navItem.equalsIgnoreCase("Notifications")) {
//                    startActivity(new Intent(HomeActivity.this, PushNotificationActivity.class));
                } else if (navItem.equalsIgnoreCase("About Us")) {
//                    clearSearchHistoryDialog(HomeActivity.this);
                } else if (navItem.equalsIgnoreCase("Rate our app")) {
                    Utils.navigatePlayStore();
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
        }
    }

//    void setTabLayout(int pos) {
//
//            tabLayout.getTabAt(pos).setText(home_tabs_name.get(pos));
//        }

}
