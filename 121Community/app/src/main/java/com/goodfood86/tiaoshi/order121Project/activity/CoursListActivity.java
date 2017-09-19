package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.CoursListAdapter;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.CoursListModel;
import com.goodfood86.tiaoshi.order121Project.model.CouserTypeModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/12/16.
 */

public class CoursListActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.nav_back)
    ImageView nav_back;
    @ViewInject(R.id.nav_title)
    TextView nav_title;
    @ViewInject(R.id.tv_type1)
    TextView tv_type1;
    @ViewInject(R.id.tv_type2)
    TextView tv_type2;
    @ViewInject(R.id.tv_type3)
    TextView tv_type3;
    @ViewInject(R.id.tv_type4)
    TextView tv_type4;
    @ViewInject(R.id.activity_PTRListView)
    private PullToRefreshListView activity_PTRListView;
    CouserTypeModel couserTypeModel;
    List<CoursListModel.DataBean> couseList;
    CoursListAdapter coursListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_courslist);
        ViewUtils.inject(this);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        nav_title.setText(getIntent().getStringExtra("name"));
        couseList = new ArrayList<>();
        coursListAdapter = new CoursListAdapter(couseList, CoursListActivity.this);
        activity_PTRListView.setAdapter(coursListAdapter);
    }

    private void initListener() {
        nav_back.setOnClickListener(this);
        tv_type1.setOnClickListener(this);
        tv_type2.setOnClickListener(this);
        tv_type3.setOnClickListener(this);
        tv_type4.setOnClickListener(this);
        activity_PTRListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(CoursListActivity.this, CoursDetailActivity.class)
                .putExtra("id",couseList.get((int)id).getId()));
            }
        });
    }

    private void initData() {
        HashMap map = new HashMap();
        map.put("code", getIntent().getStringExtra("coursTypeCode"));
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign1 = SignUtil.getSign(map);
        map.put("sign", MD5.getMD5(sign1));
        OkHttpManager.postAsync(G.Host.COURSE_TYPE, map, new MyCallBack(1, this, new CouserTypeModel(), handler));
    }

    private void getCoursList(String code) {
        HashMap map = new HashMap();
        map.put("courseTypeId", code);
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign1 = SignUtil.getSign(map);
        map.put("sign", MD5.getMD5(sign1));
        OkHttpManager.postAsync(G.Host.COURSE_LIST, map, new MyCallBack(2, this, new CoursListModel(), handler));

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    couserTypeModel = (CouserTypeModel) msg.obj;
                    if ("SUCCESS".equals(couserTypeModel.getRespCode())) {
                        tv_type1.setText(couserTypeModel.getData().get(0).getName());
                        tv_type2.setText(couserTypeModel.getData().get(1).getName());
                        tv_type3.setText(couserTypeModel.getData().get(2).getName());
                        tv_type4.setText(couserTypeModel.getData().get(3).getName());
                        getCoursList(couserTypeModel.getData().get(0).getId());
                    }

                    break;
                case 2:
                    CoursListModel couserListModel = (CoursListModel) msg.obj;
                    if ("SUCCESS".equals(couserListModel.getRespCode())) {
                        couseList.clear();
                        couseList.addAll(couserListModel.getData());
                        coursListAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_back:
                finish();
                break;
            case R.id.tv_type1:
                getCoursList(couserTypeModel.getData().get(0).getId());
                break;
            case R.id.tv_type2:
                getCoursList(couserTypeModel.getData().get(1).getId());
                break;
            case R.id.tv_type3:
                getCoursList(couserTypeModel.getData().get(2).getId());
                break;
            case R.id.tv_type4:
                getCoursList(couserTypeModel.getData().get(3).getId());
                break;
        }

    }
}
