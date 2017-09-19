package com.goodfood86.tiaoshi.order121Project.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.activity.EvaluateActivity;
import com.goodfood86.tiaoshi.order121Project.activity.OrderPayActivity;
import com.goodfood86.tiaoshi.order121Project.model.OrderListModel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Administrator on 2016/4/8.
 */
public class OrderAdapter extends BaseAdapter implements View.OnClickListener{
    private List<OrderListModel.DataEntity.ListEntity> mlist;
    private LayoutInflater layoutInflater;
    private Context context;
    public OrderAdapter(List<OrderListModel.DataEntity.ListEntity> list,Context context){
        this.mlist=list;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.order_manager_item,null);
            viewHolder=new ViewHolder();
            viewHolder.rl_order_title= (RelativeLayout) convertView.findViewById(R.id.rl_order_title);
            viewHolder.rl_foot= (RelativeLayout) convertView.findViewById(R.id.rl_foot);
            viewHolder.tv_No= (TextView) convertView.findViewById(R.id.tv_no);
            viewHolder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_type= (TextView) convertView.findViewById(R.id.tv_type);
            viewHolder.tv_price= (TextView) convertView.findViewById(R.id.tv_price);
            viewHolder.tv_receive_address= (TextView) convertView.findViewById(R.id.tv_receive_address);
            viewHolder.tv_receive_detailAddress= (TextView) convertView.findViewById(R.id.tv_receive_address_detail);
            viewHolder.tv_send_address= (TextView) convertView.findViewById(R.id.tv_send_address);
            viewHolder.tv_send_detailAddress= (TextView) convertView.findViewById(R.id.tv_send_address_detail);
            viewHolder.tv_unit= (TextView) convertView.findViewById(R.id.tv_unit);
            viewHolder.tv_go= (TextView) convertView.findViewById(R.id.tv_topayorcancal);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_No.setText(mlist.get(position).getOrderNo());
        if (mlist.get(position).getStatusPay()==0){
            viewHolder.tv_No.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.tv_time.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.tv_type.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.tv_type.setText("待支付");
            viewHolder.rl_foot.setVisibility(View.VISIBLE);
            viewHolder.tv_go.setText("立即支付");
            viewHolder.rl_order_title.setBackgroundColor(Color.parseColor("#ff7c86"));
        }else if (mlist.get(position).getStatusPay()==2&&mlist.get(position).getStatus()==0){
            viewHolder.rl_order_title.setBackgroundColor(Color.parseColor("#8892ff"));
            viewHolder.rl_foot.setVisibility(View.GONE);
            viewHolder.tv_No.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.tv_time.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.tv_type.setTextColor(Color.parseColor("#FFFFFF"));
//            viewHolder.rl_foot.setVisibility(View.VISIBLE);
//            viewHolder.tv_go.setText("取消订单");
            viewHolder.tv_type.setText("待配送");
        }else if (mlist.get(position).getStatusPay()==2&&mlist.get(position).getStatus()==3&&mlist.get(position).getIsAppraise()==0){
            viewHolder.rl_order_title.setBackgroundColor(Color.parseColor("#73d0ff"));
            viewHolder.tv_No.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.tv_time.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.tv_type.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.rl_foot.setVisibility(View.VISIBLE);
            viewHolder.tv_go.setText("立即评价");
            viewHolder.tv_type.setText("待评价");
        }else if(mlist.get(position).getStatusPay()==2&&mlist.get(position).getStatus()==1){
            viewHolder.tv_type.setText("已抢单");
            viewHolder.rl_order_title.setBackgroundResource(R.color.bg);
            viewHolder.tv_No.setTextColor(Color.parseColor("#646464"));
            viewHolder.tv_time.setTextColor(Color.parseColor("#646464"));
            viewHolder.tv_type.setTextColor(Color.parseColor("#646464"));
            viewHolder.rl_foot.setVisibility(View.GONE);
        }else if(mlist.get(position).getStatusPay()==2&&mlist.get(position).getStatus()==2){
            viewHolder.tv_type.setText("已取件");
            viewHolder.tv_No.setTextColor(Color.parseColor("#646464"));
            viewHolder.tv_time.setTextColor(Color.parseColor("#646464"));
            viewHolder.tv_type.setTextColor(Color.parseColor("#646464"));
            viewHolder.rl_order_title.setBackgroundResource(R.color.bg);
            viewHolder.rl_foot.setVisibility(View.GONE);
        }else if(mlist.get(position).getStatusPay()==2&&mlist.get(position).getStatus()==3&&mlist.get(position).getIsAppraise()!=0){
            viewHolder.tv_type.setText("已完成");
            viewHolder.tv_No.setTextColor(Color.parseColor("#646464"));
            viewHolder.tv_time.setTextColor(Color.parseColor("#646464"));
            viewHolder.tv_type.setTextColor(Color.parseColor("#646464"));
            viewHolder.rl_order_title.setBackgroundResource(R.color.bg);
            viewHolder.rl_foot.setVisibility(View.GONE);
        }
//        viewHolder.tv_time.setText();
        viewHolder.rl_foot.setTag(position);
        viewHolder.rl_foot.setOnClickListener(this);
        viewHolder.tv_send_address.setText(mlist.get(position).getSenderAddress());
        viewHolder.tv_send_detailAddress.setText(mlist.get(position).getSenderAddressDetail());
        viewHolder.tv_receive_address.setText(mlist.get(position).getRecipientAddress());
        viewHolder.tv_receive_detailAddress.setText(mlist.get(position).getRecipientAddressDetail());
        viewHolder.tv_price.setText(mlist.get(position).getCommission()+"元");
        viewHolder.tv_unit.setText(mlist.get(position).getOrderDistance()+"公里/"+mlist.get(position).getProduct().get(0).getProductUnit());
        viewHolder.tv_time.setText(getTime(mlist.get(position).getCreateTime()));

