package com.tianzunchina.sample.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.AdapterView;

import com.tianzunchina.android.api.view.list.TZCommonListActivity;
import com.tianzunchina.sample.R;
import com.tianzunchina.sample.model.Circle;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/21.
 */

public class CircleJoinedActivity extends TZCommonListActivity<Circle> {

    private static final int HBQY_CIRCLE_ID = 190;
    private static final int TIME_OUT = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setup();
    }

    private void setup() {
        setTitle("我的圈子", "返回", null);
        ArrayMap<String, Object> propertyMap = new ArrayMap<>();
        propertyMap.put("UserID", 30);
        setWebServicePropertys(propertyMap);
        adapter = new CircleListAdapter(this, listData, R.layout.item_list2, false);
        setAdapter(adapter);
    }

    @Override
    protected ArrayMap<String, Object> getWebServicePropertys() {
        return super.getWebServicePropertys();
    }

    private void init() {
        String url = "http://218.108.93.154:8090/" + "CirclesService.asmx";
        init(url, "http://tempuri.org/", "GetJoinedCircles", "SkipNumber", "TakeNumber",
                "Circles", TIME_OUT);
    }

    @Override
    protected Circle json2Obj(JSONObject json) {
        return new Circle(json);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        Circle circle = (Circle) parent.getAdapter().getItem(position);
        if (circle == null) {
            return;
        }

        Intent intent = new Intent(this, CircleActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("circle", circle);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
