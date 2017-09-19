package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.ChooseCityAdapter;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.widget.TitleBarView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/1.
 */
public class ChooseCityActivity extends Activity{
    @ViewInject(R.id.title_bar)
    private LinearLayout title_bar;
    private TitleBarView titleBarView;
    @ViewInject(R.id.gv_choose_city)
    private GridView gv_choose_city;
    private String[] cityList;
    private ChooseCityAdapter madapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosecity);
        ViewUtils.inject(this);
        initData();
        initTitleBar();
        initAdapter();
        initData2();
        initListener();
    }
    public void onResume() {
        super.onResume();
        //友盟统计
       //已删
    }
    public void onPause() {
        super.onPause();
        //友盟统计
        //已删
    }

    private void initTitleBar() {
        titleBarView=new TitleBarView(this,title_bar,"选择城市");
    }

    private void initData2() {
        for (int j=0;j<cityList.length;j++){
            if (getIntent().getStringExtra("mCurrentCity").equals(cityList[j])){
                madapter.setSelectItem(j);
                Log.e(")))",getIntent().getStringExtra("mCurrentCity"));
                madapter.notifyDataSetInvalidated();
            }
        }
    }

    private void initListener() {
        gv_choose_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    madapter.setSelectItem(position);
                    madapter.notifyDataSetInvalidated();
                    Intent intent=new Intent();
                    intent.putExtra("CurrentCity",cityList[position]);
                    setResult(RESULT_OK,intent);
                    finish();
                }else {
                    ToastUtil.showShort(ChooseCityActivity.this,"该城市暂未开通");
                }

            }
        });
    }

    private void initData() {
        String cityString=getIntent().getStringExtra("city");
        Log.e("citySteing",cityString);
        cityList=cityString.split("\\|");
    }

    private void initAdapter() {
        madapter=new ChooseCityAdapter(cityList,ChooseCityActivity.this);
        gv_choose_city.setAdapter(madapter);

    }
}
