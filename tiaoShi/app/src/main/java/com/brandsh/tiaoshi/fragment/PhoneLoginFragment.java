package com.brandsh.tiaoshi.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.activity.FCActivity;
import com.brandsh.tiaoshi.activity.UserAgreementActivity;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.LoginJsonData;
import com.brandsh.tiaoshi.model.RefreshTokenModel;
import com.brandsh.tiaoshi.model.SMSCodejsonData;
import com.brandsh.tiaoshi.model.UpdateVersionModel;
import com.brandsh.tiaoshi.model.UserInfoJsonData;
import com.brandsh.tiaoshi.model.WXLogInModel;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.LogUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.REAide;
import com.brandsh.tiaoshi.utils.SPUtil;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.brandsh.tiaoshi.wxapi.WXEntryActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mingle.widget.ShapeLoadingDialog;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.modelmsg.SendAuth;
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
public class PhoneLoginFragment extends BaseFragment implements View.OnClickListener {

    private static final int REQUEST_FROM_REG = 0;
    private static final int REQUEST_FROM_FORGET_PWD = 1;
    private static final int MIMA_LOGIN = 2;
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
    @ViewInject(R.id.tv_go_xy)
    private TextView tv_go_xy;
    @ViewInject(R.id.nav_right_text)
    private TextView nav_right_text;
    @ViewInject(R.id.register_btn_code)
    private Button register_btn_get_code;

    private HttpUtils httpUtils;
    private HashMap loginRequestMap;

