package com.tianzunchina.sample.home

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.text.method.ScrollingMovementMethod
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.tianzunchina.android.api.base.TZAppCompatActivity
import com.tianzunchina.android.api.log.TZToastTool
import com.tianzunchina.android.api.network.SOAPWebAPI
import com.tianzunchina.android.api.network.TZRequest
import com.tianzunchina.android.api.network.WebCallBackListener
import com.tianzunchina.android.api.util.PhotoTools
import com.tianzunchina.android.api.util.UnitConverter
import com.tianzunchina.sample.R
import com.tianzunchina.sample.model.CircleAct
import com.tianzunchina.sample.widget.SamplePagerAdapter1
import org.json.JSONObject
import java.io.File
import java.net.MalformedURLException
import java.net.URL
import java.util.ArrayList

/**
 * Created by Administrator on 2017/5/26.
 */
class CircleActActivity1 : TZAppCompatActivity() {

    private var mOpenKeyboardHandler: Handler? = null
    private val marginDefault = UnitConverter.dip2px(10f)
    private val circleActPhotoLength = UnitConverter.dip2px(65f)
    private var isComment: Boolean = false
    private var userID: Int = 0
    private var mCircleActID: Int = 0
    private var mCircleActName: String? = null
    private var commentContent: String? = null
    private var mCircleAct: CircleAct? = null
    private var pt: PhotoTools? = null
    private var alertDialog: android.support.v7.app.AlertDialog? = null
    private var mContext: Context? = null
    private var photoLayoutParams: LinearLayout.LayoutParams? = null
    private var imageLayoutDefaultParams: LinearLayout.LayoutParams? = null
    private var imageLayoutParams: LinearLayout.LayoutParams? = null
    private var mCircleTitleTextView: TextView? = null
    private var mCircleCreateTimeTextView: TextView? = null
    private var mCircleActTitleTextView: TextView? = null
    private var mCircleActContentTextView: TextView? = null
    private var mCircleImage: ImageView? = null
    private var mCircleActPhotoLayout: LinearLayout? = null
    private var mCircleActSign: LinearLayout? = null
    private var mCircleActComment: LinearLayout? = null
    private var mCircleActEvaluate: LinearLayout? = null
    private var mCircleActAlbum: LinearLayout? = null
    private var mPopupWindow: PopupWindow? = null
    private var commentContentText: EditText? = null

