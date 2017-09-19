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
import com.goodfood86.tiaoshi.order121Project.adapter.PartnersGridviewAdapter;
import com.goodfood86.tiaoshi.order121Project.adapter.ZixunGridviewAdapter;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.PubDocModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class ShowZixunListActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.gv_group_partners)
    GridView gv_group_partners;
    @ViewInject(R.id.nav_title)
    TextView nav_title;
    @ViewInject(R.id.nav_back)
    ImageView nav_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_zixunlist);
        ViewUtils.inject(this);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        nav_title.setText("合作伙伴");
    }

    private void initListener() {
        nav_back.setOnClickListener(this);
    }

    private void initData() {
        //获取合作伙伴
        HashMap map2 = new HashMap();
        map2.put("code", "Partner");
        map2.put("actReq", SignUtil.getRandom());
        map2.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign2 = SignUtil.getSign(map2);
        map2.put("sign", MD5.getMD5(sign2));
        OkHttpManager.postAsync(G.Host.PUB_DOC, map2, new MyCallBack(1, this, new PubDocModel(), handler));
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    PubDocModel pubDocModel2 = (PubDocModel) msg.obj;
                    if ("SUCCESS".equals(pubDocModel2.getRespCode()) && null != pubDocModel2.getData()) {
                        final List<PubDocModel.DataBean.NodesBean> list = new ArrayList();
                        list.addAll(pubDocModel2.getData().getNodes());
                        PartnersGridviewAdapter adapter = new PartnersGridviewAdapter(ShowZixunListActivity.this, list);
                        gv_group_partners.setAdapter(adapter);
                        gv_group_partners.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                startActivity(new Intent(ShowZixunListActivity.this, ZixunDetailActivity.class)
                                        .putExtra("name", list.get(position).getName())
                                        .putExtra("id", list.get(position).getId()));
                            }
                        });
                    }
                    break;

            }
        }
    };

    @Override
    public void onClick(View v) {
        finish();
    }
}
