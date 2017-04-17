package com.tianzunchina.android.api.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.tianzunchina.android.api.base.TZApplication;

import org.json.JSONException;
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

    public void callByMethod(int method, final TZRequest request, final WebCallBackListener listener) {
        StringRequest stringRequest = new StringRequest(method, request.getUrlParams(),
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.success(response, request);
                try {
                    listener.success(new JSONObject(response), request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.err(error.getMessage(), request);
            }
        });
        ThreadTool.addRequest(stringRequest);
    }
    public void callGet(final TZRequest request, final WebCallBackListener listener) {
        callByMethod(Request.Method.GET, request, listener);
     }

    public void callPost(final TZRequest request, final WebCallBackListener listener) {
        callByMethod(Request.Method.POST, request, listener);
    }

}
