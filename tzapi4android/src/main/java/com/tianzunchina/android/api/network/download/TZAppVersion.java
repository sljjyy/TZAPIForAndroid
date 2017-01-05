package com.tianzunchina.android.api.network.download;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public abstract class TZAppVersion implements Serializable{
    public static final String VERSION_CODE = "versionCode";
    public static final String VERSION_NAME = "versionName";
    public static final String VERSION_URL = "versionURL";
    public static final int MAX = 100;

    private int versionCode;
    private String versionName;
    private String versionURL;
    private String describe;//具体内容描述
    private boolean isImportant;//是否需要强制更新，true 强制，false 非强制

    public TZAppVersion() {
    }

//	public AppVersion(JSONObject json) {
//		try {
//			this.versionCode = json.getInt(VERSION_CODE);
//			this.versionName = json.getString(VERSION_NAME);
//			this.versionURL = json.getString(VERSION_URL);
////			this.versionSize = json.getInt(VERSION_SIZE);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}

//    public abstract void parse(JSONObject json);

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionURL() {
        return versionURL;
    }

    public void setVersionURL(String versionURL) {
        this.versionURL = versionURL;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }
}
