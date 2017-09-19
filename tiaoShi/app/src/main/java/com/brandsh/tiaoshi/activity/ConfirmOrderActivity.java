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
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.ShopCartList1Adapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.AddressListJsonData;
import com.brandsh.tiaoshi.model.ConfirmOrderJsonData1;
import com.brandsh.tiaoshi.model.CouponModel;
import com.brandsh.tiaoshi.model.CouponPriceModel;
import com.brandsh.tiaoshi.model.DiyShoppingCartJsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.brandsh.tiaoshi.widget.ProductDetailImgListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConfirmOrderActivity extends FragmentActivity implements View.OnClickListener {
    //回退
    @ViewInject(R.id.confirm_order_ivBack)
    ImageView confirm_order_ivBack;
    //有收货地址
    @ViewInject(R.id.confirm_order_rl1)
    RelativeLayout confirm_order_rl1;
    //优惠券
    @ViewInject(R.id.rl_go_coupon)
    RelativeLayout rl_go_coupon;
    //联系人
    @ViewInject(R.id.confirm_order_tvContact)
    TextView confirm_order_tvContact;
    //电话
    @ViewInject(R.id.confirm_order_tvPhone)
    TextView confirm_order_tvPhone;
    //地址
    @ViewInject(R.id.confirm_order_tvAddress)
    TextView confirm_order_tvAddress;
    //没有收货地址
    @ViewInject(R.id.confirm_order_rl2)
    RelativeLayout confirm_order_rl2;
    //店铺地址
    @ViewInject(R.id.confirm_order_tvShopName)
    TextView confirm_order_tvShopName;
    //购物车商品列表
    @ViewInject(R.id.confirm_order_lv)
    ProductDetailImgListView confirm_order_lv;
    //购物车数量价格统计
    @ViewInject(R.id.confirm_order_tvTotal)
    TextView confirm_order_tvTotal;
    //订单备注
    @ViewInject(R.id.confirm_order_et)
    EditText confirm_order_et;
    //订单总价
    @ViewInject(R.id.confirm_order_tvTotalCash)
    TextView confirm_order_tvTotalCash;
    //提交订单
    @ViewInject(R.id.confirm_order_tvConfirmOrder)
    TextView confirm_order_tvConfirmOrder;
    //配送费
    @ViewInject(R.id.peisongmoney)
    TextView peisongmoney;
    @ViewInject(R.id.tv_coupon_price)
    TextView tv_coupon_price;
    @ViewInject(R.id.tv_kajuan_money)
    TextView tv_kajuan_money;
    @ViewInject(R.id.ll_address_show)
    private LinearLayout ll_address_show;
    private HashMap orderRequestMap;
    private HashMap requestMap;
    private List<AddressListJsonData.DataBean.ListBean> resList;
    private ProgressDialog progDialog;
    private List<DiyShoppingCartJsonData.GoodsListEntity> goodsList;
    private ShopCartList1Adapter shopCartList1Adapter;
    private String remarks;
    private boolean isHavenAddress;
    private ChangeAddressBR changeAddressBR;
    private TextView tv_manjian_money;
    private String discountId = "";
    private String couponUseCodes;
    private String couponUseCodeSource;
    private boolean isDefaultAddress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        //沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);
        init();
    }

    private void loadDataCoupon() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("role", "USE");
        hashMap.put("shopId", TiaoshiApplication.diyShoppingCartJsonData.getShop_id());
        hashMap.put("goods", creayGoods());
        hashMap.put("token", TiaoshiApplication.globalToken);
        hashMap.put("actReq", SignUtil.getRandom());
        hashMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(hashMap);
        hashMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.GET_DISCOUNT_LIST, hashMap, new MyCallBack(7, this, new CouponModel(), handler));
    }

    private class ChangeAddressBR extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("changeDefaultAddress".equals(intent.getAction())) {
                isHavenAddress = true;
                confirm_order_rl1.setVisibility(View.VISIBLE);
                confirm_order_rl2.setVisibility(View.GONE);
                lng = intent.getStringExtra("lng");
                lat = intent.getStringExtra("lat");
                contact = intent.getStringExtra("contact");
                tel = intent.getStringExtra("tel");
                addressDetail = intent.getStringExtra("addressDetail");
                address = intent.getStringExtra("address");
                orderRequestMap.put("lng", lng);
                orderRequestMap.put("lat", lat);
                orderRequestMap.put("contact", contact);
                orderRequestMap.put("tel", tel);
                orderRequestMap.put("addressDetail", addressDetail);
                orderRequestMap.put("address", address);
                confirm_order_tvContact.setText(contact);
                confirm_order_tvPhone.setText(tel);
                confirm_order_tvAddress.setText(address + addressDetail);
                if (isDefaultAddress) {
                    getAllPrice();
                } else {
                    loadDataCoupon();
                }

            }
        }
    }

    private void init() {
        showProgressDialog("正在加载");
        confirm_order_ivBack.setOnClickListener(this);
        confirm_order_rl1.setOnClickListener(this);
        confirm_order_rl2.setOnClickListener(this);
        confirm_order_tvConfirmOrder.setOnClickListener(this);
        rl_go_coupon.setOnClickListener(this);
        resList = new LinkedList<>();
        goodsList = TiaoshiApplication.diyShoppingCartJsonData.getGoodsList();
        shopCartList1Adapter = new ShopCartList1Adapter(goodsList, this);
        confirm_order_lv.setAdapter(shopCartList1Adapter);
        requestMap = new HashMap();
        orderRequestMap = new HashMap();
        requestMap.put("user_id", TiaoshiApplication.globalUserId);
        requestMap.put("token", TiaoshiApplication.globalToken);
        requestMap.put("actReq", SignUtil.getRandom());
        requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(requestMap);
        requestMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.ADDRESS_LIST, requestMap, new MyCallBack(1, ConfirmOrderActivity.this, new AddressListJsonData(), handler));
        orderRequestMap.put("token", TiaoshiApplication.globalToken);
        orderRequestMap.put("shopId", TiaoshiApplication.diyShoppingCartJsonData.getShop_id());
        orderRequestMap.put("total", TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_cash() + "");
        orderRequestMap.put("goods", creayGoods());
        changeAddressBR = new ChangeAddressBR();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("changeDefaultAddress");
        registerReceiver(changeAddressBR, intentFilter);
        confirm_order_tvShopName.setText(getIntent().getStringExtra("shop_name"));
        confirm_order_tvTotal.setText("共 " + TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_count() + " 件,合计 ￥" + TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_cash() + " 元");
        confirm_order_tvTotalCash.setText("总计 ￥" + TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_cash() + " 元");
        tv_manjian_money = (TextView) findViewById(R.id.tv_manjian_money);
        if ("YES".equals(getIntent().getStringExtra("isShare"))) {
            ll_address_show.setVisibility(View.GONE);
        }
    }
    private String creayGoods() {
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("goods_id", TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().get(i).getGoods_id());
            map.put("count", TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().get(i).getGoods_count() + "");
            if (TextUtils.isEmpty(TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().get(i).getTypename())) {
                map.put("pack", "");
            } else {
                map.put("pack", TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().get(i).getTypename());
            }
            map.put("unit", TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().get(i).getSales_unit());

            list.add(map);
        }
        String Goods = SignUtil.getArrayToString1(list);
        return Goods;
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

    boolean a = false;
    boolean b = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_order_ivBack:
                finish();
                break;
            case R.id.confirm_order_rl1:
                Intent intent1 = new Intent(this, ChooseDeliveryAddressActivity.class);
                startActivity(intent1);
                break;
            case R.id.confirm_order_rl2:
                Intent intent2 = new Intent(this, ChooseDeliveryAddressActivity.class);
                startActivity(intent2);
                break;
            case R.id.confirm_order_tvConfirmOrder:
                if (!isHavenAddress && !"YES".equals(getIntent().getStringExtra("isShare"))) {
                    Toast.makeText(ConfirmOrderActivity.this, "请先选择收货地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("确认订单").setMessage("确认提交订单?").setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remarks = confirm_order_et.getText().toString() + "";
                        HashMap map = orderRequestMap;
                        if (!TextUtils.isEmpty(discountId)) {
                            map.put("discountId", discountId);
                        }
                        map.put("remarks", remarks);
                        if ("YES".equals(getIntent().getStringExtra("isShare"))) {
                            map.put("isShare", "YES");
                        }
                        if (!TextUtils.isEmpty(getIntent().getStringExtra("ORDER_TYPE"))) {
                            map.put("orderType", getIntent().getStringExtra("ORDER_TYPE"));
                        }
                        //优惠券
                        if (!TextUtils.isEmpty(couponUseCodeSource)) {
                            map.put("couponUseCodeSource", couponUseCodeSource);
                        }
                        if (!TextUtils.isEmpty(couponUseCodes)) {
                            map.put("couponUseCodes", couponUseCodes);
                        }
                        map.put("actReq", SignUtil.getRandom());
                        map.put("actTime", System.currentTimeMillis() / 1000 + "");
                        if (map.containsKey("sign")) {
                            map.remove("sign");
                        }
                        String sign = SignUtil.getSign(map);
                        map.put("sign", Md5.toMd5(sign));
                        Log.e("-----", sign);
                        OkHttpManager.postAsync(G.Host.CONFIRM_ORDER, map, new MyCallBack(2, ConfirmOrderActivity.this, new ConfirmOrderJsonData1(), handler));
                        showProgressDialog("订单提交中");
                    }
                }).setPositiveButton("取消", null);
                builder.create().show();
                break;
            case R.id.rl_go_coupon:
                if (!isHavenAddress && !"YES".equals(getIntent().getStringExtra("isShare"))) {
                    Toast.makeText(ConfirmOrderActivity.this, "请先选择收货地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(ConfirmOrderActivity.this, UseCouponActivaty.class);
                intent.putExtra("goods", creayGoods());
                intent.putExtra("shopId", TiaoshiApplication.diyShoppingCartJsonData.getShop_id());
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
                couponUseCodes = data.getStringExtra("couponUseCodes");
                if (!TextUtils.isEmpty(data.getStringExtra("isWeiXin"))) {
                    if (data.getStringExtra("isWeiXin").equals("YES")) {
                        couponUseCodeSource = "WX";
                    } else if (data.getStringExtra("isWeiXin").equals("NO")) {
                        couponUseCodeSource = "TS";
                    }
                }
                getAllPrice();
            }
        }
    }

    private void getAllPrice() {
        HashMap map = new HashMap();
        map.put("goods", creayGoods());
        map.put("token", TiaoshiApplication.globalToken);
        map.put("shopId", TiaoshiApplication.diyShoppingCartJsonData.getShop_id());
        map.put("lng", lng);
        map.put("lat", lat);
        if (!TextUtils.isEmpty(couponUseCodes)) {
            map.put("couponUseCodes", couponUseCodes);
        }
        if (!TextUtils.isEmpty(couponUseCodeSource)) {
            map.put("couponUseCodeSource", couponUseCodeSource);
        }
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign));
        Log.e("---", sign);
        OkHttpManager.postAsync(G.Host.GET_COUPON_PRICE, map, new MyCallBack(6, this, new CouponPriceModel(), handler));
    }

    private String lng;
    private String lat;
    private String contact;
    private String tel;
    private String addressDetail;
    private String address;
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
                            if (resList.size() != 0) {
                                confirm_order_rl1.setVisibility(View.VISIBLE);
                                confirm_order_rl2.setVisibility(View.GONE);
                                isHavenAddress = true;
                                confirm_order_tvContact.setText(resList.get(0).getContact());
                                confirm_order_tvPhone.setText(resList.get(0).getTel());
                                confirm_order_tvAddress.setText(resList.get(0).getAddress() + resList.get(0).getAddressDetail());
                                lng = resList.get(0).getLng();
                                lat = resList.get(0).getLat();
                                contact = resList.get(0).getContact();
                                tel = resList.get(0).getTel();
                                addressDetail = resList.get(0).getAddressDetail();
                                address = resList.get(0).getAddress();
                                ;
                                orderRequestMap.put("lng", lng);
                                orderRequestMap.put("lat", lat);
                                orderRequestMap.put("contact", contact);
                                orderRequestMap.put("tel", tel);
                                orderRequestMap.put("addressDetail", addressDetail);
                                orderRequestMap.put("address", address);
                                isDefaultAddress = true;
                                loadDataCoupon();
                            } else {
                                if (!TextUtils.isEmpty(getIntent().getStringExtra("isShare")) && getIntent().getStringExtra("isShare").equals("YES")) {
                                    lng = TiaoshiApplication.Lng;
                                    lat = TiaoshiApplication.Lat;
                                    loadDataCoupon();
                                }
                                confirm_order_rl1.setVisibility(View.GONE);
                                confirm_order_rl2.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(ConfirmOrderActivity.this, addressListJsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    dissmissProgressDialog();
                    break;
                case 2:
                    ConfirmOrderJsonData1 confirmOrderJsonData = (ConfirmOrderJsonData1) msg.obj;
                    if (confirmOrderJsonData != null) {
                        if ("SUCCESS".equals(confirmOrderJsonData.getRespCode())) {
                            Toast.makeText(ConfirmOrderActivity.this, "订单提交成功", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(ConfirmOrderActivity.this, PayOrderActivity.class);
                            intent1.putExtra("order_code", confirmOrderJsonData.getData().getOrderCode() + "");
                            intent1.putExtra("orderId", confirmOrderJsonData.getData().getOrderId() + "");
                            intent1.putExtra("total", confirmOrderJsonData.getData().getTotal() + "");
                            intent1.putExtra("payOrderName", "从店铺 " + getIntent().getStringExtra("shop_name")
                                    + " 中购买了 " + TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_count()
                                    + " 件商品, 合计 " + TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_cash() + " 元。");
                            intent1.putExtra("payOrderDetail", "付款来源: 安卓支付宝客户端,"
                                    + "订单编号：" + confirmOrderJsonData.getData().getOrderCode() + ",购买数量："
                                    + TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_count()
                                    + ",合计：" + TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_cash() + " 元。"
                                    + "收货人姓名：" + contact);
                            startActivity(intent1);
                            Intent intent = new Intent("removeAll");
                            sendBroadcast(intent);
                            finish();
                        } else {
                            Toast.makeText(ConfirmOrderActivity.this, confirmOrderJsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    dissmissProgressDialog();
                    break;
                case 6:
                    CouponPriceModel couponPriceModel = (CouponPriceModel) msg.obj;
                    if ("SUCCESS".equals(couponPriceModel.getRespCode())) {
                        if (!TextUtils.isEmpty(getIntent().getStringExtra("ORDER_TYPE"))&&getIntent().getStringExtra("ORDER_TYPE").equals("MONTH")){
                            //来自果汁月套餐的订单，去除优惠券的金额
                            BigDecimal bigDecimal = new BigDecimal(couponPriceModel.getData().getTotal()).add(new BigDecimal(couponPriceModel.getData().getSendFree())).subtract(new BigDecimal(couponPriceModel.getData().getShopCouponPrice()));
                            confirm_order_tvTotalCash.setText("总计 ￥" + bigDecimal + " 元");
                            confirm_order_tvTotal.setText("共 " + TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_count() + " 件,合计 ￥" + bigDecimal + " 元");
                            tv_manjian_money.setText("-￥" + couponPriceModel.getData().getShopCouponPrice());
                            peisongmoney.setText("￥" + couponPriceModel.getData().getSendFree());
                            tv_kajuan_money.setText("-￥0");
                            tv_coupon_price.setText("此商品不支持卡卷");
                            rl_go_coupon.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ToastUtil.showShort(ConfirmOrderActivity.this,"此商品暂不支持卡卷！");
                                }
                            });
                        }else {
                            BigDecimal bigDecimal = new BigDecimal(couponPriceModel.getData().getTotal()).add(new BigDecimal(couponPriceModel.getData().getSendFree())).subtract(new BigDecimal(couponPriceModel.getData().getCouponPrice())).subtract(new BigDecimal(couponPriceModel.getData().getShopCouponPrice()));
                            confirm_order_tvTotalCash.setText("总计 ￥" + bigDecimal + " 元");
                            confirm_order_tvTotal.setText("共 " + TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_count() + " 件,合计 ￥" + bigDecimal + " 元");
                            tv_manjian_money.setText("-￥" + couponPriceModel.getData().getShopCouponPrice());
                            peisongmoney.setText("￥" + couponPriceModel.getData().getSendFree());
                            tv_coupon_price.setText("减 " + couponPriceModel.getData().getCouponPrice() + " 元");
                            tv_kajuan_money.setText("-￥" + couponPriceModel.getData().getCouponPrice());
                        }
                    } else {
                        ToastUtil.showShort(ConfirmOrderActivity.this, couponPriceModel.getRespMsg());
                    }
                    break;
                case 7:
                    CouponModel couponModel = (CouponModel) msg.obj;
                    if ("SUCCESS".equals(couponModel.getRespCode())) {
                        if (null != couponModel.getData() && couponModel.getData().size() > 0) {
                            couponUseCodes = couponModel.getData().get(0).getCouponUseCode();
                        }
                        getAllPrice();
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

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
