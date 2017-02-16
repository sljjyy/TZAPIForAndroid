package com.tianzunchina.android.api.login;

import android.support.v4.util.ArrayMap;

import com.tianzunchina.android.api.network.SOAPWebAPI;
import com.tianzunchina.android.api.network.TZRequest;
import com.tianzunchina.android.api.network.WebCallBackListener;

/**
 * CraetTime 2016-3-22
 *
 * @author SunLiang
 */
public class Luncher {
    private WebCallBackListener listenner;
    private String url;
    private String method;
    private ArrayMap<String, Object> params;

    public Luncher(String url, String method, ArrayMap<String, Object> params, WebCallBackListener listenner) {
        this.url = url;
        this.method = method;
        this.params = params;
        this.listenner = listenner;
    }

    public void login() {
        TZRequest tzRequest = new TZRequest(url, method, params);
        if (params != null) {
            for (int i = 0; i < params.size(); i++) {
                tzRequest.addParam(params.keyAt(i), params.valueAt(i));
            }
        }
        new SOAPWebAPI().call(tzRequest, listenner);
    }

}
