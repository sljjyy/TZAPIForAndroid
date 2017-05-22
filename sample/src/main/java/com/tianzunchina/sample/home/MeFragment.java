package com.tianzunchina.sample.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.tianzunchina.android.api.base.TZFragment;
import com.tianzunchina.android.api.log.TZToastTool;
import com.tianzunchina.android.api.network.SOAPWebAPI;
import com.tianzunchina.android.api.network.TZRequest;
import com.tianzunchina.android.api.network.WebCallBackListener;
import com.tianzunchina.android.api.network.download.TZAppVersion;
import com.tianzunchina.android.api.network.download.TZUpdateListener;
import com.tianzunchina.android.api.network.download.TZUpdateManager;
import com.tianzunchina.android.api.util.FileCache;
import com.tianzunchina.android.api.util.PhoneTools;
import com.tianzunchina.android.api.util.PhotoTools;
import com.tianzunchina.android.api.util.TimeConverter;
import com.tianzunchina.android.api.util.UnitConverter;
import com.tianzunchina.sample.MainActivity;
import com.tianzunchina.sample.R;
import com.tianzunchina.sample.login.LoginActivity;
import com.tianzunchina.sample.service.WebService;
import com.tianzunchina.sample.util.LoginUtil;
import com.tianzunchina.sample.widget.SelectPicPopupWindow;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Date;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * 个人信息
 * Created by yqq on 2017/4/7.
 */

public class MeFragment extends TZFragment  {

    private static final String VERSION_CODE = "versionCode";
    private static final String VERSION_NAME = "versionName";
    private static final String VERSION_URL = "versionURL";
    private ImageView ivUserHeader;//头像
    private FileCache mFileCache = new FileCache();
    SOAPWebAPI webAPI;
    private static final int NONE = 0;//取消
    private static final int PHOTO_CA = 1;// 拍照
    private static final int PHOTO_RESULT = 3;// 结果
    private static final int PHOTO = 4;// 相册
    public static final String IMAGE_UNSPECIFIED = "image/*";
    private File priFile;
    private AlertDialog alertDialog;
    TextView tvName, tvAppVsersion;
    private static final String FilePath = "clipping_picture.jpg";
    Button btnLogout;
    private SelectPicPopupWindow menuWindow;
    com.tianzunchina.sample.model.User user;
    LinearLayout llVersionUpdate;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    EditText etvVersionCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText("个人信息");
        ivUserHeader = (ImageView) view.findViewById(R.id.ivHeader);
        ivUserHeader.setOnClickListener(v -> {
            if (checkLogin()) {
                menuWindow = new SelectPicPopupWindow(
                        MeFragment.this.getActivity(), itemsOnClick);
                menuWindow.showAtLocation(
                        MeFragment.this.getActivity().findViewById(R.id.main),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
            }
        });
        btnLogout = (Button) view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {

            if (LoginUtil.getCacheUser(MeFragment.this.getActivity()) != null) {
                if (LoginUtil.getCacheUser(MeFragment.this.getActivity()).isRemember() == false) {
                    LoginUtil.clearCacheUserPassWord(MeFragment.this.getActivity());
                }
            }
            Intent intent = new Intent(MeFragment.this.getActivity(), LoginActivity.class);
            startActivity(intent);
            broadcast();
            getActivity().finish();
        });
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvAppVsersion = (TextView) view.findViewById(R.id.tvAppVsersion);

        llVersionUpdate = (LinearLayout) view.findViewById(R.id.llVersionUpdate);
        llVersionUpdate.setOnClickListener(v -> showUpdateDialog());
        user = LoginUtil.getNowUser();
        tvName.setText(user.getName());

