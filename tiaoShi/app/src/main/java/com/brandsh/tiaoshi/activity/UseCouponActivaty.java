package com.brandsh.tiaoshi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.MyCouponAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.CouponModel;
import com.brandsh.tiaoshi.model.CouponPriceModel;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.LogUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.ToastUtil;
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
public class UseCouponActivaty extends Activity implements View.OnClickListener {
    @ViewInject(R.id.add_address_ivBack)
    private ImageView add_address_ivBack;
    @ViewInject(R.id.discount_list_ptrListView)
    SelfPullToRefreshListView discount_list_ptrListView;
    @ViewInject(R.id.guanzhu_list_rlNoItem)
    RelativeLayout guanzhu_list_rlNoItem;
    @ViewInject(R.id.ib_use)
    ImageButton ib_use;
    @ViewInject(R.id.iv_frist_hint)
    ImageView iv_frist_hint;
    String couponCode;
    private List<CouponModel.DataBean> mList = new ArrayList<>();
    private Boolean isRefresh = true;
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
//                        if (mList.size()>=Integer.parseInt(couponModel.getData().getTotalCount())){
//                            discount_list_ptrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//                        }else {
//                            discount_list_ptrListView.setMode(PullToRefreshBase.Mode.BOTH);
//                        }
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
                case 2:
                    CouponPriceModel couponModel1 = (CouponPriceModel) msg.obj;
                    if ("SUCCESS".equals(couponModel1.getRespCode())) {
                        ToastUtil.showShort(UseCouponActivaty.this, "OK");
                        if (null != finalDialog1 && finalDialog1.isShowing()) {
                            finalDialog1.dismiss();
                        }
                        Intent intent = new Intent();
                        intent.putExtra("couponUseCodes", couponCode);
                        intent.putExtra("isWeiXin", "YES");
                        setResult(1, intent);
                        finish();
                    } else {
                        ToastUtil.showShort(UseCouponActivaty.this, couponModel1.getRespMsg());
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
        setContentView(R.layout.activity_use_coupon);
        //沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);
        initAdapter();
        initListener();
        setListenerToptrListView();
        loadData();
    }

    private void initListener() {
        add_address_ivBack.setOnClickListener(this);
        iv_frist_hint.setOnClickListener(this);
        ib_use.setOnClickListener(this);
    }

    private void initAdapter() {
        myCouponAdapter = new MyCouponAdapter(this, mList);
        discount_list_ptrListView.setAdapter(myCouponAdapter);
    }

    private void loadData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("role", "USE");
        hashMap.put("shopId", getIntent().getStringExtra("shopId"));
        hashMap.put("goods", getIntent().getStringExtra("goods"));
        hashMap.put("token", TiaoshiApplication.globalToken);
        hashMap.put("actReq", SignUtil.getRandom());
        hashMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(hashMap);
        hashMap.put("sign", Md5.toMd5(sign));
        LogUtil.e(sign);
        OkHttpManager.postAsync(G.Host.GET_DISCOUNT_LIST + "?page=" + page, hashMap, new MyCallBack(1, this, new CouponModel(), handler));
    }

    private void setListenerToptrListView() {
        discount_list_ptrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("couponUseCodes", mList.get((int) id).getCouponUseCode());
                intent.putExtra("isWeiXin", "NO");
                setResult(1, intent);
                finish();
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
            case R.id.ib_use:
                if (getSharedPreferences(G.SP.APP_NAME, MODE_PRIVATE).getString("isfarst_coupon", "YES").equals("YES")) {
                    iv_frist_hint.setVisibility(View.VISIBLE);
                } else {
                    showPopup();
                }
                break;
            case R.id.iv_frist_hint:
                iv_frist_hint.setVisibility(View.GONE);
                SharedPreferences.Editor editor = getSharedPreferences(G.SP.APP_NAME, Context.MODE_PRIVATE).edit();
                editor.putString("isfarst_coupon", "NO");
                editor.commit();
                showPopup();
                break;

        }
    }

    private void showPopup() {
        final Dialog finalDialog;
        View view = LayoutInflater.from(this).inflate(R.layout.coupon_use_dialog, null);
        TextView tv_dismiss_pack = (TextView) view.findViewById(R.id.tv_dismiss_pack);
        TextView tv_sys = (TextView) view.findViewById(R.id.tv_sys);
        TextView tv_input_no = (TextView) view.findViewById(R.id.tv_input_no);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setCancelable(false);
        builder1.setView(view);
        finalDialog = builder1.show();
        tv_dismiss_pack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalDialog.dismiss();
            }
        });
        tv_sys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(UseCouponActivaty.this, SimpleCaptureActivity.class).putExtra("isUse", "YES"), 1);
                finalDialog.dismiss();
            }
        });
        tv_input_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputPopup();
                finalDialog.dismiss();
            }
        });
    }

    Dialog finalDialog1;

    private void showInputPopup() {
        View view = LayoutInflater.from(this).inflate(R.layout.coupon_inputuse_dialog, null);
        TextView tv_dismiss_pack = (TextView) view.findViewById(R.id.tv_dismiss_pack);
        TextView tv_sys = (TextView) view.findViewById(R.id.tv_tijiao);
        final EditText et_coupon_No = (EditText) view.findViewById(R.id.et_coupon_No);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setCancelable(false);
        builder1.setView(view);
        finalDialog1 = builder1.show();
        tv_dismiss_pack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalDialog1.dismiss();
            }
        });
        tv_sys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couponCode = et_coupon_No.getText().toString();
                if (TextUtils.isEmpty(couponCode)) {
                    ToastUtil.showShort(UseCouponActivaty.this, "请输入券码！");
                } else {
                    isCouponUse();
                }

            }
        });
    }

    private void isCouponUse() {
        HashMap map = new HashMap();
        map.put("shopId", getIntent().getStringExtra("shopId"));
        map.put("goods", getIntent().getStringExtra("goods"));
        map.put("token", TiaoshiApplication.globalToken);
        map.put("couponUseCode", couponCode);
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.IS_WX_COUPON, map, new MyCallBack(2, this, new CouponPriceModel(), handler));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
                String resultString = data.getStringExtra("resultString");
                if (!TextUtils.isEmpty(resultString)) {
                    couponCode = resultString;
                    isCouponUse();
                }
            }
        }
    }
}
