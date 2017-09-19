package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;

import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.widget.TitleBarView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/25.
 */
public class ChooseAddressActivity extends Activity implements AMap.OnMapLoadedListener,AMap.OnCameraChangeListener,PoiSearch.OnPoiSearchListener,View.OnClickListener {
    private List<PoiItem> listString;
    @ViewInject(R.id.iv_back)
    private ImageView iv_back;
    @ViewInject(R.id.tv_sure)
    private TextView tv_sure;
    private MapView mapView;
    @ViewInject(R.id.li_poi)
    private ListView li_poi;
    @ViewInject(R.id.rl_error)
    private RelativeLayout rl_error;
    private AMap aMap;
    private LatLng target;
    private int currentPage;
    private String city;
    private PoiSearch.Query query;
    private LatLonPoint lp;
    private PoiSearch poiSearch;
    private ArrayList<PoiItem> poiItems;
    private PoiItem poiItem;
    private final static int REQUEST_SU=1;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private AMapLocationListener aMapLocationListener;
    private String mcity;
    private String mtitle;
    private String mSnippet;
    private double mlat;
    private double mlng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseaddress);
        ViewUtils.inject(this);
        mapView=(MapView)findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        initMapView();
        initListener();

    }


    private void initListener() {
        tv_sure.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        li_poi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                double latitude = listString.get(position).getLatLonPoint().getLatitude();//获取经度
                double longitude =listString.get(position).getLatLonPoint().getLongitude();//获取纬度
                LatLng latlng = new LatLng(latitude, longitude);
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(latlng));
                poiItem=listString.get(position);
            }
        });
    }


    private void initMapView() {
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setOnMapLoadedListener(this);
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
            aMap.getUiSettings().setScaleControlsEnabled(true);
            aMap.moveCamera(CameraUpdateFactory.zoomTo(15));

        }
        aMap.setOnCameraChangeListener(this);
        aMap.getUiSettings().setMyLocationButtonEnabled(false);
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
        //声明定位回调监听器
        aMapLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        double latitude = amapLocation.getLatitude();//获取经度
                        double longitude = amapLocation.getLongitude();//获取纬度
                        LatLng latlng = new LatLng(latitude, longitude);
                        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latlng));
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
                        city = amapLocation.getCity();
                        mcity=amapLocation.getCity();
                        mtitle=amapLocation.getAddress();
                        mSnippet=amapLocation.getAddress();
                        mlat=amapLocation.getLatitude();
                        mlng=amapLocation.getLongitude();
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

