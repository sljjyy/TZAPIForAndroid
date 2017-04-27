package com.tianzunchina.sample.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.flyco.banner.anim.select.RotateEnter;
import com.flyco.banner.anim.select.ZoomInEnter;
import com.flyco.banner.anim.unselect.NoAnimExist;
import com.flyco.banner.transform.ZoomOutSlideTransformer;
import com.tianzunchina.android.api.network.HTTPWebAPI;
import com.tianzunchina.android.api.network.TZRequest;
import com.tianzunchina.android.api.network.WebCallBackListener;
import com.tianzunchina.sample.R;
import com.tianzunchina.sample.app.SysApplication;
import com.tianzunchina.sample.home.HomePageButtonAdapter;
import com.tianzunchina.sample.model.CaseParent;
import com.tianzunchina.sample.widget.ADImageBanner;
import com.tianzunchina.sample.widget.ADItem;
import com.tianzunchina.sample.widget.AppIco;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
/**
 * 台州首页
 * Created by yqq on 2017/4/17.
 */

public class TzMainFragment extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener{

    private ADImageBanner adIBanner;
    private List<ADItem> adItems = new ArrayList<>();
    View view;
    private ArrayList<AppIco> appIcos = new ArrayList<>();
    HTTPWebAPI webAPI = new HTTPWebAPI();
    Intent intent;
    ListView lvPending, lvHis;
    TextView tvGd, tvDclGd;
    ArrayList<CaseParent> hisCases, pendingCases;
    ProgressBar pbHis, pbPending;
    ScrollView svCaseList;
    boolean isRefresh = true;
    boolean isRefresh2 = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tz_main, container, false);

        init();
        initView();

        return view;
    }

    private void init(){
        lvPending = (ListView) view.findViewById(R.id.lvPendingList);
        lvHis = (ListView) view.findViewById(R.id.lvHistoryList);
        tvGd = (TextView) view.findViewById(R.id.tvHistoryMore);//事件清单
        tvGd.setOnClickListener(this);
        tvDclGd = (TextView) view.findViewById(R.id.tvPendingMore);//待办事件
        tvDclGd.setOnClickListener(this);
        pbHis = (ProgressBar) view.findViewById(R.id.pbHistory);
        pbPending = (ProgressBar) view.findViewById(R.id.pbPending);
        svCaseList = (ScrollView) view.findViewById(R.id.id_sv_hp_case_list);

        initGridView();

        adIBanner = (ADImageBanner) view.findViewById(R.id.adibanner);
        adItems.add(new ADItem("首页1",R.drawable.tz_welcome));
        adItems.add(new ADItem("首页2",R.drawable.tz_welcome2));
        adItems.add(new ADItem("首页3",R.drawable.tz_welcome3));

        setAdData();
    }

    /**
     * 初始化
     */
    private void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initHistory();
                initPending();
            }
        }, 0);
    }
    /**
     * 初始化待办
     */
    private void initPending() {
        pendingCases = new ArrayList<>();
        pbPending.setVisibility(View.VISIBLE);
        lvPending.setVisibility(View.GONE);
        getPendingList();
    }

    /**
     * 初始化历史
     */
    private void initHistory() {
        hisCases = new ArrayList<>();
        pbHis.setVisibility(View.VISIBLE);
        lvHis.setVisibility(View.GONE);
        getHistoryList();
    }




    //TODO 广告栏配置
    private void setAdData() {
        adIBanner.setSelectAnimClass(ZoomInEnter.class)
                .setSource(adItems)
                .setSelectAnimClass(RotateEnter.class)
                .setUnselectAnimClass(NoAnimExist.class)
                .setTransformerClass(ZoomOutSlideTransformer.class)
                .startScroll();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {
        Intent i ;
        switch (v.getId()){
            case R.id.tvHistoryMore:
                i = new Intent(this.getActivity(),CaseListActivity.class);
                startActivity(i);

                break;
            case R.id.tvPendingMore:
                i = new Intent(this.getActivity(),EventPendingListActivity.class);
                startActivity(i);
                break;
        }

    }

    class ItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AppIco appIco = (AppIco) parent.getAdapter().getItem(position);
            Class<?> toClass = appIco.getToClass();
            if (toClass != null) {
                intent = new Intent(getActivity(), toClass);
                startActivity(intent);

              /*  switch (appIco.getNameID()) {
                    case R.string.submit_event:
                        intent.putExtra("title", appIco.getTitle());
                        break;
                }*/
            }
        }
    }

    /**
     * 初始化首页图标
     */
    private void initGridView(){
        GridView gridview = (GridView) view.findViewById(R.id.app_gridView);
        initAppList();

        HomePageButtonAdapter adapter = new HomePageButtonAdapter(this.getActivity(), appIcos);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new ItemClickListener());

        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.llAppColumns);
        params.height = layout.getHeight();
        gridview.setLayoutParams(params);
    }
    //初始化图标
    synchronized private void initAppList() {
        appIcos.add(new AppIco(CaseActivity.class,R.string.submit_event, R.mipmap.img_my_task_event));
        appIcos.add(new AppIco(R.string.web, R.mipmap.ico_xzsp));
       /* appIcos.add(new AppIco(R.string.office, R.mipmap.ico_chaxun));
        appIcos.add(new AppIco(R.string.law, R.mipmap.ico_wenshu));
        appIcos.add(new AppIco(R.string.move_car, R.mipmap.ico_move_car));
        appIcos.add(new AppIco(R.string.comm, R.mipmap.ico_send_out));
        appIcos.add(new AppIco(R.string.folder, R.mipmap.img_folder));*/

    }

    private void getPendingList(){
        TZRequest tzRequest = new TZRequest("http://122.226.143.66:10007/PhoneWebService.aspx","");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", 88);
            jsonObject.put("unitid", 50);
            jsonObject.put("pageSize", 4);
            jsonObject.put("pageIndex", 1);
        }catch (JSONException e){
            e.printStackTrace();
        }

        tzRequest.addParam("reqCode","6");
        tzRequest.addParam("reqData",jsonObject.toString());

        Log.e("tzRequest",tzRequest.toString());
        webAPI.callPost(tzRequest, new WebCallBackListener() {
            @Override
            public void success(JSONObject jsonObject, TZRequest request) {
                pbPending.setVisibility(View.GONE);
                isRefresh = true;
                try {
                    JSONArray resData = jsonObject.getJSONArray("resData");
                    if (resData != null) {
                        pendingCases = new ArrayList<>();
                        for (int i = 0; i < resData.length(); i++) {
                            CaseParent caseParent = new CaseParent();
                            JSONObject jsonData = resData.getJSONObject(i);
                            Log.e("this is pendingList",jsonData.toString());
                            caseParent.getCaseParent(jsonData);
                            pendingCases.add(caseParent);
                        }
                    }

                    if (pendingCases != null && !pendingCases.isEmpty()) {
                        try {
                            CaseListAdapter adapterPending = new CaseListAdapter(SysApplication.getInstance().getContext(), pendingCases, -1);
                            lvPending.setAdapter(adapterPending);
                            resetList(lvPending, 0);
                            lvPending.setOnItemClickListener(TzMainFragment.this);
                            lvPending.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void success(Object response, TZRequest request) {
                Log.e("this is response",response.toString());

            }

            @Override
            public void err(String err, TZRequest request) {
             }
        });

    }

    private void getHistoryList(){
        TZRequest tzRequest = new TZRequest("http://122.226.143.66:10007/","PhoneWebService.aspx");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", 88);
            jsonObject.put("unitid", 50);
            jsonObject.put("pageSize", 4);
            jsonObject.put("pageIndex", 1);
        }catch (JSONException e){
            e.printStackTrace();
        }
        tzRequest.addParam("reqCode","12");
        tzRequest.addParam("reqData",jsonObject.toString());

        webAPI.callPost(tzRequest, new WebCallBackListener() {
            @Override
            public void success(JSONObject jsonObject, TZRequest request) {
                pbHis.setVisibility(View.GONE);
                isRefresh2 = true;
                try {
                    JSONArray resData = jsonObject.getJSONArray("resData");
                    if (resData != null) {
                        hisCases = new ArrayList<>();
                        for (int i = 0; i < resData.length(); i++) {
                            CaseParent caseParent = new CaseParent();
                            JSONObject jsonData = resData.getJSONObject(i);
                            if (jsonData != null) {
                                caseParent.getCaseParent(jsonData);
                                hisCases.add(caseParent);
                            }
                        }
                    }

                    if (hisCases != null && !hisCases.isEmpty()) {
                        try {
                            CaseListAdapter adapterPending = new CaseListAdapter(SysApplication.getInstance().getContext(), hisCases, -1);
                            lvHis.setAdapter(adapterPending);
                            resetList(lvHis, 0);
                            lvHis.setOnItemClickListener(TzMainFragment.this);
                            lvHis.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

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

    public  void resetList(ListView listView, int count) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = 0;
            listView.setLayoutParams(params);
            return;
        }

        int totalHeight = 10;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        if (count != 0) {
            params.height = totalHeight + (listView.getDividerHeight() * (count - 1));
        } else {
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        }

        listView.setLayoutParams(params);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 1) {
            initPending();
        } else if (requestCode == 2 && resultCode == 1) {
            initHistory();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void reload() {
        if (isRefresh && isRefresh2) {
            isRefresh = false;
            isRefresh2 = false;
            initHistory();
            initPending();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        initPending();
        initHistory();
    }
}
