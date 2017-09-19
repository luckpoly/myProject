package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.widget.TitleBarView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2016/3/30.
 */
public class SupplementAddress extends Activity implements View.OnClickListener{
    @ViewInject(R.id.title_bar)
    private LinearLayout title_bar;
    private TitleBarView titleBarView;
    @ViewInject(R.id.et_supplement)
    private EditText et_supplement;
    @ViewInject(R.id.et_phone)
    private EditText et_phone;
    @ViewInject(R.id.bt_sure)
    private Button bt_sure;
    @ViewInject(R.id.rl_phone_title)
    private RelativeLayout rl_phone_title;
    String type="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supplementaddress);
        ViewUtils.inject(this);
        type=getIntent().getStringExtra("type");
        if (null==type||type.equals("1")||type.equals("2")){
            rl_phone_title.setVisibility(View.GONE);
        }
        initTitlebar();
        initListener();
    }

    private void initTitlebar() {
        titleBarView=new TitleBarView(this,title_bar,"补充地址");
    }

    private void initListener() {
        bt_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v!=null){
            switch (v.getId()){
                case R.id.bt_sure:
                    if (TextUtils.isEmpty(et_supplement.getText().toString())){
                        ToastUtil.showShort(SupplementAddress.this,"请填写详细地址");
                        break;
                    }
                    if (null!=type&&!(type.equals("1")||type.equals("2"))){
                        if (TextUtils.isEmpty(et_phone.getText().toString())){
                            ToastUtil.showShort(SupplementAddress.this,"请输入手机号");
                            break;
                        }
                    }
                        Intent intent=new Intent();
                        intent.putExtra("suaddress",et_supplement.getText().toString());
                        intent.putExtra("phoneNo",et_phone.getText().toString());
                        setResult(RESULT_OK,intent);
                        finish();
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
}
