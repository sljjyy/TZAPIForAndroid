package com.tianzunchina.android.api.network.download;

import com.tianzunchina.android.api.util.Config;

public class TZUpdateConfig extends Config {

	private static final String LAST_VERSION_CODE = "LastVersionCode";
	private static final String LAST_VERSION_NAME = "LastVersionName";

	public void cancelUpdate(int versionCode, String versionName){
		saveInfo(LAST_VERSION_CODE, versionCode);
		saveInfo(LAST_VERSION_NAME, versionName);
	}

	/**
	 * 是否已取消该版本升级
	 * @param versionCode 服务器最新版本号
	 * @param versionName 服务器最新版本名称
     * @return
     */
	public boolean isCanceled(int versionCode, String versionName){
		int code = loadInt(LAST_VERSION_CODE, 0);
		String name = loadString(LAST_VERSION_NAME);
		if(name == null){
			return false;
		}
		if(code == versionCode && name.equals(versionName)){
			return true;
		}
		return false;
	}

	/**
	 * 清除记录
	 */
	public void clear(){
		removeInfo(LAST_VERSION_CODE);
		removeInfo(LAST_VERSION_NAME);
	}

}