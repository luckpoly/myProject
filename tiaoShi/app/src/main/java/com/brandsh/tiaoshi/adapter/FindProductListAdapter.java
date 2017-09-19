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
import com.brandsh.tiaoshi.model.FindJsonData1;
import com.brandsh.tiaoshi.utils.DensityUtil;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by XianXianGe on 2016/1/9.
 */
public class FindProductListAdapter extends BaseAdapter {
    private List<FindJsonData1.DataBean.ListBean> resList;
    private Context context;
    private BitmapUtils bitmapUtils;
    private FindJsonData1.DataBean.ListBean listEntity;
    private int poi;

    public FindProductListAdapter(List<FindJsonData1.DataBean.ListBean> resList, Context context) {
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
        listEntity = resList.get(position);
        bitmapUtils = TiaoshiApplication.getGlobalBitmapUtils();
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.find_item,null);
            viewHolder.find_item_iv = (ImageView) convertView.findViewById(R.id.find_item_iv);
            viewHolder.find_item_tvProductName = (TextView) convertView.findViewById(R.id.find_item_tvProductName);
            viewHolder.find_item_tvStoreName = (TextView) convertView.findViewById(R.id.find_item_tvStoreName);
            viewHolder.find_item_tvMoney = (TextView) convertView.findViewById(R.id.find_item_tvMoney);
            viewHolder.find_item_tvPrice = (TextView) convertView.findViewById(R.id.find_item_tvPrice);
            viewHolder.find_item_tvUnit = (TextView) convertView.findViewById(R.id.find_item_tvUnit);
            viewHolder.find_item_tvOldPrice = (TextView) convertView.findViewById(R.id.find_item_tvOldPrice);
            viewHolder.find_item_view = convertView.findViewById(R.id.find_item_view);
            viewHolder.find_item_tvDistance = (TextView) convertView.findViewById(R.id.find_item_tvDistance);
            viewHolder.find_item_tvCount = (TextView) convertView.findViewById(R.id.find_item_tvCount);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bitmapUtils.display(viewHolder.find_item_iv, listEntity.getThumImg());
        /**
         * 动画
         */
        Animation mAnimation = null ;
        /**
         * 显示动画的ImageView
         */
        mAnimation = AnimationUtils.loadAnimation(context,R.anim. viewbig);
//        viewHolder.find_item_iv.setAnimation(mAnimation );
        convertView.setAnimation(mAnimation );
        mAnimation.start();
        viewHolder.find_item_tvProductName.setText(listEntity.getGoodsName());
        viewHolder.find_item_tvStoreName.setText(listEntity.getShopName());
        viewHolder.find_item_tvMoney.setText("满￥"+listEntity.getFreeSend()+"元免费配送");
        viewHolder.find_item_tvPrice.setText("￥"+listEntity.getPromotePrice());
        if (TextUtils.isEmpty(listEntity.getSalesNum())||listEntity.getSalesNum().equals("1")){
            viewHolder.find_item_tvUnit.setText("元/"+listEntity.getSalesUnit());
        }else {
            viewHolder.find_item_tvUnit.setText("元/"+listEntity.getSalesNum()+listEntity.getSalesUnit());
        }
        viewHolder.find_item_tvOldPrice.setText(listEntity.getPrice());
        if (!TextUtils.isEmpty(listEntity.getTotalSoldOut())){
            viewHolder.find_item_tvCount.setText("销量:" + listEntity.getTotalSoldOut());
        }else {
            viewHolder.find_item_tvCount.setText("销量:0");
        }
        //设置原价
        if ("0".equals(listEntity.getPromotePrice())|| TextUtils.isEmpty(listEntity.getPromotePrice())) {
            viewHolder.find_item_tvPrice.setText("￥" + listEntity.getPrice());
            viewHolder.find_item_tvOldPrice.setVisibility(View.GONE);
            viewHolder.find_item_view.setVisibility(View.GONE);
        } else {
            viewHolder.find_item_tvOldPrice.setVisibility(View.VISIBLE);
            viewHolder.find_item_view.setVisibility(View.VISIBLE);
            viewHolder.find_item_tvOldPrice.setText("￥" + listEntity.getPrice());
            viewHolder.find_item_tvPrice.setText("￥" + listEntity.getPromotePrice());
        }
        DensityUtil.setDistance(viewHolder.find_item_tvDistance,"",listEntity.getDistance());
        return convertView;
    }

    class ViewHolder{
        private ImageView find_item_iv;
        private TextView find_item_tvProductName;
        private TextView find_item_tvStoreName;
        private TextView find_item_tvMoney;
        private TextView find_item_tvPrice;
        private TextView find_item_tvUnit;
        private TextView find_item_tvOldPrice;
        private TextView find_item_tvDistance;
        private TextView find_item_tvCount;
        private View find_item_view;
    }
}
