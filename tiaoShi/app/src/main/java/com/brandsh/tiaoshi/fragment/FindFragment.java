package com.brandsh.tiaoshi.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.brandsh.tiaoshi.activity.BannerDetailActivity;
import com.brandsh.tiaoshi.activity.FCActivity;
import com.brandsh.tiaoshi.adapter.FindProductListAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.FindBannerJsonData;
import com.brandsh.tiaoshi.model.FindJsonData1;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.brandsh.tiaoshi.widget.SelfPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lidroid.xutils.ViewUtils;
import com.mingle.widget.ShapeLoadingDialog;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by libokang on 15/9/2.
 */
public class FindFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private ConvenientBanner convenient_banner;
    private SelfPullToRefreshListView pullToRefreshListView;
    private View find_headview;
    private ShapeLoadingDialog loadingDialog;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationListener aMapLocationListener;
    private AMapLocationClientOption aMapLocationClientOption;
    private double latitude;
    private double longitude;
    private String page;
    private Toast toast;
    private HashMap<String,String> dataRequestMap;
    private List<FindJsonData1.DataBean.ListBean> resList;
    private List<FindBannerJsonData.DataBean.DocsBean> picList;
    private List<String> picUrlList;
    private FindProductListAdapter findProductListAdapter;
    private AlertDialog.Builder locationBuilder;
    private List<String> urlList;
    private List<String> titleList;
    private  HashMap map;
    private View home_footerview;
    private ImageView iv_breaktop;
    private TextView tv_line;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.common_list_fragment, null);
            ViewUtils.inject(this, rootView);

            find_headview = inflater.inflate(R.layout.find_headview, null);

            initView();

            initData();

        } else {
            if (rootView.getParent() != null) {
                ((ViewGroup) rootView.getParent()).removeView(rootView);
            }
        }

        return rootView;
    }

    private void initView() {
        convenient_banner = (ConvenientBanner) find_headview.findViewById(R.id.find_convenient_banner);
        /**
         * 动画
         */
        Animation mAnimation = null ;
        /**
         * 显示动画的ImageView
         */
        mAnimation = AnimationUtils.loadAnimation(getContext(),R.anim. viewbig);
        convenient_banner.setAnimation(mAnimation );
        mAnimation.start();
        pullToRefreshListView = (SelfPullToRefreshListView) rootView.findViewById(R.id.find_PTRListView);
        toast = Toast.makeText(getActivity(), "定位失败, 请稍后再试", Toast.LENGTH_SHORT);
        loadingDialog = ProgressHUD.show(getActivity(), "努力加载中...");
        loadingDialog.show();
        page = "1";

        pullToRefreshListView.getRefreshableView().addHeaderView(find_headview);
        iv_breaktop=(ImageView)rootView.findViewById(R.id.iv_breaktop);
        tv_line=(TextView)rootView.findViewById(R.id.tv_line);
        setListenerToPTRListView();

        dataRequestMap=new HashMap<>();

        resList = new LinkedList<>();
        urlList = new LinkedList<>();
        titleList = new LinkedList<>();

        findProductListAdapter = new FindProductListAdapter(resList, getActivity());
        pullToRefreshListView.setAdapter(findProductListAdapter);

        //请求轮播图接口
        map=new HashMap<>();
        map.put("code","APPUserFound");
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign= SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.GET_WORD, map, new MyCallBack(3, getActivity(), new FindBannerJsonData(), handler));
        locationBuilder = new AlertDialog.Builder(getActivity()).setTitle("系统提示");
        iv_breaktop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pullToRefreshListView.getRefreshableView().smoothScrollToPosition(0);
            }
        });
    }
    private void setListenerToPTRListView() {
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position - 2>resList.size()){
                    return;
                }
                Intent intent = FCActivity.getFCActivityIntent(getActivity(), ProductDetailFragment.class);
                intent.putExtra("goods_id", resList.get(position - 2).getGoodsId());
                intent.putExtra("goods_name", resList.get(position-2).getGoodsName());
                intent.putExtra("shop_id", resList.get(position-2).getShopId());
                intent.putExtra("shop_name", resList.get(position-2).getShopName());
                intent.putExtra("min_cost", resList.get(position-2).getFreeSend());
                intent.putExtra("is_new", "");
                startActivity(intent);
            }
        });
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = "1";
                initData();
                OkHttpManager.postAsync(G.Host.GET_WORD, map, new MyCallBack(3, getActivity(), new FindBannerJsonData(), handler));
                handler.sendEmptyMessageDelayed(150, 1500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                OkHttpManager.postAsync(G.Host.FIND + "?page=" + page, dataRequestMap, new MyCallBack(2, getActivity(), new FindJsonData1(), handler));
                handler.sendEmptyMessageDelayed(150, 1500);
            }
        });
        pullToRefreshListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到顶部
                        if (pullToRefreshListView.getRefreshableView().getFirstVisiblePosition() == 0) {
                            iv_breaktop.setVisibility(View.GONE);
                            iv_breaktop.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.go_dismiss));
                        } else {
                            iv_breaktop.setVisibility(View.VISIBLE);
                            iv_breaktop.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.go_show));
                        }
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }
    private void initData(){
        dataRequestMap.clear();
        dataRequestMap.put("lat", TiaoshiApplication.Lat);
        dataRequestMap.put("lng", TiaoshiApplication.Lng);
        dataRequestMap.put("actReq",SignUtil.getRandom());
        dataRequestMap.put("actTime",System.currentTimeMillis()/1000+"");
        String sign= SignUtil.getSign(dataRequestMap);
        dataRequestMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.FIND + "?page=" + page,dataRequestMap,new MyCallBack(1, getActivity(), new FindJsonData1(), handler));
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
    @Override
    public void onClick(View v) {

    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    FindJsonData1 findJsonData = (FindJsonData1) msg.obj;
                    if (findJsonData != null) {
                        if (findJsonData.getRespCode().equals("SUCCESS")){
                            pullToRefreshListView.getRefreshableView().removeFooterView(home_footerview);
                            page = findJsonData.getData().getNextPage() + "";
                            resList.clear();
                            resList.addAll(findJsonData.getData().getList());
                            if (resList.size()>=Integer.parseInt(findJsonData.getData().getTotalCount())){
                                pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                showToast("已经是最后一页了");
                            }else {
                                pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                            findProductListAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(getActivity(), findJsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                        loadingDialog.dismiss();
                    break;
                case 2:
                    FindJsonData1 findJsonData1 = (FindJsonData1) msg.obj;
                    if (findJsonData1 != null) {
                        if (findJsonData1.getRespCode().equals("SUCCESS")) {
                            page = findJsonData1.getData().getNextPage() + "";
                            List<FindJsonData1.DataBean.ListBean> List=findJsonData1.getData().getList();
                            resList.addAll(findJsonData1.getData().getList());
                            findProductListAdapter.notifyDataSetChanged();
                            if (resList.size()>=Integer.parseInt(findJsonData1.getData().getTotalCount())){
                                pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                showToast("已经是最后一页了");
                            }else {
                                pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                        } else {
                            showToast(findJsonData1.getRespMsg());
                        }
                    }
                    break;
                case 3:
                    FindBannerJsonData findBannerJsonData = (FindBannerJsonData) msg.obj;
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
                Intent intent = new Intent(getActivity(), BannerDetailActivity.class);
                intent.putExtra("url", urlList.get(position));
                intent.putExtra("title", titleList.get(position));
                if (!TextUtils.isEmpty(urlList.get(position))&&!urlList.get(position).equals("#")){
                    startActivity(intent);
                }
            }
        }
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
