package com.example.lenovo.discountgali.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.activity.HomeActivity;
import com.example.lenovo.discountgali.activity.OfferDetailActivity;
import com.example.lenovo.discountgali.adapter.AdapterCategories;
import com.example.lenovo.discountgali.adapter.HomeAdapter;
import com.example.lenovo.discountgali.model.ModelCategories;
import com.example.lenovo.discountgali.model.ServerResponse;
import com.example.lenovo.discountgali.model.TopOffers;
import com.example.lenovo.discountgali.network.Code;
import com.example.lenovo.discountgali.network.HttpRequestHandler;
import com.example.lenovo.discountgali.network.api.ApiCall;
import com.example.lenovo.discountgali.network.apicall.GetRecentMessageApiCall;
import com.example.lenovo.discountgali.utility.AlertUtils;
import com.example.lenovo.discountgali.utils.EndlessRecyclerOnScrollListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lenovo on 22-01-2017.
 */

public class CategoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, HomeAdapter.OnItemClickListener {

    private RecyclerView                        rv_categories, rv_offers;
    private LinearLayoutManager                 linearLayoutManager, horizontalLayoutManager;
    private EndlessRecyclerOnScrollListener     endlessScrollListener;
    private SwipeRefreshLayout                  swipeRefreshLayout;
    private HomeAdapter                         mAdapter;
    private ArrayList<TopOffers>                topOfferslist = new ArrayList<>();
    private String                              serviceId;
    private int                                 categoryId = 0;
    private AdapterCategories                   adapterCategories;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        initUi(view);
        parseArguments();
        setRecyclerViews();
        setListeners();

        return view;
    }

    private void setListeners() {
        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//
//                getTopOffers();
//            }
//        });

        endlessScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {

//                    getOffers(serviceId, current_page, visibleThreshold)
            }

            @Override
            public void onFirstElementReached() {
                HomeActivity.appBarOpenClose();
            }
        };
        rv_offers.addOnScrollListener(endlessScrollListener);
    }

    private void setRecyclerViews() {
        setOffersRecyclerView();
        setCategoryRecyclerView();

    }

    private void setCategoryRecyclerView() {
        horizontalLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        rv_categories.setLayoutManager(horizontalLayoutManager);
        adapterCategories = new AdapterCategories(getActivity(), Arrays.asList(getResources().getStringArray(R.array.categories)));
        rv_categories.setHasFixedSize(true);
        rv_categories.setAdapter(adapterCategories);
    }

    private void setOffersRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_offers.setLayoutManager(linearLayoutManager);
        mAdapter = new HomeAdapter(getActivity(), topOfferslist);
        rv_offers.setHasFixedSize(true);
        rv_offers.setAdapter(mAdapter);
    }


//    private void getOffers() {
//
//        try{
//
//            if(isApiRunning)
//            {
//                return;
//            }
//        swipeRefreshLayout.setRefreshing(true);
//        final GetOffersCategoryWiseApiCall apiCall = new GetOffersCategoryWiseApiCall();
//        HttpRequestHandler.getInstance(getActivity()).executeRequest(apiCall, new ApiCall.OnApiCallCompleteListener() {
//            @Override
//            public void onComplete(Exception e) {
////                    dismissProgressDialog();
//                swipeRefreshLayout.setRefreshing(false);
//                if (e == null) {
//                    ServerResponse<TopOffers> serverResponse = (ServerResponse<TopOffers>) changePasswordApiCall.getResult();
//                    if(serverResponse!=null)
//                    {
//                        switch (serverResponse.baseModel.MessageCode)
//                        {
//                            case Code.SUCCESS_MESSAGE_CODE:
//                                topOfferslist.addAll(serverResponse.data);
//                                mAdapter.notifyDataSetChanged();
//                                break;
//                        }
//                    }
////                    ServerResponse serverResponse = (ServerResponse) changePasswordApiCall.getResult();
////                    switch (serverResponse.getCode()){
////                        case Code.CHANGED_PASSWORD_SUCCESS:
////                            finish();
////                            AlertUtils.showToast(ChangePasswordActivity.this, serverResponse.getMessage());
////                            break;
////                        default:
////                            AlertUtils.showToast(ChangePasswordActivity.this, !TextUtils.isEmpty(serverResponse.getMessage()) ? serverResponse.getMessage() : getString(R.string.alert_invalid_response));
////                            break;
////                    }
//                } else {
//                    AlertUtils.showToast(getActivity(), e.getMessage());
//                }
//            }
//        }, false);
//
//
////        for(int i=0; i<100; i++)
////        {
////            topOfferslist.add(new TopOffers());
////        }
////        mAdapter.notifyDataSetChanged();
//    }
//
    public void initUi(View view)
    {
        rv_offers = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        rv_categories = (RecyclerView) view.findViewById(R.id.recycler_categories);

    }
    public static CategoryFragment newInstance(String service_id) {
        CategoryFragment fragment = new CategoryFragment();

        Bundle args = new Bundle();
        args.putString("tab_name", service_id);
        fragment.setArguments(args);

        return fragment;
    }
    private void parseArguments() {
        if (getArguments() != null) {
            serviceId = getArguments().getString("tab_name");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClicked(TopOffers topOffers) {
        Intent intent = new Intent(getActivity(), OfferDetailActivity.class);
        intent.putExtra("offer", topOffers);
        startActivity(intent);
    }
}
