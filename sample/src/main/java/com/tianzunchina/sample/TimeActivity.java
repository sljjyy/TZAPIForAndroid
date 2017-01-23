package com.tianzunchina.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianzunchina.android.api.util.TimeDialogUtil;
import com.tianzunchina.android.api.widget.dialog.TimePickerDialog;

import java.util.GregorianCalendar;

/**
 * Created by Administrator on 2017/1/23.
 */

public class TimeActivity extends Activity implements View.OnClickListener, com.tianzunchina.android.api.widget.dialog.TimePickerDialog.TimePickerDialogInterface {

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
        llDialog.setOnClickListener(this);
        llUtil.setOnClickListener(this);
        timePickerDialog = new TimePickerDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llDialog:
                timePickerDialog.showDateAndTimePickerDialog();
                break;
            case R.id.llUtil:
                timeDialogUtil.openSelectedDateAndTime(this, gregorianCalendar, tvUtil);
                break;

        }
    }

    @Override
    public void positiveListener() {
        int year = timePickerDialog.getYear();
        int month = timePickerDialog.getMonth();
        int day = timePickerDialog.getDay();
        int hour = timePickerDialog.getHour();
        int minute = timePickerDialog.getMinute();
        String time = String.valueOf(year + month + day + hour + minute);
        tvDialog.setText(time);

    }

    @Override
    public void negativeListener() {

    }
}
