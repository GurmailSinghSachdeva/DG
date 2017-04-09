package com.discount.coupons.discountgali.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.discount.coupons.discountgali.R;
import com.discount.coupons.discountgali.model.ModelTopStores;
import com.discount.coupons.discountgali.utility.Utils;
import com.discount.coupons.discountgali.utils.ImageLoaderUtils;

import java.util.List;

/**
 * Created by Dushyant Singh on 1/12/2017.
 */

public class AdapterTopStores extends RecyclerView.Adapter<AdapterTopStores.MyViewHolder>{

    private List<ModelTopStores> listStores;
    private Activity context;
    private AdapterTopStores.OnItemClickListener listener;

    public AdapterTopStores(Activity activity,List<ModelTopStores> listStores) {
        this.listStores = listStores;
        this.context = activity;
//        this.listener= (AdapterTopStores.OnItemClickListener) activity;
    }

    @Override
    public AdapterTopStores.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_top_stores_grid, parent, false);

        return new AdapterTopStores.MyViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return listStores.size();
    }

    @Override
    public void onBindViewHolder(AdapterTopStores.MyViewHolder holder, int position) {
        final ModelTopStores modelTopStores = listStores.get(position);


        ImageLoaderUtils.loadImage(modelTopStores.getIcon(), holder.img_store);

        holder.onBind(modelTopStores);

    }

    @Override
    public int getItemCount() {
        return listStores.size();
    }

    public interface OnItemClickListener {
        void onItemClick(ModelTopStores modelTopStores,View view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView img_store;
        private int position;
        private ModelTopStores modelTopStores;


        public MyViewHolder(View itemView) {
            super(itemView);

            img_store = (ImageView) itemView.findViewById(R.id.img_store);

            itemView.setOnClickListener(this);
        }

        public void onBind(ModelTopStores modelTopStores)
        {
            this.modelTopStores = modelTopStores;
        }

        @Override
        public void onClick(View v) {
            Utils.showOffersOnlineStoresActivity(context, modelTopStores);
//            if(recentsModel.getForwardHeader() == 0){
//                listener.onItemClick(recentsModel,v);
//            }
//            listener.onItemClick(recentsModel,v);
        }
    }
}
