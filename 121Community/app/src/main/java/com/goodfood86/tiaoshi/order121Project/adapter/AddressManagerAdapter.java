package com.goodfood86.tiaoshi.order121Project.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.activity.AddressManagerActivity;
import com.goodfood86.tiaoshi.order121Project.activity.EditHistoryAddressActivity;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.AddressmanagerModel;
import com.goodfood86.tiaoshi.order121Project.model.CommonResultInfoModel;
import com.goodfood86.tiaoshi.order121Project.model.HistoryAddressListModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.widget.ProgressHUD;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

/**
 * Created by Administrator on 2016/4/5.
 */
public class AddressManagerAdapter extends BaseAdapter implements View.OnClickListener{
    private final LayoutInflater layoutInflater;
    private Context mContext;
    private Handler handler;
    private ProgressHUD dialog;
    private List<HistoryAddressListModel.DataEntity.ListEntity> addressmanagerModelList;
    public  AddressManagerAdapter(List<HistoryAddressListModel.DataEntity.ListEntity> addressmanagerModelList, Context mContext, Handler handler, ProgressHUD dialog){
        this.addressmanagerModelList=addressmanagerModelList;
        this.mContext=mContext;
        this.handler=handler;
        this.dialog=dialog;
        layoutInflater=LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        return addressmanagerModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return addressmanagerModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.addressmanager_item,parent,false);
            viewHolder.tv1= (TextView) convertView.findViewById(R.id.tv_address);
            viewHolder.tv_phone= (TextView) convertView.findViewById(R.id.tv_phone);
            viewHolder.iv_del= (ImageView) convertView.findViewById(R.id.address_del);
            viewHolder.iv_edit= (ImageView) convertView.findViewById(R.id.iv_edit);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv1.setText(addressmanagerModelList.get(position).getAddress()+addressmanagerModelList.get(position).getAddressDetail());
        viewHolder.tv_phone.setText(addressmanagerModelList.get(position).getPhone());
        viewHolder.iv_del.setOnClickListener(this);
        viewHolder.iv_del.setTag(position);
        viewHolder.iv_edit.setOnClickListener(this);
        viewHolder.iv_edit.setTag(position);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        if (v!=null){
            switch (v.getId()){
                case R.id.address_del:
                    int position= (int) v.getTag();
                    Log.e("222",dialog+"  00");
                    dialog.show();
                    RequestParams requestParams=new RequestParams();
                    requestParams.addBodyParameter("id",addressmanagerModelList.get(position).getId()+"");
                    requestParams.addBodyParameter("token", Order121Application.globalLoginModel.getData().getToken());
                    Log.e("===",position+"  "+addressmanagerModelList.get(position).getId());
                    Order121Application.getGlobalHttpUtils().send(HttpRequest.HttpMethod.POST, G.Host.DEL_HISTORYADDRESS,requestParams,new MyRequestCallBack(mContext,handler,3,new CommonResultInfoModel()));
                    break;
                case R.id.iv_edit:
                    int position1= (int) v.getTag();
                    Log.e("position1",position1+"");
                    Intent intent=new Intent(mContext, EditHistoryAddressActivity.class);
                    intent.putExtra("address",addressmanagerModelList.get(position1).getAddress());
                    intent.putExtra("position",position1+"");
                    ((AddressManagerActivity)mContext).startActivityForResult(intent,(((AddressManagerActivity)mContext).GO_EDITADDRESS));
                    break;
            }
        }

    }

    private class ViewHolder{
        private TextView tv1,tv_phone;
        private ImageView iv_del;
        private ImageView iv_edit;
    }
}
