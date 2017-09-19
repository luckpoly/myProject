package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.model.MonthOrderListModel;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Created by Administrator on 2016/10/18.
 */

public class MonthListAdapter extends BaseAdapter {
    List<MonthOrderListModel.DataBean.ListBean> listBean;
    LayoutInflater inflater;
    Context context;

    public MonthListAdapter(Context context, List<MonthOrderListModel.DataBean.ListBean> listBean) {
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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_month_name.setText(formatDate(listBean.get(position).getCreateTime()).substring(0, 11) + listBean.get(position).getOrderGoods().get(0).getGoodsName());
        if (listBean.get(position).getStatus().equals("PAYING")) {
            holder.tv_month_type.setText("去支付");
            holder.tv_month_type.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            holder.tv_month_type.setTextColor(context.getResources().getColor(R.color.hong));
            holder.tv_month_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        } else {
            if (listBean.get(position).getStartStatus().equals("0")) {
                holder.tv_month_type.setText("暂停配送");
            } else {
                holder.tv_month_type.setText("正常配送");
            }
        }
        if (TextUtils.isEmpty(listBean.get(position).getRestJuiceCount()) || TextUtils.isEmpty(listBean.get(position).getJuiceTotal())) {
            holder.tv_month_content.setText("订单未支付");
        } else {
            holder.tv_month_content.setText("套餐剩余杯数：" + listBean.get(position).getRestJuiceCount() + "杯/" + listBean.get(position).getJuiceTotal() + "杯");
        }
        if (!TextUtils.isEmpty(listBean.get(position).getRestJuiceCount()) && listBean.get(position).getRestJuiceCount().equals("0")) {
            holder.tv_month_type.setText("订单已结束");
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_month_name, tv_month_content, tv_month_type;
    }

    private String formatDate(String str) {
        long seconds = Long.parseLong(str);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(seconds * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(gc.getTime());
    }
}
