package com.goodfood86.tiaoshi.order121Project.wxapi;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.model.WXLogInModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.sina.weibo.sdk.utils.LogUtil;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;


public class WXEntryActivity extends FragmentActivity implements IWXAPIEventHandler {

    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    private static final String TAG = "WXEntryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        Order121Application.getIwxapi().handleIntent(getIntent(), this);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo";
        Order121Application.getIwxapi().sendReq(req);
        LogUtil.e("----","发送登录请求成功");
    }
    @Override
    public void onReq(BaseReq baseReq) {

    }
    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Toast.makeText(WXEntryActivity.this, "分享被拒绝", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Toast.makeText(WXEntryActivity.this, "取消分享", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_OK:
                Toast.makeText(WXEntryActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
                String code = ((SendAuth.Resp) baseResp).code;
                LogUtil.e("===","获取code成功"+code);
                getWeiXinInfo(code);
                break;

        }
    }
    public void getWeiXinInfo(String code) {
        Log.d("gaolei", "getWeiXinOpenId------------------------");
        String getWeiXinOpenIdUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?"
                + "appid="
                + "wx2d919de3398e3c11"
                + "&secret="
                + "3359b8c5dd1e87d2ebb0b27b8a9bb7a8"
                + "&code="
                + code
                + "&grant_type=authorization_code";
        OkHttpManager.getAsync(getWeiXinOpenIdUrl,new MyCallBack(1,this,new WXLogInModel(),handler));
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
            }
        }
    };
}
