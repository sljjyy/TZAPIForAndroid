package com.tianzunchina.android.api.network;

import org.json.JSONObject;

/**
 * admin
 * 2016/3/23 0023.
 */
public interface WebCallBackListener {
    /**
     * 方法过时 为保证通用性统一使用success(String response, TZRequest request);
     * @param jsonObject
     * @param request
     */
    @Deprecated
    void success(JSONObject jsonObject, TZRequest request);

    /**
     * 为了保持通用性(如SoapObjct的接收)修改了之前的String类型 改为Object
     * @param response 要获得原有String类型请toString()
     * @param request
     */
    void success(Object response, TZRequest request);
    void err(String err, TZRequest request);
}
