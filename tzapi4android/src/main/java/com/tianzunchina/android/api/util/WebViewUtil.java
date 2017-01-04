package com.tianzunchina.android.api.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.Timer;
import java.util.TimerTask;

/**
 * webview工具类
 * Created by yqq on 2016/12/19.
 */

public class WebViewUtil extends WebViewClient {
    public Context context;
    public String loadingStr;
    private Dialog progressDialog;
    private String errorHtml = "打开错误!请检查网络!";
    private WebView webView;
    public WebViewUtil() {
    }

    public WebViewUtil(Context context, String loadingStr) {
        this.context = context;
        this.loadingStr = loadingStr;
        progressDialog = ProgressDialog.show(context, null, loadingStr);
        Timer timer = new Timer();
        timer.schedule(new TimeOutTask(), 10000);
    }

    /**
     * 加载webView
     * @param context 上下文
     * @param loadingStr 网址没加载之前的对话框文字
     * @param webView 控件
     * @param url 网络地址
     */
    public  WebViewUtil(Context context,String loadingStr,WebView webView,String url){
        this.context = context;
        this.loadingStr = loadingStr;
        this.webView = webView;
        progressDialog = ProgressDialog.show(context, null, loadingStr);
        Timer timer = new Timer();
        timer.schedule(new TimeOutTask(), 10000);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebChromeClient(new ICCWebChromeViewClient());
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.loadUrl(url);
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        progressDialog.show();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        progressDialog.cancel();
    }

    @Override
    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        view.getSettings().setDefaultTextEncodingName("UTF-8");
        //view.loadData(errorHtml,"text/html","UTF-8");
        view.loadDataWithBaseURL(null, errorHtml.toString(), "text/html", "UTF-8", null);
        progressDialog.cancel();
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        // 不要使用super，否则有些手机访问不了，因为包含了一条 handler.cancel()
        // super.onReceivedSslError(view, handler, error);

        // 接受所有网站的证书，忽略SSL错误，执行访问网页
        handler.proceed();
    }

    class TimeOutTask extends TimerTask {
        @Override
        public void run() {
            progressDialog.cancel();
        }
    }
}
