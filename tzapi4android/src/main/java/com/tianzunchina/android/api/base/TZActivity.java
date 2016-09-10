package com.tianzunchina.android.api.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * CraetTime 2016-3-14
 * @author SunLiang
 */
public class TZActivity extends Activity {
    public static final int OK = 1;
    public static final int NO = 0;
    public static final int ERR = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TZApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TZApplication.getInstance().finishActivity(this.getClass().getName());
    }

    protected void showLoading(){

    }
}
