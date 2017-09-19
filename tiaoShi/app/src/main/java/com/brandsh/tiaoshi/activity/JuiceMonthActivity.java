package com.brandsh.tiaoshi.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.fragment.PhoneLoginFragment;
import com.brandsh.tiaoshi.fragment.ProductDetailFragment;
import com.brandsh.tiaoshi.model.ConfirmOrderJsonData1;
import com.brandsh.tiaoshi.model.GoodsDetailJsonData;
import com.brandsh.tiaoshi.model.StoreDetailJsonData1;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mingle.widget.ShapeLoadingDialog;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/10.
 */
public class JuiceMonthActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.banner_ivBack)
    ImageView banner_ivBack;
    @ViewInject(R.id.banner_wv)
    WebView banner_wv;
    @ViewInject(R.id.srl_webview)
    SwipeRefreshLayout srl_webview;
    @ViewInject(R.id.iv_shuoming)
    ImageView iv_shuoming;
    String goodsId;
    String shopId;
    //加载动画
    private ShapeLoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juicemonth);
        //沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);
        init();
    }

    @Override
    public void onBackPressed() {
        if (banner_wv.canGoBack()) {
            banner_wv.goBack();
        } else {
            banner_wv.clearCache(true);
            banner_wv.clearHistory();
            super.onBackPressed();
        }
    }

    private void init() {
        banner_ivBack.setOnClickListener(this);
        iv_shuoming.setOnClickListener(this);
        WebSettings settings = banner_wv.getSettings();
        loadingDialog = ProgressHUD.show(this, "努力加载中...");
        loadingDialog.show();
        settings.setJavaScriptEnabled(true);
        if (getIntent().getStringExtra("URL").equals(G.Host.PICK_LIST)) {
            iv_shuoming.setVisibility(View.VISIBLE);
        }
        banner_wv.loadUrl(getIntent().getStringExtra("URL"));
        banner_wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingDialog.dismiss();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                view.reload();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    String url1 = java.net.URLDecoder.decode(url, "utf-8");
                    //tiaoshi://GoShop?ShopId=261&ShopName=果汁店铺&ShopFreeSend=0
                    Log.e("==========", url1);
                    String[] str = url1.split("\\?");
                    if (null != str && str.length >= 2) {
                        switch (str[0]) {
                            case "tiaoshi://GoShop":
                                break;
                            case "tiaoshi://GoGoodsDetail":
                                String[] goods = str[1].split("&");
                                if (null != goods && goods.length >= 2) {
                                    goodsId = goods[0].replace("GoodsId=", "");
                                    shopId = goods[1].replace("ShopId=", "");
                                    if (!TextUtils.isEmpty(getIntent().getStringExtra("From")) && getIntent().getStringExtra("From").equals("DiscountActivaty")) {
                                        HashMap requestMap = new HashMap();
                                        requestMap.put("shopId", shopId);
                                        if (TextUtils.isEmpty(TiaoshiApplication.Lat)) {
                                            requestMap.put("lat", "31.312564");
                                            requestMap.put("lng", "121.487778");
                                        } else {
                                            if (TiaoshiApplication.isLogin) {
                                                requestMap.put("userId", TiaoshiApplication.globalUserId);
                                            }
                                            requestMap.put("lat", TiaoshiApplication.Lat);
                                            requestMap.put("lng", TiaoshiApplication.Lng);
                                        }
                                        requestMap.put("actReq", SignUtil.getRandom());
                                        requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
                                        String sign = SignUtil.getSign(requestMap);
                                        requestMap.put("sign", Md5.toMd5(sign));
                                        OkHttpManager.postAsync(G.Host.STORE_DETAIL, requestMap, new MyCallBack(4, JuiceMonthActivity.this, new StoreDetailJsonData1(), handler));
                                    } else {
                                        Intent intent = new Intent(JuiceMonthActivity.this, JuiceMonthDetailActivity.class);
                                        intent.putExtra("goodsId", goodsId);
                                        intent.putExtra("shopId", shopId);
                                        startActivity(intent);
                                    }
                                }
                                break;
                            case "tiaoshi://GoPay":
                                //获取商品详情下订单
                                if(!TiaoshiApplication.isLogin){
                                   ToastUtil.showShort(JuiceMonthActivity.this,"您还未登陆，请先登录！");
                                    startActivity(FCActivity.getFCActivityIntent(JuiceMonthActivity.this,PhoneLoginFragment.class));
                                   break;
                                }
                                String[] proxy = str[1].split("&");
                                if (null != proxy && proxy.length >= 2) {
                                    String goodsId = proxy[0].replace("GoodsId=", "");
                                    String ordreType = proxy[1].replace("OrderType=", "");
                                    HashMap requestMap = new HashMap();
                                    requestMap.put("goodsId", goodsId);
                                    requestMap.put("actReq", SignUtil.getRandom());
                                    requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
                                    String sign = SignUtil.getSign(requestMap);
                                    requestMap.put("sign", Md5.toMd5(sign));
                                    OkHttpManager.postAsync(G.Host.PRODUCT_DETAIL, requestMap, new MyCallBack(1, JuiceMonthActivity.this, new GoodsDetailJsonData(), handler));
                                }

                                break;
                            case "tiaoshi://CouponReceive":
                                String CouponCode = str[1].replace("CouponCode=", "");
                                HashMap requestMap = new HashMap();
                                requestMap.put("token", TiaoshiApplication.globalToken);
                                requestMap.put("couponCode", CouponCode);
                                requestMap.put("actReq", SignUtil.getRandom());
                                requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
                                String sign = SignUtil.getSign(requestMap);
                                requestMap.put("sign", Md5.toMd5(sign));
                                Log.e("----", sign);
                                OkHttpManager.postAsync(G.Host.GET_COUPON, requestMap, new MyCallBack(3, JuiceMonthActivity.this, new GoodsDetailJsonData(), handler));
                                break;
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

        srl_webview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                banner_wv.loadUrl(banner_wv.getUrl());
            }
        });
        srl_webview.setColorSchemeColors(getResources().getColor(R.color.theme_color));
        //设置进度条
        banner_wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    //隐藏进度条
                    srl_webview.setRefreshing(false);
                } else {
                    if (!srl_webview.isRefreshing())
                        srl_webview.setRefreshing(true);
                }

                super.onProgressChanged(view, newProgress);
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    GoodsDetailJsonData goodsDetailJsonData = (GoodsDetailJsonData) msg.obj;
                    GoodsDetailJsonData.DataBean bean = goodsDetailJsonData.getData().get(0);
                    if (goodsDetailJsonData != null) {
                        if ("SUCCESS".equals(goodsDetailJsonData.getRespCode())) {
                            HashMap datamap = new HashMap();
                            datamap.put("lng", TiaoshiApplication.Lng);
                            datamap.put("lat", TiaoshiApplication.Lat);
                            if (!TextUtils.isEmpty(TiaoshiApplication.phone)){
                                datamap.put("contact", TiaoshiApplication.phone);
                                datamap.put("tel", TiaoshiApplication.phone);
                            }else {
                                datamap.put("contact", "路人甲");
                                datamap.put("tel", "未知");
                            }
                            datamap.put("addressDetail", "-");
                            datamap.put("address", TiaoshiApplication.Address);
                            datamap.put("token", TiaoshiApplication.globalToken);
                            datamap.put("shopId", bean.getShopId());
                            datamap.put("total", bean.getPrice() + "");
                            List<Map<String, String>> list = new ArrayList<>();
                            Map<String, String> map = new HashMap<>();
                            map.put("goods_id", bean.getGoodsId());
                            map.put("count", "1");
                            map.put("pack", "");
                            map.put("unit", bean.getSalesUnit());
                            list.add(map);
                            String Goods = SignUtil.getArrayToString1(list);
                            datamap.put("goods", Goods);
                            datamap.put("actReq", SignUtil.getRandom());
                            datamap.put("actTime", System.currentTimeMillis() / 1000 + "");
                            if (datamap.containsKey("sign")) {
                                datamap.remove("sign");
                            }
                            String sign = SignUtil.getSign(datamap);
                            datamap.put("sign", Md5.toMd5(sign));
                            Log.e("-------", sign);
                            OkHttpManager.postAsync(G.Host.CONFIRM_ORDER, datamap, new MyCallBack(2, JuiceMonthActivity.this, new ConfirmOrderJsonData1(), handler));
                        }
                    }
                    break;
                case 2:
                    ConfirmOrderJsonData1 confirmOrderJsonData = (ConfirmOrderJsonData1) msg.obj;
                    if (confirmOrderJsonData != null) {
                        if ("SUCCESS".equals(confirmOrderJsonData.getRespCode())) {
                            Toast.makeText(JuiceMonthActivity.this, "订单提交成功", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(JuiceMonthActivity.this, PayOrderActivity.class);
                            intent1.putExtra("order_code", confirmOrderJsonData.getData().getOrderCode() + "");
                            intent1.putExtra("orderId", confirmOrderJsonData.getData().getOrderId() + "");
                            intent1.putExtra("total", confirmOrderJsonData.getData().getTotal() + "");
                            intent1.putExtra("payOrderName", "加盟会员费");
                            intent1.putExtra("payOrderDetail", "付款来源: 安卓支付宝客户端,"
                                    + "订单编号：" + confirmOrderJsonData.getData().getOrderCode() + ",购买数量："
                                    + TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_count()
                                    + ",合计：" + TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_cash() + " 元。"
                                    + "收货人姓名：" + "---");
                            startActivity(intent1);
                        } else {
                            Toast.makeText(JuiceMonthActivity.this, confirmOrderJsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 3:
                    //优惠券领取
                    GoodsDetailJsonData goodsDetailJsonData1 = (GoodsDetailJsonData) msg.obj;
                    if ("SUCCESS".equals(goodsDetailJsonData1.getRespCode())) {
                        ToastUtil.showShort(JuiceMonthActivity.this, "领取成功！");
                        startActivity(new Intent(JuiceMonthActivity.this, DiscountActivaty.class));
                        finish();
                    } else {
                        ToastUtil.showShort(JuiceMonthActivity.this, goodsDetailJsonData1.getRespMsg());
                    }
                    break;
                case 4:
                    StoreDetailJsonData1 storeDetailJsonData = (StoreDetailJsonData1) msg.obj;
                    if (storeDetailJsonData != null && storeDetailJsonData.getRespCode().equals("SUCCESS")) {
                        Intent intent = FCActivity.getFCActivityIntent(JuiceMonthActivity.this, ProductDetailFragment.class);
                        intent.putExtra("goods_id", goodsId);
                        intent.putExtra("shop_id", shopId);
                        intent.putExtra("shop_name", storeDetailJsonData.getData().getName());
                        intent.putExtra("min_cost", storeDetailJsonData.getData().getFreeSend());
                        intent.putExtra("is_new", "");
                        startActivity(intent);
                    }
                    break;


            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner_ivBack:
                banner_wv.clearCache(true);
                banner_wv.clearHistory();
                finish();
                break;
            case R.id.iv_shuoming:
                startActivity(new Intent(this, AboutUsSMActivity.class).putExtra("type", "套餐说明").putExtra("url", "MonthPackageUserOperateRules"));
                break;
        }

    }
}
