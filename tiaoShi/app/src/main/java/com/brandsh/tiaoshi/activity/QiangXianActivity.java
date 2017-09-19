package com.brandsh.tiaoshi.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.QiangXianListAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.fragment.ProductDetailFragment;
import com.brandsh.tiaoshi.model.QiangXianBannerJsonData;
import com.brandsh.tiaoshi.model.QiangXianJsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.brandsh.tiaoshi.widget.SelfPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lidroid.xutils.HttpUtils;
import com.mingle.widget.ShapeLoadingDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class QiangXianActivity extends FragmentActivity implements View.OnClickListener {
    private RelativeLayout qiangxian_rlBack;
    private RelativeLayout guanzhu_list_rlNoItem;
    private TextView qiangxian_tvStoreCount;
    private SelfPullToRefreshListView pullToRefreshListView;
    private View headView;
    private ConvenientBanner qiangxian_head_cb;
    private ShapeLoadingDialog loadingDialog;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationListener aMapLocationListener;
    private AMapLocationClientOption aMapLocationClientOption;
    private double latitude;
    private double longitude;
    private String page;
    private Toast toast;
    private List<QiangXianJsonData.DataBean.ListBean> resList;
    private HashMap dataRequestMap;
    private HttpUtils httpUtils;
    private QiangXianListAdapter qiangXianListAdapter;
    private List<String> picUrlList;
    private AlertDialog.Builder locationBuilder;
    private List<String> urlList;
    private List<String> titleList;
    private HashMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiang_xian);
        //沉浸状态栏
        AppUtil.Setbar(this);

        initView();

        initLocation();
    }

    private void initView() {
        qiangxian_rlBack = (RelativeLayout) findViewById(R.id.qiangxian_rlBack);
        guanzhu_list_rlNoItem = (RelativeLayout) findViewById(R.id.guanzhu_list_rlNoItem);
        qiangxian_tvStoreCount = (TextView) findViewById(R.id.qiangxian_tvStoreCount);
        TextView activity_title_name = (TextView) findViewById(R.id.activity_title_name);
        activity_title_name.setText(getIntent().getStringExtra("title"));
        pullToRefreshListView = (SelfPullToRefreshListView) findViewById(R.id.qiangxian_PTRListView);
        headView = LayoutInflater.from(this).inflate(R.layout.qiangxian_headview, null);

        qiangxian_head_cb = (ConvenientBanner) headView.findViewById(R.id.qiangxian_head_cb);
        qiangxian_head_cb.startTurning(4000);

        loadingDialog = ProgressHUD.show(this, "努力加载中...");
        loadingDialog.show();
        picUrlList = new LinkedList<>();
        locationBuilder = new AlertDialog.Builder(this).setTitle("系统提示");
        page = "1";
        toast = Toast.makeText(this, "定位失败，请检查" +
                "网络或打开设置检查是否已经启动GPS", Toast.LENGTH_SHORT);
        dataRequestMap = new HashMap();
        httpUtils = TiaoshiApplication.getGlobalHttpUtils();

        pullToRefreshListView.getRefreshableView().addHeaderView(headView);

        qiangxian_rlBack.setOnClickListener(this);
        setListenerToPTRListView();

        resList = new LinkedList<>();
        urlList = new LinkedList<>();
        titleList = new LinkedList<>();

        qiangXianListAdapter = new QiangXianListAdapter(resList, QiangXianActivity.this);
        pullToRefreshListView.setAdapter(qiangXianListAdapter);
        map = new HashMap<>();
        map.put("code", "APPUserActivityAfternoon");
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.GET_WORD, map, new MyCallBack(3, QiangXianActivity.this, new QiangXianBannerJsonData(), handler));
    }

    //初始化banner
    private void initBanner() {
        if (picUrlList.size() > 0) {
            qiangxian_head_cb.setPointViewVisible(true);
            qiangxian_head_cb.setPages(new CBViewHolderCreator<BannerHolderView>() {
                @Override
                public BannerHolderView createHolder() {
                    return new BannerHolderView();
                }
            }, picUrlList).setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);
            qiangxian_head_cb.setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
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
            TiaoshiApplication.getGlobalBitmapUtils().display(imageView, data);
            //绑定事件
            imageView.setOnClickListener(new MyOnClickListener(position));
        }
    }

    class MyOnClickListener implements View.OnClickListener {
        private int position;

        public MyOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (urlList.size() > 0) {
                Intent intent = new Intent(QiangXianActivity.this, BannerDetailActivity.class);
                intent.putExtra("url", urlList.get(position));
                intent.putExtra("title", titleList.get(position));
                if (!TextUtils.isEmpty(urlList.get(position)) && !urlList.get(position).equals("#")) {
                    startActivity(intent);
                }
            }
        }
    }

    private void setListenerToPTRListView() {
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = FCActivity.getFCActivityIntent(QiangXianActivity.this, ProductDetailFragment.class);
                intent.putExtra("goods_id", resList.get(position - 2).getGoodsId());
                intent.putExtra("goods_name", resList.get(position - 2).getGoodsName());
                intent.putExtra("shop_id", resList.get(position - 2).getShopId());
                intent.putExtra("shop_name", resList.get(position - 2).getShopName());
                intent.putExtra("min_cost", resList.get(position - 2).getFreeSend());
                intent.putExtra("is_new", "1");
                startActivity(intent);
            }
        });
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = "1";
                initLocation();
                OkHttpManager.postAsync(G.Host.GET_WORD, map, new MyCallBack(3, QiangXianActivity.this, new QiangXianBannerJsonData(), handler));
                handler.sendEmptyMessageDelayed(150, 5000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                OkHttpManager.postAsync(G.Host.ACTIVITY_LIST + "?page=" + page, dataRequestMap, new MyCallBack(2, QiangXianActivity.this, new QiangXianJsonData(), handler));
                handler.sendEmptyMessageDelayed(150, 5000);
            }
        });
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
                        latitude = amapLocation.getLatitude();
                        longitude = amapLocation.getLongitude();
                        aMapLocationClient.onDestroy();
                        dataRequestMap.clear();
                        dataRequestMap.put("lat", TiaoshiApplication.Lat + "");
                        dataRequestMap.put("lng", TiaoshiApplication.Lng + "");
                        dataRequestMap.put("code", getIntent().getStringExtra("code"));
                        Log.e("code", getIntent().getStringExtra("code"));
                        dataRequestMap.put("actReq", "123456");
                        dataRequestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
                        String sign = SignUtil.getSign(dataRequestMap);
                        dataRequestMap.put("sign", Md5.toMd5(sign));
                        OkHttpManager.postAsync(G.Host.ACTIVITY_LIST + "?page=" + page, dataRequestMap, new MyCallBack(1, QiangXianActivity.this, new QiangXianJsonData(), handler));

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qiangxian_rlBack:
                finish();
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    loadingDialog.dismiss();
                    pullToRefreshListView.onRefreshComplete();
                    QiangXianJsonData qiangXianJsonData = (QiangXianJsonData) msg.obj;
                    if (qiangXianJsonData != null) {
                        if (qiangXianJsonData.getRespCode().equals("SUCCESS") && qiangXianJsonData.getData() != null) {
                            guanzhu_list_rlNoItem.setVisibility(View.GONE);
//                            qiangxian_tvStoreCount.setText(qiangXianJsonData.getData().getTotalCount()+"家");
                            page = qiangXianJsonData.getData().getNextPage() + "";
                            resList.clear();
                            resList.addAll(qiangXianJsonData.getData().getList());
                            qiangXianListAdapter.notifyDataSetChanged();
                            if (resList.size() >= Integer.parseInt(qiangXianJsonData.getData().getTotalCount())) {
                                pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                Toast.makeText(QiangXianActivity.this, "已经是最后一页了", Toast.LENGTH_SHORT).show();
                            } else if (qiangXianJsonData.getData().getList().size() < qiangXianJsonData.getData().getLimit()) {
                                pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                Toast.makeText(QiangXianActivity.this, "已经是最后一页了", Toast.LENGTH_SHORT).show();
                            } else {
                                pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                        } else if (qiangXianJsonData.getData() == null || qiangXianJsonData.getData().getList().size() == 0) {
                            Toast.makeText(QiangXianActivity.this, "暂时没有相关内容", Toast.LENGTH_SHORT).show();
                            guanzhu_list_rlNoItem.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(QiangXianActivity.this, qiangXianJsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 2:

                    loadingDialog.dismiss();

                    pullToRefreshListView.onRefreshComplete();
                    QiangXianJsonData qiangXianJsonData1 = (QiangXianJsonData) msg.obj;
                    if (qiangXianJsonData1 != null) {
                        if (qiangXianJsonData1.getRespCode().equals("SUCCESS")) {
                            qiangxian_tvStoreCount.setText(qiangXianJsonData1.getData().getTotalCount() + "家");
                            page = qiangXianJsonData1.getData().getNextPage() + "";
                            resList.addAll(qiangXianJsonData1.getData().getList());
                            qiangXianListAdapter.notifyDataSetChanged();
                            if (resList.size() >= Integer.parseInt(qiangXianJsonData1.getData().getTotalCount())) {
                                pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                Toast.makeText(QiangXianActivity.this, "已经是最后一页了", Toast.LENGTH_SHORT).show();
                            } else {
                                pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                        } else {
                            Toast.makeText(QiangXianActivity.this, qiangXianJsonData1.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 3:
                    QiangXianBannerJsonData qiangXianBannerJsonData = (QiangXianBannerJsonData) msg.obj;
                    if (qiangXianBannerJsonData != null) {
                        if (qiangXianBannerJsonData.getRespCode().equals("SUCCESS")) {
                            picUrlList.clear();
                            titleList.clear();
                            for (int i = 0; i < qiangXianBannerJsonData.getData().getDocs().size(); i++) {
                                picUrlList.add(qiangXianBannerJsonData.getData().getDocs().get(i).getImg());
                                urlList.add(qiangXianBannerJsonData.getData().getDocs().get(i).getLink());
                                titleList.add(qiangXianBannerJsonData.getData().getDocs().get(i).getName());
                            }
                            initBanner();
                        }
                    }
                    break;
                case 150:
                    pullToRefreshListView.onRefreshComplete();
                    break;
                case 200:
                    pullToRefreshListView.onRefreshComplete();
                    break;
                case 300:
                    pullToRefreshListView.onRefreshComplete();
                    Toast.makeText(QiangXianActivity.this, "网络繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        qiangxian_head_cb.stopTurning();
        super.onDestroy();
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
