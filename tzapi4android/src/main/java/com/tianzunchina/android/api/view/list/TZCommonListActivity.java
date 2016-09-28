package com.tianzunchina.android.api.view.list;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.tianzunchina.android.api.R;
import com.tianzunchina.android.api.log.TZLog;
import com.tianzunchina.android.api.log.TZToastTool;
import com.tianzunchina.android.api.network.TZCommonListSOAPWebAPI;
import com.tianzunchina.android.api.network.TZRequest;
import com.tianzunchina.android.api.network.WebCallBackListenner;
import com.tianzunchina.android.api.util.TimeConverter;
import com.tianzunchina.android.api.view.InstalList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 标准列表页面
 * CraetTime 2016-3-14
 * @author SunLiang
 */
public abstract class TZCommonListActivity<T> extends Activity implements XListView.IXListViewListener,
        View.OnClickListener, AdapterView.OnItemClickListener, WebCallBackListenner{
    private InstalList instalList;
    public TextView tvLeft, tvTitle, tvRight;
    protected XListView mListView;
    protected List<T> listData;
    protected TZCommonAdapter<T> adapter;
    private TZCommonListSOAPWebAPI webAPI;
    private ArrayMap<String, Object> propertyMap;
    private int takeNumber = 10;
    private boolean isRefresh = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_list);
        initView();
    }

    protected void onResume() {
        super.onResume();
        if(isRefresh){
            this.onRefresh();
        }
    }

    /**
     * 初始化参数
     * @param webServiceUrl 接口服务器地址
     * @param namespace WebService命名空间
     * @param name 接口方法名
     * @param skipNumber 跳过条数的参数名
     * @param takeNumber 获得条数的参数名
     * @param jsonList 数据在json中的位置 多层可用.分隔  例如Body.list
     * @param timeOut 超时时间
     * @param isRefresh onResume时是否需要刷新
     */
    protected void init(String webServiceUrl, String namespace, String name, String skipNumber,
                        String takeNumber, String jsonList, int timeOut, boolean isRefresh ){
        instalList = new InstalList(webServiceUrl, namespace, name, skipNumber, takeNumber, jsonList,
                timeOut);
        webAPI = new TZCommonListSOAPWebAPI(instalList, this);
        listData = new ArrayList<T>();
        this.isRefresh = isRefresh;
    }

    protected void init(String webServiceUrl, String namespace, String name, String skipNumber,
                        String takeNumber, String jsonList, int timeOut ){
        instalList = new InstalList(webServiceUrl, namespace, name, skipNumber, takeNumber, jsonList,
                timeOut);
        webAPI = new TZCommonListSOAPWebAPI(instalList, this);
        listData = new ArrayList<T>();
        init(webServiceUrl,namespace,name,skipNumber,takeNumber,jsonList,timeOut,true);
    }

    protected void setAdapter(TZCommonAdapter<T> adapter){
        this.adapter = adapter;
        mListView.setAdapter(adapter);
    }

    /**
     * 需调用设置title
     * @param title 页面标题
     * @param leftName 左按钮显示文字
     * @param rightName 右按钮显示文字
     */
    protected void setTitle(String title, String leftName, String rightName){
        tvTitle.setText(title);
        setBtn(tvLeft, leftName);
        setBtn(tvRight, rightName);
    }

    /**
     * 需调用设置title 默认左右按钮分别为返回及刷新
     * @param title 页面标题
     */
    protected void setTitle(String title){
        setTitle(title, "返回", "刷新");
    }

    protected void setBtn(TextView tv, String name){
        if(name == null){
            tv.setVisibility(View.INVISIBLE);
        } else {
            tv.setText(name);
        }
    }

    /**
     * 默认操作为关闭本页面
     * 点击titleBar 左边按钮效果
     */
    protected void clickLeft(){
        finish();
    }

    /**
     * 默认操作为调用onRefresh方法
     * 点击titleBar 右边按钮效果
     */
    protected void clickRight(){
        onRefresh();
    }

    /**
     *
     * @param map
     */
    protected void setWebServicePropertys(ArrayMap<String, Object> map){
        propertyMap = map;
    }

    protected ArrayMap<String, Object> getWebServicePropertys(){
        return propertyMap;
    }

    /**
     * json转换对象
     */
    protected abstract T json2Obj(JSONObject json);

    protected void setTakeNumber(int take){
        takeNumber = take;
    }

    protected void setData(int skip, List<T> list) {
        if (skip == 0) {
            listData.clear();
            listData.addAll(list);
            mListView.setRefreshTime(TimeConverter.date2Str(new Date(),"MM-dd HH:mm"));
        } else {
            for (T t : list) {
                if (!listData.contains(t)) {
                    listData.add(t);
                }
            }
        }
        if (takeNumber == list.size()) {
            mListView.setPullLoadEnable(true);
        } else {
            mListView.setPullLoadEnable(false);
        }
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
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);
    }

    @Override
    public void onRefresh() {
        new Thread(){
            @Override
            public void run() {
                webAPI.getList(0, takeNumber, getWebServicePropertys());
            }
        }.start();
    }

    @Override
    public void onLoadMore() {
        new Thread(){
            @Override
            public void run() {
                webAPI.getList(listData.size(), takeNumber, getWebServicePropertys());
            }
        }.start();
    }

    private void stopLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
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

    /**
     *
     * @param jsonObject
     * @param request
     */
    public void success(JSONObject jsonObject,TZRequest request){
        String info = "";
        try {
            JSONArray jsonList = findJSONArray(jsonObject, instalList.getJsonListName());
            int size = jsonList.length();
            List<T> list = new ArrayList<T>();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    T t = json2Obj(jsonList.getJSONObject(i));
                    list.add(t);
                }
            } else {
                info = "抱歉，暂无数据！";
            }
            setData((Integer) request.findParam(instalList.getSkipNumber()), list);
        } catch (Exception e) {
            e.printStackTrace();
            info = "数据异常！\n请稍后重新尝试";
            TZLog.i(jsonObject.toString());
        } finally {
            TZToastTool.essential(info);
            stopLoad();
        }
    }

    private JSONArray findJSONArray(JSONObject root, String path) throws Exception{
        String[] keys = path.split("\\.");
        JSONArray jsonArray = null;
        for (int i = 0; i < keys.length; i++) {
            if(i == keys.length - 1){
                jsonArray = root.getJSONArray(keys[i]);
            } else {
                root = root.getJSONObject(keys[i]);
            }
        }
        return jsonArray;
    }

    public void err(String err, TZRequest request){
        stopLoad();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ){
            this.finish();
        }
        return false;
    }

    protected void setDivider(int height, int colorID){
        Drawable drawable  = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = getResources().getDrawable(colorID, this.getTheme());
        } else {
            drawable =  getResources().getDrawable(colorID);
        }
        mListView.setDivider(drawable);
        mListView.setDividerHeight(height);
    }
}
