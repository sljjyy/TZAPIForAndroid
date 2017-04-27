package com.tianzunchina.sample.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import com.tianzunchina.android.api.base.TZActivity;
import com.tianzunchina.android.api.network.SOAPWebAPI;
import com.tianzunchina.android.api.network.TZRequest;
import com.tianzunchina.android.api.network.WebCallBackListener;
import com.tianzunchina.android.api.util.TimeConverter;
import com.tianzunchina.android.api.view.list.TZCommonAdapter;
import com.tianzunchina.android.api.view.list.TZViewHolder;
import com.tianzunchina.android.api.view.list.XListView;
import com.tianzunchina.sample.R;
import com.tianzunchina.sample.model.Circle;
import com.tianzunchina.sample.model.CircleType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//圈子列表
public class CircleListActivity extends TZActivity implements XListView.IXListViewListener {

    private CircleType circleType;
    private Circle circle;
    private List<Circle> circles;
    private XListView mListView;
    private TZCommonAdapter<Circle> circleTZCommonAdapter;
    private static final int TAKE_NUMBER = 10;
    SOAPWebAPI webAPI;
    private String countDetail = "圈友$1名，活动$2次";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list1);
        init();
    }

    private void init() {
        webAPI = new SOAPWebAPI();
        initTitle();
        mListView = (XListView) findViewById(R.id.list);
        circleType = (CircleType) getIntent().getSerializableExtra("circleType");
        circles = new ArrayList<>();
        mListView.setAdapter(circleTZCommonAdapter = new TZCommonAdapter<Circle>(this, circles, R.layout.item_list2) {
            @Override
            public void convert(TZViewHolder holder, Circle circle, int position) {
                holder.setText(R.id.tvName, circle.getTitle());
                holder.setText(R.id.tvDescription, countDetail
                        .replace("$1", circle.getCircleMemCount())
                        .replace("$2", circle.getActCount())
                        .replace("$3", String.valueOf(position + 1)));
                holder.setText(R.id.state,circle.getIsApplyStr());
                holder.setImage(R.id.image, "http://218.108.93.154:8090/ImgHandler.ashx?Path=" + circle.getSmallPath(), R.drawable.ico_rank_picture, 80, 80);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CircleListActivity.this, CircleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("circle", circles.get(position - 1));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mListView.setPullLoadEnable(false);
        mListView.setXListViewListener(this);
        this.onRefresh();
    }

    private void initTitle() {
        TextView tvTitle = (TextView) findViewById(R.id.tvTopTitle);
        tvTitle.setText("圈子列表");
    }

    private void setData(List<Circle> list,int isRefresh) {

        if (isRefresh == 1) {
            circles.clear();
            circles.addAll(list);
            mListView.setRefreshTime(TimeConverter.date2Str(new Date(),
                    "MM-dd HH:mm"));
        } else {
            for (Circle circle : list) {
                if (!circles.contains(circle)) {
                    circles.add(circle);
                }
            }
        }
        if (10 == list.size()) {
            mListView.setPullLoadEnable(true);
        } else {
            mListView.setPullLoadEnable(false);
        }
        circleTZCommonAdapter.notifyDataSetChanged();

    }

    @Override
    public void onRefresh() {
        getCircleTypesList(0);
    }

    @Override
    public void onLoadMore() {
        getCircleTypesList(circles.size());
    }

    private void getCircleTypesList(int skip){
        TZRequest tzRequest = new TZRequest("http://218.108.93.154:8090/CirclesService.asmx","GetCircles");
        tzRequest.addParam("SkipNumber",skip);
        tzRequest.addParam("TakeNumber",TAKE_NUMBER);
        tzRequest.addParam("UserID", 60);
        tzRequest.addParam("CircleTypeID", circleType.getCircleTypeID());
        webAPI.call(tzRequest, new WebCallBackListener() {
            @Override
            public void success(JSONObject jsonObject, TZRequest request) {
                try {
                    JSONObject body = jsonObject.getJSONObject("Body");
                    JSONArray jsonList = body.getJSONArray("Circles");
                    int size = jsonList.length();
                    List<Circle> circles = new ArrayList<>();
                    if (size > 0) {
                        for (int i = 0; i < size; i++) {
                            circle= new Circle(
                                    jsonList.getJSONObject(i));
                            circles.add(circle);
                        }
                    }
                    setData(circles,0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mListView.stopRefresh();

            }

            @Override
            public void success(Object response, TZRequest request) {

            }

            @Override
            public void err(String err, TZRequest request) {
                mListView.stopRefresh();

            }
        });
    }


}
