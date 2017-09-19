package com.brandsh.tiaoshi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.MonthListAdapter;
import com.brandsh.tiaoshi.adapter.MonthListFenAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.DeleteOlderJsonData;
import com.brandsh.tiaoshi.model.MonthOrderListModel;
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

public class MonthFenOrderListActivity extends FragmentActivity implements View.OnClickListener {
    @ViewInject(R.id.my_order_ivBack)
    ImageView my_order_ivBack;
    @ViewInject(R.id.my_order_ptrListView)
    SelfPullToRefreshListView my_order_ptrListView;
    @ViewInject(R.id.message_list_rlNoItem)
    RelativeLayout message_list_rlNoItem;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    private HttpUtils httpUtils;
    private HashMap requestMap;
    private ShapeLoadingDialog loadingDialog;
    private List<MonthOrderListModel.DataBean.ListBean.OrderPartialBean> resList;
    private MonthListFenAdapter monthListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthorder);
        //沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);
        init();
        setListenerToptrListView();
    }
    private void setListenerToptrListView() {
        my_order_ptrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        my_order_ptrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = "1";
                OkHttpManager.postAsync(G.Host.MONTHORDER + "?page=" + page, requestMap, new MyCallBack(1, MonthFenOrderListActivity.this, new MonthOrderListModel(), handler));
                handler.sendEmptyMessageDelayed(150, 1000);
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                OkHttpManager.postAsync(G.Host.MONTHORDER + "?page=" + page, requestMap, new MyCallBack(2, MonthFenOrderListActivity.this, new MonthOrderListModel(), handler));
                handler.sendEmptyMessageDelayed(150, 5000);
            }
        });
    }

    private void init() {
        page = "1";
        my_order_ivBack.setOnClickListener(this);
        tv_title.setText("已配送订单");
        requestMap=new HashMap();
        requestMap.put("token", TiaoshiApplication.globalToken);
        requestMap.put("orderType", "MONTH");
        requestMap.put("orderId", getIntent().getStringExtra("orderId"));
        requestMap.put("actReq", SignUtil.getRandom());
        requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        requestMap.put("sign", Md5.toMd5(SignUtil.getSign(requestMap)));
        OkHttpManager.postAsync(G.Host.MONTHORDER + "?page=" + page,requestMap,new MyCallBack(1, MonthFenOrderListActivity.this, new MonthOrderListModel(), handler));
        loadingDialog = ProgressHUD.show(this, "订单列表加载中...");
        loadingDialog.show();
        resList = new ArrayList<>();
        monthListAdapter = new MonthListFenAdapter(this,resList);
        my_order_ptrListView.setAdapter(monthListAdapter);
        my_order_ptrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_order_ivBack:
                finish();
                break;
        }
    }
    private int result = -1;
    private String page;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    my_order_ptrListView.onRefreshComplete();
                    MonthOrderListModel orderListJsondata = (MonthOrderListModel) msg.obj;
                    if (orderListJsondata != null) {
                        if (orderListJsondata.getRespCode().equals("SUCCESS")) {
                            page = orderListJsondata.getData().getNextPage() + "";
                            resList.clear();
                            resList.addAll(orderListJsondata.getData().getList().get(0).getOrderPartial());
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

                            monthListAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(MonthFenOrderListActivity.this, orderListJsondata.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    loadingDialog.dismiss();
                    break;
                case 2:
                    my_order_ptrListView.onRefreshComplete();
                    MonthOrderListModel orderListJsondata2 = (MonthOrderListModel) msg.obj;
                    if (orderListJsondata2 != null) {
                        if (orderListJsondata2.getRespCode().equals("SUCCESS")) {
                            page = orderListJsondata2.getData().getNextPage() + "";
                            resList.addAll(orderListJsondata2.getData().getList().get(0).getOrderPartial());
                            if (resList.size() >= Integer.parseInt(orderListJsondata2.getData().getTotalCount())) {
                                my_order_ptrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                Toast.makeText(MonthFenOrderListActivity.this, "已经是最后一页了", Toast.LENGTH_SHORT).show();
                            } else if(orderListJsondata2.getData().getList().size()<orderListJsondata2.getData().getLimit()) {
                                my_order_ptrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                Toast.makeText(MonthFenOrderListActivity.this, "已经是最后一页了", Toast.LENGTH_SHORT).show();
                            }else {
                                my_order_ptrListView.setMode(PullToRefreshBase.Mode.BOTH);

                            }
                            monthListAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(MonthFenOrderListActivity.this, orderListJsondata2.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 5:
                    DeleteOlderJsonData deleteOlderJsonData= (DeleteOlderJsonData) msg.obj;
                    if (deleteOlderJsonData!=null){
                        if ("SUCCESS".equals(deleteOlderJsonData.getRespCode())){
                            ToastUtil.showShort(MonthFenOrderListActivity.this,deleteOlderJsonData.getRespMsg());
                            OkHttpManager.postAsync(G.Host.MY_ORDER , requestMap, new MyCallBack(1, MonthFenOrderListActivity.this, new MonthOrderListModel(), handler));
                        }else {
                            ToastUtil.showShort(MonthFenOrderListActivity.this,deleteOlderJsonData.getRespMsg());
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
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        page = "1";
        OkHttpManager.postAsync(G.Host.MONTHORDER + "?page=" + page, requestMap, new MyCallBack(1, MonthFenOrderListActivity.this, new MonthOrderListModel(), handler));
        handler.sendEmptyMessageDelayed(150, 1000);
    }
}
