package com.example.lenovo.discountgali.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lenovo.discountgali.R;
import com.example.lenovo.discountgali.adapter.HomeAdapter;
import com.example.lenovo.discountgali.adapter.RecentSearchAdapter;
import com.example.lenovo.discountgali.model.ModelCategories;
import com.example.lenovo.discountgali.model.ServerResponse;
import com.example.lenovo.discountgali.model.TopOffers;
import com.example.lenovo.discountgali.network.HttpRequestHandler;
import com.example.lenovo.discountgali.network.api.ApiCall;
import com.example.lenovo.discountgali.network.apicall.GetSearchItemsListApiCall;
import com.example.lenovo.discountgali.utility.Syso;
import com.example.lenovo.discountgali.utility.Utils;
import com.example.lenovo.discountgali.utils.Constants;
import com.example.lenovo.discountgali.utils.EndlessRecyclerOnScrollListener;
import com.example.lenovo.discountgali.utils.SharedPreference;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.example.lenovo.discountgali.activity.SearchItemActivity.SearchType.OFFLINE;
import static com.example.lenovo.discountgali.activity.SearchItemActivity.SearchType.ONLINE;
import static com.example.lenovo.discountgali.adapter.HomeAdapter.*;

/**
 * Created by chirag on 7/12/15.
 */

public class SearchItemActivity extends BaseActivity implements View.OnClickListener, HomeAdapter.OnItemClickListener {

    private static final int LIMIT_WORDS_SEARCH = 1;
    private EditText editTextSearch;
    private RecyclerView recyclerViewSearch;
    private RecyclerView.Adapter adapter;
    private ProgressBar progressBarSearch;
    private Handler handler;
    private Runnable runnable;
    private TextView emptyView;

    private SearchType selectedTypeSearch = SearchType.getDefaultSearchType();

    private EndlessRecyclerOnScrollListener endlessScrollListener;
    private boolean isLastItemFound, isApiRunning;
    private String searchingWord;
    private ArrayList<TopOffers> onlineList = new ArrayList<>();
    private ArrayList<TopOffers> offlineList = new ArrayList<>();

    @Override
    public void onItemClicked(TopOffers topOffers) {

    }

    public enum SearchType {
        ONLINE("1"), OFFLINE("2");
        public String typeCodeJson;

        private SearchType(String typeCodeJson) {
            this.typeCodeJson = typeCodeJson;
        }

        public static SearchType getDefaultSearchType() {
            return ONLINE;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = getLayoutInflater().inflate(R.layout.activity_search_item, null, false);
        setContentView(v);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        SearchType[] values = SearchType.values();
        for (SearchType value : values) {
            tabLayout.addTab(tabLayout.newTab().setText(value.name()));
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                resetLoading();
                selectedTypeSearch = SearchType.values()[tab.getPosition()];
                //editTextSearch.setHint("Search for " + selectedTypeSearch.name());
                editTextSearch.setHint("Search");
                String trim = editTextSearch.getText().toString().trim();
                if (trim.length() > LIMIT_WORDS_SEARCH) {
                    hideEmptyView();
                    itemSearchApiCall(trim);
                } else {
                    hideEmptyView();
                    setRecentSearchAdapter(trim);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        handler = new Handler();
        emptyView = (TextView) findViewById(R.id.emptyViewSearchScreen);
        editTextSearch = (EditText) findViewById(R.id.edit_text_search);
        //editTextSearch.setHint("Search for " + selectedTypeSearch.name());
        editTextSearch.setHint("Search");
        findViewById(R.id.clear_search).setOnClickListener(this);
        recyclerViewSearch = (RecyclerView) findViewById(R.id.recyclerViewSearch);
        LinearLayoutManager manager = new LinearLayoutManager(SearchItemActivity.this);
        recyclerViewSearch.setLayoutManager(manager);
        endlessScrollListener = new EndlessRecyclerOnScrollListener(manager, Constants.PAGE_LIMIT_DEFAULT_LARGE) {
            @Override
            public void onLoadMore(int current_page) {

                itemSearchApiCall(searchingWord, current_page + 1, visibleThreshold);
            }

            @Override
            public void onFirstElementReached() {

            }
        };
        recyclerViewSearch.addOnScrollListener(endlessScrollListener);

        progressBarSearch = (ProgressBar) findViewById(R.id.progressBarLoadingSearch);

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence string, int start, int before, int count) {

                resetLoading();
                searchingWord = string.toString().trim();
                if (searchingWord.length() > LIMIT_WORDS_SEARCH) {
                    hideEmptyView();
                    itemSearchApiCall(searchingWord);
                } else {
                    hideEmptyView();
                    setRecentSearchAdapter(searchingWord);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setRecentSearchAdapter("");

    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (adapter != null && recyclerViewSearch != null)
                        adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Syso.print(" EXCEPTION " + e.getMessage());
                }
            }
        });
    }

