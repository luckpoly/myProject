package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.constant.G;

import java.util.List;

/**
 * Created by 猪猪~ on 2016/3/12.
 */
public class UploadImgAdapter extends BaseAdapter {
    private List<Bitmap> resList;
    private Context context;
    private List<String> base64List;
    private ImageView iv_add_img;

    public UploadImgAdapter(List<Bitmap> resList, Context context, List<String> base64List, ImageView iv_add_img) {
        this.resList = resList;
        this.context = context;
        this.base64List = base64List;
        this.iv_add_img = iv_add_img;
    }

    @Override
    public int getCount() {

        if (resList != null) {
            return resList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return resList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.upload_img_item, null);
            viewHolder = new ViewHolder();
            viewHolder.upload_img_item_ivContent = (ImageView) convertView.findViewById(R.id.upload_img_item_ivContent);
            viewHolder.upload_img_item_ivDelete = (ImageView) convertView.findViewById(R.id.upload_img_item_ivDelete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.upload_img_item_ivContent.setImageBitmap(resList.get(position));
        viewHolder.upload_img_item_ivDelete.setOnClickListener(new DeleteImgClick(position));
        return convertView;
    }

    private class DeleteImgClick implements View.OnClickListener {
        private int position;

        public DeleteImgClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            resList.remove(position);
            base64List.remove(position);
            notifyDataSetChanged();
            if (resList.size() < 5&& base64List.size() < 5){
                iv_add_img.setVisibility(View.VISIBLE);
            }
        }
    }

    private class ViewHolder {
        ImageView upload_img_item_ivContent;
        ImageView upload_img_item_ivDelete;
    }
}
