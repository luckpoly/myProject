package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.VerifyModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.REAide;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.utils.StringUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.widget.ProgressHUD;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by tiashiwang on 2016/4/6.
 */
public class UpdatapswActivity extends Activity implements View.OnClickListener {

    @ViewInject(R.id.iv_register_yanzhengma)
    private LinearLayout iv_register_yanzhengma;
    @ViewInject(R.id.et_register_user)
    private EditText et_register_user;
    @ViewInject(R.id.et_register_yanzhengma)
    private EditText et_register_yanzhengma;
    @ViewInject(R.id.et_register_psw)
    private EditText et_register_psw;
    @ViewInject(R.id.et_register_ispsw)
    private EditText et_register_ispsw;
    @ViewInject(R.id.tv_register_register)
    private TextView tv_register_register;
    @ViewInject(R.id.ck_register_select)
    private CheckBox ck_register_select;
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.tv_register_yzmNo)
    private TextView tv_register_yzmNo;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;

    @ViewInject(R.id.tv_user_xieyi)
    private  TextView tv_user_xieyi;
    private String yanzhengma;
    private String data;
    private String user;
    private String psw;
    private String ispsw;
    HttpUtils httpUtils;
    private RequestParams getyanzhengma;
    private RequestParams regsiterhttp;
    private ProgressHUD dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatapassword);
        ViewUtils.inject(this);
        initView();
        ck_register_select.setChecked(true);

    }
    public void onResume() {
        super.onResume();
        //友盟统计
       //已删

    }
    public void onPause() {
        super.onPause();
        //友盟统计
        //已删
    }
    private void initView() {
        nav_title.setText("修改密码");
        httpUtils = Order121Application.getGlobalHttpUtils();
        iv_register_yanzhengma.setOnClickListener(this);
        tv_register_register.setOnClickListener(this);

        nav_back.setVisibility(View.VISIBLE);
        nav_back.setOnClickListener(this);
        dialog= ProgressHUD.show(this,"玩命加载中...",false,null);
        ck_register_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tv_register_register.setBackground(getResources().getDrawable(R.drawable.loginbt_yaunjiaolan));
                    tv_register_register.setEnabled(true);
                } else {
                    tv_register_register.setBackground(getResources().getDrawable(R.drawable.loginbt_yaunjiaohui));
                    tv_register_register.setEnabled(false);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        getdata();
        switch (v.getId()) {
            case R.id.iv_register_yanzhengma:
                if (user.equals("") || user == null) {
                    ToastUtil.showShort(UpdatapswActivity.this, " 手机号不能为空");
                    break;
                } else if (!REAide.checkPhoneNumValide(user)) {
                    ToastUtil.showShort(this, "请输入正确手机号");
                    break;
                }
                Log.e("---", user);
                getyanzhengma = new RequestParams();
                getyanzhengma.addBodyParameter("phone", user);
                getyanzhengma.addBodyParameter("smsType", "2");
                httpUtils.send(HttpRequest.HttpMethod.POST, G.Host.GET_YANZHENGMA, getyanzhengma, new MyRequestCallBack(UpdatapswActivity.this, handler, 1, new VerifyModel()));
                new Countdown().start();
                setbg();
                break;
            case R.id.tv_register_register:
                boolean ispassword = StringUtil.IsPassword(ispsw);
                if (user.equals("") || user == null) {
                    ToastUtil.showShort(UpdatapswActivity.this, " 手机号不能为空");
                    break;
                } else if (!REAide.checkPhoneNumValide(user)) {
                    ToastUtil.showShort(this, "请输入正确手机号");
                    break;
                } else if (yanzhengma.equals("")) {
                    ToastUtil.showShort(this, "验证码不能为空");
                    break;
                }else if (!ispassword) {
                    ToastUtil.showShort(this, "新密码不符合要求");
                    break;
                }
                dialog.show();
                regsiterhttp = new RequestParams();
                regsiterhttp.addBodyParameter("token", Order121Application.globalLoginModel.getData().getToken());
                regsiterhttp.addBodyParameter("oldPassword", MD5.getMD5(psw) );
                regsiterhttp.addBodyParameter("smsCode", yanzhengma);
                regsiterhttp.addBodyParameter("confirm", MD5.getMD5(ispsw));
                regsiterhttp.addBodyParameter("password", MD5.getMD5(ispsw));
                httpUtils.send(HttpRequest.HttpMethod.POST, G.Host.UPDATAPASSWORD, regsiterhttp, new MyRequestCallBack(UpdatapswActivity.this, handler, 2, new VerifyModel()));
                break;
//            case R.id.tv_user_xieyi:
//                startActivity(new Intent(ForgotpswActivity.this, AgreementActivity.class));
//                break;
            case R.id.nav_back:
                finish();
                break;
        }
    }
    //设置验证码背景颜色
    private void setbg(){
        iv_register_yanzhengma.setBackground(getResources().getDrawable(R.drawable.loginbt_yaunjiaohui));
        iv_register_yanzhengma.setEnabled(false);
    }
    private void setbglan(){
        iv_register_yanzhengma.setBackground(getResources().getDrawable(R.drawable.loginbt_yaunjiaolan));
        iv_register_yanzhengma.setEnabled(true);
    }
    private void getdata(){
        user=et_register_user.getText().toString().trim();
        psw=et_register_psw.getText().toString().trim();
        yanzhengma=et_register_yanzhengma.getText().toString().trim();
        ispsw= et_register_ispsw.getText().toString().trim();
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    VerifyModel verifyModel= (VerifyModel) msg.obj;
                    if (verifyModel.getRespCode()==0){
                        ToastUtil.showShort(UpdatapswActivity.this,verifyModel.getRespMsg());
                    }else if (verifyModel.getRespCode()==-1){
                        ToastUtil.showShort(UpdatapswActivity.this,verifyModel.getRespMsg());
                    }
                    break;
                case 2:
                    dialog.dismiss();
                    VerifyModel verifyModel1= (VerifyModel) msg.obj;
                    if (verifyModel1.getRespCode()==0){
                        ToastUtil.showShort(UpdatapswActivity.this, verifyModel1.getRespMsg());
                        Intent intent=new Intent(UpdatapswActivity.this,LoginActivity.class);
                        intent.putExtra("type",G.SP.RORDOTPSW);
                        startActivity(intent);
                        Order121Application.getInstance().finishAllActivity();
                        finish();
                    }else if (verifyModel1.getRespCode()==-1){
                        ToastUtil.showShort(UpdatapswActivity.this,verifyModel1.getRespMsg());
                    }
                    break;
//
                case 3:
                    String str = msg.obj.toString();
                    tv_register_yzmNo.setText("("+str+"s)");
                    if (str.equals("0")){
                        tv_register_yzmNo.setText("(60s)");
                        setbglan();
                    }

                    break;

            }
        }
    };
    private class Countdown extends Thread {
        @Override
        public void run() {
            try {
                int i = 60;
                while (i > 0) {
                    Message msg = handler.obtainMessage();
                    i--;
                    msg.what=3;
                    msg.obj = i;
                    handler.sendMessage(msg);
                    sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
