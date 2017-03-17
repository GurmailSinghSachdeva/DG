package com.example.lenovo.discountgali.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.model.FeaturedModel;
import com.example.lenovo.discountgali.model.LocalDealCategoryModel;
import com.example.lenovo.discountgali.utility.Syso;
import com.example.lenovo.discountgali.utility.Utils;
import com.example.lenovo.discountgali.utils.ImageLoaderUtils;

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
            Utils.openUrl(context, deal.redirect_url);
//            listener.onItemClick(deal, v);
        }

    }
}
