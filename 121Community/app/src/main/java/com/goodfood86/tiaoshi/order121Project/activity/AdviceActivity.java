package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.VerifyModel;
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
public class AdviceActivity extends Activity implements View.OnClickListener{
    View view;
    @ViewInject(R.id.et_commit)
    private EditText et_commit;
    @ViewInject(R.id.bt_commit)
    private Button bt_commit;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    private ProgressHUD dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        ViewUtils.inject(this);
        initView();
    }
    private void initView(){
        dialog= ProgressHUD.show(AdviceActivity.this,"玩命提交中...",false,null);
        nav_title.setText("问题反馈");
        nav_back.setOnClickListener(this);
        bt_commit.setOnClickListener(this);
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dialog.dismiss();
            switch (msg.what){
                case 1:
                    VerifyModel model=(VerifyModel)msg.obj;
                    if (model.getRespCode()==0){
                        ToastUtil.showShort(AdviceActivity.this,model.getRespMsg());
                        et_commit.setText("感谢您的反馈！");
                    }else {
                        ToastUtil.showShort(AdviceActivity.this,model.getRespMsg());
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_back:
                finish();
                break;
            case R.id.bt_commit:
                if (!Order121Application.isLogin()){
                    startActivity(new Intent(AdviceActivity.this, LoginActivity.class));
                    return;
                }
                String content= et_commit.getText().toString().trim();
                if (content==null||content.equals("")){
                    ToastUtil.showShort(AdviceActivity.this,"请输入反馈内容");
                    return;
                }
                dialog.show();
                HttpUtils httpUtils=new HttpUtils();
                RequestParams params=new RequestParams();
                params.addBodyParameter("token", Order121Application.globalLoginModel.getData().getToken());
                params.addBodyParameter("content",content);
                Log.i("Info", Order121Application.globalLoginModel.getData().getToken()+content);
                httpUtils.send(HttpRequest.HttpMethod.POST, G.Host.COMMID,params,new MyRequestCallBack(AdviceActivity.this,handler,1,new VerifyModel()));
                break;
        }
    }
}
