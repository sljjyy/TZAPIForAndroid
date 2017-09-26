package com.tianzunchina.sample.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import com.tianzunchina.android.api.base.TZActivity
import com.tianzunchina.android.api.network.SOAPWebAPI
import com.tianzunchina.android.api.network.TZRequest
import com.tianzunchina.android.api.network.WebCallBackListener
import com.tianzunchina.android.api.util.TimeConverter
import com.tianzunchina.android.api.view.list.XListView
import com.tianzunchina.sample.R
import com.tianzunchina.sample.model.CircleAct
import org.json.JSONException
import org.json.JSONObject
import java.util.*

/**
 * Created by Administrator on 2017/5/26.
 */
class CircleActListActivity1 : TZActivity(), XListView.IXListViewListener {
    private val ACTIVITY_NAME = "CircleActListActivity"
    private var userID: Int = 0
    private var circleID: Int = 0
    private var circleName: String? = null
    private var circleActivities: MutableList<CircleAct>? = null
    private var mAdapter: CircleActListAdapter? = null
    private var mListView: XListView? = null
    internal var webAPI = SOAPWebAPI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list1)
        init()
    }

    private fun init() {
        initIntent()
        initTitle()
        initView()
        userID = 60

        val listener = MyListener()
        registerReceiver(listener, IntentFilter(ACTIVITY_NAME))

        this.onRefresh()
    }

    private fun initView() {
        mListView = findViewById(R.id.list) as XListView
        circleActivities = ArrayList<CircleAct>()
        mAdapter = CircleActListAdapter(this, circleActivities, R.layout.item_circle_act)
        mListView!!.adapter = mAdapter

        mListView!!.setPullLoadEnable(false)
        mListView!!.setXListViewListener(this)
    }

    private fun initTitle() {
        val tvTitle = findViewById(R.id.tvTopTitle) as TextView
        tvTitle.text = circleName
    }

    private fun initIntent() {
        val intent = intent
        circleID = intent.getIntExtra("circleID", -2)
        circleName = intent.getStringExtra("circleName")
    }

    fun setData(isRefresh: Int, list: List<CircleAct>) {
        if (isRefresh == 1) {
            circleActivities!!.clear()
            circleActivities!!.addAll(list)
            mListView!!.setRefreshTime(TimeConverter.date2Str(Date(),
                    "MM-dd HH:mm"))
        } else {
            for (circleAct in list) {
                if (!circleActivities!!.contains(circleAct)) {
                    circleActivities!!.add(circleAct)
                }
            }
        }
        mListView!!.setPullLoadEnable(true)
        if (takeNumber == list.size) {
            mListView!!.setPullLoadEnable(true)
        } else {
            mListView!!.setPullLoadEnable(false)
        }
        mAdapter!!.notifyDataSetChanged()
    }

    override fun onRefresh() {
        getCircleActList(0)
    }

    override fun onLoadMore() {
        getCircleActList(circleActivities!!.size)
    }

    private fun stopLoad() {
        mListView!!.stopRefresh()
        mListView!!.stopLoadMore()
    }

    private fun getCircleActList(skip: Int) {
        val tzRequest = TZRequest("http://218.108.93.154:8090/CirclesService.asmx", "GetActivitiesOfCircle")
        tzRequest.addParam("CircleID", circleID)
        tzRequest.addParam("UserID", userID)
        tzRequest.addParam("SkipNumber", skip)
        tzRequest.addParam("TakeNumber", takeNumber)
        webAPI.call(tzRequest, object : WebCallBackListener {
            override fun success(jsonObject: JSONObject, request: TZRequest) {
                try {
                    val body = jsonObject.getJSONObject("Body")
                    val jsonList = body.getJSONArray("CircleActivities")
                    val size = jsonList.length()
                    val circleActivies = ArrayList<CircleAct>()
                    if (size > 0) {
                        for (i in 0..size - 1) {
                            val circleAct = CircleAct(
                                    jsonList.getJSONObject(i))
                            circleActivies.add(circleAct)
                        }
                    }
                    setData(skip, circleActivies)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                stopLoad()

            }

            override fun success(response: Any, request: TZRequest) {

            }

            override fun err(err: String, request: TZRequest) {

            }
        })

    }

    private inner class MyListener : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == ACTIVITY_NAME) {
                onRefresh()
            }
        }
    }

    companion object {

        private val takeNumber = 10
    }
}