        EasyImage.configuration(this.getActivity())
                .setImagesFolderName("EasyImage sample")
                .setCopyExistingPicturesToPublicLocation(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    /*
   初始化数据
    */
    private void initData() {

        if (LoginUtil.isGuest()) {
            btnLogout.setText("登陆/注册");
        } else {
            btnLogout.setText("退出账户");
        }
        //设置圆形头像
        Glide.with(this.getActivity()).load(WebService.getPicUrl() + LoginUtil.getNowUser().getPicPath())
                .error(R.drawable.ico_rank_picture).into(ivUserHeader);

    }

    //检查是否登录
    public boolean checkLogin() {
        if (LoginUtil.isGuest()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
            builder.setTitle("提示");
            builder.setMessage("请登录后操作");
            builder.setPositiveButton("确定",
                    (dialog1, which) -> {
                        return;
                    });
            builder.create().show();
            return false;
        }
        return true;
    }

    /**
     * 为弹出窗口实现监听类
     */
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            menuWindow = null;
            switch (v.getId()) {
                case R.id.btn_take_photo:
                    EasyImage.openCamera(MeFragment.this.getActivity(), PHOTO_CA);
                    break;
                case R.id.btn_pick_photo:
                    EasyImage.openGallery(MeFragment.this.getActivity(), PHOTO);
                    break;
                default:
                    break;

            }
        }
    };

    /**
     * 发送广播
     */
    private void broadcast() {
        Intent i = new Intent();
        i.setAction(MainActivity.FINISH);
        getActivity().sendBroadcast(i);
    }

    /**
     * 回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode != PHOTO_RESULT) {
            EasyImage.handleActivityResult(requestCode, resultCode, data, this.getActivity(), new DefaultCallback() {
                @Override
                public void onImagePicked(File file, EasyImage.ImageSource imageSource, int i) {
                    startPhotoZoom(file);
                }
            });
        }

        if (requestCode == NONE)
            return;

        if (data == null)
            return;
        // 处理结果，保存裁剪过的图片到本地。
        if (requestCode == PHOTO_RESULT) {
            File path = new File(mFileCache.getThumbDir().getPath(), FilePath);
            Uri imageUri = Uri.fromFile(path);
            Bitmap photo = PhotoTools.decodeUriAsBitmap(imageUri, getActivity());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);

            String clippingPath = mFileCache.getThumbDir().getPath() + "/clipping_picture.jpg";
            File file = PhotoTools.saveBitmap(clippingPath, photo, 75); // 保存到本地
            priFile = file;

            AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
            builder.setTitle("上传头像");
            builder.setMessage("是否上传头像?");
            builder.setPositiveButton("上传",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 上传头像
                            uploadingImages(priFile);
                        }
                    });
            builder.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

            builder.create().show();
        }

    }

    /**
     * 设置照片尺寸 裁剪头像
     *
     * @param file 图片地址
     */
    public void startPhotoZoom(File file) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(PhotoTools.getImageContentUri(MeFragment.this.getActivity(), file), IMAGE_UNSPECIFIED);
        // intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        float xy = 60 * getResources().getDisplayMetrics().density + 0.5f;
        intent.putExtra("outputX", xy);
        intent.putExtra("outputY", xy);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mFileCache.getThumbDir().getPath(), FilePath)));
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, PHOTO_RESULT);
    }

    /**
     * 上传头像
     *
     * @param file 文件
     */
    private void uploadingImages(File file) {
        if (null == file) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
            builder.setTitle("提示");
            builder.setMessage("请选择您要上传的头像");
            builder.setPositiveButton("确定", null);
            builder.show();
        } else {
            showDialog();
            updatePhoto(file);
        }
    }

    //修改头像，向服务器发送
    private void updatePhoto(File file) {
        TZRequest tzRequest = new TZRequest(WebService.getWebServiceUrl() + WebService.SERVICE_USER_HEAD, WebService.MODIFY_HEAD);
        String pic = null;
        if (file != null)
            pic = UnitConverter.byte2base64(UnitConverter.file2byte(file));
        tzRequest.addParam("UserID", LoginUtil.getNowUser().getUserID());
        tzRequest.addParam("photo", pic);
        webAPI.call(tzRequest, new WebCallBackListener() {
            @Override
            public void success(JSONObject jsonObject, TZRequest tzRequest) {
                try {
                    JSONObject body = jsonObject.getJSONObject("Body");
                    int errorCount = body.getInt("ErrorCount");
                    String picName = body.getString("Path");

                    if (0 == errorCount) {
                        if (!TextUtils.isEmpty(picName)) {
                            closeDialog();
                            TZToastTool.essential("恭喜您，头像修改成功！");
                            user.setPicPath(picName);
                        } else {
                            closeDialog();
                            TZToastTool.essential("抱歉，头像修改失败！");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    closeDialog();
                    TZToastTool.essential("出现异常，请稍后重新尝试");
                    return;
                }

            }

            @Override
            public void success(Object o, TZRequest tzRequest) {

            }

            @Override
            public void err(String s, TZRequest tzRequest) {
                closeDialog();
                Toast.makeText(MeFragment.this.getActivity(), s,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    // 弹出提示信息
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setMessage("上传中，请稍后...");
        alertDialog = builder.create();
        alertDialog.show();
    }

    // 关闭提示信息
    public void closeDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    private void updateVersion(int versionCode) {
        try {
            // 以中和为例
            // 接口参数设置
            TZRequest tzRequest = new TZRequest("http://218.108.93.154:8090/UpgradeVersionService.asmx", "GetNewVersion");
            tzRequest.addParam("imeiCode", PhoneTools.getInstance().getDeviceId());
            tzRequest.addParam("phoneTime", TimeConverter.date2Str(new Date(), TimeConverter.DEF_DATE_FORMAT));
            tzRequest.addParam("versionCode", versionCode);
            // 接口
            SOAPWebAPI api = new SOAPWebAPI();

            // 监听设置
            TZUpdateListener listener = getListener();

            // 版本更新设置参数
            TZUpdateManager manager = new TZUpdateManager.Builder(this.getActivity())
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

    /**
     * 弹出修改密码对话框
     */
    private void showUpdateDialog() {
        builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("版本更新");
        View myLayout = View.inflate(getActivity(), R.layout.dlg_change_password, null);
        etvVersionCode = (EditText) myLayout.findViewById(R.id.etvVersionCode);
        builder.setView(myLayout);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                try {
                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                    field.setAccessible(true);
                    field.set(dialog, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (etvVersionCode.getText().toString().equals("")) {
                    showToast(getActivity(), "请输入版本号");
                    return;
                }
                updateVersion(Integer.valueOf(etvVersionCode.getText().toString()));
            }
        });
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 提示框
     *
     * @param activity 当前页面
     * @param text     弹出的信息提示
     */
    public void showToast(Activity activity, String text) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 提示框
     *
     * @param activity 当前页面
     * @param resID    资源ID
     */
    public void showToast(Activity activity, int resID) {
        showToast(activity, activity.getString(resID));
    }


}
