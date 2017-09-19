package com.goodfood86.tiaoshi.order121Project.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;


public class StartActivity extends InstrumentedActivity {

    private SharedPreferences sp;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                //进入引导页
                case 0:
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean(G.SP.IS_FIRST_IN, false);
                    editor.commit();
                    startActivity(new Intent(StartActivity.this, GuideActivity.class));
                    Order121Application.getInstance().addActivity(StartActivity.this);
                    finish();
                    break;
                //进入首页
                case 1:
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    Order121Application.getInstance().addActivity(StartActivity.this);
                    finish();
                    break;

                default:
                    break;

            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /**
                     * 是否第一次进入: 是 -> 引导页 GuideActivity 否 -> 主 页
                     */
                    sp = StartActivity.this.getSharedPreferences(G.SP.APP_NAME, Context.MODE_PRIVATE);
                    boolean isFirstIn = sp.getBoolean(G.SP.IS_FIRST_IN, true);

                    Thread.sleep(1500);

                    if (isFirstIn) {
                        // 进入引导页
                        handler.sendEmptyMessage(0);
                    } else {
                        // 进入首页
                        handler.sendEmptyMessage(1);
                    }

                } catch (Exception e) {
                }
            }
        }).start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        //友盟统计
//       //已删

    }
    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        //友盟统计
//        //已删
    }

}
