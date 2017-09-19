package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by tiashiwang on 2016/4/5.
 */

public class MsgdateilsActivaty extends Activity {
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.tv_msgdata_time)
    private  TextView tv_msgdata_time;
    @ViewInject(R.id.tv_msgdatatitle)
    private  TextView tv_msgdatatitle;
    @ViewInject(R.id.tv_msgdata_content)
    private  TextView tv_msgdata_content;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massagedetails);
        ViewUtils.inject(this);
        initView();
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
    private void initView(){
        setResult(0);
        Intent intent=getIntent();
        String time =intent.getStringExtra("time");
        String conten=intent.getStringExtra("content");
        String title=intent.getStringExtra("name");
        nav_title.setText("消息详情");
        tv_msgdata_time.setText(time);
        tv_msgdata_content.setText(conten);
        tv_msgdatatitle.setText(title);
        nav_back.setVisibility(View.VISIBLE);
        nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


    }
}
