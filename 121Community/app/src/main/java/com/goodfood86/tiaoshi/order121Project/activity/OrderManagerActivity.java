package com.goodfood86.tiaoshi.order121Project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.OrderManagerTabAdapter;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.widget.TitleBarView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2016/4/6.
 */
public class OrderManagerActivity extends FragmentActivity {
    @ViewInject(R.id.title_bar)
    private LinearLayout title_bar;
    private TitleBarView titleBarView;
    @ViewInject(R.id.tabs)
    private PagerSlidingTabStrip tabs;
    @ViewInject(R.id.view_pager)
    private ViewPager view_pager;
    public static int GO_PAY=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordermanager);
        ViewUtils.inject(this);
        initTitlebar();
        initData();

    }

    public void onResume() {
        super.onResume();
        //友盟统计
       //已删
    }
    public void onPause() {
        super.onPause();
        //友盟统计
        //已删
    }
    private void initData() {
        view_pager.setAdapter(new OrderManagerTabAdapter(getSupportFragmentManager()));
        view_pager.setOffscreenPageLimit(6);
        tabs.setViewPager(view_pager);
        if (getIntent().getIntExtra("what",1)==1){
                view_pager.setCurrentItem(0);
        }else if (getIntent().getIntExtra("what",1)==2){
            view_pager.setCurrentItem(1);
        }else if (getIntent().getIntExtra("what",1)==3){
            view_pager.setCurrentItem(2);
        }else if (getIntent().getIntExtra("what",1)==4){
            view_pager.setCurrentItem(4);
        }

    }

    private void initTitlebar() {
        titleBarView=new TitleBarView(this,title_bar,"订单管理");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
