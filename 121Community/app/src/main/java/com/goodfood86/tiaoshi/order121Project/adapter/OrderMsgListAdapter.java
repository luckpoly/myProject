package com.goodfood86.tiaoshi.order121Project.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.model.OrderMsgLisrModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiashiwang on 2016/4/8.
 */
public class OrderMsgListAdapter extends BaseAdapter {
    private Context context;
    private List<OrderMsgLisrModel.DataBean.ListBean> mlist;
    private LayoutInflater inflater;

    public OrderMsgListAdapter(Context context, List<OrderMsgLisrModel.DataBean.ListBean> mlist) {
        this.context = context;
        this.mlist = mlist;
        inflater=LayoutInflater.from(context);
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
        OrderMsgLisrModel.DataBean.ListBean bean=mlist.get(position);


        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.item_ordermsglist,parent,false);
            viewHolder.tv_orderlist_time= (TextView) convertView.findViewById(R.id.tv_orderlist_time);
            viewHolder.tv_orderlist_No= (TextView) convertView.findViewById(R.id.tv_orderlist_No);
            viewHolder.tv_orderlist_money= (TextView) convertView.findViewById(R.id.tv_orderlist_money);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_orderlist_time.setText(strtime(bean.getAddTime()));
        viewHolder.tv_orderlist_No.setText(strdata(bean.getNo()));
        viewHolder.tv_orderlist_money.setText(bean.getAmount()+"元");
        return convertView;
    }
    class ViewHolder{
        private TextView tv_orderlist_time;
        private TextView tv_orderlist_No;
        private TextView tv_orderlist_money;
    }
    private  String strdata(String str){
      String ss=  str.substring(0,4)+"***"+str.substring(str.length()-4,str.length());
        return ss;
    }
    private String strtime(String str){
      String ss=  str.substring(0,10).replace('-','/');
        return  ss;

    }
}
