package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.MsgListAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.GlobalLoginModel;
import com.goodfood86.tiaoshi.order121Project.model.MsgListModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.widget.ProgressHUD;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.internal.Utils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiashiwang on 2016/4/5.
 */
public class MsglistActivity extends Activity {
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.plv_msglist)
    private PullToRefreshListView plv_msglist;
    private RequestParams data;
    private HttpUtils httpUtils;
    MsgListAdapter adapter;
    private List<MsgListModel.DataBean.ListBean> list=new ArrayList<>();
    private ProgressHUD dialog;
    private CommonDialog commonDialog;
    private int i=-1;
    private int a=-1;
    private Boolean isRefresh=false;
    private int page=1;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    dialog.dismiss();
                    plv_msglist.onRefreshComplete();
                    MsgListModel msgListModel= (MsgListModel) msg.obj;
                    if (msgListModel.getRespCode()==0){
                        List<MsgListModel.DataBean.ListBean>  list1= msgListModel.getData().getList();
                        if (isRefresh){
                            list.clear();
                            isRefresh=false;
                        }
                        list.addAll(list1);
                        adapter.notifyDataSetChanged();

                }

                    break;
                case 2:
                    dialog.dismiss();
                    inputdata(2);

                    break;
                case 3:

                    inputdata(3);
                    break;
            }
        }
    };
    private  void inputdata(int i){
        Intent intent = new Intent(MsglistActivity.this, MsgdateilsActivaty.class);
        intent.putExtra("time", list.get(a).getDateTime());
        intent.putExtra("content", list.get(a).getContent());
        intent.putExtra("name",list.get(a).getSubject());
        if (i==2){
            startActivityForResult(intent,0);
        }else if (i==3){
            startActivity(intent);
        }
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msglist);
        ViewUtils.inject(this);
        initView();
    }
    public void initView(){
        dialog= ProgressHUD.show(this, "玩命加载中...", false,null);
        httpUtils= Order121Application.getGlobalHttpUtils();
        nav_title.setText("消息中心");
        nav_back.setVisibility(View.VISIBLE);
        nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        plv_msglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                a = (int) id;
                if (list.get(a).getStatus()!=1){
                    RequestParams data1 = new RequestParams();
                    data1.addBodyParameter("token", Order121Application.globalLoginModel.getData().getToken());
                    int a = list.get((int) id).getId();
                    data1.addBodyParameter("noticeId", a + "");
                    httpUtils.send(HttpRequest.HttpMethod.POST, G.Host.GET_MSGMSG, data1, new MyRequestCallBack(MsglistActivity.this, handler, 2, new MsgListModel()));
                    dialog.show();
                }else {
                    handler.sendEmptyMessage(3);
                }

            }
        });
        plv_msglist.getRefreshableView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                i = (int) id;
                commonDialog = CommonDialog.show(MsglistActivity.this, "确定要删除此消息？", true, "");
                commonDialog.show();
                return true;
            }
        });
        plv_msglist.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                setRefreshDate(refreshView);
                isRefresh = true;
                httpsend(page);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                setRefreshDate(refreshView);
                i++;
                isRefresh=false;
                httpsend(i);
            }
        });

         adapter=new MsgListAdapter(list,MsglistActivity.this);
        plv_msglist.setAdapter(adapter);


        httpsend(1);
    }
    private void setRefreshDate(PullToRefreshBase<ListView> refreshView) {
        //放置在刷新时的监听中
        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("上次刷新 " + DateUtils.formatDateTime(
                this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL));
    }
    private void httpsend(int i) {
        dialog.show();
        data=new RequestParams();
        data.addBodyParameter("token", Order121Application.globalLoginModel.getData().getToken());
        data.addBodyParameter("Page",i+"");
        httpUtils.send(HttpRequest.HttpMethod.POST, G.Host.GET_MSGLIST, data, new MyRequestCallBack(MsglistActivity.this, handler, 1, new MsgListModel()));

    }
    public void deletemsg(){
        dialog.show();
        RequestParams data2 = new RequestParams();
        data2.addBodyParameter("token", Order121Application.globalLoginModel.getData().getToken());
        data2.addBodyParameter("noticeId", list.get(i).getId()+"");
        httpUtils.send(HttpRequest.HttpMethod.POST, G.Host.DELETE_MSGLIST, data2, new MyRequestCallBack(MsglistActivity.this, handler, 2, new MsgListModel()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0){
            isRefresh=true;
            httpsend(1);
        }
    }
}

/**
 * 删除提示框
 */
class CommonDialog extends Dialog {
    private Context context;
    public CommonDialog(Context context) {
        super(context);
        this.context=context;
    }
    public CommonDialog(Context context, int theme) {
        super(context, theme);
        this.context=context;
    }
    public static CommonDialog show(final MsglistActivity context, CharSequence message,
                                    boolean cancelable, CharSequence title) {
        final CommonDialog dialog = new CommonDialog(context, R.style.ProgressHUD);
        dialog.setTitle("");
        dialog.setContentView(R.layout.common_dialog);
        if (message == null || message.length() == 0) {
            dialog.findViewById(R.id.content).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) dialog.findViewById(R.id.tv_info);
            txt.setText(message);
        }
        if (title == null || title.length() == 0) {
            dialog.findViewById(R.id.li_title).setVisibility(View.GONE);
            dialog.findViewById(R.id.content).setBackgroundResource(R.drawable.bg_title_custom_dialog);
        } else {
            TextView txt = (TextView) dialog.findViewById(R.id.tv_title_show);
            txt.setText(title);
        }
        Button btn_sure= (Button) dialog.findViewById(R.id.sure_perfect_btn);
        Button btn_cancel= (Button) dialog.findViewById(R.id.cancel_perfect_btn);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.deletemsg();
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
//         dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//         dialog.show();
        return dialog;
    }


}

