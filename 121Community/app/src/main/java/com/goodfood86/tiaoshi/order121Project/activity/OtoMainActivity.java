package com.goodfood86.tiaoshi.order121Project.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.amap.api.services.route.WalkStep;
import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.pickerview.OptionsPickerView;
import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.CityListModel;
import com.goodfood86.tiaoshi.order121Project.model.GetWordModel;
import com.goodfood86.tiaoshi.order121Project.model.GlobalLoginModel;
import com.goodfood86.tiaoshi.order121Project.model.MoneyTableModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.widget.TitleBarView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class OtoMainActivity extends FragmentActivity implements View.OnClickListener , RouteSearch.OnRouteSearchListener {
    @ViewInject(R.id.iv_back)
    private ImageView iv_back;
    @ViewInject(R.id.tv_city)
    private TextView tv_city;
    @ViewInject(R.id.convenient_banner)
    private ConvenientBanner convenient_banner;
    @ViewInject(R.id.rl_sendaddress)
    private RelativeLayout rl_sendaddress;
    @ViewInject(R.id.tv_sendaddress)
    private TextView tv_sendaddress;
    @ViewInject(R.id.rl_reciveaddress)
    private RelativeLayout rl_reciveaddress;
    @ViewInject(R.id.tv_reciveaddress)
    private TextView tv_reciveaddress;
    @ViewInject(R.id.rl_gettime)
    private RelativeLayout rl_gettime;
    @ViewInject(R.id.tv_gettime)
    private TextView tv_gettime;
    @ViewInject(R.id.btn_minus)
    private Button btn_minus;
    @ViewInject(R.id.btn_add)
    private Button btn_add;
    @ViewInject(R.id.tv_weight)
    private TextView tv_weight;
    @ViewInject(R.id.tv_distance)
    private TextView tv_distance;
    @ViewInject(R.id.tv_price)
    private TextView tv_price;
    @ViewInject(R.id.btn_go)
    private Button btn_go;
    private int wei;
    private TitleBarView titleBarView;
    private FragmentManager fragmentManager;
    private ArrayList<String> options1Items = new ArrayList<String>();
    private  ArrayList<ArrayList<ArrayList<String>>> options3Items  = new ArrayList<ArrayList<ArrayList<String>>>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private OptionsPickerView pvOptions;
    private String mCity;
    private final int SENDADDRESS=1;
    private final int RECIVEADDRESS=2;
    private Toast toast;
    private boolean currentBackState;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private AMapLocationListener aMapLocationListener;
    private final static int RESQUEST_CHOOSECITY=3;
    private Double mSendLat=0.0,mSendLng=0.0,mReceiveLat=0.0,mReceiveLng=0.0;
    private RouteSearch routeSearch;
    private int distance;
    private String[] cityList;
    private List<String> picUrlList=new ArrayList<>();
    private MoneyTableModel.DataBean data;
    private String cityString;
    public static boolean isForeground = false;
    private MessageReceiver mMessageReceiver;
    private List<GetWordModel.DataEntity.WordsEntity> wordsEntityList;
    private int ACCESS_FINE_LOCATION_CODE=0x11;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg!=null){
                switch (msg.what){
                    case 1:
                        CityListModel cityListModel= (CityListModel) msg.obj;
                        if (cityListModel.getRespCode()==0){
                            cityString=cityListModel.getData().getCity();
                            cityList=cityString.split("\\|");
                            Log.e("cityList",cityList.length+"");
                        }
                        break;
                    case 2:
                        MoneyTableModel moneyTableModel= (MoneyTableModel) msg.obj;
                        if (moneyTableModel.getRespCode()==0){
                            Order121Application.globalMoneyTableModel=moneyTableModel;
                            data=moneyTableModel.getData();
                            wei=Integer.parseInt(data.getWeightMin());
                            tv_weight.setText(wei+"公斤以下");
                            Log.e("minweight",data.getWeightMin());
                        }
                        break;
                    case 3:
                        GlobalLoginModel globalLoginModel= (GlobalLoginModel) msg.obj;
                        if (globalLoginModel!=null) {
                            if (globalLoginModel.getRespCode() == 0) {
                                Order121Application.globalLoginModel = globalLoginModel;
                                sendBroadcast(new Intent("updateSlidingFragment"));
                                if (Order121Application.isLogin()){
                                    jPush();
                                }
                            }else{
                                Order121Application.globalLoginModel=null;
                            }
                        }
                        break;
                    case 4:
                        GetWordModel getWordModel= (GetWordModel) msg.obj;
                        if (getWordModel!=null){
                            if (getWordModel.getRespCode()==0) {
                                if (picUrlList.size() == 0) {
                                    wordsEntityList = getWordModel.getData().getWords();
                                    for (int i = 0; i < wordsEntityList.size(); i++) {
                                        picUrlList.add(wordsEntityList.get(i).getImg());
                                    }
                                    initBanner();
                                }
                            }
                            }

                        break;
                }
            }
        }
    };
    private int price;
    private double distancedouble;
    private RequestParams logindata;
    private HttpUtils httpUtils;
    private String mSendAddress;
    private String mSendSuAddress;
    private String mReceiveAddress;
    private String mReceiveSuAddress;

    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otoordermain);
        ViewUtils.inject(this);
        initView();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请ACCESS_FINE_LOCATION权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_CODE);
        }else {
            initLocation();
        }
        initData();
        initListener();
        initPopwindows();
