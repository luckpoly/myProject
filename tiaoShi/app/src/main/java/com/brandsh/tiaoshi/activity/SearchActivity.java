package com.brandsh.tiaoshi.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.SearchAdapter;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.fragment.ProductDetailFragment;
import com.brandsh.tiaoshi.model.SearchJsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.StatusBarUtil;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.brandsh.tiaoshi.widget.SelfPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mingle.widget.ShapeLoadingDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class SearchActivity extends FragmentActivity implements View.OnClickListener {
    @ViewInject(R.id.search_ivBack)
    ImageView search_ivBack;
    @ViewInject(R.id.search_et)
    EditText search_et;
    @ViewInject(R.id.search_tvSearch)
    TextView search_tvSearch;
    @ViewInject(R.id.tv_youlike)
    TextView tv_youlike;
    @ViewInject(R.id.search_ptrListView)
    SelfPullToRefreshListView search_ptrListView;


    private HashMap requestMap;
    private String searchContent="";
    private List<SearchJsonData.DataBean.ListBean> resList;
    private String page;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private AMapLocationListener aMapLocationListener;
    private double latitude;
    private double longitude;
    private ShapeLoadingDialog loadingDialog;
    private Toast toast;
    private AlertDialog.Builder locationBuilder;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        StatusBarUtil.StatusBarLightMode(this);
        //沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);

        init();
        initLocation();
        setListenerToPTRListView();
    }

    private void init() {
        toast = Toast.makeText(this, "定位失败, 请稍后再试", Toast.LENGTH_SHORT);
        locationBuilder = new AlertDialog.Builder(this).setTitle("系统提示");
        loadingDialog = ProgressHUD.show(this, "努力加载中...");
        search_ivBack.setOnClickListener(this);
        search_tvSearch.setOnClickListener(this);
        search_ptrListView.setMode(PullToRefreshBase.Mode.BOTH);
        search_ptrListView.setVisibility(View.GONE);

        resList = new LinkedList();
        page = "1";
        searchAdapter = new SearchAdapter(this, resList);
        search_ptrListView.setAdapter(searchAdapter);
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
                        requestMap=new HashMap();
                        requestMap.put("goodsName",searchContent);
                        requestMap.put("lat", latitude + "");
                        requestMap.put("lng", longitude + "");
                        requestMap.put("actReq", SignUtil.getRandom());
                        requestMap.put("actTime",System.currentTimeMillis()/1000+"");
                        String sign= SignUtil.getSign(requestMap);
                        requestMap.put("sign", Md5.toMd5(sign));
                        OkHttpManager.postAsync(G.Host.FIND,requestMap,new MyCallBack(1, SearchActivity.this, new SearchJsonData(), handler));




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

    //给列表设置监听
    private void setListenerToPTRListView() {
        search_ptrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String goods_id = resList.get(position - 1).getGoodsId();
                Intent intent = FCActivity.getFCActivityIntent(SearchActivity.this, ProductDetailFragment.class);
                intent.putExtra("goods_id", goods_id);
                intent.putExtra("goods_name", resList.get(position-1).getGoodsName());
                intent.putExtra("shop_id", resList.get(position-1).getShopId());
                intent.putExtra("shop_name", resList.get(position-1).getName());
                intent.putExtra("min_cost", resList.get(position-1).getFreeSend());
                intent.putExtra("is_new", "");
                startActivity(intent);
            }
        });

        search_ptrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = "1";
                initLocation();
                handler.sendEmptyMessageDelayed(150, 5000);
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                    OkHttpManager.postAsync(G.Host.FIND + "?page=" + page, requestMap, new MyCallBack(2, SearchActivity.this, new SearchJsonData(), handler));
                    handler.sendEmptyMessageDelayed(150, 5000);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_tvSearch:
                //开始搜索
                if (TextUtils.isEmpty(search_et.getText().toString())){
                    Toast.makeText(SearchActivity.this, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                searchContent = search_et.getText().toString();
                tv_youlike.setVisibility(View.GONE);
                initLocation();
                loadingDialog.show();
                break;
            case R.id.search_ivBack:
                finish();
                break;
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    search_ptrListView.onRefreshComplete();
                    SearchJsonData searchJsonData = (SearchJsonData) msg.obj;
                    if (searchJsonData != null) {
                        if (searchJsonData.getRespCode().equals("SUCCESS")){
                            page = searchJsonData.getData().getNextPage()+"";
                            resList.clear();
                            if (TextUtils.isEmpty(searchContent)){
                                if (searchJsonData.getData().getList().size()>=3){
                                    resList.addAll(searchJsonData.getData().getList().subList(0,3));
                                }else {
                                    resList.addAll(searchJsonData.getData().getList());
                                }
                            }else {
                                resList.addAll(searchJsonData.getData().getList());
                            }
                            if (resList.size() == 0){
                                Toast.makeText(SearchActivity.this, "没有搜索到相关商品", Toast.LENGTH_SHORT).show();
                                search_ptrListView.setVisibility(View.GONE);
                                searchAdapter.notifyDataSetChanged();
                            }else {
                                search_ptrListView.setVisibility(View.VISIBLE);
                                if (resList.size()>=Integer.parseInt(searchJsonData.getData().getTotalCount())){
                                    search_ptrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                    Toast.makeText(SearchActivity.this, "已经是最后一页了", Toast.LENGTH_SHORT).show();
                                }else {
                                    search_ptrListView.setMode(PullToRefreshBase.Mode.BOTH);
                                    if (TextUtils.isEmpty(searchContent)){
                                        search_ptrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);}
                                }
                            }
                            searchAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(SearchActivity.this, searchJsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                        loadingDialog.dismiss();

                    break;
                case 2:
                    search_ptrListView.onRefreshComplete();
                    SearchJsonData searchJsonData1 = (SearchJsonData) msg.obj;
                    if (searchJsonData1 != null) {
                        if (searchJsonData1.getRespCode().equals("SUCCESS")){
                            page = searchJsonData1.getData().getNextPage()+"";
                            resList.addAll(searchJsonData1.getData().getList());
                            if (resList.size()>=Integer.parseInt(searchJsonData1.getData().getTotalCount())){
                                search_ptrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                Toast.makeText(SearchActivity.this, "已经是最后一页了", Toast.LENGTH_SHORT).show();
                            }else {
                                search_ptrListView.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                            searchAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(SearchActivity.this, searchJsonData1.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 150:
                    search_ptrListView.onRefreshComplete();
                    break;
                case 200:
                    search_ptrListView.onRefreshComplete();
                    break;
                case 300:
                    search_ptrListView.onRefreshComplete();
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
