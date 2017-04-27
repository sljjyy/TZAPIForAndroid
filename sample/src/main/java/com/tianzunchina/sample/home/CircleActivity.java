package com.tianzunchina.sample.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tianzunchina.android.api.network.SOAPWebAPI;
import com.tianzunchina.android.api.network.TZRequest;
import com.tianzunchina.android.api.network.WebCallBackListener;
import com.tianzunchina.android.api.util.PhotoTools;
import com.tianzunchina.android.api.util.TimeConverter;
import com.tianzunchina.android.api.view.list.XListView;
import com.tianzunchina.sample.R;
import com.tianzunchina.sample.model.Circle;
import com.tianzunchina.sample.model.CircleAct;
import com.tianzunchina.sample.widget.StickyLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CircleActivity extends Activity implements OnClickListener,
        XListView.IXListViewListener, StickyLayout.OnGiveUpTouchEventListener {
    private static final int CIRCLE_ACT_LIST = 1, CIRCLE_APPLY = 2, takeNumber = 10;
    private String ACTIVITY_NAME = "CircleActivity";
    private Circle circle;
    private int userID;
    private SOAPWebAPI webAPI;
    private List<CircleAct> circleActivities;
    private CircleActListAdapter mAdapter;
    private PhotoTools pt = new PhotoTools();

    private ImageView circleImage;
    private TextView circleTitle,
            circleDesc, circleApply;
    private RelativeLayout operateApply, operateCreateAct, operateApplication, operateMenber;
    private XListView mXListView;

    private MyListener listener = null;

    private static final int APPLYNO = 0;
    private static final int APPLYOK = 1;
    private static final int APPLYWAIT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        init();
    }

    private void init() {
        userID = 60;

        listener = new MyListener();
        registerReceiver(listener, new IntentFilter(ACTIVITY_NAME));
        webAPI = new SOAPWebAPI();

        initIntent();
        initView();
        initData();
        this.onRefresh();
    }

    private void initData() {
        // 加载图标
        String imgePath = circle.getSmallPath().replace("\\", "/");
        File file = pt.getCache(imgePath);
        if (file != null && file.exists()) {
            circleImage.setImageBitmap(pt.getBitmapFromPath(file
                    .getAbsolutePath()));
        } else {
            new SetImageViewThread(imgePath).start();
        }
        circleTitle.setText(circle.getTitle());
        circleDesc.setText(circle.getContent());
        switch (circle.getIsApply()) {
            case APPLYNO:
                circleApply.setText("申请加入");
                operateApply.setOnClickListener(this);
                break;
            case APPLYOK:
                circleApply.setText("已加入");
                break;
            case APPLYWAIT:
                circleApply.setText("审核中");
                break;
        }

        if (1 == circle.getCreateAccess()) {
            operateCreateAct = (RelativeLayout) findViewById(R.id.circle_operate_create_act);
            operateApplication = (RelativeLayout) findViewById(R.id.circle_operate_application);

            operateCreateAct.setVisibility(View.VISIBLE);
            operateApplication.setVisibility(View.VISIBLE);

            operateCreateAct.setOnClickListener(this);
            operateApplication.setOnClickListener(this);
        }
    }

    private void initView() {
        circleImage = (ImageView) findViewById(R.id.ivCircleImage);
        circleTitle = (TextView) findViewById(R.id.circle_title);
        circleDesc = (TextView) findViewById(R.id.circle_description);
        circleApply = (TextView) findViewById(R.id.circle_isApply);
        operateApply = (RelativeLayout) findViewById(R.id.circle_operate_apply);
        operateMenber = (RelativeLayout) findViewById(R.id.circle_operate_menber);
        StickyLayout mStickyLayout = (StickyLayout) findViewById(R.id.sticky_layout);
        mXListView = (XListView) findViewById(R.id.circle_activities);
        circleActivities = new ArrayList<>();

        mAdapter = new CircleActListAdapter(this, circleActivities, R.layout.item_circle_act);
        mXListView.setAdapter(mAdapter);
        mXListView.setPullLoadEnable(false);
        mXListView.setXListViewListener(this);

        operateMenber.setOnClickListener(this);
        mStickyLayout.setOnGiveUpTouchEventListener(this);
    }

    private void initIntent() {
        Intent intent = getIntent();
        circle = (Circle) intent.getSerializableExtra("circle");
    }


    public void setData(int isRefresh, List<CircleAct> list) {
        if (isRefresh == 1) {
            circleActivities.clear();
            circleActivities.addAll (list);
            mXListView.setRefreshTime(TimeConverter.date2Str(new Date(),
                    "MM-dd HH:mm"));
        } else {
            for (CircleAct circleAct : list) {
                if (!circleActivities.contains(circleAct)) {
                    circleActivities.add(circleAct);
                }
            }
        }
        mXListView.setPullLoadEnable(true);
        if (takeNumber == list.size()) {
            mXListView.setPullLoadEnable(true);
        } else {
            mXListView.setPullLoadEnable(false);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void updateApplyStatus() {
        circleApply.setText("审核中");
        operateApply.setOnClickListener(null);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.circle_operate_apply:

                break;
            case R.id.circle_operate_create_act:

                break;
            case R.id.circle_operate_application:

                break;
            case R.id.circle_operate_menber:

                break;
        }
    }

    private class SetImageViewThread extends Thread {
        private String imgePath;

        public SetImageViewThread(String imgePath) {
            this.imgePath = imgePath;
        }

        @Override
        public void run() {
            URL url = null;
            if (imgePath == null) {
                return;
            }
            try {
                url = new URL("http://218.108.93.154:8090/ImgHandler.ashx?Path=" + imgePath.trim());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            Bitmap bitmap = pt.getBitMap(url);
            if (bitmap != null) {
                File bitmapfile = pt.saveBitmap(bitmap, url);
                Message msg = new Message();
                msg.obj = bitmapfile;
                handler.sendMessage(msg);
            }
        }

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            circleImage.setImageBitmap(pt.getBitmapFromPath(((File) msg.obj)
                    .getAbsolutePath()));
        }
    };


    private void  getCircleActList(){
        TZRequest tzRequest = new TZRequest("http://218.108.93.154:8090/CirclesService.asmx","GetActivitiesOfCircle");
        tzRequest.addParam("UserID", userID);
        tzRequest.addParam("CircleID", circle.getId());
        tzRequest.addParam("SkipNumber", 0);
        tzRequest.addParam("TakeNumber", takeNumber);
        webAPI.call(tzRequest, new WebCallBackListener() {
            @Override
            public void success(JSONObject jsonObject, TZRequest request) {
                try {
                    JSONObject body = jsonObject.getJSONObject("Body");
                    JSONArray jsonList = body.getJSONArray("CircleActivities");
                    int size = jsonList.length();
                    List<CircleAct> circleActivies = new ArrayList<>();
                    if (size > 0) {
                        for (int i = 0; i < size; i++) {
                            CircleAct circleAct = new CircleAct(
                                    jsonList.getJSONObject(i));
                            circleActivies.add(circleAct);
                        }
                    }
                    setData(0, circleActivies);
//                    stopLoad();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void success(Object response, TZRequest request) {

            }

            @Override
            public void err(String err, TZRequest request) {

            }
        });

    }

    private void getCircleActNextList(){
        TZRequest tzRequest = new TZRequest("http://218.108.93.154:8090/CirclesService.asmx","GetActivitiesOfCircle");
        tzRequest.addParam("UserID", userID);
        tzRequest.addParam("CircleID", circle.getId());
        tzRequest.addParam("SkipNumber", circleActivities.size());
        tzRequest.addParam("TakeNumber", takeNumber);
        webAPI.call(tzRequest, new WebCallBackListener() {
            @Override
            public void success(JSONObject jsonObject, TZRequest request) {
                try {
                    JSONObject body = jsonObject.getJSONObject("Body");
                    JSONArray jsonList = body.getJSONArray("CircleActivities");
                    int size = jsonList.length();
                    List<CircleAct> circleActivies = new ArrayList<>();
                    if (size > 0) {
                        for (int i = 0; i < size; i++) {
                            CircleAct circleAct = new CircleAct(
                                    jsonList.getJSONObject(i));
                            circleActivies.add(circleAct);
                        }
                    }
                    setData(1, circleActivies);
//                    stopLoad();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void success(Object response, TZRequest request) {

            }

            @Override
            public void err(String err, TZRequest request) {

            }
        });
    }


    @Override
    public void onRefresh() {
        getCircleActList();
    }

    @Override
    public void onLoadMore() {
        getCircleActNextList();
    }

    private void stopLoad() {
        mXListView.stopRefresh();
        mXListView.stopLoadMore();
    }

    @Override
    public boolean giveUpTouchEvent(MotionEvent event) {
        return mXListView.getFirstVisiblePosition() == 0;
    }

    private class MyListener extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTIVITY_NAME)) {
                onRefresh();
            }
        }
    }
}
