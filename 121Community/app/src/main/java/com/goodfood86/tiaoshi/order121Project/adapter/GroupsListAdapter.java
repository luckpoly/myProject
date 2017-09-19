package com.goodfood86.tiaoshi.order121Project.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.model.ActivityListModel;
import com.goodfood86.tiaoshi.order121Project.model.ActivityListModel1;
import com.goodfood86.tiaoshi.order121Project.model.GroupsModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/12.
 */
public class GroupsListAdapter extends BaseAdapter {
    List<ActivityListModel1.DataBean> list;
    Context context;
    LayoutInflater inflater;

    public GroupsListAdapter(List<ActivityListModel1.DataBean> list, Context context) {
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
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder =new ViewHolder();
            convertView=inflater.inflate(R.layout.groups_item,null);
            viewHolder.group_name= (TextView) convertView.findViewById(R.id.group_name);
            viewHolder.group_icon=(ImageView)convertView.findViewById(R.id.group_icon);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.group_name.setText(list.get(position).getName());
        viewHolder.group_icon.setImageDrawable(list.get(position).getDrawable());
        return convertView;
    }
    class ViewHolder{
        ImageView group_icon;
        TextView group_name;
    }
}
