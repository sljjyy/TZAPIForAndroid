package com.tianzunchina.sample.view

import android.app.Activity
import android.os.Bundle
import android.webkit.WebView
import android.widget.TextView
import com.tianzunchina.android.api.network.browser.WebViewUtil
import com.tianzunchina.sample.R

/**
 * Created by Administrator on 2017/5/31.
 */
class WebViewActivity1 : Activity() {

    internal var webViewUtil: WebViewUtil = null!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val tvTitle = findViewById(R.id.tvTopTitle) as TextView
        tvTitle.text = "网页"
        val webView = findViewById(R.id.webView1) as WebView
        webViewUtil = WebViewUtil(this, "数据加载中...", webView, "https://www.baidu.com/")
    }
}