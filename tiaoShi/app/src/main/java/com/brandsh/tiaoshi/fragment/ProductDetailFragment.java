package com.brandsh.tiaoshi.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.activity.ConfirmOrderActivity;
import com.brandsh.tiaoshi.activity.FCActivity;
import com.brandsh.tiaoshi.activity.StoreDetailActivity;
import com.brandsh.tiaoshi.adapter.ShopDetailListAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.DiyShoppingCartJsonData;
import com.brandsh.tiaoshi.model.GoodsDetailJsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.DensityUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.StatusBarUtil;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.jauker.widget.BadgeView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by libokang on 15/11/2.
 */
public class ProductDetailFragment extends BaseFragment implements View.OnClickListener {

    private static final int ASYNC_GET_PRODUCT_DETAIL = 0;
    @ViewInject(R.id.convenientBanner)
    private ConvenientBanner convenientBanner;
    @ViewInject(R.id.name)
    private TextView name;
    @ViewInject(R.id.tv_price)
    private TextView tv_price;
    @ViewInject(R.id.product_detail_view)
    private View product_detail_view;
    @ViewInject(R.id.count_and_unit)
    private TextView count_and_unit;
    @ViewInject(R.id.detail)
    private TextView detail;
    @ViewInject(R.id.total)
    private TextView total;
    @ViewInject(R.id.buy_count)
    private TextView buy_count;
    @ViewInject(R.id.tv_pack_choose)
    private TextView tv_pack_choose;
    @ViewInject(R.id.btn_add)
    private ImageView btn_add;
    @ViewInject(R.id.btn_minus)
    private ImageView btn_minus;
    @ViewInject(R.id.store_product_juiceDelete)
    private ImageView store_product_juiceDelete;
    @ViewInject(R.id.btn_to_buy)
    private Button btn_to_buy;
    @ViewInject(R.id.product_detail_llTag1)
    private LinearLayout product_detail_llTag1;
    @ViewInject(R.id.product_detail_ivGift)
    private TextView product_detail_ivGift;
    @ViewInject(R.id.product_detail_ivChaoZhi)
    private TextView product_detail_ivChaoZhi;
    @ViewInject(R.id.product_detail_ivChaoZhi2)
    private TextView product_detail_ivChaoZhi2;
    @ViewInject(R.id.comment_pic)
    private LinearLayout comment_pic;
    @ViewInject(R.id.tv_zhognliang)
    private TextView tv_zhognliang;
    private RelativeLayout product_detail_rlBack;

