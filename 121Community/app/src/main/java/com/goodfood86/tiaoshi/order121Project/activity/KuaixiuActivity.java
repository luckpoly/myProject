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
import com.goodfood86.tiaoshi.order121Project.adapter.kuaixiuDownGridviewAdapter;
import com.goodfood86.tiaoshi.order121Project.adapter.kuaixiuGridviewAdapter;
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
import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class KuaixiuActivity extends Activity implements View.OnClickListener{
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.gv_kuaixiu_up)
    private GridView gv_kuaixiu_up;
    @ViewInject(R.id.gv_kuaixiu_down)
    private GridView gv_kuaixiu_down;
    @ViewInject(R.id.iv_kuaixiu_im)
    private ImageView iv_kuaixiu_im;
    String code="Maintain";
    ModuleMainModel moduleMainModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuaixiu);
        initView();
    }
    private void initView(){
        ViewUtils.inject(this);
        nav_back.setOnClickListener(this);
        if (getIntent().getStringExtra("type").equals("kuaixiu")){
            nav_title.setText("快修服务");
            code="Maintain";
        }else {
            nav_title.setText("便捷洗衣");
            code="Laundry";
        }
        initDate();
        initListener();
    }
    private  void initDate(){
        HashMap map=new HashMap();
        map.put("code",code);
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime",System.currentTimeMillis()/1000+"");
        String sign= SignUtil.getSign(map);
        map.put("sign", MD5.getMD5(sign));
        OkHttpManager.postAsync(G.Host.MODULE_CATEGORY,map,new MyCallBack(1,KuaixiuActivity.this, new ModuleMainModel(),handler));

    }
    private void initListener(){
        gv_kuaixiu_up.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(KuaixiuActivity.this,YuyueActivity.class);
                intent.putExtra("moduleCategoryId",moduleMainModel.getData().getNodes().get(position).getId());
                intent.putExtra("typeName",moduleMainModel.getData().getNodes().get(position).getTitle());
                intent.putExtra("code",moduleMainModel.getData().getNodes().get(position).getCode());
                startActivity(intent);
            }
        });
        gv_kuaixiu_down.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(KuaixiuActivity.this,YuyueActivity.class);
                intent.putExtra("moduleCategoryId",moduleMainModel.getData().getNodes().get(position+2).getId());
                intent.putExtra("typeName",moduleMainModel.getData().getNodes().get(position+2).getTitle());
                intent.putExtra("code",moduleMainModel.getData().getNodes().get(position+2).getCode());
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
                    moduleMainModel= (ModuleMainModel) msg.obj;
                    if ("SUCCESS".equals(moduleMainModel.getRespCode())&&null!=moduleMainModel.getData()){
                        Order121Application.getGlobalBitmapUtilsBg().display(iv_kuaixiu_im,moduleMainModel.getData().getSubBanner());
                        kuaixiuGridviewAdapter adapter=new kuaixiuGridviewAdapter(KuaixiuActivity.this,moduleMainModel.getData().getNodes());
                        gv_kuaixiu_up.setAdapter(adapter);
                        List<ModuleMainModel.DataBean.NodesBean>  nodesBeanTs=moduleMainModel.getData().getNodes();
                        kuaixiuDownGridviewAdapter adapter1=new kuaixiuDownGridviewAdapter(KuaixiuActivity.this,nodesBeanTs);
                        gv_kuaixiu_down.setAdapter(adapter1);

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
