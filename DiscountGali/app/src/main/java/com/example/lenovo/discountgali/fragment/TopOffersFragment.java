package com.example.lenovo.discountgali.fragment;

import android.app.ProgressDialog;
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
import com.example.lenovo.discountgali.adapter.HomeAdapter;
import com.example.lenovo.discountgali.model.ServerResponse;
import com.example.lenovo.discountgali.model.TopOffers;
import com.example.lenovo.discountgali.network.Code;
import com.example.lenovo.discountgali.network.HttpRequestHandler;
import com.example.lenovo.discountgali.network.api.ApiCall;
import com.example.lenovo.discountgali.network.apicall.GetRecentMessageApiCall;
import com.example.lenovo.discountgali.utility.AlertUtils;
import com.example.lenovo.discountgali.utils.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;

/**
 * Created by Dushyant Singh on 11/24/2016.
 */
public class TopOffersFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    protected ProgressDialog progressDialog;

    private String tab_name;
    private boolean isGrid;
    private int gridNumber;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private EndlessRecyclerOnScrollListener endlessScrollListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private HomeAdapter mAdapter;
    private ArrayList<TopOffers> topOfferslist = new ArrayList<>();


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

//                    getTopOffers((current_page * visibleThreshold), visibleThreshold)
//                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFirstElementReached() {
                    HomeActivity.appBarOpenClose();
                }
            };
            mRecyclerView.addOnScrollListener(endlessScrollListener);
            mAdapter = new HomeAdapter(getActivity(), topOfferslist);
            mRecyclerView.setAdapter(mAdapter);
            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {

                    getTopOffers();
                }
            });
        }catch (Exception e)
        {

        }
        return view;
    }

    private LinearLayoutManager getGridOrLinearLayoutManager() {

        if(linearLayoutManager!=null)
            return linearLayoutManager;
        return new LinearLayoutManager(getContext());
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
    private void getTopOffers() {
//            showProgressDialog();
            //TODO: change parameters

        swipeRefreshLayout.setRefreshing(true);
            final GetRecentMessageApiCall changePasswordApiCall = new GetRecentMessageApiCall();
            HttpRequestHandler.getInstance(getActivity()).executeRequest(changePasswordApiCall, new ApiCall.OnApiCallCompleteListener() {
                @Override
                public void onComplete(Exception e) {
//                    dismissProgressDialog();
                    swipeRefreshLayout.setRefreshing(false);
                    if (e == null) {
                        ServerResponse<TopOffers> serverResponse = (ServerResponse<TopOffers>) changePasswordApiCall.getResult();
                        if(serverResponse!=null)
                        {
                            switch (serverResponse.baseModel.MessageCode)
                            {
                                case Code.SUCCESS_MESSAGE_CODE:
                                    topOfferslist.addAll(serverResponse.data);
                                    mAdapter.notifyDataSetChanged();
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
                    } else {
                        AlertUtils.showToast(getActivity(), R.string.alert_invalid_response);
                    }
                }
            }, false);


//        for(int i=0; i<100; i++)
//        {
//            topOfferslist.add(new TopOffers());
//        }
//        mAdapter.notifyDataSetChanged();
    }

    public void initUi(View view)
    {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        view.findViewById(R.id.incl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Utils.showWriteARoastActivity(getActivity());
                Toast.makeText(v.getContext(),"Under Development",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public static TopOffersFragment newInstance(String service_id) {
        TopOffersFragment fragment = new TopOffersFragment();

        Bundle args = new Bundle();
        args.putString("tab_name", service_id);
        fragment.setArguments(args);

        return fragment;
    }
    public void scrollToTop() {
        if (linearLayoutManager != null && mRecyclerView != null && mAdapter != null)
            linearLayoutManager.scrollToPositionWithOffset(0, 0);
    }
    private void parseArguments() {
        if (getArguments() != null) {
            tab_name = getArguments().getString("tab_name");
            isGrid = getArguments().getBoolean("isGrid");
            gridNumber = getArguments().getInt("gridNumber");
        }
    }


    @Override
    public void onRefresh() {
swipeRefreshLayout.setRefreshing(false);
    }
}
