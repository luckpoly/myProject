package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.model.QiangXianJsonData;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by XianXianGe on 2016/1/8.
 */
public class QiangXianListAdapter extends BaseAdapter {
    private List<QiangXianJsonData.DataBean.ListBean> resList;
    private Context context;
    private QiangXianJsonData.DataBean.ListBean listEntity;
    private BitmapUtils bitmapUtils;
    private BitmapUtils tagBitmapUtils;
    private int poi;

    public QiangXianListAdapter(List<QiangXianJsonData.DataBean.ListBean> resList, Context context) {
        this.resList = resList;
        this.context = context;
        this.bitmapUtils = TiaoshiApplication.getGlobalBitmapUtils();
        this.tagBitmapUtils = new BitmapUtils(context);
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
        listEntity = resList.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.qiangxian_item, null);
            viewHolder.qiangxian_item_iv = (ImageView) convertView.findViewById(R.id.qiangxian_item_iv);
            viewHolder.qiangxian_item_ivGift = (ImageView) convertView.findViewById(R.id.qiangxian_item_ivGift);
            viewHolder.qiangxian_item_ivChaoZhi = (ImageView) convertView.findViewById(R.id.qiangxian_item_ivChaoZhi);
            viewHolder.qiangxian_item_ivChaoZhi2 = (ImageView) convertView.findViewById(R.id.qiangxian_item_ivChaoZhi2);
            viewHolder.qiangxian_item_tvProductName = (TextView) convertView.findViewById(R.id.qiangxian_item_tvProductName);
            viewHolder.qiangxian_item_tvStoreName = (TextView) convertView.findViewById(R.id.qiangxian_item_tvStoreName);
            viewHolder.qiangxian_item_tvPrice = (TextView) convertView.findViewById(R.id.qiangxian_item_tvPrice);
            viewHolder.qiangxian_item_tvUnit = (TextView) convertView.findViewById(R.id.qiangxian_item_tvUnit);
            viewHolder.qiangxian_item_tvDistance = (TextView) convertView.findViewById(R.id.qiangxian_item_tvDistance);
            viewHolder.qiangxian_item_tvMoney = (TextView) convertView.findViewById(R.id.qiangxian_item_tvMoney);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bitmapUtils.display(viewHolder.qiangxian_item_iv, listEntity.getThumImg());
        viewHolder.qiangxian_item_tvProductName.setText(listEntity.getGoodsName());
        if (listEntity.getTags().size() == 1){
            tagBitmapUtils.display(viewHolder.qiangxian_item_ivGift, listEntity.getTags().get(0));
        }else if (listEntity.getTags().size() == 2){
            tagBitmapUtils.display(viewHolder.qiangxian_item_ivGift, listEntity.getTags().get(0));
            tagBitmapUtils.display(viewHolder.qiangxian_item_ivChaoZhi, listEntity.getTags().get(1));
        }else if (listEntity.getTags().size() == 3){
            tagBitmapUtils.display(viewHolder.qiangxian_item_ivGift, listEntity.getTags().get(0));
            tagBitmapUtils.display(viewHolder.qiangxian_item_ivChaoZhi, listEntity.getTags().get(1));
            tagBitmapUtils.display(viewHolder.qiangxian_item_ivChaoZhi2, listEntity.getTags().get(2));
        }
        viewHolder.qiangxian_item_tvStoreName.setText(listEntity.getShopName());
        viewHolder.qiangxian_item_tvPrice.setText(listEntity.getPrice());
        viewHolder.qiangxian_item_tvUnit.setText("元/"+listEntity.getUnit());
        String juli=Double.parseDouble(listEntity.getDistance())/1000.0+"";
        for (int i =0; i<juli.length(); i++){
            if (".".equals(juli.charAt(i)+"")){
                poi = i;
            }
        }
        //距离
        if (poi==1&&"0".equals(juli.charAt(0)+"")){
            if ("0".equals(juli.charAt(2)+"")){
                if ("0".equals(juli.charAt(3)+"")){
                    viewHolder.qiangxian_item_tvDistance.setText(juli.substring(4, poi + 4)+"m");
                }else {
                    viewHolder.qiangxian_item_tvDistance.setText(juli.substring(3, poi + 4)+"m");
                }
            }else {
                viewHolder.qiangxian_item_tvDistance.setText(juli.substring(2, poi + 4)+"m");
            }        }else {
            viewHolder.qiangxian_item_tvDistance.setText(juli.substring(0, poi+3)+"km");
        }
        viewHolder.qiangxian_item_tvMoney.setText("满"+listEntity.getFreeSend()+"元免费配送");
        return convertView;

    }

    class ViewHolder {
        ImageView qiangxian_item_iv;
        TextView qiangxian_item_tvProductName;
        ImageView qiangxian_item_ivGift;
        ImageView qiangxian_item_ivChaoZhi;
        ImageView qiangxian_item_ivChaoZhi2;
        TextView qiangxian_item_tvStoreName;
        TextView qiangxian_item_tvPrice;
        TextView qiangxian_item_tvUnit;
        TextView qiangxian_item_tvDistance;
        TextView qiangxian_item_tvMoney;
    }
}
