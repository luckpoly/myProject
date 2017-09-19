package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.widget.TitleBarView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by Administrator on 2016/4/12.
 */
public class OrderDetailActivity extends Activity{
    @ViewInject(R.id.ll_login_title)
    private RelativeLayout title_bar;
    private TitleBarView titleBarView;
    @ViewInject(R.id.tv_orderno)
    private TextView tv_orderno;
    @ViewInject(R.id.tv_time)
    private TextView tv_time;
    @ViewInject(R.id.tv_orderprice)
    private TextView tv_orderprice;
    @ViewInject(R.id.tv_unit)
    private TextView tv_unit;
    @ViewInject(R.id.tv_send_address)
    private TextView tv_sendaddress;
    @ViewInject(R.id.tv_send_info)
    private TextView tv_send_info;
    @ViewInject(R.id.tv_receive_address)
    private TextView tv_receive_address;
    @ViewInject(R.id.tv_receive_info)
    private TextView tv_receive_info;
    @ViewInject(R.id.tv_thing_name)
    private TextView tv_thing_name;
    @ViewInject(R.id.tv_beizhu)
    private TextView tv_beizhu;
    @ViewInject(R.id.gettime)
    private TextView gettime;
    @ViewInject(R.id.tv_pay_type)
    private TextView tv_pay_type;
    @ViewInject(R.id.tv_order_total_price)
    private TextView tv_order_total_price;
    @ViewInject(R.id.tv_order_pay_price)
    private TextView tv_order_pay_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderdetail_activity);
        ViewUtils.inject(this);
        initTitleBar();
        initView();
    }
    public void onResume() {
        super.onResume();
        //友盟统计
       //已删
    }
    public void onPause() {
        super.onPause();
        //友盟统计
        //已删
    }
    private void initTitleBar() {
        titleBarView=new TitleBarView(this,title_bar,"订单详情");
    }

    private void initView() {
        int statusPay=getIntent().getIntExtra("statusPay",0);
        tv_thing_name.setText(getIntent().getStringExtra("thingName"));
        if (getIntent().getStringExtra("beizhu")!=null) {
            tv_beizhu.setText(getIntent().getStringExtra("beizhu"));
        }else {
            tv_beizhu.setText("");
        }

        tv_orderno.setText(getIntent().getStringExtra("orderNo"));
        tv_time.setText(getTime(getIntent().getIntExtra("orderTime",0)));
        tv_orderprice.setText(getIntent().getStringExtra("totalPrice")+"元");
        tv_unit.setText(getIntent().getStringExtra("unit"));
        tv_sendaddress.setText(getIntent().getStringExtra("sendAddress"));
        tv_send_info.setText(getIntent().getStringExtra("sendInfo"));
        tv_receive_address.setText(getIntent().getStringExtra("receiveAddress"));
        tv_receive_info.setText(getIntent().getStringExtra("receiveInfo"));
        tv_order_total_price.setText(getIntent().getStringExtra("totalPrice")+"元");
        if (statusPay==0){
            tv_pay_type.setText("尚未付款");
            tv_order_pay_price.setText("尚未付款");
        }else {
            tv_pay_type.setText("支付宝支付");
            tv_order_pay_price.setText(getIntent().getStringExtra("totalPrice")+"元");
        }
    }
    private String getTime(long time) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(time * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        String totalTime = simpleDateFormat.format(gc.getTime());
        return totalTime;
    }
}
