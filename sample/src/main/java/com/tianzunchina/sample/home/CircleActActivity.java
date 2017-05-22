package com.tianzunchina.sample.home;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.tianzunchina.android.api.base.TZAppCompatActivity;
import com.tianzunchina.android.api.log.TZToastTool;
import com.tianzunchina.android.api.network.SOAPWebAPI;
import com.tianzunchina.android.api.network.TZRequest;
import com.tianzunchina.android.api.network.WebCallBackListener;
import com.tianzunchina.android.api.util.PhotoTools;
import com.tianzunchina.android.api.util.UnitConverter;
import com.tianzunchina.sample.R;
import com.tianzunchina.sample.model.CircleAct;
import com.tianzunchina.sample.widget.SamplePagerAdapter;
import org.json.JSONObject;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的活动，所有活动，活动详情页面
 */
@SuppressWarnings("deprecation")
public class CircleActActivity extends TZAppCompatActivity  {

    private Handler mOpenKeyboardHandler;
    // 检测时间间隔，单位：毫秒
    private static final int detchTime = 5, CIRCLE_IMAGE = 1, CIRCLE_ACT_PHOTO = 2,
            CIRCLE_ACT_SIGN = 1, CIRCLE_ACT_COMM = 2, CIRCLE_ACT_EVAL = 3;
    private int marginDefault = UnitConverter.dip2px(10),
            circleActPhotoLength = UnitConverter.dip2px(65);
    private boolean isComment;
    private int userID, mCircleActID;
    private String mCircleActName, commentContent;
    private CircleAct mCircleAct;
    private PhotoTools pt;
    private android.support.v7.app.AlertDialog alertDialog;
    private Context mContext;
    private LinearLayout.LayoutParams photoLayoutParams, imageLayoutDefaultParams,
            imageLayoutParams;
    private TextView
            mCircleTitleTextView, mCircleCreateTimeTextView,
            mCircleActTitleTextView, mCircleActContentTextView;
    private ImageView mCircleImage;
    private LinearLayout mCircleActPhotoLayout, mCircleActSign, mCircleActComment,
            mCircleActEvaluate, mCircleActAlbum;
    private PopupWindow mPopupWindow;
    private EditText commentContentText;

