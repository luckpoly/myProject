package com.brandsh.tiaoshi.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import io.github.xudaojie.qrcodelib.CaptureActivity;

/**
 * Created by xdj on 16/9/17.
 */

public class SimpleCaptureActivity extends CaptureActivity {
    protected Activity mActivity = this;

    private AlertDialog mDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void handleResult(String resultString) {
        if (TextUtils.isEmpty(resultString)) {
            restartPreview();
        } else {
            if (!TextUtils.isEmpty(getIntent().getStringExtra("isUse")) && getIntent().getStringExtra("isUse").equals("YES")) {
                Intent intent = new Intent();
                intent.putExtra("resultString", resultString);
                setResult(1, intent);
                finish();
                return;
            }
            // TODO: 16/9/17 ...
            if (resultString.length() >= 4 && resultString.substring(0, 4).equals("http")) {
                Intent intent = new Intent(SimpleCaptureActivity.this, JuiceMonthActivity.class);
                intent.putExtra("URL", resultString);
                startActivity(intent);
                finish();
            } else {
                if (mDialog == null) {
                    mDialog = new AlertDialog.Builder(mActivity)
                            .setMessage(resultString)
                            .setPositiveButton("确定", null)
                            .create();
                    mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            restartPreview();
                        }
                    });
                }
                if (!mDialog.isShowing()) {
                    mDialog.setMessage(resultString);
                    mDialog.show();
                }
            }
        }
    }
}
