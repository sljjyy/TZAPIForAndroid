package com.tianzunchina.sample.widget;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import java.util.List;

/**
 * Created by HL on 2015/12/3.
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> list_fragment;

    public SampleFragmentPagerAdapter(Context context, FragmentManager fm, List<Fragment> list_fragment) {
        super(fm);
        this.context = context;
        this.list_fragment = list_fragment;
    }

    @Override
    public int getCount() {
        return list_fragment.size();
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

}
