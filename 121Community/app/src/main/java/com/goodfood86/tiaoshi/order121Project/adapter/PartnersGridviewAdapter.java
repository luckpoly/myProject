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
import com.goodfood86.tiaoshi.order121Project.model.PubDocModel;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class PartnersGridviewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<PubDocModel.DataBean.NodesBean> nodesBeanT;

    public PartnersGridviewAdapter(Context context, List<PubDocModel.DataBean.NodesBean> nodesBeanT) {
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

        View view = layoutInflater.inflate(R.layout.item_grid_partners, null);
        ImageView iv_partners_icon = (ImageView) view.findViewById(R.id.iv_partners_icon);
        TextView tv_partners_name = (TextView) view.findViewById(R.id.tv_partners_name);
        TextView tv_name_intro = (TextView) view.findViewById(R.id.tv_name_intro);
        TextView tv_partners_content = (TextView) view.findViewById(R.id.tv_partners_content);
        Order121Application.getHeadImgBitmapUtils().display(iv_partners_icon,nodesBeanT.get(position).getIcon());
        tv_partners_name.setText(nodesBeanT.get(position).getName());
        tv_partners_content.setText("　　"+nodesBeanT.get(position).getIntro());
        tv_name_intro.setText(nodesBeanT.get(position).getSubName());
        return view;
    }
}
