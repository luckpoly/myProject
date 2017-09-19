package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.utils.DataCleanManager;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/7/29.
 */
public class SettingActivity extends Activity implements View.OnClickListener{
    @ViewInject(R.id.rl_clearcache)
    private RelativeLayout rl_clearcache;
    @ViewInject(R.id.rl_aboutus)
    private RelativeLayout rl_aboutus;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ViewUtils.inject(this);
        initView();
    }

    private void initView(){
        rl_clearcache.setOnClickListener(this);
        rl_aboutus.setOnClickListener(this);
        nav_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_clearcache:
                showClean();
                break;
            case R.id.rl_aboutus:
                startActivity(new Intent(SettingActivity.this,AboutUsActivity.class));
                break;
            case R.id.nav_back:
                finish();
                break;
        }
    }
    private void showClean(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示:").setMessage("确定要清除所有缓存？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DataCleanManager.cleanApplicationData(SettingActivity.this);
                Order121Application.globalLoginModel=null;
                SharedPreferences.Editor editor =getSharedPreferences(G.SP.APP_NAME, Context.MODE_PRIVATE).edit();
                editor.putString(G.SP.LOGIN_NAME, null);
                editor.putString(G.SP.LOGIN_PWD, null);
                editor.commit();
                sendBroadcast(new Intent("logOut"));
                ToastUtil.showShort(SettingActivity.this,"操作成功");
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
}
