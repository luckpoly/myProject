package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.EvaluateModel;
import com.goodfood86.tiaoshi.order121Project.model.OrderListModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.widget.TitleBarView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by Administrator on 2016/4/12.
 */
public class EvaluateActivity extends Activity implements View.OnClickListener{
    @ViewInject(R.id.ll_login_title)
    private RelativeLayout ll_login_title;
    private TitleBarView titleBarView;
    @ViewInject(R.id.tv_orderNo)
    private TextView tv_orderNo;
    @ViewInject(R.id.tv_orderTime)
    private TextView tv_orderTime;
    @ViewInject(R.id.tv_order_price)
    private TextView tv_order_price;
    @ViewInject(R.id.tv_orderUnit)
    private TextView tv_orderUnit;
    @ViewInject(R.id.et_evaluate)
    private TextView et_evaluate;
    @ViewInject(R.id.bt_commit)
    private Button bt_commit;
    @ViewInject(R.id.iv1)
    private ImageView iv1;
    @ViewInject(R.id.iv2)
    private ImageView iv2;
    @ViewInject(R.id.iv3)
    private ImageView iv3;
    @ViewInject(R.id.iv4)
    private ImageView iv4;
    @ViewInject(R.id.iv5)
    private ImageView iv5;
    private int evaluateBar=1;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg!=null){
                switch (msg.what){
                    case 1:
                        EvaluateModel evaluateModel= (EvaluateModel) msg.obj;
                        if (evaluateModel.getRespCode()==0){
                            ToastUtil.showShort(EvaluateActivity.this,"评价成功");
                            sendBroadcast(new Intent("updateOrder"));
                            EvaluateActivity.this.finish();
                        }
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valueorder_activity);
        ViewUtils.inject(this);
        initTitleBar();
        initData();
        initListener();
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
        titleBarView=new TitleBarView(this,ll_login_title,"评价");
    }

    private void initData() {
        tv_orderNo.setText(getIntent().getStringExtra("orderNo"));
        tv_order_price.setText(getIntent().getStringExtra("orderPrice")+"元");
        tv_orderUnit.setText(getIntent().getStringExtra("orderUnit"));
        tv_orderTime.setText(getTime(getIntent().getIntExtra("orderTime",0)));
    }

    private void initListener() {
        bt_commit.setOnClickListener(this);
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);
        iv5.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v!=null){
            switch (v.getId()){
                case R.id.iv1:
                    iv1.setImageResource(R.mipmap.star);
                    iv2.setImageResource(R.mipmap.non_star);
                    iv3.setImageResource(R.mipmap.non_star);
                    iv4.setImageResource(R.mipmap.non_star);
                    iv5.setImageResource(R.mipmap.non_star);
                    evaluateBar=1;
                    break;
                case R.id.iv2:
                    iv1.setImageResource(R.mipmap.star);
                    iv2.setImageResource(R.mipmap.star);
                    iv3.setImageResource(R.mipmap.non_star);
                    iv4.setImageResource(R.mipmap.non_star);
                    iv5.setImageResource(R.mipmap.non_star);
                    evaluateBar=2;
                    break;
                case R.id.iv3:
                    iv1.setImageResource(R.mipmap.star);
                    iv2.setImageResource(R.mipmap.star);
                    iv3.setImageResource(R.mipmap.star);
                    iv4.setImageResource(R.mipmap.non_star);
                    iv5.setImageResource(R.mipmap.non_star);
                    evaluateBar=3;
                    break;
                case R.id.iv4:
                    iv1.setImageResource(R.mipmap.star);
                    iv2.setImageResource(R.mipmap.star);
                    iv3.setImageResource(R.mipmap.star);
                    iv4.setImageResource(R.mipmap.star);
                    iv5.setImageResource(R.mipmap.non_star);
                    evaluateBar=4;
                    break;
                case R.id.iv5:
                    iv1.setImageResource(R.mipmap.star);
                    iv2.setImageResource(R.mipmap.star);
                    iv3.setImageResource(R.mipmap.star);
                    iv4.setImageResource(R.mipmap.star);
                    iv5.setImageResource(R.mipmap.star);
                    evaluateBar=5;
                    break;
                case R.id.bt_commit:
                    if (!TextUtils.isEmpty(et_evaluate.getText().toString().trim())){
                        submit();
                    }
                    break;
            }
        }

    }
    private void submit(){
        RequestParams requestParams=new RequestParams();
        requestParams.addBodyParameter("orderId",getIntent().getStringExtra("orderId"));
        requestParams.addBodyParameter("content",et_evaluate.getText().toString());
        requestParams.addBodyParameter("score",(evaluateBar*20)+"");
        requestParams.addBodyParameter("token", Order121Application.globalLoginModel.getData().getToken());
        Log.e("--",getIntent().getStringExtra("orderId"));
        Order121Application.getGlobalHttpUtils().send(HttpRequest.HttpMethod.POST, G.Host.ORDER_EVALUATE,requestParams,new MyRequestCallBack(this,handler,1,new EvaluateModel()));
    }
    private String getTime(long time) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(time * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd  hh:mm");
        String totalTime = simpleDateFormat.format(gc.getTime());
        return totalTime;
    }
}
