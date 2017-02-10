package com.tianzunchina.android.api.widget.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 通知栏工具类
 * Created by HL on 2016/12/21.
 */

public class TZNotification {

    private Context context;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private long currentTimeMillis = System.currentTimeMillis();

    public TZNotification(Context context) {
        this.context = context;
        initManager();
    }

    public TZNotification(Context context, String title, String content, String ticker, int icon, Intent intent) {
        this.context = context;
        initManager();
        initNotify(title, content, ticker, icon, intent);
    }

    private void initManager() {
        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
    }

    //初始化通知栏
    private void initNotify(String title, String content, String ticker, int icon, Intent intent) {
        mBuilder = new NotificationCompat.Builder(context);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        mBuilder.setContentTitle(title)
                .setContentText(content)
                .setWhen(currentTimeMillis)
                .setSmallIcon(icon)
                .setTicker(ticker)//通知首次出现在通知栏，带上升动画效果的
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);//点击此通知后自动清除此通知
    }

    /**
     * 显示普通通知栏
     *
     * @param id 通知栏标识ID（注:确保唯一性）
     */
    public void showNotify(int id) {
        mNotificationManager.notify(id, mBuilder.build());
    }

    /**
     * 显示通知栏
     *
     * @param id    通知栏标识ID（注:确保唯一性）
     * @param flags 设置flag位
     *              （FLAG_AUTO_CANCEL      该通知能被状态栏的清除按钮给清除掉
     *              FLAG_NO_CLEAR           该通知能被状态栏的清除按钮不给清除掉
     *              FLAG_ONGOING_EVENT      通知放置在正在运行
     *              FLAG_INSISTENT          通知的音乐效果一直播放）
     */
    public void showNotify(int id, int flags) {
        Notification notification = mBuilder.build();
        notification.flags = flags;
        mNotificationManager.notify(id, notification);
    }

    /**
     * 显示进度条通知栏
     *
     * @param id       通知栏标识ID（注:确保唯一性）
     * @param progress 进度
     * @param total    总进度
     * @param content  显示内容
     */
    public void showNotify(int id, int progress, int total, String content, DownLoadListener listener) {
        if (progress == total) {
            mBuilder.setContentTitle("下载完成");
            listener.success();
            content = "";
        }
        mBuilder.setContentText(content)
                .setProgress(total, progress, false);
        mNotificationManager.notify(id, mBuilder.build());
    }

    //消除对应ID的通知栏
    public void cancle(int notificationID) {
        mNotificationManager.cancel(notificationID);
    }

    //消除创建的所有通知栏
    public void cancleAll() {
        mNotificationManager.cancelAll();
    }

    public interface DownLoadListener {
        void success();
        void fail();
    }
}
