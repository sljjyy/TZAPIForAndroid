package com.tianzunchina.android.api.network;

import android.support.v4.util.ArrayMap;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求实体类
 * CraetTime 2016-3-15
 * @author SunLiang
 */
public class TZRequest {
    public String service = null; //服务器地址
    public String method = null; //调用方法
    ArrayMap<String, Object> params = new ArrayMap<String, Object>(); //参数

    public TZRequest(String service, String method) {
        this.service = service;
        this.method = method;
    }

    public TZRequest(String service, String method, ArrayMap<String, Object> params) {
        if(!service.endsWith("/")){
            service += service + "/";
        }
        this.service = service;
        this.method = method;
        this.params = params;
    }

    public void addParam(String key, Object val){
        params.put(key, val);
    }

    public void addAllParam(Map<String, Object> map){
        params.putAll(map);
    }

    public Object findParam(String key) {
        return params.get(key);
    }



    public void removeParam(String key){
        params.remove(key);
    }

    /**
     * 拼接GET请求参数
     * @return
     */
    String getParamsMap(){
        String str = "?";
        for (int i = 0; i < params.size(); i++) {
            str += params.keyAt(i) + "=" + params.valueAt(i) + "&";
        }
        return str.substring(0, str.length() - 1);
    }
    /**
     * 拼接POST请求参数
     * @return
     */
    public Map<String, String> getParams() {
        //POST 参数
        Map<String, String> paramsPost = new HashMap<>();
        for (int i = 0; i < params.size(); i++) {
            paramsPost.put(params.keyAt(i),params.valueAt(i).toString());
        }
        return paramsPost;
    }

    /**
     * 获取GET请求完整URL
     * @return
     */
    public String getUrlParams(){
        return  service + method + getParamsMap();
    }
    /**
     * 获取POST请求完整URL
     * @return
     */
    public String getUrl(){
        return  service + method;
    }
}
