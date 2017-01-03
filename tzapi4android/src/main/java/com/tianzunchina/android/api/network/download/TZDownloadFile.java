package com.tianzunchina.android.api.network.download;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tianzunchina.android.api.base.TZApplication;
import com.tianzunchina.android.api.log.TZToastTool;
import com.tianzunchina.android.api.network.HTTPWebAPI;
import com.tianzunchina.android.api.network.TZRequest;
import com.tianzunchina.android.api.network.WebCallBackListener;
import com.tianzunchina.android.api.util.DialogUtil;
import com.tianzunchina.android.api.util.PhoneTools;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import static com.tianzunchina.android.api.R.style.dialog;

/**
 * Created by zwt on 2016/12/20.
 */
public class TZDownloadFile{
    private TZAppVersion version;
    private int max;
    private Context context;
    // 用于判断是否将更新按钮隐藏
    private boolean isHidden = false;

    // 下载时，记录实时流量累计
    private double index;

    private CallBackListener listener;
    private HTTPWebAPI http;

    private static final int RUN = 1;
    private static final int OVER = 2;
    private static final int ERR = -1;

    public TZDownloadFile(Context context, TZAppVersion version) {
        this.context = context;
        this.version = version;
        http = new HTTPWebAPI();
        thread();
    }

    public TZDownloadFile(Context context, TZAppVersion version,CallBackListener listener) {
        this.context = context;
        this.version = version;
        http = new HTTPWebAPI();
        this.listener = listener;
        thread();
    }

    public void init(ProgressBar pbUpdate) {
        pbUpdate.setMax(TZAppVersion.MAX);
        max = TZAppVersion.MAX;
    }

    private void thread() {
        TZApplication.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                try {
//            本地文件夹中的apk文件处理
                    File file = fileHandle();
//            去服务器获取，并处理数据
                    toWebService(file);
                    message.what = 2;
                    message.obj = file.getAbsolutePath();
                } catch (Exception e) {
                    message.what = -1;
                    e.printStackTrace();
                }
                handler.sendMessage(message);
            }
        });
    }

    private File fileHandle() {
        File path = new File(Environment.getExternalStorageDirectory(),
                context.getPackageName());
        path.mkdirs();
        File file = new File(path, version.getVersionName() + ".apk");
        if (file.exists()) {
            file.delete();
        }
        Log.e("11",version.getVersionName()+".apk 是否存在？--》 "+file.exists());
        return file;
    }

    private void toWebService(File file) {
        try {
            URL Url = new URL(version.getVersionURL());
            URLConnection conn = Url.openConnection();
            conn.connect();
//            流处理
            InputStream is = conn.getInputStream();
            int size = conn.getContentLength();
            if (size <= 0 || is == null) {
                throw new RuntimeException("无法获取文件");
            }
            FileOutputStream fos = new FileOutputStream(file);
            byte buf[] = new byte[1024];
            double base = size / 100;
            while (true) {
                int hasRead = is.read(buf);
                if (hasRead != -1) {
//                    处理数据
                    updateData(fos, buf, hasRead, base);
                } else {
                    break;
                }
            }
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateData(FileOutputStream fos, byte buf[], int hasRead, double base) {
        try {
            fos.write(buf, 0, hasRead);
            index += hasRead;
            Message message = new Message();
            message.what = 1;
            message.arg1 = (int) (index / base);
            handler.sendMessage(message);
        } catch (Exception e) {
            Message message = new Message();
            message.what = -1;
            handler.sendMessage(message);
            e.printStackTrace();
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case RUN:
                        listener.callback(msg.arg1);
                        break;
                    case OVER:
                        TZToastTool.essential("文件下载完成");
                        DialogUtil.close();
                        Intent intent = PhoneTools.getInstance().getApkFileIntent((String) msg.obj);
                        context.startActivity(intent);
                        break;
                    case ERR:
                        TZToastTool.essential("抱歉更新出错！请稍后重试！");
                        DialogUtil.close();
                        break;
                    default:
                        break;
                }
            }
        }
    };

    public void setCallBackListener(CallBackListener listener) {
        this.listener = listener;
    }

}
