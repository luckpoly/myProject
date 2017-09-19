package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.alipay.Alipay;
import com.goodfood86.tiaoshi.order121Project.alipay.PayResult;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.MoneyTableModel;
import com.goodfood86.tiaoshi.order121Project.model.WeixinModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.widget.CommonDialog;
import com.goodfood86.tiaoshi.order121Project.widget.ProgressHUD;
import com.goodfood86.tiaoshi.order121Project.widget.TitleBarView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/5.
 */
public class OrderPayActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.title_bar)
    private LinearLayout title_bar;
    private TitleBarView titleBarView;
    @ViewInject(R.id.send_address)
    private TextView send_address;
    @ViewInject(R.id.send_info)
    private TextView send_info;
    @ViewInject(R.id.re_address)
    private TextView re_address;
    @ViewInject(R.id.re_info)
    private TextView re_info;
    @ViewInject(R.id.tv_thing)
    private TextView tv_thing;
    @ViewInject(R.id.tv_beizhu)
    private TextView tv_beizhu;
    @ViewInject(R.id.tv_total_price)
    private TextView tv_total_price;
    @ViewInject(R.id.tv_needtopay)
    private TextView tv_needtopay;
    @ViewInject(R.id.comfiretopay)
    private Button comfiretopay;
    @ViewInject(R.id.rl_totalPrice)
    private RelativeLayout rl_totalPrice;
    @ViewInject(R.id.rl_apily)
    private RelativeLayout rl_apily;
    @ViewInject(R.id.rl_weixing)
    private RelativeLayout rl_weixing;
    @ViewInject(R.id.iv_apliy)
    private ImageView iv_apliy;
    @ViewInject(R.id.iv_weixing)
    private ImageView iv_weixing;

    private Alipay alipay;
    private String payOrderName;
    private String payOrderDetail;
    private String payOrderTotal;
    private int weight, distance, startingPrice, addWeightPrice, addDistancePrice;


    //    private String total;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    WeixinModel weixinModel = (WeixinModel) msg.obj;
                    if (weixinModel.getRespCode() == 0) {
                        IWXAPI msgApi = WXAPIFactory.createWXAPI(OrderPayActivity.this, weixinModel.getData().getAppId(), true);
                        msgApi.registerApp(weixinModel.getData().getAppId());
                        if (!msgApi.isWXAppInstalled()) {
                            ToastUtil.showShort(OrderPayActivity.this, "没有安装微信");
                            return;
                        }
                        if (!msgApi.isWXAppSupportAPI()) {
                            ToastUtil.showShort(OrderPayActivity.this, "没有安装微信");
                            return;
                        }
                        PayReq req = new PayReq();
                        req.appId = weixinModel.getData().getAppId();
                        req.partnerId = weixinModel.getData().getMchId();
                        req.prepayId = weixinModel.getData().getPrepayId();
                        req.packageValue = "Sign=WXPay";
                        req.nonceStr = weixinModel.getData().getNonceStr();
                        String currentTime = String.valueOf(genTimeStamp());
                        req.timeStamp = currentTime;
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("appid", req.appId);
                        hashMap.put("partnerid", req.partnerId);
                        hashMap.put("prepayid", req.prepayId);
                        hashMap.put("package", req.packageValue);
                        hashMap.put("noncestr", req.nonceStr);
                        hashMap.put("timestamp", req.timeStamp);
                        req.sign = SignUtil.getWeixinSign(hashMap).toUpperCase();
                        Log.e("222", "appId" + weixinModel.getData().getAppId() + "partnerId" + weixinModel.getData().getMchId() + "prepayId" + weixinModel.getData().getPrepayId() +
                                "packageValue" + "Sign=WXPay" + "nonceStr" + weixinModel.getData().getNonceStr().toLowerCase() + "timeStamp" + currentTime + "sign" + SignUtil.getWeixinSign(hashMap).toUpperCase());
                        msgApi.sendReq(req);
                        Order121Application.getInstance().addOrderActivity(OrderPayActivity.this);
                    }
                    break;
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
                        Toast.makeText(OrderPayActivity.this, "支付成功,请到我的订单中查看订单详情", Toast.LENGTH_SHORT).show();
                        sendBroadcast(new Intent("updateOrder"));
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OrderPayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Log.e("resultStatus", resultStatus);
                            Toast.makeText(OrderPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
            }
        }
    };
    private CommonDialog dialog;
    private int pay_Type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderpay);
        ViewUtils.inject(this);
        initData();
        initTitleBar();
        initListener();
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

    private void initData() {
        tv_thing.setText("物品名称：" + getIntent().getStringExtra("thingname"));
        if (getIntent().getStringExtra("beizhu") != null) {
            tv_beizhu.setText(getIntent().getStringExtra("beizhu"));
        }
        tv_total_price.setText(getIntent().getStringExtra("total") + "元");
        tv_needtopay.setText(getIntent().getStringExtra("total") + "元");
        send_address.setText(getIntent().getStringExtra("sendaddress"));
        send_info.setText(getIntent().getStringExtra("sendinfo"));
        re_address.setText(getIntent().getStringExtra("receiveaddress"));
        re_info.setText(getIntent().getStringExtra("receiveinfo"));
        alipay = new Alipay(this, handler, getIntent().getStringExtra("orderNo"));
        payOrderName = getIntent().getStringExtra("payOrderName");
        payOrderDetail = getIntent().getStringExtra("payOrderDetail");
        payOrderTotal = getIntent().getStringExtra("total");
        MoneyTableModel moneyTableModel = Order121Application.globalMoneyTableModel;
        dialog = CommonDialog.show(OrderPayActivity.this, "若暂不支付，可在30分钟内进入订单管理中支付", false, null);
        distance = (int) Math.ceil(Double.parseDouble(getIntent().getStringExtra("distance")));
        if ("-".equals((getIntent().getStringExtra("weight")).replace("公斤", ""))) {
            weight = 3;
        } else {
            weight = Integer.parseInt((getIntent().getStringExtra("weight")).replace("公斤", ""));
        }
        startingPrice = Integer.parseInt(moneyTableModel.getData().getDistance()) + Integer.parseInt(moneyTableModel.getData().getWeight());
        if (distance > Integer.parseInt(moneyTableModel.getData().getDistanceMin())) {
            addDistancePrice = ((distance - Integer.parseInt(moneyTableModel.getData().getDistanceMin()) / Integer.parseInt(moneyTableModel.getData().getDistanceSpacing())) * Integer.parseInt(moneyTableModel.getData().getDistancePrice()));
        } else {
            addDistancePrice = 0;
        }
        if (weight > Integer.parseInt(moneyTableModel.getData().getWeightMin())) {
            addWeightPrice = (weight - Integer.parseInt(moneyTableModel.getData().getWeightMin()) / Integer.parseInt(moneyTableModel.getData().getWeightSpacing())) * Integer.parseInt(moneyTableModel.getData().getWeightPrice());
        }
    }

    private void initListener() {
        rl_totalPrice.setOnClickListener(this);
        comfiretopay.setOnClickListener(this);
        rl_apily.setOnClickListener(this);
        rl_weixing.setOnClickListener(this);
    }

    private void initTitleBar() {
        titleBarView = new TitleBarView(title_bar, "确认支付");
        titleBarView.nav_back.setVisibility(View.VISIBLE);
        titleBarView.nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.rl_apily:
                    iv_apliy.setImageResource(R.mipmap.pay_check);
                    iv_weixing.setImageResource(R.mipmap.pay_uncheck);
                    pay_Type = 1;
                    break;
                case R.id.rl_weixing:
                    iv_apliy.setImageResource(R.mipmap.pay_uncheck);
                    iv_weixing.setImageResource(R.mipmap.pay_check);
                    pay_Type = 2;
                    break;
                case R.id.comfiretopay:
                    if (pay_Type == 1) {
                        alipay.pay(payOrderName, payOrderDetail, payOrderTotal);
                    } else if (pay_Type == 2) {
                        initWeixin();
                    }
                    break;
                case R.id.rl_totalPrice:
                    final Dialog dialog = new Dialog(this, R.style.Dialog_Fullscreen);
                    RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.order_totalprice, null);
                    ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.iv_del);
                    TextView tv_total = (TextView) relativeLayout.findViewById(R.id.tv_total);
                    TextView tv_startPrice = (TextView) relativeLayout.findViewById(R.id.tv_startPrice);
                    TextView tv_adddistancePrice = (TextView) relativeLayout.findViewById(R.id.tv_adddistancePrice);
                    TextView tv_addWeightPrice = (TextView) relativeLayout.findViewById(R.id.tv_addWeightPrice);
                    TextView tv_premium = (TextView) relativeLayout.findViewById(R.id.tv_premium);
                    TextView tv_totalPrice = (TextView) relativeLayout.findViewById(R.id.tv_totalPrice);
                    tv_total.setText(getIntent().getStringExtra("distance") + "公里/" + getIntent().getStringExtra("weight"));
                    tv_adddistancePrice.setText(addDistancePrice + "元");
                    tv_addWeightPrice.setText(addWeightPrice + "元");
                    tv_startPrice.setText(startingPrice + "元");
                    tv_premium.setText(getIntent().getStringExtra("premium") + "元");
                    tv_totalPrice.setText(getIntent().getStringExtra("total") + "元");
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.setContentView(relativeLayout);
                    dialog.show();
                    Window win = dialog.getWindow();
                    win.getDecorView().setPadding(0, 0, 0, 0);
                    WindowManager.LayoutParams lp = win.getAttributes();
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                    win.setAttributes(lp);
                    break;
            }
        }
    }

    private void initWeixin() {
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("orderNo", getIntent().getStringExtra("orderNo"));
        Order121Application.getGlobalHttpUtils().send(HttpRequest.HttpMethod.POST, G.Host.WEIXINPAY, requestParams, new MyRequestCallBack(OrderPayActivity.this, handler, 1, new WeixinModel()));
    }


    @Override
    public void onBackPressed() {
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }
}
