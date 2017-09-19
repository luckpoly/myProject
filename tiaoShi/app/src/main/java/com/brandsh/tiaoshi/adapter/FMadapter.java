package com.brandsh.tiaoshi.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FMadapter extends FragmentPagerAdapter{
private List<Fragment>list;
	public FMadapter(FragmentManager fm) {
		super(fm);
		
	}
	public FMadapter(FragmentManager fm,List<Fragment> list) {
		super(fm);
		this.list=list;
		
		
	}


	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

}
