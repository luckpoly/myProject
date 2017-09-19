package com.brandsh.tiaoshi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.model.StoreModel;

import java.util.List;

/**
 * Created by libokang on 15/10/26.
 */
public class DemoListAdapter extends RecyclerView.Adapter {


    private List<StoreModel> mDataset;

    public DemoListAdapter(List<StoreModel> mStoreList) {
        mDataset = mStoreList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.demo_list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        StoreModel listEntity = mDataset.get(position);
        final ViewHolder holder = (ViewHolder) viewHolder;

        String imgURL = listEntity.getStoreImgURL();
        if (holder.img.getTag() == null || !imgURL.equals(holder.img.getTag().toString())) {
            TiaoshiApplication.getGlobalBitmapUtils().display(holder.img, imgURL);
            holder.img.setTag(listEntity.getStoreImgURL());
        }

        holder.title.setText(listEntity.getStoreName());
        holder.address.setText(listEntity.getStoreAddress());
        holder.distance.setText(listEntity.getDistance());
        holder.detail.setText(listEntity.getCategoryCount());

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

        public ImageView img;
        public TextView title;
        public TextView address;
        public TextView distance;
        public TextView detail;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            title = (TextView) itemView.findViewById(R.id.title);
            address = (TextView) itemView.findViewById(R.id.address);
            distance = (TextView) itemView.findViewById(R.id.distance);
            detail = (TextView) itemView.findViewById(R.id.detail);
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
