package com.brandsh.tiaoshi.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.LoginJsonData;
import com.brandsh.tiaoshi.model.SMSCodejsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.LogUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.REAide;
import com.brandsh.tiaoshi.utils.SPUtil;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Administrator on 2017/1/12.
 */

public class BondPhoneActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.login_phone)
    private EditText et_phone;
    @ViewInject(R.id.register_btn_code)
    private Button register_btn_get_code;
    @ViewInject(R.id.login_pwd)
    private EditText et_pwd;
    @ViewInject(R.id.login_login)
    private Button btn_login;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.tv_go_xy)
    private TextView tv_go_xy;
    private String uuid;
    private int time = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_gh);
        ViewUtils.inject(this);
        initView();
        initListener();
        AppUtil.Setbar(this);
    }

    private void initView() {
        nav_title.setText("绑定手机号");
        btn_login.setText("确认绑定");
        tv_go_xy.setVisibility(View.GONE);
    }

    private void initListener() {
        btn_login.setOnClickListener(this);
        register_btn_get_code.setOnClickListener(this);
        nav_back.setVisibility(View.VISIBLE);
        nav_back.setOnClickListener(this);
    }

    private void getYZCode() {
        String phone = et_phone.getText().toString();

        //验证合法性
        if (!REAide.checkPhoneNumValide(phone)) {
            shortToast("请输入正确的手机号码");
            return;
        }
        handler.sendEmptyMessageDelayed(10, 1000);
        HashMap smsCodeRequestMap = new HashMap();
        smsCodeRequestMap.put("tel", phone);
        smsCodeRequestMap.put("actReq", SignUtil.getRandom());
        smsCodeRequestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(smsCodeRequestMap);
        smsCodeRequestMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.GET_CODE, smsCodeRequestMap, new MyCallBack(2, this, new SMSCodejsonData(), handler));
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    LoginJsonData loginJsonData = (LoginJsonData) msg.obj;
                    if (loginJsonData.getRespCode().equals("SUCCESS")) {
                        //本地保存用户名和密码
                        SPUtil.setSP(G.SP.LOGIN_NAME, et_phone.getText().toString());
                        SPUtil.setSP(G.SP.LOGIN_PWD, et_pwd.getText().toString());
                        SPUtil.setSP("userId", loginJsonData.getData().getUserId()+"");
                        SPUtil.setSP("token", loginJsonData.getData().getAccessToken());
                        SPUtil.setSP("tokenTime", loginJsonData.getData().getAccessOverTime());
                        SPUtil.setSP("refreshToken", loginJsonData.getData().getRefreshToken());
                        SPUtil.setSP("nickName", loginJsonData.getData().getNickName());
                        TiaoshiApplication.globalUserId = loginJsonData.getData().getUserId()+"";
                        TiaoshiApplication.globalToken = loginJsonData.getData().getAccessToken();
                        TiaoshiApplication.isLogin = true;
                        TiaoshiApplication.phone = et_phone.getText().toString();
                        jPush();
//                        //全局保存用户信息
//                        UserModel userModel = JSONObject.parseObject(commonRespInfo.data, UserModel.class);
//                        TiaoshiApplication.getInstance().globalUserModel = userModel;
                        shortToast("绑定成功！");
                        //发送广播，通知我的界面更改UI
                        Intent intent = new Intent("com.brandsh.tiaoshi.MyFragment");
                       sendBroadcast(intent);
                        AlertDialog.Builder builder = new AlertDialog.Builder(BondPhoneActivity.this);
                        builder.setCancelable(false);
                        builder.setTitle("提示：").setMessage("绑定成功").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setResult(Activity.RESULT_OK);
                                finish();
                            }
                        }).create().show();
                    } else {
                        shortToast(loginJsonData.getRespMsg());
                    }
                    break;
                case 2:
                    SMSCodejsonData smsCodejsonData = (SMSCodejsonData) msg.obj;
                    if (smsCodejsonData.getRespCode().equals("SUCCESS")) {
                        shortToast("验证码发送成功");
                        register_btn_get_code.setBackgroundResource(R.drawable.btn_code1);
                        uuid = smsCodejsonData.getData().getUuid();
                    } else {
                        shortToast(smsCodejsonData.getRespMsg());
                    }
                    break;
                case 10:
                    --time;
                    if (time >= 0) {
                        handler.sendEmptyMessageDelayed(10, 1000);
                        register_btn_get_code.setText("验证码(" + time + "秒)");
                    } else {
                        time = 60;
                        register_btn_get_code.setText("验证码(60秒)");
                        register_btn_get_code.setBackgroundResource(R.drawable.btn_code);
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login:
                if (checkValid()) {
                    isOk();
                }
                break;
            case R.id.register_btn_code:
                if (time == 60) {
                    getYZCode();
                } else {
                    return;
                }
                break;
            case R.id.nav_back:
                finish();
                break;
        }
    }

    private void isOk() {
        final String phone = et_phone.getText().toString();
        final String pwd = et_pwd.getText().toString();
        HashMap loginRequestMap = new HashMap();
        loginRequestMap.put("tel", phone);
        loginRequestMap.put("token", TiaoshiApplication.globalToken);
        loginRequestMap.put("code", pwd);
        loginRequestMap.put("uuid", uuid);
        loginRequestMap.put("terminal", TiaoshiApplication.AppIMEI);
        loginRequestMap.put("actReq", SignUtil.getRandom());
        loginRequestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(loginRequestMap);
        loginRequestMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.CHANGE_TEL, loginRequestMap, new MyCallBack(1, this, new LoginJsonData(), handler));
    }

    protected boolean checkValid() {
        final String phone = et_phone.getText().toString();
        final String pwd = et_pwd.getText().toString();
        if (phone.length() != 11) {
            shortToast("请输入正确的手机号码");
            return false;
        } else if (TextUtils.isEmpty(pwd)) {
            shortToast("验证码不能为空");
            return false;
        }
        return true;
    }
    private void jPush() {
        JPushInterface.setAlias(this, G.SP.URL_TYPE+"_USER_" + TiaoshiApplication.globalUserId + "", new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.e("i", i + "");
                if (i == 6002) {
                    jPush();
                }
            }
        });
    }
}
