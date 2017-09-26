package com.tianzunchina.sample.widget

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by Administrator on 2017/5/31.
 */
class SampleFragmentPagerAdapter1(private val context: Context, fm: FragmentManager, private val list_fragment: List<Fragment>) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return list_fragment.size
    }

    override fun getItem(position: Int): Fragment {
        return list_fragment[position]
    }

}