//        initSlidingMenu();
        registerMessageReceiver();



    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (extras!=null&&extras.length()>0) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
            }
        }
    }
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageReceiver);
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
        //友盟统计
       //已删
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
        //友盟统计
        //已删
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
        //设置定位间隔,单位毫秒,默认为2000ms
        aMapLocationClientOption.setInterval(3500);
        //给定位客户端对象设置定位参数
        aMapLocationClient.setLocationOption(aMapLocationClientOption);

//        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.location));
//        aMap.setMyLocationStyle(myLocationStyle);

        //声明定位回调监听器
        aMapLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                       mCity=amapLocation.getCity();
                        Order121Application.globalCity=mCity;
                        tv_city.setText(mCity);
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                        ToastUtil.showShort(OtoMainActivity.this,amapLocation.getErrorInfo());
                    }
                }
            }
        };
        //设置定位回调监听
        aMapLocationClient.setLocationListener(aMapLocationListener);

        //启动定位
        aMapLocationClient.startLocation();
        //自动登录

    }

    private void initData() {

        Order121Application.getGlobalHttpUtils().send(HttpRequest.HttpMethod.POST, G.Host.CITYLIST,new MyRequestCallBack(this,handler,1,new CityListModel()));
        Log.e("----","____");
        Order121Application.getGlobalHttpUtils().send(HttpRequest.HttpMethod.POST, G.Host.GET_MONEYTABLE,new MyRequestCallBack(this,handler,2,new MoneyTableModel()));
        RequestParams requestParams=new RequestParams();
        requestParams.addBodyParameter("code","UserOrderIndex");
        Order121Application.getGlobalHttpUtils().send(HttpRequest.HttpMethod.POST, G.Host.GET_WORD,requestParams,new MyRequestCallBack(this,handler,4,new GetWordModel()));
    }

    private void initPopwindows() {
        pvOptions = new OptionsPickerView(this);
        pvOptions.setPicker(options1Items, options2Items,options3Items,true);
        pvOptions.setCyclic(false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(1, 1, 1);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {

            }
        });

    }

    private void initView() {
        convenient_banner.startTurning(4000);
        toast = Toast.makeText(this,"再按一次回退键退出程序", Toast.LENGTH_SHORT);
        fragmentManager = getSupportFragmentManager();
        options1Items.add("上午");
        options1Items.add("中午");
        options1Items.add("下午");
        ArrayList<String> options2Items_1=new ArrayList<>();
        options2Items_1.add("7点");
        options2Items_1.add("8点");
        ArrayList<String> options2Items_2=new ArrayList<>();
//        for (int i=0;i<24;i++){
//            if (i<10){
//                options2Items_2.add("0"+i);
//            }else{
//                options2Items_2.add(i+"");
//            }
//        }
        options2Items_2.add("11点");
        options2Items_2.add("12点");
        ArrayList<String> options2Items_3=new ArrayList<>();
        options2Items_3.add("17点");
        options2Items_3.add("18点");
        options2Items.add(options2Items_1);
        options2Items.add(options2Items_2);
        options2Items.add(options2Items_3);
        ArrayList list=new ArrayList();
        ArrayList list1=new ArrayList();
        list1.add("00分");
        list1.add("10分");
        list1.add("20分");
        list1.add("30分");
        list1.add("40分");
        list1.add("50分");
        list.add(list1);
        list.add(list1);
        list.add(list1);
        options3Items.add(list);
        options3Items.add(list);
        options3Items.add(list);

    }

    private void initListener() {
        rl_sendaddress.setOnClickListener(this);
        rl_reciveaddress.setOnClickListener(this);
        rl_gettime.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_go.setOnClickListener(this);

        httpsend();
    }

    @Override
    public void onClick(View v) {
        if (v!=null){
            if (data==null||cityList==null){
                initData();

                ToastUtil.showShort(this,"网络请求失败,正在重新加载！");
                return;
            }
            if (mCity==null){
                initLocation();
                return;
            }
            switch (v.getId()){
                case R.id.rl_sendaddress:
                    if (goSelectAddress()) {
                        Intent intent = new Intent(OtoMainActivity.this, SelectAddressMapActivity.class);
                        intent.putExtra("what", "1");
                        intent.putExtra("city", mCity);
                        startActivityForResult(intent, SENDADDRESS);
                    }else {
                        ToastUtil.showShort(this,"该城市暂未开通，请重新选择");
                    }
                    break;
                case R.id.rl_reciveaddress:
                    if (goSelectAddress()) {
                        Intent intent1 = new Intent(OtoMainActivity.this, SelectAddressMapActivity.class);
                        intent1.putExtra("what", "2");
                        intent1.putExtra("city", mCity);
                        startActivityForResult(intent1, RECIVEADDRESS);
                    }else {
                        ToastUtil.showShort(this,"该城市暂未开通，请重新选择");
                    }
                    break;
                case R.id.rl_gettime:
//                    pvOptions.show();
                    break;
                case R.id.btn_minus:
                    if (data!=null) {
                        if (wei < Integer.parseInt(data.getWeightMin())) {
                            --wei;
                            tv_weight.setText(wei + "公斤以下");
                        }
                        if (!(TextUtils.isEmpty(tv_sendaddress.getText().toString()) || TextUtils.isEmpty(tv_reciveaddress.getText().toString()))) {
                            TotalPrice();
                        }
                    }
                    break;
                case R.id.btn_add:
                    if (data!=null) {
                        if (wei > Integer.parseInt(data.getWeightMax())) {
                            ++wei;
                            tv_weight.setText(wei + "公斤以下");
                        } else {
                            ToastUtil.showShort(this, "暂不支持" + data.getWeightMax() + "公斤以上的配送");
                        }
                        if (!(TextUtils.isEmpty(tv_sendaddress.getText().toString()) || TextUtils.isEmpty(tv_reciveaddress.getText().toString()))) {
                            TotalPrice();
                        }
                    }
                    break;
                case R.id.btn_go:
                    if (correct()) {
                        if (!(mSendLat == 0.0 || mSendLng == 0.0 || mReceiveLat == 0.0 || mReceiveLng == 0.0)) {
                            Intent intent3 = new Intent(OtoMainActivity.this, SubmitOrderActivity.class);
                            intent3.putExtra("mSendLat", mSendLat);
                            intent3.putExtra("mSendLng", mSendLng);
                            intent3.putExtra("mReceiveLat", mReceiveLat);
                            intent3.putExtra("mReceiveLng", mReceiveLng);
                            intent3.putExtra("price", price);
                            intent3.putExtra("weight", wei);
                            intent3.putExtra("distance", distancedouble);
                            intent3.putExtra("send_address", mSendAddress);
                            intent3.putExtra("send_detailaddress", mSendSuAddress);
                            intent3.putExtra("receive_address",mReceiveAddress);
                            intent3.putExtra("receive_detailaddress", mReceiveSuAddress);
                            startActivity(intent3);
                        } else {
                            ToastUtil.show(this, "请选择正确的寄收货地址", Toast.LENGTH_SHORT);
                        }
                    }
                    break;
            }
        }

    }
    private boolean goSelectAddress(){
        for (int i=0;i<cityList.length;i++){
            if (mCity.equals(cityList[i])){
                return true;
            }
        }
        return false;
    }

    private boolean correct() {

        if (wei>Integer.parseInt(data.getWeightMax())){
            ToastUtil.show(this,"暂不支持"+data.getWeightMax()+"公斤以上的配送",Toast.LENGTH_SHORT);
            return false;
        }
        if (distance>Integer.parseInt(data.getDistanceMax())){
            ToastUtil.show(this,"暂不支持"+data.getDistanceMax()+"公里以上的配送",Toast.LENGTH_SHORT);
            return false;
        }

        return true;
    }

