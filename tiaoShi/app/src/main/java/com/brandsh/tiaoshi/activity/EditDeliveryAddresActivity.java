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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.AddsPhoneTuijianModel;
import com.brandsh.tiaoshi.model.DelAddressJsonData;
import com.brandsh.tiaoshi.model.ModifyAddressJsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.LogUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 猪猪~ on 2016/3/14.
 */
public class EditDeliveryAddresActivity extends FragmentActivity implements View.OnClickListener {
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
    //保存
    @ViewInject(R.id.add_address_tvSave)
    private TextView add_address_tvSave;
    @ViewInject(R.id.tv_getphone)
    private TextView tv_getphone;
    @ViewInject(R.id.tv_show_popup)
    private TextView tv_show_popup;
    @ViewInject(R.id.add_address_etmph)
    private EditText add_address_etmph;
    //删除收货地址
    @ViewInject(R.id.ll_recicle)
    private View ll_recicle;
    @ViewInject(R.id.gv_sex)
    private RadioGroup gv_sex;
    @ViewInject(R.id.gv_tag)
    private RadioGroup gv_tag;
    private String sex;
    private String tag;
    private String num = "";
    private String phoneNumber;
    private String userName;
    private String userPhone;
    private String userAddress;
    private String userHouseNumber;

    private double lng;
    private double lat;
    private String area;
    private AlertDialog.Builder builder_del, builder_save;
    private HashMap delRequestMap;
    private HashMap modifyRequestMap;
    private AddAddressBR addAddressBR;
    private Bundle bundle;
    private String contact;
    private String phone;
    private String address1;
    private String address;
    private String addr_id;
    private final static int PICK_SEND_CONTACT = 1;
    private int BOOK_CODE = 0x11;
    private String[] str;
    InputMethodManager mInputMethodManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delivery_address);
        //沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);
        initView();
        initData();
    }

    private void initView() {
        gv_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_man:
                        sex = "男";
                        break;
                    case R.id.rb_woman:
                        sex = "女";
                        break;
                }
            }
        });
        gv_tag.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_jia:
                        tag = "家";
                        break;
                    case R.id.rb_gs:
                        tag = "公司";
                        break;
                    case R.id.rb_xx:
                        tag = "学校";
                        break;
                }
            }
        });
        tv_getphone.setOnClickListener(this);
        tv_show_popup.setOnClickListener(this);
        mInputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private class AddAddressBR extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("updateAddress".equals(intent.getAction())) {
                userAddress = intent.getStringExtra("address");
                area = intent.getStringExtra("area");
                add_address_tvAddAddress.setText(userAddress);
                add_address_etAddAddress.setText(area);
                if (intent.getStringExtra("lng") != null) {
                    lng = Double.parseDouble(intent.getStringExtra("lng"));
                }
                if (intent.getStringExtra("lat") != null) {
                    lat = Double.parseDouble(intent.getStringExtra("lat"));
                }
            }
        }
    }

    private void initData() {
        addAddressBR = new AddAddressBR();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("updateAddress");
        registerReceiver(addAddressBR, intentFilter);

        bundle = getIntent().getExtras();
        contact = bundle.getString("contact");
        add_address_etUserName.setText(contact);

        phone = bundle.getString("phone");
        add_address_etUserPhone.setText(phone);

        address1 = bundle.getString("address1");
        add_address_tvAddAddress.setText(address1);

        address = bundle.getString("address");
        add_address_etAddAddress.setText(address);

        addr_id = bundle.getString("addr_id");
        lng = Double.parseDouble(bundle.getString("lng"));
        lat = Double.parseDouble(bundle.getString("lat"));
        sex = bundle.getString("sex");
        if (sex != null) {
            switch (sex) {
                case "男":
                    gv_sex.check(R.id.rb_man);
                    break;
                case "女":
                    gv_sex.check(R.id.rb_woman);
                    break;
            }
        }
        tag = bundle.getString("tag");
        if (tag != null) {
            switch (tag) {
                case "家":
                    gv_tag.check(R.id.rb_jia);
                    break;
                case "公司":
                    gv_tag.check(R.id.rb_gs);
                    break;
                case "学校":
                    gv_tag.check(R.id.rb_xx);
                    break;
            }
        }
        num = bundle.getString("num") + "";
        add_address_etmph.setText(num);
        setOnClick();
        initDialog();
        //获取推荐手机号码
        HashMap map = new HashMap();
        map.put("token", TiaoshiApplication.globalToken);
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign1 = SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign1));
        OkHttpManager.postAsync(G.Host.RECO_PHONE, map, new MyCallBack(3, this, new AddsPhoneTuijianModel(), handler));

    }

    private void initDialog() {
        builder_del = new AlertDialog.Builder(this).setTitle("删除地址").setMessage("是否删除该收货地址？").setNegativeButton("取 消", null).setPositiveButton("删 除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delRequestMap = new HashMap();
                Log.e("addr_id", addr_id);
                delRequestMap.put("token", TiaoshiApplication.globalToken);
                delRequestMap.put("addressId", addr_id);
                delRequestMap.put("actReq", SignUtil.getRandom());
                delRequestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
                String sign = SignUtil.getSign(delRequestMap);
                delRequestMap.put("sign", Md5.toMd5(sign));
               OkHttpManager.postAsync(G.Host.DEL_ADDRESS, delRequestMap, new MyCallBack(1, EditDeliveryAddresActivity.this, new DelAddressJsonData(), handler));
            }
        });
        builder_save = new AlertDialog.Builder(this).setTitle("确认修改").setMessage("是否修改该收货地址？").setNegativeButton("取 消", null).setPositiveButton("修 改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                modifyRequestMap = new HashMap();
//                modifyRequestMap.put("user_id", TiaoshiApplication.globalUserId);
                modifyRequestMap.put("token", TiaoshiApplication.globalToken);
                modifyRequestMap.put("addressId", addr_id);
                modifyRequestMap.put("contact", userName);
                modifyRequestMap.put("tel", userPhone);
                modifyRequestMap.put("address", userAddress);
                modifyRequestMap.put("addressDetail", userHouseNumber);
                if (sex != null) {
                    modifyRequestMap.put("sex", sex);
                }
                if (tag != null) {
                    modifyRequestMap.put("tag", tag);
                }
                modifyRequestMap.put("num", num);
                modifyRequestMap.put("lng", lng + "");
                modifyRequestMap.put("lat", lat + "");
                modifyRequestMap.put("actReq", "123456");
                modifyRequestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
                String sign = SignUtil.getSign(modifyRequestMap);
                modifyRequestMap.put("sign", Md5.toMd5(sign));
                OkHttpManager.postAsync(G.Host.MODIFY_ADDRESS, modifyRequestMap, new MyCallBack(2, EditDeliveryAddresActivity.this, new ModifyAddressJsonData(), handler));


            }
        });
    }

    private void setOnClick() {
        add_address_ivBack.setOnClickListener(this);
        add_address_tvAddAddress.setOnClickListener(this);
        add_address_tvSave.setOnClickListener(this);
        ll_recicle.setOnClickListener(this);
        add_address_etUserPhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_recicle:
                builder_del.create().show();
                break;
            case R.id.add_address_tvAddAddress:
//                Intent intent = new Intent(this, ChooseAddressActivity.class);
                Intent intent = new Intent(this, ChooseAddressMapActivity.class);
                startActivity(intent);
                break;

            case R.id.add_address_tvSave:
                if (isCorrect()) {
                    builder_save.create().show();
                }
                break;
            case R.id.add_address_ivBack:
                finish();
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
                if (str != null) {
                    showPopupWindow();
                    tv_show_popup.setVisibility(View.VISIBLE);
                } else {
                    tv_show_popup.setVisibility(View.GONE);
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

    private boolean isCorrect() {
        userName = add_address_etUserName.getText().toString();
        userPhone = add_address_etUserPhone.getText().toString();
        userAddress = add_address_tvAddAddress.getText().toString();
        userHouseNumber = add_address_etAddAddress.getText().toString();
        num = add_address_etmph.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(EditDeliveryAddresActivity.this, "收货人姓名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(userPhone)) {
            Toast.makeText(EditDeliveryAddresActivity.this, "联系电话不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userPhone.length() != 11) {
            Toast.makeText(EditDeliveryAddresActivity.this, "联系电话格式不正确", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(userHouseNumber)) {
            Toast.makeText(EditDeliveryAddresActivity.this, "请输入详细地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(num)) {
            Toast.makeText(EditDeliveryAddresActivity.this, "请输入门牌号", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
                    DelAddressJsonData delAddressJsonData = (DelAddressJsonData) msg.obj;
                    if (delAddressJsonData != null) {
                        if ("SUCCESS".equals(delAddressJsonData.getRespCode())) {
                            Toast.makeText(EditDeliveryAddresActivity.this, "收货地址删除成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent("updateAddressList");
                            sendBroadcast(intent);
                            finish();
                        } else {
                            Toast.makeText(EditDeliveryAddresActivity.this, delAddressJsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 2:
                    ModifyAddressJsonData modifyAddressJsonData = (ModifyAddressJsonData) msg.obj;
                    if (modifyAddressJsonData != null) {
                        if ("SUCCESS".equals(modifyAddressJsonData.getRespCode())) {
                            Toast.makeText(EditDeliveryAddresActivity.this, "收货地址修改成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent("updateAddressList");
                            sendBroadcast(intent);
                            finish();
                        } else {
                            Toast.makeText(EditDeliveryAddresActivity.this, modifyAddressJsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 3:
                    AddsPhoneTuijianModel addsPhoneTuijianModel = (AddsPhoneTuijianModel) msg.obj;
                    if ("SUCCESS".equals(addsPhoneTuijianModel.getRespCode()) && addsPhoneTuijianModel.getData() != null) {
                        if (!TextUtils.isEmpty(addsPhoneTuijianModel.getData().getPhones())) {
                            String [] a=addsPhoneTuijianModel.getData().getPhones().split(",");
                            List<String> tmp = new ArrayList<>();
                            for(String str:a){
                                if(str!=null && str.length()!=0){
                                    tmp.add(str);
                                }
                            }
                            str = tmp.toArray(new String[0]);
                        }
                    }
                    break;
            }
        }
    };

    private void showPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_phone_list, null);
        final PopupWindow popupWindow = new PopupWindow(view, RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        ListView listView = (ListView) view.findViewById(R.id.ll_phone_tj);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, str));
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
        if (resultCode == Activity.RESULT_OK && (requestCode == PICK_SEND_CONTACT)) {
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
