package com.brandsh.tiaoshi.utils;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by Administrator on 2016/12/2.
 */

public class DateFormatUtil {
    /**
     * 时间转换 时间戳——时间
     * @param str
     * @return
     */
    public static String formatDate(String str){
        long seconds = Long.parseLong(str);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(seconds * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(gc.getTime());
    }
    public static String formatDateNohh(String str){
        long seconds = Long.parseLong(str);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(seconds * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(gc.getTime());
    }
}
