package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.model.SearchJsonData;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by sisi on 16/3/7.
 */
public class SearchAdapter extends BaseAdapter {
    private Context context;
    private List<SearchJsonData.DataBean.ListBean> resList;
    private SearchJsonData.DataBean.ListBean listEntity;
    private BitmapUtils bitmapUtils;
    private int poi;
    private BitmapUtils tagBitmapUtils;

    public SearchAdapter(Context context, List<SearchJsonData.DataBean.ListBean> resList) {
        this.context = context;
        this.resList = resList;
        bitmapUtils = TiaoshiApplication.getGlobalBitmapUtils();
        tagBitmapUtils = new BitmapUtils(context);
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
        SearchViewHolder searchViewHolder = null;
        listEntity = resList.get(position);
        if (convertView == null) {
            searchViewHolder = new SearchViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.search_item, null);
            searchViewHolder.search_item_iv = (ImageView) convertView.findViewById(R.id.search_item_iv);
            searchViewHolder.search_item_tvGoodsName = (TextView) convertView.findViewById(R.id.search_item_tvGoodsName);
            searchViewHolder.search_item_llTag = (LinearLayout) convertView.findViewById(R.id.search_item_llTag);
            searchViewHolder.search_item_ivGift = (ImageView) convertView.findViewById(R.id.search_item_ivGift);
            searchViewHolder.search_ivChaoZhi = (ImageView) convertView.findViewById(R.id.search_ivChaoZhi);
            searchViewHolder.search_ivChaoZhi2 = (ImageView) convertView.findViewById(R.id.search_ivChaoZhi2);
            searchViewHolder.search_item_tvShopName = (TextView) convertView.findViewById(R.id.search_item_tvShopName);
            searchViewHolder.search_item_tvMoney1 = (TextView) convertView.findViewById(R.id.search_item_tvMoney1);
            searchViewHolder.search_item_tvPrice = (TextView) convertView.findViewById(R.id.search_item_tvPrice);
            searchViewHolder.search_item_tvUnit = (TextView) convertView.findViewById(R.id.search_item_tvUnit);
            searchViewHolder.search_item_view = convertView.findViewById(R.id.search_item_view);
            searchViewHolder.search_item_tvOldPrice = (TextView) convertView.findViewById(R.id.search_item_tvOldPrice);
            searchViewHolder.search_tvDistance = (TextView) convertView.findViewById(R.id.search_tvDistance);
            searchViewHolder.search_item_tvMoney2 = (TextView) convertView.findViewById(R.id.search_item_tvMoney2);
            searchViewHolder.search_item_tvCount = (TextView) convertView.findViewById(R.id.search_item_tvCount);
            convertView.setTag(searchViewHolder);
        } else {
            searchViewHolder = (SearchViewHolder) convertView.getTag();
        }
        //商品图片
        bitmapUtils.display(searchViewHolder.search_item_iv, listEntity.getThumImg());
        //商品名称
        searchViewHolder.search_item_tvGoodsName.setText(listEntity.getGoodsName());

