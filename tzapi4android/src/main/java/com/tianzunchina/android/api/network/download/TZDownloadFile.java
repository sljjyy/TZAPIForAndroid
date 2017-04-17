package com.tianzunchina.android.api.network.download;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

import com.tianzunchina.android.api.R;
import com.tianzunchina.android.api.base.TZApplication;
import com.tianzunchina.android.api.log.TZToastTool;
import com.tianzunchina.android.api.network.ThreadTool;
import com.tianzunchina.android.api.util.DialogUtil;
import com.tianzunchina.android.api.util.PhoneTools;
import com.tianzunchina.android.api.widget.notification.TZNotification;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 文件下载
 * Created by zwt on 2016/12/20.
 */
public class TZDownloadFile {
    private static final int RUN = 1;
    private static final int OVER = 2;
    private static final int ERR = -1;
    private static final int DOWNLOAD_ID = 0;//通知栏标识ID

    private TZFile tzFile;
    private Context context;

    private double index;// 下载时，记录实时流量累计

    private DownloadListener onDownloadListener;// 下载时的监听

    private TZNotification tzNotification;

    public TZDownloadFile(Context context, TZFile tzFile) {
        this.context = context;
        this.tzFile = tzFile;//TODO tzFile空指针
        thread();
    }

    /**
     * 通知栏式文件下载
     *
     * @param context 上下文
     * @param tzFile
     * @param title   通知栏标题
     * @param content 内容
     * @param ticker  标记
     * @param icon    图标
     * @param intent  跳转页面
     */
    public TZDownloadFile(Context context, TZFile tzFile, String title, String content, String ticker, int icon, Intent intent) {
        this.context = context;
        this.tzFile = tzFile;//TODO tzFile使用前需要非空判断
        tzNotification = new TZNotification(context, title, content, ticker, icon, intent);
        thread();
    }

    public void init(ProgressBar pbUpdate) {
        pbUpdate.setMax(TZAppVersion.MAX);
    }

    private void thread() {
        ThreadTool.execute(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                try {
//            本地文件夹中的apk文件处理
                    File file = fileHandle();
//            去服务器获取，并处理数据
                    toWebService(file);
                    message.what = 2;//TODO 常量使用
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
        File file = new File(path, tzFile.getFileName() + tzFile.getFilenameExtension());
        if (file.exists()) {
            file.delete();
        }
        return file;
    }

    private void toWebService(File file) {
        try {
            URL Url = new URL(tzFile.getDownloadURL());
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            DialogUtil.close();
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case RUN:
                        onDownloading(msg.arg1);
                        break;
                    case OVER:
                        onSuccess((String) msg.obj);
                        break;
                    case ERR:
                        onFail();
                        break;
                    default:
                        break;
                }
            }
        }
    };

    private void onDownloading(int percent) {
        if (onDownloadListener != null) {
            onDownloadListener.onDownloading(percent);
        } else {
            showNotify(percent);
        }
    }

    private void onSuccess(String path) {
        if (onDownloadListener != null) {
            onDownloadListener.onSuccess(path);
        } else {
            cancleNotify(path);
        }
    }

    private void onFail() {
        if (onDownloadListener != null) {
            onDownloadListener.onFail();
        }
    }

    private void cancleNotify(String filePath) {
        tzNotification.cancle(DOWNLOAD_ID);
        // 安装apk
        Intent intent = PhoneTools.getInstance().getApkFileIntent(filePath,context);
        context.startActivity(intent);
    }

    private void showNotify(int percent) {
        tzNotification.showNotify(DOWNLOAD_ID, percent, TZAppVersion.MAX, String.format("已下载%d%s", percent, "%"), new TZNotification.DownLoadListener() {
            @Override
            public void success() {
            }

            @Override
            public void fail() {
            }
        });
    }

    public void setOnDownloadListener(DownloadListener onDownloadListener) {
        this.onDownloadListener = onDownloadListener;
    }

    public interface DownloadListener {
        /**
         * 下载中，用于进度条
         *
         * @param percent 进度条中进度百分比
         */
        void onDownloading(int percent);

        /**
         * 下载成功
         *
         * @param filePath 下载好的文件路径
         */
        void onSuccess(String filePath);

        /**
         * 下载失败
         */
        void onFail();
    }

}
