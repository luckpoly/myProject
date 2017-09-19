package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.JiazhengGridviewAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.ModuleMainModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/8/8.
 */
public class JiazhengActivity extends Activity  {
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.gv_qingjie)
    private GridView gv_qingjie;
    @ViewInject(R.id.gv_baomu)
    private GridView gv_baomu;
    @ViewInject(R.id.gv_shenghuo)
    private GridView gv_shenghuo;
    @ViewInject(R.id.gv_jiaju)
    private GridView gv_jiaju;
    @ViewInject(R.id.gv_qita)
    private GridView gv_qita;
    @ViewInject(R.id.iv_jiazheng_img)
    private ImageView iv_jiazheng_img;
    private  ModuleMainModel moduleMainModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiazheng);
        initView();
        initDate();
        initListener();
    }
    private void initView(){
        ViewUtils.inject(this);
        nav_title.setText("家政服务");
        nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private  void initDate(){
        HashMap map=new HashMap();
        map.put("code","Housekeeping");
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime",System.currentTimeMillis()/1000+"");
        String sign= SignUtil.getSign(map);
        map.put("sign", MD5.getMD5(sign));
        OkHttpManager.postAsync(G.Host.MODULE_CATEGORY,map,new MyCallBack(2,JiazhengActivity.this, new ModuleMainModel(),handler));
    }
    private void initListener(){
        gv_qingjie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(JiazhengActivity.this,YuyueActivity.class);
                intent.putExtra("moduleCategoryId",moduleMainModel.getData().getNodes().get(0).getNodes().get(position).getId());
                intent.putExtra("typeName",moduleMainModel.getData().getNodes().get(0).getNodes().get(position).getTitle());
                intent.putExtra("code",moduleMainModel.getData().getNodes().get(0).getNodes().get(position).getCode());
                startActivity(intent);
            }
        });
        gv_baomu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(JiazhengActivity.this,YuyueActivity.class);
                intent.putExtra("moduleCategoryId",moduleMainModel.getData().getNodes().get(1).getNodes().get(position).getId());
                intent.putExtra("typeName",moduleMainModel.getData().getNodes().get(1).getNodes().get(position).getTitle());
                intent.putExtra("code",moduleMainModel.getData().getNodes().get(1).getNodes().get(position).getCode());
                startActivity(intent);
            }
        });
        gv_shenghuo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(JiazhengActivity.this,YuyueActivity.class);
                intent.putExtra("moduleCategoryId",moduleMainModel.getData().getNodes().get(2).getNodes().get(position).getId());
                intent.putExtra("typeName",moduleMainModel.getData().getNodes().get(2).getNodes().get(position).getTitle());
                intent.putExtra("code",moduleMainModel.getData().getNodes().get(2).getNodes().get(position).getCode());
                startActivity(intent);
            }
        });
        gv_jiaju.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(JiazhengActivity.this,YuyueActivity.class);
                intent.putExtra("moduleCategoryId",moduleMainModel.getData().getNodes().get(3).getNodes().get(position).getId());
                intent.putExtra("typeName",moduleMainModel.getData().getNodes().get(3).getNodes().get(position).getTitle());
                intent.putExtra("code",moduleMainModel.getData().getNodes().get(3).getNodes().get(position).getCode());
                startActivity(intent);
            }
        });
        gv_qita.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(JiazhengActivity.this,YuyueActivity.class);
                intent.putExtra("moduleCategoryId",moduleMainModel.getData().getNodes().get(4).getNodes().get(position).getId());
                intent.putExtra("typeName",moduleMainModel.getData().getNodes().get(4).getNodes().get(position).getTitle());
                intent.putExtra("code",moduleMainModel.getData().getNodes().get(4).getNodes().get(position).getCode());
                startActivity(intent);
            }
        });
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    break;
                case 2:
                     moduleMainModel= (ModuleMainModel) msg.obj;
                    if ("SUCCESS".equals(moduleMainModel.getRespCode())&&null!=moduleMainModel.getData()){
                       Order121Application.getGlobalBitmapUtilsBg().display(iv_jiazheng_img,moduleMainModel.getData().getSubBanner());
                        JiazhengGridviewAdapter adapter=new JiazhengGridviewAdapter(JiazhengActivity.this,moduleMainModel.getData().getNodes().get(0).getNodes());
                        gv_qingjie.setAdapter(adapter);
                        JiazhengGridviewAdapter adapter1=new JiazhengGridviewAdapter(JiazhengActivity.this,moduleMainModel.getData().getNodes().get(1).getNodes());
                        gv_baomu.setAdapter(adapter1);
                        JiazhengGridviewAdapter adapter2=new JiazhengGridviewAdapter(JiazhengActivity.this,moduleMainModel.getData().getNodes().get(2).getNodes());
                        gv_shenghuo.setAdapter(adapter2);
                        JiazhengGridviewAdapter adapter3=new JiazhengGridviewAdapter(JiazhengActivity.this,moduleMainModel.getData().getNodes().get(3).getNodes());
                        gv_jiaju.setAdapter(adapter3);
                        JiazhengGridviewAdapter adapter4=new JiazhengGridviewAdapter(JiazhengActivity.this,moduleMainModel.getData().getNodes().get(4).getNodes());
                        gv_qita.setAdapter(adapter4);
                    }
                    break;
            }
        }
    };

}
