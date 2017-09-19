package com.goodfood86.tiaoshi.order121Project.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
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
import com.goodfood86.tiaoshi.order121Project.adapter.ZixunGridviewAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.PubDocModel;
import com.goodfood86.tiaoshi.order121Project.model.YaodianDetailModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */

public class YaodianDetailActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.gv_group_partners)
    GridView gv_group_partners;
    @ViewInject(R.id.nav_title)
    TextView nav_title;
    @ViewInject(R.id.nav_back)
    ImageView nav_back;
    @ViewInject(R.id.ll_yd_paiban)
    LinearLayout ll_yd_paiban;
    @ViewInject(R.id.ll_yd_phone)
    LinearLayout ll_yd_phone;
    @ViewInject(R.id.ll_yd_zixun)
    LinearLayout ll_yd_zixun;
    @ViewInject(R.id.convenient_banner)
    private ConvenientBanner convenient_banner;
    private List<PubDocModel.DataBean.NodesBean> listZixunBean;
    private ZixunGridviewAdapter zixunGridviewAdapter;
    private List<String> picUrlList = new ArrayList<>();
    YaodianDetailModel yaodianDetailModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yaodian_detail);
        ViewUtils.inject(this);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        listZixunBean = new ArrayList();
        zixunGridviewAdapter = new ZixunGridviewAdapter(this, listZixunBean);
        gv_group_partners.setAdapter(zixunGridviewAdapter);
        gv_group_partners.setFocusable(false);
    }

    private void initListener() {
        nav_back.setOnClickListener(this);
        ll_yd_paiban.setOnClickListener(this);
        ll_yd_zixun.setOnClickListener(this);
        ll_yd_phone.setOnClickListener(this);
        gv_group_partners.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(YaodianDetailActivity.this, ZixunDetailActivity.class)
                        .putExtra("id", listZixunBean.get(position).getId())
                        .putExtra("name", listZixunBean.get(position).getName()));
            }
        });
    }

    private void initData() {
        HashMap map1 = new HashMap();
        map1.put("pharmacyId", getIntent().getStringExtra("id"));
        map1.put("actReq", SignUtil.getRandom());
        map1.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign1 = SignUtil.getSign(map1);
        map1.put("sign", MD5.getMD5(sign1));
        OkHttpManager.postAsync(G.Host.PHARMACY_DETAIL, map1, new MyCallBack(1, this, new YaodianDetailModel(), handler));
        HashMap map2 = new HashMap();
        map2.put("code", "MedicalJkzx");
        map2.put("nodesLimit", "5");
        map2.put("actReq", SignUtil.getRandom());
        map2.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign2 = SignUtil.getSign(map2);
        map2.put("sign", MD5.getMD5(sign2));
        OkHttpManager.postAsync(G.Host.PUB_DOC, map2, new MyCallBack(2, this, new PubDocModel(), handler));
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    yaodianDetailModel = (YaodianDetailModel) msg.obj;
                    if ("SUCCESS".equals(yaodianDetailModel.getRespCode())) {
                        if (picUrlList.size() == 0) {
                            String[] str = yaodianDetailModel.getData().getImgs().split(",");
                            for (int i = 0; i < str.length; i++) {
                                picUrlList.add(str[i]);
                            }
                            initBanner();
                        }
                        nav_title.setText(yaodianDetailModel.getData().getName());
                    }
                    break;
                case 2:
                    PubDocModel pubDocModel = (PubDocModel) msg.obj;
                    if ("SUCCESS".equals(pubDocModel.getRespCode()) && pubDocModel.getData().getNodes() != null) {
                        listZixunBean.addAll(pubDocModel.getData().getNodes());
                        zixunGridviewAdapter.notifyDataSetChanged();
                    }
                    break;

            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_back:
                finish();
                break;
            case R.id.ll_yd_phone:
                if (yaodianDetailModel.getData().getTel() != null) {
                    call(yaodianDetailModel.getData().getTel());
                }
                break;
            case R.id.ll_yd_zixun:
                startActivity(new Intent(YaodianDetailActivity.this, YdZixuntaiActivity.class)
                        .putExtra("phone", yaodianDetailModel.getData().getTel())
                        .putExtra("data", yaodianDetailModel.getData().getAdviceBar())
                        .putExtra("from", "zixun"));
                break;
            case R.id.ll_yd_paiban:
                startActivity(new Intent(YaodianDetailActivity.this, YdZixuntaiActivity.class)
                        .putExtra("phone", yaodianDetailModel.getData().getTel())
                        .putExtra("data", yaodianDetailModel.getData().getDoctors())
                        .putExtra("from", "paiban"));
                break;
        }

    }

    private void call(final String phone) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("联系药店:").setMessage(phone);
        builder.setPositiveButton("拨打", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
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

        public MyOnClickListener(int position) {
            this.mPosition = position;
        }

        @Override
        public void onClick(View v) {
//            if (wordsEntityList.get(mPosition).getHref() != null && !wordsEntityList.get(mPosition).getHref().equals("#")) {
//                Intent intent = new Intent(getActivity(), WebViewShowActivity.class);
//                intent.putExtra("url", wordsEntityList.get(mPosition).getLink());
//                if (!TextUtils.isEmpty(wordsEntityList.get(mPosition).getLink()) && wordsEntityList.get(mPosition).getLink().length() > 1) {
//                    startActivity(intent);
//                }
//            }
        }
    }
}
