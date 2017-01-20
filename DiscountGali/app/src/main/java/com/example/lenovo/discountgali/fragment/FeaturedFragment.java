package com.example.lenovo.discountgali.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.model.FeaturedModel;

/**
 * Created by Dushyant Singh on 11/24/2016.
 */
public class FeaturedFragment extends Fragment implements View.OnClickListener {
    public int a = 0;

    private FeaturedModel featuredModel;

    public static FeaturedFragment newInstance(FeaturedModel featuredModel) {
        FeaturedFragment fragment = new FeaturedFragment();

        Bundle args = new Bundle();
        args.putSerializable("featured_model", featuredModel);
        fragment.setArguments(args);

        return fragment;
    }

    public FeaturedFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_featured, container, false);
        parseArguments();

        ((TextView)rootView.findViewById(R.id.tv_title)).setText(featuredModel.title);
        ((TextView)rootView.findViewById(R.id.tv_text)).setText(featuredModel.description);
//        ((ImageView)rootView.findViewById(R.id.iv_icon)).setImageURI(Uri.parse(featuredModel.icon));
        return rootView;
    }

    private void parseArguments() {
        if (getArguments() != null) {
            featuredModel = (FeaturedModel) getArguments().getSerializable("featured_model");
        }
    }

    @Override
    public void onClick(View v) {
            Toast.makeText(v.getContext(),"Under Development",Toast.LENGTH_SHORT).show();

    }
}