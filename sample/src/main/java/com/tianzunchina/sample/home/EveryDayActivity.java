package com.tianzunchina.sample.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import com.tianzunchina.android.api.util.WebViewUtil;
import com.tianzunchina.sample.R;

/**
 * Created by Administrator on 2016/12/19.
 */

public class EveryDayActivity extends Activity{
    private WebView webView;
    WebViewUtil webViewUtil = new WebViewUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_everyday);
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        webView = (WebView) findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewUtil(this, "数据加载中...",webView,"http://115.238.60.49:82//News/News_wap/59?userID=2"));

    }


    @Override
    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack(); // goBack()表示返回WebView的上一页面
            } else {
                this.finish();
            }
            return true;
        }
        return false;
    }
}
