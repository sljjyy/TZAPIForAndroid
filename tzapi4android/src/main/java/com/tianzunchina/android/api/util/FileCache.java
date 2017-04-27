package com.tianzunchina.android.api.util;

import android.os.Environment;

import com.tianzunchina.android.api.base.TZApplication;

import java.io.File;
import java.net.URL;
import java.util.Date;

/**
 * Created by admin on 2016/3/15 0015.
 */
public class FileCache {
    public boolean haveSDCard(){
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获得一个隐藏的缓存目录
     * @return 缓存目录
     */
    public File getCacheDir() {
        File dir = new File(Environment.getExternalStorageDirectory(),
                "." + TZApplication.getInstance().getPackageName());
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }

    public File getCacheJPG() {
        int count = 0;
        File file;
        do{
            file = new File(getCacheDir(), TimeConverter.SDF_FILE.format(new Date())  + "_" + count + ".jpg");
            count ++;
        } while (file.exists());
        return file;
    }

    /**
     * 获得该url的缓存路径
     * @param urlStr
     * *
     * @return 缓存路径
     */
    public String getSavePath(String urlStr) {
        String filename = String.valueOf(urlStr.hashCode());
        String type = "";
        try {
            URL url = new URL(urlStr);
            String name = url.getFile();
            type = "." + name.split(".")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new File(getCacheDir(), filename + type).getAbsolutePath();
    }

    /**
     * 判断该url文件是否已被缓存
     * @param urlStr
     * @return 是否已缓存
     */
    public Boolean haveCache(String urlStr){
        File file = getSaveFile(urlStr);
        return file != null && file.exists();
    }

    /**
     * 获得该url的缓存文件
     * @param url
     * @return 缓存文件
     */
    public File getSaveFile(String url) {
        File f = new File(getSavePath(url));
        return f;
    }

    /**
     * 获取缓存图片
     * @return
     */
    File getTempJPG() {
        File file = new File(getCacheDir(), "tmp.jpg");
        return file;
    }

    /**
     * 获取该url文件的文件名
     * @param url
     * @return 文件名
     */
    String url2fileName(URL url) {
        String fileName = url.getFile();
        fileName = url2fileName(fileName);
        return fileName;
    }

    /**
     * 截取url路径中的文件名
     * @param url
     * @return
     */
    String url2fileName(String url) {
        if (url.contains("=")) {
            url = url.split("=")[1];
        } else if (url.contains("/")) {
            String[] strs = url.split("/");
            url = strs[strs.length - 1];
        }
        url = url.replace("/", "_");
        return url;
    }

    public static File getThumbDir() {
        File dir = new File(Environment.getExternalStorageDirectory(),
                ".".concat(TZApplication.getInstance().getPackageName()));
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }
}