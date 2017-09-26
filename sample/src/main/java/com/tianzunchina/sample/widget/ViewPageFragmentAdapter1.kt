package com.tianzunchina.sample.widget

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by Administrator on 2017/5/31.
 */
class ViewPageFragmentAdapter1 : FragmentPagerAdapter {
    private var title: List<String>? = null
    private var pages: List<Fragment>? = null

    constructor(fm: FragmentManager, pages: List<Fragment>) : super(fm) {
        this.pages = pages
    }

    constructor(fm: FragmentManager, title: List<String>, pages: List<Fragment>) : super(fm) {
        this.title = title
        this.pages = pages
    }

    override fun getItem(arg0: Int): Fragment {
        return pages!![arg0]
    }

    override fun getCount(): Int {
        return pages!!.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        if (title != null) {
            return title!![position]
        }
        return super.getPageTitle(position)
    }
}
