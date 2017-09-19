package com.goodfood86.tiaoshi.order121Project.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;


import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.AboutUsViewPagerAdapter;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.widget.TitleBarView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2016/3/25.
 */
public class AppointmentActivity extends FragmentActivity {
    @ViewInject(R.id.title_bar)
    private LinearLayout title_bar;
    private TitleBarView titleBarView;
    @ViewInject(R.id.view_pager)
    private ViewPager view_pager;
    @ViewInject(R.id.rg_aboutus)
    private RadioGroup rg_aboutus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        ViewUtils.inject(this);
        initData();
        initListener();
        initTitlebar();
    }

    private void initTitlebar() {
        titleBarView = new TitleBarView(this, title_bar, "我的预约");
    }

    private void initListener() {
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
                    case R.id.rb3:
                        view_pager.setCurrentItem(2);
                        break;
                    case R.id.rb4:
                        view_pager.setCurrentItem(3);
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
        view_pager.setOffscreenPageLimit(4);

    }
    public void onResume() {
        super.onResume();
       //已删
    }
    public void onPause() {
        super.onPause();
        //已删
    }
}
