package com.goodfood86.tiaoshi.order121Project.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.CreateOrderModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.widget.ProgressHUD;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/4/1.
 */
public class SubmitOrderActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.iv_back)
    private ImageView iv_back;
    @ViewInject(R.id.iv_route)
    private ImageView iv_route;
    @ViewInject(R.id.tv_price)
    private TextView tv_price;
    @ViewInject(R.id.tv_weight)
    private TextView tv_weight;
    @ViewInject(R.id.tv_distance)
    private TextView tv_distance;
    @ViewInject(R.id.tv_sendaddress)
    private TextView tv_sendaddress;
    @ViewInject(R.id.tv_reciveaddress)
    private TextView tv_reciveaddress;
    @ViewInject(R.id.et_send_name)
    private EditText et_send_name;
    @ViewInject(R.id.et_send_phone)
    private EditText et_send_phone;
    @ViewInject(R.id.et_receive_name)
    private EditText et_receive_name;
    @ViewInject(R.id.et_receive_phone)
    private EditText et_receive_phone;
    @ViewInject(R.id.iv_send_book)
    private ImageView iv_send_book;
    @ViewInject(R.id.iv_receive_book)
    private ImageView iv_receive_book;
    @ViewInject(R.id.show_rg)
    private RelativeLayout show_rg;
    @ViewInject(R.id.cb_check)
    private CheckBox cb_check;
    @ViewInject(R.id.et_thingname)
    private EditText et_thingname;
    @ViewInject(R.id.et_beizhu)
    private EditText et_beizhu;
    @ViewInject(R.id.tv_total_price)
    private TextView tv_total_price;
    @ViewInject(R.id.rg_addprice)
    private RadioGroup rg_addprice;
    @ViewInject(R.id.btn_gopay)
    private Button btn_gopay;
    @ViewInject(R.id.rl_peisong)
    private RelativeLayout rl_peisong;
    private final static int PICK_SEND_CONTACT = 1;
    private final static int PICK_RECEIVE_CONTACT = 2;
    private final static int GO_LOGIN = 3;
    private final static int GO_PAY = 4;
    private int BOOK_CODE = 0x11;
    private int BOOK_CODE_OTHER = 0x12;
    private String sendname, sendPhoneNumber, receivename, receivePhoneNumber;
    private String name;
    private String phoneNumber;
    private int premium;
    private int totalPrice;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        dialog.dismiss();
                        CreateOrderModel createOrderModel = (CreateOrderModel) msg.obj;
                        if (createOrderModel.getRespCode() == 0) {
                            Intent intent4 = new Intent(SubmitOrderActivity.this, OrderPayActivity.class);
                            intent4.putExtra("orderNo", createOrderModel.getData().getOrderNo());
                            intent4.putExtra("total", totalPrice + "");
                            intent4.putExtra("payOrderName", "需要配送" + et_thingname.getText().toString() + ",共1件物品，合计 " + totalPrice + " 元。");
                            intent4.putExtra("payOrderDetail", "付款来源: 安卓支付宝客户端,"
                                    + "订单编号：" + createOrderModel.getData().getOrderNo() + ",配送数量：1件" +
                                    ",合计：" + totalPrice + " 元。");
                            intent4.putExtra("sendaddress", tv_sendaddress.getText().toString().trim());
                            intent4.putExtra("receiveaddress", tv_reciveaddress.getText().toString().trim());
                            intent4.putExtra("sendinfo", et_send_name.getText().toString() + "      " + et_send_phone.getText().toString());
                            intent4.putExtra("receiveinfo", et_receive_name.getText().toString() + "        " + et_receive_phone.getText().toString());
                            if (et_thingname.getText().toString().trim() != null) {
                                intent4.putExtra("thingname", et_thingname.getText().toString().trim());
                            }
                            intent4.putExtra("distance", getIntent().getDoubleExtra("distance", 0.0) + "");
                            intent4.putExtra("weight", getIntent().getIntExtra("weight", 0) + "公斤");
                            intent4.putExtra("premium", premium + "");
                            intent4.putExtra("beizhu", et_beizhu.getText().toString().trim());
                            startActivityForResult(intent4, GO_PAY);
                            Order121Application.getInstance().addOrderActivity(SubmitOrderActivity.this);
                        } else {
                            ToastUtil.showShort(SubmitOrderActivity.this, createOrderModel.getRespMsg());
                        }
                        break;
                }
            }
        }
    };
    private ProgressHUD dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submitorder);
        ViewUtils.inject(this);
        initData();
        initListener();
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

    private void initData() {
        totalPrice = getIntent().getIntExtra("price", 0);
        tv_total_price.setText(totalPrice + "元");
        tv_price.setText(getIntent().getIntExtra("price", 0) + "");
        tv_weight.setText(getIntent().getIntExtra("weight", 0) + "");
        tv_distance.setText(getIntent().getDoubleExtra("distance", 0.0) + "");
        tv_sendaddress.setText(getIntent().getStringExtra("send_address") + getIntent().getStringExtra("send_detailaddress"));
        tv_reciveaddress.setText(getIntent().getStringExtra("receive_address") + getIntent().getStringExtra("receive_detailaddress"));
        dialog = ProgressHUD.show(this, "创建订单中", false, null);
    }


    private void initListener() {
        iv_send_book.setOnClickListener(this);
        iv_receive_book.setOnClickListener(this);
        cb_check.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_route.setOnClickListener(this);
        btn_gopay.setOnClickListener(this);
        rl_peisong.setOnClickListener(this);
        rg_addprice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_1:
                        totalPrice = getIntent().getIntExtra("price", 0) + 5;
                        break;
                    case R.id.rb_2:
                        totalPrice = getIntent().getIntExtra("price", 0) + 10;
                        break;
                    case R.id.rb_3:
                        totalPrice = getIntent().getIntExtra("price", 0) + 15;
                        break;
                    case R.id.rb_4:
                        totalPrice = getIntent().getIntExtra("price", 0) + 20;
                        break;
                }
                tv_total_price.setText(totalPrice + "元");
            }
        });
        cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    totalPrice = getIntent().getIntExtra("price", 0);
                    tv_total_price.setText(totalPrice + "元");
                } else {
                    switch (rg_addprice.getCheckedRadioButtonId()) {
                        case R.id.rb_1:
                            totalPrice = getIntent().getIntExtra("price", 0) + 5;
                            break;
                        case R.id.rb_2:
                            totalPrice = getIntent().getIntExtra("price", 0) + 10;
                            break;
                        case R.id.rb_3:
                            totalPrice = getIntent().getIntExtra("price", 0) + 15;
                            break;
                        case R.id.rb_4:
                            totalPrice = getIntent().getIntExtra("price", 0) + 20;
                            break;
                    }
                    tv_total_price.setText(totalPrice + "元");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.iv_send_book:
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                            != PackageManager.PERMISSION_GRANTED) {
                        //申请ACCESS_FINE_LOCATION权限
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                                BOOK_CODE);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, PICK_SEND_CONTACT);
                    }
                    break;
                case R.id.iv_receive_book:
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                            != PackageManager.PERMISSION_GRANTED) {
                        //申请ACCESS_FINE_LOCATION权限
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                                BOOK_CODE_OTHER);
                    } else {
                        Intent intent2 = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent2, PICK_RECEIVE_CONTACT);
                    }
                    break;
                case R.id.cb_check:
                    if (cb_check.isChecked()) {
                        show_rg.setVisibility(View.VISIBLE);
                    } else {
                        show_rg.setVisibility(View.GONE);
                    }
                    break;
                case R.id.iv_back:
                    finish();
                    break;
                case R.id.iv_route:
                    Intent intent3 = new Intent(this, ShowRouteActivity.class);
                    intent3.putExtra("mSendLat", getIntent().getDoubleExtra("mSendLat", 0.0));
                    intent3.putExtra("mSendLng", getIntent().getDoubleExtra("mSendLng", 0.0));
                    intent3.putExtra("mReceiveLat", getIntent().getDoubleExtra("mReceiveLat", 0.0));
                    intent3.putExtra("mReceiveLng", getIntent().getDoubleExtra("mReceiveLng", 0.0));
                    startActivity(intent3);
                    break;
                case R.id.btn_gopay:
                    if (Regular()) {
                        if (!Order121Application.isLogin()) {
                            startActivityForResult(new Intent(this, LoginActivity.class), GO_LOGIN);
                        } else {
                            createOrder();

                        }
                    }
                    break;
                case R.id.rl_peisong:
                    Intent intent4 = new Intent(this, AgreementActivity.class);
                    intent4.putExtra("title", "配送说明");
                    intent4.putExtra("code", "UserDispatching");
                    startActivity(intent4);
                    break;
            }
        }
    }

    private void createOrder() {
        dialog.show();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("commission", getIntent().getIntExtra("price", 0) + "");
        if (cb_check.isChecked()) {
            switch (rg_addprice.getCheckedRadioButtonId()) {
                case R.id.rb_1:
                    requestParams.addBodyParameter("premium", "5");
                    premium = 5;
                    totalPrice = getIntent().getIntExtra("price", 0) + 5;
                    Log.e("premium", "5");
                    break;
                case R.id.rb_2:
                    requestParams.addBodyParameter("premium", "10");
                    premium = 10;
                    totalPrice = getIntent().getIntExtra("price", 0) + 10;
                    Log.e("premium", "10");
                    break;
                case R.id.rb_3:
                    premium = 15;
                    requestParams.addBodyParameter("premium", "15");
                    totalPrice = getIntent().getIntExtra("price", 0) + 15;
                    Log.e("premium", "15");
                    break;
                case R.id.rb_4:
                    premium = 20;
                    requestParams.addBodyParameter("premium", "20");
                    totalPrice = getIntent().getIntExtra("price", 0) + 20;
                    Log.e("premium", "20");
                    break;
            }
        } else {
            premium = 0;
            totalPrice = getIntent().getIntExtra("price", 0);
        }
        if (!TextUtils.isEmpty(et_beizhu.getText().toString().trim())) {
            requestParams.addBodyParameter("desc", et_beizhu.getText().toString().trim());
        }
        requestParams.addBodyParameter("distance", getIntent().getDoubleExtra("distance", 0.0) + "");
        requestParams.addBodyParameter("token", Order121Application.globalLoginModel.getData().getToken());
        requestParams.addBodyParameter("sender[username]", et_send_name.getText().toString());
        requestParams.addBodyParameter("sender[phone]", et_send_phone.getText().toString());
        requestParams.addBodyParameter("sender[address]", getIntent().getStringExtra("send_address"));
        requestParams.addBodyParameter("sender[addressDetail]", getIntent().getStringExtra("send_detailaddress"));
        requestParams.addBodyParameter("sender[lng]", getIntent().getDoubleExtra("mSendLng", 0.0) + "");
        requestParams.addBodyParameter("sender[lat]", getIntent().getDoubleExtra("mSendLat", 0.0) + "");
        requestParams.addBodyParameter("customer[username]", et_receive_name.getText().toString());
        requestParams.addBodyParameter("customer[phone]", et_receive_phone.getText().toString());
        requestParams.addBodyParameter("customer[address]", getIntent().getStringExtra("receive_address"));
        requestParams.addBodyParameter("customer[addressDetail]", getIntent().getStringExtra("receive_detailaddress"));
        requestParams.addBodyParameter("customer[lng]", getIntent().getDoubleExtra("mReceiveLng", 0.0) + "");
        requestParams.addBodyParameter("customer[lat]", getIntent().getDoubleExtra("mReceiveLat", 0.0) + "");
        requestParams.addBodyParameter("product[1][productName]", et_thingname.getText().toString().trim());
        requestParams.addBodyParameter("product[1][productUnit]", getIntent().getIntExtra("weight", 0) + "公斤");
        Log.e("---", "commission" + getIntent().getIntExtra("price", 0) + "distance" + getIntent().getDoubleExtra("distance", 0.0) + "sender[username]" + sendname +
                "sender[phone]" + sendPhoneNumber + "sender[address]" + getIntent().getStringExtra("send_address") + "sender[addressDetail]" + "customer[username]" + receivename +
                "customer[phone]" + receivePhoneNumber + "product[productName]" + et_thingname.getText().toString().trim() + "product[productUnit]" + getIntent().getIntExtra("weight", 0) + "公斤");
        Order121Application.getGlobalHttpUtils().send(HttpRequest.HttpMethod.POST, G.Host.CREATE_ORDER, requestParams, new MyRequestCallBack(this, handler, 1, new CreateOrderModel()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && (requestCode == PICK_SEND_CONTACT || requestCode == PICK_RECEIVE_CONTACT)) {
            Uri contactData = data.getData();
            Cursor c = managedQuery(contactData, null, null, null, null);
            if (c.moveToFirst()) {
                name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
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
                    Log.e("===", name + "   " + phoneNumber);
                }
            }
            if (requestCode == PICK_SEND_CONTACT) {
                sendname = name;
                sendPhoneNumber = phoneNumber;
                et_send_name.setText(sendname);
                et_send_phone.setText(sendPhoneNumber);
            } else if (requestCode == PICK_RECEIVE_CONTACT) {
                receivename = name;
                Log.e("customer[username]", receivename);
                receivePhoneNumber = phoneNumber;
                et_receive_name.setText(receivename);
                et_receive_phone.setText(receivePhoneNumber);
            }
        } else if (resultCode == RESULT_OK && requestCode == GO_LOGIN) {

        } else if (resultCode == RESULT_OK && requestCode == GO_PAY) {
            finish();
        }
    }

    private Boolean Regular() {
        if (TextUtils.isEmpty(et_send_name.getText().toString().trim())) {
            ToastUtil.show(this, "请输入寄件人姓名", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtils.isEmpty(et_send_phone.getText().toString().trim())) {
            ToastUtil.show(this, "请输入寄件人手机号", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtils.isEmpty(et_receive_name.getText().toString().trim())) {
            ToastUtil.show(this, "请输入收件人姓名", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtils.isEmpty(et_receive_phone.getText().toString().trim())) {
            ToastUtil.show(this, "请输入收件人手机号", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtils.isEmpty(et_thingname.getText().toString().trim())) {
            ToastUtil.show(this, "物品名称不能为空", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == BOOK_CODE) {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_SEND_CONTACT);
        } else if (requestCode == BOOK_CODE_OTHER) {
            Intent intent2 = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent2, PICK_RECEIVE_CONTACT);
        }
    }
}
