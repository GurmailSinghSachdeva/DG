package com.discount.coupons.discountgali.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.discount.coupons.discountgali.R;
import com.discount.coupons.discountgali.adapter.AdapterLocalDealsCategory;
import com.discount.coupons.discountgali.model.CityModel;
import com.discount.coupons.discountgali.model.LocalDealCategoryModel;
import com.discount.coupons.discountgali.model.ServerResponse;
import com.discount.coupons.discountgali.network.HttpRequestHandler;
import com.discount.coupons.discountgali.network.api.ApiCall;
import com.discount.coupons.discountgali.network.apicall.GetCityListApiCall;
import com.discount.coupons.discountgali.network.apicall.GetLocalDealCategoryApiCAll;
import com.discount.coupons.discountgali.utility.AlertUtils;
import com.discount.coupons.discountgali.utility.Syso;
import com.discount.coupons.discountgali.utility.Utils;
import com.discount.coupons.discountgali.utils.Constants;
import com.discount.coupons.discountgali.utils.DialogUtils;
import com.discount.coupons.discountgali.utils.EndlessRecyclerOnScrollListenerGrid;

import java.util.ArrayList;

/**
 * Created by lenovo on 19-01-2017.
 */

public class LocalDealCategoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterLocalDealsCategory.OnItemClickListener {
    private RecyclerView recyclerView;
    private AdapterLocalDealsCategory adapterLocalDealsCategory;
    private ArrayList<LocalDealCategoryModel> localDealCategoryList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private EndlessRecyclerOnScrollListenerGrid endlessScrollListenerGrid;
    private GridLayoutManager gridLayoutManager;
    private Handler handler = new Handler();
    private ArrayList<CityModel> cityList = new ArrayList<>();


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


        setGridlayoitManager();
        setListeners();
        setRecyclerViews();

//        getLocalDealCategoryApiCall(null);

//        getTopStoresApiCAll(null);

        return view;

    }
    private void setGridlayoitManager()
    {
        gridLayoutManager = new GridLayoutManager(getContext(), Constants.Grids.Local_Deal_Categories);

    }

    private void setListeners() {
        endlessScrollListenerGrid = new EndlessRecyclerOnScrollListenerGrid(gridLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {


                getLocalDealCategoryApiCall(current_page + 1, visibleThreshold);

            }
        };

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                getLocalDealCategoryApiCall();
            }
        });
    }
    private void getCityListApiCAll() {
        try {


            final ProgressDialog progressDialog = DialogUtils.getProgressDialog(getActivity());
            progressDialog.show();
            final GetCityListApiCall apiCall;
                apiCall = new GetCityListApiCall();
            HttpRequestHandler.getInstance(getActivity().getApplicationContext()).executeRequest(apiCall, new ApiCall.OnApiCallCompleteListener() {

                @Override
                public void onComplete(Exception e) {
                    DialogUtils.hideProgressDialog(progressDialog);
                    if (e == null) { // Success
                        try {
                            ServerResponse<CityModel> serverResponse = (ServerResponse<CityModel>) apiCall.getResult();
                            if(serverResponse!=null && serverResponse.data!=null && serverResponse.data.size()>0){
                                cityList.clear();
                                cityList.addAll(serverResponse.data);
                                showCityDialog();
                            }
                            else {
                                DialogUtils.showAlert(getActivity(), getString(R.string.alert_no_deals_availabale));
                            }
                        } catch (Exception e1) {

                            Utils.handleError(e1, getActivity());
                        }
                    } else { // Failure
                        Utils.handleError(e, getActivity());
                    }

                }
            }, false);
        } catch (Exception e) {

            Utils.handleError(e, getActivity());
        }


    }

    private void getLocalDealCategoryApiCall(int... pagingParams) {
        try {

            if (isApiRunning) {
                if (pagingParams.length == 2) {
                    endlessScrollListenerGrid.decreasePagingCount();
                }
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
            if (pagingParams.length == 2) {
                apiCall = new GetLocalDealCategoryApiCAll(pagingParams[0], pagingParams[1]);
            } else {
                apiCall = new GetLocalDealCategoryApiCAll();
            }
            HttpRequestHandler.getInstance(getActivity().getApplicationContext()).executeRequest(apiCall, new ApiCall.OnApiCallCompleteListener() {

                @Override
                public void onComplete(Exception e) {
                    isApiRunning = false;
                    swipeRefreshLayout.setRefreshing(false);
                    DialogUtils.hideProgressDialog(progressDialog);
                    if (e == null) { // Success
                        try {
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

                            Utils.handleError(e1, getActivity());
                        }
                    } else { // Failure
                        Utils.handleError(e, getActivity());
                    }

                }
            }, false);
        } catch (Exception e) {

            Utils.handleError(e, getActivity());
        }


    }



    private void setRecyclerViews() {
        adapterLocalDealsCategory = new AdapterLocalDealsCategory(getActivity(), localDealCategoryList, this);
        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListenerGrid(gridLayoutManager) {
//            @Override
//            public void onLoadMore(int current_page) {
//                getLocalDealCategoryApiCall(current_page + 1, visibleThreshold);
//            }
//        });

        recyclerView.addOnScrollListener(endlessScrollListenerGrid);
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
        getLocalDealCategoryApiCall();

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

    @Override
    public void onItemClick(LocalDealCategoryModel LocalDealCategoryModel, View view) {
getCityListApiCAll();    }
    AlertDialog alertDialog;
    int cityId = -1;
    String cityName = "";

    public void showCityDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.dialog_select_city));

        String[] cityNames = new String[cityList.size()];
        final int[] cityIds = new int[cityList.size()];
        for(int i = 0;i<cityList.size();i++)
        {
            cityNames[i] = cityList.get(i).CityName;
            cityIds[i] = cityList.get(i).CityId;
        }
        builder.setSingleChoiceItems(cityNames, cityId,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Syso.print("position sele >>" + item);
                        cityId = cityIds[item];
                        Syso.print("position id " + cityId);
                    }
                });
        builder.setPositiveButton(getString(R.string.tv_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();

                if(cityId !=-1)
                {
                    for (int i=0;i<cityList.size();i++)
                    {
                        if(cityList.get(i).CityId == cityId)
                            cityName = cityList.get(i).CityName;
                    }
                    Syso.print("position id name " + cityName);

                    Utils.showLocalDealsActivity(getActivity(), cityName, adapterLocalDealsCategory.categoryId);
                    cityId = -1;
                }
                else {
                    AlertUtils.showToast(getActivity(), R.string.alert_please_select_city);
                    showCityDialog();
                }

            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

}
