package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.CreateActivityModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.DateTimePickDialogUtil;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/8/9.
 */
public class ActionYuyueActivity extends Activity implements View.OnClickListener{
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.tv_type_name)
    private TextView tv_type_name;
    @ViewInject(R.id.tv_time)
    private TextView tv_time;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.tv_address)
    private TextView tv_address;
    @ViewInject(R.id.tv_yuyue)
    private TextView tv_yuyue;
    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;
    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.et_remark)
    private EditText et_remark;
    @ViewInject(R.id.rl_yuyue_time)
    private RelativeLayout rl_yuyue_time;
    @ViewInject(R.id.rl_yuyue_address)
    private RelativeLayout rl_yuyue_address;
    @ViewInject(R.id.rl_yuyue_name)
    private RelativeLayout rl_yuyue_name;
    private String initEndDateTime = "2016年8月8日 08:08"; // 初始化结束时间
    private Double mSendLat=0.0,mSendLng=0.0;
    private String mSendAddress;
    private String mSendSuAddress;
    private String phoneNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_yuyue);
        initView();
    }
    private void initView(){
        ViewUtils.inject(this);
        nav_title.setText("预约");
        initEndDateTime= formatDate(System.currentTimeMillis()+"") ;
        nav_back.setOnClickListener(this);
        rl_yuyue_address.setOnClickListener(this);
        rl_yuyue_name.setOnClickListener(this);
        rl_yuyue_time.setOnClickListener(this);
        tv_yuyue.setOnClickListener(this);
        tv_type_name.setText("服务类型："+getIntent().getStringExtra("typeName"));
    }
      private String formatDate(String str){
        long seconds = Long.parseLong(str);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(seconds);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh:mm");
        return simpleDateFormat.format(gc.getTime());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_back:
                finish();
                break;
            case R.id.rl_yuyue_time:
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        ActionYuyueActivity.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(tv_time);
                break;
            case R.id.rl_yuyue_name:
                showDialog("姓名：",tv_name.getText().toString(),tv_name);
                break;
            case R.id.rl_yuyue_address:
                Intent intent = new Intent(ActionYuyueActivity.this, SelectAddressMapActivity.class);
                intent.putExtra("what", "4");
//                intent.putExtra("city", mCity);
                startActivityForResult(intent, 1);
                break;
            case R.id.tv_yuyue:
                if (TextUtils.isEmpty(tv_name.getText().toString())){
                    ToastUtil.showShort(ActionYuyueActivity.this,"请输入姓名");
                    break;
                }
                if (TextUtils.isEmpty(mSendAddress)){
                    ToastUtil.showShort(ActionYuyueActivity.this,"请选择地址");
                    break;
                }
                if (TextUtils.isEmpty(tv_time.getText().toString())){
                    ToastUtil.showShort(ActionYuyueActivity.this,"请选择服务时间");
                    break;
                }
                if (TextUtils.isEmpty(et_remark.getText().toString())){
                    ToastUtil.showShort(ActionYuyueActivity.this,"请输入服务备注");
                    break;
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String str=tv_time.getText().toString().replaceAll("年","-").replaceAll("月","-").replaceAll("日","")+":00";
                try {
                    Date  date = simpleDateFormat.parse(str);
                    Date curDate = new Date(System.currentTimeMillis());
                    if (date.getTime()<curDate.getTime()){
                        ToastUtil.showShort(ActionYuyueActivity.this,"预约服务时间要大于当前时间");
                        break;
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                initData();
                break;
        }
    }
    private void showDialog(String name, String content, final TextView textView) {
        final Dialog finalDialog;
        View view = LayoutInflater.from(this).inflate(R.layout.update_name_layout, null);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        final EditText et_content = (EditText) view.findViewById(R.id.et_content);
        TextView tv_title_name = (TextView) view.findViewById(R.id.tv_title_name);
        tv_title_name.setText(name);
        et_content.setText(content);
        builder1.setView(view);
        builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textView.setText(et_content.getText().toString());
                dialog.dismiss();
            }
        });
        builder1.setNegativeButton("取消", null);
        finalDialog = builder1.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK) {
            if (requestCode == 1) {
                mSendLat=data.getDoubleExtra("lat",0.0);
                mSendLng=data.getDoubleExtra("lng",0.0);
                mSendAddress=data.getStringExtra("title");
                mSendSuAddress=data.getStringExtra("suaddress");
                phoneNo=data.getStringExtra("phoneNo");
                tv_address.setText(data.getStringExtra("title") + data.getStringExtra("suaddress"));
                tv_phone.setText(phoneNo);
                Log.e("latlng",mSendLat+"  "+mSendLng+phoneNo);
            }
        }
    }
    private void initData(){
        HashMap map=new HashMap();
        map.put("token", Order121Application.globalLoginModel.getData().getToken());
        if (getIntent().getStringExtra("moduleCategoryId") != null) {
            map.put("moduleCategoryId", getIntent().getStringExtra("moduleCategoryId"));
        }
        if (getIntent().getStringExtra("code")!=null){
            map.put("code", getIntent().getStringExtra("code"));
        }
        map.put("name", tv_name.getText().toString());
        map.put("phone", phoneNo);
        map.put("address", mSendAddress);
        map.put("addressDetail", mSendSuAddress);
        map.put("remark", et_remark.getText().toString());
        map.put("lng", mSendLng+"");
        map.put("lat", mSendLat+"");
        map.put("time", tv_time.getText().toString().replaceAll("年","-").replaceAll("月","-").replaceAll("日","")+":00");
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime",System.currentTimeMillis()/1000+"");
        String sign= SignUtil.getSign(map);
        map.put("sign", MD5.getMD5(sign));
        Log.e("____",sign);
        OkHttpManager.postAsync(G.Host.ACTION_YUYUE,map,new MyCallBack(1,this,new CreateActivityModel(),handler));
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    CreateActivityModel createActivityModel= (CreateActivityModel) msg.obj;
                    if ("SUCCESS".equals(createActivityModel.getRespCode())){
                        ToastUtil.showShort(ActionYuyueActivity.this,"预约成功");
                        finish();
                    }else {
                        ToastUtil.showShort(ActionYuyueActivity.this,createActivityModel.getRespMsg());
                    }
                    break;
            }
        }
    };

}
