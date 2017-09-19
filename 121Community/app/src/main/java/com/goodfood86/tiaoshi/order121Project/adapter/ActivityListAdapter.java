package com.goodfood86.tiaoshi.order121Project.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.activity.LlqDateilsActivity;
import com.goodfood86.tiaoshi.order121Project.activity.ShowImageActivity;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.ActivityListModel;
import com.goodfood86.tiaoshi.order121Project.model.CreateActivityModel;
import com.goodfood86.tiaoshi.order121Project.model.GridViewModel;
import com.goodfood86.tiaoshi.order121Project.model.GroupUserQueryModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.RongHttp;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.core.BitmapSize;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import io.rong.imkit.RongIM;

/**
 * Created by tiashiwang on 2016/4/12.
 */
public class ActivityListAdapter extends BaseAdapter {
    private List<ActivityListModel.DataBean.ListBean> mlist;
    private Context context;
    private LayoutInflater inflater;
    Handler handler;

    public ActivityListAdapter(List<ActivityListModel.DataBean.ListBean> mlist, Context context,Handler handler) {
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
            convertView=inflater.inflate(R.layout.llq_list_item,null);
          viewHolder.iv_llq_head=(ImageView)convertView.findViewById(R.id.iv_llq_head);
          viewHolder.tv_act_name=(TextView)convertView.findViewById(R.id.tv_act_name);
          viewHolder.tv_time=(TextView)convertView.findViewById(R.id.tv_time);
          viewHolder.tv_people=(TextView)convertView.findViewById(R.id.tv_people);
          viewHolder.tv_pNo=(TextView)convertView.findViewById(R.id.tv_pNo);
          viewHolder.tv_baoming=(TextView)convertView.findViewById(R.id.tv_baoming);
          viewHolder.into_group=(TextView)convertView.findViewById(R.id.into_group);
          viewHolder.tv_chakan=(TextView)convertView.findViewById(R.id.tv_chakan);
          viewHolder.ll_go_detail=(LinearLayout) convertView.findViewById(R.id.ll_go_detail);
          viewHolder.gv_group_partners=(GridView) convertView.findViewById(R.id.gv_group_partners);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Order121Application.getHeadImgBitmapUtils().display(viewHolder.iv_llq_head,bean.getUserIcon());
        String[] str=bean.getImg().split(",");
       List<GridViewModel> modelList=new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            GridViewModel model=new GridViewModel(str[i]);
            modelList.add(model);
        }
        ActivityImgItemAdapter gridviewAdapter=new ActivityImgItemAdapter(context,modelList);
       viewHolder.gv_group_partners.setAdapter(gridviewAdapter);
        viewHolder.tv_act_name.setText("(发起"+bean.getName()+"活动)");
        viewHolder.tv_time.setText("报名截止时间:"+formatDate(bean.getEndTime()).substring(0,10));
        viewHolder.tv_pNo.setText("已报名:"+bean.getApplyCount());
        viewHolder.tv_people.setText(bean.getOmName());
       if(bean.getIsApply().equals("ON")){
           viewHolder.tv_baoming.setVisibility(View.VISIBLE);
           viewHolder.into_group.setVisibility(View.GONE);
       }else {
           viewHolder.tv_baoming.setVisibility(View.GONE);
           viewHolder.into_group.setVisibility(View.VISIBLE);
       }
        viewHolder.tv_baoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap map =new HashMap();
                map.put("token",Order121Application.globalLoginModel.getData().getToken());
                map.put("customActivityId",bean.getId());
                map.put("actReq", SignUtil.getRandom());
                map.put("actTime",System.currentTimeMillis()/1000+"");
                String sign= SignUtil.getSign(map);
                map.put("sign", MD5.getMD5(sign));
                OkHttpManager.postAsync(G.Host.BAOMING_ACTIVITY,map,new MyCallBack(2,context,new CreateActivityModel(),handler));
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        List<NameValuePair> nameValuePair = new ArrayList<>();
                        nameValuePair.add(new BasicNameValuePair("groupId",bean.getId()));
                        nameValuePair.add(new BasicNameValuePair("userId",Order121Application.globalLoginModel.getData().getPhone()));
                        nameValuePair.add(new BasicNameValuePair("groupName",bean.getName()));
                        RongHttp.rPostHttp("group/create.json",nameValuePair,new GroupUserQueryModel(),handler,4,context);
                    }
                }.start();


            }
        });
        viewHolder.into_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startGroupChat(context, bean.getId(),bean.getName());
            }
        });
        viewHolder.ll_go_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, LlqDateilsActivity.class);
                intent.putExtra("id",bean.getId());
                context.startActivity(intent);
            }
        });
        viewHolder.gv_group_partners.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(context, ShowImageActivity.class);
                intent.putExtra("No",position);
                String [] str=bean.getImg().split(",");
                intent.putExtra("array",str);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder{
//        ImageView iv_img;
        TextView tv_act_name,tv_time,tv_people,tv_pNo,tv_baoming,tv_chakan,into_group;
        GridView gv_group_partners;
        ImageView iv_llq_head;
        LinearLayout ll_go_detail;
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
