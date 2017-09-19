package com.goodfood86.tiaoshi.order121Project.interfaces;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * Created by 鲜鲜哥 on 2015/12/11.
 */
public class BaseIUiListener implements IUiListener {
    private Context context;

    public BaseIUiListener(Context context) {
        this.context = context;
    }

    protected void doComplete() {
        Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onComplete(Object o) {
        doComplete();
    }

    @Override
    public void onError(UiError e) {
        Log.e("onError:", "code:" + e.errorCode + ", msg:"
                + e.errorMessage + ", detail:" + e.errorDetail);
    }
    @Override
    public void onCancel() {
        Log.e("onCancel", "");
    }
}
