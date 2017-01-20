package com.example.lenovo.discountgali.fragment;

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

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.adapter.AdapterLocalDealsCategory;
import com.example.lenovo.discountgali.model.LocalDealCategoryModel;
import com.example.lenovo.discountgali.network.HttpRequestHandler;
import com.example.lenovo.discountgali.network.api.ApiCall;
import com.example.lenovo.discountgali.network.apicall.GetLocalDealCategoryApiCAll;
import com.example.lenovo.discountgali.utility.Syso;
import com.example.lenovo.discountgali.utility.Utils;
import com.example.lenovo.discountgali.utils.Constants;
import com.example.lenovo.discountgali.utils.DialogUtils;
import com.example.lenovo.discountgali.utils.EndlessRecyclerOnScrollListenerGrid;

import java.util.ArrayList;

/**
 * Created by lenovo on 19-01-2017.
 */

public class LocalDealCategoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private AdapterLocalDealsCategory adapterLocalDealsCategory;
    private ArrayList<LocalDealCategoryModel> localDealCategoryList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private EndlessRecyclerOnScrollListenerGrid endlessScrollListenerGrid;
    private GridLayoutManager gridLayoutManager;
    private Handler handler = new Handler();


    private boolean isApiRunning;
    private boolean isLastItemFound;

    public static LocalDealCategoryFragment newInstance(String service_id) {
        LocalDealCategoryFragment fragment = new LocalDealCategoryFragment();

        Bundle args = new Bundle();
        args.putString("tab_name", service_id);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_deal_category, container, false);

        initUi(view);

        setRecyclerViews();

        setListeners();

//        getLocalDealCategoryApiCall(null);

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

                getLocalDealCategoryApiCall(null);
            }
        });
    }

    private void getLocalDealCategoryApiCall(String service_id, int... pagingParams) {
        try {
            Syso.print("HELLO " + "inside api");

            if (isApiRunning) {
//                if (pagingParams.length == 2) {
//                    endlessScrollListenerGrid.decreasePagingCount();
//                }
                return;
            }
            if (isLastItemFound) {
                return;
            }
            isApiRunning = true;
            swipeRefreshLayout.setRefreshing(true);
            final ProgressDialog progressDialog = DialogUtils.getProgressDialog(getActivity());
            //progressDialog.show();
            final GetLocalDealCategoryApiCAll apiCall;

//            apiCall = new GetTopStoreApiCall(getActivity(), service_id, pagingParams[0], pagingParams[1]);
            apiCall = new GetLocalDealCategoryApiCAll(getActivity(), service_id, 1, 15);

            HttpRequestHandler.getInstance(getActivity().getApplicationContext()).executeRequest(apiCall, new ApiCall.OnApiCallCompleteListener() {

                @Override
                public void onComplete(Exception e) {
                    isApiRunning = false;
                    swipeRefreshLayout.setRefreshing(false);
                    DialogUtils.hideProgressDialog(progressDialog);
                    if (e == null) { // Success
                        try {
                            Syso.print("HELLO 6" + "inside response");
                            localDealCategoryList.addAll(apiCall.getList());
                            adapterLocalDealsCategory.notifyDataSetChanged();

                            if (apiCall.getTotalRecords() > 0 && apiCall.getTotalRecords() == localDealCategoryList.size()) {
                                isLastItemFound = true;
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



    private void setRecyclerViews() {
        adapterLocalDealsCategory = new AdapterLocalDealsCategory(getActivity(), localDealCategoryList, null);
        gridLayoutManager = new GridLayoutManager(getActivity(), Constants.Grids.Local_Deal_Categories);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterLocalDealsCategory);

    }


    private void initUi(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);

    }

    @Override
    public void onRefresh() {
        Syso.print("HELLO " + "inside onrefresh");

        resetLoading();
        swipeRefreshLayout.setRefreshing(false);
        getLocalDealCategoryApiCall(null);

    }
    private void resetLoading() {
        isLastItemFound = false;
        localDealCategoryList.clear();
        if (adapterLocalDealsCategory != null && recyclerView != null)
            adapterLocalDealsCategory.notifyDataSetChanged();
        endlessScrollListenerGrid.reset();
    }

    public void scrollToTop() {
        if (gridLayoutManager != null && recyclerView != null && adapterLocalDealsCategory != null)
            gridLayoutManager.scrollToPositionWithOffset(0, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (adapterLocalDealsCategory != null && recyclerView != null)
                        adapterLocalDealsCategory.notifyDataSetChanged();
                } catch (Exception e) {
                }
            }
        });

    }

}
