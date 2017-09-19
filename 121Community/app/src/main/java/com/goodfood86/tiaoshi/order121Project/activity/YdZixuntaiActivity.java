package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/12/15.
 */

public class YdZixuntaiActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.nav_back)
    ImageView nav_back;
    @ViewInject(R.id.nav_title)
    TextView nav_title;
    @ViewInject(R.id.iv_go_call)
    ImageView iv_go_call;
    @ViewInject(R.id.tv_show_text)
    TextView tv_show_text;
    @ViewInject(R.id.ll_default)
    LinearLayout ll_default;
    @ViewInject(R.id.wv_showDetail)
    WebView wv_showDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ydweb_zixuntai);
        ViewUtils.inject(this);
        initView();
        initListener();
    }

    private void initView() {
        String data=getIntent().getStringExtra("data");
        if (TextUtils.isEmpty(data)){
            ll_default.setVisibility(View.VISIBLE);
            iv_go_call.setOnClickListener(this);

        }else {
            wv_showDetail.getSettings().setJavaScriptEnabled(true);
            wv_showDetail.loadData(data, "text/html;charset=UTF-8", null);
        }
        if (getIntent().getStringExtra("from").equals("zixun")){
            tv_show_text.setText("还没有常见的问题哦！");
            nav_title.setText("咨询台");
        }else {
            nav_title.setText("医生排班");
            tv_show_text.setText("暂时没有医生排班！");
        }

    }
    private void initListener(){
        nav_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_back:
                finish();
                break;
            case R.id.iv_go_call:
                call(getIntent().getStringExtra("phone"));
                break;
        }
    }
    private void call(final String phone) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("联系药店:").setMessage(phone);
        builder.setPositiveButton("拨打", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    startActivity(intent);
                }catch (Exception e){
                }

                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
}
