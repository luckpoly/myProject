package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.ActivityListAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.ActivityListModel;
import com.goodfood86.tiaoshi.order121Project.model.CreateActivityModel;
import com.goodfood86.tiaoshi.order121Project.model.GroupUserQueryModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/8/1.
 */
public class LlqListActivity extends Activity  implements View.OnClickListener{
    @ViewInject(R.id.activity_PTRListView)
    private PullToRefreshListView activity_PTRListView;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    private Boolean isRefresh=false;
    private List<ActivityListModel.DataBean.ListBean>  dataBean=new ArrayList<>();
    private ActivityListAdapter adapter;
    private ActivityListModel activityListModel;
    String mPage="1";

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    activityListModel= (ActivityListModel) msg.obj;
                    if ("SUCCESS".equals(activityListModel.getRespCode())&&null!=activityListModel.getData()){
                        activity_PTRListView.onRefreshComplete();
                        mPage=activityListModel.getData().getNextPage()+"";
                        List<ActivityListModel.DataBean.ListBean> newData=activityListModel.getData().getList();
                        if (isRefresh){
                            dataBean.clear();
                            isRefresh=false;
                        }
                        dataBean.addAll(newData);
                        if (dataBean.size()>=Integer.parseInt(activityListModel.getData().getTotalCount())){
                            activity_PTRListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            ToastUtil.showShort(LlqListActivity.this,"已经是最后一页了");
                        }else {
                            activity_PTRListView.setMode(PullToRefreshBase.Mode.BOTH);
                        }
                        adapter.notifyDataSetChanged();
                        String id=getIntent().getStringExtra("id");
                        if (!TextUtils.isEmpty(id)){
                            for (int i = 0; i < dataBean.size(); i++) {
                                if (id.equals(dataBean.get(i).getId())){
                                    activity_PTRListView.getRefreshableView().setSelection(i+1);
                                }
                            }
                        }
                    }
                    break;
                case 2:
                    CreateActivityModel createActivityModel= (CreateActivityModel) msg.obj;
                    if ("SUCCESS".equals(createActivityModel.getRespCode())){
                        ToastUtil.showShort(LlqListActivity.this,"报名成功");
                        isRefresh = true;
                        initData();
                    }else {
                        ToastUtil.showShort(LlqListActivity.this,createActivityModel.getRespMsg());
                    }
                    break;
                case 4:
                    GroupUserQueryModel model= (GroupUserQueryModel) msg.obj;
                    if (model.getCode()==200){
                        Log.e("sssssssssssss","加群成功");
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llq_list);
        ViewUtils.inject(this);
        initView();
        initData();
        initIndicator();
        setListenerToPTRListView();
    }
   private void initView(){
        nav_title.setText("旅游活动");
       nav_back.setOnClickListener(this);
       adapter=new ActivityListAdapter(dataBean,LlqListActivity.this,handler);
       activity_PTRListView.setAdapter(adapter);
       activity_PTRListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent=new Intent(LlqListActivity.this, LlqDateilsActivity.class);
               intent.putExtra("id",activityListModel.getData().getList().get((int)id).getId());
               startActivity(intent);
           }
       });
       ListView listView=activity_PTRListView.getRefreshableView();
       listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               return false;
           }
       });
    }
    private void  initData(){
        HashMap map=new HashMap();
        map.put("token", Order121Application.globalLoginModel.getData().getToken());
        map.put("page",mPage);
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime",System.currentTimeMillis()/1000+"");
        String sign= SignUtil.getSign(map);
        map.put("sign", MD5.getMD5(sign));
        OkHttpManager.postAsync(G.Host.ACTIVITY_LIST,map,new MyCallBack(1,this,new ActivityListModel(),handler));
    }
    private void setListenerToPTRListView() {
        activity_PTRListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                setRefreshDate(refreshView);
                isRefresh = true;
                mPage="1";
                initData();
                handler.sendEmptyMessageDelayed(100, 1500);
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                setRefreshDate(refreshView);
                initData();
                handler.sendEmptyMessageDelayed(100, 1500);
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
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_back:
                finish();
                break;
        }
    }
}
