package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.goodfood86.tiaoshi.order121Project.adapter.BaseGridviewAdapter;
import com.goodfood86.tiaoshi.order121Project.adapter.JibingGridviewAdapter;
import com.goodfood86.tiaoshi.order121Project.adapter.YaodianGridviewAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.GridViewModel;
import com.goodfood86.tiaoshi.order121Project.model.PubDocModel;
import com.goodfood86.tiaoshi.order121Project.model.YaodianListModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
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
public class MedicalActivity extends Activity implements View.OnClickListener {
    private List<String> picUrlList = new ArrayList<>();
    private List<PubDocModel.DataBean.NodesBean> wordsEntityList;
    @ViewInject(R.id.convenient_banner)
    private ConvenientBanner convenient_banner;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.gv_group)
    private GridView gv_group;
    @ViewInject(R.id.gv_group_partners)
    private GridView gv_group_partners;
    @ViewInject(R.id.ll_goselect_yaodian)
    private LinearLayout ll_goselect_yaodian;
    @ViewInject(R.id.iv_cishan)
    private ImageView iv_cishan;
    @ViewInject(R.id.iv_baobaoyimiao)
    private ImageView iv_baobaoyimiao;
    @ViewInject(R.id.iv_jiankang)
    private ImageView iv_jiankang;
    @ViewInject(R.id.iv_kuaisu)
    private ImageView iv_kuaisu;
    @ViewInject(R.id.iv_yuer)
    private ImageView iv_yuer;
    private List<GridViewModel> listGridData;
    private List<PubDocModel.DataBean.NodesBean> jibingBean;
    private JibingGridviewAdapter jibingGridviewAdapter;
    private YaodianGridviewAdapter yaodianGridviewAdapter;
    private List<YaodianListModel.DataBean.ListBean> yaodianBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        ViewUtils.inject(this);
        nav_title.setText("医疗");
        nav_back.setOnClickListener(this);
        ll_goselect_yaodian.setOnClickListener(this);
        convenient_banner.startTurning(4000);
        jibingBean = new ArrayList<>();
        jibingGridviewAdapter = new JibingGridviewAdapter(MedicalActivity.this, jibingBean);
        gv_group.setAdapter(jibingGridviewAdapter);
        yaodianBean = new ArrayList<>();
        yaodianGridviewAdapter = new YaodianGridviewAdapter(MedicalActivity.this, yaodianBean);
        gv_group_partners.setAdapter(yaodianGridviewAdapter);
        gv_group_partners.setFocusable(false);
    }

    private void initListener() {
        iv_cishan.setOnClickListener(this);
        iv_baobaoyimiao.setOnClickListener(this);
        iv_jiankang.setOnClickListener(this);
        iv_kuaisu.setOnClickListener(this);
        iv_yuer.setOnClickListener(this);
        gv_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MedicalActivity.this, ZixunDetailActivity.class)
                        .putExtra("id", jibingBean.get(position).getId())
                        .putExtra("name", jibingBean.get(position).getName()));
            }
        });
        gv_group_partners.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MedicalActivity.this, YaodianDetailActivity.class)
                        .putExtra("id", yaodianBean.get(position).getId()));
            }
        });
    }

    private void initData() {
        //banner图
        HashMap map = new HashMap();
        map.put("code", "BannerMedical");
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", MD5.getMD5(sign));
        OkHttpManager.postAsync(G.Host.PUB_DOC, map, new MyCallBack(1, this, new PubDocModel(), handler));
        //疾病知识
        HashMap map1 = new HashMap();
        map1.put("code", "MedicalJbzs");
        map1.put("actReq", SignUtil.getRandom());
        map1.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign1 = SignUtil.getSign(map1);
        map1.put("sign", MD5.getMD5(sign1));
        OkHttpManager.postAsync(G.Host.PUB_DOC, map1, new MyCallBack(2, this, new PubDocModel(), handler)); //疾病知识
        HashMap map2 = new HashMap();
        map2.put("lng", Order121Application.Lng);
        map2.put("lat", Order121Application.Lat);
        map2.put("limit", "8");
        map2.put("actReq", SignUtil.getRandom());
        map2.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign2 = SignUtil.getSign(map2);
        map2.put("sign", MD5.getMD5(sign2));
        OkHttpManager.postAsync(G.Host.PHARMACY, map2, new MyCallBack(3, this, new YaodianListModel(), handler));
    }

    Handler handler = new Handler() {
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
                            jibingBean.clear();
                            jibingBean.addAll(pubDocModel1.getData().getNodes());
                            jibingGridviewAdapter.notifyDataSetChanged();
                        }
                        break;
                    case 3:
                        YaodianListModel yaodianListModel = (YaodianListModel) msg.obj;
                        if ("SUCCESS".equals(yaodianListModel.getRespCode()) && yaodianListModel.getData().getList() != null) {
                            yaodianBean.clear();
                            yaodianBean.addAll(yaodianListModel.getData().getList());
                            yaodianGridviewAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showShort(MedicalActivity.this, yaodianListModel.getRespMsg());
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
            case R.id.ll_goselect_yaodian:
                startActivity(new Intent(MedicalActivity.this, YaodianListActivity.class));
                break;
            case R.id.iv_cishan:
                startActivity(new Intent(MedicalActivity.this, ZixunDetailActivity.class)
                        .putExtra("type", "list")
                        .putExtra("name", "慈善捐助")
                        .putExtra("code", "MedicalCsjz"));
                break;
            case R.id.iv_baobaoyimiao:
                startActivity(new Intent(MedicalActivity.this, ZixunDetailActivity.class)
                        .putExtra("type", "list")
                        .putExtra("name", "宝宝疫苗通知")
                        .putExtra("code", "MedicalBbymtz"));
                break;
            case R.id.iv_jiankang:
                startActivity(new Intent(MedicalActivity.this, ZixunDetailActivity.class)
                        .putExtra("type", "list")
                        .putExtra("name", "健康资讯")
                        .putExtra("code", "MedicalJkzx"));
                break;
            case R.id.iv_kuaisu:
                startActivity(new Intent(MedicalActivity.this,KuaisuZixunActivity.class));
                break;
            case R.id.iv_yuer:
                startActivity(new Intent(MedicalActivity.this, ZixunDetailActivity.class)
                        .putExtra("type", "list")
                        .putExtra("name", "育儿资讯")
                        .putExtra("code", "MedicalYezx"));
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
                Intent intent = new Intent(MedicalActivity.this, WebViewShowActivity.class);
                intent.putExtra("url", wordsEntityList.get(mPosition).getLink());
                if (!TextUtils.isEmpty(wordsEntityList.get(mPosition).getLink()) && wordsEntityList.get(mPosition).getLink().length() > 1) {
                    startActivity(intent);
                }
            }
        }
    }
}
