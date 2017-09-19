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
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ChooseDeliveryAddressActivity extends FragmentActivity implements View.OnClickListener {
    @ViewInject(R.id.choose_delivery_address_ivBack)
    ImageView choose_delivery_address_ivBack;
    @ViewInject(R.id.choose_delivery_address_listView)
    ListView choose_delivery_address_listView;
    @ViewInject(R.id.choose_delivery_address_rlAddAddress)
    LinearLayout choose_delivery_address_rlAddAddress;

    private HashMap requestMap;
    private UpdateAddressListBR updateAddressListBR;
    private ProgressDialog progDialog;
    private List<AddressListJsonData.DataBean.ListBean> resList;
    private MyAddressAdapter myAddressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_delivery_address);
        //沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);

        init();

        setListenerToLv();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_delivery_address_ivBack:
                finish();
                break;
            case R.id.choose_delivery_address_rlAddAddress:
                Intent intent = new Intent(this, AddDeliveryAddressActivity.class);
                startActivity(intent);
                break;
        }
    }

    private class UpdateAddressListBR extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("updateAddressList".equals(intent.getAction())) {
                OkHttpManager.postAsync(G.Host.ADDRESS_LIST, requestMap, new MyCallBack(1, ChooseDeliveryAddressActivity.this, new AddressListJsonData(), handler));
            }
        }
    }

    private void init() {
        choose_delivery_address_ivBack.setOnClickListener(this);
        choose_delivery_address_rlAddAddress.setOnClickListener(this);

        resList = new LinkedList<>();
        myAddressAdapter = new MyAddressAdapter(resList, this);
        choose_delivery_address_listView.setAdapter(myAddressAdapter);

        updateAddressListBR = new UpdateAddressListBR();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("updateAddressList");
        registerReceiver(updateAddressListBR, intentFilter);

        showProgressDialog();

        requestMap = new HashMap();
        requestMap.put("user_id", TiaoshiApplication.globalUserId);
        requestMap.put("token", TiaoshiApplication.globalToken);
        requestMap.put("actReq", "123456");
        requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(requestMap);
        requestMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.ADDRESS_LIST, requestMap, new MyCallBack(1, ChooseDeliveryAddressActivity.this, new AddressListJsonData(), handler));
    }

    private void setListenerToLv() {
        choose_delivery_address_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent("changeDefaultAddress");
                intent.putExtra("contact", resList.get(position).getContact());
                intent.putExtra("tel", resList.get(position).getTel());
                intent.putExtra("address", resList.get(position).getAddress());
                intent.putExtra("addressDetail", resList.get(position).getAddressDetail()+" "+resList.get(position).getNum());
                intent.putExtra("lng", resList.get(position).getLng());
                intent.putExtra("lat", resList.get(position).getLat());
                intent.putExtra("sex", resList.get(position).getSex());
                intent.putExtra("tag", resList.get(position).getTag());
                sendBroadcast(intent);
                finish();
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    AddressListJsonData addressListJsonData = (AddressListJsonData) msg.obj;
                    if (addressListJsonData != null) {
                        if ("SUCCESS".equals(addressListJsonData.getRespCode())) {
                            resList.clear();
                            resList.addAll(addressListJsonData.getData().getList());
                            myAddressAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(ChooseDeliveryAddressActivity.this, addressListJsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                    }
                    dissmissProgressDialog();
                    break;
            }
        }
    };

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
