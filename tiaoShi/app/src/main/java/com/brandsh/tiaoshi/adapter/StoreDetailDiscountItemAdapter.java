package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.model.StoreDetailJsonData1;

import java.util.List;

/**
 * Created by sisi on 16/3/16.
 */
public class StoreDetailDiscountItemAdapter extends BaseAdapter {
    private Context context;
    private List<StoreDetailJsonData1.DataBean.ShopPreferentialBean> resList;
    private StoreDetailJsonData1.DataBean.ShopPreferentialBean discountEntity;

    public StoreDetailDiscountItemAdapter(Context context, List<StoreDetailJsonData1.DataBean.ShopPreferentialBean> resList) {
        this.context = context;
        this.resList = resList;
    }

    @Override
    public int getCount() {
        if (resList!=null){
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
        Viewholder viewholder  = null;
        discountEntity = resList.get(position);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.store_detail_discount_item, null);
            viewholder = new Viewholder();
            viewholder.store_detail_tvYouHui1 = (TextView) convertView.findViewById(R.id.store_detail_tvYouHui1);
            convertView.setTag(viewholder);
        }else {
            viewholder = (Viewholder) convertView.getTag();
        }
        viewholder.store_detail_tvYouHui1.setText("满"+discountEntity.getFull()+"元减" +discountEntity.getSubtract()+"元");
        return convertView;
    }

    private class Viewholder{
        private TextView store_detail_tvYouHui1;
    }
}
