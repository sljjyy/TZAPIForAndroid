package com.tianzunchina.android.api.widget.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 通知栏
 * Created by HL on 2016/12/21.
 */

public class TZNotification {

    private Context context;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private long currentTimeMillis = System.currentTimeMillis();
    public static final int NOTIFYID = 100;//普通通知栏
    public static final int CZNOTIFYID = 200;//常驻通知栏
    public static final int JDNOTIFYID = 300;//进度条通知栏

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

    //显示通知栏
    public void showNotify() {
        mNotificationManager.notify(NOTIFYID, mBuilder.build());
    }

    //显示常驻通知栏
    public void showCzNotify() {
        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;//不能滑动删除
        mNotificationManager.notify(CZNOTIFYID, notification);
    }

    //显示进度条通知栏
    public void showProgressNotify(int progress) {
        if (progress == 100) {
            mBuilder.setContentTitle("下载完成");
        }
        mBuilder.setContentText(String.format("已下载%d%s", progress, "%"))
                .setProgress(100, progress, false);
        mNotificationManager.notify(JDNOTIFYID, mBuilder.build());
    }

    //消除对应ID的通知
    public void cancle(int notificationID) {
        mNotificationManager.cancel(notificationID);
    }

    //消除创建的所有通知
    public void cancleAll() {
        mNotificationManager.cancelAll();
    }
}
