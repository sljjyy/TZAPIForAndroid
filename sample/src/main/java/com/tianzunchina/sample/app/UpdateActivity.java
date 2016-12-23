package com.tianzunchina.sample.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tianzunchina.android.api.network.download.AppVersion;
import com.tianzunchina.android.api.network.download.TZUpdateDialog;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppVersion version = new AppVersion();
        version.setVersionCode(26);
        version.setVersionName("1.7.0");
        version.setVersionURL("http://218.108.93.246:9001/ZHStreet_Supervision.apk");
        new TZUpdateDialog(this,version).showDialog();
    }
}
