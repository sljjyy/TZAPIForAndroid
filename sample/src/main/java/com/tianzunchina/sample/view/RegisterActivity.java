package com.tianzunchina.sample.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.tianzunchina.sample.R;

/**
 * 注册
 * Created by Administrator on 2017/4/20.
 */

public class RegisterActivity extends Activity{

    TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tvTitle = (TextView) findViewById(R.id.tvTopTitle);
        tvTitle.setText("注册");
    }
}
