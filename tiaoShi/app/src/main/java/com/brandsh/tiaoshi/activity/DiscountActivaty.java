package com.brandsh.tiaoshi.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.MyCouponAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.CouponModel;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.widget.SelfPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tiashiwang on 2016/5/23.
 */
public class DiscountActivaty extends Activity implements View.OnClickListener {
    @ViewInject(R.id.add_address_ivBack)
    private ImageView add_address_ivBack;
    @ViewInject(R.id.discount_list_ptrListView)
    SelfPullToRefreshListView discount_list_ptrListView;
    @ViewInject(R.id.guanzhu_list_rlNoItem)
    RelativeLayout guanzhu_list_rlNoItem;
    @ViewInject(R.id.tv_select)
    TextView tv_select;
    private PopupWindow popupWindow;
    private View popView;
    private List<CouponModel.DataBean> mList = new ArrayList<>();
    private Boolean isRefresh = true;
    private String status;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    CouponModel couponModel = (CouponModel) msg.obj;
                    discount_list_ptrListView.onRefreshComplete();
                    if ("SUCCESS".equals(couponModel.getRespCode()) && null != couponModel.getData()) {
                        List<CouponModel.DataBean> newData = couponModel.getData();
                        if (isRefresh) {
                            mList.clear();
                        }
                        mList.addAll(newData);
                        if (mList.size() == 0) {
                            guanzhu_list_rlNoItem.setVisibility(View.VISIBLE);
                            discount_list_ptrListView.setVisibility(View.GONE);
                        } else {
                            guanzhu_list_rlNoItem.setVisibility(View.GONE);
                            discount_list_ptrListView.setVisibility(View.VISIBLE);
                        }
                        myCouponAdapter.notifyDataSetChanged();
                    } else {
                        guanzhu_list_rlNoItem.setVisibility(View.VISIBLE);
                        discount_list_ptrListView.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };
    private String page;
    private MyCouponAdapter myCouponAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        //沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);
        initView();
        initAdapter();
        initListener();
        loadData();
        setListenerToptrListView();
    }

    private void initView() {
        popView = LayoutInflater.from(this).inflate(R.layout.coupon_manage_pop, null);
        TextView pop_tvAll = (TextView) popView.findViewById(R.id.pop_tvAll);
        TextView pop_tvDJJ = (TextView) popView.findViewById(R.id.pop_tvDJJ);
        TextView pop_tvZKJ = (TextView) popView.findViewById(R.id.pop_tvZKJ);
        TextView pop_tvDHJ = (TextView) popView.findViewById(R.id.pop_tvDHJ);
        TextView pop_tvTGJ = (TextView) popView.findViewById(R.id.pop_tvTGJ);
        TextView pop_tvYHJ = (TextView) popView.findViewById(R.id.pop_tvYHJ);
        pop_tvAll.setOnClickListener(this);
        pop_tvDJJ.setOnClickListener(this);
        pop_tvZKJ.setOnClickListener(this);
        pop_tvDHJ.setOnClickListener(this);
        pop_tvTGJ.setOnClickListener(this);
        pop_tvYHJ.setOnClickListener(this);
        popupWindow = new PopupWindow(popView, 300, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
    }

    private void initListener() {
        add_address_ivBack.setOnClickListener(this);
        tv_select.setOnClickListener(this);
    }

    private void initAdapter() {
        myCouponAdapter = new MyCouponAdapter(this, mList);
        discount_list_ptrListView.setAdapter(myCouponAdapter);
    }

    private void loadData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", TiaoshiApplication.globalToken);
        hashMap.put("role", "USER");
        if (!TextUtils.isEmpty(status)) {
            hashMap.put("couponType", status);
        }
        hashMap.put("actReq", SignUtil.getRandom());
        hashMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(hashMap);
        hashMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.GET_DISCOUNT_LIST + "?page=" + page, hashMap, new MyCallBack(1, this, new CouponModel(), handler));
    }

    private void setListenerToptrListView() {
        discount_list_ptrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent6 = new Intent(DiscountActivaty.this, JuiceMonthActivity.class);
                intent6.putExtra("URL", G.Host.COUPON_DETAIL + mList.get((int) id).getCouponUseCode());
                intent6.putExtra("From", "DiscountActivaty");
                startActivity(intent6);
            }
        });
        discount_list_ptrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                page = "1";
                isRefresh = true;
                loadData();
                handler.sendEmptyMessageDelayed(150, 5000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData();
                handler.sendEmptyMessageDelayed(150, 5000);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_address_ivBack:
                finish();
                break;
            case R.id.tv_select:
                if (!popupWindow.isShowing()) {
                    popupWindow.showAsDropDown(tv_select, tv_select.getWidth(), 1);
                } else {
                    popupWindow.dismiss();
                }
                break;
            case R.id.pop_tvAll:
                status = "";
                loadData();
                tv_select.setText("全部");
                popupWindow.dismiss();
                break;
            case R.id.pop_tvDJJ:
                status = "CASH";
                loadData();
                tv_select.setText("代金券");
                popupWindow.dismiss();
                break;
            case R.id.pop_tvZKJ:
                status = "DISCOUNT";
                loadData();
                tv_select.setText("折扣券");
                popupWindow.dismiss();
                break;
            case R.id.pop_tvDHJ:
                status = "GIFT";
                loadData();
                tv_select.setText("兑换券");
                popupWindow.dismiss();
                break;
            case R.id.pop_tvTGJ:
                status = "GROUPON";
                loadData();
                tv_select.setText("团购券");
                popupWindow.dismiss();
                break;
            case R.id.pop_tvYHJ:
                status = "GENERAL_COUPON";
                loadData();
                tv_select.setText("优惠券");
                popupWindow.dismiss();
                break;
        }
    }
}
