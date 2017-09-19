package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.ActivityListAdapter;
import com.goodfood86.tiaoshi.order121Project.adapter.MyActivityListAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.ActivityListModel;
import com.goodfood86.tiaoshi.order121Project.model.CreateActivityModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class PublishedActivity extends Activity implements View.OnClickListener{
    @ViewInject(R.id.tv_fabu_activity)
    private TextView tv_fabu_activity;
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.activity_PTRListView)
    private PullToRefreshListView activity_PTRListView;
    @ViewInject(R.id.ll_no_fabubg)
    private LinearLayout ll_no_fabubg;
    private Boolean isRefresh=false;
    private List<ActivityListModel.DataBean.ListBean>  dataBean=new ArrayList<>();
    private MyActivityListAdapter adapter;
    private ActivityListModel activityListModel;
    private  String type;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    activityListModel= (ActivityListModel) msg.obj;
                    if ("SUCCESS".equals(activityListModel.getRespCode())&&null!=activityListModel.getData()){
                        activity_PTRListView.onRefreshComplete();
                        List<ActivityListModel.DataBean.ListBean> newData=activityListModel.getData().getList();
                        if (isRefresh){
                            dataBean.clear();
                            isRefresh=false;
                        }
                        dataBean.addAll(newData);
                        adapter.notifyDataSetChanged();
                        if (dataBean.size()>0){
                            ll_no_fabubg.setVisibility(View.GONE);
                        }else {
                            ll_no_fabubg.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                case 2:

                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_published);
        ViewUtils.inject(this);
        initView();
        initData();
        initIndicator();
        setListenerToPTRListView();

    }
    private void initView(){
        type=   getIntent().getStringExtra("type");
        nav_title.setText("已发布");
        if (type.equals("已报名")){
            tv_fabu_activity.setVisibility(View.GONE);
            nav_title.setText("已报名");
        }
        tv_fabu_activity.setOnClickListener(this);
        nav_back.setOnClickListener(this);

        adapter=new MyActivityListAdapter(dataBean,PublishedActivity.this,handler);
        activity_PTRListView.setAdapter(adapter);
        activity_PTRListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(PublishedActivity.this, MyActivityDateilsActivity.class);
                intent.putExtra("id",activityListModel.getData().getList().get((int)id).getId());
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });

    }
    private void  initData(){
        HashMap map=new HashMap();
        map.put("token", Order121Application.globalLoginModel.getData().getToken());
        if (type.equals("已发布")){
            map.put("isSelf", "Yes");
        }else {
            map.put("apply", "YES");
        }
        map.put("pageSize", "50");
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime",System.currentTimeMillis()/1000+"");
        String sign= SignUtil.getSign(map);
        map.put("sign", MD5.getMD5(sign));
        OkHttpManager.postAsync(G.Host.ACTIVITY_LIST,map,new MyCallBack(1,this,new ActivityListModel(),handler));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_fabu_activity:
                startActivity(new Intent(PublishedActivity.this,FabuActivity.class));
                break;
            case R.id.nav_back:
                finish();
                break;
        }
    }
    private void setListenerToPTRListView() {
        activity_PTRListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                setRefreshDate(refreshView);
                isRefresh = true;
                initData();
                handler.sendEmptyMessageDelayed(100, 3000);
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                setRefreshDate(refreshView);
            }
        });
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
}
