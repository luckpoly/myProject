package com.goodfood86.tiaoshi.order121Project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.model.GridViewModel;
import com.goodfood86.tiaoshi.order121Project.model.YuLeListModel;
import com.goodfood86.tiaoshi.order121Project.utils.DensityUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class YuLeListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<YuLeListModel.DataBean.ListBean> nodesBeanT;

    public YuLeListAdapter(Context context, List<YuLeListModel.DataBean.ListBean> nodesBeanT) {
        this.context = context;
        this.nodesBeanT = nodesBeanT;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return nodesBeanT.size();
    }

    @Override
    public Object getItem(int position) {
        return nodesBeanT.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.item_yule, null);
        ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        TextView tv_money = (TextView) view.findViewById(R.id.tv_money);
        TextView tv_juli = (TextView) view.findViewById(R.id.tv_juli);
        Order121Application.getGlobalBitmapUtils().display(iv_icon,nodesBeanT.get(position).getIcon());
        tv_name.setText(nodesBeanT.get(position).getTitle());
         tv_content.setText(nodesBeanT.get(position).getAddress()+nodesBeanT.get(position).getAddressDetail());
         tv_money .setText(nodesBeanT.get(position).getPerCost()+"元");
DensityUtil.setDistance(tv_juli,"距离：",nodesBeanT.get(position).getDistance());


        return view;
    }
}
