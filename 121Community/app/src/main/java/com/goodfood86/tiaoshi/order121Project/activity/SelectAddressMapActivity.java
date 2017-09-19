package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
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
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.SelectAddressListViewItemAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.HistoryAddressListModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/29.
 */
public class SelectAddressMapActivity extends Activity implements View.OnClickListener, TextWatcher, PoiSearch.OnPoiSearchListener {
    @ViewInject(R.id.ll_findmap)
    private LinearLayout ll_findmap;
    @ViewInject(R.id.ll_other)
    private LinearLayout ll_other;
    @ViewInject(R.id.ll_map_address)
    private LinearLayout ll_map_address;
    @ViewInject(R.id.li_choose)
    private ListView li_choose;
    @ViewInject(R.id.et_search)
    private EditText et_search;
    @ViewInject(R.id.iv_del)
    private ImageView iv_del;
    @ViewInject(R.id.location_address)
    private TextView location_address;
    @ViewInject(R.id.rl_findmap)
    private RelativeLayout rl_findmap;
    @ViewInject(R.id.ll_his)
    private LinearLayout ll_his;
    @ViewInject(R.id.lv_hisaddress)
    private ListView lv_hisaddress;
    private List<PoiItem> listString;
    @ViewInject(R.id.rl_error)
    private RelativeLayout rl_error;
    @ViewInject(R.id.nav_back)
    public ImageView nav_back;
    @ViewInject(R.id.nav_title)
    public TextView nav_title;

