package com.tianzunchina.sample.app.download;

import com.tianzunchina.android.api.network.download.TZAppVersion;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 版本升级对象
 * Created by zwt on 2017/1/3.
 */
public class AppVersion extends TZAppVersion {
	public static final String VERSION_CODE = "versionCode";
	public static final String VERSION_NAME = "versionName";
	public static final String VERSION_URL = "versionURL";

	/**
	 * 此方法用于JSONObject解析，可根据接收到的值一一对应到TZAppVersion的相应的属性中
	 *
	 * @param json
     */
    public AppVersion(JSONObject json) {
		parse(json);
	}

	public void parse(JSONObject json) {

	}
}
