package com.tianzunchina.android.api.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tianzunchina.android.api.base.TZApplication;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP网络访问工具
 * CrateTime 2016-3-23
 * @author SunLiang
 */
public class HTTPWebAPI implements WebAPIable {

    @Override
    public void call(final TZRequest request, final WebCallBackListener listener) {
        callGet(request, listener);
    }
    public void callGet(final TZRequest request, final WebCallBackListener listener) {
        try{
            JsonObjectRequest jsonRequest = new JsonObjectRequest(request.getUrlParams(), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    listener.success(response, request);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.err(error.getMessage(), request);
                }
            });
            TZApplication.addRequest(jsonRequest);
        } catch (Exception e){
            listener.err(e.getMessage(), request);
        }

    }

    public void callPost(final TZRequest request, final WebCallBackListener listener) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, request.getUrl(), new JSONObject(request.getParams()),new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.success(response, request);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.err(error.getMessage(), request);
            }
        });
        TZApplication.addRequest(jsonRequest);
    }

}
