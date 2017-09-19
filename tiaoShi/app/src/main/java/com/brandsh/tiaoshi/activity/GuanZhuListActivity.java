package com.brandsh.tiaoshi.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.GuanZhuListItemAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.GuanZhuJsonData;
import com.brandsh.tiaoshi.model.GuanZhuListJsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.brandsh.tiaoshi.widget.SelfPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mingle.widget.ShapeLoadingDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;

public class GuanZhuListActivity extends FragmentActivity implements View.OnClickListener {
    @ViewInject(R.id.guanzhu_list_ivBack)
    ImageView guanzhu_list_ivBack;
    @ViewInject(R.id.guanzhu_list_rlNoItem)
    RelativeLayout guanzhu_list_rlNoItem;
    @ViewInject(R.id.guanzhu_list_ptrListView)
    SelfPullToRefreshListView guanzhu_list_ptrListView;

    private HashMap listMap;
    private ShapeLoadingDialog loadingDialog;
    private List<GuanZhuListJsonData.DataBean.ListBean> resList;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private AMapLocationListener aMapLocationListener;
    private AlertDialog.Builder locationBuilder;
    private double latitude;
    private double longitude;
    private String page;
    private Toast toast;
    private GuanZhuListItemAdapter guanZhuListItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_zhu_list);
        //沉浸状态栏
        AppUtil.Setbar(this);

        ViewUtils.inject(this);

        init();

        initLocation();

        setListenerToptrListView();
    }

    private void setListenerToptrListView() {
        guanzhu_list_ptrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GuanZhuListActivity.this, StoreDetailActivity.class);
                intent.putExtra("shop_id", resList.get(position - 1).getShopId());
                intent.putExtra("shop_name", resList.get(position - 1).getName());
                intent.putExtra("min_cost", resList.get(position - 1).getFreeSend());
                startActivity(intent);
            }
        });
        guanzhu_list_ptrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = "1";
                initLocation();
                handler.sendEmptyMessageDelayed(150, 5000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                OkHttpManager.postAsync(G.Host.GUANZHU_LIST + "?page=" + page, listMap, new MyCallBack(2, GuanZhuListActivity.this, new GuanZhuListJsonData(), handler));
                handler.sendEmptyMessageDelayed(150, 5000);
            }
        });
    }

    private void init() {
        listMap = new HashMap();
        guanzhu_list_ptrListView.setMode(PullToRefreshBase.Mode.BOTH);
        guanzhu_list_rlNoItem.setVisibility(View.GONE);
        guanzhu_list_ptrListView.setVisibility(View.GONE);
        loadingDialog = ProgressHUD.show(this, "努力加载中...");
        loadingDialog.show();
        page = "1";
        toast = Toast.makeText(this, "定位失败, 请稍后再试", Toast.LENGTH_SHORT);
        locationBuilder = new AlertDialog.Builder(this).setTitle("系统提示");
        guanzhu_list_ivBack.setOnClickListener(this);
    }

    private void initLocation() {
        //初始化定位
        aMapLocationClient = new AMapLocationClient(this);
        //初始化定位参数
        aMapLocationClientOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        aMapLocationClientOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        aMapLocationClientOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        aMapLocationClientOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        aMapLocationClientOption.setMockEnable(true);
        //给定位客户端对象设置定位参数
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        //声明定位回调监听器
        aMapLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        latitude = amapLocation.getLatitude();
                        longitude = amapLocation.getLongitude();
                        Log.e("经度", longitude + "");
                        Log.e("纬度", latitude + "");
                        aMapLocationClient.onDestroy();
                        listMap.clear();
                        listMap.put("token", TiaoshiApplication.globalToken);
                        listMap.put("lat", latitude + "");
                        listMap.put("lng", longitude + "");
                        listMap.put("actReq", "123456");
                        listMap.put("actTime", System.currentTimeMillis() / 1000 + "");
                        String sign = SignUtil.getSign(listMap);
                        listMap.put("sign", Md5.toMd5(sign));
                        OkHttpManager.postAsync(G.Host.GUANZHU_LIST + "?page=" + page, listMap, new MyCallBack(1, GuanZhuListActivity.this, new GuanZhuListJsonData(), handler));

                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                        if (amapLocation.getErrorCode() == 2) {
                            locationBuilder.setMessage("wifi信息不足,请使用gprs定位").setPositiveButton("确定", null).create().show();
                        } else if (amapLocation.getErrorCode() == 4) {
                            locationBuilder.setMessage("网络连接失败,无法启用定位").setPositiveButton("确定", null).create().show();
                        } else if (amapLocation.getErrorCode() == 9) {
                            locationBuilder.setMessage("定位初始化失败，请重新启动定位").setPositiveButton("确定", null).create().show();
                        } else if (amapLocation.getErrorCode() == 11) {
                            locationBuilder.setMessage("基站信息错误").setPositiveButton("确定", null).create().show();
                        } else if (amapLocation.getErrorCode() == 12) {
                            locationBuilder.setMessage("缺少定位权限,请在设备中开启GPS并允许定位").setPositiveButton("确定", null).create().show();
                        } else {
                            toast.show();

                        }
                    }
                }
            }
        };
        //设置定位回调监听
        aMapLocationClient.setLocationListener(aMapLocationListener);
        aMapLocationClient.startLocation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guanzhu_list_ivBack:
                finish();
                break;
        }
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                        loadingDialog.dismiss();

                    guanzhu_list_ptrListView.onRefreshComplete();
                    GuanZhuListJsonData guanZhuListJsonData = (GuanZhuListJsonData) msg.obj;
                    if (guanZhuListJsonData != null) {
                        if (guanZhuListJsonData.getRespCode().equals("SUCCESS")) {
                            resList = guanZhuListJsonData.getData().getList();
                            if (resList.size()>=Integer.parseInt(guanZhuListJsonData.getData().getTotalCount())){
                                guanzhu_list_ptrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }else {
                                guanzhu_list_ptrListView.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                            page = guanZhuListJsonData.getData().getNextPage()+"";
                            if (resList.size() == 0) {
                                guanzhu_list_rlNoItem.setVisibility(View.VISIBLE);
                                guanzhu_list_ptrListView.setVisibility(View.GONE);
                            } else {
                                guanzhu_list_rlNoItem.setVisibility(View.GONE);
                                guanzhu_list_ptrListView.setVisibility(View.VISIBLE);
                                guanZhuListItemAdapter = new GuanZhuListItemAdapter(resList, GuanZhuListActivity.this, handler);
                                guanzhu_list_ptrListView.setAdapter(guanZhuListItemAdapter);
                                guanZhuListItemAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(GuanZhuListActivity.this, guanZhuListJsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 2:
                    guanzhu_list_ptrListView.onRefreshComplete();
                    GuanZhuListJsonData guanZhuListJsonData1 = (GuanZhuListJsonData) msg.obj;
                    if (guanZhuListJsonData1 != null) {
                        if (guanZhuListJsonData1.getRespCode().equals("SUCCESS")) {
                            resList.addAll(guanZhuListJsonData1.getData().getList());
                            guanZhuListItemAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(GuanZhuListActivity.this, guanZhuListJsonData1.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 3:
                    GuanZhuJsonData guanZhuJsonData = (GuanZhuJsonData) msg.obj;
                    if (guanZhuJsonData != null) {
                        if (guanZhuJsonData.getRespCode().equals("SUCCESS")) {
                            Toast.makeText(GuanZhuListActivity.this, "取消关注成功", Toast.LENGTH_SHORT).show();
                            loadingDialog.show();
                            page = "1"; 
                            initLocation();
                        }
                    }
                    break;
                case 150:
                    guanzhu_list_ptrListView.onRefreshComplete();
                    break;
                case 200:
                    guanzhu_list_ptrListView.onRefreshComplete();
                    break;
                case 300:
                    guanzhu_list_ptrListView.onRefreshComplete();
                    break;
            }
        }
    };

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
