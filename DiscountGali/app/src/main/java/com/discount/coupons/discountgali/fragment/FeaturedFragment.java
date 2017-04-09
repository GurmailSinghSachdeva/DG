package com.discount.coupons.discountgali.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.discount.coupons.discountgali.R;
import com.discount.coupons.discountgali.model.FeaturedModel;
import com.discount.coupons.discountgali.model.ModelTopStores;
import com.discount.coupons.discountgali.model.ServerResponse;
import com.discount.coupons.discountgali.model.TopOffers;
import com.discount.coupons.discountgali.network.Code;
import com.discount.coupons.discountgali.network.HttpRequestHandler;
import com.discount.coupons.discountgali.network.api.ApiCall;
import com.discount.coupons.discountgali.network.apicall.GetDealDetails;
import com.discount.coupons.discountgali.utility.Utils;
import com.discount.coupons.discountgali.utils.DialogUtils;
import com.discount.coupons.discountgali.utils.ImageLoaderUtils;

/**
 * Created by Dushyant Singh on 11/24/2016.
 */
public class FeaturedFragment extends Fragment implements View.OnClickListener {
    public int a = 0;

    private FeaturedModel featuredModel;
    private ImageView ivIcon;

    public static FeaturedFragment newInstance(FeaturedModel featuredModel) {
        FeaturedFragment fragment = new FeaturedFragment();

        Bundle args = new Bundle();
        args.putSerializable("featured_model", featuredModel);
        fragment.setArguments(args);

        return fragment;
    }

    public FeaturedFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_featured, container, false);
        parseArguments();

        ((TextView)rootView.findViewById(R.id.tv_title)).setText(featuredModel.title);
        ((TextView)rootView.findViewById(R.id.tv_text)).setText(featuredModel.description);
        ivIcon = (ImageView) rootView.findViewById(R.id.iv_icon);

        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(featuredModel.dealId == 0){
                    Utils.openUrl(getActivity(), featuredModel.redirect_url);
                    return;
                }

                if(featuredModel.isOnloine)
                {
                    ModelTopStores modelTopStores = new ModelTopStores();
                    modelTopStores.setId(featuredModel.dealId);
                    if(featuredModel.dealId>0)
                        Utils.showOffersOnlineStoresActivity(getActivity(), modelTopStores);
                    else {
                        Utils.handleError(getActivity().getString(R.string.alert_deal_not_available_expired),getActivity(), null);
                    }
                }
                else {
                    getDealDeatils(featuredModel.dealId);
                }

//                Utils.openUrl(getActivity(), featuredModel.redirect_url);
            }
        });
        ImageLoaderUtils.loadImage(featuredModel.icon, ivIcon);
//        ((ImageView)rootView.findViewById(R.id.iv_icon)).setImageURI(Uri.parse(featuredModel.icon));
        return rootView;
    }

    private void parseArguments() {
        if (getArguments() != null) {
            featuredModel = (FeaturedModel) getArguments().getSerializable("featured_model");
        }
    }

    @Override
    public void onClick(View v) {
//            Toast.makeText(v.getContext(),"Under Development",Toast.LENGTH_SHORT).show();

    }

    private void getDealDeatils(int dealId) {

        try {

            final GetDealDetails apiCall;
            apiCall = new GetDealDetails(dealId);

            final ProgressDialog pDialog = DialogUtils.getProgressDialog(getActivity());
            pDialog.show();
            HttpRequestHandler.getInstance(getActivity().getApplicationContext()).executeRequest(apiCall, new ApiCall.OnApiCallCompleteListener() {
                @Override
                public void onComplete(Exception e) {

                    DialogUtils.hideProgressDialog(pDialog);
                    if (e == null) {
                        ServerResponse<TopOffers> serverResponse = (ServerResponse<TopOffers>) apiCall.getResult();
                        if (serverResponse != null) {
                            switch (serverResponse.baseModel.MessageCode) {
                                case Code.SUCCESS_MESSAGE_CODE:
                                    TopOffers topOffers = apiCall.getDealDetails();
                                    if(topOffers!=null && topOffers.OnlineDealId > 0){
                                        Utils.showOfferDetailActivity(getActivity(), topOffers);

                                    }
                                    else
                                    {
                                        Utils.handleError(getActivity().getString(R.string.alert_deal_not_available_expired),getActivity(), null);
                                    }
                                    break;
                                default:

                                    if(!getActivity().isFinishing())
                                        Utils.handleError(getActivity().getString(R.string.alert_deal_not_available_expired),getActivity(), null);
                                    break;
                            }
                        }

                    } else {
                        Utils.handleError(getActivity().getString(R.string.alert_deal_not_available_expired), getActivity(), null);
                    }
                }
            }, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}