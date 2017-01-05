package com.tianzunchina.android.api.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.tianzunchina.android.api.context.ContextManger;
import com.tianzunchina.android.api.network.ThreadTool;

/**
 * CraetTime 2016-3-14
 *
 * @author SunLiang
 */
public class TZApplication extends Application {

    private static Application instance;

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init(this);
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static void init(Application application){
        ThreadTool.init(application);
        instance = application;
    }

    /**
     * use{@link com.tianzunchina.android.api.network.ThreadTool}
     * @param runnable
     */
    @Deprecated
    public static void execute(Runnable runnable) {
        ThreadTool.execute(runnable);
    }


    /**
     * 添加当前开启的Activity
     *
     * @param activity 当前开启的Activity
     */
    public void addActivity(Activity activity) {
        ContextManger.addActivity(activity);
    }

    /**
     * 从队列中移除指定Activity
     *
     * @param key 指定Activity的key, 即className
     */
    public void removeActivity(String key) {
        ContextManger.removeActivity(key);
    }


    /**
     * 关闭指定Activity
     * use{@link com.tianzunchina.android.api.context.ContextManger }
     * @param key 指定Activity的key, 即className
     */
    @Deprecated
    public void finishActivity(String key) {
        ContextManger.finishActivity(key);
    }

    /**
     * use{@link com.tianzunchina.android.api.context.ContextManger }
     * @param index
     */
    @Deprecated
    public void finishActivity(int index) {
        ContextManger.finishActivity(index);
    }

    /**
     * use{@link com.tianzunchina.android.api.context.ContextManger }
     * 退出应用
     */
    @Deprecated
    public void exit() {
        ContextManger.exit();
    }

    /**
     * 获取全局Volley队列实例
     *
     * use{@link com.tianzunchina.android.api.network.ThreadTool}
     * @return
     */
    @Deprecated
    public RequestQueue getVolleyRequestQueue() {
        return ThreadTool.getVolleyRequestQueue();
    }

    /**
     * 队列中添加请求
     * use{@link com.tianzunchina.android.api.network.ThreadTool}
     * @param request
     */
    @Deprecated
    public static void addRequest(Request<?> request) {
        ThreadTool.addRequest(request);
    }

    /**
     * 在请求中加入tag标识 并加入队列
     * use{@link com.tianzunchina.android.api.network.ThreadTool}
     * @param request
     * @param tag
     */
    @Deprecated
    public static void addRequest(Request<?> request, String tag) {
        ThreadTool.addRequest(request, tag);
    }

    /**
     * 取消该tag标识对应的请求
     * use{@link com.tianzunchina.android.api.network.ThreadTool}
     * @param tag 请求标识
     */
    @Deprecated
    public static void cancelAllRequests(String tag) {
        ThreadTool.cancelAllRequests(tag);
    }
}
