package com.tianzunchina.sample.app;

import com.tianzunchina.android.api.network.download.TZAppVersion;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 版本升级对象
 * Created by zwt on 2017/1/3.
 */
public class AppVersion extends TZAppVersion {
	/**
	 * 此方法用于JSONObject解析，可根据接收到的值一一对应到TZAppVersion的相应的属性中
	 * @param json
     */
    public AppVersion(JSONObject json) {
		try {
			setVersionCode(json.getInt(VERSION_CODE));
			setVersionName(json.getString(VERSION_NAME));
			setVersionURL(json.getString(VERSION_URL));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