        return convertView;
    }

    @Override
    public void onClick(View v) {
        if (v!=null){
            switch (v.getId()){
                case R.id.rl_foot:
                    int position= (int) v.getTag();
                    if (mlist.get(position).getStatusPay()==0){
                        Intent intent=new Intent(context,OrderPayActivity.class);
                        String totalPrice= String.valueOf(BigDecimal.valueOf(Double.parseDouble(mlist.get(position).getCommission())).add(BigDecimal.valueOf(Double.parseDouble(mlist.get(position).getPremium()))));
                        intent.putExtra("orderNo",mlist.get(position).getOrderNo());
                        intent.putExtra("total",totalPrice);
                        intent.putExtra("payOrderName","需要配送1件物品，合计 "+totalPrice+" 元。");
                        intent.putExtra("payOrderDetail","付款来源: 安卓支付宝客户端,"
                                + "订单编号："+mlist.get(position).getOrderNo()+",配送数量：1件"+
                                ",合计："+mlist.get(position).getCommission()+" 元。");
                        intent.putExtra("sendaddress",mlist.get(position).getSenderAddress());
                        intent.putExtra("receiveaddress",mlist.get(position).getRecipientAddress());
                        intent.putExtra("sendinfo",mlist.get(position).getSenderName()+"      "+mlist.get(position).getSenderPhone());
                        intent.putExtra("receiveinfo",mlist.get(position).getRecipientName()+"        "+mlist.get(position).getRecipientPhone());
                        intent.putExtra("thingname", mlist.get(position).getProduct().get(0).getProductName());
                        intent.putExtra("distance", mlist.get(position).getOrderDistance());
                        intent.putExtra("weight", mlist.get(position).getProduct().get(0).getProductUnit());
                        intent.putExtra("premium", mlist.get(position).getPremium());
                        intent.putExtra("beizhu", mlist.get(position).getDesc());
                        context.startActivity(intent);
                    }else if (mlist.get(position).getStatusPay()==2&&mlist.get(position).getStatus()==3&&mlist.get(position).getIsAppraise()==0){
                        Intent intent=new Intent(context, EvaluateActivity.class);
                        intent.putExtra("orderId",mlist.get(position).getId()+"");
                        intent.putExtra("orderNo",mlist.get(position).getOrderNo());
                        intent.putExtra("orderPrice",mlist.get(position).getCommission()+"");
                        intent.putExtra("orderUnit",mlist.get(position).getOrderDistance()+"公里/"+mlist.get(position).getProduct().get(0).getProductUnit());
                        intent.putExtra("orderTime",mlist.get(position).getCreateTime());
                        context.startActivity(intent);
                    }
                    break;
            }
        }
    }

    class ViewHolder{
        RelativeLayout rl_order_title;
        RelativeLayout rl_foot;
        TextView tv_send_address;
        TextView tv_send_detailAddress;
        TextView tv_receive_address;
        TextView tv_receive_detailAddress;
        TextView tv_No;
        TextView tv_time;
        TextView tv_type;
        TextView tv_price;
        TextView tv_unit;
        TextView tv_go;

    }
    private String getTime(long time) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(time * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String totalTime = simpleDateFormat.format(gc.getTime());
        return totalTime;
    }
}
