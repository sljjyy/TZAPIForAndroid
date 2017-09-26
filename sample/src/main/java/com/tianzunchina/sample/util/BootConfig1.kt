package com.tianzunchina.sample.util

import com.tianzunchina.android.api.util.Config

/**
 * Created by Administrator on 2017/6/1.
 */
class BootConfig1 : Config() {

    fun boot() {
        //		保存用户名及密码信息 并标记是否记住密码及自动登录
        saveInfo(IS_BOOT, true)
    }

    val isBoot: Boolean
        get() {
            val `is` = loadBoolean(IS_BOOT, false)!!
            return `is`
        }

    companion object {

        private val IS_BOOT = "isBooted"
    }
}