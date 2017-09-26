package com.tianzunchina.sample.view

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.tianzunchina.sample.R

/**
 * Created by Administrator on 2017/5/31.
 */
class RegisterActivity1 : Activity() {

    internal var tvTitle: TextView = null!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        tvTitle = findViewById(R.id.tvTopTitle) as TextView
        tvTitle.text = "注册"
    }
}
