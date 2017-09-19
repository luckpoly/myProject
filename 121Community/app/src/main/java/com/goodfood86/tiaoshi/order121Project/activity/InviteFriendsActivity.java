package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.interfaces.BaseIUiListener;
import com.goodfood86.tiaoshi.order121Project.model.GlobalLoginModel;
import com.goodfood86.tiaoshi.order121Project.model.InviteFriendModel;
import com.goodfood86.tiaoshi.order121Project.model.VerifyModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.widget.ProgressHUD;
import com.goodfood86.tiaoshi.order121Project.widget.TitleBarView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.tauth.Tencent;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2016/3/24.
 */
public class InviteFriendsActivity extends Activity implements View.OnClickListener,WeiboAuthListener ,IWeiboHandler.Response{
    @ViewInject(R.id.title_bar)
    private RelativeLayout title_bar;
    private TitleBarView titleBarView;
    @ViewInject(R.id.share_ivQq)
    private ImageView share_ivQq;
    @ViewInject(R.id.share_ivKongjian)
    private ImageView share_ivKongjian;
    @ViewInject(R.id.share_ivWeixin)
    private ImageView share_ivWeixin;
    @ViewInject(R.id.share_ivWeibo)
    private ImageView share_ivWeibo;
    @ViewInject(R.id.share_tvCode)
    private TextView share_tvCode;
    @ViewInject(R.id.share_btn)
    private Button share_btn;
    private IWXAPI iwxapi;
    private AlertDialog.Builder builder;
    private AuthInfo authInfo;
    private SsoHandler ssoHandler;
    private Tencent tencent;
    private Oauth2AccessToken oauth2AccessToken;
    private Intent intent;
    private String name;
    private RequestParams requestParams;
    private String phoneNumber;
    private HttpUtils httpUtils;
    private ProgressHUD dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invitefriendsactivity);
        ViewUtils.inject(this);
        tencent = Tencent.createInstance("1104941329", getApplicationContext());
        registerToWx();
        initTitlebar();
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

    private void initTitlebar() {
        titleBarView=new TitleBarView(this,title_bar,"邀请好友");
        builder = new AlertDialog.Builder(this).setTitle("微信分享").setMessage("分享到朋友圈还是微信好友");
//        authInfo = new AuthInfo(this, Order121Application.APP_KEY, Order121Application.REDIRECT_URL, Order121Application.SCOPE);
        ssoHandler = new SsoHandler(this, authInfo);

    }

    private void initListener() {
        dialog= ProgressHUD.show(this,"玩命加载中...",false,null);
        share_btn.setOnClickListener(this);
        share_ivQq.setOnClickListener(this);
        share_ivKongjian.setOnClickListener(this);
        share_ivWeixin.setOnClickListener(this);
        share_ivWeibo.setOnClickListener(this);
        String invitecode=Order121Application.globalLoginModel.getData().getInviteCode();
        share_tvCode.setText("动态邀请码：" + invitecode);
        intent = new Intent(Intent.ACTION_PICK);
        intent.setData(ContactsContract.Contacts.CONTENT_URI);
        httpUtils= Order121Application.getGlobalHttpUtils();
    }

    @Override
    public void onClick(View v) {
        if (v!=null){
            switch (v.getId()){
                case R.id.share_ivQq:
                    dialog.show();
                    disdialog();
                    onClickShare();

                break;
                case R.id.share_ivKongjian:
                    dialog.show();
                    disdialog();
                    shareToQzone ();

                    break;
                case R.id.share_ivWeixin:
                    builder.setPositiveButton("朋友圈", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            shareArticleToWx(1);
                        }
                    }).setNegativeButton("微信好友", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            shareArticleToWx(0);
                        }
                    }).create().show();
                    break;
                case R.id.share_ivWeibo:
                    break;
                case R.id.share_btn:
                    startActivityForResult(intent,0);
                    break;
            }

        }

    }


