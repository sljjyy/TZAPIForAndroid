package com.tianzunchina.android.api.base;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.tianzunchina.android.api.log.TZLog;

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
        TZApplication.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void showLoading(){

    }
}
