package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.model.MonthOrderListModel;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Created by Administrator on 2016/10/18.
 */

public class MonthListFenAdapter extends BaseAdapter {
    List<MonthOrderListModel.DataBean.ListBean.OrderPartialBean> listBean;
    LayoutInflater inflater;
    Context context;

    public MonthListFenAdapter(Context context, List<MonthOrderListModel.DataBean.ListBean.OrderPartialBean> listBean) {
        this.context = context;
        this.listBean = listBean;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listBean.size();
    }

    @Override
    public Object getItem(int position) {
        return listBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.month_list_item, null);
            holder.tv_month_name = (TextView) convertView.findViewById(R.id.tv_month_name);
            holder.tv_month_content = (TextView) convertView.findViewById(R.id.tv_month_content);
            holder.tv_month_type = (TextView) convertView.findViewById(R.id.tv_month_type);
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_month_name.setText(listBean.get(position).getGoodsName());
        holder.iv_icon.setVisibility(View.GONE);
        if (listBean.get(position).getOrderStatus().equals("0")) {
            holder.tv_month_type.setText("订单异常");
        } else {
            if (Double.parseDouble(listBean.get(position).getSendTime()) < System.currentTimeMillis() / 1000) {
                holder.tv_month_type.setText("订单完成");
            } else {
                holder.tv_month_type.setText("待配送");
            }

        }
        holder.tv_month_content.setText(formatDate(listBean.get(position).getSendTime()).substring(0, 11) + "/" + listBean.get(position).getCup() + " 店家配送");
        return convertView;
    }

    class ViewHolder {
        TextView tv_month_name, tv_month_content, tv_month_type;
        ImageView iv_icon;
    }

    private String formatDate(String str) {
        long seconds = Long.parseLong(str);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(seconds * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(gc.getTime());
    }
}
