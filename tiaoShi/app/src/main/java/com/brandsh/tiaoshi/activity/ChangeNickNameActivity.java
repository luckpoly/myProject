package com.brandsh.tiaoshi.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.ChangeUserInfoData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

public class ChangeNickNameActivity extends FragmentActivity implements View.OnClickListener {
    @ViewInject(R.id.change_nickname_rlBack)
    RelativeLayout change_nickname_rlBack;
    @ViewInject(R.id.change_nickname_tvSure)
    TextView change_nickname_tvSure;
    @ViewInject(R.id.change_nickname_etNickName)
    EditText change_nickname_etNickName;


    private String currentName;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_nick_name);
        //沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);

        init();
    }


    private void init() {
        intent = getIntent();

        change_nickname_etNickName.setText(TiaoshiApplication.globalUserInfo.getData().getNickName());
        change_nickname_rlBack.setOnClickListener(this);
        change_nickname_tvSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_nickname_rlBack:
                finish();
                break;
            case R.id.change_nickname_tvSure:
                if (!TextUtils.isEmpty(change_nickname_etNickName.getText().toString())) {
                    currentName = change_nickname_etNickName.getText().toString();
                    HashMap requestMap = new HashMap<>();
                    requestMap.put("token", TiaoshiApplication.globalToken);
                    requestMap.put("nickName", change_nickname_etNickName.getText().toString());
                    requestMap.put("actReq", SignUtil.getRandom());
                    requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
                    String sign = SignUtil.getSign(requestMap);
                    requestMap.put("sign", Md5.toMd5(sign));
                    OkHttpManager.postAsync(G.Host.CHANGE_USERINFO, requestMap, new MyCallBack(1, ChangeNickNameActivity.this, new ChangeUserInfoData(), handler));
                }
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    ChangeUserInfoData changeNickNameJsonData = (ChangeUserInfoData) msg.obj;
                    if (changeNickNameJsonData != null) {
                        if (changeNickNameJsonData.getRespCode().equals("SUCCESS")) {
                            TiaoshiApplication.globalUserInfo.getData().setNickName(currentName);
                            Log.e("currentName", TiaoshiApplication.globalUserInfo.getData().getNickName());
                            Intent intent = new Intent("updateNickName");
                            sendBroadcast(intent);
                            Intent intent1 = new Intent("updateNickName1");
                            sendBroadcast(intent1);
                            AlertDialog.Builder builder = new AlertDialog.Builder(ChangeNickNameActivity.this);
                            SharedPreferences.Editor editor = getSharedPreferences(G.SP.APP_NAME, MODE_PRIVATE).edit();
                            editor.putString("nickName", currentName).commit();
                            builder.setTitle("修改成功").setMessage("昵称修改成功").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).create().show();
                        } else {
                            Toast.makeText(ChangeNickNameActivity.this, changeNickNameJsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        }
    };

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}

