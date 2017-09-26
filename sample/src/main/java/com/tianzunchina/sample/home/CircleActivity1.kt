package com.tianzunchina.sample.home

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.tianzunchina.android.api.base.TZAppCompatActivity
import com.tianzunchina.android.api.network.SOAPWebAPI
import com.tianzunchina.android.api.network.TZRequest
import com.tianzunchina.android.api.network.WebCallBackListener
import com.tianzunchina.android.api.util.PhotoTools
import com.tianzunchina.android.api.util.TimeConverter
import com.tianzunchina.android.api.view.list.XListView
import com.tianzunchina.sample.R
import com.tianzunchina.sample.model.Circle
import com.tianzunchina.sample.model.CircleAct
import com.tianzunchina.sample.widget.StickyLayout1
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.net.MalformedURLException
import java.net.URL
import java.util.*

/**
 * Created by Administrator on 2017/5/26.
 */
class CircleActivity1 : TZAppCompatActivity(), XListView.IXListViewListener, StickyLayout1.OnGiveUpTouchEventListener {
    private val ACTIVITY_NAME = "CircleActivity"
    private var circle: Circle? = null
    private var userID: Int = 0
    private var webAPI: SOAPWebAPI? = null
    private var circleActivities: MutableList<CircleAct>? = null
    private var mAdapter: CircleActListAdapter? = null
    private val pt = PhotoTools()
    private var circleImage: ImageView? = null
    private var circleTitle: TextView? = null
    private var circleDesc: TextView? = null
    private var circleApply: TextView? = null
    private var operateApply: RelativeLayout? = null
    private var mXListView: XListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle)
        //  ActivityCircleBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_circle);
        init()
    }

    private fun init() {
        userID = 60

        val listener = MyListener()
        registerReceiver(listener, IntentFilter(ACTIVITY_NAME))
        webAPI = SOAPWebAPI()

        initIntent()
        initView()
        initData()
        this.onRefresh()
    }

    private fun initData() {
        // 加载图标
        val imgePath = circle!!.smallPath.replace("\\", "/")
        val file = pt.getCache(imgePath)
        if (file != null && file.exists()) {
            circleImage!!.setImageBitmap(pt.getBitmapFromPath(file
                    .absolutePath))
        } else {
            SetImageViewThread(imgePath).start()
        }
        circleTitle!!.text = circle!!.title
        circleDesc!!.text = circle!!.content
        when (circle!!.isApply) {
            APPLYNO -> {
                circleApply!!.text = "申请加入"
                operateApply!!.setOnClickListener { v ->

                }
            }
            APPLYOK -> circleApply!!.text = "已加入"
            APPLYWAIT -> circleApply!!.text = "审核中"
        }

        if (1 == circle!!.createAccess) {
            val operateCreateAct = findViewById(R.id.circle_operate_create_act) as RelativeLayout
            val operateApplication = findViewById(R.id.circle_operate_application) as RelativeLayout

            operateCreateAct.visibility = View.VISIBLE
            operateApplication.visibility = View.VISIBLE

            operateCreateAct.setOnClickListener { v ->

            }
            operateApplication.setOnClickListener { v ->

            }
        }
    }

    private fun initView() {
        circleImage = findViewById(R.id.ivCircleImage) as ImageView
        circleTitle = findViewById(R.id.circle_title) as TextView
        circleDesc = findViewById(R.id.circle_description) as TextView
        circleApply = findViewById(R.id.circle_isApply) as TextView
        operateApply = findViewById(R.id.circle_operate_apply) as RelativeLayout
        val operateMenber = findViewById(R.id.circle_operate_menber) as RelativeLayout
        val mStickyLayout = findViewById(R.id.sticky_layout) as StickyLayout1
        mXListView = findViewById(R.id.circle_activities) as XListView
        circleActivities = ArrayList<CircleAct>()

        mAdapter = CircleActListAdapter(this, circleActivities, R.layout.item_circle_act)
        mXListView!!.adapter = mAdapter
        mXListView!!.setPullLoadEnable(false)
        mXListView!!.setXListViewListener(this)

        operateMenber.setOnClickListener { v ->

        }
        mStickyLayout.setOnGiveUpTouchEventListener(this)
    }

    private fun initIntent() {
        val intent = intent
        circle = intent.getSerializableExtra("circle") as Circle
    }


    fun setData(isRefresh: Int, list: List<CircleAct>) {
        if (isRefresh == 1) {
            circleActivities!!.clear()
            circleActivities!!.addAll(list)
            mXListView!!.setRefreshTime(TimeConverter.date2Str(Date(),
                    "MM-dd HH:mm"))
        } else {
            for (circleAct in list) {
                if (!circleActivities!!.contains(circleAct)) {
                    circleActivities!!.add(circleAct)
                }
            }
        }
        mXListView!!.setPullLoadEnable(true)
        if (takeNumber == list.size) {
            mXListView!!.setPullLoadEnable(true)
        } else {
            mXListView!!.setPullLoadEnable(false)
        }
        mAdapter!!.notifyDataSetChanged()
    }

    fun updateApplyStatus() {
        circleApply!!.text = "审核中"
        operateApply!!.setOnClickListener(null)
    }

    private inner class SetImageViewThread(private val imgePath: String?) : Thread() {

        override fun run() {
            var url: URL? = null
            if (imgePath == null) {
                return
            }
            try {
                url = URL("http://218.108.93.154:8090/ImgHandler.ashx?Path=" + imgePath.trim { it <= ' ' })
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }

            val bitmap = pt.getBitMap(url)
            if (bitmap != null) {
                val bitmapfile = pt.saveBitmap(bitmap, url)
                val msg = Message()
                msg.obj = bitmapfile
                handler.sendMessage(msg)
            }
        }

    }

    @SuppressLint("HandlerLeak")
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            circleImage!!.setImageBitmap(pt.getBitmapFromPath((msg.obj as File)
                    .absolutePath))
        }
    }


    private fun getCircleActList() {
        val tzRequest = TZRequest("http://218.108.93.154:8090/CirclesService.asmx", "GetActivitiesOfCircle")
        tzRequest.addParam("UserID", userID)
        tzRequest.addParam("CircleID", circle!!.id)
        tzRequest.addParam("SkipNumber", 0)
        tzRequest.addParam("TakeNumber", takeNumber)
        webAPI!!.call(tzRequest, object : WebCallBackListener {
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
                    setData(0, circleActivies)
                    //                    stopLoad();
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

    private fun getCircleActNextList() {
        val tzRequest = TZRequest("http://218.108.93.154:8090/CirclesService.asmx", "GetActivitiesOfCircle")
        tzRequest.addParam("UserID", userID)
        tzRequest.addParam("CircleID", circle!!.id)
        tzRequest.addParam("SkipNumber", circleActivities!!.size)
        tzRequest.addParam("TakeNumber", takeNumber)
        webAPI!!.call(tzRequest, object : WebCallBackListener {
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
                    setData(1, circleActivies)
                    //                    stopLoad();
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


    override fun onRefresh() {
        getCircleActList()
    }

    override fun onLoadMore() {
        getCircleActNextList()
    }

    private fun stopLoad() {
        mXListView!!.stopRefresh()
        mXListView!!.stopLoadMore()
    }

    override fun giveUpTouchEvent(event: MotionEvent): Boolean {
        return mXListView!!.firstVisiblePosition == 0
    }

    private inner class MyListener : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == ACTIVITY_NAME) {
                onRefresh()
            }
        }
    }

    companion object {
        private val CIRCLE_ACT_LIST = 1
        private val CIRCLE_APPLY = 2
        private val takeNumber = 10
        private val APPLYNO = 0
        private val APPLYOK = 1
        private val APPLYWAIT = 2
    }
}
