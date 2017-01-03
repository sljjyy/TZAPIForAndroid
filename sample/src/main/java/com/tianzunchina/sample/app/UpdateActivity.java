package com.tianzunchina.sample.app;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tianzunchina.android.api.network.download.TZAppVersion;
import com.tianzunchina.android.api.network.download.TZUpdateDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 版本更新调用demo
 */
public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TZAppVersion version = new TZAppVersion();
//        version.setVersionCode(26);
//        version.setVersionName("1.7.0");
//        version.setVersionURL("http://218.108.93.246:9001/ZHStreet_Supervision.apk");
//        new UpdateDialog1(this,version).showDialog();

        String jsonStr = "{\"versionCode\":26,\"versionName\":\"1.7.0\", \"versionURL\":\"http://218.108.93.246:9001/ZHStreet_Supervision.apk\"}";
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
//            此AppVersion继承了TZAppVersion
            AppVersion version = new AppVersion(jsonObject);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            TZUpdateDialog updateDialogFragment = new TZUpdateDialog();
            if (null != updateDialogFragment) {
                ft.remove(updateDialogFragment);
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable(TZUpdateDialog.VERSION, version);
            updateDialogFragment.setArguments(bundle);
            updateDialogFragment.show(getFragmentManager(), "updateDialog");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
