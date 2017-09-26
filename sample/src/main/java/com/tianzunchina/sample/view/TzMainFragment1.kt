package com.tianzunchina.sample.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.flyco.banner.anim.select.RotateEnter
import com.flyco.banner.anim.select.ZoomInEnter
import com.flyco.banner.anim.unselect.NoAnimExist
import com.flyco.banner.transform.ZoomOutSlideTransformer
import com.tianzunchina.android.api.network.HTTPWebAPI
import com.tianzunchina.android.api.network.TZRequest
import com.tianzunchina.android.api.network.WebCallBackListener
import com.tianzunchina.sample.R
import com.tianzunchina.sample.app.SysApplication1
import com.tianzunchina.sample.home.HomePageButtonAdapter
import com.tianzunchina.sample.model.CaseParent1
import com.tianzunchina.sample.widget.ADItem1
import com.tianzunchina.sample.widget.AppIco1
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList
import kotlinx.android.synthetic.main.fragment_tz_main.*
/**
 * Created by Administrator on 2017/5/31.
 */
class TzMainFragment1 : Fragment() {

    private val adItems = ArrayList<ADItem1>()
    private val appIcos = ArrayList<AppIco1>()
    internal var webAPI = HTTPWebAPI()
    internal var hisCases: ArrayList<CaseParent1>? = null
    internal var pendingCases: ArrayList<CaseParent1>? = null
    internal var isRefresh = true
    internal var isRefresh2 = true

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_tz_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()

    }

    private fun init() {

        tvHistoryMore.setOnClickListener { v ->
            val i = Intent(this@TzMainFragment1.activity, CaseListActivity1::class.java)
            startActivity(i)

        }
        tvPendingMore.setOnClickListener { v ->
            val i = Intent(this@TzMainFragment1.activity, EventPendingListActivity1::class.java)
            startActivity(i)

        }

        initGridView()

        adItems.add(ADItem1("首页1", R.drawable.tz_welcome))
        adItems.add(ADItem1("首页2", R.drawable.tz_welcome2))
        adItems.add(ADItem1("首页3", R.drawable.tz_welcome3))

        setAdData()
    }

    /**
     * 初始化
     */
    private fun initView() {
        Handler().postDelayed({
            initHistory()
            initPending()
        }, 0)
    }

    /**
     * 初始化待办
     */
    private fun initPending() {
        pendingCases = ArrayList<CaseParent1>()
        pbPending.visibility = View.VISIBLE
        lvPendingList.visibility = View.GONE
        getPendingList()
    }

    /**
     * 初始化历史
     */
    private fun initHistory() {
        hisCases = ArrayList<CaseParent1>()
        pbHistory.visibility = View.VISIBLE
        lvHistoryList.visibility = View.GONE
        getHistoryList()
    }

    //TODO 广告栏配置
    private fun setAdData() {
        adibanner!!.setSelectAnimClass(ZoomInEnter::class.java)
                .setSource(adItems)
                .setSelectAnimClass(RotateEnter::class.java)
                .setUnselectAnimClass(NoAnimExist::class.java)
                .setTransformerClass(ZoomOutSlideTransformer::class.java)
                .startScroll()
    }

    internal inner class ItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            val appIco = parent.adapter.getItem(position) as AppIco1
            val toClass = appIco.toClass
            if (toClass != null) {
                val intent = Intent()
                //获取intent对象
                intent.setClass(activity, toClass)
                // 获取class是使用::反射
                startActivity(intent)

                /*  switch (appIco.getNameID()) {
                    case R.string.submit_event:
                        intent.putExtra("title", appIco.getTitle());
                        break;
                }*/
            }
        }
    }

    /**
     * 初始化首页图标
     */
    private fun initGridView() {
        initAppList()

        val adapter = HomePageButtonAdapter(this.activity, appIcos)
        app_gridView.adapter = adapter
        app_gridView.onItemClickListener = ItemClickListener()
        val params = app_gridView.layoutParams
        params.height = llAppColumns.height
        app_gridView.layoutParams = params
    }

    //初始化图标
    @Synchronized private fun initAppList() {
        appIcos.add(AppIco1(CaseActivity1::class.java, R.string.submit_event, R.mipmap.img_my_task_event))
        appIcos.add(AppIco1(R.string.web, R.mipmap.ico_xzsp))
        /* appIcos.add(new AppIco1(R.string.office, R.mipmap.ico_chaxun));
        appIcos.add(new AppIco1(R.string.law, R.mipmap.ico_wenshu));
        appIcos.add(new AppIco1(R.string.move_car, R.mipmap.ico_move_car));
        appIcos.add(new AppIco1(R.string.comm, R.mipmap.ico_send_out));
        appIcos.add(new AppIco1(R.string.folder, R.mipmap.img_folder));*/

    }

    private fun getPendingList() {
        val tzRequest = TZRequest("http://122.226.143.66:10007/PhoneWebService.aspx", "")
        val jsonObject = JSONObject()
        try {
            jsonObject.put("userID", 88)
            jsonObject.put("unitid", 50)
            jsonObject.put("pageSize", 4)
            jsonObject.put("pageIndex", 1)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        tzRequest.addParam("reqCode", "6")
        tzRequest.addParam("reqData", jsonObject.toString())

        Log.e("tzRequest", tzRequest.toString())
        webAPI.callPost(tzRequest, object : WebCallBackListener {
            override fun success(jsonObject: JSONObject, request: TZRequest) {
                pbPending.visibility = View.GONE
                isRefresh = true
                try {
                    val resData = jsonObject.getJSONArray("resData")
                    if (resData != null) {
                        pendingCases = ArrayList<CaseParent1>()
                        for (i in 0..resData.length() - 1) {
                            val caseParent = CaseParent1()
                            val jsonData = resData.getJSONObject(i)
                            Log.e("this is pendingList", jsonData.toString())
                            caseParent.getCaseParent(jsonData)
                            pendingCases!!.add(caseParent)
                        }
                    }

                    if (pendingCases != null && !pendingCases!!.isEmpty()) {
                        try {
                            val adapterPending = CaseListAdapter1(SysApplication1.instance!!.context, pendingCases, -1)
                            lvPendingList.adapter = adapterPending
                            resetList(lvPendingList, 0)
                            lvPendingList.setOnItemClickListener { parent, view1, position, id ->

                            }
                            lvPendingList.visibility = View.VISIBLE
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }


            }

            override fun success(response: Any, request: TZRequest) {
                Log.e("this is response", response.toString())

            }

            override fun err(err: String, request: TZRequest) {}
        })

    }

    private fun getHistoryList() {
        val tzRequest = TZRequest("http://122.226.143.66:10007/", "PhoneWebService.aspx")
        val jsonObject = JSONObject()
        try {
            jsonObject.put("userID", 88)
            jsonObject.put("unitid", 50)
            jsonObject.put("pageSize", 4)
            jsonObject.put("pageIndex", 1)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        tzRequest.addParam("reqCode", "12")
        tzRequest.addParam("reqData", jsonObject.toString())

        webAPI.callPost(tzRequest, object : WebCallBackListener {
            override fun success(jsonObject: JSONObject, request: TZRequest) {
                pbHistory.visibility = View.GONE
                isRefresh2 = true
                try {
                    val resData = jsonObject.getJSONArray("resData")
                    if (resData != null) {
                        hisCases = ArrayList<CaseParent1>()
                        for (i in 0..resData.length() - 1) {
                            val caseParent = CaseParent1()
                            val jsonData = resData.getJSONObject(i)
                            if (jsonData != null) {
                                caseParent.getCaseParent(jsonData)
                                hisCases!!.add(caseParent)
                            }
                        }
                    }

                    if (hisCases != null && !hisCases!!.isEmpty()) {
                        try {
                            val adapterPending = CaseListAdapter1(SysApplication1.instance!!.context, hisCases, -1)
                            lvHistoryList.adapter = adapterPending
                            resetList(lvHistoryList, 0)
                            lvHistoryList.setOnItemClickListener { parent, view1, position, id ->

                            }
                            lvHistoryList.visibility = View.VISIBLE
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }


            }

            override fun success(response: Any, request: TZRequest) {

            }

            override fun err(err: String, request: TZRequest) {

            }
        })

    }

    fun resetList(listView: ListView, count: Int) {
        val listAdapter = listView.adapter
        if (listAdapter == null) {
            val params = listView.layoutParams
            params.height = 0
            listView.layoutParams = params
            return
        }

        var totalHeight = 10
        for (i in 0..listAdapter.count - 1) {
            val listItem = listAdapter.getView(i, null, listView)
            listItem.measure(0, 0)
            totalHeight += listItem.measuredHeight
        }
        val params = listView.layoutParams
        if (count != 0) {
            params.height = totalHeight + listView.dividerHeight * (count - 1)
        } else {
            params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
        }

        listView.layoutParams = params
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == 1) {
            initPending()
        } else if (requestCode == 2 && resultCode == 1) {
            initHistory()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun reload() {
        if (isRefresh && isRefresh2) {
            isRefresh = false
            isRefresh2 = false
            initHistory()
            initPending()
        }

    }

    override fun onResume() {
        super.onResume()
        initPending()
        initHistory()
    }
}
