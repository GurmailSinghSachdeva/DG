package com.example.lenovo.discountgali.network.apicall;

import android.content.Context;

import com.example.lenovo.discountgali.model.LocalDealCategoryModel;
import com.example.lenovo.discountgali.model.ModelTopStores;
import com.example.lenovo.discountgali.model.ServerResponse;
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
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by lenovo on 19-01-2017.
 */

public class GetLocalDealCategoryApiCAll extends BaseApiCall {
    private String service_id;
    private int page_start,page_limit;
    private Context context;
    private String result;
    private String outputjson;
    private ServerResponse<LocalDealCategoryModel> serverResponse = new ServerResponse<>();

    public GetLocalDealCategoryApiCAll(int page_start, int page_limit) {
        this.page_start = page_start;
        this.page_limit = page_limit;
    }
    public GetLocalDealCategoryApiCAll() {
        this.page_start = Constants.PAGE_START_DEFAULT;
        this.page_limit = Constants.PAGE_LIMIT_DEFAULT;
    }
    @Override
    protected String getRequestUrl() {
        return ServerRequests.REQUEST_LOCAL_DEAL_CATEGORIES;
    }

    public void populateFromResponse(Object response) throws JSONException {
        super.populateFromResponse(response);
        result = (String) response;
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
                Syso.print("=====REQUEST======GETLOCALDEALCATEGORY " + outputjson);
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

    }

    private void parseData(String response) {

        if (response != null && !response.isEmpty()) {
            try {
                JSONObject json = new JSONObject(response);
                JSONObject dataobject = json.getJSONObject("Data");
                JSONArray listdata = dataobject.getJSONArray("List");

                serverResponse.data = JSONParsingUtils.getLocalDealCategories(listdata);
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
//        return serverResponseList;
        return serverResponse;
    }

    @Override
    public String getStringRequest() {

//        String requestBody =
//                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
//                "  <soap:Body>\n" +
//                "    <GetOnlineBrand xmlns=\"http://tempuri.org/\">\n" +
//                "      <pageCount>" + page_limit + "</pageCount>\n" +
//                "      <pageNo>" + page_start + "</pageNo>\n" +
//                "    </GetOnlineBrand>\n" +
//                "  </soap:Body>\n" +
//                "</soap:Envelope>";

        String requestBody = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "  <soap12:Body>\n" +
                "    <GetLocalDealCategories xmlns=\"http://DiscountGali.com/\" />\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>";
        return requestBody;
    }
    public ArrayList<LocalDealCategoryModel> getList()
    {
        return serverResponse.data;
    }
    public int getTotalRecords()
    {
        return serverResponse.totalCount;
    }

}
