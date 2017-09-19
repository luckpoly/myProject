package com.goodfood86.tiaoshi.order121Project.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.model.LlqDetailsModel;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class LlqHardImgAdapter extends RecyclerView.Adapter<LlqHardImgAdapter.ViewHolder>{
    public List<LlqDetailsModel.DataBean.ApplayBean> datas = null;

    public LlqHardImgAdapter(List<LlqDetailsModel.DataBean.ApplayBean> datas) {
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.llq_hardimgs_item,parent,false);
        ViewHolder vh=new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order121Application.getHeadImgBitmapUtils().display(holder.iv,datas.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
            return datas.size();

    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv;
        public ViewHolder(View view){
            super(view);
             iv = (ImageView) view.findViewById(R.id.iv_llq_head);
        }
    }
}
