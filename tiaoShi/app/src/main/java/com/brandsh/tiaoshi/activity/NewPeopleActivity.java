package com.brandsh.tiaoshi.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.Newpeopledata;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mingle.widget.ShapeLoadingDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;


/**
 * Created by tiashiwang on 2016/4/20.
 */
public class NewPeopleActivity extends Activity {
    @ViewInject(R.id.tv_newpople_title)
    private TextView tv_newpople_title;
    @ViewInject(R.id.wv_newpeople_content)
    private WebView wv_newpeople_content;
    @ViewInject(R.id.about_us_rlBack)
    private RelativeLayout about_us_rlBack;

    private HashMap dataMap;
    private ShapeLoadingDialog loadingDialog;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Newpeopledata newpeopledata = (Newpeopledata) msg.obj;
                    if (newpeopledata.getRespCode().equals("SUCCESS")) {
                        String url = newpeopledata.getData().getLink();
                        if (!TextUtils.isEmpty(url)) {
                            wv_newpeople_content.loadUrl(url);
                        } else if (!TextUtils.isEmpty(newpeopledata.getData().getContent())) {
                            wv_newpeople_content.loadData(newpeopledata.getData().getIntro(), "text/html;charset=UTF-8", null);
                        } else {
                            ToastUtil.showShort(NewPeopleActivity.this, "没有任何内容");
                        }
                    } else {
                        ToastUtil.showShort(NewPeopleActivity.this, newpeopledata.getRespMsg());
                    }

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpeople);
        //沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);
        initView();
        initWeb();
    }

    @Override
    public void onBackPressed() {
        if (wv_newpeople_content.canGoBack()) {
            wv_newpeople_content.goBack();
        } else {
            wv_newpeople_content.clearCache(true);
            wv_newpeople_content.clearHistory();
            super.onBackPressed();
        }
    }
    private void initView() {
        about_us_rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loadingDialog = ProgressHUD.show(this, "玩命加载中...");
        tv_newpople_title.setText(getIntent().getStringExtra("title"));
        dataMap = new HashMap();
        dataMap.put("code", getIntent().getStringExtra("code"));
        dataMap.put("actReq", SignUtil.getRandom());
        dataMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(dataMap);
        dataMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.GET_WORD, dataMap, new MyCallBack(1, NewPeopleActivity.this, new Newpeopledata(), handler));

    }

    private void initWeb() {
        wv_newpeople_content.getSettings().setJavaScriptEnabled(true);
        wv_newpeople_content.setWebViewClient(new WebViewClient() {
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

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
