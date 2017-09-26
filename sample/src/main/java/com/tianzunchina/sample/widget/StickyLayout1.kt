package com.tianzunchina.sample.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.LinearLayout
import com.tianzunchina.android.api.log.TZLog
import java.util.NoSuchElementException

/**
 * Created by Administrator on 2017/5/31.
 */
class StickyLayout1 : LinearLayout {

    interface OnGiveUpTouchEventListener {
        fun giveUpTouchEvent(event: MotionEvent): Boolean
    }

    private var mHeader: View? = null
    private var mContent: View? = null
    private var mGiveUpTouchEventListener: OnGiveUpTouchEventListener? = null

    // header的高度 单位：px
    var headerHeight: Int = 0
        private set
    private var mHeaderMarginTop = 0

    private var mStatus = STATUS_DISPLAY

    private var mTouchSlop: Int = 0

    // 分别记录上次滑动的坐标
    private var mLastX = 0
    private var mLastY = 0

    // 分别记录上次滑动的坐标(onInterceptTouchEvent)
    private var mLastXIntercept = 0
    private var mLastYIntercept = 0

    private var mIsSticky = true
    private var mInitDataSucceed = false
    private var mDisallowInterceptTouchEventOnHeader = true

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (hasWindowFocus && (mHeader == null || mContent == null)) {
            initData()
        }
    }

    private fun initData() {
        val headerId = resources.getIdentifier("sticky_header", "id",
                context.packageName)
        val contentId = resources.getIdentifier("sticky_content", "id",
                context.packageName)
        if (headerId != 0 && contentId != 0) {
            mHeader = findViewById(headerId)
            mContent = findViewById(contentId)
            headerHeight = mHeader!!.measuredHeight
            mTouchSlop = ViewConfiguration.get(context)
                    .scaledTouchSlop
            if (headerHeight > 0) {
                mInitDataSucceed = true
            }
            if (DEBUG) {
                TZLog.e(TAG + " initData()", "mTouchSlop = " + mTouchSlop
                        + ", mHeaderHeight = " + headerHeight)
            }
        } else {
            throw NoSuchElementException(
                    "Did your view with id \"sticky_header\" or \"sticky_content\" exists?")
        }
    }

    fun setOnGiveUpTouchEventListener(l: OnGiveUpTouchEventListener) {
        mGiveUpTouchEventListener = l
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        var intercepted = false
        val x = event.x.toInt()
        val y = event.y.toInt()

        if (DEBUG) {
            TZLog.e(TAG + " onInterceptTouchEvent()", "x = $x, y = $y")
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastXIntercept = x
                mLastYIntercept = y
                mLastX = x
                mLastY = y
                intercepted = false
                if (DEBUG) {
                    TZLog.e(TAG + " onInterceptTouchEvent() ACTION_DOWN",
                            "intercepted = " + intercepted)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = x - mLastXIntercept
                val deltaY = y - mLastYIntercept

                if (DEBUG) {
                    TZLog.e(TAG + " onInterceptTouchEvent() ACTION_MOVE", "deltaX = "
                            + deltaX + ", deltaY = " + deltaY + ", mStatus = "
                            + mStatus)
                }

                if (mDisallowInterceptTouchEventOnHeader) {
                    if (Math.abs(deltaY) <= Math.abs(deltaX)) {
                        intercepted = false
                    } else if (mStatus == STATUS_DISPLAY && deltaY <= -mTouchSlop) {
                        intercepted = true
                    } else if (mGiveUpTouchEventListener != null) {
                        if (mStatus == STATUS_HIDDEN
                                && mGiveUpTouchEventListener!!
                                .giveUpTouchEvent(event)
                                && deltaY >= mTouchSlop) {
                            intercepted = true
                        }
                    }
                }

                if (DEBUG) {
                    TZLog.e(TAG + " onInterceptTouchEvent() ACTION_MOVE",
                            "intercepted = " + intercepted)
                }
            }
            MotionEvent.ACTION_UP -> {
                mLastYIntercept = 0
                mLastXIntercept = mLastYIntercept
                intercepted = false

                if (DEBUG) {
                    TZLog.e(TAG + " onInterceptTouchEvent() ACTION_UP",
                            "intercepted = " + intercepted)
                }
            }
            else -> {
            }
        }

        if (DEBUG) {
            TZLog.e(TAG + " onInterceptTouchEvent()", "intercepted=" + intercepted)
        }
        return intercepted && mIsSticky
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!mIsSticky) {
            return true
        }
        val x = event.x.toInt()
        val y = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = x - mLastX
                val deltaY = y - mLastY
                if (DEBUG) {
                    TZLog.e(TAG + " onTouchEvent() ACTION_MOVE",
                            "mHeaderMarginTop = " + mHeaderMarginTop
                                    + ", deltaY = " + deltaY + ", mlastY = "
                                    + mLastY)
                }
                mHeaderMarginTop += deltaY
                headerMarginTop = mHeaderMarginTop
            }
            MotionEvent.ACTION_UP -> {
                // 这里做了下判断，当松开手的时候，会自动向两边滑动，具体向哪边滑，要看当前所处的位置
                var destHeaderMarginTop = 0
                if (Math.abs(mHeaderMarginTop) <= headerHeight * 0.5) {
                    destHeaderMarginTop = 0
                    mStatus = STATUS_DISPLAY
                } else {
                    destHeaderMarginTop = -headerHeight
                    mStatus = STATUS_HIDDEN
                }
                // 慢慢滑向终点
                this.smoothSetHeaderMarginTop(mHeaderMarginTop,
                        destHeaderMarginTop, 500)
            }
            else -> {
            }
        }
        mLastX = x
        mLastY = y
        return true
    }

    fun smoothSetHeaderMarginTop(from: Int, to: Int,
                                 duration: Long) {
        val frameCount = (duration / 1000f * 30).toInt() + 1
        val partation = (to - from) / frameCount.toFloat()
        object : Thread("Thread#smoothSetHeaderHeight") {

            override fun run() {
                for (i in 0..frameCount - 1) {
                    val marginTop: Int
                    if (i == frameCount - 1) {
                        marginTop = to
                    } else {
                        marginTop = (from + partation * i).toInt()
                    }
                    post { headerMarginTop = marginTop }
                    try {
                        Thread.sleep(10)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                }

            }

        }.start()
    }

    var headerMarginTop: Int
        get() = mHeaderMarginTop
        set(marginTop) {
            var marginTop = marginTop
            if (!mInitDataSucceed) {
                initData()
            }

            if (DEBUG) {
                TZLog.e(TAG + " setHeaderMarginTop()",
                        "setHeaderMarginTop marginTop = " + marginTop)
            }
            if (marginTop >= 0) {
                marginTop = 0
            } else if (marginTop <= -headerHeight) {
                marginTop = -headerHeight
            }

            if (marginTop == 0) {
                mStatus = STATUS_DISPLAY
            } else {
                mStatus = STATUS_HIDDEN
            }

            if (mHeader != null && mHeader!!.layoutParams != null) {
                (mHeader!!.layoutParams as ViewGroup.MarginLayoutParams).setMargins(0,
                        marginTop, 0, 0)
                mHeader!!.requestLayout()
                mHeaderMarginTop = marginTop
            } else {
                if (DEBUG) {
                    TZLog.e(TAG + " setHeaderMarginTop()",
                            "null LayoutParams when setHeaderMarginTop")
                }
            }
        }

    fun setSticky(isSticky: Boolean) {
        mIsSticky = isSticky
    }

    fun requestDisallowInterceptTouchEventOnHeader(
            disallowIntercept: Boolean) {
        mDisallowInterceptTouchEventOnHeader = disallowIntercept
    }

    companion object {
        private val TAG = "StickyLayout1"
        private val DEBUG = true
        val STATUS_DISPLAY = 1
        val STATUS_HIDDEN = 2
    }

}