package com.goodfood86.tiaoshi.order121Project.adapter;

import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.model.PubDocModel;
import com.goodfood86.tiaoshi.order121Project.model.YaodianListModel;
import com.goodfood86.tiaoshi.order121Project.utils.DensityUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class YaodianGridviewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<YaodianListModel.DataBean.ListBean> nodesBeanT;
    private int poi;

    public YaodianGridviewAdapter(Context context, List<YaodianListModel.DataBean.ListBean> nodesBeanT) {
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

        View view = layoutInflater.inflate(R.layout.item_yiliao_yaodian, null);
        ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        TextView tv_juli = (TextView) view.findViewById(R.id.tv_juli);
        TextPaint tp = tv_name.getPaint();
        tp.setFakeBoldText(true);
        Order121Application.getHeadImgBitmapUtils().display(iv_icon,nodesBeanT.get(position).getIcon());
        tv_name.setText(nodesBeanT.get(position).getName());
        tv_content.setText("地址："+nodesBeanT.get(position).getAddress()+nodesBeanT.get(position).getAddressDetail());
        tv_juli.setText(nodesBeanT.get(position).getDistance());
        DensityUtil.setDistance(tv_juli,"距离：",nodesBeanT.get(position).getDistance());
        return view;
    }
}
