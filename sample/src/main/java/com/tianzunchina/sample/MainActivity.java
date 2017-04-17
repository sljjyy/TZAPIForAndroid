package com.tianzunchina.sample;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import com.tianzunchina.android.api.base.TZAppCompatActivity;
import com.tianzunchina.android.api.widget.permission.PermissionsActivity;
import com.tianzunchina.android.api.widget.permission.PermissionsChecker;
import com.tianzunchina.sample.event.RecordingFragment;
import com.tianzunchina.sample.home.CircleFragment;
import com.tianzunchina.sample.home.HomeFragment;
import com.tianzunchina.sample.home.MeFragment;
import com.tianzunchina.sample.view.TzMainFragment;
import com.tianzunchina.sample.widget.SampleFragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 * CraetTime 2016-3-14
 * @author SunLiang
 */
public class MainActivity extends TZAppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
,ViewPager.OnPageChangeListener{

    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
    };
    private static final int REQUEST_CODE = 900;
    HomeFragment homeFragment = new HomeFragment();
    MeFragment meFragment = new MeFragment();
    CircleFragment circleFragment = new CircleFragment();
    RecordingFragment recordingFragment = new RecordingFragment();
    TzMainFragment tzMainFragment = new TzMainFragment();
    private ViewPager viewPager;
    private List<Fragment> list_fragment = new ArrayList<>(); //定义要装fragment的列表
    private MenuItem menuItem;
    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionsChecker mPermissionsChecker = new PermissionsChecker(this);
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
        }
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(4);//设置缓存页面的个数
        setupViewPager(viewPager);
        mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigationView.setOnNavigationItemSelectedListener(this);
        viewPager.addOnPageChangeListener(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        list_fragment.add(homeFragment);
        list_fragment.add(circleFragment);
        list_fragment.add(meFragment);
        list_fragment.add(tzMainFragment);
        SampleFragmentPagerAdapter adapter = new SampleFragmentPagerAdapter(this,getSupportFragmentManager(),list_fragment);
        viewPager.setAdapter(adapter);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.shouye:

                viewPager.setCurrentItem(0);
                break;
            case R.id.circle:

                viewPager.setCurrentItem(1);
                break;
            case R.id.me:

                viewPager.setCurrentItem(2);
                break;
            case R.id.tz:
                viewPager.setCurrentItem(3);

                break;
        }

        return true;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        if (menuItem != null) {
            menuItem.setChecked(false);
        } else {
            mNavigationView.getMenu().getItem(0).setChecked(false);
        }
        if(homeFragment != null){

        }
        menuItem = mNavigationView.getMenu().getItem(position);
        menuItem.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