    private ImageView product_detail_ivStar1;
    private ImageView product_detail_ivStar2;
    private ImageView product_detail_ivStar3;
    private ImageView product_detail_ivStar4;
    private ImageView product_detail_ivStar5;
    private TextView product_detail_tvSaleCount;
    private ScrollView product_detail_scrollView;
    private TextView product_detail_tvOldPrice;
    private RelativeLayout product_detail_rlStore;
    private ImageView product_detail_ivShoppingTrolley;
    private RelativeLayout rl_buy;
    private PopupWindow mPopWindow;
    private View shoppingTrolleyDialog;
    private String goods_id;
    private HashMap requestMap;
    private int goods_count;
    private List<DiyShoppingCartJsonData.GoodsListEntity> shopCarList;
    private int total_goods_count;
    private double total_goods_cash;
    private View view;
    private List<String> images;
    private ShopDetailListAdapter shopDetailListAdapter;
    private ListView shopping_trolley_lv;
    private TextView hopping_trolley_tvClear;
    private BadgeView badgeView;
    private String min_cost;
    private String goods_name;
    private Intent intent1;
    private Intent intent2;
    private RemoveAllBR removeAllBR;
    private String is_new;
    private String status;
    private BitmapUtils bitmapUtils;
    private AlertDialog.Builder builder;
    private int type = 0;
    private int goods_sc_count = 0;
    private String[] juicepack;
    private String typename = "";
    private GoodsDetailJsonData.DataBean bean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.product_detail_fragment, null);
        shoppingTrolleyDialog = inflater.inflate(R.layout.shopping_trolley_dialog, null);
        shopping_trolley_lv = (ListView) shoppingTrolleyDialog.findViewById(R.id.shopping_trolley_lv);
        hopping_trolley_tvClear = (TextView) shoppingTrolleyDialog.findViewById(R.id.hopping_trolley_tvClear);
        ViewUtils.inject(this, view);
        convenientBanner.startTurning(4000);
        goods_id = getActivity().getIntent().getStringExtra("goods_id");
        min_cost = getActivity().getIntent().getStringExtra("min_cost");
        shop_id = getActivity().getIntent().getStringExtra("shop_id");
        shop_name = getActivity().getIntent().getStringExtra("shop_name");
        is_new = getActivity().getIntent().getStringExtra("is_new");
        if (null != getActivity().getIntent().getStringExtra("goods_count")) {
            goods_sc_count = Integer.parseInt(getActivity().getIntent().getStringExtra("goods_count"));
        }
        intent1 = new Intent("changeCount");
        intent2 = new Intent("changeList");
        images = new LinkedList<>();
        requestMap = new HashMap();
        requestMap.put("goodsId", goods_id);
        requestMap.put("shopId", shop_id);
        requestMap.put("actReq", SignUtil.getRandom());
        requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(requestMap);
        requestMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.PRODUCT_DETAIL, requestMap, new MyCallBack(1, getActivity(), new GoodsDetailJsonData(), handler));
        initView();
        initListener();
        if (TiaoshiApplication.diyShoppingCartJsonData.getShop_id() != null) {
            if (!TiaoshiApplication.diyShoppingCartJsonData.getShop_id().equals(shop_id)) {
                goods_count = 0;
                total_goods_cash = 0;
                total_goods_count = 0;
                shopCarList = new LinkedList<>();
            } else {
                if (TiaoshiApplication.diyShoppingCartJsonData.getGoodsList() != null) {
                    goods_count = 0;
                    shopCarList = TiaoshiApplication.diyShoppingCartJsonData.getGoodsList();
                    total_goods_cash = TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_cash();
                    total_goods_count = TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_count();
                    for (int i = 0; i < TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().size(); i++) {
                        if (goods_id.equals(TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().get(i).getGoods_id())) {
                            goods_count += TiaoshiApplication.diyShoppingCartJsonData.getGoodsList().get(i).getGoods_count();
                        }
                    }
                } else {
                    goods_count = 0;
                    total_goods_cash = TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_cash();
                    total_goods_count = TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_count();
                    shopCarList = new LinkedList<>();
                }
            }
        } else {
            goods_count = 0;
            total_goods_cash = TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_cash();
            total_goods_count = TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_count();
            shopCarList = new LinkedList<>();
        }
        if (goods_count != 0) {
            buy_count.setText(goods_count + "");
            buy_count.setVisibility(View.VISIBLE);
            btn_minus.setVisibility(View.VISIBLE);
        } else {
            buy_count.setText("0");
            buy_count.setVisibility(View.GONE);
            btn_minus.setVisibility(View.GONE);
        }
        if (total_goods_cash == 0) {
            total.setText("还差" + min_cost + "元免费配送");
            btn_to_buy.setText("结     算");
        } else {
            if (total_goods_cash < Double.parseDouble(min_cost)) {
                total.setText("还差" + (new BigDecimal(min_cost).subtract(new BigDecimal(Double.toString(total_goods_cash)))) + "元免费配送");
            } else {
                total.setText("您的购物车总价：￥" + total_goods_cash + "元");
            }
        }
        badgeView.setText(total_goods_count + "");
        if ("0".equals(badgeView.getText().toString())) {
            badgeView.setVisibility(View.GONE);
        } else {
            badgeView.setVisibility(View.VISIBLE);
        }

        StatusBarUtil.StatusBarLightMode(getActivity());

        return view;
    }

    private class RemoveAllBR extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("removeAll".equals(intent.getAction())) {
                removeAll();
            } else if ("changedetail".equals(intent.getAction())) {
                total_goods_count = TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_count();
                total_goods_cash = TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_cash();
                shopCarList = TiaoshiApplication.diyShoppingCartJsonData.getGoodsList();
                Boolean isNull = false;
                for (int i = 0; i < shopCarList.size(); i++) {
                    if (goods_id.equals(shopCarList.get(i).getGoods_id())) {
                        goods_count = shopCarList.get(i).getGoods_count();
                        buy_count.setText(goods_count + "");
                        isNull = true;
                    }
                }
                if (!isNull) {
                    if (goods_count > 0) {
                        goods_count = 0;
                        buy_count.setVisibility(View.GONE);
                        btn_minus.setVisibility(View.GONE);
                    }
                } else {
                    buy_count.setVisibility(View.VISIBLE);
                    btn_minus.setVisibility(View.VISIBLE);
                }
                if (total_goods_cash < Double.parseDouble(min_cost)) {
                    total.setText("还差" + (new BigDecimal(min_cost).subtract(new BigDecimal(Double.toString(total_goods_cash)))) + "元免费配送");

                } else {
                    total.setText("您的购物车总价：￥" + total_goods_cash + "元");

                }
                if (total_goods_count == 0) {
                    badgeView.setVisibility(View.GONE);
                } else {
                    badgeView.setVisibility(View.VISIBLE);
                }
                badgeView.setText(total_goods_count + "");
            }
        }
    }

    private void initView() {
        bitmapUtils = new BitmapUtils(getActivity());
        badgeView = new BadgeView(getActivity());
        removeAllBR = new RemoveAllBR();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("removeAll");
        intentFilter.addAction("changedetail");
        getActivity().registerReceiver(removeAllBR, intentFilter);

        product_detail_rlBack = (RelativeLayout) view.findViewById(R.id.product_detail_rlBack);
        product_detail_ivStar1 = (ImageView) view.findViewById(R.id.product_detail_ivStar1);
        product_detail_ivStar2 = (ImageView) view.findViewById(R.id.product_detail_ivStar2);
        product_detail_ivStar3 = (ImageView) view.findViewById(R.id.product_detail_ivStar3);
        product_detail_ivStar4 = (ImageView) view.findViewById(R.id.product_detail_ivStar4);
        product_detail_ivStar5 = (ImageView) view.findViewById(R.id.product_detail_ivStar5);
        product_detail_scrollView = (ScrollView) view.findViewById(R.id.product_detail_scrollView);

        product_detail_tvOldPrice = (TextView) view.findViewById(R.id.product_detail_tvOldPrice);
        product_detail_tvSaleCount = (TextView) view.findViewById(R.id.product_detail_tvSaleCount);
        product_detail_rlStore = (RelativeLayout) view.findViewById(R.id.product_detail_rlStore);
        product_detail_ivShoppingTrolley = (ImageView) view.findViewById(R.id.product_detail_ivShoppingTrolley);
        badgeView.setTargetView(product_detail_ivShoppingTrolley);
        rl_buy = (RelativeLayout) view.findViewById(R.id.rl_buy);
        mPopWindow = new PopupWindow(shoppingTrolleyDialog, ViewGroup.LayoutParams.MATCH_PARENT, 600, true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        builder = new AlertDialog.Builder(getActivity()).setTitle("系统提示").setMessage("当前购物车还有其他商铺商品,请清空后再添加").setNegativeButton("清空", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(0);
                TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(0);
                TiaoshiApplication.diyShoppingCartJsonData.setShop_id(null);
                TiaoshiApplication.diyShoppingCartJsonData.setGoodsList(new LinkedList<DiyShoppingCartJsonData.GoodsListEntity>());
                Intent intent = new Intent("clearCount");
                getActivity().sendBroadcast(intent);
            }
        }).setPositiveButton("取消", null);
        btn_to_buy.setClickable(false);
    }

    private void initListener() {
        product_detail_rlBack.setOnClickListener(this);
        product_detail_rlStore.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btn_to_buy.setOnClickListener(this);
        hopping_trolley_tvClear.setOnClickListener(this);
        rl_buy.setOnClickListener(this);
        tv_pack_choose.setOnClickListener(this);
        store_product_juiceDelete.setOnClickListener(this);
    }

    private void initImgShow() {
        convenientBanner.setPages(new CBViewHolderCreator<BannerHolderView>() {
            @Override
            public BannerHolderView createHolder() {
                return new BannerHolderView();
            }
        }, images).setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer)
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
    }

    public class BannerHolderView implements CBPageAdapter.Holder<String> {

        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, String data) {

            //加载图片
            TiaoshiApplication.getGlobalBitmapUtils().display(imageView, data);
        }
    }


    private String shop_id;
    private String price;
    private String old_price;
    private String shop_name;
    private int star;
    private DiyShoppingCartJsonData.GoodsListEntity shopCarListEntity;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    GoodsDetailJsonData goodsDetailJsonData = (GoodsDetailJsonData) msg.obj;
                    bean = goodsDetailJsonData.getData().get(0);
                    if (goodsDetailJsonData != null) {
                        if ("SUCCESS".equals(goodsDetailJsonData.getRespCode())) {
                            goods_name = bean.getGoodsName();
                            name.setText(goods_name);
                            product_detail_tvSaleCount.setText("月销量:" + bean.getTotalSoldOut());
                            price = bean.getPromotePrice();
                            old_price = bean.getPrice();
                            status = bean.getSalesStatus() + "";
                            if (bean.getSalesUnit().equals("克") || bean.getSalesUnit().equals("斤")) {
                                tv_zhognliang.setText("重量:" + bean.getWeight() + bean.getWeightUnit());
                            } else if (bean.getSalesUnit().equals("小杯") || bean.getSalesUnit().equals("中杯") || bean.getSalesUnit().equals("大杯") || bean.getSalesUnit().equals("杯")) {
                                tv_zhognliang.setText("");
                            } else {
                                tv_zhognliang.setText("重量:约" + bean.getWeight() + bean.getWeightUnit());
                            }
                            star = Integer.parseInt(bean.getStar());
                            juicepack = bean.getPack().split(",");
                            if (!TextUtils.isEmpty(bean.getPack())) {
                                tv_pack_choose.setVisibility(View.VISIBLE);
                                btn_add.setVisibility(View.GONE);
                                if (goods_count != 0) {
                                    btn_minus.setVisibility(View.GONE);
                                    store_product_juiceDelete.setVisibility(View.VISIBLE);
                                }
                            } else {
                                tv_pack_choose.setVisibility(View.GONE);
                                btn_add.setVisibility(View.VISIBLE);
                            }

                            if ("0".equals(bean.getPromotePrice()) || TextUtils.isEmpty(bean.getPromotePrice())) {
                                tv_price.setText("￥" + bean.getPrice());
                                product_detail_tvOldPrice.setVisibility(View.GONE);
                                product_detail_view.setVisibility(View.GONE);
                            } else {
                                product_detail_tvOldPrice.setVisibility(View.VISIBLE);
                                product_detail_view.setVisibility(View.VISIBLE);
                                product_detail_tvOldPrice.setText("￥" + bean.getPrice());
                                tv_price.setText("￥" + bean.getPromotePrice());
                            }
                            if (!TextUtils.isEmpty(star + "")) {
                                if (star == 1) {
                                    product_detail_ivStar1.setImageResource(R.mipmap.rating_bar_list_focus);
                                    product_detail_ivStar2.setImageResource(R.mipmap.rating_bar_list_normal);
                                    product_detail_ivStar3.setImageResource(R.mipmap.rating_bar_list_normal);
                                    product_detail_ivStar4.setImageResource(R.mipmap.rating_bar_list_normal);
                                    product_detail_ivStar5.setImageResource(R.mipmap.rating_bar_list_normal);
                                } else if (star == 2) {
                                    product_detail_ivStar1.setImageResource(R.mipmap.rating_bar_list_focus);
                                    product_detail_ivStar2.setImageResource(R.mipmap.rating_bar_list_focus);
                                    product_detail_ivStar3.setImageResource(R.mipmap.rating_bar_list_normal);
                                    product_detail_ivStar4.setImageResource(R.mipmap.rating_bar_list_normal);
                                    product_detail_ivStar5.setImageResource(R.mipmap.rating_bar_list_normal);
                                } else if (star == 3) {
                                    product_detail_ivStar1.setImageResource(R.mipmap.rating_bar_list_focus);
                                    product_detail_ivStar2.setImageResource(R.mipmap.rating_bar_list_focus);
                                    product_detail_ivStar3.setImageResource(R.mipmap.rating_bar_list_focus);
                                    product_detail_ivStar4.setImageResource(R.mipmap.rating_bar_list_normal);
                                    product_detail_ivStar5.setImageResource(R.mipmap.rating_bar_list_normal);
                                } else if (star == 4) {
                                    product_detail_ivStar1.setImageResource(R.mipmap.rating_bar_list_focus);
                                    product_detail_ivStar2.setImageResource(R.mipmap.rating_bar_list_focus);
                                    product_detail_ivStar3.setImageResource(R.mipmap.rating_bar_list_focus);
                                    product_detail_ivStar4.setImageResource(R.mipmap.rating_bar_list_focus);
                                    product_detail_ivStar5.setImageResource(R.mipmap.rating_bar_list_normal);
                                } else if (star == 5) {
                                    product_detail_ivStar1.setImageResource(R.mipmap.rating_bar_list_focus);
                                    product_detail_ivStar2.setImageResource(R.mipmap.rating_bar_list_focus);
                                    product_detail_ivStar3.setImageResource(R.mipmap.rating_bar_list_focus);
                                    product_detail_ivStar4.setImageResource(R.mipmap.rating_bar_list_focus);
                                    product_detail_ivStar5.setImageResource(R.mipmap.rating_bar_list_focus);
                                } else {
                                    product_detail_ivStar1.setImageResource(R.mipmap.rating_bar_list_normal);
                                    product_detail_ivStar2.setImageResource(R.mipmap.rating_bar_list_normal);
                                    product_detail_ivStar3.setImageResource(R.mipmap.rating_bar_list_normal);
                                    product_detail_ivStar4.setImageResource(R.mipmap.rating_bar_list_normal);
                                    product_detail_ivStar5.setImageResource(R.mipmap.rating_bar_list_normal);
                                }
                            }
                            if (is_new.length() > 0) {
                                String[] str = bean.getTags().get(0).split("，");
                                if (str.length == 1 && !str[0].equals("")) {
                                    product_detail_ivGift.setText(str[0]);
                                    product_detail_ivGift.setVisibility(View.VISIBLE);
                                } else if (str.length == 2) {
                                    product_detail_ivGift.setText(str[0]);
                                    product_detail_ivGift.setVisibility(View.VISIBLE);
                                    product_detail_ivChaoZhi.setVisibility(View.VISIBLE);
                                    product_detail_ivChaoZhi.setText(str[1]);
                                } else if (str.length == 3) {
                                    product_detail_ivGift.setText(str[0]);
                                    product_detail_ivGift.setVisibility(View.VISIBLE);
                                    product_detail_ivChaoZhi.setVisibility(View.VISIBLE);
                                    product_detail_ivChaoZhi2.setVisibility(View.VISIBLE);
                                    product_detail_ivChaoZhi.setText(str[1]);
                                    product_detail_ivChaoZhi2.setText(str[2]);
                                }
                            }
                            if (TextUtils.isEmpty(bean.getSalesNum()) || bean.getSalesNum().equals("1")) {
                                count_and_unit.setText("元/" + bean.getSalesUnit());
                            } else {
                                count_and_unit.setText("元/" + bean.getSalesNum() + bean.getSalesUnit());
                            }
                            detail.setText(bean.getDescription());
                            if (!TextUtils.isEmpty(bean.getGoodsRotateImgs())) {
                                String[] a = bean.getGoodsRotateImgs().split(",");
                                for (int i = 0; i < a.length; i++) {
                                    images.add(a[i]);
                                }
                            }
                            if (!TextUtils.isEmpty(bean.getGoodsDetialImgs())) {
                                String[] pics = bean.getGoodsDetialImgs().split(",");
                                comment_pic.removeAllViews();
                                for (int i = 0; i < pics.length; i++) {
                                    ImageView img = new ImageView(getContext());
                                    img.setScaleType(ImageView.ScaleType.FIT_XY);
                                    img.setAdjustViewBounds(true);
                                    Log.e("pic", pics[0]);
                                    int width = (int) ((DensityUtil.getWidthInPx(getContext())));
                                    img.setMaxWidth(width);
                                    img.setMaxHeight(width * 10);
                                    TiaoshiApplication.getGlobalBitmapUtils().display(img, pics[i]);
                                    if (pics[i].length() > 10) {
                                        comment_pic.addView(img);
                                    }
                                }
                            }
                            //添加图片
                            if ("CLOSE".equals(status)) {
                                Toast.makeText(getActivity(), "当前商铺已经打烊，暂时无法购买", Toast.LENGTH_SHORT).show();
                            }
                            initImgShow();
                        }
                    }
                    break;
                case 2:
                    ++total_goods_count;
                    ++goods_count;
                    if (total_goods_count == 1) {
                        badgeView.setVisibility(View.VISIBLE);
                    }
                    badgeView.setText(total_goods_count + "");
                    int position = (int) msg.obj;
                    shopCarListEntity = shopCarList.get(position);
