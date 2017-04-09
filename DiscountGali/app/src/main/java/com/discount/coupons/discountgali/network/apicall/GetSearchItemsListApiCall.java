package com.discount.coupons.discountgali.network.apicall;


import com.discount.coupons.discountgali.activity.SearchItemActivity;
import com.discount.coupons.discountgali.model.ServerResponse;
import com.discount.coupons.discountgali.model.TopOffers;
import com.discount.coupons.discountgali.network.Code;
import com.discount.coupons.discountgali.network.ServerRequests;
import com.discount.coupons.discountgali.utility.Syso;
import com.discount.coupons.discountgali.utils.Constants;
import com.discount.coupons.discountgali.utils.JSONParsingUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class GetSearchItemsListApiCall extends BaseApiCall {

    // Output/Result
    private int total_records;
    private String result;

    // Input Params.
    private final String searchKeyWord;
    private SearchItemActivity.SearchType currentSearchType;
    private int page_start = Constants.PAGE_START_DEFAULT, page_limit = Constants.PAGE_LIMIT_DEFAULT_LARGE;
    private String outputjson;
    private ServerResponse<TopOffers> serverResponse = new ServerResponse<>();

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
        return ServerRequests.REQUEST_SEARCH;
    }

    /**
     * Method is used to receive the response
     *
     * @param response
     * @throws JSONException
     */
    public void populateFromResponse(Object response) throws JSONException {
        super.populateFromResponse(response);

        try{
            result = (String) response;
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        parseXml(result);
        }

    private void parseXml(String result) {
        if(result!=null && !result.isEmpty())
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = null;
            try {
                db = dbf.newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(result));
                Document doc = db.parse(is);
                outputjson = doc.getDocumentElement().getTextContent();
                if(currentSearchType.typeCodeJson.equals(SearchItemActivity.SearchType.ONLINE.typeCodeJson))
                {
                    parseDataOnline(outputjson.toString());

                }
                else {
                    parseData(outputjson.toString());

                }

                Syso.print("URL " + getRequestUrl());
                Syso.print("RESPONSE Search " + outputjson.toString());
            } catch (SAXException e) {
                // handle SAXException
            } catch (IOException e) {
                // handle IOException
            }
            catch (ParserConfigurationException e1) {
                // handle ParserConfigurationException
            }
        }

    }

    private void parseDataOnline(String response) {
        if (response != null && !response.isEmpty()) {
            try {
                JSONObject json = new JSONObject(response);
                serverResponse.baseModel.MessageCode = json.getInt("MessageCode");
                if(serverResponse.baseModel.MessageCode == Code.SUCCESS_MESSAGE_CODE) {

                    JSONObject dataobject = json.getJSONObject("Data");
                    JSONArray listdata = dataobject.getJSONArray("List");

                    serverResponse.data = JSONParsingUtils.getTopOffers(listdata);
                }
                serverResponse.baseModel.MessageCode = json.getInt("MessageCode");
                serverResponse.baseModel.Message = json.getString("Message");
                serverResponse.totalCount = json.getInt("TotalRecordCount");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void parseData(String response) {

        if (response != null && !response.isEmpty()) {
            try {
                JSONObject json = new JSONObject(response);
                serverResponse.baseModel.MessageCode = json.getInt("MessageCode");
                if(serverResponse.baseModel.MessageCode == Code.SUCCESS_MESSAGE_CODE) {

                    JSONObject dataobject = json.getJSONObject("Data");
                    JSONArray listdata = dataobject.getJSONArray("List");

                    serverResponse.data = JSONParsingUtils.getLocalDeals(listdata);
                }
                serverResponse.baseModel.MessageCode = json.getInt("MessageCode");
                serverResponse.baseModel.Message = json.getString("Message");
                serverResponse.totalCount = json.getInt("TotalRecordCount");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
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
        return serverResponse;
    }

    public SearchItemActivity.SearchType getSearchType() {
        return currentSearchType;
    }

    @Override
    public String getStringRequest() {
        //TODO: do work here

        String stringRequest = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "  <soap12:Body>\n" +
                "    <GetSearchOnlineResult xmlns=\"http://DiscountGali.com/\">\n" +
                "      <pageCount>" + page_limit + "</pageCount>\n" +
                "      <pageNo>" + page_start + "</pageNo>\n" +
                "      <allRecords>1</allRecords>\n" +
                "      <brandName>" + searchKeyWord + "</brandName>\n" +
                "      <dealType>" + Integer.parseInt(currentSearchType.typeCodeJson) + "</dealType>\n" +
                "    </GetSearchOnlineResult>\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>";


        return stringRequest;
    }

    public int getTotal_records() {
        return serverResponse.totalCount;
    }
}
