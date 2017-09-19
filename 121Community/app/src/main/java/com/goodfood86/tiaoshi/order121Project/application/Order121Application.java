package com.goodfood86.tiaoshi.order121Project.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.telephony.TelephonyManager;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.GlobalLoginModel;
import com.goodfood86.tiaoshi.order121Project.model.MoneyTableModel;
import com.goodfood86.tiaoshi.order121Project.photo.GlideImageLoader;
import com.goodfood86.tiaoshi.order121Project.rongyun.SealAppContext;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.core.BitmapSize;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.LinkedList;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.FileMessageItemProvider;
import io.rong.imlib.ipc.RongExceptionHandler;
import io.rong.message.FileMessage;
import io.rong.message.GroupNotificationMessage;
import io.rong.push.RongPushClient;
import io.rong.push.common.RongException;


/**
 * @author wangwuhao
 * @date create at 2016/3/24
 */
public class Order121Application extends Application {
    private static Context CONTEXT;
    public static boolean isFirstLogin;
    private  String cachePath;

    private static IWXAPI iwxapi;
    private SharedPreferences sharedPreferences;
    private String userId;
    private String userToken;
    public static GlobalLoginModel globalLoginModel;
    public static MoneyTableModel globalMoneyTableModel;
    public static boolean isLogin() {
        if (globalLoginModel == null) {
            return false;
        }
        return true;
    }

    // 用户所在经纬度
    public static String Lat;
    public static String Lng;

    //用户是否登陆
    public static boolean isLogin;



    //全局用户ID和Token
    public static String globalUserId;
    public static String globalToken;
    public static String globalCity;


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
    private static BitmapUtils globalBitmapUtilsBg;
    private static BitmapUtils headImgBitmapUtils;
    private static BitmapUtils activityBitmapUtils;

    public static BitmapUtils getActivityBitmapUtils() {
        return activityBitmapUtils;
    }public static BitmapUtils getHeadImgBitmapUtils() {
        return headImgBitmapUtils;
    }

    public static BitmapUtils getGlobalBitmapUtils() {
        return globalBitmapUtils;
    }
    public static BitmapUtils getGlobalBitmapUtilsBg() {
        return globalBitmapUtilsBg;
    }

    public static void setGlobalBitmapUtils(BitmapUtils globalBitmapUtils) {
        Order121Application.globalBitmapUtils = globalBitmapUtils;
    }

    public static void setGlobalHttpUtils(HttpUtils globalHttpUtils) {
        Order121Application.globalHttpUtils = globalHttpUtils;
    }

    public static void setHeadImgBitmapUtils(BitmapUtils headImgBitmapUtils) {
        Order121Application.headImgBitmapUtils = headImgBitmapUtils;
    }

    private List<Activity> activities;

    private List<Activity> orderActivities;
    /**
     * Activity回退相关
     */

