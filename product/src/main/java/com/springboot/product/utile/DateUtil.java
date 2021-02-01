package com.springboot.product.utile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtil {

    /**
     * yyyy-MM-dd
     */
    public static final String FORMAT_YMD_1 = "yyyy-MM-dd";

    /**
     * yyyy/MM/dd
     */
    public static final String FORMAT_YMD_2 = "yyyy/MM/dd";

    /**
     * yyyyMMdd
     */
    public static final String FORMAT_YMD_3 = "yyyyMMdd";

    /**
     * yy/MM/dd
     */
    public static final String FORMAT_YMD_4 = "yy/MM/dd";

    /**
     * yyyy/M/d
     */
    public static final String FORMAT_YMD_5 = "yyyy/M/d";

    /**
     * yyMMdd
     */
    public static final String FORMAT_YMD_6 = "yyMMdd";

    /**
     * yyyy.MM.dd
     */
    public static final String FORMAT_YMD_7 = "yyyy.MM.dd";

    /**
     * yyyyMM
     */
    public static final String FORMAT_YM_1 = "yyyyMM";

    /**
     * yyyy/MM
     */
    public static final String FORMAT_YM_2 = "yyyy/MM";

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String FORMAT_DATE_TIME_1 = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy/MM/dd HH:mm:ss
     */
    public static final String FORMAT_DATE_TIME_2 = "yyyy/MM/dd HH:mm:ss";

    /**
     * yyyyMMddHHmmss
     */
    public static final String FORMAT_DATE_TIME_3 = "yyyyMMddHHmmss";

    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String FORMAT_DATE_TIME_4 = "yyyy-MM-dd HH:mm";

    /**
     * yyyy/MM/dd HH:mm
     */
    public static final String FORMAT_DATE_TIME_5 = "yyyy/MM/dd HH:mm";

    /**
     * yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String FORMAT_DATE_TIME_6 = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * HHmmss
     */
    public static final String FORMAT_DATE_TIME_7 = "HHmmss";

    /**
     * yyyyMMddHHmmss_SSS
     */
    public static final String FORMAT_DATE_TIME_8 = "yyyyMMddHHmmss_SSS";

    /**
     * yyyyMMdd_HHmmssSSS
     */
    public static final String FORMAT_DATE_TIME_9 = "yyyyMMdd_HHmmssSSS";

    /**
     * yyyyMMddHHmmssSSS
     */
    public static final String FORMAT_DATE_TIME_10 = "yyyyMMddHHmmssSSS";

    /**
     * HH:mm
     */
    public static final String FORMAT_DATE_TIME_11 = "HH:mm";

    /**
     * HH:mm:ss
     */
    public static final String FORMAT_DATE_TIME_12 = "HH:mm:ss";

    /**
     * dd/MM/yyyy HH:mm:ss
     */
    public static final String FORMAT_DATE_TIME_13 = "dd/MM/yyyy HH:mm:ss";

    /**
     * 获取当前时间
     *
     * @return 时间
     */
    public static String nowDateStr() {
        return nowDateStr(null);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String nowDateStr(String format) {

        if (StringUtil.isEmpty(format)) {
            format = FORMAT_YMD_6;
        }
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateNowStr = sdf.format(d);
        log("格式化后的日期：" + dateNowStr);
        return dateNowStr;
    }

    /**
     * 两个时间相差距离多少秒
     *
     * @param str1
     * @param str2
     * @return
     */
    public static long getDistanceTimes(String str1, String str2) throws Exception {
        DateFormat df = new SimpleDateFormat(FORMAT_DATE_TIME_1);
        Date one;
        Date two;
        long diff = 0;
        one = df.parse(str1);
        two = df.parse(str2);
        long time1 = one.getTime();
        long time2 = two.getTime();

        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        return diff / 1000;
    }

    private static void log(String var) {
        System.out.println(var);
    }

    public static void main(String[] args) {
        String date = nowDateStr();
        log(date);
    }
}
