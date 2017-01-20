package com.example.lenovo.discountgali.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.fragment.TopStoresFragment;
import com.example.lenovo.discountgali.model.ModelCategories;
import com.example.lenovo.discountgali.utility.Syso;
import com.example.lenovo.discountgali.utils.ImageLoaderUtils;

import java.util.List;

/**
 * Created by Dushyant Singh on 1/12/2017.
 */

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.MyViewHolder>{

    private List<ModelCategories> listCategories;
    private Activity context;
    private OnItemClickListener listener;

    public AdapterCategories(Activity activity, List<ModelCategories> listCategories, TopStoresFragment context) {
        this.listCategories = listCategories;
        this.context = activity;
//        this.listener= (OnItemClickListener) context;
    }

    @Override
    public AdapterCategories.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_categories, parent, false);

        return new AdapterCategories.MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(AdapterCategories.MyViewHolder holder, int position) {
        final ModelCategories modelCategories = listCategories.get(position);

        Syso.print("------Adapter" + modelCategories.getCategoryName());
        holder.categoryName.setText(modelCategories.getCategoryName());
//        ImageLoaderUtils.loadImage(modelCategories.getCategoryLogo(), holder.categoryLogo);
    }

    @Override
    public int getItemCount() {
        return listCategories.size();
    }

    public interface OnItemClickListener {
        void onItemClick(ModelCategories modelCategories,View view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView categoryLogo ;
        private TextView categoryName;
        private int position;
        private ModelCategories modelCategories;


        public MyViewHolder(View itemView) {
            super(itemView);

            categoryLogo = (ImageView) itemView.findViewById(R.id.icon_category);
            categoryName = (TextView) itemView.findViewById(R.id.text_category);

            itemView.setOnClickListener(this);
        }

        public void onBind(ModelCategories modelCategories)
        {
            this.modelCategories = modelCategories;
        }

        @Override
        public void onClick(View v) {
//            if(recentsModel.getForwardHeader() == 0){
//                listener.onItemClick(recentsModel,v);
//            }
//            listener.onItemClick(recentsModel,v);
        }
    }
}
