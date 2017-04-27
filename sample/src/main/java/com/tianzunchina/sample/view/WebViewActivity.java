package com.tianzunchina.sample.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.widget.TextView;

import com.tianzunchina.android.api.network.browser.WebViewUtil;
import com.tianzunchina.sample.R;

/**
 * Created by yqq on 2017/4/25.
 */

public class WebViewActivity extends Activity{

    WebViewUtil webViewUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        TextView tvTitle = (TextView) findViewById(R.id.tvTopTitle);
        tvTitle.setText("网页");
        WebView webView = (WebView) findViewById(R.id.webView1);
        webViewUtil = new WebViewUtil(this,"数据加载中...",webView,"https://www.baidu.com/");
    }
}
