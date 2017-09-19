package com.brandsh.tiaoshi.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.MyAddressAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.AddressListJsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MyDeliveryAddressActivity extends FragmentActivity implements View.OnClickListener {
    @ViewInject(R.id.my_address_ivBack)
    ImageView my_address_ivBack;
    @ViewInject(R.id.my_address_listView)
    ListView my_address_listView;
    @ViewInject(R.id.my_address_rlAddAddress)
    LinearLayout my_address_rlAddAddress;
    private HashMap requestMap;
    private UpdateAddressListBR updateAddressListBR;
    private ProgressDialog progDialog;
    private List<AddressListJsonData.DataBean.ListBean> resList;
    private MyAddressAdapter myAddressAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_delivery_address);
//沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);
        init();
        initData();
        setListenerToLv();
    }
    private class UpdateAddressListBR extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("updateAddressList".equals(intent.getAction())){
                initData();
            }
        }
    }

    private void setListenerToLv() {
        my_address_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyDeliveryAddressActivity.this,EditDeliveryAddresActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("addr_id", resList.get(position).getAddressId());
                bundle.putString("lng", resList.get(position).getLng());
                bundle.putString("lat", resList.get(position).getLat());
                bundle.putString("contact", resList.get(position).getContact());
                bundle.putString("phone", resList.get(position).getTel());
                bundle.putString("address1", resList.get(position).getAddress());
                bundle.putString("address",resList.get(position).getAddressDetail());
                bundle.putString("sex",resList.get(position).getSex());
                bundle.putString("tag",resList.get(position).getTag());
                bundle.putString("num",resList.get(position).getNum());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在加载");
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }


    private void init() {
        my_address_ivBack.setOnClickListener(this);
        my_address_rlAddAddress.setOnClickListener(this);

        resList = new LinkedList<>();
        myAddressAdapter = new MyAddressAdapter(resList, this,"MyAddress",handler);
        my_address_listView.setAdapter(myAddressAdapter);

        updateAddressListBR = new UpdateAddressListBR();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("updateAddressList");
        registerReceiver(updateAddressListBR, intentFilter);
        showProgressDialog();

    }
    private void initData(){
        requestMap=new HashMap();
//        requestMap.put("user_id", TiaoshiApplication.globalUserId);
        requestMap.put("token", TiaoshiApplication.globalToken);
        requestMap.put("actReq","123456");
        requestMap.put("actTime",System.currentTimeMillis()/1000+"");
        String sign= SignUtil.getSign(requestMap);
        requestMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.ADDRESS_LIST,requestMap,new MyCallBack(1, MyDeliveryAddressActivity.this, new AddressListJsonData(), handler));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_address_ivBack:
                finish();
                break;
            case R.id.my_address_rlAddAddress:
                Intent intent = new Intent(this, AddDeliveryAddressActivity.class);
                startActivity(intent);
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    AddressListJsonData addressListJsonData = (AddressListJsonData) msg.obj;
                    if (addressListJsonData != null) {
                        if ("SUCCESS".equals(addressListJsonData.getRespCode())){
                            resList.clear();
                            resList.addAll(addressListJsonData.getData().getList());
                            myAddressAdapter.notifyDataSetChanged();
                        }
                    }else {
                        Toast.makeText(MyDeliveryAddressActivity.this, addressListJsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                    }
                    dissmissProgressDialog();
                    break;
                case 2:
                    AddressListJsonData addressListJsonData1 = (AddressListJsonData) msg.obj;
                    if ("SUCCESS".equals(addressListJsonData1.getRespCode())){
                        ToastUtil.showShort(MyDeliveryAddressActivity.this,"设置成功");
                      initData();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        unregisterReceiver(updateAddressListBR);
        super.onDestroy();
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
