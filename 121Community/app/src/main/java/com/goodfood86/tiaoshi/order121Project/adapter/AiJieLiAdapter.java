package com.goodfood86.tiaoshi.order121Project.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.activity.ZixunDetailActivity;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.model.CustomActivityModel;
import com.goodfood86.tiaoshi.order121Project.model.PubDocModel;

import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */

public class AiJieLiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    List<PubDocModel.DataBean.NodesBean> listData;

    public AiJieLiAdapter(Context context, List<PubDocModel.DataBean.NodesBean> listData) {
        this.context = context;
        this.listData = listData;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(inflater.inflate(R.layout.item_gy_jieli, parent, false));
        return holder;

    }

    public void setData(List<PubDocModel.DataBean.NodesBean> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PubDocModel.DataBean.NodesBean dataBean = listData.get(position);
        ((MyViewHolder) holder).tv_main_name.setText(dataBean.getName());
        Order121Application.getGlobalBitmapUtils().display(((MyViewHolder) holder).iv_huodong_icon, dataBean.getIcon());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_main_name;
        ImageView iv_huodong_icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_huodong_icon = (ImageView) itemView.findViewById(R.id.iv_huodong_icon);
            tv_main_name = (TextView) itemView.findViewById(R.id.tv_main_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            context.startActivity(new Intent(context,ZixunDetailActivity.class)
            .putExtra("id",listData.get(getAdapterPosition()).getId())
            .putExtra("name",listData.get(getAdapterPosition()).getName()));

        }
    }
}
