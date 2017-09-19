package com.brandsh.tiaoshi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.fragment.CategoryFragment;
import com.brandsh.tiaoshi.fragment.FindFragment;
import com.brandsh.tiaoshi.fragment.HomeFragment;
import com.brandsh.tiaoshi.fragment.MyFragment;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends FragmentActivity {
    private Toast toast;
    private boolean currentBackState;
    private ImageView iv_sys;
    private RelativeLayout rl_sys;
    public FragmentTabHost tab_host;
    private Class mFragments[] = {HomeFragment.class, CategoryFragment.class,FindFragment.class, FindFragment.class, MyFragment.class};
    private int mMenuImg[] = {R.drawable.tab_menu_home, R.drawable.tab_menu_category, R.color.bg, R.drawable.tab_menu_find, R.drawable.tab_menu_my};
    private String mStrArr[] = {"首页", "分类", "", "发现", "我的"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        //沉浸状态栏
        AppUtil.Setbar(this);
        toast = Toast.makeText(this, "再按一次回退键退出程序", Toast.LENGTH_SHORT);

        tab_host = (FragmentTabHost) findViewById(android.R.id.tabhost);
        // Tab切换时，当前Tab对应的Fragment会被加入到tab_fragment中作为其子View
        tab_host.setup(this, getSupportFragmentManager(), R.id.tab_fragment);
        initView();
    }

    @Override
    public void onBackPressed() {
        if (currentBackState) {
            TiaoshiApplication.globalUserInfo = null;
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        sleep(500);
                        TiaoshiApplication.getInstance().exit();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            super.onBackPressed();
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

    private void initView() {
        rl_sys = (RelativeLayout) findViewById(R.id.rl_sys);
        rl_sys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SimpleCaptureActivity.class));
            }
        });

        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < mFragments.length; i++) {
            TabHost.TabSpec tabSpec = tab_host.newTabSpec(i + "").setIndicator(getMenuItemView(inflater, i));
            tab_host.addTab(tabSpec, mFragments[i], null);
            tab_host.getTabWidget().getChildAt(i).setBackgroundResource(android.R.color.transparent);
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
    protected void onDestroy() {
        TiaoshiApplication.globalUserInfo = null;
        TiaoshiApplication.diyShoppingCartJsonData = null;
        TiaoshiApplication.globalUserId = null;
        TiaoshiApplication.globalToken = null;
        TiaoshiApplication.setGlobalBitmapUtils(null);
        TiaoshiApplication.setHeadImgBitmapUtils(null);
        TiaoshiApplication.setGlobalHttpUtils(null);
        super.onDestroy();
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