//                    int count=shopCarListEntity.getGoods_count();
//                    int count=goods_sc_count+1;
                    goods_sc_count += 1;
                    if (goods_id.equals(shopCarListEntity.getGoods_id())) {
                        buy_count.setText(goods_sc_count + "");
                    }

                    String money3 = shopCarListEntity.getPrice();
                    BigDecimal a3 = new BigDecimal(Double.toString(total_goods_cash));
                    BigDecimal b3 = new BigDecimal(money3);
                    total_goods_cash = a3.add(b3).doubleValue();
                    a3 = new BigDecimal(Double.toString(total_goods_cash));
                    if (total_goods_cash < Double.parseDouble(min_cost)) {
                        total.setText("还差￥" + (new BigDecimal(min_cost).subtract(a3)) + "元免费配送");
                    } else {
                        total.setText("您的购物车总价：￥" + total_goods_cash + "元");
                    }
                    TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(total_goods_count);
                    TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(total_goods_cash);
                    getActivity().sendBroadcast(intent1);
                    getActivity().sendBroadcast(intent2);

                    break;
                case 3:
                    --total_goods_count;
                    if (total_goods_count <= 0) {
                        badgeView.setVisibility(View.GONE);
                    }
                    badgeView.setText(total_goods_count + "");
                    int position1 = (int) msg.obj;
                    shopCarListEntity = shopCarList.get(position1);
                    int count1 = shopCarListEntity.getGoods_count();
