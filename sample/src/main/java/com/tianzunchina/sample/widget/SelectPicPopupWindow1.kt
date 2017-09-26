package com.tianzunchina.sample.widget

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import com.tianzunchina.sample.R

/**
 * Created by Administrator on 2017/5/31.
 */
class SelectPicPopupWindow1(context: Activity, itemsOnClick: View.OnClickListener) : PopupWindow(context) {

    private val btn_take_photo: Button
    private val btn_pick_photo: Button
    private val btn_cancel: Button
    private val mMenuView: View

    init {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mMenuView = inflater.inflate(R.layout.alert_dialog, null)
        btn_take_photo = mMenuView.findViewById(R.id.btn_take_photo) as Button
        btn_pick_photo = mMenuView.findViewById(R.id.btn_pick_photo) as Button
        btn_cancel = mMenuView.findViewById(R.id.btn_cancel) as Button
        // 取消按钮
        btn_cancel.setOnClickListener {
            // 销毁弹出框
            dismiss()
        }
        // 设置按钮监听
        btn_pick_photo.setOnClickListener(itemsOnClick)
        btn_take_photo.setOnClickListener(itemsOnClick)
        // 设置SelectPicPopupWindow的View
        this.contentView = mMenuView
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        // 设置SelectPicPopupWindow弹出窗体的高
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.isFocusable = true
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.animationStyle = R.style.AnimBottom
        // 实例化一个ColorDrawable颜色为半透明
        val dw = ColorDrawable(0xb0000000.toInt())
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw)
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener { v, event ->
            val height = mMenuView.findViewById(R.id.pop_layout).top
            val y = event.y.toInt()
            if (event.action == MotionEvent.ACTION_UP) {
                if (y < height) {
                    dismiss()
                }
            }
            true
        }
    }
}
