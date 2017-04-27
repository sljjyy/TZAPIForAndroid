package com.tianzunchina.sample.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.tianzunchina.android.api.base.TZAppCompatActivity;
import com.tianzunchina.sample.R;

/**
 * 待办事件
 * Created by Administrator on 2017/4/20.
 */

public class EventPendingListActivity extends TZAppCompatActivity {

    TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_list);
        tvTitle = (TextView) findViewById(R.id.tvTopTitle);
        tvTitle.setText("待办事件列表");
    }
}
