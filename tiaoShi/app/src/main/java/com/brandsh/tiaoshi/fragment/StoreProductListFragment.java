package com.brandsh.tiaoshi.fragment;

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
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.activity.ConfirmOrderActivity;
import com.brandsh.tiaoshi.activity.FCActivity;
import com.brandsh.tiaoshi.adapter.ShopCartListAdapter;
import com.brandsh.tiaoshi.adapter.StoreProductAdapter;
import com.brandsh.tiaoshi.adapter.TypeAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.DiyShoppingCartJsonData;
import com.brandsh.tiaoshi.model.StoreProductJsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.widget.ProgressHUD;
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

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by libokang on 15/9/2.
 */
public class StoreProductListFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;

    @ViewInject(R.id.product_list_ptrListView)
    private StickyListHeadersListView product_list_ptrListView;
    @ViewInject(R.id.product_list_rl_buy)
    private RelativeLayout product_list_rl_buy;
    @ViewInject(R.id.product_list_ivShoppingTrolley)
    private ImageView product_list_ivShoppingTrolley;
    @ViewInject(R.id.product_list_tvTotal)
    private TextView product_list_tvTotal;
    @ViewInject(R.id.product_list_btn_to_buy)
    private Button product_list_btn_to_buy;
  @ViewInject(R.id.typeRecyclerView)
    private RecyclerView rvType;

    //数据及数据源状态判断
    private List<StoreProductJsonData.DataBean.ListBean> resList = new ArrayList<>();
    private StoreProductJsonData.DataBean.ListBean listEntity;
    private List<StoreProductJsonData.DataBean.ListBean> dataList;
    private StoreProductAdapter mAdapter;
    private HashMap<String,String> requestMap;
    private String page;
    private ShapeLoadingDialog loadingDialog;
    private String min_cost="0";
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
    private String status="OPEN";
    private String listolderNo="";
    private String typename;
     private  int GoodsNO=0;
    private TypeAdapter typeAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.common_list_fragment_without_title, null);
            ViewUtils.inject(this, rootView);
            shoppingTrolleyDialog = inflater.inflate(R.layout.shopping_trolley_dialog, null);
            shopping_trolley_lv = (ListView) shoppingTrolleyDialog.findViewById(R.id.shopping_trolley_lv);
            hopping_trolley_tvClear = (TextView) shoppingTrolleyDialog.findViewById(R.id.hopping_trolley_tvClear);
            if (!TextUtils.isEmpty(getActivity().getIntent().getStringExtra("min_cost"))){
                min_cost = getActivity().getIntent().getStringExtra("min_cost");
            }
            shop_id = getActivity().getIntent().getStringExtra("shop_id");
            if (shop_id==null){
                shop_id="null";
            }
            if (TiaoshiApplication.diyShoppingCartJsonData==null){
                TiaoshiApplication.diyShoppingCartJsonData=new DiyShoppingCartJsonData();
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
            }else {
                shopCarList = new LinkedList<>();
            }
            page = "1";
            requestMap=new HashMap<>();
            requestMap.put("shopId", shop_id);
            requestMap.put("lng", TiaoshiApplication.Lng);
            requestMap.put("lat", TiaoshiApplication.Lat);
            requestMap.put("actReq",SignUtil.getRandom());
            requestMap.put("pageSize","500" );
            requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
            String sign= SignUtil.getSign(requestMap);
            requestMap.put("sign", Md5.toMd5(sign));
            Log.e("1111",sign);
            OkHttpManager.postAsync(G.Host.STORE_PRODUCT_LIST + "?page=" + page, requestMap, new MyCallBack(1, getActivity(), new StoreProductJsonData(), handler));
            init();
            setListenerToPTRListView();
            mAdapter = new StoreProductAdapter(getActivity(), resList, handler, shop_id,badgeView);
            product_list_ptrListView.setAdapter(mAdapter);
        } else {
            if (rootView.getParent() != null) {
                ((ViewGroup) rootView.getParent()).removeView(rootView);
            }
        }
        return rootView;
    }


    private void call() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle("重新选择:").setMessage("您选择的商品中有已下架商品请重新选择");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                removeAll();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeAll();
                getActivity().finish();
            }
        });
        builder.show();
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("changeList".equals(intent.getAction())) {
                total_goods_count = TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_count();
                total_goods_cash = TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_cash();
                shopCarList = TiaoshiApplication.diyShoppingCartJsonData.getGoodsList();
                for (int i = 0; i < resList.size(); i++) {
                    int a=0;
                    for (int j = 0; j < shopCarList.size(); j++) {
                        if (resList.get(i).getGoodsId().equals(shopCarList.get(j).getGoods_id())) {
                            a+= shopCarList.get(j).getGoods_count();
                            resList.get(i).setGoods_sc_count(a);

                        }
                    }
                }
                Boolean isNull=false;
                for (int i=0;i<resList.size();i++){
                    if (resList.get(i).getGoods_sc_count()>0) {
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
                    product_list_tvTotal.setText("还差" + (new BigDecimal(min_cost).subtract(new BigDecimal(Double.toString(total_goods_cash)))) + "元免费配送");
                } else {
                    product_list_tvTotal.setText("购物车总价：￥"+ total_goods_cash + "元");
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
                product_list_tvTotal.setText("还差" + min_cost + "元免费配送");
            } else if ("removeAll".equals(intent.getAction())) {
                removeAll();
            }
        }
    }

    private void setListenerToPTRListView() {
        product_list_ptrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = FCActivity.getFCActivityIntent(getActivity(), ProductDetailFragment.class);
                intent.putExtra("what","1");
                intent.putExtra("goods_id", resList.get(position).getGoodsId());
                intent.putExtra("goods_name", resList.get(position).getGoodsName());
                intent.putExtra("shop_id", resList.get(position).getShopId());
                intent.putExtra("shop_name", resList.get(position ).getShopName());
                intent.putExtra("goods_count", resList.get(position).getGoods_sc_count()+"");
                intent.putExtra("min_cost", min_cost);
                intent.putExtra("is_new", resList.get(position).getActivityName());
                startActivity(intent);
            }
        });
    }
    private void init() {
        listolderNo= getActivity().getIntent().getStringExtra("listolderNO");
        intent = new Intent("changeCount");
        intent1=new Intent("changedetail");
        hopping_trolley_tvClear.setOnClickListener(this);
        product_list_rl_buy.setOnClickListener(this);
        product_list_btn_to_buy.setOnClickListener(this);
        loadingDialog = ProgressHUD.show(getActivity(), "努力加载中...");
        loadingDialog.show();
        badgeView = new BadgeView(getActivity());
        badgeView.setTargetView(product_list_ivShoppingTrolley);
        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("changeList");
        intentFilter.addAction("clearList");
        intentFilter.addAction("removeAll");
        getActivity().registerReceiver(myBroadcastReceiver, intentFilter);

        if (total_goods_count != 0) {
            badgeView.setVisibility(View.VISIBLE);
            badgeView.setText(total_goods_count + "");
        } else {
            badgeView.setVisibility(View.GONE);
        }
        mPopWindow = new PopupWindow(shoppingTrolleyDialog, ViewGroup.LayoutParams.MATCH_PARENT, 600, true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());


    }
    //根据类别id获取分类的Position 用于滚动左侧的类别列表
    public int getSelectedGroupPosition(int typeId){
        for(int i=0;i<resList.size();i++){
            if(typeId==Integer.parseInt(resList.get(i).getGoodsId())){
                return i;
            }
        }
        return 0;
    }
    private int getSelectedPosition(int typeId){
        int position = 0;
        List<StoreProductJsonData.DataBean.ListBean> resList1=this.resList;
        for(int i=0;i<resList1.size();i++){
            if(Integer.parseInt(resList1.get(i).getCustomCategoryId()) == typeId){
                position = i;
                break;
            }
        }
        return position;
    }

    private void aboutPopWindows(View v) {
        shopCartListAdapter = new ShopCartListAdapter(shopCarList, getActivity(),handler);
        shopping_trolley_lv.setAdapter(shopCartListAdapter);
        shopCartListAdapter.notifyDataSetChanged();
        mPopWindow.setAnimationStyle(R.style.AnimBottom);
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        mPopWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1] - mPopWindow.getHeight());
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    GoodsNO=0;
                    StoreProductJsonData storeProductJsonData = (StoreProductJsonData) msg.obj;
                    if (storeProductJsonData != null) {
                        if (storeProductJsonData.getRespCode().equals("SUCCESS")&&null!=storeProductJsonData.getData().getList()) {
                            dataList = storeProductJsonData.getData().getList();
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
                            List<StoreProductJsonData.DataBean.ListBean> typeList = new ArrayList<>();
                            typeList.addAll(dataList);
                          for (int i=0;i<typeList.size()-1;i++){
                              for (int j=typeList.size()-1;j>i;j--){
                                  if (typeList.get(j).getCustomCategoryId().equals(typeList.get(i).getCustomCategoryId())){
                                      typeList.remove(j);
                                  }
                              }
                          }
                            for (int i=0;i<typeList.size();i++){
                                for (int j=0;j<dataList.size();j++){
                                    if (typeList.get(i).getCustomCategoryId().equals(dataList.get(j).getCustomCategoryId())){
                                        resList.add(dataList.get(j));
                                    }
                                }
                            }
                            //type----------------------kaishi
                            rvType.setLayoutManager(new LinearLayoutManager(getActivity()));
                            typeAdapter = new TypeAdapter(getActivity(),typeList,handler);
                            rvType.setAdapter(typeAdapter);
                            rvType.addItemDecoration(new DividerDecoration(getActivity()));
                           //设置购物车数据
                            if (dataList.size()>0){
                               shop_name=dataList.get(0).getShopName();
                                min_cost=dataList.get(0).getFreeSend();
                                if (total_goods_cash == 0) {
                                    product_list_tvTotal.setText("还差"+min_cost+"元免费配送");
                                } else {
                                    if (total_goods_cash <= Double.parseDouble(min_cost)) {
                                        product_list_tvTotal.setText("还差" + (new BigDecimal(min_cost).subtract(new BigDecimal(Double.toString(total_goods_cash)))) + "元免费配送");
                                    } else {
                                        product_list_tvTotal.setText("购物车总价：￥"+ total_goods_cash + "元");
                                    }
                                }
                            }
                            //-------------
                            product_list_ptrListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(AbsListView view, int scrollState) {

                                }

                                @Override
                                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                 if (null!=resList&&resList.size()>0){

                                     StoreProductJsonData.DataBean.ListBean item = resList.get(firstVisibleItem);
                                     if(typeAdapter.selectTypeId != Integer.parseInt(item.getCustomCategoryId())) {
                                         typeAdapter.selectTypeId = Integer.parseInt(item.getCustomCategoryId());
                                         typeAdapter.notifyDataSetChanged();
                                         rvType.smoothScrollToPosition(getSelectedGroupPosition(Integer.parseInt(item.getCustomCategoryId())));
                                     }
                                 }
                                }
                            });
                            int oldgoodlistsize=-1;
                            if (TiaoshiApplication.diyShoppingCartJsonData.getGoodsList() != null) {
                                if (TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().size() != 0) {
                                    List<DiyShoppingCartJsonData.GoodsListEntity> list1 = new LinkedList<>();
                                    oldgoodlistsize=TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().size();
                                    int count=0;
                                    Double allmoney=0.0;
                                    DecimalFormat formater = new DecimalFormat("#0.#");
                                        for (int k = 0; k < resList.size(); k++) {
                                            double money=0.0;
                                            int a=0;
                                            for (int j = 0; j < TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().size(); j++) {
                                            if (TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().get(j).getGoods_id().equals(resList.get(k).getGoodsId())) {
                                                a+=TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().get(j).getGoods_count();
                                                resList.get(k).setGoods_sc_count(a);
                                                //再来一单所涉及逻辑
                                                if (listolderNo!=null&&listolderNo.equals(G.SP.LIST_Older)){
                                                    DiyShoppingCartJsonData.GoodsListEntity d1 = new DiyShoppingCartJsonData.GoodsListEntity();
                                                    d1.setGoods_id(resList.get(k).getGoodsId());
                                                    d1.setTypename(TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().get(j).getTypename());
                                                    if (TextUtils.isEmpty(d1.getTypename())){
                                                        d1.setGoods_name(resList.get(k).getGoodsName());
                                                    }else {
                                                        d1.setGoods_name(resList.get(k).getGoodsName()+"-"+d1.getTypename());
                                                    }

                                                    int Goods_conunt=TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().get(j).getGoods_count();
                                                    if (!resList.get(k).getPromotePrice().equals("0")&&!TextUtils.isEmpty(resList.get(k).getPromotePrice())){
                                                        d1.setPrice(resList.get(k).getPromotePrice());
                                                        money=Double.parseDouble(resList.get(k).getPromotePrice())*Goods_conunt;
                                                    }else {
                                                        d1.setPrice(resList.get(k).getPrice());
                                                        money=Double.parseDouble(resList.get(k).getPrice())*Goods_conunt;
                                                    }
                                                    d1.setGoods_count(Goods_conunt);
                                                    d1.setGoods_price(formater.format(money) + "元");
                                                    d1.setUnit(resList.get(k).getSalesUnit());
                                                    count+=Goods_conunt;
                                                    list1.add(d1);
                                                    GoodsNO+=1;
                                                }
                                            }
                                        }
                                        allmoney+=money;
                                    }
                                    if (listolderNo!=null&&listolderNo.equals(G.SP.LIST_Older)){
                                        TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(count);
                                        TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(Double.parseDouble(formater.format(allmoney)));
                                        TiaoshiApplication.diyShoppingCartJsonData.setShop_id(TiaoshiApplication.diyShoppingCartJsonData.getShop_id());
                                        TiaoshiApplication.diyShoppingCartJsonData.setGoodsList(list1);
                                    }

                                }
                            }
                            mAdapter.notifyDataSetChanged();
                            if (listolderNo!=null&&listolderNo.equals(G.SP.LIST_Older)){
                                if (TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().size()==oldgoodlistsize){
                                  Intent intent1 = new Intent("changeList");
                                    getActivity().sendBroadcast(intent1);
                                }else {
                                    call();
                                }
                            }
                        } else {
                            showToast(storeProductJsonData.getRespMsg());
                        }
                    }
                        loadingDialog.dismiss();

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
                    if ("0".equals(listEntity.getPromotePrice())||TextUtils.isEmpty(listEntity.getPromotePrice())) {
                        money1 = listEntity.getPrice();
                    } else {
                        money1 = listEntity.getPromotePrice();
                    }
                    BigDecimal a1 = new BigDecimal(Double.toString(total_goods_cash));
                    BigDecimal b1 = new BigDecimal(money1);
                    total_goods_cash = a1.add(b1).doubleValue();
                    a1 = new BigDecimal(Double.toString(total_goods_cash));
                    if (total_goods_cash < Double.parseDouble(min_cost)) {
                        product_list_tvTotal.setText("还差"+(new BigDecimal(min_cost).subtract(a1))+"元免费配送");
                    } else {
                        product_list_tvTotal.setText("购物车总价：￥"+ total_goods_cash + "元");
                    }
                    String [] juicepack=listEntity.getPack().split(",");
                    switch (type){
                        case 1:
                            typename=juicepack[0];
                            break;
                        case 2:
                            typename=juicepack[1];
                            break;
                        case 3:
                            typename=juicepack[2];
                            break;
                        case 0:
                            typename="";
                            break;
                    }
                    if (shopCarList.size() == 0) {
                        DiyShoppingCartJsonData.GoodsListEntity goodsListEntity = new DiyShoppingCartJsonData.GoodsListEntity();
                        goodsListEntity.setGoods_id(listEntity.getGoodsId());
                        if (type==0){
                            goodsListEntity.setGoods_name(listEntity.getGoodsName());
                        }else {
                            goodsListEntity.setGoods_name(listEntity.getGoodsName()+"-"+typename);
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
                            if (listEntity.getGoodsId().equals(shopCarList.get(i).getGoods_id())&&typename.equals(shopCarList.get(i).getTypename())) {
                                DiyShoppingCartJsonData.GoodsListEntity goodsListEntity = shopCarList.get(i);
                                goodsListEntity.setGoods_id(listEntity.getGoodsId());
                                goodsListEntity.setGoods_price(b1.multiply(new BigDecimal(shopCarList.get(i).getGoods_count()+1)).toString() + "元");
                                goodsListEntity.setPrice(money1);
                                goodsListEntity.setGoods_count(shopCarList.get(i).getGoods_count()+1);
                                isContained = true;
                            }
                        }
                        if (!isContained) {
                            DiyShoppingCartJsonData.GoodsListEntity goodsListEntity = new DiyShoppingCartJsonData.GoodsListEntity();
                            goodsListEntity.setGoods_id(listEntity.getGoodsId());
                            if (type==0){
                                goodsListEntity.setGoods_name(listEntity.getGoodsName());
                            }else {
                                goodsListEntity.setGoods_name(listEntity.getGoodsName()+"-"+typename);
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
                    getActivity().sendBroadcast(intent);
                    getActivity().sendBroadcast(intent1);
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
                    if ("0".equals(listEntity.getPromotePrice())||TextUtils.isEmpty(listEntity.getPromotePrice())) {
                        money2 = listEntity.getPrice();
                    } else {
                        money2 = listEntity.getPromotePrice();
                    }
                    BigDecimal a2 = new BigDecimal(Double.toString(total_goods_cash));
                    BigDecimal b2 = new BigDecimal(money2);
                    total_goods_cash = a2.subtract(b2).doubleValue();
                    a2 = new BigDecimal(Double.toString(total_goods_cash));
                    if (total_goods_cash < Double.parseDouble(min_cost)) {
                        product_list_tvTotal.setText("还差"+(new BigDecimal(min_cost).subtract(a2))+"元免费配送");
                    } else {
                        product_list_tvTotal.setText("购物车总价：￥"+ total_goods_cash + "元");
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
                    getActivity().sendBroadcast(intent);
                    getActivity().sendBroadcast(intent1);
                    break;
                case 5:
                    ++total_goods_count;
                    if (total_goods_count == 1) {
                        badgeView.setVisibility(View.VISIBLE);
                    }
                    badgeView.setText(total_goods_count + "");
                    int position3 = (int) msg.obj;
                    shopCarListEntity =shopCarList.get(position3);

                    for (int i = 0; i < resList.size(); i++) {
                        if (shopCarListEntity.getGoods_id().equals(resList.get(i).getGoodsId())) {
                            int count=resList.get(i).getGoods_sc_count();
                            ++count;
                            resList.get(i).setGoods_sc_count(count);
                            String money3 = shopCarListEntity.getPrice();
                            BigDecimal a3 = new BigDecimal(Double.toString(total_goods_cash));
                            BigDecimal b3 = new BigDecimal(money3);
                            total_goods_cash = a3.add(b3).doubleValue();
                            a3 = new BigDecimal(Double.toString(total_goods_cash));
                            if (total_goods_cash < Double.parseDouble(min_cost)) {
                                product_list_tvTotal.setText("还差"+(new BigDecimal(min_cost).subtract(a3))+"元免费配送");
                            } else {
                                product_list_tvTotal.setText("购物车总价：￥"+ total_goods_cash + "元");
                            }
                            TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(total_goods_count);
                            TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(total_goods_cash);
                            getActivity().sendBroadcast(intent);
                            getActivity().sendBroadcast(intent1);
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
                            int count=resList.get(i).getGoods_sc_count();
                            --count;
                            resList.get(i).setGoods_sc_count(count);
                            if (count==0||shopCarListEntity.getGoods_count()==0){
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
                        product_list_tvTotal.setText("还差"+(new BigDecimal(min_cost).subtract(a4))+"元免费配送");
                    } else {
                        product_list_tvTotal.setText("购物车总价：￥"+ total_goods_cash + "元");
                    }
                    if (resList.size() == 0) {
                        TiaoshiApplication.diyShoppingCartJsonData.setShop_id(null);
                    }
                    TiaoshiApplication.diyShoppingCartJsonData.setGoodsList(shopCarList);
                    TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(total_goods_count);
                    TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(total_goods_cash);
                    getActivity().sendBroadcast(intent);
                    getActivity().sendBroadcast(intent1);
                    mAdapter.notifyDataSetChanged();
                    shopCartListAdapter.notifyDataSetChanged();
                    break;
                case 8:
                    //根据左边滑动右边
                    onTypeClicked(msg.arg1);
                    break;
//                case 150:
//                    product_list_ptrListView.onRefreshComplete();
//                    break;
//                case 200:
//                    product_list_ptrListView.onRefreshComplete();
//                    break;
//                case 300:
//                    product_list_ptrListView.onRefreshComplete();
//                    break;
            }
        }
    };
    public void onTypeClicked(int typeId){
        product_list_ptrListView .setSelection(getSelectedPosition(typeId));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.product_list_btn_to_buy:
                if (!TiaoshiApplication.isLogin) {
                    showToast("您尚未登录，请先登录");
                    startActivity(FCActivity.getFCActivityIntent(getActivity(), PhoneLoginFragment.class));
                    return;
                }
                if ("CLOSE".equals(status)){
                    Toast.makeText(getActivity(), "当前商铺已经打烊，暂时无法购买", Toast.LENGTH_SHORT).show();
                } else if ("OPEN".equals(status)){
                        if (shopCarList.size() != 0) {
                            Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
                            intent.putExtra("shop_name", shop_name);
                            startActivity(intent);
                        }
                }
                break;
            case R.id.product_list_rl_buy:
                if (shopCarList.size() > 0) {
                    if (mPopWindow.isShowing()) {
                        mPopWindow.dismiss();
                    } else {
                        mPopWindow.setWidth(product_list_rl_buy.getMeasuredWidth());
                        aboutPopWindows(v);
                    }
                }
                break;
            case R.id.hopping_trolley_tvClear:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("清空购物车").setMessage("确定清空购物车?").setPositiveButton("取消", null).setNegativeButton("清空", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeAll();
                    }
                }).create().show();
                break;
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

        product_list_tvTotal.setText("还差￥" + min_cost + "元免费配送");
        TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(0);
        TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(0);
        TiaoshiApplication.diyShoppingCartJsonData.setGoodsList(new LinkedList<DiyShoppingCartJsonData.GoodsListEntity>());
        TiaoshiApplication.diyShoppingCartJsonData.setShop_id(null);
        Intent intent = new Intent("clearCount");
        getActivity().sendBroadcast(intent);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(myBroadcastReceiver);
    }
}
