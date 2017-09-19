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

import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class BaseActivityGridviewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<GridViewModel> nodesBeanT;

    public BaseActivityGridviewAdapter(Context context, List<GridViewModel> nodesBeanT) {
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

        View view = layoutInflater.inflate(R.layout.item_gridview_activity_base, null);
        ImageView iv_img = (ImageView) view.findViewById(R.id.iv_image);
        Order121Application.getGlobalBitmapUtils().display(iv_img,nodesBeanT.get(position).getImageUrl());
        return view;
    }
}
