package com.brandsh.tiaoshi.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.AboutUsJsonData;
import com.brandsh.tiaoshi.model.Newpeopledata;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.mingle.widget.ShapeLoadingDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

public class AboutUsSMActivity extends FragmentActivity implements View.OnClickListener {
    private RelativeLayout about_us_rlBack;
    private WebView wv_aboutus;
    private ShapeLoadingDialog loadingDialog;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        //沉浸状态栏
        AppUtil.Setbar(this);
        init();
        initWeb();

    }

    @Override
    public void onBackPressed() {
        if (wv_aboutus.canGoBack()) {
            wv_aboutus.goBack();
        } else {
            wv_aboutus.clearCache(true);
            wv_aboutus.clearHistory();
            super.onBackPressed();
        }
    }

    private void init() {
        loadingDialog = ProgressHUD.show(this, "玩命加载中...");
        about_us_rlBack = (RelativeLayout) findViewById(R.id.about_us_rlBack);
        about_us_rlBack.setOnClickListener(this);
        wv_aboutus = (WebView) findViewById(R.id.wv_aboutus);
        tv_title = (TextView) findViewById(R.id.tv_title);
        if (getIntent().getStringExtra("type") != null) {
            tv_title.setText(getIntent().getStringExtra("type"));
        }
        HashMap dataMap = new HashMap();
        dataMap.put("code", getIntent().getStringExtra("url"));
        dataMap.put("actReq", SignUtil.getRandom());
        dataMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(dataMap);
        dataMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.GET_WORD, dataMap, new MyCallBack(2, AboutUsSMActivity.this, new Newpeopledata(), handler));
    }

    private void initWeb() {
        wv_aboutus.getSettings().setJavaScriptEnabled(true);
        wv_aboutus.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wv_aboutus.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                loadingDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                loadingDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_us_rlBack:
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
                    AboutUsJsonData aboutUsJsonData = (AboutUsJsonData) msg.obj;
                    if (aboutUsJsonData != null) {
                        if (aboutUsJsonData.getRespCode() == 0) {
                        } else {
                            Toast.makeText(AboutUsSMActivity.this, aboutUsJsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 2:
                    Newpeopledata newpeopledata = (Newpeopledata) msg.obj;
                    if (newpeopledata.getRespCode().equals("SUCCESS")) {
                        String url = newpeopledata.getData().getLink();
                        if (!TextUtils.isEmpty(url)) {
                            wv_aboutus.loadUrl(url);
                        } else if (!TextUtils.isEmpty(newpeopledata.getData().getContent())) {
                            wv_aboutus.loadData(newpeopledata.getData().getContent(), "text/html;charset=UTF-8", null);
                        } else {
                            ToastUtil.showShort(AboutUsSMActivity.this, "没有任何内容");
                        }
                    } else {
                        ToastUtil.showShort(AboutUsSMActivity.this, newpeopledata.getRespMsg());
                    }
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
