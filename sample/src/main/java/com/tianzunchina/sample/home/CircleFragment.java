package com.tianzunchina.sample.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tianzunchina.android.api.base.TZFragment;
import com.tianzunchina.android.api.network.SOAPWebAPI;
import com.tianzunchina.android.api.network.TZRequest;
import com.tianzunchina.android.api.network.WebCallBackListener;
import com.tianzunchina.sample.R;
import com.tianzunchina.sample.model.Circle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * 圈子
 * Created by yqq on 2017/4/14.
 */

public class CircleFragment extends TZFragment  {
    private ArrayList<Circle> mCircleColumns = new ArrayList<>();
    private HomePageColumnAdapter mHomePageColumnAdapter;
    SOAPWebAPI webAPI = new SOAPWebAPI();
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_circle, container, false);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText("圈子");
        init();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void init() {

        //gvCircle = (GridView) view.findViewById(R.id.gvMyCircles);
        initView();
        initData();
    }

    private void initView() {
        RelativeLayout rlCircleType = (RelativeLayout) view
                .findViewById(R.id.indAllCircles);
        RelativeLayout rlMyCircle = (RelativeLayout) view
                .findViewById(R.id.indMyCircles);
        RelativeLayout rlCircleAtc = (RelativeLayout) view
                .findViewById(R.id.indAllActivities);
        RelativeLayout rlMyCircleAtc = (RelativeLayout) view
                .findViewById(R.id.indMyActivities);
        RelativeLayout rlCircleData = (RelativeLayout) view
                .findViewById(R.id.indCircleData);
        ImageView tvTypeImage = (ImageView) rlCircleType
                .findViewById(R.id.image);
        TextView tvTypeTitle = (TextView) rlCircleType.findViewById(R.id.tvName);
        TextView tvTypeDesc = (TextView) rlCircleType
                .findViewById(R.id.tvDescription);
        ImageView tvMyCircleImage = (ImageView) rlMyCircle
                .findViewById(R.id.image);
        TextView tvMyCircleTitle = (TextView) rlMyCircle
                .findViewById(R.id.tvName);
        TextView tvMyCircleDesc = (TextView) rlMyCircle
                .findViewById(R.id.tvDescription);
        ImageView tvAtcImage = (ImageView) rlCircleAtc.findViewById(R.id.image);
        TextView tvAtcTitle = (TextView) rlCircleAtc.findViewById(R.id.tvName);
        TextView tvAtcDesc = (TextView) rlCircleAtc
                .findViewById(R.id.tvDescription);
        ImageView tvMyAtcImage = (ImageView) rlMyCircleAtc
                .findViewById(R.id.image);
        TextView tvMyAtcTitle = (TextView) rlMyCircleAtc
                .findViewById(R.id.tvName);
        TextView tvMyAtcDesc = (TextView) rlMyCircleAtc
                .findViewById(R.id.tvDescription);
        ImageView tvCircleDataImage = (ImageView) rlCircleData
                .findViewById(R.id.image);
        TextView tvCircleDataTitle = (TextView) rlCircleData
                .findViewById(R.id.tvName);
        TextView tvCircleDataDesc = (TextView) rlCircleData
                .findViewById(R.id.tvDescription);

        mHomePageColumnAdapter = new HomePageColumnAdapter(getActivity(), mCircleColumns);
        tvTypeImage.setImageResource(R.drawable.ico_all_circle);
        tvTypeTitle.setText("海量圈子");
        tvTypeDesc.setText("申请新圈子");
        tvMyCircleImage.setImageResource(R.drawable.ico_my_circle);
        tvMyCircleTitle.setText("我的圈子");
        tvMyCircleDesc.setText("我已经加入的圈子");
        tvAtcImage.setImageResource(R.drawable.ico_all_activity);
        tvAtcTitle.setText("所有活动");
        tvAtcDesc.setText("所有圈子的活动");
        tvMyAtcImage.setImageResource(R.drawable.ico_my_activity);
        tvMyAtcTitle.setText("我的活动");
        tvMyAtcDesc.setText("我参与的活动");
        tvCircleDataImage.setImageResource(R.drawable.ico_circle_data);
        tvCircleDataTitle.setText("圈子大数据");
        tvCircleDataDesc.setText("圈子排名、人气活动");
       /* gvCircle.setAdapter(mHomePageColumnAdapter);
        gvCircle.setOnItemClickListener(new GridItemClickListener());*/
        rlCircleType.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CircleTypeActivity.class);
            startActivity(intent);
        });
        rlMyCircle.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CircleJoinedActivity.class);
            startActivity(intent);
        });
        rlCircleAtc.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CircleActListActivity.class);
            intent.putExtra("circleID", 0);
            intent.putExtra("circleName", "所有活动");
            startActivity(intent);
        });
        rlMyCircleAtc.setOnClickListener(v -> {

        });
        rlCircleData.setOnClickListener(v -> {

        });
    }

    private void fixedView(View view) {
       /* ViewGroup.LayoutParams params = gvCircle.getLayoutParams();
        LinearLayout layout = (LinearLayout) view
                .findViewById(R.id.llMyCircles);
        params.height = layout.getHeight();
        gvCircle.setLayoutParams(params);*/
    }

    private void initData() {
        GetFixedCircle();
        fixedView(view);
    }

    public void setData(ArrayList<Circle> circles) {
        mCircleColumns = circles;
        mHomePageColumnAdapter.setList(mCircleColumns);
    }

    private void GetFixedCircle(){
        TZRequest tzRequest = new TZRequest("http://218.108.93.154:8090/CirclesService.asmx","GetCirclesOfHome");
        tzRequest.addParam("UserID",60);
        tzRequest.addParam("RegionID",1);
        webAPI.call(tzRequest, new WebCallBackListener() {
            @Override
            public void success(JSONObject jsonObject, TZRequest request) {
                try {
                    JSONObject body =  jsonObject.getJSONObject("Body");
                    JSONArray jsonList = body.getJSONArray("Circles");
                    int size = jsonList.length();
                    ArrayList<Circle> circles = new ArrayList<>();
                    if (size > 0) {
                        for (int i = 0; i < size; i++) {
                            Circle circle = new Circle(
                                    jsonList.getJSONObject(i));
                            circles.add(circle);
                        }
                    }
                    setData(circles);

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


}
