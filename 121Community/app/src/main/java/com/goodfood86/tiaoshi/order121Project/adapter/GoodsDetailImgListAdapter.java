package com.goodfood86.tiaoshi.order121Project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by apple on 16/2/22.
 */
public class GoodsDetailImgListAdapter extends BaseAdapter{
    private List<String> resList;
    private Context context;
    private BitmapUtils bitmapUtils;

    public GoodsDetailImgListAdapter(List<String> resList, Context context) {
        this.resList = resList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(resList!=null){
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
        final  String url=resList.get(position);
        GoodsDetailImgListItemViewHolder goodsDetailImgListItemViewHolder = null;
        bitmapUtils = Order121Application.getGlobalBitmapUtils();
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.goods_detail_img_list_item, null);
            goodsDetailImgListItemViewHolder = new GoodsDetailImgListItemViewHolder();
            goodsDetailImgListItemViewHolder.goods_detail_img_list_item_iv = (ImageView) convertView.findViewById(R.id.goods_detail_img_list_item_iv);
            convertView.setTag(goodsDetailImgListItemViewHolder);
        }else {
            goodsDetailImgListItemViewHolder = (GoodsDetailImgListItemViewHolder) convertView.getTag();
        }
        bitmapUtils.display(goodsDetailImgListItemViewHolder.goods_detail_img_list_item_iv, url);
        return convertView;
    }

    private class GoodsDetailImgListItemViewHolder{
        ImageView goods_detail_img_list_item_iv;
    }

}
