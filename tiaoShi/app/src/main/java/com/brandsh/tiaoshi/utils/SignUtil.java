package com.brandsh.tiaoshi.utils;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.brandsh.tiaoshi.wxapi.Constants;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by Administrator on 2016/5/13.
 */
public class SignUtil {
    String key="tiao!Q@W#E$R12&*()shi";
    private static SignUtil signUtil;
    private SignUtil(){

    }
    public static SignUtil getInstance() {

        if (signUtil == null) {
            signUtil = new SignUtil();
        }
        return signUtil;
    }
    public static String getArrayToString(HashMap<String, String> params){
        StringBuilder builder = new StringBuilder("");
        Set<Map.Entry<String, String>> set = params.entrySet();
        builder.append("[{");
        for (Map.Entry<String, String> entry : set) {
            builder.append(entry.getKey());
            builder.append(":");
            builder.append(entry.getValue());
            builder.append(",");
        }
        builder = builder.replace(builder.lastIndexOf(","), builder.length() + 1, "");
        builder.append("}]");
        return builder.toString();
    }
    public static String getArrayToString1(List<Map<String,String>> list){
        StringBuilder builder = new StringBuilder("");
        builder.append("[");
        for (int i=0;i<list.size();i++){
            HashMap<String,String> params=(HashMap)list.get(i);
            Set<Map.Entry<String, String>> set = params.entrySet();
            builder.append("{");
            int a=0;
            for (Map.Entry<String, String> entry : set) {
                builder.append("\""+entry.getKey()+"\"");
                builder.append(":");
                builder.append("\""+entry.getValue()+"\"");
//                if (a==0)
                builder.append(",");
                a++;
            }
            builder= new StringBuilder(builder.subSequence(0,builder.length()-1));
            builder.append("},");
        }
        builder = builder.replace(builder.lastIndexOf(","), builder.length() + 1, "");
        builder.append("]");
        return builder.toString();
    }
    public static String getSign(HashMap<String,String> hashMap){
        signUtil=SignUtil.getInstance();
        String sign=signUtil.sort(signUtil.getUrl(hashMap))+signUtil.key;
        return sign;
    }
    public static String getWeixinSign(HashMap<String,String> hashMap){
        signUtil=SignUtil.getInstance();
        String sign=signUtil.sort(signUtil.getUrl(hashMap))+"&key="+ Constants.API_KEY;
        return sign;
    }

    private String  sort(String a){
        String [] strs=a.split("&");
        String b="";
        for (int i = 0; i < strs.length-1; i++) {
            for (int j =i+1; j < strs.length; j++) {
//                int intTemp = strs[i].compareToIgnoreCase(strs[j]);
                int intTemp = strs[i].compareTo(strs[j]);
                String strTemp = "";
                if(intTemp>0){
                    strTemp = strs[j];
                    strs[j] = strs[i];
                    strs[i] = strTemp;
                }
            }
        }
        for (int i = 0; i < strs.length; i++) {
            b=b+strs[i]+"&";
        }
        return b.substring(0,b.length()-1);
    }
    private String getUrl(HashMap<String, String> params) {
        StringBuilder builder = new StringBuilder("");
        Set<Map.Entry<String, String>> set = params.entrySet();
        for (Map.Entry<String, String> entry : set) {
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue());
            builder.append("&");
        }
        builder = builder.replace(builder.lastIndexOf("&"), builder.length() + 1, "");
        return builder.toString();
    }

    /**
     * 获取6位随机数
     * @return
     */
    public  static  String getRandom(){
        Random random = new Random();
        int x = random.nextInt(899999);
        return  x+100000+"";
    }
    //base64加密
    public static String base64JaMi(String str){
        if (TextUtils.isEmpty(str)){
            return str;
        }
        return Base64.encodeToString(str.getBytes(),Base64.DEFAULT);
    }
    //base64解密
    public static String base64JieMi(String str){
        if (TextUtils.isEmpty(str)){
            return str;
        }
        return new String(Base64.decode(str.getBytes(),Base64.DEFAULT));
    }


}
