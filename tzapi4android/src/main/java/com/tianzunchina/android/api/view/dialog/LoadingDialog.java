package com.tianzunchina.android.api.view.dialog;


import android.app.AlertDialog;
import android.content.Context;

import java.util.Timer;
import java.util.TimerTask;

/**
 * admin
 * 2016/7/7 0007.
 */

public class LoadingDialog extends AlertDialog {
    private long timeOut = 10 * 1000;
    private Timer timer;

    protected LoadingDialog(Context context) {
        super(context);
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LoadingDialog(Context context, int themeResId, long timeOut) {
        super(context, themeResId);
        this.timeOut = timeOut;
    }

    public void show(){
        super.show();
        timer = new Timer();
        timer.schedule(new TimeOutTask(), timeOut);
    }

    public void dismiss(){
        super.dismiss();
        if(timer != null){
            timer.cancel();
        }
    }

    class TimeOutTask extends TimerTask {
        @Override
        public void run() {
            LoadingDialog.super.dismiss();
        }
    }
}
