package com.tianzunchina.android.api.network.download;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tianzunchina.android.api.R;
import com.tianzunchina.android.api.base.TZApplication;

public class TZUpdateDialog extends DialogFragment implements View.OnClickListener {

    public static final String VERSION = "version";

    private TZDownloadFile downloadFile;
    private ProgressBar pbUpdate;
    private TextView tvUpdate, tvNegative,tvNeutral;
    private TZAppVersion version;
    private int max;
    private View view;
    // 用于判断是否将更新按钮隐藏
    private boolean isHidden = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);//设置点击屏幕Dialog不消失
    }

//    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_update, container);
        init();
        return view;
    }

    private void init() {
        max = TZAppVersion.MAX;
        //设置标题
//        getDialog().setTitle("版本更新");
//        设置无自带标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        initIntent();
        tvNeutral = (TextView) view.findViewById(R.id.tvNeutral);
        tvNegative = (TextView) view.findViewById(R.id.tvNegative);
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
            //               TODO 更新
            if (downloadFile == null) {
                downloadFile = new TZDownloadFile(TZApplication.getInstance(), version, listener);
            }
//            tvNegative.setClickable(false);

        } else if (i == R.id.tvNegative) {
            //               TODO 关闭or退出
            if (version.isImportant()) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                TZUpdateDialog.this.startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            } else {
                TZUpdateConfig config = new TZUpdateConfig();
                config.cancelUpdate(version.getVersionCode(), version.getVersionName());
                dismiss();
            }

        }
    }

    private CallBackListener listener = new CallBackListener() {
        @Override
        public void callback(int percent) {
            if (percent >= 0 && percent <= max) {
                pbUpdate.setProgress(percent);
                tvUpdate.setText("正在更新……" + percent + "%");
            } else {
                tvUpdate.setText("正在更新……");
                pbUpdate.setProgress(max);
            }
        }
    };
}
