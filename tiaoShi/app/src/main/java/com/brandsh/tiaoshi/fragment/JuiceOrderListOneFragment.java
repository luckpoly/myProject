package com.brandsh.tiaoshi.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.activity.OrderDetailsActivity;
import com.brandsh.tiaoshi.adapter.JuiceOrderListAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.DeleteOlderJsonData;
import com.brandsh.tiaoshi.model.OrderListJsondata1;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.brandsh.tiaoshi.widget.SelfPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mingle.widget.ShapeLoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JuiceOrderListOneFragment extends Fragment {
    @ViewInject(R.id.my_order_ptrListView)
    SelfPullToRefreshListView my_order_ptrListView;
    @ViewInject(R.id.message_list_rlNoItem)
    RelativeLayout message_list_rlNoItem;
    private HashMap requestMap;
    private ShapeLoadingDialog loadingDialog;
    private List<OrderListJsondata1.DataBean.ListBean> resList;
    private JuiceOrderListAdapter orderListAdapter;
    private RefreshBDReceiver refreshBDReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.activity_myjuice_order,null);
        ViewUtils.inject(this,view);
        init();
        setListenerToptrListView();
        return view;


    }
    private class RefreshBDReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("refreshOrderList".equals(intent.getAction())){
                page = "1";
                OkHttpManager.postAsync(G.Host.MY_ORDER + "?page=" + page, requestMap, new MyCallBack(3, getActivity(), new OrderListJsondata1(), handler));
            }
        }
    }
    private void setListenerToptrListView() {
        my_order_ptrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("orderList",resList.get(position-1));
                intent.putExtras(bundle);
                intent.putExtra("OrderId", resList.get(position-1).getOrderId()+"") ;
                intent.putExtra("shop_id", resList.get(position-1).getShopId()+"");
                intent.putExtra("shop_name", resList.get(position-1).getShopName());
                intent.putExtra("min_cost", resList.get(position-1).getFreeSend());
                intent.putExtra("type", "果汁分享");
                intent.putExtra("shareStatus", resList.get(position-1).getShareStatus());
                startActivity(intent);
            }
        });
        my_order_ptrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = "1";
                OkHttpManager.postAsync(G.Host.MY_JUICE_ORDER + "?page=" + page, requestMap, new MyCallBack(3, getActivity(), new OrderListJsondata1(), handler));
                handler.sendEmptyMessageDelayed(150, 1000);
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                OkHttpManager.postAsync(G.Host.MY_JUICE_ORDER + "?page=" + page, requestMap, new MyCallBack(2, getActivity(), new OrderListJsondata1(), handler));
                handler.sendEmptyMessageDelayed(150, 5000);
            }
        });
    }
    private void init() {
        page = "1";
        requestMap=new HashMap();
        requestMap.put("token", TiaoshiApplication.globalToken);
        requestMap.put("actReq", SignUtil.getRandom());
        requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        requestMap.put("sign", Md5.toMd5(SignUtil.getSign(requestMap)));
        OkHttpManager.postAsync(G.Host.MY_JUICE_ORDER + "?page=" + page,requestMap,new MyCallBack(3, getActivity(), new OrderListJsondata1(), handler));
        loadingDialog = ProgressHUD.show(getActivity(), "努力加载中...");
        loadingDialog.show();
        resList = new ArrayList<>();
        orderListAdapter = new JuiceOrderListAdapter(resList, getActivity(),handler);
        my_order_ptrListView.setAdapter(orderListAdapter);
        my_order_ptrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        refreshBDReceiver = new RefreshBDReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("refreshOrderList");
        getActivity().registerReceiver(refreshBDReceiver,intentFilter);
    }
    private int result = -1;
    private String page;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 3:
                    result = orderListAdapter.countDownTime();
                    my_order_ptrListView.onRefreshComplete();
                    OrderListJsondata1 orderListJsondata = (OrderListJsondata1) msg.obj;
                    if (orderListJsondata != null) {
                        if (orderListJsondata.getRespCode().equals("SUCCESS")) {
                            page = orderListJsondata.getData().getNextPage() + "";
                            resList.clear();
                            resList.addAll(orderListJsondata.getData().getList());
                            if (resList.size() >= Integer.parseInt(orderListJsondata.getData().getTotalCount())) {
                                my_order_ptrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//                                Toast.makeText(getActivity(), "已经是最后一页了", Toast.LENGTH_SHORT).show();
                            } else {
                                my_order_ptrListView.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                            if (resList.size()==0){
                                message_list_rlNoItem.setVisibility(View.VISIBLE);
                                my_order_ptrListView.setVisibility(View.GONE);
                            } else {
                            message_list_rlNoItem.setVisibility(View.GONE);
                                my_order_ptrListView.setVisibility(View.VISIBLE);}

                            orderListAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), orderListJsondata.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (result == 0) {
                        handler.removeMessages(3);
                        return;
                    } else {
                        orderListAdapter.notifyDataSetChanged();
                        handler.sendEmptyMessageDelayed(3, 1000);
                    }
                    loadingDialog.dismiss();
                    break;
                case 2:
                    my_order_ptrListView.onRefreshComplete();
                    OrderListJsondata1 orderListJsondata2 = (OrderListJsondata1) msg.obj;
                    if (orderListJsondata2 != null) {
                        if (orderListJsondata2.getRespCode().equals("SUCCESS")) {
                            page = orderListJsondata2.getData().getNextPage() + "";
                            resList.addAll(orderListJsondata2.getData().getList());
                            if (resList.size() >= Integer.parseInt(orderListJsondata2.getData().getTotalCount())) {
                                my_order_ptrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            } else if(orderListJsondata2.getData().getList().size()<orderListJsondata2.getData().getLimit()) {
                                my_order_ptrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }else {
                                my_order_ptrListView.setMode(PullToRefreshBase.Mode.BOTH);

                            }
                            orderListAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), orderListJsondata2.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 5:
                    DeleteOlderJsonData deleteOlderJsonData= (DeleteOlderJsonData) msg.obj;
                    if (deleteOlderJsonData!=null){
                        if ("SUCCESS".equals(deleteOlderJsonData.getRespCode())){
                            OkHttpManager.postAsync(G.Host.MY_JUICE_ORDER , requestMap, new MyCallBack(3, getActivity(), new OrderListJsondata1(), handler));
                        }else {
                            ToastUtil.showShort(getActivity(),deleteOlderJsonData.getRespMsg());
                        }
                    }
                    break;
                case 150:
                    my_order_ptrListView.onRefreshComplete();
                    break;
                case 200:
                    my_order_ptrListView.onRefreshComplete();
                    break;
                case 300:
                    my_order_ptrListView.onRefreshComplete();
                    break;
            }
        }
    };
    @Override
    public void onDestroy() {
       getActivity().unregisterReceiver(refreshBDReceiver);
        super.onDestroy();
    }
}
