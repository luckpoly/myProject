package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.widget.ProgressHUD;
import com.goodfood86.tiaoshi.order121Project.widget.TitleBarView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2016/3/1.
 */
public class WebViewShowActivity extends Activity {
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.nav_title)
    private TextView nav_title;

    private WebView wv;
    private ProgressHUD dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        ViewUtils.inject(this);
        initTitleBar();
        wv= (WebView) findViewById(R.id.wv_agreement);
        dialog=ProgressHUD.show(WebViewShowActivity.this,"正在加载中",false,null);
        initWebview();
    }
    public void onResume() {
        super.onResume();
        //友盟统计
       //已删
    }
    public void onPause() {
        super.onPause();
        //友盟统计
        //已删
    }
    private void initTitleBar() {
        nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (!TextUtils.isEmpty(getIntent().getStringExtra("name"))){
            nav_title.setText(getIntent().getStringExtra("name"));
        }else {
            nav_title.setText("121社区");
        }
    }

    private void initWebview() {
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("url"))){
            wv.loadUrl(getIntent().getStringExtra("url"));
        }else if (!TextUtils.isEmpty(getIntent().getStringExtra("content"))){
            wv.loadData(getIntent().getStringExtra("content"), "text/html;charset=UTF-8", null);
        }
        // goBack()表示返回webView的上一页面
    }
    public boolean onKeyDown(int keyCoder, KeyEvent event) {
        if (wv.canGoBack() && keyCoder == KeyEvent.KEYCODE_BACK) {
            wv.goBack();
            return true;
        }else{
            finish();
            return false;
        }

    }
}
