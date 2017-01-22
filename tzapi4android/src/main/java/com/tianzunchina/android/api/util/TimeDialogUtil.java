package com.tianzunchina.android.api.util;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 时间选择对话框工具类
 * Created by yqq on 2017/1/17.
 */

public class TimeDialogUtil {

    private static TimeDialogUtil timeDialogUtil;

    public static TimeDialogUtil getInstance(){
        if(timeDialogUtil == null){
            timeDialogUtil = new TimeDialogUtil();
        }
        return timeDialogUtil;
    }
    /**
     * 选择日期和时间弹出框
     */
    public void openSelectedDateAndTime(Activity activity, final GregorianCalendar gregorianCalendar, final TextView tvTime) {
        Calendar cd = Calendar.getInstance();
        int hour = cd.get(Calendar.HOUR_OF_DAY);
        int min = cd.get(Calendar.MINUTE);
        int year = cd.get(Calendar.YEAR);
        int month = cd.get(Calendar.MONTH);
        int day = cd.get(Calendar.DAY_OF_MONTH);
        new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                gregorianCalendar.set(Calendar.HOUR_OF_DAY, hour);
                gregorianCalendar.set(Calendar.MINUTE, minute);
                tvTime.setText(TimeConverter.getSrcOfDate(gregorianCalendar.getTime(), TimeConverter.DEF_DATE_FORMAT));
            }
        }, hour, min, true).show();
        new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                gregorianCalendar.set(Calendar.YEAR, year);
                gregorianCalendar.set(Calendar.MONTH, month);
                gregorianCalendar.set(Calendar.DAY_OF_MONTH, day);
            }
        }, year, month, day).show();
    }

    /**
     * 选择日期弹出框
     */
    public void openSelectedDate(Activity activity, final TextView tvTime) {
        Calendar cd = Calendar.getInstance();
        int year = cd.get(Calendar.YEAR);
        int month = cd.get(Calendar.MONTH);
        int day = cd.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.set(Calendar.YEAR, year);
                gregorianCalendar.set(Calendar.MONTH, month);
                gregorianCalendar.set(Calendar.DAY_OF_MONTH, day);
                tvTime.setText(TimeConverter.getSrcOfDate(gregorianCalendar.getTime(), TimeConverter.DEF_DATE_YMD_FORMAT));
            }
        }, year, month, day).show();
    }

    /**
     * 选择时间弹出框
     */
    public void openSelectedTime(Activity activity, final GregorianCalendar gregorianCalendar, final TextView tvTime) {
        Calendar cd = Calendar.getInstance();
        int hour = cd.get(Calendar.HOUR_OF_DAY);
        int min = cd.get(Calendar.MINUTE);
        new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                gregorianCalendar.set(Calendar.HOUR_OF_DAY, hour);
                gregorianCalendar.set(Calendar.MINUTE, minute);
                tvTime.setText(TimeConverter.getSrcOfDate(gregorianCalendar.getTime(), TimeConverter.DEF_DATE_TIME_FORMAT));
            }
        }, hour, min, true).show();
    }
}
