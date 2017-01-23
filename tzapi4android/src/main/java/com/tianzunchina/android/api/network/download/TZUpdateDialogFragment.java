package com.tianzunchina.android.api.network.download;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tianzunchina.android.api.R;
import com.tianzunchina.android.api.base.TZApplication;
import com.tianzunchina.android.api.log.TZToastTool;
import com.tianzunchina.android.api.util.PhoneTools;

/**
 * 版本升级Dialog
 *
 * @author ZhuWenting 201612
 */
public class TZUpdateDialogFragment extends DialogFragment implements View.OnClickListener, TZDownloadFile.DownloadListener {

    public static final String VERSION = "version";
    private TZDownloadFile downloadFile;
    private ProgressBar pbUpdate;
    private TextView tvUpdate;
    private TZAppVersion version;
    private int max;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置点击屏幕Dialog不消失
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_update, container);
        init();
        return view;
    }

    private void init() {
        max = TZAppVersion.MAX;
//        设置无自带标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        initIntent();
        TextView tvNeutral = (TextView) view.findViewById(R.id.tvNeutral);
        TextView tvNegative = (TextView) view.findViewById(R.id.tvNegative);
        TextView tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        View svDescription = view.findViewById(R.id.svDescription);
        pbUpdate = (ProgressBar) view.findViewById(R.id.pbUpdate);
        tvUpdate = (TextView) view.findViewById(R.id.tvUpdate);
        initPB(pbUpdate);
        if (version.getDescribe() == null) {
            svDescription.setVisibility(View.GONE);
            tvDescription.setVisibility(View.GONE);
        }
        tvDescription.setText(version.getDescribe());
        if (version.isImportant()) {
            tvNegative.setText("退出");
        } else {
            tvNegative.setText("关闭");
        }
        tvNeutral.setOnClickListener(this);
        tvNegative.setOnClickListener(this);
    }

    private void initIntent() {
        version = (TZAppVersion) getArguments().getSerializable(VERSION);
        version.setFilenameExtension(".apk");
    }

    public void initPB(ProgressBar pbUpdate) {
        pbUpdate.setMax(TZAppVersion.MAX);
        if (version.isImportant()) {
            tvUpdate.setText("此次为重要更新, 更新后方可正常登陆\n请您更新民情e点通客户端！");
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tvNeutral) {
            //  更新
            if (downloadFile == null) {
//                downloadFile = new TZDownloadFile(TZApplication.getInstance(), version, listener);
                downloadFile = new TZDownloadFile(TZApplication.getInstance(),version);
                downloadFile.setOnDownloadListener(this);
            }
//            tvNegative.setClickable(false);

        } else if (i == R.id.tvNegative) {
            //  关闭or退出
            if (version.isImportant()) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                TZUpdateDialogFragment.this.startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            } else {
                TZUpdateConfig config = new TZUpdateConfig();
                config.cancelUpdate(version.getVersionCode(), version.getVersionName());
                dismiss();
            }

        }
    }

    @Override
    public void onDownloading(int percent) {
        if (percent >= 0 && percent <= max) {
            pbUpdate.setProgress(percent);
            tvUpdate.setText("正在更新……" + percent + "%");
        } else {
            tvUpdate.setText("正在更新……");
            pbUpdate.setProgress(max);
        }
    }

    @Override
    public void onSuccess(String filePath) {
        TZToastTool.essential("文件下载完成");
        // 安装apk
        Intent intent = PhoneTools.getInstance().getApkFileIntent(filePath);
        startActivity(intent);
    }

    @Override
    public void onFail() {
        // 提交失败后给予提示
        TZToastTool.essential("抱歉更新出错！请稍后重试！");
    }
}
