package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.CoursListAdapter;
import com.goodfood86.tiaoshi.order121Project.adapter.YuLeListAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.CoursListModel;
import com.goodfood86.tiaoshi.order121Project.model.YuLeListModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/12/17.
 */

public class YuleActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.gv_group_partners)
    private GridView gv_group_partners;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    YuLeListAdapter yuLeListAdapter;
    List<YuLeListModel.DataBean.ListBean> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yule_list);
        ViewUtils.inject(this);
        initView();
        getDataList();
    }

    private void initView() {
        nav_title.setText("K歌房");
        dataList = new ArrayList<>();
        nav_back.setOnClickListener(this);
        yuLeListAdapter = new YuLeListAdapter(YuleActivity.this, dataList);
        gv_group_partners.setAdapter(yuLeListAdapter);
        gv_group_partners.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(YuleActivity.this, KTVDetailActivity.class)
                        .putExtra("id", dataList.get(position).getId()));
            }
        });
    }

    private void getDataList() {
        HashMap map = new HashMap();
        map.put("lng", Order121Application.Lng);
        map.put("lat", Order121Application.Lat);
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign1 = SignUtil.getSign(map);
        map.put("sign", MD5.getMD5(sign1));
        OkHttpManager.postAsync(G.Host.LEISURE, map, new MyCallBack(1, this, new YuLeListModel(), handler));

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    YuLeListModel yuLeListModel = (YuLeListModel) msg.obj;
                    if ("SUCCESS".equals(yuLeListModel.getRespCode())) {
                        dataList.addAll(yuLeListModel.getData().getList());
                        yuLeListAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_back:
                finish();
                break;
        }

    }
}
