package com.tianzunchina.android.api.network;

import org.json.JSONObject;

/**
 * admin
 * 2016/3/23 0023.
 */
public interface WebCallBackListener {
    void success(JSONObject jsonObject, TZRequest request);
    void err(String err, TZRequest request);
}