    private UpdateVersionModel updateVersionModel;
    Tencent mTencent;
    BaseUiListener baseUiListener;
    private int time=60;
    private String uuid;
    private LoginBroadcastReciver loginBroadcastReciver;
    private String openId;
    //加载动画
    private ShapeLoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phone_login_fragment, null);
        ViewUtils.inject(this, view);
        init();
        initListener();
        registerReceiver();
        return view;
    }
    private void init() {
        nav_back.setVisibility(View.VISIBLE);
        nav_title.setText("手机号登录");
        nav_right_text.setText("密码登录");
         baseUiListener=new BaseUiListener();
        mTencent=TiaoshiApplication.mTencent;
        httpUtils = TiaoshiApplication.getGlobalHttpUtils();
        loginRequestMap = new HashMap();
        //设置用户协议颜色
        String content=tv_go_xy.getText().toString();
        tv_go_xy.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableStringBuilder spannable = new SpannableStringBuilder(content);
        spannable.setSpan(new TextClick(),content.length()-8,content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_go_xy.setText(spannable);
        loginBroadcastReciver = new LoginBroadcastReciver();
        loadingDialog = ProgressHUD.show(getActivity(), "登录中，请稍候...");

    }
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("WXLoginSuccess");
        intentFilter.addAction("QQLoginSuccess");
        getActivity().registerReceiver(loginBroadcastReciver, intentFilter);
    }
    private void initListener() {
        nav_back.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_inter_reg.setOnClickListener(this);
        btn_inter_forgetpwd.setOnClickListener(this);
        tv_qqlogin.setOnClickListener(this);
        tv_wx_login.setOnClickListener(this);
        nav_right_text.setOnClickListener(this);
        register_btn_get_code.setOnClickListener(this);
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
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        tv_qqlogin.setOnClickListener(null);
                        try {
                            sleep(5000);
                            tv_qqlogin.setOnClickListener(PhoneLoginFragment.this);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            case  R.id.tv_wx_login:
                if (!TiaoshiApplication.iwxapi.isWXAppInstalled()) {
                    Toast.makeText(getActivity(), "您还未安装微信客户端",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_demo";
                TiaoshiApplication.iwxapi.sendReq(req);
                LogUtil.e("登录微信");
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        tv_wx_login.setOnClickListener(null);
                        try {
                            sleep(5000);
                            tv_wx_login.setOnClickListener(PhoneLoginFragment.this);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            case  R.id.nav_right_text:
                startActivityForResult(FCActivity.getFCActivityIntent(getActivity(), LoginFragment.class), MIMA_LOGIN);
                break;
            case R.id.register_btn_code:
                if(time == 60){
                    getYZCode();
                }else {
                    return;
                }
                break;

        }
    }
    private void getYZCode() {
        String phone = et_phone.getText().toString();

        //验证合法性
        if (!REAide.checkPhoneNumValide(phone)) {
            showToast("请输入正确的手机号码");
            return;
        }

        handler.sendEmptyMessageDelayed(10, 1000);
         HashMap smsCodeRequestMap=new HashMap();
        smsCodeRequestMap.put("tel", phone);
        smsCodeRequestMap.put("actReq",SignUtil.getRandom());
        smsCodeRequestMap.put("actTime", System.currentTimeMillis()/1000+"");
        String sign= SignUtil.getSign(smsCodeRequestMap);
        smsCodeRequestMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync( G.Host.GET_CODE,smsCodeRequestMap,new MyCallBack(2, getActivity(), new SMSCodejsonData(), handler));
    }

    private void login() {
        final String phone = et_phone.getText().toString();
        final String pwd = et_pwd.getText().toString();
        loginRequestMap.clear();
        loginRequestMap.put("tel", phone);
        loginRequestMap.put("code", pwd);
        loginRequestMap.put("uuid", uuid);
        loginRequestMap.put("terminal", AppUtil.getIMEI(getActivity()));
        loginRequestMap.put("actReq", SignUtil.getRandom());
        loginRequestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(loginRequestMap);
        loginRequestMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.PHONE_LOGIN, loginRequestMap, new MyCallBack(1, getActivity(), new LoginJsonData(), handler));
    }


    protected boolean checkValid() {
        final String phone = et_phone.getText().toString();
        final String pwd = et_pwd.getText().toString();
        if (phone.length() != 11) {
            showToast("请输入正确的手机号码");
            return false;
        } else if (TextUtils.isEmpty(pwd)) {
            showToast("验证码不能为空");
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
                case 2:
                    SMSCodejsonData smsCodejsonData = (SMSCodejsonData) msg.obj;
                    if (smsCodejsonData.getRespCode() .equals("SUCCESS") ) {
                        Toast.makeText(getActivity(), "验证码发送成功", Toast.LENGTH_SHORT).show();
                        register_btn_get_code.setBackgroundResource(R.drawable.btn_code1);
                        uuid = smsCodejsonData.getData().getUuid();
                    } else {
                        Toast.makeText(getActivity(), smsCodejsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 10:
                    --time;
                    if(time>=0){
                        handler.sendEmptyMessageDelayed(10,1000);
                        register_btn_get_code.setText("验证码(" + time + "秒)");
                    }else {
                        time = 60;
                        register_btn_get_code.setText("验证码(60秒)");
                        register_btn_get_code.setBackgroundResource(R.drawable.btn_code);
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
                loadingDialog.show();
                JSONObject object=new JSONObject(o.toString());
                 openId=object.getString("openid");
                String accessToken = object.getString("access_token");
                String expires = object.getString("expires_in");
                mTencent.setOpenId(openId);
                mTencent.setAccessToken(accessToken, expires);
                LogUtil.e(openId);
                QQToken token=mTencent.getQQToken();
                UserInfo info = new UserInfo(getContext(), token);
                info.getUserInfo(new BaseUserInfoListener());
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
    class BaseUserInfoListener implements IUiListener{

        @Override
        public void onComplete(Object o) {
            if(o == null){
                return;
            }
            try {
                JSONObject jo = (JSONObject) o;
                Log.e("JO:",jo.toString());
                int ret = jo.getInt("ret");
                if (ret==0){
                    HashMap map=new HashMap();
                    map.put("externalPlatform","QQ");
                    map.put("externalKey",openId);
                    map.put("terminal",TiaoshiApplication.AppIMEI);
                    map.put("icon",jo.getString("figureurl_qq_2"));
                    if (!TextUtils.isEmpty(jo.getString("gender"))&&jo.getString("gender").equals("女")){
                        map.put("sex","2");
                    }else {
                        map.put("sex","1");
                    }
                    map.put("nickname",jo.getString("nickname"));
                    map.put("actReq", SignUtil.getRandom());
                    map.put("actTime", System.currentTimeMillis() / 1000 + "");
                    String sign = SignUtil.getSign(map);
                    map.put("sign", Md5.toMd5(sign));
                    OkHttpManager.postAsync(G.Host.EXTERNAL_LOGIN_EXT,map,new MyCallBack(1,getContext(),new LoginJsonData(),handler));
                   //QQ登出
                    mTencent.logout(getContext());
                }
            } catch (Exception e) {
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
            mTencent.login(this, "all", baseUiListener);
        }
    }

    private class LoginBroadcastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("WXLoginSuccess".equals(intent.getAction())) {
                loadingDialog.show();
                HashMap hashMap=new HashMap();
                hashMap.put("externalPlatform", "WX");
                hashMap.put("terminal", AppUtil.getIMEI(getContext()));
                hashMap.put("externalKey", intent.getStringExtra("code"));
                hashMap.put("actReq", SignUtil.getRandom());
                hashMap.put("actTime", System.currentTimeMillis() / 1000 + "");
                String sign = SignUtil.getSign(hashMap);
                hashMap.put("sign", Md5.toMd5(sign));
                LogUtil.e(sign);
                OkHttpManager.postAsync(G.Host.EXTERNAL_LOGIN, hashMap, new MyCallBack(1, getActivity(), new LoginJsonData(), handler));
            } else if ("QQLoginSuccess".equals(intent.getAction())) {

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode,resultCode,data,baseUiListener);
        if (requestCode==MIMA_LOGIN){
            if (resultCode== Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
            }
        }
    }
    class TextClick extends ClickableSpan{

        @Override
        public void onClick(View widget) {
            Intent intent = new Intent(getActivity(), UserAgreementActivity.class);
            startActivity(intent);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(getResources().getColor(R.color.theme_color));
            ds.setUnderlineText(false);
        }
    }
    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(loginBroadcastReciver);
        super.onDestroy();
    }
}
