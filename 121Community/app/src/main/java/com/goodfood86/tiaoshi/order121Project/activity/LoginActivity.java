package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.GlobalLoginModel;
import com.goodfood86.tiaoshi.order121Project.model.GroupUserQueryModel;
import com.goodfood86.tiaoshi.order121Project.model.RTokenModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.REAide;
import com.goodfood86.tiaoshi.order121Project.utils.RongHttp;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.utils.StringUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.widget.ProgressHUD;
import com.goodfood86.tiaoshi.order121Project.wxapi.WXEntryActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

/**
 * Created by Administrator on 2016-03-31.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.et_login_username)
    private EditText et_login_username;
    @ViewInject(R.id.et_login_password)
    private EditText et_login_password;
    @ViewInject(R.id.tv_login_wangjipsw)
    private TextView tv_login_wangjipsw;
    @ViewInject(R.id.tv_login_login)
    private TextView tv_login_login;
    @ViewInject(R.id.tv_login_register)
    private TextView tv_login_register;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.tv_go_wxlogin)
    private TextView tv_go_wxlogin;

    private String psw;
    private String user;
    private HttpUtils httpUtils;
    private RequestParams logindata;
    private int Atype = 0;
    private ProgressHUD dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this);
        Intent intent = getIntent();
        Atype = intent.getIntExtra("type", 0);
        initView();
        StatusBarUtil.StatusBarLightMode(this);
    }

    public void onResume() {
        super.onResume();
        //友盟统计
//       //已删
    }

    public void onPause() {
        super.onPause();
        //友盟统计
//        //已删
    }

    private void initView() {
        httpUtils = Order121Application.getGlobalHttpUtils();
        tv_login_login.setOnClickListener(this);
        tv_login_register.setOnClickListener(this);
        tv_login_wangjipsw.setOnClickListener(this);
        tv_go_wxlogin.setOnClickListener(this);
        nav_back.setVisibility(View.VISIBLE);
        nav_back.setOnClickListener(this);
        nav_title.setText("登录");
        dialog = ProgressHUD.show(this, "玩命加载中...", false, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_login:
                psw = et_login_password.getText().toString().trim();
                user = et_login_username.getText().toString().trim();
                boolean ispassword = StringUtil.IsPassword(psw);
                if (user == null || user.equals("")) {
                    ToastUtil.showShort(this, "手机号不能为空");
                    break;
                } else if (psw == null || psw.equals("")) {
                    ToastUtil.showShort(this, "密码不能为空");
                    break;
                } else if (!REAide.checkPhoneNumValide(user)) {
                    ToastUtil.showShort(this, "请检查手机号是否正确");
                    break;
                } else if (!ispassword) {
                    ToastUtil.showShort(this, "密码不符合要求");
                    break;
                }
                dialog.show();
                httpsend();

                break;
            case R.id.tv_login_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_login_wangjipsw:
                startActivity(new Intent(LoginActivity.this, ForgotpswActivity.class));
                break;
            case R.id.nav_back:
                finish();
                break;
            case R.id.tv_go_wxlogin:
                startActivity(new Intent(LoginActivity.this, WXEntryActivity.class));
                break;

        }
    }

    private void httpsend() {
        logindata = new RequestParams();
        logindata.addBodyParameter("username", user);
        logindata.addBodyParameter("password", MD5.getMD5(psw));
        httpUtils.send(HttpRequest.HttpMethod.POST, G.Host.LOGIN, logindata, new MyRequestCallBack(LoginActivity.this, handler, 1, new GlobalLoginModel()));

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dialog.dismiss();
            switch (msg.what) {
                case 1:
                    GlobalLoginModel globalLoginModel = (GlobalLoginModel) msg.obj;
                    ToastUtil.showShort(LoginActivity.this, globalLoginModel.getRespMsg());
                    if (globalLoginModel.getRespCode() == 0) {
                        SharedPreferences.Editor editor = getSharedPreferences(G.SP.APP_NAME, Context.MODE_PRIVATE).edit();
                        editor.putString(G.SP.LOGIN_NAME, et_login_username.getText().toString().trim());
                        editor.putString(G.SP.LOGIN_PWD, et_login_password.getText().toString().trim());
                        editor.commit();
                        if (globalLoginModel != null) {
                            Order121Application.globalLoginModel = globalLoginModel;
                            sendBroadcast(new Intent("updateSlidingFragment"));
                            if (Order121Application.isLogin()) {
                                jPush();
                            }
                            if (Atype == 0) {
                                finish();
                            } else if (Atype == G.SP.RORDOTPSW) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                        getTestToken();
                    }
                    break;
                case 2:
                    RTokenModel model= (RTokenModel) msg.obj;
                    if (model.getCode()==200){
                        initRy(model.getToken());
                    }else {
                        Log.e("融云初始化结果：","失败");
                    }
                    break;
            }
        }
    };
    private void initRy(String token){
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                getTestToken();
            }
            @Override
            public void onSuccess(String s) {
                Log.e("------","rongyun--init---SUCCESS");
                UserInfo info=new UserInfo(Order121Application.globalLoginModel.getData().getPhone(),Order121Application.globalLoginModel.getData().getNickname(), Uri.parse(Order121Application.globalLoginModel.getData().getImgKey()));
                RongIM.getInstance().setCurrentUserInfo(info);
                RongIM.getInstance().setMessageAttachedUserInfo(true);
                for (int i=1;i<=19;i++){
                    final int finalI = i;
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            List<NameValuePair> nameValuePair = new ArrayList<>();
                            nameValuePair.add(new BasicNameValuePair("groupId","020"+ finalI));
                            nameValuePair.add(new BasicNameValuePair("userId",Order121Application.globalLoginModel.getData().getPhone()));
//                            nameValuePair.add(new BasicNameValuePair("groupName",createActivityModel.getData().getCustomActivityName()));
                            RongHttp.rPostHttp("group/join.json",nameValuePair,new GroupUserQueryModel(),handler,3,LoginActivity.this);
                        }
                    }.start();
                }
            }
            @Override
            public void onError(RongIMClient.ErrorCode e) {
            }
        });
    }
    private void getTestToken(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                List<NameValuePair> nameValuePair = new ArrayList<>();
                nameValuePair.add(new BasicNameValuePair("userId",Order121Application.globalLoginModel.getData().getPhone()));
                RongHttp.rPostHttp("user/getToken.json",nameValuePair,new RTokenModel(),handler,2,LoginActivity.this);
            }
        }.start();

    }
    private void jPush() {
        JPushInterface.setAlias(LoginActivity.this, Order121Application.globalLoginModel.getData().getId() + "", new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.e("11", i + "");
                if (i == 6002) {
                    jPush();
                }
            }
        });
    }


}

