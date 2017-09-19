package com.brandsh.tiaoshi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.brandsh.tiaoshi.fragment.StoreDetailFragment;
import com.brandsh.tiaoshi.fragment.StoreEvaluateFragment;
import com.brandsh.tiaoshi.fragment.StoreProductListFragment;

/**
 * Created by libokang on 15/8/21.
 */
public class StoreDetailTabAdapter extends FragmentPagerAdapter {

    String[] title = {"全部商品", "评价", "商户"};
    Fragment fragment1;
    Fragment fragment2;
    Fragment fragment3;

    public StoreDetailTabAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fragment1 = new StoreProductListFragment();
                return fragment1;
            case 1:
                fragment2 = new StoreEvaluateFragment();
                return fragment2;
            case 2:
                fragment3 = new StoreDetailFragment();
                return fragment3;

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
