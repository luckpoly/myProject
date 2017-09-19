package com.brandsh.tiaoshi.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.HomeStoreListAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.fragment.FindFragment;
import com.brandsh.tiaoshi.fragment.HomeFragment;
import com.brandsh.tiaoshi.model.FindBannerJsonData;
import com.brandsh.tiaoshi.model.HomeBannerJsonData;
import com.brandsh.tiaoshi.model.HomeStoreModel1;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.LogUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SPUtil;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.brandsh.tiaoshi.widget.SelfPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mingle.widget.ShapeLoadingDialog;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */

public class StoreListCSActivity extends BaseActivity implements View.OnClickListener{
    @ViewInject(R.id.home_PTRListView)
    SelfPullToRefreshListView pullToRefreshListView;
    @ViewInject(R.id.iv_breaktop)
    ImageView iv_breaktop;
    @ViewInject(R.id.nav_back)
    ImageView nav_back;
    @ViewInject(R.id.nav_title)
    TextView nav_title;
    @ViewInject(R.id.guanzhu_list_rlNoItem)
    private RelativeLayout guanzhu_list_rlNoItem;
    private HomeStoreListAdapter homeStoreListAdapter;
    private List<HomeStoreModel1.DataBean.ListBean> resList;
    private HomeStoreModel1.DataBean dataEntity;
    private String page="1";
    private HashMap<String, String> map1;
    private ShapeLoadingDialog loadingDialog;
    private MyBroadcastReceiver myBroadcastReceiver;
    private ConvenientBanner convenient_banner;
    private List<String> urlList;
    private List<String> titleList;
    private List<String> picUrlList;
    private List<FindBannerJsonData.DataBean.DocsBean> picList;
    View find_headview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cs_list);
        find_headview = LayoutInflater.from(this).inflate(R.layout.find_headview, null);
        ViewUtils.inject(this);
        AppUtil.Setbar(this);
        initView();
        initListener();
        initData();
        setListenerToPTRListView();
    }

    private void initView() {
        nav_title.setText("社区超市");
        convenient_banner = (ConvenientBanner) find_headview.findViewById(R.id.find_convenient_banner);

        pullToRefreshListView.getRefreshableView().addHeaderView(find_headview);
        resList = new LinkedList<>();
        urlList = new LinkedList<>();
        titleList = new LinkedList<>();

        resList = new LinkedList<>();
        homeStoreListAdapter = new HomeStoreListAdapter(resList, this);
        pullToRefreshListView.setAdapter(homeStoreListAdapter);
        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("changeCount");
        intentFilter.addAction("clearCount");
        intentFilter.addAction("changeAddress");
        registerReceiver(myBroadcastReceiver, intentFilter);
        loadingDialog = ProgressHUD.show(this, "努力加载中...");
        loadingDialog.show();

    }
    private void initListener(){
        iv_breaktop.setOnClickListener(this);
        nav_back.setVisibility(View.VISIBLE);
        nav_back.setOnClickListener(this);
    }
    private void initData(){
        //请求轮播图接口
       HashMap map=new HashMap<>();
        map.put("code","APPCSuperMarket");
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign0= SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign0));
        OkHttpManager.postAsync(G.Host.GET_WORD, map, new MyCallBack(3, this, new FindBannerJsonData(), handler));

        //访问网络
        map1 = new HashMap<>();
        map1.put("actReq", SignUtil.getRandom());
        map1.put("actTime", System.currentTimeMillis() / 1000 + "");
        map1.put("lat", TiaoshiApplication.Lat);
        map1.put("lng", TiaoshiApplication.Lng);
        map1.put("supermarket", "YES");
        String sign = SignUtil.getSign(map1);
        map1.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.HOME_STORE_LIST + "?page=" + page, map1, new MyCallBack(1, this, new HomeStoreModel1(), handler));
    }
    private void initBanner() {
        convenient_banner.setPointViewVisible(true);
        convenient_banner.setPages(new CBViewHolderCreator<BannerHolderView>() {
            @Override
            public BannerHolderView createHolder() {
                return new BannerHolderView();
            }
        }, picUrlList).setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);
        convenient_banner.setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    loadingDialog.dismiss();
                    HomeStoreModel1 homeStoreModel = (HomeStoreModel1) msg.obj;
                    if (homeStoreModel != null ) {
                        if (homeStoreModel.getRespCode().equals("SUCCESS")){
                            dataEntity = homeStoreModel.getData();
                            resList.clear();
                            resList.addAll(dataEntity.getList());
                            if (resList.size() == 0) {
                                guanzhu_list_rlNoItem.setVisibility(View.VISIBLE);
                            } else {
                                guanzhu_list_rlNoItem.setVisibility(View.GONE);
                            }
                            for (int i=0; i<resList.size();i++){
                                if (null!=TiaoshiApplication.diyShoppingCartJsonData.getShop_id()&&resList.get(i).getShopId().equals(TiaoshiApplication.diyShoppingCartJsonData.getShop_id())){
                                    resList.get(i).setGoods_sc_count(TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_count());
                                }
                            }
                            homeStoreListAdapter.notifyDataSetChanged();
                            if (resList.size()>=Integer.parseInt(homeStoreModel.getData().getTotalCount())){
                                pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//                                ToastUtil.showShort(getActivity(),"已经是最后一页了");
                            }else {
                                pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                        }else {
                            shortToast(homeStoreModel.getRespMsg());
                        }
                    }
                    break;
                case 2:
                    loadingDialog.dismiss();
                    HomeStoreModel1 homeStoreModel2 = (HomeStoreModel1) msg.obj;
                    if (homeStoreModel2 != null) {
                        if (homeStoreModel2.getRespCode().equals("SUCCESS")) {
                            dataEntity = homeStoreModel2.getData();
                            resList.addAll(homeStoreModel2.getData().getList());
                            if (resList.size() >= Integer.parseInt(homeStoreModel2.getData().getTotalCount())) {
                                pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                shortToast("已经是最后一页了");
                            } else {
                                pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                            for (int i = 0; i < resList.size(); i++) {
                                if (resList.get(i).getShopId().equals(TiaoshiApplication.diyShoppingCartJsonData.getShop_id())) {
                                    resList.get(i).setGoods_sc_count(TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_count());
                                }
                            }
                            homeStoreListAdapter.notifyDataSetChanged();
                        } else {
                            shortToast(homeStoreModel2.getRespMsg());
                        }
                    }
                    break;
                case 3:
                    FindBannerJsonData findBannerJsonData = (FindBannerJsonData) msg.obj;
                    if ("SUCCESS".equals(findBannerJsonData.getRespCode())){
                        picList = findBannerJsonData.getData().getDocs();
                        picUrlList = new LinkedList<>();
                        urlList.clear();
                        titleList.clear();
                        for (int i = 0; i < picList.size(); i++) {
                            picUrlList.add(picList.get(i).getImg());
                            urlList.add(picList.get(i).getLink());
                            titleList.add(picList.get(i).getName());
                        }
                        initBanner();
                        /**
                         * 动画
                         */
                        Animation mAnimation = null ;
                        /**
                         * 显示动画的ImageView
                         */
                        mAnimation = AnimationUtils.loadAnimation(StoreListCSActivity.this,R.anim. viewbig);
                        convenient_banner.setAnimation(mAnimation );
                        mAnimation.start();
                    }else {
                        shortToast(findBannerJsonData.getRespMsg());
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
                    break;
            }
        }
    };
    //给列表设置监听
    private void setListenerToPTRListView() {
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position - 2>resList.size()){
                    return;
                }
                String storeId = resList.get(position - 2).getShopId();
                Intent intent = new Intent(StoreListCSActivity.this, StoreDetailCSActivity.class);
                intent.putExtra("shop_id", storeId);
                intent.putExtra("shop_name", resList.get(position - 2).getName());
                intent.putExtra("min_cost", resList.get(position - 2).getFreeSend());
                startActivity(intent);
            }
        });
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = "1";
                initData();
                handler.sendEmptyMessageDelayed(150, 1500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (dataEntity != null) {
                    page = dataEntity.getNextPage() + "";
                    OkHttpManager.postAsync(G.Host.HOME_STORE_LIST + "?page=" + page, map1, new MyCallBack(2, StoreListCSActivity.this, new HomeStoreModel1(), handler));
                }
                handler.sendEmptyMessageDelayed(150, 1500);
            }
        });
        pullToRefreshListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState){
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到顶部
                        if (pullToRefreshListView.getRefreshableView().getFirstVisiblePosition() == 0) {
                            iv_breaktop.setVisibility(View.GONE);
                            iv_breaktop.setAnimation(AnimationUtils.loadAnimation(StoreListCSActivity.this,R.anim.go_dismiss));
//                            tv_line.setVisibility(View.GONE);
                        }else {
                            iv_breaktop.setVisibility(View.VISIBLE);
                            iv_breaktop.setAnimation(AnimationUtils.loadAnimation(StoreListCSActivity.this, R.anim.go_show));
//                            tv_line.setVisibility(View.VISIBLE);
                        }
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }
    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("changeCount".equals(intent.getAction())) {
                if (TiaoshiApplication.diyShoppingCartJsonData.getShop_id() == null) {
                    for (int i = 0; i < resList.size(); i++) {
                        resList.get(i).setGoods_sc_count(0);
                    }
                    homeStoreListAdapter.notifyDataSetChanged();
                    return;
                } else {
                    for (int i = 0; i < resList.size(); i++) {
                        if (resList.get(i).getShopId().equals(TiaoshiApplication.diyShoppingCartJsonData.getShop_id())) {
                            resList.get(i).setGoods_sc_count(TiaoshiApplication.diyShoppingCartJsonData.getGoods_total_count());
                            homeStoreListAdapter.notifyDataSetChanged();
                            return;
                        }
                    }
                }
            } else if ("clearCount".equals(intent.getAction())) {
                for (int i = 0; i < resList.size(); i++) {
                    resList.get(i).setGoods_sc_count(0);
                }
                homeStoreListAdapter.notifyDataSetChanged();
            }else if ("changeAddress".equals(intent.getAction())){
                initData();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_breaktop:
                pullToRefreshListView.getRefreshableView().smoothScrollToPosition(0);
                break;
            case R.id.nav_back:
                finish();
                break;
        }
    }
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
            //绑定事件
            imageView.setOnClickListener(new MyOnClickListener(position));
            //加载图片
            TiaoshiApplication.getGlobalBitmapUtils().display(imageView, data);
        }
    }
    class MyOnClickListener implements View.OnClickListener {
        private int position;

        public MyOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (urlList.size()>0){
                Intent intent = new Intent(StoreListCSActivity.this, BannerDetailActivity.class);
                intent.putExtra("url", urlList.get(position));
                intent.putExtra("title", titleList.get(position));
                if (!TextUtils.isEmpty(urlList.get(position))&&!urlList.get(position).equals("#")){
                    startActivity(intent);
                }
            }
        }
    }
    @Override
    protected void onDestroy() {
        unregisterReceiver(myBroadcastReceiver);
        super.onDestroy();
    }
    @Override
    public void onResume() {
        convenient_banner.startTurning(4000);
        super.onResume();
    }
    @Override
    public void onPause() {
        convenient_banner.stopTurning();
        super.onPause();
    }
}
