package com.example.lenovo.discountgali.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.discountgali.R;

import java.util.ArrayList;


public class RecentSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<String> searchResult;
    private LayoutInflater inflater;

    public RecentSearchAdapter(Context context, ArrayList<String> searchResult) {
        inflater = LayoutInflater.from(context);
        this.searchResult = searchResult;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textResult;

        public ViewHolder(View itemView) {
            super(itemView);
            textResult = (TextView) itemView.findViewById(R.id.text_recent);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(inflater.inflate(R.layout.recent_search_layout, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder holderCat = (ViewHolder) holder;
            holderCat.textResult.setText(searchResult.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ((SearchItemActivity) inflater.getContext()).onSearchItemClicked(searchResult.get(position));
                }
            });
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return searchResult.size();
    }
}
