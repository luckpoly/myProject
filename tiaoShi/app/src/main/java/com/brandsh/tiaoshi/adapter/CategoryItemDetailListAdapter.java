package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.model.CategoryDetailListJsonData;
import com.brandsh.tiaoshi.utils.DensityUtil;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by apple on 16/2/22.
 */
public class CategoryItemDetailListAdapter extends BaseAdapter {
    private List<CategoryDetailListJsonData.DataBean.ListBean> resList;
    private Context context;
    private CategoryDetailListJsonData.DataBean.ListBean listEntity;
    private BitmapUtils bitmapUtils;
    private int poi;

    public CategoryItemDetailListAdapter(List<CategoryDetailListJsonData.DataBean.ListBean> resList, Context context) {
        this.resList = resList;
        this.context = context;
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
        bitmapUtils = TiaoshiApplication.getGlobalBitmapUtils();
        listEntity = resList.get(position);
        CategoryItemDetailListViewHolder categoryItemDetailListViewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.category_list_item, null);
            categoryItemDetailListViewHolder = new CategoryItemDetailListViewHolder();
            categoryItemDetailListViewHolder.category_list_item_iv = (ImageView) convertView.findViewById(R.id.category_list_item_iv);
            categoryItemDetailListViewHolder.category_list_item_tvProductName = (TextView) convertView.findViewById(R.id.category_list_item_tvProductName);
            categoryItemDetailListViewHolder.category_list_item_tvStoreName = (TextView) convertView.findViewById(R.id.category_list_item_tvStoreName);
            categoryItemDetailListViewHolder.category_list_item_tvMoney = (TextView) convertView.findViewById(R.id.category_list_item_tvMoney);
            categoryItemDetailListViewHolder.category_list_item_tvPrice = (TextView) convertView.findViewById(R.id.category_list_item_tvPrice);
            categoryItemDetailListViewHolder.category_list_item_view = convertView.findViewById(R.id.category_list_item_view);
            categoryItemDetailListViewHolder.category_list_item_tvUnit = (TextView) convertView.findViewById(R.id.category_list_item_tvUnit);
            categoryItemDetailListViewHolder.category_list_item_tvOldPrice = (TextView) convertView.findViewById(R.id.category_list_item_tvOldPrice);
            categoryItemDetailListViewHolder.category_list_item_tvDistance = (TextView) convertView.findViewById(R.id.category_list_item_tvDistance);
            categoryItemDetailListViewHolder.category_list_item_tvCount = (TextView) convertView.findViewById(R.id.category_list_item_tvCount);
            convertView.setTag(categoryItemDetailListViewHolder);
        }else {
            categoryItemDetailListViewHolder = (CategoryItemDetailListViewHolder) convertView.getTag();
        }

        bitmapUtils.display(categoryItemDetailListViewHolder.category_list_item_iv, listEntity.getThumImg());
        categoryItemDetailListViewHolder.category_list_item_tvProductName.setText(listEntity.getGoodsName());
        categoryItemDetailListViewHolder.category_list_item_tvStoreName.setText(listEntity.getShopName());
        categoryItemDetailListViewHolder.category_list_item_tvMoney.setText("满￥"+listEntity.getFreeSend()+"元免费配送");
        if(TextUtils.isEmpty(listEntity.getSalesNum())||listEntity.getSalesNum().equals("1")){
            categoryItemDetailListViewHolder.category_list_item_tvUnit.setText("元/"+listEntity.getSalesUnit());
        }else {
            categoryItemDetailListViewHolder.category_list_item_tvUnit.setText("元/"+listEntity.getSalesNum()+listEntity.getSalesUnit());
        }
        if (TextUtils.isEmpty(listEntity.getTotalSoldOut())){
            categoryItemDetailListViewHolder.category_list_item_tvCount.setText("销量:0");
        }else {
            categoryItemDetailListViewHolder.category_list_item_tvCount.setText("销量:"+listEntity.getTotalSoldOut());
        }

        //价格
        if ("0".equals(listEntity.getPromotePrice())|| TextUtils.isEmpty(listEntity.getPromotePrice())) {
            categoryItemDetailListViewHolder.category_list_item_tvPrice.setText("￥" + listEntity.getPrice());
            categoryItemDetailListViewHolder.category_list_item_tvOldPrice.setVisibility(View.GONE);
            categoryItemDetailListViewHolder.category_list_item_view.setVisibility(View.GONE);
        } else {
            categoryItemDetailListViewHolder.category_list_item_tvOldPrice.setVisibility(View.VISIBLE);
            categoryItemDetailListViewHolder.category_list_item_view.setVisibility(View.VISIBLE);
            categoryItemDetailListViewHolder.category_list_item_tvOldPrice.setText("￥" + listEntity.getPrice());
            categoryItemDetailListViewHolder.category_list_item_tvPrice.setText("￥" + listEntity.getPromotePrice());
        }
        //距离
        DensityUtil.setDistance(categoryItemDetailListViewHolder.category_list_item_tvDistance,"",listEntity.getDistance());
        /**
         * 动画
         */
        Animation mAnimation = null ;
        /**
         * 显示动画的ImageView
         */
        mAnimation = AnimationUtils.loadAnimation(context,R.anim. viewbig);
        convertView.setAnimation(mAnimation );
        mAnimation.start();
        return convertView;
    }

    private class CategoryItemDetailListViewHolder{
        ImageView category_list_item_iv;
        TextView category_list_item_tvProductName;
        TextView category_list_item_tvStoreName;
        TextView category_list_item_tvMoney;
        TextView category_list_item_tvPrice;
        View category_list_item_view;
        TextView category_list_item_tvOldPrice;
        TextView category_list_item_tvDistance;
        TextView category_list_item_tvCount;
        TextView category_list_item_tvUnit;
    }
}
