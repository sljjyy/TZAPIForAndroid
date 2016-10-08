package com.tianzunchina.android.api.network;

/**
 * WebServer访问接口
 * CraetTime 2016-3-22
 * @author SunLiang
 */
public interface WebAPIable {

    void call(TZRequest request, final WebCallBackListener listenner);
}
