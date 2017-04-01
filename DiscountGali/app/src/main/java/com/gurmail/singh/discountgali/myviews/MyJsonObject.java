package com.gurmail.singh.discountgali.myviews;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 04-02-2017.
 */

public class MyJsonObject extends JSONObject {

    public MyJsonObject(JSONObject parent) throws JSONException {
        super(parent.toString());
    }
    @Override
    public String getString(String name) throws JSONException {
        if(this.has(name) && this.opt(name)!=null)
        return super.optString(name);
        return "";
    }

    @Override
    public int getInt(String name) throws JSONException {
        if(this.has(name) && this.opt(name)!=null)
        return super.optInt(name);
        return -1;
    }
}
