package com.brandsh.tiaoshi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.StoreDetailTabAdapter;
import com.brandsh.tiaoshi.fragment.StoreProductListFragment;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.lidroid.xutils.ViewUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by libokang on 15/8/21.
 */
public class StoreDetailActivity extends FragmentActivity implements View.OnClickListener{

    private RelativeLayout store_detail_rlSearch;
    private ViewPager viewPager;
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    private String tag;
    StoreDetailTabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_tab_sliding_activity);
        //状态栏沉浸
        AppUtil.Setbar(this);
        ViewUtils.inject(this);

        store_detail_rlSearch = (RelativeLayout) findViewById(R.id.store_detail_rlSearch);
        store_detail_rlSearch.setOnClickListener(this);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
         adapter=new StoreDetailTabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pagerSlidingTabStrip.setViewPager(viewPager);

        tag = getIntent().getStringExtra("tag");

    }
    @Override
    public void onBackPressed() {
        if(tag==null){
            super.onBackPressed();
//            sendBroadcast(new Intent("changedetail"));
            finish();
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.store_detail_rlSearch:
                if(tag==null){
//                    sendBroadcast(new Intent("changedetail"));
                    finish();
                }else {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
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