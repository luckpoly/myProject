package com.goodfood86.tiaoshi.order121Project.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.fragment.HomeFragment;
import com.goodfood86.tiaoshi.order121Project.fragment.OrderFragment;
import com.goodfood86.tiaoshi.order121Project.fragment.OtoMainFragment;
import com.goodfood86.tiaoshi.order121Project.fragment.OtoOederFragment;
import com.goodfood86.tiaoshi.order121Project.fragment.ShoppingFragment;
import com.goodfood86.tiaoshi.order121Project.fragment.SlidingFragment;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/7/13.
 */
public class MainActivity extends FragmentActivity {
    private Toast toast;
    private boolean currentBackState;
    public FragmentTabHost tab_host;
//    OtoOederFragment.class
    private Class mFragments[] = {HomeFragment.class, ShoppingFragment.class, OtoMainFragment.class,OrderFragment.class,SlidingFragment.class};
    private int mMenuImg[] = {R.drawable.tab_menu_home, R.drawable.tab_menu_shopping_, R.drawable.tab_menu_otoorder,R.drawable.tab_menu_order, R.drawable.tab_menu_my};
    private String mStrArr[] = {"首页", "购物", "速递","订单", "我的"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        tab_host = (FragmentTabHost) findViewById(android.R.id.tabhost);
        // Tab切换时，当前Tab对应的Fragment会被加入到tab_fragment中作为其子View
        tab_host.setup(this, getSupportFragmentManager(), R.id.tab_fragment);
        initView();
    }
    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        toast = Toast.makeText(this,"再按一次回退键退出程序", Toast.LENGTH_SHORT);
        for (int i = 0; i < mFragments.length; i++) {
            TabHost.TabSpec tabSpec = tab_host.newTabSpec(i + "").setIndicator(getMenuItemView(inflater, i));
            tab_host.addTab(tabSpec, mFragments[i], null);
//          tab_host.getTabWidget().getChildAt(i).setBackgroundResource(android.R.color.transparent);
            tab_host.getTabWidget().setDividerDrawable(android.R.color.transparent);
        }
    }
    private View getMenuItemView(LayoutInflater inflater, int index) {
        View view = inflater.inflate(R.layout.menu_item_view, null);
        ImageView img = (ImageView) view.findViewById(R.id.item_img);
        img.setImageResource(mMenuImg[index]);
        TextView tv = (TextView) view.findViewById(R.id.item_tv);
        tv.setText(mStrArr[index]);
        return view;
    }
    @Override
    public void onBackPressed() {
        if (currentBackState) {
            super.onBackPressed();
            Order121Application.getInstance().finishAllActivity();
        }
        currentBackState = true;
        if (!isFinishing()) {
            toast.show();
        } else {
            toast.cancel();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    currentBackState = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
