package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.model.DiyShoppingCartJsonData;

import java.util.List;

/**
 * Created by sisi on 16/3/9.
 */
public class ShopCartList1Adapter extends BaseAdapter {
    private List<DiyShoppingCartJsonData.GoodsListEntity> resList;
    private Context context;
    private DiyShoppingCartJsonData.GoodsListEntity goodsListEntity;

    public ShopCartList1Adapter(List<DiyShoppingCartJsonData.GoodsListEntity> resList, Context context) {
        this.resList = resList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return resList.size();
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
        goodsListEntity = resList.get(position);
        ShopCartListViewHolder shopCartListViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.shopping_cart_item1, null);
            shopCartListViewHolder = new ShopCartListViewHolder();
            shopCartListViewHolder.shop_cart_item1_tvName = (TextView) convertView.findViewById(R.id.shop_cart_item1_tvName);
            shopCartListViewHolder.shop_cart_item1_tvCount = (TextView) convertView.findViewById(R.id.shop_cart_item1_tvCount);
            shopCartListViewHolder.shop_cart_item1_tvCash = (TextView) convertView.findViewById(R.id.shop_cart_item1_tvCash);
            convertView.setTag(shopCartListViewHolder);
        } else {
            shopCartListViewHolder = (ShopCartListViewHolder) convertView.getTag();
        }
        shopCartListViewHolder.shop_cart_item1_tvName.setText(goodsListEntity.getGoods_name() + "");
        shopCartListViewHolder.shop_cart_item1_tvCount.setText("x " + goodsListEntity.getGoods_count() + "");
        shopCartListViewHolder.shop_cart_item1_tvCash.setText("￥ " + goodsListEntity.getGoods_price() + "");
        return convertView;
    }

    private class ShopCartListViewHolder {
        TextView shop_cart_item1_tvName;
        TextView shop_cart_item1_tvCount;
        TextView shop_cart_item1_tvCash;
    }
}
