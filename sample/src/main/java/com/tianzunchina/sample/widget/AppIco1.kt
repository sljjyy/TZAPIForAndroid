package com.tianzunchina.sample.widget

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.tianzunchina.sample.app.SysApplication1

/**
 * Created by Administrator on 2017/5/31.
 */
class AppIco1 : GVItem1 {
    var nameID: Int = 0
        private set
    var toClass: Class<*>? = null
        private set
    var msgCount: Int = 0

    constructor(toClass: Class<*>, @StringRes nameID: Int, @DrawableRes ico: Int) : super() {
        title = SysApplication1.instance!!.resources.getString(nameID)
        this.nameID = nameID
        this.resID = ico
        this.toClass = toClass
    }

    constructor(@StringRes nameID: Int, @DrawableRes ico: Int) : super() {
        this.nameID = nameID
        this.resID = ico
        title = SysApplication1.instance!!.resources.getString(nameID)
    }

    constructor(toClass: Class<*>, @StringRes nameID: Int, @DrawableRes ico: Int, MsgCount: Int) : super() {
        title = SysApplication1.instance!!.resources.getString(nameID)
        this.nameID = nameID
        this.resID = ico
        this.toClass = toClass
        this.msgCount = MsgCount
    }

    val name: String
        get() = title!!

    fun setName(name: Int) {
        this.nameID = name
    }
}
