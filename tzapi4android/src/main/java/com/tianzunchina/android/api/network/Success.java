package com.tianzunchina.android.api.network;

import org.json.JSONObject;

/**
 * EventBus传输对象
 * CraetTime 2016-3-29
 * @author SunLiang
 */
class Success{
    JSONObject json;
    WebCallBackListener listenner;
    TZRequest request;
    public Success(JSONObject json, WebCallBackListener listenner, TZRequest request) {
        this.json = json;
        this.listenner = listenner;
        this.request = request;
    }
}