package com.tianzunchina.android.api.network;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tianzunchina.android.api.network.okhttp.TZOkHttpStack;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * admin
 * 2017/1/4 0004.
 */

public class ThreadTool {
    private static ThreadTool tool;
    private RequestQueue mRequestQueue;
    private ExecutorService executorService;
    private int poolCount = 3;
    private Application application;

    private ThreadTool(Application application){
        this.application = application;
        executorService = Executors.newFixedThreadPool(poolCount);
    }


    public static void init(Application application){
        tool = new ThreadTool(application);
    }

    public static void execute(Runnable runnable) {
        tool.executorService.execute(runnable);
    }

    /**
     * 获取全局Volley队列实例
     *
     * @return
     */
    public static RequestQueue getVolleyRequestQueue() {
        if (tool.mRequestQueue == null) {
            tool.mRequestQueue = Volley.newRequestQueue(tool.application, new TZOkHttpStack());
        }
        return tool.mRequestQueue;
    }

    /**
     * 队列中添加请求
     *
     * @param request
     */
    public static void addRequest(Request<?> request) {
        ThreadTool.getVolleyRequestQueue().add(request);
    }

    /**
     * 在请求中加入tag标识 并加入队列
     *
     * @param request
     * @param tag
     */
    public static void addRequest(Request<?> request, String tag) {
        request.setTag(tag);
        addRequest(request);
    }

    /**
     * 取消该tag标识对应的请求
     *
     * @param tag 请求标识
     */
    public static void cancelAllRequests(String tag) {
        if (ThreadTool.getVolleyRequestQueue() != null) {
            ThreadTool.getVolleyRequestQueue().cancelAll(tag);
        }
    }
}
