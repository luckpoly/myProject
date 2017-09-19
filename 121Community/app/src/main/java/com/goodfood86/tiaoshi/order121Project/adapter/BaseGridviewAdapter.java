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
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.GridViewModel;
import com.goodfood86.tiaoshi.order121Project.model.ModuleMainModel;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class BaseGridviewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<GridViewModel> nodesBeanT;

    public BaseGridviewAdapter(Context context, List<GridViewModel> nodesBeanT) {
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
        View view = layoutInflater.inflate(R.layout.item_gridview_base, null);
        ImageView iv_img = (ImageView) view.findViewById(R.id.iv_image);
        ImageView iv_image_jy = (ImageView) view.findViewById(R.id.iv_image_jy);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_type_name);
        if (nodesBeanT.get(position).getTypeName()!=null){
            tv_title.setText(nodesBeanT.get(position).getTypeName());
            if (nodesBeanT.get(position).getDrawable() != null) {
                iv_img.setImageDrawable(nodesBeanT.get(position).getDrawable());
            }
        }else {
            tv_title.setVisibility(View.GONE);
            iv_img.setVisibility(View.GONE);
            iv_image_jy.setVisibility(View.VISIBLE);
            iv_image_jy.setImageDrawable(nodesBeanT.get(position).getDrawable());
        }

        return view;
    }
}
