package com.goodfood86.tiaoshi.order121Project.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.goodfood86.tiaoshi.order121Project.fragment.AllOredrFragment;
import com.goodfood86.tiaoshi.order121Project.fragment.CompletedOrderFragment;
import com.goodfood86.tiaoshi.order121Project.fragment.DeliveryOrderFragment;
import com.goodfood86.tiaoshi.order121Project.fragment.ForDeliveryOrderFragment;
import com.goodfood86.tiaoshi.order121Project.fragment.ForEvaluationOrderFragment;
import com.goodfood86.tiaoshi.order121Project.fragment.NoPayOrderFragment;

/**
 * Created by Administrator on 2016/4/6.
 */
public class OrderManagerTabAdapter extends FragmentPagerAdapter{
    String[] title = {"全部订单", "待支付", "待配送","配送中","待评价","已完成"};
    Fragment fragment1;
    Fragment fragment2;
    Fragment fragment3;
    Fragment fragment4;
    Fragment fragment5;
    Fragment fragment6;
    public OrderManagerTabAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fragment1 = new AllOredrFragment();
                return fragment1;
            case 1:
                fragment2 = new NoPayOrderFragment();
                return fragment2;
            case 2:
                fragment3 = new ForDeliveryOrderFragment();
                return fragment3;
            case 3:
                fragment4 = new DeliveryOrderFragment();
                return fragment4;
            case 4:
                fragment5 = new ForEvaluationOrderFragment();
                return fragment5;
            case 5:
                fragment6 = new CompletedOrderFragment();
                return fragment6;

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
