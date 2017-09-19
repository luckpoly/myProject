package com.brandsh.tiaoshi.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.activity.UserAgreementActivity;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.LoginJsonData;
import com.brandsh.tiaoshi.model.RegisterJsonData;
import com.brandsh.tiaoshi.model.SMSCodejsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.REAide;
import com.brandsh.tiaoshi.utils.SPUtil;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by libokang on 15/10/9.
 */
public class RegisterFragment extends BaseFragment implements View.OnClickListener {


    private static final int ASYNC_GET_CODE = 0;
    private static final int ASYNC_REGIEST = 1;
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.register_phone)
    private EditText register_et_phone;
    @ViewInject(R.id.register_btn_code)
    private Button register_btn_get_code;
    @ViewInject(R.id.register_et_code)
    private EditText register_et_code;
    @ViewInject(R.id.register_et_pwd)
    private EditText register_et_pwd;
    @ViewInject(R.id.register_btn_reg)
    private Button register_btn_reg;
    @ViewInject(R.id.register_ivAgree)
    private ImageView register_ivAgree;
    @ViewInject(R.id.register_tvAgreement)
    private TextView register_tvAgreement;
    @ViewInject(R.id.register_ll)
    private LinearLayout register_ll;


    private String relCode;
    private HttpUtils httpUtils;
    private HashMap<String,String> smsCodeRequestMap;
    private HashMap<String,String> submitRequestMap;
    private boolean isAgreed=false;
    private String uuid;
    private int time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, null);
        ViewUtils.inject(this, view);
        init();
        initListener();
        return view;
    }

    private void init() {
        httpUtils = TiaoshiApplication.getGlobalHttpUtils();
        nav_title.setText("注册");
        nav_back.setVisibility(View.VISIBLE);
        smsCodeRequestMap=new HashMap<>();
        submitRequestMap=new HashMap<>();
        time = 60;
        register_tvAgreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        if (isAgreed) {
            register_btn_reg.setBackgroundResource(R.drawable.register_submit_btn1);
            isAgreed = false;
            register_ivAgree.setImageResource(R.mipmap.unselect);
        } else {
            register_btn_reg.setBackgroundResource(R.drawable.register_submit_btn);
            isAgreed = true;
            register_ivAgree.setImageResource(R.mipmap.select);
        }
    }

    private void initListener() {
        register_btn_get_code.setOnClickListener(this);
        register_btn_reg.setOnClickListener(this);
        register_ll.setOnClickListener(this);
        register_tvAgreement.setOnClickListener(this);
        register_tvAgreement.setOnClickListener(this);
        nav_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.register_btn_code:
                if(time == 60){
                    getYZCode();
                }else {
                    return;
                }
                break;

            case R.id.register_btn_reg:
                if (isAgreed) {
                    register();
                } else {
                    return;
                }
                break;

            case R.id.register_ll:
                if (isAgreed) {
                    register_btn_reg.setBackgroundResource(R.drawable.register_submit_btn1);
                    isAgreed = false;
                    register_ivAgree.setImageResource(R.mipmap.unselect);
                } else {
                    register_btn_reg.setBackgroundResource(R.drawable.register_submit_btn);
                    isAgreed = true;
                    register_ivAgree.setImageResource(R.mipmap.select);
                }
                break;
            case R.id.register_tvAgreement:
                Intent intent = new Intent(getActivity(), UserAgreementActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_back:
                getActivity().finish();
                break;

        }
    }

    private void register() {
        if (checkValid()) {
            submitRequestMap.clear();
            submitRequestMap.put("username", register_et_phone.getText().toString());
            submitRequestMap.put("verifyCode", register_et_code.getText().toString());
            submitRequestMap.put("password", register_et_pwd.getText().toString());
            submitRequestMap.put("uuid", uuid);
            submitRequestMap.put("actReq",SignUtil.getRandom());
            submitRequestMap.put("actTime",System.currentTimeMillis()/1000+"");
            String sign= SignUtil.getSign(submitRequestMap);
            submitRequestMap.put("sign", Md5.toMd5(sign));
            OkHttpManager.postAsync(G.Host.REGISTER, submitRequestMap, new MyCallBack(2, getActivity(), new RegisterJsonData(), handler));
        }
    }

    private boolean checkValid() {

        if (TextUtils.isEmpty(register_et_phone.getText().toString())) {
            showToast("手机号不能为空");
            return false;
        }


        if (TextUtils.isEmpty(register_et_pwd.getText().toString())) {
            showToast("密码不能为空");
            return false;
        }else if(register_et_pwd.getText().toString().length()<6||register_et_pwd.getText().toString().length()>16){
            showToast("密码只支持6～16位的数字或英文");
            return false;
        }

        String code = register_et_code.getText().toString();

        if (TextUtils.isEmpty(code)) {
            showToast("请输入验证码");
            return false;
        }

        if(uuid == null){
            showToast("请先获取验证码");
            return false;
        }

        return true;
    }

    private void getYZCode() {
        String phone = register_et_phone.getText().toString();

        //验证合法性
        if (!REAide.checkPhoneNumValide(phone)) {
            showToast("请输入正确的手机号码");
            return;
        }

        handler.sendEmptyMessageDelayed(10, 1000);
        register_btn_get_code.setBackgroundResource(R.drawable.btn_code1);
        smsCodeRequestMap.clear();
        smsCodeRequestMap.put("tel", phone);
        smsCodeRequestMap.put("actReq",SignUtil.getRandom());
        smsCodeRequestMap.put("actTime", System.currentTimeMillis()/1000+"");
        String sign= SignUtil.getSign(smsCodeRequestMap);
        smsCodeRequestMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync( G.Host.GET_CODE,smsCodeRequestMap,new MyCallBack(1, getActivity(), new SMSCodejsonData(), handler));
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    SMSCodejsonData smsCodejsonData = (SMSCodejsonData) msg.obj;
                    if (smsCodejsonData.getRespCode() .equals("SUCCESS") ) {
                        Toast.makeText(getActivity(), "验证码发送成功", Toast.LENGTH_SHORT).show();
                        uuid = smsCodejsonData.getData().getUuid();
                    } else {
                        Toast.makeText(getActivity(), smsCodejsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    RegisterJsonData registerJsonData = (RegisterJsonData) msg.obj;
                    if(registerJsonData.getRespCode().equals("SUCCESS")){
                       HashMap loginRequestMap = new HashMap();
                        loginRequestMap.put("username", register_et_phone.getText().toString());
                        loginRequestMap.put("password", register_et_pwd.getText().toString());
                        loginRequestMap.put("actReq", SignUtil.getRandom());
                        loginRequestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
                        String sign = SignUtil.getSign(loginRequestMap);
                        loginRequestMap.put("sign", Md5.toMd5(sign));
                        OkHttpManager.postAsync(G.Host.LOGIN, loginRequestMap, new MyCallBack(3, getActivity(), new LoginJsonData(), handler));
//                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    }else {
                        Toast.makeText(getActivity(), registerJsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 3:
                    LoginJsonData loginJsonData = (LoginJsonData) msg.obj;
                    if (loginJsonData.getRespCode().equals("SUCCESS")) {
                        //本地保存用户名和密码
                        SPUtil.setSP(G.SP.LOGIN_NAME, register_et_phone.getText().toString());
                        SPUtil.setSP(G.SP.LOGIN_PWD, register_et_pwd.getText().toString());
                        SPUtil.setSP("userId", loginJsonData.getData().getUserId()+"");
                        SPUtil.setSP("token", loginJsonData.getData().getAccessToken());
                        SPUtil.setSP("tokenTime", loginJsonData.getData().getAccessOverTime());
                        SPUtil.setSP("refreshToken", loginJsonData.getData().getRefreshToken());
                        SPUtil.setSP("nickName", loginJsonData.getData().getNickName());
                        TiaoshiApplication.globalUserId = loginJsonData.getData().getUserId()+"";
                        TiaoshiApplication.globalToken = loginJsonData.getData().getAccessToken();
                        TiaoshiApplication.isLogin = true;
                        TiaoshiApplication.phone = register_et_phone.getText().toString();
                        jPush();
                        showToast("注册成功！");
                        //发送广播，通知我的界面更改UI
                        Intent intent = new Intent("com.brandsh.tiaoshi.MyFragment");
                        getActivity().sendBroadcast(intent);
                        //返回首页
                        getActivity().setResult(Activity.RESULT_OK);
                        getActivity().finish();
                    } else {
                        showToast(loginJsonData.getRespMsg());
                    }
                    break;
                case 10:
                    --time;
                    if(time>=0){
                        handler.sendEmptyMessageDelayed(10,1000);
                        register_btn_get_code.setText("验证码\n(" + time + "秒)");
                    }else {
                        time = 60;
                        register_btn_get_code.setText("验证码\n(60秒)");
                        register_btn_get_code.setBackgroundResource(R.drawable.btn_code);
                    }
                    break;
            }
        }
    };
    private void jPush() {
        JPushInterface.setAlias(getContext(), "USER_" + TiaoshiApplication.globalUserId + "", new TagAliasCallback() {
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