//    private void initSlidingMenu() {
//        setBehindContentView(R.layout.sliding_layout);//设置侧拉页面用的布局文件
//        slidingMenu = getSlidingMenu();
//        slidingMenu.setMode(SlidingMenu.LEFT);//设置侧拉页面从哪边滑出
//        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置在屏幕哪里滑动滑出侧拉页面
//        slidingMenu.setBehindOffset(120);//设置屏幕预留宽度
//        slidingMenu.setFadeDegree(1.0f);// 设置渐入渐出效果的值
//        slidingMenu.addIgnoredView(convenient_banner);
//        fragmentManager.beginTransaction().replace(R.id.sliding_layout, new SlidingFragment(slidingMenu,OtoMainActivity.this)).commit();// 把侧拉页面使用的布局文件用一个fragment来替换
//    }
    private void getDistance(){
        LatLonPoint point_send=new LatLonPoint(mSendLat,mSendLng);
        LatLonPoint point_receive=new LatLonPoint(mReceiveLat,mReceiveLng);
        RouteSearch.FromAndTo fromAndTo=new RouteSearch.FromAndTo(point_send,point_receive);
        routeSearch = new RouteSearch(this);//初始化routeSearch 对象
        routeSearch.setRouteSearchListener(this);//设置数据回调监听器
        RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo,RouteSearch.WalkDefault );//初始化query对象，fromAndTo是包含起终点信息，walkMode是不行路径规划的模式
        routeSearch.calculateWalkRouteAsyn(query);//开始算路
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK) {
            if (requestCode == SENDADDRESS) {
                mSendLat=data.getDoubleExtra("lat",0.0);
                mSendLng=data.getDoubleExtra("lng",0.0);
                mSendAddress=data.getStringExtra("title");
                mSendSuAddress=data.getStringExtra("suaddress");
                tv_sendaddress.setText(data.getStringExtra("title") + data.getStringExtra("suaddress"));
                Log.e("latlng",mSendLat+"  "+mSendLng);
            } else if (requestCode==RECIVEADDRESS){
                mReceiveLat=data.getDoubleExtra("lat",0.0);
                mReceiveLng=data.getDoubleExtra("lng",0.0);
                mReceiveAddress=data.getStringExtra("title");
                mReceiveSuAddress=data.getStringExtra("suaddress");
                tv_reciveaddress.setText(data.getStringExtra("title") + data.getStringExtra("suaddress"));
                Log.e("latlng",mReceiveLat+"  "+mReceiveLng);
            }else if (requestCode==RESQUEST_CHOOSECITY){
                mCity=data.getStringExtra("CurrentCity");
                Order121Application.globalCity=mCity;
                tv_city.setText(mCity);
            }
        }
        if (mSendLng!=0.0&&mReceiveLng!=0.0&&mSendLat!=0.0&&mReceiveLat!=0.0){
            getDistance();
        }
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
               List<WalkStep> walkSteps = walkRouteResult.getPaths().get(0).getSteps();
                Float distancefloat=walkSteps.get(0).getDistance();
                for (int j=1;j<walkSteps.size();j++){
                    Log.e("distancefloat",walkSteps.get(j).getDistance()+"");
                    distancefloat+=walkSteps.get(j).getDistance();
                }
                distancedouble=Double.parseDouble(String.valueOf(distancefloat/1000));
                distance= (int) Math.ceil(distancedouble);
                tv_distance.setText(distancedouble+"");
                TotalPrice();
            } else {
                ToastUtil.show(OtoMainActivity.this,"对不起，没有搜索到相关数据！",Toast.LENGTH_SHORT);
            }
        } else if (i == 27) {
            ToastUtil.show(OtoMainActivity.this,"搜索失败,请检查网络连接！",Toast.LENGTH_SHORT);
        }
    }

    //计算价格
    private void TotalPrice(){
        if (wei<Integer.parseInt(data.getWeightMin())&&distance<Integer.parseInt(data.getDistanceMin())){
            price=Integer.parseInt(data.getDistance())+Integer.parseInt(data.getWeight());
        }else if (wei<Integer.parseInt(data.getWeightMin())&&distance>=Integer.parseInt(data.getDistanceMin())){
            price=Integer.parseInt(data.getDistance())+((distance-Integer.parseInt(data.getDistanceMin())/Integer.parseInt(data.getDistanceSpacing()))*Integer.parseInt(data.getDistancePrice()))+Integer.parseInt(data.getWeight());
        }else if (distance<Integer.parseInt(data.getDistanceMin())&&wei>=Integer.parseInt(data.getWeightMin())){
            price=Integer.parseInt(data.getDistance())+(wei-Integer.parseInt(data.getWeightMin())/Integer.parseInt(data.getWeightSpacing()))*Integer.parseInt(data.getWeightPrice())+Integer.parseInt(data.getWeight());
        }else{
            price=Integer.parseInt(data.getDistance())+(wei-Integer.parseInt(data.getWeightMin())/Integer.parseInt(data.getWeightSpacing()))*Integer.parseInt(data.getWeightPrice())+Integer.parseInt(data.getWeight())+
                    ((distance-Integer.parseInt(data.getDistanceMin())/Integer.parseInt(data.getDistanceSpacing()))*Integer.parseInt(data.getDistancePrice()));
        }
        tv_price.setText(price+"");
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
            //加载图片
            Order121Application.getGlobalBitmapUtils().display(imageView, data);
            //绑定事件
            imageView.setOnClickListener(new MyOnClickListener(position));
        }
    }
    private class MyOnClickListener implements View.OnClickListener {
        int mPosition;
        public MyOnClickListener( int position){
            this.mPosition=position;
        }

        @Override
        public void onClick(View v) {
            if (wordsEntityList.get(mPosition).getHref()!=null&&!wordsEntityList.get(mPosition).getHref().equals("#")) {
                Intent intent = new Intent(OtoMainActivity.this, WebViewShowActivity.class);
                intent.putExtra("url", wordsEntityList.get(mPosition).getHref());
                startActivity(intent);
            }
        }
    }
    private void jPush(){
        JPushInterface.setAlias(OtoMainActivity.this, Order121Application.globalLoginModel.getData().getId() + "", new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.e("11", i + "");
                if (i == 6002) {
                    jPush();
                }
            }
        });
    }


    /**
     * 自动登录
     */
    private void httpsend() {
        httpUtils= Order121Application.getGlobalHttpUtils();
        SharedPreferences sp=this.getSharedPreferences(G.SP.APP_NAME,Context.MODE_PRIVATE);
       String user= sp.getString(G.SP.LOGIN_NAME, "0");
        String psw=sp.getString(G.SP.LOGIN_PWD,"0");
        if (user.length()==11){
            logindata=new RequestParams();
            logindata.addBodyParameter("username",user);
            logindata.addBodyParameter("password", MD5.getMD5(psw));
            httpUtils.send(HttpRequest.HttpMethod.POST, G.Host.LOGIN,logindata,new MyRequestCallBack(OtoMainActivity.this,handler,3,new GlobalLoginModel()));
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        initLocation();
    }
}

