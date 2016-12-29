package com.tianzunchina.android.api.network.download;

import org.json.JSONException;
import org.json.JSONObject;

public class AppVersion {
    private String VERSION_CODE;
    private String VERSION_NAME;
    private String VERSION_URL;
    private String VERSION_SIZE;
    private String DESCRIBE;
    private String ISIMPORTANT;

    private int versionCode;
    private String versionName;
    private String versionURL;
    private int versionSize = 100;
    private String describe;
    private boolean isImportant;

    public AppVersion() {
    }

    public AppVersion(JSONObject json, String versionCode, String versionName, String versionURL,
                      String versionSize, String describe, String isImportant) {
        this.VERSION_CODE = versionCode;
        this.VERSION_NAME = versionName;
        this.VERSION_URL = versionURL;
        this.VERSION_SIZE = versionSize;
        this.DESCRIBE = describe;
        this.ISIMPORTANT = isImportant;
        try {
            if (json.has(VERSION_CODE)) {
                this.versionCode = json.getInt(VERSION_CODE);
            }
            if (json.has(VERSION_NAME)) {
                this.versionName = json.getString(VERSION_NAME);
            }
            if (json.has(VERSION_URL)) {
                this.versionURL = json.getString(VERSION_URL);
            }
            if (json.has(VERSION_SIZE)) {
                this.versionSize = json.getInt(VERSION_SIZE);
            }
            if (json.has(DESCRIBE)) {
                this.describe = json.getString(DESCRIBE);
            }
            if (json.has(ISIMPORTANT)) {
                this.isImportant = json.getBoolean(ISIMPORTANT);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getVersionSize() {
        return versionSize;
    }

    public void setVersionSize(int versionSize) {
        this.versionSize = versionSize;
    }

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

    @Override
    public String toString() {
        return "AppVersion [versionCode=" + versionCode + ", versionName="
                + versionName + ", versionURL=" + versionURL + ", versionSize="
                + versionSize + "]";
    }
}
