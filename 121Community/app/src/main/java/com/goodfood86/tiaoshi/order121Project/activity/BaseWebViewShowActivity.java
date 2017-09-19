package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.widget.ProgressHUD;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/3/1.
 */
public class BaseWebViewShowActivity extends Activity implements View.OnClickListener{
    @ViewInject(R.id.nav_back)
    ImageView nav_back;
    @ViewInject(R.id.nav_title)
    TextView nav_title;
    private WebView wv;
    private ProgressHUD dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        ViewUtils.inject(this);
        initTitleBar();
        wv = (WebView) findViewById(R.id.wv_agreement);
        dialog = ProgressHUD.show(BaseWebViewShowActivity.this, "正在加载中", false, null);
        initWebview();
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initTitleBar() {
        nav_back.setOnClickListener(this);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("title"))) {
            nav_title.setText(getIntent().getStringExtra("title"));
        } else {
            nav_title.setText(getIntent().getStringExtra(getString(R.string.app_name)));
        }
    }
    private void initWebview() {
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv.loadUrl(BaseWebViewShowActivity.this.getIntent().getStringExtra("url"));
        dialog.show();
        //加载数据
        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    BaseWebViewShowActivity.this.setTitle("加载完成");
                } else {
                    BaseWebViewShowActivity.this.setTitle("加载中.......");

                }
            }
        });
//这个是当网页上的连接被点击的时候
        wv.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                Log.e("url", url);
                wv.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dialog.dismiss();
            }
        });
        // goBack()表示返回webView的上一页面
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wv != null) {
            wv.destroy();
        }
    }
}
