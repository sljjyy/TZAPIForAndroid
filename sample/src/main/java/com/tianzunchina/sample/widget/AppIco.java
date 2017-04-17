package com.tianzunchina.sample.widget;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.tianzunchina.sample.app.SysApplication;

/**
 * 应用下的图标里的名字和图片
 */
public class AppIco extends GVItem {
    private int nameID;
    private Class toClass;
    private int MsgCount;

    public AppIco(Class toClass, @StringRes int nameID, @DrawableRes int ico) {
        super();
        title = SysApplication.getInstance().getResources().getString(nameID);
        this.nameID = nameID;
        this.resID = ico;
        this.toClass = toClass;
    }

    public AppIco(@StringRes int nameID, @DrawableRes int ico) {
        super();
        this.nameID = nameID;
        this.resID = ico;
        title = SysApplication.getInstance().getResources().getString(nameID);
    }

    public AppIco(Class toClass, @StringRes int nameID, @DrawableRes int ico, int MsgCount) {
        super();
        title = SysApplication.getInstance().getResources().getString(nameID);
        this.nameID = nameID;
        this.resID = ico;
        this.toClass = toClass;
        this.MsgCount = MsgCount;
    }

    public String getName() {
        return title;
    }

    public int getNameID() {
        return nameID;
    }

    public void setName(int name) {
        this.nameID = name;
    }

    public Class getToClass() {
        return toClass;
    }

    public int getMsgCount() {
        return MsgCount;
    }

    public void setMsgCount(int msgCount) {
        MsgCount = msgCount;
    }
}
