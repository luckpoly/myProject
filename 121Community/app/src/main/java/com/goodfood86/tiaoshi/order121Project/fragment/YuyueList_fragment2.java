package com.goodfood86.tiaoshi.order121Project.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.YuyueOrderAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.YuyueListModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class YuyueList_fragment2 extends Fragment {
    private View view;
    @ViewInject(R.id.all_order_PTRListView)
    private PullToRefreshListView all_order_PTRListView;
    @ViewInject(R.id.ll_yuyue_bg)
    private LinearLayout ll_yuyue_bg;
    private List<YuyueListModel.DataBean> dataBeen=new ArrayList<>();
    private YuyueOrderAdapter adapter;
    private String type;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.yuyue_list_fragment,null);
        ViewUtils.inject(this,view);
        initView();
        initData();
        return view;
    }
    private void initView(){
       adapter=new YuyueOrderAdapter(dataBeen,getContext());
        all_order_PTRListView.setAdapter(adapter);
    }
    private void initData(){
        HashMap map=new HashMap();
        map.put("token", Order121Application.globalLoginModel.getData().getToken());
        map.put("status", "OK");
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime",System.currentTimeMillis()/1000+"");
        String sign= SignUtil.getSign(map);
        map.put("sign", MD5.getMD5(sign));
        OkHttpManager.postAsync(G.Host.YUYUE_LIST,map,new MyCallBack(1,getContext(),new YuyueListModel(),handler));

    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    YuyueListModel yuyueListModel= (YuyueListModel) msg.obj;
                    if ("SUCCESS".equals(yuyueListModel.getRespCode())&&null!=yuyueListModel.getData()){

                        dataBeen.addAll(yuyueListModel.getData());
                        if (dataBeen.size()>0){
                            ll_yuyue_bg.setVisibility(View.GONE);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };
}