//                    Log.e("count1",count1+"");
                    if (count1 == 0) {
                        shopCarList.remove(position1);
                    }
                    goods_sc_count -= 1;
                    if (goods_sc_count == 0) {
                        if (goods_id.equals(shopCarListEntity.getGoods_id())) {
                            buy_count.setVisibility(View.GONE);
                            btn_minus.setVisibility(View.GONE);
                            store_product_juiceDelete.setVisibility(View.GONE);

                        }
                    } else {
                        if (goods_id.equals(shopCarListEntity.getGoods_id())) {
                            buy_count.setText(goods_sc_count + "");
                        }
                    }
                    --goods_count;
                    String money4 = shopCarListEntity.getPrice();
                    BigDecimal a4 = new BigDecimal(Double.toString(total_goods_cash));
                    BigDecimal b4 = new BigDecimal(money4);
                    total_goods_cash = a4.subtract(b4).doubleValue();
                    a4 = new BigDecimal(Double.toString(total_goods_cash));
                    if (total_goods_cash < Double.parseDouble(min_cost)) {
                        total.setText("还差￥" + (new BigDecimal(min_cost).subtract(a4)) + "元免费配送");
                    } else {
                        total.setText("您的购物车总价：￥" + total_goods_cash + "元");
                    }
                    if (shopCarList.size() == 0) {
                        TiaoshiApplication.diyShoppingCartJsonData.setShop_id(null);
                    }
                    TiaoshiApplication.diyShoppingCartJsonData.setGoodsList(shopCarList);
                    TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(total_goods_count);
                    TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(total_goods_cash);
                    getActivity().sendBroadcast(intent1);
                    getActivity().sendBroadcast(intent2);

                    shopDetailListAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    public void setListViewHeightBasedOnChildren(ListView listView, int desiredWidth) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, 0);
            totalHeight += listItem.getMeasuredHeight();
        }


        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.product_detail_rlBack:
                getActivity().finish();
                break;
            case R.id.product_detail_rlStore:
                if (getActivity().getIntent().getStringExtra("what") == null) {
                    Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                    intent.putExtra("shop_id", shop_id);
                    intent.putExtra("min_cost", min_cost);
//                    intent.putExtra("tag", "tag");
                    startActivity(intent);
                } else {
                    getActivity().finish();
                }
                break;
            case R.id.btn_add:
                if (TiaoshiApplication.diyShoppingCartJsonData.getShop_id() != null) {
                    if (!TiaoshiApplication.diyShoppingCartJsonData.getShop_id().equals(shop_id)) {
                        builder.create().show();
                        return;
                    }
                } else {
                    TiaoshiApplication.diyShoppingCartJsonData.setShop_id(shop_id);
                }
                ++goods_count;
                ++total_goods_count;
                goods_sc_count += 1;
                String money1 = "";
                if ("0".equals(price) || TextUtils.isEmpty(price)) {
                    money1 = old_price;
                } else {
                    money1 = price;
                }
                BigDecimal a1 = new BigDecimal(Double.toString(total_goods_cash));
                BigDecimal b1 = new BigDecimal(money1);
                total_goods_cash = a1.add(b1).doubleValue();
                a1 = new BigDecimal(Double.toString(total_goods_cash));
                if (total_goods_cash < Double.parseDouble(min_cost)) {
                    total.setText("还差￥" + (new BigDecimal(min_cost).subtract(a1)) + "元免费配送");
                } else {
                    total.setText("您的购物车总价：￥" + total_goods_cash + "元");
                }
                if (shopCarList.size() == 0) {
                    DiyShoppingCartJsonData.GoodsListEntity goodsListEntity = new DiyShoppingCartJsonData.GoodsListEntity();
                    goodsListEntity.setGoods_id(goods_id);
                    goodsListEntity.setGoods_name(goods_name);
                    goodsListEntity.setGoods_price(b1.multiply(new BigDecimal(goods_count + "")).toString() + "元");
                    goodsListEntity.setPrice(money1);
                    goodsListEntity.setTypename(typename);
                    goodsListEntity.setGoods_count(goods_count);
                    shopCarList.add(goodsListEntity);
                } else {
                    boolean isContained = false;
                    for (int i = 0; i < shopCarList.size(); i++) {
                        if (goods_id.equals(shopCarList.get(i).getGoods_id())) {
                            DiyShoppingCartJsonData.GoodsListEntity goodsListEntity = shopCarList.get(i);
                            goodsListEntity.setGoods_id(goods_id);
                            goodsListEntity.setGoods_name(goods_name);
                            goodsListEntity.setGoods_price(b1.multiply(new BigDecimal(goods_count + "")).toString() + "元");
                            goodsListEntity.setPrice(money1);
                            goodsListEntity.setGoods_count(goods_count);
                            isContained = true;
                        }
                    }
                    if (!isContained) {
                        DiyShoppingCartJsonData.GoodsListEntity goodsListEntity = new DiyShoppingCartJsonData.GoodsListEntity();
                        goodsListEntity.setGoods_id(goods_id);
                        goodsListEntity.setGoods_name(goods_name);
                        goodsListEntity.setGoods_price(b1.multiply(new BigDecimal(goods_count + "")).toString() + "元");
                        goodsListEntity.setPrice(money1);
                        goodsListEntity.setTypename(typename);

                        goodsListEntity.setGoods_count(goods_count);
                        shopCarList.add(goodsListEntity);
                    }
                }
                TiaoshiApplication.diyShoppingCartJsonData.setGoodsList(shopCarList);
                TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(total_goods_count);
                TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(total_goods_cash);

                if (total_goods_count == 1) {
                    badgeView.setVisibility(View.VISIBLE);
                }
                badgeView.setText(total_goods_count + "");
                if (goods_count > 0) {
                    buy_count.setVisibility(View.VISIBLE);
                    btn_minus.setVisibility(View.VISIBLE);
                }
                buy_count.setText(goods_count + "");
                getActivity().sendBroadcast(intent1);
                getActivity().sendBroadcast(intent2);
                break;

            case R.id.btn_minus:
                --goods_count;
                --total_goods_count;
                goods_sc_count -= 1;
                if (total_goods_count == 0) {
                    badgeView.setVisibility(View.GONE);
                }
                badgeView.setText(total_goods_count + "");
                String money2 = "";
                if ("0".equals(price) || TextUtils.isEmpty(price)) {
                    money2 = old_price;
                } else {
                    money2 = price;
                }
                BigDecimal a2 = new BigDecimal(Double.toString(total_goods_cash));
                BigDecimal b2 = new BigDecimal(money2);
                total_goods_cash = a2.subtract(b2).doubleValue();
                a2 = new BigDecimal(Double.toString(total_goods_cash));
                if (total_goods_cash < Double.parseDouble(min_cost)) {
                    total.setText("还差￥" + (new BigDecimal(min_cost).subtract(a2)) + "元免费配送");
                } else {
                    total.setText("您的购物车总价：￥" + total_goods_cash + "元");
                }
                if (goods_count == 0) {
                    for (int i = 0; i < shopCarList.size(); i++) {
                        if (goods_id.equals(shopCarList.get(i).getGoods_id())) {
                            shopCarList.remove(i);
                        }
                    }
                } else {
                    for (int i = 0; i < shopCarList.size(); i++) {
                        if (goods_id.equals(shopCarList.get(i).getGoods_id())) {
                            DiyShoppingCartJsonData.GoodsListEntity goodsListEntity = shopCarList.get(i);
                            goodsListEntity.setGoods_id(goods_id);
                            goodsListEntity.setGoods_name(goods_name);
                            goodsListEntity.setGoods_price(b2.multiply(new BigDecimal(goods_count + "")).toString() + "元");
                            goodsListEntity.setPrice(money2);
                            goodsListEntity.setGoods_count(goods_count);
                        }
                    }
                }
                if (shopCarList.size() == 0) {
                    TiaoshiApplication.diyShoppingCartJsonData.setShop_id(null);
                }
                TiaoshiApplication.diyShoppingCartJsonData.setGoodsList(shopCarList);
                TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(total_goods_count);
                TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(total_goods_cash);
                if (goods_count == 0) {
                    buy_count.setVisibility(View.GONE);
                    btn_minus.setVisibility(View.GONE);
                }
                buy_count.setText(goods_count + "");
                getActivity().sendBroadcast(intent1);
                getActivity().sendBroadcast(intent2);


                break;

            case R.id.rl_buy:
                if (shopCarList.size() > 0) {
                    if (mPopWindow.isShowing()) {
                        mPopWindow.dismiss();
                    } else {
                        aboutPopWindows(v);
                    }
                }
                break;

            case R.id.btn_to_buy:
                if (TiaoshiApplication.isLogin) {
                    if ("CLOSE".equals(status)) {
                        Toast.makeText(getActivity(), "当前商铺已经打烊，暂时无法购买", Toast.LENGTH_SHORT).show();
                    } else if ("OPEN".equals(status)) {
                        if (shopCarList.size() != 0) {
                            toCommitOrder();
                        }
                    }
                } else {
                    showToast("您尚未登录,请先登录");
                    toLogin();
                }
                break;
            case R.id.hopping_trolley_tvClear:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                builder2.setTitle("清空购物车").setMessage("确定清空购物车?").setPositiveButton("取消", null).setNegativeButton("清空", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeAll();
                    }
                }).create().show();
                break;
            case R.id.tv_pack_choose:
                final Dialog finalDialog;
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.choose_goods_dialog, null);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rg_chooseWeight);
                TextView tv_dismiss_pack = (TextView) view.findViewById(R.id.tv_dismiss_pack);
                TextView tv_addjuice = (TextView) view.findViewById(R.id.tv_addjuice);
                TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
                tv_title.setText(bean.getGoodsName());
                RadioButton rb1 = (RadioButton) view.findViewById(R.id.rb1);
                RadioButton rb2 = (RadioButton) view.findViewById(R.id.rb2);
                RadioButton rb3 = (RadioButton) view.findViewById(R.id.rb3);
                switch (juicepack.length) {
                    case 1:
                        rb1.setVisibility(View.VISIBLE);
                        rb1.setText(juicepack[0]);
                        break;
                    case 2:
                        rb1.setVisibility(View.VISIBLE);
                        rb2.setVisibility(View.VISIBLE);
                        rb1.setText(juicepack[0]);
                        rb2.setText(juicepack[1]);
                        break;
                    case 3:
                        rb1.setVisibility(View.VISIBLE);
                        rb2.setVisibility(View.VISIBLE);
                        rb3.setVisibility(View.VISIBLE);
                        rb1.setText(juicepack[0]);
                        rb2.setText(juicepack[1]);
                        rb3.setText(juicepack[2]);
                        break;
                }

                radioGroup.check(R.id.rb1);
                type = 1;
                typename = juicepack[0];
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.rb1:
                                type = 1;
                                typename = juicepack[0];
                                break;
                            case R.id.rb2:
                                type = 2;
                                typename = juicepack[1];
                                break;
                            case R.id.rb3:
                                type = 3;
                                typename = juicepack[2];
                                break;
                        }
                    }
                });
                builder1.setCancelable(false);
                builder1.setView(view);
                finalDialog = builder1.show();
                tv_dismiss_pack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finalDialog.dismiss();
                    }
                });
                tv_addjuice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TiaoshiApplication.diyShoppingCartJsonData.getShop_id() != null) {
                            if (!TiaoshiApplication.diyShoppingCartJsonData.getShop_id().equals(shop_id)) {
                                builder.create().show();
                                return;
                            }
                        } else {
                            TiaoshiApplication.diyShoppingCartJsonData.setShop_id(shop_id);
                        }
                        ++goods_count;
                        ++total_goods_count;
                        goods_sc_count += 1;
                        String money1 = "";
                        if ("0".equals(price) || TextUtils.isEmpty(price)) {
                            money1 = old_price;
                        } else {
                            money1 = price;
                        }
                        BigDecimal a1 = new BigDecimal(Double.toString(total_goods_cash));
                        BigDecimal b1 = new BigDecimal(money1);
                        total_goods_cash = a1.add(b1).doubleValue();
                        a1 = new BigDecimal(Double.toString(total_goods_cash));
                        if (total_goods_cash < Double.parseDouble(min_cost)) {
                            total.setText("还差￥" + (new BigDecimal(min_cost).subtract(a1)) + "元免费配送");
                        } else {
                            total.setText("您的购物车总价：￥" + total_goods_cash + "元");
                        }
                        if (shopCarList.size() == 0) {
                            DiyShoppingCartJsonData.GoodsListEntity goodsListEntity = new DiyShoppingCartJsonData.GoodsListEntity();
                            goodsListEntity.setGoods_id(goods_id);
                            if (type == 0) {
                                goodsListEntity.setGoods_name(goods_name);
                            } else {
                                goodsListEntity.setGoods_name(goods_name + "-" + typename);
                            }
                            goodsListEntity.setTypename(typename);
                            goodsListEntity.setGoods_price(b1.multiply(new BigDecimal(goods_count + "")).toString() + "元");
                            goodsListEntity.setPrice(money1);
                            goodsListEntity.setGoods_count(goods_count);
                            shopCarList.add(goodsListEntity);
                        } else {
                            boolean isContained = false;
                            for (int i = 0; i < shopCarList.size(); i++) {
                                if (goods_id.equals(shopCarList.get(i).getGoods_id()) && typename.equals(shopCarList.get(i).getTypename())) {
                                    DiyShoppingCartJsonData.GoodsListEntity goodsListEntity = shopCarList.get(i);
                                    goodsListEntity.setGoods_id(goods_id);
                                    goodsListEntity.setGoods_price(b1.multiply(new BigDecimal(shopCarList.get(i).getGoods_count() + 1 + "")).toString() + "元");
                                    goodsListEntity.setPrice(money1);
                                    goodsListEntity.setGoods_count(shopCarList.get(i).getGoods_count() + 1);
                                    isContained = true;
                                }
                            }
                            if (!isContained) {
                                DiyShoppingCartJsonData.GoodsListEntity goodsListEntity = new DiyShoppingCartJsonData.GoodsListEntity();
                                goodsListEntity.setGoods_id(goods_id);
                                if (type == 0) {
                                    goodsListEntity.setGoods_name(goods_name);
                                } else {
                                    goodsListEntity.setGoods_name(goods_name + "-" + typename);
                                }
                                goodsListEntity.setGoods_price(b1.multiply(new BigDecimal(1 + "")).toString() + "元");
                                goodsListEntity.setPrice(money1);
                                goodsListEntity.setTypename(typename);
                                goodsListEntity.setGoods_count(1);
                                shopCarList.add(goodsListEntity);
                            }
                        }
                        TiaoshiApplication.diyShoppingCartJsonData.setGoodsList(shopCarList);
                        TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(total_goods_count);
                        TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(total_goods_cash);

                        if (total_goods_count == 1) {
                            badgeView.setVisibility(View.VISIBLE);
                        }
                        badgeView.setText(total_goods_count + "");
                        if (goods_count > 0) {
                            buy_count.setVisibility(View.VISIBLE);
                            store_product_juiceDelete.setVisibility(View.VISIBLE);
                        }
                        buy_count.setText(goods_count + "");
                        getActivity().sendBroadcast(intent1);
                        getActivity().sendBroadcast(intent2);
                        finalDialog.dismiss();
                    }
                });
                break;
            case R.id.store_product_juiceDelete:
                ToastUtil.showShort(getActivity(), "多规格商品只能到购物车删除哦");
                break;
        }
    }

    private void removeAll() {
        shopCarList.clear();
        if (shopDetailListAdapter != null) {
            shopDetailListAdapter.notifyDataSetChanged();
        }
        mPopWindow.dismiss();
        total_goods_count = 0;
        total_goods_cash = 0;
        goods_count = 0;
        buy_count.setVisibility(View.GONE);
        btn_minus.setVisibility(View.GONE);
        store_product_juiceDelete.setVisibility(View.GONE);
        badgeView.setText(total_goods_count + "");
        badgeView.setVisibility(View.GONE);
        total.setText("还差￥" + min_cost + "元免费配送");
        TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(0);
        TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(0);
        TiaoshiApplication.diyShoppingCartJsonData.setGoodsList(new LinkedList<DiyShoppingCartJsonData.GoodsListEntity>());
        TiaoshiApplication.diyShoppingCartJsonData.setShop_id(null);
        Intent intent = new Intent("clearCount");
        getActivity().sendBroadcast(intent);
        Intent intent2 = new Intent("clearList");
        getActivity().sendBroadcast(intent2);
    }

    private void aboutPopWindows(View v) {
        shopDetailListAdapter = new ShopDetailListAdapter(shopCarList, getActivity(), handler);
        shopping_trolley_lv.setAdapter(shopDetailListAdapter);
        shopDetailListAdapter.notifyDataSetChanged();
        mPopWindow.setAnimationStyle(R.style.AnimBottom);
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        mPopWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1] - mPopWindow.getHeight());
    }


    private void toLogin() {
        startActivity(FCActivity.getFCActivityIntent(getActivity(), PhoneLoginFragment.class));
    }

    private void toCommitOrder() {
        Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
        intent.putExtra("shop_name", shop_name);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(removeAllBR);
        convenientBanner.stopTurning();
        super.onDestroy();
    }
}

