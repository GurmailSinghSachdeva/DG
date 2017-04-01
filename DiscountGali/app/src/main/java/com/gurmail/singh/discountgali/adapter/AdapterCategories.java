package com.gurmail.singh.discountgali.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gurmail.singh.discountgali.R;
import com.gurmail.singh.discountgali.interfaces.CategoryInterface;
import com.gurmail.singh.discountgali.model.ModelCategories;

import java.util.ArrayList;

/**
 * Created by Dushyant Singh on 1/12/2017.
 */

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.MyViewHolder>{

    private CategoryInterface categoryInterface;
    private int[]                               categoryIds = { R.drawable.all_category,
            R.drawable.automobile,
            R.drawable.bags,
            R.drawable.books_stationary,
            R.drawable.camera,
            R.drawable.electronics,
            R.drawable.eyecare,
            R.drawable.flowers_gifts,
            R.drawable.food_dining,
            R.drawable.fun_entertainment,
            R.drawable.grocery,
            R.drawable.health_beauty,
            R.drawable.home_kitchen,
            R.drawable.hotels_travels,
            R.drawable.kids_clothing,
            R.drawable.kids_toys,
            R.drawable.laptop_computer,
            R.drawable.lingerie,
            R.drawable.medical,
            R.drawable.mens_fashion,
            R.drawable.mobile,
            R.drawable.others,
            R.drawable.recharge,
            R.drawable.health_fitness,
            R.drawable.watch,
            R.drawable.women_fashion};


    private ArrayList<ModelCategories> listCategories;
    private Activity context;
    private OnItemClickListener listener;
    public int checkedCategory;

    public AdapterCategories(Activity activity, ArrayList<ModelCategories> listCategories, OnItemClickListener listener, int checkedCategory) {
        this.listCategories = listCategories;
        this.context = activity;
        this.listener = listener;
        this.checkedCategory = checkedCategory;
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

        holder.categoryName.setText(modelCategories.getCategoryName());
//        ImageLoaderUtils.loadImage(modelCategories.getCategoryLogo(), holder.categoryLogo);

        holder.categoryLogo.setImageResource(categoryIds[position]);
        if(modelCategories.getCategoryId() == checkedCategory)
        {
            holder.containerCategories.setBackgroundColor(context.getResources().getColor(R.color.gray));
        }else {
            holder.containerCategories.setBackgroundColor(context.getResources().getColor(R.color.white));

        }
       holder.position = position;
        holder.onBind(modelCategories);
    }

    @Override
    public int getItemCount() {
        return listCategories.size();
    }

    public interface OnItemClickListener {
        void onItemClick();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView categoryLogo ;
        private TextView categoryName;
        private int position;
        private ModelCategories modelCategories;
        private RelativeLayout containerCategories;


        public MyViewHolder(View itemView) {
            super(itemView);

            categoryLogo = (ImageView) itemView.findViewById(R.id.icon_category);
            categoryName = (TextView) itemView.findViewById(R.id.text_category);
            containerCategories = (RelativeLayout) itemView.findViewById(R.id.container_categories);
            itemView.setOnClickListener(this);
        }

        public void onBind(ModelCategories modelCategories)
        {
            this.modelCategories = modelCategories;
        }

        @Override
        public void onClick(View v) {

            checkedCategory = modelCategories.getCategoryId();
            modelCategories.setCheck(!modelCategories.isCheck());       // this is not doing anything but used si that adapter could know that some chenge has been done on the arraylist and so notifydatasetchanged should work
            notifyDataSetChanged();
            listener.onItemClick();


        }
    }
}
