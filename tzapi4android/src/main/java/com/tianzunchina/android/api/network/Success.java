package com.tianzunchina.android.api.network;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * EventBus传输对象
 * CraetTime 2016-3-29
 * @author SunLiang
 */
class Success{
    JSONObject json;
    String response;
    WebCallBackListener listenner;
    TZRequest request;
    public Success(String response, WebCallBackListener listenner, TZRequest request) {
        this.response = response;
        try {
            this.json = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.listenner = listenner;
        this.request = request;
    }

    public JSONObject getJson(){
        if (json == null) return new JSONObject();
        return json;
    }
}