package com.goodfood86.tiaoshi.order121Project.rongyun;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.model.GroupUserQueryModel;
import com.goodfood86.tiaoshi.order121Project.utils.RongHttp;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public class GroupDetailActivity extends Activity{
    @ViewInject(R.id.nav_title)
    TextView nav_title;
    @ViewInject(R.id.tv_group_name)
    TextView tv_group_name;
    @ViewInject(R.id.tv_group_id)
    TextView tv_group_id;
    @ViewInject(R.id.tv_renNo)
    TextView tv_renNo;
    @ViewInject(R.id.nav_back)
    ImageView nav_back;


    @ViewInject(R.id.gv_group)
    GridView gv_group;
    private  String mTargetId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);
        ViewUtils.inject(this);
        initView();
        initData();

    }
    private void initView(){
        mTargetId=getIntent().getStringExtra("TargetId");
        tv_group_id.setText(mTargetId);
        tv_group_name.setText(getIntent().getStringExtra("GroupName"));
        nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initData(){
        if (!TextUtils.isEmpty(mTargetId)){
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    List<NameValuePair> nameValuePair = new ArrayList<>();
                    nameValuePair.add(new BasicNameValuePair("groupId",mTargetId));
                    RongHttp.rPostHttp("group/user/query.json",nameValuePair,new GroupUserQueryModel(),handler,1,GroupDetailActivity.this);
                }
            }.start();
        }
    }
    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    GroupUserQueryModel model= (GroupUserQueryModel) msg.obj;
                    if (model.getCode()==200){
                        GridAdapter adapter=new GridAdapter(model.getUsers(),GroupDetailActivity.this);
                        gv_group.setAdapter(adapter);
                        tv_renNo.setText(model.getUsers().size()+"/3000");
                    }
                    break;
            }
        }
    };
    private class GridAdapter extends BaseAdapter {
        private List<GroupUserQueryModel.UsersBean> list;
        private Context context;
        public GridAdapter(List<GroupUserQueryModel.UsersBean> list, Context context) {
            this.list = list;
            this.context = context;
        }
        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public Object getItem(int position) {
            return list.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           if (convertView==null){
               convertView= LayoutInflater.from(context).inflate(R.layout.group_user_item, null);
           }
           ImageView iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            TextView tv_username = (TextView) convertView.findViewById(R.id.tv_username);
            String id=list.get(position).getId();
            if (id.length()>7){
                tv_username.setText(id.substring(0,3)+"****"+id.substring(id.length()-4,id.length()));
            }else {
                tv_username.setText(list.get(position).getId());
            }
            return convertView;
        }
    }
}
