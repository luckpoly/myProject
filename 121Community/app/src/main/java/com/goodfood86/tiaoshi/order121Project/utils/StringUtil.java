package com.goodfood86.tiaoshi.order121Project.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.goodfood86.tiaoshi.order121Project.constant.G;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tiashiwang on 2016/4/1.
 */
public class StringUtil {
    public  static boolean IsPassword(String psw){
        if (psw.length()<6&&psw.length()>12){
            return false;
        }else {
            return true;
        }
    }
public static Boolean isOK(String startTime,String applyTime,String applyEndTime,Context context) {

   if (startTime.length()<7){
       ToastUtil.showShort(context,"请选择时间");
       return false;
   }
    if (applyTime.length()<7){
        ToastUtil.showShort(context,"请选择时间");
        return false;
    }
    if (applyEndTime.length()<7){
        ToastUtil.showShort(context,"请选择时间");
        return false;
    }

    Date date = null;
    Date date1 = null;
    Date date2 = null;
    try {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = simpleDateFormat.parse(startTime);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date1 = simpleDateFormat1.parse(applyTime);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date2 = simpleDateFormat2.parse(applyEndTime);
        Log.e("----",date.toString());
        Log.e("----",date1.toString());
        Log.e("----",date2.toString());
    } catch (ParseException e) {
        e.printStackTrace();
    }
    if (date1.getTime()>=date2.getTime()){
        ToastUtil.showShort(context,"报名截止时间需大于报名开始时间");
        return false;
    }
    if (date2.getTime()>date.getTime()){
        ToastUtil.showShort(context,"活动开始时间需大于等于报名截止时间");
        return false;
    }
    return true;
}

    //    public static void SPinput(Context context, String K, String V) {
//        SharedPreferences sp = context.getSharedPreferences(G.SP.SP_FILE_NAME, Activity.MODE_PRIVATE);
//        SharedPreferences.Editor edit = sp.edit();
//        edit.putString(K, V);
//        edit.commit();
//    }
//    public static String SPExtract(Context context, String K) {
//        SharedPreferences sp = context.getSharedPreferences(G.SP.SP_FILE_NAME, Activity.MODE_PRIVATE);
//        return sp.getString(K, "0");
//    }


}