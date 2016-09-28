package com.tianzunchina.android.api.log;

import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.tianzunchina.android.api.base.TZApplication;

/**
 * Toast信息工具
 * CraetTime 2016-3-24
 * @author SunLiang
 */
public class TZToastTool {
    private static int showLv = 0;
    /**
     * 初始化参数
     * @param showLevel {@linkplain TZToastLevel 日志显示最低等级}  默认0时不显示
     */
    public static void init(int showLevel){
        showLv = showLevel;
    }

    public static void  mark(String msg){
        show(msg, Toast.LENGTH_SHORT, TZToastLevel.MARK);
    }

    public static void  debug(String msg){
        show(msg, Toast.LENGTH_SHORT, TZToastLevel.DEBUG);
    }

    public static void  essential(String msg){
        show(msg, Toast.LENGTH_SHORT, TZToastLevel.ESSENTIAL);
    }


    private static void show(String msg, int time, int level){
        if(showLv < level || msg == null || msg.isEmpty()){
            return;
        }
        Toast.makeText(TZApplication.getInstance(), msg, time).show();
    }

}
