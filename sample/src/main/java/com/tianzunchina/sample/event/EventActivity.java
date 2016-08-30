package com.tianzunchina.sample.event;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.tianzunchina.android.api.base.TZAppCompatActivity;
import com.tianzunchina.sample.R;


public class EventActivity extends TZAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
