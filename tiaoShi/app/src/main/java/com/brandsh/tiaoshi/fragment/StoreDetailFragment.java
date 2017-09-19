package com.brandsh.tiaoshi.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.activity.FCActivity;
import com.brandsh.tiaoshi.activity.StoreAddressMapActivity;
import com.brandsh.tiaoshi.adapter.JuiceImgAdapter;
import com.brandsh.tiaoshi.adapter.StoreDetailDiscountItemAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.GuanZhuJsonData;
import com.brandsh.tiaoshi.model.GuanZhuStatusJsonData;
import com.brandsh.tiaoshi.model.StoreDetailJsonData1;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.widget.ProductDetailImgListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by libokang on 15/11/2.
 */
public class StoreDetailFragment extends BaseFragment implements View.OnClickListener {
    @ViewInject(R.id.img)
    private ImageView img;
    @ViewInject(R.id.name)
    private TextView name;
    @ViewInject(R.id.store_detail_ivTag)
    private ImageView store_detail_ivTag;
    @ViewInject(R.id.sale_count)
    private TextView sale_count;
    @ViewInject(R.id.store_detail_tvMoney)
    private TextView store_detail_tvMoney;
    @ViewInject(R.id.rl_address)
    private RelativeLayout rl_address;
    @ViewInject(R.id.address)
    private TextView address;
    @ViewInject(R.id.rl_tel)
    private RelativeLayout rl_tel;
    @ViewInject(R.id.tel)
    private TextView tel;
    @ViewInject(R.id.store_detail_tvIntroduce)
    private TextView store_detail_tvIntroduce;
    @ViewInject(R.id.store_detail_ivGuanZhu)
    private ImageView store_detail_ivGuanZhu;
    @ViewInject(R.id.store_detail_tvGuanZhu)
    private TextView store_detail_tvGuanZhu;
    @ViewInject(R.id.store_detail_llGuanZhu)
    private LinearLayout store_detail_llGuanZhu;
    @ViewInject(R.id.store_detail_lv)
    private ProductDetailImgListView store_detail_lv;
    @ViewInject(R.id.store_detail_view)
    private View store_detail_view;
    @ViewInject(R.id.open_time)
    private TextView open_time;
    @ViewInject(R.id.tv_qibujia)
    private TextView tv_qibujia;

    @ViewInject(R.id.gv_img)
    private GridView gv_img;
    @ViewInject(R.id.tv_juli)
    private TextView tv_juli;

