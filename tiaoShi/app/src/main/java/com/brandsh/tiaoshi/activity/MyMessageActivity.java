package com.brandsh.tiaoshi.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.MyMessageAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.DeleteMsgJsonData;
import com.brandsh.tiaoshi.model.MyMessageListJsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.widget.MySwipeView;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.brandsh.tiaoshi.widget.SelfPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mingle.widget.ShapeLoadingDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;

public class MyMessageActivity extends FragmentActivity implements View.OnClickListener {
    @ViewInject(R.id.my_message_ivBack)
    ImageView my_message_ivBack;
    @ViewInject(R.id.my_message_ptrListView)
    SelfPullToRefreshListView my_message_ptrListView;
    @ViewInject(R.id.message_list_rlNoItem)
    RelativeLayout message_list_rlNoItem;

    private String page;
    private HashMap msgMap;
    private ShapeLoadingDialog loadingDialog;
    private MyMessageAdapter myMessageAdapter;
    private List<MyMessageListJsonData.DataBean.ListBean> resList;
    private MsgBroadcastReceiver msgBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        //沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);

        init();

        setListenerToptrListView();

        registerReceiver();
    }

    private void registerReceiver() {
        msgBroadcastReceiver = new MsgBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("updateMsgList");
        registerReceiver(msgBroadcastReceiver, intentFilter);
    }

    private class MsgBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("updateMsgList".equals(intent.getAction())){
                page = "1";
                OkHttpManager.postAsync(G.Host.MY_MESSAGE + "?page=" + page, msgMap, new MyCallBack(1, MyMessageActivity.this, new MyMessageListJsonData(), handler));
            }
        }
    }

    private void init() {
        page = "1";
       msgMap=new HashMap();
        msgMap.put("token", TiaoshiApplication.globalToken);
        msgMap.put("actReq", SignUtil.getRandom());
        msgMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        msgMap.put("sign", Md5.toMd5(SignUtil.getSign(msgMap)));
        OkHttpManager.postAsync(G.Host.MY_MESSAGE + "?page=" + page, msgMap, new MyCallBack(1, this, new MyMessageListJsonData(), handler));

        loadingDialog = ProgressHUD.show(this, "努力加载中...");
        loadingDialog.show();


        my_message_ivBack.setOnClickListener(this);
        my_message_ptrListView.setMode(PullToRefreshBase.Mode.BOTH);

        my_message_ptrListView.setVisibility(View.GONE);
    }

    private void setListenerToptrListView() {
        my_message_ptrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyMessageActivity.this, MessageDetailActivity.class);
                intent.putExtra("msg_id", resList.get(position).getMsgId());
                intent.putExtra("msg_type", resList.get(position).getType());
                startActivity(intent);
            }
        });

        my_message_ptrListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                for (int i = 0; i < MySwipeView.unClosedSwipeViews.size(); i++) {//滑动的时候把所有已经打开的关闭掉
                    MySwipeView.unClosedSwipeViews.get(i).quickClose();
                }
            }
        });

        my_message_ptrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = "1";
                OkHttpManager.postAsync(G.Host.MY_MESSAGE + "?page=" + page, msgMap, new MyCallBack(1, MyMessageActivity.this, new MyMessageListJsonData(), handler));
                handler.sendEmptyMessageDelayed(150, 5000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                OkHttpManager.postAsync(G.Host.MY_MESSAGE + "?page=" + page, msgMap, new MyCallBack(2, MyMessageActivity.this, new MyMessageListJsonData(), handler));
                handler.sendEmptyMessageDelayed(150, 5000);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_message_ivBack:
                finish();
                break;
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:

                        loadingDialog.dismiss();

                    my_message_ptrListView.onRefreshComplete();
                    MyMessageListJsonData myMessageListJsonData = (MyMessageListJsonData) msg.obj;
                    if (myMessageListJsonData != null) {
                        if ("SUCCESS".equals(myMessageListJsonData.getRespCode())){
                            page = myMessageListJsonData.getData().getNextPage()+"";
                            resList = myMessageListJsonData.getData().getList();
                            if (resList.size() == 0){
                                message_list_rlNoItem.setVisibility(View.VISIBLE);
                                my_message_ptrListView.setVisibility(View.GONE);
                            }else {
                                message_list_rlNoItem.setVisibility(View.GONE);
                                my_message_ptrListView.setVisibility(View.VISIBLE);
                                myMessageAdapter = new MyMessageAdapter(resList, MyMessageActivity.this, my_message_ptrListView, handler);
                                my_message_ptrListView.setAdapter(myMessageAdapter);
                                if (resList.size()<Integer.parseInt(myMessageListJsonData.getData().getTotalCount())){
                                    my_message_ptrListView.setMode(PullToRefreshBase.Mode.BOTH);
                                }else{
                                    my_message_ptrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                }
                                myMessageAdapter.notifyDataSetChanged();
                            }
                        }else {
                            Toast.makeText(MyMessageActivity.this, myMessageListJsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 2:
                    my_message_ptrListView.onRefreshComplete();
                    MyMessageListJsonData myMessageListJsonData1 = (MyMessageListJsonData) msg.obj;
                    if (myMessageListJsonData1 != null) {
                        if ("SUCCESS".equals(myMessageListJsonData1.getRespCode())){
                            page = myMessageListJsonData1.getData().getNextPage()+"";
                            resList.addAll(myMessageListJsonData1.getData().getList());
                            if (resList.size()<Integer.parseInt(myMessageListJsonData1.getData().getTotalCount())){
                                my_message_ptrListView.setMode(PullToRefreshBase.Mode.BOTH);
                            }else{
                                my_message_ptrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                            myMessageAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(MyMessageActivity.this, myMessageListJsonData1.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 3:
                    DeleteMsgJsonData deleteMsgJsonData = (DeleteMsgJsonData) msg.obj;
                    if (deleteMsgJsonData != null) {
                        if ("SUCCESS".equals(deleteMsgJsonData.getRespCode())){
                            Toast.makeText(MyMessageActivity.this, "消息删除成功", Toast.LENGTH_SHORT).show();
                            page = "1";
                            OkHttpManager.postAsync(G.Host.MY_MESSAGE + "?page=" + page, msgMap, new MyCallBack(1,MyMessageActivity.this, new MyMessageListJsonData(), handler));
                            loadingDialog.show();
                        }
                    }
                    break;
                case 150:
                    my_message_ptrListView.onRefreshComplete();
                    break;
                case 200:
                    my_message_ptrListView.onRefreshComplete();
                    break;
                case 300:
                    my_message_ptrListView.onRefreshComplete();
                    break;
            }
        }
    };
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
