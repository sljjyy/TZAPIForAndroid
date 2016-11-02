package com.tianzunchina.android.api.widget.dialog;

import android.app.Activity;
import android.app.Dialog;

/**
 * admin
 * 2016/11/1 0001.
 */

public class DialogManager {
    private static Dialog dialog;

    public static void setDialog(Dialog dialog) {
        if (dialog != null) {
            dialog.dismiss();
        }
        DialogManager.dialog = dialog;

    }

    public static void show(Dialog dialog) {
        if (dialog == null) {
            return;
        }
        DialogManager.dialog = dialog;
        dialog.show();
    }

    public static void close() {
        if (dialog == null) return;
        dialog.dismiss();
    }

    public static void showMessage(Activity activity, String msg) {

        DialogManager.show(new LoadingDialog(activity).loadDialog(msg));
    }
}
