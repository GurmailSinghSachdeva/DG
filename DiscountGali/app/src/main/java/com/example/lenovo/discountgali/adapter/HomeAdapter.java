package com.example.lenovo.discountgali.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.fragment.HomeFragment;
import com.example.lenovo.discountgali.model.TopOffers;
import com.example.lenovo.discountgali.utility.Utils;
import com.example.lenovo.discountgali.utils.Constants;
import com.example.lenovo.discountgali.utils.ImageLoaderUtils;

import java.util.ArrayList;

/**
 * Created by Dushyant Singh on 11/24/2016.
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private OnItemClickListener onItemClickListener;
    private final LayoutInflater inflater;
    private Activity context;
    private ArrayList<TopOffers> topOfferslist = new ArrayList<>();
    public HomeAdapter(Activity context, ArrayList<TopOffers> topOfferslist) {
        this.context = context;
        this.topOfferslist = topOfferslist;
        inflater = LayoutInflater.from(context);
//        this.onItemClickListener = (OnItemClickListener) context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.row_top_offers, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

TopOffers topOffers = topOfferslist.get(position);
        MyViewHolder viewHolder = (MyViewHolder) holder;


        if(topOffers.OnlineDeal_Type == Constants.typeOffline){
            ImageLoaderUtils.loadImage(topOffers.background, viewHolder.ivBackOffer);

            viewHolder.brandname.setVisibility(View.VISIBLE);
            viewHolder.brandname.setTextColor(context.getResources().getColor(R.color.colorAccent));
            viewHolder.brandname.setText(topOffers.BrandName);
            viewHolder.tvViewOffer.setVisibility(View.VISIBLE);
            viewHolder.tvViewOffer.setTextColor(context.getResources().getColor(R.color.white));
            viewHolder.tvViewOffer.setText(topOffers.OnlineDeal_Offer);

            viewHolder.offertitle.setVisibility(View.INVISIBLE);
            viewHolder.date.setVisibility(View.INVISIBLE);
            viewHolder.tvLoadingIocn.setVisibility(View.VISIBLE);

        }
        else {
            viewHolder.brandname.setVisibility(View.VISIBLE);
            viewHolder.brandname.setTextColor(context.getResources().getColor(R.color.colorAccent));
            viewHolder.offertitle.setVisibility(View.VISIBLE);
            viewHolder.date.setVisibility(View.VISIBLE);
            viewHolder.tvViewOffer.setVisibility(View.VISIBLE);
            viewHolder.tvViewOffer.setTextColor(context.getResources().getColor(R.color.colorPrimary));

            viewHolder.tvLoadingIocn.setVisibility(View.GONE);
            viewHolder.brandname.setText(topOffers.BrandName);
            viewHolder.offertitle.setText(topOffers.OnlineDeal_Offer);
            viewHolder.date.setText("Validity: " + topOffers.OnlineDeal_EndDate);
            ImageLoaderUtils.loadImage(topOffers.OnlineDeal_Logo, viewHolder.dealLogo);
        }


    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return topOfferslist.size();
    }

    public TopOffers getItem(int position) {
        if (topOfferslist == null || topOfferslist.size() < position)
            return null;
        return topOfferslist.get(position);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView brandname,date, branddesc, offertitle, tvViewOffer, tvLoadingIocn;
        public ImageView dealLogo, ivBackOffer;
        public RelativeLayout ll_offer_back;
        public MyViewHolder(View itemView) {
            super(itemView);

            brandname = (TextView) itemView.findViewById(R.id.brandname);
//            branddesc = (TextView) itemView.findViewById(R.id.branddesc);
            date = (TextView) itemView.findViewById(R.id.date_exp);
            offertitle = (TextView) itemView.findViewById(R.id.offertitle);
            tvLoadingIocn = (TextView) itemView.findViewById(R.id.loadingIcon);

            dealLogo = (ImageView) itemView.findViewById(R.id.dealLogo);
            tvViewOffer = (TextView) itemView.findViewById(R.id.tv_view_offer);

            ivBackOffer = (ImageView) itemView.findViewById(R.id.iv_back_offer);
            ll_offer_back = (RelativeLayout) itemView.findViewById(R.id.layout_offer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Utils.showOfferDetailActivity(context, topOfferslist.get(getAdapterPosition()));
//            onItemClickListener.onItemClicked(topOfferslist.get(getAdapterPosition()));
        }
    }
    public interface OnItemClickListener {
        void onItemClicked(TopOffers topOffers);
    }



}