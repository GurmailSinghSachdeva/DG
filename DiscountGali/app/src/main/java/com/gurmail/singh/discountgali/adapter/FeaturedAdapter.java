package com.gurmail.singh.discountgali.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gurmail.singh.discountgali.fragment.FeaturedFragment;
import com.gurmail.singh.discountgali.model.FeaturedModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Dushyant Singh on 11/24/2016.
 */
public class FeaturedAdapter extends FragmentStatePagerAdapter {
    private List<FeaturedModel> itemList = new ArrayList<>();
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public FeaturedAdapter(FragmentManager manager, List<FeaturedModel> itemList) {
        super(manager);
        this.itemList = itemList;
    }

    @Override
    public FeaturedFragment getItem(int position) {
        return FeaturedFragment.newInstance(itemList.get(position));
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
//        itemList.add(title);
    }
    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public FeaturedModel getItemAtPosition(int position) {
        if (itemList != null) {
            return itemList.get(position);
        }
        return null;
    }
}