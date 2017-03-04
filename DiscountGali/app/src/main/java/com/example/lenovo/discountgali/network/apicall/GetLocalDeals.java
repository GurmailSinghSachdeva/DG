package com.example.lenovo.discountgali.network.apicall;

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

public class GetLocalDeals extends BaseApiCall{
    private int page_start = 1;
    private int page_limit = Constants.PAGE_LIMIT_DEFAULT;
    private int categoryId;
    private String cityName;
    private String result;
    private String outputjson;
    private ServerResponse<TopOffers> serverResponse = new ServerResponse<>();

    public GetLocalDeals(int page_start, int page_limit, int categoryId, String cityName) {
        this.page_limit = page_limit;
        this.page_start = page_start;
        this.cityName = cityName;
        this.categoryId = categoryId;
    }
    public GetLocalDeals(int categoryId, String cityName) {
        this.page_limit = Constants.PAGE_LIMIT_DEFAULT;
        this.page_start = Constants.PAGE_START_DEFAULT;
        this.cityName = cityName;
        this.categoryId = categoryId;    }
    @Override
    protected String getRequestUrl() {
        return ServerRequests.REQUEST_LOCAL_DEALS;
    }

    public void populateFromResponse(Object response) throws JSONException {
        super.populateFromResponse(response);
        result = (String) response;
        Syso.print("///////////////RESPONSE//////// ONlinestorewise offers" + result);
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
                System.out.println("aaaa================Response Offers " + outputjson);
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
        Syso.print("HELLO "  + result.toString());

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

    @Override
    public String getRequest() {

        return null;
    }

    public Object getResult() {
        return serverResponse;
    }

    @Override
    public String getStringRequest() {
        String requestBody = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "  <soap12:Body>\n" +
                "    <GetLocalDeals xmlns=\"http://DiscountGali.com/\">\n" +
                "      <categoryId>" + categoryId + "</categoryId>\n" +
                "      <pageCount>" + page_limit + "</pageCount>\n" +
                "      <pageNo>" + page_start + "</pageNo>\n" +
                "      <allRecords>1</allRecords>\n" +
                "      <cityName>" + cityName + "</cityName>\n" +
                "    </GetLocalDeals>\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>";
        Syso.print("!!!!!!!!!!!!!!Request Get Offers!!!!" + requestBody);

        Syso.print("=========APICALL===== GEtOffersOnlineStoreWise " + requestBody);
        return requestBody;
    }
    public int getTotalRecords(){
        return serverResponse.totalCount;
    }
}