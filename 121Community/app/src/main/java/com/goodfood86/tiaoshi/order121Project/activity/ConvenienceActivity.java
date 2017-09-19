package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.CreateActivityModel;
import com.goodfood86.tiaoshi.order121Project.model.GetWordModel;
import com.goodfood86.tiaoshi.order121Project.model.PubDocModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.goodfood86.tiaoshi.order121Project.widget.ObservableScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/8/1.
 * 1.5.0之前便民页面
 */
public class ConvenienceActivity extends Activity implements View.OnClickListener ,ObservableScrollView.ScrollViewListener{
    private List<String> picUrlList = new ArrayList<>();
    private List<PubDocModel.DataBean.NodesBean> wordsEntityList;
    @ViewInject(R.id.convenient_banner)
    private ConvenientBanner convenient_banner;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.sv_home)
    private ObservableScrollView sv_home;
    @ViewInject(R.id.rl_title)
    private RelativeLayout rl_title;
    @ViewInject(R.id.ll_xiyi)
    private LinearLayout ll_xiyi;
    @ViewInject(R.id.ll_jiazheng)
    private LinearLayout ll_jiazheng;
    @ViewInject(R.id.ll_kuaixiu)
    private LinearLayout ll_kuaixiu;
    private int imageHeight;
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

                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convenience);
        initView();
        initData();
    }
    private void initView() {
        ViewUtils.inject(this);
        nav_back.setOnClickListener(this);
        ll_xiyi.setOnClickListener(this);
        ll_jiazheng.setOnClickListener(this);
        ll_kuaixiu.setOnClickListener(this);
        convenient_banner.startTurning(4000);
        convenient_banner. setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        //获取banner高度

        ViewTreeObserver vto = convenient_banner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                convenient_banner.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                imageHeight = convenient_banner.getHeight();
                Log.e("00000",imageHeight+"");
                sv_home.setScrollViewListener(ConvenienceActivity.this);
            }
        });
    }

    private void initData() {
        //banner图
        HashMap map = new HashMap();
        map.put("code", "BannerConvenience");
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", MD5.getMD5(sign));
        OkHttpManager.postAsync(G.Host.PUB_DOC, map, new MyCallBack(1,this, new PubDocModel(), handler));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_back:
                finish();
                break;
            case R.id.ll_jiazheng:
                startActivity(new Intent(ConvenienceActivity.this,JiazhengActivity.class));
                break;
            case R.id.ll_xiyi:
                Intent intent=new Intent(ConvenienceActivity.this,KuaixiuActivity.class);
                intent.putExtra("type","xiyi");
                startActivity(intent);
                break;
            case R.id.ll_kuaixiu:
                Intent intent1=new Intent(ConvenienceActivity.this,KuaixiuActivity.class);
                intent1.putExtra("type","kuaixiu");
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            rl_title.setBackgroundColor(Color.argb((int) 0, 55, 176, 255));//AGB由相关工具获得，或者美工提供
        } else if (y > 0 && y <= imageHeight) {
            float scale = (float) y / imageHeight;
            float alpha = (255 * scale);
            rl_title.setBackgroundColor(Color.argb((int) alpha, 55, 176, 255));
        } else {
            rl_title.setBackgroundColor(Color.argb((int) 255, 55, 176, 255));
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
                Intent intent = new Intent(ConvenienceActivity.this, WebViewShowActivity.class);
                intent.putExtra("url", wordsEntityList.get(mPosition).getLink());
                if (!TextUtils.isEmpty(wordsEntityList.get(mPosition).getLink()) && wordsEntityList.get(mPosition).getLink().length() > 1) {
                    startActivity(intent);
                }
            }
        }
    }
}