    public Order121Application() {
        activities = new LinkedList<Activity>();
        orderActivities=new LinkedList<Activity>();
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
    public void addOrderActivity(Activity activity) {
        if (orderActivities != null && orderActivities.size() > 0) {
            if (!orderActivities.contains(activity)) {
                orderActivities.add(activity);
            }
        } else {
            orderActivities.add(activity);
        }
    }
    // 遍历所有的activity并finish
    public void finishOrderActivity() {
        if (orderActivities != null && orderActivities.size() > 0) {
            for (Activity activity : orderActivities) {
                activity.finish();
            }
            orderActivities.clear();
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
    private static Order121Application instance;

    public static Order121Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isFirstLogin=true;
        //单例
        instance = this;
        cachePath = Environment.getExternalStorageDirectory() + "/otoCacheFileDir";
        globalHttpUtils = new HttpUtils();
        globalBitmapUtils = new BitmapUtils(instance);
//        globalBitmapUtils.configDefaultLoadingImage(R.mipmap.default_loadding_img);
        globalBitmapUtils.configDefaultLoadFailedImage(R.mipmap.hard_bg);
        globalBitmapUtilsBg = new BitmapUtils(instance);
//        globalBitmapUtils.configDefaultLoadingImage(R.mipmap.default_loadding_img);
        globalBitmapUtilsBg.configDefaultLoadFailedImage(R.mipmap.llq_item_bg);
        sharedPreferences = getSharedPreferences(G.SP.APP_NAME, MODE_PRIVATE);
        headImgBitmapUtils = new BitmapUtils(instance);
        headImgBitmapUtils.configDefaultLoadingImage(R.mipmap.touxiang);
        headImgBitmapUtils.configDefaultLoadFailedImage(R.mipmap.belle);
       activityBitmapUtils=new BitmapUtils(instance,cachePath);
        activityBitmapUtils.configDefaultLoadingImage(R.mipmap.zhanweitu);
        activityBitmapUtils.configDefaultLoadFailedImage(R.mipmap.llq_item_bg);
        BitmapSize size = new BitmapSize(700,350);
        activityBitmapUtils.configDefaultBitmapMaxSize(size);

        headImgBitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        userId = sharedPreferences.getString("userId", null);
        userToken = sharedPreferences.getString("token", null);



        if (userId != null && userToken != null) {
            globalUserId = userId;
            globalToken = userToken;
            isLogin = true;
        }
        iwxapi = WXAPIFactory.createWXAPI(this, "wx2d919de3398e3c11", false);
        iwxapi.registerApp("wx2d919de3398e3c11");

        CONTEXT = getApplicationContext();
//        initImageLoader();
        initGalleryFinal();
        //融云初始化
        initRy();
    }
    public static IWXAPI getIwxapi() {
        return iwxapi;
    }

    private void initGalleryFinal() {
        //设置主题
        //ThemeConfig.CYAN
        ThemeConfig theme = ThemeConfig.DARK;
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder().setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();

        //配置imageloader
        cn.finalteam.galleryfinal.ImageLoader imageloader = new GlideImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageloader, theme).setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }



    /**
     * 设置图片加载方式
     */
    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .discCacheSize(50 * 1024 * 1024)//
                .discCacheFileCount(100)//缓存一百张图片
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }
    public static Context getContext() {
        return CONTEXT;
    }

    private void initRy(){
//                LeakCanary.install(this);//内存泄露检测
        RongPushClient.registerHWPush(this);
        RongPushClient.registerMiPush(this, "2882303761517473625", "5451747338625");
        /**
         * 注意：
         *
         * IMKit SDK调用第一步 初始化
         *
         * context上下文
         *
         * 只有两个进程需要初始化，主进程和 push 进程
         */
        //RongIM.setServerInfo("nav.cn.ronghub.com", "img.cn.ronghub.com");
        RongIM.init(this);
        SealAppContext.init(this);
//        SharedPreferencesContext.init(this);
        Thread.setDefaultUncaughtExceptionHandler(new RongExceptionHandler(this));

        try {
            RongIM.registerMessageType(GroupNotificationMessage.class);
            RongIM.registerMessageType(FileMessage.class);
//            RongIM.registerMessageTemplate(new ContactNotificationMessageProvider());
//            RongIM.registerMessageTemplate(new RealTimeLocationMessageProvider());
//            RongIM.registerMessageTemplate(new GroupNotificationMessageProvider());
            RongIM.registerMessageTemplate(new FileMessageItemProvider());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        options = new DisplayImageOptions.Builder()
//                .showImageForEmptyUri(R.drawable.de_default_portrait)
//                .showImageOnFail(R.drawable.de_default_portrait)
//                .showImageOnLoading(R.drawable.de_default_portrait)
//                .displayer(new FadeInBitmapDisplayer(300))
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .build();

        //初始化图片下载组件
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(200)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
//                .defaultDisplayImageOptions(options)
                .build();

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //解决notclass
        MultiDex.install(this);
    }
}
