package com.example.lenovo.discountgali.network.apicall;

import com.example.lenovo.discountgali.model.BaseModel;
import com.example.lenovo.discountgali.model.CityModel;
import com.example.lenovo.discountgali.model.ServerResponse;
import com.example.lenovo.discountgali.network.Code;
import com.example.lenovo.discountgali.network.ServerRequests;
import com.example.lenovo.discountgali.utility.Syso;
import com.example.lenovo.discountgali.utility.Utils;
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
 * Created by lenovo on 18-03-2017.
 */

public class LoginApiCall extends BaseApiCall{
    private String result;
    private String outputjson;
    private String mobile_no;
    private String ref_no;
    private int success = 0;
    private int isVerification = 0;
    private String otp;
    private BaseModel serverResponse = new BaseModel();

    public LoginApiCall(String mobile_no, String ref_no, int isVerification, String otp) {
        this.mobile_no = mobile_no;
        this.ref_no = ref_no;
        this.isVerification = isVerification;
        this.otp = otp;
    }


    @Override
    protected String getRequestUrl() {
        if(isVerification == 1)
        {
            return ServerRequests.REQUEST_LOGIN_VERIFY;

        }else {
            return ServerRequests.REQUEST_LOGIN_DG;

        }
    }

    public void populateFromResponse(Object response) throws JSONException {
        super.populateFromResponse(response);
        result = (String) response;
        Syso.print("///////////////RESPONSE////////login" + result);
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
                System.out.println("================Response login " + outputjson);
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
        if(isVerification == 1){
            String requestBody = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                    "  <soap12:Body>\n" +
                    "    <MobileLoginVerification xmlns=\"http://DiscountGali.com/\">\n" +
                    "      <OTP>" + otp + "</OTP>\n" +
                    "    </MobileLoginVerification>\n" +
                    "  </soap12:Body>\n" +
                    "</soap12:Envelope>";
            Syso.print("!!!!!!!!!!!!!!Request Get Offers!!!!" + requestBody);

            Syso.print("=========APICALL===== LOGin " + requestBody);
            return requestBody;
        }else {
//            String requestBody = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                    "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
//                    "  <soap12:Body>\n" +
//                    "    <MobileLogin xmlns=\"http://DiscountGali.com/\">\n" +
//                    "      <mobileNo>" + mobile_no + "</mobileNo>\n" +
//                    "      <referenceNo>" + ref_no + "</referenceNo>\n" +
//                    "    </MobileLogin>\n" +
//                    "  </soap12:Body>\n" +
//                    "</soap12:Envelope>";


            String requestBody = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                    "  <soap12:Body>\n" +
                    "    <MobileLogin xmlns=\"http://DiscountGali.com/\">\n" +
                    "      <mobileNo>" + mobile_no + "</mobileNo>\n" +
                    "      <referenceNo>" + ref_no + "</referenceNo>\n" +
//                    "      <deviceId>" + Utils.getDeviceID() + "</deviceId>\n" +
                    "    </MobileLogin>\n" +
                    "  </soap12:Body>\n" +
                    "</soap12:Envelope>";
            Syso.print("!!!!!!!!!!!!!!Request Get Offers!!!!" + requestBody);

            Syso.print("=========APICALL===== LOGin " + requestBody);
            return requestBody;
        }

    }
}
