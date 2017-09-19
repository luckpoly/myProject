package com.brandsh.tiaoshi.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.activity.StoreDetailActivity;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.DeleteOlderJsonData;
import com.brandsh.tiaoshi.model.DiyShoppingCartJsonData;
import com.brandsh.tiaoshi.model.OrderListJsondata1;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.OrderStatusUtil;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.widget.OrderListItemListView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by sisi on 16/3/10.
 */
public class OrderListAdapter extends BaseAdapter {
    private List<OrderListJsondata1.DataBean.ListBean> resList;
    private Context context;
    private OrderListJsondata1.DataBean.ListBean listEntity;
    private Handler handler;


    public OrderListAdapter(List<OrderListJsondata1.DataBean.ListBean> resList, Context context, Handler hander) {
        this.resList = resList;
        this.context = context;
        this.handler = hander;
    }

    @Override
    public int getCount() {
        if (resList != null) {
            return resList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return resList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final List<OrderListJsondata1.DataBean.ListBean.OrderGoodsBean> list;
        final OrderListJsondata1.DataBean.ListBean listEntity = resList.get(position);
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.order_list_item, null);
            viewHolder.order_list_item_tvOrderNumber = (TextView) convertView.findViewById(R.id.order_list_item_tvOrderNumber);
            viewHolder.order_list_item_tvOrderStatus = (TextView) convertView.findViewById(R.id.order_list_item_tvOrderStatus);
            viewHolder.order_list_item_lv = (OrderListItemListView) convertView.findViewById(R.id.order_list_item_lv);
            viewHolder.order_list_item_tvTotal = (TextView) convertView.findViewById(R.id.order_list_item_tvTotal);
            viewHolder.tv_oldermore = (TextView) convertView.findViewById(R.id.tv_oldermore);
            viewHolder.tv_oldedelete = (TextView) convertView.findViewById(R.id.tv_oldedelete);
            viewHolder.tv_ordercancel = (TextView) convertView.findViewById(R.id.tv_ordercancel);
            viewHolder.tv_Confirm_Payment = (TextView) convertView.findViewById(R.id.tv_Confirm_Payment);
            viewHolder.tv_refund = (TextView) convertView.findViewById(R.id.tv_refund);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.order_list_item_tvOrderNumber.setText("订单编号:" + listEntity.getOrderCode());
        //设置订单状态
        switch (OrderStatusUtil.getOrderStatus(listEntity.getStatus(),listEntity.getDeliveryStatus(),listEntity.getRefundStatus(),listEntity.getCancelStatus(),listEntity.getEvaluateStatus())){
            case "待支付":
                viewHolder.order_list_item_tvOrderStatus.setText("去支付");
                viewHolder.order_list_item_tvOrderStatus.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                viewHolder.order_list_item_tvOrderStatus.setTextColor(context.getResources().getColor(R.color.hong));
                viewHolder.tv_oldermore.setVisibility(View.GONE);
                viewHolder.tv_time.setVisibility(View.VISIBLE);
                viewHolder.tv_oldedelete.setVisibility(View.VISIBLE);
                viewHolder.tv_ordercancel.setVisibility(View.GONE);
                viewHolder.tv_refund.setVisibility(View.GONE);
                final int time = 1800 - (int) (System.currentTimeMillis() / 1000 - listEntity.getCreateTime());
                listEntity.setTime1(time);
                if (time > 0) {
                    viewHolder.tv_time.setText(getTimeStr(time) + "");
                } else {
                    viewHolder.tv_time.setText("");
                }
                break;
            case "待配送":
                viewHolder.order_list_item_tvOrderStatus.setText("待配送");
                viewHolder.order_list_item_tvOrderStatus.getPaint().setFlags(0);
                viewHolder.order_list_item_tvOrderStatus.setTextColor(context.getResources().getColor(R.color.hui3));
                viewHolder.tv_oldermore.setVisibility(View.GONE);
                viewHolder.tv_oldedelete.setVisibility(View.GONE);
                viewHolder.tv_time.setVisibility(View.GONE);
                viewHolder.tv_ordercancel.setVisibility(View.GONE);
                viewHolder.tv_Confirm_Payment.setVisibility(View.GONE);
                viewHolder.tv_refund.setVisibility(View.VISIBLE);
                break;
            case "配送中":
                viewHolder.order_list_item_tvOrderStatus.setText("配送中");
                viewHolder.order_list_item_tvOrderStatus.getPaint().setFlags(0);
                viewHolder.order_list_item_tvOrderStatus.setTextColor(context.getResources().getColor(R.color.hui3));
                viewHolder.tv_oldermore.setVisibility(View.GONE);
                viewHolder.tv_oldedelete.setVisibility(View.GONE);
                viewHolder.tv_time.setVisibility(View.GONE);
                viewHolder.tv_ordercancel.setVisibility(View.GONE);
                viewHolder.tv_Confirm_Payment.setVisibility(View.GONE);
                viewHolder.tv_refund.setVisibility(View.GONE);
                break;
            case "待评价":
                viewHolder.order_list_item_tvOrderStatus.setText("待评价");
                viewHolder.order_list_item_tvOrderStatus.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                viewHolder.order_list_item_tvOrderStatus.setTextColor(context.getResources().getColor(R.color.hong));
                viewHolder.tv_oldermore.setVisibility(View.VISIBLE);
                viewHolder.tv_oldedelete.setVisibility(View.GONE);
                viewHolder.tv_time.setVisibility(View.GONE);
                viewHolder.tv_ordercancel.setVisibility(View.GONE);
                viewHolder.tv_Confirm_Payment.setVisibility(View.GONE);
                viewHolder.tv_refund.setVisibility(View.GONE);
                break;
            case "已完成":
                viewHolder.order_list_item_tvOrderStatus.setText("已完成");
                viewHolder.order_list_item_tvOrderStatus.getPaint().setFlags(0);
                viewHolder.order_list_item_tvOrderStatus.setTextColor(context.getResources().getColor(R.color.line));
                viewHolder.tv_oldermore.setVisibility(View.VISIBLE);
                viewHolder.tv_oldedelete.setVisibility(View.VISIBLE);
                viewHolder.tv_time.setVisibility(View.GONE);
                viewHolder.tv_ordercancel.setVisibility(View.GONE);
                viewHolder.tv_Confirm_Payment.setVisibility(View.GONE);
                viewHolder.tv_refund.setVisibility(View.GONE);
                break;
            case "退款售后":
                viewHolder.order_list_item_tvOrderStatus.setText("退款/售后");
                viewHolder.tv_oldermore.setVisibility(View.GONE);
                viewHolder.tv_oldedelete.setVisibility(View.GONE);
                viewHolder.tv_time.setVisibility(View.GONE);
                viewHolder.tv_ordercancel.setVisibility(View.GONE);
                viewHolder.tv_Confirm_Payment.setVisibility(View.GONE);
                viewHolder.tv_refund.setVisibility(View.GONE);
                break;
            case "订单异常":
                viewHolder.order_list_item_tvOrderStatus.setText("订单异常");
                viewHolder.tv_oldermore.setVisibility(View.VISIBLE);
                viewHolder.tv_oldedelete.setVisibility(View.VISIBLE);
                viewHolder.tv_time.setVisibility(View.GONE);
                viewHolder.tv_ordercancel.setVisibility(View.GONE);
                viewHolder.tv_Confirm_Payment.setVisibility(View.GONE);
                viewHolder.tv_refund.setVisibility(View.GONE);
                break;
            case "订单已取消":
                viewHolder.order_list_item_tvOrderStatus.setText("已取消");
                viewHolder.tv_oldermore.setVisibility(View.GONE);
                viewHolder.tv_oldedelete.setVisibility(View.GONE);
                viewHolder.tv_time.setVisibility(View.GONE);
                viewHolder.tv_ordercancel.setVisibility(View.GONE);
                viewHolder.tv_Confirm_Payment.setVisibility(View.GONE);
                break;
            case "配送完成":
                viewHolder.order_list_item_tvOrderStatus.setText("配送完成");
                viewHolder.tv_oldermore.setVisibility(View.GONE);
                viewHolder.tv_oldedelete.setVisibility(View.GONE);
                viewHolder.tv_time.setVisibility(View.GONE);
                viewHolder.tv_ordercancel.setVisibility(View.GONE);
                viewHolder.tv_Confirm_Payment.setVisibility(View.VISIBLE);
                viewHolder.tv_refund.setVisibility(View.VISIBLE);
                break;
            case "退款中":
                viewHolder.order_list_item_tvOrderStatus.setText("退款中");
                viewHolder.tv_oldermore.setVisibility(View.GONE);
                viewHolder.tv_oldedelete.setVisibility(View.GONE);
                viewHolder.tv_time.setVisibility(View.GONE);
                viewHolder.tv_ordercancel.setVisibility(View.GONE);
                viewHolder.tv_Confirm_Payment.setVisibility(View.GONE);
                viewHolder.tv_refund.setVisibility(View.GONE);
                break;
            case "退款完成":
                viewHolder.order_list_item_tvOrderStatus.setText("退款完成");
                viewHolder.tv_oldermore.setVisibility(View.GONE);
                viewHolder.tv_oldedelete.setVisibility(View.GONE);
                viewHolder.tv_time.setVisibility(View.GONE);
                viewHolder.tv_ordercancel.setVisibility(View.GONE);
                viewHolder.tv_Confirm_Payment.setVisibility(View.GONE);
                viewHolder.tv_refund.setVisibility(View.GONE);
                break;


        }
//        if ("PAYING".equals(listEntity.getStatus())&&listEntity.getCancelStatus().equals("NORMAL")) {
//            viewHolder.order_list_item_tvOrderStatus.setText("去支付");
//            viewHolder.order_list_item_tvOrderStatus.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//            viewHolder.order_list_item_tvOrderStatus.setTextColor(context.getResources().getColor(R.color.hong));
//            viewHolder.tv_oldermore.setVisibility(View.GONE);
//            viewHolder.tv_time.setVisibility(View.VISIBLE);
//            viewHolder.tv_oldedelete.setVisibility(View.GONE);
//            viewHolder.tv_ordercancel.setVisibility(View.VISIBLE);
//            final int time = 1800 - (int) (System.currentTimeMillis() / 1000 - listEntity.getCreateTime());
//            listEntity.setTime1(time);
//            if (time > 0) {
//                viewHolder.tv_time.setText(getTimeStr(time) + "");
//            } else {
//                viewHolder.tv_time.setText("");
//            }
//
//        } else if ("PAY".equals(listEntity.getStatus())&&listEntity.getCancelStatus().equals("NORMAL")&&listEntity.getDeliveryStatus().equals("PRE")) {
//
//            viewHolder.order_list_item_tvOrderStatus.setText("待配送");
//            viewHolder.order_list_item_tvOrderStatus.getPaint().setFlags(0);
//            viewHolder.order_list_item_tvOrderStatus.setTextColor(context.getResources().getColor(R.color.hui3));
//            viewHolder.tv_oldermore.setVisibility(View.GONE);
//            viewHolder.tv_oldedelete.setVisibility(View.GONE);
//            viewHolder.tv_time.setVisibility(View.GONE);
//            viewHolder.tv_ordercancel.setVisibility(View.VISIBLE);
//        } else if ("PAY".equals(listEntity.getStatus())&&listEntity.getCancelStatus().equals("NORMAL")&&listEntity.getDeliveryStatus().equals("SENDING")) {
//            viewHolder.order_list_item_tvOrderStatus.setText("配送中");
//            viewHolder.order_list_item_tvOrderStatus.getPaint().setFlags(0);
//            viewHolder.order_list_item_tvOrderStatus.setTextColor(context.getResources().getColor(R.color.hui3));
//            viewHolder.tv_oldermore.setVisibility(View.GONE);
//            viewHolder.tv_oldedelete.setVisibility(View.GONE);
//            viewHolder.tv_time.setVisibility(View.GONE);
//            viewHolder.tv_ordercancel.setVisibility(View.GONE);
//        } else if ("PAY".equals(listEntity.getStatus())&&listEntity.getCancelStatus().equals("NORMAL")&&listEntity.getDeliveryStatus().equals("OK")&&listEntity.getEvaluateStatus().equals("NOT_YET")) {
//            viewHolder.order_list_item_tvOrderStatus.setText("待评价");
//            viewHolder.order_list_item_tvOrderStatus.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//            viewHolder.order_list_item_tvOrderStatus.setTextColor(context.getResources().getColor(R.color.hong));
//            viewHolder.tv_oldermore.setVisibility(View.VISIBLE);
//            viewHolder.tv_oldedelete.setVisibility(View.GONE);
//            viewHolder.tv_time.setVisibility(View.GONE);
//            viewHolder.tv_ordercancel.setVisibility(View.GONE);
//        } else if ("OK".equals(listEntity.getStatus())&&listEntity.getCancelStatus().equals("NORMAL")&&listEntity.getDeliveryStatus().equals("OK")&&listEntity.getEvaluateStatus().equals("DONE")) {
//            viewHolder.order_list_item_tvOrderStatus.setText("已完成");
//            viewHolder.order_list_item_tvOrderStatus.getPaint().setFlags(0);
//            viewHolder.order_list_item_tvOrderStatus.setTextColor(context.getResources().getColor(R.color.line));
//            viewHolder.tv_oldermore.setVisibility(View.VISIBLE);
//            viewHolder.tv_oldedelete.setVisibility(View.VISIBLE);
//            viewHolder.tv_time.setVisibility(View.GONE);
//            viewHolder.tv_ordercancel.setVisibility(View.GONE);
//        }
        int count = 0;
        for (int i = 0; i < listEntity.getOrderGoods().size(); i++) {
            count = count + Integer.parseInt(listEntity.getOrderGoods().get(i).getGoodsCount());
        }
        //设置件数和总价格
        viewHolder.order_list_item_tvTotal.setText("共 " + count + " 件, 总计 ￥" + listEntity.getTotal() + " 元");

        //设置商品列表
        list = listEntity.getOrderGoods();
        OrderListItemAdapter orderListItemAdapter = new OrderListItemAdapter(list, context);
        viewHolder.order_list_item_lv.setAdapter(orderListItemAdapter);
        setListViewHeight(viewHolder.order_list_item_lv);
        viewHolder.tv_oldermore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                List<DiyShoppingCartJsonData.GoodsListEntity> list1 = new LinkedList<>();
                for (int i = 0; i < list.size(); i++) {
                    OrderListJsondata1.DataBean.ListBean.OrderGoodsBean o1 = list.get(i);
                    DiyShoppingCartJsonData.GoodsListEntity d1 = new DiyShoppingCartJsonData.GoodsListEntity();
                    d1.setGoods_id(o1.getGoodsId() + "");
                    Log.e("----------Goods_id", o1.getGoodsId());
                    int Goods_conunt = Integer.parseInt(o1.getGoodsCount());
                    d1.setGoods_count(Goods_conunt);
                    d1.setTypename(o1.getPack());
                    d1.setSales_unit(o1.getUnit());
                    count += Goods_conunt;
                    list1.add(d1);
                }
                TiaoshiApplication.diyShoppingCartJsonData = new DiyShoppingCartJsonData();
                TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(count);
                TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(Double.valueOf(listEntity.getTotal()));
                TiaoshiApplication.diyShoppingCartJsonData.setShop_id(listEntity.getShopId() + "");
                TiaoshiApplication.diyShoppingCartJsonData.setGoodsList(list1);
                Intent intent = new Intent(context, StoreDetailActivity.class);
                intent.putExtra("shop_id", listEntity.getShopId() + "");
                intent.putExtra("shop_name", listEntity.getShopName());
                intent.putExtra("min_cost", listEntity.getFreeSend());
                intent.putExtra("listolderNO", G.SP.LIST_Older);
                context.startActivity(intent);
            }
        });
        viewHolder.tv_oldedelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示:").setMessage("确定要删除该订单？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap map = new HashMap();
                        map.put("token", TiaoshiApplication.globalToken);
                        map.put("orderId", listEntity.getOrderId() + "");
                        map.put("actReq", SignUtil.getRandom());
                        map.put("actTime", System.currentTimeMillis() / 1000 + "");
                        String sign = SignUtil.getSign(map);
                        map.put("sign", Md5.toMd5(sign));
                        OkHttpManager.postAsync(G.Host.ORDER_DELETE, map, new MyCallBack(5, context, new DeleteOlderJsonData(), handler));
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
            }
        });
        viewHolder.tv_ordercancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示:").setMessage("确定要取消该订单？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap map = new HashMap();
                        map.put("token", TiaoshiApplication.globalToken);
                        map.put("orderId", listEntity.getOrderId() + "");
                        map.put("actReq", SignUtil.getRandom());
                        map.put("actTime", System.currentTimeMillis() / 1000 + "");
                        String sign = SignUtil.getSign(map);
                        map.put("sign", Md5.toMd5(sign));
                        OkHttpManager.postAsync(G.Host.ORDER_CANCEL, map, new MyCallBack(5, context, new DeleteOlderJsonData(), handler));
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("返回", null);
                builder.show();
            }
        });
        viewHolder.tv_Confirm_Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示:").setMessage("确认收货？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap map = new HashMap();
                        map.put("token", TiaoshiApplication.globalToken);
                        map.put("orderId", listEntity.getOrderId() + "");
                        map.put("actReq", SignUtil.getRandom());
                        map.put("actTime", System.currentTimeMillis() / 1000 + "");
                        String sign = SignUtil.getSign(map);
                        map.put("sign", Md5.toMd5(sign));
                        OkHttpManager.postAsync(G.Host.CONFIRM_PAY, map, new MyCallBack(5, context, new DeleteOlderJsonData(), handler));
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("返回", null);
                builder.show();
            }
        });
        viewHolder.tv_refund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                        OkHttpManager.postAsync(G.Host.REFUND, map, new MyCallBack(5, context, new DeleteOlderJsonData(), handler));
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("返回", null);
                builder.show();
            }
        });
        return convertView;
    }
    private class ViewHolder {
        TextView order_list_item_tvOrderNumber;
        TextView order_list_item_tvOrderStatus;
        OrderListItemListView order_list_item_lv;
        TextView order_list_item_tvTotal, tv_oldermore, tv_oldedelete, tv_time, tv_ordercancel,tv_Confirm_Payment,tv_refund;
    }
    private void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    private int result = -1;
    /**
     * 倒数，实时更新每个item的显示时间
     *
     * @return
     */
    public int countDownTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    if (resList == null || result == resList.size() + 1) {
                        result = 0;
                        return;
                    }
                    result = 1;
                    for (OrderListJsondata1.DataBean.ListBean goods : resList) {
                        if (goods.getTime1() == 0) {
                            goods.setTime1(0);
                            result++;
                        } else {
                            goods.setTime1(goods.getTime1() - 1);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Log.i("test", "result==>" + result);
        return result;
    }
    /**
     * 格式化时间显示，格式为：XX分XX秒
     *
     * @param timeDiff
     * @return
     */
    public static String getTimeStr(int timeDiff) {
        int min = timeDiff / 60;
        int sec = timeDiff - min * 60;

        String result = "";
        String m = min + "";
        String s = sec + "";
        if (min < 10) {
            m = "0" + min;
        }
        if (sec < 10) {
            s = "0" + sec;
        }
        result = m + ":" + s;
        return result;
    }

}