private void disdialog(){
    new Thread(){
        @Override
        public void run() {
            super.run();
            try {
                sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(2);
        }
    }.start();
}
    @Override
    protected void onRestart() {
        super.onRestart();
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (ssoHandler!= null) {
                ssoHandler.authorizeCallBack(requestCode, resultCode, data);
            }
            if(requestCode == 0){
                try{
                    //ContentResolver实例带的方法可实现找到指定的ContentProvider并获取到ContentProvider的数据
                    ContentResolver reContentResolverol = getContentResolver();
                    //URI,每个ContentProvider定义一个唯一的公开的URI,用于指定到它的数据集
                    Uri contactData = data.getData();
                    //查询就是输入URI等参数,其中URI是必须的,其他是可选的,如果系统能找到URI对应的ContentProvider将返回一个Cursor对象.
                    Cursor cursor = managedQuery(contactData, null, null, null, null);
                    cursor.moveToFirst();
                    //获得DATA表中的名字
                    name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    //条件为联系人ID
                    String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    // 获得DATA表中的电话号码，条件为联系人ID,因为手机号码可能会有多个
                    Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                            null,
                            null);
                    while (phone.moveToNext()) {
                        phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    String phoneNo=phoneNumber.trim().replace(" ", "");
                    Log.e("info", name + "\n" + phoneNo);
                    requestParams=new RequestParams();
//                    requestParams.addBodyParameter("secKey", Order121Application.getSecKey());
//                    requestParams.addBodyParameter("terminal", Order121Application.getTerminal());
//                    requestParams.addBodyParameter("deviceId", Order121Application.getDeviceId());
//                    requestParams.addBodyParameter("lng", longitude);
//                    requestParams.addBodyParameter("lat", latitude);
                    requestParams.addBodyParameter("token", Order121Application.globalLoginModel.getData().getToken());
                    requestParams.addBodyParameter("phone", phoneNo);
                    requestParams.addBodyParameter("type","2");
                    httpUtils.send(HttpRequest.HttpMethod.POST, G.Host.INVITE,requestParams,new MyRequestCallBack(InviteFriendsActivity.this,handler,1,new InviteFriendModel()));

                }catch (Exception e){
                    Toast.makeText(InviteFriendsActivity.this, "已取消邀请", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    InviteFriendModel jsonData1 = (InviteFriendModel) msg.obj;
                    if(jsonData1!=null){
                        Toast.makeText(InviteFriendsActivity.this, jsonData1.getRespMsg(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    if (dialog.isShowing()){
                        dialog.dismiss();
                    }

                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }

    private void registerToWx() {
        iwxapi = Order121Application.getIwxapi();
    }
    private void shareToQzone () {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "121下单");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "加入121下单，用行动来诠释生活！");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.86goodfood.com");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "121下单");
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        tencent.shareToQQ(InviteFriendsActivity.this, params, new BaseIUiListener(InviteFriendsActivity.this));
    }
    private void onClickShare() {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "121下单");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "加入121下单，用行动来诠释生活！！");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.86goodfood.com");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "121下单");
        tencent.shareToQQ(InviteFriendsActivity.this, params, new BaseIUiListener(InviteFriendsActivity.this));
    }
    private void shareArticleToWx(final int flag) {
        if (!iwxapi.isWXAppInstalled()) {
            Toast.makeText(InviteFriendsActivity.this, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }
        WXWebpageObject webPage = new WXWebpageObject();
        webPage.webpageUrl = "http://www.86goodfood.com";
        WXMediaMessage msg = new WXMediaMessage(webPage);
        msg.title = "121下单";
        msg.description = "加入121下单，用行动来诠释生活！！";
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        iwxapi.sendReq(req);
    }
    //分享到微博
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = "加入121下单，体验真正的快捷";
        return textObject;
    }
    private void sendMultiMessage(boolean hasText, boolean hasImage, boolean hasWebpage,
                                  boolean hasMusic, boolean hasVideo, boolean hasVoice) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();//初始化微博的分享消息
        if (hasText) {
            weiboMessage. textObject = getTextObj();
        }
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
    }

    @Override
    public void onResponse(BaseResponse baseResponse) {

        switch (baseResponse.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
                Toast.makeText(InviteFriendsActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                Toast.makeText(InviteFriendsActivity.this, "取消分享", Toast.LENGTH_SHORT).show();
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                Toast.makeText(InviteFriendsActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
    }



    @Override
    public void onComplete(Bundle bundle) {
        // 从 Bundle 中解析 Token
        oauth2AccessToken = Oauth2AccessToken.parseAccessToken(bundle);
    }

    @Override
    public void onWeiboException(WeiboException e) {

    }

    @Override
    public void onCancel() {

    }

}
