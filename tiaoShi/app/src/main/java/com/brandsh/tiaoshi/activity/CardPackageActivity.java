package com.brandsh.tiaoshi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.OverdueNoModel;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/11/8.
 */

public class CardPackageActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.ll_my_conpon)
    LinearLayout ll_my_conpon;
    @ViewInject(R.id.add_address_ivBack)
    ImageView add_address_ivBack;
    @ViewInject(R.id.tv_get_coupon)
    TextView tv_get_coupon;
    @ViewInject(R.id.tv_overdueNo)
    TextView tv_overdueNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardpackage);
        ViewUtils.inject(this);
        //状态栏
        AppUtil.Setbar(this);
        initView();
        initData();
    }

    private void initView() {
        ll_my_conpon.setOnClickListener(this);
        add_address_ivBack.setOnClickListener(this);
        tv_get_coupon.setOnClickListener(this);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    OverdueNoModel overdueNoModel = (OverdueNoModel) msg.obj;
                    if ("SUCCESS".equals(overdueNoModel.getRespCode())) {
                        tv_overdueNo.setText("（" + overdueNoModel.getData().getCount() + "张即将过期）");
                    }
                    break;
            }
        }
    };

    private void initData() {
        HashMap map = new HashMap();
        map.put("token", TiaoshiApplication.globalToken);
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.GET_OVERDUE_NO, map, new MyCallBack(1, this, new OverdueNoModel(), handler));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_my_conpon:
                startActivity(new Intent(this, DiscountActivaty.class));
                break;
            case R.id.add_address_ivBack:
                finish();
                break;
            case R.id.tv_get_coupon:
                startActivity(new Intent(CardPackageActivity.this, SimpleCaptureActivity.class));
                break;
        }
    }
}
