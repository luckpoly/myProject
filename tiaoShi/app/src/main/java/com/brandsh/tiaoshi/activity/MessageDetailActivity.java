package com.brandsh.tiaoshi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.MessageDetailJsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mingle.widget.ShapeLoadingDialog;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class MessageDetailActivity extends FragmentActivity implements View.OnClickListener{
    @ViewInject(R.id.msg_detail_tvTitle)
    TextView msg_detail_tvTitle;
    @ViewInject(R.id.msg_detail_tvContent)
    TextView msg_detail_tvContent;
    @ViewInject(R.id.msg_detail_tvDate)
    TextView msg_detail_tvDate;
    @ViewInject(R.id.msg_detail_ivBack)
    ImageView msg_detail_ivBack;

    private HttpUtils httpUtils;
    private HashMap requestMap;
    private String msg_id;
    private String msg_type;
    private ShapeLoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
//沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);

        init();
    }

    private void init() {
        msg_detail_ivBack.setOnClickListener(this);
        msg_id = getIntent().getStringExtra("msg_id");
        msg_type = getIntent().getStringExtra("msg_type");

        if ("4".equals(msg_type)){
            msg_detail_tvTitle.setText("接单提醒");
        }else if ("5".equals(msg_type)){
            msg_detail_tvTitle.setText("发货提醒");
        }else if ("6".equals(msg_type)){
            msg_detail_tvTitle.setText("催单提醒");
        }
        loadingDialog = ProgressHUD.show(this, "努力加载中...");
        loadingDialog.show();

        httpUtils = TiaoshiApplication.getGlobalHttpUtils();
        requestMap=new HashMap();
        requestMap.put("msgId", msg_id);
        requestMap.put("token", TiaoshiApplication.globalToken);
        requestMap.put("actReq",SignUtil.getRandom());
        requestMap.put("actTime",System.currentTimeMillis()/1000+"");
        String sign= SignUtil.getSign(requestMap);
        requestMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.GET_MESSAGE_DETAIL,requestMap,new MyCallBack(1, this, new MessageDetailJsonData(), handler));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.msg_detail_ivBack:
                finish();
                break;
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:

                        loadingDialog.dismiss();

                    MessageDetailJsonData messageDetailJsonData = (MessageDetailJsonData) msg.obj;
                    if (messageDetailJsonData != null) {
                        if ("SUCCESS".equals(messageDetailJsonData.getRespCode())){
                            Intent intent = new Intent("updateMsgList");
                            sendBroadcast(intent);
                            msg_detail_tvTitle.setText(messageDetailJsonData.getData().getTitle());
                            msg_detail_tvContent.setText(messageDetailJsonData.getData().getContent());
                            msg_detail_tvDate.setText(formatDate(messageDetailJsonData.getData().getCreateTime()));
                        }
                    }
                    break;
            }
        }
    };

    private String formatDate(String str){
        long seconds = Long.parseLong(str);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(seconds * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(gc.getTime());
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
