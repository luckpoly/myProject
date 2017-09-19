package com.goodfood86.tiaoshi.order121Project.activity;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.AboutUsModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.widget.ProgressHUD;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/3/28.
 */
public class AboutUsActivity extends Activity {
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.wv_agreement)
    private WebView wv_agreement;
    private boolean isHomePage = true;
    View view;
    private ProgressHUD dialog;
    private RequestParams logindata;
    private HttpUtils httpUtils;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    dialog.dismiss();
                    AboutUsModel aboutUsModel = (AboutUsModel) msg.obj;
                    if (aboutUsModel.getRespCode() == 0) {
                        String url = aboutUsModel.getData().getLink();
                        if (url.length() > 0) {
                            setUrl(url);
                        } else {
                            String comtemt = aboutUsModel.getData().getContent();
                            wv_agreement.loadData(comtemt, "text/html;charset=UTF-8", null);
                        }
                    } else {
                        ToastUtil.showShort(AboutUsActivity.this, aboutUsModel.getRespMsg());
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        ViewUtils.inject(this);
        initView();
    }
    private void initView() {
        dialog = ProgressHUD.show(AboutUsActivity.this, "玩命加载中...", false, null);
        httpUtils = Order121Application.getGlobalHttpUtils();
        nav_back.setVisibility(View.VISIBLE);
        nav_title.setText("关于我们");
        nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getdata();
    }

    private void getdata() {
        dialog.show();
        logindata = new RequestParams();
        logindata.addBodyParameter("code", "UserWordAboutus");
        httpUtils.send(HttpRequest.HttpMethod.POST, G.Host.GET_WORD, logindata, new MyRequestCallBack(AboutUsActivity.this, handler, 1, new AboutUsModel()));
    }
    private void setUrl(String url) {
        wv_agreement.getSettings().setJavaScriptEnabled(true);
        wv_agreement.loadUrl(url);
        wv_agreement.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                dialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();
            }
        });
    }
}