    private void setRecentSearchAdapter(String searchingWord) {
        Set<String> searchingWordsSet = SharedPreference.getSearchingWordsSet(SearchItemActivity.this);
        ArrayList<String> searchResult = new ArrayList<String>();
        if (searchingWord.equals("")) {
            searchResult.addAll(searchingWordsSet);
        } else {
            for (String next : searchingWordsSet) {
                if (next != null && next.startsWith(searchingWord)) {
                    searchResult.add(next);
                }
            }
        }


        if (searchResult.isEmpty()) {
            adapter = null;
            recyclerViewSearch.setAdapter(null);
        } else {
            adapter = new RecentSearchAdapter(SearchItemActivity.this, searchResult);
            recyclerViewSearch.setAdapter(adapter);
        }
    }


    private void itemSearchApiCall(final String searchKeyWord, final int... pagingParams) {
        if (searchKeyWord == null || searchKeyWord.isEmpty())
            return;

        progressBarSearch.setVisibility(View.VISIBLE);
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
        runnable = new Runnable() {
            @Override
            public void run() {
                runnableWork(searchKeyWord, pagingParams);
            }
        };

        handler.postDelayed(runnable, 1000);

    }


    public void runnableWork(final String searchKeyWord, final int... pagingParams) {

        if (isApiRunning) {
            //only decrease if calling from load more
            if (isPagingParamsCall(pagingParams)) {
                endlessScrollListener.decreasePagingCount();
            }
            return;
        }
        if (isLastItemFound) {
            progressBarSearch.setVisibility(View.GONE);
            return;
        }
        isApiRunning = true;

        final GetSearchItemsListApiCall searchItemsListApiCall;

        if (isPagingParamsCall(pagingParams)) {
            searchItemsListApiCall = new GetSearchItemsListApiCall(searchKeyWord, selectedTypeSearch, pagingParams[0], pagingParams[1]);
        } else {
            searchItemsListApiCall = new GetSearchItemsListApiCall(searchKeyWord, selectedTypeSearch);
        }

        HttpRequestHandler.getInstance(SearchItemActivity.this.getApplicationContext()).executeRequest(searchItemsListApiCall, new ApiCall.OnApiCallCompleteListener() {
                    @Override
                    public void onComplete(Exception e) {
                        isApiRunning = false;
                        progressBarSearch.setVisibility(View.GONE);
                        if (e == null) { // Success

                            onSuccess(searchKeyWord, searchItemsListApiCall, pagingParams);
                        } else {
                            adapter = null;
                            recyclerViewSearch.setAdapter(null);
                            if (Utils.isNoInternetException(e)) {
                                showErrorInEmptyView();
                            } else {
                                showEmptyView();
                            }
                        }

                    }
                },false

        );
    }


