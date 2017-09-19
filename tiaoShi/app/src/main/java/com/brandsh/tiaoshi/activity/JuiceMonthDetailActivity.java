package com.brandsh.tiaoshi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.fragment.PhoneLoginFragment;
import com.brandsh.tiaoshi.model.DiyShoppingCartJsonData;
import com.brandsh.tiaoshi.model.GoodsDetailJsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.DensityUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mingle.widget.ShapeLoadingDialog;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
public class JuiceMonthDetailActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.banner_ivBack)
    ImageView banner_ivBack;
    @ViewInject(R.id.comment_pic)
    private LinearLayout comment_pic;
    @ViewInject(R.id.tv_zhifu)
    private TextView tv_zhifu;
    GoodsDetailJsonData.DataBean bean;
    private List<DiyShoppingCartJsonData.GoodsListEntity> shopCarList;
    //加载动画
    private ShapeLoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juicemonth_detail);
        //沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);
        init();
    }

    private void init() {
        loadingDialog = ProgressHUD.show(this, "努力加载中...");
        banner_ivBack.setOnClickListener(this);
        tv_zhifu.setOnClickListener(this);
        HashMap requestMap = new HashMap();
        requestMap.put("goodsId", getIntent().getStringExtra("goodsId"));
        requestMap.put("shopId", getIntent().getStringExtra("shopId"));
        requestMap.put("actReq", SignUtil.getRandom());
        requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(requestMap);
        requestMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.PRODUCT_DETAIL, requestMap, new MyCallBack(1, this, new GoodsDetailJsonData(), handler));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner_ivBack:
                finish();
                break;
            case R.id.tv_zhifu:
                if (!TiaoshiApplication.isLogin) {
                    startActivity(FCActivity.getFCActivityIntent(this, PhoneLoginFragment.class));
                    break;
                } else {
                    TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(0);
                    TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(0);
                    TiaoshiApplication.diyShoppingCartJsonData.setGoodsList(new LinkedList<DiyShoppingCartJsonData.GoodsListEntity>());
                    TiaoshiApplication.diyShoppingCartJsonData.setShop_id(null);
                    //装载数据
                    shopCarList = new LinkedList<>();
                    DiyShoppingCartJsonData.GoodsListEntity goodsListEntity = new DiyShoppingCartJsonData.GoodsListEntity();
                    goodsListEntity.setGoods_id(bean.getGoodsId());
                    goodsListEntity.setGoods_name(bean.getGoodsName());
                    String price;
                    if (TextUtils.isEmpty(bean.getPromotePrice()) || bean.getPromotePrice().equals("0")) {
                        price = bean.getPrice();
                    } else {
                        price = bean.getPromotePrice();
                    }
                    goodsListEntity.setGoods_price(price);
                    goodsListEntity.setPrice(price);
                    goodsListEntity.setTypename("");
                    goodsListEntity.setGoods_count(1);
                    shopCarList.add(goodsListEntity);
                    TiaoshiApplication.diyShoppingCartJsonData.setShop_id(bean.getShopId());
                    TiaoshiApplication.diyShoppingCartJsonData.setGoodsList(shopCarList);
                    TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(1);
                    TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(Double.parseDouble(price));
                    Intent intent = new Intent(this, ConfirmOrderActivity.class);
                    intent.putExtra("shop_name", "挑食网");
                    intent.putExtra("ORDER_TYPE", "MONTH");
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!TextUtils.isEmpty(TiaoshiApplication.diyShoppingCartJsonData.getShop_id()) && TiaoshiApplication.diyShoppingCartJsonData.getShop_id().equals(bean.getShopId())) {
            TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(0);
            TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(0);
            TiaoshiApplication.diyShoppingCartJsonData.setGoodsList(new LinkedList<DiyShoppingCartJsonData.GoodsListEntity>());
            TiaoshiApplication.diyShoppingCartJsonData.setShop_id(null);
            Intent intent = new Intent();
            intent.setAction("clearCount");
            this.sendBroadcast(intent);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    GoodsDetailJsonData goodsDetailJsonData = (GoodsDetailJsonData) msg.obj;
                    bean = goodsDetailJsonData.getData().get(0);
                    if (goodsDetailJsonData != null) {
                        if ("SUCCESS".equals(goodsDetailJsonData.getRespCode())) {
                            if (!TextUtils.isEmpty(bean.getGoodsDetialImgs())) {
                                String[] pics = bean.getGoodsDetialImgs().split(",");
                                comment_pic.removeAllViews();
                                for (int i = 0; i < pics.length; i++) {
                                    ImageView img = new ImageView(JuiceMonthDetailActivity.this);
                                    img.setScaleType(ImageView.ScaleType.FIT_XY);
                                    img.setAdjustViewBounds(true);
                                    Log.e("pic", pics[0]);
                                    int width = (int) ((DensityUtil.getWidthInPx(JuiceMonthDetailActivity.this)));
                                    img.setMaxWidth(width);
                                    img.setMaxHeight(width * 10);
                                    TiaoshiApplication.getGlobalBitmapUtils().display(img, pics[i]);
                                    if (pics[i].length() > 10) {
                                        comment_pic.addView(img);
                                    }
                                }
                            }
                        }
                    }
                    break;
            }
        }
    };
}
