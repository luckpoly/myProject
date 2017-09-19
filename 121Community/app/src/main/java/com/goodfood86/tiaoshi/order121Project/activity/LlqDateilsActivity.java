package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.LlqHardImgAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.CreateActivityModel;
import com.goodfood86.tiaoshi.order121Project.model.GroupUserQueryModel;
import com.goodfood86.tiaoshi.order121Project.model.LlqDetailsModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.DateFormatUtil;
import com.goodfood86.tiaoshi.order121Project.utils.DensityUtil;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.RongHttp;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2016/8/8.
 */
public class LlqDateilsActivity extends Activity implements View.OnClickListener{
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
    @ViewInject(R.id.tv_time)
    private TextView tv_time;
    @ViewInject(R.id.tv_address)
    private TextView tv_address;
    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;
    @ViewInject(R.id.into_group)
    private TextView into_group;
    @ViewInject(R.id.tv_baoming)
    private TextView tv_baoming;
    @ViewInject(R.id.tv_title_name)
    private TextView tv_title_name;
    @ViewInject(R.id.tv_mainpeople)
    private TextView tv_mainpeople;
    @ViewInject(R.id.rv_people_head)
    private RecyclerView rv_people_head;
    @ViewInject(R.id.comment_pic)
    private LinearLayout comment_pic;
    @ViewInject(R.id.rl_bottom)
    private RelativeLayout rl_bottom;
    private LlqDetailsModel llqDetailsModel;
    private LinearLayoutManager mLayoutManager;
    private LlqHardImgAdapter llqHardImgAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llqdateils);
        initView();
        initData();
    }

    private void initView() {
        ViewUtils.inject(this);
        tv_baoming.setOnClickListener(this);
        nav_title.setText("活动详情");
        nav_back.setOnClickListener(this);
        into_group.setOnClickListener(this);

    }

    private void initData() {
        HashMap map = new HashMap();
        map.put("customActivityId", getIntent().getStringExtra("id"));
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", MD5.getMD5(sign));
        OkHttpManager.postAsync(G.Host.ACTIVITY_DETAIL, map, new MyCallBack(1, LlqDateilsActivity.this, new LlqDetailsModel(), handler));

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
                        tv_title_name.setText(llqDetailsModel.getData().getName());
                        tv_title.setText(llqDetailsModel.getData().getContent());
                        tv_peopleNo.setText(llqDetailsModel.getData().getApplyCount() + "人");
                        tv_mainpeople.setText(llqDetailsModel.getData().getOmName());
                        tv_time.setText(DateFormatUtil.formatDateNohh(llqDetailsModel.getData().getApplyTime()));
                       tv_address.setText(money+"元");
                        tv_phone.setText(llqDetailsModel.getData().getOmPhone());
                        if ("YES".equals(llqDetailsModel.getData().getIsApply())){
                            into_group.setVisibility(View.GONE);
                            rl_bottom.setVisibility(View.VISIBLE);
                        }else {
                            rl_bottom.setVisibility(View.GONE);
                        }
                        if (!TextUtils.isEmpty(llqDetailsModel.getData().getImgs())) {
                            String[] pics = llqDetailsModel.getData().getImgs().split(",");
                            Order121Application.getActivityBitmapUtils().display(iv_titleimg,pics[0]);
                            comment_pic.removeAllViews();
                            for (int i = 0; i < pics.length; i++) {
                                ImageView img = new ImageView(LlqDateilsActivity.this);
                                img.setScaleType(ImageView.ScaleType.FIT_XY);
                                img.setAdjustViewBounds(true);
                                Log.e("pic", pics[0]);
                                int width = (int) ((DensityUtil.getWidthInPx(LlqDateilsActivity.this)));
                                img.setMaxWidth(width);
                                img.setMaxHeight(width*10);
                                Order121Application.getActivityBitmapUtils().display(img,pics[i]);
                                if (pics[i].length()>10) {
                                    comment_pic.addView(img);
                                }
                            }
                        }
                        if (llqDetailsModel.getData().getApplay().size()==0){
                            rv_people_head.setVisibility(View.GONE);
                        }else {
                            //设置水平布局
                            mLayoutManager = new LinearLayoutManager(LlqDateilsActivity.this);
                            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            rv_people_head.setLayoutManager(mLayoutManager);
                            llqHardImgAdapter=new LlqHardImgAdapter(llqDetailsModel.getData().getApplay());
                            rv_people_head.setAdapter(llqHardImgAdapter);
                        }
                    }
                    break;
                case 2:
                    CreateActivityModel createActivityModel= (CreateActivityModel) msg.obj;
                    if ("SUCCESS".equals(createActivityModel.getRespCode())){
                        ToastUtil.showShort(LlqDateilsActivity.this,"报名成功");
                    }else {
                        ToastUtil.showShort(LlqDateilsActivity.this,createActivityModel.getRespMsg());
                    }
                    break;
                case 4:
                    GroupUserQueryModel model= (GroupUserQueryModel) msg.obj;
                    if (model.getCode()==200){
                        Log.e("-----","加群成功");
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
            case R.id.tv_baoming:
                HashMap map =new HashMap();
                map.put("token",Order121Application.globalLoginModel.getData().getToken());
                map.put("customActivityId",getIntent().getStringExtra("id"));
                map.put("actReq",SignUtil.getRandom());
                map.put("actTime",System.currentTimeMillis()/1000+"");
                String sign= SignUtil.getSign(map);
                map.put("sign", MD5.getMD5(sign));
                OkHttpManager.postAsync(G.Host.BAOMING_ACTIVITY,map,new MyCallBack(2,this,new CreateActivityModel(),handler));
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        List<NameValuePair> nameValuePair = new ArrayList<>();
                        nameValuePair.add(new BasicNameValuePair("groupId",getIntent().getStringExtra("id")));
                        nameValuePair.add(new BasicNameValuePair("userId",Order121Application.globalLoginModel.getData().getPhone()));
                        nameValuePair.add(new BasicNameValuePair("groupName",llqDetailsModel.getData().getName()));
                        RongHttp.rPostHttp("group/create.json",nameValuePair,new GroupUserQueryModel(),handler,4,LlqDateilsActivity.this);
                    }
                }.start();
                break;
            case R.id.into_group:
                RongIM.getInstance().startGroupChat(LlqDateilsActivity.this, getIntent().getStringExtra("id"),llqDetailsModel.getData().getName());
                break;
        }
    }


}
