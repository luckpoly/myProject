package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.graphics.Color;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.overlay.WalkRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.amap.api.services.route.WalkStep;
import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.widget.TitleBarView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/4/2.
 */
public class ShowRouteActivity extends Activity implements  RouteSearch.OnRouteSearchListener,LocationSource,AMap.OnMapLoadedListener {
    @ViewInject(R.id.title_bar)
    private RelativeLayout title_bar;
    private TitleBarView titleBarView;
    @ViewInject(R.id.mv_showroute)
    private MapView mv_showroute;
    private AMap aMap;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private AMapLocationListener aMapLocationListener;
    private RouteSearch routeSearch;
    private LocationSource.OnLocationChangedListener locationChangedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showroute);
        ViewUtils.inject(this);
        mv_showroute.onCreate(savedInstanceState);
        initTitleBar();
        initMapView();
        getDistance();
    }

    private void initTitleBar() {
        titleBarView=new TitleBarView(this,title_bar,"订单地图");
    }

    private void initMapView() {
        if (aMap == null) {
            aMap = mv_showroute.getMap();
            aMap.setOnMapLoadedListener(this);
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
            aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
            initLocation();
        }
    }
    private void getDistance(){
        LatLonPoint point_send=new LatLonPoint(getIntent().getDoubleExtra("mSendLat",0.0),getIntent().getDoubleExtra("mSendLng",0.0));
        LatLonPoint point_receive=new LatLonPoint(getIntent().getDoubleExtra("mReceiveLat",0.0),getIntent().getDoubleExtra("mReceiveLng",0.0));
        RouteSearch.FromAndTo fromAndTo=new RouteSearch.FromAndTo(point_send,point_receive);
        routeSearch = new RouteSearch(this);//初始化routeSearch 对象
        routeSearch.setRouteSearchListener(this);//设置数据回调监听器
        RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo,RouteSearch.WalkDefault );//初始化query对象，fromAndTo是包含起终点信息，walkMode是不行路径规划的模式
        routeSearch.calculateWalkRouteAsyn(query);//开始算路
    }
    private void initLocation() {
        aMap.setLocationSource(this);
        aMap.getUiSettings().setScaleControlsEnabled(true);
        aMap.setMyLocationEnabled(true);
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
        //设置定位间隔,单位毫秒,默认为2000ms
        aMapLocationClientOption.setInterval(3500);
        //给定位客户端对象设置定位参数
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.location));
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        aMap.setMyLocationStyle(myLocationStyle);
        //声明定位回调监听器
        aMapLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        locationChangedListener.onLocationChanged(amapLocation);
                        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        double latitude = amapLocation.getLatitude();//获取经度
                        double longitude = amapLocation.getLongitude();//获取纬度
                        Log.e("lng", longitude + "");
                        Log.e("lat", latitude + "");
                        amapLocation.getAccuracy();//获取精度信息
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date(amapLocation.getTime());
                        df.format(date);//定位时间
                        amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果
                        amapLocation.getCountry();//国家信息
                        amapLocation.getProvince();//省信息
                        amapLocation.getCity();//城市信息
                        amapLocation.getDistrict();//城区信息
                        amapLocation.getRoad();//街道信息
                        amapLocation.getCityCode();//城市编码
                        amapLocation.getAdCode();//地区编码
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                }
            }
        };
        //设置定位回调监听
        aMapLocationClient.setLocationListener(aMapLocationListener);

        //启动定位
        aMapLocationClient.startLocation();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mv_showroute.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mv_showroute.onPause();
        //友盟统计
       //已删
    }

    @Override
    protected void onResume() {
        super.onResume();
        mv_showroute.onResume();
        //友盟统计
        //已删

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aMapLocationClient.stopLocation();
        aMapLocationClient.onDestroy();
        mv_showroute.onDestroy();
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
        if (i == 0) {
            if (walkRouteResult != null && walkRouteResult.getPaths() != null
                    && walkRouteResult.getPaths().size() > 0) {
                WalkPath walkPath = walkRouteResult.getPaths().get(0);
                aMap.clear();// 清理地图上的所有覆盖物
                WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(this,
                        aMap, walkPath, walkRouteResult.getStartPos(),
                        walkRouteResult.getTargetPos());
                walkRouteOverlay.setNodeIconVisibility(false);
                walkRouteOverlay.addToMap();
                walkRouteOverlay.zoomToSpan();
            }
        } else if (i == 27) {
            ToastUtil.show(ShowRouteActivity.this,"搜索失败,请检查网络连接！",Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        locationChangedListener=onLocationChangedListener;
    }

    @Override
    public void deactivate() {

    }

    @Override
    public void onMapLoaded() {

    }
}
