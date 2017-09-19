package com.goodfood86.tiaoshi.order121Project.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

    	api =  Order121Application.getIwxapi();

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
					Toast.makeText(WXPayEntryActivity.this,"付款成功",Toast.LENGTH_SHORT).show();
					Order121Application.getInstance().addOrderActivity(WXPayEntryActivity.this);
					Order121Application.getInstance().finishOrderActivity();
					break;
				case -1:
					Toast.makeText(WXPayEntryActivity.this,"支付失败",Toast.LENGTH_SHORT).show();
					finish();
					break;
				case -2:
					Toast.makeText(WXPayEntryActivity.this,"支付取消",Toast.LENGTH_SHORT).show();
					finish();
					break;

			}


		}
	}

	@Override
	public void onReq(BaseReq baseReq) {

	}


}