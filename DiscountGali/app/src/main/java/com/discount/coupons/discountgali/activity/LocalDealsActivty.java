package com.discount.coupons.discountgali.activity;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.discount.coupons.discountgali.R;
import com.discount.coupons.discountgali.adapter.HomeAdapter;
import com.discount.coupons.discountgali.model.ServerResponse;
import com.discount.coupons.discountgali.model.TopOffers;
import com.discount.coupons.discountgali.network.Code;
import com.discount.coupons.discountgali.network.HttpRequestHandler;
import com.discount.coupons.discountgali.network.api.ApiCall;
import com.discount.coupons.discountgali.network.apicall.GetLocalDeals;
import com.discount.coupons.discountgali.utility.Utils;
import com.discount.coupons.discountgali.utils.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;

public class LocalDealsActivty extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private EndlessRecyclerOnScrollListener endlessScrollListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private HomeAdapter mAdapter;
    private ArrayList<TopOffers> topOfferslist = new ArrayList<>();
    private int categoryId = -1;
    private String cityName = "";
    private Handler handler = new Handler();
    private boolean isLastItemFound;
    private boolean isApiRunning;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_deals_activty);

        initUi();
        parseArguments();
        setUpToolbar(R.drawable.signup_back_icon);
        setRecyclerView();
        setListener();
    }

    private void setListener() {
        endlessScrollListener = new EndlessRecyclerOnScrollListener(getGridOrLinearLayoutManager()) {
            @Override
            public void onLoadMore(int current_page) {
                getOffersOnline(current_page + 1, visibleThreshold);
            }

            @Override
            public void onFirstElementReached() {
                HomeActivity.appBarOpenClose();
            }
        };
        mRecyclerView.addOnScrollListener(endlessScrollListener);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                getOffersOnline();
            }
        });


    }

    private void setRecyclerView() {
        mRecyclerView.setLayoutManager(getGridOrLinearLayoutManager());
        mAdapter = new HomeAdapter(this, topOfferslist);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void initUi() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);

    }

    private void parseArguments() {
        try {
            cityName = getIntent().getStringExtra("cityId");
            categoryId = getIntent().getIntExtra("categoryId", -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private LinearLayoutManager getGridOrLinearLayoutManager() {

        if (linearLayoutManager != null)
            return linearLayoutManager;
        else {
            linearLayoutManager = new LinearLayoutManager(this);
            return linearLayoutManager;

        }
    }

    @Override
    public void onRefresh() {
        resetLoading();
        getOffersOnline();

    }

    private void resetLoading() {
        isLastItemFound = false;
        topOfferslist.clear();
        if (mAdapter != null && mRecyclerView != null)
            mAdapter.notifyDataSetChanged();
        endlessScrollListener.reset();
    }

    public void scrollToTop() {
        if (linearLayoutManager != null && mRecyclerView != null && mAdapter != null)
            linearLayoutManager.scrollToPositionWithOffset(0, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mAdapter != null && mRecyclerView != null)
                        mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                }
            }
        });

    }

    private void getOffersOnline(int... pagingParams) {
        try {

            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            };

            if (isApiRunning) {
                if (pagingParams.length == 2) {
                    endlessScrollListener.decreasePagingCount();
                }
                return;
            }
            if (isLastItemFound) {
                return;
            }
            isApiRunning = true;
            swipeRefreshLayout.setRefreshing(true);
            final GetLocalDeals apiCall;
            if (pagingParams.length == 2) {
                apiCall = new GetLocalDeals(pagingParams[0], pagingParams[1], categoryId, cityName);
            } else {
                apiCall = new GetLocalDeals(categoryId, cityName);
            }
            HttpRequestHandler.getInstance(this).executeRequest(apiCall, new ApiCall.OnApiCallCompleteListener() {
                @Override
                public void onComplete(Exception e) {
                    isApiRunning = false;
                    swipeRefreshLayout.setRefreshing(false);
                    if (e == null) {
                        ServerResponse<TopOffers> serverResponse = (ServerResponse<TopOffers>) apiCall.getResult();
                        if (serverResponse != null) {
                            switch (serverResponse.baseModel.MessageCode) {
                                case Code.SUCCESS_MESSAGE_CODE:
                                    topOfferslist.addAll(serverResponse.data);
                                    mAdapter.notifyDataSetChanged();

                                    if (topOfferslist.size() == apiCall.getTotalRecords())
                                        isLastItemFound = true;
                                    break;
                                default:

                                    if(!LocalDealsActivty.this.isFinishing())
                                        Utils.handleError(getString(R.string.alert_no_deals_availabale), LocalDealsActivty.this, runnable);
                                    break;
                            }
                        }

                    } else {
                        Utils.handleError(e, LocalDealsActivty.this, runnable);
                    }
                }
            }, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpToolbar(int navId) {
        mToolBar.setNavigationIcon(navId);
        setSupportActionBar(mToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
