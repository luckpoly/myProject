package com.goodfood86.tiaoshi.order121Project.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.model.MsgListModel;

import java.util.List;

/**
 * Created by tiashiwang on 2016/4/12.
 */
public class MsgListAdapter extends BaseAdapter {
    private List<MsgListModel.DataBean.ListBean> mlist;
    private Context context;
    private LayoutInflater inflater;

    public MsgListAdapter(List<MsgListModel.DataBean.ListBean> mlist, Context context) {
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
        MsgListModel.DataBean.ListBean bean=mlist.get(position);
        if (convertView==null){
            viewHolder =new ViewHolder();
            convertView=inflater.inflate(R.layout.item_msglist,null);
            viewHolder.tv_msgtime= (TextView) convertView.findViewById(R.id.tv_msgtime);
            viewHolder.tv_msgcontent= (TextView) convertView.findViewById(R.id.tv_msgcontent);
            viewHolder.tv_dingdan_titlemsg= (TextView) convertView.findViewById(R.id.tv_dingdan_titlemsg);
            viewHolder.iv_msglist_title=(ImageView)convertView.findViewById(R.id.iv_msglist_title);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_msgtime.setText(bean.getDateTime());
        viewHolder.tv_msgcontent.setText(bean.getContent());
        viewHolder.tv_dingdan_titlemsg.setText(bean.getSubject());
if (bean.getStatus()==0){
    viewHolder.iv_msglist_title.setImageDrawable(context.getResources().getDrawable(R.mipmap.no_read_message));
}else {
    viewHolder.iv_msglist_title.setImageDrawable(context.getResources().getDrawable(R.mipmap.message));
}
        return convertView;
    }
    class ViewHolder{
        ImageView iv_msglist_title;
        TextView tv_msgtime,tv_msgcontent,tv_dingdan_titlemsg;
    }
}
