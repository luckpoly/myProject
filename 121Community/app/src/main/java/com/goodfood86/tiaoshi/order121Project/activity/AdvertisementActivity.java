package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.AiJieLiAdapter;
import com.goodfood86.tiaoshi.order121Project.adapter.CustomAdapter;
import com.goodfood86.tiaoshi.order121Project.adapter.ZixunGridviewAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.GetWordModel;
import com.goodfood86.tiaoshi.order121Project.model.PubDocModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/8/1.
 */
public class AdvertisementActivity extends Activity implements View.OnClickListener {
    private List<String> picUrlList = new ArrayList<>();
    private List<PubDocModel.DataBean.NodesBean> wordsEntityList;
    @ViewInject(R.id.convenient_banner)
    private ConvenientBanner convenient_banner;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.iv_weixiu)
    private ImageView iv_weixiu;
    @ViewInject(R.id.iv_guoyuan)
    private ImageView iv_guoyuan;
    @ViewInject(R.id.iv_gongyizixun)
    private ImageView iv_gongyizixun;
    @ViewInject(R.id.ll_jieli)
    private LinearLayout ll_jieli;
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.gv_group_partners)
    GridView gv_group_partners;
    @ViewInject(R.id.my_recycler_huodong)
    private RecyclerView my_recycler_huodong;
    private List<PubDocModel.DataBean.NodesBean> listZixunBean;
    private ZixunGridviewAdapter zixunGridviewAdapter;
    private List<PubDocModel.DataBean.NodesBean> jieliData;
    private AiJieLiAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        initView();
        initData();
    }

    private void initView() {
        ViewUtils.inject(this);
        nav_back.setOnClickListener(this);
        ll_jieli.setOnClickListener(this);
        iv_weixiu.setOnClickListener(this);
        iv_guoyuan.setOnClickListener(this);
        iv_gongyizixun.setOnClickListener(this);
        nav_title.setText("公益");
        convenient_banner.startTurning(4000);
        listZixunBean = new ArrayList();
        zixunGridviewAdapter = new ZixunGridviewAdapter(this, listZixunBean);
        gv_group_partners.setAdapter(zixunGridviewAdapter);
        gv_group_partners.setFocusable(false);
        gv_group_partners.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(AdvertisementActivity.this,ZixunDetailActivity.class)
                .putExtra("id",listZixunBean.get(position).getId())
                .putExtra("name",listZixunBean.get(position).getName()));
            }
        });
        jieliData = new ArrayList<>();
        customAdapter = new AiJieLiAdapter(AdvertisementActivity.this, jieliData);
        //设置水平布局
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        my_recycler_huodong.setLayoutManager(mLayoutManager);
        my_recycler_huodong.setAdapter(customAdapter);
    }

    private void initData() {
        HashMap map = new HashMap();
        map.put("code", "BannerAd");
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", MD5.getMD5(sign));
        OkHttpManager.postAsync(G.Host.PUB_DOC, map, new MyCallBack(1, this, new PubDocModel(), handler));
        HashMap map1 = new HashMap();
        map1.put("code", "WelfareAxjm");
        map1.put("actReq", SignUtil.getRandom());
        map1.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign1 = SignUtil.getSign(map1);
        map1.put("sign", MD5.getMD5(sign1));
        OkHttpManager.postAsync(G.Host.PUB_DOC, map1, new MyCallBack(2, this, new PubDocModel(), handler));
        HashMap map2 = new HashMap();
        map2.put("code", "WelfareAjl");
        map2.put("actReq", SignUtil.getRandom());
        map2.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign2 = SignUtil.getSign(map2);
        map2.put("sign", MD5.getMD5(sign2));
        OkHttpManager.postAsync(G.Host.PUB_DOC, map2, new MyCallBack(3, this, new PubDocModel(), handler));
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        PubDocModel pubDocModel = (PubDocModel) msg.obj;
                        if ("SUCCESS".equals(pubDocModel.getRespCode()) && null != pubDocModel.getData()) {
                            if (picUrlList.size() == 0) {
                                wordsEntityList = pubDocModel.getData().getNodes();
                                for (int i = 0; i < wordsEntityList.size(); i++) {
                                    picUrlList.add(wordsEntityList.get(i).getImg());
                                }
                                initBanner();
                            }
                        }
                        break;
                    case 2:
                        PubDocModel pubDocModel1 = (PubDocModel) msg.obj;
                        if ("SUCCESS".equals(pubDocModel1.getRespCode()) && pubDocModel1.getData().getNodes() != null) {
                            listZixunBean.addAll(pubDocModel1.getData().getNodes());
                            zixunGridviewAdapter.notifyDataSetChanged();
                        }
                        break;
                    case 3:
                        PubDocModel pubDocModel2 = (PubDocModel) msg.obj;
                        if ("SUCCESS".equals(pubDocModel2.getRespCode()) && pubDocModel2.getData().getNodes() != null) {
                            jieliData.addAll(pubDocModel2.getData().getNodes());
                            customAdapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
        }
    };

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_back:
                finish();
                break;
            case R.id.iv_weixiu:
                startActivity(new Intent(AdvertisementActivity.this, ZixunDetailActivity.class)
                        .putExtra("type", "detail")
                        .putExtra("name", "维修基金")
                        .putExtra("code", "WelfareSqwxjj"));
                break;
            case R.id.iv_guoyuan:
                startActivity(new Intent(AdvertisementActivity.this, ZixunDetailActivity.class)
                        .putExtra("type", "detail")
                        .putExtra("name", "社区果园")
                        .putExtra("code", "WelfareGyjh"));
                break;
            case R.id.iv_gongyizixun:
                startActivity(new Intent(AdvertisementActivity.this, ZixunDetailActivity.class)
                        .putExtra("type", "list")
                        .putExtra("name", "公益资讯")
                        .putExtra("code", "WelfareGyzx"));
                break;
            case R.id.ll_jieli:
                startActivity(new Intent(AdvertisementActivity.this, ZixunDetailActivity.class)
                        .putExtra("type", "list")
                        .putExtra("name", "“爱”接力")
                        .putExtra("code", "WelfareAjl"));
                break;
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

        public MyOnClickListener(int position) {
            this.mPosition = position;
        }

        @Override
        public void onClick(View v) {
            if (wordsEntityList.get(mPosition).getHref() != null && !wordsEntityList.get(mPosition).getHref().equals("#")) {
                Intent intent = new Intent(AdvertisementActivity.this, WebViewShowActivity.class);
                intent.putExtra("url", wordsEntityList.get(mPosition).getLink());
                if (!TextUtils.isEmpty(wordsEntityList.get(mPosition).getLink()) && wordsEntityList.get(mPosition).getLink().length() > 1) {
                    startActivity(intent);
                }
            }
        }
    }
}
