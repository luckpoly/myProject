package com.goodfood86.tiaoshi.order121Project.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.goodfood86.tiaoshi.order121Project.fragment.QuestionFragment;
import com.goodfood86.tiaoshi.order121Project.fragment.YuyueList_fragment;
import com.goodfood86.tiaoshi.order121Project.fragment.YuyueList_fragment1;
import com.goodfood86.tiaoshi.order121Project.fragment.YuyueList_fragment2;
import com.goodfood86.tiaoshi.order121Project.fragment.YuyueList_fragment3;


/**
 * Created by Administrator on 2016/3/28.
 */
public class AboutUsViewPagerAdapter extends FragmentPagerAdapter {

    String[] title = {"全部预约", "预约中", "已预约","已失效"};
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
                fragment1 = new YuyueList_fragment();
                bundle.putString("type","全部");
                fragment1.setArguments(bundle);
                return fragment1;
            case 1:
                fragment2 = new YuyueList_fragment1();

                bundle.putString("type","全部");
                fragment1.setArguments(bundle);
                return fragment2;
            case 2:
                fragment3 = new YuyueList_fragment2();

                bundle.putString("type","全部");
                fragment1.setArguments(bundle);
                return fragment3;
            case 3:
                fragment4 = new YuyueList_fragment3();
                bundle.putString("type","全部");
                fragment1.setArguments(bundle);
                return fragment4;

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
