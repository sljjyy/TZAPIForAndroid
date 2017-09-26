package com.tianzunchina.sample.view

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.tianzunchina.android.api.util.TimeDialogUtil
import com.tianzunchina.android.api.widget.dialog.TimePickerDialog
import com.tianzunchina.sample.R
import java.util.*

/**
 * Created by Administrator on 2017/5/31.
 */
class TimeActivity1 : Activity(), com.tianzunchina.android.api.widget.dialog.TimePickerDialog.TimePickerDialogInterface {

    internal var tvUtil: TextView = null!!
    internal var tvDialog: TextView
    internal var llUtil: LinearLayout
    internal var llDialog: LinearLayout
    internal var gregorianCalendar = GregorianCalendar()
    internal var timePickerDialog: TimePickerDialog
    internal var timeDialogUtil = TimeDialogUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time)
        init()
    }

    private fun init() {
        llDialog = findViewById(R.id.llDialog) as LinearLayout
        llUtil = findViewById(R.id.llUtil) as LinearLayout
        tvDialog = findViewById(R.id.tvDialog) as TextView
        tvUtil = findViewById(R.id.tvUtil) as TextView
        llDialog.setOnClickListener { v -> timePickerDialog.showDateAndTimePickerDialog() }
        llUtil.setOnClickListener { v -> TimeDialogUtil.getInstance().showTime(this@TimeActivity1, tvUtil) }
        timePickerDialog = TimePickerDialog(this)
    }

    override fun positiveListener() {
        val year = timePickerDialog.year
        val month = timePickerDialog.month
        val day = timePickerDialog.day
        val hour = timePickerDialog.hour
        val minute = timePickerDialog.minute
        val date = (year.toString() + "-" + month + "-" + day + " " + hour + ":" + minute).toString()
        val time = (hour.toString() + ":" + minute).toString()
        tvDialog.text = date

    }

    override fun negativeListener() {

    }
}
