package com.tianzunchina.android.api.network;

/**
 * EventBus传输对象
 * CraetTime 2016-3-29
 * @author SunLiang
 */
public class Error {
    String msg;
    WebCallBackListener listenner;
    TZRequest request;

    public Error(String msg, WebCallBackListener listenner, TZRequest request) {
        this.msg = msg;
        this.listenner = listenner;
        this.request = request;
    }
}
