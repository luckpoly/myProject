package com.brandsh.tiaoshi.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Property;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.activity.FCActivity;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.fragment.PhoneLoginFragment;
import com.brandsh.tiaoshi.fragment.RegisterFragment;
import com.brandsh.tiaoshi.model.WXLogInModel;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.LogUtil;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2017/1/3.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        api = TiaoshiApplication.iwxapi;
        api.handleIntent(getIntent(), this);
        LogUtil.e("发送登录请求成功");
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }
    @Override
    public void onResp(BaseResp baseResp) {
        LogUtil.e(baseResp.errCode+"");
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                String code = ((SendAuth.Resp) baseResp).code;
                LogUtil.e("获取code成功"+code);
                Intent intent;
                if (TiaoshiApplication.isLogin){
                    intent=new Intent("WXBindSuccess");
                }else {
                    intent=new Intent("WXLoginSuccess");
                }
                intent.putExtra("code",code);
                intent.putExtra("from","WX");
                sendBroadcast(intent);
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                finish();
                break;

        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }
    public void getWeiXinInfo(String code) {
        Log.d("gaolei", "getWeiXinOpenId------------------------");
        String getWeiXinOpenIdUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?"
                + "appid="
                + Constants.APP_ID
                + "&secret="
                + Constants.API_SEC
                + "&code="
                + code
                + "&grant_type=authorization_code";
        OkHttpManager.getAsync(getWeiXinOpenIdUrl,new MyCallBack(1,this,new WXLogInModel(),handler));
    }
    public void getUserInfo(String acctoken,String openid){
        String getWeiXinUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?"
                + "access_token="
                + acctoken
                + "&openid="
                +openid;
        OkHttpManager.getAsync(getWeiXinUserInfoUrl,new MyCallBack(2,this,new WXLogInModel(),handler));
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    WXLogInModel wxLogInModel= (WXLogInModel) msg.obj;
                    String  openId=wxLogInModel.getOpenid();
                    String  accessToken=wxLogInModel.getAccess_token();
                    ToastUtil.showShort(WXEntryActivity.this,openId+"++成功++"+accessToken);
                    break;
                case 2:

                    break;
            }
        }
    };
}
