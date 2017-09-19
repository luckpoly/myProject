package com.brandsh.tiaoshi.application;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.CityModel;
import com.brandsh.tiaoshi.model.DiyShoppingCartJsonData;
import com.brandsh.tiaoshi.model.RefreshTokenModel;
import com.brandsh.tiaoshi.model.UserInfoJsonData;
import com.brandsh.tiaoshi.model.UserModel;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.LogUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SPUtil;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.wxapi.Constants;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * @author libokang
 * @date create at 2015/8/21
 */
public class TiaoshiApplication extends Application {
    private String userId;
    private String userToken;
    private String city;
    private String cachePath;
    public static DiyShoppingCartJsonData diyShoppingCartJsonData;
    public static boolean isFirstLogin;
    // 用户所在经纬度
    public static String Lat = "0";
    public static String Lng = "0";
    public static String Address;
    public static String phone;

    //用户是否登陆
    public static boolean isLogin;

    //全局用户信息
    public static UserInfoJsonData globalUserInfo;
    //全局开通城市信息
    public static CityModel cityModel;
    //全局用户ID和Token
    public static String globalUserId;
    public static String globalToken;
    // 全局用户信息
    public static UserModel globalUserModel;
    //手机ＩＭＥＩ号
    public static String AppIMEI;
    //微信api
    public static IWXAPI iwxapi;
    //QQapi
    public static Tencent mTencent;
    /**
     * 全局HttpUtils
     */
    private static HttpUtils globalHttpUtils;

    public static HttpUtils getGlobalHttpUtils() {
        return globalHttpUtils;
    }

    /**
     * 全局图片下载工具类
     */
    private static BitmapUtils globalBitmapUtils;
    private static BitmapUtils headImgBitmapUtils;

    public static BitmapUtils getHeadImgBitmapUtils() {
        return headImgBitmapUtils;
    }

    public static BitmapUtils getGlobalBitmapUtils() {
        return globalBitmapUtils;
    }

    public static void setGlobalBitmapUtils(BitmapUtils globalBitmapUtils) {
        TiaoshiApplication.globalBitmapUtils = globalBitmapUtils;
    }

    public static void setGlobalHttpUtils(HttpUtils globalHttpUtils) {
        TiaoshiApplication.globalHttpUtils = globalHttpUtils;
    }

    public static void setHeadImgBitmapUtils(BitmapUtils headImgBitmapUtils) {
        TiaoshiApplication.headImgBitmapUtils = headImgBitmapUtils;
    }

    private List<Activity> activities;

    /**
     * Activity回退相关
     */

    public TiaoshiApplication() {
        activities = new LinkedList<>();
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        if (activities != null && activities.size() > 0) {
            if (!activities.contains(activity)) {
                activities.add(activity);
            }
        } else {
            activities.add(activity);
        }
    }

    // 遍历所有的activity并finish
    public void finishAllActivity() {
        if (activities != null && activities.size() > 0) {
            for (Activity activity : activities) {
                activity.finish();
            }
            activities.clear();
        }
    }

    // 遍历所有Activity并finish并退出软件
    public void exit() {
        if (activities != null && activities.size() > 0) {
            for (Activity activity : activities) {
                activity.finish();
            }
            activities.clear();
        }
        System.exit(0);
    }

    /**
     * 获取应用程序的单例
     */
    private static TiaoshiApplication instance;

    public static TiaoshiApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //调试是否输出
        LogUtil.isDebug = true;
        isFirstLogin = true;
        //初始化sp
        SPUtil.init(this);
        //单例
        instance = this;
        globalHttpUtils = new HttpUtils();
        cachePath = Environment.getExternalStorageDirectory() + "/cacheFileDir";
        String cachePath1 = Environment.getExternalStorageDirectory() + "/cacheFileDira";
        globalBitmapUtils = new BitmapUtils(instance, cachePath);
//        globalBitmapUtils.configDefaultLoadingImage(R.mipmap.default_loadding_img);
        globalBitmapUtils.configDefaultLoadFailedImage(R.mipmap.default_loadding_img);
        globalBitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        headImgBitmapUtils = new BitmapUtils(instance, cachePath1);
        headImgBitmapUtils.configDefaultLoadingImage(R.mipmap.default_head_img);
        headImgBitmapUtils.configDefaultLoadFailedImage(R.mipmap.default_head_img);
        headImgBitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        userId = SPUtil.getSP("userId", null);
        AppIMEI = AppUtil.getIMEI(this);
        userToken = SPUtil.getSP("token", null);
        if (userId != null && userToken != null) {
            globalUserId = userId;
            globalToken = userToken;
            isLogin = true;
            city = SPUtil.getSP("city", null);
        }
        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //微信初始化api
        iwxapi = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        iwxapi.registerApp(Constants.APP_ID);
        //QQ初始化
        mTencent = Tencent.createInstance("1105155505", getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //解决notclass
//        MultiDex.install(this);
    }

    private void refreshToken() {
        HashMap smsCodeRequestMap = new HashMap();
        smsCodeRequestMap.put("refreshToken", SPUtil.getSP("refreshToken", null));
        smsCodeRequestMap.put("terminal", AppUtil.getIMEI(this));
        smsCodeRequestMap.put("actReq", SignUtil.getRandom());
        smsCodeRequestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(smsCodeRequestMap);
        smsCodeRequestMap.put("sign", Md5.toMd5(sign));
        LogUtil.e(sign);
        OkHttpManager.postAsync(G.Host.REFRESH_TOKEN, smsCodeRequestMap, new MyCallBack(2, this, new RefreshTokenModel(), handler));
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    RefreshTokenModel refreshTokenModel = (RefreshTokenModel) msg.obj;
                    if ("SUCCESS".equals(refreshTokenModel.getRespCode())) {
                        refreshTokenModel.getData().getAccessToken();
                        refreshTokenModel.getData().getAccessTokenOverTime();
                        refreshTokenModel.getData().getRefreshToken();
                        refreshTokenModel.getData().getRefreshTokenOverTime();
                        SPUtil.setSP("token", refreshTokenModel.getData().getAccessToken());
                        SPUtil.setSP("tokenTime", refreshTokenModel.getData().getAccessTokenOverTime());
                        SPUtil.setSP("refreshToken", refreshTokenModel.getData().getRefreshToken());
                        userToken = refreshTokenModel.getData().getAccessToken();
                    } else {
                        SPUtil.clearSP();
                        TiaoshiApplication.globalUserInfo = null;
                        TiaoshiApplication.isLogin = false;
                    }
                    break;
            }
        }
    };
}
