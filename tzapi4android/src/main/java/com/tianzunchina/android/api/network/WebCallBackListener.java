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
    void success(String response, TZRequest request);
    void err(String err, TZRequest request);
}
