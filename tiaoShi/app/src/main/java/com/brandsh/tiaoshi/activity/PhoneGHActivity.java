package com.brandsh.tiaoshi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import com.brandsh.tiaoshi.utils.SignUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/1/12.
 */

public class PhoneGHActivity extends BaseActivity implements View.OnClickListener {
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
    private String uuid;
    private int time = 60;
    private int FROM=1000;

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
        nav_title.setText("更换手机号码");
        et_phone.setEnabled(false);
        et_phone.setText(TiaoshiApplication.phone.substring(0,3)+"****"+TiaoshiApplication.phone.substring(7,11));
    }

    private void initListener() {
        btn_login.setOnClickListener(this);
        register_btn_get_code.setOnClickListener(this);
        nav_back.setVisibility(View.VISIBLE);
        nav_back.setOnClickListener(this);
    }

    private void getYZCode() {
        String phone = TiaoshiApplication.phone;
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
                    if ("SUCCESS".equals(loginJsonData.getRespCode())) {
                        LogUtil.e("验证成功");
                        startActivityForResult(new Intent(PhoneGHActivity.this,BondPhoneActivity.class),FROM);
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
        loginRequestMap.put("code", pwd);
        loginRequestMap.put("uuid", uuid);
        loginRequestMap.put("terminal", TiaoshiApplication.AppIMEI);
        loginRequestMap.put("actReq", SignUtil.getRandom());
        loginRequestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(loginRequestMap);
        loginRequestMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.VER_SMS_CODE, loginRequestMap, new MyCallBack(1, this, new LoginJsonData(), handler));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==FROM){
            if (resultCode== Activity.RESULT_OK){
                finish();
            }
        }
    }
}
