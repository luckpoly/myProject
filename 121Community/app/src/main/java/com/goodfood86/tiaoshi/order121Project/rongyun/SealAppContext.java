package com.goodfood86.tiaoshi.order121Project.rongyun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.LocationMessage;

/**
 * Created by Administrator on 2016/9/14.
 */
public class SealAppContext implements RongIM.LocationProvider,RongIM.ConversationBehaviorListener {
    private static SealAppContext mRongCloudInstance;
    private Context mContext;
    private static ArrayList<Activity> mActivities;



    private RongIM.LocationProvider.LocationCallback mLastLocationCallback;
    public SealAppContext(Context mContext) {
        this.mContext = mContext;
        initListener();
        mActivities = new ArrayList<>();
    }

    /**
     * 获取RongCloud 实例。
     *
     * @return RongCloud。
     */
    public static SealAppContext getInstance() {
        return mRongCloudInstance;
    }
    @Override
    public void onStartLocation(Context context, LocationCallback locationCallback) {
        /**
         * demo 代码  开发者需替换成自己的代码。
         */
        SealAppContext.getInstance().setLastLocationCallback(locationCallback);
        Intent intent = new Intent(context, RChooseAddressActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * init 后就能设置的监听
     */
    private void initListener() {
        RongIM.setLocationProvider(this);//设置地理位置提供者,不用位置的同学可以注掉此行代码
        RongIM.setConversationBehaviorListener(this);
    }
    /**
     * 初始化 RongCloud.
     *
     * @param context 上下文。
     */
    public static void init(Context context) {

        if (mRongCloudInstance == null) {

            synchronized (SealAppContext.class) {

                if (mRongCloudInstance == null) {
                    mRongCloudInstance = new SealAppContext(context);
                }
            }
        }

    }
    public RongIM.LocationProvider.LocationCallback getLastLocationCallback() {
        return mLastLocationCallback;
    }

    public void setLastLocationCallback(RongIM.LocationProvider.LocationCallback lastLocationCallback) {
        this.mLastLocationCallback = lastLocationCallback;
    }

    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        Intent in = new Intent(context, UserInfoActivity.class);
        if (userInfo!=null){
            in.putExtra("userInfo",userInfo);
        }
        context.startActivity(in);
        return false;
    }

    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        return false;
    }

    @Override
    public boolean onMessageClick(Context context, View view, Message message) {
        if (message.getContent() instanceof LocationMessage) {
            Intent intent = new Intent(context, StoreAddressMapActivity.class);
            intent.putExtra("location", message.getContent());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        return false;
    }

    @Override
    public boolean onMessageLinkClick(Context context, String s) {
        return false;
    }

    @Override
    public boolean onMessageLongClick(Context context, View view, Message message) {
        return false;
    }
}
