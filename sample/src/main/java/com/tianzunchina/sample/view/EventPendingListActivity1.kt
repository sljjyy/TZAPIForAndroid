package com.tianzunchina.sample.view

import android.os.Bundle
import android.widget.TextView
import com.tianzunchina.android.api.base.TZAppCompatActivity
import com.tianzunchina.sample.R

/**
 * Created by Administrator on 2017/5/31.
 */

class EventPendingListActivity1 : TZAppCompatActivity() {

    internal var tvTitle: TextView = null!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending_list)
        tvTitle = findViewById(R.id.tvTopTitle) as TextView
        tvTitle.text = "待办事件列表"
    }
}