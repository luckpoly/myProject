package com.brandsh.tiaoshi.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.OrderdetailItemAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.DeleteOlderJsonData;
import com.brandsh.tiaoshi.model.DiyShoppingCartJsonData;
import com.brandsh.tiaoshi.model.OrderDetailJsonData;
import com.brandsh.tiaoshi.model.OrderListJsondata1;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.DateFormatUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.OrderStatusUtil;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.widget.ProductDetailImgListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 猪猪~ on 2016/3/10.
 */
public class OrderDetailsActivity extends FragmentActivity implements View.OnClickListener {
    @ViewInject(R.id.product_detail_rlBack)
    RelativeLayout product_detail_rlBack;
    @ViewInject(R.id.tv_order_code)
    private TextView tv_order_code;
    @ViewInject(R.id.tv_store_name)
    private TextView tv_store_name;
    @ViewInject(R.id.tv_order_totalprice)
    private TextView tv_order_totalprice;
    @ViewInject(R.id.ll_call_store)
    private LinearLayout ll_call_store;
    @ViewInject(R.id.ll_call_service)
    private LinearLayout ll_call_service;
    @ViewInject(R.id.order_product_PDILV)
    ProductDetailImgListView order_product_PDILV;
    @ViewInject(R.id.tv_order_state)
    private TextView tv_order_state;
    @ViewInject(R.id.tv_order_time)
    private TextView tv_order_time;
    @ViewInject(R.id.tv_nickname)
    private TextView tv_nickname;
    @ViewInject(R.id.tv_tel)
    private TextView tv_tel;
    @ViewInject(R.id.tv_address)
    private TextView tv_address;
    @ViewInject(R.id.ll_sendway)
    private LinearLayout ll_sendway;
    @ViewInject(R.id.ll_address)
    private LinearLayout ll_address;
    @ViewInject(R.id.ll_phone)
    private LinearLayout ll_phone;
    @ViewInject(R.id.ll_name)
    private LinearLayout ll_name;
    @ViewInject(R.id.ll_go)
    private LinearLayout ll_go;
    @ViewInject(R.id.tv_sendway)
    private TextView tv_sendway;
    @ViewInject(R.id.tv_order_youhui)
    private TextView tv_order_youhui;
    @ViewInject(R.id.tv_order_peisong)
    private TextView tv_order_peisong;
    @ViewInject(R.id.tv_dailingqu)
    private TextView tv_dailingqu;
    @ViewInject(R.id.tv_tuikuan)
    private TextView tv_tuikuan;
    @ViewInject(R.id.tv_fenxiang)
    private TextView tv_fenxiang;
    @ViewInject(R.id.tv_yilingqu)
    private TextView tv_yilingqu;
    @ViewInject(R.id.tv_chongxinfenxiang)
    private TextView tv_chongxinfenxiang;
    @ViewInject(R.id.tv_chongxinfx)
    private TextView tv_chongxinfx;
    @ViewInject(R.id.bt_oldermore)
    private Button bt_oldermore;

