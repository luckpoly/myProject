package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/12/19.
 */

public class SearchActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.sn_spinner)
    Spinner  sn_spinner;
    @ViewInject(R.id.tv_action)
    TextView tv_action;
    @ViewInject(R.id.nav_back)
    ImageView nav_back;
    @ViewInject(R.id.wv_content)
    WebView wv_content;
    @ViewInject(R.id.et_select_name)
    EditText et_select_name;
    String type="MedicalJkzx";
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_search);
         ViewUtils.inject(this);
         initView();
    }
    private void initView() {
        tv_action.setOnClickListener(this);
        nav_back.setOnClickListener(this);
        final String arr[]=new String[]{
                "健康资讯",
                "育儿资讯"
        };
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_sp_search, arr);
        sn_spinner.setAdapter(arrayAdapter);
        sn_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (arr[position].equals("健康资询")){
                    type="MedicalJkzx";
                }else if (arr[position].equals("育儿资询")){
                    type="MedicalYezx";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        wv_content.getSettings().setJavaScriptEnabled(true);
        wv_content.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings webSettings = wv_content.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        wv_content.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                Log.e("url", url);
                wv_content.loadUrl(url);
                return false;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_action:
                wv_content.loadUrl("http://test.community.121-home.com/doc/items-list?code="+type+"&name="+et_select_name.getText().toString());
//                wv_content.loadUrl("http://www.baidu.com/");
                break;
            case R.id.nav_back:
                finish();
                break;
        }

    }
    public boolean onKeyDown(int keyCoder, KeyEvent event) {
        if (wv_content.canGoBack() && keyCoder == KeyEvent.KEYCODE_BACK) {
            wv_content.goBack();
            return true;
        }else{
            finish();
            return false;
        }

    }
}
