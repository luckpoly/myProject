package com.brandsh.tiaoshi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.activity.EvaluateDetailActivity;
import com.brandsh.tiaoshi.adapter.StoreEvaluateAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.EvaluateJsonData1;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.widget.SelfPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by libokang on 15/9/2.
 */
public class StoreEvaluateFragment extends BaseFragment {

    @ViewInject(R.id.store_evaluatePTRL)
    private SelfPullToRefreshListView store_evaluatePTRL;
    @ViewInject(R.id.message_list_rlNoItem)
    private RelativeLayout message_list_rlNoItem;
    private HttpUtils httpUtils;
    private HashMap<String,String> requestMap;

    private String page;
    private List<EvaluateJsonData1.DataBean.ListBean> resList;
    private StoreEvaluateAdapter mStoreEvaluateAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_evaluate_fragment, null);
        ViewUtils.inject(this, view);

        initData();
        return view;

    }

    private void initData() {
        page = "1";
        httpUtils = TiaoshiApplication.getGlobalHttpUtils();
        requestMap=new HashMap<>();
        requestMap.put("shopId", getActivity().getIntent().getStringExtra("shop_id"));
        requestMap.put("actReq","123456");
        requestMap.put("actTime",System.currentTimeMillis()/1000+"");
        String sign= SignUtil.getSign(requestMap);
        requestMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.LIST_EVALUATION + "?page=" + page, requestMap, new MyCallBack(1, getActivity(), new EvaluateJsonData1(), handler));
        resList = new LinkedList<>();
        mStoreEvaluateAdapter = new StoreEvaluateAdapter(resList, getActivity());
        store_evaluatePTRL.setAdapter(mStoreEvaluateAdapter);
        setListenerToPTRListView();
    }
    //给列表设置监听
    private void setListenerToPTRListView() {
        store_evaluatePTRL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), EvaluateDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("evaluateDetail", resList.get(position-1));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        store_evaluatePTRL.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = "1";
                OkHttpManager.postAsync(G.Host.LIST_EVALUATION + "?page=" + page, requestMap, new MyCallBack(1, getActivity(), new EvaluateJsonData1(), handler));
                handler.sendEmptyMessageDelayed(150, 5000);
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                OkHttpManager.postAsync(G.Host.LIST_EVALUATION + "?page=" + page, requestMap, new MyCallBack(2, getActivity(), new EvaluateJsonData1(), handler));
                handler.sendEmptyMessageDelayed(150, 5000);
            }
        });
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    EvaluateJsonData1 evaluateJsonData = (EvaluateJsonData1) msg.obj;
                    store_evaluatePTRL.onRefreshComplete();
                    if (evaluateJsonData != null) {
                        if (evaluateJsonData.getRespCode().equals("SUCCESS")) {
                            page = evaluateJsonData.getData().getNextPage() + "";
                            resList.clear();
                            resList.addAll(evaluateJsonData.getData().getList());
                            mStoreEvaluateAdapter.notifyDataSetChanged();
                            if (resList.size() >= Integer.parseInt(evaluateJsonData.getData().getTotalCount())) {
                                store_evaluatePTRL.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//                                showToast("已经是最后一页了");
                            } else {
                                store_evaluatePTRL.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                            if (resList.size()<1){
                                message_list_rlNoItem.setVisibility(View.VISIBLE);
                            }else {
                                message_list_rlNoItem.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(getActivity(), evaluateJsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 2:
                    EvaluateJsonData1 evaluateJsonData1 = (EvaluateJsonData1) msg.obj;
                    store_evaluatePTRL.onRefreshComplete();
                    if (evaluateJsonData1 != null) {
                        if (evaluateJsonData1.getRespCode() .equals("SUCCESS")) {
                            page = evaluateJsonData1.getData().getNextPage() + "";
                            resList.addAll(evaluateJsonData1.getData().getList());
                            mStoreEvaluateAdapter.notifyDataSetChanged();
                            if (resList.size() >= Integer.parseInt(evaluateJsonData1.getData().getTotalCount())) {
                                store_evaluatePTRL.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//                                showToast("已经是最后一页了");
                            } else {
                                store_evaluatePTRL.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                        } else {
                            Toast.makeText(getActivity(), evaluateJsonData1.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 150:
                    store_evaluatePTRL.onRefreshComplete();
                    break;
                case 200:
                    store_evaluatePTRL.onRefreshComplete();
                    break;
                case 300:
                    store_evaluatePTRL.onRefreshComplete();
                    break;
            }
        }
    };
}