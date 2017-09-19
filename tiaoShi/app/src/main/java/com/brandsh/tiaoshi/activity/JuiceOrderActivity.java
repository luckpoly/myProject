package com.brandsh.tiaoshi.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.AboutUsViewPagerAdapter;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2016/3/25.
 */
public class JuiceOrderActivity extends FragmentActivity {
    @ViewInject(R.id.view_pager)
    private ViewPager view_pager;
    @ViewInject(R.id.rg_aboutus)
    private RadioGroup rg_aboutus;
    @ViewInject(R.id.my_order_ivBack)
    private ImageView my_order_ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juice_order);
        ViewUtils.inject(this);
        AppUtil.Setbar(this);
        initData();
        initListener();
    }
    private void initListener() {
        my_order_ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rg_aboutus.check(R.id.rb1);
        rg_aboutus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb1:
                        view_pager.setCurrentItem(0);
                        break;
                    case R.id.rb2:
                        view_pager.setCurrentItem(1);
                        break;
                }
            }
        });
        view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                ((RadioButton) rg_aboutus.getChildAt(position)).setChecked(true);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void initData() {
        view_pager.setAdapter(new AboutUsViewPagerAdapter(getSupportFragmentManager()));
        view_pager.setOffscreenPageLimit(2);

    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
