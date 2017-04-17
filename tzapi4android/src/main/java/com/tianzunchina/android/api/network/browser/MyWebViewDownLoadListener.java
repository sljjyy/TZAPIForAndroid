package com.tianzunchina.android.api.network.browser;

import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;
import com.tianzunchina.android.api.base.TZApplication;

/**
 * Created by Administrator on 2017/3/21.
 */

public class MyWebViewDownLoadListener implements DownloadListener {
    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                long contentLength) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        TZApplication.getInstance().getApplicationContext().startActivity(intent);
    }
}
