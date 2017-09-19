package com.goodfood86.tiaoshi.order121Project.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodfood86.tiaoshi.order121Project.R;

/**
 * Created by Administrator on 2016/7/13.
 */
public class MyFragment extends Fragment {
    View  view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.my_fragment,null);
//        initView();
        return view;
    }
}
