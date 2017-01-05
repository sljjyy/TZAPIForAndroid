package com.tianzunchina.android.api.login;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.tianzunchina.android.api.base.TZActivity;

/**
 * CraetTime 2016-3-22
 * @author SunLiang
 */
public abstract class TZLoginActivity extends TZActivity {
    private LoginListenner loginListenner;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(getContentView());
    }

    protected abstract int getContentView();


    protected void setAccountRegex(String regex){

    }


}
