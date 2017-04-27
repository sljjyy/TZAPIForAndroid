package com.tianzunchina.sample.widget;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.tianzunchina.sample.R;
import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */

public class SamplePagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> list_fragment;
    private String tabTitles[] ;

    public SamplePagerAdapter(Context context, FragmentManager fm, List<Fragment> list_fragment,String tabTitles[]) {
        super(fm);
        this.context = context;
        this.list_fragment = list_fragment;
        this.tabTitles = tabTitles;
    }

    @Override
    public int getCount() {
        return list_fragment.size();
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        SpannableString sb = new SpannableString(tabTitles[position]);
        return sb;
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_custom_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.tvTab);
        tv.setText(tabTitles[position]);
        return v;
    }

}