    private boolean isGuanZhu;
    private HashMap<String, String> requestMap;
    private HashMap<String, String> guanzhuMap;
    private HashMap<String, String> guanzhuStatusMap;
    private BitmapUtils bitmapUtils;
    private List<StoreDetailJsonData1.DataBean.ShopPreferentialBean> resList;
    private StoreDetailDiscountItemAdapter storeDetailDiscountItemAdapter;
    private String shop_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.store_detail_fragment, null);
        ViewUtils.inject(this, view);

        initData();

        initListener();

        return view;
    }
    private void initData() {
        bitmapUtils = TiaoshiApplication.getGlobalBitmapUtils();
        resList = new LinkedList<>();
        storeDetailDiscountItemAdapter = new StoreDetailDiscountItemAdapter(getActivity(), resList);
        store_detail_lv.setAdapter(storeDetailDiscountItemAdapter);

        requestMap = new HashMap<>();
        shop_id = getActivity().getIntent().getStringExtra("shop_id");
        requestMap.put("shopId", shop_id);

        if (TextUtils.isEmpty(TiaoshiApplication.Lat)){
            requestMap.put("lat", "31.312564");
            requestMap.put("lng", "121.487778");
        }else {
            if (TiaoshiApplication.isLogin){
                requestMap.put("userId", TiaoshiApplication.globalUserId);
            }
            requestMap.put("lat", TiaoshiApplication.Lat);
            requestMap.put("lng", TiaoshiApplication.Lng);
        }
        requestMap.put("actReq", "123456");
        requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(requestMap);
        requestMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.STORE_DETAIL, requestMap, new MyCallBack(1, getActivity(), new StoreDetailJsonData1(), handler));
    }

    private void initListener() {
        rl_address.setOnClickListener(this);
        rl_tel.setOnClickListener(this);
        store_detail_llGuanZhu.setOnClickListener(this);
    }

    private void call() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("联系商户:").setMessage(phoneNumber);
        builder.setPositiveButton("拨打", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private String lat;
    private String lng;
    private String shop_name;
    private String shop_address;
    private String phoneNumber;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    StoreDetailJsonData1 storeDetailJsonData = (StoreDetailJsonData1) msg.obj;
                    if (storeDetailJsonData != null) {
                        if (storeDetailJsonData.getRespCode().equals("SUCCESS")) {
                            lat = storeDetailJsonData.getData().getLat() + "";
                            lng = storeDetailJsonData.getData().getLng() + "";
                            if (storeDetailJsonData.getData().getShopPreferential().size()==0) {
                                store_detail_ivTag.setVisibility(View.GONE);
                            } else if (storeDetailJsonData.getData().getShopPreferential().size()>0) {
                                store_detail_ivTag.setVisibility(View.VISIBLE);
                            }
                            shop_name = storeDetailJsonData.getData().getName();
                            shop_address = storeDetailJsonData.getData().getAddress();
                            phoneNumber = storeDetailJsonData.getData().getServiceTel();
                            bitmapUtils.display(img, storeDetailJsonData.getData().getIcon());
                            name.setText(storeDetailJsonData.getData().getName());
                            tv_juli.setText("商家配送("+storeDetailJsonData.getData().getDeliveryPriceConf().getDistanceMin()+"km以内)");
                            tv_qibujia.setText(storeDetailJsonData.getData().getDeliveryPriceConf().getDistance()+"元");
                            sale_count.setText("销量:" + storeDetailJsonData.getData().getOrderTotal());
                            store_detail_tvMoney.setText( storeDetailJsonData.getData().getFreeSend() + "元");
                            address.setText("地址："+shop_address);
                            tel.setText("电话："+storeDetailJsonData.getData().getServiceTel());
                            open_time.setText("营业时间：" + storeDetailJsonData.getData().getOpenTime() + "  ---  " + storeDetailJsonData.getData().getCloseTime());
                            store_detail_tvIntroduce.setText(storeDetailJsonData.getData().getDescription());
                            if (storeDetailJsonData.getData().getShopPreferential().size()>0) {
                                resList.addAll(storeDetailJsonData.getData().getShopPreferential());
                                store_detail_view.setVisibility(View.VISIBLE);
                                store_detail_lv.setVisibility(View.VISIBLE);
                                storeDetailDiscountItemAdapter.notifyDataSetChanged();
                            } else if (storeDetailJsonData.getData().getShopPreferential().size()==0) {
                                store_detail_view.setVisibility(View.GONE);
                                store_detail_lv.setVisibility(View.GONE);
                            }
                            if (storeDetailJsonData.getData().getFollowStatus().equals("no")) {
                                isGuanZhu = false;
                                store_detail_ivGuanZhu.setImageResource(R.mipmap.heart);
                                store_detail_tvGuanZhu.setText("关注");
                            } else {
                                isGuanZhu = true;
                                store_detail_ivGuanZhu.setImageResource(R.mipmap.heart1);
                                store_detail_tvGuanZhu.setText("取消");
                            }
                            List list=new ArrayList();
                            list.add(storeDetailJsonData.getData().getIcon());
                            JuiceImgAdapter adapter=new JuiceImgAdapter(getContext(),list);
                            gv_img.setAdapter(adapter);
                        }
                    }
                    break;
                case 2:
                    GuanZhuJsonData guanZhuJsonData = (GuanZhuJsonData) msg.obj;
                    if (guanZhuJsonData != null) {
                        if (guanZhuJsonData.getRespCode().equals("SUCCESS")) {
                            if (guanZhuJsonData.getData().getStatus().equals("1")) {
                                isGuanZhu = true;
                                Toast.makeText(getActivity(), "关注店铺成功", Toast.LENGTH_SHORT).show();
                                store_detail_ivGuanZhu.setImageResource(R.mipmap.heart1);
                                store_detail_tvGuanZhu.setText("取消");
                            } else if (guanZhuJsonData.getData().getStatus() .equals("0")) {
                                isGuanZhu = false;
                                Toast.makeText(getActivity(), "取消关注成功", Toast.LENGTH_SHORT).show();
                                store_detail_ivGuanZhu.setImageResource(R.mipmap.heart);
                                store_detail_tvGuanZhu.setText("关注");
                            }
                        }
                    }
                    break;

            }
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_address:
                Intent intent = new Intent(getActivity(), StoreAddressMapActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.putExtra("shop_name", shop_name);
                intent.putExtra("shop_address", shop_address.replaceAll(" ", ""));
                if (!"".equals(lat) && !"".equals(lng)) {
                    startActivity(intent);
                } else {
                    showToast("当前店铺还没上传具体地址");
                }
                break;
            case R.id.rl_tel:
                if ("".equals(phoneNumber)) {
                    showToast("商家暂时还没有上传电话号码");
                } else {
                    call();
                }
                break;
            case R.id.store_detail_llGuanZhu:
                if (!TiaoshiApplication.isLogin) {
                    Toast.makeText(getActivity(), "您尚未登陆,请先登陆", Toast.LENGTH_SHORT).show();
                    startActivity(FCActivity.getFCActivityIntent(getActivity(), PhoneLoginFragment.class));
                    return;
                }
                guanzhuMap = new HashMap<>();
                guanzhuMap.put("shopId", shop_id);
                guanzhuMap.put("token", TiaoshiApplication.globalToken);
                guanzhuMap.put("actReq", "123456");
                guanzhuMap.put("actTime", System.currentTimeMillis() / 1000 + "");
                String sign = SignUtil.getSign(guanzhuMap);
                guanzhuMap.put("sign", Md5.toMd5(sign));
                OkHttpManager.postAsync(G.Host.GUANZHU, guanzhuMap, new MyCallBack(2, getActivity(), new GuanZhuJsonData(), handler));
                break;
        }
    }
}
