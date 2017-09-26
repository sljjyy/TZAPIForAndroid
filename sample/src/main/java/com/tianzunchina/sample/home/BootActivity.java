package com.tianzunchina.sample.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.tianzunchina.android.api.base.TZFragmentActivity;
import com.tianzunchina.sample.R;
import com.tianzunchina.sample.widget.ViewPageFragmentAdapter1;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */

public class BootActivity extends TZFragmentActivity {

    private ImageView[] dots;
    List<Fragment> listViews;
    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_boot);
        init();
    }

    private void init() {
        ViewPager mPager = (ViewPager) findViewById(R.id.vPager);
        listViews = new ArrayList<>();
        listViews.add(new Boot2Fragment1(R.drawable.boot2_bg1, R.drawable.boot2_pic1, R.drawable.boot2_title1, false));
        listViews.add(new Boot2Fragment1(R.drawable.boot2_bg2, R.drawable.boot2_pic2, R.drawable.boot2_title2, true));
        mPager.setAdapter(new ViewPageFragmentAdapter1(getSupportFragmentManager(), listViews));
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new ViewPageChangeListener());
        initDots();
    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.llBottomDots);

        dots = new ImageView[listViews.size()];

        // 循环取得小点图片
        for (int i = 0; i < listViews.size(); i++) {
            ImageView dot = new ImageView(this);
            dot.setScaleType(ImageView.ScaleType.CENTER);
            dot.setAdjustViewBounds(false);
            Glide.with(this).load(android.R.drawable.presence_invisible).into(dot);
            dots[i] = dot;
            ll.addView(dots[i]);

        }
        Glide.with(this).load(android.R.drawable.presence_online).into(dots[currentIndex]);
    }

    private void setCurrentDot(int position) {
        if (position < 0 || position > listViews.size() - 1
                || currentIndex == position) {
            return;
        }

        Glide.with(this).load(android.R.drawable.presence_online).into(dots[position]);
        Glide.with(this).load(android.R.drawable.presence_invisible).into(dots[currentIndex]);

        currentIndex = position;
    }

    public class ViewPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            // 设置底部小点选中状态
            setCurrentDot(arg0);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
