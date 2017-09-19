package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.OrderMsgListAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.OrderMsgLisrModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.widget.ProgressHUD;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;


/**
 * 消费统计类
 * Created by tiashiwang on 2016/4/7.
 */
public class OrderMsgListActivity extends Activity {
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.plv_msglist)
    private PullToRefreshListView plv_msglist;
    private HttpUtils httpUtils;
    private RequestParams orderdata;
    private ProgressHUD dialog;
    private List<OrderMsgLisrModel.DataBean.ListBean> list;
    private OrderMsgListAdapter adapter;
    private Boolean isRefresh=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msglist);
        ViewUtils.inject(this);
        initIndicator();
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
        nav_title.setText("交易明细");
        nav_back.setVisibility(View.VISIBLE);
        dialog= ProgressHUD.show(this,"玩命加载中...",false,null);
        nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list=new ArrayList<>();

         adapter=new OrderMsgListAdapter(this, list);
        plv_msglist.setAdapter(adapter);
        getHttpdate();
        plv_msglist.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                setRefreshDate(refreshView);
                isRefresh = true;
                getHttpdate();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                setRefreshDate(refreshView);
            }
        });
    }
    private void initIndicator() {
        ILoadingLayout startLabels = plv_msglist.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在载入...");// 刷新时
        startLabels.setReleaseLabel("放开以刷新...");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabels = plv_msglist.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉加载更多...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在载入...");// 刷新时
        endLabels.setReleaseLabel("放开以刷新...");// 下来达到一定距离时，显示的提示
    }
    private void setRefreshDate(PullToRefreshBase<ListView> refreshView) {
        //放置在刷新时的监听中
        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("上次刷新 " + DateUtils.formatDateTime(
                this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL));
    }
    private  void getHttpdate(){
        dialog.show();
        httpUtils= Order121Application.getGlobalHttpUtils();
        orderdata=new RequestParams();
        orderdata.addBodyParameter("token",Order121Application.globalLoginModel.getData().getToken());
        httpUtils.send(HttpRequest.HttpMethod.POST, G.Host.GET_Billlist,orderdata,new MyRequestCallBack(OrderMsgListActivity.this,handler,1,new OrderMsgLisrModel()));
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    dialog.dismiss();
                    plv_msglist.onRefreshComplete();
                    OrderMsgLisrModel orderMsgLisrModel=(OrderMsgLisrModel)msg.obj;
                    if (orderMsgLisrModel.getRespCode()==0){
                        List<OrderMsgLisrModel.DataBean.ListBean> list1=  orderMsgLisrModel.getData().getList();
                        if (isRefresh){
                            list.clear();
                            isRefresh=false;
                        }
                        list.addAll(list1);
                    adapter.notifyDataSetChanged();
                    }else {
                        ToastUtil.showShort(OrderMsgListActivity.this,orderMsgLisrModel.getRespMsg());
                    }
                    break;
            }

        }
    };
}