    private void onSuccess(String searchKeyWord, GetSearchItemsListApiCall searchItemsListApiCall,int... pagingParams) {
        if (searchItemsListApiCall.getSearchType().equals(selectedTypeSearch)) {

            if (editTextSearch != null && editTextSearch.getText().length() <= LIMIT_WORDS_SEARCH) {
                setRecentSearchAdapter(editTextSearch.getText().toString());
            } else {
                switch (searchItemsListApiCall.getSearchType()) {
                    case ONLINE: {
                        Syso.print("INSIDE " + "skipping layout1");
                        ServerResponse<TopOffers> serverResponse = (ServerResponse<TopOffers>) searchItemsListApiCall.getResult();
                        if(serverResponse.data!=null && !serverResponse.data.isEmpty())
                        onlineList.addAll(serverResponse.data);
                        Syso.print("INSIDE " + "skipping layout3");

                        if (onlineList != null && !onlineList.isEmpty()) {
                            Syso.print("INSIDE " + "skipping layout2");

                            hideEmptyView();
                            if (isPagingParamsCall(pagingParams)) {
                                Syso.print("INSIDE " + "skipping layout");
                                adapter.notifyDataSetChanged();
                            } else {
                                adapter = new HomeAdapter(SearchItemActivity.this, onlineList);
                                recyclerViewSearch.setAdapter(adapter);
                            }
                            setLastItemConditon(searchItemsListApiCall, onlineList);
//                            SharedPreference.addSearchingWords(searchKeyWord, SearchItemActivity.this);
                        } else {
                            adapter = null;
                            recyclerViewSearch.setAdapter(null);
                            showEmptyView();
                        }

                        break;
                    }
                    case OFFLINE: {

                        ServerResponse<TopOffers> serverResponse = (ServerResponse<TopOffers>) searchItemsListApiCall.getResult();
                        if(serverResponse.data!=null && !serverResponse.data.isEmpty())
                        offlineList.addAll(serverResponse.data);
                        if (offlineList != null && !offlineList.isEmpty()) {
                            hideEmptyView();
                            if (isPagingParamsCall(pagingParams)) {
                                adapter.notifyDataSetChanged();
                            } else {
                                adapter = new HomeAdapter(SearchItemActivity.this, offlineList);
                                recyclerViewSearch.setAdapter(adapter);
                            }
                            setLastItemConditon(searchItemsListApiCall, offlineList);
//                            SharedPreference.addSearchingWords(searchKeyWord, SearchItemActivity.this);
                        } else {
                            adapter = null;
                            recyclerViewSearch.setAdapter(null);
                            showEmptyView();
                        }
                        break;
                    }
                }
            }
        }

    }




    public void setLastItemConditon(GetSearchItemsListApiCall apiCall, List<? extends Object> objects) {
        if (apiCall.getTotal_records() > 0 && apiCall.getTotal_records() == objects.size()) {
            isLastItemFound = true;
        }
    }

    private boolean isPagingParamsCall(int[] pagingParams) {
        return pagingParams.length == 2;
    }

    private void onBackButtonClicked() {
        finish();
    }

    private void onClearSearchButtonClicked() {
        editTextSearch.setText("");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_search:
                onClearSearchButtonClicked();
                break;
        }
    }

    public void onSearchItemClicked(String result) {
        editTextSearch.setText(result);
    }

    private void showEmptyView() {
        //emptyView.setText("No " + selectedTypeSearch.name() + " Found");
        emptyView.setText("Oops! No results found.");
        emptyView.setVisibility(View.VISIBLE);
    }

    private void showErrorInEmptyView() {
        emptyView.setText("Network error");
        emptyView.setVisibility(View.VISIBLE);
    }

    private void hideEmptyView() {
        emptyView.setVisibility(View.GONE);
    }

    private void resetLoading() {
        isLastItemFound = false;
        onlineList.clear();
        offlineList.clear();
        if(adapter != null && recyclerViewSearch != null) {
            adapter.notifyDataSetChanged();
        }
        endlessScrollListener.reset();
    }
}
