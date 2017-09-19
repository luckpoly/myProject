package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.model.ChongzhiModel;
import com.brandsh.tiaoshi.utils.DateFormatUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */

public class BalanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    List<ChongzhiModel.DataBean> listData;
    int Type=1;

    public BalanceAdapter(Context context, List<ChongzhiModel.DataBean> listData) {
        this.context = context;
        this.listData = listData;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder=new MyViewHolder(inflater.inflate(R.layout.item_chongzhi,parent,false));
        return holder;

    }
  public void setData(List<ChongzhiModel.DataBean> listData){
        this.listData=listData;
        notifyDataSetChanged();
    }
    public void setType(int Type){
        this.Type=Type;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChongzhiModel.DataBean dataBean=listData.get(position);
        switch (Type){
            case 1:
                ((MyViewHolder)holder).total.setText(dataBean.getTotal()+"元");
                String phone=dataBean.getInvite();
                if (TextUtils.isEmpty(phone)){
                    ((MyViewHolder)holder).phone.setText("无");
                }else if (phone.length()>=11){
                    ((MyViewHolder)holder).phone.setText(phone.substring(0,3)+"****"+phone.substring(7,11));
                }
                ((MyViewHolder)holder).tv_name_type.setText("邀请人:");
                ((MyViewHolder)holder).deta.setText(DateFormatUtil.formatDateNohh(dataBean.getCreateTime()));
                ((MyViewHolder)holder).tv_yifan_money.setVisibility(View.GONE);
                break;
            case 2:
                ((MyViewHolder)holder).total.setText("已充"+dataBean.getTotal()+"元");
                String phone1=dataBean.getTopup();
                if (TextUtils.isEmpty(phone1)){
                    ((MyViewHolder)holder).phone.setText("无");
                }else if (phone1.length()>=11){
                    ((MyViewHolder)holder).phone.setText(phone1.substring(0,3)+"****"+phone1.substring(7,11));
                }
                ((MyViewHolder)holder).deta.setText(DateFormatUtil.formatDateNohh(dataBean.getCreateTime()));
                ((MyViewHolder)holder).tv_name_type.setText("被邀请人:");
                ((MyViewHolder)holder).tv_yifan_money.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(dataBean.getCashback())){
                    ((MyViewHolder)holder).tv_yifan_money.setText("未达到返现金额");
                }else {

                    ((MyViewHolder)holder).tv_yifan_money.setText("返现"+dataBean.getCashback()+"元");
                }
                break;
        }


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
    class MyViewHolder  extends RecyclerView.ViewHolder{
        TextView phone;
        TextView total;
        TextView deta;
        TextView tv_name_type;
        TextView tv_yifan_money;
        public MyViewHolder(View itemView) {
            super(itemView);
           phone=(TextView) itemView.findViewById(R.id.tv_phone);
            total=(TextView) itemView.findViewById(R.id.tv_money);
            deta=(TextView) itemView.findViewById(R.id.tv_deta);
            tv_name_type=(TextView) itemView.findViewById(R.id.tv_name_type);
            tv_yifan_money=(TextView) itemView.findViewById(R.id.tv_yifan_money);
        }
    }
}
