package com.tianzunchina.android.api.network.download;

import org.json.JSONException;
import org.json.JSONObject;

public class AppVersion {
	public static final String VERSION_CODE = "versionCode";
	public static final String VERSION_NAME = "versionName";
	public static final String VERSION_URL = "versionURL";
	public static final String VERSION_SIZE = "versionSize";

	private int versionCode;
	private String versionName;
	private String versionURL;
	private int versionSize = 100;

	public AppVersion() {
	}
	
	public AppVersion(JSONObject json) {
		try {
			this.versionCode = json.getInt(VERSION_CODE);
			this.versionName = json.getString(VERSION_NAME);
			this.versionURL = json.getString(VERSION_URL);
//			this.versionSize = json.getInt(VERSION_SIZE);
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

	@Override
	public String toString() {
		return "AppVersion [versionCode=" + versionCode + ", versionName="
				+ versionName + ", versionURL=" + versionURL + ", versionSize="
				+ versionSize + "]";
	}
}
