package com.goodfood86.tiaoshi.order121Project.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.activity.ShowImageActivity;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.CoursListModel;
import com.goodfood86.tiaoshi.order121Project.model.FriendModel;
import com.goodfood86.tiaoshi.order121Project.model.GridViewModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.photo.PreviewPhotoActivity;
import com.goodfood86.tiaoshi.order121Project.utils.DateFormatUtil;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by tiashiwang on 2016/4/12.
 */
public class FriendQuanListAdapter extends BaseAdapter {
    private List<FriendModel.DataBean.ListBean> mlist;
    private Context context;
    private LayoutInflater inflater;
    private boolean isMy=false;
    private Handler handler;

    public FriendQuanListAdapter(List<FriendModel.DataBean.ListBean> mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
        inflater=LayoutInflater.from(context);

    }
    public FriendQuanListAdapter(List<FriendModel.DataBean.ListBean> mlist, Context context,Handler handler,boolean isMy) {
        this.mlist = mlist;
        this.context = context;
        inflater=LayoutInflater.from(context);
        this.isMy=isMy;
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
        final FriendModel.DataBean.ListBean bean=mlist.get(position);
        if (convertView==null){
            viewHolder = new ViewHolder();
             convertView = inflater.inflate(R.layout.item_friendquan_list, null);
            viewHolder.iv_llq_head = (ImageView) convertView.findViewById(R.id.iv_llq_head);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.gv_group_partners=(GridView) convertView.findViewById(R.id.gv_group_partners);
            viewHolder.tv_showall=(TextView) convertView.findViewById(R.id.tv_showall);
            viewHolder.tv_delete=(TextView) convertView.findViewById(R.id.tv_delete);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        final ViewHolder finalViewHolder = viewHolder;
        Order121Application.getHeadImgBitmapUtils().display(finalViewHolder.iv_llq_head,bean.getIcon());
        finalViewHolder.tv_name .setText(bean.getNickname());
        finalViewHolder.tv_content .setText(bean.getContent());
        finalViewHolder.tv_time .setText(DateFormatUtil.formatDate(bean.getCreateTime()));
        final List<GridViewModel> modelList=new ArrayList<>();
        String[] img=bean.getImgs().split(",");
        for (int i = 0; i < img.length; i++) {
            if (!TextUtils.isEmpty(img[i])){
                GridViewModel model=new GridViewModel(img[i]);
                modelList.add(model);
            }
        }
        BaseActivityGridviewAdapter gridviewAdapter=new BaseActivityGridviewAdapter(context,modelList);
        finalViewHolder.gv_group_partners.setAdapter(gridviewAdapter);
        finalViewHolder.gv_group_partners.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(context, ShowImageActivity.class);
                intent.putExtra("No",position);
                String [] str=new String[modelList.size()];
                for (int i=0;i<modelList.size();i++){
                    str[i]=modelList.get(i).getImageUrl();
                }
                intent.putExtra("array",str);
                context.startActivity(intent);
            }
        });


        if (finalViewHolder.tv_content.getLineCount()<=5){
            finalViewHolder.tv_showall.setVisibility(View.GONE);
        }else {
            finalViewHolder.tv_content.setMaxLines(6);
            finalViewHolder.tv_showall.setText("全文");
            finalViewHolder.tv_showall.setVisibility(View.VISIBLE);
        }

        viewHolder.tv_showall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalViewHolder.tv_showall.getText().toString().equals("全文")){
                    finalViewHolder.tv_content.setMaxLines(100);
                    finalViewHolder.tv_showall.setText("收起");
                }else if (finalViewHolder.tv_showall.getText().toString().equals("收起")){
                    finalViewHolder.tv_content.setMaxLines(6);
                    finalViewHolder.tv_showall.setText("全文");
                }
            }
        });
        if (isMy){
            finalViewHolder.tv_delete.setVisibility(View.VISIBLE);
        }
        finalViewHolder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("删除说说:").setMessage("确定要删除此条说说？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap map=new HashMap();
                        map.put("token", Order121Application.globalLoginModel.getData().getToken());
                        map.put("blogId", bean.getId());
                        map.put("actReq", SignUtil.getRandom());
                        map.put("actTime", System.currentTimeMillis() / 1000 + "");
                        String sign = SignUtil.getSign(map);
                        map.put("sign", MD5.getMD5(sign));
                        OkHttpManager.postAsync(G.Host.BLOG_DEL,map,new MyCallBack(2,context,new FriendModel(),handler));
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
            }
        });
        return convertView;
    }
    class ViewHolder{
        ImageView iv_llq_head;
        TextView tv_name,tv_content,tv_time,tv_showall,tv_delete;
        GridView gv_group_partners;
    }

}
