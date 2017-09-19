package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.activity.AddDeliveryAddressActivity;
import com.brandsh.tiaoshi.activity.EditDeliveryAddresActivity;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.AddressListJsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sisi on 16/3/9.
 */
public class MyAddressAdapter extends BaseAdapter {
    private List<AddressListJsonData.DataBean.ListBean> resList;
    private Context context;
    private AddressListJsonData.DataBean.ListBean  dataEntity;
    private String from;
    Handler handler;

    public MyAddressAdapter(List<AddressListJsonData.DataBean.ListBean> resList, Context context) {
        this.resList = resList;
        this.context = context;
    }
    public MyAddressAdapter(List<AddressListJsonData.DataBean.ListBean> resList, Context context, String from, Handler handler) {
        this.resList = resList;
        this.context = context;
        this.from=from;
        this.handler=handler;
    }

    @Override
    public int getCount() {
        if (resList !=null){
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
       dataEntity = resList.get(position);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.my_address_item, null);
            viewHolder = new ViewHolder();
            viewHolder.my_address_item_tvContact = (TextView) convertView.findViewById(R.id.my_address_item_tvContact);
            viewHolder.my_address_item_tvPhone = (TextView) convertView.findViewById(R.id.my_address_item_tvPhone);
            viewHolder.my_address_item_tvAddress = (TextView) convertView.findViewById(R.id.my_address_item_tvAddress);
            viewHolder.ll_set_default = (LinearLayout) convertView.findViewById(R.id.ll_set_default);
            viewHolder.tv_sex = (TextView) convertView.findViewById(R.id.tv_sex);
            viewHolder.tv_tag = (TextView) convertView.findViewById(R.id.tv_tag);
            viewHolder.iv_go_bj = (ImageView) convertView.findViewById(R.id.iv_go_bj);
            viewHolder.iv_set_default = (ImageView) convertView.findViewById(R.id.iv_set_default);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TextPaint tp1 = viewHolder.my_address_item_tvContact.getPaint();
        tp1.setFakeBoldText(true);
        viewHolder.my_address_item_tvContact.setText(dataEntity.getContact());
        viewHolder.my_address_item_tvPhone.setText(dataEntity.getTel());
        if (!TextUtils.isEmpty(dataEntity.getTag())){
            viewHolder.tv_tag.setText(dataEntity.getTag());
            viewHolder.tv_tag.setVisibility(View.VISIBLE);
            viewHolder.my_address_item_tvAddress.setText("　　　"+dataEntity.getAddress()+dataEntity.getAddressDetail()+" "+dataEntity.getNum());
        }else {
            viewHolder.tv_tag.setVisibility(View.GONE);
            viewHolder.my_address_item_tvAddress.setText(dataEntity.getAddress()+dataEntity.getAddressDetail()+" "+dataEntity.getNum());
        }
        if (!TextUtils.isEmpty(dataEntity.getSex())){
            viewHolder.tv_sex.setText(dataEntity.getSex()+"士");
            viewHolder.tv_sex.setVisibility(View.VISIBLE);
        }else {
            viewHolder.tv_sex.setVisibility(View.GONE);
        }

        if (from!=null&&from.equals("MyAddress")){
            viewHolder.ll_set_default.setVisibility(View.VISIBLE);
            if (dataEntity.getIsDefault().equals("YES")){
                viewHolder.iv_set_default.setImageResource(R.mipmap.default_adds_yes);
                viewHolder.iv_set_default.setOnClickListener(null);
            }else {
                viewHolder.iv_set_default.setImageResource(R.mipmap.default_adds_no);
                viewHolder.iv_set_default.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap map=new HashMap();
                        map.put("token", TiaoshiApplication.globalToken);
                        map.put("addressId",resList.get(position).getAddressId());
                        map.put("isDefault","YES");
                        map.put("actReq",SignUtil.getRandom());
                        map.put("actTime",System.currentTimeMillis()/1000+"");
                        String sign= SignUtil.getSign(map);
                        map.put("sign", Md5.toMd5(sign));
                        OkHttpManager.postAsync(G.Host.SET_DEFAULT_ADDS,map,new MyCallBack(2,context,new AddressListJsonData(),handler));
                    }
                });
            }
        }else {
            viewHolder.ll_set_default.setVisibility(View.GONE);
            viewHolder.iv_go_bj.setVisibility(View.GONE);
        }
        viewHolder.iv_go_bj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,EditDeliveryAddresActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("addr_id", dataEntity.getAddressId());
                bundle.putString("lng", dataEntity.getLng());
                bundle.putString("lat", dataEntity.getLat());
                bundle.putString("contact", dataEntity.getContact());
                bundle.putString("phone", dataEntity.getTel());
                bundle.putString("address1", dataEntity.getAddress());
                bundle.putString("address",dataEntity.getAddressDetail());
                bundle.putString("sex",dataEntity.getSex());
                bundle.putString("tag",dataEntity.getTag());
                bundle.putString("num",dataEntity.getNum());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private class ViewHolder{
        TextView my_address_item_tvContact;
        TextView my_address_item_tvPhone;
        TextView my_address_item_tvAddress;
        TextView tv_sex;
        TextView tv_tag;
        ImageView iv_go_bj,iv_set_default;
        LinearLayout ll_set_default;
    }
}
