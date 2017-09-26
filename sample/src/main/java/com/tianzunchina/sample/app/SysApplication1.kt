package com.tianzunchina.sample.app

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.StrictMode
import android.support.multidex.MultiDex
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import com.tianzunchina.android.api.base.TZApplication
import com.tianzunchina.android.api.log.TZLog
import com.tianzunchina.android.api.log.TZLogLevel
import com.tianzunchina.android.api.log.TZToastLevel
import com.tianzunchina.android.api.log.TZToastTool

/**
 * Created by Administrator on 2017/5/25.
 */
class SysApplication1 : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        init()
    }

    val context: Context
        get() = applicationContext

    private fun init() {
        TZApplication.init(this)
        MultiDex.install(this) //防止方法数超额
        initStrictMode()
        TZLog.init(TZLogLevel.INFO) //设置日志最低打印等级
        TZToastTool.init(TZToastLevel.MARK) //设置Toast最低显示等级
        CustomActivityOnCrash.install(this)

    }

    //TODO 严格审查模式
    private fun initStrictMode() {
        val appFlags = this.applicationInfo.flags
        if (appFlags and ApplicationInfo.FLAG_DEBUGGABLE != 0) {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build())
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build())
        }
    }

    companion object {

        var instance: SysApplication1? = null
            private set

    }

}