package com.tianzunchina.android.api.util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.tianzunchina.android.api.base.TZApplication;

/**
 * 配置信息管理的父类
 * 所有用到的配置信息管理都继承此类 以便于对信息进行保存及读取
 * CraetTime 2016-3-3
 * @author SunLiang
 */
public class Config {
    private SharedPreferences share = null;
    private Editor editor = null;
    private static final String PREFERENCE_NAME = "saveInfo";

    /**
     * @return 获取一个唯一的私有的SharedPreferences
     */
    private void initSP() {
        if (share == null) {
            share = TZApplication.getInstance().getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        }
    }

    /**
     * 提供配置信息修改的编辑器
     * @return 获取一个唯一的私有的Editor
     */
    private void initEd(){
        if (editor == null) {
            if (share == null) {
                initSP();
            }
            editor = share.edit();
            editor.apply();
        }
    }

    /**
     * 提交对编辑器内容的修改
     * 在对属性editor进行修改后请调用此方法进行保存
     * @return 无返回值
     */
    private void edCommit() {
        editor.commit();
    }

    /**
     * 读取配置中key对应的String内容
     * @param key 所需内容对应的键值
     * @return 返回key对应的内容 找不到对应内容时返回null
     */
    protected String loadString(String key) {
        initSP();
        return share.getString(key, null);
    }

    /**
     * 读取配置中key对应的String内容
     * @param key 所需内容对应的键值
     * @param defVal 在找不到key对应内容时返回defVal
     * @return 返回key对应的内容 找不到对应内容时返回defVal
     */
    protected String loadString(String key, String defVal) {
        initSP();
        return share.getString(key, defVal);
    }

    /**
     * 读取配置中key对应的boolean内容
     * @param key 所需内容对应的键值
     * @return 返回key对应的内容 找不到对应内容时返回false
     */
    protected Boolean loadBoolean(String key) {
        initSP();
        return share.getBoolean(key, false);
    }

    /**
     * 读取配置中key对应的boolean内容
     * @param key 所需内容对应的键值
     * @param defVal 在找不到key对应内容时返回defVal
     * @return 返回key对应的内容 找不到对应内容时返回defVal
     */
    protected Boolean loadBoolean(String key , Boolean defVal) {
        initSP();
        return share.getBoolean(key, defVal);
    }

    /**
     * 读取配置中key对应的int内容
     * @param key 所需内容对应的键值
     * @return 返回key对应的内容 找不到对应内容时返回0
     */
    protected int loadInt(String key) {
        initSP();
        return share.getInt(key, 0);
    }

    /**
     * 读取配置中key对应的boolean内容
     * @param key 所需内容对应的键值
     * @param defVal 在找不到key对应内容时返回defVal
     * @return 返回key对应的内容 找不到对应内容时返回defVal
     */
    protected int loadInt(String key, int defVal) {
        initSP();
        return share.getInt(key, defVal);
    }

    /**
     * 读取配置中key对应的long内容
     * @param key 所需内容对应的键值
     * @return 返回key对应的内容 找不到对应内容时返回0
     */
    protected long loadLong(String key) {
        initSP();
        return share.getLong(key, 0);
    }

    /**
     * 读取配置中key对应的boolean内容
     * @param key 所需内容对应的键值
     * @param defVal 在找不到key对应内容时返回defVal
     * @return 返回key对应的内容 找不到对应内容时返回defVal
     */
    protected long loadLong(String key, long defVal) {
        initSP();
        return share.getLong(key, defVal);
    }

    /**
     * 读取配置中key对应的float内容
     * @param key 所需内容对应的键值
     * @return 返回key对应的内容 找不到对应内容时返回0
     */
    protected float loadFloat(String key) {
        initSP();
        return share.getFloat(key, 0f);
    }

    /**
     * 读取配置中key对应的float内容
     * @param key 所需内容对应的键值
     * @param defVal 在找不到key对应内容时返回defVal
     * @return 返回key对应的内容 找不到对应内容时返回defVal
     */
    protected float loadFloat(String key, float defVal) {
        initSP();
        return share.getFloat(key, defVal);
    }

    /**
     * 将key及对应的String类型的val保存至配置中
     * @param key 保存的键值
     * @param val key对应的String类型的值
     * @return 无返回值
     */
    protected void saveInfo(String key, String val) {
        initEd();
        editor.putString(key, val);
        edCommit();
    }

    /**
     * 将key及对应的String类型的val保存至配置中 并提交修改
     * @param key 保存的键值
     * @param val key对应的boolean类型的值
     * @return 无返回值
     */
    protected void saveInfo(String key , Boolean val) {
        initEd();
        editor.putBoolean(key, val);
        edCommit();
    }

    /**
     * 将key及对应的String类型的val保存至配置中 并提交修改
     * @param key 保存的键值
     * @param val key对应的int类型的值
     * @return 无返回值
     */
    protected void saveInfo(String key, int val) {
        initEd();
        editor.putInt(key, val);
        edCommit();
    }

    /**
     * 将key及对应的String类型的val保存至配置中 并提交修改
     * @param key 保存的键值
     * @param val key对应的long类型的值
     * @return 无返回值
     */
    protected void saveInfo(String key , long val) {
        initEd();
        editor.putLong(key,val);
        edCommit();
    }

    /**
     * 将key及对应的String类型的val保存至配置中 并提交修改
     * @param key 保存的键值
     * @param val key对应的Float类型的值
     * @return 无返回值
     */
    protected void saveInfo(String key, float val) {
        initEd();
        editor.putFloat(key, val);
        edCommit();
    }

    /**
     * 删除key对应的配置信息
     * @param key 键值
     * @return 无返回值
     */
    protected void removeInfo(String key ) {
        initEd();
        editor.remove(key);
        edCommit();
    }

}
