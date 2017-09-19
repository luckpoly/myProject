package com.brandsh.tiaoshi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.MyAddressAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.fragment.PhoneLoginFragment;
import com.brandsh.tiaoshi.model.AddressListJsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.LogUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SPUtil;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mingle.widget.ShapeLoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/28.
 */

public class LocationActivity extends BaseActivity implements PoiSearch.OnPoiSearchListener, View.OnClickListener {
    @ViewInject(R.id.my_address_listView)
    ListView my_address_listView;
    @ViewInject(R.id.li_poi)
    ListView li_poi;
    @ViewInject(R.id.tv_address_title)
    TextView tv_address_title;
    @ViewInject(R.id.tv_loadmore)
    TextView tv_loadmore;
    @ViewInject(R.id.tv_dangqian_location)
    TextView tv_dangqian_location;
    @ViewInject(R.id.tv_new_location)
    TextView tv_new_location;
    @ViewInject(R.id.tv_go_input)
    TextView tv_go_input;
    @ViewInject(R.id.iv_back)
    ImageView iv_back;
    @ViewInject(R.id.tv_add_adds)
    TextView tv_add_adds;
    private double latitude;
    private double longitude;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationListener aMapLocationListener;
    private AMapLocationClientOption aMapLocationClientOption;
    private LatLonPoint latLonPoint;
    private AlertDialog.Builder locationBuilder;
    private LatLonPoint lp;
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private List<AddressListJsonData.DataBean.ListBean> resList;
    private MyAddressAdapter myAddressAdapter;
    private ArrayList<PoiItem> poiItems;
    private int FROMMAP=1001;
    private int LOGIN=1002;
    String dangqianAddress;
    //加载动画
    private ShapeLoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ViewUtils.inject(this);
        initView();
        initListener();
        if (TiaoshiApplication.isLogin) {
            tv_address_title.setVisibility(View.VISIBLE);
            initData();
        }
        initLocation();
        AppUtil.Setbar(this);
    }
    private void initView() {
        resList = new LinkedList<>();
        myAddressAdapter = new MyAddressAdapter(resList, this);
        my_address_listView.setAdapter(myAddressAdapter);
        my_address_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent("changeAddress");
                intent.putExtra("address", resList.get(position).getAddress());
                intent.putExtra("area", resList.get(position).getAddressDetail());
                intent.putExtra("lng", resList.get(position).getLng());
                intent.putExtra("lat", resList.get(position).getLat());
                sendBroadcast(intent);
                finish();
            }
        });
        loadingDialog = ProgressHUD.show(this, "正在定位...");

    }
    private void initListener(){
        tv_loadmore.setOnClickListener(this);
        tv_new_location.setOnClickListener(this);
        tv_dangqian_location.setOnClickListener(this);
        tv_go_input.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_add_adds.setOnClickListener(this);
    }

    private void initData() {
        HashMap requestMap = new HashMap();
        requestMap.put("token", TiaoshiApplication.globalToken);
        requestMap.put("actReq", SignUtil.getRandom());
        requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(requestMap);
        requestMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.ADDRESS_LIST, requestMap, new MyCallBack(1, LocationActivity.this, new AddressListJsonData(), handler));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_go_input:
            case R.id.tv_loadmore:
                Intent intent=new Intent(LocationActivity.this,ChooseAddressMapActivity.class)
                        .putExtra("isLocation","YES");
                startActivityForResult(intent,FROMMAP);
                break;
            case R.id.tv_new_location:
                loadingDialog.show();
                aMapLocationClient.startLocation();
                break;
            case R.id.tv_dangqian_location:
                if (TextUtils.isEmpty(dangqianAddress)){
                    shortToast("获取位置信息失败，请重新定位");
                    return;
                }
                Intent intent1 = new Intent("changeAddress");
                intent1.putExtra("address",dangqianAddress );
                intent1.putExtra("area", dangqianAddress);
                intent1.putExtra("lng",longitude+"" );
                intent1.putExtra("lat",latitude+"" );
                sendBroadcast(intent1);
                finish();
                break;
            case R.id.tv_add_adds:
                if (!TiaoshiApplication.isLogin){
                    startActivityForResult(FCActivity.getFCActivityIntent(LocationActivity.this, PhoneLoginFragment.class),LOGIN);
                }else {
                    goAddAdds();
                }
                break;
        }

    }
    private void goAddAdds(){
        Intent intent2=new Intent(LocationActivity.this,AddDeliveryAddressActivity.class)
                . putExtra("isLocation","YES");
        startActivityForResult(intent2,FROMMAP);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    AddressListJsonData addressListJsonData = (AddressListJsonData) msg.obj;
                    if (addressListJsonData != null) {
                        if ("SUCCESS".equals(addressListJsonData.getRespCode())) {
                            resList.clear();
                            resList.addAll(addressListJsonData.getData().getList());
                            myAddressAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(LocationActivity.this, addressListJsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

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
                    loadingDialog.dismiss();
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        latitude = amapLocation.getLatitude();
                        longitude = amapLocation.getLongitude();
                         dangqianAddress=amapLocation.getAddress();
                        tv_dangqian_location.setText(dangqianAddress);
                        Log.e("经度", longitude + "");
                        Log.e("纬度", latitude + "");
                        if (!SPUtil.isKtCity(amapLocation.getCity())){
                            Double lng=Double.parseDouble(TiaoshiApplication.cityModel.getData().get(0).getLng());
                            Double lat=Double.parseDouble(TiaoshiApplication.cityModel.getData().get(0).getLat());
                            LatLonPoint llp=new LatLonPoint(lat, lng);
                            mclsearch(llp);
                            shortToast("当前定位城市未开通，已推荐开通城市地址");
                            return;
                        }
                        lp = new LatLonPoint(latitude, longitude);
                        mclsearch(lp);

                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        LogUtil.e("AmapError", "location Error, ErrCode:"
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
                            shortToast("定位失败，请稍后再试");
                        }
                    }
                }
            }
        };
        //设置定位回调监听
        aMapLocationClient.setLocationListener(aMapLocationListener);
        aMapLocationClient.startLocation();
    }

    private void mclsearch(LatLonPoint lp) {
        query = new PoiSearch.Query("", "汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|" +
                "医疗保健服务|住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|" +
                "金融保险服务|道路附属设施|地名地址信息", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(3);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);// 设置查第一页
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

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        Log.e("33", "hehe" + "  " + i);
        if (i == 1000) {
            if (poiResult != null && poiResult.getQuery() != null) {// 搜索poi的结果
                if (poiResult.getQuery().equals(query)) {// 是否是同一条
                    poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    if (poiItems != null && poiItems.size() > 0) {
                        Log.e("poiItems.size", poiItems.size() + "");
                        ShowTipsAdapter aAdapter = new ShowTipsAdapter(poiItems);
                        li_poi.setAdapter(aAdapter);
                    }
                }
            } else {
                ToastUtil.show(LocationActivity.this, "对不起，没有搜索到相关数据！", Toast.LENGTH_SHORT);
            }
        } else if (i == 27) {
            ToastUtil.show(LocationActivity.this, "搜索失败,请检查网络连接！", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==FROMMAP){
            if (resultCode==1){
                finish();
            }

        }else if (requestCode==LOGIN){
            if (resultCode== Activity.RESULT_OK){
                goAddAdds();
            }
        }
    }

    public class ShowTipsAdapter extends BaseAdapter {
        private List<PoiItem> listString;

        public ShowTipsAdapter(List<PoiItem> listString) {
            this.listString = listString;
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
            convertView = LayoutInflater.from(LocationActivity.this).inflate(R.layout.item_location_tuijian, null);
            TextView tv1 = (TextView) convertView.findViewById(R.id.route_inputs_tvName);
            Log.e("hehe", listString.get(position).getAdName() + " getAdName " + listString.get(position).getBusinessArea() + " getBusinessArea " + listString.get(position).getCityName() + " getCityName " +
                    listString.get(position).getEmail() + " getEmail  " + listString.get(position).getParkingType() + "getParkingType  " + listString.get(position).getSnippet() + " getSnippet " +
                    listString.get(position).getTitle() + " getTitle  " + listString.get(position).getTypeDes() + " getTypeDes " + listString.get(position).describeContents());
            final String address = listString.get(position).getTitle();
            final String area = listString.get(position).getCityName() + listString.get(position).getAdName() + listString.get(position).getSnippet();
            final String lng = listString.get(position).getLatLonPoint().getLongitude() + "";
            final String lat = listString.get(position).getLatLonPoint().getLatitude() + "";
            tv1.setText(address);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //--------------------------
                    Intent intent = new Intent("changeAddress");
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aMapLocationClient.onDestroy();
    }
}
