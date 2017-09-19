package com.brandsh.tiaoshi.alipay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.brandsh.tiaoshi.constant.G;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Alipay {

    // 商户PID
    public static final String PARTNER = "2088221309938252";
    // 商户收款账号
    public static final String SELLER = "tiaoshi1234@126.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANwgYl3cL+9CwLYoup+9V6yclwhD0Y6H/nd2yp6boIhZNNxZRAK1H7t+efghPk0NdC2ghj0WkXjtaWf1uFkm1l/4aNd1OZYOz7r78Autp/uaX0oZs3NjT+AiKY9kQ7VYdZxf+75ylYZimMt9IPIgXpUd08zIZ7Cq4xK2HkOE+o9PAgMBAAECgYEApWzE36qQb2sn7f0PuuoNEFAhhHmK62UyC88/GBD2hGEdw5Zl8O7y6PF0cc9xxXAd5ggjuZbTPiCUvSbBImZ4ZQPlBbJQlYaL37uiHO+MSYRItc8CYvR2IpobDmUK73g+1cTXyoUT95rCeF9KMS7ucRIJGauN/dT8zyxeFyICeOkCQQD347Du/vKgMgbqr4BP60lS4BkcqkMRMtwbF0+JMKLSY8Z1TuNHHq2pDRVDJs6qGyzOmhG+oYhNf399HSUlDwnNAkEA41QnADNfZDE2TWxj/9IodcxZFLy1IypvsJH5VKPzI1hVi/ZHfnNoQTxQUtc7fwoMjJHfUNye7vJVZfro6LAxiwJASByzpwMBn/qNqo39fMJMpVBN3dnmAsXR5Aum9pjwdNXTsOKPGWxiRpBoxA0xB6k6IqwNR3CET08szj/BQ4OZbQJALI//gTOQ3IQlH/JCfBCHa+geAkmnvHgCIvvKg4QMEn6rbl9dznTzF+6p1ENq31Fp+lhSDXjiEvcgNHiGFa8MVwJBAJdiq4lBtT0TRK5kAmUzZ8O7D3JWBiFFOzQIpOE4SXSk1IMJUz1dPlNBCJTonvEnjPQXBwO+QRnB/GmRroAI1Co=";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

    public static final int SDK_PAY_FLAG = 1001;
    public static final int SDK_CHECK_FLAG = 1002;

    private Handler mHandler;
    private Activity activity;
    private String mAliOrderNo;

    public Alipay(Activity activity, Handler handler) {
        this.mHandler = handler;
        this.activity = activity;
    }

    public Alipay(Activity context, Handler handler, String order_code){
        this.mHandler = handler;
        this.activity = context;
        this.mAliOrderNo = order_code;
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(String subject, String body, String price) {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
                || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(activity)
                    .setTitle("警告")
                    .setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                    //
                                    activity.finish();
                                }
                            }).show();
            return;
        }
        // 订单
        String orderInfo = getOrderInfo(subject, body, price);

        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(activity);
        String version = payTask.getVersion();
        Toast.makeText(activity, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * create the order info. 创建订单信息
     *
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + G.Host.SUCCESS_PAY + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public String getOutTradeNo() {
        return mAliOrderNo;
    }

    /**
     * 获取已经生产的订单编号
     *
     * @return
     */
    public String getOrderNo() {
        return mAliOrderNo;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

}
