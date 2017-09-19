package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.model.JuiceOrderListdata;
import com.brandsh.tiaoshi.model.OrderDetailJsonData;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by sisi on 16/3/10.
 */
public class JuiceOrderdetailItemAdapter extends BaseAdapter {
    private List<JuiceOrderListdata.DataBean.OrderGoodsBean> resList;
    private Context context;
    private JuiceOrderListdata.DataBean.OrderGoodsBean orderDetailEntity;

    public JuiceOrderdetailItemAdapter(List<JuiceOrderListdata.DataBean.OrderGoodsBean> resList, Context context) {
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
        orderDetailEntity = resList.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.juice_order_list_item_item, null);
            viewHolder.order_list_item_item_tvName = (TextView) convertView.findViewById(R.id.order_list_item_item_tvName);
            viewHolder.order_list_item_item_tvCount = (TextView) convertView.findViewById(R.id.order_list_item_item_tvCount);
            viewHolder.order_list_item_item_tvCash = (TextView) convertView.findViewById(R.id.order_list_item_item_tvCash);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (TextUtils.isEmpty(orderDetailEntity.getPack())){
            viewHolder.order_list_item_item_tvName.setText(orderDetailEntity.getGoodsName());
        }else {
            viewHolder.order_list_item_item_tvName.setText(orderDetailEntity.getGoodsName()+"-"+orderDetailEntity.getPack());
        }

        viewHolder.order_list_item_item_tvCount.setText("x " + orderDetailEntity.getGoodsCount());

        if (!"0".equals(orderDetailEntity.getPromotePrice())&&!TextUtils.isEmpty(orderDetailEntity.getPromotePrice()+"")) {
            BigDecimal a = new BigDecimal(orderDetailEntity.getGoodsCount());
            BigDecimal b = new BigDecimal(orderDetailEntity.getPromotePrice());
            viewHolder.order_list_item_item_tvCash.setText("￥" + a.multiply(b));
        } else if (!TextUtils.isEmpty(orderDetailEntity.getPrice()+"")){
            BigDecimal a = new BigDecimal(orderDetailEntity.getGoodsCount());
            BigDecimal b = new BigDecimal(orderDetailEntity.getPrice());
            viewHolder.order_list_item_item_tvCash.setText("￥" + a.multiply(b));
        }

        return convertView;
    }

    private class ViewHolder {
        TextView order_list_item_item_tvName;
        TextView order_list_item_item_tvCount;
        TextView order_list_item_item_tvCash;
    }
}