//    private void setUpMap() {
//
//        addMarkersToMap();// 往地图上添加marker
//    }
//
//    private void addMarkersToMap() {
//        Marker marker = aMap.addMarker(new MarkerOptions()
//                .icon(BitmapDescriptorFactory
//                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
//                .draggable(false));
//        marker.setPositionByPixels(mapView.getWidth()/2,(mapView.getHeight()+title_bar.getHeight()-10)/2);
//
//
//    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        //友盟统计
       //已删

    }


    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        //友盟统计
        //已删
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        target = cameraPosition.target;
        Log.e("--", target.latitude + "  " + target.longitude);
        lp = new LatLonPoint(target.latitude, target.longitude);
        mclsearch();

    }

    private void mclsearch() {
        currentPage = 0;
        query = new PoiSearch.Query("", "汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|"+
                "医疗保健服务|住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务" +
                "金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施", Order121Application.globalCity);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        if (lp != null) {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(lp, 500, true));//
            Log.e("query",lp.toString());
            // 设置搜索区域为以lp点为圆心，其周围2000米范围
			/*
			 * List<LatLonPoint> list = new ArrayList<LatLonPoint>();
			 * list.add(lp);
			 * list.add(AMapUtil.convertToLatLonPoint(Constants.BEIJING));
			 * poiSearch.setBound(new SearchBound(list));// 设置多边形poi搜索范围
			 */
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }


    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        Log.e("33","hehe"+"  "+i);
        if (i == 0) {
            if (poiResult != null && poiResult.getQuery() != null) {// 搜索poi的结果
                if (poiResult.getQuery().equals(query)) {// 是否是同一条
                    poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    if (poiItems != null && poiItems.size() > 0) {
                        rl_error.setVisibility(View.GONE);
                        li_poi.setVisibility(View.VISIBLE);
                        Log.e("poiItems.size",poiItems.size()+"");
                        poiItem=poiItems.get(0);
                        ShowTipsAdapter aAdapter = new ShowTipsAdapter(poiItems);
                        li_poi.setAdapter(aAdapter);
                    } else {
                        rl_error.setVisibility(View.VISIBLE);
                        li_poi.setVisibility(View.GONE);
                    }
                }
            } else {
                ToastUtil
                        .show(ChooseAddressActivity.this, "对不起，没有搜索到相关数据！", Toast.LENGTH_SHORT);
            }
        } else if (i == 27) {
            ToastUtil
                    .show(ChooseAddressActivity.this, "搜索失败,请检查网络连接！", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public void onClick(View v) {
        if (v!=null){
            switch (v.getId()){
                case R.id.iv_back:
                    finish();
                    break;
                case R.id.tv_sure:
                    Intent intent=new Intent(ChooseAddressActivity.this,SupplementAddress.class);
                    intent.putExtra("type",getIntent().getStringExtra("type"));
                    startActivityForResult(intent,REQUEST_SU);
                    break;

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("00","haha");
        if (requestCode==REQUEST_SU){
            if (resultCode==RESULT_OK){
                Log.e("00","hehe");
                if (poiItem!=null) {
                    mcity = poiItem.getCityName();
                    mtitle = poiItem.getTitle();
                    mSnippet = poiItem.getSnippet();
                    mlat=poiItem.getLatLonPoint().getLatitude();
                    mlng=poiItem.getLatLonPoint().getLongitude();
                }
                Intent intent=new Intent();
                intent.putExtra("city",mcity);
                intent.putExtra("title",mtitle);
                intent.putExtra("street",mSnippet);
                intent.putExtra("suaddress",data.getStringExtra("suaddress"));
                intent.putExtra("phoneNo",data.getStringExtra("phoneNo"));
                intent.putExtra("lat",mlat);
                intent.putExtra("lng",mlng);
                setResult(RESULT_OK,intent);
                finish();
            }

        }
    }

    public class ShowTipsAdapter extends BaseAdapter {

        public ShowTipsAdapter(List<PoiItem> listString) {
            ChooseAddressActivity.this.listString = listString;
        }

        @Override
        public int getCount() {
            return listString.size();
        }

        @Override
        public Object getItem(int position) {
            return listString.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            if (convertView == null) {
                convertView = LayoutInflater.from(ChooseAddressActivity.this).inflate(R.layout.route_inputs, null);
                holder.tv1 = (TextView) convertView.findViewById(R.id.online_user_list_item_textview);
                holder.tv2 = (TextView) convertView.findViewById(R.id.online_user_list_item_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Log.e("hehe", listString.get(position).getAdName() + " getAdName " + listString.get(position).getBusinessArea() + " getBusinessArea " + listString.get(position).getCityName() + " getCityName " +
                    listString.get(position).getEmail() + " getEmail  " + listString.get(position).getParkingType() + "getParkingType  " + listString.get(position).getSnippet() + " getSnippet " +
                    listString.get(position).getTitle() + " getTitle  " + listString.get(position).getTypeDes()+" getTypeDes "+listString.get(position).describeContents());
            holder.tv1.setText(listString.get(position).getTitle());
            holder.tv2.setText(listString.get(position).getCityName()+listString.get(position).getAdName()+listString.get(position).getSnippet());
            return convertView;
        }

        class ViewHolder {
            private TextView tv1;
            private TextView tv2;
        }
    }
}