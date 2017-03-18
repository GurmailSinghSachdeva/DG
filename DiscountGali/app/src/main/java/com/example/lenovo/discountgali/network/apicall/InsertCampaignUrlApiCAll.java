package com.example.lenovo.discountgali.network.apicall;

import com.example.lenovo.discountgali.model.BaseModel;
import com.example.lenovo.discountgali.network.Code;
import com.example.lenovo.discountgali.network.ServerRequests;
import com.example.lenovo.discountgali.utility.Syso;

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
 * Created by lenovo on 18-03-2017.
 */

public class InsertCampaignUrlApiCAll extends BaseApiCall{
    private String result;
    private String outputjson;
    private String referrerString;
    private String deviceID;
    private int success = 0;
    private BaseModel serverResponse = new BaseModel();

    public InsertCampaignUrlApiCAll(String referrerString, String deviceID) {
        this.referrerString = referrerString;
        this.deviceID = deviceID;
    }


    @Override
    protected String getRequestUrl() {
        return ServerRequests.REQUEST_INSERT_CAMPAIGN;

    }

    public void populateFromResponse(Object response) throws JSONException {
        super.populateFromResponse(response);
        result = (String) response;
        Syso.print("///////////////RESPONSE////////campaign" + result);
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
                System.out.println("================Response campign " + outputjson);
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
                serverResponse.MessageCode = json.getInt("MessageCode");

                if(serverResponse.MessageCode == Code.SUCCESS_MESSAGE_CODE) {

                    JSONObject dataobject = json.getJSONObject("Data");
//                    JSONArray listdata = dataobject.getJSONArray("List");

                    if(dataobject.has("success")){
                        success = dataobject.getInt("success");
                    }

                }
                serverResponse.Message = json.getString("Message");


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
    public int getSuccessStatus(){
        return success;
    }

    @Override
    public String getStringRequest() {
        String requestBody = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "  <soap12:Body>\n" +
                "    <InsertCampaignRecords xmlns=\"http://DiscountGali.com/\">\n" +
                "      <emiNo>" + deviceID + "</emiNo>\n" +
                "      <Url>" + referrerString + "</Url>\n" +
                "    </InsertCampaignRecords>\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>";
        Syso.print("!!!!!!!!!!!!!!Request Get Offers!!!!" + requestBody);

        Syso.print("=========APICALL===== LOGin " + requestBody);
        return requestBody;}

    }

