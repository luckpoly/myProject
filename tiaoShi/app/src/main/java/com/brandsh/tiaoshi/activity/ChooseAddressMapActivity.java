package com.brandsh.tiaoshi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
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
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.ChooseAddressAdapter;
import com.brandsh.tiaoshi.adapter.CityGvAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.CategoryDetailListJsonData;
import com.brandsh.tiaoshi.model.CityModel;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.LogUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SPUtil;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/26.
 */

public class ChooseAddressMapActivity extends Activity implements AMap.OnMapLoadedListener, AMap.OnCameraChangeListener, PoiSearch.OnPoiSearchListener, Inputtips.InputtipsListener, View.OnClickListener, TextWatcher {

    @ViewInject(R.id.li_poi)
    private ListView li_poi;
    @ViewInject(R.id.rl_error)
    private RelativeLayout rl_error;
    @ViewInject(R.id.choose_address_lv)
    ListView choose_address_lv;
    @ViewInject(R.id.choose_address_et)
    EditText choose_address_et;
    @ViewInject(R.id.ll_map_view)
    LinearLayout ll_map_view;
    @ViewInject(R.id.ll_list_view)
    LinearLayout ll_list_view;
    @ViewInject(R.id.category_rg)
    private RadioGroup category_rg;
    @ViewInject(R.id.choose_address_tvSure)
    private TextView choose_address_tvSure;
    @ViewInject(R.id.choose_address_ivBack)
    private ImageView choose_address_ivBack;
    @ViewInject(R.id.tv_city)
    private TextView tv_city;

