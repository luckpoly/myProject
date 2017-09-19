package com.brandsh.tiaoshi.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.CategoryItemDetailListAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.fragment.ProductDetailFragment;
import com.brandsh.tiaoshi.model.CategoryDetailListJsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.StatusBarUtil;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.brandsh.tiaoshi.widget.SelfPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mingle.widget.ShapeLoadingDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;

public class CategoryItemDetailListActivity extends FragmentActivity implements View.OnClickListener {
    @ViewInject(R.id.category_detail_ivBack)
    ImageView category_detail_ivBack;
    @ViewInject(R.id.category_detail_tvTitle)
    TextView category_detail_tvTitle;
    @ViewInject(R.id.category_detail_ptrListView)
    SelfPullToRefreshListView category_detail_ptrListView;
    @ViewInject(R.id.category_rg)
    private RadioGroup category_rg;

    @ViewInject(R.id.guanzhu_list_rlNoItem)
    private RelativeLayout guanzhu_list_rlNoItem;

    private Intent intent;
    private String cate_id;
    private HashMap requestMap;
    private String page;
    private AlertDialog.Builder locationBuilder;
    private ShapeLoadingDialog loadingDialog;
    private Toast toast;
    private List<CategoryDetailListJsonData.DataBean.ListBean> resList;
    private CategoryItemDetailListAdapter categoryItemDetailListAdapter;
    private String typeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item_detail_list);
        //沉浸状态栏
        AppUtil.Setbar(this);
        //设置状态栏颜色为黑
        StatusBarUtil.StatusBarLightMode(this);
        ViewUtils.inject(this);
        init();
        setListenerToptrListView();
        initData();
        initviewLayout();
    }

    public void initviewLayout() {
        category_rg.check(R.id.categorydata_rb1);
        category_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                page = "1";
                switch (checkedId) {
                    case R.id.categorydata_rb1:
                        requestMap.clear();
                        requestMap.put("categoryId", cate_id);
                        requestMap.put("typeId", typeId);
                        requestMap.put("lat", TiaoshiApplication.Lat);
                        requestMap.put("lng", TiaoshiApplication.Lng);
                        requestMap.put("isHot", "1");
                        requestMap.put("actReq", SignUtil.getRandom());
                        requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
                        String sign = SignUtil.getSign(requestMap);
                        requestMap.put("sign", Md5.toMd5(sign));
                        OkHttpManager.postAsync(G.Host.FIND + "?page=" + page, requestMap, new MyCallBack(1, CategoryItemDetailListActivity.this, new CategoryDetailListJsonData(), handler));
                        break;
                    case R.id.categorydata_rb2:
                        requestMap.clear();
                        requestMap.put("categoryId", cate_id);
                        requestMap.put("typeId", typeId);
                        requestMap.put("lat", TiaoshiApplication.Lat);
                        requestMap.put("lng", TiaoshiApplication.Lng);
                        requestMap.put("isNewProduct", "1");
                        requestMap.put("actReq", SignUtil.getRandom());
                        requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
                        String sign0 = SignUtil.getSign(requestMap);
                        requestMap.put("sign", Md5.toMd5(sign0));
                        OkHttpManager.postAsync(G.Host.FIND + "?page=" + page, requestMap, new MyCallBack(1, CategoryItemDetailListActivity.this, new CategoryDetailListJsonData(), handler));
                        break;
                    case R.id.categorydata_rb3:
                        requestMap.clear();
                        requestMap.put("categoryId", cate_id);
                        requestMap.put("typeId", typeId);
                        requestMap.put("lat", TiaoshiApplication.Lat);
                        requestMap.put("lng", TiaoshiApplication.Lng);
                        requestMap.put("isPromote", "1");
                        requestMap.put("actReq", SignUtil.getRandom());
                        requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
                        String sign1 = SignUtil.getSign(requestMap);
                        requestMap.put("sign", Md5.toMd5(sign1));
                        OkHttpManager.postAsync(G.Host.FIND + "?page=" + page, requestMap, new MyCallBack(1, CategoryItemDetailListActivity.this, new CategoryDetailListJsonData(), handler));
                        break;
                }
            }
        });
    }

    private void init() {
        intent = getIntent();
        cate_id = getIntent().getStringExtra("cate_id");
        typeId = getIntent().getStringExtra("TypeId");
        requestMap = new HashMap();

        loadingDialog = ProgressHUD.show(this, "努力加载中...");
        loadingDialog.show();

        page = "1";
        toast = Toast.makeText(this, "定位失败, 请稍后再试", Toast.LENGTH_SHORT);
        locationBuilder = new AlertDialog.Builder(this).setTitle("系统提示");
        category_detail_tvTitle.setText(intent.getStringExtra("firstCategory") + "-" + intent.getStringExtra("cate_name"));

        category_detail_ivBack.setOnClickListener(this);
    }

    private void setListenerToptrListView() {
        category_detail_ptrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String goods_id = resList.get(position - 1).getGoodsId();
                Intent intent = FCActivity.getFCActivityIntent(CategoryItemDetailListActivity.this, ProductDetailFragment.class);
                intent.putExtra("goods_id", goods_id);
                intent.putExtra("goods_name", resList.get(position - 1).getGoodsName());
                intent.putExtra("shop_id", resList.get(position - 1).getShopId());
                intent.putExtra("shop_name", resList.get(position - 1).getShopName());
                intent.putExtra("min_cost", resList.get(position - 1).getFreeSend());
                intent.putExtra("is_new", "");
                startActivity(intent);
            }
        });

        category_detail_ptrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = "1";
                initData();
                handler.sendEmptyMessageDelayed(150, 1500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                OkHttpManager.postAsync(G.Host.FIND + "?page=" + page, requestMap, new MyCallBack(2, CategoryItemDetailListActivity.this, new CategoryDetailListJsonData(), handler));
                handler.sendEmptyMessageDelayed(150, 1500);
            }
        });
    }

    private void initData() {
        requestMap.clear();
        requestMap.put("categoryId", cate_id);
        requestMap.put("typeId", typeId);
        Log.e("___-------", cate_id);
        requestMap.put("lat", TiaoshiApplication.Lat);
        requestMap.put("lng", TiaoshiApplication.Lng);
        requestMap.put("actReq", SignUtil.getRandom());
        requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(requestMap);
        requestMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.FIND + "?page=" + page, requestMap, new MyCallBack(1, CategoryItemDetailListActivity.this, new CategoryDetailListJsonData(), handler));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.category_detail_ivBack:
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

                    category_detail_ptrListView.onRefreshComplete();
                    CategoryDetailListJsonData categoryDetailListJsonData = (CategoryDetailListJsonData) msg.obj;
                    if (categoryDetailListJsonData != null) {
                        if (categoryDetailListJsonData.getRespCode().equals("SUCCESS")) {
                            page = categoryDetailListJsonData.getData().getNextPage() + "";
                            resList = categoryDetailListJsonData.getData().getList();
                            categoryItemDetailListAdapter = new CategoryItemDetailListAdapter(resList, CategoryItemDetailListActivity.this);
                            category_detail_ptrListView.setAdapter(categoryItemDetailListAdapter);
                            categoryItemDetailListAdapter.notifyDataSetChanged();
                            if (resList.size() == 0) {
                                guanzhu_list_rlNoItem.setVisibility(View.VISIBLE);
                            } else {
                                guanzhu_list_rlNoItem.setVisibility(View.GONE);
                            }
                            if (resList.size() >= Integer.parseInt(categoryDetailListJsonData.getData().getTotalCount())) {
                                category_detail_ptrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//                                Toast.makeText(CategoryItemDetailListActivity.this, "已经是最后一页了", Toast.LENGTH_SHORT).show();
                            } else {
                                category_detail_ptrListView.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                        } else {
                            Toast.makeText(CategoryItemDetailListActivity.this, categoryDetailListJsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 2:
                    category_detail_ptrListView.onRefreshComplete();
                    CategoryDetailListJsonData categoryDetailListJsonData2 = (CategoryDetailListJsonData) msg.obj;
                    if (categoryDetailListJsonData2 != null) {
                        if (categoryDetailListJsonData2.getRespCode().equals("SUCCESS")) {
                            page = categoryDetailListJsonData2.getData().getNextPage() + "";
                            resList.addAll(categoryDetailListJsonData2.getData().getList());
                            categoryItemDetailListAdapter.notifyDataSetChanged();
                            if (resList.size() >= Integer.parseInt(categoryDetailListJsonData2.getData().getTotalCount())) {
                                category_detail_ptrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

                            } else {
                                category_detail_ptrListView.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                        } else {
                            Toast.makeText(CategoryItemDetailListActivity.this, categoryDetailListJsonData2.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 150:
                    category_detail_ptrListView.onRefreshComplete();
                    break;
                case 200:
                    category_detail_ptrListView.onRefreshComplete();
                    break;
                case 300:
                    category_detail_ptrListView.onRefreshComplete();
                    break;
            }
        }
    };

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
