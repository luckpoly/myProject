package com.brandsh.tiaoshi.myRequestCallBack;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * Created by XianXianGe on 2016/1/7.
 */
public class MyRequestCallBack extends RequestCallBack<String> {
    private int what;
    private Context context;
    private Object object;
    private Handler handler;

    public MyRequestCallBack(int what, Context context, Object object, Handler handler) {
        this.what = what;
        this.context = context;
        this.object = object;
        this.handler = handler;
    }

    @Override
    public void onSuccess(ResponseInfo<String> responseInfo) {
        Log.e("result", responseInfo.result);
        try {
            object = new Gson().fromJson(responseInfo.result, object.getClass());
            Message message = handler.obtainMessage();
            message.obj = object;
            message.what = what;
            handler.sendMessage(message);
        }catch (Exception e){
            Toast.makeText(context, "网络繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessage(300);
        }
    }

    @Override
    public void onFailure(HttpException e, String s) {
        Toast.makeText(context, "网络异常，请检查手机网络", Toast.LENGTH_SHORT).show();
        Log.e("exception", e.getMessage());
        handler.sendEmptyMessage(200);
    }
}
