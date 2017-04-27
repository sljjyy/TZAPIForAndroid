package com.tianzunchina.sample.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import com.tianzunchina.android.api.base.TZAppCompatActivity;
import com.tianzunchina.sample.R;
import com.tianzunchina.sample.widget.SamplePagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * 事件上报
 * Created by yqq on 2017/4/18.
 */

public class CaseActivity  extends TZAppCompatActivity  {

    private SamplePagerAdapter fAdapter;//定义适配器
    EventCaseFragment eventCaseFragment = new EventCaseFragment();
    IpCaseFragment ipCaseFragment = new IpCaseFragment();
    SampleCaseFragment sampleCaseFragment = new  SampleCaseFragment();
    TextView tvTitle;
    private String tabTitles[] = new String[]{"简易案件","执法事件","违停案件"};
    List<Fragment> list_fragment;
    TabLayout tabLayout;
    ViewPager vpCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case);
        initView();
    }

    @Override
    protected void onResume() {
        System.gc();
        System.runFinalization();
        super.onResume();
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tvTopTitle);
        tvTitle.setText("事件上报");
        //初始化viewpager和tabLayout
        vpCase = (ViewPager) findViewById(R.id.vpCase);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //将各个fragment装进列表中
        list_fragment = new ArrayList<>();
        list_fragment.add(sampleCaseFragment);
        list_fragment.add(eventCaseFragment);
        list_fragment.add(ipCaseFragment);

        //为TabLayout添加tab名称
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);

            if (tab != null) {
                tab.setCustomView(fAdapter.getTabView(i));
            }
         }

        fAdapter = new SamplePagerAdapter(this, this.getSupportFragmentManager(), list_fragment,tabTitles);
        //viewpager加载adapter
        vpCase.setAdapter(fAdapter);
        //tabLayout加载viewpager
        tabLayout.setupWithViewPager(vpCase);

    }

}
