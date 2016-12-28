package com.tianzunchina.sample.notify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tianzunchina.android.api.base.TZAppCompatActivity;
import com.tianzunchina.android.api.widget.notification.TZNotification;
import com.tianzunchina.sample.MainActivity;
import com.tianzunchina.sample.R;

/**
 * 通知栏
 * Created by HL on 2016/12/26.
 */

public class NotifyActivity extends TZAppCompatActivity {

    private TZNotification tzNotification;
    private Intent resultIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        resultIntent = new Intent(this, MainActivity.class);
    }

    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btnNormalNotify:
                tzNotification = new TZNotification(this, "普通标题", "这是一条普通通知栏", "您有一条新消息！", R.mipmap.ic_launcher, resultIntent);
                tzNotification.showNotify();
                break;
            case R.id.btnResidentNotify:
                tzNotification = new TZNotification(this, "常驻标题", "这是一条常驻通知栏", "您有一条新消息！", R.mipmap.ic_launcher, resultIntent);
                tzNotification.showCzNotify();
                break;
            case R.id.btnProgressNotify:
                tzNotification = new TZNotification(this, "正在下载中...", "", "开始下载", R.mipmap.ic_launcher, resultIntent);
                for (int i = 0; i <= 100; i++) {
                    tzNotification.showProgressNotify(i);
                }
                break;
            case R.id.btnClearAllNotify:
                tzNotification = new TZNotification(this);
                tzNotification.cancleAll();
                break;
        }
    }
}
