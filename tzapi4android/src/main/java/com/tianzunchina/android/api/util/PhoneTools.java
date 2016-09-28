package com.tianzunchina.android.api.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.tianzunchina.android.api.base.TZApplication;

import java.io.File;

/**
 * 手机信息工具类
 * CraetTime 2016-3-4
 * @author SunLiang
 */
public class PhoneTools {
    TZApplication app = TZApplication.getInstance();
    TelephonyManager tm = (TelephonyManager) app.getSystemService(Context.TELEPHONY_SERVICE);
    PackageManager pm = app.getPackageManager();
    private static PhoneTools instence;

    private PhoneTools(){}

    public static PhoneTools getInstence(){
        if(instence == null){
            instence = new PhoneTools();
        }
        return instence;
    }

    /**
     * 应用版本号
     * @return
     */
    public int getVersionCode() {
        int version = 0;
        try {
            PackageInfo packInfo = pm.getPackageInfo(app.getPackageName(), 0);
            version = packInfo.versionCode;
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 应用版本名称
     * @return
     */
    public String getVersionName(){
        String versionName = null;
        try {
            PackageInfo packInfo = pm.getPackageInfo(app.getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 唯一的设备ID： GSM手机的 IMEI 和 CDMA手机的 MEID.
     * @return 获得设备ID
     */
    public String getDeviceId(){
        return tm.getDeviceId();
    }

    /**
     * 手机号码
     * @return 获取本机号码
     */
    public String getPhoneNumber(){
        return tm.getLine1Number();
    }


    /**
     * 打开系统设置
     * @param context
     */
    public void showSettingDate(Context context ) {
        context.startActivity(new Intent(Settings.ACTION_DATE_SETTINGS));
    }

    /**
     * 安装APK(获取意图)
     * @param path .apk文件路径
     * @return
     */
    public Intent getApkFileIntent(String path)  {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(path));
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        return intent;
    }

    public Intent getImageFileIntent(File file) {
        Uri uri = Uri.fromFile(file);
        return getImageFileIntent(uri);
    }

    /**
     * 通过系统图片浏览器打开图片(获取意图)
     * @param uri
     * @return
     */
    public Intent getImageFileIntent(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    /**
     * 打开时间设置页面
     * @param activity
     */
    public void  openDatetimeSetting(Activity activity ) {
        openSetting(activity, SETTING_DATETIME);
    }

    /**
     * 打开GPS定位设置页面
     * @param activity
     */
    public void openGPSSetting(Activity activity ) {
        openSetting(activity, SETTING_GPS);
    }

    /**
     * 打开设置界面
     */
    public void openSetting(Activity activity , String setting) {
        Intent intent = new Intent("/");
        try {
            ComponentName cm = new ComponentName("com.android.settings",
                    "com.android.settings." + setting);
            intent.setComponent(cm);
            intent.setAction(Intent.ACTION_VIEW);
            activity.startActivityForResult(intent, 0);
        } catch (Exception e) {
        }
    }

    private static final String SETTING_DATETIME = "DateTimeSettings";
    private static final String  SETTING_GPS = "SecuritySettings";
}
