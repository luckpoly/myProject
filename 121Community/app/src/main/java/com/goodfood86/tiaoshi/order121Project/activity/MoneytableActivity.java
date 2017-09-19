package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.MoneyTableModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.widget.ProgressHUD;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2016-03-30.
 */
public class MoneytableActivity extends Activity{
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    //默认最小距离最小重量
    @ViewInject(R.id.tv_moneytable_dw)
    private TextView tv_moneytable_dw;
    //默认最小价格
    @ViewInject(R.id.tv_moneytable_dwm)
    private TextView tv_moneytable_dwm;
    //每增加-- 公里
    @ViewInject(R.id.tv_moneytable_d1)
    private TextView tv_moneytable_d1;
    //每增加-- 公里增加 --元
    @ViewInject(R.id.tv_moneytable_dm1)
    private TextView tv_moneytable_dm1;
    //每增加--公斤
    @ViewInject(R.id.tv_default_w1)
    private TextView tv_default_w1;
    //每增加-- 公斤增加 --元
    @ViewInject(R.id.tv_moneytable_wm1)
    private TextView tv_moneytable_wm1;
    //最大重量
    @ViewInject(R.id.tv_moneytable_maxw)
    private TextView tv_moneytable_maxw;
    //最大距离
    @ViewInject(R.id.tv_moneytable_maxd)
    private TextView tv_moneytable_maxd;
    //文字显示
    @ViewInject(R.id.tv_moneytable_maxww)
    private TextView tv_moneytable_maxww;
    @ViewInject(R.id.tv_moneytable_maxdd)
    private TextView tv_moneytable_maxdd;
    HttpUtils httpUtils;
    private ProgressHUD dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moneytable);
        ViewUtils.inject(this);
        initView();
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
    //初始化控件
    private void initView(){
        TextView nav_title= (TextView)findViewById(R.id.nav_title);
        nav_title.setText("价格表");
        dialog= ProgressHUD.show(this, "玩命加载中...", false, null);
        httpUtils= Order121Application.getGlobalHttpUtils();
        nav_back.setVisibility(View.VISIBLE);
        nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getData();
    }
    private void getData(){
        dialog.show();
        httpUtils.send(HttpRequest.HttpMethod.POST, G.Host.GET_MONEYTABLE,new RequestParams(),new MyRequestCallBack(MoneytableActivity.this,handler,1,new MoneyTableModel()));
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    dialog.dismiss();
                    MoneyTableModel moneyTableModel=(MoneyTableModel)msg.obj;
                    if (moneyTableModel.getRespCode()==0){
                        tv_moneytable_dw.setText(moneyTableModel.getData().getDistanceMin()+"公里以内"+moneyTableModel.getData().getWeightMin()+"公斤以下");
                       int i=Integer.valueOf(moneyTableModel.getData().getDistance());
                        int a=Integer.valueOf(moneyTableModel.getData().getWeight());
                        tv_moneytable_dwm.setText((i+a)+"元");
                        tv_moneytable_d1.setText("每增加"+moneyTableModel.getData().getDistanceSpacing()+"公里");
                        tv_moneytable_dm1.setText("增加"+moneyTableModel.getData().getDistancePrice()+"元");
                        tv_default_w1.setText("每增加"+moneyTableModel.getData().getWeightSpacing()+ "公斤");
                        tv_moneytable_wm1.setText("增加"+moneyTableModel.getData().getWeightPrice()+"元");
                        tv_moneytable_maxw.setText(moneyTableModel.getData().getWeightMax()+"公斤");
                        tv_moneytable_maxd.setText(moneyTableModel.getData().getDistanceMax()+"公里");
                        tv_moneytable_maxww.setVisibility(View.VISIBLE);
                        tv_moneytable_maxdd.setVisibility(View.VISIBLE);

                    }else {
                        ToastUtil.showShort(MoneytableActivity.this,moneyTableModel.getRespMsg());
                    }

                    break;
            }
        }
    };
}
