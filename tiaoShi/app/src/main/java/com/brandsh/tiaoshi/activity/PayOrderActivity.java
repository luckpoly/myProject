package com.brandsh.tiaoshi.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.alipay.Alipay;
import com.brandsh.tiaoshi.alipay.PayResult;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.OrderPayWayModel;
import com.brandsh.tiaoshi.model.WeiXinPayDate;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.LogUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.brandsh.tiaoshi.wxapi.Constants;
import com.brandsh.tiaoshi.wxapi.MD5;
import com.brandsh.tiaoshi.wxapi.WXPayEntryActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Random;

public class PayOrderActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.pay_order_ivBack)
    private ImageView pay_order_ivBack;
    @ViewInject(R.id.pay_order_tvOrderCode)
    private TextView pay_order_tvOrderCode;
    @ViewInject(R.id.pay_order_tvOrderTotal)
    private TextView pay_order_tvOrderTotal;
    @ViewInject(R.id.pay_order_btnPay)
    private Button pay_order_btnPay;
    @ViewInject(R.id.rl_apily)
    private RelativeLayout rl_apily;
    @ViewInject(R.id.rl_weixing)
    private RelativeLayout rl_weixing;
    @ViewInject(R.id.rl_yuepay)
    private RelativeLayout rl_yuepay;
    @ViewInject(R.id.iv_apliy)
    private ImageView iv_apliy;
    @ViewInject(R.id.iv_weixing)
    private ImageView iv_weixing;
    @ViewInject(R.id.iv_yuepay)
    private ImageView iv_yuepay;

    private AlertDialog.Builder builder;
    private Alipay alipay;
    private String payOrderName;
    private String payOrderDetail;
    private String payOrderTotal;
    private String total;
    private int pay_Type = 1;
    private ProgressDialog progDialog;
    private PaySuccessBroadcastReciver paySuccessBroadcastReciver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_order);
        //沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);
        init();
        getPayWay();
        registerReceiver();
    }

    /**
     * 显示进度框
     */
    private void showProgressDialog(String str) {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage(str);
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
        pay_order_ivBack.setOnClickListener(this);
        pay_order_btnPay.setOnClickListener(this);
        rl_apily.setOnClickListener(this);
        rl_weixing.setOnClickListener(this);
        iv_yuepay.setOnClickListener(this);
        builder = new AlertDialog.Builder(this).setTitle("确定离开").setMessage("订单未付款,是否离开该页面?").setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        pay_order_tvOrderCode.setText("订单号：" + getIntent().getStringExtra("order_code"));
        alipay = new Alipay(this, handler, getIntent().getStringExtra("order_code"));
        payOrderName = getIntent().getStringExtra("payOrderName");
        payOrderDetail = getIntent().getStringExtra("payOrderDetail");
        total = getIntent().getStringExtra("total");
        pay_order_tvOrderTotal.setText(total);
        payOrderTotal = total;
        paySuccessBroadcastReciver=new PaySuccessBroadcastReciver();

    }
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("WXPaySuccess");
       registerReceiver(paySuccessBroadcastReciver, intentFilter);
    }
    private void getPayWay(){
        HashMap map=new HashMap();
        map.put("token", TiaoshiApplication.globalToken);
        if (getIntent().getStringExtra("orderId") != null) {
            map.put("orderId",getIntent().getStringExtra("orderId"));
        }
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign));
        LogUtil.e(sign);
        OkHttpManager.postAsync(G.Host.ORDER_PAY_WEY,map,new MyCallBack(4,this,new OrderPayWayModel(),handler));
    }

    @Override
    public void onBackPressed() {
        builder.create().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_apily:
                iv_apliy.setImageResource(R.mipmap.icon_pay_checked);
                iv_weixing.setImageResource(R.mipmap.icon_pay_normal);
                iv_yuepay.setImageResource(R.mipmap.icon_pay_normal);
                pay_Type = 1;
                break;
            case R.id.rl_weixing:
                iv_apliy.setImageResource(R.mipmap.icon_pay_normal);
                iv_weixing.setImageResource(R.mipmap.icon_pay_checked);
                iv_yuepay.setImageResource(R.mipmap.icon_pay_normal);
                pay_Type = 2;
                break;
            case R.id.iv_yuepay:
                iv_apliy.setImageResource(R.mipmap.icon_pay_normal);
                iv_weixing.setImageResource(R.mipmap.icon_pay_normal);
                iv_yuepay.setImageResource(R.mipmap.icon_pay_checked);
                pay_Type = 3;
                break;
            case R.id.pay_order_ivBack:
                builder.create().show();
                break;
            case R.id.pay_order_btnPay:
                if (pay_Type == 1) {
                    alipay.pay(payOrderName, payOrderDetail, payOrderTotal);
                } else if (pay_Type == 2) {
                    showProgressDialog("去微信付款~~");
                    HashMap map = new HashMap();
                    map.put("orderNo", getIntent().getStringExtra("order_code"));
                    map.put("actReq", SignUtil.getRandom());
                    map.put("actTime", System.currentTimeMillis() / 1000 + "");
                    String sign = SignUtil.getSign(map);
                    map.put("sign", Md5.toMd5(sign));
                    OkHttpManager.postAsync(G.Host.WEIXIN_ORDER, map, new MyCallBack(3, PayOrderActivity.this, new WeiXinPayDate(), handler));
                }else if (pay_Type == 3){
                        HashMap map=new HashMap();
                    map.put("token",TiaoshiApplication.globalToken);
                    if (null!=getIntent().getStringExtra("orderId")){
                        map.put("orderId",getIntent().getStringExtra("orderId"));
                    }else {
                        shortToast("订单错误！");
                        return;
                    }
                    map.put("actReq", SignUtil.getRandom());
                    map.put("actTime", System.currentTimeMillis() / 1000 + "");
                    String sign = SignUtil.getSign(map);
                    map.put("sign", Md5.toMd5(sign));
                    OkHttpManager.postAsync(G.Host.ORDER_PAY_PLAT,map,new MyCallBack(5,this,new OrderPayWayModel(),handler));

                }
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Alipay.SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(PayOrderActivity.this, "支付成功,请到“我的”页面查看详情", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PayOrderActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(PayOrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                case 3:
                    WeiXinPayDate weiXinPayDate = (WeiXinPayDate) msg.obj;
                    if (weiXinPayDate != null) {
                        if ("SUCCESS".equals(weiXinPayDate.getRespCode())) {
                            IWXAPI msgApi = WXAPIFactory.createWXAPI(PayOrderActivity.this, null);
                            msgApi.registerApp(Constants.APP_ID);
                            PayReq req = new PayReq();
                            req.appId = Constants.APP_ID;
                            req.partnerId = Constants.MCH_ID;
                            req.prepayId = weiXinPayDate.getData().getPrepayId();
                            req.packageValue = "Sign=WXPay";
                            String nonceStr = genNonceStr();
                            req.nonceStr = nonceStr;
                            String timeStamp = String.valueOf(genTimeStamp());
                            req.timeStamp = timeStamp;
                            HashMap map = new HashMap();
                            map.put("appid", Constants.APP_ID);
                            map.put("partnerid", Constants.MCH_ID);
                            map.put("prepayid", weiXinPayDate.getData().getPrepayId());
                            map.put("package", "Sign=WXPay");
                            map.put("noncestr", nonceStr);
                            map.put("timestamp", timeStamp);
                            req.sign = Md5.toMd5(SignUtil.getWeixinSign(map)).toUpperCase();
                            Log.e("---", req.sign.toString());
                            msgApi.sendReq(req);
                            dissmissProgressDialog();
                        } else {
                            ToastUtil.showShort(PayOrderActivity.this, weiXinPayDate.getRespMsg());
                            dissmissProgressDialog();
                        }
                    }
                    break;
                case 4:
                    OrderPayWayModel orderPayWayModel= (OrderPayWayModel) msg.obj;
                    if ("SUCCESS".equals(orderPayWayModel.getRespCode())){
                        String[] str=orderPayWayModel.getData().getPayWay().split("\\|");
                        for (String s : str) {
                            switch (s){
                                case "PLAT":
                                    rl_yuepay.setVisibility(View.VISIBLE);
                                    break;
                                case "ALIPAY":
                                    rl_apily.setVisibility(View.VISIBLE);
                                    break;
                                case "WX":
                                    rl_weixing.setVisibility(View.VISIBLE);
                                    break;
                            }
                        }
                    }
                    break;
                case 5:
                    OrderPayWayModel orderPayWayModel1= (OrderPayWayModel) msg.obj;
                    if ("SUCCESS".equals(orderPayWayModel1.getRespCode())){
                        shortToast("支付成功,请到“我的”页面查看详情");
                        Intent intent = new Intent("com.brandsh.tiaoshi.MyFragment");
                        sendBroadcast(intent);
                        setResult(RESULT_OK);
                        finish();
                    }else {
                        shortToast(orderPayWayModel1.getRespMsg());
                    }
                    break;
                default:
                    break;
            }

        }
    };

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    public void onDestroy() {
        unregisterReceiver(paySuccessBroadcastReciver);
        super.onDestroy();
    }
    private class PaySuccessBroadcastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("WXPaySuccess".equals(intent.getAction())) {
                Toast.makeText(PayOrderActivity.this, "支付成功,请到“我的”页面查看详情", Toast.LENGTH_SHORT).show();
                PayOrderActivity.this.setResult(RESULT_OK);
                finish();
            }
        }
    }
}
