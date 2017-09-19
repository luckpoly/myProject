package com.brandsh.tiaoshi.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.AddAddressJsonData;
import com.brandsh.tiaoshi.model.AddsPhoneTuijianModel;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddDeliveryAddressActivity extends FragmentActivity implements View.OnClickListener {
    //回退
    @ViewInject(R.id.add_address_ivBack)
    private ImageView add_address_ivBack;
    //收货人姓名
    @ViewInject(R.id.add_address_etUserName)
    private EditText add_address_etUserName;
    //收货人电话
    @ViewInject(R.id.add_address_etUserPhone)
    private EditText add_address_etUserPhone;
    //收货人地址选择
    @ViewInject(R.id.add_address_tvAddAddress)
    private TextView add_address_tvAddAddress;
    //收货人地址补充
    @ViewInject(R.id.add_address_etAddAddress)
    private EditText add_address_etAddAddress;
    @ViewInject(R.id.add_address_etmph)
    private EditText add_address_etmph;
    //保存
    @ViewInject(R.id.add_address_tvSave)
    private TextView add_address_tvSave;
    @ViewInject(R.id.tv_getphone)
    private TextView tv_getphone;
    @ViewInject(R.id.tv_show_popup)
    private TextView tv_show_popup;
    @ViewInject(R.id.gv_sex)
    private RadioGroup gv_sex;
    @ViewInject(R.id.gv_tag)
    private RadioGroup gv_tag;
    @ViewInject(R.id.gv_ykt_city)
    private GridView gv_ykt_city;
    @ViewInject(R.id.gv_wkt_city)
    private GridView gv_wkt_city;

    private String userName;
    private String userPhone;
    private String userAddress;
    private String userHouseNumber;
    private boolean isChooseAddress;
    private RequestParams requestParams;
    private HashMap requestMap;
    private AddAddressBR addAddressBR;
    private double lng=Double.parseDouble(TiaoshiApplication.Lng);
    private double lat=Double.parseDouble(TiaoshiApplication.Lat);
    private String area;
    private AlertDialog.Builder builder;
    private String sex;
    private String tag;
    private String num="";
    private String phoneNumber;
    private final static int PICK_SEND_CONTACT = 1;
    private int BOOK_CODE = 0x11;
    private String[] str;
    InputMethodManager mInputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delivery_address);
        //沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);
        init();
        initData();
    }

    private class AddAddressBR extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("updateAddress".equals(intent.getAction())) {
                isChooseAddress = true;
                userAddress = intent.getStringExtra("address");
                area = intent.getStringExtra("area");
                add_address_tvAddAddress.setText(userAddress);
                add_address_etAddAddress.setText(area);
                lng = Double.parseDouble(intent.getStringExtra("lng"));
                lat = Double.parseDouble(intent.getStringExtra("lat"));
            }
        }
    }
    private void init() {
        add_address_etUserPhone.setText(TiaoshiApplication.phone);
        add_address_ivBack.setOnClickListener(this);
        add_address_tvAddAddress.setOnClickListener(this);
        add_address_tvSave.setOnClickListener(this);
        tv_getphone.setOnClickListener(this);
        tv_show_popup.setOnClickListener(this);
        add_address_etUserPhone.setOnClickListener(this);
        requestParams = new RequestParams();
        requestMap=new HashMap();
        addAddressBR = new AddAddressBR();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("updateAddress");
        registerReceiver(addAddressBR, intentFilter);
         mInputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        builder = new AlertDialog.Builder(this).setTitle("确认新增").setMessage("确认是否新增该收货地址").setNegativeButton("确 认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestMap.clear();
                requestMap.put("token", TiaoshiApplication.globalToken);
                requestMap.put("contact", userName);
                requestMap.put("tel", userPhone);
                if (sex!=null){
                    requestMap.put("sex", sex);
                }
                if (tag!=null){
                    requestMap.put("tag", tag);
                }
                requestMap.put("num", num);
                requestMap.put("address", userAddress);
                requestMap.put("addressDetail", userHouseNumber);
                requestMap.put("lng", lng + "");
                requestMap.put("lat", lat + "");
                requestMap.put("actReq", SignUtil.getRandom());
                requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
                String sign = SignUtil.getSign(requestMap);
                requestMap.put("sign", Md5.toMd5(sign));
                Log.e("-",sign);
                OkHttpManager.postAsync(G.Host.ADD_ADDRESS, requestMap, new MyCallBack(1, AddDeliveryAddressActivity.this, new AddAddressJsonData(), handler));


            }
        }).setPositiveButton("再看看", null);
        gv_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_man:
                        sex="男";
                        break;
                    case R.id.rb_woman:
                        sex="女";
                        break;
                }
            }
        });
        gv_tag.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_jia:
                        tag="家";
                        break;
                    case R.id.rb_gs:
                        tag="公司";
                        break;
                    case R.id.rb_xx:
                        tag="学校";
                        break;
                }
            }
        });
    }
    private void initData(){
        HashMap map=new HashMap();
        map.put("token",TiaoshiApplication.globalToken);
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.RECO_PHONE,map,new MyCallBack(2,this,new AddsPhoneTuijianModel(),handler));
    }

    private boolean isCorrect() {
        userName = add_address_etUserName.getText().toString();
        userPhone = add_address_etUserPhone.getText().toString();
        userAddress = add_address_tvAddAddress.getText().toString();
        userHouseNumber = add_address_etAddAddress.getText().toString();
        num = add_address_etmph.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(AddDeliveryAddressActivity.this, "收货人姓名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(userPhone)) {
            Toast.makeText(AddDeliveryAddressActivity.this, "联系电话不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userPhone.length() != 11) {
            Toast.makeText(AddDeliveryAddressActivity.this, "联系电话格式不正确", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(add_address_tvAddAddress.getText().toString())) {
            Toast.makeText(AddDeliveryAddressActivity.this, "请先输入地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(userHouseNumber)) {
            Toast.makeText(AddDeliveryAddressActivity.this, "请输入详细地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(num)) {
            Toast.makeText(AddDeliveryAddressActivity.this, "请输入门牌号", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_address_ivBack:
                finish();
                break;
            case R.id.add_address_tvAddAddress:
                Intent intent = new Intent(this, ChooseAddressMapActivity.class);
                startActivity(intent);
                break;
            case R.id.add_address_tvSave:
                if (isCorrect()) {
                    builder.create().show();
                }
                break;
            case R.id.tv_getphone:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请ACCESS_FINE_LOCATION权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                            BOOK_CODE);
                } else {
                    Intent intent1 = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent1, PICK_SEND_CONTACT);
                }
                break;
            case R.id.tv_show_popup:
                if (str!=null){
                    showPopupWindow();
                }
                break;
            case R.id.add_address_etUserPhone:
                add_address_etUserPhone.setFocusable(true);//设置输入框可聚集
                add_address_etUserPhone.setFocusableInTouchMode(true);//设置触摸聚焦
                add_address_etUserPhone.requestFocus();//请求焦点
                add_address_etUserPhone.findFocus();//获取焦点
                 mInputMethodManager.showSoftInput(add_address_etUserPhone, InputMethodManager.SHOW_FORCED);// 显示输入法
                add_address_etUserPhone.setSelectAllOnFocus(true);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(addAddressBR);
        super.onDestroy();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    AddAddressJsonData addAddressJsonData = (AddAddressJsonData) msg.obj;
                    if (addAddressJsonData != null) {
                        if ("SUCCESS".equals(addAddressJsonData.getRespCode())) {
                            Toast.makeText(AddDeliveryAddressActivity.this, "收货地址新增成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent("updateAddressList");
                            if (getIntent().getStringExtra("isLocation")!=null&&getIntent().getStringExtra("isLocation").equals("YES")){
                                intent = new Intent("changeAddress");
                                intent.putExtra("address", userAddress);
                                intent.putExtra("area", userHouseNumber+num);
                                intent.putExtra("lng", lng+"");
                                intent.putExtra("lat", lat+"");
                                setResult(1);
                            }
                            sendBroadcast(intent);
                            finish();
                        } else {
                            Toast.makeText(AddDeliveryAddressActivity.this, addAddressJsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 2:
                    AddsPhoneTuijianModel addsPhoneTuijianModel= (AddsPhoneTuijianModel) msg.obj;
                    if ("SUCCESS".equals(addsPhoneTuijianModel.getRespCode())&&addsPhoneTuijianModel.getData()!=null){
                        if (!TextUtils.isEmpty(addsPhoneTuijianModel.getData().getPhones())){
                            String [] a=addsPhoneTuijianModel.getData().getPhones().split(",");
                            List<String> tmp = new ArrayList<>();
                            for(String str:a){
                                if(str!=null && str.length()!=0){
                                    tmp.add(str);
                                }
                            }
                            str = tmp.toArray(new String[0]);
                            tv_show_popup.setVisibility(View.VISIBLE);
                        }else {
                            tv_show_popup.setVisibility(View.GONE);
                        }
                    }
                    break;
            }
        }
    };
    private void showPopupWindow(){
        View view= LayoutInflater.from(this).inflate(R.layout.popup_phone_list,null);
        final PopupWindow popupWindow=new PopupWindow(view, RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
       ListView listView= (ListView) view.findViewById(R.id.ll_phone_tj);
        listView.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1,str));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                add_address_etUserPhone.setText(str[position]);
                add_address_etUserPhone.setFocusable(false);
                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(add_address_etUserPhone);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && (requestCode == PICK_SEND_CONTACT )) {
            Uri contactData = data.getData();
            Cursor c = managedQuery(contactData, null, null, null, null);
            if (c.moveToFirst()) {
                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                if (hasPhone.equalsIgnoreCase("1")) {
                    hasPhone = "true";
                } else {
                    hasPhone = "false";
                }
                if (Boolean.parseBoolean(hasPhone)) {
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    while (phones.moveToNext()) {
                        phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    phones.close();
                    Log.e("===", "   " + phoneNumber);
                }
            }
            if (requestCode == PICK_SEND_CONTACT) {
                add_address_etUserPhone.setText(phoneNumber);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == BOOK_CODE) {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_SEND_CONTACT);
        }
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
