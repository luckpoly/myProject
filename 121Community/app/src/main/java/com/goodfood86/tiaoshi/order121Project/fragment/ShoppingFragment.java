package com.goodfood86.tiaoshi.order121Project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodfood86.tiaoshi.order121Project.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/7/13.
 */
public class ShoppingFragment extends Fragment implements View.OnClickListener {
    @ViewInject(R.id.tv_gotiaoshi)
    private TextView tv_gotiaoshi;
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shopping_fragment, null);
        ViewUtils.inject(this, view);
        initView();
        return view;
    }

    private void initView() {
        tv_gotiaoshi.setOnClickListener(this);
        nav_back.setVisibility(View.GONE);
        nav_title.setText("购物");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_gotiaoshi:
                Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.brandsh.tiaoshi");
                if (intent != null) {
                    startActivity(intent);
                } else {
                    // 没有安装要跳转的app应用，提醒一下
                    Toast.makeText(getActivity(), "您还没有安装挑食网APP，快快去下载吧！", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
