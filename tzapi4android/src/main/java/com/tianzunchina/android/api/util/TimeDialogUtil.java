package com.tianzunchina.android.api.util;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
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
    private boolean mIgnoreTimeSet = false;

    //TODO 如果要写单例的话，就定义一个私有的构造方法（防止通过 new TimeDialogUtil()去实例化）
    public static TimeDialogUtil getInstance() {
        if (timeDialogUtil == null) {
            timeDialogUtil = new TimeDialogUtil();
        }
        return timeDialogUtil;
    }

    /**
     * 选择日期和时间弹出框
     */
    //TODO GregorianCalendar可以不作为方法参数，tvTime空指针判断，提取公用方法
    //TODO 没有考虑到选择取消的情况
    public void openSelectedDateAndTime(Activity activity, final TextView tvTime) {
        final GregorianCalendar gregorianCalendar = new GregorianCalendar();
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
                if(tvTime != null){
                    tvTime.setText(TimeConverter.getSrcOfDate(gregorianCalendar.getTime(), TimeConverter.DEF_DATE_FORMAT));//TODO 格式无法修改
                }
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
        Calendar cal = Calendar.getInstance();
        final DatePickerDialog mDialog = new DatePickerDialog(activity, null,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        //手动设置按钮
        mDialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
                DatePicker datePicker = mDialog.getDatePicker();
                int year = datePicker.getYear();
                int month = datePicker.getMonth()+1;
                int day = datePicker.getDayOfMonth();
                if(tvTime != null){
                    tvTime.setText(year + "-" + month + "-" + day);
                }
            }
        });
        //取消按钮，如果不需要直接不设置即可
        mDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        mDialog.show();
    }


    /**
     * 选择时间弹出框
     */
    public void openSelectedTime(Activity activity, final TextView tvTime) {

        final GregorianCalendar gregorianCalendar = new GregorianCalendar();
        Calendar cd = Calendar.getInstance();
        int hour = cd.get(Calendar.HOUR_OF_DAY);
        int min = cd.get(Calendar.MINUTE);
        new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                gregorianCalendar.set(Calendar.HOUR_OF_DAY, hour);
                gregorianCalendar.set(Calendar.MINUTE, minute);
                if(tvTime != null){
                    tvTime.setText(TimeConverter.getSrcOfDate(gregorianCalendar.getTime(), TimeConverter.DEF_DATE_TIME_FORMAT));//TODO 格式无法修改
                }
            }
        }, hour, min, true).show();

    }

    public void showTime(final Activity activity, final TextView tvTime) {
        Calendar cal = Calendar.getInstance();

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        final TimePickerDialog timeDlg = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (mIgnoreTimeSet) return;
                tvTime.setText(hourOfDay + ":" + minute);
            }
        }, hour, min, true);

        timeDlg.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mIgnoreTimeSet = false;
                // only manually invoke OnTimeSetListener (through the dialog) on pre-ICS devices
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                    timeDlg.onClick(dialog, which);
            }
        });

        timeDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mIgnoreTimeSet = true;
                dialog.dismiss();
            }
        });

        timeDlg.show();

    }


}
