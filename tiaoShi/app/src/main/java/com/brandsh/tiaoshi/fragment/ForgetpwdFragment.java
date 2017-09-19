package com.brandsh.tiaoshi.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.ForgetPwdJsonData;
import com.brandsh.tiaoshi.model.SMSCodejsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.REAide;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;

/**
 * Created by libokang on 15/10/9.
 */
public class ForgetpwdFragment extends BaseFragment implements View.OnClickListener {


    private static final int ASYNC_GET_CODE = 0;
    private static final int ASYNC_FORGETPWD = 1;
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.forgetpwd_phone)
    private EditText forgetpwd_phone;
    @ViewInject(R.id.forgetpwd_btn_code)
    private Button forgetpwd_btn_code;
    @ViewInject(R.id.forgetpwd_et_code)
    private EditText forgetpwd_et_code;
    @ViewInject(R.id.forgetpwd_et_surepwd)
    private EditText forgetpwd_et_surepwd;
    @ViewInject(R.id.forgetpwd_et_pwd)
    private EditText forgetpwd_et_pwd;
    @ViewInject(R.id.forgetpwd_btn_sure)
    private Button forgetpwd_btn_sure;


    private String uuid;
    private HttpUtils httpUtils;
    private int time;
    private HashMap<String,String> submitRequestMap;
    private HashMap<String,String> smsCodeRequestMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.forgetpwd_fragment, null);
        ViewUtils.inject(this, view);

        init();


        initListener();

        return view;
    }

    private void init() {
        time = 60;
        httpUtils = TiaoshiApplication.getGlobalHttpUtils();


        if (getActivity().getIntent().getStringExtra("isShezhi")!=null){
            nav_title.setText(getActivity().getIntent().getStringExtra("isShezhi"));
        }else {
            nav_title.setText("重置密码");
        }
        nav_back.setVisibility(View.VISIBLE);

    }

    private void initListener() {
        forgetpwd_btn_code.setOnClickListener(this);
        forgetpwd_btn_sure.setOnClickListener(this);
        nav_back.setOnClickListener(this);
    }


    private void forgetpwd() {
        submitRequestMap=new HashMap<>();
       submitRequestMap.put("username", forgetpwd_phone.getText().toString());
       submitRequestMap.put("verifyCode", forgetpwd_et_code.getText().toString());
       submitRequestMap.put("password", forgetpwd_et_pwd.getText().toString());
       submitRequestMap.put("uuid", uuid);
        submitRequestMap.put("actReq","123456");
        submitRequestMap.put("actTime",System.currentTimeMillis()/1000+"");
        String sign= SignUtil.getSign(submitRequestMap);
        submitRequestMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.FORGET_PWD,submitRequestMap,new MyCallBack(2, getActivity(), new ForgetPwdJsonData(), handler));

    }

    private boolean checkValid() {

        String phone = forgetpwd_phone.getText().toString();
        String code = forgetpwd_et_code.getText().toString();
        String pwd = forgetpwd_et_pwd.getText().toString();
        String surepwd = forgetpwd_et_surepwd.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            showToast("手机号不能为空");
            return false;
        }

        if (TextUtils.isEmpty(code)) {
            showToast("请输入验证码");
            return false;
        }


        if (TextUtils.isEmpty(pwd)) {
            showToast("密码不能为空");
            return false;
        } else if (pwd.length() < 6 || pwd.length() > 16) {
            showToast("密码只支持6～16位的数字或英文");
            return false;
        }

        if (TextUtils.isEmpty(surepwd)) {
            showToast("确认密码不能为空");
            return false;
        }

        if (surepwd != null && pwd != null) {
            if (!surepwd.equals(pwd)) {
                showToast("密码输入不一致");
                return false;
            }
        }

        if (uuid == null) {
            showToast("请先获取验证码");
            return false;
        }

        return true;
    }

    private void getYZCode() {
        String phone = forgetpwd_phone.getText().toString();

        //验证合法性
        if (!REAide.checkPhoneNumValide(phone)) {
            showToast("请输入正确的手机号码");
            return;
        }

        forgetpwd_btn_code.setBackgroundResource(R.drawable.btn_code1);
        smsCodeRequestMap=new HashMap<>();
        smsCodeRequestMap.put("tel", phone);
        smsCodeRequestMap.put("actReq","123456");
        smsCodeRequestMap.put("actTime", System.currentTimeMillis()/1000+"");
        String sign= SignUtil.getSign(smsCodeRequestMap);
        smsCodeRequestMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.GET_CODE,smsCodeRequestMap,new MyCallBack(1, getActivity(), new SMSCodejsonData(), handler));
        handler.sendEmptyMessageDelayed(10, 1000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    SMSCodejsonData smsCodejsonData = (SMSCodejsonData) msg.obj;
                    if (smsCodejsonData.getRespCode().equals("SUCCESS")) {
                        Toast.makeText(getActivity(), "验证码发送成功", Toast.LENGTH_SHORT).show();
                        uuid = smsCodejsonData.getData().getUuid();
                    } else {
                        Toast.makeText(getActivity(), smsCodejsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    ForgetPwdJsonData forgetPwdJsonData = (ForgetPwdJsonData) msg.obj;
                    if (forgetPwdJsonData.getRespCode().equals("SUCCESS")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setCancelable(false);
                        builder.setTitle("系统提示").setMessage(nav_title.getText().toString()+"成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().setResult(1001);
                                getActivity().finish();
                            }
                        }).create().show();
                    } else {
                        Toast.makeText(getActivity(), forgetPwdJsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 10:
                    --time;
                    if (time >= 0) {
                        handler.sendEmptyMessageDelayed(10, 1000);
                        forgetpwd_btn_code.setText("验证码(" + time + "秒)");
                    } else {
                        time = 60;
                        forgetpwd_btn_code.setText("验证码(60秒)");
                        forgetpwd_btn_code.setBackgroundResource(R.drawable.btn_code);
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.forgetpwd_btn_code:
                if (time == 60) {
                    getYZCode();
                } else {
                    return;
                }
                break;


            case R.id.forgetpwd_btn_sure:

                if (checkValid()) {
                    forgetpwd();
                }
                break;
            case R.id.nav_back:
                getActivity().finish();
                break;
        }
    }
}
