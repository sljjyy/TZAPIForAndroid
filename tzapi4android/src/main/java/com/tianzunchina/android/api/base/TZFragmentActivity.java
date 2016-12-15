package com.tianzunchina.android.api.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/**
 * admin
 * 2016/11/4 0004.
 */

public class TZFragmentActivity extends FragmentActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        TZApplication.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        TZApplication.getInstance().removeActivity(this.getPackageName());
        super.onDestroy();
    }
}
