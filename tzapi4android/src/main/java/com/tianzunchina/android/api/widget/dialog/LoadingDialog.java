package com.tianzunchina.android.api.widget.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

/**
 * 加载弹出框,默认加载框
 * Created by yqq on 2016/8/12.
 */
public class LoadingDialog extends AlertDialog {

    private static AlertDialog ad;
    private Activity mActivity;

    public LoadingDialog(Context context) {
        super(context);
        this.mActivity = (Activity) context;
    }

    public LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener, Activity mActivity) {
        super(context, cancelable, cancelListener);
        this.mActivity = mActivity;
    }

    public LoadingDialog(Context context, int themeResId, Activity mActivity) {
        super(context, themeResId);
        this.mActivity = mActivity;
    }

    public AlertDialog loadDialog(String msg) {
//        if (ad != null && ad.isShowing()) {
//            return ad;
//        }
//        View loadingView = mActivity.getLayoutInflater()
//                .inflate(R.layout.fullscreen_loading, null);
//        TextView tv = (TextView) loadingView.findViewById(R.id.tv_loading);
//        tv.setText(msg);
//        DialogInterface.OnKeyListener listener = new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//                    ad.dismiss();
//                }
//                return false;
//            }
//        };
//        ad = new AlertDialog.Builder(mActivity).setView(loadingView).setOnKeyListener(listener).show();
//        ad.setCanceledOnTouchOutside(false);
        return ad;
    }

    public static void closeDialog() {
        if (ad != null) {
            ad.dismiss();
        }
    }

}
