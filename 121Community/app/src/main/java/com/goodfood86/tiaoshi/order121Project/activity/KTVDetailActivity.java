package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.CoursDetailModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/12/16.
 */

public class KTVDetailActivity extends Activity {
    @ViewInject(R.id.nav_back)
    ImageView nav_back;
    @ViewInject(R.id.nav_title)
    TextView nav_title; @ViewInject(R.id.iv_image)
    ImageView iv_image; @ViewInject(R.id.tv_school_name)
    TextView tv_school_name; @ViewInject(R.id.tv_star)
    TextView tv_star; @ViewInject(R.id.tv_content)
    WebView tv_content; @ViewInject(R.id.tv_type)
    TextView tv_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ktv_detail);
        ViewUtils.inject(this);
        initView();
        initData();
    }

    private void initData() {
        HashMap map=new HashMap();
        map.put("id",getIntent().getStringExtra("id"));
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign1 = SignUtil.getSign(map);
        map.put("sign", MD5.getMD5(sign1));
        OkHttpManager.postAsync(G.Host.LEISURE_DETAIL,map,new MyCallBack(1,KTVDetailActivity.this,new CoursDetailModel(),handler));
    }

    private void initView() {
        nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    CoursDetailModel coursDetailModel= (CoursDetailModel) msg.obj;
                    if ("SUCCESS".equals(coursDetailModel.getRespCode())) {
                        CoursDetailModel.DataBean dataBean=coursDetailModel.getData();
                        Order121Application.getGlobalBitmapUtils().display(iv_image,dataBean.getIcon());
                        tv_type.setText(dataBean.getPerCost()+"元");
                        tv_star.setText(dataBean.getTel());
                        tv_content.loadData(dataBean.getContent(), "text/html;charset=UTF-8", null);
                        tv_school_name.setText(dataBean.getAddress()+dataBean.getAddressDetail());
                        nav_title.setMaxEms(6);
                        nav_title.setSingleLine();
                        nav_title.setEllipsize(TextUtils.TruncateAt.END);
                        nav_title.setText(dataBean.getTitle());
                    }
                    break;
            }
        }
    };
}
