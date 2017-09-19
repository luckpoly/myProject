package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.AddressManagerAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.AddHistoryAddressModel;
import com.goodfood86.tiaoshi.order121Project.model.CommonResultInfoModel;
import com.goodfood86.tiaoshi.order121Project.model.EditAddressModel;
import com.goodfood86.tiaoshi.order121Project.model.HistoryAddressListModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.widget.ProgressHUD;
import com.goodfood86.tiaoshi.order121Project.widget.TitleBarView;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/5.
 */
public class AddressManagerActivity extends Activity implements View.OnClickListener{
    @ViewInject(R.id.title_bar)
    private LinearLayout title_bar;
    private TitleBarView titleBarView;
    @ViewInject(R.id.address_PTRListView)
    private PullToRefreshListView address_PTRListView;
    public static int GO_EDITADDRESS=2;
    private List<HistoryAddressListModel.DataEntity.ListEntity> entitylist=new ArrayList<>();
    public static int ADDRESSMANAGER=1;
    private Boolean isRefresh=false;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg!=null){
                switch (msg.what){
                    case 1:
                        address_PTRListView.onRefreshComplete();
                        dialog.dismiss();
                        HistoryAddressListModel historyAddressListModel= (HistoryAddressListModel) msg.obj;
                        if (historyAddressListModel.getRespCode()==0) {
                            List<HistoryAddressListModel.DataEntity.ListEntity> newData = historyAddressListModel.getData().getList();
                            if (isRefresh){
                                entitylist.clear();
                                isRefresh=false;
                            }
                            entitylist.addAll(newData);
                            Log.e("size",entitylist.size()+"");
                            addressMangerAdapter.notifyDataSetChanged();
                        }
                        break;
                    case 2:
                        dialog.dismiss();
                        AddHistoryAddressModel addHistoryAddressModel= (AddHistoryAddressModel) msg.obj;
                        if (addHistoryAddressModel.getRespCode()==0){
                            isRefresh=true;
                            initData();
                        }else {
                            ToastUtil.showShort(AddressManagerActivity.this,addHistoryAddressModel.getRespMsg());
                        }
                        break;
                    case 3:
                        dialog.dismiss();
                        CommonResultInfoModel commonResultInfoModel= (CommonResultInfoModel) msg.obj;
                        if (commonResultInfoModel.getRespCode()==0){
                            isRefresh=true;
                            initData();
                        }else {
                            ToastUtil.showShort(AddressManagerActivity.this,commonResultInfoModel.getRespMsg());
                        }
                        break;
                    case 4:
                        dialog.dismiss();
                        EditAddressModel editAddressModel= (EditAddressModel) msg.obj;
                        if (editAddressModel.getRespCode()==0){
                            isRefresh=true;
                            initData();
                        }else {
                            ToastUtil.showShort(AddressManagerActivity.this,editAddressModel.getRespMsg());
                        }
                        break;
                    case 100:
                        address_PTRListView.onRefreshComplete();
                        break;
                }
            }
        }
    };
    private AddressManagerAdapter addressMangerAdapter;
    public  ProgressHUD dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addressmanager);
        ViewUtils.inject(this);
        initTitleBar();
        initView();
        initAdapter();
        initIndicator();
        setListenerToPTRListView();
        initData();
    }

    private void initData() {
        dialog.show();
        RequestParams requestParams=new RequestParams();
        requestParams.addBodyParameter("token", Order121Application.globalLoginModel.getData().getToken());
        Order121Application.getGlobalHttpUtils().send(HttpRequest.HttpMethod.POST, G.Host.HISTORY_ADDRESSLIST,requestParams,new MyRequestCallBack(this,handler,1,new HistoryAddressListModel()));
    }

    private void initView() {
        dialog= ProgressHUD.show(this,"正在加载中",false,null);
    }

    private void initTitleBar() {
        titleBarView=new TitleBarView(this,title_bar,"地址管理");
        titleBarView.nav_right.setVisibility(View.VISIBLE);
        titleBarView.nav_right.setImageResource(R.mipmap.friend_fabu);
        titleBarView.nav_right.setOnClickListener(this);
    }
    private void initAdapter() {
        addressMangerAdapter=new AddressManagerAdapter(entitylist,this,handler,dialog);
        address_PTRListView.setAdapter(addressMangerAdapter);
    }
    private void initIndicator() {
        ILoadingLayout startLabels = address_PTRListView
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在载入...");// 刷新时
        startLabels.setReleaseLabel("放开以刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = address_PTRListView.getLoadingLayoutProxy(
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

    private void setListenerToPTRListView() {
        address_PTRListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                setRefreshDate(refreshView);
                isRefresh = true;
                initData();
                handler.sendEmptyMessageDelayed(100, 3000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                setRefreshDate(refreshView);

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v!=null){
            switch (v.getId()){
                case R.id.nav_right:
                    Intent intent=new Intent(this,SelectAddressMapActivity.class);
                    intent.putExtra("what","3");
                    startActivityForResult(intent,ADDRESSMANAGER);
                    break;
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ADDRESSMANAGER&&resultCode==RESULT_OK){
            addAddress(data);
        }else if (requestCode==GO_EDITADDRESS&&resultCode==RESULT_OK){
            editAddress(data);
        }
    }

    private void addAddress(Intent data) {
        dialog.show();
        RequestParams requestParams=new RequestParams();
        requestParams.addBodyParameter("city",data.getStringExtra("city"));
        requestParams.addBodyParameter("area",data.getStringExtra("street"));
        requestParams.addBodyParameter("address",data.getStringExtra("title"));
        requestParams.addBodyParameter("lng",data.getDoubleExtra("lng",0.0)+"");
        requestParams.addBodyParameter("lat",data.getDoubleExtra("lat",0.0)+"");
        requestParams.addBodyParameter("addressDetail",data.getStringExtra("suaddress"));
        requestParams.addBodyParameter("phone",data.getStringExtra("phoneNo"));
        requestParams.addBodyParameter("token", Order121Application.globalLoginModel.getData().getToken());
        Order121Application.getGlobalHttpUtils().send(HttpRequest.HttpMethod.POST, G.Host.ADD_HISTORYADDRESS, requestParams, new MyRequestCallBack(this, handler, 2, new AddHistoryAddressModel()));
    }
    private void editAddress(Intent data) {
        dialog.show();
        RequestParams requestParams=new RequestParams();
        requestParams.addBodyParameter("id",entitylist.get(Integer.parseInt(data.getStringExtra("position"))).getId()+"");
        requestParams.addBodyParameter("city",data.getStringExtra("city"));
        requestParams.addBodyParameter("area",data.getStringExtra("street"));
        requestParams.addBodyParameter("address",data.getStringExtra("title"));
        requestParams.addBodyParameter("lng",data.getDoubleExtra("lng",0.0)+"");
        requestParams.addBodyParameter("lat",data.getDoubleExtra("lat",0.0)+"");
        requestParams.addBodyParameter("addressDetail",data.getStringExtra("suaddress"));
        requestParams.addBodyParameter("phone",data.getStringExtra("phoneNo"));
        requestParams.addBodyParameter("token",Order121Application.globalLoginModel.getData().getToken());
        Log.e("id",entitylist.get(data.getIntExtra("position",0)).getId()+"  "+data.getIntExtra("position",0));
        Order121Application.getGlobalHttpUtils().send(HttpRequest.HttpMethod.POST, G.Host.EDIT_HISTORYADDRESS, requestParams, new MyRequestCallBack(this, handler, 4, new EditAddressModel()));
    }
    public void onResume() {
        super.onResume();
    }
    public void onPause() {
        super.onPause();
        //已删
    }
}
