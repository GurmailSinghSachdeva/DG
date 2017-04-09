package com.discount.coupons.discountgali.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by lenovo on 14-01-2017.
 */

public abstract class EndlessRecyclerOnScrollListenerGrid extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerOnScrollListenerGrid.class.getSimpleName();

    private boolean flag = false;
    private boolean updateFlag = false;

    //private int recentFlag=0;
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    protected int visibleThreshold = Constants.PAGE_LIMIT_DEFAULT; // The minimum amount of items to have below your current scroll position before loading more.
    protected int visibleThresholdRecent = Constants.PAGE_LIMIT_RECENT;
    protected int visibleThresholdupdate = Constants.PAGE_LIMIT_UPDATE;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private int current_page = 0;

    private GridLayoutManager mGridLayoutManager;

    public EndlessRecyclerOnScrollListenerGrid(GridLayoutManager gridLayoutManager) {
        this(gridLayoutManager, Constants.PAGE_LIMIT_DEFAULT);
    }

    public EndlessRecyclerOnScrollListenerGrid(GridLayoutManager gridLayoutManager, int visibleThreshold) {
        this.mGridLayoutManager = gridLayoutManager;
        this.visibleThreshold = visibleThreshold;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mGridLayoutManager.getItemCount();
        firstVisibleItem = mGridLayoutManager.findFirstVisibleItemPosition();
        System.out.println("==visibleItemCount==totalItemCount==firstVisibleItem"+visibleItemCount+"="+totalItemCount+"="+firstVisibleItem);
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached

            // Do something
            current_page++;
            flag=true;
            updateFlag=true;

            onLoadMore(current_page);

            loading = true;
        }

        if ((totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThresholdRecent)) {
            // End has been reached

            // Do something
            //current_page++;


            if(flag)
            {

                flag=false;
//                if(recentFlag%2==0)
//                {
//
//                    Log.e("inside","recent1");
//                    recentFlag++;
                onLoadRecent(current_page);


            }


            //loading = true;
        }

        if ((totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThresholdupdate)) {
            // End has been reached

            // Do something
            //current_page++;


            if(updateFlag)
            {

                updateFlag=false;
//                if(recentFlag%2==0)
//                {
//
//                    Log.e("inside","recent1");
//                    recentFlag++;
                onLoadUpdate(current_page);


            }


            //loading = true;
        }
    }

    public void reset() {
        this.previousTotal = 0;
        this.loading = false;
        this.current_page = 0;
    }

    public abstract void onLoadMore(int current_page);

    public  void onLoadRecent(int current_page)
    {

    }
    public  void onLoadUpdate(int current_page)
    {

    }


    public void decreasePagingCount() {
        current_page--;
    }
}