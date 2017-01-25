package com.tianzunchina.sample.app.download;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tianzunchina.android.api.network.download.TZUpdateDialog;
import com.tianzunchina.sample.app.download.AppVersion;

import org.json.JSONObject;

/**
 * 版本更新调用demo
 */
public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String jsonStr = "{\"versionCode\":26,\"versionName\":\"1.7.0\", \"versionURL\":\"http://218.108.93.246:9001/ZHStreet_Supervision.apk\"}";
        try {
            //TODO 调用过于复杂
            JSONObject jsonObject = new JSONObject(jsonStr);
//            此AppVersion继承了TZAppVersion
            AppVersion version = new AppVersion(jsonObject);

            //showDialog(tag)  tag = null，默认为"updateDialog"
            new TZUpdateDialog(this,version).showDialog(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
