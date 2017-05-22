package com.tianzunchina.sample.home;

import android.content.Intent;
import android.os.Bundle;
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
import com.tianzunchina.sample.model.CircleType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 海量圈子
 */
public class CircleTypeActivity extends TZActivity implements XListView.IXListViewListener {

    private List<CircleType> circleTypes;
    private XListView mListView;
    private TZCommonAdapter<CircleType> circleTypeTZCommonAdapter;
    CircleType circleType;
    SOAPWebAPI webAPI;

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

        circleTypes = new ArrayList<>();

        mListView.setAdapter(circleTypeTZCommonAdapter = new TZCommonAdapter<CircleType>(this, circleTypes, R.layout.item_list1) {
            @Override
            public void convert(TZViewHolder holder, CircleType circleType, int position) {
                holder.setText(R.id.tvName, circleType.getCircleTypeName());
                holder.setText(R.id.tvDescription, circleType.getCircleTypeDescrible());
                holder.setImage(R.id.image, "http://218.108.93.154:8090/ImgHandler.ashx?Path=" + circleType.getPath(), R.drawable.ico_rank_picture, 80, 80);
            }
        });
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(CircleTypeActivity.this, CircleListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("circleType", circleTypes.get(position-1));
            intent.putExtras(bundle);
            startActivity(intent);
        });

        mListView.setPullLoadEnable(false);
        mListView.setXListViewListener(this);
        this.onRefresh();
    }

    private void initTitle() {
        TextView tvTitle = (TextView) findViewById(R.id.tvTopTitle);
        tvTitle.setText("圈子分类列表");
    }

    private void setData(List<CircleType> list,int isRefresh) {

        if (isRefresh == 1) {
            circleTypes.clear();
            circleTypes.addAll(list);
            mListView.setRefreshTime(TimeConverter.date2Str(new Date(),
                    "MM-dd HH:mm"));
        } else {
            for (CircleType circleType : list) {
                if (!circleTypes.contains(circleType)) {
                    circleTypes.add(circleType);
                }
            }
        }
        if (10 == list.size()) {
            mListView.setPullLoadEnable(true);
        } else {
            mListView.setPullLoadEnable(false);
        }
        circleTypeTZCommonAdapter.notifyDataSetChanged();

    }

    @Override
    public void onRefresh() {
        getCircleTypesList();
    }

    @Override
    public void onLoadMore() {
    }

    private void getCircleTypesList(){
        TZRequest tzRequest = new TZRequest("http://218.108.93.154:8090/CirclesService.asmx","GetCircleTypes");
        webAPI.call(tzRequest, new WebCallBackListener() {
            @Override
            public void success(JSONObject jsonObject, TZRequest request) {
                try {
                    JSONObject body = jsonObject.getJSONObject("Body");
                    JSONArray jsonList = body.getJSONArray("CircleTypes");
                    int size = jsonList.length();
                    List<CircleType> circleTypes = new ArrayList<>();
                    if (size > 0) {
                        for (int i = 0; i < size; i++) {
                            circleType = new CircleType(
                                    jsonList.getJSONObject(i));
                            circleTypes.add(circleType);
                        }
                    }
                    setData(circleTypes,0);
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
