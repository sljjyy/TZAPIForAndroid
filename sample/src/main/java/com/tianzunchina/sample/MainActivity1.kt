package com.tianzunchina.sample

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.MenuItem
import com.tianzunchina.android.api.base.TZAppCompatActivity
import com.tianzunchina.android.api.widget.permission.PermissionsActivity
import com.tianzunchina.android.api.widget.permission.PermissionsChecker
import com.tianzunchina.sample.home.BootActivity
import com.tianzunchina.sample.home.CircleFragment
import com.tianzunchina.sample.home.HomeFragment
import com.tianzunchina.sample.util.BootConfig1
import com.tianzunchina.sample.view.TzMainFragment1
import com.tianzunchina.sample.widget.SampleFragmentPagerAdapter1
import java.util.ArrayList

/**
 * Created by Administrator on 2017/5/25.
 */
class MainActivity1 : TZAppCompatActivity() {
    internal var homeFragment = HomeFragment()
    //internal var meFragment = MeFragment()
    internal var circleFragment = CircleFragment()
    internal var tzMainFragment = TzMainFragment1()
    private var viewPager: ViewPager? = null
    private val list_fragment = ArrayList<Fragment>() //定义要装fragment的列表
    private var menuItem: MenuItem? = null
    private var mNavigationView: BottomNavigationView? = null
    internal var fragment: Fragment? = Fragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mPermissionsChecker = PermissionsChecker(this)
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(*PERMISSIONS)) {
            PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS)
        }

        val listener = MyListener()
        registerReceiver(listener, IntentFilter(FINISH))

        viewPager = findViewById(R.id.viewpager) as ViewPager
        viewPager!!.offscreenPageLimit = 3//设置缓存页面的个数
        setupViewPager(viewPager!!)
        mNavigationView = findViewById(R.id.navigation) as BottomNavigationView
        mNavigationView!!.setOnNavigationItemSelectedListener { menuItem1 ->
            when (menuItem1.itemId) {
                R.id.shouye -> {

                    viewPager!!.currentItem = 0
                    fragment = homeFragment
                }
                R.id.circle -> {

                    viewPager!!.currentItem = 1
                    fragment = circleFragment
                }
                R.id.tz -> {
                    viewPager!!.currentItem = 3
                    fragment = tzMainFragment
                }

            }

            true
        }
        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (menuItem != null) {
                    menuItem!!.isChecked = false
                } else {
                    mNavigationView!!.menu.getItem(0).isChecked = false
                }
                menuItem = mNavigationView!!.menu.getItem(position)
                menuItem!!.isChecked = true
                /*if (position == 3) {
                    fragment = meFragment
                }*/
            }

            override fun onPageScrollStateChanged(i: Int) {

            }
        })
        initBoot()
    }

    private fun setupViewPager(viewPager: ViewPager) {
        list_fragment.add(homeFragment)
        list_fragment.add(circleFragment)
        list_fragment.add(tzMainFragment)
     //   list_fragment.add(meFragment)
        val adapter = SampleFragmentPagerAdapter1(this, supportFragmentManager, list_fragment)
        viewPager.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish()
        }
        if (fragment == null) {
            return
        }
        fragment!!.onActivityResult(requestCode, resultCode, data)
    }

    private fun initBoot() {
        val bc = BootConfig1()
        if (!bc.isBoot) {
            val intent = Intent(this, BootActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    /**
     * 广播，当退出时，关闭此页面
     */
    private inner class MyListener : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == FINISH) {
                finish()
            }
        }
    }

    companion object {

        internal val PERMISSIONS = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        private val REQUEST_CODE = 900
        val FINISH = "finish"
    }

}
