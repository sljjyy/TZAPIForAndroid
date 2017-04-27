package com.tianzunchina.sample.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPageFragmentAdapter extends FragmentPagerAdapter {
    private List<String> title;
    private List<Fragment> pages;

    public ViewPageFragmentAdapter(FragmentManager fm, List<Fragment> pages) {
        super(fm);
        this.pages = pages;
    }

    public ViewPageFragmentAdapter(FragmentManager fm, List<String> title, List<Fragment> pages) {
        super(fm);
        this.title = title;
        this.pages = pages;
    }

    @Override
    public Fragment getItem(int arg0) {
        return pages.get(arg0);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (title != null) {
            return title.get(position);
        }
        return super.getPageTitle(position);
    }
}
