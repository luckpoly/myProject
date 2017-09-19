package com.goodfood86.tiaoshi.order121Project.utils;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by Administrator on 2016/5/13.
 */
public class SignUtil {
    String key="Qsau*J#HD)&IDKCommunity";
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
        String sign=signUtil.sort(signUtil.getUrl(hashMap))+"&key="+signUtil.key;
        return sign;
    }
    public static String getWeixinSign(HashMap<String,String> hashMap){
        signUtil=SignUtil.getInstance();
        String sign=signUtil.sort(signUtil.getUrl(hashMap))+"&key="+"Qsau4QjL9BbpW9NNeKn2cHFWrg2nsEEx";
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
    //融云算法加密
    public static String getRyUrl(String appKey,String Nonce,String Timestamp){
        signUtil=SignUtil.getInstance();
        return signUtil.sha1(appKey+Nonce+Timestamp);
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
    public  static  String getRandom(){
        Random random = new Random();
        int x = random.nextInt(899999);
        return  x+100000+"";
    }
    private  String sha1(String data){
        StringBuffer buf = new StringBuffer();
        try{
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(data.getBytes());
            byte[] bits = md.digest();
            for(int i = 0 ; i < bits.length;i++){
                int a = bits[i];
                if(a<0) a+=256;
                if(a<16) buf.append("0");
                buf.append(Integer.toHexString(a));
            }
        }catch(Exception e){

        }
        return buf.toString();
    }
}