    private AMap aMap;
    private MapView mapView;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private AMapLocationListener aMapLocationListener;
    private String city;
    private LatLng target;
    private LatLonPoint lp;
    private int currentPage;
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private ArrayList<PoiItem> poiItems;
    private PoiItem poiItem;
    private List<PoiItem> listString;
    private List<AddressInfo> listAddressInfo;
    private boolean isChosen;
    private List<CityModel.DataBean> yktList= new ArrayList<>();
    private List<CityModel.DataBean> wktList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseaddress);
        ViewUtils.inject(this);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        initMapView();
        initListener();
        initData();
        AppUtil.Setbar(this);
    }

    private void initListener() {
        choose_address_et.addTextChangedListener(this);
        choose_address_tvSure.setOnClickListener(this);
        choose_address_ivBack.setOnClickListener(this);
        tv_city.setOnClickListener(this);
        li_poi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                double latitude = listString.get(position).getLatLonPoint().getLatitude();//获取经度
                double longitude = listString.get(position).getLatLonPoint().getLongitude();//获取纬度
                LatLng latlng = new LatLng(latitude, longitude);
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(latlng));
                poiItem = listString.get(position);

            }
        });
        choose_address_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isChosen = true;
                choose_address_et.setText(listAddressInfo.get(position).getAddress());
                String address = listAddressInfo.get(position).getAddress();
                String area = listAddressInfo.get(position).getArea();
                if (listAddressInfo.get(position).getLatLng() == null) {
                    ToastUtil.showShort(ChooseAddressMapActivity.this, "选择地址请精确到县区");
                    return;
                }
                Double longitude = listAddressInfo.get(position).getLatLng().getLongitude();
                Double latitude = listAddressInfo.get(position).getLatLng().getLatitude();
                Intent intent;
                if (getIntent().getStringExtra("isLocation")!=null&&getIntent().getStringExtra("isLocation").equals("YES")){
                     intent = new Intent("changeAddress");
                    setResult(1);
                }else {
                     intent = new Intent("updateAddress");
                }
                intent.putExtra("address", address);
                intent.putExtra("area", area);
                intent.putExtra("lng", longitude+"");
                intent.putExtra("lat", latitude+"");
                sendBroadcast(intent);
                finish();


            }
        });
    }
    //获取开通城市
    private void getCityData(){
        HashMap map=new HashMap();
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.GET_CITY,map,new MyCallBack(8,this,new CityModel(),handler));
    }
    private void initData(){
        if (TiaoshiApplication.cityModel!=null){
            yktList.clear();
            wktList.clear();
            for (int i = 0; i < TiaoshiApplication.cityModel.getData().size(); i++) {
                if (TiaoshiApplication.cityModel.getData().get(i).getOpenStatus().equals("YES")){
                    yktList.add(TiaoshiApplication.cityModel.getData().get(i));
                }else if (TiaoshiApplication.cityModel.getData().get(i).getOpenStatus().equals("NO")){
                    wktList.add(TiaoshiApplication.cityModel.getData().get(i));
                }
            }
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 8:
                    CityModel cityModel= (CityModel) msg.obj;
                    if ("SUCCESS".equals(cityModel.getRespCode())&&null!=cityModel.getData()){
                        TiaoshiApplication.cityModel=cityModel;
                        initData();
                        if (yktList!=null){
                            showPopupWindow();
                        }
                        LogUtil.e("获取开通城市成功");
                    }
                    break;
            }
        }
    };
    public void initviewLayout(){
        category_rg.check(R.id.categorydata_rb1);
        query = new PoiSearch.Query("", "汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|" +
                "医疗保健服务|住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|" +
                "金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        category_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.categorydata_rb1:
                        query = new PoiSearch.Query("", "汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|" +
                                "医疗保健服务|住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|" +
                                "金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
                        break;
                    case R.id.categorydata_rb2:
                        query = new PoiSearch.Query("写字楼", "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
                        break;
                    case R.id.categorydata_rb3:
                        query = new PoiSearch.Query("小区", "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
                        break;
                    case R.id.categorydata_rb4:
                        query = new PoiSearch.Query("学校", "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
                        break;

                }
                mclsearch();
            }
        });
    }

    private void initMapView() {
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setOnMapLoadedListener(this);
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
            aMap.setLocationSource(new LocationSource() {
                @Override
                public void activate(OnLocationChangedListener onLocationChangedListener) {
                    aMapLocationClient.startLocation();
                }

                @Override
                public void deactivate() {

                }
            });
            aMap.getUiSettings().setScaleControlsEnabled(true);
            aMap.setMyLocationEnabled(true);
            aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
            aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        }
        aMap.setOnCameraChangeListener(this);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
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
            public void onLocationChanged( AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        double latitude = amapLocation.getLatitude();//获取经度
                        double longitude = amapLocation.getLongitude();//获取纬度
                        final LatLng latlng = new LatLng(latitude, longitude);
                        final String lsCity=amapLocation.getCity();
                        LogUtil.e("=============="+amapLocation.getCity());
                        if (!SPUtil.isKtCity(amapLocation.getCity())){
                         Double lng=Double.parseDouble(TiaoshiApplication.cityModel.getData().get(0).getLng());
                         Double lat=Double.parseDouble(TiaoshiApplication.cityModel.getData().get(0).getLat());
                            final LatLng latlng1 = new LatLng(lat, lng);
                            //选择是否跳转位置
                            AlertDialog.Builder builder = new AlertDialog.Builder(ChooseAddressMapActivity.this);
                            builder.setTitle("提示:").setMessage("当前城市未开通，是否切换到已开通城市？");
                            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(latlng1));
                                    city=TiaoshiApplication.cityModel.getData().get(0).getName();
                                    tv_city.setText(city);
                                    initviewLayout();
                                    dialog.dismiss();
                                }
                            });
                            builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    city = lsCity;
                                    initviewLayout();
                                    tv_city.setText(city);
                                    tv_city.setTextColor(getResources().getColor(R.color.line));
                                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(latlng));
                                }
                            });
                            builder.show();
                            return;
                        }
                        city = lsCity;
                        initviewLayout();
                        tv_city.setText(city);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choose_address_tvSure:
                choose_address_et.setText("");
                break;
            case R.id.choose_address_ivBack:
                finish();
                break;
            case R.id.tv_city:
                getCityData();
                break;
        }
    }
    private void showPopupWindow(){
        View view= LayoutInflater.from(this).inflate(R.layout.popup_choose_city,null);
        final PopupWindow popupWindow=new PopupWindow(view, RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT,true);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        GridView gv_ykt_city=(GridView)view.findViewById(R.id.gv_ykt_city);
        GridView gv_wkt_city=(GridView)view.findViewById(R.id.gv_wkt_city);
        TextView tv_out=(TextView)view.findViewById(R.id.tv_out);
        popupWindow.showAsDropDown(choose_address_ivBack);
        CityGvAdapter cityGvAdapter=new CityGvAdapter(ChooseAddressMapActivity.this,yktList,city);
        gv_ykt_city.setAdapter(cityGvAdapter);
        CityGvAdapter cityGvAdapter1=new CityGvAdapter(ChooseAddressMapActivity.this,wktList);
        gv_wkt_city.setAdapter(cityGvAdapter1);
        gv_ykt_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_city.setText(yktList.get(position).getName());
                tv_city.setTextColor(getResources().getColor(R.color.theme_color));
                city=yktList.get(position).getName();
                popupWindow.dismiss();
                Double lat=Double.parseDouble(yktList.get(position).getLat());
                Double lng=Double.parseDouble(yktList.get(position).getLng());
                LatLng latlng = new LatLng(lat, lng);
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(latlng));
                SPUtil.setSP("city",yktList.get(position).getName());
                SPUtil.setSP("cityLng",yktList.get(position).getLng());
                SPUtil.setSP("cityLat",yktList.get(position).getLat());
            }
        });
        gv_wkt_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtil.showShort(ChooseAddressMapActivity.this,"此城市尚未开通");
            }
        });
        tv_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
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

    @Override
    public void onMapLoaded() {

    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        Log.e("33", "hehe" + "  " + i);
        if (i == 1000) {
            if (poiResult != null && poiResult.getQuery() != null) {// 搜索poi的结果
                if (poiResult.getQuery().equals(query)) {// 是否是同一条
                    poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    if (poiItems != null && poiItems.size() > 0) {
                        rl_error.setVisibility(View.GONE);
                        li_poi.setVisibility(View.VISIBLE);
                        Log.e("poiItems.size", poiItems.size() + "");
                        poiItem = poiItems.get(0);
                        ShowTipsAdapter aAdapter = new ShowTipsAdapter(poiItems);
                        li_poi.setAdapter(aAdapter);
                    } else {
                        rl_error.setVisibility(View.VISIBLE);
                        li_poi.setVisibility(View.GONE);
                    }
                }
            } else {
                ToastUtil
                        .show(ChooseAddressMapActivity.this, "对不起，没有搜索到相关数据！", Toast.LENGTH_SHORT);
            }
        } else if (i == 27) {
            ToastUtil
                    .show(ChooseAddressMapActivity.this, "搜索失败,请检查网络连接！", Toast.LENGTH_SHORT);
        }

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    private void mclsearch() {
        currentPage = 0;
        query.setPageSize(15);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        if (lp != null) {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(lp, 1000, true));//
            Log.e("query", lp.toString());
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
    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == 1000) {// 正确返回
            listAddressInfo = new ArrayList<>();
            for (int i = 0; i < tipList.size(); i++) {
                AddressInfo addressInfo = new AddressInfo();
                addressInfo.setAddress(tipList.get(i).getName());
                addressInfo.setArea(tipList.get(i).getDistrict());
                addressInfo.setLatLng(tipList.get(i).getPoint());
                listAddressInfo.add(addressInfo);
            }
            ChooseAddressAdapter chooseAddressAdapter = new ChooseAddressAdapter(listAddressInfo, this);
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
        //设置是否显示
        LogUtil.e(newText);
        if (!TextUtils.isEmpty(newText)) {
            ll_map_view.setVisibility(View.GONE);
            ll_list_view.setVisibility(View.VISIBLE);
            choose_address_tvSure.setVisibility(View.VISIBLE);
        } else {
            ll_map_view.setVisibility(View.VISIBLE);
            ll_list_view.setVisibility(View.GONE);
            choose_address_tvSure.setVisibility(View.GONE);
        }
        InputtipsQuery inputquery = new InputtipsQuery(newText, city);
        Inputtips inputTips = new Inputtips(this, inputquery);
        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    public class ShowTipsAdapter extends BaseAdapter {

        public ShowTipsAdapter(List<PoiItem> listString) {
            ChooseAddressMapActivity.this.listString = listString;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            if (convertView == null) {
                convertView = LayoutInflater.from(ChooseAddressMapActivity.this).inflate(R.layout.route_inputs, null);
                holder.tv1 = (TextView) convertView.findViewById(R.id.route_inputs_tvName);
                holder.tv2 = (TextView) convertView.findViewById(R.id.route_inputs_tvArea);
                holder.tv_dq = (TextView) convertView.findViewById(R.id.tv_dq);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Log.e("hehe", listString.get(position).getAdName() + " getAdName " + listString.get(position).getBusinessArea() + " getBusinessArea " + listString.get(position).getCityName() + " getCityName " +
                    listString.get(position).getEmail() + " getEmail  " + listString.get(position).getParkingType() + "getParkingType  " + listString.get(position).getSnippet() + " getSnippet " +
                    listString.get(position).getTitle() + " getTitle  " + listString.get(position).getTypeDes() + " getTypeDes " + listString.get(position).describeContents()+ listString.get(position).getLatLonPoint().getLatitude() + "");
            final String address = listString.get(position).getTitle();
            final String area = listString.get(position).getCityName() + listString.get(position).getAdName() + listString.get(position).getSnippet();
            final String lng = listString.get(position).getLatLonPoint().getLongitude() + "";
            final String lat = listString.get(position).getLatLonPoint().getLatitude() + "";
            holder.tv1.setText(address);
            holder.tv2.setText(area);
            if (position==0){
                holder.tv_dq.setVisibility(View.VISIBLE);
            }else {
                holder.tv_dq.setVisibility(View.GONE);
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //--------------------------
                    Intent intent;
                    if (getIntent().getStringExtra("isLocation")!=null&&getIntent().getStringExtra("isLocation").equals("YES")){
                        intent = new Intent("changeAddress");
                        setResult(1);
                    }else {
                        intent = new Intent("updateAddress");
                    }
                    intent.putExtra("address", address);
                    intent.putExtra("area", area);
                    intent.putExtra("lng", lng);
                    intent.putExtra("lat", lat);
                    sendBroadcast(intent);
                    finish();
                }
            });


            return convertView;
        }

        class ViewHolder {
            private TextView tv1;
            private TextView tv2,tv_dq;
        }
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
}
