package com.gurmail.singh.discountgali.network.apicall;

import android.content.Context;

import com.gurmail.singh.discountgali.model.DealUrlModel;
import com.gurmail.singh.discountgali.model.ServerResponse;
import com.gurmail.singh.discountgali.network.Code;
import com.gurmail.singh.discountgali.network.ServerRequests;
import com.gurmail.singh.discountgali.utility.Syso;
import com.gurmail.singh.discountgali.utils.Constants;
import com.gurmail.singh.discountgali.utils.JSONParsingUtils;

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
 * Created by lenovo on 25-02-2017.
 */

public class GetDealUrlApiCall extends BaseApiCall {
    private Context context;
    private String result;
    private String outputjson;
    private String mobileNo;
    private int dealId;
    private int dealType;
    private ServerResponse<DealUrlModel> serverResponse = new ServerResponse<>();

    public GetDealUrlApiCall(String mobileNo, int dealId, int dealType) {

        this.mobileNo = mobileNo;
        this.dealId = dealId;
        this.dealType = dealType;
    }
    @Override
    protected String getRequestUrl() {
        return ServerRequests.REQUEST_GET_URL_ON_MOBILE_NUMBER;
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

    }

    private void parseData(String response) {

        if (response != null && !response.isEmpty()) {
            try {
                JSONObject json = new JSONObject(response);
                serverResponse.baseModel.MessageCode = json.getInt("MessageCode");
                if(serverResponse.baseModel.MessageCode == Code.SUCCESS_MESSAGE_CODE) {
                    JSONObject dataobject = json.getJSONObject("Data");
                if(dealType == Constants.typeOnline) {
                    JSONArray listdata = dataobject.getJSONArray("List");
                    serverResponse.data = JSONParsingUtils.getDealUrl(listdata);
                }
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
                "    <GetDealWithMobileNo xmlns=\"http://DiscountGali.com/\">\n" +
                "      <mobileNo>" + mobileNo + "</mobileNo>\n" +
                "      <dealId>" + dealId + "</dealId>\n" +
                "      <dealType>" + dealType + "</dealType>\n" +
                "    </GetDealWithMobileNo>\n" +
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
