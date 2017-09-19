package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.model.OrderTypeModel;

import java.util.List;

/**
 * Created by Administrator on 2017/1/5.
 */

public class OrderTypeAdapter extends BaseAdapter{
    List<OrderTypeModel> list;
    LayoutInflater inflater;
    Context context;

    public OrderTypeAdapter(List<OrderTypeModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.item_ordertype,null);
       ImageView iv_icon =(ImageView)convertView.findViewById(R.id.iv_icon);
       TextView tv_type =(TextView) convertView.findViewById(R.id.tv_type);
        iv_icon.setImageDrawable(list.get(position).getD());
        tv_type.setText(list.get(position).getName());
        return convertView;
    }
}
