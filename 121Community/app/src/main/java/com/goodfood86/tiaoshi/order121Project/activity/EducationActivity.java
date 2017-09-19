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
import android.widget.TextView;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.BaseGridviewAdapter;
import com.goodfood86.tiaoshi.order121Project.adapter.ZixunGridviewAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.GridViewModel;
import com.goodfood86.tiaoshi.order121Project.model.PubDocModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/8/1.
 */
public class EducationActivity extends Activity implements View.OnClickListener {
    private List<String> picUrlList = new ArrayList<>();
    private List<PubDocModel.DataBean.NodesBean> wordsEntityList;
    @ViewInject(R.id.convenient_banner)
    private ConvenientBanner convenient_banner;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back; @ViewInject(R.id.iv_jiangzuo)
    private ImageView iv_jiangzuo; @ViewInject(R.id.iv_wenhua)
    private ImageView iv_wenhua; @ViewInject(R.id.iv_wudao)
    private ImageView iv_wudao;
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.gv_group)
    private GridView gv_group;
    @ViewInject(R.id.gv_group_partners)
    GridView gv_group_partners;
    private List<GridViewModel> listGridData;
    private List<PubDocModel.DataBean.NodesBean> listZixunBean;
    private ZixunGridviewAdapter zixunGridviewAdapter;
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
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        initView();
        initListener();
        initData();
    }
    private void initView() {
        ViewUtils.inject(this);
        nav_title.setText("教育");
        convenient_banner.startTurning(4000);
        BaseGridviewAdapter baseGridviewAdapter = new BaseGridviewAdapter(this, setData());
        gv_group.setAdapter(baseGridviewAdapter);
        //知识天地
        listZixunBean = new ArrayList();
        zixunGridviewAdapter = new ZixunGridviewAdapter(this, listZixunBean);
        gv_group_partners.setAdapter(zixunGridviewAdapter);
        gv_group_partners.setFocusable(false);
    }
    private void initListener(){
        nav_back.setOnClickListener(this);
        iv_jiangzuo.setOnClickListener(this);
        iv_wenhua.setOnClickListener(this);
        iv_wudao.setOnClickListener(this);
        gv_group_partners.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(EducationActivity.this,ZixunDetailActivity.class)
                .putExtra("id",listZixunBean.get(position).getId())
                .putExtra("name",listZixunBean.get(position).getName()));
            }
        });
        gv_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(EducationActivity.this,CoursListActivity.class)
                        .putExtra("name","小学补习班")
                        .putExtra("coursTypeCode","GradeXiao"));
                        break;
                    case 1:
                        startActivity(new Intent(EducationActivity.this,CoursListActivity.class)
                                .putExtra("name","初中补习班")
                                .putExtra("coursTypeCode","GradeZhong"));
                        break;
                    case 2:
                        startActivity(new Intent(EducationActivity.this,CoursListActivity.class)
                                .putExtra("name","高中补习班")
                                .putExtra("coursTypeCode","GradeGao"));
                        break;
                    case 3:
                        startActivity(new Intent(EducationActivity.this, ZixunDetailActivity.class)
                                .putExtra("type", "list")
                                .putExtra("name", "兴趣班")
                                .putExtra("code", "EducationXqb"));

                        break;
                    case 4:
                        Intent intent1 = new Intent(EducationActivity.this, BaseWebViewShowActivity.class);
                        intent1.putExtra("title", "免费课堂");
                        intent1.putExtra("url", "http://m.sp910.com/");
                        startActivity(intent1);
                        break;
                    case 5:
                        Intent intent5 = new Intent(EducationActivity.this, BaseWebViewShowActivity.class);
                        intent5.putExtra("title", "热门资讯");
                        intent5.putExtra("url", "http://video.m.baidu.com/?from=wise_nav#/index/news?_k=l69r6f");
                        startActivity(intent5);
                        break;
                    case 6:
                        Intent intent6 = new Intent(EducationActivity.this, BaseWebViewShowActivity.class);
                        intent6.putExtra("title", "公益视频");
                        intent6.putExtra("url", "http://m.iqiyi.com/gongyi");
                        startActivity(intent6);
                        break;
                    case 7:
                        Intent intent7 = new Intent(EducationActivity.this, BaseWebViewShowActivity.class);
                        intent7.putExtra("title", "爱学习");
                        intent7.putExtra("url", "http://m.open.163.com/");
                        startActivity(intent7);
                        break;
                }
            }
        });
    }

    private void initData() {
        //banner图
        HashMap map = new HashMap();
        map.put("code", "BannerEducation");
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", MD5.getMD5(sign));
        OkHttpManager.postAsync(G.Host.PUB_DOC, map, new MyCallBack(1,this, new PubDocModel(), handler));
        HashMap map1 = new HashMap();
        map1.put("code", "EducationZstd");
        map1.put("actReq", SignUtil.getRandom());
        map1.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign1 = SignUtil.getSign(map1);
        map1.put("sign", MD5.getMD5(sign1));
        OkHttpManager.postAsync(G.Host.PUB_DOC, map1, new MyCallBack(2,this, new PubDocModel(), handler));

    }
    private List<GridViewModel> setData() {
        listGridData = new ArrayList();
        int pic[] ={R.mipmap.jy_xiaoxue,R.mipmap.jy_zhongxue,R.mipmap.jy_gaozhong,R.mipmap.jy_xingqu,R.mipmap.jy_mianfei,R.mipmap.jy_zixun,R.mipmap.jy_gongyi,R.mipmap.jy_aixuexi};
        for (int i = 0; i < 8; i++) {
            GridViewModel model1 = new GridViewModel(getResources().getDrawable(pic[i]));
            listGridData.add(model1);
        }
        return listGridData;
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
            case R.id.iv_jiangzuo:
                startActivity(new Intent(EducationActivity.this, ZixunDetailActivity.class)
                        .putExtra("type", "detail")
                        .putExtra("name", "校园讲座")
                        .putExtra("code", "EducationZjjz"));
                break;
            case R.id.iv_wenhua:
                startActivity(new Intent(EducationActivity.this, ZixunDetailActivity.class)
                        .putExtra("type", "list")
                        .putExtra("name", "文化")
                        .putExtra("code", "EducationWen"));
                break;
            case R.id.iv_wudao:
                startActivity(new Intent(EducationActivity.this, ZixunDetailActivity.class)
                        .putExtra("type", "list")
                        .putExtra("name", "舞蹈")
                        .putExtra("code", "EducationWu"));
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
                Intent intent = new Intent(EducationActivity.this, WebViewShowActivity.class);
                intent.putExtra("url", wordsEntityList.get(mPosition).getLink());
                if (!TextUtils.isEmpty(wordsEntityList.get(mPosition).getLink()) && wordsEntityList.get(mPosition).getLink().length() > 1) {
                    startActivity(intent);
                }
            }
        }
    }
}
