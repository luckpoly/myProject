package com.brandsh.tiaoshi.myRequestCallBack;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.brandsh.tiaoshi.activity.FCActivity;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.fragment.PhoneLoginFragment;
import com.brandsh.tiaoshi.utils.LogUtil;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;

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
    public void requestFailure(Request request, IOException e) {
        Log.e("---", request.body().toString());
        handler.sendEmptyMessage(200);
        Toast.makeText(context, "网络异常,请检查手机网络后再试", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void requestSuccess(String result) throws Exception {
        LogUtil.e(result);
        try {
            JSONObject jsonObject = new JSONObject(result.toString());
            String uploadToken = jsonObject.getString("respCode");
            String resMsg=jsonObject.getString("respMsg");
            if ("API_FAILURE_TOKEN".equals(uploadToken)||"API_FAILURE_TOKEN_TIMEOUT".equals(uploadToken)
                    ||null==resMsg||resMsg.equals("token异常")||resMsg.equals("token超时")){
                TiaoshiApplication.isLogin = false;
                context.startActivity(FCActivity.getFCActivityIntent((Activity) context, PhoneLoginFragment.class));
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
