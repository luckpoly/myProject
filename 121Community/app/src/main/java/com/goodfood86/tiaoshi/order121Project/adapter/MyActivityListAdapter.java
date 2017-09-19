package com.goodfood86.tiaoshi.order121Project.adapter;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.ActivityListModel;
import com.goodfood86.tiaoshi.order121Project.model.CreateActivityModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.core.BitmapSize;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import io.rong.imkit.RongIM;

/**
 * Created by tiashiwang on 2016/4/12.
 */
public class MyActivityListAdapter extends BaseAdapter {
    private List<ActivityListModel.DataBean.ListBean> mlist;
    private Context context;
    private LayoutInflater inflater;
    Handler handler;

    public MyActivityListAdapter(List<ActivityListModel.DataBean.ListBean> mlist, Context context, Handler handler) {
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
        final ActivityListModel.DataBean.ListBean bean=mlist.get(position);
        if (convertView==null){
            viewHolder =new ViewHolder();
            convertView=inflater.inflate(R.layout.myllq_list_item,null);
          viewHolder.iv_img=(ImageView)convertView.findViewById(R.id.iv_img);
          viewHolder.tv_act_name=(TextView)convertView.findViewById(R.id.tv_act_name);
          viewHolder.tv_time=(TextView)convertView.findViewById(R.id.tv_time);
          viewHolder.tv_people=(TextView)convertView.findViewById(R.id.tv_people);
          viewHolder.tv_pNo=(TextView)convertView.findViewById(R.id.tv_pNo);
          viewHolder.tv_baoming=(TextView)convertView.findViewById(R.id.tv_baoming);
          viewHolder.tv_chakan=(TextView)convertView.findViewById(R.id.tv_chakan);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Order121Application.getActivityBitmapUtils().display( viewHolder.iv_img, bean.getImg());
        viewHolder.tv_act_name.setText(bean.getName());
        viewHolder.tv_time.setText("活动日期："+formatDate(bean.getStartTime()).substring(0,10));
        viewHolder.tv_pNo.setText("报名人数："+bean.getApplyCount());
        viewHolder.tv_people.setText("发起人："+bean.getOmName());
        viewHolder.tv_chakan.setVisibility(View.VISIBLE);
        viewHolder.tv_chakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startGroupChat(context, bean.getId(),bean.getName());
            }
        });
        return convertView;
    }
    class ViewHolder{
        ImageView iv_img;
        TextView tv_act_name,tv_time,tv_people,tv_pNo,tv_baoming,tv_chakan;
    }

    private String formatDate(String str){
        long seconds = Long.parseLong(str);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(seconds * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(gc.getTime());
    }
    private void init(){
        BitmapDisplayConfig displayConfig = new BitmapDisplayConfig();

                //设置内存中Bitmap最大的像素尺寸,减少内存占用
                 BitmapSize size = new BitmapSize(100,50);
                 displayConfig.setBitmapMaxSize(size);
               //设置图片加载成功之后 到最终图片显示完成之间的动画效果

    }

}
