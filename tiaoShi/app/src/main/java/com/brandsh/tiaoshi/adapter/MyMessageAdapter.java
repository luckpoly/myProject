package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.DeleteMsgJsonData;
import com.brandsh.tiaoshi.model.MyMessageListJsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.widget.MySwipeView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by apple on 16/2/23.
 */
public class MyMessageAdapter extends BaseAdapter implements View.OnClickListener{
    private List<MyMessageListJsonData.DataBean.ListBean>  resList;
    private Context context;
    private PullToRefreshListView pullToRefreshListView;
    private Handler handler;
    private HttpUtils httpUtils;
    private HashMap requestMap;
    private MyMessageListJsonData.DataBean.ListBean listEntity;

    public MyMessageAdapter(List<MyMessageListJsonData.DataBean.ListBean> resList, Context context, PullToRefreshListView pullToRefreshListView, Handler handler) {
        this.resList = resList;
        this.context = context;
        this.pullToRefreshListView = pullToRefreshListView;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return resList.size();
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
        listEntity = resList.get(position);
        MsgItemViewHolder msgItemViewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.my_message_item, null);
            msgItemViewHolder = new MsgItemViewHolder();
            msgItemViewHolder.mySwipeView = (MySwipeView) convertView;
            msgItemViewHolder.msg_item_ivStatus = (ImageView) convertView.findViewById(R.id.msg_item_ivStatus);
            msgItemViewHolder.msg_item_tvTitle = (TextView) convertView.findViewById(R.id.msg_item_tvTitle);
            msgItemViewHolder.msg_item_tvDate = (TextView) convertView.findViewById(R.id.msg_item_tvDate);
            msgItemViewHolder.msg_item_tvContent = (TextView) convertView.findViewById(R.id.msg_item_tvContent);
            msgItemViewHolder.msg_item_tvDelete = (TextView) convertView.findViewById(R.id.msg_item_tvDelete);
            convertView.setTag(msgItemViewHolder);
        }else {
            msgItemViewHolder = (MsgItemViewHolder) convertView.getTag();
        }
        msgItemViewHolder.mySwipeView.setPtrListView(pullToRefreshListView);
        msgItemViewHolder.mySwipeView.setTag(msgItemViewHolder.mySwipeView.getId(), position);
        msgItemViewHolder.msg_item_tvDelete.setTag("" + position);
        msgItemViewHolder.msg_item_tvDelete.setOnClickListener(this);
        if ("0".equals(listEntity.getViewStatus())){
            msgItemViewHolder.msg_item_ivStatus.setImageResource(R.mipmap.notice_undo);
        }else if ("1".equals(listEntity.getViewStatus())){
            msgItemViewHolder.msg_item_ivStatus.setImageResource(R.mipmap.notice);
        }
        if ("0".equals(listEntity.getType())){
            msgItemViewHolder.msg_item_tvTitle.setText("其他消息");
        }else if("1".equals(listEntity.getType())||"3".equals(listEntity.getType())||"4".equals(listEntity.getType())){
            msgItemViewHolder.msg_item_tvTitle.setText("系统消息");
        } else if ("2".equals(listEntity.getType())){
            msgItemViewHolder.msg_item_tvTitle.setText("订单消息");
        }else {
            msgItemViewHolder.msg_item_tvTitle.setText(listEntity.getTitle());
        }
        msgItemViewHolder.msg_item_tvDate.setText(formatDate(listEntity.getCreateTime()));
        msgItemViewHolder.msg_item_tvContent.setText(listEntity.getContent());
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.msg_item_tvDelete:
                String postion = (String) v.getTag();
                ((MySwipeView)v.getParent()).quickClose();
              requestMap=new HashMap();
                requestMap.put("token", TiaoshiApplication.globalToken);
                requestMap.put("msgId", resList.get(Integer.parseInt(postion)).getMsgId());
                requestMap.put("actReq","123456");
                requestMap.put("actTime",System.currentTimeMillis()/1000+"");
                String sign= SignUtil.getSign(requestMap);
                requestMap.put("sign", Md5.toMd5(sign));
                OkHttpManager.postAsync(G.Host.DELETE_MESSAGE,requestMap,new MyCallBack(3, context, new DeleteMsgJsonData(), handler));



                break;
        }
    }

    private class MsgItemViewHolder{
        private MySwipeView mySwipeView;
        private ImageView msg_item_ivStatus;
        private TextView msg_item_tvTitle;
        private TextView msg_item_tvDate;
        private TextView msg_item_tvContent;
        private TextView msg_item_tvDelete;
    }

    private String formatDate(String str){
        long seconds = Long.parseLong(str);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(seconds * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(gc.getTime());
    }
}
