package com.tianzunchina.sample.app.download;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tianzunchina.android.api.network.download.TZDownloadFile;
import com.tianzunchina.android.api.network.download.TZFile;
import com.tianzunchina.android.api.network.download.TZUpdateDialog;
import com.tianzunchina.sample.R;

import org.json.JSONObject;

public class DownloadFileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_file);

//        String jsonStr = "{\"versionCode\":26,\"versionName\":\"1.7.0\", \"versionURL\":\"http://218.108.93.246:9001/ZHStreet_Supervision.apk\"}";
//        try {
//            //TODO 调用过于复杂
//            JSONObject jsonObject = new JSONObject(jsonStr);
////            此AppVersion继承了TZAppVersion
//            TZFile file = new TZFile(jsonObject);
//
//            new TZDownloadFile(this,file);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
