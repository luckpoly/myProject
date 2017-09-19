package com.brandsh.tiaoshi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.BalanceAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.ChongzhiModel;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mingle.widget.ShapeLoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */

public class BalanceActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.nav_back)
    ImageView nav_back;
    @ViewInject(R.id.nav_title)
    TextView nav_title;
    @ViewInject(R.id.tv_show_text)
    TextView tv_show_text;
    @ViewInject(R.id.tv_my_money)
    TextView tv_my_money;
    @ViewInject(R.id.tv_go_chongzhi)
    TextView tv_go_chongzhi;
    @ViewInject(R.id.tv_yue_shuoming)
    TextView tv_yue_shuoming;
    @ViewInject(R.id.rg_chongoryao)
    RadioGroup rg_chongoryao;
    @ViewInject(R.id.rv_mingxi)
    RecyclerView rv_mingxi;
    List<ChongzhiModel.DataBean> listData;
    BalanceAdapter adapter;
    //加载动画
    private ShapeLoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        initView();
        initListener();
        getData();

    }
    private void initView(){
        ViewUtils.inject(this);
        AppUtil.Setbar(this);
        nav_title.setText("我的余额");
        loadingDialog = ProgressHUD.show(this, "努力加载中...");
        nav_back.setVisibility(View.VISIBLE);
        rg_chongoryao.check(R.id.rb1);
        listData=new ArrayList();
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        rv_mingxi.setLayoutManager(layoutManager);
         adapter=new BalanceAdapter(this,listData);
        rv_mingxi.setAdapter(adapter);
    }
    private void initListener(){
        nav_back.setOnClickListener(this);
        tv_go_chongzhi.setOnClickListener(this);
        tv_yue_shuoming.setOnClickListener(this);
        rg_chongoryao.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb1:
                        getData();
                        break;
                    case R.id.rb2:
                        getDataTwo();
                        break;
                }
            }
        });
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loadingDialog.dismiss();
            switch (msg.what){
                case 1:
                    ChongzhiModel chongzhiModel= (ChongzhiModel) msg.obj;
                    if ("SUCCESS".equals(chongzhiModel.getRespCode())){
                        if (null!=chongzhiModel.getData()){
                            listData.clear();
                            listData.addAll(chongzhiModel.getData());
                            adapter.setType(1);
                            adapter.notifyDataSetChanged();
                            tv_show_text.setVisibility(View.GONE);
                        }else {
                            listData.clear();
                            adapter.notifyDataSetChanged();
                            tv_show_text.setVisibility(View.VISIBLE);
                        }

                    }
                    break;
                case 2:
                    ChongzhiModel chongzhiModel2= (ChongzhiModel) msg.obj;
                    if ("SUCCESS".equals(chongzhiModel2.getRespCode())){
                        if (null!=chongzhiModel2.getData()){
                            listData.clear();
                            listData.addAll(chongzhiModel2.getData());
                            adapter.setType(2);
                            adapter.notifyDataSetChanged();
                            tv_show_text.setVisibility(View.GONE);
                        }else {
                            listData.clear();
                            adapter.notifyDataSetChanged();
                            tv_show_text.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
            }
        }
    };
    private  void getData(){
        loadingDialog.show();
        HashMap map=new HashMap();
        map.put("token", TiaoshiApplication.globalToken);
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.TOP_UP,map,new MyCallBack(1,this,new ChongzhiModel(),handler));
    }
    private  void getDataTwo(){
        loadingDialog.show();
        HashMap map=new HashMap();
        map.put("token", TiaoshiApplication.globalToken);
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.INVITE,map,new MyCallBack(2,this,new ChongzhiModel(),handler));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TiaoshiApplication.isLogin){
            tv_my_money.setText(TiaoshiApplication.globalUserInfo.getData().getBalance());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_back:
                finish();
                break;
            case R.id.tv_go_chongzhi:
                startActivity(new Intent(this,TopUpActivity.class));
                break;
            case R.id.tv_yue_shuoming:
                startActivity(new Intent(this, AboutUsSMActivity.class).putExtra("type", "余额说明").putExtra("url", "URechargeRules"));
                break;
        }
    }
}
