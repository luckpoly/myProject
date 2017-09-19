package com.goodfood86.tiaoshi.order121Project.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public class RongHttp {
    private static RongHttp rHttpUtil;
    public static RongHttp getInstance() {
        if (rHttpUtil == null) {
            rHttpUtil = new RongHttp();
        }
        return rHttpUtil;
    }
    public static void rPostHttp(String uurl, List<NameValuePair> nameValuePair, Object object, Handler handler, int what, Context context) {
        StringBuffer res = new StringBuffer();
        String url = "https://api.cn.ronghub.com/"+uurl;
        String App_Key ="e5t4ouvptesaa"; //开发者平台分配的 App Key。
        String App_Secret = "jbZDGQLLvoex";
        String Timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳，从 1970 年 1 月 1 日 0 点 0 分 0 秒开始到现在的秒数。
        String Nonce = String.valueOf(Math.floor(Math.random() * 1000000));//随机数，无长度限制。
        String Signature = sha1(App_Secret + Nonce + Timestamp);//数据签名。
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("App-Key", App_Key);
        httpPost.setHeader("Timestamp", Timestamp);
        httpPost.setHeader("Nonce", Nonce);
        httpPost.setHeader("Signature", Signature);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
//        nameValuePair.add(new BasicNameValuePair("userId",username));
        HttpResponse httpResponse = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,"utf-8"));
            httpResponse = httpClient.execute(httpPost);
            BufferedReader br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String line = null;
            while ((line = br.readLine()) != null) {
                res.append(line);
            }
            Log.e("----",res.toString());
            try {
                Object obj = new Gson().fromJson(res.toString(), object.getClass());
                Message message = handler.obtainMessage();
                message.obj = obj;
                message.what = what;
                handler.sendMessage(message);
            } catch (Exception e) {
                Log.e("Exception", e.toString());
                handler.sendEmptyMessage(300);
                Toast.makeText(context, "网络繁忙,请稍后再试", Toast.LENGTH_SHORT).show();

            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "网络繁忙,请稍后再试", Toast.LENGTH_SHORT).show();
        }
    }
    private static String sha1(String data){
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
