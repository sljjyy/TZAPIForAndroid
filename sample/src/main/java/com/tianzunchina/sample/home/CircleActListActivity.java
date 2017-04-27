package com.tianzunchina.sample.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;
import com.tianzunchina.android.api.base.TZActivity;
import com.tianzunchina.android.api.network.SOAPWebAPI;
import com.tianzunchina.android.api.network.TZRequest;
import com.tianzunchina.android.api.network.WebCallBackListener;
import com.tianzunchina.android.api.util.TimeConverter;
import com.tianzunchina.android.api.view.list.XListView;
import com.tianzunchina.sample.R;
import com.tianzunchina.sample.model.CircleAct;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 所有活动
 */

public class CircleActListActivity extends TZActivity implements
        XListView.IXListViewListener {

    private static final int takeNumber = 10;
    private String ACTIVITY_NAME = "CircleActListActivity";
    private int userID, circleID;
    private String circleName;
    private List<CircleAct> circleActivities;
    private CircleActListAdapter mAdapter;
    private XListView mListView;
    SOAPWebAPI webAPI = new SOAPWebAPI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list1);
        init();
    }

    private void init() {
        initIntent();
        initTitle();
        initView();
        userID = 60;

        MyListener listener = new MyListener();
        registerReceiver(listener, new IntentFilter(ACTIVITY_NAME));

        this.onRefresh();
    }

    private void initView() {
        mListView = (XListView) findViewById(R.id.list);
        circleActivities = new ArrayList<>();
        mAdapter = new CircleActListAdapter(this, circleActivities, R.layout.item_circle_act);
        mListView.setAdapter(mAdapter);

        mListView.setPullLoadEnable(false);
        mListView.setXListViewListener(this);
    }

    private void initTitle() {
        TextView tvTitle = (TextView) findViewById(R.id.tvTopTitle);
        tvTitle.setText(circleName);
    }

    private void initIntent() {
        Intent intent = getIntent();
        circleID = intent.getIntExtra("circleID", -2);
        circleName = intent.getStringExtra("circleName");
    }

    public void setData(int isRefresh, List<CircleAct> list) {
        if (isRefresh == 1) {
            circleActivities.clear();
            circleActivities.addAll(list);
            mListView.setRefreshTime(TimeConverter.date2Str(new Date(),
                    "MM-dd HH:mm"));
        } else {
            for (CircleAct circleAct : list) {
                if (!circleActivities.contains(circleAct)) {
                    circleActivities.add(circleAct);
                }
            }
        }
        mListView.setPullLoadEnable(true);
        if (takeNumber == list.size()) {
            mListView.setPullLoadEnable(true);
        } else {
            mListView.setPullLoadEnable(false);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        getCircleActList(0);
    }

    @Override
    public void onLoadMore() {
        getCircleActList(circleActivities.size());
    }

    private void stopLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
    }

    private void getCircleActList(final int skip){
        TZRequest tzRequest = new TZRequest("http://218.108.93.154:8090/CirclesService.asmx","GetActivitiesOfCircle");
        tzRequest.addParam("CircleID", circleID);
        tzRequest.addParam("UserID",userID);
        tzRequest.addParam("SkipNumber", skip);
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
                    setData(skip, circleActivies);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                stopLoad();

            }

            @Override
            public void success(Object response, TZRequest request) {

            }

            @Override
            public void err(String err, TZRequest request) {

            }
        });

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
