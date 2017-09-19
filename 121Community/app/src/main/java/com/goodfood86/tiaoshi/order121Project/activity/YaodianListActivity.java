package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.YaodianListAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.YaodianListModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class YaodianListActivity extends Activity implements View.OnClickListener{
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.tv_action)
    private TextView tv_action;
    @ViewInject(R.id.et_select_name)
    private EditText et_select_name;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.activity_PTRListView)
    private PullToRefreshListView activity_PTRListView;
    @ViewInject(R.id.ll_no_fabubg)
    private LinearLayout ll_no_fabubg;
    private Boolean isRefresh=false;
    private List<YaodianListModel.DataBean.ListBean>  dataBean=new ArrayList<>();
    private YaodianListAdapter adapter;
    private YaodianListModel yaodianListModel;
    private String page;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    yaodianListModel= (YaodianListModel) msg.obj;
                    if ("SUCCESS".equals(yaodianListModel.getRespCode())&&null!=yaodianListModel.getData()){
                        page = yaodianListModel.getData().getNextPage() + "";
                        activity_PTRListView.onRefreshComplete();
                        List<YaodianListModel.DataBean.ListBean> newData=yaodianListModel.getData().getList();
                        if (isRefresh){
                            dataBean.clear();
                            isRefresh=false;
                        }
                        dataBean.addAll(newData);
                        adapter.notifyDataSetChanged();
                        if (dataBean.size()>0){
                            ll_no_fabubg.setVisibility(View.GONE);
                        }else {
                            ll_no_fabubg.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                case 2:

                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yaodian_list);
        ViewUtils.inject(this);
        initView();
        initData();
        initIndicator();
        setListenerToPTRListView();
    }
    private void initView(){
        nav_title.setText("24小时药店");
        nav_back.setOnClickListener(this);
        tv_action.setOnClickListener(this);
        adapter=new YaodianListAdapter(dataBean,YaodianListActivity.this,handler);
        activity_PTRListView.setAdapter(adapter);
        activity_PTRListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(YaodianListActivity.this,YaodianDetailActivity.class)
                .putExtra("id",dataBean.get(position-1).getId()));
            }
        });
    }
    private void  initData(){
        HashMap map2 = new HashMap();
        map2.put("lng", Order121Application.Lng);
        map2.put("lat", Order121Application.Lat);
        map2.put("name", et_select_name.getText().toString()+"");
        map2.put("actReq", SignUtil.getRandom());
        map2.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign2 = SignUtil.getSign(map2);
        map2.put("sign", MD5.getMD5(sign2));
        OkHttpManager.postAsync(G.Host.PHARMACY+ "?page=" + page, map2, new MyCallBack(1, this, new YaodianListModel(), handler));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_back:
                finish();
                break;
            case R.id.tv_action:
                isRefresh=true;
                page="1";
                initData();
                break;
        }
    }
    private void setListenerToPTRListView() {
        activity_PTRListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = "1";
                setRefreshDate(refreshView);
                isRefresh = true;
                initData();
                handler.sendEmptyMessageDelayed(100, 1500);
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                setRefreshDate(refreshView);
                isRefresh=false;
                initData();
            }
        });
    }
    private void initIndicator() {
        ILoadingLayout startLabels = activity_PTRListView
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在载入...");// 刷新时
        startLabels.setReleaseLabel("放开以刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = activity_PTRListView.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉加载更多...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在载入...");// 刷新时
        endLabels.setReleaseLabel("放开以刷新...");// 下来达到一定距离时，显示的提示
    }
    private void setRefreshDate(PullToRefreshBase<ListView> refreshView) {
        //放置在刷新时的监听中
        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("上次刷新 " + DateUtils.formatDateTime(
                this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL));
    }
}
