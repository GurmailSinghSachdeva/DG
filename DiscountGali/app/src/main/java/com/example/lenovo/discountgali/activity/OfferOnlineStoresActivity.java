package com.example.lenovo.discountgali.activity;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.adapter.HomeAdapter;
import com.example.lenovo.discountgali.fragment.HomeFragment;
import com.example.lenovo.discountgali.model.ModelTopStores;
import com.example.lenovo.discountgali.model.ServerResponse;
import com.example.lenovo.discountgali.model.TopOffers;
import com.example.lenovo.discountgali.network.Code;
import com.example.lenovo.discountgali.network.HttpRequestHandler;
import com.example.lenovo.discountgali.network.api.ApiCall;
import com.example.lenovo.discountgali.network.apicall.GetOffersOnlineStoreWise;
import com.example.lenovo.discountgali.network.apicall.GetRecentMessageApiCall;
import com.example.lenovo.discountgali.utility.AlertUtils;
import com.example.lenovo.discountgali.utility.Syso;
import com.example.lenovo.discountgali.utility.Utils;
import com.example.lenovo.discountgali.utils.DialogUtils;
import com.example.lenovo.discountgali.utils.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;

import static com.example.lenovo.discountgali.R.id.view;

public class OfferOnlineStoresActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private EndlessRecyclerOnScrollListener endlessScrollListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private HomeAdapter mAdapter;
    private ArrayList<TopOffers> topOfferslist = new ArrayList<>();
    private int storeId = -1;
    private Handler handler = new Handler();
    private boolean isLastItemFound;
    private boolean isApiRunning;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_online_stores);

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
            ModelTopStores modelTopStores = (ModelTopStores) getIntent().getSerializableExtra("store");
            storeId = modelTopStores.getId();
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
            final GetOffersOnlineStoreWise apiCall;
            if (pagingParams.length == 2) {
                apiCall = new GetOffersOnlineStoreWise(pagingParams[0], pagingParams[1], storeId);
            } else {
                apiCall = new GetOffersOnlineStoreWise(storeId);
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
                                    DialogUtils.showAlert(OfferOnlineStoresActivity.this, getString(R.string.alert_no_deals_availabale));
                                    break;
                            }
                        }

                    } else {
                        Utils.handleError(e, OfferOnlineStoresActivity.this);
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