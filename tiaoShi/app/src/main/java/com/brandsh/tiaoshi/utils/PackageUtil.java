package com.brandsh.tiaoshi.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by XianXianGe on 2016/1/5.
 */
public class PackageUtil {
    public static String getPackageVersion(Context context){
        String version = "1.0";
        //获取PackageManager
        PackageManager manager = context.getPackageManager();

        try {
            //获取PackageInfo
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),PackageManager.GET_ACTIVITIES);

            //获取当前app版本号
            version = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return  version;
    }
}
