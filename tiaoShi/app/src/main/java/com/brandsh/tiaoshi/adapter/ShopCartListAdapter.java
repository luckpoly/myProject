package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.model.DiyShoppingCartJsonData;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by sisi on 16/3/2.
 */
public class ShopCartListAdapter extends BaseAdapter {
    private List<DiyShoppingCartJsonData.GoodsListEntity> resList;
    private Context context;
    private DiyShoppingCartJsonData.GoodsListEntity goodsListEntity;
    private Handler handler;
    public ShopCartListAdapter(List<DiyShoppingCartJsonData.GoodsListEntity> resList, Context context, Handler handler) {
        this.resList = resList;
        this.context = context;
        this.handler=handler;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.shop_cart_item, null);
            shopCartListViewHolder = new ShopCartListViewHolder();
            shopCartListViewHolder.shop_cart_item_tvName = (TextView) convertView.findViewById(R.id.shop_cart_item_tvName);
            shopCartListViewHolder.shop_cart_item_tvCount = (TextView) convertView.findViewById(R.id.shop_cart_item_tvCount);
            shopCartListViewHolder.shop_cart_item_tvCash = (TextView) convertView.findViewById(R.id.shop_cart_item_tvCash);
            shopCartListViewHolder.btn_add= (Button) convertView.findViewById(R.id.bt_add);
            shopCartListViewHolder.btn_minus= (Button) convertView.findViewById(R.id.bt_minus);
            convertView.setTag(shopCartListViewHolder);
        } else {
            shopCartListViewHolder = (ShopCartListViewHolder) convertView.getTag();
        }
        shopCartListViewHolder.shop_cart_item_tvName.setText(goodsListEntity.getGoods_name() + "");
        shopCartListViewHolder.shop_cart_item_tvCount.setText("x " + goodsListEntity.getGoods_count() + "");
        shopCartListViewHolder.shop_cart_item_tvCash.setText("￥ " + goodsListEntity.getGoods_price() + "");
        shopCartListViewHolder.btn_add.setOnClickListener(new AddClickListener(position,goodsListEntity.getGoods_count()));
        shopCartListViewHolder.btn_minus.setOnClickListener(new DeleteClickListener(position,goodsListEntity.getGoods_count()));
        return convertView;
    }
    private class AddClickListener implements View.OnClickListener {
        private int position;
        private int goods_sc_count;

        public AddClickListener(int position, int goods_sc_count) {
            this.position = position;
            this.goods_sc_count = goods_sc_count;
        }

        @Override
        public void onClick(View v) {
            ++goods_sc_count;
            resList.get(position).setGoods_count(goods_sc_count);
            resList.get(position).setGoods_price( new BigDecimal(resList.get(position).getPrice()).multiply(new BigDecimal(goods_sc_count)).toString() + "元");
            TiaoshiApplication.diyShoppingCartJsonData.setGoodsList(resList);
            notifyDataSetChanged();
            Message message = handler.obtainMessage();
            message.obj = position;
            message.what = 5;
            handler.sendMessage(message);

        }
    }

    private class DeleteClickListener implements View.OnClickListener {
        private int position;
        private int goods_sc_count;

        public DeleteClickListener(int position, int goods_sc_count) {
            this.position = position;
            this.goods_sc_count = goods_sc_count;
        }

        @Override
        public void onClick(View v) {
            --goods_sc_count;
            resList.get(position).setGoods_count(goods_sc_count);
            if (goods_sc_count>0) {
                resList.get(position).setGoods_price(new BigDecimal(resList.get(position).getPrice()).multiply(new BigDecimal(goods_sc_count)).toString() + "元");
            }
            notifyDataSetChanged();
            Message message = handler.obtainMessage();
            message.obj = position;
            message.what = 6;
            handler.sendMessage(message);
        }
    }
    private class ShopCartListViewHolder {
        TextView shop_cart_item_tvName;
        TextView shop_cart_item_tvCount;
        TextView shop_cart_item_tvCash;
        Button btn_add;
        Button btn_minus;
    }
}
