package com.goodfood86.tiaoshi.order121Project.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.model.LlqDetailsModel;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class MyLlqHardImgAdapter extends RecyclerView.Adapter<MyLlqHardImgAdapter.ViewHolder>{
    public List<LlqDetailsModel.DataBean.ApplayBean> datas = null;
    public  String type;
    private Context context;

    public MyLlqHardImgAdapter(List<LlqDetailsModel.DataBean.ApplayBean> datas,String type) {
        this.datas = datas;
        this.type=type;
    }
    public MyLlqHardImgAdapter(Context context,List<LlqDetailsModel.DataBean.ApplayBean> datas, String type) {
        this.datas = datas;
        this.type=type;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.myllq_hardimgs_item,parent,false);
        ViewHolder vh=new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order121Application.getHeadImgBitmapUtils().display(holder.iv,datas.get(position).getIcon());
        holder. tv_people_name.setText(datas.get(position).getNickname());
        final String str=datas.get(position).getPhone();
        if (type.equals("已报名")){
            holder.tv_people_phone.setText("电话："+str.substring(0,str.length()-8)+"****"+str.substring(str.length()-4));
        }else {
            holder.tv_people_phone.setText("电话："+str);

            holder.tv_people_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("联系他(她):").setMessage(str);
                    builder.setPositiveButton("拨打", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent myInt=new Intent("android.intent.action.CALL", Uri.parse("tel:"+str));
                            context.startActivity(myInt);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    builder.show();


                }
            });
        }
    }

    @Override
    public int getItemCount() {
            return datas.size();

    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv;
        public TextView tv_people_name;
        public TextView tv_people_phone;

        public ViewHolder(View view){
            super(view);
             iv = (ImageView) view.findViewById(R.id.iv_llq_head);
            tv_people_name = (TextView) view.findViewById(R.id.tv_people_name);
            tv_people_phone = (TextView) view.findViewById(R.id.tv_people_phone);
        }
    }
}
