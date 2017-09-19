package com.goodfood86.tiaoshi.order121Project.myRequestCallBack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.goodfood86.tiaoshi.order121Project.activity.LoginActivity;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;


/**
 * Created by 习惯 on 2016/5/5.
 */
public class MyCallBack implements OkHttpManager.DataCallBack {
    private Context context;
    private Handler handler;
    private int what;
    private Object object;

    public MyCallBack(int what, Context context, Object object, Handler handler) {
        this.context = context;
        this.handler = handler;
        this.what = what;
        this.object = object;
    }

    @Override
    public void requestFailure(okhttp3.Request request, IOException e) {
        Log.e("result", request.toString());
    }

    @Override
    public void requestSuccess(String result) throws Exception {
        Log.e("result", result);
        try {
            JSONObject jsonObject = new JSONObject(result.toString());
            String uploadToken = jsonObject.getString("respCode");
            String resMsg=jsonObject.getString("respMsg");
            if ("API_FAILURE_TOKEN".equals(uploadToken)||"API_FAILURE_TOKEN_TIMEOUT".equals(uploadToken)
                    ||null==resMsg||resMsg.equals("token异常")||resMsg.equals("token超时")){
                Order121Application.globalLoginModel=null;
                context.startActivity(new Intent(context,LoginActivity.class));
            }else {
                Object obj = new Gson().fromJson(result, object.getClass());
                Message message = handler.obtainMessage();
                message.obj = obj;
                message.what = what;
                handler.sendMessage(message);
            }
        } catch (Exception e) {
            Log.e("Exception", e.toString());
            handler.sendEmptyMessage(300);
            Toast.makeText(context, "网络繁忙,请稍后再试", Toast.LENGTH_SHORT).show();

        }
    }
}
