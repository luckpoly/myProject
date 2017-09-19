package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.ZixunGridviewAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.GetWordModel;
import com.goodfood86.tiaoshi.order121Project.model.ModuleMainModel;
import com.goodfood86.tiaoshi.order121Project.model.PubDocModel;
import com.goodfood86.tiaoshi.order121Project.model.RTokenModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.rongyun.GroupListActivity;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.RongHttp;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

/**
 * Created by Administrator on 2016/8/1.
 */
public class OldManActivity extends Activity implements View.OnClickListener {
    private List<String> picUrlList = new ArrayList<>();
    private List<PubDocModel.DataBean.NodesBean> wordsEntityList;
    @ViewInject(R.id.convenient_banner)
    private ConvenientBanner convenient_banner;
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.iv_huodong)
    private ImageView iv_huodong;
     @ViewInject(R.id.iv_liaotianshi)
    private ImageView iv_liaotianshi;
    @ViewInject(R.id.iv_go_yue)
    private ImageView iv_go_yue;
    @ViewInject(R.id.gv_group_partners)
    GridView gv_group_partners;
    @ViewInject(R.id.iv_go_friend)
    private ImageView iv_go_friend;
    private List<PubDocModel.DataBean.NodesBean> listZixunBean;

    private ZixunGridviewAdapter zixunGridviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oldman);
        initView();
        initData();
    }
    private void initView() {
        ViewUtils.inject(this);
        nav_back.setOnClickListener(this);
        iv_huodong.setOnClickListener(this);
        iv_liaotianshi.setOnClickListener(this);
        iv_go_friend.setOnClickListener(this);
        iv_go_yue.setOnClickListener(this);
        nav_title.setText("邻里圈");
        convenient_banner.startTurning(4000);
        listZixunBean = new ArrayList();
        zixunGridviewAdapter = new ZixunGridviewAdapter(this, listZixunBean);
        gv_group_partners.setAdapter(zixunGridviewAdapter);
        gv_group_partners.setFocusable(false);
        gv_group_partners.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(OldManActivity.this,ZixunDetailActivity.class)
                .putExtra("id",listZixunBean.get(position).getId())
                .putExtra("name",listZixunBean.get(position).getName()));
            }
        });
    }
    private void initData() {
        //banner图
        HashMap map = new HashMap();
        map.put("code", "BannerAged");
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", MD5.getMD5(sign));
        OkHttpManager.postAsync(G.Host.PUB_DOC, map, new MyCallBack(1,this, new PubDocModel(), handler));
        HashMap map1 = new HashMap();
        map1.put("code", "CommunityHdcs");
        map1.put("actReq", SignUtil.getRandom());
        map1.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign1 = SignUtil.getSign(map1);
        map1.put("sign", MD5.getMD5(sign1));
        OkHttpManager.postAsync(G.Host.PUB_DOC, map1, new MyCallBack(2,this, new PubDocModel(), handler));
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
            case R.id.iv_huodong:
                if (!Order121Application.isLogin()) {
                    ToastUtil.show(OldManActivity.this,"请先登陆", Toast.LENGTH_SHORT);
                    startActivity(new Intent(OldManActivity.this,LoginActivity.class));
                }else {
                    startActivity(new Intent(OldManActivity.this,LlqListActivity.class));
                }
                break;
            case R.id.iv_liaotianshi:
                if (!Order121Application.isLogin()) {
                    ToastUtil.show(OldManActivity.this,"请先登陆", Toast.LENGTH_SHORT);
                    startActivity(new Intent(OldManActivity.this,LoginActivity.class));
                }else {
                    startActivity(new Intent(OldManActivity.this,GroupListActivity.class));
                }
                break;
            case R.id.iv_go_friend:
                startActivity(new Intent(OldManActivity.this,FriendsQuanActivity.class));
                break;
            case R.id.iv_go_yue:
                startActivity(new Intent(OldManActivity.this,YuleActivity.class));
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
                Intent intent = new Intent(OldManActivity.this, WebViewShowActivity.class);
                intent.putExtra("url", wordsEntityList.get(mPosition).getLink());
                if (!TextUtils.isEmpty(wordsEntityList.get(mPosition).getLink()) && wordsEntityList.get(mPosition).getLink().length() > 1) {
                    startActivity(intent);
                }
            }
        }
    }

}
