package com.tianzunchina.sample.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianzunchina.android.api.base.TZFragment;
import com.tianzunchina.sample.R;
import com.tianzunchina.sample.view.TimeActivity;
import com.tianzunchina.sample.form.DynamicFormActivity;
import com.tianzunchina.sample.login.LoginActivity;
import com.tianzunchina.sample.notify.NotifyActivity;
import com.tianzunchina.sample.app.download.UpdateActivity;
import com.tianzunchina.sample.event.EventActivity;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends TZFragment implements View.OnClickListener,
        BaseQuickAdapter.OnRecyclerViewItemClickListener,
        BaseQuickAdapter.OnRecyclerViewItemLongClickListener {
    private static final Class<?>[] ACTIVITYS = {EventActivity.class, UpdateActivity.class, NotifyActivity.class,
            LoginActivity.class, DynamicFormActivity.class, TimeActivity.class};
    private static final String[] TITLES = {"案件上报", "版本更新", "通知栏", "登录", "表单框", "时间选择"};
    private ArrayList<HomeItem> mDataList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initData();
        RecyclerView rvList = (RecyclerView) view.findViewById(R.id.rvList);
        BaseQuickAdapter homeAdapter = new HomeAdapter(R.layout.item_home, mDataList);
        homeAdapter.openLoadAnimation();
        homeAdapter.setOnRecyclerViewItemClickListener(this);
        homeAdapter.setOnRecyclerViewItemLongClickListener(this);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setAdapter(homeAdapter);
        return view;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initData() {
        for (int i = 0; i < TITLES.length; i++) {
            HomeItem item = new HomeItem();
            item.setTitle(TITLES[i]);
            item.setFragment(ACTIVITYS[i]);
            mDataList.add(item);
        }
    }

    @Override
    public void onItemClick(View view, int i) {
        Intent intent = new Intent(getActivity(), ACTIVITYS[i]);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(View view, int i) {
        return false;
    }
}
