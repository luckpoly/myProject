package com.goodfood86.tiaoshi.order121Project.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.activity.AddressManagerActivity;
import com.goodfood86.tiaoshi.order121Project.activity.AdviceActivity;
import com.goodfood86.tiaoshi.order121Project.activity.AppointmentActivity;
import com.goodfood86.tiaoshi.order121Project.activity.FriendsQuanActivity;
import com.goodfood86.tiaoshi.order121Project.activity.LoginActivity;
import com.goodfood86.tiaoshi.order121Project.activity.MyZixunListActivity;
import com.goodfood86.tiaoshi.order121Project.activity.OrderManagerActivity;
import com.goodfood86.tiaoshi.order121Project.activity.PersonalSettingActivity;
import com.goodfood86.tiaoshi.order121Project.activity.PublishedActivity;
import com.goodfood86.tiaoshi.order121Project.activity.SettingActivity;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.GlobalLoginModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.widget.CircleImageView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/3/24.
 */
@SuppressLint("ValidFragment")
public class SlidingFragment extends Fragment implements View.OnClickListener {
    @ViewInject(R.id.circleImageView)
    private CircleImageView circleImageView;
    @ViewInject(R.id.tv_user_phone)
    private TextView tv_user_phone;
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.rl_myappointment)
    private RelativeLayout rl_myappointment;
    @ViewInject(R.id.ordermanager)
    private RelativeLayout ordermanager;
    @ViewInject(R.id.ll_to_pay)
    private LinearLayout ll_to_pay;
    @ViewInject(R.id.ll_to_get_order)
    private LinearLayout ll_to_get_order;
    @ViewInject(R.id.ll_to_evaluate)
    private LinearLayout ll_to_evaluate;
    @ViewInject(R.id.rl_myActivity)
    private RelativeLayout rl_myActivity;
    @ViewInject(R.id.rl_myFriend)
    private RelativeLayout rl_myFriend;
    @ViewInject(R.id.rl_myZiXun)
    private RelativeLayout rl_myZiXun;
    @ViewInject(R.id.addressManage)
    private RelativeLayout addressManage;
    @ViewInject(R.id.rl_fankui)
    private RelativeLayout rl_fankui;
    @ViewInject(R.id.rl_setting)
    private RelativeLayout rl_setting;
    @ViewInject(R.id.ll_gosetting)
    private LinearLayout ll_gosetting;
    private static int ISLOGIN = 1;
    private Context mContext;
    private View view;
    private MyFragmentBroadcastReciver myFragmentBroadcastReciver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_sliding, container, false);
            ViewUtils.inject(this, view);
            initListener();
            initData();
        } else {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
        }
        registerReceiver();
        return view;
    }

    private void initData() {
        if (Order121Application.isLogin()) {
            login();
        }
    }

    private void registerReceiver() {
        myFragmentBroadcastReciver = new MyFragmentBroadcastReciver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("updateImg");
        intentFilter.addAction("updateSlidingFragment");
        intentFilter.addAction("updateName");
        intentFilter.addAction("logOut");
        getActivity().registerReceiver(myFragmentBroadcastReciver, intentFilter);
    }

    public class MyFragmentBroadcastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("updateImg".equals(intent.getAction())) {
                login();
            } else if ("updateSlidingFragment".equals(intent.getAction())) {
                login();
            } else if ("updateName".equals(intent.getAction())) {
                login();
            }else  if ("logOut".equals(intent.getAction())){
                circleImageView.setImageResource(R.mipmap.belle);
                tv_user_phone.setText("未登录");
            }
        }
    }

    private void initListener() {
        nav_title.setText("我的");
        nav_back.setVisibility(View.GONE);
        rl_myappointment.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        rl_fankui.setOnClickListener(this);
        rl_myActivity.setOnClickListener(this);
        addressManage.setOnClickListener(this);
        ll_gosetting.setOnClickListener(this);
        ordermanager.setOnClickListener(this);
        ll_to_pay.setOnClickListener(this);
        ll_to_get_order.setOnClickListener(this);
        ll_to_evaluate.setOnClickListener(this);
        rl_myFriend.setOnClickListener(this);
        rl_myZiXun.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            if (v.getId() == R.id.rl_setting) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
                Order121Application.getInstance().addActivity(getActivity());
            } else if (!Order121Application.isLogin()) {
                ToastUtil.show(getActivity(), "请先登陆", Toast.LENGTH_SHORT);
                startActivityForResult(new Intent(getActivity(), LoginActivity.class), ISLOGIN);
            } else {
                switch (v.getId()) {
                    case R.id.rl_fankui:
                        startActivity(new Intent(getActivity(), AdviceActivity.class));
                        break;
                    case R.id.ll_gosetting:
                        startActivity(new Intent(getActivity(), PersonalSettingActivity.class));
                        Order121Application.getInstance().addActivity(getActivity());
                        break;
                    case R.id.rl_myActivity:
                        final Dialog finalDialog;
                        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_myactivity, null);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setView(view);
                        finalDialog = builder.show();
                        view.findViewById(R.id.tv_yifabu).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), PublishedActivity.class);
                                intent.putExtra("type", "已发布");
                                startActivity(intent);
                                finalDialog.dismiss();
                            }
                        });
                        view.findViewById(R.id.tv_yibaoming).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), PublishedActivity.class);
                                intent.putExtra("type", "已报名");
                                startActivity(intent);
                                finalDialog.dismiss();
                            }
                        });
                        Display display = getActivity().getWindowManager().getDefaultDisplay();
                        WindowManager.LayoutParams params = finalDialog.getWindow().getAttributes();
                        params.width = (int) (display.getWidth() * 0.6);
                        params.height = (int) (display.getHeight() * 0.28);
                        ;
                        finalDialog.getWindow().setAttributes(params);
                        break;
                    case R.id.addressManage:
                        startActivity(new Intent(getActivity(), AddressManagerActivity.class));
                        break;
                    case R.id.ordermanager:
                        Intent intent1 = new Intent(getActivity(), OrderManagerActivity.class);
                        intent1.putExtra("what", 1);
                        startActivity(intent1);
                        break;
                    case R.id.rl_myappointment:
                        startActivity(new Intent(getActivity(), AppointmentActivity.class));
                        break;
                    case R.id.rl_myZiXun:
                        startActivity(new Intent(getActivity(), MyZixunListActivity.class));
                        break;
                    case R.id.rl_myFriend:
                        startActivity(new Intent(getActivity(), FriendsQuanActivity.class)
                                .putExtra("from", "my"));
                        break;
                }
            }
        }
    }

    public void login() {
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("token", Order121Application.globalLoginModel.getData().getToken());
        Order121Application.getGlobalHttpUtils().send(HttpRequest.HttpMethod.POST, G.Host.USERINFO, requestParams, new MyRequestCallBack(getActivity(), handler, 1, new GlobalLoginModel()));
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        GlobalLoginModel globalLoginModel = (GlobalLoginModel) msg.obj;
                        if (globalLoginModel != null) {
                            if (globalLoginModel.getRespCode() == 0) {
                                Order121Application.globalLoginModel = globalLoginModel;
                                Log.e("--", Order121Application.globalLoginModel.getData().getPhone());
                                if (TextUtils.isEmpty(Order121Application.globalLoginModel.getData().getNickname())) {
                                    tv_user_phone.setText(Order121Application.globalLoginModel.getData().getPhone());
                                } else {
                                    tv_user_phone.setText(Order121Application.globalLoginModel.getData().getNickname());
                                }
                                Log.e("getImgKey", Order121Application.globalLoginModel.getData().getImgKey());
                                Order121Application.getHeadImgBitmapUtils().display(circleImageView, Order121Application.globalLoginModel.getData().getImgKey());
                            }
                        }
                        break;
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(myFragmentBroadcastReciver);
    }
}
