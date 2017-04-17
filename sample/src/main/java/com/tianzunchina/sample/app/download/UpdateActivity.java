package com.tianzunchina.sample.app.download;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tianzunchina.android.api.log.TZToastTool;
import com.tianzunchina.android.api.network.HTTPWebAPI;
import com.tianzunchina.android.api.network.SOAPWebAPI;
import com.tianzunchina.android.api.network.TZRequest;
import com.tianzunchina.android.api.network.WebAPIable;
import com.tianzunchina.android.api.network.WebCallBackListener;
import com.tianzunchina.android.api.network.download.TZAppVersion;
import com.tianzunchina.android.api.network.download.TZUpdateListener;
import com.tianzunchina.android.api.network.download.TZUpdateManager;
import com.tianzunchina.android.api.util.PhoneTools;
import com.tianzunchina.android.api.util.TimeConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * 版本更新调用demo
 */
public class UpdateActivity extends AppCompatActivity {
    private static final String VERSION_CODE = "versionCode";
    private static final String VERSION_NAME = "versionName";
    private static final String VERSION_URL = "versionURL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        String jsonStr = "{\"versionCode\":26,\"versionName\":\"1.7.0\", \"versionURL\":\"http://218.108.93.246:9001/ZHStreet_Supervision.apk\"}";
        try {
            // 以中和为例
            // 接口参数设置
            TZRequest tzRequest = new TZRequest("http://218.108.93.154:8090/UpgradeVersionService.asmx", "GetNewVersion");
            tzRequest.addParam("imeiCode", PhoneTools.getInstance().getDeviceId());
            tzRequest.addParam("phoneTime", TimeConverter.date2Str(new Date(),TimeConverter.DEF_DATE_FORMAT));
            tzRequest.addParam("versionCode", 1);

            // 接口
            SOAPWebAPI api = new SOAPWebAPI();

            // 监听设置
            TZUpdateListener listener = getListener();

            // 版本更新设置参数
            TZUpdateManager manager = new TZUpdateManager.Builder(this)
                    .setDownUrl("http://218.108.93.154:8090/")
                    .setRequest(tzRequest)
                    .setWebAPI(api)
                    .setListener(listener).build();

            // 已设置好的版本更新manager开始工作
            manager.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 版本更新调用时需要用到的监听设置
     *
     * @return
     */
    private TZUpdateListener getListener() {
        return new TZUpdateListener() {
            @Override
            public void err(String err, TZRequest request) {
                //网络错误时
                TZToastTool.essential("网络异常！\\n上报事件提交失败\\n请稍后重新尝试");
            }

            @Override
            public void success(String str) {
                // 成功
            }

            @Override
            public void downloading(int percent) {
                // 下载中
            }

            @Override
            public void downloadSuccess() {
                // 下载成功
            }

            @Override
            public void downloadErr() {
                // 下载失败
            }

            @Override
            public TZAppVersion json2obj(JSONObject object) {
                TZAppVersion version = new TZAppVersion();

                try {
                    version.setVersionCode(object.getInt(VERSION_CODE));
                    version.setVersionName(object.getString(VERSION_NAME));
                    version.setVersionURL(object.getString(VERSION_URL));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return version;
            }

            @Override
            public boolean needUpdate(TZAppVersion newVersion, TZAppVersion oldVersion) {
                if (newVersion.getVersionCode() > oldVersion.getVersionCode()) {
                    return true;
                } else {
                    return false;
                }
            }
        };
    }

}
