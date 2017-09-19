package com.brandsh.tiaoshi.activity;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.JuiceOrderdetailItemAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.FenxiangModel;
import com.brandsh.tiaoshi.model.JuiceOrderListdata;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.brandsh.tiaoshi.widget.ProductDetailImgListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/8/23.
 */
public class JuiceOrderDetailActivity extends Activity implements View.OnClickListener{
    @ViewInject(R.id.order_product_PDILV)
     private   ProductDetailImgListView order_product_PDILV;
    @ViewInject(R.id.tv_name)
     private   TextView tv_name;
    @ViewInject(R.id.tv_phone)
    private   TextView tv_phone;
    @ViewInject(R.id.tv_content)
    private   TextView tv_content;
    @ViewInject(R.id.tv_address)
    private   TextView tv_address;
    @ViewInject(R.id.tv_confirm)
    private   TextView tv_confirm;
    @ViewInject(R.id.tv_title)
    private   TextView tv_title;
    @ViewInject(R.id.tv_userphone)
    private   TextView tv_userphone;
    @ViewInject(R.id.ll_address)
    private LinearLayout ll_address;
    @ViewInject(R.id.product_detail_rlBack)
    private RelativeLayout product_detail_rlBack;
    private ChangeAddressBR changeAddressBR;
    private boolean isHavenAddress;
    private HashMap orderRequestMap;

    private JuiceOrderListdata.DataBean orderListJsondata1;
    private JuiceOrderdetailItemAdapter orderListItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juice_orderdetail);
        AppUtil.Setbar(this);
        initView();
    }
    private void initView(){
        ViewUtils.inject(this);
        ll_address.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        product_detail_rlBack.setOnClickListener(this);
        orderRequestMap=new HashMap();
        orderListJsondata1= (JuiceOrderListdata.DataBean) getIntent().getExtras().getSerializable("orderList");
        orderListItemAdapter = new JuiceOrderdetailItemAdapter(orderListJsondata1.getOrderGoods(),this) ;
        order_product_PDILV.setAdapter(orderListItemAdapter);
        tv_name.setText(orderListJsondata1.getShareUsername());
        tv_phone.setText(orderListJsondata1.getSharePhone());
        tv_content.setText(orderListJsondata1.getShareIntro());
        changeAddressBR = new ChangeAddressBR();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("changeAddress");
        registerReceiver(changeAddressBR, intentFilter);
        if (null!=getIntent().getStringExtra("type")&&getIntent().getStringExtra("type").equals("orderDetail")){

            if (!orderListJsondata1.getGetStatus().equals("GET")){
                tv_confirm.setVisibility(View.GONE);
                ll_address.setOnClickListener(null);
            }
            tv_address.setText(orderListJsondata1.getGetAddress()+orderListJsondata1.getGetAddressDetail());
            tv_userphone.setText(orderListJsondata1.getGetPhone());
            tv_title.setText("领取详情");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_address:
                Intent intent1 = new Intent(this, ChooseDeliveryAddressActivity.class);
                startActivity(intent1);
                break;
            case  R.id.tv_confirm:
                if (!isHavenAddress) {
                    Toast.makeText(JuiceOrderDetailActivity.this, "请先选择地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendData();
                break;
            case R.id.product_detail_rlBack:
                finish();
                break;
        }
    }
    private String lng;
    private String lat;
    private String contact;
    private String tel;
    private String address;
    private String addressDetail;
    private class ChangeAddressBR extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("changeAddress".equals(intent.getAction())) {
                isHavenAddress = true;
                lng = intent.getStringExtra("lng");
                lat = intent.getStringExtra("lat");
                contact = intent.getStringExtra("contact");
                tel = intent.getStringExtra("tel");
                address = intent.getStringExtra("address");
                addressDetail = intent.getStringExtra("addressDetail");
                orderRequestMap.clear();
                orderRequestMap.put("lng", lng);
                orderRequestMap.put("lat", lat);
                orderRequestMap.put("contact", contact);
                orderRequestMap.put("phone", tel);
                orderRequestMap.put("addressDetail", addressDetail);
                orderRequestMap.put("address", address);
                tv_userphone.setText(tel);
                tv_address.setText(address + addressDetail);
            }
        }
    }
    private void sendData(){
        HashMap map=new HashMap();
        map.clear();
        map=orderRequestMap;
        map.put("orderId", orderListJsondata1.getOrderId()+"");
        map.put("token", TiaoshiApplication.globalToken);
        map.put("actReq",SignUtil.getRandom());
        map.put("actTime",System.currentTimeMillis()/1000+"");
        String sign= SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.SHARE_SET_ADDRESS,map,new MyCallBack(1,JuiceOrderDetailActivity.this,new FenxiangModel(),handler));
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    FenxiangModel fenxiangModel= (FenxiangModel) msg.obj;
                    if ("SUCCESS".equals(fenxiangModel.getRespCode())){
                        ToastUtil.showShort(JuiceOrderDetailActivity.this,"领取成功");
                        finish();
                    }else {
                        ToastUtil.showShort(JuiceOrderDetailActivity.this,fenxiangModel.getRespMsg());
                        finish();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onDestroy() {
        unregisterReceiver(changeAddressBR);
        super.onDestroy();
    }
}
