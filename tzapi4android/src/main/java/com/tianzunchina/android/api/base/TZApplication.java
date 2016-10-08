package com.tianzunchina.android.api.base;

import android.app.Activity;
import android.support.multidex.MultiDexApplication;
import android.support.v4.util.ArrayMap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;
import com.tianzunchina.android.api.network.okhttp.TZOkHttpStack;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CraetTime 2016-3-14
 *
 * @author SunLiang
 */
public class TZApplication extends MultiDexApplication {
    private ArrayMap<String, Activity> mActivities = new ArrayMap<>();
    private static TZApplication instance;
    private RequestQueue mRequestQueue;
    private ExecutorService executorService;
    private int poolCount = 3;

    public static TZApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        executorService = Executors.newFixedThreadPool(poolCount);
    }

    public void execute(Runnable runnable){
        executorService.execute(runnable);
    }

    /**
     * 设置线程池大小
     * 在onCreate前执行
     * @param count
     */
    protected void setPoolCount(int count){
        poolCount = count;
    }

    /**
     * 添加当前开启的Activity
     * @param activity 当前开启的Activity
     */
    public void addActivity(Activity activity) {
        String key = activity.getClass().getName();
        if (mActivities.containsKey(key)) {
            finishActivity(key);
        }
        mActivities.put(key, activity);
    }

    /**
     * 关闭指定Activity
     * @param key 指定Activity的key
     */
    public void finishActivity(String key) {
        Activity activity = mActivities.get(key);
        if (activity != null) {
            activity.finish();
            mActivities.remove(key);
        }
    }

    public void finishActivity(int index) {
        Activity activity = mActivities.removeAt(index);
        if (activity != null) {
            activity.finish();
        }
    }

    /**
     * 退出应用
     */
    public void exit() {
        try {
            for(int i = mActivities.size() - 1; i >= 0; i--){
                finishActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    /**
     * 获取全局Volley队列实例
     *
     * @return
     */
    public RequestQueue getVolleyRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this, new TZOkHttpStack(new OkHttpClient()));
        }
        return mRequestQueue;
    }

    /**
     * 队列中添加请求
     * @param request
     */
    public static void addRequest(Request<?> request) {
        getInstance().getVolleyRequestQueue().add(request);
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
     * @param tag 请求标识
     */
    public static void cancelAllRequests(String tag) {
        if (getInstance().getVolleyRequestQueue() != null) {
            getInstance().getVolleyRequestQueue().cancelAll(tag);
        }
    }
}
