package com.goodfood86.tiaoshi.order121Project.myRequestCallBack;

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
 * Created by XianXianGe on 2016/1/25.
 */
public class MyRequestCallBack extends RequestCallBack<String> {
    private Context context;
    private Handler handler;
    private int what;
    private Object object;

    public MyRequestCallBack(Context context, Handler handler, int what, Object object) {
        this.context = context;
        this.handler = handler;
        this.what = what;
        this.object = object;
    }


    @Override
    public void onSuccess(ResponseInfo<String> responseInfo) {
        Log.e("result", responseInfo.result);
        try {
            Object obj = new Gson().fromJson(responseInfo.result, object.getClass());
            Message message = handler.obtainMessage();
            message.obj = obj;
            message.what = what;
            handler.sendMessage(message);
        }catch (Exception e){
            Log.e("Exception",e.toString());
            handler.sendEmptyMessage(300);
            Toast.makeText(context, "网络繁忙,请稍后再试", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onFailure(HttpException e, String s) {
        Log.e("---",s);
        handler.sendEmptyMessage(200);
        Toast.makeText(context, "网络异常,请检查手机网络后再试", Toast.LENGTH_SHORT).show();

    }
}
