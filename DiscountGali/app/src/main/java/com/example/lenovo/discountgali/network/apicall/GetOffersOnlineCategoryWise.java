package com.example.lenovo.discountgali.network.apicall;

import android.content.Context;

import com.example.lenovo.discountgali.model.ServerResponse;
import com.example.lenovo.discountgali.model.TopOffers;
import com.example.lenovo.discountgali.network.Code;
import com.example.lenovo.discountgali.network.ServerRequests;
import com.example.lenovo.discountgali.utility.Syso;
import com.example.lenovo.discountgali.utils.Constants;
import com.example.lenovo.discountgali.utils.JSONParsingUtils;

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

/**
 * Created by lenovo on 04-02-2017.
 */

public class GetOffersOnlineCategoryWise extends BaseApiCall {
    private int page_start = Constants.PAGE_START_DEFAULT;
    private int page_limit = Constants.PAGE_LIMIT_DEFAULT;
    private Context context;
    private String result;
    private String outputjson;
    private int categoryId;
    private ServerResponse<TopOffers> serverResponse = new ServerResponse<>();

    public GetOffersOnlineCategoryWise(int page_start, int page_limit, int categoryId) {
        this.page_limit = page_limit;
        this.page_start = page_start;
        this.categoryId = categoryId;
    }
    public GetOffersOnlineCategoryWise(int categoryId) {

        this.categoryId = categoryId;
        page_limit = Constants.PAGE_LIMIT_DEFAULT;
        page_start = Constants.PAGE_START_DEFAULT;
    }
    @Override
    protected String getRequestUrl() {
        return ServerRequests.REQUEST_GET_OFFERS_ONLINE_CATEGORY_WISE;
    }

    public void populateFromResponse(Object response) throws JSONException {
        super.populateFromResponse(response);
        result = (String) response;
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
                System.out.println("================Response Offers " + outputjson);
                parseData(outputjson.toString());

            } catch (SAXException e) {
                // handle SAXException
            } catch (IOException e) {
                // handle IOException
            }
            catch (ParserConfigurationException e1) {
                // handle ParserConfigurationException
            }
        }

//        SoapObject soapObject = result;
        Syso.print("URL "  + getRequestUrl());
        Syso.print("RESPONSE GET Top Offers "  + result.toString());

        if (response instanceof JSONObject) {
//            Type type = new TypeToken<ServerResponseList<MessageList>>() {}.getType();
//            serverResponseList = new Gson().fromJson(response.toString(),type);
//            if(serverResponseList!=null && !TextUtils.isEmpty(serverResponseList.getTimeStamp())) {
//                if(Utils.getLong(serverResponseList.getTimeStamp())>0)
//                 UserPreference.getInstance(context).setStringField(UserPreference.FIELD_TIME_STAMP_MESSAGE, serverResponseList.getTimeStamp());
////                Syso.print("************* Time stamp from server : " + serverResponseList.getTimeStamp());
////                Syso.print("************* Time stamp from saved : "+UserPreference.getInstance(context).getStringField(UserPreference.FIELD_TIME_STAMP_MESSAGE));
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

    @Override
    public String getRequest() {


//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("pkAccountSender", DecodeUtills.getBase64(pkAccount));
//            jsonObject.put("filterDate", DecodeUtills.getBase64(timeStamp));
//            jsonObject.put("filterDate", DecodeUtills.getBase64(UserPreference.getInstance(context).getStringField(UserPreference.FIELD_TIME_STAMP_MESSAGE)));
//            jsonObject.put("newMessages", DecodeUtills.getBase64(newMessage));
////            Syso.print("************* Time stamp from saved in getRequest: " + UserPreference.getInstance(context).getStringField(UserPreference.FIELD_TIME_STAMP_MESSAGE));
////            Syso.print("************* getRequest: " + jsonObject.toString());
//            return jsonObject;
//        } catch (Exception exc) {
//            exc.printStackTrace();
//            Syso.print("Exception caught in getRequest()" + exc.toString());
//        }
//
        return null;
    }

    public Object getResult() {
//        return serverResponseList;
        return serverResponse;
    }

    @Override
    public String getStringRequest() {
        String requestBody = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "  <soap12:Body>\n" +
                "    <GetOnlineDealOnCategory xmlns=\"http://DiscountGali.com/\">\n" +
                "      <pageCount>" + page_limit + "</pageCount>\n" +
                "      <pageNo>" + page_start + "</pageNo>\n" +
                "      <allRecords>1</allRecords>\n" +
                "      <categoryId>" + categoryId + "</categoryId>\n" +
                "    </GetOnlineDealOnCategory>\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>";
        Syso.print("REQUEST Get top offers" + requestBody);

        return requestBody;
    }
    public int getTotalRecords()
    {
        return serverResponse.totalCount;
    }
}
