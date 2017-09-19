package com.goodfood86.tiaoshi.order121Project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.model.CoursListModel;
import com.goodfood86.tiaoshi.order121Project.model.CouserTypeModel;

import java.util.List;

/**
 * Created by tiashiwang on 2016/4/12.
 */
public class CoursListAdapter extends BaseAdapter {
    private List<CoursListModel.DataBean> mlist;
    private Context context;
    private LayoutInflater inflater;

    public CoursListAdapter(List<CoursListModel.DataBean> mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
        inflater=LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        final CoursListModel.DataBean bean=mlist.get(position);
        if (convertView==null){
            viewHolder =new ViewHolder();
             convertView = inflater.inflate(R.layout.item_cours, null);
            viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.rtb_star = (RatingBar) convertView.findViewById(R.id.rtb_star);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Order121Application.getHeadImgBitmapUtils().display(viewHolder.iv_icon,bean.getIcon());
        viewHolder.tv_name.setText(bean.getName());
        viewHolder.tv_content.setText(bean.getSchool());
        try {
            viewHolder.rtb_star.setRating(Integer.parseInt(bean.getScore()));
        }catch (Exception e){
            viewHolder.rtb_star.setRating(4);
        }
        return convertView;
    }
    class ViewHolder{
        ImageView iv_icon;
        TextView tv_name,tv_content;
        RatingBar rtb_star;
    }

}
