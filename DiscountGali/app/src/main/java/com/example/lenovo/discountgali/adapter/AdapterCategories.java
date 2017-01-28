package com.example.lenovo.discountgali.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.interfaces.CategoryInterface;
import com.example.lenovo.discountgali.model.ModelCategories;

import java.util.List;

/**
 * Created by Dushyant Singh on 1/12/2017.
 */

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.MyViewHolder>{

    private CategoryInterface categoryInterface;
    private int[]                               categoryIds = { R.drawable.file_image,
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
            R.drawable.file_image,
            R.drawable.recharge,
            R.drawable.health_fitness,
            R.drawable.watch,
            R.drawable.women_fashion};

    private List<String> listCategories;
    private Activity context;
    private OnItemClickListener listener;

    public AdapterCategories(Activity activity, List<String> listCategories) {
        this.listCategories = listCategories;
        this.context = activity;
        categoryInterface = (CategoryInterface) activity;
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
        final String modelCategories = listCategories.get(position);

        holder.categoryName.setText(modelCategories);
        holder.categoryLogo.setImageResource(categoryIds[position]);
        if(position == 1)
        {
            holder.containerCategories.setBackgroundColor(context.getResources().getColor(R.color.gray));
        }
        else {
            holder.containerCategories.setBackgroundColor(context.getResources().getColor(R.color.white));

        }
//        holder.onBind(modelCategories);
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
//            if(recentsModel.getForwardHeader() == 0){
//                listener.onItemClick(recentsModel,v);
//            }
//            listener.onItemClick(recentsModel,v);
        }
    }
}
