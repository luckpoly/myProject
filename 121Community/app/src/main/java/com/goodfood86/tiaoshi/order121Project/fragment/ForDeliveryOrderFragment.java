package com.goodfood86.tiaoshi.order121Project.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.activity.OrderDetailActivity;
import com.goodfood86.tiaoshi.order121Project.adapter.OrderAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.OrderListModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.widget.ProgressHUD;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public class ForDeliveryOrderFragment extends Fragment {
    @ViewInject(R.id.for_delivery_PTRListView)
    private PullToRefreshListView for_delivery_PTRListView;
    private List<OrderListModel.DataEntity.ListEntity> orderList=new ArrayList<>();
    private ProgressHUD dialog;
    private boolean isRefresh=false;
    private int mPage=1;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg!=null){
                switch (msg.what){
                    case 1:
                        dialog.dismiss();
                        for_delivery_PTRListView.onRefreshComplete();
                        OrderListModel orderListModel= (OrderListModel) msg.obj;
                        if (orderListModel.getRespCode()==0){
                            List<OrderListModel.DataEntity.ListEntity> newData=orderListModel.getData().getList();
                            if (isRefresh){
                                orderList.clear();
                                isRefresh=false;
                            }
                            orderList.addAll(newData);
                            Log.e("size",orderList.size()+"");
                            if (orderList.size()<orderListModel.getData().getTotal()){
                                for_delivery_PTRListView.setMode(PullToRefreshBase.Mode.BOTH);
                            }else {
                                for_delivery_PTRListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                            orderAdapter.notifyDataSetChanged();
                        }
                        break;
                    case 100:
                        dialog.dismiss();
                        for_delivery_PTRListView.onRefreshComplete();
                        break;
                }
            }

        }
    };
    private OrderAdapter orderAdapter;
    private MyFragmentBroadcastReciver myFragmentBroadcastReciver;

    private void registerReceiver() {
        myFragmentBroadcastReciver = new MyFragmentBroadcastReciver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("updateOrder");
        getActivity().registerReceiver(myFragmentBroadcastReciver, intentFilter);
    }

    public class MyFragmentBroadcastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("updateOrder".equals(intent.getAction())) {
                isRefresh=true;
                mPage=1;
                initData();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(myFragmentBroadcastReciver);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fordeliveryorderfragment,null);
        ViewUtils.inject(this,view);
        initAdapter();
        registerReceiver();
        initData();
        initListener();
        initIndicator();

        setListenerToPTRListView();
        return view;

    }

    private void initListener() {
        for_delivery_PTRListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtra("statusPay",orderList.get(position-1).getStatusPay());
                intent.putExtra("orderNo",orderList.get(position-1).getOrderNo());
                intent.putExtra("orderTime",orderList.get(position-1).getCreateTime());
                intent.putExtra("totalPrice",orderList.get(position-1).getCommission()+"");
                intent.putExtra("unit",orderList.get(position-1).getOrderDistance()+"公里/"+orderList.get(position-1).getProduct().get(0).getProductUnit());
                intent.putExtra("sendAddress",orderList.get(position-1).getSenderAddress()+orderList.get(position-1).getSenderAddressDetail());
                intent.putExtra("sendInfo",orderList.get(position-1).getSenderName()+"    "+orderList.get(position-1).getSenderPhone());
                intent.putExtra("receiveAddress",orderList.get(position-1).getRecipientAddress()+orderList.get(position-1).getRecipientAddressDetail());
                intent.putExtra("receiveInfo",orderList.get(position-1).getRecipientName()+"    "+orderList.get(position-1).getRecipientPhone());
                intent.putExtra("thingName",orderList.get(position-1).getProduct().get(0).getProductName());
                intent.putExtra("beizhu",orderList.get(position-1).getDesc());
                startActivity(intent);
            }
        });
    }

    private void initData() {
        RequestParams requestParams=new RequestParams();
        requestParams.addBodyParameter("statusPay","2");
        requestParams.addBodyParameter("status","0");
        requestParams.addBodyParameter("page",mPage+"");
        requestParams.addBodyParameter("token", Order121Application.globalLoginModel.getData().getToken());
        Order121Application.getGlobalHttpUtils().send(HttpRequest.HttpMethod.POST, G.Host.ORDER_LIST,requestParams,new MyRequestCallBack(getActivity(),handler,1,new OrderListModel()));
    }

    private void initAdapter() {
        orderAdapter=new OrderAdapter(orderList,getActivity());
        for_delivery_PTRListView.setAdapter(orderAdapter);
        dialog= ProgressHUD.show(getActivity(),"正在加载中",false,null);
        dialog.show();

    }
    private void initIndicator() {
        ILoadingLayout startLabels = for_delivery_PTRListView
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在载入...");// 刷新时
        startLabels.setReleaseLabel("放开以刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = for_delivery_PTRListView.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉加载更多...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在载入...");// 刷新时
        endLabels.setReleaseLabel("放开以刷新...");// 下来达到一定距离时，显示的提示
    }

    private void setRefreshDate(PullToRefreshBase<ListView> refreshView) {
        //放置在刷新时的监听中
        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("上次刷新 " + DateUtils.formatDateTime(
                getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL));
    }

    private void setListenerToPTRListView() {
        for_delivery_PTRListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                setRefreshDate(refreshView);
                isRefresh=true;
                mPage=1;
                initData();
                handler.sendEmptyMessageDelayed(100,3000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                setRefreshDate(refreshView);
                mPage++;
                initData();
                handler.sendEmptyMessageDelayed(100,3000);
            }
        });
    }
}
