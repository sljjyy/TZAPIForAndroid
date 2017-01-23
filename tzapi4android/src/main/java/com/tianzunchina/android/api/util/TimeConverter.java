package com.tianzunchina.android.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间格式转换器
 * CraetTime 2016-3-7
 * @author SunLiang
 */
public class TimeConverter {
    public static final SimpleDateFormat SDF = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm", Locale.CHINA);
    public static final SimpleDateFormat SDF_TIME = new SimpleDateFormat(
            "HH:mm", Locale.CHINA);
    public static final SimpleDateFormat SDF_WEEK_TIME = new SimpleDateFormat(
            "aa HH:mm", Locale.CHINA);
    public static final SimpleDateFormat SDF_DATE = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.CHINA);
    public static final SimpleDateFormat SDF_DATE_NO_YEAR = new SimpleDateFormat(
            "MM-dd HH:mm", Locale.CHINA);
    public static final SimpleDateFormat SDF_DATE_NO_YEAR_NO_TIME = new SimpleDateFormat(
            "MM-dd", Locale.CHINA);
    public static final SimpleDateFormat SDF_FILE = new SimpleDateFormat(
            "yyyyMMdd_HHmmss", Locale.CHINA);
    public static final String DEF_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEF_DATE_FORMAT_NEW = "yyyy-MM-dd HH:mm";
    public static final String DEF_DATE_YMD_FORMAT = "yyyy-MM-dd";
    public static final String DEF_DATE_TIME_FORMAT = "HH:mm:ss";
    public static String getSrcOfDate(Date date, String reg) {
        SimpleDateFormat formatter = new SimpleDateFormat(reg);
        if (date == null) {
            return null;
        }
        return formatter.format(date);
    }
    /**
     * 输出日期文字
     * @param date
     */
    public static String date2Str(Date date) {
        return SDF.format(date);
    }

    /**
     * 输出日期文字
     * @param time 时间戳
     */
    public static String date2Str(long time) {
        return SDF.format(new Date(time));
    }

    /**
     * 自定义输出格式
     * @param date
     * @param format 输出格式
     */
    public static String date2Str(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return dateFormat.format(date);
    }

    /**
     * 根据文本转换日期
     * @param str 格式：yyyy-MM-dd HH:mm
     */
    public static Date str2Date(String str) {
        try {
            return SDF.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 根据自定义文本转换日期
     * @param dateStr
     *
     */
    public static Date str2Date(String dateStr, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 判断2个日期是否是同一周
     */
    public static boolean isSameWeek(Calendar cal1, Calendar cal2) {
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        // subYear==0,说明是同一年
        if (subYear == 0) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
                    .get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        // 例子:cal1是"2005-1-1"，cal2是"2004-12-25"
        // java对"2004-12-25"处理成第52周
        // "2004-12-26"它处理成了第1周，和"2005-1-1"相同了
        // 大家可以查一下自己的日历
        // 处理的比较好
        // 说明:java的一月用"0"标识，那么12月用"11"
        else if (subYear == 1 && cal2.get(Calendar.MONTH) == 11) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
                    .get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        // 例子:cal1是"2004-12-31"，cal2是"2005-1-1"
        else if (subYear == -1 && cal1.get(Calendar.MONTH) == 11) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
                    .get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }

    /**
     * 判断2个日期是否是同一周
     */
    public static boolean isSameWeek(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return isSameWeek(cal1, cal2);
    }

    /**
     * 获得cal的星期
     * @param cal
     */
    public static String getWeek(Calendar cal) {
        String week = "周";
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                week += "日";
                break;
            case 2:
                week += "一";
                break;
            case 3:
                week += "二";
                break;
            case 4:
                week += "三";
                break;
            case 5:
                week += "四";
                break;
            case 6:
                week += "五";
                break;
            case 7:
                week += "六";
                break;
        }
        return week;
    }

    /**
     * 是否2个日期是否同一年
     */
    public static boolean isSameDate(Date date1, Date date2){
        String d1 = SDF_DATE.format(date1);
        String d2 = SDF_DATE.format(date2);
        return d1.equals(d2);
    }

    /**
     * 是否2个日期是否同一年
     */
    public static boolean isSameYear(Date date1, Date date2){
        int d1 = date1.getYear();
        int d2 = date2.getYear();
        return d1 == d2;
    }
}