        String juli = Double.parseDouble(listEntity.getDistance()) / 1000.0 + "";
        //距离
        for (int i = 0; i < juli.length(); i++) {
            if (".".equals(juli.charAt(i) + "")) {
                poi = i;
            }
        }
        if (poi == 1 && "0".equals(juli.charAt(0) + "")) {
            if ("0".equals(juli.charAt(2) + "")) {
                if ("0".equals(juli.charAt(3) + "")) {
                    searchViewHolder.search_tvDistance.setText(juli.substring(4, poi + 4) + "m");
                } else {
                    searchViewHolder.search_tvDistance.setText(juli.substring(3, poi + 4) + "m");
                }
            } else {
                searchViewHolder.search_tvDistance.setText(juli.substring(2, poi + 4) + "m");
            }
        } else {
            searchViewHolder.search_tvDistance.setText(juli.substring(0, poi + 3) + "km");
        }
        //店铺名称
        searchViewHolder.search_item_tvShopName.setText(listEntity.getName());
        //单位
        searchViewHolder.search_item_tvUnit.setText("元/" + listEntity.getSalesUnit());
        //不同类型商品做不同区分
//        if ("0".equals(listEntity.getIs_new())) {
        searchViewHolder.search_item_llTag.setVisibility(View.GONE);
        searchViewHolder.search_item_tvMoney1.setVisibility(View.VISIBLE);
        searchViewHolder.search_item_tvMoney1.setText("满￥" + listEntity.getFreeSend() + "元,免费配送");
        //设置原价
        if ("0".equals(listEntity.getPromotePrice()) || TextUtils.isEmpty(listEntity.getPromotePrice())) {
            searchViewHolder.search_item_tvPrice.setText("￥" + listEntity.getPrice());
            searchViewHolder.search_item_tvOldPrice.setVisibility(View.GONE);
            searchViewHolder.search_item_view.setVisibility(View.GONE);
        } else {
            searchViewHolder.search_item_tvOldPrice.setVisibility(View.VISIBLE);
            searchViewHolder.search_item_view.setVisibility(View.VISIBLE);
            searchViewHolder.search_item_tvOldPrice.setText("￥" + listEntity.getPrice());
            searchViewHolder.search_item_tvPrice.setText("￥" + listEntity.getPromotePrice());
        }
        //隐藏第二种起送金额
        searchViewHolder.search_item_tvMoney2.setVisibility(View.GONE);
        //设置销量
        searchViewHolder.search_item_tvCount.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(listEntity.getTotalSoldOut())) {
            searchViewHolder.search_item_tvCount.setText("销量:0");
        } else {
            searchViewHolder.search_item_tvCount.setText("销量:" + listEntity.getTotalSoldOut());
        }
//        }else if ("1".equals(listEntity.getIs_new())){
//            searchViewHolder.search_item_llTag.setVisibility(View.VISIBLE);
//            searchViewHolder.search_item_tvMoney1.setVisibility(View.GONE);
//            searchViewHolder.search_item_tvPrice.setText(listEntity.getOld_price());
//            searchViewHolder.search_item_view.setVisibility(View.GONE);
//            searchViewHolder.search_item_tvOldPrice.setVisibility(View.GONE);
//            searchViewHolder.search_item_tvMoney2.setVisibility(View.VISIBLE);
//            searchViewHolder.search_item_tvMoney2.setText("满￥" + listEntity.getMin_cost() + "元,免费配送");
//            searchViewHolder.search_item_tvCount.setVisibility(View.GONE);
//            //抢鲜图标
//            if (listEntity.getTags().size() == 1){
//                tagBitmapUtils.display(searchViewHolder.search_item_ivGift, listEntity.getTags().get(0));
//            }else if (listEntity.getTags().size() == 2){
//                tagBitmapUtils.display(searchViewHolder.search_item_ivGift, listEntity.getTags().get(0));
//                tagBitmapUtils.display(searchViewHolder.search_ivChaoZhi, listEntity.getTags().get(1));
//            }else if (listEntity.getTags().size() == 3){
//                tagBitmapUtils.display(searchViewHolder.search_item_ivGift, listEntity.getTags().get(0));
//                tagBitmapUtils.display(searchViewHolder.search_ivChaoZhi, listEntity.getTags().get(1));
//                tagBitmapUtils.display(searchViewHolder.search_ivChaoZhi2, listEntity.getTags().get(2));
//            }
//        }

        return convertView;
    }

    private class SearchViewHolder {
        ImageView search_item_iv;
        TextView search_item_tvGoodsName;
        LinearLayout search_item_llTag;
        ImageView search_item_ivGift;
        ImageView search_ivChaoZhi;
        ImageView search_ivChaoZhi2;
        TextView search_item_tvShopName;
        TextView search_item_tvMoney1;
        TextView search_item_tvPrice;
        TextView search_item_tvUnit;
        View search_item_view;
        TextView search_item_tvOldPrice;
        TextView search_tvDistance;
        TextView search_item_tvMoney2;
        TextView search_item_tvCount;
    }
}
