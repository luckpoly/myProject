package com.brandsh.tiaoshi.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.ChooseAddressAdapter;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class ChooseAddressActivity extends FragmentActivity implements View.OnClickListener, Inputtips.InputtipsListener, TextWatcher {
    @ViewInject(R.id.choose_address_ivBack)
    ImageView choose_address_ivBack;
    @ViewInject(R.id.choose_address_et)
    EditText choose_address_et;
    @ViewInject(R.id.choose_address_tvSure)
    TextView choose_address_tvSure;
    @ViewInject(R.id.choose_address_lv)
    ListView choose_address_lv;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private AMapLocationListener aMapLocationListener;
    private Toast toast;
    private AlertDialog.Builder locationBuilder;
    private String cityName;
    private ChooseAddressAdapter chooseAddressAdapter;
    private List<AddressInfo> listString;
    private boolean isChosen;
    private double longitude;
    private double latitude;
    private AlertDialog.Builder builder;
    private String address;
    private String area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);

        //沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);
        init();
    }

    private void init() {
        choose_address_ivBack.setOnClickListener(this);
        choose_address_tvSure.setOnClickListener(this);
        choose_address_et.addTextChangedListener(this);
        toast = Toast.makeText(this, "定位失败, 请稍后再试", Toast.LENGTH_SHORT);
        locationBuilder = new AlertDialog.Builder(this).setTitle("系统提示");
        builder = new AlertDialog.Builder(this).setTitle("确定选择").setMessage("确定选择该街道地址?").setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent("updateAddress");
                intent.putExtra("address", address);
                intent.putExtra("area", area);
                intent.putExtra("lng", longitude);
                intent.putExtra("lat", latitude);
                sendBroadcast(intent);
                finish();
            }
        }).setPositiveButton("取消", null);

        initLocation();

        choose_address_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isChosen = true;
                choose_address_et.setText(listString.get(position).getAddress());
                address = listString.get(position).getAddress();
                area = listString.get(position).getArea();
                if (listString.get(position).getLatLng() == null) {
                    ToastUtil.showShort(ChooseAddressActivity.this, "选择地址请精确到县区");
                    return;
                }
                longitude = listString.get(position).getLatLng().getLongitude();
                latitude = listString.get(position).getLatLng().getLatitude();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_address_ivBack:
                finish();
                break;
            case R.id.choose_address_tvSure:
                if (!isChosen) {
                    Toast.makeText(ChooseAddressActivity.this, "请先选择街道地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(longitude + "")||longitude==0.0) {
                    ToastUtil.showShort(ChooseAddressActivity.this, "选择地址请精确到县区");
                    return;
                }
                builder.create().show();
                break;
        }
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
                        cityName = amapLocation.getCity();
                        aMapLocationClient.onDestroy();
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
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == 1000) {// 正确返回
            listString = new ArrayList<>();
            for (int i = 0; i < tipList.size(); i++) {
                AddressInfo addressInfo = new AddressInfo();
                addressInfo.setAddress(tipList.get(i).getName());
                addressInfo.setArea(tipList.get(i).getDistrict());
                addressInfo.setLatLng(tipList.get(i).getPoint());
                listString.add(addressInfo);
            }
//            chooseAddressAdapter = new ChooseAddressAdapter(listString, this);
            choose_address_lv.setAdapter(chooseAddressAdapter);
            chooseAddressAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString().trim();
        InputtipsQuery inputquery = new InputtipsQuery(newText, cityName);
        Inputtips inputTips = new Inputtips(this, inputquery);
        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    public class AddressInfo {
        private String address;
        private String area;
        private LatLonPoint latLng;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public LatLonPoint getLatLng() {
            return latLng;
        }

        public void setLatLng(LatLonPoint latLng) {
            this.latLng = latLng;
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
