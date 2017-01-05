package com.tianzunchina.android.api.context;

import android.app.Activity;
import android.support.v4.util.ArrayMap;

/**
 * admin
 * 2017/1/5 0005.
 */

public class ContextManger {
    private static ArrayMap<String, Activity> mActivities = new ArrayMap<>();

    /**
     * 添加当前开启的Activity
     *
     * @param activity 当前开启的Activity
     */
    public static void addActivity(Activity activity) {
        String key = activity.getClass().getName();
        if (mActivities.containsKey(key)) {
            finishActivity(key);
        }
        mActivities.put(key, activity);
    }

    /**
     * 从队列中移除指定Activity
     *
     * @param key 指定Activity的key, 即className
     */
    public static void removeActivity(String key) {
        mActivities.remove(key);
    }


    /**
     * 关闭指定Activity
     *
     * @param key 指定Activity的key, 即className
     */
    public static void finishActivity(String key) {
        Activity activity = mActivities.get(key);
        if (activity != null) {
            activity.finish();
            mActivities.remove(key);
        }
    }

    public static void finishActivity(int index) {
        Activity activity = mActivities.removeAt(index);
        if (activity != null) {
            activity.finish();
        }
    }

    /**
     * 退出应用
     */
    public static void exit() {
        try {
            for (int i = mActivities.size() - 1; i >= 0; i--) {
                finishActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
