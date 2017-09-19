package com.brandsh.tiaoshi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.MonthOrderDetailModel;
import com.brandsh.tiaoshi.model.MonthOrderListModel;
import com.brandsh.tiaoshi.model.MonthTimeModel;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.widget.SwitchButton;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/10/19.
 */

public class MonthOrderDetailActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.switch_btn)
    SwitchButton switch_btn;
    @ViewInject(R.id.ll_isShow)
    LinearLayout ll_isShow;
    @ViewInject(R.id.ll_isShow_wan)
    LinearLayout ll_isShow_wan;
    @ViewInject(R.id.tv_time_1)
    TextView tv_time_1;
    @ViewInject(R.id.tv_time_2)
    TextView tv_time_2;
    @ViewInject(R.id.tv_timeContent1)
    TextView tv_timeContent1;
    @ViewInject(R.id.tv_timeContent2)
    TextView tv_timeContent2;
    @ViewInject(R.id.tv_bianji)
    TextView tv_bianji;
    @ViewInject(R.id.tv_chooseAddress)
    TextView tv_chooseAddress;
    @ViewInject(R.id.tv_add_content)
    TextView tv_add_content;
    @ViewInject(R.id.tv_add_name)
    TextView tv_add_name;
    @ViewInject(R.id.tv_state)
    TextView tv_state;
    @ViewInject(R.id.tv_yuliang)
    TextView tv_yuliang;
    @ViewInject(R.id.tv_beizhu)
    TextView tv_beizhu;
    @ViewInject(R.id.tv_go_goods)
    TextView tv_go_goods;
    @ViewInject(R.id.iv_back)
    ImageView iv_back;
    @ViewInject(R.id.rl_go_Orderlist)
    RelativeLayout rl_go_Orderlist;
    private ChangeAddressBR changeAddressBR;
    private ArrayList<String> options1Items = new ArrayList<String>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();

    private ArrayList<String> options1Items1 = new ArrayList<String>();
    private ArrayList<ArrayList<String>> options2Items1 = new ArrayList<ArrayList<String>>();


    private OptionsPickerView pvOptions;
    private OptionsPickerView pvOptions1;
    private String lng = "";
    private String lat = "";
    private String contact = "";
    private String tel = "";
    private String address = "";
    private String addressDetail = "";
    private String morningSendTime = "";
    private String noonSendTime = "";
    private String nightSendTime = "";
    private String remarks = "";
    private boolean isEdit=false;
    private boolean isOkSwh=false;
    MonthTimeModel monthTimeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthorder_datail);
        //状态栏字体颜色
        AppUtil.Setbar(this);
        initView();
        initData();
        getTime();
    }

    private void initView() {
        ViewUtils.inject(this);
        tv_time_1.setOnClickListener(this);
        tv_time_2.setOnClickListener(this);
        tv_bianji.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_chooseAddress.setOnClickListener(this);
        tv_beizhu.setOnClickListener(this);
        rl_go_Orderlist.setOnClickListener(this);
        tv_go_goods.setOnClickListener(this);
        switch_btn.setThumbColorRes(R.drawable.custom_thumb_color);
        switch_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isOkSwh){
                    if (isChecked) {
                        setIsSeng("YES");
                        Log.e("-----", "YES");
                    } else {
                        setIsSeng("NO");
                        Log.e("-----", "NO");
                    }
                }

            }
        });
        changeAddressBR = new ChangeAddressBR();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("changeAddress");
        registerReceiver(changeAddressBR, intentFilter);
    }

    private void initData() {
        HashMap map = new HashMap();
        map.put("token", TiaoshiApplication.globalToken);
        map.put("orderId", getIntent().getStringExtra("OrderId"));
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        map.put("sign", Md5.toMd5(SignUtil.getSign(map)));
        OkHttpManager.postAsync(G.Host.MONTHORDER_DEAIL, map, new MyCallBack(1, this, new MonthOrderDetailModel(), handler));
    }

    private void setIsSeng(String state) {
        HashMap map = new HashMap();
        map.put("token", TiaoshiApplication.globalToken);
        map.put("orderId", getIntent().getStringExtra("OrderId"));
        map.put("startStatus", state);
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        map.put("sign", Md5.toMd5(SignUtil.getSign(map)));
        OkHttpManager.postAsync(G.Host.IS_SEND, map, new MyCallBack(2, this, new MonthOrderDetailModel(), handler));

    }
    private void getTime(){
        HashMap map=new HashMap();
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        map.put("sign", Md5.toMd5(SignUtil.getSign(map)));
        OkHttpManager.postAsync(G.Host.GET_TIME,map,new MyCallBack(3,this,new MonthTimeModel(),handler));
    }

    private void updateMonth() {
        if (tv_timeContent1.getText().toString().substring(0,2).equals("早上")){
            morningSendTime=tv_timeContent1.getText().toString().replace("早上","");
        }else if (tv_timeContent1.getText().toString().substring(0,2).equals("中午")){
            noonSendTime=tv_timeContent1.getText().toString().replace("中午","");
        }
        if (tv_timeContent2.getText().toString().substring(0,2).equals("中午")&&tv_timeContent1.getText().toString().substring(0,2).equals("中午")){
            nightSendTime="18:00";
        }else if (tv_timeContent2.getText().toString().substring(0,2).equals("晚上")){
            nightSendTime=tv_timeContent2.getText().toString().replace("晚上","");
        }else if (tv_timeContent2.getText().toString().substring(0,2).equals("中午")){
            noonSendTime=tv_timeContent2.getText().toString().replace("中午","");
        }
        remarks=tv_beizhu.getText().toString();
        HashMap map = new HashMap();
        map.put("token", TiaoshiApplication.globalToken);
        map.put("orderId", getIntent().getStringExtra("OrderId"));
        map.put("lng", lng);
        map.put("lat", lat);
        map.put("address", address);
        map.put("addressDetail", addressDetail);
        map.put("contact", contact);
        map.put("tel", tel);
        map.put("remarks", remarks);
        map.put("morningSendTime", morningSendTime);
        map.put("noonSendTime", noonSendTime);
        map.put("nightSendTime", nightSendTime);
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        map.put("sign", Md5.toMd5(SignUtil.getSign(map)));
        OkHttpManager.postAsync(G.Host.UPDATE_MONTH, map, new MyCallBack(2, this, new MonthOrderDetailModel(), handler));
       morningSendTime = "";
        noonSendTime = "";
        nightSendTime = "";

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    MonthOrderDetailModel monthOrderDetailModel = (MonthOrderDetailModel) msg.obj;
                    if ("SUCCESS".equals(monthOrderDetailModel.getRespCode())) {
                        if (monthOrderDetailModel.getData().getStartStatus().equals("1")) {
                            switch_btn.setChecked(true);
                            tv_state.setText("当前状态：正常配送");
                            ll_isShow.setVisibility(View.VISIBLE);
                        } else {
                            switch_btn.setChecked(false);
                            tv_state.setText("当前状态：暂停配送");
                            ll_isShow.setVisibility(View.GONE);
                        }
                        isOkSwh=true;
                        tv_add_name.setText(monthOrderDetailModel.getData().getContact());
                        tv_add_content.setText(monthOrderDetailModel.getData().getAddress() + monthOrderDetailModel.getData().getAddressDetail());
                        tv_yuliang.setText(monthOrderDetailModel.getData().getRestJuiceCount() + "杯");

                        tv_beizhu.setText(monthOrderDetailModel.getData().getRemarks());
                        lng = monthOrderDetailModel.getData().getLng();
                        lat = monthOrderDetailModel.getData().getLat();
                        tel = monthOrderDetailModel.getData().getTel();
                        contact = monthOrderDetailModel.getData().getContact();
                        address = monthOrderDetailModel.getData().getAddress();
                        addressDetail = monthOrderDetailModel.getData().getAddressDetail();
                        if (!TextUtils.isEmpty(monthOrderDetailModel.getData().getMorningSendTime())) {
                            tv_timeContent1.setText("早上" + monthOrderDetailModel.getData().getMorningSendTime());
                        } else {
                            tv_timeContent1.setText("中午" + monthOrderDetailModel.getData().getNoonSendTime());
                        }
                        if (!TextUtils.isEmpty(monthOrderDetailModel.getData().getNightSendTime())) {
                            tv_timeContent2.setText("晚上" + monthOrderDetailModel.getData().getNightSendTime());
                        } else {
                            tv_timeContent2.setText("中午" + monthOrderDetailModel.getData().getNoonSendTime());
                        }
                        //余量等于零的时候
                        if (monthOrderDetailModel.getData().getRestJuiceCount().equals("0")){
                            ll_isShow_wan.setVisibility(View.GONE);
                            switch_btn.setVisibility(View.GONE);
                            tv_state.setText("当前状态：订单已结束");
                            tv_go_goods.setVisibility(View.VISIBLE);
                        }
                        if (monthOrderDetailModel.getData().getIsEdit().equals("YES")){
                            isEdit=true;
                        }

                    }
                    break;
                case 2:
                    MonthOrderDetailModel monthOrderDetailModel1 = (MonthOrderDetailModel) msg.obj;
                    if ("SUCCESS".equals(monthOrderDetailModel1.getRespCode())) {
                        initData();
                    }
                    break;
                case 3:
                    monthTimeModel= (MonthTimeModel) msg.obj;
                    if (monthTimeModel.getRespCode().equals("SUCCESS")){
                        initPopwindows();
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_time_1:
                if (isEdit){
                    pvOptions.show();
                }else {
                    showTishi();
                }

                break;
            case R.id.tv_time_2:

                if (isEdit){
                    pvOptions1.show();
                }else {
                    showTishi();
                }
                break;
            case R.id.tv_bianji:
                if (isEdit){
                    showPopup();
                }else {
                    showTishi();
                }

                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_chooseAddress:
                if (isEdit){
                    Intent intent1 = new Intent(this, ChooseDeliveryAddressActivity.class);
                    startActivity(intent1);
                }else {
                    showTishi();
                }

                break;
            case R.id.rl_go_Orderlist:
                startActivity(new Intent(MonthOrderDetailActivity.this,MonthFenOrderListActivity.class).putExtra("orderId",getIntent().getStringExtra("OrderId")));
                break;
            case R.id.tv_go_goods:
                Intent intent6=new Intent(this,JuiceMonthActivity.class);
                intent6.putExtra("URL",G.Host.PICK_LIST);
                startActivity(intent6);
                break;

        }
    }

    private void showPopup() {
        final Dialog finalDialog;
        View view = LayoutInflater.from(this).inflate(R.layout.month_beizhu_dialog, null);
        TextView tv_dismiss_pack = (TextView) view.findViewById(R.id.tv_dismiss_pack);
        TextView tv_addjuice = (TextView) view.findViewById(R.id.tv_addjuice);
        final EditText et_beizhu_content = (EditText) view.findViewById(R.id.et_beizhu_content);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setCancelable(false);
        builder1.setView(view);
        finalDialog = builder1.show();
        tv_dismiss_pack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalDialog.dismiss();
            }
        });
        tv_addjuice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_beizhu.setText(et_beizhu_content.getText().toString());
                updateMonth();
                finalDialog.dismiss();
            }
        });
    }
    private void showTishi(){
        final Dialog finalDialog;
        View view = LayoutInflater.from(this).inflate(R.layout.month_tishi_dialog, null);
        TextView tv_dismiss_pack = (TextView) view.findViewById(R.id.tv_dismiss_pack);
        TextView tv_addjuice = (TextView) view.findViewById(R.id.tv_addjuice);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setCancelable(false);
        builder1.setView(view);
        finalDialog = builder1.show();
        tv_dismiss_pack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalDialog.dismiss();
            }
        });
        tv_addjuice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalDialog.dismiss();
            }
        });
    }

    private void initPopwindows() {
        options1Items.add("早上");
        options1Items.add("中午");
        options1Items1.add("中午");
        options1Items1.add("晚上");
        String [] str1=monthTimeModel.getData().getMorningSendTime().split(",");
        String [] str2=monthTimeModel.getData().getNoonSendTime().split(",");
        String [] str3=monthTimeModel.getData().getNightSendTime().split(",");
        ArrayList<String> options2Items_1 = new ArrayList<>();
        for (int i=0;i<str1.length;i++){
            options2Items_1.add(str1[i]+"点");
        }
        ArrayList<String> options2Items_2 = new ArrayList<>();
        for (int i=0;i<str2.length;i++){
            options2Items_2.add(str2[i]+"点");
        }
        ArrayList<String> options2Items_3 = new ArrayList<>();
        for (int i=0;i<str3.length;i++){
            options2Items_3.add(str3[i]+"点");
        }
        options2Items.add(options2Items_1);
        options2Items.add(options2Items_2);
        options2Items1.add(options2Items_2);
        options2Items1.add(options2Items_3);
        ArrayList list = new ArrayList();
        ArrayList list1 = new ArrayList();
        list1.add("00分");
        list1.add("10分");
        list1.add("20分");
        list1.add("30分");
        list1.add("40分");
        list1.add("50分");
        list.add(list1);
        list.add(list1);
        list.add(list1);
        options3Items.add(list);
        options3Items.add(list);
        options3Items.add(list);
        pvOptions = new OptionsPickerView(this);
        pvOptions.setPicker(options1Items, options2Items, options3Items, true);
        pvOptions.setCyclic(false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(1, 1, 1);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                    tv_timeContent1.setText(options1Items.get(options1) + options2Items.get(options1).get(option2).replace("点", ":") + options3Items.get(options1).get(option2).get(options3).replace("分", ""));
                    updateMonth();
            }
        });
        pvOptions1 = new OptionsPickerView(this);
        pvOptions1.setPicker(options1Items1, options2Items1, options3Items, true);
        pvOptions1.setCyclic(false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions1.setSelectOptions(1, 1, 1);
        pvOptions1.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                    tv_timeContent2.setText(options1Items1.get(options1) + options2Items1.get(options1).get(option2).replace("点", ":") + options3Items.get(options1).get(option2).get(options3).replace("分", ""));
                    updateMonth();
            }
        });

    }

    private class ChangeAddressBR extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("changeAddress".equals(intent.getAction())) {
                lng = intent.getStringExtra("lng");
                lat = intent.getStringExtra("lat");
                contact = intent.getStringExtra("contact");
                tel = intent.getStringExtra("tel");
                address = intent.getStringExtra("address");
                addressDetail = intent.getStringExtra("addressDetail");
                tv_add_name.setText(contact);
                tv_add_content.setText(address + addressDetail);
                updateMonth();
            }
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(changeAddressBR);
        super.onDestroy();
    }
}
