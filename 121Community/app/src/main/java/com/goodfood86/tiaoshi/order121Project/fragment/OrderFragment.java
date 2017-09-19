package com.goodfood86.tiaoshi.order121Project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.activity.AppointmentActivity;
import com.goodfood86.tiaoshi.order121Project.activity.LoginActivity;
import com.goodfood86.tiaoshi.order121Project.activity.OrderManagerActivity;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/7/13.
 */
public class OrderFragment extends Fragment implements View.OnClickListener {
    @ViewInject(R.id.ll_otoorder)
    private LinearLayout ll_otoorder;
    @ViewInject(R.id.ll_yuyue)
    private LinearLayout ll_yuyue;
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.order_fragment, null);

        initView();
        return view;
    }

    private void initView() {
        ViewUtils.inject(this, view);
        nav_title.setText("订单");
        nav_back.setVisibility(View.GONE);
        ll_otoorder.setOnClickListener(this);
        ll_yuyue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!Order121Application.isLogin()) {
            ToastUtil.show(getActivity(), "请先登陆", Toast.LENGTH_SHORT);
            startActivity(new Intent(getActivity(), LoginActivity.class));
        } else {
            switch (v.getId()) {
                case R.id.ll_otoorder:
                    Intent intent1 = new Intent(getActivity(), OrderManagerActivity.class);
                    startActivity(intent1);
                    return;
                case R.id.ll_yuyue:
                    Intent intent2 = new Intent(getActivity(), AppointmentActivity.class);
                    startActivity(intent2);
                    return;
            }
        }
    }
}
