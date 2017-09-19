package com.brandsh.tiaoshi.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.brandsh.tiaoshi.fragment.JuiceOrderListOneFragment;
import com.brandsh.tiaoshi.fragment.JuiceOrderListTwoFragment;


/**
 * Created by Administrator on 2016/3/28.
 */
public class AboutUsViewPagerAdapter extends FragmentPagerAdapter {

    String[] title = {"分享订单", "领取订单"};
    Fragment fragment1;
    Fragment fragment2;
    Fragment fragment3;
    Fragment fragment4;

    public AboutUsViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle=new Bundle();
        switch (position) {
            case 0:
                fragment1 = new JuiceOrderListOneFragment();
                return fragment1;
            case 1:
                fragment2 = new JuiceOrderListTwoFragment();
                return fragment2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return title.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
