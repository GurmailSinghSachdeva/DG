package com.example.lenovo.discountgali.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.adapter.AdapterCategories;
import com.example.lenovo.discountgali.adapter.AdapterTopStores;
import com.example.lenovo.discountgali.model.ModelCategories;
import com.example.lenovo.discountgali.model.ModelTopStores;
import com.example.lenovo.discountgali.model.TopOffers;
import com.example.lenovo.discountgali.network.HttpRequestHandler;
import com.example.lenovo.discountgali.network.api.ApiCall;
import com.example.lenovo.discountgali.network.apicall.GetCategoriesApiCall;
import com.example.lenovo.discountgali.network.apicall.GetTopStoreApiCall;
import com.example.lenovo.discountgali.utility.Syso;
import com.example.lenovo.discountgali.utility.Utils;
import com.example.lenovo.discountgali.utils.Constants;
import com.example.lenovo.discountgali.utils.DialogUtils;
import com.example.lenovo.discountgali.utils.EndlessRecyclerOnScrollListener;
import com.example.lenovo.discountgali.utils.EndlessRecyclerOnScrollListenerGrid;

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

        setRecyclerViews();

        setListeners();

//        getCategoriesApiCall();

//        getTopStoresApiCAll(null);

        return view;

    }

    private void setListeners() {
        endlessScrollListenerGrid = new EndlessRecyclerOnScrollListenerGrid(gridLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {

                Syso.print("HELLO " + "inside onload  more");

//                getTopStoresApiCAll(null, (current_page * visibleThreshold), visibleThreshold);

            }
        };

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                Syso.print("HELLO " + "inside onswipe");

                getTopStoresApiCAll(null);
            }
        });
    }

    private void getTopStoresApiCAll(String service_id, int... pagingParams) {
        try {
            Syso.print("HELLO " + "inside api");

            if (isTopStoresApiRunning) {
//                if (pagingParams.length == 2) {
//                    endlessScrollListenerGrid.decreasePagingCount();
//                }
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

//            apiCall = new GetTopStoreApiCall(getActivity(), service_id, pagingParams[0], pagingParams[1]);
            apiCall = new GetTopStoreApiCall(getActivity(), service_id, 1, 15);

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

                            if (apiCall.getTotalRecords() > 0 && apiCall.getTotalRecords() == storesList.size()) {
                                isLastStoreFound = true;
                            }

//                            if(Utils.getVersionCode(getActivity())<apiCall.getServerVersionCode())
//                            {
//                                // UPDATE AVAILABLE
//                                AppUpdateImplementation.updateAvailable();
//                            }
                        } catch (Exception e1) {

                            Syso.print("HELLO " + e1.getMessage());
                            Utils.handleError(e1, getActivity());
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

//    private void getCategoriesApiCall() {
//        try {
//            if (isCategoryApiRunning) {
//
//                return;
//            }
//
//            isCategoryApiRunning = true;
//
//            final ProgressDialog progressDialog = DialogUtils.getProgressDialog(getActivity());
//            //progressDialog.show();
//            final GetCategoriesApiCall apiCall;
//
//            apiCall = new GetCategoriesApiCall(getActivity(), null, 1, 15);
//
//            HttpRequestHandler.getInstance(getActivity().getApplicationContext()).executeRequest(apiCall, new ApiCall.OnApiCallCompleteListener() {
//
//                @Override
//                public void onComplete(Exception e) {
//                    isCategoryApiRunning = false;
////                    swipeRefreshLayout.setRefreshing(false);
//                    DialogUtils.hideProgressDialog(progressDialog);
//                    if (e == null) { // Success
//                        try {
//                            Syso.print("-----------Inside--------" + "onresponse category");
//                            categoriesList.clear();
//                            categoriesList.addAll(apiCall.getCategoriesList());
//                            Syso.print("-----------Inside" + categoriesList.size());
////                            if(categoriesList!=null && !categoriesList.isEmpty())
////                            adapterCategories.notifyDataSetChanged();
////                            else recyclerCategories.setVisibility(View.GONE);
////                            setRecyclerViewCategories();
//                            adapterCategories.notifyDataSetChanged();
//
//
//                        } catch (Exception e1) {
//                            Utils.handleError(e1, getActivity());
//                        }
//                    } else { // Failure
//                        Utils.handleError(e, getActivity());
//                    }
//
//                }
//            }, false);
//        } catch (Exception e) {
//            Utils.handleError(e, getActivity());
//        }
//
//
//    }


    private void setRecyclerViews() {
        setRecyclerViewStores();

    }

    private void setRecyclerViewStores() {
        adapterTopStores = new AdapterTopStores(getActivity(), storesList);
        gridLayoutManager = new GridLayoutManager(getActivity(), Constants.Grids.Top_stores);
        recyclerTopStores.setLayoutManager(gridLayoutManager);
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
        getTopStoresApiCAll(null);

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
