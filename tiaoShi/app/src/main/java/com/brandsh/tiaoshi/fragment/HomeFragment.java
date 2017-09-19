package com.brandsh.tiaoshi.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.activity.BannerDetailActivity;
import com.brandsh.tiaoshi.activity.FCActivity;
import com.brandsh.tiaoshi.activity.JuiceActivity;
import com.brandsh.tiaoshi.activity.JuiceMonthActivity;
import com.brandsh.tiaoshi.activity.JuiceOrderDetailActivity;
import com.brandsh.tiaoshi.activity.LocationActivity;
import com.brandsh.tiaoshi.activity.MainActivity;
import com.brandsh.tiaoshi.activity.QiangXianActivity;
import com.brandsh.tiaoshi.activity.SearchActivity;
import com.brandsh.tiaoshi.activity.StoreDetailActivity;
import com.brandsh.tiaoshi.activity.StoreDetailCSActivity;
import com.brandsh.tiaoshi.activity.StoreListCSActivity;
import com.brandsh.tiaoshi.activity.TopUpActivity;
import com.brandsh.tiaoshi.adapter.HomeFLGridviewAdapter;
import com.brandsh.tiaoshi.adapter.HomeStoreListAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.CityModel;
import com.brandsh.tiaoshi.model.HomeBannerJsonData;
import com.brandsh.tiaoshi.model.HomeCategoryGVModel;
import com.brandsh.tiaoshi.model.HomeStoreModel1;
import com.brandsh.tiaoshi.model.JuiceOrderListdata;
import com.brandsh.tiaoshi.model.ShareShopIdModel;
import com.brandsh.tiaoshi.model.UpdateVersionModel;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.LogUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SPUtil;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.brandsh.tiaoshi.versionUpdate.DownLoadService;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.brandsh.tiaoshi.widget.SelfPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lidroid.xutils.ViewUtils;
import com.mingle.widget.ShapeLoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
/**
 * Created by libokang on 15/9/2.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private ConvenientBanner convenient_banner;
    private RelativeLayout home_rlQiangXian;
    private SelfPullToRefreshListView pullToRefreshListView;
    private View home_headview;
    private View home_footerview;
    private TextView home_tvLocation;
    private RelativeLayout home_rlSearch;
    private RelativeLayout rl_first;
    private RelativeLayout rl_tejia;
    private RelativeLayout rl_coupon;
    private RelativeLayout rl_xinping;
    private LinearLayout ll_location;
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private TextView tv_4;
    private TextView tv_5;
    private TextView tv_6;
    private ShapeLoadingDialog loadingDialog;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationListener aMapLocationListener;
    private AMapLocationClientOption aMapLocationClientOption;
    private LatLonPoint latLonPoint;
    private GeocodeSearch geocoderSearch;
    private double latitude;
    private double longitude;
    private List<HomeStoreModel1.DataBean.ListBean> resList;
    private HomeStoreListAdapter homeStoreListAdapter;
    private String page;
    private HomeStoreModel1.DataBean dataEntity;
    private Toast toast;
    private List<HomeBannerJsonData.DataBean.DocsBean> picList;
    private List<String> picUrlList;
    private AlertDialog.Builder locationBuilder;
    private MyBroadcastReceiver myBroadcastReceiver;
    private List<String> urlList;
    private List<String> titleList;
    private HashMap<String, String> map1;
    private HashMap<String, String> map;
    private  UpdateVersionModel updateVersionModel;
    private Dialog dialog2;
    private int ACCESS_FINE_LOCATION_CODE=2;
    private ImageView iv_breaktop;
    private TextView tv_line;
    private TextView tv_jiameng;
    private JuiceOrderListdata orderListJsondata1;
    private boolean isfenxiang=true;
    private GridView gv_group;
    HomeFLGridviewAdapter homeFLGridviewAdapter;
    List<HomeCategoryGVModel.DataBean> reDataBeen;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.home_fragment, null);
            ViewUtils.inject(this, rootView);
            ((MainActivity) getActivity()).tab_host.setCurrentTab(0);
            home_headview = inflater.inflate(R.layout.home_headview, null);
            initView();
            initAddress();
            aotoUpdate();
            getCityData();

        } else {
            if (rootView.getParent() != null) {
                ((ViewGroup) rootView.getParent()).removeView(rootView);
            }
        }
        if (TiaoshiApplication.isLogin&&isfenxiang){
            isFenxiang();
        }
        return rootView;
    }
    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("changeCount".equals(intent.getAction())) {
                if (TiaoshiApplication.diyShoppingCartJsonData.getShop_id() == null) {
                    for (int i = 0; i < resList.size(); i++) {
                        resList.get(i).setGoods_sc_count(0);
                    }
                    homeStoreListAdapter.notifyDataSetChanged();
                    return;
                } else {
                    for (int i = 0; i < resList.size(); i++) {
                        if (resList.get(i).getShopId().equals(TiaoshiApplication.diyShoppingCartJsonData.getShop_id())) {
                            resList.get(i).setGoods_sc_count(TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_count());
                            homeStoreListAdapter.notifyDataSetChanged();
                            return;
                        }
                    }
                }
            } else if ("clearCount".equals(intent.getAction())) {
                for (int i = 0; i < resList.size(); i++) {
                    resList.get(i).setGoods_sc_count(0);
                }
                homeStoreListAdapter.notifyDataSetChanged();
            }else if ("changeAddress".equals(intent.getAction())){
                TiaoshiApplication.Lat=intent.getStringExtra("lat");
                TiaoshiApplication.Lng=intent.getStringExtra("lng");
                LogUtil.e(TiaoshiApplication.Lat+ "--"+TiaoshiApplication.Lng);
                home_tvLocation.setText(intent.getStringExtra("address"));
                SPUtil.setSP("LocationLat",TiaoshiApplication.Lat);
                SPUtil.setSP("LocationLng",TiaoshiApplication.Lng);
                SPUtil.setSP("LocationAdds",intent.getStringExtra("address"));
                initData();
            }
        }
    }
    private void initAddress(){
        String adds=SPUtil.getSP("LocationAdds","");
        String lat=SPUtil.getSP("LocationLat","");
        String lng=SPUtil.getSP("LocationLng","");
        if (TextUtils.isEmpty(adds)||TextUtils.isEmpty(lat)||TextUtils.isEmpty(lng)){
            //6.0申请地图权限
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请ACCESS_FINE_LOCATION权限
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        ACCESS_FINE_LOCATION_CODE);
            }else {
                initLocation();
            }
        }else {
            TiaoshiApplication.Lng=lng;
            TiaoshiApplication.Lat=lat;
            home_tvLocation.setText(adds);
            TiaoshiApplication.Address=adds;
            initData();
        }

        //        文件权限
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},
                    1);
        }

    }
    private void aotoUpdate() {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("appType","ANDROID");
        hashMap.put("appRole","USER");
        hashMap.put("actReq", SignUtil.getRandom());
        hashMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(hashMap);
        hashMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.UPDATEVERSION, hashMap, new MyCallBack(4,getActivity(),new UpdateVersionModel(), handler));
    }
    //获取开通城市
    private void getCityData(){
        HashMap map=new HashMap();
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.GET_CITY,map,new MyCallBack(8,getActivity(),new CityModel(),handler));
    }
    private void initView() {
        resList = new LinkedList<>();
        convenient_banner = (ConvenientBanner) home_headview.findViewById(R.id.convenient_banner);
        convenient_banner.startTurning(4000);
        home_rlQiangXian = (RelativeLayout) home_headview.findViewById(R.id.home_rlQiangXian);
        rl_coupon = (RelativeLayout) home_headview.findViewById(R.id.rl_coupon);
        rl_first = (RelativeLayout) home_headview.findViewById(R.id.rl_first);
        rl_tejia = (RelativeLayout) home_headview.findViewById(R.id.rl_tejia);
        rl_xinping = (RelativeLayout) home_headview.findViewById(R.id.rl_xinping);
        tv_1 = (TextView) home_headview.findViewById(R.id.tv_1);
        tv_2 = (TextView) home_headview.findViewById(R.id.tv_2);
        tv_3 = (TextView) home_headview.findViewById(R.id.tv_3);
        tv_4 = (TextView) home_headview.findViewById(R.id.tv_4);
        tv_5 = (TextView) home_headview.findViewById(R.id.tv_5);
        tv_6 = (TextView) home_headview.findViewById(R.id.tv_6);
        iv_breaktop=(ImageView)rootView.findViewById(R.id.iv_breaktop);
        tv_line=(TextView)rootView.findViewById(R.id.tv_line);
        ll_location=(LinearLayout)rootView.findViewById(R.id.ll_location);
        tv_jiameng=(TextView)home_headview.findViewById(R.id.tv_jiameng);
        gv_group=(GridView)home_headview.findViewById(R.id.gv_group);
       reDataBeen=new ArrayList<>();
        homeFLGridviewAdapter=new HomeFLGridviewAdapter(getContext(),reDataBeen);
        gv_group.setAdapter(homeFLGridviewAdapter);
        iv_breaktop.setOnClickListener(this);
        pullToRefreshListView = (SelfPullToRefreshListView) rootView.findViewById(R.id.home_PTRListView);
        homeStoreListAdapter = new HomeStoreListAdapter(resList, getActivity());
        pullToRefreshListView.setAdapter(homeStoreListAdapter);
        home_rlSearch = (RelativeLayout) rootView.findViewById(R.id.home_rlSearch);
        home_tvLocation = (TextView) rootView.findViewById(R.id.home_tvLocation);
        toast = Toast.makeText(getActivity(), "定位失败, 请稍后再试", Toast.LENGTH_SHORT);
        loadingDialog = ProgressHUD.show(getActivity(), "努力加载中...");
        loadingDialog.show();
        page = "1";
        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("changeCount");
        intentFilter.addAction("clearCount");
        intentFilter.addAction("changeAddress");
        getActivity().registerReceiver(myBroadcastReceiver, intentFilter);
        pullToRefreshListView.getRefreshableView().addHeaderView(home_headview);
        home_rlQiangXian.setOnClickListener(this);
        rl_coupon.setOnClickListener(this);
        rl_first.setOnClickListener(this);
        rl_tejia.setOnClickListener(this);
        rl_xinping.setOnClickListener(this);
        tv_1.setOnClickListener(this);
        tv_2.setOnClickListener(this);
        tv_3.setOnClickListener(this);
        tv_4.setOnClickListener(this);
        tv_5.setOnClickListener(this);
        tv_6.setOnClickListener(this);
        home_rlSearch.setOnClickListener(this);
        tv_jiameng.setOnClickListener(this);
        ll_location.setOnClickListener(this);
        setListenerToPTRListView();
        //获取banner图
        map = new HashMap<>();
        map.put("code", "APPIndexCarousel");
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.GET_WORD, map, new MyCallBack(3, getActivity(), new HomeBannerJsonData(), handler));
        picUrlList = new LinkedList<>();
        urlList = new LinkedList<>();
        titleList = new LinkedList<>();
        locationBuilder = new AlertDialog.Builder(getActivity()).setTitle("系统提示");
        Intent intent2 = new Intent("updateUI");
        intent2.putExtra("what_category", "shuiguo");
        getActivity().sendStickyBroadcast(intent2);

    }
    //检测有没有分享订单
    private void isFenxiang(){
        HashMap map =new HashMap();
        map.put("token",TiaoshiApplication.globalToken);
        map.put("getStatus","GET");
        map.put("actReq",SignUtil.getRandom());
        map.put("actTime",System.currentTimeMillis()/1000+"");
        String sign= SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.SHARE_GET_LIST,map,new MyCallBack(5,getContext(),new JuiceOrderListdata(),handler));


    }
    //给列表设置监听
    private void setListenerToPTRListView() {
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position - 2>resList.size()){
                    return;
                }
                String storeId = resList.get(position - 2).getShopId();
                Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                intent.putExtra("shop_id", storeId);
                intent.putExtra("shop_name", resList.get(position - 2).getName());
                intent.putExtra("min_cost", resList.get(position - 2).getFreeSend());
                startActivity(intent);
            }
        });
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = "1";
                initData();
                OkHttpManager.postAsync(G.Host.GET_WORD, map, new MyCallBack(3, getActivity(), new HomeBannerJsonData(), handler));
                handler.sendEmptyMessageDelayed(150, 1500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (dataEntity != null) {
                    page = dataEntity.getNextPage() + "";
                    OkHttpManager.postAsync(G.Host.HOME_STORE_LIST + "?page=" + page, map1, new MyCallBack(2, getActivity(), new HomeStoreModel1(), handler));
                }
                handler.sendEmptyMessageDelayed(150, 1500);
            }
        });
        pullToRefreshListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState){
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到顶部
                        if (pullToRefreshListView.getRefreshableView().getFirstVisiblePosition() == 0) {
                            iv_breaktop.setVisibility(View.GONE);
                            iv_breaktop.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.go_dismiss));
//                            tv_line.setVisibility(View.GONE);
                        }else {
                            iv_breaktop.setVisibility(View.VISIBLE);
                            iv_breaktop.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.go_show));
//                            tv_line.setVisibility(View.VISIBLE);
                        }
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        gv_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (reDataBeen.get(position).getCode()){
                    //水果
                    case "Fruit":
                        G.SP.isfalse=false;
                        ((MainActivity) getActivity()).tab_host.setCurrentTab(1);
                        Intent intent2 = new Intent("updateUI");
                        intent2.putExtra("what_category", "shuiguo");
                        getActivity().sendStickyBroadcast(intent2);
                        break;
                    //果汁
                    case "Juice":
                        G.SP.isfalse=false;
                        ((MainActivity) getActivity()).tab_host.setCurrentTab(1);
                        Intent intent3 = new Intent("updateUI");
                        intent3.putExtra("what_category", "guozhi");
                        getActivity().sendStickyBroadcast(intent3);
                        break;
                    //零食
                    case "Snacks":
                        G.SP.isfalse=false;
                        ((MainActivity) getActivity()).tab_host.setCurrentTab(1);
                        Intent intent4 = new Intent("updateUI");
                        intent4.putExtra("what_category", "lingshi");
                        getActivity().sendStickyBroadcast(intent4);
                        break;
                    //蔬菜粮油
                    case "Grain":
                        G.SP.isfalse=false;
                        ((MainActivity) getActivity()).tab_host.setCurrentTab(1);
                        Intent intent5 = new Intent("updateUI");
                        intent5.putExtra("what_category", "liangyou");
                        getActivity().sendStickyBroadcast(intent5);
                        break;
                }
            }
        });
    }
    private void initData(){
        //访问网络
        map1 = new HashMap<>();
        map1.put("actReq", SignUtil.getRandom());
        map1.put("actTime", System.currentTimeMillis() / 1000 + "");
        map1.put("lat", TiaoshiApplication.Lat);
        map1.put("lng", TiaoshiApplication.Lng);
        String sign = SignUtil.getSign(map1);
        map1.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.HOME_STORE_LIST + "?page=" + page, map1, new MyCallBack(1, getActivity(), new HomeStoreModel1(), handler));
        //获取分享店铺ID
        HashMap map1=new HashMap();
        map1.put("code", "TS02");
        map1.put("actReq", SignUtil.getRandom());
        map1.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign1 = SignUtil.getSign(map1);
        map1.put("sign", Md5.toMd5(sign1));
        Log.e("-----",sign1);
        OkHttpManager.postAsync(G.Host.GET_SHARE_ID , map1, new MyCallBack(6, getActivity(), new ShareShopIdModel(), handler));
        //获取分类
        HashMap map=new HashMap();
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign2 = SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign2));
        Log.e("-----",sign1);
        OkHttpManager.postAsync(G.Host.GOOS_CATEGORY , map, new MyCallBack(7, getActivity(), new HomeCategoryGVModel(), handler));

        //获取社区超市店铺ID 2.3.9版本废弃
//        HashMap map1=new HashMap();
//        map1.put("code", "TSCS001");
//        map1.put("actReq", SignUtil.getRandom());
//        map1.put("actTime", System.currentTimeMillis() / 1000 + "");
//        String sign2 = SignUtil.getSign(map1);
//        map1.put("sign", Md5.toMd5(sign2));
//        Log.e("-----",sign2);
//        OkHttpManager.postAsync(G.Host.GET_SHARE_ID + "?page=" + page, map1, new MyCallBack(7, getActivity(), new ShareShopIdModel(), handler));
    }
    private void initLocation() {
        //初始化定位
        aMapLocationClient = new AMapLocationClient(getActivity());
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
                        TiaoshiApplication.Lng=longitude+"";
                        TiaoshiApplication.Lat=latitude + "";
                        latLonPoint = new LatLonPoint(latitude, longitude);
                        Log.e("经度", longitude + "");
                        Log.e("纬度", latitude + "");
                        TiaoshiApplication.Address=amapLocation.getAddress();
                        home_tvLocation.setText(TiaoshiApplication.Address);
                        aMapLocationClient.onDestroy();
                        initData();
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
    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint) {
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 10,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }
    //初始化banner
    private void initBanner() {
        if (picUrlList.size() > 0) {
            convenient_banner.setPointViewVisible(true);
            convenient_banner.setPages(new CBViewHolderCreator<BannerHolderView>() {
                @Override
                public BannerHolderView createHolder() {
                    return new BannerHolderView();
                }
            }, picUrlList).setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);
            convenient_banner.setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_rlQiangXian:
                Intent intent = new Intent(getActivity(), StoreListCSActivity.class);
                startActivity(intent);
                break;
            case R.id.home_rlSearch:
                Intent intent1 = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_1:
                G.SP.isfalse=false;
                ((MainActivity) getActivity()).tab_host.setCurrentTab(1);
                Intent intent2 = new Intent("updateUI");
                intent2.putExtra("what_category", "shuiguo");
                getActivity().sendStickyBroadcast(intent2);
                break;
            case R.id.tv_2:
                G.SP.isfalse=false;
                ((MainActivity) getActivity()).tab_host.setCurrentTab(1);
                Intent intent3 = new Intent("updateUI");
                intent3.putExtra("what_category", "guozhi");
                getActivity().sendStickyBroadcast(intent3);

                break;
            case R.id.tv_3:
                G.SP.isfalse=false;
                ((MainActivity) getActivity()).tab_host.setCurrentTab(1);
                Intent intent4 = new Intent("updateUI");
                intent4.putExtra("what_category", "lingshi");
                getActivity().sendStickyBroadcast(intent4);
                break;
            case R.id.rl_coupon:
                Intent intent6=new Intent(getActivity(),JuiceMonthActivity.class);
                intent6.putExtra("URL",G.Host.PICK_LIST);
                startActivity(intent6);
                break;
            case R.id.rl_first:
                Intent intent8 = new Intent(getActivity(), QiangXianActivity.class);
                intent8.putExtra("title", "礼品套餐");
                intent8.putExtra("code", "APPGiftPackage");
                startActivity(intent8);
                break;
            case R.id.rl_tejia:
                if (TiaoshiApplication.isLogin){
                    startActivity(new Intent(getActivity(), TopUpActivity.class));
                }else {
                    startActivity(FCActivity.getFCActivityIntent(getActivity(), PhoneLoginFragment.class));
                }
                break;
            case R.id.rl_xinping:
                Intent intent9=new Intent(getActivity(), JuiceActivity.class);
                startActivity(intent9);
                break;
            case R.id.btn_cancel:
               dialog2.dismiss();
                break;
            case R.id.btn_go_update:
                getActivity().startService(new Intent(getActivity(), DownLoadService.class).putExtra("url",updateVersionModel.getData().getUpload()));
                dialog2.dismiss();
                ToastUtil.showShort(getContext(),"开始下载");
                break;
            case R.id.iv_breaktop:
                pullToRefreshListView.getRefreshableView().smoothScrollToPosition(0);
                break;
            case R.id.tv_jiameng:
                Intent intent5=new Intent(getActivity(),JuiceMonthActivity.class);
                intent5.putExtra("URL",G.Host.PROXY_GOODS);
                startActivity(intent5);
                break;
            case R.id.ll_location:
                startActivity(new Intent(getActivity(),LocationActivity.class));
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
                    HomeStoreModel1 homeStoreModel = (HomeStoreModel1) msg.obj;
                    if (homeStoreModel != null ) {
                        if (homeStoreModel.getRespCode().equals("SUCCESS")){
                            pullToRefreshListView.getRefreshableView().removeFooterView(home_footerview);
                            dataEntity = homeStoreModel.getData();
                            resList.clear();
                            resList.addAll(dataEntity.getList());
                            for (int i=0; i<resList.size();i++){
                                if (null!=TiaoshiApplication.diyShoppingCartJsonData.getShop_id()&&resList.get(i).getShopId().equals(TiaoshiApplication.diyShoppingCartJsonData.getShop_id())){
                                    resList.get(i).setGoods_sc_count(TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_count());
                                }
                            }
                            homeStoreListAdapter.notifyDataSetChanged();
                            if (resList.size()>=Integer.parseInt(homeStoreModel.getData().getTotalCount())){
                                pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//                                ToastUtil.showShort(getActivity(),"已经是最后一页了");
                            }else {
                                pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                        }else {
                            ToastUtil.showShort(getActivity(),homeStoreModel.getRespMsg());
                        }
                    }
                    break;
                case 2:
                        loadingDialog.dismiss();
                    HomeStoreModel1 homeStoreModel2 = (HomeStoreModel1) msg.obj;
                    if (homeStoreModel2 != null) {
                        if (homeStoreModel2.getRespCode().equals("SUCCESS")) {
                            dataEntity = homeStoreModel2.getData();
                            resList.addAll(homeStoreModel2.getData().getList());
                            if (resList.size() >= Integer.parseInt(homeStoreModel2.getData().getTotalCount())) {
                                pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                ToastUtil.showShort(getActivity(),"已经是最后一页了");
                            } else {
                                pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                            for (int i = 0; i < resList.size(); i++) {
                                if (resList.get(i).getShopId().equals(TiaoshiApplication.diyShoppingCartJsonData.getShop_id())) {
                                    resList.get(i).setGoods_sc_count(TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_count());
                                }
                            }
                            homeStoreListAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showShort(getActivity(),homeStoreModel2.getRespMsg());
                        }
                    }
                    break;
                case 3:
                    HomeBannerJsonData homeBannerJsonData = (HomeBannerJsonData) msg.obj;
                    if (homeBannerJsonData.getRespCode().equals("SUCCESS")){
                        picUrlList.clear();
                        urlList.clear();
                        titleList.clear();
                        picList = homeBannerJsonData.getData().getDocs();
                        for (int i = 0; i < picList.size(); i++) {
                            picUrlList.add(homeBannerJsonData.getData().getDocs().get(i).getImg());
                            urlList.add(homeBannerJsonData.getData().getDocs().get(i).getHref());
                            titleList.add(homeBannerJsonData.getData().getDocs().get(i).getName());
                        }
                        initBanner();
                    }else {
                        ToastUtil.showShort(getActivity(),homeBannerJsonData.getRespMsg());
                    }
                    break;
                case 4:
                    updateVersionModel= (UpdateVersionModel) msg.obj;
                    if ("SUCCESS".equals(updateVersionModel.getRespCode())&&TiaoshiApplication.isFirstLogin){
                        Log.e("code", AppUtil.getVersionCode(getActivity()) + "  " + updateVersionModel.getData().getVersion()+"."+updateVersionModel.getData().getDeputy());
                        SPUtil.setSP("newVersion",updateVersionModel.getData().getVersion()+"."+updateVersionModel.getData().getDeputy());
                        if (!AppUtil.getVersionName(getActivity()).equals(updateVersionModel.getData().getVersion()+"."+updateVersionModel.getData().getDeputy())) {
                            dialog2 = new Dialog(getActivity());
                            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.updateversion, null);
                            Button btn1 = (Button) linearLayout.findViewById(R.id.btn_cancel);
                            Button btn2 = (Button) linearLayout.findViewById(R.id.btn_go_update);
                            TextView tv_content = (TextView) linearLayout.findViewById(R.id.tv_content);
                            TextView tv_title = (TextView) linearLayout.findViewById(R.id.tv_title);
                            tv_title.setText("版本升级v"+updateVersionModel.getData().getVersion()+"."+updateVersionModel.getData().getDeputy());
                            CharSequence charSequence= Html.fromHtml(updateVersionModel.getData().getIntro());
                            tv_content.setText(charSequence);
                            tv_content.setMovementMethod(LinkMovementMethod.getInstance());
                            dialog2.setContentView(linearLayout);
                            dialog2.setCanceledOnTouchOutside(false);
                            dialog2.getWindow().setBackgroundDrawable(new ColorDrawable());
                            btn1.setOnClickListener(HomeFragment.this);
                            btn2.setOnClickListener(HomeFragment.this);
                            TiaoshiApplication.isFirstLogin=false;
                            dialog2.show();
                        }
                    }
                    break;
                case 5:
                    orderListJsondata1= (JuiceOrderListdata) msg.obj;
                    if ("SUCCESS".equals(orderListJsondata1.getRespCode())&&null!=orderListJsondata1.getData()){
                        if (orderListJsondata1.getData().size()>0){
                            call();
                        }else {
                            isfenxiang=false;
                        }
                    }
                    break;
                case 6:
                    ShareShopIdModel shareShopIdModel = (ShareShopIdModel) msg.obj;
                    if ("SUCCESS".equals(shareShopIdModel.getRespCode())&&null!=shareShopIdModel.getData()){
                        if (!TextUtils.isEmpty(shareShopIdModel.getData().getShopId())){
                            G.SP.JUICE_SHORP_ID=shareShopIdModel.getData().getShopId();
                        }
                    }
                    break;
                case 7:
                    HomeCategoryGVModel homeCategoryGVModel = (HomeCategoryGVModel) msg.obj;
                    if ("SUCCESS".equals(homeCategoryGVModel.getRespCode())&&null!=homeCategoryGVModel.getData()){
                        reDataBeen.clear();
                        reDataBeen.addAll(homeCategoryGVModel.getData());
                        homeFLGridviewAdapter.notifyDataSetChanged();
                    }
                    break;
                case 8:
                    CityModel cityModel= (CityModel) msg.obj;
                    if ("SUCCESS".equals(cityModel.getRespCode())&&null!=cityModel.getData()){
                      TiaoshiApplication.cityModel=cityModel;
                        LogUtil.e("获取开通城市成功");
                    }
                    break;
                case 150:
                    pullToRefreshListView.onRefreshComplete();
                    break;
                case 200:
                    pullToRefreshListView.onRefreshComplete();
                    break;
                case 300:
                    pullToRefreshListView.onRefreshComplete();
                    break;
            }
        }
    };
    private void call() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("果汁领取:").setMessage("恭喜您已成功领取果汁，速速填写地址？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), JuiceOrderDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("orderList",orderListJsondata1.getData().get(0));
                intent.putExtras(bundle);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isfenxiang=false;
            }
        });
        builder.show();
    }
    //轮播图的ViewHolder
    public class BannerHolderView implements CBPageAdapter.Holder<String> {
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }
        @Override
        public void UpdateUI(Context context, final int position, String data) {
            //绑定事件
            imageView.setOnClickListener(new MyOnClickListener(position));
            //加载图片
            TiaoshiApplication.getGlobalBitmapUtils().display(imageView, data);
        }
    }
    class MyOnClickListener implements View.OnClickListener {
        private int position;
        public MyOnClickListener(int position) {
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            if (urlList.size() > 0) {
                //banner入口——————————————————————————————————
                Intent intent = new Intent(getActivity(), BannerDetailActivity.class);
                intent.putExtra("url", urlList.get(position));
                intent.putExtra("title", titleList.get(position));
                String ss=urlList.get(position);
                Log.e("=====",ss);
                String [] aa= ss.split("\\|");
                Log.e("---",aa.length+"");
                if (aa[0].equals("#GoShop")&&aa.length>=4){
                    Intent intent1 = new Intent(getActivity(), StoreDetailActivity.class);
                    intent1.putExtra("shop_id", aa[1]);
                    intent1.putExtra("shop_name",aa[2]);
                    intent1.putExtra("min_cost", aa[3]);
                    startActivity(intent1);
                }else if (!TextUtils.isEmpty(urlList.get(position))&&!urlList.get(position).substring(0,1).equals("#")){
                    startActivity(intent);
                }
            }
        }
    }
    @Override
    public void onDestroy() {
        convenient_banner.stopTurning();
        getActivity().unregisterReceiver(myBroadcastReceiver);
        super.onDestroy();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 2:
                initLocation();
                break;
            case 1:
                // 授权失败！
                Toast.makeText(getContext(),"授权失败，部分功能将无法使用",Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
