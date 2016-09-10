package com.tianzunchina.android.api.utils;

import android.app.Dialog;

/**
 * 对话框管理
 * 2016/8/3 0003.
 */

public class DialogUtil {
    private static Dialog dialog;
    public static void setDialog(Dialog dialog){
        if(dialog != null){
            dialog.dismiss();
        }
        DialogUtil.dialog = dialog;
    }

    public static void show(Dialog dialog){
        setDialog(dialog);
        dialog.show();
    }

    public static void close(){
        if (dialog == null) return;
        dialog.dismiss();
    }
}
