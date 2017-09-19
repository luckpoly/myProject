package com.brandsh.tiaoshi.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.activity.StoreDetailActivity;
import com.brandsh.tiaoshi.model.StoreProductJsonData;

import java.util.ArrayList;
import java.util.List;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {
    public int selectTypeId;
    public Activity activity;
    private  Handler handler;
    private List<StoreProductJsonData.DataBean.ListBean> dataList;
//    public ArrayList<GoodsItem> dataList;

    public TypeAdapter(Activity activity, List<StoreProductJsonData.DataBean.ListBean> dataList, Handler handler) {
        this.activity = activity;
        this.dataList = dataList;
        this.handler=handler;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StoreProductJsonData.DataBean.ListBean item = dataList.get(position);

        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        if(dataList==null){
            return 0;
        }
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvCount,type;
        private StoreProductJsonData.DataBean.ListBean item;
        public ViewHolder(View itemView) {
            super(itemView);
            tvCount = (TextView) itemView.findViewById(R.id.tvCount);
            type = (TextView) itemView.findViewById(R.id.type);
            itemView.setOnClickListener(this);
        }

        public void bindData(StoreProductJsonData.DataBean.ListBean item){
            this.item = item;
            type.setText(item.getCustomCategoryName());
//            int count = activity.getSelectedGroupCountByTypeId(item.typeId);
//            tvCount.setText(String.valueOf(count));
//            if(count<1){
//                tvCount.setVisibility(View.GONE);
//            }else{
//                tvCount.setVisibility(View.VISIBLE);
//            }
            if(item.getCustomCategoryId().equals(selectTypeId+"")){
                itemView.setBackgroundColor(Color.WHITE);
                type.setTextColor(activity.getResources().getColor(R.color.categorydata_nav_bg_selected));
            }else{
                itemView.setBackgroundColor(activity.getResources().getColor(R.color.bg_hui));
                type.setTextColor(activity.getResources().getColor(R.color.hui3));
            }

        }

        @Override
        public void onClick(View v) {
            Message message=new Message();
            message.what=8;
            message.arg1=Integer.parseInt(item.getCustomCategoryId());
            handler.sendMessage(message);
        }
    }
}
