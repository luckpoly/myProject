package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.MyZixunListAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.MyZixunListModel;
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
 * Created by Administrator on 2016/12/19.
 */

public class MyZixunListActivity extends Activity implements View.OnClickListener{
    @ViewInject(R.id.nav_back)
    ImageView nav_back;
    @ViewInject(R.id.nav_title)
    TextView nav_title;
    @ViewInject(R.id.activity_PTRListView)
    PullToRefreshListView activity_PTRListView;
    List<MyZixunListModel.DataBean> listData;
    MyZixunListAdapter friendQuanListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_zixunlist);
        ViewUtils.inject(this);
        initView();
        initListener();
        initData();
    }
    private void initView() {
        nav_title.setText("我的咨询");
        listData=new ArrayList<>();
         friendQuanListAdapter=new MyZixunListAdapter(listData,this,handler);
        activity_PTRListView.setAdapter(friendQuanListAdapter);
    }
    private void initListener(){
        nav_back.setOnClickListener(this);
    }
    private void initData(){
        HashMap map1=new HashMap();
        map1.put("token", Order121Application.globalLoginModel.getData().getToken());
        map1.put("actReq", SignUtil.getRandom());
        map1.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign1 = SignUtil.getSign(map1);
        map1.put("sign", MD5.getMD5(sign1));
        OkHttpManager.postAsync(G.Host.MEDICAL_CONSULT,map1,new MyCallBack(1,this,new MyZixunListModel(),handler));
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    MyZixunListModel myZixunListModel= (MyZixunListModel) msg.obj;
                    if ("SUCCESS".equals(myZixunListModel.getRespCode())){
                        listData.clear();
                        if (null!=myZixunListModel.getData()){
                            listData.addAll(myZixunListModel.getData());
                        }
                        friendQuanListAdapter.notifyDataSetChanged();
                    }
                    break;
                case 2:
                    MyZixunListModel myZixunListModel1= (MyZixunListModel) msg.obj;
                    if ("SUCCESS".equals(myZixunListModel1.getRespCode())){
                        ToastUtil.showShort(MyZixunListActivity.this,"删除成功");
                        initData();
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