    SOAPWebAPI webAPI = new SOAPWebAPI();
    ViewPager vpCircle;
    TextView tvTitle;
    private String tabTitles[] = new String[]{"参与","评论","打分"};
    List<Fragment> listFragment;
    private SamplePagerAdapter fAdapter;//定义适配器
    CircleActParticipateFrg circleActParticipateFrg = new CircleActParticipateFrg();
    CircleActCommentFrg circleActCommentFrg = new CircleActCommentFrg();
    CircleActEvalActListFrg circleActEvalActListFrg = new CircleActEvalActListFrg();
    TabLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_circle);
        init();
    }

    private void init() {
        initData();
        initLayoutParams();
        initView();
        initPopuptWindow();
        if (isComment) {
            // 开始检测循环检测activity是否初始化完毕
            openPopupWindow(new Handler(), detchTime);
        }
        initSetView();
    }

    private void initSetView() {

        tvTitle.setText(mCircleActName);
        setCircleImageView();
        setCircleActView();
        getCircleActStatistics();

        mCircleActSign.setOnClickListener(v -> {

        });
        mCircleActComment.setOnClickListener(v -> {

        });
        mCircleActEvaluate.setOnClickListener(v -> {

        });
        mCircleActAlbum.setOnClickListener(v -> {

        });
    }

    private void setCircleActView() {
        mCircleTitleTextView.setText(mCircleAct.getCircleName());
        mCircleCreateTimeTextView.setText(mCircleAct.getcreateTimeTimeStr());

        String[] pathArray = mCircleAct.getPathArray();
        int pathArrayLength = pathArray.length;
        if (pathArrayLength > 0) {
            mCircleActPhotoLayout.setLayoutParams(photoLayoutParams);
            for (int i = 0; i < pathArrayLength; i++) {
                ImageView childImageView = new ImageView(mContext);
                childImageView.setScaleType(ScaleType.FIT_XY);
                if (i == 0) {
                    mCircleActPhotoLayout.addView(childImageView, i, imageLayoutDefaultParams);
                } else {
                    mCircleActPhotoLayout.addView(childImageView, i, imageLayoutParams);
                }

                String imgePath = pathArray[i].replace("\\", "/");
                File file = pt.getCache(imgePath);
                if (file != null && file.exists()) {
                    childImageView.setImageBitmap(pt.getBitmapFromPath(file
                            .getAbsolutePath()));
                } else {
                    new SetImageViewThread(CIRCLE_ACT_PHOTO, imgePath, i)
                            .start();
                }

            }
        }

        mCircleActTitleTextView.setText(mCircleAct.getTitle());
        if (mCircleAct.getContent() != null && mCircleAct.getContent().length() > 500) {
            mCircleActContentTextView.setHeight(400);
//            mCircleActContentTextView.setVerticalScrollBarEnabled(false);
            mCircleActContentTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
        }
        mCircleActContentTextView.setText(mCircleAct.getContent());
    }

    private void setCircleImageView() {
        String cirlceImgePath = mCircleAct.getCircleSmallPath();
        if (!"null".equals(cirlceImgePath)) {
            cirlceImgePath = cirlceImgePath.replace("\\", "/");
            File file = pt.getCache(cirlceImgePath);
            if (file != null && file.exists()) {
                mCircleImage.setImageBitmap(pt.getBitmapFromPath(file.getAbsolutePath()));
            } else {
                new SetImageViewThread(CIRCLE_IMAGE, cirlceImgePath, 0).start();
            }
        }
    }

    @SuppressLint("InflateParams")
    private void initView() {
        mContext = this;
        tvTitle = (TextView) findViewById(R.id.tvTopTitle);
        //初始化viewpager和tabLayout
        tableLayout = (TabLayout) findViewById(R.id.tabCircle);
        vpCircle = (ViewPager) findViewById(R.id.vpCircle);
        //将各个fragment装进列表中
        listFragment = new ArrayList<>();
        listFragment.add(circleActParticipateFrg);
        listFragment.add(circleActCommentFrg);
        listFragment.add(circleActEvalActListFrg);

        //为TabLayout添加tab名称
        for (int i = 0; i < tableLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tableLayout.getTabAt(i);

            if (tab != null) {
                tab.setCustomView(fAdapter.getTabView(i));
            }
        }
        fAdapter = new SamplePagerAdapter(this, this.getSupportFragmentManager(), listFragment,tabTitles);
        //tabLayout加载viewpager
        tableLayout.setupWithViewPager(vpCircle);
        //viewpager加载adapter
        vpCircle.setAdapter(fAdapter);


        mCircleImage = (ImageView) findViewById(R.id.circle_image);
        mCircleTitleTextView = (TextView) findViewById(R.id.circle_title);
        mCircleCreateTimeTextView = (TextView) findViewById(R.id.circle_act_createTime);
        mCircleActPhotoLayout = (LinearLayout) findViewById(R.id.circle_act_photo);
        mCircleActTitleTextView = (TextView) findViewById(R.id.circle_act_title);
        mCircleActContentTextView = (TextView) findViewById(R.id.circle_act_content);
        View mOperateBarTopLine = findViewById(R.id.operate_bar_top_line1);
        mCircleActSign = (LinearLayout) findViewById(R.id.circle_act_sign);
        mCircleActComment = (LinearLayout) findViewById(R.id.circle_act_comment);
        mCircleActEvaluate = (LinearLayout) findViewById(R.id.circle_act_evaluate);
        mCircleActAlbum = (LinearLayout) findViewById(R.id.circle_act_album);
        mOperateBarTopLine.setVisibility(View.VISIBLE);
    }

    private void initLayoutParams() {
        photoLayoutParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        photoLayoutParams.setMargins(0, marginDefault, 0, 0);

        imageLayoutDefaultParams = new LinearLayout.LayoutParams(
                circleActPhotoLength, circleActPhotoLength);
        imageLayoutParams = new LinearLayout.LayoutParams(
                circleActPhotoLength, circleActPhotoLength);
        imageLayoutParams.setMargins(marginDefault, 0, 0, 0);
    }

    private void initData() {
        Intent intent = getIntent();
        mCircleAct = (CircleAct) intent.getSerializableExtra("circleAct");
        isComment = intent.getBooleanExtra("isComment", false);
        mCircleActID = mCircleAct.getId();
        mCircleActName = mCircleAct.getTitle();
        userID = 60;
        pt = new PhotoTools();


        //注册广播接受者
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("CircleActActivity");
        registerReceiver(mBroadcastReceiver, intentFilter);
    }


    private class SetImageViewThread extends Thread {
        private int imageType;
        private String imgePath;
        private int childIndex;

        SetImageViewThread(int imageType, String imgePath, int childIndex) {
            this.imageType = imageType;
            this.imgePath = imgePath;
            this.childIndex = childIndex;
        }

        @Override
        public void run() {
            URL url = null;
            if (imgePath == null) {
                return;
            }
            try {
                url = new URL("http://218.108.93.154:8090/" + imgePath.trim());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            Bitmap bitmap = pt.getBitMap(url);
            if (bitmap != null) {
                File bitmapfile = pt.saveBitmap(bitmap, url);
                Message msg = new Message();
                msg.what = imageType;
                msg.arg1 = childIndex;
                msg.obj = bitmapfile;
                imageHandler.sendMessage(msg);
            }
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler imageHandler = new Handler() {
        public void handleMessage(Message msg) {
            ImageView imageView = null;
            switch (msg.what) {
                case CIRCLE_IMAGE:
                    imageView = mCircleImage;
                    break;

                case CIRCLE_ACT_PHOTO:
                    imageView = (ImageView) mCircleActPhotoLayout
                            .getChildAt(msg.arg1);
                    break;
            }
            if (imageView != null) {
                imageView.setImageBitmap(pt.getBitmapFromPath(((File) msg.obj)
                        .getAbsolutePath()));
            }

        }
    };

    @SuppressLint({"InflateParams", "CutPasteId"})
    private void initPopuptWindow() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View popupWindow = layoutInflater.inflate(
                R.layout.activity_circle_act_comment, null);

        // 创建一个PopupWindow
        mPopupWindow = new PopupWindow(popupWindow);
        // 设置SelectPicPopupWindow弹出窗体的宽
        mPopupWindow.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        mPopupWindow.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        mPopupWindow.setFocusable(true);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        mPopupWindow.setOutsideTouchable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0);
        mPopupWindow.setBackgroundDrawable(dw);
        // 设置动画
        mPopupWindow.setAnimationStyle(R.style.circleActCommentLayoutAnimation);
        // 设置弹出窗体需要软键盘
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        // 再设置模式，和Activity的一样，覆盖，调整大小。
        mPopupWindow
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mOpenKeyboardHandler = new Handler();

        commentContentText = (EditText) popupWindow
                .findViewById(R.id.activity_commemnt_content);
        Button cleanButton = (Button) popupWindow
                .findViewById(R.id.activity_commemnt_clean);
        Button submitButton = (Button) popupWindow
                .findViewById(R.id.activity_commemnt_submit);
        cleanButton.setOnClickListener(v -> commentContentText.setText(""));
        submitButton.setOnClickListener(v -> {
            commentContent = commentContentText.getText().toString();
            if (commentContent.isEmpty()) {
                showInfo("请先填写评论内容再发表，谢谢！");
            } else {
                commentCircleAct();
                openDialog(CIRCLE_ACT_COMM);
            }
        });
    }

    /**
     * 以下代码用来循环检测activity是否初始化完毕，打开软键盘
     */
    private void openPopupWindow(final Handler mHandler, int s) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 得到activity中的根元素
                View view = findViewById(R.id.main);
                // 如何根元素的width和height大于0说明activity已经初始化完毕
                if (view != null && view.getWidth() > 0 && view.getHeight() > 0) {
                    // 显示popwindow
                    showPopupWindow();
                    // 停止检测
                    mHandler.removeCallbacks(this);
                } else {
                    // 如果activity没有初始化完毕则等待detchTime毫秒再次检测
                    mHandler.postDelayed(this, detchTime);
                }
            }
        }, s);
    }

    /**
     * 打开弹出框
     */
    private void showPopupWindow() {
        if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(findViewById(R.id.main),
                    Gravity.CENTER, 0, 0);
            // 打开键盘，设置延时时长
            openKeyboard(mOpenKeyboardHandler, 0);
        } else {
            mPopupWindow.dismiss();
        }
    }

    /**
     * 打开软键盘
     */
    private void openKeyboard(Handler mHandler, int s) {
        mHandler.postDelayed(() -> {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }, s);
    }

    private void openDialog(int method) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        switch (method) {
            case CIRCLE_ACT_SIGN:
                builder.setMessage("活动报名中...请稍等");
                break;
            case CIRCLE_ACT_COMM:
                builder.setMessage("评论提交中...请稍等");
                break;
            case CIRCLE_ACT_EVAL:
                builder.setMessage("查看是否已经打分...请稍等");
                break;
        }
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void closeDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    private void applyCircleAct(){
        TZRequest tzRequest = new TZRequest("http://218.108.93.154:8090/CirclesService.asmx","ApplyActivity");
        tzRequest.addParam("CAID",mCircleActID);
        tzRequest.addParam("UserID", userID);;
        webAPI.call(tzRequest, new WebCallBackListener() {
            @Override
            public void success(JSONObject jsonObject, TZRequest request) {

            }

            @Override
            public void success(Object response, TZRequest request) {

            }

            @Override
            public void err(String err, TZRequest request) {

            }
        });
    }

    private void commentCircleAct(){
        TZRequest tzRequest = new TZRequest("http://218.108.93.154:8090/CirclesService.asmx","CommentActivity");
        tzRequest.addParam("CAID",mCircleActID);
        tzRequest.addParam("UserID", userID);;
        tzRequest.addParam("CCContent", commentContent);

        webAPI.call(tzRequest, new WebCallBackListener() {
            @Override
            public void success(JSONObject jsonObject, TZRequest request) {

            }

            @Override
            public void success(Object response, TZRequest request) {

            }

            @Override
            public void err(String err, TZRequest request) {

            }
        });
    }

    private void isEvalCircleAct(){
        TZRequest tzRequest = new TZRequest("http://218.108.93.154:8090/CirclesService.asmx","IsCommentActivity");
        tzRequest.addParam("CAID",mCircleActID);
        tzRequest.addParam("UserID", userID);;

        webAPI.call(tzRequest, new WebCallBackListener() {
            @Override
            public void success(JSONObject jsonObject, TZRequest request) {

            }

            @Override
            public void success(Object response, TZRequest request) {

            }

            @Override
            public void err(String err, TZRequest request) {

            }
        });
    }

    private void getCircleActStatistics(){
        TZRequest tzRequest = new TZRequest("http://218.108.93.154:8090/CirclesService.asmx","GetActivityOfStatistics");
        tzRequest.addParam("CAID",mCircleActID);

        webAPI.call(tzRequest, new WebCallBackListener() {
            @Override
            public void success(JSONObject jsonObject, TZRequest request) {

            }

            @Override
            public void success(Object response, TZRequest request) {

            }

            @Override
            public void err(String err, TZRequest request) {

            }
        });
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    private void sendBroadcast(String toActivity) {
        Intent intent = new Intent();
        intent.setAction(toActivity);
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    private void showInfo(String info) {
        TZToastTool.essential(info);
    }
}
