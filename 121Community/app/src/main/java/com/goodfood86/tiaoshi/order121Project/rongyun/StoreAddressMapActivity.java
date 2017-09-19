package com.goodfood86.tiaoshi.order121Project.rongyun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.goodfood86.tiaoshi.order121Project.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import io.rong.message.LocationMessage;

public class StoreAddressMapActivity extends FragmentActivity implements View.OnClickListener, AMap.OnMarkerClickListener{
    @ViewInject(R.id.map_ivBack)
    private ImageView map_ivBack;
    @ViewInject(R.id.map_mapView)
    private MapView mapView;
    private LocationMessage mMsg;


    private AMap aMap;
    private UiSettings uiSettings;
    private Intent intent;
    private LatLng latLng;
    private MarkerOptions markerOptions;
    private Marker marker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_address_map);
        ViewUtils.inject(this);
        mapView.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        intent = getIntent();
        mMsg = getIntent().getParcelableExtra("location");
        latLng = new LatLng(mMsg.getLat(), mMsg.getLng());

        markerOptions = new MarkerOptions();
        String poi=mMsg.getPoi();
        if(poi.length()>20){
            poi=poi.substring(0,20)+"...";
        }
        markerOptions.position(latLng).title(poi)
                .snippet("")
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.location));

        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setOnMarkerClickListener(this);
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
        }

        //设置中心点
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        //设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(13));
        marker = aMap.addMarker(markerOptions);
        marker.showInfoWindow();
        uiSettings = aMap.getUiSettings();
        uiSettings.setScaleControlsEnabled(true);
        map_ivBack.setOnClickListener(this);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.map_ivBack:
                finish();
                break;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return false;
    }
}
