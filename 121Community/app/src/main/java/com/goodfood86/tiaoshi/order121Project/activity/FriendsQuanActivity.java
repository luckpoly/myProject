package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.FriendQuanListAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.CoursDetailModel;
import com.goodfood86.tiaoshi.order121Project.model.FriendModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/12/16.
 */

public class FriendsQuanActivity extends Activity implements View.OnClickListener{
    @ViewInject(R.id.nav_back)
    ImageView nav_back;
    @ViewInject(R.id.nav_title)
    TextView nav_title;
    @ViewInject(R.id.nav_right)
    ImageView nav_right;
    @ViewInject(R.id.activity_PTRListView)
    PullToRefreshListView activity_PTRListView;
    List <FriendModel.DataBean.ListBean> listData;
    FriendQuanListAdapter friendQuanListAdapter;
    private Boolean isRefresh=false;
    private  String mPage="1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendsquan);
        ViewUtils.inject(this);
        initView();
        initLisener();
        initData();
        initIndicator();
        setListenerToPTRListView();
    }
    private void initView() {
        nav_title.setText("朋友圈");
        if (getIntent().getStringExtra("from")!=null&&getIntent().getStringExtra("from").equals("my")){
            nav_right.setImageResource(R.mipmap.friend_fabu);
            nav_right.setVisibility(View.VISIBLE);
            nav_right.setOnClickListener(this);
        }
    }
    private void initLisener() {
        nav_back.setOnClickListener(this);
         listData=new ArrayList<>();
        if (getIntent().getStringExtra("from")!=null&&getIntent().getStringExtra("from").equals("my")){
            friendQuanListAdapter=new FriendQuanListAdapter(listData,this,handler,true);
        }else {
            friendQuanListAdapter=new FriendQuanListAdapter(listData,this);
        }
        activity_PTRListView.setAdapter(friendQuanListAdapter);
    }
    private void setListenerToPTRListView() {
        activity_PTRListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                setRefreshDate(refreshView);
                isRefresh = true;
                mPage="1";
                initData();
                handler.sendEmptyMessageDelayed(100, 1500);
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                setRefreshDate(refreshView);
                initData();
                handler.sendEmptyMessageDelayed(100, 1500);
            }
        });
    }
    private void initData(){
        if (getIntent().getStringExtra("from")!=null&&getIntent().getStringExtra("from").equals("my")){
            HashMap map=new HashMap();
            map.put("token", Order121Application.globalLoginModel.getData().getToken());
            map.put("page",mPage);
            map.put("actReq", SignUtil.getRandom());
            map.put("actTime", System.currentTimeMillis() / 1000 + "");
            String sign = SignUtil.getSign(map);
            map.put("sign", MD5.getMD5(sign));
            OkHttpManager.postAsync(G.Host.MY_BLOG,map,new MyCallBack(1,this,new FriendModel(),handler));
        }else {
            HashMap map=new HashMap();
            map.put("page",mPage);
            map.put("pageSize","10");
            map.put("actReq", SignUtil.getRandom());
            map.put("actTime", System.currentTimeMillis() / 1000 + "");
            String sign = SignUtil.getSign(map);
            map.put("sign", MD5.getMD5(sign));
            OkHttpManager.postAsync(G.Host.BLOG,map,new MyCallBack(1,this,new FriendModel(),handler));
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    FriendModel friendModel= (FriendModel) msg.obj;
                    if ("SUCCESS".equals(friendModel.getRespCode())) {

                        mPage=friendModel.getData().getNextPage()+"";
                        if (isRefresh){
                            listData.clear();
                            isRefresh=false;
                        }else {
                            activity_PTRListView.onRefreshComplete();
                        }
                        listData.addAll(friendModel.getData().getList());
                        if (listData.size()>=Integer.parseInt(friendModel.getData().getTotalCount())){
                            activity_PTRListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            ToastUtil.showShort(FriendsQuanActivity.this,"已经是最后一页了");
                        }else {
                            activity_PTRListView.setMode(PullToRefreshBase.Mode.BOTH);
                        }
                        friendQuanListAdapter.notifyDataSetChanged();
                    }
                    break;
                case 2:
                    FriendModel friendModel1= (FriendModel) msg.obj;
                    if ("SUCCESS".equals(friendModel1.getRespCode())) {
                        ToastUtil.showShort(FriendsQuanActivity.this,"删除成功");
                        initData();
                    }
                    break;
                case 100:
                    activity_PTRListView.onRefreshComplete();
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
            case R.id.nav_right:
                startActivityForResult(new Intent(this,FrientFabuActivity.class),0);
                break;

        }
    }
    private void initIndicator() {
        ILoadingLayout startLabels = activity_PTRListView
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在载入...");// 刷新时
        startLabels.setReleaseLabel("放开以刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = activity_PTRListView.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉加载更多...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在载入...");// 刷新时
        endLabels.setReleaseLabel("放开以刷新...");// 下来达到一定距离时，显示的提示
    }
    private void setRefreshDate(PullToRefreshBase<ListView> refreshView) {
        //放置在刷新时的监听中
        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("上次刷新 " + DateUtils.formatDateTime(
                this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0){
            if (resultCode==1){
                mPage="1";
                initData();
            }
        }
    }
}
