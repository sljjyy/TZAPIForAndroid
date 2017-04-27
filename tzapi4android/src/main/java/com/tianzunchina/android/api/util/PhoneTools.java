package com.tianzunchina.android.api.util;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.telephony.TelephonyManager;

import com.tianzunchina.android.api.base.TZApplication;

import java.io.File;

/**
 * 手机信息工具类
 * CraetTime 2016-3-4
 * @author SunLiang
 */
public class PhoneTools {
    Application app = TZApplication.getInstance();
    TelephonyManager tm = (TelephonyManager) app.getSystemService(Context.TELEPHONY_SERVICE);
    PackageManager pm = app.getPackageManager();
    private static PhoneTools instence;

    private PhoneTools(){}

    public static PhoneTools getInstance(){
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
    public Intent getApkFileIntent(String path,Context context)  {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion < 24) {
            Uri uri = Uri.fromFile(new File(path));
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        } else {
            Uri uri = getUriToFile(context,new File(path));
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }

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

    static Uri getUriToFile(@NonNull Context context, @NonNull File file) {
        String packageName = context.getApplicationContext().getPackageName();
        String authority = packageName + ".tianzunchina.fileprovider";
        return FileProvider.getUriForFile(context, authority, file);
    }

    private static final String SETTING_DATETIME = "DateTimeSettings";
    private static final String  SETTING_GPS = "SecuritySettings";
}
