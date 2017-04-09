package com.discount.coupons.discountgali.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.discount.coupons.discountgali.R;
import com.discount.coupons.discountgali.adapter.AdapterTopStores;
import com.discount.coupons.discountgali.model.ModelTopStores;
import com.discount.coupons.discountgali.network.HttpRequestHandler;
import com.discount.coupons.discountgali.network.api.ApiCall;
import com.discount.coupons.discountgali.network.apicall.GetTopStoreApiCall;
import com.discount.coupons.discountgali.utility.Syso;
import com.discount.coupons.discountgali.utility.Utils;
import com.discount.coupons.discountgali.utils.Constants;
import com.discount.coupons.discountgali.utils.DialogUtils;
import com.discount.coupons.discountgali.utils.EndlessRecyclerOnScrollListenerGrid;

import java.util.ArrayList;

/**
 * Created by Dushyant Singh on 1/12/2017.
 */

public class TopStoresFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerTopStores;
    private AdapterTopStores adapterTopStores;
    private ArrayList<ModelTopStores> storesList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private EndlessRecyclerOnScrollListenerGrid endlessScrollListenerGrid;
    private GridLayoutManager gridLayoutManager;
    private Handler handler = new Handler();
    private boolean isTopStoresApiRunning;
    private boolean isLastStoreFound;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_stores, container, false);

        initUi(view);

        setGridlayoitManager();
        setListeners();
        setRecyclerViews();

        return view;

    }

    private void setGridlayoitManager()
    {
        gridLayoutManager = new GridLayoutManager(getContext(), Constants.Grids.Top_stores);

    }
    private void setListeners() {
        endlessScrollListenerGrid = new EndlessRecyclerOnScrollListenerGrid(gridLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {

                Syso.print("HELLO " + "inside onload  more");

                getTopStoresApiCAll(current_page + 1, visibleThreshold);

            }
        };

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                Syso.print("HELLO " + "inside onswipe");

                getTopStoresApiCAll();
            }
        });
    }

    private void getTopStoresApiCAll(int... pagingParams) {
        try {
            Syso.print("HELLO " + "inside api");

            if (isTopStoresApiRunning) {
                if (pagingParams.length == 2) {
                    endlessScrollListenerGrid.decreasePagingCount();
                }
                return;
            }
            if (isLastStoreFound) {
                return;
            }
            isTopStoresApiRunning = true;
            swipeRefreshLayout.setRefreshing(true);
            final ProgressDialog progressDialog = DialogUtils.getProgressDialog(getActivity());
            //progressDialog.show();
            final GetTopStoreApiCall apiCall;
            if (pagingParams.length == 2) {
                apiCall = new GetTopStoreApiCall(pagingParams[0], pagingParams[1]);
            } else {
                apiCall = new GetTopStoreApiCall();
            }
            HttpRequestHandler.getInstance(getActivity().getApplicationContext()).executeRequest(apiCall, new ApiCall.OnApiCallCompleteListener() {

                @Override
                public void onComplete(Exception e) {
                    isTopStoresApiRunning = false;
                    swipeRefreshLayout.setRefreshing(false);
                    DialogUtils.hideProgressDialog(progressDialog);
                    if (e == null) { // Success
                        try {
                            Syso.print("HELLO 6" + "inside response");
                            storesList.addAll(apiCall.getStoreList());
                            adapterTopStores.notifyDataSetChanged();

//                            if (apiCall.getTotalRecords() > 0 && apiCall.getTotalRecords() == storesList.size()) {
//                                isLastStoreFound = true;
//                            }

//                            if(Utils.getVersionCode(getActivity())<apiCall.getServerVersionCode())
//                            {
//                                // UPDATE AVAILABLE
//                                AppUpdateImplementation.updateAvailable();
//                            }
                        } catch (Exception e1) {

                            Syso.print("HELLO " + e1.getMessage());
                            Utils.handleError(getString(R.string.alert_no_deals_availabale), getActivity(), null);
                        }
                    } else { // Failure
                        Syso.print("HELLO 1" + e.getMessage());

                        Utils.handleError(e, getActivity());
                    }

                }
            }, false);
        } catch (Exception e) {
            Syso.print("HELLO 2 " + e.getMessage());

            Utils.handleError(e, getActivity());
        }


    }


    private void setRecyclerViews() {
        setRecyclerViewStores();

    }

    private void setRecyclerViewStores() {
        adapterTopStores = new AdapterTopStores(getActivity(), storesList);
        recyclerTopStores.setLayoutManager(gridLayoutManager);

//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
//            @Override
//            public void onScrolled(RecyclerView rv, int dx, int dy) {
//                super.onScrolled(rv, dx, dy);
//
//                String url;
//
//                visibleItemCount = mRecyclerView.getChildCount();
//                totalItemCount = mGridLayoutManager.getItemCount();
//                firstVisibleItem = mGridLayoutManager.findFirstVisibleItemPosition();
//
//                if (loading) {
//                    if (totalItemCount > previousTotal) {
//                        loading = false;
//                        previousTotal = totalItemCount;
//                        pageCount++;
//                    }
//                }
//                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)){
//                    url = PHOTOS_URL + "&page=" + String.valueOf(pageCount);
//                    sendJSONRequest(url);
//                    loading = true;
//                }
//            }
//        });


//        recyclerTopStores.addOnScrollListener(new EndlessRecyclerOnScrollListenerGrid(gridLayoutManager) {
//            @Override
//            public void onLoadMore(int current_page) {
//
//                Syso.print("HELLO " + "inside onload  more");
//
//                getTopStoresApiCAll(current_page + 1, visibleThreshold);
//            }
//
//
//        });
        recyclerTopStores.addOnScrollListener(endlessScrollListenerGrid);
        recyclerTopStores.setAdapter(adapterTopStores);
    }




    private void initUi(View v) {
        recyclerTopStores = (RecyclerView) v.findViewById(R.id.recycler_stores);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);

    }

    @Override
    public void onRefresh() {
        Syso.print("HELLO " + "inside onrefresh");

        resetLoading();
        swipeRefreshLayout.setRefreshing(false);
        getTopStoresApiCAll();

    }
    private void resetLoading() {
        isLastStoreFound = false;
        storesList.clear();
        if (adapterTopStores != null && recyclerTopStores != null)
            adapterTopStores.notifyDataSetChanged();
        endlessScrollListenerGrid.reset();
    }

    public void scrollToTop() {
        if (gridLayoutManager != null && recyclerTopStores != null && adapterTopStores != null)
            gridLayoutManager.scrollToPositionWithOffset(0, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (adapterTopStores != null && recyclerTopStores != null)
                        adapterTopStores.notifyDataSetChanged();
                } catch (Exception e) {
                }
            }
        });

    }

}
