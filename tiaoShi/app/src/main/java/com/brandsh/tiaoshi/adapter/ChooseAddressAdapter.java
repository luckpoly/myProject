package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.activity.ChooseAddressActivity;
import com.brandsh.tiaoshi.activity.ChooseAddressMapActivity;

import java.util.List;

/**
 * Created by sisi on 16/3/8.
 */
public class ChooseAddressAdapter extends BaseAdapter {
    private List<ChooseAddressMapActivity.AddressInfo> resList;
    private Context context;
    private ChooseAddressMapActivity.AddressInfo addressInfo;

//    public ChooseAddressAdapter(List<ChooseAddressActivity.AddressInfo> resList, Context context) {
//        this.resList = resList;
//        this.context = context;
//    }
    public ChooseAddressAdapter(List<ChooseAddressMapActivity.AddressInfo> resList, Context context) {
        this.resList = resList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (resList != null) {
            return resList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return resList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        addressInfo = resList.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.route_inputs, null);
            viewHolder.route_inputs_tvName = (TextView) convertView.findViewById(R.id.route_inputs_tvName);
            viewHolder.route_inputs_tvArea = (TextView) convertView.findViewById(R.id.route_inputs_tvArea);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.route_inputs_tvName.setText(addressInfo.getAddress());
        viewHolder.route_inputs_tvArea.setText(addressInfo.getArea());
        return convertView;
    }

    private class ViewHolder{
        TextView route_inputs_tvName;
        TextView route_inputs_tvArea;
    }
}
