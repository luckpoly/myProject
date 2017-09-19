package com.goodfood86.tiaoshi.order121Project.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;

import java.util.List;


/**
 * Created by Administrator on 2016/4/1.
 */
public class ChooseCityAdapter extends BaseAdapter{
    private String[] listString;
    private LayoutInflater inflater;
    public ChooseCityAdapter(String[] listString, Context mContext){
        this.listString=listString;
        inflater=LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        return listString.length;
    }

    @Override
    public Object getItem(int position) {
        return listString[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.choosecity_item,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.tv_city= (TextView) convertView.findViewById(R.id.tv_city);
            viewHolder.ll_show= (LinearLayout) convertView.findViewById(R.id.ll_show);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
            viewHolder.tv_city.setText(listString[position]);
        if (position == selectItem){
            viewHolder.ll_show.setBackgroundResource(R.drawable.gv_item_bg);
            viewHolder.tv_city.setTextColor(Color.parseColor("#ffffff"));
        }else {
            viewHolder.ll_show.setBackgroundResource(R.drawable.gv_item_bg_normal);
            viewHolder.tv_city.setTextColor(Color.parseColor("#646464"));
        }
        return convertView;
    }
    private class ViewHolder{
        private TextView tv_city;
        private LinearLayout ll_show;
    }
    public  void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }
    private int  selectItem=-1;
}
