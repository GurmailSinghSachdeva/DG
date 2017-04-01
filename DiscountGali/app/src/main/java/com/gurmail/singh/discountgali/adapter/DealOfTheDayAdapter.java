package com.gurmail.singh.discountgali.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gurmail.singh.discountgali.R;
import com.gurmail.singh.discountgali.model.FeaturedModel;
import com.gurmail.singh.discountgali.model.ModelTopStores;
import com.gurmail.singh.discountgali.model.ServerResponse;
import com.gurmail.singh.discountgali.model.TopOffers;
import com.gurmail.singh.discountgali.network.Code;
import com.gurmail.singh.discountgali.network.HttpRequestHandler;
import com.gurmail.singh.discountgali.network.api.ApiCall;
import com.gurmail.singh.discountgali.network.apicall.GetDealDetails;
import com.gurmail.singh.discountgali.utility.Utils;
import com.gurmail.singh.discountgali.utils.DialogUtils;
import com.gurmail.singh.discountgali.utils.ImageLoaderUtils;

import java.util.List;

/**
 * Created by lenovo on 12-03-2017.
 */

public class DealOfTheDayAdapter extends RecyclerView.Adapter<DealOfTheDayAdapter.MyViewHolder> {
    private List<FeaturedModel> list;
    private Activity context;
    private DealOfTheDayAdapter.OnItemClickListener listener;
    public static int categoryId = -1;

    public DealOfTheDayAdapter(Activity activity, List<FeaturedModel> list, OnItemClickListener listener) {
        this.list = list;
        this.context = activity;
        this.listener= listener;
    }

    @Override
    public DealOfTheDayAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_featured, parent, false);

        return new DealOfTheDayAdapter.MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(DealOfTheDayAdapter.MyViewHolder holder, int position) {
        final FeaturedModel deal = list.get(position);

        holder.onBind(deal);
        holder.position = position;
        ImageLoaderUtils.loadImage(deal.icon, holder.categoryLogo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(FeaturedModel deal,View view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView categoryLogo ;
        private TextView categoryName;
        private int position;
        private FeaturedModel deal;


        public MyViewHolder(View itemView) {
            super(itemView);

            categoryLogo = (ImageView) itemView.findViewById(R.id.iv_icon);

            itemView.setOnClickListener(this);
        }

        public void onBind(FeaturedModel deal)
        {
            this.deal = deal;
        }

        @Override
        public void onClick(View v) {
            if(deal.dealId == 0){
                Utils.openUrl(context, deal.redirect_url);
//                listener.onItemClick(deal, v);
                return;
            }

            if(deal.isOnloine)
            {
                ModelTopStores modelTopStores = new ModelTopStores();
                modelTopStores.setId(deal.dealId);
                if(deal.dealId>0)
                Utils.showOffersOnlineStoresActivity(context, modelTopStores);
                else {
                    Utils.handleError(context.getString(R.string.alert_deal_not_available_expired),context, null);
                }
            }
            else {
                getDealDeatils(deal.dealId);
            }

        }

    }

    private void getDealDeatils(int dealId) {

        try {

            final GetDealDetails apiCall;
            apiCall = new GetDealDetails(dealId);

            final ProgressDialog pDialog = DialogUtils.getProgressDialog(context);
            pDialog.show();
            HttpRequestHandler.getInstance(context.getApplicationContext()).executeRequest(apiCall, new ApiCall.OnApiCallCompleteListener() {
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
                                    Utils.showOfferDetailActivity(context, topOffers);

                                }
                                else
                                {
                                    Utils.handleError(context.getString(R.string.alert_deal_not_available_expired),context, null);
                                }
                                    break;
                                default:

                                    if(!context.isFinishing())
                                        Utils.handleError(context.getString(R.string.alert_deal_not_available_expired),context, null);
                                    break;
                            }
                        }

                    } else {
                        Utils.handleError(context.getString(R.string.alert_deal_not_available_expired), context, null);
                    }
                }
            }, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
