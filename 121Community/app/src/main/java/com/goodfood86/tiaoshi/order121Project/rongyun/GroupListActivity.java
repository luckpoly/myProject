package com.goodfood86.tiaoshi.order121Project.rongyun;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.GroupsListAdapter;
import com.goodfood86.tiaoshi.order121Project.model.ActivityListModel1;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;

/**
 * Created by AMing on 16/3/8.
 * Company RongCloud
 */
public class GroupListActivity extends FragmentActivity {
    @ViewInject(R.id.nav_title)
    TextView nav_title;
    @ViewInject(R.id.nav_back)
    ImageView nav_back;
    @ViewInject(R.id.group_listview)
    private PullToRefreshListView group_listview;
    private ActivityListModel1 ActivityListModel1;
    private List<ActivityListModel1.DataBean> list=new ArrayList<>();
    private Boolean isRefresh=false;
    private GroupsListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fr_group_list);
        ViewUtils.inject(this);
        initView();
//        initIndicator();
        setListenerToPTRListView();
        initData();
    }

    private void initView(){
        nav_title.setText("聊天室列表");
        group_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RongIM.getInstance().startGroupChat(GroupListActivity.this, list.get(position-1).getId(),list.get(position-1).getName());
            }
        });
        nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
        private void  initData(){
//            HashMap map=new HashMap();
//            map.put("token", Order121Application.globalLoginModel.getData().getToken());
//            map.put("apply", "YES");
//            map.put("actReq", SignUtil.getRandom());
//            map.put("actTime",System.currentTimeMillis()/1000+"");
//            String sign= SignUtil.getSign(map);
//            map.put("sign", MD5.getMD5(sign));
//            OkHttpManager.postAsync(G.Host.ACTIVITY_LIST,map,new MyCallBack(1,this,new ActivityListModel1(),handler));
            setData();
            adapter=new GroupsListAdapter(list,GroupListActivity.this);
            group_listview.setAdapter(adapter);
        }
    private void setData(){
        ActivityListModel1.DataBean bean=new ActivityListModel1.DataBean();
        bean.setId("0201");
        bean.setName("黄浦区");
        bean.setDrawable(getResources().getDrawable(R.mipmap.huang_gic));
        list.add(bean);
        ActivityListModel1.DataBean bean1=new ActivityListModel1.DataBean();
        bean1.setId("0202");
        bean1.setName("卢湾区");
        bean1.setDrawable(getResources().getDrawable(R.mipmap.lu_gic));
        list.add(bean1);
        ActivityListModel1.DataBean bean2=new ActivityListModel1.DataBean();
        bean2.setId("0203");
        bean2.setName("静安区");
        bean2.setDrawable(getResources().getDrawable(R.mipmap.jing_gic));
        list.add(bean2);
        ActivityListModel1.DataBean bean3=new ActivityListModel1.DataBean();
        bean3.setId("0204");
        bean3.setName("徐汇区");
        bean3.setDrawable(getResources().getDrawable(R.mipmap.xu_gic));
        list.add(bean3);
        ActivityListModel1.DataBean bean4=new ActivityListModel1.DataBean();
        bean4.setId("0205");
        bean4.setName("浦东区");
        bean4.setDrawable(getResources().getDrawable(R.mipmap.pu_gic));
        list.add(bean4);
        ActivityListModel1.DataBean bean5=new ActivityListModel1.DataBean();
        bean5.setId("0206");
        bean5.setName("长宁区");
        bean5.setDrawable(getResources().getDrawable(R.mipmap.chang_gic));
        list.add(bean5);
        ActivityListModel1.DataBean bean6=new ActivityListModel1.DataBean();
        bean6.setId("0207");
        bean6.setName("虹口区");
        bean6.setDrawable(getResources().getDrawable(R.mipmap.hong_gic));
        list.add(bean6);
        ActivityListModel1.DataBean bean7=new ActivityListModel1.DataBean();
        bean7.setId("0208");
        bean7.setName("杨浦区");
        bean7.setDrawable(getResources().getDrawable(R.mipmap.yang_gic));
        list.add(bean7);

        ActivityListModel1.DataBean bean8=new ActivityListModel1.DataBean();
        bean8.setId("0209");
        bean8.setName("普陀区");
        bean8.setDrawable(getResources().getDrawable(R.mipmap.pu_gic));
        list.add(bean8);
        ActivityListModel1.DataBean bean9=new ActivityListModel1.DataBean();
        bean9.setId("02010");
        bean9.setName("闸北区");
        bean9.setDrawable(getResources().getDrawable(R.mipmap.zha_gic));
        list.add(bean9);
        ActivityListModel1.DataBean bean10=new ActivityListModel1.DataBean();
        bean10.setId("02011");
        bean10.setName("闵行区");
        bean10.setDrawable(getResources().getDrawable(R.mipmap.min_gic));
        list.add(bean10);
        ActivityListModel1.DataBean bean11=new ActivityListModel1.DataBean();
        bean11.setId("02012");
        bean11.setName("宝山区");
        bean11.setDrawable(getResources().getDrawable(R.mipmap.bao_gic));
        list.add(bean11);
        ActivityListModel1.DataBean bean12=new ActivityListModel1.DataBean();
        bean12.setId("02013");
        bean12.setName("青浦区");
        bean12.setDrawable(getResources().getDrawable(R.mipmap.ping_gic));
        list.add(bean12);
        ActivityListModel1.DataBean bean13=new ActivityListModel1.DataBean();
        bean13.setId("02014");
        bean13.setName("嘉定区");
        bean13.setDrawable(getResources().getDrawable(R.mipmap.jia_gic));
        list.add(bean13);
        ActivityListModel1.DataBean bean14=new ActivityListModel1.DataBean();
        bean14.setId("02015");
        bean14.setName("奉贤区");
        bean14.setDrawable(getResources().getDrawable(R.mipmap.feng_gic));
        list.add(bean14);
        ActivityListModel1.DataBean bean15=new ActivityListModel1.DataBean();
        bean15.setId("02016");
        bean15.setName("南汇区");
        bean15.setDrawable(getResources().getDrawable(R.mipmap.nan_gic));
        list.add(bean15);
        ActivityListModel1.DataBean bean16=new ActivityListModel1.DataBean();
        bean16.setId("02017");
        bean16.setName("崇明区");
        bean16.setDrawable(getResources().getDrawable(R.mipmap.chong_gic));
        list.add(bean16);
        ActivityListModel1.DataBean bean17=new ActivityListModel1.DataBean();
        bean17.setId("02018");
        bean17.setName("金山区");
        bean17.setDrawable(getResources().getDrawable(R.mipmap.jin_gic));
        list.add(bean17);
        ActivityListModel1.DataBean bean18=new ActivityListModel1.DataBean();
        bean18.setId("02019");
        bean18.setName("松江区");
        bean18.setDrawable(getResources().getDrawable(R.mipmap.song_gic));
        list.add(bean18);
    }


    private void setListenerToPTRListView() {
        group_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                setRefreshDate(refreshView);
//                isRefresh = true;
//                initData();
                handler.sendEmptyMessageDelayed(100, 2000);
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                setRefreshDate(refreshView);
            }
        });
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    ActivityListModel1= (ActivityListModel1) msg.obj;
                    if ("SUCCESS".equals(ActivityListModel1.getRespCode())&&null!=ActivityListModel1.getData()){
                        group_listview.onRefreshComplete();
                        List<ActivityListModel1.DataBean> newData=ActivityListModel1.getData();
                        if (isRefresh){
                            list.clear();
                            isRefresh=false;
                        }
                        list.addAll(newData);
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case 100:
                    group_listview.onRefreshComplete();
                    break;
            }
        }
    };
    private void initIndicator() {
        ILoadingLayout startLabels = group_listview
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在载入...");// 刷新时
        startLabels.setReleaseLabel("放开以刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = group_listview.getLoadingLayoutProxy(
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
