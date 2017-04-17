package com.tianzunchina.android.api.network.download;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import com.tianzunchina.android.api.network.TZRequest;
import com.tianzunchina.android.api.network.WebAPIable;
import com.tianzunchina.android.api.network.WebCallBackListener;
import com.tianzunchina.android.api.util.PhoneTools;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 版本升级相关的管理
 * <p>
 * 可在此调用与版本升级有关的任何方法
 * Created by zwt on 2017/1/17.
 */

public class TZUpdateManager implements TZDownloadFile.DownloadListener {
    private static final String TAG = "updateDialog";
    private WebAPIable mWebAPIable;
    private TZRequest mRequest;
    private TZUpdateListener mListener;
    private Activity activity;
    boolean needNotification = false;//是否需要通知栏，默认不打开
    private String downUrl;

    public TZUpdateManager(Activity activity) {
        this.activity = activity;
    }

    public TZUpdateManager(Activity activity, boolean needNotification, WebAPIable webApi, TZRequest request, TZUpdateListener listener,String downUrl) {
        this.activity = activity;
        this.mWebAPIable = webApi;
        this.mRequest = request;
        this.mListener = listener;
        this.needNotification = needNotification;
        this.downUrl = downUrl;
    }

    /**
     * builder设置完毕后，开始进行版本更新功能的操作
     */
    public void start() {
        lastVersionComparison();
    }

    /**
     * 获取最新的版本信息与当前版本比较
     * <p>
     * 从服务器访问到最新的版本信息
     */
    public void lastVersionComparison() {

        mWebAPIable.call(mRequest, new WebCallBackListener() {
            @Override
            public void success(JSONObject jsonObject, TZRequest request) {
            }

            @Override
            public void success(Object response, TZRequest request) {
                TZAppVersion lastVersion = null;
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONObject jsonObject1 = jsonObject.getJSONObject("Body");
                    lastVersion = new TZAppVersion(jsonObject1);
                    lastVersion.setDownloadURL(downUrl + lastVersion.getVersionURL());
                } catch (JSONException e) {
                    e.printStackTrace();
                    this.err(e.getMessage(), request);
                }
                TZAppVersion oldVersion = getOldVersion();
                if (mListener.needUpdate(lastVersion, oldVersion)) {
//                     打开更新提示Dialog
                    openUpdateDialog(lastVersion);
                }
            }

            @Override
            public void err(String err, TZRequest request) {
                mListener.err(err, request);
            }
        });
    }

    private void openUpdateDialog(TZAppVersion version) {
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        TZUpdateDialogFragment updateDialogFragment = new TZUpdateDialogFragment();
        if (null != updateDialogFragment) {
            ft.remove(updateDialogFragment);
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(TZUpdateDialogFragment.VERSION, version);
        updateDialogFragment.setArguments(bundle);
        updateDialogFragment.show(activity.getFragmentManager(), TAG);
    }

    /**
     * 获取手机已装的版本
     *
     * @return
     */
    public TZAppVersion getOldVersion() {
        TZAppVersion version = new TZAppVersion();
        int versionCode = PhoneTools.getInstance().getVersionCode();
        String versionName = PhoneTools.getInstance().getVersionName();
        version.setVersionCode(versionCode);
        version.setVersionName(versionName);
        return version;
    }

    @Override
    public void onDownloading(int percent) {
        mListener.downloading(percent);
    }

    @Override
    public void onSuccess(String filePath) {
        // 下载成功时
        mListener.downloadSuccess();
    }

    @Override
    public void onFail() {
        // 下载失败时
        mListener.downloadErr();
    }

    /**
     * Builder构建
     */
    public static class Builder {
        WebAPIable webApi;
        TZRequest request;
        TZUpdateListener listener;
        Activity activity;
        boolean needNotification = false;
        String downUrl;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder setDownUrl(String downUrl) {
            this.downUrl = downUrl;
            return this;
        }

        public Builder setNeedNotification(boolean needNotification) {
            this.needNotification = needNotification;
            return this;
        }

        /**
         * 获取最新版本信息的接口访问
         *
         * @param webApi 最新版本信息的接口访问
         * @return 最新版本信息的接口访问
         */
        public Builder setWebAPI(WebAPIable webApi) {
            this.webApi = webApi;
            return this;
        }

        /**
         * 得到最新版本信息的网络请求类
         *
         * @param request 最新版本信息的网络请求类
         * @return 最新版本信息的网络请求类
         */
        public Builder setRequest(TZRequest request) {
            this.request = request;
            return this;
        }

        /**
         * 关于版本更新
         *
         * @param listener
         * @return
         */
        public Builder setListener(TZUpdateListener listener) {
            this.listener = listener;
            return this;
        }

        public TZUpdateManager build() {
            return new TZUpdateManager(activity, needNotification, webApi, request, listener,downUrl);
        }
    }

}
