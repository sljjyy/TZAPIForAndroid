package com.tianzunchina.android.api.view.dialog;

import com.tianzunchina.android.api.base.TZApplication;

/**
 * admin
 * 2016/7/8 0008.
 */

public class LoadingTool {
    private static LoadingDialog dialog;

    public static void show(){
        if(dialog == null){
            dialog = new LoadingDialog(TZApplication.getInstance());
        }
    }
}
