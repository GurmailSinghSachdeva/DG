package com.example.lenovo.discountgali.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.fragment.TopStoresFragment;
import com.example.lenovo.discountgali.model.LocalDealCategoryModel;
import com.example.lenovo.discountgali.model.ModelCategories;
import com.example.lenovo.discountgali.utility.Syso;

import java.util.List;

/**
 * Created by lenovo on 19-01-2017.
 */

public class AdapterLocalDealsCategory extends RecyclerView.Adapter<AdapterLocalDealsCategory.MyViewHolder> {
    private List<LocalDealCategoryModel> listCategories;
    private Activity context;
    private AdapterLocalDealsCategory.OnItemClickListener listener;
    public static int categoryId = -1;

    public AdapterLocalDealsCategory(Activity activity, List<LocalDealCategoryModel> listCategories, OnItemClickListener listener) {
        this.listCategories = listCategories;
        this.context = activity;
        this.listener= listener;
    }

    @Override
    public AdapterLocalDealsCategory.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_local_deal_category, parent, false);

        return new AdapterLocalDealsCategory.MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(AdapterLocalDealsCategory.MyViewHolder holder, int position) {
        final LocalDealCategoryModel localDealCategoryModel = listCategories.get(position);

        Syso.print("------Adapter" + localDealCategoryModel.getCategoryName());
        holder.categoryName.setText(localDealCategoryModel.getCategoryName());

        holder.onBind(localDealCategoryModel);
        holder.position = position;
//        ImageLoaderUtils.loadImage(LocalDealCategoryModel.getCategoryLogo(), holder.categoryLogo);
    }

    @Override
    public int getItemCount() {
        return listCategories.size();
    }

    public interface OnItemClickListener {
        void onItemClick(LocalDealCategoryModel LocalDealCategoryModel,View view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView categoryLogo ;
        private TextView categoryName;
        private int position;
        private LocalDealCategoryModel localDealCategoryModel;


        public MyViewHolder(View itemView) {
            super(itemView);

            categoryLogo = (ImageView) itemView.findViewById(R.id.icon_category);
            categoryName = (TextView) itemView.findViewById(R.id.tv_local_deal_cat);

            itemView.setOnClickListener(this);
        }

        public void onBind(LocalDealCategoryModel localDealCategoryModel)
        {
            this.localDealCategoryModel = localDealCategoryModel;
        }

        @Override
        public void onClick(View v) {


            categoryId = localDealCategoryModel.getCategoryId();
            listener.onItemClick(localDealCategoryModel, v);
//            if(recentsModel.getForwardHeader() == 0){
//                listener.onItemClick(recentsModel,v);
//            }
//            listener.onItemClick(recentsModel,v);
        }

    }
}
