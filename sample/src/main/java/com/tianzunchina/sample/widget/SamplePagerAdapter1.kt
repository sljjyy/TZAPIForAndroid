package com.tianzunchina.sample.widget

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.tianzunchina.sample.R

/**
 * Created by Administrator on 2017/5/31.
 */
class SamplePagerAdapter1(private val context: Context, fm: FragmentManager, private val list_fragment: List<Fragment>, private val tabTitles: Array<String>) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return list_fragment.size
    }

    override fun getItem(position: Int): Fragment {
        return list_fragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence {

        val sb = SpannableString(tabTitles[position])
        return sb
    }

    fun getTabView(position: Int): View {
        val v = LayoutInflater.from(context).inflate(R.layout.layout_custom_tab, null)
        val tv = v.findViewById(R.id.tvTab) as TextView
        tv.text = tabTitles[position]
        return v
    }

}
