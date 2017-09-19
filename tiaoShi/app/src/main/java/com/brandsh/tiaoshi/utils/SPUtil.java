package com.brandsh.tiaoshi.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;

/**
 * Created by Administrator on 2017/1/4.
 */

public class SPUtil {
  static   SharedPreferences sp;
    public static void init(Context context){
        sp = context.getSharedPreferences(G.SP.APP_NAME, Context.MODE_PRIVATE);
    }
    /**
     * sp存储
     * @param name 名字
     * @param data  内容
     */
    public static void setSP( String name,String data){
        sp.edit().putString(name, SignUtil.base64JaMi(data)).commit();
    }
    public static String getSP(String name,String dft){
       return SignUtil.base64JieMi(sp.getString(name,dft));
    }
    public static void clearSP(){
        sp.edit().clear().commit();
    }

    //检测定位地址是否是开通城市
    public  static boolean isKtCity(String city){
        if (TiaoshiApplication.cityModel==null||TiaoshiApplication.cityModel.getData().size()==0){
            return false;
        }
        for (int i = 0; i < TiaoshiApplication.cityModel.getData().size(); i++) {
            if (city.equals(TiaoshiApplication.cityModel.getData().get(i).getName())
                    &&TiaoshiApplication.cityModel.getData().get(i).getOpenStatus().equals("YES")){
                    return true;
            }
        }
        return false;
    }

}
