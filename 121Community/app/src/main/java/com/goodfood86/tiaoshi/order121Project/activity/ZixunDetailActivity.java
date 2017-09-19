package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/12/13.
 */

public class ZixunDetailActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.wv_content)
    WebView wv_content;
    @ViewInject(R.id.nav_title)
    TextView nav_title;
    @ViewInject(R.id.nav_back)
    ImageView nav_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zixun_detail);
        ViewUtils.inject(this);
        initView();
        initWeb();
    }

    private void initView() {
        nav_back.setOnClickListener(this);
        nav_title.setText(getIntent().getStringExtra("name"));
        nav_title.setMaxEms(8);
        nav_title.setMaxLines(1);
        nav_title.setEllipsize(TextUtils.TruncateAt.END);
    }

    private void initWeb() {
        wv_content.getSettings().setJavaScriptEnabled(true);
        wv_content.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        wv_content.getSettings().setDefaultTextEncodingName("UTF-8");
        wv_content.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (getIntent().getStringExtra("type") != null) {
            if (getIntent().getStringExtra("type").equals("list")) {
                //文档列表详情
                wv_content.loadUrl(G.Host.LIST_ITEM + getIntent().getStringExtra("code"));
            } else if (getIntent().getStringExtra("type").equals("detail")) {
                //文档详情
                wv_content.loadUrl(G.Host.DOC_DETAIL + getIntent().getStringExtra("code"));
            }
        }else {
            //列表详情
            wv_content.loadUrl(G.Host.ITEM_DETAIL + getIntent().getStringExtra("id"));
        }
        wv_content.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            @Override
            public void onPageFinished(WebView view, String url) {
            }
        });


    }

    public boolean onKeyDown(int keyCoder, KeyEvent event) {
        if (wv_content.canGoBack() && keyCoder == android.view.KeyEvent.KEYCODE_BACK) {
            wv_content.goBack();
            return true;
        } else {
            finish();
            return false;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_back:
                if (wv_content.canGoBack()) {
                    wv_content.goBack();
                } else {
                    finish();
                }
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wv_content!=null){
            wv_content.getSettings().setBuiltInZoomControls(true);
            wv_content.setVisibility(View.GONE);
            wv_content.destroy();
        }

    }
}
