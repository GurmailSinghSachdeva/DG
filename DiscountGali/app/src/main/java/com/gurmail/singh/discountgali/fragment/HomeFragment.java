package com.gurmail.singh.discountgali.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gurmail.singh.discountgali.R;
import com.gurmail.singh.discountgali.activity.HomeActivity;
import com.gurmail.singh.discountgali.adapter.HomeAdapter;
import com.gurmail.singh.discountgali.model.ServerResponse;
import com.gurmail.singh.discountgali.model.TopOffers;
import com.gurmail.singh.discountgali.network.Code;
import com.gurmail.singh.discountgali.network.HttpRequestHandler;
import com.gurmail.singh.discountgali.network.api.ApiCall;
import com.gurmail.singh.discountgali.network.apicall.GetRecentMessageApiCall;
import com.gurmail.singh.discountgali.utility.Syso;
import com.gurmail.singh.discountgali.utility.Utils;
import com.gurmail.singh.discountgali.utils.DialogUtils;
import com.gurmail.singh.discountgali.utils.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;

/**
 * Created by Dushyant Singh on 11/24/2016.
 */

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, HomeAdapter.OnItemClickListener {
    protected ProgressDialog progressDialog;

    private RecyclerView mRecyclerView, rv_categories;
    private LinearLayoutManager linearLayoutManager;
    private EndlessRecyclerOnScrollListener endlessScrollListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private HomeAdapter mAdapter;
    private ArrayList<TopOffers> topOfferslist = new ArrayList<>();
    private boolean isApiRunning;
    private boolean isLastItemFound;
    private Handler handler = new Handler();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        try{
            initUi(view);
            parseArguments();
            mRecyclerView.setLayoutManager(getGridOrLinearLayoutManager());
            endlessScrollListener = new EndlessRecyclerOnScrollListener(getGridOrLinearLayoutManager()) {
                @Override
                public void onLoadMore(int current_page) {
                    Syso.print("INSIDE " + "onLoadMore Home fragment");

                    getTopOffers(current_page + 1, visibleThreshold);

//                    getTopOffers((current_page * visibleThreshold), visibleThreshold);
                }

                @Override
                public void onFirstElementReached() {
                    HomeActivity.appBarOpenClose();
                }
            };
            mAdapter = new HomeAdapter(getActivity(), topOfferslist);

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addOnScrollListener(endlessScrollListener);

            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {

                    Syso.print("INSIDE " + "onswipe Home fragment");
                    getTopOffers();
                }
            });
        }catch (Exception e)
        {

            Syso.print("EXCEPTION " + e.getMessage());
        }
        return view;
    }

    private LinearLayoutManager getGridOrLinearLayoutManager() {

        if(linearLayoutManager!=null)
            return linearLayoutManager;
        else {
            linearLayoutManager = new LinearLayoutManager(getContext());
            return linearLayoutManager;
        }
    }

    public void showProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
            progressDialog.show();
        }else {
            progressDialog =  new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    /**
     * Method to dismiss progress dialog.
     */
    public void dismissProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
    private void getTopOffers(int... pagingParams) {

        try{

            if (isApiRunning) {
                Syso.print("INSIDE " + "API RUNNING");
                if (pagingParams.length == 2) {
                    endlessScrollListener.decreasePagingCount();
                }
                return;
            }
            if (isLastItemFound) {
                Syso.print("INSIDE " + "last item found");

                return;
            }
            isApiRunning = true;
            swipeRefreshLayout.setRefreshing(true);
            final GetRecentMessageApiCall apiCall;
            if (pagingParams.length == 2) {
                apiCall = new GetRecentMessageApiCall(pagingParams[0], pagingParams[1]);
            } else {
                apiCall = new GetRecentMessageApiCall();
            }
            HttpRequestHandler.getInstance(getActivity()).executeRequest(apiCall, new ApiCall.OnApiCallCompleteListener() {
                @Override
                public void onComplete(Exception e) {
//                    dismissProgressDialog();
                    isApiRunning = false;
                    swipeRefreshLayout.setRefreshing(false);
                    if (e == null) {
                        ServerResponse<TopOffers> serverResponse = (ServerResponse<TopOffers>) apiCall.getResult();
                        if(serverResponse!=null)
                        {
                            switch (serverResponse.baseModel.MessageCode)
                            {
                                case Code.SUCCESS_MESSAGE_CODE:
                                    topOfferslist.addAll(serverResponse.data);
                                    mAdapter.notifyDataSetChanged();

                                    if(apiCall.getTotalRecords() == topOfferslist.size())
                                    {

                                        isLastItemFound = true;
                                    }
                                    break;
                                default:
                                    DialogUtils.showAlert(getActivity(), getString(R.string.alert_no_deals_availabale));

                                    break;
                            }
                        }

                    } else {
                        isApiRunning = false;

                        Utils.handleError(e,getActivity());
                    }
                }
            }, false);
        }catch (Exception e)
        {                    isApiRunning = false;
            Utils.handleError(e,getContext());
        }
    }

    public void initUi(View view)
    {
        rv_categories = (RecyclerView) view.findViewById(R.id.recycler_categories);
        rv_categories.setVisibility(View.GONE);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

    }
    public static HomeFragment newInstance(String service_id) {
        HomeFragment fragment = new HomeFragment();

        Bundle args = new Bundle();
        args.putString("tab_name", service_id);
        fragment.setArguments(args);

        return fragment;
    }
    private void parseArguments() {
//        if (getArguments() != null) {
//            tab_name = getArguments().getString("tab_name");
//            isGrid = getArguments().getBoolean("isGrid");
//            gridNumber = getArguments().getInt("gridNumber");
//        }
    }

    @Override
    public void onRefresh() {
        Syso.print("INSIDE " + "onrefresh Home fragment");

        resetLoading();
        getTopOffers();

    }
    private void resetLoading() {
        isLastItemFound = false;
        topOfferslist.clear();
        if (mAdapter != null && mRecyclerView != null)
            mAdapter.notifyDataSetChanged();
        endlessScrollListener.reset();
    }

    public void scrollToTop() {
        if (getGridOrLinearLayoutManager() != null && mRecyclerView != null && mAdapter != null)
            getGridOrLinearLayoutManager().scrollToPositionWithOffset(0, 0);
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


    @Override
    public void onItemClicked(TopOffers topOffers) {
        Utils.showOfferDetailActivity(getActivity(), topOffers);

    }


}
