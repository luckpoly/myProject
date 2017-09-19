package com.goodfood86.tiaoshi.order121Project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.activity.OtoMainActivity;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.MoneyTableModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/7/13.
 */
public class OtoOederFragment extends Fragment implements View.OnClickListener {
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    //默认最小距离最小重量
    @ViewInject(R.id.tv_moneytable_dw)
    private TextView tv_moneytable_dw;
    //默认最小价格
    @ViewInject(R.id.tv_moneytable_dwm)
    private TextView tv_moneytable_dwm;
    //每增加-- 公里
    @ViewInject(R.id.tv_moneytable_d1)
    private TextView tv_moneytable_d1;
    //每增加-- 公里增加 --元
    @ViewInject(R.id.tv_moneytable_dm1)
    private TextView tv_moneytable_dm1;
    //每增加--公斤
    @ViewInject(R.id.tv_default_w1)
    private TextView tv_default_w1;
    //每增加-- 公斤增加 --元
    @ViewInject(R.id.tv_moneytable_wm1)
    private TextView tv_moneytable_wm1;
    //最大重量
    @ViewInject(R.id.tv_moneytable_maxw)
    private TextView tv_moneytable_maxw;
    //最大距离
    @ViewInject(R.id.tv_moneytable_maxd)
    private TextView tv_moneytable_maxd;
    //文字显示
    @ViewInject(R.id.tv_moneytable_maxww)
    private TextView tv_moneytable_maxww;
    @ViewInject(R.id.tv_moneytable_maxdd)
    private TextView tv_moneytable_maxdd;
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    HttpUtils httpUtils;

    @ViewInject(R.id.tv_action)
    private TextView tv_action;
    private View view;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    MoneyTableModel moneyTableModel = (MoneyTableModel) msg.obj;
                    if (moneyTableModel.getRespCode() == 0) {
                        tv_moneytable_dw.setText(moneyTableModel.getData().getDistanceMin() + "公里以内" + moneyTableModel.getData().getWeightMin() + "公斤以下");
                        int i = Integer.valueOf(moneyTableModel.getData().getDistance());
                        int a = Integer.valueOf(moneyTableModel.getData().getWeight());
                        tv_moneytable_dwm.setText((i + a) + "元");
                        tv_moneytable_d1.setText("每增加" + moneyTableModel.getData().getDistanceSpacing() + "公里");
                        tv_moneytable_dm1.setText("增加" + moneyTableModel.getData().getDistancePrice() + "元");
                        tv_default_w1.setText("每增加" + moneyTableModel.getData().getWeightSpacing() + "公斤");
                        tv_moneytable_wm1.setText("增加" + moneyTableModel.getData().getWeightPrice() + "元");
                        tv_moneytable_maxw.setText(moneyTableModel.getData().getWeightMax() + "公斤");
                        tv_moneytable_maxd.setText(moneyTableModel.getData().getDistanceMax() + "公里");
                        tv_moneytable_maxww.setVisibility(View.VISIBLE);
                        tv_moneytable_maxdd.setVisibility(View.VISIBLE);

                    } else {
                        ToastUtil.showShort(getActivity(), moneyTableModel.getRespMsg());
                    }

                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.otoorder_fragment, null);
            initView();
        } else {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
        }
        return view;
    }

    private void initView() {
        ViewUtils.inject(this, view);
        tv_action.setOnClickListener(this);
        nav_title.setText("速递");
//        dialog= ProgressHUD.show(this, "玩命加载中...", false, null);
        httpUtils = Order121Application.getGlobalHttpUtils();
        nav_back.setVisibility(View.GONE);
        getData();
    }

    private void getData() {
        httpUtils.send(HttpRequest.HttpMethod.POST, G.Host.GET_MONEYTABLE, new RequestParams(), new MyRequestCallBack(getActivity(), handler, 1, new MoneyTableModel()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_action:
                startActivity(new Intent(getActivity(), OtoMainActivity.class));
                return;
        }
    }
}
