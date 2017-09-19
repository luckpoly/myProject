package com.goodfood86.tiaoshi.order121Project.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.FMadapter;
import com.goodfood86.tiaoshi.order121Project.fragment.Weixin_fragment;

import java.util.ArrayList;
import java.util.List;

public class ShowImageActivity extends FragmentActivity {
	private ViewPager Pager;
	String [] array;
	int No=0;
	private TextView tv_show;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showimage);
		Intent intent=getIntent();
		 array=intent.getStringArrayExtra("array");
		 No= intent.getIntExtra("No", 0);
		Pager=(ViewPager) findViewById(R.id.vp);
		tv_show=(TextView)findViewById(R.id.tv_show);
		tv_show.setText(No+1+"/"+array.length);
		initPager();
		findViewById(R.id.ib_fanhui).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
public void initPager(){
	List<Fragment>list=new ArrayList<Fragment>();
		for (int i = 0; i < array.length; i++) {
			Weixin_fragment fg1=new Weixin_fragment();
			Bundle bundle=new Bundle();
			bundle.putString("url",array[i]);
			fg1.setArguments(bundle);
			list.add(fg1);
		}
		FragmentManager manager=getSupportFragmentManager();
		FMadapter adapter=new FMadapter(manager,list);
		Pager.setAdapter(adapter);
		Pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
//				changIcon(arg0);
				tv_show.setText(arg0+1+"/"+array.length);
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		Pager.setCurrentItem(No);
	
	}

}
