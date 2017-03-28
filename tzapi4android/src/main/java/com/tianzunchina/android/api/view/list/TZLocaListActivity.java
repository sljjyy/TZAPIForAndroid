package com.tianzunchina.android.api.view.list;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.tianzunchina.android.api.R;
import com.tianzunchina.android.api.base.TZAppCompatActivity;
import com.tianzunchina.android.api.log.TZLog;
import com.tianzunchina.android.api.log.TZToastTool;
import com.tianzunchina.android.api.network.TZRequest;
import com.tianzunchina.android.api.network.WebCallBackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 标准列表页面
 * CraetTime 2016-3-14
 *
 * @author SunLiang
 */
public abstract class TZLocaListActivity<T> extends TZAppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener{
    public TextView tvLeft, tvTitle, tvRight;
    protected XListView mListView;
    protected List<T> listData;
    protected TZCommonAdapter<T> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_list);
        init();
    }

    protected void init() {
        listData = new ArrayList<T>();
        initView();
    }

    protected void setAdapter(TZCommonAdapter<T> adapter) {
        this.adapter = adapter;
        mListView.setAdapter(adapter);
    }

    /**
     * 需调用设置title
     *
     * @param title     页面标题
     * @param leftName  左按钮显示文字
     * @param rightName 右按钮显示文字
     */
    protected void setTitle(String title, String leftName, String rightName) {
        tvTitle.setText(title);
        setBtn(tvLeft, leftName);
        setBtn(tvRight, rightName);
    }

    /**
     * 需调用设置title 默认左右按钮分别为返回及刷新
     *
     * @param title 页面标题
     */
    protected void setTitle(String title) {
        setTitle(title, "返回", "刷新");
    }

    protected void setBtn(TextView tv, String name) {
        if (name == null) {
            tv.setVisibility(View.INVISIBLE);
        } else {
            tv.setText(name);
        }
    }

    /**
     * 默认操作为关闭本页面
     * 点击titleBar 左边按钮效果
     */
    protected void clickLeft() {
        finish();
    }

    /**
     * 默认操作为调用onRefresh方法
     * 点击titleBar 右边按钮效果
     */
    protected void clickRight() {
    }

    public void initData(List<T> list) {
        listData.clear();
        addAllData(list);
    }

    public void addAllData(List<T> list) {
        listData.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public void addData(T data) {
        listData.add(data);
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        tvLeft = (TextView) findViewById(R.id.tvBack);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvRight = (TextView) findViewById(R.id.tvList);

        mListView = (XListView) findViewById(R.id.list);
        tvLeft.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        mListView.setOnItemClickListener(this);
        mListView.setPullLoadEnable(false);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tvBack) {
            clickLeft();
        } else if (i == R.id.tvList) {
            clickRight();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
        }
        return false;
    }

    protected void setDivider(int height, int colorID) {
        Drawable drawable = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = getResources().getDrawable(colorID, this.getTheme());
        } else {
            drawable = getResources().getDrawable(colorID);
        }
        mListView.setDivider(drawable);
        mListView.setDividerHeight(height);
    }
}
