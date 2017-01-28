package com.example.lenovo.discountgali.network.apicall;


import com.example.lenovo.discountgali.activity.SearchItemActivity;
import com.example.lenovo.discountgali.utility.Syso;
import com.example.lenovo.discountgali.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetSearchItemsListApiCall extends BaseApiCall {

    // Output/Result
    private int total_records;
    private Object result;

    // Input Params.
    private final String searchKeyWord;
    private SearchItemActivity.SearchType currentSearchType;
    private int page_start = Constants.PAGE_START_DEFAULT, page_limit = Constants.PAGE_LIMIT_DEFAULT_LARGE;

    public GetSearchItemsListApiCall( String searchKeyword, SearchItemActivity.SearchType searchType) {
        this.searchKeyWord = searchKeyword;
        this.currentSearchType=searchType;
    }

    public GetSearchItemsListApiCall(String searchKeyWord, SearchItemActivity.SearchType currentSearchType, int page_start, int page_limit) {
        this.searchKeyWord = searchKeyWord;
        this.currentSearchType = currentSearchType;
        this.page_start = page_start;
        this.page_limit = page_limit;
    }

    @Override
    protected String getRequestUrl() {
        //TODO: add requerd url
        return null;
    }

    /**
     * Method is used to receive the response
     *
     * @param response
     * @throws JSONException
     */
    public void populateFromResponse(Object response) throws JSONException {
        //TODO: do work here
        }


    private void parseCityList(JSONObject json) throws JSONException {
        if (json != null) {
            JSONArray dataJA = json.optJSONArray("search_result");

//            switch(currentSearchType)
//            {
//                case TOPICS:
//                    result=JSONParsingUtils.getHashTagList(dataJA);
//                    break;
//                case PEOPLE:
//                    result = JSONParsingUtils.getUserList(dataJA);
//                    break;
//                case ROAST:
//                    result=JSONParsingUtils.getRoastsList(dataJA);
//                    break;
//            }

        }
    }


    @Override
    public Object getRequest() {
       return null;
    }

    /**
     * Method to return the result got from the API call
     *
     * @return result, if any problem occur then return null
     */
    public Object getResult() {
        return result;
    }

    public SearchItemActivity.SearchType getSearchType() {
        return currentSearchType;
    }

    @Override
    public String getStringRequest() {
        //TODO: do work here
        return super.getStringRequest();
    }

    public int getTotal_records() {
        return total_records;
    }
}
