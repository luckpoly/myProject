package com.brandsh.tiaoshi.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.activity.BondPhoneActivity;
import com.brandsh.tiaoshi.activity.ChangeNickNameActivity;
import com.brandsh.tiaoshi.activity.FCActivity;
import com.brandsh.tiaoshi.activity.PhoneGHActivity;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.ChangeUserInfoData;
import com.brandsh.tiaoshi.model.ExternalModel;
import com.brandsh.tiaoshi.model.LoginJsonData;
import com.brandsh.tiaoshi.model.UserInfoJsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.LogUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.QiLiuUtils;
import com.brandsh.tiaoshi.utils.SPUtil;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.TakePhotoUtil;
import com.brandsh.tiaoshi.widget.CircleImageView;
import com.brandsh.tiaoshi.wxapi.WXEntryActivity;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by libokang on 15/11/5.
 */
public class MyInfoFragment extends BaseFragment implements OnClickListener {
    @ViewInject(R.id.rl_change_head)
    private RelativeLayout rl_change_head;
    @ViewInject(R.id.civ_head_img)
    private CircleImageView civ_head_img;
    @ViewInject(R.id.nav_title)
    TextView nav_title;
    @ViewInject(R.id.nav_back)
    ImageView nav_back;
    @ViewInject(R.id.rl_name)
    private RelativeLayout rl_name;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;

    @ViewInject(R.id.rl_sex)
    private RelativeLayout rl_sex;
    @ViewInject(R.id.tv_sex)
    private TextView tv_sex;
    @ViewInject(R.id.tv_qq)
    private TextView tv_qq;
    @ViewInject(R.id.tv_wx)
    private TextView tv_wx;
    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;
    @ViewInject(R.id.tv_pwd)
    private TextView tv_pwd;
    @ViewInject(R.id.rl_phone)
    private RelativeLayout rl_phone;
    @ViewInject(R.id.rl_qq)
    private RelativeLayout rl_qq;
    @ViewInject(R.id.rl_wx)
    private RelativeLayout rl_wx;

    @ViewInject(R.id.rl_pwd)
    private RelativeLayout rl_pwd;

    @ViewInject(R.id.rl_logout)
    private RelativeLayout rl_logout;


