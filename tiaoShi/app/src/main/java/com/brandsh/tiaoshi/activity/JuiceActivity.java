package com.brandsh.tiaoshi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.ShopCartListAdapter;
import com.brandsh.tiaoshi.adapter.StoreProductJuiceAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.fragment.PhoneLoginFragment;
import com.brandsh.tiaoshi.fragment.ProductJuiceDetailFragment;
import com.brandsh.tiaoshi.model.DiyShoppingCartJsonData;
import com.brandsh.tiaoshi.model.StoreProductJsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.brandsh.tiaoshi.widget.SelfPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.jauker.widget.BadgeView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mingle.widget.ShapeLoadingDialog;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tiashiwang on 2016/5/18.
 */
public class JuiceActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.product_list_ptrListView)
    private SelfPullToRefreshListView product_list_ptrListView;
    @ViewInject(R.id.product_list_rl_buy)
    private RelativeLayout product_list_rl_buy;
    @ViewInject(R.id.product_list_ivShoppingTrolley)
    private ImageView product_list_ivShoppingTrolley;
    @ViewInject(R.id.product_list_tvTotal)
    private TextView product_list_tvTotal;
    @ViewInject(R.id.product_list_btn_to_buy)
    private Button product_list_btn_to_buy;
    @ViewInject(R.id.juice_rlBack)
    private RelativeLayout juice_rlBack;

    //数据及数据源状态判断
    private List<StoreProductJsonData.DataBean.ListBean> resList = new ArrayList<>();
    private StoreProductJsonData.DataBean.ListBean listEntity;
    private List<StoreProductJsonData.DataBean.ListBean> dataList;
    private StoreProductJuiceAdapter mAdapter;
    private HashMap<String, String> requestMap;
    private String page;
    private ShapeLoadingDialog loadingDialog;
    private String min_cost;
    private int total_goods_count;
    private double total_goods_cash;
    private String shop_id;
    private BadgeView badgeView;
    private PopupWindow mPopWindow;
    private View shoppingTrolleyDialog;
    private ShopCartListAdapter shopCartListAdapter;
    private List<DiyShoppingCartJsonData.GoodsListEntity> shopCarList;
    private DiyShoppingCartJsonData.GoodsListEntity shopCarListEntity;
    private ListView shopping_trolley_lv;
    private TextView hopping_trolley_tvClear;
    private Intent intent;
    private Intent intent1;
    private MyBroadcastReceiver myBroadcastReceiver;
    private String shop_name;
    private String status = "OPEN";
    private String listolderNo = "";
    private String typename;
    private int GoodsNO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juice);
        ViewUtils.inject(this);
        AppUtil.Setbar(this);
        initView();
    }

    private void initView() {
        shoppingTrolleyDialog = LayoutInflater.from(this).inflate(R.layout.shopping_trolley_dialog, null);
        shopping_trolley_lv = (ListView) shoppingTrolleyDialog.findViewById(R.id.shopping_trolley_lv);
        hopping_trolley_tvClear = (TextView) shoppingTrolleyDialog.findViewById(R.id.hopping_trolley_tvClear);
        min_cost = 0 + "";
        shop_id = G.SP.JUICE_SHORP_ID;
        if (shop_id == null) {
            shop_id = "null";
        }
        shop_name = getIntent().getStringExtra("shop_name");
        if (TiaoshiApplication.diyShoppingCartJsonData == null) {
            TiaoshiApplication.diyShoppingCartJsonData = new DiyShoppingCartJsonData();
        }
        if (shop_id.equals(TiaoshiApplication.diyShoppingCartJsonData.getShop_id())) {
            total_goods_count = TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_count();
            total_goods_cash = TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_cash();
        } else {
            total_goods_cash = 0;
            total_goods_count = 0;
        }
        if (shop_id.equals(TiaoshiApplication.diyShoppingCartJsonData.getShop_id())) {
            if (TiaoshiApplication.diyShoppingCartJsonData.getGoodsList() != null) {
                if (TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().size() != 0) {
                    shopCarList = TiaoshiApplication.diyShoppingCartJsonData.getGoodsList();
                }
            }
        } else {
            shopCarList = new LinkedList<>();
        }
        page = "1";
        requestMap = new HashMap();
        requestMap.put("actReq", SignUtil.getRandom());
        requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        requestMap.put("pageSize", "500");
        String sign = SignUtil.getSign(requestMap);
        requestMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.JUICE_GOODS_LIST, requestMap, new MyCallBack(1, this, new StoreProductJsonData(), handler));
        init();
        setListenerToPTRListView();
        mAdapter = new StoreProductJuiceAdapter(this, resList, handler, shop_id, badgeView);
        product_list_ptrListView.setAdapter(mAdapter);
    }

    private void init() {
        listolderNo = getIntent().getStringExtra("listolderNO");
        intent = new Intent("changeCount");
        intent1 = new Intent("changedetail");
        hopping_trolley_tvClear.setOnClickListener(this);
        product_list_rl_buy.setOnClickListener(this);
        product_list_btn_to_buy.setOnClickListener(this);
        loadingDialog = ProgressHUD.show(this, "努力加载中...");
        loadingDialog.show();
        badgeView = new BadgeView(this);
        badgeView.setTargetView(product_list_ivShoppingTrolley);
        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("changeList");
        intentFilter.addAction("clearList");
        intentFilter.addAction("removeAll");
        registerReceiver(myBroadcastReceiver, intentFilter);

        if (total_goods_count != 0) {
            badgeView.setVisibility(View.VISIBLE);
            badgeView.setText(total_goods_count + "");
        } else {
            badgeView.setVisibility(View.GONE);
        }
        if (total_goods_cash == 0) {
            product_list_tvTotal.setText("￥：" + total_goods_cash + "元");
        } else {
            if (total_goods_cash <= Double.parseDouble(min_cost)) {
                product_list_tvTotal.setText("￥：" + total_goods_cash + "元");
            } else {
                product_list_tvTotal.setText("￥：" + total_goods_cash + "元");
            }
        }
        mPopWindow = new PopupWindow(shoppingTrolleyDialog, ViewGroup.LayoutParams.MATCH_PARENT, 600, true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        juice_rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setListenerToPTRListView() {
        product_list_ptrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = FCActivity.getFCActivityIntent(JuiceActivity.this, ProductJuiceDetailFragment.class);
                intent.putExtra("what", "1");
                intent.putExtra("goods_id", resList.get(position - 1).getGoodsId());
                intent.putExtra("goods_name", resList.get(position - 1).getGoodsName());
                intent.putExtra("shop_id", resList.get(position - 1).getShopId());
                intent.putExtra("shop_name", resList.get(position - 1).getShopName());
                intent.putExtra("goods_count", resList.get(position - 1).getGoods_sc_count() + "");
                intent.putExtra("min_cost", min_cost);
                startActivity(intent);
            }
        });
        product_list_ptrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                OkHttpManager.postAsync(G.Host.JUICE_GOODS_LIST, requestMap, new MyCallBack(1, JuiceActivity.this, new StoreProductJsonData(), handler));
                handler.sendEmptyMessageDelayed(150, 5000);
            }

        });
    }

    private void aboutPopWindows(View v) {
        shopCartListAdapter = new ShopCartListAdapter(shopCarList, this, handler);
        shopping_trolley_lv.setAdapter(shopCartListAdapter);
        shopCartListAdapter.notifyDataSetChanged();
        mPopWindow.setAnimationStyle(R.style.AnimBottom);
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        mPopWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1] - mPopWindow.getHeight());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.product_list_btn_to_buy:
                if (!TiaoshiApplication.isLogin) {
                    Toast.makeText(this, "您尚未登录，请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(FCActivity.getFCActivityIntent(this,PhoneLoginFragment.class));
                    return;
                }
                if (shopCarList.size() != 0) {
                    Intent intent = new Intent(JuiceActivity.this, ConfirmOrderActivity.class);
                    intent.putExtra("shop_name", shop_name);
                    intent.putExtra("isShare", "YES");
                    startActivity(intent);
                }
                break;
            case R.id.product_list_rl_buy:
                if (shopCarList.size() > 0) {
                    if (mPopWindow.isShowing()) {
                        mPopWindow.dismiss();
                    } else {
                        aboutPopWindows(v);
                    }
                }
                break;
            case R.id.hopping_trolley_tvClear:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("清空购物车").setMessage("确定清空购物车?").setPositiveButton("取消", null).setNegativeButton("清空", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeAll();
                    }
                }).create().show();
                break;
        }
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("changeList".equals(intent.getAction())) {
                total_goods_count = TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_count();
                total_goods_cash = TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_cash();
                shopCarList = TiaoshiApplication.diyShoppingCartJsonData.getGoodsList();
                for (int i = 0; i < resList.size(); i++) {
                    int a = 0;
                    for (int j = 0; j < shopCarList.size(); j++) {
                        if (resList.get(i).getGoodsId().equals(shopCarList.get(j).getGoods_id())) {
                            a += shopCarList.get(j).getGoods_count();
                            resList.get(i).setGoods_sc_count(a);

                        }
                    }
                }
                Boolean isNull = false;
                for (int i = 0; i < resList.size(); i++) {
                    if (resList.get(i).getGoods_sc_count() > 0) {
                        for (int j = 0; j < shopCarList.size(); j++) {
                            if (resList.get(i).getGoodsId().equals(shopCarList.get(j).getGoods_id())) {
                                isNull = true;
                            }
                        }
                        if (!isNull) {
                            resList.get(i).setGoods_sc_count(0);
                        }
                    }
                    isNull = false;
                }
                mAdapter.notifyDataSetChanged();
                BigDecimal a2 = new BigDecimal(Double.toString(total_goods_cash));
                if (total_goods_cash < Double.parseDouble(min_cost)) {
                    product_list_tvTotal.setText("￥：" + total_goods_cash + "元");
                } else {
                    product_list_tvTotal.setText("￥：" + total_goods_cash + "元");
                }
                if (total_goods_count == 0) {
                    badgeView.setVisibility(View.GONE);
                } else {
                    badgeView.setVisibility(View.VISIBLE);
                }
                badgeView.setText(total_goods_count + "");
            } else if ("clearList".equals(intent.getAction())) {
                for (int i = 0; i < resList.size(); i++) {
                    resList.get(i).setGoods_sc_count(0);
                }
                mAdapter.notifyDataSetChanged();
                total_goods_count = 0;
                total_goods_cash = 0;
                shopCarList.clear();
                badgeView.setText(total_goods_cash + "");
                badgeView.setVisibility(View.GONE);
                product_list_tvTotal.setText("￥：" + total_goods_cash + "元");
            } else if ("removeAll".equals(intent.getAction())) {
                removeAll();
            }
        }
    }

    private void removeAll() {
        shopCarList.clear();
        if (shopCartListAdapter != null) {
            shopCartListAdapter.notifyDataSetChanged();
        }
        for (int i = 0; i < resList.size(); i++) {
            resList.get(i).setGoods_sc_count(0);
        }
        mAdapter.notifyDataSetChanged();
        mPopWindow.dismiss();
        total_goods_count = 0;
        total_goods_cash = 0;
        badgeView.setText(total_goods_count + "");
        badgeView.setVisibility(View.GONE);
        product_list_tvTotal.setText("￥：" + total_goods_cash + "元");
        TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(0);
        TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(0);
        TiaoshiApplication.diyShoppingCartJsonData.setGoodsList(new LinkedList<DiyShoppingCartJsonData.GoodsListEntity>());
        TiaoshiApplication.diyShoppingCartJsonData.setShop_id(null);
        Intent intent = new Intent("clearCount");
        this.sendBroadcast(intent);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    loadingDialog.dismiss();
                    GoodsNO = 0;
                    product_list_ptrListView.onRefreshComplete();
                    StoreProductJsonData storeProductJsonData = (StoreProductJsonData) msg.obj;
                    if (storeProductJsonData != null) {
                        if (storeProductJsonData.getRespCode().equals("SUCCESS") && null != storeProductJsonData.getData().getList()) {
                            dataList = storeProductJsonData.getData().getList();
                            if (dataList.size() > 0) {
                                shop_id = dataList.get(0).getShopId();
                                shop_name = dataList.get(0).getShopName();
                            } else {
                                ToastUtil.showShort(JuiceActivity.this, "暂时没有相关内容");
                                break;
                            }
                            if (resList.size() > 0) {
                                status = storeProductJsonData.getData().getList().get(0).getShopSalesStatus();
                                for (int m = 0; m < dataList.size(); m++) {
                                    for (int n = 0; n < resList.size(); n++) {
                                        if (dataList.get(m).getGoodsId().equals(resList.get(n).getGoodsId())) {
                                            dataList.get(m).setGoods_sc_count(resList.get(n).getGoods_sc_count());
                                        }
                                    }
                                }
                            }
                            resList.clear();
                            resList.addAll(dataList);
                            int oldgoodlistsize = -1;
                            if (TiaoshiApplication.diyShoppingCartJsonData.getGoodsList() != null) {
                                if (TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().size() != 0) {
                                    List<DiyShoppingCartJsonData.GoodsListEntity> list1 = new LinkedList<>();
                                    oldgoodlistsize = TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().size();
                                    int count = 0;
                                    Double allmoney = 0.0;
                                    DecimalFormat formater = new DecimalFormat("#0.#");
                                    for (int k = 0; k < resList.size(); k++) {
                                        double money = 0.0;
                                        int a = 0;
                                        for (int j = 0; j < TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().size(); j++) {
                                            if (TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().get(j).getGoods_id().equals(resList.get(k).getGoodsId())) {
                                                a += TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().get(j).getGoods_count();
                                                resList.get(k).setGoods_sc_count(a);
                                                //再来一单所涉及逻辑
                                            }
                                        }
                                        allmoney += money;
                                    }
                                    if (listolderNo != null && listolderNo.equals(G.SP.LIST_Older)) {
                                        TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(count);
                                        TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(Double.parseDouble(formater.format(allmoney)));
                                        TiaoshiApplication.diyShoppingCartJsonData.setShop_id(TiaoshiApplication.diyShoppingCartJsonData.getShop_id());
                                        TiaoshiApplication.diyShoppingCartJsonData.setGoodsList(list1);
                                    }
                                }
                            }
                            mAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showShort(JuiceActivity.this, storeProductJsonData.getRespMsg());
                        }
                    }

                    break;
                case 3:
                    ++total_goods_count;
                    if (total_goods_count == 1) {
                        badgeView.setVisibility(View.VISIBLE);
                    }
                    badgeView.setText(total_goods_count + "");
                    int position = (int) msg.obj;
                    int type = msg.arg1;
                    listEntity = resList.get(position);
                    String money1 = "";
                    if ("0".equals(listEntity.getPromotePrice()) || TextUtils.isEmpty(listEntity.getPromotePrice())) {
                        money1 = listEntity.getPrice();
                    } else {
                        money1 = listEntity.getPromotePrice();
                    }
                    BigDecimal a1 = new BigDecimal(Double.toString(total_goods_cash));
                    BigDecimal b1 = new BigDecimal(money1);
                    total_goods_cash = a1.add(b1).doubleValue();
                    a1 = new BigDecimal(Double.toString(total_goods_cash));
                    if (total_goods_cash < Double.parseDouble(min_cost)) {
                        product_list_tvTotal.setText("￥：" + total_goods_cash + "元");
                    } else {
                        product_list_tvTotal.setText("￥：" + total_goods_cash + "元");
                    }
                    String[] juicepack = listEntity.getPack().split(",");
                    switch (type) {
                        case 1:
                            typename = juicepack[0];
                            break;
                        case 2:
                            typename = juicepack[1];
                            break;
                        case 3:
                            typename = juicepack[2];
                            break;
                        case 0:
                            typename = "";
                            break;
                    }
                    if (shopCarList.size() == 0) {
                        DiyShoppingCartJsonData.GoodsListEntity goodsListEntity = new DiyShoppingCartJsonData.GoodsListEntity();
                        goodsListEntity.setGoods_id(listEntity.getGoodsId());
                        if (type == 0) {
                            goodsListEntity.setGoods_name(listEntity.getGoodsName());
                        } else {
                            goodsListEntity.setGoods_name(listEntity.getGoodsName() + "-" + typename);
                        }
                        goodsListEntity.setGoods_price(b1.multiply(new BigDecimal(listEntity.getGoods_sc_count())).toString() + "元");
                        goodsListEntity.setPrice(money1);
                        goodsListEntity.setTypename(typename);
                        goodsListEntity.setSales_unit(listEntity.getSalesUnit());
                        goodsListEntity.setGoods_count(listEntity.getGoods_sc_count());
                        shopCarList.add(goodsListEntity);
                    } else {
                        boolean isContained = false;
                        for (int i = 0; i < shopCarList.size(); i++) {
                            if (listEntity.getGoodsId().equals(shopCarList.get(i).getGoods_id()) && typename.equals(shopCarList.get(i).getTypename())) {
                                DiyShoppingCartJsonData.GoodsListEntity goodsListEntity = shopCarList.get(i);
                                goodsListEntity.setGoods_id(listEntity.getGoodsId());
                                goodsListEntity.setGoods_price(b1.multiply(new BigDecimal(shopCarList.get(i).getGoods_count() + 1)).toString() + "元");
                                goodsListEntity.setPrice(money1);
                                goodsListEntity.setGoods_count(shopCarList.get(i).getGoods_count() + 1);
                                isContained = true;
                            }
                        }
                        if (!isContained) {
                            DiyShoppingCartJsonData.GoodsListEntity goodsListEntity = new DiyShoppingCartJsonData.GoodsListEntity();
                            goodsListEntity.setGoods_id(listEntity.getGoodsId());
                            if (type == 0) {
                                goodsListEntity.setGoods_name(listEntity.getGoodsName());
                            } else {
                                goodsListEntity.setGoods_name(listEntity.getGoodsName() + "-" + typename);
                            }
//                            goodsListEntity.setGoods_price(b1.multiply(new BigDecimal(listEntity.getGoods_sc_count())).toString() + "元");
                            goodsListEntity.setGoods_price(b1.multiply(new BigDecimal(1)).toString() + "元");
                            goodsListEntity.setPrice(money1);
//                            goodsListEntity.setGoods_count(listEntity.getGoods_sc_count());
                            goodsListEntity.setGoods_count(1);
                            goodsListEntity.setTypename(typename);
                            goodsListEntity.setSales_unit(listEntity.getSalesUnit());
                            shopCarList.add(goodsListEntity);
                        }
                    }
                    TiaoshiApplication.diyShoppingCartJsonData.setGoodsList(shopCarList);
                    TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(total_goods_count);
                    TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(total_goods_cash);
                    sendBroadcast(intent);
                    sendBroadcast(intent1);
                    break;
                case 4:
                    --total_goods_count;
                    if (total_goods_count <= 0) {
                        badgeView.setVisibility(View.GONE);
                    }
                    badgeView.setText(total_goods_count + "");
                    int position2 = (int) msg.obj;
                    listEntity = resList.get(position2);
                    String money2 = "";
                    if ("0".equals(listEntity.getPromotePrice()) || TextUtils.isEmpty(listEntity.getPromotePrice())) {
                        money2 = listEntity.getPrice();
                    } else {
                        money2 = listEntity.getPromotePrice();
                    }
                    BigDecimal a2 = new BigDecimal(Double.toString(total_goods_cash));
                    BigDecimal b2 = new BigDecimal(money2);
                    total_goods_cash = a2.subtract(b2).doubleValue();
                    a2 = new BigDecimal(Double.toString(total_goods_cash));
                    if (total_goods_cash < Double.parseDouble(min_cost)) {
                        product_list_tvTotal.setText("￥：" + total_goods_cash + "元");
                    } else {
                        product_list_tvTotal.setText("￥：" + total_goods_cash + "元");
                    }
                    if (listEntity.getGoods_sc_count() == 0) {
                        for (int i = 0; i < shopCarList.size(); i++) {
                            if (listEntity.getGoodsId().equals(shopCarList.get(i).getGoods_id())) {
                                shopCarList.remove(i);
                            }
                        }
                    } else {
                        for (int i = 0; i < shopCarList.size(); i++) {
                            if (listEntity.getGoodsId().equals(shopCarList.get(i).getGoods_id())) {
                                DiyShoppingCartJsonData.GoodsListEntity goodsListEntity = shopCarList.get(i);
                                goodsListEntity.setGoods_id(listEntity.getGoodsId());
                                goodsListEntity.setGoods_name(listEntity.getGoodsName());
                                goodsListEntity.setGoods_price(b2.multiply(new BigDecimal(listEntity.getGoods_sc_count())).toString() + "元");
                                goodsListEntity.setPrice(money2);
                                goodsListEntity.setGoods_count(listEntity.getGoods_sc_count());
                            }
                        }
                    }
                    if (shopCarList.size() == 0) {
                        TiaoshiApplication.diyShoppingCartJsonData.setShop_id(null);
                    }
                    TiaoshiApplication.diyShoppingCartJsonData.setGoodsList(shopCarList);
                    TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(total_goods_count);
                    TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(total_goods_cash);
                    sendBroadcast(intent);
                    sendBroadcast(intent1);
                    break;
                case 5:
                    ++total_goods_count;
                    if (total_goods_count == 1) {
                        badgeView.setVisibility(View.VISIBLE);
                    }
                    badgeView.setText(total_goods_count + "");
                    int position3 = (int) msg.obj;
                    shopCarListEntity = shopCarList.get(position3);

                    for (int i = 0; i < resList.size(); i++) {
                        if (shopCarListEntity.getGoods_id().equals(resList.get(i).getGoodsId())) {
                            int count = resList.get(i).getGoods_sc_count();
                            ++count;
                            resList.get(i).setGoods_sc_count(count);
                            String money3 = shopCarListEntity.getPrice();
                            BigDecimal a3 = new BigDecimal(Double.toString(total_goods_cash));
                            BigDecimal b3 = new BigDecimal(money3);
                            total_goods_cash = a3.add(b3).doubleValue();
                            a3 = new BigDecimal(Double.toString(total_goods_cash));
                            if (total_goods_cash < Double.parseDouble(min_cost)) {
                                product_list_tvTotal.setText("￥：" + total_goods_cash + "元");
                            } else {
                                product_list_tvTotal.setText("￥：" + total_goods_cash + "元");
                            }
                            TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(total_goods_count);
                            TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(total_goods_cash);
                            sendBroadcast(intent);
                            sendBroadcast(intent1);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
                case 6:
                    --total_goods_count;
                    if (total_goods_count <= 0) {
                        badgeView.setVisibility(View.GONE);
                    }
                    badgeView.setText(total_goods_count + "");
                    int position4 = (int) msg.obj;
                    shopCarListEntity = shopCarList.get(position4);
                    for (int i = 0; i < resList.size(); i++) {
                        if (shopCarListEntity.getGoods_id().equals(resList.get(i).getGoodsId())) {
                            int count = resList.get(i).getGoods_sc_count();
                            --count;
                            resList.get(i).setGoods_sc_count(count);
                            if (count == 0 || shopCarListEntity.getGoods_count() == 0) {
                                shopCarList.remove(position4);
                            }
                        }
                    }
                    String money4 = shopCarListEntity.getPrice();
                    BigDecimal a4 = new BigDecimal(Double.toString(total_goods_cash));
                    BigDecimal b4 = new BigDecimal(money4);
                    total_goods_cash = a4.subtract(b4).doubleValue();
                    a4 = new BigDecimal(Double.toString(total_goods_cash));
                    if (total_goods_cash < Double.parseDouble(min_cost)) {
                        product_list_tvTotal.setText("￥：" + total_goods_cash + "元");
                    } else {
                        product_list_tvTotal.setText("￥：" + total_goods_cash + "元");
                    }
                    if (resList.size() == 0) {
                        TiaoshiApplication.diyShoppingCartJsonData.setShop_id(null);
                    }
                    TiaoshiApplication.diyShoppingCartJsonData.setGoodsList(shopCarList);
                    TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(total_goods_count);
                    TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(total_goods_cash);
                    sendBroadcast(intent);
                    sendBroadcast(intent1);
                    mAdapter.notifyDataSetChanged();
                    shopCartListAdapter.notifyDataSetChanged();
                    break;
                case 150:
                    product_list_ptrListView.onRefreshComplete();
                    break;
                case 200:
                    product_list_ptrListView.onRefreshComplete();
                    break;
                case 300:
                    product_list_ptrListView.onRefreshComplete();
                    break;
            }
        }
    };

}
