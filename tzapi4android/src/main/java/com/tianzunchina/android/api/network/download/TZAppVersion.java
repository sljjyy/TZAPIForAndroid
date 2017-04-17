package com.tianzunchina.android.api.network.download;

import org.json.JSONException;
import org.json.JSONObject;

public class TZAppVersion extends TZFile{
    public static final int MAX = 100;//默认进度条百分比

    private int versionCode;
    private String describe;//具体内容描述
    private boolean isImportant;//是否需要强制更新，true 强制，false 非强制

    public TZAppVersion() {
    }

    public TZAppVersion(JSONObject json){
        try {
            json = json.getJSONObject("Version");
            versionCode = json.getInt("VersionCode");
            setVersionName(json.getString("VersionName"));
            setVersionURL(json.getString("Url"));
            if(json.has("Describe")){
                describe = json.getString("Describe").replace("\\n", "");
            }
            isImportant = json.getBoolean("IsImportant");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    public abstract void parse(JSONObject json);

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return getFileName();
    }

    public void setVersionName(String versionName) {
        setFileName(versionName);
    }

    public String getVersionURL() {
        return getDownloadURL();
    }

    public void setVersionURL(String versionURL) {
        setDownloadURL(versionURL);
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
