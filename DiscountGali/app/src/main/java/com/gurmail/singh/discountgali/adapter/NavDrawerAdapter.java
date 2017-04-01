package com.gurmail.singh.discountgali.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gurmail.singh.discountgali.R;

import java.util.ArrayList;

public class NavDrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<NavMenuItem> data;
    private OnItemClickListener onItemClickListener = null;
    private boolean isHeaderClickable = false;

    private Context context;
    public interface OnItemClickListener {
        void onItemClicked(int index);
    }

    public NavDrawerAdapter(ArrayList<NavMenuItem> data, Context context) {
        this.data = data;
        this.context=context;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nav_header,parent,false);
        return new VHHeader(v);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NavMenuItem dataItem = getItem(position);
        VHHeader item = (VHHeader) holder;

        item.title.setText(dataItem.title);
        item.icon.setImageResource(dataItem.icon_id);
        }


    @Override
    public int getItemCount() {
        return data.size();
    }



    private NavMenuItem getItem(int position) {
        return data.get(position);
    }





    class VHHeader extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;

        public VHHeader(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txt_title);
            icon = (ImageView) itemView.findViewById(R.id.nav_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener != null) {
                        onItemClickListener.onItemClicked(getAdapterPosition());
                    }
                }
            });
        }
    }



    public static class NavMenuItem {

        public String title = "";
        public int icon_id = 0;
        public boolean isBottomLine = false;

        public NavMenuItem(String title, int icon_id) {
            this.title = title;
            this.icon_id = icon_id;
            this.isBottomLine = isBottomLine;
        }

    }
}