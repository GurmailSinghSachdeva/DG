package com.example.lenovo.discountgali.network.apicall;


import android.content.Context;

import com.example.lenovo.discountgali.network.ServerRequests;
import com.example.lenovo.discountgali.network.api.ApiCall;
import com.example.lenovo.discountgali.utility.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public abstract class BaseApiCall extends ApiCall {
    private String errorCode;
    private String errorDesc;

    protected abstract String getRequestUrl();

    public BaseApiCall() {
        super();
    }

    public void createHeader(Context context){
        long timeStamp = Utils.getCurrentTimeStamp();
        headers = new HashMap<String, String>();
//        POST /MobileApi.asmx HTTP/1.1
//        Host: discountgali.com
//        Content-Type: text/xml; charset=utf-8
//        Content-Length: length
//        SOAPAction: "http://tempuri.org/GetOnlineBrand"

        headers.put("SOAPAction", "http://tempuri.org/GetOnlineBrand");
//        headers.put("Content-Type", "application/json; charset=utf-8");
//        headers.put("encoding", Constants.Base64_Param_Value+"; charset=utf-8"); // 1 = enable ; 2 = disable
//        headers.put("gZip", Constants.Gzip_Param_Value+"; charset=utf-8"); // 1 = enable ; 2 = disable
////        headers.put("userAgent", "Android; charset=utf-8"); // will send device type and model number
//        headers.put("DeviceType", "Android");
//        headers.put("CurrentAppVersion", Utils.getAppVersion(context));
//        headers.put("appId", Constants.APP_Id);
//        headers.put("timeStamp", String.valueOf(timeStamp));
//        headers.put("apiHeader", Utils.getSha512(Constants.APP_Id + timeStamp + getRequest().toString(), Constants.SECRET_KEY));
//        headers.put("apiHeader", Utils.getSha512(Constants.SECRET_KEY, "test")+"; charset=utf-8");
//        headers.put("apiHeader", Utils.getSha512(Constants.SECRET_KEY,"98@#4AIzaSyCEbR31470316213076")+"; charset=utf-8");
    }

    @Override
    public String getServiceURL(Context context) {
        createHeader(context);
        return getRequestUrl();
    }

    public void parseResponseCode(Object response) throws JSONException {
        if (response instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) response;

//            errorCode = DecodeUtills.getStringFromBase64(jsonObject.optString("error_code"));
//            errorDesc = DecodeUtills.getStringFromBase64(jsonObject.optString("error_desc"));
        }
    }

    @Override
    public void populateFromResponse(Object response) throws JSONException {
        parseResponseCode(response);
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorDesc() {
        return errorDesc;
    }

}
