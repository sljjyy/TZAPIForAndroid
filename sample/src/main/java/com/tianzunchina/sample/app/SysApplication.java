package com.tianzunchina.sample.app;

import android.content.pm.ApplicationInfo;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.tianzunchina.android.api.base.TZApplication;
import com.tianzunchina.android.api.log.TZLog;
import com.tianzunchina.android.api.log.TZLogLevel;
import com.tianzunchina.android.api.log.TZToastLevel;
import com.tianzunchina.android.api.log.TZToastTool;

public class SysApplication extends TZApplication {

	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}

	private void init() {
		MultiDex.install(this); //防止方法数超额
		initStrictMode();
		TZLog.init(TZLogLevel.INFO); //设置日志最低打印等级
		TZToastTool.init(TZToastLevel.MARK); //设置Toast最低显示等级
	}

	//TODO 严格审查模式
	private void initStrictMode(){
		int appFlags = this.getApplicationInfo().flags;
		if ((appFlags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads()
					.detectDiskWrites()
					.detectNetwork()
					.penaltyLog()
					.build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectLeakedSqlLiteObjects()
					.penaltyLog()
					.penaltyDeath()
					.build());
		}
	}

}