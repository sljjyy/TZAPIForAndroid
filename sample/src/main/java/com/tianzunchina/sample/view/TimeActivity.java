package com.tianzunchina.sample.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tianzunchina.android.api.util.TimeDialogUtil;
import com.tianzunchina.android.api.widget.dialog.TimePickerDialog;
import com.tianzunchina.sample.R;

import java.util.GregorianCalendar;

/**
 * 时间选择对话框示例
 * Created by yqq on 2017/1/23.
 */

public class TimeActivity extends Activity implements com.tianzunchina.android.api.widget.dialog.TimePickerDialog.TimePickerDialogInterface {

    TextView tvUtil, tvDialog;
    LinearLayout llUtil, llDialog;
    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    TimePickerDialog timePickerDialog;
    TimeDialogUtil timeDialogUtil = new TimeDialogUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        init();
    }

    private void init() {
        llDialog = (LinearLayout) findViewById(R.id.llDialog);
        llUtil = (LinearLayout) findViewById(R.id.llUtil);
        tvDialog = (TextView) findViewById(R.id.tvDialog);
        tvUtil = (TextView) findViewById(R.id.tvUtil);
        llDialog.setOnClickListener(v -> timePickerDialog.showDateAndTimePickerDialog());
        llUtil.setOnClickListener(v -> TimeDialogUtil.getInstance().showTime(TimeActivity.this, tvUtil));
        timePickerDialog = new TimePickerDialog(this);
    }

    @Override
    public void positiveListener() {
        int year = timePickerDialog.getYear();
        int month = timePickerDialog.getMonth();
        int day = timePickerDialog.getDay();
        int hour = timePickerDialog.getHour();
        int minute = timePickerDialog.getMinute();
        String date = String.valueOf(year + "-"+month + "-"+day + " "+hour +":"+ minute);
        String time = String.valueOf(hour +":"+ minute);
        tvDialog.setText(date);

    }

    @Override
    public void negativeListener() {

    }
}