    String what;
    private AMapLocation aMapLo;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationListener aMapLocationListener;
    private AMapLocationClientOption aMapLocationClientOption;
    private LatLng latlng;
    private final static int REQUEST_SUPPLE = 1;
    private final static int REQUEST_CHOOSE = 2;
    private final static int REQUEST_SUPPLE_TWO = 3;
    private PoiSearch.Query query;
    private ArrayList<PoiItem> poiItems;
    private PoiItem poiItem;
    private List<HistoryAddressListModel.DataEntity.ListEntity> hisaddressList = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        HistoryAddressListModel historyAddressListModel = (HistoryAddressListModel) msg.obj;
                        if (historyAddressListModel.getRespCode() == 0) {
                            hisaddressList.clear();
                            hisaddressList = historyAddressListModel.getData().getList();
                            initAdapter();
                            selectAddressListViewItemAdapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
        }
    };
    private SelectAddressListViewItemAdapter selectAddressListViewItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectaddress);
        ViewUtils.inject(this);
        initListener();
        initLocation();
        if (Order121Application.isLogin()) {
            initData();
            lv_hisaddress.setVisibility(View.VISIBLE);
        } else {
            lv_hisaddress.setVisibility(View.GONE);
        }
        initTitlebar();
    }

    public void onResume() {
        super.onResume();
        //友盟统计
       //已删
    }

    public void onPause() {
        super.onPause();
        //友盟统计
        //已删
    }

    private void initAdapter() {
        selectAddressListViewItemAdapter = new SelectAddressListViewItemAdapter(hisaddressList, this);
        lv_hisaddress.setAdapter(selectAddressListViewItemAdapter);
    }

    private void initData() {
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("token", Order121Application.globalLoginModel.getData().getToken());
        Order121Application.getGlobalHttpUtils().send(HttpRequest.HttpMethod.POST, G.Host.HISTORY_ADDRESSLIST, requestParams, new MyRequestCallBack(this, handler, 1, new HistoryAddressListModel()));
    }

    private void initListener() {
        ll_findmap.setOnClickListener(this);
        rl_findmap.setOnClickListener(this);
        et_search.addTextChangedListener(this);
        iv_del.setOnClickListener(this);
        li_choose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                poiItem = poiItems.get(position);
                Intent intent1 = new Intent(SelectAddressMapActivity.this, SupplementAddress.class);
                intent1.putExtra("type",getIntent().getStringExtra("what"));
                startActivityForResult(intent1, REQUEST_SUPPLE_TWO);
            }
        });
        lv_hisaddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (hisaddressList.get(position).getCity().equals(Order121Application.globalCity)||what.equals("4")) {
                    Log.e("getCity", hisaddressList.get(position).getCity() + "   " + Order121Application.globalCity);
                    Intent intent = new Intent();
                    intent.putExtra("suaddress", hisaddressList.get(position).getAddressDetail());
                    intent.putExtra("city", hisaddressList.get(position).getCity());
                    intent.putExtra("title", hisaddressList.get(position).getAddress());
                    intent.putExtra("street", hisaddressList.get(position).getArea());
                    intent.putExtra("lat", Double.parseDouble(hisaddressList.get(position).getLat()));
                    intent.putExtra("lng", Double.parseDouble(hisaddressList.get(position).getLng()));
                    intent.putExtra("phoneNo", hisaddressList.get(position).getPhone());
                    setResult(RESULT_OK, intent);
                    finish();
//                } else {
//                    Log.e("getCity", hisaddressList.get(position).getCity() + "   " + Order121Application.globalCity);
//                    ToastUtil.showShort(SelectAddressMapActivity.this, "当前城市与所选城市不一致");
//                }
            }
        });
    }

    private void initTitlebar() {
        nav_back.setVisibility(View.VISIBLE);
        nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        what = getIntent().getStringExtra("what");
        Log.e("__", what);
        if (what.equals("2")) {
//            titleBarView = new TitleBarView(this, title_bar, "收件地址");
            nav_title.setText("收件地址");
            nav_back.setVisibility(View.VISIBLE);
            et_search.setHint("请输入收件地址");
        } else if (what.equals("1")) {
//            titleBarView = new TitleBarView(this, title_bar, "寄件地址");
            et_search.setHint("请输入寄件地址");
            nav_title.setText("寄件地址");
        } else if (what.equals("3")) {
//            titleBarView = new TitleBarView(this, title_bar, "新增地址");
            nav_title.setText("新增地址");
            et_search.setHint("请输入新增地址");
            ll_his.setVisibility(View.GONE);
        }else if (what.equals("4")){
            nav_title.setText("输入地址");
            et_search.setHint("请输入地址");
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
                        Log.e("===", amapLocation.getAddress() + "  " + amapLocation.getStreet() + "  " + amapLocation.getDistrict() + "  " + amapLocation.getLocationDetail() + "  " + amapLocation.getStreetNum());
                        location_address.setText(amapLocation.getAddress() + amapLocation.getStreetNum());
                        aMapLo = amapLocation;
                        latlng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
//                        if (amapLocation.getErrorCode() == 2) {
//                            locationBuilder.setMessage("wifi信息不足,请使用gprs定位").setPositiveButton("确定", null).create().show();
//                        } else if (amapLocation.getErrorCode() == 4) {
//                            locationBuilder.setMessage("网络连接失败,无法启用定位").setPositiveButton("确定", null).create().show();
//                        } else if (amapLocation.getErrorCode() == 9) {
//                            locationBuilder.setMessage("定位初始化失败，请重新启动定位").setPositiveButton("确定", null).create().show();
//                        } else if (amapLocation.getErrorCode() == 11) {
//                            locationBuilder.setMessage("基站信息错误").setPositiveButton("确定", null).create().show();
//                        } else if (amapLocation.getErrorCode() == 12) {
//                            locationBuilder.setMessage("缺少定位权限,请在设备中开启GPS并允许定位").setPositiveButton("确定", null).create().show();
//                        } else {
//                            toast.show();
//                        }
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
        if (v != null) {
            switch (v.getId()) {
                case R.id.ll_findmap:
                    Intent intent = new Intent(this, ChooseAddressActivity.class);
                    intent.putExtra("type",getIntent().getStringExtra("what"));
                    startActivityForResult(intent, REQUEST_CHOOSE);
                    break;
                case R.id.rl_findmap:
//                    if (aMapLo.getCity().equals(Order121Application.globalCity)) {
                        Intent intent1 = new Intent(this, SupplementAddress.class);
                  intent1.putExtra("type",getIntent().getStringExtra("what"));
                        startActivityForResult(intent1, REQUEST_SUPPLE);
//                    } else {
//                        ToastUtil.showShort(this, "当前城市与所选城市不一致");
//                    }
                    break;
                case R.id.iv_del:
                    et_search.setText("");
                    break;
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHOOSE) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent();
                intent.putExtra("suaddress", data.getStringExtra("suaddress"));
                intent.putExtra("city", data.getStringExtra("city"));
                intent.putExtra("title", data.getStringExtra("title"));
                intent.putExtra("street", data.getStringExtra("street"));
                intent.putExtra("lat", data.getDoubleExtra("lat", 0.0));
                intent.putExtra("lng", data.getDoubleExtra("lng", 0.0));
                intent.putExtra("phoneNo", data.getStringExtra("phoneNo"));
                setResult(RESULT_OK, intent);
                finish();
            }
        } else if (requestCode == REQUEST_SUPPLE) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent();
                intent.putExtra("suaddress", data.getStringExtra("suaddress"));
                intent.putExtra("city", aMapLo.getCity());
                intent.putExtra("title", aMapLo.getAddress());
                intent.putExtra("street", aMapLo.getStreet());
                intent.putExtra("lat", aMapLo.getLatitude());
                intent.putExtra("lng", aMapLo.getLongitude());
                intent.putExtra("phoneNo", data.getStringExtra("phoneNo"));
                setResult(RESULT_OK, intent);
                finish();
            }
        } else if (requestCode == REQUEST_SUPPLE_TWO) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent();
                intent.putExtra("suaddress", data.getStringExtra("suaddress"));
                intent.putExtra("city", poiItem.getCityName());
                intent.putExtra("title", poiItem.getSnippet()+poiItem.getTitle());
                intent.putExtra("street", poiItem.getSnippet());
                intent.putExtra("lat", poiItem.getLatLonPoint().getLatitude());
                intent.putExtra("lng", poiItem.getLatLonPoint().getLongitude());
                intent.putExtra("phoneNo", data.getStringExtra("phoneNo"));
                setResult(RESULT_OK, intent);
                finish();
            }
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString().trim();
//        InputtipsQuery inputquery = new InputtipsQuery(newText, getIntent().getStringExtra("city"));
//        Inputtips inputTips = new Inputtips(SelectAddressMapActivity.this, inputquery);
//        inputTips.setInputtipsListener(this);
//        inputTips.requestInputtipsAsyn();

        query = new PoiSearch.Query(newText, "汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|" +
                "医疗保健服务|住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务" +
                "金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施", Order121Application.globalCity);
