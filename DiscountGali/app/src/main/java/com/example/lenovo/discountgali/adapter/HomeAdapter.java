package com.example.lenovo.discountgali.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.fragment.HomeFragment;
import com.example.lenovo.discountgali.model.TopOffers;
import com.example.lenovo.discountgali.utility.Utils;
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
        viewHolder.brandname.setText(topOffers.BrandName);
//        viewHolder.branddesc.setText(topOffers.OnlineDeal_OfferDescription);
        viewHolder.offertitle.setText(topOffers.OnlineDeal_Offer);
        viewHolder.date.setText("Validity: " + topOffers.OnlineDeal_EndDate);
        ImageLoaderUtils.loadImage(topOffers.OnlineDeal_Logo, viewHolder.dealLogo);

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

        public TextView brandname,date, branddesc, offertitle;
        public ImageView dealLogo;
        public MyViewHolder(View itemView) {
            super(itemView);

            brandname = (TextView) itemView.findViewById(R.id.brandname);
//            branddesc = (TextView) itemView.findViewById(R.id.branddesc);
            date = (TextView) itemView.findViewById(R.id.date_exp);
            offertitle = (TextView) itemView.findViewById(R.id.offertitle);
            dealLogo = (ImageView) itemView.findViewById(R.id.dealLogo);

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