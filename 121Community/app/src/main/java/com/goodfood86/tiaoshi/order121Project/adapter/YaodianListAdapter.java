package com.goodfood86.tiaoshi.order121Project.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.model.ActivityListModel;
import com.goodfood86.tiaoshi.order121Project.model.YaodianListModel;
import com.goodfood86.tiaoshi.order121Project.utils.DensityUtil;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.core.BitmapSize;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import io.rong.imkit.RongIM;

/**
 * Created by tiashiwang on 2016/4/12.
 */
public class YaodianListAdapter extends BaseAdapter {
    private List<YaodianListModel.DataBean.ListBean> mlist;
    private Context context;
    private LayoutInflater inflater;
    Handler handler;

    public YaodianListAdapter(List<YaodianListModel.DataBean.ListBean> mlist, Context context, Handler handler) {
        this.mlist = mlist;
        this.context = context;
        inflater=LayoutInflater.from(context);
        this.handler=handler;
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
        final YaodianListModel.DataBean.ListBean bean=mlist.get(position);
        if (convertView==null){
            viewHolder =new ViewHolder();
             convertView = inflater.inflate(R.layout.item_yiliao_yaodian, null);
            viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.tv_juli = (TextView) convertView.findViewById(R.id.tv_juli);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Order121Application.getHeadImgBitmapUtils().display(viewHolder.iv_icon,bean.getIcon());
        viewHolder.tv_name.setText(bean.getName());
        viewHolder.tv_content.setText(bean.getAddress()+bean.getAddressDetail());
        viewHolder.tv_juli.setText(bean.getDistance());
        DensityUtil.setDistance(viewHolder.tv_juli,"距离：",bean.getDistance());
        return convertView;
    }
    class ViewHolder{
        ImageView iv_icon;
        TextView tv_name,tv_content,tv_juli;
    }

}
