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
import com.goodfood86.tiaoshi.order121Project.model.YuyueListModel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Administrator on 2016/4/8.
 */
public class YuyueOrderAdapter extends BaseAdapter{
    private List<YuyueListModel.DataBean> mlist;
    private LayoutInflater layoutInflater;
    private Context context;
    public YuyueOrderAdapter(List<YuyueListModel.DataBean> list, Context context){
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
        YuyueListModel.DataBean bean=   mlist.get(position);
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.yuyue_order_item,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_yuyueorder_type=(TextView) convertView.findViewById(R.id.tv_yuyueorder_type);
            viewHolder.tv_yuyueorder_stauts=(TextView) convertView.findViewById(R.id.tv_yuyueorder_stauts);
            viewHolder.tv_yuyueorder_time=(TextView) convertView.findViewById(R.id.tv_yuyueorder_time);
            viewHolder.tv_yuyueorder_address=(TextView) convertView.findViewById(R.id.tv_yuyueorder_address);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_yuyueorder_type.setText(bean.getModuleCategoryName());
        viewHolder.tv_yuyueorder_time.setText(getTime(Long.parseLong(bean.getTime())));
        viewHolder.tv_yuyueorder_address.setText(bean.getAddress()+bean.getAddressDetail());
        if (bean.getStatus().equals("CANCEL")){
            viewHolder.tv_yuyueorder_stauts.setText("已取消");
        }else if (bean.getStatus().equals("MAKE")){
            viewHolder.tv_yuyueorder_stauts.setText("预约中");
        }else if (bean.getStatus().equals("OK")){
            viewHolder.tv_yuyueorder_stauts.setText("已完成");
        }

        return convertView;
    }



    class ViewHolder{

        TextView tv_yuyueorder_type,tv_yuyueorder_stauts,tv_yuyueorder_time,tv_yuyueorder_address;

    }
    private String getTime(long time) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(time * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String totalTime = simpleDateFormat.format(gc.getTime());
        return totalTime;
    }
}
