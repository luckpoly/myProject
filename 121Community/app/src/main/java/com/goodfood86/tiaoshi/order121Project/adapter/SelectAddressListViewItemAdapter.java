package com.goodfood86.tiaoshi.order121Project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.model.HistoryAddressListModel;

import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public class SelectAddressListViewItemAdapter extends BaseAdapter{
    private List<HistoryAddressListModel.DataEntity.ListEntity> mlist;
    private Context mContext;
    private LayoutInflater layoutInflater;
    public SelectAddressListViewItemAdapter(List<HistoryAddressListModel.DataEntity.ListEntity> list, Context context){
        this.mlist=list;
        this.mContext=context;
        layoutInflater=LayoutInflater.from(mContext);
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
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.selectlv_item,parent,false);
            viewHolder.tv_item_address= (TextView) convertView.findViewById(R.id.tv_item_address);
            viewHolder.tv_phone= (TextView) convertView.findViewById(R.id.tv_phone);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_item_address.setText(mlist.get(position).getAddress()+mlist.get(position).getAddressDetail());
        viewHolder.tv_phone.setText(mlist.get(position).getPhone());

        return convertView;
    }
    class ViewHolder{
        private TextView tv_item_address,tv_phone;
    }
}
