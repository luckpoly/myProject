package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
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

public class CoursDetailActivity extends Activity {
    @ViewInject(R.id.nav_back)
    ImageView nav_back;
    @ViewInject(R.id.nav_title)
    TextView nav_title;
    @ViewInject(R.id.tv_name)
    TextView tv_name;
    @ViewInject(R.id.tv_school_name)
    TextView tv_school_name;
    @ViewInject(R.id.tv_star)
    TextView tv_star;
    @ViewInject(R.id.tv_content)
    WebView tv_content;
    @ViewInject(R.id.tv_type)
    TextView tv_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cours_detail);
        ViewUtils.inject(this);
        initView();
        initData();
    }

    private void initData() {
        HashMap map = new HashMap();
        map.put("courseId", getIntent().getStringExtra("id"));
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign1 = SignUtil.getSign(map);
        map.put("sign", MD5.getMD5(sign1));
        OkHttpManager.postAsync(G.Host.COURSE_DETAIL, map, new MyCallBack(1, CoursDetailActivity.this, new CoursDetailModel(), handler));
    }

    private void initView() {
        nav_title.setText("课程详情");
        nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    CoursDetailModel coursDetailModel = (CoursDetailModel) msg.obj;
                    if ("SUCCESS".equals(coursDetailModel.getRespCode())) {
                        CoursDetailModel.DataBean dataBean = coursDetailModel.getData();
                        tv_name.setText(dataBean.getName());
                        tv_type.setText(dataBean.getCourseTypeName());
                        tv_star.setText(dataBean.getScore() + "分");
                        tv_content.loadData(dataBean.getIntro(), "text/html;charset=UTF-8", null);
                        tv_school_name.setText(dataBean.getSchool());
                    }
                    break;
            }
        }
    };
}
