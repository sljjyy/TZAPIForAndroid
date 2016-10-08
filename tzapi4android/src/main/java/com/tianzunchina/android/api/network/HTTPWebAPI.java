package com.tianzunchina.android.api.network;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tianzunchina.android.api.base.TZApplication;

import org.json.JSONObject;

/**
 * HTTP网络访问工具
 * CraetTime 2016-3-23
 * @author SunLiang
 */
public class HTTPWebAPI implements WebAPIable {

    @Override
    public void call(final TZRequest request, final WebCallBackListenner listenner) {
        try{
            JsonObjectRequest jsonRequest = new JsonObjectRequest(request.getUrl(), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    listenner.success(response, request);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listenner.err(error.getMessage(), request);
                }
            });
            TZApplication.addRequest(jsonRequest);
        } catch (Exception e) {
            listenner.err(e.getMessage(), request);
        }
    }
}
