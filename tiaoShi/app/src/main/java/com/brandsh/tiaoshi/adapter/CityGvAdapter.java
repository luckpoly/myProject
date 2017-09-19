package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.model.CityModel;

import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 */

public class CityGvAdapter extends BaseAdapter {
    private Context context;
    private List<CityModel.DataBean> beanList;
    private LayoutInflater inflater;
    String city;

    public CityGvAdapter(Context context, List<CityModel.DataBean> beanList) {
        this.context = context;
        this.beanList = beanList;
        inflater=LayoutInflater.from(context);
    }
    public CityGvAdapter(Context context, List<CityModel.DataBean> beanList,String city) {
        this.context = context;
        this.beanList = beanList;
        inflater=LayoutInflater.from(context);
        this.city=city;
    }

    @Override
    public int getCount() {
        return beanList.size();
    }

    @Override
    public Object getItem(int position) {
        return beanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      convertView=inflater.inflate(R.layout.item_city,null);
        TextView tv_city=(TextView)convertView.findViewById(R.id.tv_city);
        tv_city.setText(beanList.get(position).getName());
        if (city!=null&&city.equals(beanList.get(position).getName())){
            tv_city.setTextColor(context.getResources().getColor(R.color.theme_color));
        }
        return convertView;
    }
}
