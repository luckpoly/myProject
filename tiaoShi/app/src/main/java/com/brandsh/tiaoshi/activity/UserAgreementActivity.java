package com.brandsh.tiaoshi.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;
public class UserAgreementActivity extends FragmentActivity implements View.OnClickListener{
    @ViewInject(R.id.user_agreement_ivBack)
    private ImageView user_agreement_ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);
        //沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);
        user_agreement_ivBack.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_agreement_ivBack:
                finish();
                break;
        }
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
