package com.example.lenovo.discountgali.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
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
import com.example.lenovo.discountgali.network.apicall.GetCategoriesApiCall;
import com.example.lenovo.discountgali.network.apicall.GetOffersOnlineCategoryWise;
import com.example.lenovo.discountgali.network.apicall.GetRecentMessageApiCall;
import com.example.lenovo.discountgali.utility.AlertUtils;
import com.example.lenovo.discountgali.utility.Syso;
import com.example.lenovo.discountgali.utility.Utils;
import com.example.lenovo.discountgali.utils.DialogUtils;
import com.example.lenovo.discountgali.utils.EndlessRecyclerOnScrollListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lenovo on 22-01-2017.
 */

public class CategoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, HomeAdapter.OnItemClickListener, AdapterCategories.OnItemClickListener {

    private RecyclerView                        rv_categories, rv_offers;
    private LinearLayoutManager                 linearLayoutManager, horizontalLayoutManager;
    private EndlessRecyclerOnScrollListener     endlessScrollListener;
    private SwipeRefreshLayout                  swipeRefreshLayout;
    private HomeAdapter                         mAdapter;
    private ArrayList<TopOffers>                topOfferslist = new ArrayList<>();
    private String                              serviceId;
    private int                                 categoryId = 1;
    private AdapterCategories                   adapterCategories;
    private ArrayList<ModelCategories>          categoriesList = new ArrayList<>();
    private boolean isApiRunning;
    private boolean isLastItemFound;
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        initUi(view);
        parseArguments();
        setRecyclerViews();
        setListeners();
        getCategoriesApiCall();

        return view;
    }

    private void setListeners() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                getOffers();
            }
        });

        endlessScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {

                    getOffers(current_page + 1, visibleThreshold);
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
        adapterCategories = new AdapterCategories(getActivity(), categoriesList, this, 1);
        rv_categories.setHasFixedSize(true);
        rv_categories.setAdapter(adapterCategories);
//        rv_categories.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (v.getId() == R.id.recycler_categories) {
//                    v.getParent().requestDisallowInterceptTouchEvent(true);
//                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
//                        case MotionEvent.ACTION_UP:
//                            v.getParent().requestDisallowInterceptTouchEvent(false);
//                            break;
//                    }
//
//                }
//                return false;
//            }
//        });

//        rv_categories.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                horizontalLayoutManager.findLastCompletelyVisibleItemPosition()
//            }
//        });
    }

    private void setOffersRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_offers.setLayoutManager(linearLayoutManager);
        mAdapter = new HomeAdapter(getActivity(), topOfferslist);
        rv_offers.setHasFixedSize(true);
        rv_offers.setAdapter(mAdapter);
    }


    private void getOffers(int... pagingParams) {

        try {
            if (isApiRunning) {
                Syso.print("INSIDE " + "API RUNNING");
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
            final GetOffersOnlineCategoryWise apiCall;
            if (pagingParams.length == 2) {
                apiCall = new GetOffersOnlineCategoryWise(pagingParams[0], pagingParams[1], adapterCategories.checkedCategory);
            } else {
                apiCall = new GetOffersOnlineCategoryWise(adapterCategories.checkedCategory);
            }
            HttpRequestHandler.getInstance(getActivity()).executeRequest(apiCall, new ApiCall.OnApiCallCompleteListener() {
                @Override
                public void onComplete(Exception e) {
//                    dismissProgressDialog();
                    isApiRunning = false;

                    swipeRefreshLayout.setRefreshing(false);
                    if (e == null) {

                        try {
                            ServerResponse<TopOffers> serverResponse = (ServerResponse<TopOffers>) apiCall.getResult();
                            if (serverResponse != null) {
                                switch (serverResponse.baseModel.MessageCode) {
                                    case Code.SUCCESS_MESSAGE_CODE:
                                        topOfferslist.addAll(serverResponse.data);
                                        mAdapter.notifyDataSetChanged();

                                        if(topOfferslist.size()>0 && topOfferslist.size() == apiCall.getTotalRecords())
                                            isLastItemFound = true;

                                        break;
                                    default:
                                        DialogUtils.showAlert(getActivity(), getString(R.string.alert_no_deals_availabale));

                                        break;
                                }
                            }
//                    ServerResponse serverResponse = (ServerResponse) changePasswordApiCall.getResult();
//                    switch (serverResponse.getCode()){
//                        case Code.CHANGED_PASSWORD_SUCCESS:
//                            finish();
//                            AlertUtils.showToast(ChangePasswordActivity.this, serverResponse.getMessage());
//                            break;
//                        default:
//                            AlertUtils.showToast(ChangePasswordActivity.this, !TextUtils.isEmpty(serverResponse.getMessage()) ? serverResponse.getMessage() : getString(R.string.alert_invalid_response));
//                            break;
//                    }
                        } catch (Exception e1) {
                            Utils.handleError(getString(R.string.alert_no_deals_availabale), getActivity(), null);

                        }
                    } else {
                        AlertUtils.showToast(getActivity(), e.getMessage());
                    }
                }
            }, false);

        } catch (Exception e) {
            Utils.handleError(e, getActivity());
        }
    }


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
    public void onRefresh() {
        Syso.print("INSIDE " + "onrefresh Home fragment");

        resetLoading();
        getOffers();

    }
    private void resetLoading() {
        isLastItemFound = false;
        topOfferslist.clear();
        if (mAdapter != null && rv_offers != null)
            mAdapter.notifyDataSetChanged();
        endlessScrollListener.reset();
    }

    public void scrollToTop() {
        if (linearLayoutManager != null && rv_offers != null && mAdapter != null)
            linearLayoutManager.scrollToPositionWithOffset(0, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mAdapter != null && rv_offers != null)
                        mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                }
            }
        });

        animateCategoriesStrip();
    }
    @Override
    public void onItemClicked(TopOffers topOffers) {
        Intent intent = new Intent(getActivity(), OfferDetailActivity.class);
        intent.putExtra("offer", topOffers);
        startActivity(intent);
    }

        private void getCategoriesApiCall() {
        try {

            final GetCategoriesApiCall apiCall;

            apiCall = new GetCategoriesApiCall();

            HttpRequestHandler.getInstance(getActivity().getApplicationContext()).executeRequest(apiCall, new ApiCall.OnApiCallCompleteListener() {

                @Override
                public void onComplete(Exception e) {
                    if (e == null) { // Success
                        try {

                            categoriesList.clear();
                            categoriesList.addAll(apiCall.getCategoriesList());
                            adapterCategories.notifyDataSetChanged();


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


    @Override
    public void onItemClick() {

        onRefresh();
    }

    public void animateCategoriesStrip(){
        if(rv_categories!=null)
        {
            YoYo.with(Techniques.FlipInX)
                    .duration(700)
                    .playOn(rv_categories);
        }
    }
}
