package com.tianzunchina.sample.view;

import android.os.Bundle;
import android.widget.TextView;

import com.tianzunchina.android.api.base.TZAppCompatActivity;
import com.tianzunchina.sample.R;

/**
 * 事件清单
 * Created by Administrator on 2017/4/20.
 */

public class CaseListActivity extends TZAppCompatActivity{

    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_list);
        tvTitle = (TextView) findViewById(R.id.tvTopTitle);
        tvTitle.setText("事件清单");
    }
}
