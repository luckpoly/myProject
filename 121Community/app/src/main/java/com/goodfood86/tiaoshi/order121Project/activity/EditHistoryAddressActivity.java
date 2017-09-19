package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.widget.TitleBarView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public class EditHistoryAddressActivity extends Activity implements View.OnClickListener,TextWatcher,PoiSearch.OnPoiSearchListener{
    @ViewInject(R.id.title_bar)
    private RelativeLayout title_bar;
    private TitleBarView titleBarView;
    @ViewInject(R.id.ll_findmap)
    private LinearLayout ll_findmap;
    @ViewInject(R.id.ll_other)
    private LinearLayout ll_other;
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
    private List<PoiItem> listString;
    private AMapLocation aMapLo;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationListener aMapLocationListener;
    private AMapLocationClientOption aMapLocationClientOption;
    private final static int REQUEST_SUPPLE=1;
    private final static int REQUEST_CHOOSE=2;
    private final static int REQUEST_SUPPLE_TWO=3;
    private PoiSearch.Query query;
    private ArrayList<PoiItem> poiItems;
    private PoiItem poiItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edithistoryaddress);
        ViewUtils.inject(this);
        initData();
        initListener();
        initTitlebar();
        initLocation();
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

    private void initData() {
        et_search.setText(getIntent().getStringExtra("address"));
    }

    private void initListener() {
        ll_findmap.setOnClickListener(this);
        rl_findmap.setOnClickListener(this);
        et_search.addTextChangedListener(this);
        iv_del.setOnClickListener(this);
        li_choose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                poiItem=poiItems.get(position);
                Intent intent1=new Intent(EditHistoryAddressActivity.this,SupplementAddress.class);
                startActivityForResult(intent1,REQUEST_SUPPLE_TWO);
            }
        });
    }

    private void initTitlebar() {
        titleBarView = new TitleBarView(this, title_bar, "编辑地址");
        et_search.setHint("请输入地址");
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
                        Log.e("===",amapLocation.getAddress()+"  "+amapLocation.getStreet()+"  "+amapLocation.getDistrict()+"  "+amapLocation.getLocationDetail()+"  "+amapLocation.getStreetNum());
                        location_address.setText(amapLocation.getAddress()+amapLocation.getStreetNum());
                        aMapLo=amapLocation;
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
        if (v!=null){
            switch (v.getId()){
                case R.id.ll_findmap:
                    Intent intent=new Intent(this,ChooseAddressActivity.class);
                    startActivityForResult(intent,REQUEST_CHOOSE);
                    break;
                case R.id.rl_findmap:
                    Intent intent1=new Intent(this,SupplementAddress.class);
                    startActivityForResult(intent1,REQUEST_SUPPLE);
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
        if (requestCode==REQUEST_CHOOSE){
            if (resultCode==RESULT_OK) {
                Intent intent = new Intent();
                intent.putExtra("position", getIntent().getStringExtra("position"));
                Log.e("position1",getIntent().getStringExtra("position"));
                intent.putExtra("suaddress", data.getStringExtra("suaddress"));
                intent.putExtra("city", data.getStringExtra("city"));
                intent.putExtra("title", data.getStringExtra("title"));
                intent.putExtra("street", data.getStringExtra("street"));
                intent.putExtra("lat", data.getDoubleExtra("lat",0.0));
                intent.putExtra("lng", data.getDoubleExtra("lng",0.0));
                intent.putExtra("phoneNo", data.getStringExtra("phoneNo"));
                setResult(RESULT_OK, intent);
                finish();
            }
        }else if (requestCode==REQUEST_SUPPLE){
            if (resultCode==RESULT_OK) {
                Intent intent = new Intent();
                intent.putExtra("position", getIntent().getStringExtra("position"));
                Log.e("position1",getIntent().getStringExtra("position"));
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
        }else if (requestCode==REQUEST_SUPPLE_TWO){
            if (resultCode==RESULT_OK) {
                Intent intent = new Intent();
                intent.putExtra("position", getIntent().getStringExtra("position"));
                Log.e("position1",getIntent().getStringExtra("position"));
                intent.putExtra("suaddress", data.getStringExtra("suaddress"));
                intent.putExtra("city", poiItem.getCityName());
                intent.putExtra("title", poiItem.getTitle());
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

        query = new PoiSearch.Query(newText,"汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|"+
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
        PoiSearch poiSearch = new PoiSearch(this,query);//初始化poiSearch对象
        poiSearch.setOnPoiSearchListener(this);//设置回调数据的监听器
        poiSearch.searchPOIAsyn();//开始搜索
        if (!"".equals(newText)){
            iv_del.setVisibility(View.VISIBLE);
        }else{
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
                    Log.e("poiItems.size",poiItems.size()+"  "+getIntent().getStringExtra("city"));
                    ShowTipsAdapter aAdapter = new ShowTipsAdapter(poiItems);
                    li_choose.setAdapter(aAdapter);
                    if (poiItems == null || poiItems.size() == 0) {
                        ToastUtil.show(EditHistoryAddressActivity.this,
                                "对不起，没有搜索到相关数据！", Toast.LENGTH_SHORT);
                    }
                }
            } else {
                ToastUtil
                        .show(EditHistoryAddressActivity.this, "对不起，没有搜索到相关数据！", Toast.LENGTH_SHORT);
            }
        } else if (i == 27) {
            ToastUtil
                    .show(EditHistoryAddressActivity.this, "搜索失败,请检查网络连接！", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    public class ShowTipsAdapter extends BaseAdapter {

        public ShowTipsAdapter(List<PoiItem> listString){
            EditHistoryAddressActivity.this.listString=listString;
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
            ViewHolder holder=new ViewHolder();
            if (convertView==null){
                convertView= LayoutInflater.from(EditHistoryAddressActivity.this).inflate(R.layout.route_inputs,null);
                holder.tv1= (TextView) convertView.findViewById(R.id.online_user_list_item_textview);
                holder.tv2= (TextView) convertView.findViewById(R.id.online_user_list_item_tv);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            holder.tv1.setText(listString.get(position).getTitle());
            holder.tv2.setText(listString.get(position).getCityName()+listString.get(position).getSnippet());
            return convertView;
        }
        class ViewHolder{
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
