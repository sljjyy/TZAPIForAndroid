package com.tianzunchina.sample.util;

import com.tianzunchina.android.api.util.Config;

public class BootConfig extends Config {
	
	private static final String IS_BOOT = "isBooted";

	public void boot(){
//		保存用户名及密码信息 并标记是否记住密码及自动登录
		saveInfo(IS_BOOT, true);
	}
	
	public boolean isBoot(){
		boolean is = loadBoolean(IS_BOOT, false);
		return is;
	}
}