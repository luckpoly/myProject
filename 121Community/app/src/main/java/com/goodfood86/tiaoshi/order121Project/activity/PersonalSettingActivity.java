package com.goodfood86.tiaoshi.order121Project.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.CommonResultInfoModel;
import com.goodfood86.tiaoshi.order121Project.model.GlobalLoginModel;
import com.goodfood86.tiaoshi.order121Project.model.ImgBase64Model;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.utils.TakePhotoUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.widget.CircleImageView;
import com.goodfood86.tiaoshi.order121Project.widget.ProgressHUD;
import com.goodfood86.tiaoshi.order121Project.widget.TitleBarView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/3/29.
 */
public class PersonalSettingActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.title_bar)
    private LinearLayout title_bar;
    private TitleBarView titleBarView;
    @ViewInject(R.id.rl_setheadimage)
    private RelativeLayout rl_setheadimage;
    @ViewInject(R.id.civ)
    private CircleImageView civ;
    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;
    @ViewInject(R.id.tv_nicheng)
    private TextView tv_nicheng;
    @ViewInject(R.id.tv_qianming)
    private TextView tv_qianming;
    @ViewInject(R.id.tv_sex)
    private TextView tv_sex;
    @ViewInject(R.id.bt_back)
    private Button bt_back;
    @ViewInject(R.id.rl_updatapsw)
    private RelativeLayout rl_updatapsw;
    @ViewInject(R.id.rl_qianming)
    private RelativeLayout rl_qianming;
    @ViewInject(R.id.rl_sex)
    private RelativeLayout rl_sex;
    @ViewInject(R.id.rl_name)
    private RelativeLayout rl_name;
    private int CAMERA_CODE = 0x11;


    private Bitmap currentbitmap;
    private ProgressHUD dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personsetting);
        ViewUtils.inject(this);
        initView();
        initListener();
        initTitleBar();

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

    private void initView() {
        tv_phone.setText(Order121Application.globalLoginModel.getData().getPhone());
        Order121Application.getHeadImgBitmapUtils().display(civ, Order121Application.globalLoginModel.getData().getImgKey());
        if (TextUtils.isEmpty(Order121Application.globalLoginModel.getData().getNickname())) {
            tv_nicheng.setText(Order121Application.globalLoginModel.getData().getPhone());
            update();
        } else {
            tv_nicheng.setText(Order121Application.globalLoginModel.getData().getNickname());
        }
        tv_qianming.setText(Order121Application.globalLoginModel.getData().getIntro());
        if (Order121Application.globalLoginModel.getData().getSex().equals("1")) {
            tv_sex.setText("男");
        } else {
            tv_sex.setText("女");
        }
    }

    private void initTitleBar() {
        titleBarView = new TitleBarView(this, title_bar, "个人资料");
    }

    private void initListener() {
        rl_setheadimage.setOnClickListener(this);
        bt_back.setOnClickListener(this);
        rl_updatapsw.setOnClickListener(this);
        rl_name.setOnClickListener(this);
        rl_qianming.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.rl_setheadimage:
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        //申请ACCESS_FINE_LOCATION权限
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                CAMERA_CODE);
                    } else {
                        TakePhotoUtil.showDialog(this);
                    }
                    break;
                case R.id.bt_back:
                    logOut();
                    break;
                case R.id.rl_updatapsw:
                    startActivity(new Intent(PersonalSettingActivity.this, UpdatapswActivity.class));
                    break;
                case R.id.rl_name:
                    showDialog("修改昵称：", tv_nicheng.getText().toString(), tv_nicheng);
                    break;
                case R.id.rl_qianming:
                    showDialog("修改签名：", tv_qianming.getText().toString(), tv_qianming);
                    break;
                case R.id.rl_sex:
                    final Dialog finalDialog;
                    View view = LayoutInflater.from(this).inflate(R.layout.chose_sex_layout, null);
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setView(view);
                    finalDialog = builder1.show();
                  TextView tv_man=  (TextView) view.findViewById(R.id.tv_man);
                    TextView tv_woman=(TextView) view.findViewById(R.id.tv_woman);
                    tv_man.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tv_sex.setText("男");
                            update();
                            finalDialog.dismiss();
                        }
                    });
                    tv_woman.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tv_sex.setText("女");
                            update();
                            finalDialog.dismiss();
                        }
                    });
                    break;
            }
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
                update();
                dialog.dismiss();
            }
        });
        builder1.setNegativeButton("取消", null);
        finalDialog = builder1.show();
    }

    private void update() {
        RequestParams requestParams = new RequestParams();
        if (TextUtils.isEmpty(tv_nicheng.getText().toString())) {
            requestParams.addBodyParameter("nickname", Order121Application.globalLoginModel.getData().getPhone());
        } else {
            requestParams.addBodyParameter("nickname", tv_nicheng.getText().toString());
        }
        if (tv_sex.getText().toString().equals("男")){
            requestParams.addBodyParameter("sex", "1");
        }else {
            requestParams.addBodyParameter("sex", "0");
        }
        requestParams.addBodyParameter("token", Order121Application.globalLoginModel.getData().getToken());
        requestParams.addBodyParameter("intro", tv_qianming.getText().toString());
        Order121Application.getGlobalHttpUtils().send(HttpRequest.HttpMethod.POST, G.Host.UPDATE_INFO, requestParams, new MyRequestCallBack(this, handler, 3, new CommonResultInfoModel()));
    }

    private void logOut() {
        Order121Application.globalLoginModel = null;
        SharedPreferences.Editor editor = getSharedPreferences(G.SP.APP_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(G.SP.LOGIN_NAME, null);
        editor.putString(G.SP.LOGIN_PWD, null);
        editor.commit();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("type", G.SP.RORDOTPSW);
        startActivity(intent);
        sendBroadcast(new Intent("logOut"));
//        Order121Application.getInstance().finishAllActivity();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        currentbitmap = TakePhotoUtil.dealActivityResult(this, requestCode, resultCode, data, true);
        if (currentbitmap == null) {
            return;
        }
        //把当前bitmap转换成base64
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        currentbitmap.compress(Bitmap.CompressFormat.JPEG, 80, bOut);
        byte[] bytes = bOut.toByteArray();
        String bitmapString = Base64.encodeToString(bytes, Base64.DEFAULT);
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("imgBase64", bitmapString);
        requestParams.addBodyParameter("tp", "1");
        requestParams.addBodyParameter("token", Order121Application.globalLoginModel.getData().getToken());
        dialog = ProgressHUD.show(this, "努力上传中...", false, null);
        dialog.show();
        Order121Application.getGlobalHttpUtils().send(HttpRequest.HttpMethod.POST, G.Host.IMGBASE64, requestParams, new MyRequestCallBack(this, handler, 1, new ImgBase64Model()));
    }

    private ImgBase64Model imgBase64Model;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        imgBase64Model = (ImgBase64Model) msg.obj;
                        if (imgBase64Model != null) {
                            if (imgBase64Model.getRespCode() == 0) {
                                RequestParams requestParams = new RequestParams();
                                requestParams.addBodyParameter("imgKey", imgBase64Model.getData().getKey());
                                requestParams.addBodyParameter("token", Order121Application.globalLoginModel.getData().getToken());
//                                Log.e("---", imgBase64Model.getData().getKey() + "   " + Order121Application.globalLoginModel.getData().getToken());
//                                if (TextUtils.isEmpty(Order121Application.globalLoginModel.getData().getNickname())) {
//                                    requestParams.addBodyParameter("nickname", Order121Application.globalLoginModel.getData().getPhone());
//                                } else {
//                                    requestParams.addBodyParameter("nickname", Order121Application.globalLoginModel.getData().getNickname());
//                                }
                                Order121Application.getGlobalHttpUtils().send(HttpRequest.HttpMethod.POST, G.Host.UPDATA_HRADIMG, requestParams, new MyRequestCallBack(PersonalSettingActivity.this, handler, 2, new CommonResultInfoModel()));
//                                Order121Application.getGlobalHttpUtils().send(HttpRequest.HttpMethod.POST, G.Host.UPDATE_INFO, requestParams, new MyRequestCallBack(PersonalSettingActivity.this, handler, 2, new CommonResultInfoModel()));
                            } else {
                                dialog.dismiss();
                                ToastUtil.showShort(PersonalSettingActivity.this, imgBase64Model.getRespMsg());
                            }
                        }
                        break;
                    case 2:
                        CommonResultInfoModel commonResultInfoModel = (CommonResultInfoModel) msg.obj;
                        if (commonResultInfoModel.getRespCode() == 0) {
                            dialog.dismiss();
                            sendBroadcast(new Intent("updateImg"));
                            civ.setImageBitmap(currentbitmap);
                            ToastUtil.showShort(PersonalSettingActivity.this, "上传头像成功");
                        } else {
                            ToastUtil.showShort(PersonalSettingActivity.this, commonResultInfoModel.getRespMsg());
                            dialog.dismiss();
                        }
                        break;
                    case 3:
                        CommonResultInfoModel commonResultInfoModel1 = (CommonResultInfoModel) msg.obj;
                        if (commonResultInfoModel1.getRespCode() == 0) {
                            ToastUtil.showShort(PersonalSettingActivity.this, "修改成功");
                            sendBroadcast(new Intent("updateName"));
                        } else {
                            ToastUtil.showShort(PersonalSettingActivity.this, commonResultInfoModel1.getRespMsg());
                        }
                        break;
                }

            }
        }
    };

    public void write(String filename, String content) {
        try {
            FileOutputStream out = openFileOutput(filename, Context.MODE_PRIVATE);
            out.write(content.getBytes("UTF-8"));
            out.close();
            Toast.makeText(this, "写入成功", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void writeSD(String filename, String content) {
        String state = Environment.getExternalStorageState();
        try {
            if (state.equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(Environment.getExternalStorageDirectory() + "/" + filename);
                FileOutputStream out;
                out = new FileOutputStream(file);
                out.write(content.getBytes());
                out.close();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_CODE) {
            TakePhotoUtil.showDialog(this);
        }
    }
}