// keyWord表示搜索字符串，
//第二个参数表示POI搜索类型，二者选填其一，
//POI搜索类型共分为以下20种：汽车服务|汽车销售|
//汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
//住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
//金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
//cityCode表示POI搜索区域的编码，是必须设置参数
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);//设置查询页码
        query.setCityLimit(true);
        PoiSearch poiSearch = new PoiSearch(this, query);//初始化poiSearch对象
        poiSearch.setOnPoiSearchListener(this);//设置回调数据的监听器
        poiSearch.searchPOIAsyn();//开始搜索
        if (!"".equals(newText)) {
            ll_other.setVisibility(View.GONE);
            li_choose.setVisibility(View.VISIBLE);
            iv_del.setVisibility(View.VISIBLE);
        } else {
            ll_other.setVisibility(View.VISIBLE);
            li_choose.setVisibility(View.GONE);
            iv_del.setVisibility(View.GONE);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == 0) {
            if (poiResult != null && poiResult.getQuery() != null) {// 搜索poi的结果
                if (poiResult.getQuery().equals(query)) {// 是否是同一条
                    poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    Log.e("poiItems.size", poiItems.size() + "  " + getIntent().getStringExtra("city"));
                    ShowTipsAdapter aAdapter = new ShowTipsAdapter(poiItems);
                    li_choose.setAdapter(aAdapter);
                    if (poiItems == null || poiItems.size() == 0) {
                        rl_error.setVisibility(View.VISIBLE);
                    } else {
                        rl_error.setVisibility(View.GONE);
                    }
                }
            } else {
                rl_error.setVisibility(View.VISIBLE);
            }
        } else if (i == 27) {
            ToastUtil
                    .show(SelectAddressMapActivity.this, "搜索失败,请检查网络连接！", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    public class ShowTipsAdapter extends BaseAdapter {

        public ShowTipsAdapter(List<PoiItem> listString) {
            SelectAddressMapActivity.this.listString = listString;
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
                convertView = LayoutInflater.from(SelectAddressMapActivity.this).inflate(R.layout.route_inputs, null);
                holder.tv1 = (TextView) convertView.findViewById(R.id.online_user_list_item_textview);
                holder.tv2 = (TextView) convertView.findViewById(R.id.online_user_list_item_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv1.setText(listString.get(position).getTitle());
            holder.tv2.setText(listString.get(position).getCityName() + listString.get(position).getSnippet());
            return convertView;
        }

        class ViewHolder {
            private TextView tv1;
            private TextView tv2;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aMapLocationClient.stopLocation();
        aMapLocationClient.onDestroy();
    }
}
