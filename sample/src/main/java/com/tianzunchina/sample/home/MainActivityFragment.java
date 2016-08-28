package com.tianzunchina.sample.home;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianzunchina.sample.R;
import com.tianzunchina.sample.event.EventActivity;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener{
    private static final Class<?>[] ACTIVITYS = {EventActivity.class};
    private static final String[] TITLES = {"案件上报"};
    private ArrayList<HomeItem> mDataList = new ArrayList<>();
    public static final String TAG = "MainActivityFragment";

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initData();
        RecyclerView rvList = (RecyclerView) view.findViewById(R.id.rvList);
        BaseQuickAdapter homeAdapter = new HomeAdapter(getActivity(), R.layout.item_home, mDataList);
        homeAdapter.openLoadAnimation();
        homeAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ACTIVITYS[position]);
                startActivity(intent);
            }
        });
        homeAdapter.setOnRecyclerViewItemLongClickListener(new BaseQuickAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                return true;
            }
        });
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
}
