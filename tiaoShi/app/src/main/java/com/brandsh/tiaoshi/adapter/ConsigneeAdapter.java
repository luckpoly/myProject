package com.brandsh.tiaoshi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.model.ConsigneeModel;

import java.util.List;

/**
 * Created by libokang on 15/10/26.
 */
public class ConsigneeAdapter extends RecyclerView.Adapter {


    private List<ConsigneeModel> mDataset;

    public ConsigneeAdapter(List<ConsigneeModel> mStoreList) {
        mDataset = mStoreList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.consignee_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ConsigneeModel consigneeModel = mDataset.get(position);
        final ViewHolder holder = (ViewHolder) viewHolder;

        holder.tv_address.setText(consigneeModel.getAddress());
        holder.tv_name_and_tel.setText(consigneeModel.getName() + "    " + consigneeModel.getTel());

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_address;
        public TextView tv_name_and_tel;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            tv_name_and_tel = (TextView) itemView.findViewById(R.id.tv_name_and_tel);
        }
    }


    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


}
