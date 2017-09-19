package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.MyLlqHardImgAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.CreateActivityModel;
import com.goodfood86.tiaoshi.order121Project.model.LlqDetailsModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/8/8.
 */
public class MyActivityDateilsActivity extends Activity implements View.OnClickListener{
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.iv_titleimg)
    private ImageView iv_titleimg;
    @ViewInject(R.id.tv_peopleNo)
    private TextView tv_peopleNo;
    @ViewInject(R.id.tv_canyurenyuan)
    private TextView tv_canyurenyuan;
    @ViewInject(R.id.tv_mainpeople)
    private TextView tv_mainpeople;
    @ViewInject(R.id.tv_line)
    private TextView tv_line;
    @ViewInject(R.id.rv_people_head)
    private RecyclerView rv_people_head;
    private LlqDetailsModel llqDetailsModel;
    private LinearLayoutManager mLayoutManager;
    private MyLlqHardImgAdapter llqHardImgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myllqdateils);
        initView();
        initData();
    }

    private void initView() {
        ViewUtils.inject(this);
        nav_title.setText("活动详情");
        nav_back.setOnClickListener(this);

    }
    private void initData() {
        HashMap map = new HashMap();
        map.put("customActivityId", getIntent().getStringExtra("id"));
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", MD5.getMD5(sign));
        OkHttpManager.postAsync(G.Host.ACTIVITY_DETAIL, map, new MyCallBack(1, MyActivityDateilsActivity.this, new LlqDetailsModel(), handler));

    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                llqDetailsModel = (LlqDetailsModel) msg.obj;
                    if ("SUCCESS".equals(llqDetailsModel.getRespCode()) && null != llqDetailsModel.getData()) {
                        String money=llqDetailsModel.getData().getCapita();
                        if (TextUtils.isEmpty(money)||money.equals("0")){
                            money="0";
                        }else {
                            money=money.substring(0,money.length()-2);
                        }
                        tv_title.setText(llqDetailsModel.getData().getContent()+",人均"+money+"元");
                        tv_peopleNo.setText("报名人数：" + llqDetailsModel.getData().getApplyCount() + "人");
                        tv_mainpeople.setText("组织人："+llqDetailsModel.getData().getOmName());
                        if (!TextUtils.isEmpty(llqDetailsModel.getData().getImgs())) {
                           String[] imgs= llqDetailsModel.getData().getImgs().split(",");
                            Order121Application.getGlobalBitmapUtils().display(iv_titleimg,imgs[0]);
                        }
                        if (llqDetailsModel.getData().getApplay().size()==0){
                            rv_people_head.setVisibility(View.GONE);
                            tv_canyurenyuan.setVisibility(View.GONE);
                            tv_line.setVisibility(View.GONE);
                        }else {
                            //设置水平布局
                            mLayoutManager = new LinearLayoutManager(MyActivityDateilsActivity.this);
                            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            rv_people_head.setLayoutManager(mLayoutManager);
                            llqHardImgAdapter=new MyLlqHardImgAdapter(MyActivityDateilsActivity.this,llqDetailsModel.getData().getApplay(),getIntent().getStringExtra("type"));
                            rv_people_head.setAdapter(llqHardImgAdapter);
                            rv_people_head.setFocusable(false);
                        }
                    }
                    break;
                case 2:
                    CreateActivityModel createActivityModel= (CreateActivityModel) msg.obj;
                    if ("SUCCESS".equals(createActivityModel.getRespCode())){
                        ToastUtil.showShort(MyActivityDateilsActivity.this,"报名成功");
                    }else {
                        ToastUtil.showShort(MyActivityDateilsActivity.this,createActivityModel.getRespMsg());
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
        }
    }
}