    internal var webAPI = SOAPWebAPI()
    internal var vpCircle: ViewPager = null!!
    internal var tvTitle: TextView
    private val tabTitles = arrayOf("参与", "评论", "打分")
    internal var listFragment: MutableList<Fragment>
    private var fAdapter: SamplePagerAdapter1? = null//定义适配器
    internal var circleActParticipateFrg = CircleActParticipateFrg()
    internal var circleActCommentFrg = CircleActCommentFrg()
    internal var circleActEvalActListFrg = CircleActEvalActListFrg()
    internal var tableLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act_circle)
        init()
    }

    private fun init() {
        initData()
        initLayoutParams()
        initView()
        initPopuptWindow()
        if (isComment) {
            // 开始检测循环检测activity是否初始化完毕
            openPopupWindow(Handler(), detchTime)
        }
        initSetView()
    }

    private fun initSetView() {

        tvTitle.text = mCircleActName
        setCircleImageView()
        setCircleActView()
        getCircleActStatistics()

        mCircleActSign!!.setOnClickListener { v ->

        }
        mCircleActComment!!.setOnClickListener { v ->

        }
        mCircleActEvaluate!!.setOnClickListener { v ->

        }
        mCircleActAlbum!!.setOnClickListener { v ->

        }
    }

    private fun setCircleActView() {
        mCircleTitleTextView!!.text = mCircleAct!!.circleName
        mCircleCreateTimeTextView!!.text = mCircleAct!!.getcreateTimeTimeStr()

        val pathArray = mCircleAct!!.pathArray
        val pathArrayLength = pathArray.size
        if (pathArrayLength > 0) {
            mCircleActPhotoLayout!!.layoutParams = photoLayoutParams
            for (i in 0..pathArrayLength - 1) {
                val childImageView = ImageView(mContext)
                childImageView.scaleType = ImageView.ScaleType.FIT_XY
                if (i == 0) {
                    mCircleActPhotoLayout!!.addView(childImageView, i, imageLayoutDefaultParams)
                } else {
                    mCircleActPhotoLayout!!.addView(childImageView, i, imageLayoutParams)
                }

                val imgePath = pathArray[i].replace("\\", "/")
                val file = pt!!.getCache(imgePath)
                if (file != null && file.exists()) {
                    childImageView.setImageBitmap(pt!!.getBitmapFromPath(file
                            .absolutePath))
                } else {
                    SetImageViewThread(CIRCLE_ACT_PHOTO, imgePath, i)
                            .start()
                }

            }
        }

        mCircleActTitleTextView!!.text = mCircleAct!!.title
        if (mCircleAct!!.content != null && mCircleAct!!.content.length > 500) {
            mCircleActContentTextView!!.height = 400
            //            mCircleActContentTextView.setVerticalScrollBarEnabled(false);
            mCircleActContentTextView!!.movementMethod = ScrollingMovementMethod.getInstance()
        }
        mCircleActContentTextView!!.text = mCircleAct!!.content
    }

    private fun setCircleImageView() {
        var cirlceImgePath = mCircleAct!!.circleSmallPath
        if ("null" != cirlceImgePath) {
            cirlceImgePath = cirlceImgePath.replace("\\", "/")
            val file = pt!!.getCache(cirlceImgePath)
            if (file != null && file.exists()) {
                mCircleImage!!.setImageBitmap(pt!!.getBitmapFromPath(file.absolutePath))
            } else {
                SetImageViewThread(CIRCLE_IMAGE, cirlceImgePath, 0).start()
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun initView() {
        mContext = this
        tvTitle = findViewById(R.id.tvTopTitle) as TextView
        //初始化viewpager和tabLayout
        tableLayout = findViewById(R.id.tabCircle) as TabLayout
        vpCircle = findViewById(R.id.vpCircle) as ViewPager
        //将各个fragment装进列表中
        listFragment = ArrayList<Fragment>()
        listFragment.add(circleActParticipateFrg)
        listFragment.add(circleActCommentFrg)
        listFragment.add(circleActEvalActListFrg)

        //为TabLayout添加tab名称
        for (i in 0..tableLayout.tabCount - 1) {
            val tab = tableLayout.getTabAt(i)

            if (tab != null) {
                tab.customView = fAdapter!!.getTabView(i)
            }
        }
        fAdapter = SamplePagerAdapter1(this, this.supportFragmentManager, listFragment, tabTitles)
        //tabLayout加载viewpager
        tableLayout.setupWithViewPager(vpCircle)
        //viewpager加载adapter
        vpCircle.adapter = fAdapter


        mCircleImage = findViewById(R.id.circle_image) as ImageView
        mCircleTitleTextView = findViewById(R.id.circle_title) as TextView
        mCircleCreateTimeTextView = findViewById(R.id.circle_act_createTime) as TextView
        mCircleActPhotoLayout = findViewById(R.id.circle_act_photo) as LinearLayout
        mCircleActTitleTextView = findViewById(R.id.circle_act_title) as TextView
        mCircleActContentTextView = findViewById(R.id.circle_act_content) as TextView
        val mOperateBarTopLine = findViewById(R.id.operate_bar_top_line1)
        mCircleActSign = findViewById(R.id.circle_act_sign) as LinearLayout
        mCircleActComment = findViewById(R.id.circle_act_comment) as LinearLayout
        mCircleActEvaluate = findViewById(R.id.circle_act_evaluate) as LinearLayout
        mCircleActAlbum = findViewById(R.id.circle_act_album) as LinearLayout
        mOperateBarTopLine.visibility = View.VISIBLE
    }

    private fun initLayoutParams() {
        photoLayoutParams = LinearLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        photoLayoutParams!!.setMargins(0, marginDefault, 0, 0)

        imageLayoutDefaultParams = LinearLayout.LayoutParams(
                circleActPhotoLength, circleActPhotoLength)
        imageLayoutParams = LinearLayout.LayoutParams(
                circleActPhotoLength, circleActPhotoLength)
        imageLayoutParams!!.setMargins(marginDefault, 0, 0, 0)
    }

    private fun initData() {
        val intent = intent
        mCircleAct = intent.getSerializableExtra("circleAct") as CircleAct
        isComment = intent.getBooleanExtra("isComment", false)
        mCircleActID = mCircleAct!!.id
        mCircleActName = mCircleAct!!.title
        userID = 60
        pt = PhotoTools()


        //注册广播接受者
        val intentFilter = IntentFilter()
        intentFilter.addAction("CircleActActivity")
        registerReceiver(mBroadcastReceiver, intentFilter)
    }


    private inner class SetImageViewThread internal constructor(private val imageType: Int, private val imgePath: String?, private val childIndex: Int) : Thread() {

        override fun run() {
            var url: URL? = null
            if (imgePath == null) {
                return
            }
            try {
                url = URL("http://218.108.93.154:8090/" + imgePath.trim { it <= ' ' })
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }

            val bitmap = pt!!.getBitMap(url)
            if (bitmap != null) {
                val bitmapfile = pt!!.saveBitmap(bitmap, url)
                val msg = Message()
                msg.what = imageType
                msg.arg1 = childIndex
                msg.obj = bitmapfile
                imageHandler.sendMessage(msg)
            }
        }
    }

    @SuppressLint("HandlerLeak")
    private val imageHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            var imageView: ImageView? = null
            when (msg.what) {
                CIRCLE_IMAGE -> imageView = mCircleImage

                CIRCLE_ACT_PHOTO -> imageView = mCircleActPhotoLayout!!
                        .getChildAt(msg.arg1) as ImageView
            }
            if (imageView != null) {
                imageView.setImageBitmap(pt!!.getBitmapFromPath((msg.obj as File)
                        .absolutePath))
            }

        }
    }

    @SuppressLint("InflateParams", "CutPasteId")
    private fun initPopuptWindow() {
        val layoutInflater = LayoutInflater.from(mContext)
        val popupWindow = layoutInflater.inflate(
                R.layout.activity_circle_act_comment, null)

        // 创建一个PopupWindow
        mPopupWindow = PopupWindow(popupWindow)
        // 设置SelectPicPopupWindow弹出窗体的宽
        mPopupWindow!!.width = FrameLayout.LayoutParams.MATCH_PARENT
        // 设置SelectPicPopupWindow弹出窗体的高
        mPopupWindow!!.height = FrameLayout.LayoutParams.WRAP_CONTENT
        // 设置SelectPicPopupWindow弹出窗体可点击
        mPopupWindow!!.isFocusable = true
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        mPopupWindow!!.isOutsideTouchable = true
        // 实例化一个ColorDrawable颜色为半透明
        val dw = ColorDrawable(0)
        mPopupWindow!!.setBackgroundDrawable(dw)
        // 设置动画
        mPopupWindow!!.animationStyle = R.style.circleActCommentLayoutAnimation
        // 设置弹出窗体需要软键盘
        mPopupWindow!!.inputMethodMode = PopupWindow.INPUT_METHOD_NEEDED
        // 再设置模式，和Activity的一样，覆盖，调整大小。
        mPopupWindow!!.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE

        mOpenKeyboardHandler = Handler()

        commentContentText = popupWindow
                .findViewById(R.id.activity_commemnt_content) as EditText
        val cleanButton = popupWindow
                .findViewById(R.id.activity_commemnt_clean) as Button
        val submitButton = popupWindow
                .findViewById(R.id.activity_commemnt_submit) as Button
        cleanButton.setOnClickListener { v -> commentContentText!!.setText("") }
        submitButton.setOnClickListener { v ->
            commentContent = commentContentText!!.text.toString()
            if (commentContent!!.isEmpty()) {
                showInfo("请先填写评论内容再发表，谢谢！")
            } else {
                commentCircleAct()
                openDialog(CIRCLE_ACT_COMM)
            }
        }
    }

    /**
     * 以下代码用来循环检测activity是否初始化完毕，打开软键盘
     */
    private fun openPopupWindow(mHandler: Handler, s: Int) {
        mHandler.postDelayed(object : Runnable {
            override fun run() {
                // 得到activity中的根元素
                val view = findViewById(R.id.main)
                // 如何根元素的width和height大于0说明activity已经初始化完毕
                if (view != null && view.width > 0 && view.height > 0) {
                    // 显示popwindow
                    showPopupWindow()
                    // 停止检测
                    mHandler.removeCallbacks(this)
                } else {
                    // 如果activity没有初始化完毕则等待detchTime毫秒再次检测
                    mHandler.postDelayed(this, detchTime.toLong())
                }
            }
        }, s.toLong())
    }

    /**
     * 打开弹出框
     */
    private fun showPopupWindow() {
        if (!mPopupWindow!!.isShowing) {
            mPopupWindow!!.showAtLocation(findViewById(R.id.main),
                    Gravity.CENTER, 0, 0)
            // 打开键盘，设置延时时长
            openKeyboard(mOpenKeyboardHandler!!, 0)
        } else {
            mPopupWindow!!.dismiss()
        }
    }

    /**
     * 打开软键盘
     */
    private fun openKeyboard(mHandler: Handler, s: Int) {
        mHandler.postDelayed({
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(0,
                    InputMethodManager.HIDE_NOT_ALWAYS)
        }, s.toLong())
    }

    private fun openDialog(method: Int) {
        val builder = android.support.v7.app.AlertDialog.Builder(this)
        when (method) {
            CIRCLE_ACT_SIGN -> builder.setMessage("活动报名中...请稍等")
            CIRCLE_ACT_COMM -> builder.setMessage("评论提交中...请稍等")
            CIRCLE_ACT_EVAL -> builder.setMessage("查看是否已经打分...请稍等")
        }
        builder.setCancelable(false)
        alertDialog = builder.create()
        alertDialog!!.show()
    }

    private fun closeDialog() {
        if (alertDialog != null) {
            alertDialog!!.dismiss()
        }
    }

    private fun applyCircleAct() {
        val tzRequest = TZRequest("http://218.108.93.154:8090/CirclesService.asmx", "ApplyActivity")
        tzRequest.addParam("CAID", mCircleActID)
        tzRequest.addParam("UserID", userID)
        webAPI.call(tzRequest, object : WebCallBackListener {
            override fun success(jsonObject: JSONObject, request: TZRequest) {

            }

            override fun success(response: Any, request: TZRequest) {

            }

            override fun err(err: String, request: TZRequest) {

            }
        })
    }

    private fun commentCircleAct() {
        val tzRequest = TZRequest("http://218.108.93.154:8090/CirclesService.asmx", "CommentActivity")
        tzRequest.addParam("CAID", mCircleActID)
        tzRequest.addParam("UserID", userID)
        tzRequest.addParam("CCContent", commentContent)

        webAPI.call(tzRequest, object : WebCallBackListener {
            override fun success(jsonObject: JSONObject, request: TZRequest) {

            }

            override fun success(response: Any, request: TZRequest) {

            }

            override fun err(err: String, request: TZRequest) {

            }
        })
    }

    private fun isEvalCircleAct() {
        val tzRequest = TZRequest("http://218.108.93.154:8090/CirclesService.asmx", "IsCommentActivity")
        tzRequest.addParam("CAID", mCircleActID)
        tzRequest.addParam("UserID", userID)

        webAPI.call(tzRequest, object : WebCallBackListener {
            override fun success(jsonObject: JSONObject, request: TZRequest) {

            }

            override fun success(response: Any, request: TZRequest) {

            }

            override fun err(err: String, request: TZRequest) {

            }
        })
    }

    private fun getCircleActStatistics() {
        val tzRequest = TZRequest("http://218.108.93.154:8090/CirclesService.asmx", "GetActivityOfStatistics")
        tzRequest.addParam("CAID", mCircleActID)

        webAPI.call(tzRequest, object : WebCallBackListener {
            override fun success(jsonObject: JSONObject, request: TZRequest) {

            }

            override fun success(response: Any, request: TZRequest) {

            }

            override fun err(err: String, request: TZRequest) {

            }
        })
    }

    private val mBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

        }
    }

    private fun sendBroadcast(toActivity: String) {
        val intent = Intent()
        intent.action = toActivity
        sendBroadcast(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mBroadcastReceiver)
    }

    private fun showInfo(info: String) {
        TZToastTool.essential(info)
    }

    companion object {
        // 检测时间间隔，单位：毫秒
        private val detchTime = 5
        private val CIRCLE_IMAGE = 1
        private val CIRCLE_ACT_PHOTO = 2
        private val CIRCLE_ACT_SIGN = 1
        private val CIRCLE_ACT_COMM = 2
        private val CIRCLE_ACT_EVAL = 3
    }
}
