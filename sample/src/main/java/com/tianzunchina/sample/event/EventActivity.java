package com.tianzunchina.sample.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

import com.tianzunchina.android.api.base.TZAppCompatActivity;
import com.tianzunchina.android.api.log.TZToastTool;
import com.tianzunchina.android.api.network.HTTPWebAPI;
import com.tianzunchina.android.api.network.TZRequest;
import com.tianzunchina.android.api.network.WebCallBackListener;
import com.tianzunchina.android.api.widget.photo.TZPhotoBoxGroup;
import com.tianzunchina.android.api.widget.photo.TZPhotoBoxOne;
import com.tianzunchina.sample.R;


public class EventActivity extends TZAppCompatActivity {
    private TZPhotoBoxGroup photoBoxGroup;
    private TZPhotoBoxOne pbOne, pbOne2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        photoBoxGroup = (TZPhotoBoxGroup) findViewById(R.id.pbg);
        pbOne = (TZPhotoBoxOne) findViewById(R.id.pbOne);
        pbOne2 = (TZPhotoBoxOne) findViewById(R.id.pbOne2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pbOne.onActivityResult(this, requestCode, resultCode, data);
        pbOne2.onActivityResult(this, requestCode, resultCode, data);
        photoBoxGroup.onActivityResult(this, requestCode, resultCode, data);
    }

    public void submit(View view) {
        TZRequest tzRequest = new TZRequest("http://10.80.2.108:8098/api/Login/", "Login");
        tzRequest.addParam("code", 10000);
        tzRequest.addParam("password", "aa1122");
        new HTTPWebAPI().call(tzRequest, new WebCallBackListener() {
            @Override
            public void success(org.json.JSONObject jsonObject, TZRequest request) {
                TZToastTool.mark(jsonObject.toString());
            }

            @Override
            public void success(String response, TZRequest request) {
                TZToastTool.mark(response);
            }

            @Override
            public void err(String s, TZRequest tzRequest) {
                TZToastTool.mark(s);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        photoBoxGroup.dispatchTouchEvent(ev, false);
        pbOne.dispatchTouchEvent(ev, false);
        pbOne2.dispatchTouchEvent(ev, false);
        return super.dispatchTouchEvent(ev);
    }
}
