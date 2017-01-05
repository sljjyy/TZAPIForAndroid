package com.tianzunchina.android.api.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.tianzunchina.android.api.context.ContextManger;

/**
 * admin
 * 2016/11/4 0004.
 */

public class TZFragmentActivity extends FragmentActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ContextManger.addActivity(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        ContextManger.removeActivity(this.getPackageName());
        super.onDestroy();
    }
}
