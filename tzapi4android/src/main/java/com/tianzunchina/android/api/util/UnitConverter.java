package com.tianzunchina.android.api.util;

import android.app.Application;
import android.util.Base64;

import com.tianzunchina.android.api.base.TZApplication;
import com.tianzunchina.android.api.util.model.LatLon;
import com.tianzunchina.android.api.util.trans.Trans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 常用数据类型转换器
 * CraetTime 2016-3-7
 * @author SunLiang
 */
public class UnitConverter {
    private static UnitConverter converter;
    private static  Application app = TZApplication.getInstance();

    public static UnitConverter getInstance(){
        if(converter == null){
            converter = new UnitConverter();
        }
        return converter;
    }

    public static UnitConverter getInstance(Application app){
        if(converter == null){
            converter = new UnitConverter(app);
        }
        return converter;
    }
    private UnitConverter(Application app) {
        this.app = app;
    }

    private UnitConverter(){
    }

    /**
     * 把bytes转换为base64
     */
    public static String byte2base64(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        String str = Base64.encodeToString(bytes, Base64.DEFAULT);
        return str;
    }
    /**
     * 把base64转换为bytes
     */
    public byte[] base642byte(String str) {
        if (str == null || str == "") {
            return null;
        }
        return Base64.decode(str, Base64.DEFAULT);
    }

    public static byte[] file2byte(File file) {
        byte[] bytes = null;
        try {
            if (file != null && file.exists()) {
                InputStream is = new FileInputStream(file);
                int length = (int) file.length();
                if (length > Integer.MAX_VALUE) {
                    System.err.println("this file is max ");
                }
                bytes = new byte[length];
                int offset = 0;
                int numRead = 0;
                while (offset < bytes.length
                        && (numRead = is.read(bytes, offset, bytes.length
                        - offset)) >= 0) {
                    offset += numRead;
                }
                if (offset < bytes.length) {
                }
                is.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 由dp至px进行转换
     * 例如 dip2px(1) 将得到1dp对应的px值
     * @param dpValue
     * @return
     */
    public static int dip2px(float dpValue) {
        final float scale = app.getResources()
                .getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 由dp至px进行转换
     * 例如 px2dip(1) 将得到1px对应的dp值
     * @param pxValue
     * @return
     */
    public int px2dip(float pxValue) {
        final float scale = app.getResources()
                .getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 84坐标系转大地2000
     * @param lon 经度
     * @param lat 纬度
     * @return
     */
    public LatLon WGS84ToCGCS2000(double lon, double lat){
        if(lon == 0){
            return new LatLon(0, 0);
        }
        Trans trans = new Trans(1);
        double x = lon, y = lat;
        double[] coords = trans.doTrans(x, y);
        x = coords[0];
        y = coords[1] - 50;
        return new LatLon(y, x);
    }
}
