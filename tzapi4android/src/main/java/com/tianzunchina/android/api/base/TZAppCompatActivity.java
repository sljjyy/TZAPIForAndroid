package com.tianzunchina.android.api.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tianzunchina.android.api.context.ContextManger;

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
        ContextManger.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        ContextManger.removeActivity(this.getPackageName());
        super.onDestroy();
    }

    protected void showLoading(){

    }
}