    private String status;
    private OrderListJsondata1.DataBean.ListBean listEntity;
    private OrderdetailItemAdapter orderListItemAdapter;
    private List<OrderDetailJsonData.DataBean.OrderGoodsBean> resList = new ArrayList<>();
    private int count;
    private HashMap map;
    private ProgressDialog progDialog;
    private boolean isChanged;
    private OrderDetailJsonData orderDetailJsonData1;
    private String type = "";
    private String shareStatus = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        //沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);
        initview();

    }

    private void initview() {
        map = new HashMap();
        map.put("token", TiaoshiApplication.globalToken);
        map.put("orderId", getIntent().getStringExtra("OrderId"));
        map.put("actReq", "123456");
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.ORDER_DETAIL, map, new MyCallBack(2, OrderDetailsActivity.this, new OrderDetailJsonData(), handler));
        orderListItemAdapter = new OrderdetailItemAdapter(resList, this);
        order_product_PDILV.setAdapter(orderListItemAdapter);
        product_detail_rlBack.setOnClickListener(this);
        tv_store_name.setOnClickListener(this);
        ll_call_store.setOnClickListener(this);
        ll_call_service.setOnClickListener(this);
        tv_order_state.setOnClickListener(this);
        bt_oldermore.setOnClickListener(this);
        tv_fenxiang.setOnClickListener(this);
        tv_chongxinfx.setOnClickListener(this);
        tv_tuikuan.setOnClickListener(this);
        listEntity = (OrderListJsondata1.DataBean.ListBean) getIntent().getExtras().getSerializable("orderList");
        type = getIntent().getStringExtra("type");
        if (!TextUtils.isEmpty(getIntent().getStringExtra("shareStatus"))) {
            shareStatus = getIntent().getStringExtra("shareStatus");
        }
        if (null != type && type.equals("果汁分享")) {
            ll_address.setVisibility(View.GONE);
            ll_phone.setVisibility(View.GONE);
            ll_name.setVisibility(View.GONE);
            tv_store_name.setVisibility(View.GONE);
            ll_go.setVisibility(View.GONE);
        }


    }
    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.product_detail_rlBack:
                finish();
                break;
            case R.id.tv_store_name:
                Intent intent = new Intent(this, StoreDetailActivity.class);
                intent.putExtra("shop_id", getIntent().getStringExtra("shop_id"));
                intent.putExtra("shop_name", getIntent().getStringExtra("shop_name"));
                intent.putExtra("min_cost", getIntent().getStringExtra("min_cost"));
                startActivity(intent);
                break;
            case R.id.ll_call_service:
                callToOrder("02161553481", "联系客服", "客服电话：  ");
                break;
            case R.id.ll_call_store:
                callToOrder(orderDetailJsonData1.getData().getServiceTel(), "联系商铺", "商铺电话：  ");
                break;
            case R.id.tv_order_state:
                if (status.equals("待支付")) {
                    Intent intent1 = new Intent(OrderDetailsActivity.this, PayOrderActivity.class);
                    intent1.putExtra("order_code", orderDetailJsonData1.getData().getOrderCode() + "");
                    intent1.putExtra("orderId", orderDetailJsonData1.getData().getOrderId() + "");
                    intent1.putExtra("total", orderDetailJsonData1.getData().getTotal() + "");
                    intent1.putExtra("payOrderName", "从店铺 " + orderDetailJsonData1.getData().getShopName()
                            + " 中购买了 " + count
                            + " 件商品, 合计 " + orderDetailJsonData1.getData().getTotal() + " 元。");
                    intent1.putExtra("payOrderDetail", "付款来源: 安卓支付宝客户端,"
                            + "订单编号：" + orderDetailJsonData1.getData().getOrderCode() + ",购买数量："
                            + count
                            + ",合计：" + orderDetailJsonData1.getData().getTotal() + " 元。"
                            + "收货人姓名：" + orderDetailJsonData1.getData().getContact());
                    startActivityForResult(intent1, 1);
                } else if (status.equals("待评价")) {
                    Intent intent1 = new Intent(this, AddEvaluationActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order_detail", listEntity);
                    intent1.putExtras(bundle);
                    startActivityForResult(intent1, 0);
                }
                break;
            case R.id.bt_oldermore:
                int count = 0;
                List<DiyShoppingCartJsonData.GoodsListEntity> list1 = new LinkedList<>();
                List<OrderListJsondata1.DataBean.ListBean.OrderGoodsBean> list = listEntity.getOrderGoods();
                for (int i = 0; i < list.size(); i++) {
                    OrderListJsondata1.DataBean.ListBean.OrderGoodsBean o1 = list.get(i);
                    DiyShoppingCartJsonData.GoodsListEntity d1 = new DiyShoppingCartJsonData.GoodsListEntity();
                    d1.setGoods_id(o1.getGoodsId() + "");
                    int Goods_conunt = Integer.parseInt(o1.getGoodsCount() + "");
                    d1.setGoods_count(Goods_conunt);
                    count += Goods_conunt;
                    list1.add(d1);
                }
                TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(count);
                TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(Double.valueOf(listEntity.getTotal()));
                TiaoshiApplication.diyShoppingCartJsonData.setShop_id(listEntity.getShopId() + "");
                TiaoshiApplication.diyShoppingCartJsonData.setGoodsList(list1);
                Intent intent1 = new Intent(OrderDetailsActivity.this, StoreDetailActivity.class);
                intent1.putExtra("shop_id", listEntity.getShopId());
                intent1.putExtra("shop_name", listEntity.getShopName());
                intent1.putExtra("min_cost", listEntity.getFreeSend());
                intent1.putExtra("listolderNO", G.SP.LIST_Older);
                startActivity(intent1);
                break;
            case R.id.tv_tuikuan:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示:").setMessage("确认退款： 退款后十五天之内资金将返还到您的支付账户中...");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap map = new HashMap();
                        map.put("token", TiaoshiApplication.globalToken);
                        map.put("orderId", listEntity.getOrderId() + "");
                        map.put("reason", "取消订单");
                        map.put("actReq", SignUtil.getRandom());
                        map.put("actTime", System.currentTimeMillis() / 1000 + "");
                        String sign = SignUtil.getSign(map);
                        map.put("sign", Md5.toMd5(sign));
                        OkHttpManager.postAsync(G.Host.REFUND, map, new MyCallBack(5, OrderDetailsActivity.this, new DeleteOlderJsonData(), handler));
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("返回", null);
                builder.show();
                break;
            case R.id.tv_fenxiang:
                startActivity(new Intent(OrderDetailsActivity.this, FenxiangActivity.class).putExtra("id", listEntity.getOrderId() + ""));
                break;
            case R.id.tv_chongxinfx:
                startActivity(new Intent(OrderDetailsActivity.this, FenxiangActivity.class).putExtra("id", listEntity.getOrderId() + ""));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 0) {
            isChanged = true;
            tv_order_state.setText("已完成");
            tv_order_state.setTextColor(getResources().getColor(R.color.line));
            tv_order_state.getPaint().setFlags(0);
        } else if (requestCode == 1) {
            isChanged = true;
            tv_order_state.setText("待配送");
            tv_order_state.setTextColor(getResources().getColor(R.color.hui3));
            tv_order_state.getPaint().setFlags(0);
        }
    }

    private void callToOrder(final String phone, String name, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(name).setMessage(msg + phone).setNegativeButton("取消", null).setPositiveButton("拨打", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.CALL");
                intent.addCategory("android.intent.category.DEFAULT");
                //指定要拨打的电话号码
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        }).create().show();
    }

    @Override
    protected void onDestroy() {
        if (isChanged) {
            sendBroadcast(new Intent("refreshOrderList"));
        }
        super.onDestroy();
    }

    private String is_discount;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    orderDetailJsonData1 = (OrderDetailJsonData) msg.obj;
                    if (orderDetailJsonData1 != null) {
                        if (orderDetailJsonData1.getRespCode().equals("SUCCESS")) {
                            tv_order_code.setText(orderDetailJsonData1.getData().getOrderCode());
                            tv_store_name.setText(orderDetailJsonData1.getData().getShopName());
                            resList.addAll(orderDetailJsonData1.getData().getOrderGoods());
                            orderListItemAdapter.notifyDataSetChanged();
                            //获取商品总数
                            count = 0;
                            for (int i = 0; i < orderDetailJsonData1.getData().getOrderGoods().size(); i++) {
                                count = count + Integer.parseInt(orderDetailJsonData1.getData().getOrderGoods().get(i).getGoodsCount() + "");
                            }
                            tv_order_totalprice.setText("共 " + count + " 件,合计 ￥" + orderDetailJsonData1.getData().getTotal() + "元");
                            resList = orderDetailJsonData1.getData().getOrderGoods();
                            //时间
                            tv_order_time.setText(DateFormatUtil.formatDate(orderDetailJsonData1.getData().getCreateTime() + ""));
                            //联系人
                            tv_nickname.setText(orderDetailJsonData1.getData().getContact());
                            //联系电话
                            tv_tel.setText(orderDetailJsonData1.getData().getTel());
                            //联系地址
                            tv_address.setText(orderDetailJsonData1.getData().getAddress() + orderDetailJsonData1.getData().getAddressDetail());
                            //优惠金额
                            if (!TextUtils.isEmpty(orderDetailJsonData1.getData().getPlatformDiscountPrice())) {
                                tv_order_youhui.setText("优惠券：-￥" + orderDetailJsonData1.getData().getPlatformDiscountPrice());
                            }
                            //配送费
                            if (!TextUtils.isEmpty(orderDetailJsonData1.getData().getUserDeliveryPrice())) {
                                tv_order_peisong.setText("配送费： ￥" + orderDetailJsonData1.getData().getUserDeliveryPrice());
                            }

                            OrderDetailJsonData.DataBean listEntity1 = orderDetailJsonData1.getData();
                            status = OrderStatusUtil.getOrderStatus(listEntity1.getStatus(), listEntity1.getDeliveryStatus(), listEntity1.getRefundStatus(), listEntity1.getCancelStatus(), listEntity1.getEvaluateStatus());
                            switch (status) {
                                case "待支付":
                                    tv_order_state.setText("去付款");
                                    tv_order_state.setTextColor(getResources().getColor(R.color.hong));
                                    tv_order_state.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                                    ll_sendway.setVisibility(View.GONE);
                                    break;
                                case "待配送":
                                    tv_order_state.setText("待配送");
                                    tv_order_state.setTextColor(getResources().getColor(R.color.hui3));
                                    ll_sendway.setVisibility(View.GONE);
                                    switch (shareStatus) {
                                        case "UNSHARE":
                                            tv_fenxiang.setVisibility(View.VISIBLE);
                                            break;
                                        case "STAY":
                                            tv_dailingqu.setVisibility(View.VISIBLE);
                                            tv_fenxiang.setVisibility(View.VISIBLE);
                                            break;
                                        case "GET":
                                            tv_yilingqu.setVisibility(View.VISIBLE);
                                            break;
                                        case "SUCCESS":
                                            tv_yilingqu.setVisibility(View.VISIBLE);
                                            break;
                                        case "FAILURE":
                                            tv_chongxinfenxiang.setVisibility(View.VISIBLE);
                                            tv_tuikuan.setVisibility(View.VISIBLE);
                                            tv_chongxinfx.setVisibility(View.VISIBLE);
                                            break;
                                    }
                                    break;
                                case "配送中":
                                    tv_order_state.setTextColor(getResources().getColor(R.color.hui3));
                                    tv_order_state.setText("配送中");
                                    ll_sendway.setVisibility(View.VISIBLE);
                                    if ("SELF".equals(orderDetailJsonData1.getData().getDeliveryWay())) {
                                        tv_sendway.setText("商家配送");
                                    } else if ("PLATFROM".equals(orderDetailJsonData1.getData().getDeliveryWay())) {
                                        tv_sendway.setText("121配送");
                                    }
                                    break;
                                case "待评价":
                                    tv_order_state.setText("去评价");
                                    tv_order_state.setTextColor(getResources().getColor(R.color.hong));
                                    tv_order_state.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                                    ll_sendway.setVisibility(View.VISIBLE);
                                    if ("SELF".equals(orderDetailJsonData1.getData().getDeliveryWay())) {
                                        tv_sendway.setText("商家配送");
                                    } else if ("PLATFROM".equals(orderDetailJsonData1.getData().getDeliveryWay())) {
                                        tv_sendway.setText("121配送");
                                    }
                                    break;
                                case "已完成":
                                    tv_order_state.setText("已完成");
                                    tv_order_state.setTextColor(getResources().getColor(R.color.line));
                                    ll_sendway.setVisibility(View.VISIBLE);
                                    bt_oldermore.setVisibility(View.GONE);
                                    if ("SELF".equals(orderDetailJsonData1.getData().getDeliveryWay())) {
                                        tv_sendway.setText("商家配送");
                                    } else if ("PLATFROM".equals(orderDetailJsonData1.getData().getDeliveryWay())) {
                                        tv_sendway.setText("121配送");
                                    }
                                    break;
                                case "退款售后":
                                    tv_order_state.setText("退款/售后");
                                    tv_order_state.setTextColor(getResources().getColor(R.color.hui3));
                                    break;
                                case "订单异常":
                                    tv_order_state.setText("订单异常");
                                    tv_order_state.setTextColor(getResources().getColor(R.color.hui3));
                                    break;
                                case "订单已取消":
                                    tv_order_state.setText("已取消");
                                    tv_order_state.setTextColor(getResources().getColor(R.color.hui3));
                                    break;
                                case "配送完成":
                                    tv_order_state.setText("配送完成");
                                    tv_order_state.setTextColor(getResources().getColor(R.color.hui3));
                                    break;
                                case "退款中":
                                    tv_order_state.setText("退款中");
                                    tv_order_state.setTextColor(getResources().getColor(R.color.hui3));
                                    break;
                                case "退款完成":
                                    tv_order_state.setText("退款完成");
                                    tv_order_state.setTextColor(getResources().getColor(R.color.hui3));
                                    break;

                            }
                        }
                    }
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
}
