package com.goodfood86.tiaoshi.order121Project.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.widget.ProgressHUD;
import com.goodfood86.tiaoshi.order121Project.widget.TitleBarView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2016/8/9.
 */
public class YuyueActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;
    @ViewInject(R.id.tv_action_yuyue)
    private TextView tv_action_yuyue;
    private WebView wv;
    private ProgressHUD dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuyue);
        ViewUtils.inject(this);
        wv = (WebView) findViewById(R.id.wv_yuyue);
        dialog = ProgressHUD.show(YuyueActivity.this, "正在加载中", false, null);
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

    private void initWebview() {
        nav_title.setText("预约");
        nav_back.setOnClickListener(this);
        tv_action_yuyue.setOnClickListener(this);
        tv_phone.setOnClickListener(this);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings webSettings = wv.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setBuiltInZoomControls(true);
//        wv.loadUrl(YuyueActivity.this.getIntent().getStringExtra("url"));
        wv.loadUrl(G.Host.DATE_URl + getIntent().getStringExtra("code"));
        dialog.show();
//加载数据
        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    YuyueActivity.this.setTitle("加载完成");
                } else {
                    YuyueActivity.this.setTitle("加载中.......");

                }
            }
        });
//这个是当网页上的连接被点击的时候
        wv.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                Log.e("url", url);
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

    public boolean onKeyDown(int keyCoder, KeyEvent event) {
        if (wv.canGoBack() && keyCoder == KeyEvent.KEYCODE_BACK) {
            wv.goBack();
            return true;
        } else {
            finish();
            return false;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_action_yuyue:
                if (!Order121Application.isLogin()) {
                    startActivity(new Intent(YuyueActivity.this, LoginActivity.class));
                    break;
                }
                Intent intent = new Intent(YuyueActivity.this, ActionYuyueActivity.class);
                intent.putExtra("moduleCategoryId", getIntent().getStringExtra("moduleCategoryId"));
                intent.putExtra("typeName", getIntent().getStringExtra("typeName"));
                intent.putExtra("code", getIntent().getStringExtra("typeCode"));
                startActivity(intent);
                break;
            case R.id.tv_phone:
                call();
                break;
            case R.id.nav_back:
                finish();
                break;
        }
    }

    private void call() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("联系商家:").setMessage("021-61553481");
        builder.setPositiveButton("拨打", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "02161553481"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(YuyueActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
}
