package com.brandsh.tiaoshi.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.ConfirmOrderJsonData1;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/12/1.
 */

public class TopUpActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.tv_phone)
    TextView tv_phone;
    @ViewInject(R.id.ed_jine)
    EditText ed_jine;
    @ViewInject(R.id.ed_code)
    EditText ed_code;
    @ViewInject(R.id.ll_520yuan)
    LinearLayout ll_520yuan;
    @ViewInject(R.id.tv_go_pay)
    TextView tv_go_pay;
    @ViewInject(R.id.nav_title)
    TextView nav_title;
    @ViewInject(R.id.nav_back)
    ImageView nav_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup);
        AppUtil.Setbar(this);
        initView();
        initListener();
    }

    private void initView() {
        ViewUtils.inject(this);
        nav_title.setText("挑食充值");
        nav_back.setVisibility(View.VISIBLE);
        tv_phone.setText(TiaoshiApplication.phone);
    }

    private void initListener() {
        nav_back.setOnClickListener(this);
        ll_520yuan.setOnClickListener(this);
        tv_go_pay.setOnClickListener(this);
    }

    private void getData(String code, String money) {
        HashMap map = new HashMap();
        map.put("token", TiaoshiApplication.globalToken);
        map.put("orderType", "USER_TOP_UP");
        if (!TextUtils.isEmpty(code)) {
            map.put("inviteCode", code);
        }
        map.put("total", money);
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.CREATE_ORDER_EXT, map, new MyCallBack(1, this, new ConfirmOrderJsonData1(), handler));

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    ConfirmOrderJsonData1 confirmOrderJsonData1 = (ConfirmOrderJsonData1) msg.obj;
                    if ("SUCCESS".equals(confirmOrderJsonData1.getRespCode())) {
                        Intent intent1 = new Intent(TopUpActivity.this, PayOrderActivity.class);
                        intent1.putExtra("order_code", confirmOrderJsonData1.getData().getOrderCode() + "");
                        intent1.putExtra("orderId", confirmOrderJsonData1.getData().getOrderId() + "");
                        intent1.putExtra("total", confirmOrderJsonData1.getData().getTotal() + "");
                        intent1.putExtra("payOrderName", "挑食网充值"
                                + confirmOrderJsonData1.getData().getTotal() + " 元。");
                        intent1.putExtra("payOrderDetail", "付款来源: 安卓客户端,"
                                + "订单编号：" + confirmOrderJsonData1.getData().getOrderCode() + ",购买数量：1"
                                + ",合计：" + confirmOrderJsonData1.getData().getTotal() + " 元。");
                        startActivityForResult(intent1, 1);
                    } else {
                        shortToast(confirmOrderJsonData1.getRespMsg());
                    }
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == -1) {
                Intent intent = new Intent("com.brandsh.tiaoshi.MyFragment");
                sendBroadcast(intent);
                ed_jine.setText("");
            }
        }
    }

    @Override
    public void onClick(View v) {
        String code = ed_code.getText().toString();
        String money = ed_jine.getText().toString();
        switch (v.getId()) {
            case R.id.nav_back:
                finish();
                break;
            case R.id.tv_go_pay:
                if (TextUtils.isEmpty(money)) {
                    shortToast("请输入充值金额!");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    call(money);
                } else {
                    getData(code, money);
                }
                break;
            case R.id.ll_520yuan:
                if (TextUtils.isEmpty(code)) {
                    call("520");
                } else {
                    getData(code, "520");
                }
                break;
        }
    }

    private void call(final String money) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("您还未填写邀请码，确定去支付？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getData("", money);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
}
