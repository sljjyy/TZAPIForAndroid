package com.tianzunchina.sample.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import com.tianzunchina.android.api.base.TZAppCompatActivity;
import com.tianzunchina.android.api.widget.photo.TZPhotoBoxGroup;
import com.tianzunchina.sample.R;


public class EventActivity extends TZAppCompatActivity {
    private TZPhotoBoxGroup photoBoxGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FragmentManager manager = getSupportFragmentManager();
        photoBoxGroup = (TZPhotoBoxGroup) findViewById(R.id.pbg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        photoBoxGroup.onActivityResult(this, requestCode, resultCode, data);
    }
}
