package com.brandsh.tiaoshi.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.fragment.HomeFragment;
import com.brandsh.tiaoshi.model.UpdateVersionModel;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SPUtil;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.brandsh.tiaoshi.versionUpdate.DownLoadService;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/1/5.
 */

public class AboutUsActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.tv_version)
    TextView tv_version;
    @ViewInject(R.id.nav_back)
    ImageView nav_back;
    @ViewInject(R.id.nav_title)
    TextView nav_title;
    @ViewInject(R.id.tv_new_versin)
    TextView tv_new_versin;
    @ViewInject(R.id.tv_tag_new)
    TextView tv_tag_new;
    @ViewInject(R.id.ll_to_update)
    RelativeLayout ll_to_update;
    @ViewInject(R.id.ll_to_sm)
    RelativeLayout ll_to_sm;
    @ViewInject(R.id.ll_to_fk)
    RelativeLayout ll_to_fk;
    private  UpdateVersionModel updateVersionModel;
    private Dialog dialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus_new);
        ViewUtils.inject(this);
        initView();
        initListener();
        AppUtil.Setbar(this);
    }

    private void initView() {
        nav_title.setText("关于我们");
        tv_version.setText("V"+AppUtil.getVersionName(this));
        String newVersion= SPUtil.getSP("newVersion","");
        if (!TextUtils.isEmpty(newVersion)){
            tv_new_versin.setText("V"+newVersion);
            if (AppUtil.getVersionName(this).equals(newVersion)){
                tv_tag_new.setVisibility(View.GONE);
            }else {
                tv_tag_new.setVisibility(View.VISIBLE);
            }
        }
    }
    private void initListener() {
        nav_back.setVisibility(View.VISIBLE);
        nav_back.setOnClickListener(this);
        ll_to_update.setOnClickListener(this);
        ll_to_sm.setOnClickListener(this);
        ll_to_fk.setOnClickListener(this);
    }
    private void aotoUpdate() {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("appType","ANDROID");
        hashMap.put("appRole","USER");
        hashMap.put("actReq", SignUtil.getRandom());
        hashMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(hashMap);
        hashMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.UPDATEVERSION, hashMap, new MyCallBack(4,this,new UpdateVersionModel(), handler));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_back:
                finish();
                break;
            case R.id.ll_to_update:
                aotoUpdate();
                break;
            case R.id.btn_cancel:
                dialog2.dismiss();
                break;
            case R.id.btn_go_update:
               startService(new Intent(AboutUsActivity.this, DownLoadService.class).putExtra("url",updateVersionModel.getData().getUpload()));
                dialog2.dismiss();
                ToastUtil.showShort(this,"开始下载");
                break;
            case R.id.ll_to_sm:
                startActivity(new Intent(AboutUsActivity.this, AboutUsSMActivity.class).putExtra("url", "APPAboutUs"));
            break;
            case R.id.ll_to_fk:
                startActivity(new Intent(AboutUsActivity.this,FeedbackActivity.class));
                break;
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 4:
                    updateVersionModel= (UpdateVersionModel) msg.obj;
                    if ("SUCCESS".equals(updateVersionModel.getRespCode())){
                        Log.e("code", AppUtil.getVersionCode(AboutUsActivity.this) + "  " + updateVersionModel.getData().getVersion()+"."+updateVersionModel.getData().getDeputy());
                        if (!AppUtil.getVersionName(AboutUsActivity.this).equals(updateVersionModel.getData().getVersion()+"."+updateVersionModel.getData().getDeputy())) {
                            dialog2 = new Dialog(AboutUsActivity.this);
                            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(AboutUsActivity.this).inflate(R.layout.updateversion, null);
                            Button btn1 = (Button) linearLayout.findViewById(R.id.btn_cancel);
                            Button btn2 = (Button) linearLayout.findViewById(R.id.btn_go_update);
                            TextView tv_content = (TextView) linearLayout.findViewById(R.id.tv_content);
                            TextView tv_title = (TextView) linearLayout.findViewById(R.id.tv_title);
                            tv_title.setText("版本升级v"+updateVersionModel.getData().getVersion()+"."+updateVersionModel.getData().getDeputy());
                            CharSequence charSequence= Html.fromHtml(updateVersionModel.getData().getIntro());
                            tv_content.setText(charSequence);
                            tv_content.setMovementMethod(LinkMovementMethod.getInstance());
                            dialog2.setContentView(linearLayout);
                            dialog2.setCanceledOnTouchOutside(false);
                            dialog2.getWindow().setBackgroundDrawable(new ColorDrawable());
                            btn1.setOnClickListener(AboutUsActivity.this);
                            btn2.setOnClickListener(AboutUsActivity.this);
                            TiaoshiApplication.isFirstLogin=false;
                            dialog2.show();
                        }else {
                            shortToast("已经是最新版本了");
                        }
                    }
                    break;
            }
        }
    };
}
