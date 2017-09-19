package com.brandsh.tiaoshi.activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.OrderListAdapter;
import com.brandsh.tiaoshi.adapter.OrderTypeAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.DeleteOlderJsonData;
import com.brandsh.tiaoshi.model.OrderListJsondata1;
import com.brandsh.tiaoshi.model.OrderTypeModel;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.brandsh.tiaoshi.widget.SelfPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mingle.widget.ShapeLoadingDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderListActivity extends FragmentActivity implements View.OnClickListener {
    @ViewInject(R.id.my_order_ivBack)
    ImageView my_order_ivBack;
    @ViewInject(R.id.my_order_ptrListView)
    SelfPullToRefreshListView my_order_ptrListView;
    @ViewInject(R.id.message_list_rlNoItem)
    RelativeLayout message_list_rlNoItem;
    @ViewInject(R.id.tv_order_type)
    TextView tv_order_type;
    @ViewInject(R.id.tv_action)
    TextView tv_action;
    @ViewInject(R.id.search_et)
    EditText search_et;
    private HttpUtils httpUtils;
    private HashMap requestMap;
    private ShapeLoadingDialog loadingDialog;
    private List<OrderListJsondata1.DataBean.ListBean> resList;
    private OrderListAdapter orderListAdapter;
    private RefreshBDReceiver refreshBDReceiver;
    List<OrderTypeModel> list;
    String customStatus;
    String search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        //沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);
        setOrderType();
        init();
        initData();
        setListenerToptrListView();
    }
    private class RefreshBDReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("refreshOrderList".equals(intent.getAction())){
                page = "1";
                OkHttpManager.postAsync(G.Host.MY_ORDER + "?page=" + page, requestMap, new MyCallBack(1, OrderListActivity.this, new OrderListJsondata1(), handler));
            }
        }
    }
    private void setListenerToptrListView() {
        my_order_ptrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OrderListActivity.this, OrderDetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("orderList",resList.get(position-1));
                intent.putExtras(bundle);
                intent.putExtra("OrderId", resList.get(position-1).getOrderId()+"") ;
                intent.putExtra("shop_id", resList.get(position-1).getShopId()+"");
                intent.putExtra("shop_name", resList.get(position-1).getShopName());
                intent.putExtra("min_cost", resList.get(position-1).getFreeSend());
                startActivity(intent);
            }
        });
        my_order_ptrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = "1";
                OkHttpManager.postAsync(G.Host.MY_ORDER + "?page=" + page, requestMap, new MyCallBack(1, OrderListActivity.this, new OrderListJsondata1(), handler));
                handler.sendEmptyMessageDelayed(150, 1000);
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                OkHttpManager.postAsync(G.Host.MY_ORDER + "?page=" + page, requestMap, new MyCallBack(2, OrderListActivity.this, new OrderListJsondata1(), handler));
                handler.sendEmptyMessageDelayed(150, 5000);
            }
        });
    }

    private void init() {
         customStatus= getIntent().getStringExtra("customStatus");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType().equals(customStatus)){
                tv_order_type.setText(list.get(i).getName()+"•••");
            }
        }
        my_order_ivBack.setOnClickListener(this);
        tv_order_type.setOnClickListener(this);
        tv_action.setOnClickListener(this);
        loadingDialog = ProgressHUD.show(this, "订单列表加载中...");
        loadingDialog.show();
        resList = new ArrayList<>();
        orderListAdapter = new OrderListAdapter(resList, this,handler);
        my_order_ptrListView.setAdapter(orderListAdapter);
        my_order_ptrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        refreshBDReceiver = new RefreshBDReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("refreshOrderList");
        registerReceiver(refreshBDReceiver,intentFilter);
    }
    private void initData(){
        page = "1";
        requestMap=new HashMap();
        requestMap.put("token", TiaoshiApplication.globalToken);
        requestMap.put("customStatus", customStatus);
        if (!TextUtils.isEmpty(search)){
            requestMap.put("goodsName", search);
        }
        requestMap.put("actReq", SignUtil.getRandom());
        requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        requestMap.put("sign", Md5.toMd5(SignUtil.getSign(requestMap)));
        OkHttpManager.postAsync(G.Host.MY_ORDER + "?page=" + page,requestMap,new MyCallBack(1, OrderListActivity.this, new OrderListJsondata1(), handler));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_order_ivBack:
                finish();
                break;
            case R.id.tv_order_type:
                showPopupWindow();
                break;
            case R.id.tv_action:
                search=search_et.getText().toString().trim();
                page="1";
                initData();
                break;
        }
    }
    private void setOrderType(){
        list=new ArrayList<>();
        OrderTypeModel model=new OrderTypeModel(getResources().getDrawable(R.mipmap.myorder_all),"全部订单","ALL");
        list.add(model);
        OrderTypeModel model1=new OrderTypeModel(getResources().getDrawable(R.mipmap.myorder_dfk),"待付款","PAYING");
        list.add(model1);
        OrderTypeModel model2=new OrderTypeModel(getResources().getDrawable(R.mipmap.myorder_dfh),"待配送","PRE");
        list.add(model2);
        OrderTypeModel model3=new OrderTypeModel(getResources().getDrawable(R.mipmap.myorder_psz),"配送中","SENDING");
        list.add(model3);
        OrderTypeModel model4=new OrderTypeModel(getResources().getDrawable(R.mipmap.myorder_dpj),"待评价","EVALUATE");
        list.add(model4);
        OrderTypeModel model5=new OrderTypeModel(getResources().getDrawable(R.mipmap.myorder_ywc),"已完成","OK");
        list.add(model5);
        OrderTypeModel model6=new OrderTypeModel(getResources().getDrawable(R.mipmap.myorder_tksh),"退款/售后","AFTER");
        list.add(model6);
    }
    private void showPopupWindow(){
        View view= LayoutInflater.from(this).inflate(R.layout.popup_ordertype_list,null);
        final PopupWindow popupWindow=new PopupWindow(view,  RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        ListView listView= (ListView) view.findViewById(R.id.ll_phone_tj);
        listView.setAdapter(new OrderTypeAdapter(list,OrderListActivity.this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                customStatus=list.get(position).getType();
                tv_order_type.setText(list.get(position).getName()+"•••");
                initData();
                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(tv_order_type);
    }
    private int result = -1;
    private String page;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
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
                            Toast.makeText(OrderListActivity.this, orderListJsondata.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (result == 0) {
                        handler.removeMessages(1);
                        return;
                    } else {
                        orderListAdapter.notifyDataSetChanged();
                        handler.sendEmptyMessageDelayed(1, 1000);
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
                                Toast.makeText(OrderListActivity.this, "已经是最后一页了", Toast.LENGTH_SHORT).show();
                            } else if(orderListJsondata2.getData().getList().size()<orderListJsondata2.getData().getLimit()) {
                                my_order_ptrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                Toast.makeText(OrderListActivity.this, "已经是最后一页了", Toast.LENGTH_SHORT).show();
                            }else {
                                my_order_ptrListView.setMode(PullToRefreshBase.Mode.BOTH);

                            }
                            orderListAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(OrderListActivity.this, orderListJsondata2.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 5:
                    DeleteOlderJsonData deleteOlderJsonData= (DeleteOlderJsonData) msg.obj;
                    if (deleteOlderJsonData!=null){
                        if ("SUCCESS".equals(deleteOlderJsonData.getRespCode())){
                            ToastUtil.showShort(OrderListActivity.this,deleteOlderJsonData.getRespMsg());
                            OkHttpManager.postAsync(G.Host.MY_ORDER , requestMap, new MyCallBack(1, OrderListActivity.this, new OrderListJsondata1(), handler));
                        }else {
                            ToastUtil.showShort(OrderListActivity.this,deleteOlderJsonData.getRespMsg());
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
    protected void onDestroy() {
        unregisterReceiver(refreshBDReceiver);
        super.onDestroy();
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
