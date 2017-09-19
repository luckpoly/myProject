package com.brandsh.tiaoshi.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.activity.FCActivity;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.LoginJsonData;
import com.brandsh.tiaoshi.model.UpdateVersionModel;
import com.brandsh.tiaoshi.model.WXLogInModel;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.LogUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SPUtil;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.wxapi.WXEntryActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by libokang on 15/10/8.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private static final int REQUEST_FROM_REG = 0;
    private static final int REQUEST_FROM_FORGET_PWD = 1;
    private static final int ASYNC_LOGIN = 0;
    @ViewInject(R.id.login_phone)
    private EditText et_phone;
    @ViewInject(R.id.login_pwd)
    private EditText et_pwd;
    @ViewInject(R.id.login_login)
    private Button btn_login;
    @ViewInject(R.id.login_register)
    private Button btn_inter_reg;
    @ViewInject(R.id.login_forgetpwd)
    private Button btn_inter_forgetpwd;
    @ViewInject(R.id.tv_qqlogin)
    private ImageView tv_qqlogin;
    @ViewInject(R.id.tv_wx_login)
    private ImageView tv_wx_login;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.nav_title)
    private TextView nav_title;

    private HttpUtils httpUtils;
    private HashMap loginRequestMap;
    private UpdateVersionModel updateVersionModel;
    Tencent mTencent;
    BaseUiListener baseUiListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, null);
        ViewUtils.inject(this, view);
        init();
        initListener();
        return view;
    }


    private void init() {
        nav_back.setVisibility(View.VISIBLE);
        nav_title.setText("密码登录");
         baseUiListener=new BaseUiListener();
         mTencent = Tencent.createInstance("1105155505", getActivity().getApplicationContext());
        httpUtils = TiaoshiApplication.getGlobalHttpUtils();
        loginRequestMap = new HashMap();
    }

    private void initListener() {
        nav_back.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_inter_reg.setOnClickListener(this);
        btn_inter_forgetpwd.setOnClickListener(this);
        tv_qqlogin.setOnClickListener(this);
        tv_wx_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_back:
                getActivity().finish();
                break;
            case R.id.login_login:
                if (checkValid()) {
                    login();
                }
                break;
            case R.id.login_register:
                startActivityForResult(FCActivity.getFCActivityIntent(getActivity(), RegisterFragment.class), REQUEST_FROM_REG);
                break;
            case R.id.login_forgetpwd:
                startActivityForResult(FCActivity.getFCActivityIntent(getActivity(), ForgetpwdFragment.class), REQUEST_FROM_FORGET_PWD);
                break;
            case  R.id.tv_qqlogin:
                qqlogin();
                break;
            case  R.id.tv_wx_login:
                startActivity(new Intent(getActivity(), WXEntryActivity.class));
                LogUtil.e("登录微信");
                break;

        }
    }

    private void login() {
        final String phone = et_phone.getText().toString();
        final String pwd = et_pwd.getText().toString();
        loginRequestMap.clear();
        loginRequestMap.put("username", phone);
        loginRequestMap.put("password", pwd);
        loginRequestMap.put("terminal", AppUtil.getIMEI(getActivity()));
        loginRequestMap.put("actReq", SignUtil.getRandom());
        loginRequestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(loginRequestMap);
        loginRequestMap.put("sign", Md5.toMd5(sign));
        LogUtil.e(sign);
        OkHttpManager.postAsync(G.Host.ACCOUNT_LOGIN, loginRequestMap, new MyCallBack(1, getActivity(), new LoginJsonData(), handler));
    }


    protected boolean checkValid() {
        final String phone = et_phone.getText().toString();
        final String pwd = et_pwd.getText().toString();
        if (phone.length() != 11) {
            showToast("请输入正确的手机号码");
            return false;
        } else if (TextUtils.isEmpty(pwd)) {
            showToast("登陆密码不能为空");
            return false;
        }
        return true;
    }

    private void jPush() {
        JPushInterface.setAlias(getContext(), G.SP.URL_TYPE+"_USER_" + TiaoshiApplication.globalUserId + "", new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.e("i", i + "");
                if (i == 6002) {
                    jPush();
                }
            }
        });
    }
    Dialog dialog2;
    private Handler handler = new Handler() {
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
                        showToast("登录成功！");
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

            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (TiaoshiApplication.isLogin){
            getActivity().finish();
        }
    }
    class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            LogUtil.e(o.toString());
            try {
                JSONObject object=new JSONObject(o.toString());
                String openId=object.getString("openid");
                LogUtil.e(openId);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    }
    public void qqlogin()
    {
        if (!mTencent.isSessionValid())
        {
            mTencent.login(this, "", baseUiListener);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode,resultCode,data,baseUiListener);
    }
}