    private Bitmap currentbitmap;
    private String currentSex;
    private BitmapUtils bitmapUtils;
    private String currentName;
    private HashMap<String, String> sexRequestMap;
    private MyFragmentBroadcastReciver myFragmentBroadcastReciver;
    private String url;
    private int CAMERA_CODE = 1;
    private int FROM = 1000;
    Tencent mTencent;
    String openId;
    BaseUiListener baseUiListener;
    String tag;
    ExternalModel externalModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_info_fragment, null);
        ViewUtils.inject(this, view);
        initListener();
        initData();
        registerReceiver();
        return view;
    }

    private void registerReceiver() {
        nav_title.setText("个人资料");
        myFragmentBroadcastReciver = new MyFragmentBroadcastReciver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("updateNickName1");
        intentFilter.addAction("WXBindSuccess");
        intentFilter.addAction("finish");
        getActivity().registerReceiver(myFragmentBroadcastReciver, intentFilter);
    }

    private class MyFragmentBroadcastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if ("updateNickName1".equals(intent.getAction())) {
                tv_name.setText(TiaoshiApplication.globalUserInfo.getData().getNickName());
            } else if ("finish".equals(intent.getAction())) {
                getActivity().finish();
            } else if ("WXBindSuccess".equals(intent.getAction())) {
                HashMap hashMap = new HashMap();
                hashMap.put("externalPlatform", "WX");
                hashMap.put("token", TiaoshiApplication.globalToken);
                hashMap.put("terminal", AppUtil.getIMEI(getContext()));
                hashMap.put("externalKey", intent.getStringExtra("code"));
                hashMap.put("actReq", SignUtil.getRandom());
                hashMap.put("actTime", System.currentTimeMillis() / 1000 + "");
                String sign = SignUtil.getSign(hashMap);
                hashMap.put("sign", Md5.toMd5(sign));
                LogUtil.e(sign);
                OkHttpManager.postAsync(G.Host.EXTERNAL_BIND, hashMap, new MyCallBack(6, getActivity(), new LoginJsonData(), handler));
            }

        }
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(myFragmentBroadcastReciver);
        super.onDestroy();
    }

    private void initData() {
        bitmapUtils = TiaoshiApplication.getHeadImgBitmapUtils();
        mTencent = TiaoshiApplication.mTencent;
        baseUiListener = new BaseUiListener();
        UserInfoJsonData userInfoJsonData = TiaoshiApplication.globalUserInfo;

        if (userInfoJsonData != null) {
            if (!"".equals(userInfoJsonData.getData().getIcon())) {
                bitmapUtils.display(civ_head_img, userInfoJsonData.getData().getIcon());
            }
            currentName = userInfoJsonData.getData().getNickName();
            tv_name.setText(currentName);

            currentSex = userInfoJsonData.getData().getSex();
            if ("0".equals(currentSex)) {
                tv_sex.setText("尚未设置");
            } else if ("MAN".equals(currentSex)) {
                tv_sex.setText("男");
            } else if ("WOMAN".equals(currentSex)) {
                tv_sex.setText("女");
            }
            if (userInfoJsonData.getData().getQq().equals("YES")) {
                tv_qq.setText("已绑定");
            } else {
                tv_qq.setText("未绑定");
            }
            if (userInfoJsonData.getData().getWx().equals("YES")) {
                tv_wx.setText("已绑定");
            } else {
                tv_wx.setText("未绑定");
            }
            if (TextUtils.isEmpty(userInfoJsonData.getData().getTel())) {
                tv_phone.setText("未绑定");
            } else {
                tv_phone.setText("修改");
            }
            if (userInfoJsonData.getData().getPwd().equals("YES")){
                tv_pwd.setText("重置密码");
            }else if (userInfoJsonData.getData().getPwd().equals("NO")){
                tv_pwd.setText("设置密码");
            }
        } else {
            Toast.makeText(getActivity(), "登录信息已过期，请重新登录", Toast.LENGTH_SHORT).show();
            Intent intent = FCActivity.getFCActivityIntent(getActivity(), PhoneLoginFragment.class);
            TiaoshiApplication.isLogin = false;
            startActivity(intent);
            getActivity().finish();
        }
    }

    private void initListener() {
        rl_change_head.setOnClickListener(this);
        rl_name.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_pwd.setOnClickListener(this);
        rl_logout.setOnClickListener(this);
        nav_back.setVisibility(View.VISIBLE);
        nav_back.setOnClickListener(this);
        rl_phone.setOnClickListener(this);
        rl_qq.setOnClickListener(this);
        rl_wx.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rl_change_head:
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请ACCESS_FINE_LOCATION权限
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            CAMERA_CODE);
                } else {
                    TakePhotoUtil.showDialog(this);
                }
                break;

            case R.id.rl_name:
                changeName();
                break;

            case R.id.rl_sex:
                changeSex();
                break;
            case R.id.rl_pwd:
                startActivityForResult(FCActivity.getFCActivityIntent(getActivity(), ForgetpwdFragment.class).putExtra("isShezhi",tv_pwd.getText().toString()),FROM);
                break;

            case R.id.rl_logout:
                logout();
                break;
            case R.id.nav_back:
                getActivity().finish();
                break;
            case R.id.rl_phone:
                if (tv_phone.getText().toString().equals("未绑定")) {
                    startActivityForResult(new Intent(getActivity(), BondPhoneActivity.class), FROM);
                } else if (tv_phone.getText().toString().equals("修改")) {
                    startActivity(new Intent(getActivity(), PhoneGHActivity.class));
                }
                break;
            case R.id.rl_qq:
                if (tv_qq.getText().toString().equals("未绑定")) {
                    qqlogin();
                } else if (tv_qq.getText().toString().equals("已绑定")) {
                    tag = "QQ";
                    isJiebang();
                }
                break;
            case R.id.rl_wx:
                if (tv_wx.getText().toString().equals("未绑定")) {
                    if (!TiaoshiApplication.iwxapi.isWXAppInstalled()) {
                        Toast.makeText(getActivity(), "您还未安装微信客户端",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo";
                    TiaoshiApplication.iwxapi.sendReq(req);
                    LogUtil.e("登录微信");
                } else if (tv_wx.getText().toString().equals("已绑定")) {
                    tag = "微信";
                    isJiebang();
                }
                break;

        }
    }

    public void qqlogin() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", baseUiListener);
        }
    }

    private void isJiebang() {
        HashMap hashMap = new HashMap();
        hashMap.put("token", TiaoshiApplication.globalToken);
        hashMap.put("actReq", SignUtil.getRandom());
        hashMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(hashMap);
        hashMap.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.EXTERNAL_ONLY, hashMap, new MyCallBack(7, getContext(), new ExternalModel(), handler));
    }

    class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            LogUtil.e(o.toString());
            try {
                JSONObject object = new JSONObject(o.toString());
                openId = object.getString("openid");
                String accessToken = object.getString("access_token");
                String expires = object.getString("expires_in");
                mTencent.setOpenId(openId);
                mTencent.setAccessToken(accessToken, expires);
                LogUtil.e(openId);
                QQToken token = mTencent.getQQToken();
                UserInfo info = new UserInfo(getContext(), token);
                info.getUserInfo(new BaseUserInfoListener());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    }

    class BaseUserInfoListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            if (o == null) {
                return;
            }
            try {
                JSONObject jo = (JSONObject) o;
                Log.e("JO:", jo.toString());
                int ret = jo.getInt("ret");
                if (ret == 0) {
                    HashMap map = new HashMap();
                    map.put("externalPlatform", "QQ");
                    map.put("token", TiaoshiApplication.globalToken);
                    map.put("externalKey", openId);
                    map.put("terminal", TiaoshiApplication.AppIMEI);
                    map.put("icon", jo.getString("figureurl_qq_2"));
                    if (!TextUtils.isEmpty(jo.getString("gender")) && jo.getString("gender").equals("女")) {
                        map.put("sex", "2");
                    } else {
                        map.put("sex", "1");
                    }
                    map.put("nickname", jo.getString("nickname"));
                    map.put("actReq", SignUtil.getRandom());
                    map.put("actTime", System.currentTimeMillis() / 1000 + "");
                    String sign = SignUtil.getSign(map);
                    map.put("sign", Md5.toMd5(sign));
                    LogUtil.e(sign);
                    OkHttpManager.postAsync(G.Host.EXTERNAL_BIND_EXT, map, new MyCallBack(5, getContext(), new LoginJsonData(), handler));
                    //QQ登出
                    mTencent.logout(getContext());
                }
            } catch (Exception e) {
            }
        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("退出登录").setMessage("是否退出？");
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(G.SP.APP_NAME, Context.MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();
                TiaoshiApplication.globalUserInfo = null;
                TiaoshiApplication.isLogin = false;
                Intent intent = new Intent("exit_login");
                getActivity().sendBroadcast(intent);
                getActivity().finish();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    private void changeSex() {
        sexRequestMap = new HashMap<>();
        sexRequestMap.put("token", TiaoshiApplication.globalToken);
        sexRequestMap.put("actReq", SignUtil.getRandom());
        sexRequestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("选择性别：");
        builder.setPositiveButton("女", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentSex = "WOMEN";
                sexRequestMap.put("sex", currentSex);
                String sign = SignUtil.getSign(sexRequestMap);
                HashMap<String, String> map = sexRequestMap;
                map.put("sign", Md5.toMd5(sign));
                OkHttpManager.postAsync(G.Host.CHANGE_USERINFO, map, new MyCallBack(3, getActivity(), new ChangeUserInfoData(), handler));
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("男", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentSex = "MAN";
                sexRequestMap.put("sex", currentSex);
                String sign = SignUtil.getSign(sexRequestMap);
                HashMap<String, String> map = sexRequestMap;
                map.put("sign", Md5.toMd5(sign));
                OkHttpManager.postAsync(G.Host.CHANGE_USERINFO, map, new MyCallBack(3, getActivity(), new ChangeUserInfoData(), handler));
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void changeName() {
        Intent intent = new Intent(getActivity(), ChangeNickNameActivity.class);
        startActivity(intent);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    ChangeUserInfoData changeHeadImgJsonData = (ChangeUserInfoData) msg.obj;
                    if (changeHeadImgJsonData != null) {
                        if (changeHeadImgJsonData.getRespCode().equals("SUCCESS")) {
                            showToast("头像修改成功");
                            TiaoshiApplication.globalUserInfo.getData().setIcon(url);
                            bitmapUtils.clearCache();
                            bitmapUtils.clearDiskCache();
                            bitmapUtils.clearMemoryCache();
                            bitmapUtils.display(civ_head_img, TiaoshiApplication.globalUserInfo.getData().getIcon());
                            Intent intent = new Intent("updateHeadImg");
                            getActivity().sendBroadcast(intent);
                        } else {
                            showToast(changeHeadImgJsonData.getRespMsg());
                        }
                    }
                    break;
                case 3:
                    ChangeUserInfoData changeSexJsonData = (ChangeUserInfoData) msg.obj;
                    if (changeSexJsonData != null) {
                        if (changeSexJsonData.getRespCode().equals("SUCCESS")) {
                            showToast("性别修改成功");
                            if ("MAN".equals(currentSex)) {
                                tv_sex.setText("男");
                                TiaoshiApplication.globalUserInfo.getData().setSex("MAN");
                            } else if ("WOMEN".equals(currentSex)) {
                                tv_sex.setText("女");
                                TiaoshiApplication.globalUserInfo.getData().setSex("WOMAN");
                            }
                        } else {
                            showToast(changeSexJsonData.getRespMsg());
                        }
                    }
                    break;
                case 4:
                    url = msg.obj.toString();
                    HashMap map = new HashMap();
                    map.put("token", TiaoshiApplication.globalToken);
                    map.put("icon", url);
                    map.put("actReq", SignUtil.getRandom());
                    map.put("actTime", System.currentTimeMillis() / 1000 + "");
                    String sign = SignUtil.getSign(map);
                    map.put("sign", Md5.toMd5(sign));
                    OkHttpManager.postAsync(G.Host.CHANGE_USERINFO, map, new MyCallBack(1, getActivity(), new ChangeUserInfoData(), handler));
                    break;
                case 5:
                    LoginJsonData loginJsonData = (LoginJsonData) msg.obj;
                    if ("SUCCESS".equals(loginJsonData.getRespCode())) {
                        showToast("绑定成功");
                        TiaoshiApplication.globalUserInfo.getData().setQq("YES");
                        tv_qq.setText("已绑定");
                    } else {
                        TiaoshiApplication.globalUserInfo.getData().setQq("NO");
                        showToast(loginJsonData.getRespMsg());
                    }
                    break;
                case 6:
                    LoginJsonData loginJsonData1 = (LoginJsonData) msg.obj;
                    if ("SUCCESS".equals(loginJsonData1.getRespCode())) {
                        showToast("绑定成功");
                        TiaoshiApplication.globalUserInfo.getData().setWx("YES");
                        tv_wx.setText("已绑定");
                    } else {
                        TiaoshiApplication.globalUserInfo.getData().setWx("NO");
                        showToast(loginJsonData1.getRespMsg());
                    }
                    break;
                case 7:
                    externalModel = (ExternalModel) msg.obj;
                    if ("SUCCESS".equals(externalModel.getRespCode())) {
                        String ss;
                        if (externalModel.getData().getOnly().equals("YES")) {
                            ss = "该登录号已经是该账户的唯一登录号，如果解绑则该账户下的所有订单和余额等数据将会丢失，无法找回！您确定解绑该登录号吗？";
                        } else {
                            ss = "确定要解除账号与" + tag + "的关联吗？解除绑定后将无法使用" + tag + "登录此账号";
                        }
                        call(ss);
                    } else {
                        showToast(externalModel.getRespMsg());
                    }
                    break;
                case 8:
                    ExternalModel externalModel1 = (ExternalModel) msg.obj;
                    if ("SUCCESS".equals(externalModel1.getRespCode())) {
                        if (externalModel.getData().getOnly().equals("YES")) {
                            SPUtil.clearSP();
                            TiaoshiApplication.globalUserInfo = null;
                            TiaoshiApplication.isLogin = false;
                            Intent intent = new Intent("exit_login");
                            getActivity().sendBroadcast(intent);
                            getActivity().finish();
                        } else {
                            if (tag.equals("QQ")) {
                                tv_qq.setText("未绑定");
                                TiaoshiApplication.globalUserInfo.getData().setQq("NO");
                            } else {
                                tv_wx.setText("未绑定");
                                TiaoshiApplication.globalUserInfo.getData().setWx("NO");
                            }
                        }

                    } else {
                        showToast(externalModel1.getRespMsg());
                    }
                    break;
                case 200:

                    break;
                case 300:

                    break;
            }
        }
    };

    private void call(String ss) {
//        android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("解除绑定:").setMessage(ss);
        if (externalModel.getData().getOnly().equals("YES")){
            builder.setIcon(R.mipmap.jingshi_icon);
        }
        builder.setPositiveButton("解除绑定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HashMap hashMap = new HashMap();
                hashMap.put("token", TiaoshiApplication.globalToken);
                if (tag.equals("QQ")) {
                    hashMap.put("externalPlatform", "QQ");
                } else {
                    hashMap.put("externalPlatform", "WX");
                }
                hashMap.put("actReq", SignUtil.getRandom());
                hashMap.put("actTime", System.currentTimeMillis() / 1000 + "");
                String sign = SignUtil.getSign(hashMap);
                hashMap.put("sign", Md5.toMd5(sign));
                OkHttpManager.postAsync(G.Host.EXTERNAL_UNBIND, hashMap, new MyCallBack(8, getContext(), new ExternalModel(), handler));
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, baseUiListener);
        if (requestCode == FROM) {
            if (resultCode == Activity.RESULT_OK) {
                tv_phone.setText("修改");
                return;
            }
            if (resultCode==1001){
                tv_pwd.setText("重置密码");
            }
        }
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        currentbitmap = TakePhotoUtil.dealActivityResult(this, requestCode, resultCode, data, true);
        if (currentbitmap == null) {
            return;
        }
        String Url = TakePhotoUtil.picPath;
        Log.e("---", Url);
        QiLiuUtils.updateQL(Url, getActivity(), handler, 4);
//        //把当前bitmap转换成base64
//        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
//        currentbitmap.compress(Bitmap.CompressFormat.JPEG, 100, bOut);
//        byte[] bytes = bOut.toByteArray();
//        String bitmapString = Base64.encodeToString(bytes, Base64.DEFAULT);
//        //上传图片
//        HashMap map = new HashMap();
//        map.put("user_id", TiaoshiApplication.globalUserId);
//        map.put("token", TiaoshiApplication.globalToken);
//        map.put("avatar", bitmapString);
//        map.put("actReq", "123456");
//        map.put("actTime", System.currentTimeMillis() / 1000 + "");
//        String sign = SignUtil.getSign(map);
//        map.put("sign", Md5.toMd5(sign));
//        Log.e("--------","----------"+currentbitmap);
//        OkHttpManager.postAsync(G.Host.CHANGE_HEAD_IMG, map, new MyCallBack(1, getActivity(), new ChangeHeadImgJsonData(), handler));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_CODE) {
            TakePhotoUtil.showDialog(this);
        }
    }

}
