package com.tianzunchina.android.api.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;

import com.tianzunchina.android.api.widget.dialog.ConfirmationDialogFragment;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/12/19.
 */

public class ICCWebChromeViewClient extends WebChromeClient implements ConfirmationDialogFragment.Listener {
    public final static String CAPTURE_IMAGE = " image/jpeg";
    public final static String CAPTURE_CAMERA = " camera";
    public static final int INPUT_FILE_REQUEST_CODE = 1;
    public final static int FILECHOOSER_RESULTCODE = 2;
    public ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> mFilePathCallback;
    public String mCameraPhotoPath;
    private Activity activity;
    private PermissionRequest mPermissionRequest;

    public ICCWebChromeViewClient(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onCloseWindow(WebView window) {
        super.onCloseWindow(window);
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean dialog,
                                  boolean userGesture, Message resultMsg) {
        return super.onCreateWindow(view, dialog, userGesture, resultMsg);
    }

    /**
     * 覆盖默认的window.alert展示界面，避免title里显示为“：来自file:////”
     */
    public boolean onJsAlert(WebView view, String url, String message,
                             JsResult result) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        builder.setTitle("对话框")
                .setMessage(message)
                .setPositiveButton("确定", null);

        // 不需要绑定按键事件
        // 屏蔽keycode等于84之类的按键
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });
        // 禁止响应按back键的事件
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        result.confirm();// 因为没有绑定事件，需要强行confirm,否则页面会变黑显示不了内容。
        return true;
        // return super.onJsAlert(view, url, message, result);
    }

    public boolean onJsBeforeUnload(WebView view, String url,
                                    String message, JsResult result) {
        return super.onJsBeforeUnload(view, url, message, result);
    }

    /**
     * 覆盖默认的window.confirm展示界面，避免title里显示为“：来自file:////”
     */
    public boolean onJsConfirm(WebView view, String url, String message,
                               final JsResult result) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("对话框")
                .setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                })
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                result.cancel();
            }
        });

        // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });
        // 禁止响应按back键的事件
        // builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        return true;
        // return super.onJsConfirm(view, url, message, result);
    }

    /**
     * 覆盖默认的window.prompt展示界面，避免title里显示为“：来自file:////”
     * window.prompt('请输入您的域名地址', '618119.com');
     */
    public boolean onJsPrompt(WebView view, String url, String message,
                              String defaultValue, final JsPromptResult result) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        builder.setTitle("对话框").setMessage(message);

        final EditText et = new EditText(view.getContext());
        et.setSingleLine();
        et.setText(defaultValue);
        builder.setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm(et.getText().toString());
                    }

                })
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                });

        // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });

        // 禁止响应按back键的事件
        // builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        return true;
        // return super.onJsPrompt(view, url, message, defaultValue,
        // result);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
    }

    @Override
    public void onRequestFocus(WebView view) {
        super.onRequestFocus(view);
    }

    public void openChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType(acceptType);
        activity.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
    }

    public void openChooserByCamera(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        openChooser(uploadMsg, acceptType);
    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        mUploadMessage = uploadMsg;
        onShowFileChooser();
    }

    public boolean onShowFileChooser() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            photoFile = createImageFile();
            takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);

            // Continue only if the File was successfully created
            if (photoFile != null) {
                mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
            } else {
                takePictureIntent = null;
            }
        }

        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");

        Intent[] intentArray;
        if (takePictureIntent != null) {
            intentArray = new Intent[]{takePictureIntent};
        } else {
            intentArray = new Intent[0];
        }

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

        activity.startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);
        return true;
    }

    // For Lollipop 5.0+ Devices
    public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        if (mFilePathCallback != null) {
            mFilePathCallback.onReceiveValue(null);
        }
        mFilePathCallback = filePathCallback;
        return onShowFileChooser();
    }


    //在sdcard卡创建缩略图
    //createImageFileInSdcard
    @SuppressLint("SdCardPath")
    private File createImageFile() {
        //mCameraPhotoPath="/mnt/sdcard/tmp.png";
        File file = new File(Environment.getExternalStorageDirectory() + "/", "tmp.png");
        mCameraPhotoPath = file.getAbsolutePath();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    //当网页内容被请求允许访问某些资源，调用此方法。
    @Override
    public void onPermissionRequest(PermissionRequest request) {
        Log.i("dada==", "onPermissionRequest");
        mPermissionRequest = request;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ConfirmationDialogFragment.newInstance(request.getResources()).show(activity.getFragmentManager(), "Dialog");
        }
    }

    //这个方法在授权请求被取消时被调用
    @Override
    public void onPermissionRequestCanceled(PermissionRequest request) {
        // 驳回提示UI作为请求不再有效。
        mPermissionRequest = null;
        DialogFragment fragment = (DialogFragment) activity.getFragmentManager()
                .findFragmentByTag("Dialog");
        if (null != fragment) {
            fragment.dismiss();
        }
    }

    @Override
    public void onConfirmation(boolean allowed) {
        if (allowed) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mPermissionRequest.grant(mPermissionRequest.getResources());
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mPermissionRequest.deny();
            }
        }
        mPermissionRequest = null;
    }
}
