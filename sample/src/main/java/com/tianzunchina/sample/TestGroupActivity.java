package com.tianzunchina.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tianzunchina.android.api.widget.photo.TZPhotoBoxGroup;

/**
 * 区分tzPhotoGroup
 * Created by Administrator on 2017/10/16.
 */

public class TestGroupActivity extends Activity {

    TZPhotoBoxGroup pb1,pb2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_group);
        pb1 = (TZPhotoBoxGroup) findViewById(R.id.pbg1);
        pb2 = (TZPhotoBoxGroup) findViewById(R.id.pbg2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pb1.onActivityResult(this,requestCode, resultCode, data);
        pb2.onActivityResult(this,requestCode, resultCode, data);

    }
}
