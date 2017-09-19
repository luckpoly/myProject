package com.brandsh.tiaoshi.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.activity.AboutUsActivity;
import com.brandsh.tiaoshi.activity.AboutUsSMActivity;
import com.brandsh.tiaoshi.activity.BalanceActivity;
import com.brandsh.tiaoshi.activity.CardPackageActivity;
import com.brandsh.tiaoshi.activity.FCActivity;
import com.brandsh.tiaoshi.activity.GuanZhuListActivity;
import com.brandsh.tiaoshi.activity.JuiceOrderActivity;
import com.brandsh.tiaoshi.activity.MonthOrderListActivity;
import com.brandsh.tiaoshi.activity.MyDeliveryAddressActivity;
import com.brandsh.tiaoshi.activity.MyMessageActivity;
import com.brandsh.tiaoshi.activity.OrderListActivity;
import com.brandsh.tiaoshi.activity.TopUpActivity;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.CouponModel;
import com.brandsh.tiaoshi.model.OrderNoModel;
import com.brandsh.tiaoshi.model.UserInfoJsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SPUtil;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.widget.CircleImageView;
import com.brandsh.tiaoshi.widget.SelfPullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;

/**
 * Created by libokang on 15/9/2.
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {
    public final int REQUEST_TO_MY_INFO = 0;
    public final int REQUEST_LOGIN = 1;

    private View rootView;


    @ViewInject(R.id.my_head_img)
    private CircleImageView my_head_img;
    @ViewInject(R.id.my_name)
    private TextView my_name;
    @ViewInject(R.id.tv_yaoqing_code)
    private TextView tv_yaoqing_code;
    @ViewInject(R.id.tv_ka_no)
    private TextView tv_ka_no;
    @ViewInject(R.id.tv_yu_e)
    private TextView tv_yu_e;
    @ViewInject(R.id.iv_diamond)
    private ImageView iv_diamond;
    @ViewInject(R.id.ll_to_my_coupon)
    private LinearLayout ll_to_my_coupon;
    @ViewInject(R.id.ll_go_detail)
    private LinearLayout ll_go_detail;
    @ViewInject(R.id.ll_go_balance)
    private LinearLayout ll_go_balance;
    @ViewInject(R.id.ll_yaoqingma)
    private LinearLayout ll_yaoqingma;
    @ViewInject(R.id.ll_to_my_guanzhu)
    private RelativeLayout ll_to_my_guanzhu;
    @ViewInject(R.id.ll_to_my_order)
    private LinearLayout ll_to_my_order;
    @ViewInject(R.id.ll_to_my_message)
    private RelativeLayout ll_to_my_message;
    @ViewInject(R.id.ll_to_my_about)
    private RelativeLayout ll_to_my_about;
    @ViewInject(R.id.ll_to_my_shengji)
    private RelativeLayout ll_to_my_shengji;
    @ViewInject(R.id.ll_to_my_fenxiang)
    private RelativeLayout ll_to_my_fenxiang;
    @ViewInject(R.id.ll_to_my_month)
    private RelativeLayout ll_to_my_month;
    @ViewInject(R.id.ll_to_my_address)
    private RelativeLayout ll_to_my_address;
    @ViewInject(R.id.srl_myfragment)
    SelfPullToRefreshScrollView srl_myfragment;
    @ViewInject(R.id.my_phone)
    TextView my_phone;
    @ViewInject(R.id.tv_dfk)
    TextView tv_dfk;
    @ViewInject(R.id.tv_dfh)
    TextView tv_dfh;
    @ViewInject(R.id.tv_psz)
    TextView tv_psz;
    @ViewInject(R.id.tv_dpj)
    TextView tv_dpj;
    @ViewInject(R.id.tv_ywc)
    TextView tv_ywc;
    @ViewInject(R.id.tv_tksh)
    TextView tv_tksh; @ViewInject(R.id.tvNotice1)
    TextView tvNotice1; @ViewInject(R.id.tvNotice2)
    TextView tvNotice2; @ViewInject(R.id.tvNotice3)
    TextView tvNotice3; @ViewInject(R.id.tvNotice4)
    TextView tvNotice4; @ViewInject(R.id.tvNotice5)
    TextView tvNotice5; @ViewInject(R.id.tvNotice6)
    TextView tvNotice6;
    @ViewInject(R.id.tv_new_versin)
    TextView tv_new_versin;
    @ViewInject(R.id.tv_tag_new)
    TextView tv_tag_new;

    private BitmapUtils bitmapUtils;
    private HttpUtils httpUtils;
    private HashMap userInfoRequestMap;
    private MyFragmentBroadcastReciver myFragmentBroadcastReciver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.my_fragment, null);
            ViewUtils.inject(this, rootView);
            init();
            initListener();
            updateUI();
            registerReceiver();
        } else {
            if (rootView.getParent() != null) {
                ((ViewGroup) rootView.getParent()).removeView(rootView);
            }
        }
        return rootView;
    }

    private void init() {
        httpUtils = TiaoshiApplication.getGlobalHttpUtils();
        bitmapUtils = TiaoshiApplication.getHeadImgBitmapUtils();
        myFragmentBroadcastReciver = new MyFragmentBroadcastReciver();
        userInfoRequestMap = new HashMap();
        TextPaint tp = tv_yaoqing_code.getPaint();
        tp.setFakeBoldText(true);
        TextPaint tp1 = tv_ka_no.getPaint();
        tp1.setFakeBoldText(true);
        TextPaint tp2 = tv_yu_e.getPaint();
        tp2.setFakeBoldText(true);
        srl_myfragment.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                updateUI();
                handler.sendEmptyMessageDelayed(150, 1500);
            }
        });
        String newVersion = SPUtil.getSP("newVersion", "");
        if (!TextUtils.isEmpty(newVersion)){
            tv_new_versin.setText("V" + newVersion);
            if (AppUtil.getVersionName(getContext()).equals(newVersion)) {
                tv_tag_new.setVisibility(View.GONE);
            } else {
                tv_tag_new.setVisibility(View.VISIBLE);
            }
        }

    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.brandsh.tiaoshi.MyFragment");
        intentFilter.addAction("updateHeadImg");
        intentFilter.addAction("updateNickName");
        intentFilter.addAction("exit_login");
        getActivity().registerReceiver(myFragmentBroadcastReciver, intentFilter);
    }

    private void initListener() {
        ll_to_my_coupon.setOnClickListener(this);
        ll_to_my_guanzhu.setOnClickListener(this);
        ll_to_my_order.setOnClickListener(this);
        ll_to_my_message.setOnClickListener(this);
        ll_to_my_fenxiang.setOnClickListener(this);
        ll_to_my_month.setOnClickListener(this);
        tv_yaoqing_code.setOnClickListener(this);
        ll_go_detail.setOnClickListener(this);
        ll_go_balance.setOnClickListener(this);
        ll_yaoqingma.setOnClickListener(this);
        ll_to_my_address.setOnClickListener(this);
        tv_dfk.setOnClickListener(this);
        tv_dfh.setOnClickListener(this);
        tv_psz.setOnClickListener(this);
        tv_dpj.setOnClickListener(this);
        tv_ywc.setOnClickListener(this);
        tv_tksh.setOnClickListener(this);
        ll_to_my_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
            }
        });
        ll_to_my_shengji.setOnClickListener(this);
    }

    public void updateUI() {
        if (TiaoshiApplication.isLogin) {
            Log.e("getUserInfo", "yeah");
            userInfoRequestMap.clear();
            userInfoRequestMap.put("token", TiaoshiApplication.globalToken);
            userInfoRequestMap.put("actReq", SignUtil.getRandom());
            userInfoRequestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
            userInfoRequestMap.put("sign", Md5.toMd5(SignUtil.getSign(userInfoRequestMap)));
            OkHttpManager.postAsync(G.Host.USER_INFO, userInfoRequestMap, new MyCallBack(1, getActivity(), new UserInfoJsonData(), handler));
        } else {
            setDefault();
        }
    }

    //获取卡券个数
    private void getCoupon() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", TiaoshiApplication.globalToken);
        hashMap.put("role", "USER");
        hashMap.put("actReq", SignUtil.getRandom());
        hashMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(hashMap);
        hashMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.GET_DISCOUNT_LIST, hashMap, new MyCallBack(2, getActivity(), new CouponModel(), handler));
    }
    //获取卡券个数
    private void getOrder() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", TiaoshiApplication.globalToken);
        hashMap.put("actReq", SignUtil.getRandom());
        hashMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(hashMap);
        hashMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.ORDER_CATEGORY, hashMap, new MyCallBack(3, getActivity(), new OrderNoModel(), handler));
    }

    private void setDefault() {
        my_head_img.setImageResource(R.mipmap.default_head_img);
        tv_yu_e.setText("0");
        tv_yaoqing_code.setText("获取");
        tv_ka_no.setText("0");
        my_name.setText("未登录");
        my_phone.setVisibility(View.GONE);
        iv_diamond.setVisibility(View.GONE);
        tvNotice1.setVisibility(View.GONE);
        tvNotice2.setVisibility(View.GONE);
        tvNotice3.setVisibility(View.GONE);
        tvNotice4.setVisibility(View.GONE);
        tvNotice6.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(myFragmentBroadcastReciver);
        super.onDestroy();
    }

    private class MyFragmentBroadcastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.brandsh.tiaoshi.MyFragment".equals(intent.getAction())) {
                updateUI();
            } else if ("updateHeadImg".equals(intent.getAction())) {
                bitmapUtils.clearCache();
                bitmapUtils.clearDiskCache();
                bitmapUtils.clearMemoryCache();
                bitmapUtils.display(my_head_img, TiaoshiApplication.globalUserInfo.getData().getIcon());
            } else if ("updateNickName".equals(intent.getAction())) {
                Log.e("nick_name", TiaoshiApplication.globalUserInfo.getData().getNickName());
                my_name.setText("昵称：" + TiaoshiApplication.globalUserInfo.getData().getNickName());
            } else if ("exit_login".equals(intent.getAction())) {
                setDefault();
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    UserInfoJsonData userInfoJsonData = (UserInfoJsonData) msg.obj;
                    if (userInfoJsonData != null) {
                        if (userInfoJsonData.getRespCode().equals("SUCCESS")) {
                            //获取卡券个数
                            getCoupon();
                            TiaoshiApplication.globalUserInfo = userInfoJsonData;
                            if (!"".equals(TiaoshiApplication.globalUserInfo.getData().getIcon()) && TiaoshiApplication.globalUserInfo.getData().getIcon() != null) {
                                Log.e("imgUrl", TiaoshiApplication.globalUserInfo.getData().getIcon());
                                if (!TextUtils.isEmpty(TiaoshiApplication.globalUserInfo.getData().getTel())) {
                                    TiaoshiApplication.phone = TiaoshiApplication.globalUserInfo.getData().getTel();
                                }
                                bitmapUtils.clearCache();
                                bitmapUtils.clearDiskCache();
                                bitmapUtils.clearMemoryCache();
                                bitmapUtils.display(my_head_img, userInfoJsonData.getData().getIcon());
                            }
                            if (!TextUtils.isEmpty(userInfoJsonData.getData().getTel()) && userInfoJsonData.getData().getTel().length() == 11) {
                                my_phone.setVisibility(View.VISIBLE);
                                my_phone.setText("电话：" + userInfoJsonData.getData().getTel().toString().substring(0, 3) + "****" + userInfoJsonData.getData().getTel().toString().substring(7, 11));
                            } else {
                                my_phone.setVisibility(View.GONE);
                            }
                            my_name.setText("昵称：" + userInfoJsonData.getData().getNickName());
                            tv_yu_e.setText(userInfoJsonData.getData().getBalance());
                            if (TextUtils.isEmpty(userInfoJsonData.getData().getInviteCode())) {
                                tv_yaoqing_code.setText("获取");
                            } else {
                                tv_yaoqing_code.setText(userInfoJsonData.getData().getInviteCode());
                            }
                            if (null != userInfoJsonData.getData().getHealthPartner() && userInfoJsonData.getData().getHealthPartner().equals("YES")) {
                                iv_diamond.setVisibility(View.VISIBLE);
                            }

                        } else {
                            showToast(userInfoJsonData.getRespMsg());
                        }
                    }
                    break;
                case 2:
                    CouponModel couponModel = (CouponModel) msg.obj;
                    if ("SUCCESS".equals(couponModel.getRespCode())) {
                        if (null != couponModel.getData()) {
                            tv_ka_no.setText(couponModel.getData().size() + "");
                        } else {
                            tv_ka_no.setText("0");
                        }
                        getOrder();
                    }
                    break;
                case 3:
                    OrderNoModel orderNoModel= (OrderNoModel) msg.obj;
                    if (orderNoModel.getRespCode().equals("SUCCESS")){
                        String a1=orderNoModel.getData().getPaying();
                        String a2=orderNoModel.getData().getPre();
                        String a3=orderNoModel.getData().getSending();
                        String a4=orderNoModel.getData().getEvaluate();
                        String a6=orderNoModel.getData().getAfter();
                        if (!a1.equals("0")){
                            tvNotice1.setVisibility(View.VISIBLE);
                            tvNotice1.setText(a1);
                        }else {
                            tvNotice1.setVisibility(View.GONE);
                        }
                        if (!a2.equals("0")){
                            tvNotice2.setVisibility(View.VISIBLE);
                            tvNotice2.setText(a2);
                        }else {
                            tvNotice2.setVisibility(View.GONE);
                        }
                        if (!a3.equals("0")){
                            tvNotice3.setVisibility(View.VISIBLE);
                            tvNotice3.setText(a3);
                        }else {
                            tvNotice3.setVisibility(View.GONE);
                        }
                        if (!a4.equals("0")){
                            tvNotice4.setVisibility(View.VISIBLE);
                            tvNotice4.setText(a4);
                        }else {
                            tvNotice4.setVisibility(View.GONE);
                        }
                        if (!a6.equals("0")){
                            tvNotice6.setVisibility(View.VISIBLE);
                            tvNotice6.setText(a6);
                        }else {
                            tvNotice6.setVisibility(View.GONE);
                        }
                    }
                    break;
                case 150:
                case 200:
                    //隐藏进度条
                    srl_myfragment.onRefreshComplete();
                    break;
                case 300:
                    srl_myfragment.onRefreshComplete();
                    showToast("网络繁忙，请稍后再试");
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {

        if (!TiaoshiApplication.isLogin) {
            startActivityForResult(FCActivity.getFCActivityIntent(getActivity(), PhoneLoginFragment.class), REQUEST_LOGIN);
            return;
        }

        switch (v.getId()) {

            case R.id.ll_go_detail:
                startActivity(FCActivity.getFCActivityIntent(getActivity(), MyInfoFragment.class));
                break;

            case R.id.ll_to_my_coupon:
                startActivity(new Intent(getActivity(), CardPackageActivity.class));
                break;

            case R.id.ll_to_my_guanzhu:
                Intent intent2 = new Intent(getActivity(), GuanZhuListActivity.class);
                startActivity(intent2);
                break;

            case R.id.ll_to_my_message:
                Intent intent4 = new Intent(getActivity(), MyMessageActivity.class);
                startActivity(intent4);
                break;

            case R.id.ll_to_my_fenxiang:
                Intent intent5 = new Intent(getActivity(), JuiceOrderActivity.class);
                startActivity(intent5);
                break;
            case R.id.ll_to_my_month:
                Intent intent6 = new Intent(getActivity(), MonthOrderListActivity.class);
                startActivity(intent6);
                break;
            case R.id.tv_yaoqing_code:
            case R.id.ll_yaoqingma:
                String str = tv_yaoqing_code.getText().toString();
                if (TextUtils.isEmpty(str) || str.equals("获取")) {
                    //挑转充值支付
                    startActivity(new Intent(getActivity(), TopUpActivity.class));
                } else {
                    //分享
                    share(str);
                }
                break;
            case R.id.ll_go_balance:
                startActivity(new Intent(getActivity(), BalanceActivity.class));
                break;
            case R.id.ll_to_my_address:
                startActivity(new Intent(getActivity(), MyDeliveryAddressActivity.class));
                break;
            case R.id.ll_to_my_order:
                Intent intent = new Intent(getActivity(), OrderListActivity.class)
                        .putExtra("customStatus", "ALL");
                startActivity(intent);
                break;
            case R.id.tv_dfk:
                Intent intent11 = new Intent(getActivity(), OrderListActivity.class)
                        .putExtra("customStatus", "PAYING");
                startActivity(intent11);
                break;
            case R.id.tv_dfh:
                Intent intent12 = new Intent(getActivity(), OrderListActivity.class)
                        .putExtra("customStatus", "PRE");
                startActivity(intent12);
                break;
            case R.id.tv_psz:
                Intent intent13 = new Intent(getActivity(), OrderListActivity.class)
                        .putExtra("customStatus", "SENDING");
                startActivity(intent13);
                break;
            case R.id.tv_dpj:
                Intent intent14 = new Intent(getActivity(), OrderListActivity.class)
                        .putExtra("customStatus", "EVALUATE");
                startActivity(intent14);
                break;
            case R.id.tv_ywc:
                Intent intent15 = new Intent(getActivity(), OrderListActivity.class)
                        .putExtra("customStatus", "OK");
                startActivity(intent15);
                break;
            case R.id.tv_tksh:
                Intent intent16 = new Intent(getActivity(), OrderListActivity.class)
                        .putExtra("customStatus", "AFTER");
                startActivity(intent16);
                break;
        }

    }

    //安卓调用系统分享文字
    private void share(String content) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        //自定义选择框的标题
        //startActivity(Intent.createChooser(shareIntent, "邀请好友"));
        //系统默认标题
        startActivity(shareIntent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (TiaoshiApplication.isLogin&&TiaoshiApplication.globalToken!=null){
            getOrder();
        }
    }
}
