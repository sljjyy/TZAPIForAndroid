package com.tianzunchina.android.api.network.download;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tianzunchina.android.api.R;
import com.tianzunchina.android.api.util.DialogUtil;

import java.lang.reflect.Field;

/**
 * 版本更新
 * Created by zwt on 2016/12/20.
 */
public class TZUpdateDialog {
    private ProgressBar pbUpdate;
    private TextView tvUpdate;
    private static AlertDialog ad;
    private AppVersion version;
    private int max;
    private Context context;
    // 用于判断是否将更新按钮隐藏
    private boolean isHidden = false;

    private CallBackListener listener = new CallBackListener() {
        @Override
        public void callback(int percent) {
            if (percent >= 0 && percent <= max) {
                pbUpdate.setProgress(percent);
                tvUpdate.setText("正在更新……" + percent + "%");
            } else {
                tvUpdate.setText("正在更新……");
                pbUpdate.setProgress(max);
                if (!isHidden) {
                    //将更新按钮隐藏
                    final Button positiveButton = ad
                            .getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setEnabled(false);
                    isHidden = true;
                }
            }
        }
    };

    public TZUpdateDialog(Context context, AppVersion version) {
        this.context = context;
        this.version = version;
    }

    public void init(ProgressBar pbUpdate) {
        pbUpdate.setMax(version.getVersionSize());
        max = version.getVersionSize();
    }

    public void showDialog() {
        if (ad != null && ad.isShowing()) {
            return ;
        }
        LinearLayout updateLayout = (LinearLayout) View.inflate(context, R.layout.dialog_update, null);
        pbUpdate = (ProgressBar) updateLayout.findViewById(R.id.pbUpdate);
        tvUpdate = (TextView) updateLayout.findViewById(R.id.tvUpdate);
        init(pbUpdate);
        ad = new AlertDialog.Builder(context)
                .setTitle("版本更新")
                .setView(updateLayout)
                .setNeutralButton("更新", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            tvUpdate.setText("正在更新……");
                            Field field = dialog.getClass().getSuperclass()
                                    .getDeclaredField("mShowing");
                            field.setAccessible(true);
                            field.set(dialog, false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        thread();
                        new TZDownloadFile(context,version,listener);
                    }
                })
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            Field field = dialog.getClass().getSuperclass()
                                    .getDeclaredField("mShowing");
                            field.setAccessible(true);
                            field.set(dialog, true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                }).create();
        DialogUtil.show(ad);
    }

    public void dismiss() {
        ad.dismiss();
    }

//    public void downByte(int num) {
//        if (num >= 0 && num <= max) {
//            pbUpdate.setProgress(num);
//            tvUpdate.setText("正在更新……" + num + "%");
//        } else {
//            tvUpdate.setText("正在更新……");
//            pbUpdate.setProgress(max);
//            if (!isHidden) {
//                //将更新按钮隐藏
//                final Button positiveButton = ad
//                        .getButton(AlertDialog.BUTTON_POSITIVE);
//                positiveButton.setEnabled(false);
//                isHidden = true;
//            }
//        }
//    }
//
//    private void thread() {
//        TZApplication.getInstance().execute(new Runnable() {
//            @Override
//            public void run() {
//                Message message = new Message();
//                try {
////            本地文件夹中的apk文件处理
//                    File file = fileHandle();
////            去服务器获取，并处理数据
//                    toWebService(file);
//                    message.what = 2;
//                    message.obj = file.getAbsolutePath();
//                } catch (Exception e) {
//                    message.what = -1;
//                    e.printStackTrace();
//                }
//                handler.sendMessage(message);
//            }
//        });
//    }
//
//    private File fileHandle() {
//        File path = new File(Environment.getExternalStorageDirectory(),
//                context.getPackageName());
//        path.mkdirs();
//        File file = new File(path, version.getVersionName() + ".apk");
//        if (file.exists()) {
//            file.delete();
//        }
//        return file;
//    }
//
//    private void toWebService(File file) {
//        try {
//            URL Url = new URL(version.getVersionURL());
//            URLConnection conn = Url.openConnection();
//            conn.connect();
////            流处理
//            InputStream is = conn.getInputStream();
//            int size = conn.getContentLength();
//            if (size <= 0 || is == null) {
//                throw new RuntimeException("无法获取文件");
//            }
//            FileOutputStream fos = new FileOutputStream(file);
//            byte buf[] = new byte[1024];
//            double base = size / 100;
//            while (true) {
//                int hasRead = is.read(buf);
//                if (hasRead != -1) {
////                    处理数据
//                    updateData(fos, buf, hasRead, base);
//                } else {
//                    break;
//                }
//            }
//            is.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void updateData(FileOutputStream fos, byte buf[], int hasRead, double base) {
//        try {
//            fos.write(buf, 0, hasRead);
//            index += hasRead;
//            Message message = new Message();
//            message.what = 1;
//            message.arg1 = (int) (index / base);
//            handler.sendMessage(message);
//        } catch (Exception e) {
//            Message message = new Message();
//            message.what = -1;
//            handler.sendMessage(message);
//            e.printStackTrace();
//        }
//    }
}
