package com.brandsh.tiaoshi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.FenxiangModel;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.brandsh.tiaoshi.wxapi.MD5;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mingle.widget.ShapeLoadingDialog;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/8/19.
 */
public class BianjiActivity extends Activity {
    @ViewInject(R.id.rg_yes_or_no)
    private RadioGroup rg_yes_or_no;
    @ViewInject(R.id.ll_phone)
    private LinearLayout ll_phone;
    @ViewInject(R.id.tv_confirm)
    private TextView tv_confirm;
    @ViewInject(R.id.et_two_code)
    private EditText et_two_code;
    @ViewInject(R.id.et_phone)
    private EditText et_phone;
    @ViewInject(R.id.et_content)
    private EditText et_content;
     @ViewInject(R.id.rb_yes)
    private RadioButton rb_yes;
    @ViewInject(R.id.juice_rlBack)
    private RelativeLayout juice_rlBack;

    private String code;
    private String phone;
    private String content;
    private ShapeLoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bianji);
        AppUtil.Setbar(this);
        ViewUtils.inject(this);
        initView();
    }
    private  void initView(){
        rg_yes_or_no.check(R.id.rb_no);
        loadingDialog = ProgressHUD.show(this, "努力加载中...");
        et_two_code.setText(getIntent().getStringExtra("etcode"));
        rg_yes_or_no.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_yes:
                        ll_phone.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rb_no:
                        ll_phone.setVisibility(View.GONE);
                        break;

                }
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code=et_two_code.getText().toString();
                phone=et_phone.getText().toString();
                content=et_content.getText().toString();
                if (TextUtils.isEmpty(code)){
                    ToastUtil.showShort(BianjiActivity.this,"请输入领取码");
                    return;
                }
                if (rb_yes.isChecked()&&!TextUtils.isEmpty(phone)){
                    if (phone.length()!=11){
                        ToastUtil.showShort(BianjiActivity.this,"请输入正确手机号码");
                        return;
                    }
                }
                initData();
            }
        });
        juice_rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private  void initData(){
        loadingDialog.show();
        HashMap map=new HashMap();
        map.put("token", TiaoshiApplication.globalToken);
        map.put("orderId", getIntent().getStringExtra("id"));
        if (rb_yes.isChecked()){
            if (TextUtils.isEmpty(phone)){
                map.put("phone", "");
            }else {
                map.put("phone", phone);
            }
        }else {
            map.put("phone", "");
        }
        if (!TextUtils.isEmpty(content)){
            map.put("intro", content);
        }else {
            map.put("intro", "");
        }
        map.put("code",code);
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime",System.currentTimeMillis()/1000+"");
        String sign= SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign));
        Log.e("---",sign);
        OkHttpManager.postAsync(G.Host.CREATE_SHARE,map,new MyCallBack(1,BianjiActivity.this,new FenxiangModel(),handler));
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 1:
                    loadingDialog.dismiss();
                    FenxiangModel fenxiangModel = (FenxiangModel) msg.obj;
                    if ("SUCCESS".equals(fenxiangModel.getRespCode())){
                      String url=  fenxiangModel.getData().getGetUrl();
                        String code=fenxiangModel.getData().getGetCode();
                        Intent intent=new Intent();
                        intent.putExtra("url",url);
                        intent.putExtra("code",code);
                        setResult(1,intent);
                        finish();
                    }
                break;

            }
        }
    };
}
