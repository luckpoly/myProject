package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.model.CategoryJsonData;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by apple on 16/2/21.
 */
public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private List<CategoryJsonData.DataBean> resList;
    private CategoryJsonData.DataBean listEntity;
    private BitmapUtils bitmapUtils;

    public CategoryAdapter(Context context, List<CategoryJsonData.DataBean> resList) {
        this.context = context;
        this.resList = resList;
    }

    @Override
    public int getCount() {
        if (resList!=null){
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
        bitmapUtils = TiaoshiApplication.getGlobalBitmapUtils();
        listEntity = resList.get(position);
        CategoryItemViewHolder categoryItemViewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.category_item, null);
            categoryItemViewHolder = new CategoryItemViewHolder();
            categoryItemViewHolder.img = (ImageView) convertView.findViewById(R.id.category_item_img);
            categoryItemViewHolder.textView = (TextView) convertView.findViewById(R.id.category_item_text);
            convertView.setTag(categoryItemViewHolder);
        }else {
            categoryItemViewHolder = (CategoryItemViewHolder) convertView.getTag();
        }
        bitmapUtils.display(categoryItemViewHolder.img, listEntity.getIcon());
        /**
         * 动画
         */
        Animation mAnimation = null ;
        /**
         * 显示动画的ImageView
         */
        mAnimation = AnimationUtils.loadAnimation(context,R.anim. viewbig);
        convertView.setAnimation(mAnimation );
        mAnimation.start();
        categoryItemViewHolder.textView.setText(listEntity.getName()+"");
        return convertView;
    }

    private class CategoryItemViewHolder{
        private ImageView img;
        private TextView textView;
    }
}
