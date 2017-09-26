package com.tianzunchina.sample.view

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.widget.TextView
import com.tianzunchina.android.api.base.TZAppCompatActivity
import com.tianzunchina.sample.R
import com.tianzunchina.sample.widget.SamplePagerAdapter1
import java.util.ArrayList

/**
 * Created by Administrator on 2017/5/31.
 */
class CaseActivity1 : TZAppCompatActivity() {

    private var fAdapter: SamplePagerAdapter1? = null//定义适配器
    internal var eventCaseFragment = EventCaseFragment1()
    internal var ipCaseFragment = IpCaseFragment1()
    internal var sampleCaseFragment = SampleCaseFragment1()
    internal var tvTitle: TextView = null!!
    private val tabTitles = arrayOf("简易案件", "执法事件", "违停案件")
    internal var list_fragment: MutableList<Fragment>
    internal var tabLayout: TabLayout
    internal var vpCase: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case)
        initView()
    }

    override fun onResume() {
        System.gc()
        System.runFinalization()
        super.onResume()
    }

    private fun initView() {
        tvTitle = findViewById(R.id.tvTopTitle) as TextView
        tvTitle.text = "事件上报"
        //初始化viewpager和tabLayout
        vpCase = findViewById(R.id.vpCase) as ViewPager
        tabLayout = findViewById(R.id.tabLayout) as TabLayout

        //将各个fragment装进列表中
        list_fragment = ArrayList<Fragment>()
        list_fragment.add(sampleCaseFragment)
        list_fragment.add(eventCaseFragment)
        list_fragment.add(ipCaseFragment)

        //为TabLayout添加tab名称
        for (i in 0..tabLayout.tabCount - 1) {
            val tab = tabLayout.getTabAt(i)

            if (tab != null) {
                tab.customView = fAdapter!!.getTabView(i)
            }
        }

        fAdapter = SamplePagerAdapter1(this, this.supportFragmentManager, list_fragment, tabTitles)
        //viewpager加载adapter
        vpCase.adapter = fAdapter
        //tabLayout加载viewpager
        tabLayout.setupWithViewPager(vpCase)

    }

}
