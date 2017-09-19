package com.brandsh.tiaoshi.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;

import java.util.List;
import java.util.Map;

public class Weixin_fragment extends Fragment {
	
	private ListView lv;
	private View view;
	private List<Map<String, Object>> list;
	String url="";
	public Weixin_fragment() {
		super();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 view=inflater.inflate(R.layout.fragment_weixin, null);
		ImageView img=(ImageView)view.findViewById(R.id.im_showimage);
		Bundle bundle=getArguments();
		//判断需写
		if(bundle!=null)
		{
			url=bundle.getString("url");
		}
		TiaoshiApplication.getGlobalBitmapUtils().display(img, url);
		return view;
	}
	
	
	
	
	
	
	}
