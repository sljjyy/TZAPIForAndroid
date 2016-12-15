package com.tianzunchina.android.api.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * CraetTime 2016-3-14
 * @author SunLiang
 */
public class TZAppCompatActivity extends AppCompatActivity {
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
        TZApplication.getInstance().removeActivity(this.getPackageName());
        super.onDestroy();
    }

    protected void showLoading(){

    }
}
