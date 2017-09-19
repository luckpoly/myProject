package com.brandsh.tiaoshi.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.DiyShoppingCartJsonData;
import com.brandsh.tiaoshi.model.HomeStoreModel1;
import com.brandsh.tiaoshi.model.StoreDetailJsonData1;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.PackageUtil;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;


public class StartActivity extends Activity implements View.OnClickListener, View.OnTouchListener{

    private EditText start_et;
    private Button start_btnStore;
    private TextView start_btnHome;
    private SharedPreferences sharedPreferences;
    private boolean isFocused;
    private String oldVersion;
    private String currentVersion;
    private ProgressDialog progDialog;
    private String shop_id;
    private String shop_name;
    private String min_cost;
    private String status;
    private HashMap requestMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
//        Intent intent = getIntent();
//        String scheme = intent.getScheme();
//        System.out.println("scheme:"+scheme);
//        Log.e("-----","scheme:"+scheme);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            this. getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            this. getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        initView();
//        initLocation();
        handler.sendEmptyMessageDelayed(0,2000);
        AppUtil.Setbar(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        JPushInterface.onPause(this);
    }

    private void initView() {
        requestMap=new HashMap();
        TiaoshiApplication.diyShoppingCartJsonData = new DiyShoppingCartJsonData();
//        start_et = (EditText) findViewById(R.id.start_et);
//        start_btnStore = (Button) findViewById(R.id.start_btnStore);
        start_btnHome = (TextView) findViewById(R.id.start_btnHome);

        sharedPreferences = getSharedPreferences("version", MODE_PRIVATE);
        //旧版本：读取从导航页面写入偏好文件中的版本号
        oldVersion = sharedPreferences.getString("version", null);
        //新版本：获取当前版本号
        currentVersion = PackageUtil.getPackageVersion(this);

//        start_et.setOnTouchListener(this);
//        start_btnStore.setOnClickListener(this);
        start_btnHome.setOnClickListener(this);
        new Thread(){
            @Override
            public void run() {
                super.run();
                for (int i = 3; i > 0; i--) {
                    Message m=new Message();
                    m.what=3;
                    m.arg1=i;
                    handler.sendMessage(m);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                //进入引导页
                case 0:
                    if (!isFocused&&!isFinishing()) {
                        if (currentVersion.equals(oldVersion)) {
                            Intent intent = new Intent(StartActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            //若是第一次安装程序或者程序版本号有变更，则偏好设置文件中的"version"为空，所以将进入导航页面
                            Intent intent = new Intent(StartActivity.this, GuideActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    break;
                case 1:
                    StoreDetailJsonData1 storeDetailJsonData = (StoreDetailJsonData1) msg.obj;
                    if (storeDetailJsonData != null) {
                        if (storeDetailJsonData.getRespCode().equals("SUCCESS")) {
                            Intent intent = new Intent(StartActivity.this, StoreDetailActivity.class);
                            intent.putExtra("shop_id", shop_id);
                            intent.putExtra("shop_name", storeDetailJsonData.getData().getName());
                            intent.putExtra("min_cost", storeDetailJsonData.getData().getFreeSend());
                            intent.putExtra("tag", "1");
                            startActivity(intent);
                            finish();
                        }else {
                            ToastUtil.showShort(StartActivity.this,"请确认商铺ID是否正确");
                        }
                    }
                    break;
                case 3:
                    start_btnHome.setText(msg.arg1+"跳过");
                    break;
                default:
                    break;

            }
            return false;
        }
    });

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_btnStore:
                shop_id = start_et.getText().toString();
                if(TextUtils.isEmpty(shop_id)){
                    Toast.makeText(StartActivity.this, "请先输入商户Id", Toast.LENGTH_SHORT).show();
                }else {
                    //通过店铺id获取基本信息
                    requestMap.clear();
                    requestMap.put("shopId", shop_id);
                    requestMap.put("lng",  TiaoshiApplication.Lng);
                    requestMap.put("lat",  TiaoshiApplication.Lat);
                    requestMap.put("actReq", SignUtil.getRandom());
                    requestMap.put("actTime", System.currentTimeMillis() / 1000+"");
                    String sign= SignUtil.getSign(requestMap);
                    requestMap.put("sign", Md5.toMd5(sign));
                    OkHttpManager.postAsync(G.Host.STORE_DETAIL ,requestMap,new MyCallBack(1, StartActivity.this, new StoreDetailJsonData1(), handler));
                }
                break;
            case R.id.start_btnHome:
                if (currentVersion.equals(oldVersion)) {
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    //若是第一次安装程序或者程序版本号有变更，则偏好设置文件中的"version"为空，所以将进入导航页面
                    Intent intent = new Intent(StartActivity.this, GuideActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
    private GeocodeSearch geocoderSearch;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationListener aMapLocationListener;
    private AMapLocationClientOption aMapLocationClientOption;
    private LatLonPoint latLonPoint;
    private double latitude;
    private double longitude;
    private void initLocation() {
        geocoderSearch = new GeocodeSearch(this);
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
                        TiaoshiApplication.Lng=longitude+"";
                        TiaoshiApplication.Lat=latitude + "";

                    }
                }
            }
        };
        //设置定位回调监听
        aMapLocationClient.setLocationListener(aMapLocationListener);
        aMapLocationClient.startLocation();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isFocused = true;
        }
        return false;
    }

}
