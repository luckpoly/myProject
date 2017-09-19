package com.brandsh.tiaoshi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.model.ProductModel;

import java.util.List;

/**
 * Created by libokang on 15/10/26.
 */
public class HotProductAdapter extends RecyclerView.Adapter {


    private List<ProductModel> mDataset;

    public HotProductAdapter(List<ProductModel> mStoreList) {
        mDataset = mStoreList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.hot_product_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ProductModel productModel = mDataset.get(position);
        final ViewHolder holder = (ViewHolder) viewHolder;

        String imgURL = productModel.getImgURL();
        if (holder.img.getTag() == null || !imgURL.equals(holder.img.getTag().toString())) {
            TiaoshiApplication.getGlobalBitmapUtils().display(holder.img, imgURL);
            holder.img.setTag(productModel.getImgURL());
        }

        holder.title.setText(productModel.getProductName());
        holder.store_name.setText(productModel.getStoreName());
        holder.price.setText("￥" + productModel.getPrice());
        holder.count_and_unit.setText("元  /  " + productModel.getCount() + "" + productModel.getUnit());
        holder.sale_count.setText("销量:" + productModel.getSaleCount());

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
        public TextView store_name;
        public TextView distance;
        public TextView price;
        public TextView count_and_unit;
        public TextView sale_count;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            title = (TextView) itemView.findViewById(R.id.title);
            store_name = (TextView) itemView.findViewById(R.id.store_name);
            distance = (TextView) itemView.findViewById(R.id.distance);
            price = (TextView) itemView.findViewById(R.id.price);
            count_and_unit = (TextView) itemView.findViewById(R.id.count_and_unit);
            sale_count = (TextView) itemView.findViewById(R.id.sale_count);
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
