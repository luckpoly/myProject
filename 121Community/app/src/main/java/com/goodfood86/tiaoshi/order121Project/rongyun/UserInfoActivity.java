package com.goodfood86.tiaoshi.order121Project.rongyun;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.model.InviteFriendModel;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import io.rong.imlib.model.UserInfo;

/**
 * Created by Administrator on 2016/9/13.
 */
public class UserInfoActivity extends Activity{
    @ViewInject(R.id.tv_nicheng)
    TextView tv_nicheng;
    @ViewInject(R.id.nav_title)
    TextView nav_title;
    @ViewInject(R.id.circleImageView)
    ImageView circleImageView;
    @ViewInject(R.id.nav_back)
    ImageView nav_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ViewUtils.inject(this);
        initView();
    }
    private void initView(){
      UserInfo info= getIntent().getParcelableExtra("userInfo");
        if (info!=null){
            String name=info.getName();
            String id=info.getUserId();
            String url="";
            if (null!=info.getPortraitUri()){
                 url=info.getPortraitUri().toString();
            }
            nav_title.setText("个人信息");
            if (!TextUtils.isEmpty(name)){
                tv_nicheng.setText(name);
            }
            if (!TextUtils.isEmpty(url)){
                Order121Application.getHeadImgBitmapUtils().display(circleImageView,url);
            }
        }
        nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
