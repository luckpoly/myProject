package com.brandsh.tiaoshi.wxapi;


import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.activity.PayOrderActivity;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
    	api = TiaoshiApplication.iwxapi;
        api.handleIntent(getIntent(), this);
    }
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}
	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			switch (resp.errCode){
				case 0:
//					ToastUtil.showShort(WXPayEntryActivity.this,"支付成功");
//					startActivity(new Intent(WXPayEntryActivity.this,PayOrderActivity.class).putExtra("from","WXPayEntryActivity"));
					Intent intent=new Intent("WXPaySuccess");
					sendBroadcast(intent);
					finish();
					break;
				case -1:
					ToastUtil.showShort(WXPayEntryActivity.this,"支付失败");
					finish();
					break;
				case -2:
					ToastUtil.showShort(WXPayEntryActivity.this,"支付取消");
					finish();
					break;
			}
		}
	}
	@Override
	public void onReq(BaseReq baseReq) {

	}
}